<?php

include('dataClassManager.php');

$params = $_GET['params'];
//$params = '255,77-252,24';
//echo $params;
$pairs = explode('-',$params);
//print_r($pairs);
$exploded_pairs = array();
foreach ($pairs as $pair) {
    $var = explode(',',$pair);
    //print_r($var);
    //print($var[0]);
    //print($var[1]);
    $exploded_pairs[$var[0]] = $var[1];
}

// Generate a random character string
function rand_str($length = 32, $chars = 'ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz1234567890')
{
    // Length of character list
    $chars_length = (strlen($chars) - 1);

    // Start our string
    $string = $chars{rand(0, $chars_length)};
    
    // Generate random string
    for ($i = 1; $i < $length; $i = strlen($string))
    {
        // Grab a random character from our list
        $r = $chars{rand(0, $chars_length)};
        
        // Make sure the same two characters don't appear next to each other
        if ($r != $string{$i - 1}) $string .=  $r;
    }
    
    // Return the string
    return $string;
}

$dcm = new dataClassManager();
@$values=$dcm->getIndicator($exploded_pairs);

//print_r($colors);

$filename="./doc.kml";
$output="";
$file = fopen($filename, "r");
while(!feof($file)) {
  $output = $output . fgets($file, 4096);
}
fclose ($file);

// print_r($colors);
foreach($values as $dataclass => $value)
{
	//echo $color["green"] . ":" . $color["red"] . ":" . ($color["green"] - $color["red"]) . "<br/>";
	/*
	$total = $color["green"] - $color["red"];
	if($total < 0)
	{
		$alpha = strtoupper(dechex(min(255, 128 - $total*128)));
		$red = "FF";
		$green = "00";
	}
	else
	{
		$red = "00";
		$green = "FF";
		$alpha = strtoupper(dechex(min(255, 128 + $total*128)));
	}
	*/
	/*
	if(strlen($red . "") == 1)
	{
		$red = "0" . $red;
	}
	if(strlen($green . "") == 1)
	{
		$green = "0" . $green;
	}
	$blue = "00";
	*/
	//echo $dataclass . ":" . $alpha . $blue . $green . $red . "<br/>";
	$alpha = strtoupper(dechex(min(255, $value * 256)));
	$blue = "00";
	$green = "FF";
	$red = "00";
	$output = str_replace("FAFAF" . $dataclass, $alpha . $blue . $green . $red, $output);
}

$randname = rand_str(5);
$filename="./doc" . $randname . ".kml";
$file = fopen($filename, "w");
fputs($file, $output);
fclose ($file);

echo "http://www.mapcelona.org/devel/doc". $randname .".kml";

?>