// globals
var factors = [];
var kmls = [];
var map_height;
var panel_padding = 10;
var panel_height;

function kmlObject(id,kml) {
	return kmlObject[id] = {id:id,kml:kml};
} 

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

// maps functions
function updateMap() { // !!!! we should check whether the array of factors has changed since last update or not in order to avoid unnecessary computation and data transfer

/*
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
        // encode all the values in the string to be sent in json should be -> [{id:204,weight:0},...]
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
            success: function(url){loadKml(url);}
        });
    }
*/

	// new version
	if($('#panel_expanded').is(":visible")) collapsePanel();
    if (factors.length > 0) {
        // encode all the values in the string to be sent in json should be -> [{id:204,weight:0},...]
        var datastring = '';
        for(var i in factors) {
	        datastring += '-' + factors[i] + ',1';
	        datastring = datastring.substring(1);
	        datastring = 'params=' + datastring;
	        // make the ajax call
	        $.ajax({
	            url:'http://www.mapcelona.org/devel/ajax.php',
	            data:datastring,
	            success: function(url){loadKml(url, factors[i]);}
	        });
	        // add the activity indicator
	        $('#selected_factors li[id='+factors[i]+']').prepend('<img src="./css/images/indicator_white.gif" />');
	        datastring = '';
        }
    }
}

function loadKml(url, id) {
	var geoXml = new GGeoXml(url,function() {
    	if (geoxml.hasLoaded()) {
        	kml.gotoDefaultViewport(map);
        	// remove the activity indicator
        	$('#selected_factors li[id='+id+']').first().remove();
        	// add the visibility checkbox
        	$('#selected_factors li[id='+factors[i]+']').prepend('<input type="checkbox" class="factor_checkbox" checked="checked" />');
        }
    });
    kmls.push({id:id,kml:geoXml});
    map.addOverlay(kmls[kmls.length-1].kml);
}

function showPolygonsForLayerWithId(id) {
	for (var i = kmls.length - 1; i >= 0; i--){
	    if(kmls[i].id == id) show(kmls[i].kml);
	};
}

function hidePolygons() {
	for(kml in kmls) hide(kml);
}

function hidePolygonsForLayerWithId(id) {
	for (var i = kmls.length - 1; i >= 0; i--){
	    if(kmls[i].id == id) hide(kmls[i].kml);
	};
}

function removeLayers() {
	for(kml in kmls) map.removeOverlay(kml);
	kmls = [];
	factors = [];
}

function removeLayerWithId(id) {
	for (var i = kmls.length - 1; i >= 0; i--){
	    if(factors[i] == id) {
	    	map.removeOverlay(kmls[i]);
	    	kmls.splice(i,1);
	    	factors.splice(i,1);
	    }
	};
}

// panel functions
function expandPanel() {
	$('#panel_expanded').toggle('slide',{},'slow');
	$('#factors_expanded').height(map_height - $('#panel_expanded_header').height() - $('#panel_expanded_footer').height() - 3 * panel_padding - 2);
}

function collapsePanel() {
	$('#panel_expanded').toggle('slide',{},'slow');
}

// factors functions		
$('.expandable .expandable_trigger').click(function() {
	$(this).parent().next().toggle('slow');
	return false;
}).parent().next().hide();

$(".factor").dblclick( function () { addFactor($(this)); });

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
	drop: function(event, ui) { // code to be executed when a factor is dropped
		addFactor(ui.draggable);
	},
	over:function(event, ui) { $('#factors_target').effect('highlight'); }
	});
	
function addFactor(factor) {
	// add the id of the factor in the array
	var id = factor.attr('id');
	factors.push(id);
	$('#selected_factors').append(
		'<li class="factor" id="'+id+'">'+factor.html()+'<a href="#" class="factor_clear" onclick="removeFactor('+id+');></a></li>'
	);
	if( $('#drag_help').hasClass('visible') ) $('#drag_help').toggleClass('visible invisible');
	// and disable the factor preventing it being added twice
	factor.draggable("option", "disabled", true); // functionally
	factor.addClass('factor_disabled'); // and visually
}

function removeFactor(id) {
	$('#selected_factors li[id='+id+']').remove();
	setTimeout("if($('#selected_factors li').length == 0 && $('#drag_help').hasClass('invisible') ) $('#drag_help').toggleClass('visible invisible');",500);
	removeLayerWithId(id);
	$('.factor_disabled[id='+id+']').removeClass('factor_disabled');
}

function removeFactors()
{
	$('#selected_factors li').remove();
	setTimeout("if( $('#drag_help').hasClass('invisible') ) $('#drag_help').toggleClass('visible invisible');",500);
	removeLayers();
	$('.factor_disabled').removeClass('factor_disabled');
}

$('.factor_checkbox').change(function() {
	if ($(this).is(':checked')) {
		showPolygonsForLayerWithId($(this).parent().attr('id'));
	} else {
		hidePolygonsForLayerWithId($(this).parent().attr('id'));
	}
});

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