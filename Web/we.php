<?php
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
?>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
        <link rel="shortcut icon" href="<?=IMAGES_PATH?>favicon.ico" type="image/x-icon">
        <title>mapcelona - <? print($Definition["We"]); ?></title>
        <link rel="stylesheet" type="text/css" href="./css/newstyle.css" />
    </head>
    <body>
        <? include('header2.php'); ?>
        <div id="we" class="gradient">
            <img src="<?=IMAGES_PATH?>nosaltres.jpg"></br></br>
            Luis (<a href="http://twitter.com/luisjoliva">@luisjoliva</a>), Miquel (<a href="http://twitter.com/msonsona">@msonsona</a>), Sergio (<a href="http://twitter.com/sergioalvarez">@sergioalvarez</a>), Ignasi.
        </div>
    </body>
</html>