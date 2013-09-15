<!DOCTYPE html>
<html>
	<head>
		<title>Pixel Pets!</title>
		<link href="style.php" rel="stylesheet" type="text/css">
		<script language="javascript" type="text/javascript" src="styleControl.js"></script>
		<script language="javascript" type="text/javascript" src="PixelPet.js"></script>
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
			<div id="main"><div id="mainBody">
				<div class="navButton" style="background:#ffffff; float:left;padding-left:20px;padding-right:20px;">PET 1</div>
				<div class="navButton" style="float:left;padding-left:20px;padding-right:20px;">PET 2</div>
				<div class="navButton" style="float:left;padding-left:20px;padding-right:20px;">PET 3</div>
				<div class="navButton" style="float:left;padding-left:20px;padding-right:20px;">PET 4</div>
				<div class="clearer"></div><br/>
				<div style="float:left;">??? the Mysterious Egg</div>
				<div style="float:right;">Lvl. ???</div>
				<div class="clearer"></div>
				<br/>
				<div style="width:128px; height:128px; background:#ffffff; border-style:solid; border-width:1px; border-color:#000000; margin:0px auto;">
					<div id="petImage" style="width:128px; height:128px; background:url(eggs_and_pets_big.png);"></div>
				</div>
				<br/>
				<div id="petDescription">It moves around a lot.<br/>It must be close to hatching!</div>
				<br/>
				<div style="display: inline-block;">
					<div class="actionButton">Rub Egg</div>
					<div class="actionButton">Shake Egg</div>
					<div class="actionButton">Talk to Egg</div>
				</div>
				
				<script type="text/javascript">					
					pet = new PixelPet();
					pet.UpdateAnimation();
					var game = setInterval(function(){main()},750);
	
					var main = function(){
						pet.UpdateAnimation();
					};
				</script>
				
			</div></div>
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