<?php
session_name("mapcelona");
session_start();
$langs = array("ca","es","en");
if(isset($_GET['lang']) && in_array($_GET['lang'],$langs)) {
    $_SESSION['lang'] = $_GET['lang'];
}
if(isset($_SESSION['lang'])) {
    $lang = $_SESSION['lang'];
} else {
    $lang = substr($_SERVER['HTTP_ACCEPT_LANGUAGE'], 0, 2);
    if(!in_array($lang,$langs)) {
        $lang = 'en';
    }
}
include './lang/'.$lang.'.php';
echo '
<html>
<head>
<meta http-equiv="Content-Type" content="text/html;charset="utf-8" />
<title>mapcelona - '.$Definition["About"].'</title>
<link rel="stylesheet" type="text/css" href="style.css" />
</head>
<body>';
include 'header.php';
echo '
    <div id="panel" style="background:#F0F1F5;">
        <ul>
            <li>
                <a href="http://www.abredatos.es">
                    <img id="logo_abredatos" src="http://www.abredatos.es/wp-content/uploads/2010/04/200x200-B.png" 
                         alt="" title="200x200 B" width="199" height="200" class="" />
                </a>
            </li>
            <li>
                <a href="#what">'.$Definition["What"].'</a>
            </li>
            <li>
                <a href="#who">'.$Definition["Who"].'</a>
            </li>
            <li>                
                <a href="#sources">'.$Definition["Sources"].'</a>
            </li>
            <li>
                <a href="#based">'.$Definition["BasedOn"].'</a>
            </li>
        </ul>
    </div><!--/panel-->
    <div id="about">
        <p><a name="what">Pellentesque</a> habitant morbi tristique senectus et netus et malesuada fames ac turpis egestas. Vestibulum tortor quam, feugiat vitae, ultricies eget, tempor sit amet, ante. Donec eu libero sit amet quam egestas semper. Aenean ultricies mi vitae est. Mauris placerat eleifend leo. Quisque sit amet est et sapien ullamcorper pharetra. Vestibulum erat wisi, condimentum sed, commodo vitae, ornare sit amet, wisi. Aenean fermentum, elit eget tincidunt condimentum, eros ipsum rutrum orci, sagittis tempus lacus enim ac dui. Donec non enim in turpis pulvinar facilisis. Ut felis. Praesent dapibus, neque id cursus faucibus, tortor neque egestas augue, eu vulputate magna eros eu erat. Aliquam erat volutpat. Nam dui mi, tincidunt quis, accumsan porttitor, facilisis luctus, metus</p>
        <p><a name="who">Pellentesque</a> habitant morbi tristique senectus et netus et malesuada fames ac turpis egestas. Vestibulum tortor quam, feugiat vitae, ultricies eget, tempor sit amet, ante. Donec eu libero sit amet quam egestas semper. Aenean ultricies mi vitae est. Mauris placerat eleifend leo. Quisque sit amet est et sapien ullamcorper pharetra. Vestibulum erat wisi, condimentum sed, commodo vitae, ornare sit amet, wisi. Aenean fermentum, elit eget tincidunt condimentum, eros ipsum rutrum orci, sagittis tempus lacus enim ac dui. Donec non enim in turpis pulvinar facilisis. Ut felis. Praesent dapibus, neque id cursus faucibus, tortor neque egestas augue, eu vulputate magna eros eu erat. Aliquam erat volutpat. Nam dui mi, tincidunt quis, accumsan porttitor, facilisis luctus, metus</p>
        <p><a name="sources">Pellentesque</a> habitant morbi tristique senectus et netus et malesuada fames ac turpis egestas. Vestibulum tortor quam, feugiat vitae, ultricies eget, tempor sit amet, ante. Donec eu libero sit amet quam egestas semper. Aenean ultricies mi vitae est. Mauris placerat eleifend leo. Quisque sit amet est et sapien ullamcorper pharetra. Vestibulum erat wisi, condimentum sed, commodo vitae, ornare sit amet, wisi. Aenean fermentum, elit eget tincidunt condimentum, eros ipsum rutrum orci, sagittis tempus lacus enim ac dui. Donec non enim in turpis pulvinar facilisis. Ut felis. Praesent dapibus, neque id cursus faucibus, tortor neque egestas augue, eu vulputate magna eros eu erat. Aliquam erat volutpat. Nam dui mi, tincidunt quis, accumsan porttitor, facilisis luctus, metus</p>
        <p><a name="based">Pellentesque</a> habitant morbi tristique senectus et netus et malesuada fames ac turpis egestas. Vestibulum tortor quam, feugiat vitae, ultricies eget, tempor sit amet, ante. Donec eu libero sit amet quam egestas semper. Aenean ultricies mi vitae est. Mauris placerat eleifend leo. Quisque sit amet est et sapien ullamcorper pharetra. Vestibulum erat wisi, condimentum sed, commodo vitae, ornare sit amet, wisi. Aenean fermentum, elit eget tincidunt condimentum, eros ipsum rutrum orci, sagittis tempus lacus enim ac dui. Donec non enim in turpis pulvinar facilisis. Ut felis. Praesent dapibus, neque id cursus faucibus, tortor neque egestas augue, eu vulputate magna eros eu erat. Aliquam erat volutpat. Nam dui mi, tincidunt quis, accumsan porttitor, facilisis luctus, metus</p>
    </div>
</body>
</html>';
?>