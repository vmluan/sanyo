		 var temp = '${table}';

//var cart = (function ($) {
theme = $.jqx.theme;
//var productsOffset = 3,
var productsOffset = 50, products = sampleProducts, theme, onCart = false, cartItems = [], totalPrice = 0;
var sttID;

function render() {
	productsRendering();
	gridRendering();
};

function addClasses() {
	$('.draggable-demo-catalog')
			.addClass('jqx-scrollbar-state-normal-' + theme);
	$('.draggable-demo-title').addClass('jqx-expander-header-' + theme);
	$('.draggable-demo-title')
			.addClass('jqx-expander-header-expanded-' + theme);
	$('.draggable-demo-total').addClass('jqx-expander-header-' + theme)
			.addClass('jqx-expander-header-expanded-' + theme);
	if (theme === 'shinyblack') {
		$('.draggable-demo-shop').css('background-color', '#555');
		$('.draggable-demo-product').css('background-color', '#999');
	}
};

function productsRendering() {
	var catalog = $('#catalog'), imageContainer = $('</div>'), image, product, left = 0, top = 30, counter = 0;
	for ( var name in products) {
		product = products[name];
		image = createProduct(name, product);
		image.appendTo(catalog);
		if (counter !== 0 && counter % 4 === 0) {
			top += 140 + 30; // image.outerHeight() + productsOffset;
			left = 0;
		}
		image.css({
			left : left,
			top : top
		});
		left += 115 + productsOffset; // image.outerWidth() + productsOffset;
		counter += 1;
	}
//	$('.draggable-demo-product').jqxDragDrop({
//		dropTarget : $('#cart'),
//		revert : true
//	});
};

function createProduct(name, product) {

	return $('<div class="draggable-demo-product jqx-rc-all">'
			+ '<div class="jqx-rc-t draggable-demo-product-header jqx-widget-header-'
			+ theme + ' jqx-fill-state-normal-' + theme + '">'
			+ '<div class="draggable-demo-product-header-label"> ' + name
			+ '</div></div>' + '<div class="jqx-fill-state-normal-' + theme
			+ ' draggable-demo-product-price"><strong>$' + product.price
			+ '</strong></div>' + '<img src="/images/' + product.pic
			+ '"' + ' alt="' + name + '" class="jqx-rc-b" />' + '</div>');
};

function gridRendering() {
	$("#jqxgrid").jqxGrid({
		height : 300,
		//width: 290,
		width : '100%',

		keyboardnavigation : false,
		selectionmode : 'none',
		columns : [ {
			text : 'STT',
			dataField : 'stt',
			width : '5%'
		}, {
			text : '',
			dataField : 'hiddenCount',
			width : 0,
			hidden: true
		}, {
			text : 'Tên',
			dataField : 'name',
			width : '70%'
		}, {
			text : 'Số lượng',
			dataField : 'count',
			width : '25%'
		}
		]
	});

};

function init() {
	// alert(table);

	theme = getDemoTheme();
	render();
	addClasses();
	addEventListeners();
	if (table) {
		loadItem();
		document.getElementById('tableNumber').value = table.tableNumber;
		document.getElementById('tableName').value = table.tableName;
		document.getElementById('customerName').value = table.customerName;
	}
	document.getElementById("tableName").focus();
	
};

//load item to the list.
function loadItem() {

	for (var i = 0; i <= table.encounters.length - 1; i++) {
		var item = new Object();
		item.price = parseInt(table.encounters[i].price);
		item.name = table.encounters[i].product.productName;

		addItem({
			price : parseInt(item.price),
			name : item.name
		}, table.encounters[i].quantity);
	}

};

function addItem(item, quantity) {
	var index = getItemIndex(item.name);
	if (index >= 0) {
		
		updateQuantity(index, quantity);

	} else {
		
		var id = cartItems.length, newItem = {
			name : item.name,
			//count: 1,
			count : getRemoveCode(id) + getInputCode(id, 1) + getAddCode(id),
			price : item.price,
			index : id,
			stt : id + 1,
			hiddenCount : 1
		};

		cartItems.push(newItem);
		addGridRow(newItem);

	}
	updatePrice(item.price*quantity);
};
/*
 * add function to update quantity value 
 */
function updateQuantity(index, number, newUpdate) {
	var currentValue = parseInt($("#jqxgrid").jqxGrid('getrowdata', index).hiddenCount, 10);
	var newValue = parseInt(currentValue)  + parseInt(number);
	cartItems[index].count = getRemoveCode(index) + getInputCode(index, newValue) + getAddCode(index);
	cartItems[index].hiddenCount += parseInt(number);
	if(cartItems[index].hiddenCount <= 0){
		cartItems.splice(index,1);
		removeGridRow(index);
		
		
	}else
		updateGridRow(index, cartItems[index]);
	if(newUpdate){
		submitBan(cartItems[index].name, number);
		updatePrice(cartItems[index].price * number);
		}
};

function udpateQuantityID(index) {
	for ( var id = index; id < cartItems.length; id++) {
		var value = cartItems[id].hiddenCount;
		var removeButton = getRemoveCode(id);
		var input = getInputCode(id, value);
		var addButton = getAddCode(id);
		
		cartItems[id].count = removeButton + input + addButton;
		updateGridRow(id, cartItems[id]);
	}
};

function getRemoveCode(id){
	return '<button type="button" id="removeButton" onClick="removeButton('
			+ id
			+ ')"><span class="glyphicon glyphicon-arrow-down"></span></button>';
};
function getAddCode(id){
	return '<button type="button" id="addButton'
			+ id
			+ '" onClick="addButton('
			+ id
			+ ')"><span class="glyphicon glyphicon-arrow-up"></span></button>';
};
function getInputCode(id, value){
	return '<input '
			+ 'id="row'
			+ id
			+ 'jqxgridQuantity" '
			+ 'type="text" value="'
			+ value
			+ '" style="width: 35%; height: 100%; border: 0px;background-color: rgb(197, 225, 226);" class="rowQuantity" onChange="updateCount(' + id +')"/>';
}
/*
	* this function is to update the index and stt of cartItems
*/
function updateOrderingNumber(index){
	for ( var id = index; id < cartItems.length; id++) {
		cartItems[id].index = id;
		cartItems[id].stt = cartItems[id].index + 1;
		
		updateGridRow(id, cartItems[id]);
	}

};
function updateCount(index){
	if (!checkTableName())
		return;
	var inputID = '#row' + index + 'jqxgridQuantity';
	if (table.status == 'PAID')
	{
		alert("Ban da tinh tien. Hay tao order cho luot ban moi. ");
		$('#jqxgrid').jqxGrid('refresh');
		return;
	}
	
	var newValue = parseInt($(inputID).val(), 10);
	var delta = newValue - cartItems[index].hiddenCount;
	updateQuantity(index, delta, true);

}

function updatePrice(price) {
	totalPrice += price;
	var formatTotalPrice =  formatCurrency(totalPrice.toString(), '$');
	$('#total').html(formatTotalPrice);
};

function addGridRow(row) {
	$("#jqxgrid").jqxGrid('addrow', null, row);
};

function updateGridRow(id, row) {
	var rowID = $("#jqxgrid").jqxGrid('getrowid', id);
	$("#jqxgrid").jqxGrid('updaterow', rowID, row);
};

function removeGridRow(id) {
	var rowID = $("#jqxgrid").jqxGrid('getrowid', id);
	$("#jqxgrid").jqxGrid('deleterow', rowID);
	
	udpateQuantityID(id);
	updateOrderingNumber(id);	
};

function getItemIndex(name) {
	for ( var i = 0; i < cartItems.length; i += 1) {
		if (cartItems[i].name === name) {
			return i;
		}
	}
	return -1;
};

function toArray(obj) {
	var item, array = [], counter = 1;
	for ( var key in obj) {
		item = {};
		item = {
			name : key,
			price : obj[key].count,
			count : obj[key].price,
			number : counter
		}
		array.push(item);
		counter += 1;
	}
	return array;
};

function addEventListeners() {
	$('.draggable-demo-product').bind(
			'click',
			function(event) {
				if (!checkTableName())
					return;
				if(table.status == 'DRINKING' || !table.status){
					var productName = $(this).find('.draggable-demo-product-header')
							.text().trim(), price = $(this).find(
							'.draggable-demo-product-price').text().replace(
							'$', '');
					price = parseInt(price, 10);

					addItem({
						price : price,
						name : productName
					}, 1);
					
					submitBan(productName, 1); //submit ban every user click on drink				
				}else if (table.status == 'PAID')
				{
					alert("Ban da tinh tien. Hay tao order cho luot ban moi. ")
				}
			});

};

//        return {
//           init: init
//      };

//  } ($));

function removeButton(index) {
	if (!checkTableName())
		return;
	if (table.status == 'PAID')
	{
		alert("Ban da tinh tien. Hay tao order cho luot ban moi. ");
		return;
	}
	var item = cartItems[index];
	var count = item.hiddenCount;

	if (count > 1) {
		updateQuantity(index, -1);
	} else {
		cartItems.splice(index, 1);
		removeGridRow(index);
		//update quantity input id
	//	udpateQuantityID(index);
	//	updateOrderingNumber(index);

	}
	updatePrice(-item.price);
	submitBan(item.name, -1); // save to database every click on drinks
};
function addButton(index) {
	if (!checkTableName())
		return;
	if (table.status == 'PAID')
	{
		alert("Ban da tinh tien. Hay tao order cho luot ban moi. ");
		return;
	}
	var item = cartItems[index];

	if (index >= 0)
		updateQuantity(index, 1);
	updatePrice(item.price);
	submitBan(item.name, 1);// save to database every click on drinks
};

$(document).ready(function() {
	init();
	
	 $("#tableName").jqxTooltip({ content: '<b>So ban:</b>', position: 'top', name: 'movieTooltip'});
	 
	  $("#customerName").jqxTooltip({ content: '<b>Ten Khach Hang</b>', position: 'top', name: 'movieTooltip'});
	  $('.draggable-demo-product').css('cursor', 'pointer');
	  
	  $("#customerName").change(function(){
			submitBan();
		
		});
	  $("#tableName").change(function(){
			submitBan();
		
		});
});

function checkTableName(){
	var tableName = document.getElementById('tableName').value;

	if (!tableName) {
		alert("Vui lòng nhập số bàn");
		return false;
	}
	return true;
};

function submitBan(productName, quantity, PAID) {
	if (table.status == 'PAID')
	{
		alert("Ban da tinh tien. Hay tao order cho luot ban moi. ");
		return;
	}
	$('.btn').attr('disabled','disabled');
	
	var	tableID = table.tableID;

	var encounters = new Array();

	var tableNumber = document.getElementById('tableNumber').value;
	var tableName = document.getElementById('tableName').value;
	var customerName = document.getElementById('customerName').value;

	if (!checkTableName())
		return;


	if (productName && quantity){
		var product = new Object();
		var encounter = new Object();

		product.productName = productName;
		encounter.product = product;
		encounter.quantity = quantity;
		encounters[0] = encounter;
	}
	var updatedTable = new Object();
	updatedTable.encounters = encounters;
	updatedTable.customerName = customerName;
	updatedTable.tableNumber = tableNumber;
	updatedTable.tableName = tableName;
	var url, urlGet, urlPost;
	urlGet = '/quanlyban?form';
	
	if (PAID){
		updatedTable.status = PAID;
	}

	if (tableID) {
		urlPost = '/quanlyban/' + tableID + '?form';
		url = urlPost;
		updatedTable.tableID = tableID;
	} else
		url = urlGet;

	
	var jsonData = JSON.stringify(updatedTable);
	console.log(jsonData);
	$.ajax({
		url : url,
		type : 'POST',
		contentType : 'application/json',
		data : jsonData,
		//dataType : 'json',
		success : function(data) {
			//On ajax success do this
			if($(data).find('#table_is_paid').size > 0){
				alert("Ban da tinh tien. Hay tao order cho luot ban moi. ");
				return;
			}
			$('.btn').removeAttr('disabled');

			$("#messageNotification").jqxNotification("open");
			$(".jqx-notification-container").css("z-index", 30000);
			
			if(PAID == 'PAID'){
				table.status = PAID;
				$("#checkInButton").text('Tao ban moi');
				$("#checkInButton").attr("onClick", "newTable();");
				$("#tableStatus").text('Da tinh tien');
				
				
			}
		},
		error : function(xhr, ajaxOptions, thrownError) {
			//On error do this

			if (xhr.status == 200) {

			} else {
				alert(xhr.status);
				alert(thrownError);
			}
		}
	});

};

function newTable(){
	var url = '/quanlyban/' + table.tableNumber + '?tableacr';
	
	$.ajax({
		url : url,
		type : 'GET',
		dataType: 'html',
		data: {newtable: true},
		success : function(data) {
			//On ajax success do this
			
		//	var output = $($.parseHTML(data)).filter("#productcart");
			var source = $('<div>' + data + '</div>');
			var tableID = parseInt(source.find('#tableID').text());		
			$('#jqxgrid').jqxGrid('clear');
			
			table.encounters = null;
			table.tableID = tableID;
			table.status = 'DRINKING';
			
			$('#tableID').html(source.find('#tableID').html());
			$.getScript('/resources/QLban.js', function(){});
			
			$("#checkInButton").text('Tinh Tien (F8)');
			$("#checkInButton").attr("onClick", "submitBan(null, null,'PAID');");
			$("#tableStatus").text('Ban moi');			
		
			
		},
		error : function(xhr, ajaxOptions, thrownError) {
			//On error do this

			if (xhr.status == 200) {
				console.log(xhr);
			} else {
				alert(xhr.status);
				alert(thrownError);
			}
		}
	});	
};
