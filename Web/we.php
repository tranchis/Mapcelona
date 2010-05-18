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
<!DOCTYPE html "-//W3C//DTD XHTML 1.0 Strict//EN" 
  "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
        <link rel="shortcut icon" href="<?=IMAGES_PATH?>favicon.ico" type="image/x-icon">
        <title>mapcelona - <? print($Definition["We"]); ?></title>
        <link rel="stylesheet" type="text/css" href="<?=CSS_PATH?>style.css" />
        <script src="http://platform.twitter.com/anywhere.js?id=nVbpytqOSFNnlV8Hxdybyw&amp;v=1"></script>
    </head>
    <body>
        <? include('header.php'); ?>
        <div id="we" class="gradient">
            <img src="<?=IMAGES_PATH?>nosaltres.jpg" alt="<? print($Definition["We"]); ?>" /></br></br>
            Luis (@luisjoliva), Miquel (@msonsona), Sergio (@sergioalvarez), Ignasi (@ignasigomez).
        </div>
        <script type="text/javascript">
		  twttr.anywhere(function (T) {
		    T.hovercards();
		  });
		</script>
    </body>
</html>