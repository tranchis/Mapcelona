/*
 *  $Id: VirtDataSource.java,v 1.10 2010/03/01 09:16:57 source Exp $
 *
 *  This file is part of the OpenLink Software Virtuoso Open-Source (VOS)
 *  project.
 *
 *  Copyright (C) 1998-2008 OpenLink Software
 *
 *  This project is free software; you can redistribute it and/or modify it
 *  under the terms of the GNU General Public License as published by the
 *  Free Software Foundation; only version 2 of the License, dated June 1991.
 *
 *  This program is distributed in the hope that it will be useful, but
 *  WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 *  General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License along
 *  with this program; if not, write to the Free Software Foundation, Inc.,
 *  51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA
 *
 */
package virtuoso.jena.driver;


import java.sql.ResultSet;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import com.hp.hpl.jena.graph.Graph;
import com.hp.hpl.jena.graph.Node;
import com.hp.hpl.jena.graph.Triple;
import com.hp.hpl.jena.query.DataSource;
import com.hp.hpl.jena.query.Dataset;
import com.hp.hpl.jena.query.LabelExistsException;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.shared.JenaException;
import com.hp.hpl.jena.shared.Lock;
import com.hp.hpl.jena.sparql.core.DatasetGraph;

public class VirtDataSource extends VirtGraph implements DataSource, Dataset {

    /**
     * Default model - may be null - according to Javadoc
     */
    Model defaultModel = null;


    public VirtDataSource()
    {
	super();
    }

    protected VirtDataSource(VirtGraph g)
    {
	super();
        this.graphName = g.getGraphName();
        setReadFromAllGraphs(g.getReadFromAllGraphs());
        this.url_hostlist = g.getGraphUrl();
    	this.user = g.getGraphUser();
    	this.password = g.getGraphPassword();
    	this.roundrobin = g.roundrobin;
        setFetchSize(g.getFetchSize());
        this.connection = g.getConnection();
    }

    public VirtDataSource(String url_hostlist, String user, String password)
    {
	super(url_hostlist, user, password);
    }

    /** Set the background graph.  Can be set to null for none.  */
    public void setDefaultModel(Model model) {
        defaultModel = model;
    }

    /** Set a named graph. */
    public void addNamedModel(String name, Model model)
			throws LabelExistsException {
	String query = "select count(*) from (sparql select * where { graph `iri(??)` { ?s ?p ?o }})f";
 	ResultSet rs = null;
	int ret = 0;

        checkOpen();
        try {
  	    java.sql.PreparedStatement ps = getConnection().prepareStatement(query);
  	    ps.setString(1, name);
	    rs = ps.executeQuery();
	    rs.next();
	    ret = rs.getInt(1);
	    rs.close();
	} catch (Exception e) {
	    throw new JenaException(e);
        }

	try {
	    if (ret != 0)
	        throw new LabelExistsException("A model with ID '" + name
					+ "' already exists.");
 	    Graph g = model.getGraph();
 	    int count = 0;
            java.sql.PreparedStatement ps = getConnection().prepareStatement(sinsert);

	    for (Iterator i = g.find(Node.ANY, Node.ANY, Node.ANY); i.hasNext();) 
	        {
	            Triple t = (Triple)i.next();

	            ps.setString(1, name);
        	    bindSubject(ps, 2, t.getSubject());
	            bindPredicate(ps, 3, t.getPredicate());
	            bindObject(ps, 4, t.getObject());
	            ps.addBatch();
	            count++;
	            if (count > BATCH_SIZE) {
	              ps.executeBatch();
	              ps.clearBatch();
	              count = 0;
	            }
	        }
	     if (count > 0) {
	         ps.executeBatch();
	         ps.clearBatch();
	     }
	} catch (Exception e) {
	    throw new JenaException(e);
	}
    }


    /** Remove a named graph. */
    public void removeNamedModel(String name) {
	    String exec_text ="sparql clear graph <"+ name + ">";

	checkOpen();
	try {
	    java.sql.Statement stmt = getConnection().createStatement();
	    stmt.executeQuery(exec_text);
	} catch (Exception e) {
		throw new JenaException(e);
	}
    }


    /** Change a named graph for another uisng the same name */
    public void replaceNamedModel(String name, Model model) {
	try {
	   getConnection().setAutoCommit(false);
	   removeNamedModel(name);
	   addNamedModel(name, model);
	   getConnection().commit();
	   getConnection().setAutoCommit(true);
	} catch (Exception e) {
 	    try {
		getConnection().rollback();
	    } catch (Exception e2) {
		throw new JenaException(
			"Could not replace model, and could not rollback!", e2);
	    }
	    throw new JenaException("Could not replace model:", e);
	}
    }


    /** Get the default graph as a Jena Model */
    public Model getDefaultModel() {
	return defaultModel;
    }


    /** Get a graph by name as a Jena Model */
    public Model getNamedModel(String name) {
	try {
		return new VirtModel(new VirtGraph(name, this.getGraphUrl(), 
			this.getGraphUser(), this.getGraphPassword()));
	} catch (Exception e) {
		throw new JenaException(e);
	}
    }


    /** Does the dataset contain a model with the name supplied? */ 
    public boolean containsNamedModel(String name) {
	String query = "select count(*) from (sparql select * where { graph `iri(??)` { ?s ?p ?o }})f";
 	ResultSet rs = null;
	int ret = 0;

        checkOpen();
        try {
  	    java.sql.PreparedStatement ps = getConnection().prepareStatement(query);
  	    ps.setString(1, name);
	    rs = ps.executeQuery();
	    rs.next();
	    ret = rs.getInt(1);
	    rs.close();
	} catch (Exception e) {
	    throw new JenaException(e);
        }

	return (ret!=0);
    }


    /** List the names */
    public Iterator<String> listNames() {
        String exec_text = "DB.DBA.SPARQL_SELECT_KNOWN_GRAPHS()";
 	ResultSet rs = null;
	int ret = 0;

        checkOpen();
        try {
	    List<String> names=new Vector(); 

  	    java.sql.Statement stmt = getConnection().createStatement();
	    rs = stmt.executeQuery(exec_text);
	    while(rs.next())
	        names.add(rs.getString(1));
	    return names.iterator();
	} catch (Exception e) {
	    throw new JenaException(e);
        }
    }


    Lock lock = null ;
    /** Get the lock for this dataset */
    public Lock getLock() {
        if (lock == null)
          lock = new com.hp.hpl.jena.shared.LockNone();
        return lock;
    }


    /** Get the dataset in graph form */
    public DatasetGraph asDatasetGraph() {
       	return null;
    }

}