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
        <title>mapcelona</title>
        <script src="http://maps.google.com/maps?file=api&amp;v=2&amp;sensor=false&amp;key=ABQIAAAAQaqpGkTfKPJnihNVtZAkqxRdbGFxy3qZiDLh0IXFGr-jkCTdRRQqAhIpOR57-sKaogrRI5pPn5WgZQ" type="text/javascript"></script>
        <script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.4/jquery.min.js"></script>
        <script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jqueryui/1.8/jquery-ui.min.js"></script>
        <link rel="stylesheet" type="text/css" href="./css/newstyle.css" />
    </head>
    <body>
        <? include('header2.php'); ?>
        <div id="about">
            <? print($Definition["AboutText"])?>
        </div>
    </body>
</html>