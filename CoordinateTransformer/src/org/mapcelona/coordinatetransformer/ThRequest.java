package org.mapcelona.coordinatetransformer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.StringTokenizer;

import org.opengis.geometry.MismatchedDimensionException;
import org.opengis.referencing.operation.TransformException;

public class ThRequest extends Thread
{
	private Socket		s;
	private Transformer	t;

	public ThRequest(Socket s, Transformer t)
	{
		this.s = s;
		this.t = t;
	}

	@Override
	public void run()
	{
		InputStream		is;
		BufferedReader	br;
		String			request;
		StringTokenizer	st;
		Float			x, y;
		String			res;
		
		try
		{
			while(s.isConnected())
			{
				is = s.getInputStream();
				br = new BufferedReader(new InputStreamReader(is));
				request = br.readLine();
				System.out.println("Request arrived: " + request);
				st = new StringTokenizer(request, ",");
				x = Float.parseFloat(st.nextToken());
				y = Float.parseFloat(st.nextToken());

				try
				{
					res = t.parse(x, y);
					System.out.println("Response: " + res);
					res = res + "\n";
					s.getOutputStream().write(res.getBytes());
				}
				catch (MismatchedDimensionException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				catch (TransformException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			s.close();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
}
