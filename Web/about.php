<?php

include('define.php');

// If the users has chosen a different language, we set it accordingly in his session
if(isset($_GET['lang']) && in_array($_GET['lang'],$langs)) $_SESSION['lang'] = $_GET['lang'];
// The webpage will use the language chosen by the user according to his decission or his browser's preferences.
if(!isset($_SESSION['lang'])) {
    $_SESSION['lang'] = substr($_SERVER['HTTP_ACCEPT_LANGUAGE'], 0, 2);
    if(!in_array($_SESSION['lang'],$langs)) $_SESSION['lang'] = 'en';
}
include LANG_PATH.$_SESSION['lang'].'.php';
?>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
        <link rel="shortcut icon" href="<?=IMAGES_PATH?>favicon.ico" type="image/x-icon">
        <title>mapcelona - <? print($Definition["About"]); ?></title>
        <link rel="stylesheet" type="text/css" href="<?=CSS_PATH?>style.css" />
    </head>
    <body>
        <? include('header2.php'); ?>
        <div id="about" class="gradient">
            <? print($Definition["AboutText"]); ?>
        </div>
    </body>
</html>