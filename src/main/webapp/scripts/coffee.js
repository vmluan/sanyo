function formatCurrency(value, currency) {
	return currency
			+ value.replace(/\D/g, "").replace(/\B(?=(\d{3})+(?!\d))/g, ".");
}

function updateHeaderStatus(currentActiveDiv, newActiveDiv){
	var currentElem = document.getElementById(currentActiveDiv);
	var newElem = document.getElementById(newActiveDiv);
	currentElem.className="";
	newElem.className="active";
}

function setDivActive(newActiveDiv){
	var currentActive = document.getElementsByClassName('active');
	for (var i =0; i < currentActive.length; i++){
		currentActive[i].className="";
	}
	var newElem = document.getElementById(newActiveDiv);
	if(newElem)
		newElem.className="active";
}
function reloadProducts(){
	categories = [];
	var selectedBoxes = $(".table-filter");
	var j = 0;
	for(var i = 0; i < selectedBoxes.length; i++){
		var checkBox = selectedBoxes[i];
		
		if (checkBox.checked == true){
			categories[j] = checkBox.value;
			j++;
			}
	}
	var url = '/quanlyban/findProductByCategories';
	var allCats;
	if(categories.length ==0){
		allCats = 'all';
		categories[0] = 0; //to avoid error
	}
		$.ajax({
			url : url,
			type : 'GET',
			dataType: 'text',
			 data: { ids: categories, allCats: allCats},
			success : function(data) {
					products=  eval('(' + data + ')' );
					$('.draggable-demo-product').remove();
					productsRendering();
					addEventListeners();
					$('.draggable-demo-product').css('cursor', 'pointer');
			},
			error : function(xhr, ajaxOptions, thrownError) {
				//On error do this
				alert('eror' + thrownError);
				if (xhr.status == 200) {
					
					products=  eval('(' + xhr.responseText + ')' );
					$('.draggable-demo-product').remove();
					productsRendering();
					addEventListeners();
					$('.draggable-demo-product').css('cursor', 'pointer');
					
				} else {
					alert(xhr.status);
					alert(thrownError);
				}
			}
		});

	
}
	// for image preview
    function readURL(input) {
        if (input.files && input.files[0]) {
            var reader = new FileReader();
            
            reader.onload = function (e) {
                $('#imgPrev').attr('src', e.target.result);
            }
            
            reader.readAsDataURL(input.files[0]);
        }
    }
    
    $("#imgUpload").change(function(){
        readURL(this);
    });