require 'rdf/cassandra'
require 'builder'

class Map
  @@repository = RDF::Cassandra::Repository.new(:servers => "127.0.0.1:9160", :timeout => 0.1)
  @@hasp = RDF::URI.new("http://data.mapcelona.org/mapcelona.owl#hasPolygon")
  @@haspt = RDF::URI.new("http://data.mapcelona.org/mapcelona.owl#hasPoint")
  @@obj_entity = RDF::URI.new('http://data.mapcelona.org/mapcelona.owl#Entity')
  @@obj_polygon = RDF::URI.new('http://data.mapcelona.org/mapcelona.owl#Polygon')
  @@obj_long = "http://www.w3.org/2003/01/geo/wgs84_pos#long"
  @@obj_lat = "http://www.w3.org/2003/01/geo/wgs84_pos#lat"
  
  attr_accessor :code
  attr_accessor :polygons

  def initialize
    @polygons = []
  end
  
  def self.find(id)
    Entity.find_map(id)
  end
end
