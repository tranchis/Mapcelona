package org.mapcelona.javaparser.pdfparser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Stack;
import java.util.StringTokenizer;

import org.mapcelona.javaparser.rdfutils.RDFUtils;

public class UrbanaParser
{
	private RDFUtils		ru;
	private static String[]	districts = {
		"ciutat vella",
		"eixample",
		"sants-montjuic",
		"les corts",
		"sarria-sant gervasi",
		"gracia",
		"horta-guinardo",
		"nou barris",
		"sant andreu",
		"sant marti"
	};
	
	/**
	 * @param args
	 * @throws InterruptedException 
	 * @throws IOException 
	 */
	public static void main(String[] args) throws InterruptedException, IOException
	{
		UrbanaParser	up;
		
		up = new UrbanaParser();
		up.execute("http://www.bcn.es/estadistica/catala/dades/anuari/pdf/capitol08.pdf");
	}

	private void execute(String url) throws InterruptedException, IOException
	{	
		ru = new RDFUtils();
		
		extractBomberos(url, 5, "Fora terme municipal");
		extractTabla(url, 9, "TOTAL 3.739 231 138", "(1) Unitats amb àmbit territorial Barcelona.", 4);
		extractTabla(url, 10, "TOTAL 391.043 35.228 29.008 25.270", "Mitjans tècnics (2) 273.708", 5);
		extractAlcoholemia(url, 11, "Tipus  BARCELONA Vella Eixample Montjuïc Corts S.Gervasi Gràcia Guinardó  Barris Andreu Martí districte trànsit diürna(1) nocturna(1)", 4);
		extractTabla(url, 12, "3.535", "àmbit", 4);
		extractTabla(url, 13, "111.160", "àmbit", 4);
		extractTabla(url, 14, "Horta-", "comissaria", 4);
		extractTabla(url, 15, "Horta-", "àmbit territoria", 5);
		extractEvolucion(url, 16, "victimització: percentatge de població", false, 2008);
		extractEvolucion(url, 18, "tica. Ajunta", true, 2009);
	}

	private void extractEvolucion(String url, int page, String endmark, boolean iniciales, int lastyear) throws IOException, InterruptedException
	{
		File				temp;
		ThExtract			te;
		FileInputStream		fis;
		BufferedReader		br;
		String				line;
		StringTokenizer		st;
		Stack<String>		pila;
		float				value;
		String				district;
		int					i, year;
		
		te = new ThExtract(url, page);
		te.start();
		te.join();
		temp = te.getFile();
		
		fis = new FileInputStream(temp);
		br = new BufferedReader(new InputStreamReader(fis));
		
		line = br.readLine();
		while(!line.contains("BARCELONA"))
		{
			line = br.readLine();
		}
		
		br.readLine(); // Vacio
		line = br.readLine();
		
		while(!line.contains(endmark))
		{
			System.out.println(line);
			st = new StringTokenizer(line);
			pila = new Stack<String>();
			while(st.hasMoreTokens())
			{
				pila.push(st.nextToken());
			}
			
			if(iniciales)
			{
				for(i=0;i<5;i++)
				{
					pila.pop();
				}
			}
			
			year = lastyear;
			for(i=0;i<5;i++)
			{
				value = Float.parseFloat(pila.pop().replace(".", "").replace(",", "."));
				System.out.println(year + ":" + value + "");
				year = year - 1;
			}
			
			if(!iniciales)
			{
				for(i=0;i<5;i++)
				{
					pila.pop();
				}
			}
			
			pila.remove(0);
			district = pila.pop();
			while(!pila.isEmpty())
			{
				district = pila.pop() + " " + district;
			}
			System.out.println(RDFUtils.normalise(district));
			
			line = br.readLine();
		}
	}

	private void extractTest(String url, int page) throws IOException, InterruptedException
	{
		File				temp;
		ThExtract			te;
		FileInputStream		fis;
		BufferedReader		br;
		String				line;
		StringTokenizer		st;
		Stack<String>		pila;
		float				value;
		String				district, concept;
		int					i;
		
		te = new ThExtract(url, page);
		te.start();
		te.join();
		temp = te.getFile();
		System.out.println(temp.getAbsolutePath());
	}

	private void extractAlcoholemia(String url, int page, String match, int extras) throws IOException, InterruptedException
	{
		File				temp;
		ThExtract			te;
		FileInputStream		fis;
		BufferedReader		br;
		String				line;
		StringTokenizer		st;
		Stack<String>		pila;
		float				value;
		String				district, concept;
		int					i;
		
		te = new ThExtract(url, page);
		te.start();
		te.join();
		temp = te.getFile();
		System.out.println(temp.getAbsolutePath());
		
		fis = new FileInputStream(temp);
		br = new BufferedReader(new InputStreamReader(fis));
		
		line = br.readLine();
		while(!line.contains(match))
		{
			line = br.readLine();
		}
		
		br.readLine(); // Vacio
		line = br.readLine();
		
		System.out.println(line);
		st = new StringTokenizer(line);
		pila = new Stack<String>();
		while(st.hasMoreTokens())
		{
			pila.push(st.nextToken());
		}
		
		for(i=0;i<extras;i++)
		{
			pila.pop(); // nocturna, etc...
		}
		
		for(i=9;i>=0;i--)
		{
			value = Float.parseFloat(pila.pop().replace(".", ""));
			System.out.println(districts[i] + ":" + value + "");
		}
		
		concept = "SecurityAlcoholTests";
		System.out.println(concept);
	}

	private void extractTabla(String url, int page, String match, String endmark, int extras) throws IOException, InterruptedException
	{
		File				temp;
		ThExtract			te;
		FileInputStream		fis;
		BufferedReader		br;
		String				line;
		StringTokenizer		st;
		Stack<String>		pila;
		float				value;
		String				district, concept;
		int					i;
		
		te = new ThExtract(url, page);
		te.start();
		te.join();
		temp = te.getFile();

		fis = new FileInputStream(temp);
		br = new BufferedReader(new InputStreamReader(fis));
		
		line = br.readLine();
		while(!line.contains(match))
		{
			line = br.readLine();
		}
		
		br.readLine(); // Vacio
		line = br.readLine();
		
		while(!line.contains(endmark))
		{
			if(line.length() > "Ord. sobre obres, instal·lacions  ".length())
			{
				System.out.println(line);
				st = new StringTokenizer(line);
				pila = new Stack<String>();
				while(st.hasMoreTokens())
				{
					pila.push(st.nextToken());
				}
				
				for(i=0;i<extras;i++)
				{
					pila.pop(); // nocturna, etc...
				}
				
				for(i=9;i>=0;i--)
				{
					value = Float.parseFloat(pila.pop().replace(".", ""));
					System.out.println(districts[i] + ":" + value + "");
				}
				
				pila.pop(); // Barcelona
				concept = pila.pop();
				while(!pila.isEmpty())
				{
					concept = pila.pop() + " " + concept;
				}
				System.out.println(concept);
			}
			
			line = br.readLine();
		}
	}

	private void extractBomberos(String url, int page, String endmark) throws IOException, InterruptedException
	{
		File				temp;
		ThExtract			te;
		FileInputStream		fis;
		BufferedReader		br;
		String				line;
		StringTokenizer		st;
		Stack<String>		pila;
		float				value;
		String				district;
		
		te = new ThExtract(url, page);
		te.start();
		te.join();
		temp = te.getFile();
		
		fis = new FileInputStream(temp);
		br = new BufferedReader(new InputStreamReader(fis));
		
		line = br.readLine();
		while(!line.contains("BARCELONA"))
		{
			line = br.readLine();
		}
		
		br.readLine();
		line = br.readLine();
		
		while(!line.contains(endmark))
		{
			System.out.println(line);
			st = new StringTokenizer(line);
			pila = new Stack<String>();
			while(st.hasMoreTokens())
			{
				pila.push(st.nextToken());
			}
			
			pila.pop(); // Assistencia tecnica
			pila.pop(); // Salvament
			pila.pop(); // Incendi/explosió
			pila.pop(); // Falsa alarma
			
			value = Float.parseFloat(pila.pop().replace(".", ""));
			System.out.println(value + "");
			
			pila.remove(0);
			district = pila.pop();
			while(!pila.isEmpty())
			{
				district = pila.pop() + " " + district;
			}
			System.out.println(RDFUtils.normalise(district));
			
			line = br.readLine();
		}
	}
}
