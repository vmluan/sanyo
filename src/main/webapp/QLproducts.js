var url = pageContext + "/products/getproductsjson";
var productCode='';
// prepare the data
var source =
        {
            datatype: "json",
            updaterow: function (rowid, rowdata, commit) {
                // synchronize with the server - send update command
                // call commit with parameter true if the synchronization with the server is successful
                // and with parameter false if the synchronization failder.
               console.log('update record');
                UpdateProduct(rowdata);
                commit(true);
            },
            datafields: [
                {name: 'productName', type: 'string'},
                {name: 'productPrice', type: 'float'},
                {name: 'picLocation', type: 'string'},
                {name: 'productCode', type: 'string'},
                {name: 'labour', type: 'string'},
				{name: 'mat_w_o_Tax_USD', type: 'string'},
				{name: 'mat_w_o_Tax_VND', type: 'string'},
				{name: 'specification', type: 'string'},
				{name: 'unit', type: 'string'}
				
            ],
            id: 'productID',
            url: url,
        	data : {
        		productGroupCode : productCode,
        		makerId : makerId
        	}
        };
var cellsrenderer = function(row, columnfield, value, defaulthtml, columnproperties, rowdata) {
    if (value < 20) {
        return '<span style="margin: 4px; float: ' + columnproperties.cellsalign + '; color: #ff0000;">' + value + '</span>';
    }
    else {
        return '<span style="margin: 4px; float: ' + columnproperties.cellsalign + '; color: #008000;">' + value + '</span>';
    }
}
var dataAdapter = new $.jqx.dataAdapter(source, {
    downloadComplete: function(data, status, xhr) {
    },
    loadComplete: function(data) {
    },
    loadError: function(xhr, status, error) {
    }
});
// initialize jqxGrid
$("#jqxgridProducts").jqxGrid(
        {
            width: '100%',
            height: 400,
            source: dataAdapter,
            sortable: true,
            pageable: true,
            autoheight: true,
            autoloadstate: false,
            autosavestate: false,
            columnsresize: true,
            columnsreorder: true,
            showfilterrow: true,
            filterable: true,
            autorowheight: true,
            editable: true,
            columns: [
				{
					text : '#',
					datafield : 'stt',
					width : '5%',
					columntype : 'number',
					align : 'center',
					cellsrenderer : function(row, column, value) {
						return "<div style='margin:4px;'>"
								+ (value + 1) + "</div>";
					}
				},
				{text: 'Specification', datafield: 'specification', width: '20%'},
				{text: 'Name', datafield: 'productName', width: '20%'},
				{text: 'Unit', datafield: 'unit', width: '10%'},
                {text: 'Labour USD', datafield: 'labour', align: 'right', cellsalign: 'right', cellsformat: 'c0', columntype: 'numberinput', width: '13%'},
				{text: 'Max USD', datafield: 'mat_w_o_Tax_USD', align: 'right', cellsalign: 'right', cellsformat: 'c0', columntype: 'numberinput', width: '00%',hidden: true },
				{text: 'Unit price VND', datafield: 'mat_w_o_Tax_VND', align: 'right', cellsalign: 'right', cellsformat: 'c0', columntype: 'numberinput', width: '17%'},
                {text: 'Action', datafield: 'Action', width: '15%',
                    cellsrenderer: function(row, column, value) {
                        return '<input type ="button" value="Edit" onClick = "updateProduct(' + row + ')"></input>'
                            + '<input type ="button" value="Delete" onClick = "deleteProduct(' + row + ')"></input>'
                            ;
                    },
                    cellbeginedit : function(row) {
                        return false;
                    }
                }				

            ]
        });

function updateProduct(row) {
    var value = $('#jqxgridProducts').jqxGrid('getcellvalue', row, "uid");
    location.href = pageContext + "/products/" + value + "?form";
}
;
function deleteProduct() {
    var selectedrowindex = $("#jqxgridProducts").jqxGrid('getselectedrowindex');
    var rowscount = $("#jqxgridProducts").jqxGrid('getdatainformation').rowscount;
    if (selectedrowindex >= 0 && selectedrowindex < rowscount) {
        var id = $("#jqxgridProducts").jqxGrid('getrowid', selectedrowindex);
        var productName = $('#jqxgridProducts').jqxGrid('getcellvalue', selectedrowindex, "productName");

        var result = confirm("Do  you want to delete " + productName + ' ?');
        if (result == true) {
            //Logic to delete the item
            var url = pageContext + '/products/' + id + '?delete';

            $.ajax({
                url: url,
                type: 'GET',
                // contentType:'application/json',
                //   data: jsonData,
                // dataType:'json',
                success: function(data) {
                    //On ajax success do this
                    location.reload();
                },
                error: function(xhr, ajaxOptions, thrownError) {
                    //On error do this


                    if (xhr.status == 200) {

                        alert("delete");
                        location.reload();
                    }
                    else {
                        alert(xhr.status);
                        alert(thrownError);
                    }
                }
            });

            var commit = $("#jqxgridProducts").jqxGrid('deleterow', selectedrowindex);
        }


    }
}
;
// events
$("#jqxgridProducts").on('cellbeginedit', function(event) {
    // event arguments.
    var args = event.args;
    // column data field.
    var dataField = event.args.datafield;
    if(dataField=='Action')
        return;

});
$("#jqxgridProducts").on('cellendedit', function(event) {

});

function UpdateProduct(data){
    var url = pageContext + '/products/' + data.uid + '?form';

    var product = new Object();
    product.productID = data.uid;
    product.productCode = data.productCode;
    product.productName = data.productName;
    product.specification = data.specification;
    product.labour = data.labour;
    product.unit = data.unit;
    //product.mat_w_o_Tax_USD = data.mat_w_o_Tax_USD;
    product.mat_w_o_Tax_VND = data.mat_w_o_Tax_VND;

    var jsonData = JSON.stringify(product);
    $.ajax({
        type: "POST",
        contentType : 'application/json',
        data: jsonData,
        url: url,
        success: function(msg){
            if(msg){
                alert('There was a problem with your submit: ' + msg)
            }else{
               // alert('Update successfully');
            }

        },
        error: function(msg){
            alert('There was a problem with your submit')
        }
    });
}

//for code checboxes
var codeUrl = pageContext + '/productgroups/getproductGroupJson';
var codeSource = {
    datatype : "json",
    datafields : [ {
        name : 'groupId',
        type : 'string'
    }, {
        name : 'groupName',
        type : 'string'
    }, {
        name : 'groupCode',
        type : 'string'
    } ],
    sortcolumn : 'groupCode',
    sortdirection : 'asc',
    id : 'groupId',
    url : codeUrl
};
var codeAdapter = new $.jqx.dataAdapter(codeSource, { autoBind :true,
    downloadComplete: function(data, status, xhr) {
    },
    loadComplete: function(data) {
    },
    loadError: function(xhr, status, error) {
    }
});
$("#codeCheckbox").jqxComboBox({
    autoDropDownHeight : true,
    source : codeAdapter,
    displayMember : "groupCode",
    valueMember : "groupCode",
    promptText : "Please Choose:"
});
$("#codeCheckbox").on('select', function(event) {
    var args = event.args;
    if (args) {
        var index = args.index;
        var item = args.item;
        if(item){
            // get item's label and value.
            var label = item.label;
            var value = item.value;
            updateMaker(value); // this method is defined in productList.jspx
        }
    }
});

$("#searchBtn").click(function() {
    var selectedMaker = $("#makerListDiv").jqxDropDownList('getSelectedItem');
    makerId = selectedMaker.value;

    var codeItem = getSelectedItemComboBox($("#codeCheckbox"));
    if(codeItem){
        source.data.productGroupCode=codeItem.value;
    }
    source.data.makerId=makerId;
    $("#jqxgridProducts").jqxGrid('updatebounddata');

});