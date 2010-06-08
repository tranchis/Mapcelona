xml.instruct!
xml.rdf(:RDF,
        "xmlns:rdf"  => "http://www.w3.org/1999/02/22-rdf-syntax-ns#",
        "xmlns:foaf" => "http://xmlns.com/foaf/0.1/",
        "xmlns:rdfs" =>"http://www.w3.org/2000/01/rdf-schema#",
        "xml:base" => "http://data.mapcelona.org/mapcelona.owl",
        "xmlns:dc" => "http://purl.org/dc/elements/1.1/",
        "xmlns:rdfs" => "http://www.w3.org/2000/01/rdf-schema#",
        "xmlns:wgs84_pos" => "http://www.w3.org/2003/01/geo/wgs84_pos#",
        "xmlns:owl" => "http://www.w3.org/2002/07/owl#",
        "xmlns:xsd" => "http://www.w3.org/2001/XMLSchema#",
        "xmlns:ontology" => "http://www.geonames.org/ontology#",
        "xmlns:mapcelona" => "http://data.mapcelona.org/mapcelona.owl") do

          @point.statements.each do |st|
            xml.rdf(:statement) do
              xml.rdf(:subject, st.subject)
              xml.rdf(:predicate, st.predicate)
              xml.rdf(:object, st.object.to_s.gsub("\"", ""))
            end
          end
end