<?php
include('define.php');
include('dataClassManager.php');

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
$colors=$dcm->getIndicator(array(456=>1));

//print_r($colors);

$filename="./doc.kml";
$output="";
$file = fopen($filename, "r");
while(!feof($file)) {
  $output = $output . fgets($file, 4096);
}
fclose ($file);

print_r($colors);
foreach($colors as $dataclass => $color)
{
	//echo $color["green"] . ":" . $color["red"] . ":" . ($color["green"] - $color["red"]) . "<br/>";
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
	/*
	if(strlen($red . "") == 1)
	{
		$red = "0" . $red;
	}
	if(strlen($green . "") == 1)
	{
		$green = "0" . $green;
	}
	*/
	$blue = "00";
	//echo $dataclass . ":" . $alpha . $blue . $green . $red . "<br/>";
	$output = str_replace("FAFAF" . $dataclass, $alpha . $blue . $green . $red, $output);
}


$filename="./doc" . rand_str(5) . ".kml";
$file = fopen($filename, "w");
fputs($file, $output);
fclose ($file);

//echo $filename;

?>
