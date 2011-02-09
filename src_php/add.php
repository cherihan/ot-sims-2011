<?php

	if(isset($_POST["lieu_depart"]) && isset($_POST["lieu_arrivee"]) && isset($_POST["date_depart"])) {
		if($_POST["lieu_depart"]!='' && $_POST["lieu_arrivee"]!='' && $_POST["date_depart"]!='') {
		
			$con = mysql_connect('localhost','root','root');
			if (!$con)
				die('Could not connect: '.mysql_error());
			mysql_select_db('ot', $con);

			$req = "INSERT INTO trajets (lieu_depart, lieu_arrivee, date_depart) VALUES ('$_POST[lieu_depart]','$_POST[lieu_arrivee]','$_POST[date_depart]')";

			if (!mysql_query($req,$con))
				die('Error: ' . mysql_error());

			mysql_close($con);
			header('Location: list.php');
		}
	}
	
?><!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html;charset=utf-8"/>
		<title>Co-voiturage</title>
	</head>
	<body>
		<header>
			<h1>Ajouter un trajet</h1>
		</header>
		<section>
			<form method="post">
				<h2>Où ?</h2>
				<input type="text" placeholder="Lieu de départ" name="lieu_depart" /><br/>
				<input type="text" placeholder="Lieu d'arrivée" name="lieu_arrivee" /><br/>
				<h2>Quand ?</h2>
				<input type="text" value="<?php echo date('Y-m-d G:i'); ?>" name="date_depart" /><br/>
				<br/>
				<input type="submit" value="Ajouter"/>
			</form>
		</section>
		<br/>
		<hr/>
		<br/>
		<nav>
			<a href="index.php">Retour à l'accueil</a>
		</nav>
	</body>
</html>