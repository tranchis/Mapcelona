<?php
// Navigation
$Definition['Home'] = 'Home';
$Definition['Examples'] = 'Examples';
$Definition['About'] = 'About';
$Definition['Help'] = 'Help';

// Action list
$Definition["GetData"] = 'Get the data';
$Definition["Share"] = 'Share';

// About links
$Definition['What'] = 'What is mapcelona?';
$Definition['Who'] = 'Who is mapcelona?';
$Definition['Sources'] = 'Which sources?';
$Definition['BasedOn'] = 'Under the hood';

// Panel
$Definition["TuneParams"] = 'Tune parameters';
$Definition["Close"] = 'Close';

// About
$Definition["AboutText"] = "Mapcelona has been developed in the context of Abredatos contest by four members of the Knowledge Engineering and Machine Learning group (<a href='http://www.kemlg.upc.edu'>KEMLg</a>) in the Technical University of Catalonia (UPC).<br /><br />
The goal of Mapcelona is to ease the access and visualisation of data coming from the Generalitat de Catalunya and the Barcelona local council.<br /><br />
These data have been parsed from PDFs and they have been modelled in RDF so, besides being used in Mapcelona, other applications can access them thus avoiding repeating the same heavy extraction task.<br /><br />
The application allows choosing those parameters to be visualised and detail the interest by putting a weight on each of them. These weighted and normalised parameters are agglutinated in an indicator that is used to generate a heat map. This map shows the value of this indicator in the neighbourhoods and districts of Barcelona.<br /><br />
The drop-down list of examples contains predefined sets of parameters along their weights: youngster, oldie and couple with children.<br /><br />
In the specific case of couple with children, it shows as interests school presence, healty facilities, parks and a low crime rate. The application then retrieves the stored data, normalise them and shows those neighbourhoods with best values for the chosen parameters.<br /><br />
It is worth to mention that the Barcelona local council has been contacted and, besides showing interest on this initiative, it is possible that the feedback received from Mapcelona users could be used to know which public data would have a higher preference to be freed according to the citizen point of view.<br /><br />
<br />
<br />
Sources:<br />
<a href='http://ca.wikipedia.org/wiki/Districtes_i_barris_de_Barcelona'>http://ca.wikipedia.org/wiki/Districtes_i_barris_de_Barcelona</a><br />
<a href='http://www.bcn.es/estadistica/catala/dades/inf/guies/dte01.pdf'>http://www.bcn.es/estadistica/catala/dades/inf/guies/dte01.pdf</a><br />
<a href='http://www.bcn.es/estadistica/catala/dades/inf/guies/dte06.pdf'>http://www.bcn.es/estadistica/catala/dades/inf/guies/dte06.pdf</a><br />
<a href='http://www.bcn.es/estadistica/catala/dades/inf/guies/dte10.pdf'>http://www.bcn.es/estadistica/catala/dades/inf/guies/dte10.pdf</a><br />
<a href='http://mediambient.gencat.cat/cat/el_medi/atmosfera/pla_millora_qualitat_aire/doc/informe_seguiment1.pdf'>http://mediambient.gencat.cat/cat/el_medi/atmosfera/pla_millora_qualitat_aire/doc/informe_seguiment1.pdf</a><br />
<a href='http://www.elpais.com/elpaismedia/ultimahora/media/200811/30/espana/20081130elpepunac_3_Pes_PDF.pdf'>http://www.elpais.com/elpaismedia/ultimahora/media/200811/30/espana/20081130elpepunac_3_Pes_PDF.pdf</a><br />
";
?>