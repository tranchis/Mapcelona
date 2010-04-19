<?php

echo'
<div id="header">
    <a id="logo" href="/" alt="mapcelona"></a>
    <div id="navigation">
        <a href="/">'.$Definition["Home"].'</a>
        |
        <a href="#">'.$Definition["Examples"].'</a>
        <select>
          <option value="youngster">Youngster</option>
          <option value="oldie">Oldie</option>
          <option value="couplewithchilds">Couple with childs</option>
        </select>
        |
        <a href="about.php">'.$Definition["About"].'</a>
        |
        <a href="#" onclick="">'.$Definition["Help"].'</a>
        |
        <a href="?lang=ca" onclick=""><img src="'.IMAGES_PATH.'cat_w.png" alt="català" class="flag" /></a>
        <a href="?lang=es" onclick=""><img src="'.IMAGES_PATH.'esp_w.png" alt="español" class="flag" /></a>
        <a href="?lang=en" onclick=""><img src="'.IMAGES_PATH.'eng_w.png" alt="english" class="flag" /></a>
    </div><!--/navigation-->
    <div id="spacer"></div>
    <div id="actionlist">
        <a href="#">'.$Definition["GetData"].'</a>
        |
        <!--<a href="http://www.addtoany.com/share_save?linkname=mapcelona&amp;linkurl=www.mapcelona.org" class="a2a_dd">'
            .$Definition["Share"].
        '</a>
        <script type="text/javascript">a2a_linkname="mapcelona";a2a_linkurl="www.mapcelona.org";</script>
        <script type="text/javascript" src="http://static.addtoany.com/menu/page.js"></script>-->
    </div><!--/actionlist-->
    <div id="spacer"></div>
</div><!--/header-->';
?>