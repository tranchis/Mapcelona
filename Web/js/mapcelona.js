// loading the map
var map =new GMap2(document.getElementById("map"));
map.addControl(new GLargeMapControl3D());
map.setCenter(new GLatLng(41.387917,2.169919), 12);
var kml;
showPolygons();

// maps functions
function showPolygons() {
    kml = new GGeoXml("http://www.mapcelona.org/doc.kml");
    map.addOverlay(kml);
}
function hidePolygons() {
    map.removeOverlay(kml);
}

// handling the panel
function expandPanel() {
    document.getElementById("panel").setAttribute("class", "panel_expanded");
    document.getElementById("panel_collapsed").setAttribute("class", "invisible");
    document.getElementById("panel_expanded").setAttribute("class", "visible");
}
function collapsePanel() {
    document.getElementById("panel").setAttribute("class", "panel_collapsed");
    document.getElementById("panel_collapsed").setAttribute("class", "visible");
    document.getElementById("panel_expanded").setAttribute("class", "invisible");
}

// factors in the panel
function checkParameter(chk) {
    if (chk.checked) {
        // add the slider
        chk.parentNode.innerHTML += '<div class="slider"><div class="slider-handle"></div></div><span>0</span> %';
        
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

    			var ratio = ui.position.left/this.parWidth;
    			var scale = this.parWidth-this.width;
    			ratio = Math.round(((ratio*100)-6)/94);
    			this.par.siblings().last().html(ui.position.left+'/'+this.parWidth+'='+ratio);
    		}
    	});
    }
}