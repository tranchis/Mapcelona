package org.mapcelona.javaparser.rdf2SqlConnector;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.sql.Statement;
import java.sql.ResultSet;

import org.mapcelona.javaparser.rdfutils.RDFUtils;

import virtuoso.jena.driver.VirtModel;

import com.hp.hpl.jena.query.Dataset;
import com.hp.hpl.jena.query.DatasetFactory;
import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QueryFactory;
import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.util.FileManager;
import com.hp.hpl.jena.rdf.model.Property;

import java.util.Enumeration;

public class Rdf2SqlConnector 
{
	private ResourceBundle	rb;
	private Model			demo, city, m, barcelona;
	
	public static void main(String[] argsv)
	{
		Rdf2SqlConnector dummy = new Rdf2SqlConnector();
		//dummy.testConnection();
		//dummy.testVirtuosoConnect();
		//dummy.cleanDatabase();
		//dummy.populateDataClass();
		//dummy.populateDistrict();
		//dummy.populateNeighbourhood();
		dummy.populateValuesNeighbourhood();
		//dummy.populateValuesDistrict();
		//dummy.populateTranslations();
	}
	
	public void populateTranslations()
	{
				
		rb = ResourceBundle.getBundle("virtuoso");
		ResourceBundle rt;
		
		java.sql.Connection con = null;
		Enumeration keys;

	    try {
	      Class.forName("com.mysql.jdbc.Driver").newInstance();
	      con = DriverManager.getConnection("jdbc:mysql://" + rb.getString("SQLip") + ":3306/sergioal_mapcelona",
	        "sergioal_mapcelo", rb.getString("SQLpassword"));
	      if(!con.isClosed())
	        System.out.println("Successfully connected to " +
	          "MySQL server using TCP/IP...");
	      String translation, actualClass;
	      java.sql.Statement stmt = con.createStatement();	      	  	      
	      
	      
	      stmt.execute("set collation_connection = utf8_general_ci;");
	      stmt.execute("set character_set_client = utf8;");
	      stmt.execute("set character_set_connection = utf8;");
	      stmt.execute("set character_set_server = utf8;");
	      stmt.execute("set character_set_results = utf8;");
	      	      
	      rt = ResourceBundle.getBundle("TranslationCA");
	      keys = rt.getKeys();
	      while (keys.hasMoreElements())
	      {
	    	  actualClass = (String)keys.nextElement();
	    	  translation = rt.getString(actualClass);
	    	  translation = translation.replace("'", "''");
	    	  System.out.println("insert into _translation (language_id,dataclass_id,_value) SELECT l.id, d.id, '" + translation + "' from _language l, dataclass d where l.name='CA' and d.name='" + actualClass + "'ON DUPLICATE KEY UPDATE _value = '" + translation +"';");
	      }
	      
	      rt = ResourceBundle.getBundle("TranslationES");
	      keys = rt.getKeys();
	      while (keys.hasMoreElements())
	      {
	    	  actualClass = (String)keys.nextElement();
	    	  translation = rt.getString(actualClass);
	    	  translation = translation.replace("'", "''");
	    	  System.out.println("insert into _translation (language_id,dataclass_id,_value) SELECT l.id, d.id, '" + translation + "' from _language l, dataclass d where l.name='ES' and d.name='" + actualClass + "'ON DUPLICATE KEY UPDATE _value = '" + translation +"';");
	      }
	      	      	      	            	      	      	      	      	    
	      rt = ResourceBundle.getBundle("TranslationEN");
	      keys = rt.getKeys();
	      while (keys.hasMoreElements())
	      {
	    	  actualClass = (String)keys.nextElement();
	    	  translation = rt.getString(actualClass);
	    	  translation = translation.replace("'", "''");
	    	  System.out.println("insert into _translation (language_id,dataclass_id,_value) SELECT l.id, d.id, '" + translation + "' from _language l, dataclass d where l.name='EN' and d.name='" + actualClass + "'ON DUPLICATE KEY UPDATE _value = '" + translation +"';");
	      }	      
	      
	      
	    } catch(Exception e) {
	      System.err.println("Exception: " + e.getMessage());
	      e.printStackTrace();
	    } finally {
	      try {
	        if(con != null)
	          con.close();
	      } catch(SQLException e) {}
	    }	  
	}
	
	public void cleanDatabase()
	{
		rb = ResourceBundle.getBundle("virtuoso");
		
		java.sql.Connection con = null;

	    try {
	      Class.forName("com.mysql.jdbc.Driver").newInstance();
	      con = DriverManager.getConnection("jdbc:mysql://" + rb.getString("SQLip") + ":3306/sergioal_mapcelona",
	        "sergioal_mapcelo", rb.getString("SQLpassword"));

	      if(!con.isClosed())
	        System.out.println("Successfully connected to " +
	          "MySQL server using TCP/IP...");
	      
	      java.sql.Statement stmt = con.createStatement();
	      stmt.execute("delete from neighbourhood_value;");
	      stmt.execute("COMMIT;");
	      
	      stmt.execute("delete from district_value;");
	      stmt.execute("COMMIT;");
	      
	      stmt.execute("delete from _translation;");
	      stmt.execute("COMMIT;");
	      
	      stmt.execute("delete from datasubclass;");
	      stmt.execute("COMMIT;");
	      
	      stmt.execute("delete from dataclass;");
	      stmt.execute("COMMIT;");
	      
	      stmt.execute("delete from neighbourhood;");
	      stmt.execute("COMMIT;");
	      
	      stmt.execute("delete from district;");
	      stmt.execute("COMMIT;");
	      
	      
	    } catch(Exception e) {
	      System.err.println("Exception: " + e.getMessage());
	    } finally {
	      try {
	        if(con != null)
	          con.close();
	      } catch(SQLException e) {}
	    }	  
	}
	
	public void populateValuesDistrict()
	{
		
		/*
		RDFUtils dummy = new RDFUtils();
		dummy.dump();
		*/
		
		rb = ResourceBundle.getBundle("virtuoso");
		
		java.sql.Connection con = null;
		//Connect to SQL database
	    try 
	    {
	      Class.forName("com.mysql.jdbc.Driver").newInstance();
	      con = DriverManager.getConnection("jdbc:mysql://" + rb.getString("SQLip") + ":3306/sergioal_mapcelona",
	        "sergioal_mapcelo", rb.getString("SQLpassword"));
	      
	      
	      InputStream	in;
			
			demo = ModelFactory.createOntologyModel();
			in = FileManager.get().open("http://www.mapcelona.org/city.owl");
			if (in == null)
			{
				throw new IllegalArgumentException("File not found");
			}
			demo.read(in, null);
			
			rb = ResourceBundle.getBundle("virtuoso");
			m =	VirtModel.openDatabaseModel(
					"Barcelona", // graph name
					"jdbc:virtuoso://" + rb.getString("ip") + ":1111", //1111 is the default port
					"dba", // user
					rb.getString("password") // password
					);
			com.hp.hpl.jena.query.QuerySolution	qs;
			com.hp.hpl.jena.rdf.model.Resource		r;
			com.hp.hpl.jena.rdf.model.Resource		s;			
			
			// Prepare query string
			String queryString =
			"PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n" +
			"PREFIX demo: <http://www.mapcelona.org/demo.owl#>\n" +
			"PREFIX city: <http://www.mapcelona.org/city.owl#>\n" +	
			"SELECT ?r ?s WHERE {\n" +
			"?r rdf:type demo:PieceOfData.\n" +	
			"?s rdf:type city:District.\n" +			
			"?r demo:refersTo ?s.\n" +
			"}";
			com.hp.hpl.jena.query.Dataset dataset = DatasetFactory.create(m);
			com.hp.hpl.jena.query.Query q = QueryFactory.create(queryString);		
			com.hp.hpl.jena.query.QueryExecution qexec = QueryExecutionFactory.create(q, dataset);
			com.hp.hpl.jena.query.ResultSet resultSet = qexec.execSelect();
											
			System.out.println("--------------STARTING POPULATION OF district table");
			java.sql.Statement stmt = con.createStatement();
			Property valueProperty = demo.getProperty("http://www.mapcelona.org/demo.owl#"+"hasValue");
			Property dataClassProperty = demo.getProperty("http://www.mapcelona.org/demo.owl#"+"isOfDataClass");
			Property refersToClassProperty = demo.getProperty("http://www.mapcelona.org/city.owl#"+"hasName");
			Property hasAgeClassProperty = demo.getProperty("http://www.mapcelona.org/demo.owl#"+"hasAge");
			
			int count = 0;
			while(resultSet.hasNext())
			{
				//count ++;
				if (count == 10)
				{
					break;
				}
				qs = resultSet.next();
				r = qs.getResource("r");
				s = qs.getResource("s");
				String ValueResult = r.getProperty(valueProperty).asTriple().getObject().getLiteralValue().toString();				
				String PropertyResult = r.getProperty(dataClassProperty).asTriple().getObject().getLocalName();
				String refersToResult = s.getProperty(refersToClassProperty).asTriple().getObject().getLiteralValue().toString();	
				String ageResult = r.getProperty(hasAgeClassProperty).asTriple().getObject().getLiteralValue().toString();
				
				System.out.println("	- Inserting: '" + ValueResult + "' '" + PropertyResult + "' '" + refersToResult + "' "+ "' " + ageResult + "'");
				//System.out.println("insert into district_value (district_id,dataclass_id,age,_value) SELECT n.id, d.id, '" + ageResult + "', '" + ValueResult + "' from district n, dataclass d where n.name = '" + refersToResult + "' and d.name='" + PropertyResult + "';");
				stmt.execute("insert into district_value (district_id,dataclass_id,age,_value) SELECT n.id, d.id, '" + ageResult + "', '" + ValueResult + "' from district n, dataclass d where n.name = '" + refersToResult + "' and d.name='" + PropertyResult + "' ON DUPLICATE KEY UPDATE age='" + ageResult + "', _value ='" + ValueResult + "';");
				
			}	
			System.out.println("--------------ENDING POPULATION OF dataclass table");
			stmt.execute("COMMIT;");
	      
	      	//It is over
		    con.close();
		    m.close();
		    
		    
	    } catch(Exception e) 
	    {
		      System.err.println("Exception: " + e.getMessage());
		      e.printStackTrace();
	    }
	}
	
	public void populateValuesNeighbourhood()
	{		
		
		/*
		RDFUtils dummy = new RDFUtils();
		dummy.dump();
		*/
		
		rb = ResourceBundle.getBundle("virtuoso");
		
		java.sql.Connection con = null;
		//Connect to SQL database
	    try 
	    {
	      Class.forName("com.mysql.jdbc.Driver").newInstance();
	      con = DriverManager.getConnection("jdbc:mysql://" + rb.getString("SQLip") + ":3306/sergioal_mapcelona",
	        "sergioal_mapcelo", rb.getString("SQLpassword"));
	      
	      
	      InputStream	in;
			
			demo = ModelFactory.createOntologyModel();
			in = FileManager.get().open("http://www.mapcelona.org/city.owl");
			if (in == null)
			{
				throw new IllegalArgumentException("File not found");
			}
			demo.read(in, null);
			
			rb = ResourceBundle.getBundle("virtuoso");
			m =	VirtModel.openDatabaseModel(
					"Barcelona", // graph name
					"jdbc:virtuoso://" + rb.getString("ip") + ":1111", //1111 is the default port
					"dba", // user
					rb.getString("password") // password
					);
			com.hp.hpl.jena.query.QuerySolution	qs;
			com.hp.hpl.jena.rdf.model.Resource		r;
			com.hp.hpl.jena.rdf.model.Resource		s;
			com.hp.hpl.jena.rdf.model.Resource		t;	
			// Prepare query string
			String queryString =
			"PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n" +
			"PREFIX demo: <http://www.mapcelona.org/demo.owl#>\n" +
			"PREFIX city: <http://www.mapcelona.org/city.owl#>\n" +	
			"PREFIX : <http://www.mapcelona.org/barcelona.owl#>\n" +	
			"SELECT ?r ?s WHERE {\n" +
			"?r rdf:type demo:PieceOfData.\n" +	
			"?s rdf:type city:Neighbourhood.\n" +			
			"?r demo:refersTo ?s.\n" +			
			"}";
			com.hp.hpl.jena.query.Dataset dataset = DatasetFactory.create(m);
			com.hp.hpl.jena.query.Query q = QueryFactory.create(queryString);		
			com.hp.hpl.jena.query.QueryExecution qexec = QueryExecutionFactory.create(q, dataset);
			com.hp.hpl.jena.query.ResultSet resultSet = qexec.execSelect();
											
			System.out.println("--------------STARTING POPULATION OF district table");
			java.sql.Statement stmt = con.createStatement();
			Property valueProperty = demo.getProperty("http://www.mapcelona.org/demo.owl#"+"hasValue");
			Property dataClassProperty = demo.getProperty("http://www.mapcelona.org/demo.owl#"+"isOfDataClass");
			Property refersToClassProperty = demo.getProperty("http://www.mapcelona.org/city.owl#"+"hasName");
			Property hasAgeClassProperty = demo.getProperty("http://www.mapcelona.org/demo.owl#"+"hasAge");
			
			int count = 0;
			while(resultSet.hasNext())
			{
				//count ++;
				if (count == 10)
				{
					break;
				}
				qs = resultSet.next();
				r = qs.getResource("r");
				s = qs.getResource("s");				
				String ValueResult = r.getProperty(valueProperty).asTriple().getObject().getLiteralValue().toString();				
				String PropertyResult = r.getProperty(dataClassProperty).asTriple().getObject().getLocalName();
				String refersToResult = s.getProperty(refersToClassProperty).asTriple().getObject().getLiteralValue().toString();				
				String ageResult = r.getProperty(hasAgeClassProperty).asTriple().getObject().getLiteralValue().toString();
				refersToResult = refersToResult.replace("'", "''");
				System.out.println("	- Inserting: '" + ValueResult + "' '" + PropertyResult + "' '" + refersToResult + "' "+ "' " + ageResult + "'");
				stmt.execute("insert into neighbourhood_value (neighbourhood_id,dataclass_id,_value,age) SELECT n.id, d.id, '" + ValueResult + "', '" + ageResult + "' from neighbourhood n, dataclass d where n.name = '" + refersToResult + "' and d.name='" + PropertyResult + "' ON DUPLICATE KEY UPDATE age='" + ageResult + "', _value ='" + ValueResult + "';");
				//System.out.println("insert into neighbourhood_value (neighbourhood_id,dataclass_id,_value,age) SELECT n.id, d.id, '" + ValueResult + "', '" + ageResult + "' from neighbourhood n, dataclass d where n.name = '" + refersToResult + "' and d.name='" + PropertyResult + "' ON DUPLICATE KEY UPDATE age='" + ageResult + "', _value ='" + ValueResult + "';");
				
			}	
			System.out.println("--------------ENDING POPULATION OF dataclass table");
			stmt.execute("COMMIT;");
	      
	      	//It is over
		    con.close();
		    m.close();
		    
		    
	    } catch(Exception e) 
	    {
		      System.err.println("Exception: " + e.getMessage());
	    }
	    
	}
	
	public void populateNeighbourhood()
	{
		
		/*
		RDFUtils dummy = new RDFUtils();
		dummy.dump();
		*/
		
		rb = ResourceBundle.getBundle("virtuoso");
		
		java.sql.Connection con = null;
		//Connect to SQL database
	    try 
	    {
	      Class.forName("com.mysql.jdbc.Driver").newInstance();
	      con = DriverManager.getConnection("jdbc:mysql://" + rb.getString("SQLip") + ":3306/sergioal_mapcelona",
	        "sergioal_mapcelo", rb.getString("SQLpassword"));
	      
	      
	      InputStream	in;
			
			demo = ModelFactory.createOntologyModel();
			in = FileManager.get().open("http://www.mapcelona.org/city.owl");
			if (in == null)
			{
				throw new IllegalArgumentException("File not found");
			}
			demo.read(in, null);					
			
			rb = ResourceBundle.getBundle("virtuoso");
			m =	VirtModel.openDatabaseModel(
					"Barcelona", // graph name
					"jdbc:virtuoso://" + rb.getString("ip") + ":1111", //1111 is the default port
					"dba", // user
					rb.getString("password") // password
					);
			com.hp.hpl.jena.query.QuerySolution	qs;
			com.hp.hpl.jena.rdf.model.Resource		r;			
			// Prepare query string
			String queryString =
			"PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n" +
			"PREFIX city: <http://www.mapcelona.org/city.owl#>\n" +
			"PREFIX : <http://www.mapcelona.org/barcelona.owl#>\n" +
			"SELECT ?r ?s WHERE {" +
			"?r rdf:type city:Neighbourhood.\n" +			
			"?r city:isNeighbourhoodOf ?s" +
			"}";
			com.hp.hpl.jena.query.Dataset dataset = DatasetFactory.create(m);
			com.hp.hpl.jena.query.Query q = QueryFactory.create(queryString);		
			com.hp.hpl.jena.query.QueryExecution qexec = QueryExecutionFactory.create(q, dataset);
			com.hp.hpl.jena.query.ResultSet resultSet = qexec.execSelect();
			//java.sql.Statement stmt = con.createStatement();								
			System.out.println("--------------STARTING POPULATION OF district table");
			java.sql.Statement stmt = con.createStatement();
			Property resultProperty = demo.getProperty("http://www.mapcelona.org/city.owl#"+"isNeighbourhoodOf");
			Property nameProperty = demo.getProperty("http://www.mapcelona.org/city.owl#"+"hasName");
			while(resultSet.hasNext())
			{
				qs = resultSet.next();
				r = qs.getResource("r");
				Resource s = qs.getResource("s");
				
				String result = r.getProperty(nameProperty).asTriple().getObject().getLiteralValue().toString();
				String result2 = s.getProperty(nameProperty).asTriple().getObject().getLiteralValue().toString();
				System.out.println("	- Inserting: '" + result + "' '" + result2 + "'" );
				//Statement
				result = result.replace("'", "''");				
				stmt.execute("insert into neighbourhood (district_id,name) SELECT d.id, '" + result + "' from district d where d.name = '" + result2 + "';");	   				
			}	
			System.out.println("--------------ENDING POPULATION OF dataclass table");
			stmt.execute("COMMIT;");
	      
	      	//It is over
		    con.close();
		    m.close();
		    
		    
	    } catch(Exception e) 
	    {
		      System.err.println("Exception: " + e.getMessage());
	    }
	}	
	
	public void populateDistrict()
	{
		rb = ResourceBundle.getBundle("virtuoso");
		
		java.sql.Connection con = null;
		//Connect to SQL database
	    try 
	    {
	      Class.forName("com.mysql.jdbc.Driver").newInstance();
	      con = DriverManager.getConnection("jdbc:mysql://" + rb.getString("SQLip") + ":3306/sergioal_mapcelona",
	        "sergioal_mapcelo", rb.getString("SQLpassword"));
	      
	      
	      InputStream	in;
			
			demo = ModelFactory.createOntologyModel();
			in = FileManager.get().open("http://www.mapcelona.org/demo.owl");
			if (in == null)
			{
				throw new IllegalArgumentException("File not found");
			}
			demo.read(in, null);
			
			rb = ResourceBundle.getBundle("virtuoso");
			m =	VirtModel.openDatabaseModel(
					"Barcelona", // graph name
					"jdbc:virtuoso://" + rb.getString("ip") + ":1111", //1111 is the default port
					"dba", // user
					rb.getString("password") // password
					);
			com.hp.hpl.jena.query.QuerySolution	qs;
			com.hp.hpl.jena.rdf.model.Resource		r;			
			// Prepare query string
			String queryString =
			"PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n" +
			"PREFIX city: <http://www.mapcelona.org/city.owl#>\n" +
			"PREFIX : <http://www.mapcelona.org/demo.owl#>\n" +
			"SELECT ?r WHERE {" +
			"?r rdf:type city:District.\n" +
			"}";
			com.hp.hpl.jena.query.Dataset dataset = DatasetFactory.create(m);
			com.hp.hpl.jena.query.Query q = QueryFactory.create(queryString);		
			com.hp.hpl.jena.query.QueryExecution qexec = QueryExecutionFactory.create(q, dataset);
			com.hp.hpl.jena.query.ResultSet resultSet = qexec.execSelect();
			//java.sql.Statement stmt = con.createStatement();								
			System.out.println("--------------STARTING POPULATION OF district table");
			java.sql.Statement stmt = con.createStatement();
			Property nameProperty = demo.getProperty("http://www.mapcelona.org/city.owl#"+"hasName");
			while(resultSet.hasNext())
			{
				qs = resultSet.next();
				r = qs.getResource("r");
				String result = r.getProperty(nameProperty).asTriple().getObject().getLiteralValue().toString();	
				System.out.println("	- Inserting: '" + result + "'");
				//Statement
				stmt.execute("insert into district (city_id,name) SELECT c.ID, '" + result + "' from city c where c.name = 'Barcelona';");	   
				
			}	
			System.out.println("--------------ENDING POPULATION OF dataclass table");
			stmt.execute("COMMIT;");
	      
	      	//It is over
		    con.close();
		    m.close();
		    
		    
	    } catch(Exception e) 
	    {
		      System.err.println("Exception: " + e.getMessage());
	    }
	}
	
	public void populateDataClass()
	{
		rb = ResourceBundle.getBundle("virtuoso");
		
		java.sql.Connection con = null;
		//Connect to SQL database
	    try 
	    {
	      Class.forName("com.mysql.jdbc.Driver").newInstance();
	      con = DriverManager.getConnection("jdbc:mysql://" + rb.getString("SQLip") + ":3306/sergioal_mapcelona",
	        "sergioal_mapcelo", rb.getString("SQLpassword"));
	      
	      
	      InputStream	in;
			
			demo = ModelFactory.createOntologyModel();
			in = FileManager.get().open("http://www.mapcelona.org/demo.owl");
			if (in == null)
			{
				throw new IllegalArgumentException("File not found");
			}
			demo.read(in, null);
			
			rb = ResourceBundle.getBundle("virtuoso");
			m =	VirtModel.openDatabaseModel(
					"Barcelona", // graph name
					"jdbc:virtuoso://" + rb.getString("ip") + ":1111", //1111 is the default port
					"dba", // user
					rb.getString("password") // password
					);
			com.hp.hpl.jena.query.QuerySolution	qs;
			com.hp.hpl.jena.rdf.model.Resource		r;
			
			// Prepare query string
			String queryString =
			"PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n" +
			"PREFIX : <http://www.mapcelona.org/demo.owl#>\n" +
			"SELECT ?r WHERE {" +
			"?r rdf:type :DataClass.\n" +
			"}";
			com.hp.hpl.jena.query.Dataset dataset = DatasetFactory.create(demo);
			com.hp.hpl.jena.query.Query q = QueryFactory.create(queryString);		
			com.hp.hpl.jena.query.QueryExecution qexec = QueryExecutionFactory.create(q, dataset);
			com.hp.hpl.jena.query.ResultSet resultSet = qexec.execSelect();
			java.sql.Statement stmt = con.createStatement();								
			System.out.println("--------------STARTING POPULATION OF dataclass table");			
			Property directionProperty = demo.getProperty("http://www.mapcelona.org/demo.owl#"+"isPositive");
			while(resultSet.hasNext())
			{
				qs = resultSet.next();
				r = qs.getResource("r");		
				String resultPositive = "neutral";
				String result = r.toString().split("#")[1];
				try
				{
					resultPositive = r.getProperty(directionProperty).asTriple().getObject().getLiteralValue().toString();	
					if (resultPositive.equalsIgnoreCase("true"))
					{
						resultPositive = "positive";
					}
					else if (resultPositive.equalsIgnoreCase("false"))
					{
						resultPositive = "negative";
					}
					else
					{
						resultPositive = "neutral";
					}
				}
				catch (Exception e)
				{
					
				}
				System.out.println("	- Inserting: '" + result + "' '" + resultPositive + "'");
				
				stmt.execute("insert into dataclass (name,direction) values('" + result + "','" + resultPositive + "') ON DUPLICATE KEY UPDATE name = '" + result + "', direction = '" + resultPositive + "' ;");			    
				
			}	
			System.out.println("--------------ENDING POPULATION OF dataclass table");
			stmt.execute("COMMIT;");
	      
	      	//It is over
		    con.close();
		    m.close();
		    
		    
	    } catch(Exception e) 
	    {
		      System.err.println("Exception: " + e.getMessage());
	    }
	    
	    
	    
	    
	      
	}
	
	public void testSQLConnection()
	{
		SQLconnect();
		
	}
	
	public void SQLconnect()
	{
		rb = ResourceBundle.getBundle("virtuoso");
		
		java.sql.Connection con = null;

	    try {
	      Class.forName("com.mysql.jdbc.Driver").newInstance();
	      con = DriverManager.getConnection("jdbc:mysql://" + rb.getString("SQLip") + ":3306/sergioal_mapcelona",
	        "sergioal_mapcelo", rb.getString("SQLpassword"));

	      if(!con.isClosed())
	        System.out.println("Successfully connected to " +
	          "MySQL server using TCP/IP...");
	      
	      java.sql.Statement stmt = con.createStatement();
	      stmt.execute("Insert into city (name) values ('Chikitistan del sur');");
	      stmt.execute("COMMIT;");
	      java.sql.ResultSet rs = stmt.executeQuery("Select * from city;");
	      
	      while (rs.next())
	      {
	    	  String result = rs.getString("name");
	    	  System.out.println("-->" + result);
	      }
	      
	      System.out.println("A tomar por culo");
	      
	    } catch(Exception e) {
	      System.err.println("Exception: " + e.getMessage());
	    } finally {
	      try {
	        if(con != null)
	          con.close();
	      } catch(SQLException e) {}
	    }	  

	}
	
	public void testVirtuosoConnect()
	{
		InputStream	in;
		
		demo = ModelFactory.createOntologyModel();
		in = FileManager.get().open("http://www.mapcelona.org/demo.owl");
		if (in == null)
		{
			throw new IllegalArgumentException("File not found");
		}
		demo.read(in, null);
		
		rb = ResourceBundle.getBundle("virtuoso");
		m =	VirtModel.openDatabaseModel(
				"Barcelona", // graph name
				"jdbc:virtuoso://" + rb.getString("Virtuosoip") + ":1111", //1111 is the default port
				"dba", // user
				rb.getString("Virtuosopassword") // password
				);
		com.hp.hpl.jena.query.QuerySolution	qs;
		com.hp.hpl.jena.rdf.model.Resource		r;
		
		// Prepare query string
		String queryString =
		"PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n" +
		"PREFIX : <http://www.mapcelona.org/demo.owl#>\n" +
		"SELECT ?r WHERE {" +
		"?r rdf:type :DataClass.\n" +
//		"?r :hasMapping ?m" + 
//		"?r :hasMapping \"Edat mitjana edificis\"." + 
		"}";
		// Use the ontology model to create a Dataset object
		// Note: If no reasoner has been attached to the model, no results
		// will be returned (MarriedPerson has no asserted instances)
		com.hp.hpl.jena.query.Dataset dataset = DatasetFactory.create(demo);
		// Parse query string and create Query object
		com.hp.hpl.jena.query.Query q = QueryFactory.create(queryString);
		// Execute query and obtain result set
		com.hp.hpl.jena.query.QueryExecution qexec = QueryExecutionFactory.create(q, dataset);
		com.hp.hpl.jena.query.ResultSet resultSet = qexec.execSelect();
		
		while(resultSet.hasNext())
		{
			qs = resultSet.next();
			r = qs.getResource("r");
			System.out.println("---->" + r.toString());
		}		
		m.close();
	}
	
}
