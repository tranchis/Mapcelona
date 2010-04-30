<?php
session_name("mapcelona");
session_start();
include('define.php');

// If the users has chosen a different language, we set it accordingly in his session
if(isset($_GET['lang']) && in_array($_GET['lang'],$langs)) $_SESSION['lang'] = $_GET['lang'];
// The webpage will use the language chosen by the user according to his decission or his browser's preferences.
if(isset($_SESSION['lang'])) $lang = $_SESSION['lang'];
else {
    $lang = substr($_SERVER['HTTP_ACCEPT_LANGUAGE'], 0, 2);
    if(!in_array($lang,$langs)) $lang = 'en';
}
include LANG_PATH.$lang.'.php';
include_once('dataClassManager.php');
$dcm = new dataClassManager();
?>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
        <meta name="keywords" content="map barcelona socioeconomic indicators">
        <meta name="description" content="Mapcelona is a mashup between Google Maps and several socioeconomic indicators obtained from official documents of the Local council and other public administrations.">
	<link rel="shortcut icon" href="<?=CSS_PATH ?>/images/favicon.ico" type="image/x-icon">
        <title>mapcelona</title>
        <script src="http://maps.google.com/maps?file=api&amp;v=2&amp;sensor=false&amp;key=ABQIAAAAQaqpGkTfKPJnihNVtZAkqxRdbGFxy3qZiDLh0IXFGr-jkCTdRRQqAhIpOR57-sKaogrRI5pPn5WgZQ" type="text/javascript"></script>
        <script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.4/jquery.min.js"></script>
        <script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jqueryui/1.8/jquery-ui.min.js"></script>
        <link rel="stylesheet" type="text/css" href="<?=CSS_PATH?>newstyle.css" />
		<link rel="stylesheet" type="text/css" media="screen, projection" href="<?=CSS_PATH?>slider.css" />
    </head>
    <body>
        <? include('header2.php'); include('floater.php');
           // if (!isset($_SESSION['floater'])) { include('floater.php'); $_SESSION['floater']=false; }
           //            else $_SESSION['floater'] ? include('floater.php'): false;
        ?>
        <!--<div id="panel" class="panel_collapsed">//-->
        <div id="panel_collapsed" class="visible">
            <a id="panel_trigger" onclick="expandPanel();"><?php print($Definition["TuneParams"]); ?> &rsaquo;</a>
			<br/>
			<a id="panel_trigger" onclick="updateMap();">Refresh map!</a><a id="panel_trigger" onclick="clearParams();">Clear parameters</a>
			<br/><br/>
            <ul id="factors_expanded">
			</ul><!--factors_expanded-->
        </div><!--panel_collapsed-->

        <div id="panel_expanded" class="visible">
            <a id="panel_trigger" onclick="collapsePanel();">&lsaquo; <?php print($Definition["Close"]); ?></a>
            <ul id="factors_expanded_inactive">
            <?php 
                foreach ($dcm->getParameters() as $parameter) print("<li onclick=\"addParam('{$parameter['id']}', '{$parameter['_value']}');\" id='{$parameter['id']}' class='factor'>{$parameter['_value']}</li>");
            ?>
            </ul><!--factors_expanded-->
            </div><!--panel_expanded-->
        </div><!--/panel-->
        <div id="map">&nbsp;</div>
        <div id="spacer"></div>
        <script type="text/javascript" src="<?=JS_PATH?>mapcelona.js"></script>
		<script type="text/javascript" src="<?=JS_PATH?>slider.js"></script>
        <script type="text/javascript">
var gaJsHost = (("https:" == document.location.protocol) ? "https://ssl." : "http://www.");
document.write(unescape("%3Cscript src='" + gaJsHost + "google-analytics.com/ga.js' type='text/javascript'%3E%3C/script%3E"));
</script>
<script type="text/javascript">
try {
var pageTracker = _gat._getTracker("UA-16003931-1");
pageTracker._trackPageview();
} catch(err) {}</script>
    </body>
</html>