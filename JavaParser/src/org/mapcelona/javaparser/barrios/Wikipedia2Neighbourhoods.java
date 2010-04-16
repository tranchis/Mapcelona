package org.mapcelona.javaparser.barrios;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Iterator;
import java.util.List;

import net.htmlparser.jericho.Element;
import net.htmlparser.jericho.Source;
import net.htmlparser.jericho.StartTag;

public class Wikipedia2Neighbourhoods
{
	public static void main(String args[]) throws MalformedURLException, IOException
	{
		Source				s;
		Iterator<Element>	it, itb;
		Iterator<StartTag>	its;
		Element				e;
		StartTag			st;
		List<Element>		l;
		
		s = new Source(new URL("http://ca.wikipedia.org/wiki/Districtes_i_barris_de_Barcelona"));
		it = s.getChildElements().iterator();
//		while(it.hasNext())
//		{
			it.next(); // <!DOCTYPE>
			e = it.next(); // <html>
			its = e.getAllStartTags().iterator();
			st = its.next();
			while(its.hasNext() && !st.getName().equals("table"))
			{
				st = its.next();
			}
			
			// Tenemos la tabla de distritos
			it = st.getChildElements().get(0).getChildElements().iterator();
			it.next(); // Nos saltamos la cabecera
			while(it.hasNext())
			{
				e = it.next();
				l = e.getChildElements();
				System.out.println("Nombre: " + l.get(1).getChildElements().get(0).getChildElements().get(0).getContent());
				l = l.get(5).getChildElements(); // Barrios
				itb = l.iterator();
				while(itb.hasNext())
				{
					System.out.println(itb.next().getContent());
				}
			}
//		}
	}
}
