/*
* this file contains common js function used by this application
* 
*/
function goBack(projectId) {
//    window.history.back();
	window.location.href=pageContext + '/projects/' + projectId + '?form';
}

function activeLink(id){
	$(".sanyoHead").removeClass();
	$('#' + id).addClass('active');
	$(".treeview-menu").css("display", "none");
}
function getDecimalNumber(input) {
	var result = Number(input).toFixed(4);
	return result;
}

//following are common methods used for jqxgrid components
function getSelectedItemComboBox( combobox){
	var item = combobox.jqxComboBox('getSelectedItem');
	return item;
}
/*
common function used to get json output by GET method
 */
function makeGetRequestJson(url,jsonData, handleData){
	$.ajax({
		type : "GET",
		data : jsonData,
		contentType : 'application/json',
		url : url,
		success : function(msg) {
			handleData(msg);
		},
		complete : function(xhr, status) {

		}
	});
}

function getDatePicker(obj,dateFormat,width, height, dateFormat){
	var width = '200px';
	var height = '32px';
	var format = 'MM/dd/yyyy';
	obj.jqxDateTimeInput({
		width: width,
		height: height,
		formatString: format
	});

}
