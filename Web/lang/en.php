<?php
// Navigation
$Definition['Home'] = 'Home';
$Definition['Examples'] = 'Examples';
$Definition['About'] = 'About';
$Definition['We'] = 'We';
$Definition['Help'] = 'Help';

// Action list
$Definition["GetData"] = 'Get the data';
$Definition["Share"] = 'Share';

// Floater
$Definition["Go_Abredatos"] = 'Redirect to the AbreDatos 2010 version';
$Definition["Go_Devel"] = 'Go to the updated version of the app';

// About links
$Definition['What'] = 'What is mapcelona?';
$Definition['Who'] = 'Who is mapcelona?';
$Definition['Sources'] = 'Which sources?';
$Definition['BasedOn'] = 'Under the hood';

// Panel
$Definition["TuneParams"] = 'Tune features';
$Definition["Close"] = 'Close';
$Definition['RefreshMap'] = 'Update map';
$Definition['ClearParams'] = 'Clear';
$Definition['DragFactors'] = 'Drag here features to be visualized';

// About
$Definition["AboutText"] = "<p>Mapcelona has been developed in the context of Abredatos contest by four members of the Knowledge Engineering and Machine Learning group (<a href='http://www.kemlg.upc.edu'>KEMLg</a>) in the Technical University of Catalonia (UPC).</p>
<p>The goal of Mapcelona is to ease the access and visualisation of data coming from the Generalitat de Catalunya and the Barcelona local council.</p>
<p>These data have been parsed from PDFs and they have been modelled in RDF so, besides being used in Mapcelona, other applications can access them thus avoiding repeating the same heavy extraction task.</p>
<p>The application allows choosing those parameters to be visualised and detail the interest by putting a weight on each of them. These weighted and normalised parameters are agglutinated in an indicator that is used to generate a heat map. This map shows the value of this indicator in the neighbourhoods and districts of Barcelona.</p>
<p>The drop-down list of examples contains predefined sets of parameters along their weights: youngster, oldie and couple with children.</p>
<p>In the specific case of couple with children, it shows as interests school presence, healty facilities, parks and a low crime rate. The application then retrieves the stored data, normalise them and shows those neighbourhoods with best values for the chosen parameters.</p>
<p>It is worth to mention that the Barcelona local council has been contacted and, besides showing interest on this initiative, it is possible that the feedback received from Mapcelona users could be used to know which public data would have a higher preference to be freed according to the citizen point of view.</p>
<br />
<br />
<p>Sources:
<ul id='sources'>
<li><a href='http://ca.wikipedia.org/wiki/Districtes_i_barris_de_Barcelona'>http://ca.wikipedia.org/wiki/Districtes_i_barris_de_Barcelona</a></li>
<li><a href='http://www.bcn.es/estadistica/catala/dades/inf/guies/dte01.pdf'>http://www.bcn.es/estadistica/catala/dades/inf/guies/dte01.pdf</a></li>
<li><a href='http://www.bcn.es/estadistica/catala/dades/inf/guies/dte02.pdf'>http://www.bcn.es/estadistica/catala/dades/inf/guies/dte02.pdf</a></li>
<li><a href='http://www.bcn.es/estadistica/catala/dades/inf/guies/dte03.pdf'>http://www.bcn.es/estadistica/catala/dades/inf/guies/dte03.pdf</a></li>
<li><a href='http://www.bcn.es/estadistica/catala/dades/inf/guies/dte04.pdf'>http://www.bcn.es/estadistica/catala/dades/inf/guies/dte04.pdf</a></li>
<li><a href='http://www.bcn.es/estadistica/catala/dades/inf/guies/dte05.pdf'>http://www.bcn.es/estadistica/catala/dades/inf/guies/dte05.pdf</a></li>
<li><a href='http://www.bcn.es/estadistica/catala/dades/inf/guies/dte06.pdf'>http://www.bcn.es/estadistica/catala/dades/inf/guies/dte06.pdf</a></li>
<li><a href='http://www.bcn.es/estadistica/catala/dades/inf/guies/dte07.pdf'>http://www.bcn.es/estadistica/catala/dades/inf/guies/dte07.pdf</a></li>
<li><a href='http://www.bcn.es/estadistica/catala/dades/inf/guies/dte08.pdf'>http://www.bcn.es/estadistica/catala/dades/inf/guies/dte08.pdf</a></li>
<li><a href='http://www.bcn.es/estadistica/catala/dades/inf/guies/dte09.pdf'>http://www.bcn.es/estadistica/catala/dades/inf/guies/dte09.pdf</a></li>
<li><a href='http://www.bcn.es/estadistica/catala/dades/inf/guies/dte10.pdf'>http://www.bcn.es/estadistica/catala/dades/inf/guies/dte10.pdf</a></li>
<li><a href='http://mediambient.gencat.cat/cat/el_medi/atmosfera/pla_millora_qualitat_aire/doc/informe_seguiment1.pdf'>http://mediambient.gencat.cat/cat/el_medi/atmosfera/pla_millora_qualitat_aire/doc/informe_seguiment1.pdf</a></li>
<li><a href='http://www.elpais.com/elpaismedia/ultimahora/media/200811/30/espana/20081130elpepunac_3_Pes_PDF.pdf'>http://www.elpais.com/elpaismedia/ultimahora/media/200811/30/espana/20081130elpepunac_3_Pes_PDF.pdf</a></li>
</ul>
</p>";
?>