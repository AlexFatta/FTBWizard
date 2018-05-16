package fr.alexfatta.ftbWizard.extractFiles;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class UnzipFileClass {

	public static void extract(final File zip, final File toFolder) throws IOException {   
	       ZipInputStream istream = new ZipInputStream(new FileInputStream(zip));
	       byte[] buffer = new byte[4096];
	       toFolder.mkdirs();
	       
	       ZipEntry entry = istream.getNextEntry();
	       while(entry != null) {
	           final String fileName = entry.getName();
	           final File out = new File(toFolder.getAbsolutePath() + "/" + fileName);
	           
	           if(entry.isDirectory()) {
	               out.mkdirs();
	               
	               entry = istream.getNextEntry();
	               continue;
	           }
	           
	           final FileOutputStream ostream = new FileOutputStream(out);
	           
	           int len;
	           while((len = istream.read(buffer)) > 0) ostream.write(buffer, 0, len);
	               
	           ostream.close();
	           entry = istream.getNextEntry();
	       }
	           
	       istream.close();
	       
	       System.out.println("Processus d'extraction termine !");
	   }

}
