var myApp = angular.module('sampleapp',['sampleappservices', 'ngResource']);

myApp.controller('GuestBookController', 
	['$scope', '$resource', 'guestbookService',
        function($scope, $http, guestbookService) {
			$scope.messages = [];
			
			$scope.createMessage = function () {
				$scope.messages.push(guestbookService.get({verb:'add', email:$scope.message.author_email, id:$scope.message.author_id, message:$scope.message.content}));
				$scope.message = {};
			};
		}
	]
);
