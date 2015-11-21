var url = pageContext + "/products/getproductsjson";
// prepare the data
var source =
        {
            datatype: "json",
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
        		productGroupCode : $("#productGroupCode").val(),
        		makerId : makerId,
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
            columnsresize: true,
            autorowheight: true,
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
                {text: 'Labour', datafield: 'labour', align: 'right', cellsalign: 'right', cellsformat: 'c0', columntype: 'numberinput', width: '10%'},
				{text: 'Max USD', datafield: 'mat_w_o_Tax_USD', align: 'right', cellsalign: 'right', cellsformat: 'c0', columntype: 'numberinput', width: '10%'},
				{text: 'Max VND', datafield: 'mat_w_o_Tax_VND', align: 'right', cellsalign: 'right', cellsformat: 'c0', columntype: 'numberinput', width: '10%'},
                {text: 'Action', datafield: 'Action', width: '15%',
                    cellsrenderer: function(row, column, value) {
                        return '<input type ="button" value="Edit" onClick = "updateProduct(' + row + ')"></input>'
                                + '<input type ="button" value="Delete" onClick = "deleteProduct(' + row + ')"></input>'
                                ;
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

});
$("#jqxgridProducts").on('cellendedit', function(event) {

});

function checkAddProduct() {
    var price = $("#productPriceWrapper").val().trim();
    var name = $("#productName").val();
    var common = document.getElementById("common_check").checked;
    alert("common: " + common);
    if (price == "" || name == "") {
        alert("Vui lòng nhập đủ tên và giá sản phẩm mới !");
        return false;
    }

    $.ajax({
        type: 'POST',
        url: "http://localhost:8080/products?form",
        data: {
            price: price,
            name: name,
            common: common,
            file: $("#file").val()
        },
        success: function(msg) {
            alert(msg);
        }
    });
}