package org.mapcelona.javaparser.entrypoint;

import org.mapcelona.javaparser.barrios.Wikipedia2Neighbourhoods;
import org.mapcelona.javaparser.pdfparser.AyuntamientoParser;
import org.mapcelona.javaparser.staticdata.Ruido;

public class InitDB
{
	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception
	{
		Wikipedia2Neighbourhoods.main(null);
		System.gc();
		AyuntamientoParser.main(new String[] { "http://www.bcn.es/estadistica/catala/dades/inf/guies/dte01.pdf" });
		System.gc();
		AyuntamientoParser.main(new String[] { "http://www.bcn.es/estadistica/catala/dades/inf/guies/dte02.pdf" });
		System.gc();
		AyuntamientoParser.main(new String[] { "http://www.bcn.es/estadistica/catala/dades/inf/guies/dte03.pdf" });
		System.gc();
		AyuntamientoParser.main(new String[] { "http://www.bcn.es/estadistica/catala/dades/inf/guies/dte04.pdf" });
		System.gc();
		AyuntamientoParser.main(new String[] { "http://www.bcn.es/estadistica/catala/dades/inf/guies/dte05.pdf" });
		System.gc();
		AyuntamientoParser.main(new String[] { "http://www.bcn.es/estadistica/catala/dades/inf/guies/dte06.pdf" });
		System.gc();
		AyuntamientoParser.main(new String[] { "http://www.bcn.es/estadistica/catala/dades/inf/guies/dte07.pdf" });
		System.gc();
		AyuntamientoParser.main(new String[] { "http://www.bcn.es/estadistica/catala/dades/inf/guies/dte08.pdf" });
		System.gc();
		AyuntamientoParser.main(new String[] { "http://www.bcn.es/estadistica/catala/dades/inf/guies/dte09.pdf" });
		System.gc();
		AyuntamientoParser.main(new String[] { "http://www.bcn.es/estadistica/catala/dades/inf/guies/dte10.pdf" });
		System.gc();
		Ruido.main(null);
	}
}
