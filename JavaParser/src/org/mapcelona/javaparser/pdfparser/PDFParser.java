package org.mapcelona.javaparser.pdfparser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;
import java.util.StringTokenizer;

import org.mapcelona.javaparser.rdfutils.RDFUtils;

import com.hp.hpl.jena.rdf.model.Resource;

public class PDFParser
{
	private String		name;

	public PDFParser(String name)
	{
		this.name = name;
	}

	public static void main(String args[]) throws Exception
	{
		File				temp;
		ThExtract			te;
		FileInputStream		fis;
		BufferedReader		br;
		String				line, district, b, data;
		float				value;
		List<String>		barrios;
		RDFUtils			ru;
		Resource			r, dataPiece;
		int					i, year;
		StringTokenizer		st;
		Stack<String>		pila;
		List<Resource>		pieces;
		Iterator<Resource>	it;
		
		ru = new RDFUtils();
		
//		te = new ThExtract("http://www.bcn.es/estadistica/catala/dades/inf/guies/dte01.pdf");
		te = new ThExtract("/Users/sergio/Desktop/dte01.pdf");
		te.start();
		te.join();
		temp = te.getFile();
		
		fis = new FileInputStream(temp);
		br = new BufferedReader(new InputStreamReader(fis));
		
		line = br.readLine();
		while(!line.startsWith("RESUM DÕINDICADORS, GRËFICS I NOTES"))
		{
			line = br.readLine();
		}
		
		br.readLine(); // 13
		district = br.readLine(); //
		System.out.println("[" + RDFUtils.normalise(district) + "]");
		
		barrios = new ArrayList<String>();
		
		line = br.readLine();
		while(!line.startsWith("ZEG"))
		{
			line = br.readLine();
		}
		
		while(line.startsWith("ZEG"))
		{
			line = br.readLine(); // Nombre de barrio
			b = line;
			line = br.readLine();
			while(!line.startsWith("ZEG") && !line.startsWith("Superf"))
			{
				b = b + line;
				line = br.readLine();
			}
			barrios.add(RDFUtils.normalise(b));
		}
		
		System.out.println(barrios);
		
		while(line != null)
		{
			pieces = new LinkedList<Resource>();
			if(line.startsWith("% alumnes centres pœblics 2003-2004 37,1 54,6 - - - - -"))
			{
				line = line.replace("% alumnes centres pœblics 2003-2004 37,1 54,6 - - - - -",
						"% alumnes centres pœblics 2003-2004 37,1 54,6 - - - - - -");
			}
			r = ru.checkMapping(line.substring(0, line.indexOf("20") - 1).replace("Þ", "fi"));
			
			st = new StringTokenizer(line);
			pila = new Stack<String>();
			
			while(st.hasMoreTokens())
			{
				pila.push(st.nextToken());
			}
			
			i = barrios.size() - 1;
			while(i >= 0)
			{
				data = pila.pop();
				if(!data.equals("-"))
				{
					value = Float.parseFloat(data.replace(".", "").replace(",", "."));
					System.out.println(barrios.get(i) + ":" + value);
					dataPiece = ru.addDataPiece(barrios.get(i), r, value);
					pieces.add(dataPiece);
				}
								
				i = i - 1;
			}
			
			data = pila.pop(); // Distrito
			value = Float.parseFloat(data.replace(".", "").replace(",", "."));
			System.out.println(district + ":" + value);
			dataPiece = ru.addDataPiece(district, r, value);
			
			pila.pop(); // Barcelona
			data = pila.pop(); // Year
			if(data.indexOf('-') > -1)
			{
				year = Integer.parseInt(data.substring(data.lastIndexOf("-") + 1));
			}
			else
			{
				year = Integer.parseInt(data);
			}
			
			it = pieces.iterator();
			while(it.hasNext())
			{
				ru.setDataPieceYear(it.next(), year);
			}
			
			System.out.println(r.getLocalName() + ":" + year);
			
			line = br.readLine();
		}
		
		ru.dump();
	}
}
