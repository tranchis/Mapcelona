xml.instruct!

xml.kml(:xmlns => "http://earth.google.com/kml/2.2") {
  xml.Document {
    xml.Placemark {
      xml.name(@map.name)
      xml.description(@map.name)
      xml.MultiGeometry { |x| x << @map.kml }
    }
  }
}
