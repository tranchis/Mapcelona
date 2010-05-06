// globals
var factors =  new Array();
var map_height;
var panel_padding = 10;
var panel_height;

// Floater
/*
$('.floater_close').bind('click', function() {
  $('#overlay').addClass('invisible');
  $('#floater').addClass('invisible');
});
*/

// adjusting layout according to window size
function resizeApp() {
	map_height = $(window).height() - $('#header').height();
	panel_height = map_height - panel_padding;
	$('#panel').height(panel_height);
	$('#panel').width($(window).width()*0.20 - panel_padding);
	$('#factors_target').height(panel_height - $('#panel_header').height() - $('#panel_footer').height() - 2 * panel_padding - 16);
	$('#panel_expanded').height(map_height);
	$('#factors_expanded').height(map_height - $('#panel_expanded_header').height() - $('#panel_expanded_footer').height() - 3 * panel_padding - 2);
	$('#map').height(map_height);
}

resizeApp();

// loading the map
var map = new GMap2(document.getElementById("map"));
map.addControl(new GLargeMapControl3D());
map.setCenter(new GLatLng(41.40,2.17), 12);
var logo = new GScreenOverlay('http://www.mapcelona.org/devel/css/images/logo_trans.png', new GScreenPoint(0.5,0,'fraction','fraction'), new GScreenPoint(68,0), new GScreenSize(135,40));
map.addOverlay(logo);
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

function updateMap() { // !!!! we should check whether the array of factors has changed since last update or not in order to avoid unnecessary computation and data transfer

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
	$('#factors_expanded').height(map_height - $('#panel_expanded_header').height() - $('#panel_expanded_footer').height() - 3 * panel_padding - 2);
}

function collapsePanel() {
	$('#panel_expanded').toggle('slide',{},'slow');
    updateMap();
}

// factors functions
/*
$('.expandable').click(function() {
			$(this).children().last().toggle('blind');
			return false;
		});
*/
		
$('.expandable .expandable_trigger').click(function() {
	$(this).parent().next().toggle('slow');
	return false;
}).parent().next().hide();

$('.factor').draggable({
	helper: function(event) {
				return $('<li class="factor" style="width:'+$('#panel').width()+';padding:5px;">'+$(this).html()+'</li>');
			},
	opacity: 0.75,
	revert: 'invalid',
	zIndex: 1000
	});

$('#factors_target').droppable({
	accept: '.factor',
	activate: function(event, ui) { $('#factors_target').effect('highlight'); },
	drop: function(event, ui) { 
		// code to be executed when a factor is dropped
		$('#selected_factors').append(ui.draggable.clone());
		if( $('#drag_help').hasClass('visible') ) $('#drag_help').toggleClass('visible invisible');
		/* $('#drag_help').effect('toggle'); */
		// add the id of the factor in the array
		factors.push(ui.draggable.attr('id'));
		// and disable the factor preventing it being added twice
		ui.draggable.draggable("option", "disabled", true);
		ui.draggable.addClass('factor_disabled');
	},
	over:function(event, ui) { $('#factors_target').effect('highlight'); }
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
	setTimeout("if( $('#drag_help').hasClass('invisible') ) $('#drag_help').toggleClass('visible invisible');",1000);
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