require 'rdf/cassandra'

class Entity
  @@repository = RDF::Cassandra::Repository.new(:servers => "127.0.0.1:9160")
  @@type = RDF::URI.new("http://www.w3.org/1999/02/22-rdf-syntax-ns#type")
  @@hasName = RDF::URI.new("http://data.mapcelona.org/mapcelona.owl#hasName")
  @@kml = RDF::URI.new("http://data.mapcelona.org/mapcelona.owl#hasKml")
  @@obj_entity = RDF::URI.new('http://data.mapcelona.org/mapcelona.owl#Entity')
  attr_accessor :code
  attr_accessor :statements
  attr_accessor :name
  attr_accessor :kml

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
    uri = 'http://data.mapcelona.org/entities/' + id
    rs = @@repository.query([RDF::URI.new(uri), nil, nil])
    entity = self.new
    if rs.empty?
      rs = @@repository.query([nil, @@hasName, id])
    end
    rs.each_statement do |r|
      entity.statements << r
      if r.predicate.to_s.eql? "http://data.mapcelona.org/mapcelona.owl#hasName"
        entity.name = r.object.value.to_s
      elsif r.predicate.to_s.eql? "http://data.mapcelona.org/mapcelona.owl#hasKml"
        entity.kml = r.object.value.to_s
      end
    end
    entity
  end
  
  def to_s
    @code
  end
end
