var accountInfoUsername = "";
var accountInfoPassword = "";
var userLoggedIn = false;

var accountInfoNotify = function(){
	nameNotification = true;
	
	var note = document.getElementById("notificationBody");
	var noteHTML = "<div class='genericPetImageContainer' style='cursor:default;margin-top:5px;'>";
	noteHTML += "<div id=\"myPetsTabPetImageNotify\" class='genericPetImage'></div>";
	noteHTML += "</div><br/>";
	noteHTML += "<div style='text-align:left;'>";
	noteHTML += "<b>Username:</b> "
	noteHTML += "<input id='accountInfoNotifyUsername' type='text' maxlength='20' style='width:142px;'/>";
	
	noteHTML += "<br/>";
	noteHTML += "<b style='margin-left:3px;'> Password:</b> ";
	noteHTML += "<input id='accountInfoNotifyPassword' type='password' maxlength='20' style='width:142px;' />";
	noteHTML += "</div>";
	noteHTML += "<div id='accountInfoNotifyText' style='font-weight:bold;margin-top:8px;color:#ff0000;'></div>";
	noteHTML += "<div style='width=180px; margin:0px auto; margin-top:10px;'>";
	noteHTML += "<div class='actionButton' style='float:left;width:80px;margin-left:60px;' onclick='accountInfoTrySignUp();'>Sign Up</div>";
	noteHTML += "<div class='actionButton' style='float:left;width:80px;' onclick='accountInfoTryLogIn();'>Log In</div>";
	noteHTML += "</div>";
	noteHTML += "<div class=\"clearer\"></div>";
	note.innerHTML = noteHTML;
		
	openNotification();
};

var accountInfoNotifyLogout = function(){
	nameNotification = true;
	
	var note = document.getElementById("notificationBody");
	var noteHTML = "<div class='genericPetImageContainer' style='cursor:default;margin-top:5px;'>";
	noteHTML += "<div id=\"myPetsTabPetImageNotify\" class='genericPetImage'></div>";
	noteHTML += "</div><br/>";
	noteHTML += "Are you sure you wish to log out of account:<br/><br/><b>" + accountInfoUsername + "</b>?<br/>";
	noteHTML += "<div id='accountInfoNotifyText' style='font-weight:bold;margin-top:8px;color:#ff0000;'></div>";
	noteHTML += "<div style='width=180px; margin:0px auto; margin-top:10px;'>";
	noteHTML += "<div class='actionButton' style='float:left;width:80px;margin-left:60px;' onclick='accountLogOut();'>Log Out</div>";
	noteHTML += "<div class='actionButton' style='float:left;width:80px;' onclick='closeNotification();'>Cancel</div>";
	noteHTML += "</div>";
	noteHTML += "<div class=\"clearer\"></div>";
	note.innerHTML = noteHTML;
		
	openNotification();
};

var accountInfoTrySignUp = function(){
	var username = document.getElementById("accountInfoNotifyUsername").value;
	var password = document.getElementById("accountInfoNotifyPassword").value;
	if (username == "" || password == ""){
		accountInfoBadCreate("Must enter username and password");
		return;
	}
	if (!validateAccountString(username) || !validateAccountString(password))
		return;
	createNewUser(username, password);
};

var accountInfoTryLogIn = function(){
	var username = document.getElementById("accountInfoNotifyUsername").value;
	var password = document.getElementById("accountInfoNotifyPassword").value;
	logInUser(username, password, true);
};

//
var validateAccountString = function(str){
	if(/^[a-zA-Z0-9-_!]*$/.test(str) == false) {
		accountInfoBadCreate("Username or password contains invalid characters.");
		return false;
	}
	return true;
};

//////////////////////
var accountInfoGoodLogin = function(){
	nameNotification = true;
	
	var note = document.getElementById("notificationBody");
	var noteHTML = "<div class='genericPetImageContainer' style='cursor:default;margin-top:5px;'>";
	noteHTML += "<div id=\"myPetsTabPetImageNotify\" class='genericPetImage'></div>";
	noteHTML += "</div><br/>";
	noteHTML += "Account with username:<br/><br/><b>" + accountInfoUsername + "</b><br/><br/>was successfully logged in!";
	noteHTML += "<div style='clear:both;'></div>";
	noteHTML += "<br/><div class='actionButton' style='float:none;width:80px;margin:0px auto; margin-top:10px;' onclick='closeNotification();'>Okay</div>";
	note.innerHTML = noteHTML;

	openNotification();
	UpdateAccountInfoArea(true);
};

var accountInfoBadLogin = function(errorText){
	document.getElementById("accountInfoNotifyText").innerHTML = errorText;
};

var accountInfoGoodCreate = function(){
	nameNotification = true;
	
	var note = document.getElementById("notificationBody");
	var noteHTML = "<div class='genericPetImageContainer' style='cursor:default;margin-top:5px;'>";
	noteHTML += "<div id=\"myPetsTabPetImageNotify\" class='genericPetImage'></div>";
	noteHTML += "</div><br/>";
	noteHTML += "Account with username:<br/><br/><b>" + accountInfoUsername + "</b><br/><br/>was successfully created!";
	noteHTML += "<div style='clear:both;'></div>";
	noteHTML += "<br/><div class='actionButton' style='float:none;width:80px;margin:0px auto; margin-top:10px;' onclick='closeNotification();'>Okay</div>";
	note.innerHTML = noteHTML;
		
	openNotification();
	UpdateAccountInfoArea(false);
};

var accountInfoBadCreate = function(errorText){
	document.getElementById("accountInfoNotifyText").innerHTML = errorText;
};

var accountLogOut = function(){
	userLoggedIn = false;
	accountInfoUsername = "";
	accountInfoPassword = "";
	
	deleteCookie("pixelpetslogin");
	deleteCookie("pixelpetspet1");
	deleteCookie("pixelpetspet2");
	deleteCookie("pixelpetspet3");
	deleteCookie("pixelpetspet4");
	deleteCookie("pixelpetsuserCodex");
	userPets = [];
	userPets.push(GetRandomPet());
	InitializeCodex();
	purpleTheme();
	gotoMyPets();
	myPetsTabgotoPet1();
	codexTabSelectedTab = null;
	codexTabLastSelectedTab = null;
	
	document.getElementById("logoutArea").innerHTML = "";
	document.getElementById("logoutArea").style.width = "0px";
	document.getElementById("accountArea").innerHTML = "<div id=\"loginButton\" style=\"margin-top:-5px;\" class=\"navButton\" onclick=\"accountInfoNotify();\">SIGN UP/LOG IN</div>";
	document.getElementById("pixelpetsHeader").style.left = "50px";
	document.getElementById("pixelpetsHeader").style.width = "540px";
	setTheme();
	closeNotification();
};

var UpdateAccountInfoArea = function(justLoggedIn){
	var accountArea = document.getElementById("accountArea");
	var text = "Logged in as<br/><b>" + accountInfoUsername + "</b>";
	document.getElementById("logoutArea").innerHTML = "<div id=\"logoutButton\" style=\"margin-top:-5px;margin-left:5px;\" class=\"navButton\" onclick=\"accountInfoNotifyLogout();\">LOG OUT</div>";
	document.getElementById("logoutArea").style.width = "80px";
	document.getElementById("pixelpetsHeader").style.left = "100px";
	document.getElementById("pixelpetsHeader").style.width = "640px";
	accountArea.innerHTML = text;
	if (!justLoggedIn){
		saveUserInfo(accountInfoUsername, accountInfoPassword);
		deleteCookie("pixelpetspet1");
		deleteCookie("pixelpetspet2");
		deleteCookie("pixelpetspet3");
		deleteCookie("pixelpetspet4");
	}
	else loadUserInfo(accountInfoUsername, accountInfoPassword);
	
	document.getElementById("accountInfoLoginWarning").innerHTML = "";
	
	userLoggedIn = true;
	dataUpdateCounter = 0;
	saveLocalData();
};