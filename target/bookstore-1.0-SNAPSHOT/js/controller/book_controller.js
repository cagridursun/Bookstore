'use strict';

App.controller('BookController', ['$scope', '$modal', 'BookService',  function ($scope, $modal, BookService) {

    var self = this;
    self.book = {id: null, bookName: '', author: ''};
    self.books = [];
    var widgetId;



    self.fetchAllBooks = function () {
        BookService.fetchAllBooks()
            .then(
                function (d) {
                    self.books = d;
                },
                function (errResponse) {
                    console.error('Error while fetching Books');
                }
            );
    };

    self.createBook = function (book) {
        var v = grecaptcha.getResponse(widgetId);
        if (v.length != 0) {
            BookService.createBook(book)
                .then(
                    self.fetchAllBooks,
                    function (errResponse) {
                        console.error('Error while creating Book.');
                    }
                );
        }
        else {
            document.getElementById('captcha').innerHTML = "You can't leave Captcha Code empty";
        }
    };

    self.updateBook = function (book, id) {
        var v = grecaptcha.getResponse(widgetId);
        if (v.length != 0) {
            BookService.updateBook(book, id)
                .then(
                    self.fetchAllBooks,
                    function (errResponse) {
                        console.error('Error while updating Book.');
                    }
                );
        }
        else {
            document.getElementById('captcha').innerHTML = "You can't leave Captcha Code empty";
        }
    };

    self.deleteBook = function (id) {
        BookService.deleteBook(id)
            .then(
                self.fetchAllBooks,
                function (errResponse) {
                    console.error('Error while deleting Book.');
                }
            );
    };

    self.fetchAllBooks();

    self.submit = function () {
        if (self.book.id == null) {
            self.createBook(self.book);
        } else {
            self.updateBook(self.book, self.book.id);
        }

    };

    self.edit = function (id) {

        for (var i = 0; i < self.books.length; i++) {
            if (self.books[i].id == id) {
                self.book = angular.copy(self.books[i]);
                break;
            }
        }
    };

    self.remove = function (id) {
        if (self.book.id === id) {
            self.reset();
        }
        self.deleteBook(id);
    };



    self.reset = function () {
        self.book = {id: null, bookName: '', author: ''};
        $scope.myForm.$setPristine(); //reset Form
        grecaptcha.reset(widgetId);
    };

    $scope['delete'] = function() {
        $scope.deleted = 'Deleted';
    };

    $scope['deleteConfirm'] = function() {
        $confirm({text: 'Are you sure you want to delete?'})
            .then(function() {
                $scope.deletedConfirm = 'Deleted';
            });
    };

    $scope['deleteConfirmWithSettings'] = function(settings) {
        $confirm(angular.extend({text: 'Are you sure you want to delete?'}, settings || {}))
            .then(function() {
                $scope.deletedConfirm = 'Deleted';
            });
    };

    $scope.open = function (book,remove) {
        $scope.modal={};
        if(remove){
            $scope.modal.templateUrl = 'removeContent.html';
        }
        else {
            $scope.modal.templateUrl = 'myModalContent.html';
        }
        $modal.open({
            templateUrl: $scope.modal.templateUrl,
            backdrop: true,
            windowClass: 'modal',
            controller: function ($scope, $modalInstance) {

                $scope.ctrl = {};
                $scope.ctrl.book = angular.copy(book);

                $scope.onloadCallback = function () {
                    widgetId = grecaptcha.render(document.getElementById('captcha'), {
                        'sitekey' : '6LelDSITAAAAAMhfhXEvgqy2jqsQ8KORLLWlhz1Q'
                    });
                };


                $scope.submit = function (myForm) {
                    var v = grecaptcha.getResponse(widgetId);
                    if (v.length != 0) {
                        self.book = myForm.ctrl.book;
                        self.submit();
                        $modalInstance.dismiss('cancel');
                    }
                    else {
                        document.getElementById('captcha').innerHTML = "You can't leave Captcha Code empty";
                    }
                };

                $scope.cancel = function () {
                    $modalInstance.dismiss('cancel');
                };

                $scope.reset = function () {
                    $scope.ctrl.book = {id: null, bookName: '', author: ''};
                    grecaptcha.reset(widgetId);
                };

                $scope.remove = function (){
                    self.remove(book.id);
                    $modalInstance.dismiss('cancel');
                };

            }
        });


    };

}]);
