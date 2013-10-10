<!DOCTYPE html>
<html>
	<head>
		<title>Pixel Pets!</title>
		<link href="pixelPets/pixelPetStyle.css" rel="stylesheet" type="text/css">
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
		<!--Initialize global variables and start application-->
		<script language="javascript" type="text/javascript" src="pixelPets/scripts/main.js"></script>
	</head>
	<body>
		<br/>
		<div style="width:440px; margin: 0px auto;margin-bottom:5px;font-weight:bold;">Pixel Pets: Pet and Talk to Virtual Pets!!</div>
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
				Images/Digimon made by Bandai
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