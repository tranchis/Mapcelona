package org.mapcelona.javaparser.kmltordf;

import java.io.File;
import java.util.Iterator;

import de.micromata.opengis.kml.v_2_2_0.Document;
import de.micromata.opengis.kml.v_2_2_0.Feature;
import de.micromata.opengis.kml.v_2_2_0.Folder;
import de.micromata.opengis.kml.v_2_2_0.Kml;
import de.micromata.opengis.kml.v_2_2_0.Placemark;
import de.micromata.opengis.kml.v_2_2_0.Point;
import de.micromata.opengis.kml.v_2_2_0.Polygon;

public class Kml2RDF
{
	public static void main(String args[])
	{
		parseKml();
	}

	private static void parseKml()
	{
		Kml					kml;
		Document			doc;
		Iterator<Feature>	it, itf, itp, itb;
		Feature				f;
		Folder				fl;
		Placemark			p;
		Point				pt;
		Polygon				pl;
		
		kml = Kml.unmarshal(new File("/Users/sergio/Dropbox/Open Data/doc.kml"));
		doc = (Document)kml.getFeature();
		it = doc.getFeature().iterator();
		while(it.hasNext())
		{
			f = it.next();
			fl = (Folder)f;
			itf = fl.getFeature().iterator();
			while(itf.hasNext())
			{
				// 1er folder: Districtes
				f = itf.next();
				if(f instanceof Placemark)
				{
					p = (Placemark)f; // Distrito
					System.out.println(p.getName());
					pl = (Polygon)p.getGeometry();
					System.out.println(pl.getOuterBoundaryIs().getLinearRing().getCoordinates());
				}
				else if(f instanceof Folder)
				{
					fl = (Folder)f;
					System.out.println(fl.getName()); // Nombre del distrito
					System.out.println(fl.getFeature());
					itp = fl.getFeature().iterator();
					while(itp.hasNext())
					{
						f = itp.next();
						if(f instanceof Folder)
						{
							fl = (Folder)f;
							System.out.println(fl.getName());
							itb = fl.getFeature().iterator();
							while(itb.hasNext())
							{
								f = itb.next();
								p = (Placemark)f;
								System.out.println(p.getName()); // Nombre del barrio
								pt = (Point)p.getGeometry();
								System.out.println(pt.getCoordinates());
							}
						}
					}
				}
			}
		}
	}
}
