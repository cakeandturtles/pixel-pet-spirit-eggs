<?php
	$username = "a5048445";
	$password = "jak31992123";
	
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
	//CREATE SELECT STATEMENT
	$qry = "SELECT userId FROM ppusers WHERE userName = '".$name."' AND password = '".$pass."'";
	$result = mysql_query($qry, $link) or die("Couldn't execute query.");
	$num = mysql_numrows($result);
	
	if ($num > 0){ //THE LOGIN WAS GOOD
		echo "login=Ok";
	}else{ //THE LOGIN WAS BAD
		echo "login=Bad";
	}
	
	exit();
	mysql_close();
?>