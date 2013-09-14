<!DOCTYPE html>
<html>
	<head>
		<title>Pixel Pets!</title>
		<link href="style.php" rel="stylesheet" type="text/css">
		<script language="javascript" type="text/javascript" src="styleControl.js"></script>
	</head>
	<body>
		<br/><br/>
		<div id="organizer">
			<div id="navigation">
				<div id="myPetsButton" class="navButton" onclick="gotoMyPets();">MY PETS</div>
				<div id="trainerButton" class="navButton" onclick="gotoTrainer();">TRAINER</div>
				<div id="inventoryButton" class="navButton" onclick="gotoInventory();">INVENTORY</div>
				<div id="gardenButton" class="navButton" onclick="gotoGarden();">GARDEN</div>
				<div id="codexButton" class="navButton"  onclick="gotoCodex();">CODEX</div>
			</div>
			<div id="main">
			</div>
			<div class="clearer"></div>
			<div id="footer">
				Made by Jake Trower
			</div>
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