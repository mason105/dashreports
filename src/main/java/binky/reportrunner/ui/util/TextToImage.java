package binky.reportrunner.ui.util;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

public class TextToImage {
	
	public byte[] getImageForText(String[] text, Color foreground,Color background, Font font,int width,int height, int offSetX, int offSetY,ImageFileFormat format ) throws IOException {
	
		BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		
		Graphics2D grph = img.createGraphics();
		
		grph.setFont(font);
		grph.setColor(foreground);
		grph.setBackground(background);		
		grph.clearRect(0, 0, width, height);
		
		int y=0;
		for (String s: text) {
				grph.drawString(s, offSetX, offSetY+y);
				y+=font.getSize()+2;
		}
		
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		
		ImageIO.write(img, format.getValue(), os);
		
		os.close();
		
		return os.toByteArray();
	}

	public enum ImageFileFormat {
		
		PNG("png"), JPG("jpg");
		
		private String value;

		ImageFileFormat(String value) {
			this.value = value;
		}

		public String getName() {
			return name();
		}

		public String getValue() {
			return value;
		}

	}
}
