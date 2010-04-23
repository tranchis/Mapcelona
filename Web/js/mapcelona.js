// Floater
$('.floater_close').bind('click', function() {
  $('#overlay').addClass('invisible');
  $('#floater').addClass('invisible');
});


// loading the map
var map =new GMap2(document.getElementById("map"));
map.addControl(new GLargeMapControl3D());
map.setCenter(new GLatLng(41.387917,2.169919), 12);
var kml;
var overlaid = false;

// maps functions
function showPolygons(url) {
    if(overlaid) hidePolygons();
    kml = new GGeoXml(url);
    map.addOverlay(kml);
    overlaid = true;
}
function hidePolygons() {
    map.removeOverlay(kml);
}

// handling the panel
var sliderIntervalId = 0;
var totalHeight = 700;
var sliderHeight = 232;
var sliding = false;
var slideSpeed = 60;
function Slide()
{
   if(sliding)
      return;
   sliding = true;
   if(sliderHeight == totalHeight)
      sliderIntervalId = setInterval('SlideUpRun()', 30);
   else
      sliderIntervalId = setInterval('SlideDownRun()', 30);
}
function SlideUpRun()
{
   slider = document.getElementById('panel_expanded');
   if(sliderHeight <= 0)
   {
      sliding = false;
      sliderHeight = 0;
      slider.style.width = '0px';
      clearInterval(sliderIntervalId);
   }
   else
   {
      sliderHeight -= slideSpeed;
      if(sliderHeight < 0)
         sliderHeight = 0;
      slider.style.width = sliderHeight + 'px';
   }
}
function SlideDownRun()
{
   slider = document.getElementById('panel_expanded');
   if(sliderHeight >= totalHeight)
   {
      sliding = false;
      sliderHeight = totalHeight;
      slider.style.width = totalHeight + 'px';
      clearInterval(sliderIntervalId);
   }
   else
   {
      sliderHeight += slideSpeed;
      if(sliderHeight > totalHeight)
         sliderHeight = totalHeight;
      slider.style.width = sliderHeight + 'px';
   }
}
function expandPanel() {
/*    document.getElementById("panel").setAttribute("class", "panel_expanded");
    document.getElementById("panel_collapsed").setAttribute("class", "invisible");
    document.getElementById("panel_expanded").setAttribute("class", "visible");*/
    //$('#panel').toggleClass('panel_expanded panel_collapsed');
    //$('#panel_collapsed').toggleClass('visible invisible');
    //$('#panel_expanded').toggleClass('visible invisible');
	Slide();
}
function collapsePanel() {
/*    document.getElementById("panel").setAttribute("class", "panel_collapsed");
    document.getElementById("panel_collapsed").setAttribute("class", "visible");
    document.getElementById("panel_expanded").setAttribute("class", "invisible");*/
    //$('#panel').toggleClass('panel_expanded panel_collapsed');
    //$('#panel_collapsed').toggleClass('visible invisible');
    //$('#panel_expanded').toggleClass('visible invisible');
	Slide();
    updateMap();
}

/*// factors in the panel
function checkParameter(chk) {
    if (chk.checked) {
        // add the slider
        chk.parentNode.innerHTML += '<div class="slider"><div class="slider-handle"></div></div><span>0</span>';
        
        
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
                
                var maxValue = this.parWidth-this.width;
                var ratio = ui.position.left/maxValue;
                ratio = Math.round(ratio*100);
                this.par.siblings().last().html(ratio + ' %');
            }
        });
    } else {
        //chk.parentNode.innerHTML 
    }
}*/

$('.factor_checkbox').change(function() {
    if ($(this).is(':checked')) {
        $(this).parent().addClass('active');
        // add the slider
        $(this).parent().append('<div class="slider"><div class="slider-handle"></div></div><span class="factor_weight">0</span> %');
        
        $('.slider-handle').draggable({
    		containment:'parent',
    		axis:'x',
    		drag:function(e,ui){
    			/* The drag function is called on every drag movement, no matter how minute */
    			if(!this.par)
    			{
    				/* Initializing the variables only on the first drag move for performance */
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