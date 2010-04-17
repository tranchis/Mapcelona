<?php
include 'lang/esp.php';
echo '
<html>
<head>
<meta name="viewport" content="initial-scale=1.0, user-scalable=no" />
<title>mapcelona</title>
<script type="text/javascript" src="http://maps.google.com/maps/api/js?sensor=false"></script>
<script type="text/javascript">
  function initialize() {
      var latlng = new google.maps.LatLng(41.387917,2.169919);
      var options = {
        zoom: 12,
      center: latlng,
      mapTypeId: google.maps.MapTypeId.ROADMAP
    };
    var map = new google.maps.Map(document.getElementById("map"), options);
  }
</script>
<link rel="stylesheet" type="text/css" href="style.css" />
</head>
<body onload="initialize()">';
    include 'header.php';
echo '
    <div id="panel">
    </div><!--/panel--> 
    <div id="map"></div>
</body>
</html>';
?>