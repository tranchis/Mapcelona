<?php
session_name("mapcelona");
session_start();
include('define.php');

// If the users has chosen a different language, we set it accordingly in his session
if(isset($_GET['lang']) && in_array($_GET['lang'],$langs)) $_SESSION['lang'] = $_GET['lang'];
// The webpage will use the language chosen by the user according to his decission or his browser's preferences.
if(!isset($_SESSION['lang'])) {
    $_SESSION['lang'] = substr($_SERVER['HTTP_ACCEPT_LANGUAGE'], 0, 2);
    if(!in_array($_SESSION['lang'],$langs)) $_SESSION['lang'] = 'en';
}
include LANG_PATH.$_SESSION['lang'].'.php';
include_once('dataClassManager.php');
$dcm = new dataClassManager();
?>
<!DOCTYPE html "-//W3C//DTD XHTML 1.0 Strict//EN" 
  "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xmlns:v="urn:schemas-microsoft-com:vml">
    <head>
        <meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
        <meta name="keywords" content="map barcelona socioeconomic indicators">
        <meta name="description" content="Mapcelona is a mashup between Google Maps and several socioeconomic indicators obtained from official documents of the Local council and other public administrations.">
	    <link rel="shortcut icon" href="<?=IMAGES_PATH?>favicon.ico" type="image/x-icon">
        <title>mapcelona</title>
        <script src="http://maps.google.com/maps?file=api&amp;v=2&amp;sensor=false&amp;key=ABQIAAAAQaqpGkTfKPJnihNVtZAkqxRdbGFxy3qZiDLh0IXFGr-jkCTdRRQqAhIpOR57-sKaogrRI5pPn5WgZQ" type="text/javascript"></script>
        <script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.4/jquery.min.js"></script>
        <script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jqueryui/1.8/jquery-ui.min.js"></script>
        <link rel="stylesheet" type="text/css" href="<?=CSS_PATH?>style.css" />
    </head>
    <body onunload="GUnload()" onresize="resizeApp()">
        <? include('header.php'); ?>
        <div id="panel">
        	<div id="panel_header" class="panel_header">
	            <a class="panel_trigger" onclick="expandPanel();"><?php print($Definition["TuneParams"]); ?> &rsaquo;</a>
			</div>
			<div id="factors_target" class="rounded">
            	<ul id="selected_factors"></ul><!--selected_factors-->
            	<p id="drag_help" class="visible"><?php print($Definition['DragFactors']); ?></p>
            </div><!--factors_target-->
            <div id="panel_footer" class="panel_footer">
				<a id="trigger_remove_params" class="panel_trigger" onclick="removeFactors();"><?php print($Definition['ClearParams']); ?></a>
				<a id="trigger_update_map" class="panel_trigger rounded" onclick="updateMap();"><?php print($Definition['RefreshMap']); ?></a>
			</div>
        </div><!--panel-->

        <div id="panel_expanded" style="display:none;">
        	<div id="panel_expanded_header" class="panel_header">
        		<a class="panel_trigger" onclick="collapsePanel();">&lsaquo; <?php print($Definition["Close"]); ?></a>
        	</div>
        	<div id="factors_expanded">
	            <ul id="factors_expanded_list">
	            <?php
                        function print_group($group){
                            foreach($group as $key=>$value){
                                if (!is_array($value)) print("<li class='factor' id='{$value}'>{$key}</li>");
                                else{
                                    print("<li class='expandable'><h3><a href='#' class='expandable_trigger'>{$key}</a></h3><ul>");
                                    print_group($value);
                                    print('</ul></li>');
                                }
                            }
                        }
                        print_group($dcm->getDataclasses($_SESSION['lang']));
	                
                            //print("<li onclick=\"addParam('{$parameter['id']}', '{$parameter['_value']}');\" id='{$parameter['id']}' class='factor'>{$parameter['_value']}</li>");
                        
	            ?>
	            </ul>
            </div><!--factors_expanded-->
            <div id="panel_expanded_footer" class="panel_footer">
        		<a class="panel_trigger" onclick="collapsePanel();">&lsaquo; <?php print($Definition["Close"]); ?></a>
        	</div>
        </div><!--panel_expanded-->
        <div id="map">&nbsp;</div>
        <!-- <div id="spacer"></div> -->
        <script type="text/javascript" src="<?=JS_PATH?>mapcelona.js"></script>
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