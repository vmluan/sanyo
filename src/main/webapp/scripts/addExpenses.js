/**
 * Created by HISP-6 on 04-11-2015.
 */
function saveExpensesList() {
    var expenseList = new Object();

    expenseList.expenseElement_1 = $("#expenseElement_1").val();
    expenseList.expenseElement_2 = $("#expenseElement_2").val();
    expenseList.expenseElement_3 = $("#expenseElement_3").val();
    expenseList.expenseElement_4 = $("#expenseElement_4").val();
    expenseList.expenseElement_5 = $("#expenseElement_5").val();
    expenseList.expenseElement_6 = $("#expenseElement_6").val();
    expenseList.expenseElement_7 = $("#expenseElement_7").val();
    expenseList.expenseElement_8 = $("#expenseElement_8").val();
    expenseList.expenseElement_9 = $("#expenseElement_9").val();
    expenseList.expenseElement_10 = $("#expenseElement_10").val();
    expenseList.expenseElement_11 = $("#expenseElement_11").val();
    expenseList.expenseElement_12 = $("#expenseElement_12").val();
    expenseList.expenseElement_13 = $("#expenseElement_13").val();
    expenseList.expenseElement_14 = $("#expenseElement_14").val();
    expenseList.expenseElement_15= $("#expenseElement_15").val();
    expenseList.expenseElement_16 = $("#expenseElement_16").val();
    expenseList.expenseElement_17 = $("#expenseElement_17").val();
    expenseList.expenseElement_18 = $("#expenseElement_18").val();
    expenseList.expenseElement_19 = $("#expenseElement_19").val();
    expenseList.expenseElement_20 = $("#expenseElement_20").val();
    expenseList.expenseElement_21 = $("#expenseElement_21").val();
    expenseList.expenseElement_22 = $("#expenseElement_22").val();
    expenseList.expenseElement_23 = $("#expenseElement_23").val();
    expenseList.expenseElement_24 = $("#expenseElement_24").val();
    expenseList.expenseElement_25 = $("#expenseElement_25").val();
    expenseList.expenseElement_26 = $("#expenseElement_26").val();
    expenseList.expenseElement_27 = $("#expenseElement_27").val();
    expenseList.expenseElement_28 = $("#expenseElement_28").val();
    expenseList.expenseElement_29 = $("#expenseElement_29").val();
    expenseList.expenseElement_30 = $("#expenseElement_30").val();
    expenseList.expenseElement_31 = $("#expenseElement_31").val();
    expenseList.expenseElement_32 = $("#expenseElement_32").val();
    expenseList.expenseElement_33 = $("#expenseElement_33").val();
    expenseList.expenseElement_34 = $("#expenseElement_34").val();
    expenseList.expenseElement_35 = $("#expenseElement_35").val();
    expenseList.expenseElement_36 = $("#expenseElement_36").val();
    expenseList.expenseElement_37 = $("#expenseElement_37").val();
    expenseList.expenseElement_38 = $("#expenseElement_38").val();
    expenseList.expenseElement_39 = $("#expenseElement_39").val();
    expenseList.expenseElement_40 = $("#expenseElement_40").val();
    expenseList.expenseElement_41 = $("#expenseElement_41").val();
    expenseList.expenseElement_42 = $("#expenseElement_42").val();
    expenseList.expenseElement_43 = $("#expenseElement_43").val();
    expenseList.expenseElement_44 = $("#expenseElement_44").val();
    expenseList.expenseElement_45 = $("#expenseElement_45").val();
    expenseList.expenseElement_46 = $("#expenseElement_46").val();
    expenseList.expenseElement_47 = $("#expenseElement_47").val();
    expenseList.expenseElement_48 = $("#expenseElement_48").val();
    expenseList.expenseElement_49 = $("#expenseElement_49").val();
    expenseList.expenseElement_50 = $("#expenseElement_50").val();
    expenseList.expenseElement_51 = $("#expenseElement_51").val();
    expenseList.expenseElement_52 = $("#expenseElement_52").val();
    expenseList.expenseElement_53 = $("#expenseElement_53").val();
    expenseList.expenseElement_54 = $("#expenseElement_54").val();
    expenseList.expenseElement_55 = $("#expenseElement_55").val();
    expenseList.expenseElement_56 = $("#expenseElement_56").val();
    expenseList.expenseElement_57 = $("#expenseElement_57").val();
    expenseList.expenseElement_58 = $("#expenseElement_58").val();
    expenseList.expenseElement_59 = $("#expenseElement_59").val();
    expenseList.expenseElement_60 = $("#expenseElement_60").val();
    expenseList.expenseElement_61 = $("#expenseElement_61").val();


    var jsonData = JSON.stringify(expenseList);
    console.log(jsonData);
    var url = pageContext + '/expenses/1/saveExpenselist?form';
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
