package binky.reportrunner.ui.util;

import java.awt.Color;
import java.awt.Font;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import junit.framework.TestCase;
import binky.reportrunner.ui.util.TextToImage.ImageFileFormat;

public class TextToImageTest extends TestCase {

	public void testGetImageForText() {
		
		try {
			TextToImage t = new TextToImage();


			byte[] ba = t.getImageForText(new String[]{"Line 1","Line 2","Line 3"}, Color.WHITE, Color.BLACK, 
					new Font("Serif", Font.PLAIN, 12), 400, 100, 10, 20, ImageFileFormat.PNG);
		
			
			File file = new File("test.png");
			if (file.exists()) file.delete();
			
			OutputStream os = new FileOutputStream(file);
			
			os.write(ba);
			os.flush();
			os.close();
			
			System.out.println(file.getAbsolutePath());
			
			assertTrue(ba.length>10);
			
		} catch (IOException e) {
			e.printStackTrace();
			fail(e.getMessage());			
		}
		
	
	}

}
