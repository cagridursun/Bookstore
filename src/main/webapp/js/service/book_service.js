'use strict';

App.factory('BookService', ['$http', '$q', function($http, $q){

	return {
		
			fetchAllBooks: function() {
					return $http.get('/book/')
							.then(
									function(response){
										return response.data;
									}, 
									function(errResponse){
										console.error('Error while fetching books');
										return $q.reject(errResponse);
									}
							);
			},
		    
		    createBook: function(book){
					return $http.post('/book/', book)
							.then(
									function(response){
										return response.data;
									}, 
									function(errResponse){
										console.error('Error while creating book');
										return $q.reject(errResponse);
									}
							);
		    },
		    
		    updateBook: function(book, id){
					return $http.post('/book/'+id, book)
							.then(
									function(response){
										return response.data;
									}, 
									function(errResponse){
										console.error('Error while updating book');
										return $q.reject(errResponse);
									}
							);
			},
		    
			deleteBook: function(id){
					return $http.delete('/book/'+id)
							.then(
									function(response){
										return response.data;
									}, 
									function(errResponse){
										console.error('Error while deleting book');
										return $q.reject(errResponse);
									}
							);
			}
		
	};

}]);
