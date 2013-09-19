<!DOCTYPE html>
<html>
	<head>
		<title>Pixel Pets!</title>
		<link href="pixelPets/style.php" rel="stylesheet" type="text/css">
		<!--Declaration of PixelPet class-->
		<script language="javascript" type="text/javascript" src="pixelPets/scripts/PixelPet.js"></script>
		<!--Declaration of various pet Objects-->
		<script language="javascript" type="text/javascript" src="pixelPets/scripts/Puglett.js"></script>
		<script language="javascript" type="text/javascript" src="pixelPets/scripts/Chloropillar.js"></script>
		<script language="javascript" type="text/javascript" src="pixelPets/scripts/Flutterpod.js"></script>
		<script language="javascript" type="text/javascript" src="pixelPets/scripts/Lunactulus.js"></script>
		<script language="javascript" type="text/javascript" src="pixelPets/scripts/Tadpox.js"></script>
		<script language="javascript" type="text/javascript" src="pixelPets/scripts/Fledgwing.js"></script>
		
		<!--Contains HTML information and specific var/scripts for My Pets Tab-->
		<script language="javascript" type="text/javascript" src="pixelPets/scripts/MyPetsTab.js"></script>
		<!--Contains HTML information and specific var/scripts for Trainer Tab-->
		<script language="javascript" type="text/javascript" src="pixelPets/scripts/TrainerTab.js"></script>
		<!--Controls theme and associated font/background. Includes whiteout of selected Nav Button-->
		<script language="javascript" type="text/javascript" src="pixelPets/scripts/styleControl.js"></script>
		<!--Various functions for initiating each tab, calls the above Tab scripts-->
		<script language="javascript" type="text/javascript" src="pixelPets/scripts/navControl.js"></script>
		<!--Initialize global variables and start application-->
		<script language="javascript" type="text/javascript" src="pixelPets/scripts/main.js"></script>
	</head>
	<body>
		<br/><br/>
		<div id="organizer">
			<div id="navigation">
				<br/>
				<div id="myPetsButton" class="navButton" onclick="gotoMyPets();">MY PETS</div>
				<div id="trainerButton" class="navButton" onclick="gotoTrainer();">TRAINER</div>
				<div id="inventoryButton" class="navButton" onclick="gotoInventory();">INVENTORY</div>
				<div id="gardenButton" class="navButton" onclick="gotoGarden();">GARDEN</div>
				<div id="codexButton" class="navButton"  onclick="gotoCodex();">CODEX</div>
			</div>
			<div id="ppMain"><div id="mainBody">				
			</div></div>
			<div class="clearer"></div>
			<div id="footer">
				Made by Jake Trower
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