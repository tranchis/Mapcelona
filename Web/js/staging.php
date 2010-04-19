<?php
session_name("mapcelona");
session_start();
include('define.php');
include('dataClassManager.php');

$dcm = new dataClassManager();
$dcm->getIndicator(array(224=>1));

?>
