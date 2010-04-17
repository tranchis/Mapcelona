<?php
$con = mysql_connect("localhost","sergioal_mapcelo","123opendata");
if (!$con)
  {
  die('Could not connect: ' . mysql_error());
  }

mysql_select_db("sergioal_mapcelona", $con);

mysql_close($con);
?> 