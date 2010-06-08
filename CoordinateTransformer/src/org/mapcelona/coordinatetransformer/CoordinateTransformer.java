package org.mapcelona.coordinatetransformer;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import org.opengis.referencing.FactoryException;
import org.opengis.referencing.NoSuchAuthorityCodeException;

public class CoordinateTransformer
{
	public static void main(String[] args) throws IOException, NoSuchAuthorityCodeException, FactoryException
	{
		CoordinateTransformer	ct;
		
		ct = new CoordinateTransformer();
		ct.start();
	}

	private void start() throws IOException, NoSuchAuthorityCodeException, FactoryException
	{
		ServerSocket	ss;
		Socket			s;
		ThRequest		th;
		Transformer		t;
		
		t = new Transformer();
		ss = new ServerSocket(1111);
		while(true)
		{
			s = ss.accept();
			th = new ThRequest(s, t);
			th.start();
		}
	}
}
