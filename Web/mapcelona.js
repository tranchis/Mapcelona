var map =new GMap2(document.getElementById("map"));
map.addControl(new GLargeMapControl3D());
map.setCenter(new GLatLng(41.387917,2.169919), 12);
var kml = new GGeoXml("http://www.mapcelona.org/doc.kml");
showPolygons();

function showPolygons() {
    kml = new GGeoXml("http://www.mapcelona.org/doc.kml");
    map.addOverlay(kml);
}
function hidePolygons() {
    map.removeOverlay(kml);
}

function expandPanel() {
    document.getElementById("panel").style.width = 100%;
}


        var map =new GMap2(document.getElementById("map"));
        map.addControl(new GLargeMapControl3D());
        map.setCenter(new GLatLng(41.387917,2.169919), 12);
        var kml = new GGeoXml("http://www.mapcelona.org/doc.kml");
        showPolygons();

        function showPolygons() {
            kml = new GGeoXml("http://www.mapcelona.org/doc.kml");
            map.addOverlay(kml);
        }
        function hidePolygons() {
            map.removeOverlay(kml);
        }
