var url = "products/getproductsjson";
// prepare the data
var source =
        {
            datatype: "json",
            datafields: [
                {name: 'productName', type: 'string'},
                {name: 'productPrice', type: 'float'},
                {name: 'picLocation', type: 'string'}
            ],
            id: 'productID',
            url: url
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
            width: 1000,
            height: 1000,
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
                    rowsheight: 140,
            //	autorowheight: true,
            columns: [
                {text: '', datafield: 'Edit', width: '15%',
                    cellsrenderer: function(row, column, value) {
                        return '<input type ="button" value="Sua" onClick = "updateProduct(' + row + ')"></input>'
                                + '<input type ="button" value="Xoa" onClick = "deleteProduct(' + row + ')"></input>'
                                ;
                    }
                },
                {text: 'STT', datafield: 'stt', width: '5%', columntype: 'number',
                    cellsrenderer: function(row, column, value) {
                        return row + 1;
                    }
                },
                {text: 'Ten SP', datafield: 'productName', width: '30%'},
                {text: 'Gia', datafield: 'productPrice', align: 'right', cellsalign: 'right', cellsformat: 'c0', columntype: 'numberinput', width: '15%'},
                {text: 'Hinh anh', datafield: 'picLocation', width: '35%',
                    cellsrenderer: function(row, column, value) {
                        if (value)
                            return '<img src="../../images/' + value + '"/>';
                    }
                }

            ]
        });

function updateProduct(row) {
    var value = $('#jqxgridProducts').jqxGrid('getcellvalue', row, "uid");
    location.href = "/products/" + value + "?form";
}
;
function deleteProduct() {
    var selectedrowindex = $("#jqxgridProducts").jqxGrid('getselectedrowindex');
    var rowscount = $("#jqxgridProducts").jqxGrid('getdatainformation').rowscount;
    if (selectedrowindex >= 0 && selectedrowindex < rowscount) {
        var id = $("#jqxgridProducts").jqxGrid('getrowid', selectedrowindex);
        var productName = $('#jqxgridProducts').jqxGrid('getcellvalue', selectedrowindex, "productName");

        var result = confirm("Ban co chac muon xoa " + productName + ' ?');
        if (result == true) {
            //Logic to delete the item
            var url = '/products/' + id + '?delete';

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