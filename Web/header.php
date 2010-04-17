<?php

echo'
<div id="header">
    <a id="logo" href="/">mapcelona</a>
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
        <a href="?lang=ca" onclick=""><img src="/files/images/cat_w.png" alt="català" class="flag" /></a>
        <a href="?lang=es" onclick=""><img src="/files/images/esp_w.png" alt="español" class="flag" /></a>
        <a href="?lang=en" onclick=""><img src="/files/images/eng_w.png" alt="english" class="flag" /></a>
    </div><!--/navigation-->
    <div id="actionlist">
        <a href="#">'.$Definition["GetData"].'</a>
        |
        <a href="#">'.$Definition["Share"].'</a>
    </div><!--/actionlist-->
    <div id="spacer"></div>
</div><!--/header-->';
?>