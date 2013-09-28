var gardenTabHTML = "";
gardenTabHTML += "<div id='GardenButton' class=\"navButton\" style=\"background:#ffffff; float:left;padding-left:20px;padding-right:20px;visibility:hidden;\" onclick=''>Garden</div>";
gardenTabHTML += "<div class=\"clearer\"></div><br/>";
gardenTabHTML += "<div style=\"float:left;\" onclick=''>The Plains</div>";
gardenTabHTML += "<div class=\"clearer\"></div><br/>";

gardenTabHTML += "<div style=\"display:inline-block;\">";
gardenTabHTML += "<div class='genericPlantImageContainer'>";
gardenTabHTML += "<div id=\"gardenTabPlantImage1\" class='genericPlantImage'></div>";
gardenTabHTML += "</div>";
gardenTabHTML += "<div class='genericPlantImageContainer'>";
gardenTabHTML += "<div id=\"gardenTabPlantImage2\" class='genericPlantImage'></div>";
gardenTabHTML += "</div>";
gardenTabHTML += "<div class='genericPlantImageContainer'>";
gardenTabHTML += "<div id=\"gardenTabPlantImage3\" class='genericPlantImage'></div>";
gardenTabHTML += "</div>";
gardenTabHTML += "<div class='genericPlantImageContainer'>";
gardenTabHTML += "<div id=\"gardenTabPlantImage4\" class='genericPlantImage'></div>";
gardenTabHTML += "</div>";
gardenTabHTML += "</div>";
gardenTabHTML += "<div class=\"clearer\"></div><br/>";

gardenTabHTML += "<div id=\"gardenTabDescription\" style=\"height:32px;\">Welcome to the vast plains of petopia!</div><br/>";
gardenTabHTML += "<div id=\"gardenTabActionButtons\" style=\"display: inline-block;\">";
gardenTabHTML += "<div id=\"gardenTabPlantFruit\" class=\"actionButton\" onclick=''>Plant Fruit</div>";
gardenTabHTML += "<div id=\"gardenTabPickFruit\" class=\"actionButton\" onclick=''>Harvest Tree</div>";
gardenTabHTML += "</div>";

var gardenTabScriptBegin = function(){
	tabThemeControl = gardenTabThemeControl;
};

var gardenTabScriptEnd = function(){
	tabThemeControl = function(){ };
};

/////////////////////////////////////////////////////////////////////////

var gardenTabThemeControl = function(){
};