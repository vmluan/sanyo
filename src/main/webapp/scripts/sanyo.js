/*
* this file contains common js function used by this application
* 
*/
function goBack(projectId) {
//    window.history.back();
	window.location.href=pageContext + '/projects/' + projectId + '?form';
}
