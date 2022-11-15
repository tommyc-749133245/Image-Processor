
/*Student Name:Cheung Ho Lam
 * Student ID:53044907
 * Lab:LC3
 */
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import javax.swing.*;
import java.util.*;

public class ImageProcessor
{	
	// The BufferedImage class describes an Image with an accessible buffer of image data
	public static BufferedImage convert(Image img)
	{
		BufferedImage bi = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_RGB);
		Graphics bg = bi.getGraphics();
		bg.drawImage(img, 0, 0, null);
		bg.dispose();
		return bi;
	}
	
	public static BufferedImage cloneImage(BufferedImage img)
	{
		BufferedImage resultImg = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_RGB);
		WritableRaster WR1 = Raster.createWritableRaster(img.getSampleModel(),null);
      WritableRaster WR2 = img.copyData(WR1);
		resultImg.setData(WR2);
		return resultImg;
	}
	
	// A method to convert color image to grayscale image
	public static BufferedImage toGrayScale(Image img)
	{
		// Convert image from type Image to BufferedImage
		BufferedImage bufImg = convert(img);
		
		// Scan through each row of the image
		for(int j=0; j<bufImg.getHeight(); j++)
		{
			// Scan through each columns of the image
			for(int i=0; i<bufImg.getWidth(); i++)
			{
				// Returns an integer pixel in the default RGB color model
				int values=bufImg.getRGB(i,j);
				// Convert the single integer pixel value to RGB color
				Color oldColor = new Color(values);
				
				int red = oldColor.getRed();		// get red value
				int green = oldColor.getGreen();	// get green value
				int blue = oldColor.getBlue();	// get blue value
				
				// Convert RGB to grayscale using formula
				// gray = 0.299 * R + 0.587 * G + 0.114 * B
				double grayVal = 0.299*red + 0.587*green + 0.114*blue;
				
				// Assign each channel of RGB with the same value
				Color newColor = new Color((int)grayVal, (int)grayVal, (int)grayVal);
				
				// Get back the integer representation of RGB color
				// and assign it back to the original position
            bufImg.setRGB(i, j, newColor.getRGB());
			}
		}
		// return back the resulting image in BufferedImage type
		return bufImg;
	}
	
	public static BufferedImage invertImage(Image img)
	{		
		BufferedImage bufImg = convert(img);
				
		for(int j=0; j<bufImg.getHeight(); j++)
		{
			for(int i=0; i<bufImg.getWidth(); i++)
			{
				int values=bufImg.getRGB(i,j);				
				Color oldColor = new Color(values);
				
				int red = oldColor.getRed();
				int green = oldColor.getGreen();
				int blue = oldColor.getBlue();
				
				Color newColor = new Color(255-red, 255 - green, 255 - blue);
				
            bufImg.setRGB(i, j, newColor.getRGB());
			}
		}
		return bufImg;
	}
	
	public static BufferedImage brighteningImage(Image img, int nBrightness)
	{
		BufferedImage bufImg = convert(img);
				
		for(int j=0; j<bufImg.getHeight(); j++)
		{
			for(int i=0; i<bufImg.getWidth(); i++)
			{
				int values=bufImg.getRGB(i,j);				
				Color oldColor = new Color(values);
				
				int red = oldColor.getRed();
				int green = oldColor.getGreen();
				int blue = oldColor.getBlue();
				
				int newRed = (red + nBrightness > 255) ? 255:red + nBrightness;
				int newGreen = (green + nBrightness > 255) ? 255:green + nBrightness;
				int newBlue = (blue + nBrightness > 255) ? 255:blue + nBrightness;

				newRed = (newRed < 0) ? 0:newRed;
				newGreen = (newGreen < 0) ? 0:newGreen;
				newBlue = (newBlue < 0) ? 0:newBlue;
				
				Color newColor = new Color(newRed, newGreen, newBlue);
				
            bufImg.setRGB(i, j, newColor.getRGB());
			}
		}
		return bufImg;	
	}
	
	public static BufferedImage offsetFilter(Image img, Point[][] offset)
	{
		// Temporary! This is only for us to make the program runnable
		return null;
	}
	
	public static BufferedImage offsetFilterAbs(Image img, Point[][] offset)
	{
		// Temporary! This is only for us to make the program runnable
		return null;
	}
	
	public static BufferedImage randomPixelMovement(Image img, int nDegree)
	{
		BufferedImage bufImg = convert(img);
		BufferedImage resultImg = convert(img);
		Random randomGen = new Random();
		
		 for(int j=0; j<bufImg.getHeight(); j++)
			{
				for(int i=0; i<bufImg.getWidth(); i++)
				{
					int valX = randomGen.nextInt(nDegree);
					int valY = randomGen.nextInt(nDegree);
					int offsetX = valX - nDegree / 2;
					int offsetY = valY - nDegree / 2;
					int x= i + offsetX;
					int y= j + offsetY;
					if(((x>bufImg.getWidth()-1))||(x<0))

						x = i;
		
					else if((y>(bufImg.getHeight()-1))||(y<0))
						y = j;

					else
						resultImg.setRGB(i, j, bufImg.getRGB(x,y));
					
				}
			}	
		return resultImg;
	}
	
	public static BufferedImage spinning(Image img, double fDegree)
	{
		BufferedImage bufImg = convert(img);
		BufferedImage resultImg = convert(img);
		int midX = bufImg.getWidth() / 2;
		int midY = bufImg.getHeight() / 2;
		int xNew;
		int yNew;
		 for(int j=0; j<bufImg.getHeight(); j++)
			{
				for(int i=0; i<bufImg.getWidth(); i++)
				{
					double x = i - midX;
					double y = j - midY;
					double theta = Math.atan2( y , x );
					double radius = Math.sqrt(x * x + y * y);
					x = (midX + (radius *Math.cos(theta + fDegree * radius)));
					 y = (midY + (radius *Math.sin(theta + fDegree * radius)));
					
			        //bufImg.setRGB(i, j, bufImg.getRGB(x,y));
					if(((x>bufImg.getWidth()-1))||(x<0))
						x = i;
					
					else if((y>(bufImg.getHeight()-1))||(y<0))
						y = j;
					
					else
					{
						xNew = (int) x;
						yNew = (int) y;
						resultImg.setRGB(i, j, bufImg.getRGB(xNew, yNew));
					}
				}
			}	
		return resultImg;
	}
	
	public static void RGBtoHSV (int R, int G, int B, float[] HSV) {
		// R,G,B in [0,255]
		float H = 0, S = 0, V = 0;
		float cMax = 255.0f;
		int cHi = Math.max(R,Math.max(G,B));
		int cLo = Math.min(R,Math.min(G,B));
		int cRng = cHi - cLo;
		
		// compute value V
		V = cHi / cMax;
		
		// compute saturation S
		if (cHi > 0)
			S = (float) cRng / cHi;

		// compute hue H
		if (cRng > 0) {
			float rr = (float)(cHi - R) / cRng;
			float gg = (float)(cHi - G) / cRng;
			float bb = (float)(cHi - B) / cRng;
			float hh;
			if (R == cHi)
				hh = bb - gg;
			else if (G == cHi)
				hh = rr - bb + 2.0f;
			else
				hh = gg - rr + 4.0f;
			if (hh < 0)
				hh= hh + 6;
			H = hh / 6;
		}
		
		if (HSV == null)
			HSV = new float[3];
		HSV[0] = H; HSV[1] = S; HSV[2] = V;
	}
	
	public static int HSVtoRGB (float h, float s, float v) {
		// h,s,v in [0,1]
		float rr = 0, gg = 0, bb = 0;
		float hh = (6 * h) % 6;                 
		int   c1 = (int) hh;                     
		float c2 = hh - c1;
		float x = (1 - s) * v;
		float y = (1 - (s * c2)) * v;
		float z = (1 - (s * (1 - c2))) * v;	
		switch (c1) {
			case 0: rr=v; gg=z; bb=x; break;
			case 1: rr=y; gg=v; bb=x; break;
			case 2: rr=x; gg=v; bb=z; break;
			case 3: rr=x; gg=y; bb=v; break;
			case 4: rr=z; gg=x; bb=v; break;
			case 5: rr=v; gg=x; bb=y; break;
		}
		int N = 256;
		int r = Math.min(Math.round(rr*N),N-1);
		int g = Math.min(Math.round(gg*N),N-1);
		int b = Math.min(Math.round(bb*N),N-1);
		int rgb = ((r&0xff)<<16) | ((g&0xff)<<8) | b&0xff; 
		return rgb;
	}
	
	public static BufferedImage preservingPartColor(Image img, boolean[][] mask, int colorVal, int rgValue, int rbValue, int gbValue)
	{
		return null;
	}
	

	public static char[][] imageToASCII(Image img)
	{
		BufferedImage bufImg = toGrayScale(img);
		char[] [] result = new char[bufImg.getHeight()][bufImg.getWidth()];
		for(int j=0; j<bufImg.getHeight(); j++)
		{
			for(int i=0; i<bufImg.getWidth(); i++)
			{
				int values=bufImg.getRGB(i,j);				
				Color oldColor = new Color(values);
				int red = oldColor.getRed();
				if(red >= 230)
					result[j][i] = ' ';
				else if(red>=200)
					result [j][i] = '.';
				else if(red>=180)
					result [j][i] = '*';
				else if(red>=160)
					result[j][i] = ':';
				else if(red>=130)
					result[j][i] = 'o';
				else if(red>=100)
					result[j][i] = '&';
				else if(red>=70)
					result[j][i] = '8';
				else if(red>=50)
					result[j][i] = '#';
				else
					result[j][i] = '@';

			}
		}
		return result;
	}	
}