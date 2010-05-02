xml.instruct!

xml.kml(:xmlns => "http://earth.google.com/kml/2.2") {
  xml.Document {
    xml.Placemark { |x| x << @map.kml }
  }
}
