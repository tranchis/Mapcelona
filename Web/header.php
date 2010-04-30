<div id="header">
    <a id="logo" href="/" alt="mapcelona"></a>
    <div id="navigation">
        <a href="?lang=ca"><img src="<?=IMAGES_PATH?>comp/catalan.png" alt="català" class="flag" /></a>
        <a href="?lang=es"><img src="<?=IMAGES_PATH?>comp/castellano.png" alt="español" class="flag" /></a>
        <a href="?lang=en"><img src="<?=IMAGES_PATH?>comp/english.png" alt="english" class="flag" /></a>
        <a href="./about.php"><img src="<?=IMAGES_PATH?>comp/help.png" alt="<? print($Definition["About"]); ?>" /></a>
        <a href="./we.php"><img src="<?=IMAGES_PATH?>comp/we.png" alt="<? print($Definition["We"]); ?>" /></a>
    </div><!--/navigation-->
    <div id="spacer"></div>
    <div id="actionlist">
    	<a id="examples_trigger"><img src="<?=IMAGES_PATH?>comp/examples_<?php  print($_SESSION['lang']); ?>.png" alt="<? print($Definition["Examples"]); ?>" /></a>
    	<a></a><img src="<?=IMAGES_PATH?>comp/download_<?php  print($_SESSION['lang']); ?>.png" alt="<? print($Definition["GetData"]); ?>" /></a>
    	<a></a><img src="<?=IMAGES_PATH?>comp/share_<?php  print($_SESSION['lang']); ?>.png" alt="<? print($Definition["Share"]); ?>" /></a>
        <!--<a href="http://www.addtoany.com/share_save?linkname=mapcelona&amp;linkurl=www.mapcelona.org" class="a2a_dd">'
            .$Definition["Share"].
        '</a>
        <script type="text/javascript">a2a_linkname="mapcelona";a2a_linkurl="www.mapcelona.org";</script>
        <script type="text/javascript" src="http://static.addtoany.com/menu/page.js"></script>-->
    </div><!--/actionlist-->
    <div id="spacer"></div>
</div><!--/header-->