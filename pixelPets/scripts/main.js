var selectedNav;
var tabThemeControl = function(){};
var userPets = [
	new Puglett(),
	new Chloropillar(),
	new Tadpox(),
	new Fledgwing()
];

window.onload = function(){
	purpleTheme();
	gotoMyPets();
};

//Warning, may return array containing different tag types?
//Modified from http://stackoverflow.com/questions/3808808/how-to-get-element-by-class-in-javascript
var getElementsByClass = function(matchClass){ 
	var elems = document.getElementsByTagName('*');
	var elemsOfClass = new Array();
	for (var i = 0; i < elems.length; i++){
		if ((' ' + elems[i].className + ' ').indexOf(' ' + matchClass + ' ') > -1){
			elemsOfClass.push(elems[i]);
		}
	}
	return elemsOfClass;
};