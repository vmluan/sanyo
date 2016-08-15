/**
 * Created by luan on 8/14/16.
 */


/*
var myapp = angular.module('myapp', ['ui']);
myapp.controller('quotationController', function ($scope) {

});
angular.bootstrap(document, ['myapp']);


function updateAngularJSList(result){
    var scope = angular.element($("#angularjsResult")).scope();
    console.log(scope);
    scope.$apply(function(){
        scope.list = result;
    });

}*/
var myapp = angular.module('myapp', ['ui']);
myapp.controller('quotationController', function ($scope) {
    $scope.list=[];
    $scope.sortableOptionsA = {

        stop : function(e, ui) {
            var item = ui.item.scope().item;
            var fromIndex = ui.item.sortable.index;
            var sortable = ui.item.sortable.resort;
            if(sortable){
                var newList = ui.item.sortable.resort.$modelValue;
                var toIndex;
                //find toIndex
                for (x in newList){
                    var newItem = newList[x];
                    if(item.encounterID == newItem.encounterID)
                        toIndex = x;
                }
                if(fromIndex != toIndex){
                    console.log('moved', item, fromIndex, toIndex);

                    //call service to update the order from [fromIndex, toIndex];
                    var startPos, endPos;
                    if(fromIndex < toIndex){
                        startPos=fromIndex;
                        endPos=toIndex;
                    }else{
                        startPos=toIndex;
                        endPos=fromIndex;
                    }
                    for(i=startPos;i<=endPos;i++){
                        var changedItem = newList[i];
                        var encounterId = changedItem.encounterID;
                        var newPos = +i + +1;
                        updateEncounterOrder(encounterId,newPos);
                    }
                }

            }

        }
    };

});
/*
myapp.directive('sortable', function ($timeout) {
    return function (scope, element, attributes) {
        element.sortable({
            stop : function(event, ui){
                scope.$apply(function () {
                    console.log(element.sortable('toArray'));

                    //scope.syncOrder(element.sortable('toArray'));
                });
            }
        });
    };
});*/
angular.bootstrap(document, ['myapp']);



function updateAngularJSList(result){
    var scope = angular.element($("#angularjsResult")).scope();
    scope.$apply(function(){
        var oldList = scope.list;
        console.log(oldList);
        console.log(result);
        scope.list = result;
    });
}
function updateEncounterOrder(encounterId, newOrder){
    var jsonData = {id:encounterId,newPosition:newOrder};
    var url = pageContext + '/quotation/updateQuotaitonOrderAngularJS';
    makeGetRequestJson(url,jsonData,handleAfterUpdateingOrder);
}
function handleAfterUpdateingOrder(result){
    // call searchBtn button click to reload result
    $("#searchBtn").click();

}