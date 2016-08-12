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
