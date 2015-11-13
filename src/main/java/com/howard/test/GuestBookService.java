package com.howard.test;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import model.Greeting;
import model.GuestBook;
import model.GuestBookStatistics;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.ObjectifyService;
import com.googlecode.objectify.VoidWork;


@Path("guestbook")
public class GuestBookService {
	
	@Inject
	Worker worker;
	
	
	@GET
	@Path("{guestbookname}/get/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Greeting getMessage (
			@PathParam("guestbookname") String name,
			@PathParam("id") long id) {
		return ObjectifyService.ofy().load().key(Key.create(Key.create(GuestBook.class, name), Greeting.class, id)).now();
	}
	
	
		
	@GET
	@Path("{guestbookname}/add")
	@Produces(MediaType.APPLICATION_JSON)
	public Greeting addMessage (
			@PathParam("guestbookname") String name, 
			@QueryParam("email") String email,
			@QueryParam("id") String id,
			@QueryParam("message") String message) {
				
		final Greeting greeting = new Greeting(name, message, id, email);
		final Key<GuestBook> theBook = Key.create(GuestBook.class, name);
		
		ObjectifyService.ofy().transact(new VoidWork () {

			@Override
			public void vrun() {
				GuestBookStatistics stats = ObjectifyService.ofy()
				.load()
				.type(GuestBookStatistics.class) 
				.ancestor(theBook) 
				.first().now();
				
				ObjectifyService.ofy().save().entity(greeting).now();
				
				if (stats == null) {
					stats = new GuestBookStatistics(theBook, 1);
				} else {				
					stats.messageCount = stats.messageCount + 1;
				}
				
				System.out.println("Message count is: " + stats.messageCount);
				
				ObjectifyService.ofy().save().entity(stats).now();
			}
		});
				
		worker.doSomeWork();
		
		return greeting;
	}

}
