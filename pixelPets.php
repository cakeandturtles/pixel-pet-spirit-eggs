<!DOCTYPE html>
<html>
	<head>
		<title>Pixel Pets!</title>
		<link href="pixelPets/pixelPetStyle.css" rel="stylesheet" type="text/css">
		<!--JSON2-->
		<script language="javascript" type="text/javascript" src="pixelPets/json2.js"></script>
		<!--Declaration of PixelPet class and species-->
		<script language="javascript" type="text/javascript" src="pixelPets/scripts/PixelPet.js"></script>
		<script language="javascript" type="text/javascript" src="pixelPets/scripts/PetSpecies.js"></script>
		<script language="javascript" type="text/javascript" src="pixelPets/scripts/PetResponses.js"></script>
		
		<!--Declaration/Management of Codex and the Codex entries-->
		<script language="javascript" type="text/javascript" src="pixelPets/scripts/Codex/PupCodex.js"></script>
		<script language="javascript" type="text/javascript" src="pixelPets/scripts/Codex/HogCodex.js"></script>
		<script language="javascript" type="text/javascript" src="pixelPets/scripts/Codex/PillarCodex.js"></script>
		<script language="javascript" type="text/javascript" src="pixelPets/scripts/Codex/SquirmCodex.js"></script>
		<script language="javascript" type="text/javascript" src="pixelPets/scripts/Codex/PeepCodex.js"></script>
		<script language="javascript" type="text/javascript" src="pixelPets/scripts/Codex/SproutCodex.js"></script>
		<script language="javascript" type="text/javascript" src="pixelPets/scripts/Codex/BlubbyCodex.js"></script>
		<script language="javascript" type="text/javascript" src="pixelPets/scripts/Codex/GlobCodex.js"></script>
		<script language="javascript" type="text/javascript" src="pixelPets/scripts/Codex/DribbleCodex.js"></script>
		<script language="javascript" type="text/javascript" src="pixelPets/scripts/Codex.js"></script>
		
		<!--Contains HTML information and specific var/scripts for Navigation Tabs-->
		<script language="javascript" type="text/javascript" src="pixelPets/scripts/navTabs/MyPetsTab.js"></script>
		<script language="javascript" type="text/javascript" src="pixelPets/scripts/navTabs/CodexTab.js"></script>
		<!--Controls theme and associated font/background. Includes whiteout of selected Nav Button-->
		<script language="javascript" type="text/javascript" src="pixelPets/scripts/styleControl.js"></script>
		<!--Various functions for initiating each tab, calls the above Tab scripts-->
		<script language="javascript" type="text/javascript" src="pixelPets/scripts/navControl.js"></script>
		<!--Data Management-->
		<script language="javascript" type="text/javascript" src="pixelPets/scripts/dataManager.js"></script>
		<script language="javascript" type="text/javascript" src="pixelPets/scripts/localDataManager.js"></script>
		<script language="javascript" type="text/javascript" src="pixelPets/scripts/formDataManager.js"></script>
		<script language="javascript" type="text/javascript" src="pixelPets/scripts/accountManager.js"></script>
		<!--Initialize global variables and start application-->
		<script language="javascript" type="text/javascript" src="pixelPets/scripts/main.js"></script>
	</head>
	<body>
		<br/>
		<div id="pixelpetsHeader" style="width:540px; height:24px; margin: 0px auto;margin-bottom:5px; position:relative;left:50px;">
			<div id="pixelPetsTitle" style="float:left;width:420px;font-weight:bold;">Pixel Pets: Pet and Talk to Virtual Pets!!</div>
			<div id="accountArea" style="float:left;width:120px;font-size:10px;text-align:right;">	
				<div id="loginButton" style="margin-top:-5px;" class="navButton" onclick="accountInfoNotify();">SIGN UP/LOG IN</div>
			</div>
			<div id="logoutArea" style="float:left;width:0px;font-size:10px;text-align:right;">
			</div>
		</div>
		<div id="organizer">
			<div id="navigation">
				<br/>
				<div id="myPetsButton" class="navButton" onclick="gotoMyPets();">MY PETS</div>
				<div id="codexButton" class="navButton"  onclick="gotoCodex();">CODEX</div>
			</div>
			<div id="ppMain"><div id="mainBody">
				Please wait!!!<br/><br/>
				Loading...<br/><br/>
				Please wait!!!
			</div></div>
			<div class="clearer"></div>
			<div id="footer">
				Programmed by Jake Trower<br/>
				Images/Digimon made by Bandai<br/><br/>
				<div id="accountInfoJavascriptWarning" style="position:relative;left:30px;"><b>***Javascript must be enabled to play!!!***</b></div>
				<div id="accountInfoLoginWarning" style="position:relative;left:30px;"><b>***Game saves to cookies unless logged in!!!***</b></div>
			</div>
		</div>
		
		<div id="overlay">
		</div>
		<div id="overlay2">
			<div id="notification">
				<a id="closeNote" href='javascript:closeNotification()'>X</a>
				<div id="notificationBody">
					<p>Content you want the user to see goes here.</p>
					<a href="javascript:close()">Close</a>
				</div>
			</div>
		</div>
		<div style="visibility:collapse;">
			<input type="text" id="form_name" name="name" /> <input type="text" id="form_pass" name="pass" />
			<!--Pet 1 Form stuff-->
			<input type="checkbox" id="form_pet1exists" name="pet1exists" /> 
			<input type="checkbox" id="form_pet1canRelease" name="pet1canRelease" />
			<input type="text" id="form_pet1petSpecies" name="pet1petSpecies" /> 
			<input type="number" id="form_pet1speciesIndex" name="pet1speciesIndex" />
			<input type="text" id="form_pet1petForm" name="pet1petForm" /> 
			<input type="checkbox" id="form_pet1wasHappyTeen" name="pet1wasHappyTeen" /> 
			<input type="number" id="form_pet1mood" name="pet1mood" /> 
			<input type="number" id="form_pet1nextEventTime" name="pet1nextEventTime" /> 
			<input type="number" id="form_pet1expTimer" name="pet1expTimer" />
			<input type="number" id="form_pet1aniY" name="pet1aniY" /> 
			<input type="text" id="form_pet1name" name="pet1name" />
			<input type="checkbox" id="form_pet1formChange" name="pet1formChange" /> 
			<!--Pet 2 Form stuff-->
			<input type="checkbox" id="form_pet2exists" name="pet2exists" /> 
			<input type="checkbox" id="form_pet2canRelease" name="pet2canRelease" />
			<input type="text" id="form_pet2petSpecies" name="pet2petSpecies" /> 
			<input type="number" id="form_pet2speciesIndex" name="pet2speciesIndex" />
			<input type="text" id="form_pet2petForm" name="pet2petForm" /> 
			<input type="checkbox" id="form_pet2wasHappyTeen" name="pet2wasHappyTeen" /> 
			<input type="number" id="form_pet2mood" name="pet2mood" /> 
			<input type="number" id="form_pet2nextEventTime" name="pet2nextEventTime" /> 
			<input type="number" id="form_pet2expTimer" name="pet2expTimer" />
			<input type="number" id="form_pet2aniY" name="pet2aniY" /> 
			<input type="text" id="form_pet2name" name="pet2name" />
			<input type="checkbox" id="form_pet2formChange" name="pet2formChange" /> 
			<!--Pet 3 Form stuff-->
			<input type="checkbox" id="form_pet3exists" name="pet3exists" /> 
			<input type="checkbox" id="form_pet3canRelease" name="pet3canRelease" />
			<input type="text" id="form_pet3petSpecies" name="pet3petSpecies" /> 
			<input type="text" id="form_pet3speciesIndex" name="pet3speciesIndex" />
			<input type="text" id="form_pet3petForm" name="pet3petForm" /> 
			<input type="checkbox" id="form_pet3wasHappyTeen" name="pet3wasHappyTeen" /> 
			<input type="number" id="form_pet3mood" name="pet3mood" /> 
			<input type="number" id="form_pet3nextEventTime" name="pet3nextEventTime" /> 
			<input type="number" id="form_pet3expTimer" name="pet3expTimer" />
			<input type="number" id="form_pet3aniY" name="pet3aniY" /> 
			<input type="text" id="form_pet3name" name="pet3name" />
			<input type="checkbox" id="form_pet3formChange" name="pet3formChange" /> 
			<!--Pet 4 Form stuff-->
			<input type="checkbox" id="form_pet4exists" name="pet4exists" /> 
			<input type="checkbox" id="form_pet4canRelease" name="pet4canRelease" />
			<input type="text" id="form_pet4petSpecies" name="pet4petSpecies" /> 
			<input type="number" id="form_pet4speciesIndex" name="pet4speciesIndex" />
			<input type="text" id="form_pet4petForm" name="pet4petForm" /> 
			<input type="checkbox" id="form_pet4wasHappyTeen" name="pet4wasHappyTeen" /> 
			<input type="number" id="form_pet4mood" name="pet4mood" /> 
			<input type="number" id="form_pet4nextEventTime" name="pet4nextEventTime" /> 
			<input type="number" id="form_pet4expTimer" name="pet4expTimer" />
			<input type="number" id="form_pet4aniY" name="pet4aniY" /> 
			<input type="text" id="form_pet4name" name="pet4name" />
			<input type="checkbox" id="form_pet4formChange" name="pet4formChange" /> 
		</div>
		
		<div id="backToCakeandturtles">
			<a href="index.php">&lt;&lt; Back to cakeandturtles</a>
		</div>
		<div id="themeSelector">
			<div style="float:left;">Theme: </div>
			<div id="purpleTheme" class="themeBox" style="background:#ceb6c3;" onclick="purpleTheme()"></div>
			<div id="orangeTheme" class="themeBox" style="background:#fae161;" onclick="orangeTheme()"></div>
			<div id="blueTheme" class="themeBox" style="background:#92b6db;" onclick="blueTheme()"></div>
			<div id="redTheme" class="themeBox" style="background:#faaa70;" onclick="redTheme()"></div>
			<div id="greenTheme" class="themeBox" style="background:#a4e676" onclick="greenTheme()"></div>
			<div id="greyTheme" class="themeBox" style="background:#31493c;" onclick="greyTheme()"></div>
		</div>
	</body>
</html>