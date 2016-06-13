<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Book Management Console</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">
    <link href="<c:url value='/css/app.css' />" rel="stylesheet">
    <script src="https://www.google.com/recaptcha/api.js" async defer></script>
</head>
<body ng-app="myApp" class="ng-cloak">
<script type="text/ng-template" id="addContent.html">
    <div class="formcontainer">
        <form ng-submit="submit(this)" name="myForm" class="form-horizontal">
            <input type="hidden" ng-model="ctrl.book.id" />
            <div class="row">
                <div class="form-group col-md-12">
                    <label class="col-md-2 control-lable" for="bookname">Book Name</label>
                    <div class="col-md-7">
                        <input type="text" ng-model="ctrl.book.bookName" id="bookname" name="bookname" class="bookName form-control input-sm" placeholder="Enter book your name" required ng-minlength="3"/>
                        <div class="has-error" ng-show="myForm.$dirty">
                            <span ng-show="myForm.bookname.$error.required">This is a required field</span>
                            <span ng-show="myForm.bookname.$error.minlength">Minimum length required is 3</span>
                            <span ng-show="myForm.bookname.$invalid">This field is invalid </span>
                        </div>
                    </div>
                </div>
            </div>


            <div class="row">
                <div class="form-group col-md-12">
                    <label class="col-md-2 control-lable" for="author">Author</label>
                    <div class="col-md-7">
                        <input type="text" ng-model="ctrl.book.author" id="author" name="author" class="form-control input-sm" placeholder="Enter Author Name" required/>
                        <div class="has-error" ng-show="myForm.$dirty">
                            <span ng-show="myForm.author.$error.required">This is a required field</span>
                            <span ng-show="myForm.author.$error.minlength">Minimum length required is 3</span>
                            <span ng-show="myForm.author.$invalid">This field is invalid </span>
                        </div>
                    </div>
                </div>
            </div>

            <div class="row">
                <div class="form-group col-md-12">
                    <div class="col-md-2"></div>
                    <div class="col-md-7">
                        <span id="captcha" style="margin-left:100px;color:red" ng-init="onloadCallback()" />
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="form-actions floatRight">
                    <input type="submit"  value="{{!ctrl.book.id ? 'Add' : 'Update'}}" class="btn btn-primary btn-sm" ng-disabled="myForm.$invalid">
                    <button type="button" ng-click="reset()" class="btn btn-warning btn-sm" ng-disabled="myForm.$pristine">Reset Form</button>
                </div>
            </div>
        </form>
    </div>
</script>

<script type="text/ng-template" id="removeContent.html">
    <div class="formcontainer">
        <div>Are you sure to remove Book with name {{ctrl.book.bookName}}</div>
        <button type="button" ng-click="remove()" class="btn btn-danger btn-sm" ng-disabled="myForm.$pristine">Remove</button>
        <button type="button" ng-click="cancel()" class="btn btn-warning btn-sm" ng-disabled="myForm.$pristine">Cancel</button>
    </div>
</script>

<div class="generic-container" ng-controller="BookController as ctrl">

    <div class="panel panel-default">
        <div class="panel-heading"><span class="lead">Book Management Page </span></div>

    </div>

    <div class="panel panel-default">
        <!-- Default panel contents -->
        <div class="panel-heading"><span class="lead">List of Books </span> <button ng-click='open()'>Add</button> </div>
        <div class="tablecontainer">
            <table class="table table-hover">
                <thead>
                <tr>
                    <th>ID.</th>
                    <th>Book Name</th>
                    <th>Author</th>
                    <th width="20%"></th>
                </tr>
                </thead>
                <tbody>
                <tr ng-repeat="book in ctrl.books">
                    <td><span ng-bind="book.id"></span></td>
                    <td><span ng-bind="book.bookName"></span></td>
                    <td><span ng-bind="book.author"></span></td>
                    <td>

                        <button type="button" ng-click="open(book,false)" class="btn btn-success custom-width">Edit</button>  <button type="button" ng-click="open(book,true)" class="btn btn-danger custom-width">Remove</button>
                    </td>
                </tr>
                </tbody>
            </table>
        </div>
    </div>
</div>
<script src="<c:url value='/js/angular.js' />"></script>
<script src="<c:url value='/js/ui-bootstrap-tpls-0.10.0.js' />"></script>
<script src="<c:url value='/js/app.js' />"></script>
<script src="<c:url value='/js/service/book_service.js' />"></script>
<script src="<c:url value='/js/controller/book_controller.js' />"></script>

</body>
</html>