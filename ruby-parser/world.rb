require 'rubygems'
require 'builder'
require 'net/http'
require 'rexml/document'
require 'coordinate_converter'
require 'socket'
require 'geonames'
require 'rdf'
require 'rdf/cassandra'
require 'cassandra'

pred_hasp = RDF::URI.new('http://data.mapcelona.org/mapcelona.owl#hasPolygon')
pred_haspt = RDF::URI.new('http://data.mapcelona.org/mapcelona.owl#hasPoint')
pred_lat = RDF::URI.new('http://www.w3.org/2003/01/geo/wgs84_pos#lat')
pred_long = RDF::URI.new('http://www.w3.org/2003/01/geo/wgs84_pos#long')
obj_polygon = RDF::URI.new('http://data.mapcelona.org/mapcelona.owl#Polygon')
obj_entity = RDF::URI.new('http://data.mapcelona.org/mapcelona.owl#Entity')
obj_point = RDF::URI.new('http://www.w3.org/2003/01/geo/wgs84_pos#Point')
type = RDF::URI.new("http://www.w3.org/1999/02/22-rdf-syntax-ns#type")
sameas = RDF::URI.new("http://www.w3.org/2002/07/owl#sameAs")
hasName = RDF::URI.new("http://data.mapcelona.org/mapcelona.owl#hasName")
hasKml = RDF::URI.new("http://data.mapcelona.org/mapcelona.owl#hasKml")
uri_polygon_base = 'http://data.mapcelona.org/polygons/'
uri_point_base = 'http://data.mapcelona.org/points/'

def get_kml(st)
  xml = Builder::XmlMarkup.new
  xml.MultiGeometry(:xmlns => "http://earth.google.com/kml/2.2") {
    st.each do |polygon|
      xml.Polygon {
        xml.outerBoundaryIs {
          xml.LinearRing {
            res = ""
            polygon.each do |coords|
              res << coords << ' '
            end
            xml.coordinates(res)
          }
        }
      }
    end
  }
end

repository = RDF::Cassandra::Repository.new(:servers => "147.83.200.118:80")
xml_data = File.open('./world.kml').read
sc = Geonames::ToponymSearchCriteria.new
sc.feature_codes = ["PCLI", "TERR", "PCLD", "PCLI", "PPLA"]
#sc.country_code = "ES"

# extract event information
#s = TCPSocket.open('localhost', 1111)
#cc = CoordinateConverter.new
doc = REXML::Document.new(xml_data)
title = ""
links = []
kml = ""
doc.elements.each('kml/Document/Folder') do |ele|
  ele.elements.each('name') do |ll|
    if ll.text.eql? "Countries"
      ele.elements.each('Folder') do |fl|
        fl.elements.each('name') do |n|
          if !n.text.eql? "Labels"
            fl.elements.each('Placemark') do |p|
              topo = nil
              id = nil
              p.elements.each('name') do |nm|
                puts nm.text
                sc.name_equals = nm.text.gsub(", The", "")
                r = Geonames::WebService.search sc
                topo = r.toponyms[0]
                id = topo.geoname_id
              end
              if topo != nil
                obj = RDF::URI.new('http://sws.geonames.org/' + id + '/about.rdf')
                subj = RDF::URI.new('http://data.mapcelona.org/entities/' + id)
                done = false
                while !done
                  begin
                    rs = repository.query([subj, sameas, obj])
                    #rs = repository.query([subj, hasName, nil])
                    if rs.empty?
                      puts topo.name + " no esta"
                      poly = []
                      p.elements.each('Polygon') do |ply|
                        coords = []
                        ply.elements.each('outerBoundaryIs/LinearRing/coordinates') do |coord|
                          coord.text.split.each do |xy|
                            coords << xy.chomp
                          end
                        end
                        poly << coords
                      end
                      p.elements.each('MultiGeometry') do |mp|
                        poly = []
                        p.elements.each('Polygon') do |ply|
                          coords = []
                          ply.elements.each('outerBoundaryIs/LinearRing/coordinates') do |coord|
                            coord.text.split.each do |xy|
                              coords << xy.chomp
                            end
                          end
                          poly << coords
                        end
                      end
                      kml << get_kml(poly)
                      print "#{title} => #{poly[0][0]}\n"
                      puts 'http://sws.geonames.org/' + id + '/about.rdf'
                      poly.each_with_index do |polygon, idx|
                        uri = uri_polygon_base + id + '_' + idx.to_s
                        repository.insert([uri, type, obj_polygon])
                        repository.insert([subj, pred_hasp, uri])
                        polygon.each do |point|
                          x = point.split(',')[0]
                          y = point.split(',')[1]
                          uri_p = uri_point_base + y.gsub('.', '_') + '_' + x.gsub('.', '_')
                          repository.insert([uri_p, type, obj_point])
                          repository.insert([uri, pred_haspt, uri_p])
                          repository.insert([uri_p, pred_lat, y.to_f])
                          repository.insert([uri_p, pred_long, x.to_f])
                        end
                      end
                      repository.insert([subj, type, obj_entity])
                      repository.insert([subj, hasName, topo.name])
                      repository.insert([subj, hasKml, kml])
                      repository.insert([subj, sameas, obj])
                    end
                    done = true
                  rescue Thrift::TransportException
                    puts "Cassandra error!"
                    repository = RDF::Cassandra::Repository.new(:servers => "147.83.200.118:80")
                  end
                end
              end
            end
          end
        end
      end
    end
  end
  
end
#s.close
