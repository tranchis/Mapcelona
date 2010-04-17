package org.mapcelona.javaparser.rdfutils;

import java.io.InputStream;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;

import virtuoso.jena.driver.VirtModel;

import com.hp.hpl.jena.query.Dataset;
import com.hp.hpl.jena.query.DatasetFactory;
import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QueryFactory;
import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.util.FileManager;

public class RDFUtils
{
	private ResourceBundle	rb;
	private Model			demo, city, m;
	private Resource		bcn, owlcity, owldistrict, owlbarrio;
	private Property		ofClass, hasName, isDistrictOf, isNeighbourhoodOf,
							hasMapping;
	
	private static final String	cityUri = "http://www.mapcelona.org/city.owl#";
	private static final String	bcnUri = "http://www.mapcelona.org/barcelona.owl#";
	private static final String	rdfUri = "http://www.w3.org/1999/02/22-rdf-syntax-ns#";
	private static final String demoUri = "http://www.mapcelona.org/demo.owl#";
	
	public RDFUtils()
	{
		rb = ResourceBundle.getBundle("virtuoso");
		connect();
		loadCity();
	}
	
	private void loadCity()
	{
		InputStream	in;

		city = ModelFactory.createOntologyModel();
		in = FileManager.get().open("http://www.mapcelona.org/city.owl");
		if (in == null)
		{
			throw new IllegalArgumentException("File not found");
		}
		city.read(in, null);

		demo = ModelFactory.createOntologyModel();
		in = FileManager.get().open("http://www.mapcelona.org/demo.owl");
		if (in == null)
		{
			throw new IllegalArgumentException("File not found");
		}
		demo.read(in, null);
		
		ofClass = city.getProperty(rdfUri + "type");
		owlcity = city.getResource(cityUri + "City");
		hasName = city.getProperty(cityUri + "hasName");
		owldistrict = city.getResource(cityUri + "District");
		isDistrictOf = city.getProperty(cityUri + "isDistrictOf");
		owlbarrio = city.getResource(cityUri + "Neighbourhood");
		isNeighbourhoodOf = city.getProperty(cityUri + "isNeighbourhoodOf");
		
		hasMapping = demo.getProperty(demoUri + "hasMapping");
	}

	private void connect()
	{
		m =	VirtModel.openDatabaseModel(
			"Barcelona", // graph name
			"jdbc:virtuoso://" + rb.getString("ip") + ":1111", //1111 is the default port
			"dba", // user
			rb.getString("password") // password
			);
	}
	
	public void clean()
	{
		m.removeAll();
		bcn = m.createResource(bcnUri + "Barcelona");
		m.add(bcn, hasName, "Barcelona");
		m.add(bcn, ofClass, owlcity);
	}

	public void addDistrict(String odistrict, List<String> barrios)
	{
		String				district, barrio;
		Iterator<String>	it;
		Resource			rdfdistrict, rdfbarrio;
		
		district = normalise(odistrict);
		rdfdistrict = m.createResource(bcnUri + normaliseSpaces(district));
		m.add(rdfdistrict, hasName, district);
		m.add(rdfdistrict, ofClass, owldistrict);
		m.add(rdfdistrict, isDistrictOf, bcn);
		
		it = barrios.iterator();
		while(it.hasNext())
		{
			barrio = it.next();
			barrio = normalise(barrio);
			rdfbarrio = m.createResource(bcnUri + normaliseSpaces(barrio) + "_n");
			m.add(rdfbarrio, hasName, barrio);
			m.add(rdfbarrio, ofClass, owlbarrio);
			m.add(rdfbarrio, isNeighbourhoodOf, rdfdistrict);
		}
	}

	public static String normaliseSpaces(String st)
	{
		return st.replace(' ', '_');
	}

	public static String normalise(String st)
	{
		String	s;
		
		s = new String(st);
		s = s.trim();
		s = s.replaceAll("[èéêë]","e");
	    s = s.replaceAll("[ûùú]","u");
	    s = s.replaceAll("[ïîí]","i");
	    s = s.replaceAll("[àâá]","a");
	    s = s.replaceAll("[Ôóò]","o");
	    s = s.replaceAll("[ç]","c");

	    s = s.replaceAll("[ÈÉÊË]","E");
	    s = s.replaceAll("[ÛÙÚ]","U");
	    s = s.replaceAll("[ÏÎÍ]","I");
	    s = s.replaceAll("[ÀÂÁ]","A");
	    s = s.replaceAll("[ÔÓÒ]","O");
	    s = s.replaceAll("[Ç]","C");
	    
	    s = s.replaceAll("- ", "-");
	    s = s.replaceAll(" -", "-");
	    s = s.replaceAll("  ", " ");
	    s = s.replaceAll("\n", "");
		s = s.toLowerCase();
	
	    return s;
	}

	public void dump()
	{
		m.write(System.out, "RDF/XML");
	}

	public void getDistrict(String district)
	{
		Resource	r;
		
		r = m.getResource(bcnUri + normalise(district));
		System.out.println(r);
	}

	public Resource checkMapping(String mapping)
	{
		QuerySolution	qs;
		Resource		r;
		
		// Prepare query string
		String queryString =
		"PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n" +
		"PREFIX : <http://www.mapcelona.org/demo.owl#>\n" +
		"SELECT ?r WHERE {" +
		"?r rdf:type :DataClass.\n" +
//		"?r :hasMapping ?m" + 
//		"?r :hasMapping \"Edat mitjana edificis\"." + 
		"?r :hasMapping \"" + mapping.trim() + "\"." + 
		"}";
		// Use the ontology model to create a Dataset object
		// Note: If no reasoner has been attached to the model, no results
		// will be returned (MarriedPerson has no asserted instances)
		Dataset dataset = DatasetFactory.create(demo);
		// Parse query string and create Query object
		Query q = QueryFactory.create(queryString);
		// Execute query and obtain result set
		QueryExecution qexec = QueryExecutionFactory.create(q, dataset);
		ResultSet resultSet = qexec.execSelect();
		
		if(resultSet.hasNext())
		{
			qs = resultSet.next();
			r = qs.getResource("r");
		}
		else
		{
			throw new UnsupportedOperationException("Mapping falla: " + mapping);
		}
		
		return r;
	}
}
