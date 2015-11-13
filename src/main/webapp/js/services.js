
var sampleAppServices = angular.module('sampleappservices', ['ngResource']);

sampleAppServices.factory('guestbookService', 
		['$resource', 
		 	function($resource) {
			
			
				return $resource('http://localhost:8888/guestbook/myguestbook/:verb', {}, 
						{query: {method:'GET', params:{}, isArray:false} });
					
			
			}
		]
);
			
			
			
	
	
	
