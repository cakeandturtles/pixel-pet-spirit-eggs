var createNewUser = function(username, password){
	var createNewUserState = 0;
	
	//CREATE XML OBJECT
	if (window.XMLHttpRequest){ //code for IE7+, Firefox, Chrome, Opera, Safari
		xmlhttp = new XMLHttpRequest();
	}else{ //code for IE6, IE5
		xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
	}
	xmlhttp.onreadystatechange=function(){
		if (xmlhttp.readyState==4 && xmlhttp.status==200){
			//Return from checking
			if (createNewUserState == 0){ 
				//IF USERNAME ALREADY EXISTS...
				if (xmlhttp.responseText.indexOf("username=Exists") != -1){
					accountInfoBadCreate("Username already exists!");
				}
				//IF NOT, TRY TO INSERT NEW USER INTO DATABASE
				else if (xmlhttp.responseText.indexOf("username=Ok") != -1){ 
					var createUserUrl = "pixelPets/dataPHP/createUser.php";
					xmlhttp.open("POST", createUserUrl, true);
					xmlhttp.setRequestHeader("Content-type","application/x-www-form-urlencoded");
					xmlhttp.send("name="+username+"&pass="+password);
					doingDbOperation = true;
					createNewUserState = 1;
				}else{ //IF ERROR OCCURED
					accountInfoBadCreate("An error has occured. Please try again.");
				}
			}
			//Return from inserting into database
			else if (createNewUserState == 1){ 
				if (xmlhttp.responseText.indexOf("writing=Ok") != -1){ //ERROR 
					accountInfoUsername = username;
					accountInfoPassword = password;
					accountInfoGoodCreate();
				}else{
					accountInfoBadCreate("An error has occured. Please try again.");
				}
			}
		}
	};
	
	//CHECK IF USERNAME ALREADY EXISTS...
	var checkUserUrl = "pixelPets/dataPHP/checkUser.php";
	xmlhttp.open("POST", checkUserUrl, true);
	xmlhttp.setRequestHeader("Content-type","application/x-www-form-urlencoded");
	xmlhttp.send("name="+username);
};

var logInUser = function(username, password){
	//CREATE XML OBJECT
	if (window.XMLHttpRequest){ //code for IE7+, Firefox, Chrome, Opera, Safari
		xmlhttp = new XMLHttpRequest();
	}else{ //code for IE6, IE5
		xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
	}
	xmlhttp.onreadystatechange=function(){
		if (xmlhttp.readyState==4 && xmlhttp.status==200){
			if (xmlhttp.responseText.indexOf("login=Ok") != -1){ //LOG IN WAS SUCCESSFUL
				accountInfoUsername = username;
				accountInfoPassword = password;
				accountInfoGoodLogin();
			}else if (xmlhttp.responseText.indexOf("login=Bad") != -1){ //BAD USERNAME/PASSWORD
				accountInfoBadLogin("Incorrect username or password.");
			}else{ //ERROR OCCURED
				accountInfoBadLogin(xmlhttp.responseText);
			}
		}
	}
	
	//TRY TO LOG IN USER
	var logInUserUrl = "pixelPets/dataPHP/loginUser.php";
	xmlhttp.open("POST", logInUserUrl, true);
	xmlhttp.setRequestHeader("Content-type","application/x-www-form-urlencoded");
	xmlhttp.send("name="+username+"&pass="+password);
};

////////////////////////////////////////////////////////////////////////////////////////////////////
//////SAVING AND LOADING////////////////////////////////////////////////////////////////////////////

var saveUserInfo = function(username, password){
	//CREATE XML OBJECT
	if (window.XMLHttpRequest){ //code for IE7+, Firefox, Chrome, Opera, Safari
		xmlhttp = new XMLHttpRequest();
	}else{ //code for IE6, IE5
		xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
	}
	xmlhttp.onreadystatechange=function(){
		if (xmlhttp.readyState==4 && xmlhttp.status==200){
			if (xmlhttp.responseText.indexOf("writing=Ok") != -1){ //SAVE WAS SUCCESSFUL
				//alert("Successful save!");
			}else{ //ERROR OCCURED
				//TODO::
				//alert(xmlhttp.responseText);
			}
		}
	}
	
	//SET THE POST VARIABLES
	var postHeader = "name="+username+"&pass="+password;
	postHeader += "&themeColor="+darkColor;
	//SET POST VARIABLES FOR PET 1
	postHeader += "&pet1exists="+(document.getElementById("form_pet1exists").checked ? 1 : 0);
	postHeader += "&pet1canRelease="+(document.getElementById("form_pet1canRelease").checked ? 1 : 0);
	postHeader += "&pet1petSpecies="+document.getElementById("form_pet1petSpecies").value;
	postHeader += "&pet1speciesIndex="+document.getElementById("form_pet1speciesIndex").value;
	postHeader += "&pet1petForm="+document.getElementById("form_pet1petForm").value;
	postHeader += "&pet1wasHappyTeen="+(document.getElementById("form_pet1wasHappyTeen").checked ? 1 : 0);
	postHeader += "&pet1mood="+document.getElementById("form_pet1mood").value;
	postHeader += "&pet1nextEventTime="+document.getElementById("form_pet1nextEventTime").value;
	postHeader += "&pet1expTimer="+document.getElementById("form_pet1expTimer").value;
	postHeader += "&pet1aniY="+document.getElementById("form_pet1aniY").value;
	postHeader += "&pet1name="+document.getElementById("form_pet1name").value;
	postHeader += "&pet1formChange="+(document.getElementById("form_pet1formChange").checked ? 1 : 0);
	//SET POST VARIABLES FOR PET 2
	postHeader += "&pet2exists="+(document.getElementById("form_pet2exists").checked ? 1 : 0);
	postHeader += "&pet2canRelease="+(document.getElementById("form_pet2canRelease").checked ? 1 : 0);
	postHeader += "&pet2petSpecies="+document.getElementById("form_pet2petSpecies").value;
	postHeader += "&pet2speciesIndex="+document.getElementById("form_pet2speciesIndex").value;
	postHeader += "&pet2petForm="+document.getElementById("form_pet2petForm").value;
	postHeader += "&pet2wasHappyTeen="+(document.getElementById("form_pet2wasHappyTeen").checked ? 1 : 0);
	postHeader += "&pet2mood="+document.getElementById("form_pet2mood").value;
	postHeader += "&pet2nextEventTime="+document.getElementById("form_pet2nextEventTime").value;
	postHeader += "&pet2expTimer="+document.getElementById("form_pet2expTimer").value;
	postHeader += "&pet2aniY="+document.getElementById("form_pet2aniY").value;
	postHeader += "&pet2name="+document.getElementById("form_pet2name").value;
	postHeader += "&pet2formChange="+(document.getElementById("form_pet2formChange").checked ? 1 : 0);
	//SET POST VARIABLES FOR PET 3
	postHeader += "&pet3exists="+(document.getElementById("form_pet3exists").checked ? 1 : 0);
	postHeader += "&pet3canRelease="+(document.getElementById("form_pet3canRelease").checked ? 1 : 0);
	postHeader += "&pet3petSpecies="+document.getElementById("form_pet3petSpecies").value;
	postHeader += "&pet3speciesIndex="+document.getElementById("form_pet3speciesIndex").value;
	postHeader += "&pet3petForm="+document.getElementById("form_pet3petForm").value;
	postHeader += "&pet3wasHappyTeen="+(document.getElementById("form_pet3wasHappyTeen").checked ? 1 : 0);
	postHeader += "&pet3mood="+document.getElementById("form_pet3mood").value;
	postHeader += "&pet3nextEventTime="+document.getElementById("form_pet3nextEventTime").value;
	postHeader += "&pet3expTimer="+document.getElementById("form_pet3expTimer").value;
	postHeader += "&pet3aniY="+document.getElementById("form_pet3aniY").value;
	postHeader += "&pet3name="+document.getElementById("form_pet3name").value;
	postHeader += "&pet3formChange="+(document.getElementById("form_pet3formChange").checked ? 1 : 0);
	//SET POST VARIABLES FOR PET 4
	postHeader += "&pet4exists="+(document.getElementById("form_pet4exists").checked ? 1 : 0);
	postHeader += "&pet4canRelease="+(document.getElementById("form_pet4canRelease").checked ? 1 : 0);
	postHeader += "&pet4petSpecies="+document.getElementById("form_pet4petSpecies").value;
	postHeader += "&pet4speciesIndex="+document.getElementById("form_pet4speciesIndex").value;
	postHeader += "&pet4petForm="+document.getElementById("form_pet4petForm").value;
	postHeader += "&pet4wasHappyTeen="+(document.getElementById("form_pet4wasHappyTeen").checked ? 1 : 0);
	postHeader += "&pet4mood="+document.getElementById("form_pet4mood").value;
	postHeader += "&pet4nextEventTime="+document.getElementById("form_pet4nextEventTime").value;
	postHeader += "&pet4expTimer="+document.getElementById("form_pet4expTimer").value;
	postHeader += "&pet4aniY="+document.getElementById("form_pet4aniY").value;
	postHeader += "&pet4name="+document.getElementById("form_pet4name").value;
	postHeader += "&pet4formChange="+(document.getElementById("form_pet4formChange").checked ? 1 : 0);
	//SET POST VARIABLES FOR THE CODEX
	var index = 0;
	for (var i = 1; i <= 9; i++){
		postHeader += "&uc0"+i+"="+(userCodex[index].inCodex ? 1 : 0);
		index++;
	}
	for (var i = 11; i <= 89; i++){
		if (i%10==0) continue;
		postHeader += "&uc"+i+"="+(userCodex[index].inCodex ? 1 : 0);
		index++;
	}
	
	//ACTUALLY MAKE THE REQUEST
	xmlhttp.open("POST", "pixelPets/dataPHP/save.php", true);
	xmlhttp.setRequestHeader("Content-type","application/x-www-form-urlencoded");
	xmlhttp.send(postHeader);
};

var loadUserInfo = function(username, password){
	//CREATE XML OBJECT
	if (window.XMLHttpRequest){ //code for IE7+, Firefox, Chrome, Opera, Safari
		xmlhttp = new XMLHttpRequest();
	}else{ //code for IE6, IE5
		xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
	}
	xmlhttp.onreadystatechange=function(){
		if (xmlhttp.readyState==4 && xmlhttp.status==200){
			if (xmlhttp.responseText.indexOf("load=Ok") != -1){ //LOAD WAS SUCCESSFUL
				loadDataIntoApp(xmlhttp.responseText);
			}else{ //ERROR OCCURED
				//TODO::
				//alert("Error: "+xmlhttp.responseText);
			}
		}
	}
	
	//ACTUALLY MAKE THE REQUEST
	xmlhttp.open("POST", "pixelPets/dataPHP/load.php", true);
	xmlhttp.setRequestHeader("Content-type","application/x-www-form-urlencoded");
	xmlhttp.send("name="+username+"&pass="+password);
};

var loadDataIntoApp = function(dataResponse){
	var data = dataResponse.split(",");
	var index = 0;
	//LOAD THEME
	var themeColor = data[index]; index++;
	if (themeColor == "#ceb6c3") purpleTheme();
	else if (themeColor == "#fae161") orangeTheme();
	else if (themeColor == "#92b6db") blueTheme();
	else if (themeColor == "#faaa70") redTheme();
	else if (themeColor == "#a4e676") greenTheme();
	else if (themeColor == "#0f1613") greyTheme();
	
	userPets = [null];
	//LOAD PET 1 BACK!!!
	index++;
	var tempCanRelease = new Boolean(parseInt(data[index])) == true; index++;
	userPets[0] = getNewPetFromSpeciesString(data[index]);
	userPets[0].canRelease = tempCanRelease; index++;
	userPets[0].speciesIndex = parseInt(data[index]);
	userPets[0].species = userPets[0].petSpeciesObj.species[data[index]]; index++;
	userPets[0].prevSpecies = userPets[0].species;
	userPets[0].petForm = data[index]; index++;
	userPets[0].wasHappyTeen = new Boolean(parseInt(data[index])) == true; index++;
	userPets[0].mood = parseInt(data[index]); index++;
	userPets[0].nextEventTime = parseInt(data[index]); index++;
	userPets[0].expTimer = parseInt(data[index]); index++;
	userPets[0].aniY = parseInt(data[index]); index++;
	userPets[0].name = data[index]; index++;
	userPets[0].formChange = new Boolean(parseInt(data[index])) == true; index++;
	
	//LOAD PET 2 BACK!!!
	var exists = new Boolean(parseInt(data[index])); index++;
	if (exists == true){
		tempCanRelease = new Boolean(parseInt(data[index])) == true; index++;
		userPets.push(getNewPetFromSpeciesString(data[index]));
		userPets[1].canRelease = tempCanRelease; index++;
		userPets[1].speciesIndex = parseInt(data[index]);
		userPets[1].species = userPets[1].petSpeciesObj.species[data[index]]; index++;
		userPets[1].prevSpecies = userPets[1].species;
		userPets[1].petForm = data[index]; index++;
		userPets[1].wasHappyTeen = new Boolean(parseInt(data[index])) == true; index++;
		userPets[1].mood = parseInt(data[index]); index++;
		userPets[1].nextEventTime = parseInt(data[index]); index++;
		userPets[1].expTimer = parseInt(data[index]); index++;
		userPets[1].aniY = parseInt(data[index]); index++;
		userPets[1].name = data[index]; index++;
		userPets[1].formChange = new Boolean(parseInt(data[index])) == true; index++;
	}else{
		index += 11;
	}
	
	//LOAD PET 3 BACK!!!
	exists = new Boolean(parseInt(data[index])); index++;
	if (exists == true){
		tempCanRelease = new Boolean(parseInt(data[index])) == true; index++;
		userPets.push(getNewPetFromSpeciesString(data[index]));
		userPets[2].canRelease = tempCanRelease; index++;
		userPets[2].speciesIndex = parseInt(data[index]);
		userPets[2].species = userPets[2].petSpeciesObj.species[data[index]]; index++;
		userPets[2].prevSpecies = userPets[2].species;
		userPets[2].petForm = data[index]; index++;
		userPets[2].wasHappyTeen = new Boolean(parseInt(data[index])) == true; index++;
		userPets[2].mood = parseInt(data[index]); index++;
		userPets[2].nextEventTime = parseInt(data[index]); index++;
		userPets[2].expTimer = parseInt(data[index]); index++;
		userPets[2].aniY = parseInt(data[index]); index++;
		userPets[2].name = data[index]; index++;
		userPets[2].formChange = new Boolean(parseInt(data[index])) == true; index++;
	}else{
		index += 11;
	}
	
	//LOAD PET 4 BACK!!!
	exists = new Boolean(parseInt(data[index])); index++;
	if (exists == true){
		tempCanRelease = new Boolean(parseInt(data[index])) == true; index++;
		userPets.push(getNewPetFromSpeciesString(data[index]));
		userPets[3].canRelease = tempCanRelease; index++;
		userPets[3].speciesIndex = parseInt(data[index]);
		userPets[3].species = userPets[3].petSpeciesObj.species[data[index]]; index++;
		userPets[3].prevSpecies = userPets[3].species;
		userPets[3].petForm = data[index]; index++;
		userPets[3].wasHappyTeen = new Boolean(parseInt(data[index])) == true; index++;
		userPets[3].mood = parseInt(data[index]); index++;
		userPets[3].nextEventTime = parseInt(data[index]); index++;
		userPets[3].expTimer = parseInt(data[index]); index++;
		userPets[3].aniY = parseInt(data[index]); index++;
		userPets[3].name = data[index]; index++;
		userPets[3].formChange = new Boolean(parseInt(data[index])) == true; index++;
	}else{
		index += 11;
	}
	
	//NOW GO THROUGH AND LOAD CODEX
	for (var i = 0; i < userCodex.length; i++){
		userCodex[i].inCodex = (new Boolean(parseInt(data[index]))) == true;
		index++;
	}
};