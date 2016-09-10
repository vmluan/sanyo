/**
 * Created by luan on 8/27/16.
 */        console.log('tested');
var myapp = angular.module('myapp', ['']);
myapp.controller('revisionController', function ($scope) {
        var date = new Date();
        $scope.date = date;
        console.log('test');
    }
);
angular.bootstrap(document, ['myapp']);
