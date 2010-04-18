package org.mapcelona.javaparser.staticdata;

import java.io.IOException;
import java.util.Iterator;

import net.htmlparser.jericho.Element;
import net.htmlparser.jericho.Source;
import net.htmlparser.jericho.StartTag;

import org.mapcelona.javaparser.rdfutils.RDFUtils;

import com.hp.hpl.jena.rdf.model.Resource;

public class Ruido
{
	private RDFUtils	ru;

	// Datos sacados de: http://www.elpais.com/elpaismedia/ultimahora/media/200811/30/espana/20081130elpepunac_3_Pes_PDF.pdf
	public static void main(String args[]) throws IOException
	{
		Ruido	r;
		
		r = new Ruido();
		r.execute();
	}
	
	public Ruido()
	{
		ru = RDFUtils.getInstance();
	}

	private void execute() throws IOException
	{
		Source				s;
		Iterator<Element>	it;
		Iterator<StartTag>	its;
		Element				e;

		s = new Source(this.getClass().getClassLoader().getResourceAsStream("index.html"));
		it = s.getChildElements().iterator();

		it.next(); // <!DOCTYPE>
		e = it.next(); // <html>
		System.out.println(e.getAllStartTags());
		its = e.getAllStartTags().iterator();
		
		calcularNivel(its, 4, "NoiseDayTimeLengthExposure");	
		calcularNivel(its, 4, "NoiseEveningTimeLengthExposure");
		calcularNivel(its, 2, "NoiseNightTimeLengthExposure");
		calcularNivel(its, 3, "NoiseAllTimeLengthExposure");
		calcularNivel(its, 4, "NoiseDayTimePopulationExposure");
		calcularNivel(its, 4, "NoiseEveningTimePopulationExposure");
		calcularNivel(its, 2, "NoiseNightTimePopulationExposure");
		calcularNivel(its, 3, "NoiseAllTimePopulationExposure");
		
		ru.dump();
	}

	private void calcularNivel(Iterator<StartTag> its, int nivel, String code)
	{
		Iterator<Element>	it, ittd;
		Element				e;
		StartTag			st;
		float				total;
		int					i;
		Resource			r, dataClass;
		String				district;
		
		dataClass = ru.getDataClass(code);
		
		st = its.next();
		while (its.hasNext() && !st.getName().equals("table"))
		{
			st = its.next();
		}

		// Longitud exposada per cada districte en horari diürn (esto significa que porcentaje de las calles de ese distrito está afectada por los rangos de dbA de la columna durante el periodo de 7h a 21h. Vamos, la media porcentual de ruido en ese distrito por las mañanas.
		it = st.getChildElements().get(0).getChildElements().get(0).getChildElements().iterator(); // tbody.children
		it.next(); //tr: Cabeceras
//		ittd = e.getChildElements().iterator(); // tds
//		ittd.next(); // El primero está siempre vacío
//		cabeceras = new LinkedList<String>();
//		while(ittd.hasNext())
//		{
//			cabeceras.add(ittd.next().getContent().toString().replace("<br>", "").trim());
//		}
//		System.out.println(cabeceras);
		
		while(it.hasNext())
		{
			e = it.next(); //tr: Datos!
			ittd = e.getChildElements().iterator(); // tds
			e = ittd.next(); // Nombre distrito
			total = 0;
			district = e.getContent().toString().replace("<br>", "").trim();
			
			for(i=0;i<nivel;i++)
			{
				e = ittd.next();
			}
			
			while(ittd.hasNext())
			{
				total = total + Float.parseFloat(e.getContent().toString().replace(",", ".").replace("%", "").replace("<br>", "").trim());
				e = ittd.next();
			}
			System.out.println(total);
			
			r = ru.addDataPieceDistrict(district, dataClass, total);
			ru.setDataPieceYear(r, 2009);
		}
	}
}
