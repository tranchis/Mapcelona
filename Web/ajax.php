<?php
include('dataClassManager.php');
// Hacer array_unique en $exploded_pairs y luego un ksort.
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


// Limpiar $_GET
$params = $_GET['params'];
//$params = '255,77-377,23';

/*
 * Versión cuando la petición contenía todas las capas a la vez
//echo $params;
$pairs = explode('-',$params);
//print_r($pairs);
$exploded_pairs = array();
$data_class_ID = 0;
foreach ($pairs as $pair) {
    $var = explode(',',$pair);
    //print_r($var);
    //print($var[0]);
    //print($var[1]);
    $data_class_ID = $var[0];
    $exploded_pairs[$data_class_ID] = $var[1];    
}*/
/* Versión actual: una petición por capa
 *
 */
//$params = '255,1';
$var = explode(',',$params);
$data_class_ID = $var[0];
if (!is_numeric($data_class_ID) || !is_numeric($var[1])) $return['error'] = true;
else{
    // We have checked that both inputs are numeric and no SQL injection is introduced
    $exploded_pairs[$data_class_ID] = $var[1];
    $dcm = new dataClassManager();
    // Buscar si existe un fichero llamado doc+toString($exploded_pairs).kml
    @$values=$dcm->getIndicator($exploded_pairs);
    //print_r($colors);
    $filename="./doc.kml";
    $output="";
    $file = fopen($filename, "r");
    while(!feof($file)) $output = $output . fgets($file, 4096);
    fclose ($file);
    // print_r($colors);
    $startcolor = hexdec("0000FF");
    $endcolor = hexdec("00FF00");
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
	$alpha = strtoupper(dechex(min(255, 128)));//100 + $value * 100)));
	if(strlen($alpha) == 1) $alpha = "0" . $alpha;
	$bgr = dechex($startcolor + $value * ($endcolor - $startcolor));
	while(strlen($bgr) < 6)	$bgr = "0" . $bgr;
	$output = str_replace("FAFAF" . $dataclass, $alpha . $bgr, $output);
    }

    $randname = rand_str(5);
    // Guardar el fichero como doc+toString($explodedPairs).kml
    // Actualizar indicadores de uso: el string explodedPairs, para saber el nº de veces usada esa combinación y luego cada parámetro por separado
    $filename="./doc" . $randname . ".kml";
    $file = fopen($filename, "w");
    fputs($file, $output);
    fclose ($file);

    $return['DataClassId'] = $data_class_ID;
    $return['Url']= "http://www.mapcelona.org/devel/doc{$randname}.kml";
    /* echo "http://www.mapcelona.org/devel/doc". $randname .".kml"; */
    //echo '{"DataClassId":'. $data_class_ID .',"Url":"http://www.mapcelona.org/devel/doc'. $randname .'.kml"}';
}
echo json_encode($return);
?>