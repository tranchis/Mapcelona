require 'rdf'
require 'rdf/cassandra'

class Polygon
  @@repository = RDF::Cassandra::Repository.new(:servers => "147.83.200.118:80")
  @@type = RDF::URI.new("http://www.w3.org/1999/02/22-rdf-syntax-ns#type")
  @@obj_entity = RDF::URI.new('http://data.mapcelona.org/mapcelona.owl#Polygon')
  attr_accessor :code
  attr_accessor :statements

  def initialize
    @statements = []
  end
  
  def self.all(*args)
    puts @@repository.count
    entities = []
    @@repository.query([nil, @@type, @@obj_entity]).each do |statement|
      entity = self.new
      entity.code = statement.subject
      entities << entity
    end
    entities
  end
  
  def self.find(id)
    @statements = []
    uri = 'http://data.mapcelona.org/polygons/' + id
    rs = @@repository.query([RDF::URI.new(uri), nil, nil])
    entity = self.new
    rs.each_statement do |r|
      entity.statements << r
    end
    entity
  end
  
  def to_s
    @code
  end
end
