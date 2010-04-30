// Floater
$('.floater_close').bind('click', function() {
  $('#overlay').addClass('invisible');
  $('#floater').addClass('invisible');
});

// loading the map
var map = new GMap2(document.getElementById("map"));
map.addControl(new GLargeMapControl3D());
map.setCenter(new GLatLng(41.40,2.17), 12);
var logo = new GScreenOverlay('http://www.mapcelona.org/devel/css/images/logo_trans.png', new GScreenPoint(0.5,0.05,'fraction','fraction'), new GScreenPoint(68,40), new GScreenSize(135,40));
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
    //setTimeout("var kml2 = new GGeoXml('http://www.mapcelona.org/devel/docAjCEC.kml');map.addOverlay(kml2);",5000);
    overlaid = true;
}
function hidePolygons() {
    map.removeOverlay(kml);
}

/*// handling the panel
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
}*/
function expandPanel() {
	$('#panel_expanded').toggle('slide',{},'slow');
	//Slide();
}
function collapsePanel() {
	$('#panel_expanded').toggle('slide',{},'slow');
	//Slide();
    updateMap();
}

function addParam(id, text)
{
	var panel = document.getElementById("factors_expanded");
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
	$('#factors_expanded li').remove();
	hidePolygons();
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