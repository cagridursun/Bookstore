'use strict';

App.factory('BookService', ['$http', '$q', function($http, $q){

	return {
		
			fetchAllBooks: function() {
					return $http.get('http://localhost:8787/book/')
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
					return $http.post('http://localhost:8787/book/', book)
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
					return $http.post('http://localhost:8787/book/'+id, book)
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
					return $http.delete('http://localhost:8787/book/'+id)
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
