<?php
session_name("mapcelona");
session_start();
include('define.php');

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
<head>
<meta http-equiv="Content-Type" content="text/html;charset="utf-8" />
<title>mapcelona</title>
<script src="http://maps.google.com/maps?file=api&amp;v=2&amp;sensor=false&amp;key=ABQIAAAAQaqpGkTfKPJnihNVtZAkqxRdbGFxy3qZiDLh0IXFGr-jkCTdRRQqAhIpOR57-sKaogrRI5pPn5WgZQ" type="text/javascript"></script>
<link rel="stylesheet" type="text/css" href="style.css" />
</head>
<body>';
include 'header.php';
echo '
    <div id="panel">
        <div id="panel_collapsed" class="visible">
            <a id="panel_trigger" href="#" onclick="expandPanel();">'.$Definition["TuneParams"].' &rsaquo;</a>
        </div><!--panel_collapsed-->
        <div id="panel_expanded" class="invisible">
            <a id="panel_trigger" href="#" onclick="collapsePanel();">&lsaquo; '.$Definition["Close"].'</a>
        </div><!--panel_expanded-->
    </div><!--/panel--> 
    <div id="map">&nbsp;</div>
    <script type="text/javascript">
        var map =new GMap2(document.getElementById("map"));
        map.addControl(new GLargeMapControl3D());
        map.setCenter(new GLatLng(41.387917,2.169919), 12);
        var kml = new GGeoXml("http://www.mapcelona.org/doc.kml");
        showPolygons();

        function showPolygons() {
            kml = new GGeoXml("http://www.mapcelona.org/doc.kml");
            map.addOverlay(kml);
        }
        function hidePolygons() {
            map.removeOverlay(kml);
        }
        
        function expandPanel() {
            document.getElementById("panel").setAttribute("class", "panel_expanded");
            document.getElementById("panel_collapsed").setAttribute("class", "invisible");
            document.getElementById("panel_expanded").setAttribute("class", "visible");
        }
        function collapsePanel() {
            document.getElementById("panel").setAttribute("class", "panel_collapsed");
            document.getElementById("panel_collapsed").setAttribute("class", "visible");
            document.getElementById("panel_expanded").setAttribute("class", "invisible");
        }
    </script>
</body>
</html>';
?>
