<?php
	$username = "username";
	$password = "passoword";
	
	$link = mysql_connect('catgamescores.db', $username, $password);
	if (!$link){
		die('Failed to connect to server: '.mysql_error());
	}
	
	//select database
	$db = mysql_select_db('pixelpets', $link);
	
	//function to sanitize values recieved from the form. Prevents SQL injection
	function clean($str)
	{
		$str = @trim($str);
		if (get_magic_quotes_gpc()){
			$str = stripslashes($str);
		}
		return mysql_real_escape_string($str); //error
	}
	
	//Sanitize the POST values
	$name = clean($_POST['name']);
	$pass = clean($_POST['pass']);

	////////////////////////////////////////////////////////////////////////////////////////////////
	//CREATE INSERT STATEMENT
	$qry = "INSERT INTO ppusers (username, password) VALUES ('".$name."', '".$pass."')";
	$result = @mysql_query($qry, $link) or die("Couldn't execute query.");
	
	//GET USER ID BACK FROM DB
	$qry = "SELECT userId FROM ppusers WHERE userName = '".$name."' AND password = '".$pass."'";
	$result = mysql_query($qry, $link) or die("Couldn't execute query.");
	$userId = -1;
	while ($row = mysql_fetch_array($result)){
		$userId = $row["userId"];
	}
	
	//INSERT DEFAULT PETS INTO DB
	$qry = "INSERT iNTO ppuserPets (userId, pet1Id, pet2Id, pet3Id, pet4Id) VALUES (".$userId.",'".$name.",pet1','".$name.",pet2','".$name.",pet3','".$name.",pet4')";
	$result = @mysql_query($qry, $link) or die("Couldn't execute query.");
	
	echo "writing=Ok";
	exit();
	mysql_close();
?>