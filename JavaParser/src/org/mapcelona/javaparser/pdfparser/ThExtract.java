package org.mapcelona.javaparser.pdfparser;

import java.io.File;
import java.io.IOException;

import org.apache.pdfbox.ExtractText;

public class ThExtract extends Thread
{
	private String	name;
	private File	temp;
	private int		page;

	public ThExtract(String name, int page) throws IOException
	{
		this.name = name;
		temp = File.createTempFile("dades", "tmp");
		this.page = page;
	}
	
	public File getFile()
	{
		return temp;
	}

	@Override
	public void run()
	{
		try
		{
			ExtractText.main(new String [] { "-startPage", "" + page, "-endPage", "" + page, name, temp.getAbsolutePath()} );
			System.out.println(temp.getAbsolutePath());
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
}
