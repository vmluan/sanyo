/**
 * Created by HISP-6 on 04-11-2015.
 */
function saveExpensesList() {
    var expenseList = new Object();

    makerList.categoryName = data.categoryName;
    makerList.productGroupName = data.productGroupName;
    makerList.modelNo = data.modelNo;
    makerList.makerName = data.makerName;
    makerList.delivery = data.delivery;
    makerList.remarks = data.remarks;
    makerList.makerId = data.makerId;
    makerList.categoryId = data.categoryId;
    makerList.productGroupId = data.productGroupId;
    makerList.equivalent = data.equivalent;

    var jsonData = JSON.stringify(makerList);
    console.log(jsonData);
    var url = pageContext + '/quotation/1/addmakerlist?form';
    $.ajax({
        type : "POST",
        contentType : 'application/json',
        data : jsonData,
        url : url,
        success : function(msg) {
            $("#list").jqxGrid('updatebounddata');
            $("#listResult").jqxGrid('updatebounddata');
            // $('#list').jqxGrid('addrow', null, {}, 'first');

            $("#list").jqxGrid('begincelledit', 0, "categoryName");
        },
        complete : function(xhr, status) {
            // $("#assignRegionButton").prop('disabled', false);
        }
    });

}
