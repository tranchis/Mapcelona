package org.mapcelona.javaparser.pdfparser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Set;
import java.util.Stack;
import java.util.StringTokenizer;
import java.util.TreeSet;

import org.mapcelona.javaparser.rdfutils.RDFUtils;

import com.hp.hpl.jena.rdf.model.Resource;

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
	private Set<String>		excepciones;
	
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
		excepciones = new TreeSet<String>();
		excepciones.add("i venda d'animals");
		excepciones.add("i serveis en el domini públic");
		excepciones.add("del paisatge urbà");
		excepciones.add("Ord. metropolitana d'edificació");
		excepciones.add("dels aliments");
		excepciones.add("de comerç alimentaris");
		excepciones.add("contra incendis als edificis");
		excepciones.add("Reglament d'explosius");
		excepciones.add("Zones forestals");
		excepciones.add("Reglament de pesca");
		excepciones.add("Reglament de caça");
		excepciones.add("venda d'articles pirotècnics");
		excepciones.add("Altres suprimides");
		excepciones.add("indigents o captaires");
		excepciones.add("en estat etílic");
		excepciones.add("malalts");
		excepciones.add("ferits");
		excepciones.add("persones extraviades");
		excepciones.add("tercera edat");
		excepciones.add("facultats mentals pertorbades");
		excepciones.add("manca de mitjans");
		excepciones.add("maltractaments");
		excepciones.add("per consum de drogues");
		excepciones.add("intent de suïcidi");
		excepciones.add("netejavidres");
		excepciones.add("sobredosi");
		excepciones.add("síndrome d’abstinència");
		excepciones.add("habitatge sense condicions");
		excepciones.add("animals en l'habitatge");
		excepciones.add("habitatge sinistrat");
		excepciones.add("agressions sexuals");
		excepciones.add("desinfecció d'habitatge");
		excepciones.add("mort per sobredosi");
		excepciones.add("ocupació d'habitatge");
		excepciones.add("altres situacions");
		excepciones.add("assistencial o altre lloc");
		excepciones.add("Reparar petites avaries vehicles");
		excepciones.add("les claus a l'interior");
		excepciones.add("Ajut a conductors");
		excepciones.add("del lloc d'estacionament");
		excepciones.add("vehicles reclamats");
		excepciones.add("Apagar petits incendis vehicles");
		excepciones.add("Altres serveis a conductors");
		excepciones.add("d'oli o carburant");
		excepciones.add("(incloent-hi zona forestal)");
		excepciones.add("viades a zona forestal");
		excepciones.add("Auxili de persones al mar");
		excepciones.add("Altres actuacions d'ajut");
		excepciones.add("Altres serveis d'ajut");
		excepciones.add("Trasllat o absentar-se de lloc");
		excepciones.add("Trasllat centre assistencial");
		excepciones.add("Trasllat centre sanitari");
		excepciones.add("Trasllat domicili");
		excepciones.add("Trasllat comissaria");
		excepciones.add("Contra el patrimoni");
		excepciones.add("Relatius al trànsit");
		excepciones.add("col·lectiva");
		excepciones.add("Contra l'rdre públic");
		excepciones.add("Lesions");
		excepciones.add("Contra la Constitució");
		excepciones.add("de seguretat ciutadana");
		excepciones.add("convivència ciutadana BCN");
		excepciones.add("Llei d'estrangeria");
		excepciones.add("en matèria de joc");
		excepciones.add("Contra el patrimoni");
		excepciones.add("Contra les persones");
		excepciones.add("Contra l'ordre públic");
		excepciones.add("generals");
		
		ru = RDFUtils.getInstance();
		
//		extractBomberos(url, 5, "Fora terme municipal");
//		extractTabla(url, 9, "Horta-", "Semàfors", 4, "SecurityBrokenStreetSign");
//		extractTabla(url, 10, "Horta-", "vehicles", 5, "SecurityTrafficSanctions");
		extractTabla(url, 11, "Horta-", "Accident", 4, "SecurityAlcoholTests");
//		extractTabla(url, 12, "3.535", "àmbit", 4, null);
		extractTabla(url, 13, "111.160", "àmbit", 4, null);
		extractTabla(url, 14, "Horta-", "comissaria", 4, null);
		extractTabla(url, 15, "Horta-", "àmbit territoria", 5, null);
		extractEvolucion(url, 16, "victimització: percentatge de població", false, 2008, "SecurityCrimeReporting");
		extractEvolucion(url, 18, "tica. Ajunta", true, 2009, "SecurityLevelScore");
		
		ru.dump();
	}

	private void extractEvolucion(String url, int page, String endmark, boolean iniciales, int lastyear, String code) throws IOException, InterruptedException
	{
		File				temp;
		ThExtract			te;
		FileInputStream		fis;
		BufferedReader		br;
		String				line;
		StringTokenizer		st;
		Stack<String>		pila;
		float[]				values;
		String				district;
		int					i, year;
		Resource			dataClass;
		
		dataClass = ru.getDataClass(code);
		
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
			
			values = new float[5];
			for(i=0;i<5;i++)
			{
				values[i] = Float.parseFloat(pila.pop().replace(".", "").replace(",", "."));
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
			
			year = lastyear;
			for(i=0;i<5;i++)
			{
				ru.setDataPieceYear(ru.addDataPieceDistrict(RDFUtils.normalise(district), dataClass, values[i]), year);
				year = year - 1;
			}
			
			line = br.readLine();
		}
	}

	private void extractTabla(String url, int page, String match, String endmark, int extras, String override) throws IOException, InterruptedException
	{
		File				temp;
		ThExtract			te;
		FileInputStream		fis;
		BufferedReader		br;
		String				line;
		StringTokenizer		st;
		Stack<String>		pila;
		float				value;
		float[]				values;
		String				district, concept;
		int					i;
		Resource			dataClass;
		
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
				
				values = new float[10];
				for(i=9;i>=0;i--)
				{
					values[i] = Float.parseFloat(pila.pop().replace(".", ""));
					System.out.println(districts[i] + ":" + values[i] + "");
				}
				
				pila.pop(); // Barcelona
				concept = pila.pop();
				while(!pila.isEmpty())
				{
					concept = pila.pop() + " " + concept;
				}
				System.out.println(concept);
				
				if(override == null)
				{
					try
					{
						dataClass = ru.checkMapping(concept);
						for(i=0;i<10;i++)
						{
							ru.setDataPieceYear(ru.addDataPieceDistrict(districts[i], dataClass, values[i]), 2008);
						}
					}
					catch(UnsupportedOperationException uoe)
					{
						if(!excepciones.contains(concept))
						{
							throw uoe;
						}
					}
				}
				else
				{
					dataClass = ru.getDataClass(override);
					for(i=0;i<10;i++)
					{
						ru.setDataPieceYear(ru.addDataPieceDistrict(districts[i], dataClass, values[i]), 2008);
					}
				}					
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
		Resource			dataClass;
		
		dataClass = ru.getDataClass("SecurityFireFighterIntervention");
		
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
			ru.setDataPieceYear(ru.addDataPieceDistrict(RDFUtils.normalise(district), dataClass, value), 2008);
			
			line = br.readLine();
		}
	}
}
