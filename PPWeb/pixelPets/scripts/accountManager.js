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
	logInUser(username, password);
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

var UpdateAccountInfoArea = function(justLoggedIn){
	var accountArea = document.getElementById("accountArea");
	var text = "Logged in as<br/><b>" + accountInfoUsername + "</b>";
	accountArea.innerHTML = text;
	if (!justLoggedIn)
		saveUserInfo(accountInfoUsername, accountInfoPassword);
	else loadUserInfo(accountInfoUsername, accountInfoPassword);
	
	userLoggedIn = true;
	dataUpdateCounter = 0;
};