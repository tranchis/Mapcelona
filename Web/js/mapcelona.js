// Floater
$('.floater_close').bind('click', function() {
  $('#overlay').addClass('invisible');
  $('#floater').addClass('invisible');
});

// adjusting layout according to window size
var map_height = $(window).height() - $('#header').height();
var panel_padding = 10;
var panel_height = map_height - panel_padding;
$('#panel').height(panel_height);
$('#panel').width($(window).width()*0.20 - panel_padding);
$('#factors_target').height(panel_height - $('#panel_header').height() - panel_padding - 16);
$('#panel_expanded').height(map_height);
$('#map').height(map_height);

// loading the map
var map = new GMap2(document.getElementById("map"));
map.addControl(new GLargeMapControl3D());
map.setCenter(new GLatLng(41.40,2.17), 12);
var logo = new GScreenOverlay('http://www.mapcelona.org/devel/css/images/logo_trans.png', new GScreenPoint(0.5,0.05,'fraction','fraction'), new GScreenPoint(68,35), new GScreenSize(135,40));
map.addOverlay(logo);
var kmls = new Array();
var kml;
var overlaid = false;

// maps functions
function showPolygons(url) {
    if(overlaid) hidePolygons();
    kml = new GGeoXml(url/*,function() {
          if (geoxml.loadedCorrectly()) {
            kml.gotoDefaultViewport(map);
          }
        }*/);
    map.addOverlay(kml);
    overlaid = true;
}
function hidePolygons() {
    map.removeOverlay(kml);
}

function updateMap() {
    // collect all active factors and their weights
    var arrayOfIds = new Array();
    var arrayOfWeights = new Array();
    $('.active').each(function(){
        arrayOfIds.push($(this).attr('id'));
    });
    $('.factor_weight').each(function(){
        arrayOfWeights.push($(this).html());
    });
    if (arrayOfIds.length == arrayOfWeights.length && arrayOfIds.length > 0) {
        // encode all the values in the string to be sent
        var datastring = '';
        for(var i in arrayOfIds) {
            datastring += '-' + arrayOfIds[i] + ',' + arrayOfWeights[i];
        }
        datastring = datastring.substring(1);
        datastring = 'params=' + datastring;
        // make the ajax call
        $.ajax({
            url:'http://www.mapcelona.org/devel/ajax.php',
            data:datastring,
            success: function(url){showPolygons(url);}
        });
    }
}

// panel functions
function expandPanel() {
	$('#panel_expanded').toggle('slide',{},'slow');
	$('#factors_expanded').height(map_height - $('#panel_expanded_header').height() - $('#panel_expanded_footer').height() - 2 * panel_padding - 2);
}

function collapsePanel() {
	$('#panel_expanded').toggle('slide',{},'slow');
    updateMap();
}

// factors functions
$('.factor').draggable({ helper: 'clone', opacity: 0.75, revert: 'invalid', stack: ".factor", zIndex: 1000 });
$('#factors_target').droppable({
	activate: function(event, ui) {  },
	drop: function(event, ui) { $('#selected_factors').append(ui.draggable); },
	over:function(event, ui) { $('#factors_target').css('margin','2px'); },
	out:function(event, ui) { $('#factors_target').css('margin','5px'); }
});

function addParam(id, text)
{
	var panel = document.getElementById("selected_factors");
	var item = document.createElement("li");
	var bar = document.createElement("input");
	item.innerText = text;
	item.setAttribute("id", id);
	item.setAttribute("onclick", "\"addParam('" + id + "', '" + text + "');\"");
	item.setAttribute("class", "factor active");
	var div = document.createElement("div");
	var div2 = document.createElement("div");
	var div3 = document.createElement("div");
	div.setAttribute("class", "slider");
	div2.setAttribute("class", "slider-handle");
	div3.setAttribute("class", "factor_weight");
	div3.innerText = "0";
	div.appendChild(div2);
	item.appendChild(div);
	item.appendChild(div3);

	//<input name="slider1" id="slider-r" type="text" title="Range: 0 - 255" class="fd_range_0_255 fd_classname_extraclass fd_callback_updateColor" value="128" />
	bar.setAttribute("name", "slider1");
	bar.setAttribute("id", "slider-r");
	bar.setAttribute("type", "text");
	bar.setAttribute("class", "fd_range_0_255 fd_classname_extraclass fd_callback_updateColor");
	bar.setAttribute("value", "100");
	item.appendChild(bar);

	panel.appendChild(item);
}

function clearParams()
{
	$('#selected_factors li').remove();
	hidePolygons();
}

/*
$('.factor_checkbox').change(function() {
    if ($(this).is(':checked')) {
        $(this).parent().addClass('active');
        // add the slider
        $(this).parent().append('<div class="slider"><div class="slider-handle"></div></div><span class="factor_weight">0</span> %');
        
        $('.slider-handle').draggable({
    		containment:'parent',
    		axis:'x',
    		drag:function(e,ui){
    			// The drag function is called on every drag movement, no matter how minute
    			if(!this.par)
    			{
    				// Initializing the variables only on the first drag move for performance
    				this.par = $(this).parent();
    				this.parWidth = this.par.width();
    				this.width = $(this).width();
    			}
                
    			var ratio = ui.position.left/(this.parWidth-this.width);
    			ratio = Math.round(ratio*100);
    			this.par.siblings().last().html(ratio);
    		}
    	});
    } else {
        $(this).parent().removeClass('active');
        $(this).siblings().last().remove();
        $(this).siblings().last().remove();
        // we should test whether we are removing the '%' symbol or not
        //$(this).siblings().last().remove();
    }
});

$('.slider-handle').draggable({
	containment:'parent',
	axis:'x',
	drag:function(e,ui){
		// The drag function is called on every drag movement, no matter how minute 
		if(!this.par)
		{
			// Initializing the variables only on the first drag move for performance 
			this.par = $(this).parent();
			this.parWidth = this.par.width();
			this.width = $(this).width();
		}
        
		var ratio = ui.position.left/(this.parWidth-this.width);
		ratio = Math.round(ratio*100);
		this.par.siblings().last().html(ratio);
	}
});
*/

// utils

// parse a string containing an integer and the substring "px" and return the integer value
function parsePixels(pixels, defaultValue) {
	var parsedValue = parseInt(pixels.substring(0,pixels.length-2));
	if(typeof parsedValue == 'Integer') return parsedValue;
	else return defaultValue;
}