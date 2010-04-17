package org.mapcelona.javaparser.pdfparser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

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
		File			temp;
		ThExtract		te;
		FileInputStream	fis;
		BufferedReader	br;
		String			line, district, b;
		List<String>	barrios;
		RDFUtils		ru;
		Resource		r;
		int				i;
		
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
			r = ru.checkMapping(line.substring(0, line.indexOf("20") - 1).replace("Þ", "fi"));
			
			i = barrios.size() - 1;
			while(i >= 0)
			{
				System.out.println(barrios.get(i));
				i = i - 1;
			}
			
			line = br.readLine();
		}
	}
}
