package org.mapcelona.javaparser.rdfutils;

import java.io.InputStream;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;

import virtuoso.jena.driver.VirtModel;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.util.FileManager;

public class RDFUtils
{
	private ResourceBundle	rb;
	private Model			city, m;
	private Resource		bcn, owlcity, owldistrict, owlbarrio;
	private Property		ofClass, hasName, isDistrictOf, isNeighbourhoodOf;
	
	private static final String	cityUri = "http://www.mapcelona.org/city.owl#";
	private static final String	bcnUri = "http://www.mapcelona.org/barcelona.owl#";
	private static final String	rdfUri = "http://www.w3.org/1999/02/22-rdf-syntax-ns#";
	
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
		ofClass = city.getProperty(rdfUri + "type");
		owlcity = city.getResource(cityUri + "City");
		hasName = city.getProperty(cityUri + "hasName");
		owldistrict = city.getResource(cityUri + "District");
		isDistrictOf = city.getProperty(cityUri + "isDistrictOf");
		owlbarrio = city.getResource(cityUri + "Neighbourhood");
		isNeighbourhoodOf = city.getProperty(cityUri + "isNeighbourhoodOf");
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
}
