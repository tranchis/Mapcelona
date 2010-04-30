require 'net/http'
require 'rexml/document'
require 'coordinate_converter'
require 'socket'

xml_data = File.open('/Users/sergio/Desktop/Municipis.gml').read

# extract event information
s = TCPSocket.open('localhost', 1111)
cc = CoordinateConverter.new
doc = REXML::Document.new(xml_data)
titles = []
links = []
doc.elements.each('gml:FeatureCollection/gml:featureMember/idec:Municipis') do |ele|
  ele.elements.each('idec:NOM_MUNI') do |nom|
    titles << nom.text
  end
  poly = []
  ele.elements.each('idec:the_geom/gml:MultiPolygon/gml:polygonMember') do |pm|
    pm.elements.each('gml:Polygon') do |polygon|
      coords = []
      polygon.elements.each('gml:outerBoundaryIs/gml:LinearRing/gml:coordinates') do |coord|
        coord.text.split.each do |xy|
          s.puts(xy)
          coords << s.gets.chomp
        end
      end
      poly << coords
    end
  end
  links << poly
end
s.close

# print all events
#puts titles.length
#puts links.length
titles.each_with_index do |title, idx|
  puts links[idx].length.to_s + " poligons"
  print "#{title} => #{links[idx][0][0]}\n"
end
