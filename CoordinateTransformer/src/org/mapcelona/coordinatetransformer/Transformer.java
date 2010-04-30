package org.mapcelona.coordinatetransformer;

import org.geotools.geometry.GeneralDirectPosition;
import org.geotools.referencing.CRS;
import org.opengis.geometry.DirectPosition;
import org.opengis.geometry.MismatchedDimensionException;
import org.opengis.referencing.FactoryException;
import org.opengis.referencing.NoSuchAuthorityCodeException;
import org.opengis.referencing.crs.CoordinateReferenceSystem;
import org.opengis.referencing.operation.MathTransform;
import org.opengis.referencing.operation.TransformException;

public class Transformer
{
	private MathTransform	mt;
	
	public Transformer() throws NoSuchAuthorityCodeException, FactoryException
	{
		CoordinateReferenceSystem	source, target;
		
		source = CRS.decode("EPSG:23031");
		target = CRS.decode("EPSG:4326");
		
		mt = CRS.findMathTransform(source, target, true);
	}

	public synchronized String parse(Float x, Float y) throws MismatchedDimensionException, TransformException
	{
		GeneralDirectPosition	gdp;
		DirectPosition			dp;
		String					res;
		
		gdp = new GeneralDirectPosition(x, y);
		dp = mt.transform(gdp, null);
		
		res = dp.getCoordinate()[1] + "," + dp.getCoordinate()[0];
		
		return res;
	}
}
