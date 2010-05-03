require 'rdf/cassandra'
require 'geonames'

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
  
  def self.get_query(id)
    uri = 'http://data.mapcelona.org/entities/' + id
    rs = @@repository.query([RDF::URI.new(uri), nil, nil])
    if false
    #if rs.empty?
      sc = Geonames::ToponymSearchCriteria.new
      sc.q = id
      r = Geonames::WebService.search sc
      topo = r.toponyms[0].geoname_id.to_s
      uri = 'http://data.mapcelona.org/entities/' + topo
      rs = @@repository.query([RDF::URI.new(uri), nil, nil])
      if rs.empty?
        sc.q = nil
        sc.feature_codes = ["PCLI", "TERR", "PCLD", "PCLI", "PPLA", "PPLC"]
        sc.name_equals = id.gsub(", The", "")
        r = Geonames::WebService.search sc
        topo = r.toponyms[0].geoname_id.to_s
        uri = 'http://data.mapcelona.org/entities/' + topo
        rs = @@repository.query([RDF::URI.new(uri), nil, nil])
      end
    end
    rs
  end
  
  def self.get_query_online(id)
    uri = 'http://data.mapcelona.org/entities/' + id
    entity = self.new
    done = false
    rs = @@repository.query([RDF::URI.new(uri), nil, nil]) do |r|
      done = true
      entity.statements << r
      if r.predicate.to_s.eql? "http://data.mapcelona.org/mapcelona.owl#hasName"
        entity.name = r.object.value.to_s
      elsif r.predicate.to_s.eql? "http://data.mapcelona.org/mapcelona.owl#hasKml"
        entity.kml = r.object.value.to_s
      end
    end
    if !done
      sc = Geonames::ToponymSearchCriteria.new
      sc.q = id
      r = Geonames::WebService.search sc
      topo = r.toponyms[0].geoname_id.to_s
      entity = get_query_online(topo)
    end
    entity
  end
  
  def self.populate(rs)
    entity = self.new
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
  
  def self.find(id)
    @statements = []
    entity = get_query_online(id)
    #entity = populate(rs)
  end
  
  def to_s
    @code
  end
end
