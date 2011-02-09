<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html;charset=utf-8"/>
		<title>Co-voiturage</title>
	</head>
	<body>
		<header>
			<h1>Liste des trajets</h1>
		</header>
		<section>
			<table>
				<tr>
					<th>#ID</th>
					<th>Lieu de départ</th>
					<th>Lieu d'arrivée</th>
					<th>Date de départ</th>
				</tr>
<?php
	$con = mysql_connect('localhost','root','root');
	if (!$con)
		die('Could not connect: '.mysql_error());
	mysql_select_db('ot', $con);

	$result = mysql_query('SELECT * FROM trajets');
	while($row = mysql_fetch_array($result)){
		echo "<tr>\n<td>".$row['id']."</td>\n<td>".$row['lieu_depart']."</td>\n<td>".$row['lieu_arrivee']."</td>\n<td>".$row['date_depart']."</td>\n</tr>";
  }
  
	mysql_close($con);
?>
			</table>
		</section>
		<br/>
		<hr/>
		<br/>
		<nav>
			<a href="index.php">Retour à l'accueil</a>
		</nav>
	</body>
</html>