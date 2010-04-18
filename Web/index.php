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
<title>mapcelona</title>
<script src="http://maps.google.com/maps?file=api&amp;v=2&amp;sensor=false&amp;key=ABQIAAAAQaqpGkTfKPJnihNVtZAkqxRdbGFxy3qZiDLh0IXFGr-jkCTdRRQqAhIpOR57-sKaogrRI5pPn5WgZQ" type="text/javascript"></script>
<script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.4.2/jquery.min.js"></script>
<script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jqueryui/1.7.2/jquery-ui.min.js"></script>
<script type="text/javascript" src="<?=JS_PATH?>sliders.js"></script>
<link rel="stylesheet" type="text/css" href="<?=CSS_PATH?>style.css" />
</head>
<body>
<?php include_once 'header.php'; ?>
<div id="panel">
<?php
# IDEA:
# Al expandir, se listan los parámetros, al cerrar, se avisa con AJAX al servidor para que almacene la configuración del usuario en la sesión
# el javascript deberá listar en el panel colapsado los elegidos con sus sliders (cuando vuelva a cargar, se meterá la configuración desde la sesión)
# En el futuro, si hay parents y childs de dataclass, al expandir se pueden o bien mostrar los parents y al clicark en algun botón (tipo +) se enseñan los hijos, y que
# su slider sea para pesar el agregado de todos sus hijos (ej: crimen y todas sus variedades).
?>
    <div id="panel_collapsed" class="visible">
        <a id="panel_trigger" onclick="expandPanel();"><?php print($Definition["TuneParams"]); ?> &rsaquo;</a>
    </div><!--panel_collapsed-->
    
    <div id="panel_expanded" class="invisible">
        <a id="panel_trigger" onclick="collapsePanel();">&lsaquo; <?php print($Definition["Close"]); ?></a>
        <ul id="factors_expanded">
        <?php
            $parameters = $dcm->getParameters();
            foreach ($parameters as $parameter) {
                print('<li id="factor_'.$parameter['id'].'" class="factor">
                            <input type="checkbox" onclick="checkParameter(this)" />'.
                            $parameter['name'].
                            //'<div class="slider"><div class="slider-handle"></div></div><span>0</span> %'.
                        '</li>');
            }
        ?>
        </ul><!--factors_expanded-->
        
    </div><!--panel_expanded-->
</div><!--/panel-->
<div id="map">&nbsp;</div>
<script type="text/javascript" src="<?=JS_PATH?>mapcelona.js"></script>
</body>
</html>