xml.instruct!

xml.kml(:xmlns => "http://earth.google.com/kml/2.2") {
  xml.Document {
    xml.Placemark {
      xml.Name(@map.name)
      xml.Description(@map.name)
      xml.MultiGeometry { |x| x << @map.kml }
    }
  }
}
