<?php
session_name("mapcelona");
session_start();
$langs = array("ca","es","en");
if(isset($_GET['lang']) && in_array($_GET['lang'],$langs)) {
    $_SESSION['lang'] = $_GET['lang'];
}
if(isset($_SESSION['lang'])) {
    $lang = $_SESSION['lang'];
} else {
    $lang = substr($_SERVER['HTTP_ACCEPT_LANGUAGE'], 0, 2);
    if(!in_array($lang,$langs)) {
        $lang = 'en';
    }
}
include './lang/'.$lang.'.php';
    
echo '
<html>
<head>';
/*<meta name="viewport" content="initial-scale=1.0, user-scalable=no" />
<title>mapcelona</title>
<script type="text/javascript" src="http://maps.google.com/maps/api/js?sensor=false"></script>
<script type="text/javascript" src="js/geoxml3.js"></script>
<script type="text/javascript" src="js/ProjectedOverlay.js"></script>
<script type="text/javascript">
  function initialize() {
      var latlng = new google.maps.LatLng(41.387917,2.169919);
      var options = {
        zoom: 12,
      center: latlng,
      mapTypeId: google.maps.MapTypeId.ROADMAP
    };
    var myMap = new google.maps.Map(document.getElementById("map"), options);
    var myParser = new geoXML3.parser({map: myMap});
    myParser.parse("http://www.mapcelona.org/doc.kml");
  }
</script>*/
echo'
<script src="http://maps.google.com/maps?file=api&amp;v=2&amp;sensor=false&amp;key=ABQIAAAAQaqpGkTfKPJnihNVtZAkqxRdbGFxy3qZiDLh0IXFGr-jkCTdRRQqAhIpOR57-sKaogrRI5pPn5WgZQ" type="text/javascript"></script>
<link rel="stylesheet" type="text/css" href="style.css" />
</head>
<body onload="initialize()">';
    include 'header.php';
echo '
    <div id="panel">
    </div><!--/panel--> 
    <div id="map"></div>
    <script type="text/javascript">
        var map =new GMap2(document.getElementById("map"));
        map.addControl(new GLargeMapControl3D());
        map.setCenter(new GLatLng(41.387917,2.169919), 12);
        var kml = new GGeoXml("http://www.mapcelona.org/doc.kml");
        showPolygons();
        
        var timer = setInterval(hidePolygons,3000);

        function hidePolygons() {
            map.removeOverlay(kml);
        }
        function showPolygons() {
            map.addOverlay(kml);
        }
    </script>
</body>
</html>';
?>
