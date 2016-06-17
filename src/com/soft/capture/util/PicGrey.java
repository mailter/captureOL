package com.soft.capture.util;
 

import static org.bytedeco.javacpp.lept.pixRead;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.color.ColorSpace;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.awt.image.ColorConvertOp;
import java.awt.image.ColorModel;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;
import java.awt.image.MemoryImageSource;
import java.awt.image.PixelGrabber;
import java.awt.image.RescaleOp;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.imageio.stream.FileImageOutputStream;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.bytedeco.javacpp.BytePointer;
import org.bytedeco.javacpp.Loader;
import org.bytedeco.javacpp.lept.PIX;
import org.bytedeco.javacpp.tesseract.TessBaseAPI;

//import com.jniwrapper.win32.ole.types.UserClassType;
import com.soft.ol.action.CompanyAction;

public class PicGrey {
	private static Logger log = (Logger) LogManager
			.getLogger(PicGrey.class);
	BufferedImage image; 
	private int iw, ih; 
	private int[] pixels;
	private static int pixSeq = 0;
	
	public PicGrey(byte[] bitmap, BufferedImage bi) { 
		if ( bitmap != null) {
			FileImageOutputStream imageOutput;
			try {
				// 保留临时文件，便于事后分析哪些图片不能被识别
				if (pixSeq == 10) pixSeq = 0;
				String pixName = String.valueOf(pixSeq++);
				imageOutput = new FileImageOutputStream(new File("C:\\"+pixName+".jpg"));
				imageOutput.write(bitmap, 0, bitmap.length);
				imageOutput.close();
				
				ByteArrayInputStream fin = new ByteArrayInputStream(bitmap);
				image = ImageIO.read(fin);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else{
			image = bi;
		}

		iw = image.getWidth();
		ih = image.getHeight();
		pixels = new int[iw * ih]; 
	}
	
	public BufferedImage changeGrey(int grey) { 
		PixelGrabber pg = new PixelGrabber(image.getSource(), 0, 0, iw, ih, pixels,0, iw);
		try { 
			pg.grabPixels(); 
		} catch (InterruptedException e) {
			e.printStackTrace(); 
		} 
		
		// 设定二值化的域值，默认值为100
		//int grey = 100; 
		//  对图像进行二值化处理，Alpha值保持不变 
		ColorModel cm = ColorModel.getRGBdefault(); 
		for (int i = 0; i < iw * ih; i++) { 
			int red, green, blue;
			int alpha = cm.getAlpha(pixels[i]); 
			if (alpha < 255) {
				System.out.println("alpha ");
			}
			
			if (cm.getRed(pixels[i]) > grey) {
				red = 255; 
			} else { 
				red = 0; 
			} 
			if (cm.getGreen(pixels[i]) > grey) { 
				green = 255; 
			} else {
				green = 0;
			} 
			if (cm.getBlue(pixels[i]) > grey) {
				blue = 255; 
			} else { 
				blue = 0;
			} 
			
			
			
			pixels[i] = alpha << 24 | red << 16 | green << 8 | blue; 
		}
		//通过移位重新构成某一点像素的RGB值 } 
		// 将数组中的象素产生一个图像
		Image tempImg=Toolkit.getDefaultToolkit().createImage(new MemoryImageSource(iw,ih, pixels, 0, iw)); 
		image = new BufferedImage(tempImg.getWidth(null),tempImg.getHeight(null), BufferedImage.TYPE_INT_BGR ); 
		image.createGraphics().drawImage(tempImg, 0, 0, null); 
		return image; 
	}
	
	public BufferedImage changeGZPix(int ihigh, int iwidth) { 
		PixelGrabber pg = new PixelGrabber(image.getSource(), 0, 0, iw, ih, pixels,0, iw);
		try { 
			pg.grabPixels(); 
		} catch (InterruptedException e) {
			e.printStackTrace(); 
		} 

		for(int i=iwidth; i < iw; i++)
		{
			for (int j=0; j < ih; j++){
				if (j < ihigh) pixels[iw*j+i] = -1;
			}
		}
		
		for(int i=0; i < iwidth; i++)
		{
			for (int j=0; j < ih; j++){
				pixels[iw*j+i] = -1;
			}
		}

		Image tempImg=Toolkit.getDefaultToolkit().createImage(new MemoryImageSource(iw,ih, pixels, 0, iw)); 
		image = new BufferedImage(tempImg.getWidth(null),tempImg.getHeight(null), BufferedImage.TYPE_INT_BGR ); 
		image.createGraphics().drawImage(tempImg, 0, 0, null); 
		return image; 
	}
	
	public BufferedImage changeGreyBySpecialColor() { 
		PixelGrabber pg = new PixelGrabber(image.getSource(), 0, 0, iw, ih, pixels,0, iw);
		try { 
			pg.grabPixels(); 
		} catch (InterruptedException e) {
			e.printStackTrace(); 
		} 
		
		// 设定二值化的域值，默认值为100
		//int grey = 100; 
		//  对图像进行二值化处理，Alpha值保持不变 
		ColorModel cm = ColorModel.getRGBdefault(); 
		for (int i = 0; i < iw * ih; i++) { 
			int red, green, blue;
			int alpha = cm.getAlpha(pixels[i]); 
			if (alpha < 255) {
				System.out.println("alpha ");
			}
			
			// for the same values of red, green, blue
			if (cm.getRed(pixels[i]) == cm.getGreen(pixels[i])  && 
				cm.getGreen(pixels[i]) == cm.getBlue(pixels[i])  &&  
				cm.getBlue(pixels[i]) != 255) {
				red = 0; 
				green = 0;
				blue = 0;
			} else { 
				red = 255; 
				green = 255;
				blue = 255;
			} 	
			
			pixels[i] = alpha << 24 | red << 16 | green << 8 | blue; 
		}
		//通过移位重新构成某一点像素的RGB值 } 
		// 将数组中的象素产生一个图像
		Image tempImg=Toolkit.getDefaultToolkit().createImage(new MemoryImageSource(iw,ih, pixels, 0, iw)); 
		image = new BufferedImage(tempImg.getWidth(null),tempImg.getHeight(null), BufferedImage.TYPE_INT_BGR ); 
		image.createGraphics().drawImage(tempImg, 0, 0, null); 
		return image; 

	}
	
	public void jsEcrateCircle(int ecrateValue) { 
		PixelGrabber pg = new PixelGrabber(image.getSource(), 0, 0, iw, ih, pixels,0, iw);
		try { 
			pg.grabPixels(); 
		} catch (InterruptedException e) {
			e.printStackTrace(); 
		} 

		//  remove the special points
		ColorModel cm = ColorModel.getRGBdefault(); 
		for (int i = iw; i < iw * ih - iw; i++) { 
			int red, green, blue;
			int alpha = cm.getAlpha(pixels[i]); 
			if (alpha < 255) {
				System.out.println("alpha ");
			}
			
			if ( ( ( cm.getRed(pixels[i-1]) + cm.getGreen(pixels[i-1]) + cm.getBlue(pixels[i-1]) ) > ecrateValue) &&
			     ( ( cm.getRed(pixels[i+1]) + cm.getGreen(pixels[i+1]) + cm.getBlue(pixels[i+1]) ) > ecrateValue) ||
			     ( ( cm.getRed(pixels[i-iw]) + cm.getGreen(pixels[i-iw]) + cm.getBlue(pixels[i-iw]) ) > ecrateValue) &&
				 ( ( cm.getRed(pixels[i+iw]) + cm.getGreen(pixels[i+iw]) + cm.getBlue(pixels[i+iw]) ) > ecrateValue) ) {

				red = 255; 
				green = 255; 
				blue = 255; 
				pixels[i] = alpha << 24 | red << 16 | green << 8 | blue; 
			}
			
		}
		//通过移位重新构成某一点像素的RGB值 } 
		// 将数组中的象素产生一个图像
		Image tempImg=Toolkit.getDefaultToolkit().createImage(new MemoryImageSource(iw,ih, pixels, 0, iw)); 
		image = new BufferedImage(tempImg.getWidth(null),tempImg.getHeight(null), BufferedImage.TYPE_INT_BGR ); 
		image.createGraphics().drawImage(tempImg, 0, 0, null); 
	}
	
	public BufferedImage ahRemoveBase() { 
		PixelGrabber pg = new PixelGrabber(image.getSource(), 0, 0, iw, ih, pixels,0, iw);
		try { 
			pg.grabPixels(); 
		} catch (InterruptedException e) {
			e.printStackTrace(); 
		} 
		
		// 设定二值化的域值，默认值为100
		int ecrateValue = 300; 
		//  对图像进行二值化处理，Alpha值保持不变 
		ColorModel cm = ColorModel.getRGBdefault(); 
		for (int i = 1; i < iw * ih - 1; i++) { 
			int red, green, blue;
			int alpha = cm.getAlpha(pixels[i]); 
			if (alpha < 255) {
				System.out.println("alpha ");
			}
			
			if ( ( ( cm.getRed(pixels[i]) + cm.getGreen(pixels[i]) + cm.getBlue(pixels[i]) ) > ecrateValue) &&
				   (cm.getRed(pixels[i]) > 100 && cm.getGreen(pixels[i]) > 100 && cm.getBlue(pixels[i])> 100  ) ) {
				
				red = 255; 
				green = 255; 
				blue = 255; 
				pixels[i] = alpha << 24 | red << 16 | green << 8 | blue; 
			}
			
		}
		//通过移位重新构成某一点像素的RGB值 } 
		// 将数组中的象素产生一个图像
		Image tempImg=Toolkit.getDefaultToolkit().createImage(new MemoryImageSource(iw,ih, pixels, 0, iw)); 
		image = new BufferedImage(tempImg.getWidth(null),tempImg.getHeight(null), BufferedImage.TYPE_INT_BGR ); 
		image.createGraphics().drawImage(tempImg, 0, 0, null); 
		
		return image; 

	}
	
	public BufferedImage removeEdegeBlack(int highParts) { 
		PixelGrabber pg = new PixelGrabber(image.getSource(), 0, 0, iw, ih, pixels,0, iw);
		try { 
			pg.grabPixels(); 
		} catch (InterruptedException e) {
			e.printStackTrace(); 
		} 

		//  对图像进行二值化处理，Alpha值保持不变 
		ColorModel cm = ColorModel.getRGBdefault(); 
		for (int i = 1; i < iw * ih; i++) { //四周的点去掉
			int red, green, blue;
			int alpha = cm.getAlpha(pixels[i]); 
			if (alpha < 255) {
				System.out.println("alpha ");
			}
		
			// 将上边和下边部分区域填白
			if ( i < iw*ih/highParts || i > iw*ih/highParts*(highParts-1) ){
				red = 255; 
				green = 255; 
				blue = 255; 
				pixels[i] = alpha << 24 | red << 16 | green << 8 | blue; 
				continue;
			}
			
		}
		//通过移位重新构成某一点像素的RGB值 } 
		// 将数组中的象素产生一个图像
		Image tempImg=Toolkit.getDefaultToolkit().createImage(new MemoryImageSource(iw,ih, pixels, 0, iw)); 
		image = new BufferedImage(tempImg.getWidth(null),tempImg.getHeight(null), BufferedImage.TYPE_INT_BGR ); 
		image.createGraphics().drawImage(tempImg, 0, 0, null); 
		
		return image; 
	}
	
	public BufferedImage removeSingleBlack(int ecrateValue) { 
		PixelGrabber pg = new PixelGrabber(image.getSource(), 0, 0, iw, ih, pixels,0, iw);
		try { 
			pg.grabPixels(); 
		} catch (InterruptedException e) {
			e.printStackTrace(); 
		} 
		
		// 设定二值化的域值，默认值为100
		//int ecrateValue = 300; 
		//  对图像进行二值化处理，Alpha值保持不变 
		ColorModel cm = ColorModel.getRGBdefault(); 
		for (int i = iw; i < iw * ih - iw; i++) { //四周的点去掉
			int red, green, blue;
			int alpha = cm.getAlpha(pixels[i]); 
			if (alpha < 255) {
				System.out.println("alpha ");
			}
			// 
			try{
				if ( ( ( cm.getRed(pixels[i-1]) + cm.getGreen(pixels[i-1]) + cm.getBlue(pixels[i-1]) ) > ecrateValue) &&
				     ( ( cm.getRed(pixels[i+1]) + cm.getGreen(pixels[i+1]) + cm.getBlue(pixels[i+1]) ) > ecrateValue) &&
				     ( ( cm.getRed(pixels[i-iw]) + cm.getGreen(pixels[i-iw]) + cm.getBlue(pixels[i-iw]) ) > ecrateValue) &&
					 ( ( cm.getRed(pixels[i+iw]) + cm.getGreen(pixels[i+iw]) + cm.getBlue(pixels[i+iw]) ) > ecrateValue) ) {
						red = 255; 
						green = 255; 
						blue = 255; 
						pixels[i] = alpha << 24 | red << 16 | green << 8 | blue; 
				}
			} catch(Exception e){
				System.out.println("Excepton: " + e);
			}
			
		}
		//通过移位重新构成某一点像素的RGB值 } 
		// 将数组中的象素产生一个图像
		Image tempImg=Toolkit.getDefaultToolkit().createImage(new MemoryImageSource(iw,ih, pixels, 0, iw)); 
		image = new BufferedImage(tempImg.getWidth(null),tempImg.getHeight(null), BufferedImage.TYPE_INT_BGR ); 
		image.createGraphics().drawImage(tempImg, 0, 0, null); 
		
		return image; 

	}
	
	public BufferedImage removeGreyPoint() { 
		PixelGrabber pg = new PixelGrabber(image.getSource(), 0, 0, iw, ih, pixels,0, iw);
		try { 
			pg.grabPixels(); 
		} catch (InterruptedException e) {
			e.printStackTrace(); 
		} 
		
		//  对图像进行二值化处理，Alpha值保持不变 
		ColorModel cm = ColorModel.getRGBdefault(); 
		for (int i = 1; i < iw * ih ; i++) { 
			int red, green, blue;
			int alpha = cm.getAlpha(pixels[i]); 
			if (alpha < 255) {
				System.out.println("alpha ");
			}
			
			// 
			try{
				if ( ( cm.getRed(pixels[i]) > 90 && cm.getRed(pixels[i]) < 130) &&
				     ( cm.getGreen(pixels[i]) > 90 && cm.getGreen(pixels[i]) < 130) &&
				     ( cm.getBlue(pixels[i]) > 90 && cm.getBlue(pixels[i]) < 130) ) {
						red = 255; 
						green = 255; 
						blue = 255; 
						pixels[i] = alpha << 24 | red << 16 | green << 8 | blue; 
				}
			} catch(Exception e){
				System.out.println("Excepton: " + e);
			}
			
		}
		
		//通过移位重新构成某一点像素的RGB值 } 
		// 将数组中的象素产生一个图像
		Image tempImg=Toolkit.getDefaultToolkit().createImage(new MemoryImageSource(iw,ih, pixels, 0, iw)); 
		image = new BufferedImage(tempImg.getWidth(null),tempImg.getHeight(null), BufferedImage.TYPE_INT_BGR ); 
		image.createGraphics().drawImage(tempImg, 0, 0, null); 
		
		return image; 

	}
	
	public BufferedImage addBlack() { 
		PixelGrabber pg = new PixelGrabber(image.getSource(), 0, 0, iw, ih, pixels,0, iw);
		try { 
			pg.grabPixels(); 
		} catch (InterruptedException e) {
			e.printStackTrace(); 
		} 
		
		// 设定二值化的域值，默认值为100
		int ecrateValue = 300; 
		//  对图像进行二值化处理，Alpha值保持不变 
		ColorModel cm = ColorModel.getRGBdefault(); 
		for (int i = 0; i < iw * ih; i++) { //四周的点去掉
			int red, green, blue;
			int alpha = cm.getAlpha(pixels[i]); 

			// 将上边和下边部分区域填白
			if ( i < iw*ih/5 || i > iw*ih/5*4 ){
				red = 255; 
				green = 255; 
				blue = 255; 
				pixels[i] = alpha << 24 | red << 16 | green << 8 | blue; 
			}
		}
		for (int i = iw; i < iw * ih - iw; i++) { //四周的点去掉
			int red, green, blue;
			int alpha = cm.getAlpha(pixels[i]); 
			if (alpha < 255) {
				System.out.println("alpha ");
			}
				
			// 将字符内部的白点填黑
			try{
				if ( ( ( cm.getRed(pixels[i-1]) + cm.getGreen(pixels[i-1]) + cm.getBlue(pixels[i-1]) ) < ecrateValue) &&
				     ( ( cm.getRed(pixels[i+1]) + cm.getGreen(pixels[i+1]) + cm.getBlue(pixels[i+1]) ) < ecrateValue) ) {
					
					red = 0; 
					green = 0; 
					blue = 0; 
					pixels[i] = alpha << 24 | red << 16 | green << 8 | blue; 
				}
				
				if ( ( ( cm.getRed(pixels[i-iw]) + cm.getGreen(pixels[i-iw]) + cm.getBlue(pixels[i-iw]) ) < ecrateValue) &&
					     ( ( cm.getRed(pixels[i+iw]) + cm.getGreen(pixels[i+iw]) + cm.getBlue(pixels[i+iw]) ) < ecrateValue) ) {
						
						red = 0; 
						green = 0; 
						blue = 0; 
						pixels[i] = alpha << 24 | red << 16 | green << 8 | blue; 
				}
			} catch(Exception e){
				System.out.println("Excepton: " + e);
			}
			
		}
		//通过移位重新构成某一点像素的RGB值 } 
		// 将数组中的象素产生一个图像
		Image tempImg=Toolkit.getDefaultToolkit().createImage(new MemoryImageSource(iw,ih, pixels, 0, iw)); 
		image = new BufferedImage(tempImg.getWidth(null),tempImg.getHeight(null), BufferedImage.TYPE_INT_BGR ); 
		image.createGraphics().drawImage(tempImg, 0, 0, null); 
		
		return image; 

	}
	
	public String calculateJXPoint() { 
		PixelGrabber pg = new PixelGrabber(image.getSource(), 0, 0, iw, ih, pixels,0, iw);
		try { 
			pg.grabPixels(); 
		} catch (InterruptedException e) {
			e.printStackTrace(); 
		} 
		
		ColorModel cm = ColorModel.getRGBdefault(); 
		int red, green, blue;
		int[] sumByWidth = new int[iw];
		int[] sumByHigh = new int[ih];
		int[] wordLeftWidth = new int[ih], wordRightWidth = new int[ih];
		boolean wordBegin = false, wordEnd = false;
		int startWidth = 0, endWidth = 0;
		int startHigh = 0, endHigh = 0;
		int wordSec = 0;
		String code = "";
		
		// start to scan the word 
		// width
		for (int i = 1; i < iw -1 ; i++) { 
			// high
			for (int j = 1; j < ih ; j++) { 
				red = cm.getRed(pixels[j*iw+i]); 
				green = cm.getGreen(pixels[j*iw+i]);
				blue = cm.getBlue(pixels[j*iw+i]);
				if (red + green + blue == 0){
					// sum the number of black points by column
					sumByWidth[i] += 1;
				}
			}
			
			// get the start point
			if (sumByWidth[i] != 0 && startWidth == 0) {
				wordBegin = true;
				startWidth = i;
			}
			
			// get the end point
			if (sumByWidth[i] == 0 && wordBegin == true && i < iw -1) {
				int nextWidthCount = 0;
				for (int j = 1; j < ih ; j++) { 
					red = cm.getRed(pixels[j*iw+i+1]); 
					green = cm.getGreen(pixels[j*iw+i+1]);
					blue = cm.getBlue(pixels[j*iw+i+1]);
					if (red + green + blue == 0){
						// sum the number of black points by column
						nextWidthCount += 1;
					}
				}
				if (wordSec == 1 && nextWidthCount > 8) 
					// to avoid the case: the second word JIA is partly scanned 
					;
				else {
					wordEnd = true;
					endWidth = i;
				}
			}
			
			// get the whole word now
			if (wordBegin == true && wordEnd == true) {
				// word
				wordSec += 1;
				int[][] word = new int[ih][endWidth];
				
				// high
				for (int m = 1; m < ih ; m++) { 
					// width					
					for (int n = startWidth; n < endWidth ; n++) { 
						red = cm.getRed(pixels[m*iw+n]); 
						green = cm.getGreen(pixels[m*iw+n]);
						blue = cm.getBlue(pixels[m*iw+n]);
						if (red + green + blue == 0){
							sumByHigh[m] += 1;
							word[m][n] = 1;
							if (startHigh == 0){
								startHigh = m;
							}
							if (wordLeftWidth[m] == 0) wordLeftWidth[m] = n; 
							if (wordRightWidth[m] < n) wordRightWidth[m] = n; 
						}
						if((n == (endWidth -1)) && startHigh>1 && sumByHigh[m] == 0 &&  (( endHigh-startHigh) < 4 ) ){
							endHigh = m-1;
							// to avoid the noise black point and other useless scan
							if ((endHigh-startHigh) > 4 ) break;
						}
					}
				}
				
				// the number of black/white times for each line of the word
				int blackTimesByRow = 0, whiteTimesByRow = 0; 
				// the number of circle times of the word
				int circleNumber = 0, circleHighUp = 0;
				// definition for the circle property, to check  0, 6, 8, 9,    and 4
				boolean isCircleStart = false, isCircleEnd = false, isCircleStartUp = false, isCircleEndDown = false, isCircleFound = false;
				boolean isFour = false, isLeftLine = false, isRightLine = false;
				// the circle up and down width
				int circleUpHigh = 0, circleDownHigh = 0;
				// the word's high is separated for five parts
				int highCheck = (endHigh - startHigh)/5;
				// the word's width is separated for 2 parts
				int widthCheck = (endWidth - startWidth)/3;
				// definition for whether the black happened in the four places, to check 2, 3, 5
				int upLeftCount = 0, upRightCount = 0, downLeftCount = 0, downRightCount = 0;
				boolean isLeftUp = false, isLeftDown = false, isRightUp = false, isRightDown = false;
                // the width for the up of 1/5 width, to decide whether the word is 1 or 7
				int upPartialWidthStart = 0, upPartialWidthEnd = 0, downPartialWidthStart = 0, downPartialWidthEnd = 0;
				// definition whether the word is JIA, to check JIA, CHENG
				boolean isJia = true;
				
				// the up and down start width, end width
				int upStartWidth = 0, upEndWidth = 0, downStartWidth = 0, downEndWidth = 0;
				for (int m = startHigh; m <= endHigh ; m++) { 
					// width
					for (int n = startWidth; n < endWidth ; n++) { 
						if (word[m][n] == 1){
							// up
							if (m < (startHigh + endHigh)/2 ){
								if (upStartWidth > n || upStartWidth == 0) upStartWidth = n;
								if (upEndWidth < n) upEndWidth = n;
							}
							else{    // down
								if (downStartWidth > n || downStartWidth == 0) downStartWidth = n;
								if (downEndWidth < n) downEndWidth = n;
							}
						}
					}
				}
				
				for (int m = startHigh; m <= endHigh ; m++) { 
					whiteTimesByRow = 0;
					blackTimesByRow = 0;
					// width
					for (int n = startWidth; n < endWidth ; n++) { 
						if (word[m][n] == 1){
							// count the black point to check whether the circle is to be started, ignore the cycle > 180
							blackTimesByRow = whiteTimesByRow + 1;
							
							// check the value of isLeftUp, isLeftDown, isRightUp, isRightDown
							if ( (m > startHigh + highCheck) &&  (m < startHigh + highCheck*2) &&  (n <= startWidth + widthCheck) ){
								isLeftUp = true;
							}
							else if ( (m > startHigh + highCheck) &&  (m < startHigh + highCheck*2) &&  (n >= startWidth + widthCheck*2) ){
								isRightUp = true;
							}
							else if ( (m > startHigh + highCheck*3) &&  (m < startHigh + highCheck*4) &&  (n >= startWidth + widthCheck*2) ){
								isRightDown = true;
							}
							
							// to check whether is's 1 or 7
							if (m <= startHigh + highCheck) {
								if (upPartialWidthStart > n || upPartialWidthStart == 0) upPartialWidthStart = n;
								if (upPartialWidthEnd < n ) upPartialWidthEnd = n;
							}
							// to check whether is's 2 or 7
							if (m >= startHigh + highCheck * 4) {
								if (downPartialWidthStart > n || downPartialWidthStart == 0) downPartialWidthStart = n;
								if (downPartialWidthEnd < n ) downPartialWidthEnd = n;
							}
							
							// count up / down 's  left / right black points 
							if (m < (startHigh + endHigh)/2 ){
								if (n < ( upStartWidth + upEndWidth )/2 ) 
									upLeftCount++;
								else if (n >( upStartWidth + upEndWidth )/2 ) 
									upRightCount++;
							}
							else if (m > (startHigh + endHigh)/2 ) {    // down
								if (n < ( downStartWidth + downEndWidth )/2 ) 
									downLeftCount++;
								else if (n > ( downStartWidth + downEndWidth )/2 ) 
									downRightCount++;
							}
							
							// set the circle found
							if (isCircleEnd) {
								isCircleFound = true;
								circleDownHigh = m;
							}
						}
						else {
							if (isCircleFound ){
								// such case, do nothing
							}
							else if (isCircleStart) {
								// initial the default value for each partial time  
								if (blackTimesByRow > whiteTimesByRow ) {
									isCircleEnd = true;
								}
								// to check whether the circle is end
								if (word[m+1][n] == 1){
									isCircleEnd = isCircleEnd && true;
								}
								else{
									isCircleEnd = false;
								}
								//double check whether the circle is end through checking whether up is OK
								boolean isCircleHighUpGot = false;
								for (int kk = m-1; kk >= circleHighUp; kk--) {
									if (word[kk][n] == 1) {
										isCircleHighUpGot = true;
										break;
									}
								}
								isCircleEnd = isCircleEnd && isCircleHighUpGot;
							}
							whiteTimesByRow = blackTimesByRow ;
						}
					}
					if (isCircleStart ){
						// check whether the second line is correct
						if ( blackTimesByRow < 2 ){
							isCircleStart = false;
							isCircleEnd = false;
							if (circleNumber == 0){
								isCircleStartUp = false;
								isCircleEndDown = false;
							}
						}
					}
					else if ( blackTimesByRow >= 2){
						boolean isCircleBeginCheck = false;
						boolean isChecked = false;
						int tmpBlackTimesByRow = 1;
						for (int n = startWidth; n < endWidth ; n++) { 
							if (word[m][n] == 1){
								if (isChecked && isCircleStart)  {
									circleHighUp = m-1;
									break; // circle start found
								}
								
								if (isChecked) tmpBlackTimesByRow += 1;
								// to start check the blank points again
								if (tmpBlackTimesByRow < blackTimesByRow) 
									isCircleBeginCheck = true;    
								else
									isCircleBeginCheck = false;
								
								isChecked = false;
							}
							else {
								if (isCircleBeginCheck){
									if (! isChecked) {
										isCircleStart = true;
										isChecked = true;
									}
									// check whether the above is black
									if (word[m-1][n] == 1){
										isCircleStart = isCircleStart && true;
									}
									else{
										isCircleStart = false;
									}
								}
							}
							if (isCircleStart && circleDownHigh == 0) circleUpHigh = m;
						}
					}
					
					// check whether it's JIA
					if (( m > startHigh+(endHigh - startHigh)/2 -2) && (  m < startHigh+(endHigh - startHigh)/2 +2 ) ){
						if (blackTimesByRow == 4) 
							isJia = isJia && true;
						else 
							isJia = false;
					}

					// check whether there is a circle
					if (isCircleStart == true && isCircleEnd == true ){					
						isCircleStart = false;
						isCircleEnd = false;
						isCircleFound = false;
						// check whether the first circle is start by up
						if (m >= endHigh - highCheck){
							isCircleEndDown = true;
						}
						circleNumber += 1;
					}
					
					if(isCircleStart) {
						// check whether the first circle is end by down
						if (( m < startHigh + 3) ){
							isCircleStartUp = true;
						}
					}
				}
				
				// count black number to distinct JIA / CHEN
				int sumBlack = 0;
				for (int k = 0; k < ih; k++){
					sumBlack = sumBlack + sumByHigh[k];
				}
				
				// calculate the circle whether is for 4
				if (circleNumber == 1) {
					int lineStart = 0, lineLCount = 0, lineRCount = 0;
					int upCompareValue = wordRightWidth[(startHigh+endHigh)/2] - wordRightWidth[startHigh];
					int downCompareValue = wordRightWidth[endHigh -2] - wordRightWidth[(startHigh+endHigh)/2];
					if ( ( upCompareValue == 0 && downCompareValue == 0 ) ) {
						for (int m = startHigh; m <= endHigh; m++){
							if ( wordRightWidth[m+1] - wordRightWidth[m] == 0 ) {
								lineRCount ++ ;
							}
						}
						if ( lineRCount >= (endHigh - startHigh)/5*4 )  isFour = true;
					}
					else if (  upCompareValue >= 0 && downCompareValue >= 0 ) {
						for (int m = startHigh; m <= endHigh; m++){
							if ( wordRightWidth[m+1] - wordRightWidth[m] >= 0 ) {
								lineRCount ++ ;
							}
						}
						if ( lineRCount >= (endHigh - startHigh)/5*4 )  isFour = true;
					}
					else if (  upCompareValue <= 0 && downCompareValue <= 0 ) {
						for (int m = startHigh; m <= endHigh; m++){
							if ( wordRightWidth[m+1] - wordRightWidth[m] <= 0 ) {
								lineRCount ++ ;
							}
						}
						if ( lineRCount >= (endHigh - startHigh)/5*4 )  isFour = true;
					}
					
					if (isFour) {
						// to avoid the special 0
						if ( (upCompareValue == 0 || downCompareValue == 0) && isCircleEndDown )
							isFour = false;
						// to avoid the special 9
						if ( !isCircleEndDown && ( (upPartialWidthEnd - upPartialWidthStart)+2 > (downPartialWidthEnd - downPartialWidthStart) ) )
							isFour = false;
					}
				}
				
				// calculate the circle whether is for left and right line
				if (circleNumber == 0) {
					int compareValue = 0;
					if ( wordLeftWidth[startHigh+highCheck+1] > wordLeftWidth[endHigh-highCheck-1] ) 
						compareValue = -1;
					else
						compareValue = 1;
					for (int m = startHigh+highCheck+1; m < endHigh-highCheck-1; m++){
						if ((wordLeftWidth[m+1] - wordLeftWidth[m] == compareValue) || (wordLeftWidth[m+1] - wordLeftWidth[m] ==0)) {
							isLeftLine = true ;
						}
						else {
							isLeftLine = false ;
							break;
						}
					}	
					
					if ( wordRightWidth[startHigh+highCheck+1] > wordRightWidth[endHigh-highCheck-1] ) 
						compareValue = -1;
					else
						compareValue = 1;
					for (int m = startHigh+highCheck+1; m < endHigh-highCheck-1; m++){
						if ((wordRightWidth[m+1] - wordRightWidth[m] == compareValue) || (wordRightWidth[m+1] - wordRightWidth[m] ==0)) {
							isRightLine = true ;
						}
						else {
							isRightLine = false ;
							break;
						}
					}
				}
	
				// make decision for the word
				if (wordSec == 1 || wordSec == 3){
					if (circleNumber >= 2){
						code = code + "8";
					} 					
//					else if( ( circleNumber == 1 && !isCircleStartUp && !isCircleEndDown) ||
//							 ( circleNumber == 1 && isCircleStartUp && !isCircleEndDown &&
//							    ( (upPartialWidthEnd - upPartialWidthStart) < (endWidth - startWidth) / 2) ) ){
//						code = code + "4";
//					} 
					else if(isFour){
						code = code + "4";
					}
					else if(circleNumber == 1 && !isCircleEndDown){
						code = code + "9";
					}
					else if(circleNumber == 1 && !isCircleStartUp ){
						code = code + "6";
					}
					else if(circleNumber == 1 ){
						code = code + "0";
					}
					else if ( isLeftLine && isRightLine && ( (upPartialWidthEnd - upPartialWidthStart) < (downPartialWidthEnd - downPartialWidthStart) )) {
						code = code + "1";
					}
					else if (circleNumber == 0 && isRightLine &&  (endWidth - startWidth) >= (downPartialWidthEnd - downPartialWidthStart) *2){
						code = code + "7";
					}
					// isLeftLine is used to distinguish 3 in special case
					else if(upRightCount > upLeftCount && ( ( downRightCount < downLeftCount) || isLeftLine ) ){
						code = code + "2";
					}
					else if(upRightCount > upLeftCount && downRightCount > downLeftCount ){
						code = code + "3";
					}
					else if(upRightCount < upLeftCount && downRightCount > downLeftCount ){
						code = code + "5";
					}

					
					if (wordSec == 3){
						// just check the top three words
						break;
					}
				}
				else if (wordSec == 2 ) {
//					if (sumBlack < 130)
//						code = code + "+";
//					else {
//						if (isJia && sumBlack < 162)
//							code = code + "+";
//						else {
							// to check the right part points again
							int lCount = 0, zCount = 0, bCount = 0, lastTypeValue = -2, currentTypeValue = -2;
							for (int m = startHigh; m < endHigh; m++){
								if ( wordRightWidth[m+1] - wordRightWidth[m] == 0) {
									zCount ++ ;
									// added for the last type
									if (lastTypeValue == -1)
										lCount ++ ;
									else if (lastTypeValue == 1)
										bCount ++ ;
									
									if (currentTypeValue == -2) {
										// initial
										lastTypeValue = 0;
										currentTypeValue = 0;
									}
									else if ( currentTypeValue != 0){
										// there is a change
										lastTypeValue = currentTypeValue;
									    currentTypeValue = 0;
									}
								}
								else if ( wordRightWidth[m+1] - wordRightWidth[m] == 1) {
									bCount ++ ;
									// added for the last zero type
									if (currentTypeValue == 0) {
										bCount = bCount + zCount;
										zCount = 0;
									}
									
									if (currentTypeValue == -2) {
										lastTypeValue = 1;
										currentTypeValue = 1;
									}
									else if ( currentTypeValue != 1){
										lastTypeValue = currentTypeValue;
									    currentTypeValue = 1;
									}
									// change the last type
									if (bCount == 2) lastTypeValue = 1;
											
									// for the change, reset the value
									if (lCount < 6) lCount = 0;
								}
								else if ( wordRightWidth[m+1] - wordRightWidth[m] == -1) {
									lCount ++ ;
									// added for the last zero type
									if (currentTypeValue == 0) {
										lCount = lCount + zCount;
										zCount = 0;
									}
									
									if (currentTypeValue == -2) {
										lastTypeValue = -1;
										currentTypeValue = -1;
									}
									else  if ( currentTypeValue != -1) {
										lastTypeValue = currentTypeValue;
										currentTypeValue = -1;
									}
									// change the last type
									if (lCount == 2) lastTypeValue = -1;
									
									// for the change, reset the value
									if (bCount < 6) bCount = 0;
								}
								else {
									lastTypeValue = -2; currentTypeValue = -2; zCount = 0; 
									if (lCount < 6) lCount = 0; 
									if (bCount < 6) bCount = 0; 
								}
							}	
							if (lCount >= (endHigh- startHigh)/2 ||
								bCount >= (endHigh- startHigh)/2	)
								code = code + "+";
							else
								code = code + "*";
//						}
//					}
				}
								
				// reset
				// print the value
				for (int m = 1; m < ih ; m++) { 
					sumByHigh[m] = 0;				
				}
				for (int n = startWidth; n < endWidth ; n++) { 
					sumByWidth[n] = 0;
				}

				wordBegin = false;
				wordEnd = false;
				startWidth = 0;
				endWidth = 0;
				startHigh = 0;
				endHigh = 0;
			}
		}	
		
		return code; 
	}
	
	public void removeLowBase(int sumValue) { 
		PixelGrabber pg = new PixelGrabber(image.getSource(), 0, 0, iw, ih, pixels,0, iw);
		try { 
			pg.grabPixels(); 
		} catch (InterruptedException e) {
			e.printStackTrace(); 
		} 
		//  remove the background point, which total value less than sumValue
		ColorModel cm = ColorModel.getRGBdefault(); 
		for (int i = 0; i < iw * ih; i++) { //四周的点去掉
			int red, green, blue;
			int alpha = cm.getAlpha(pixels[i]); 
			alpha = 255;

			if ( ( cm.getRed(pixels[i]) + cm.getGreen(pixels[i]) + cm.getBlue(pixels[i]) ) > sumValue ) {
					
					red = 255; 
					green = 255; 
					blue = 255; 
					pixels[i] = alpha << 24 | red << 16 | green << 8 | blue; 
				}
				
		}
		//通过移位重新构成某一点像素的RGB值 } 
		// 将数组中的象素产生一个图像
		Image tempImg=Toolkit.getDefaultToolkit().createImage(new MemoryImageSource(iw,ih, pixels, 0, iw)); 
		image = new BufferedImage(tempImg.getWidth(null),tempImg.getHeight(null), BufferedImage.TYPE_INT_BGR ); 
		image.createGraphics().drawImage(tempImg, 0, 0, null); 
	}
		
	public BufferedImage getMedian() { 
		PixelGrabber pg = new PixelGrabber(image.getSource(), 0, 0, iw, ih, pixels, 0, iw); 
		try { 
			pg.grabPixels(); 
		} catch (InterruptedException e) { e.printStackTrace(); } 
		
		// 对图像进行中值滤波，Alpha值保持不变
		ColorModel cm = ColorModel.getRGBdefault(); 
		for (int i = 1; i < ih - 1; i++) {
			for (int j = 1; j < iw - 1; j++) { 
				int red, green, blue; 
				int alpha = cm.getAlpha(pixels[i * iw + j]); 
				// int red2 = cm.getRed(pixels[(i - 1) * iw + j]);
				int red4 = cm.getRed(pixels[i * iw + j - 1]); 
				int red5 = cm.getRed(pixels[i * iw + j]);
				int red6 = cm.getRed(pixels[i * iw + j + 1]);
				
				// int red8 = cm.getRed(pixels[(i + 1) * iw + j]);
				// 水平方向进行中值滤波 
				if (red4 >= red5) {
					if (red5 >= red6) {
						red = red5; 
					} else {
						if (red4 >= red6) {
							red = red6;
						} else { 
							red = red4; 
						} 
					} 
				} else {
					if (red4 > red6) {
						red = red4; 
					} else { 
						if (red5 > red6) {
							red = red6;
						} else {
							red = red5; 
						}
					} 
				} 
				
				int green4 = cm.getGreen(pixels[i * iw + j - 1]); 
				int green5 = cm.getGreen(pixels[i * iw + j]); 
				int green6 = cm.getGreen(pixels[i * iw + j + 1]);
				
				// 水平方向进行中值滤波
				if (green4 >= green5) { 
					if (green5 >= green6) {
						green = green5;
					} else {
						if (green4 >= green6) {
							green = green6; 
						} else { 
							green = green4;
						} 
					} 
				} else { 
					if (green4 > green6) { 
						green = green4; 
					} else { 
						if (green5 > green6) {
							green = green6; 
						} else { 
							green = green5;
						}
					} 
				} 
				
				// int blue2 = cm.getBlue(pixels[(i - 1) * iw + j]); 
				int blue4 = cm.getBlue(pixels[i * iw + j - 1]); 
				int blue5 = cm.getBlue(pixels[i * iw + j]); 
				int blue6 = cm.getBlue(pixels[i * iw + j + 1]); 
				
				// int blue8 = cm.getBlue(pixels[(i + 1) * iw + j]); 
				// 水平方向进行中值滤波 
				if (blue4 >= blue5) {
					if (blue5 >= blue6) { 
						blue = blue5; 
					} else { 
						if (blue4 >= blue6) {
							blue = blue6;
						} else { 
							blue = blue4;
						} 
					} 
				} else { 
					if (blue4 > blue6) {
						blue = blue4; 
					} else { 
						if (blue5 > blue6) { 
							blue = blue6;
						} else {
							blue = blue5;
						} 
					} 
				} 
				pixels[i * iw + j] = alpha << 24 | red << 16 | green << 8 | blue; 
			}
		} 
		
		// 将数组中的象素产生一个图像 
		
		Image tempImg=Toolkit.getDefaultToolkit().createImage(new MemoryImageSource(iw,ih, pixels, 0, iw));
		image = new BufferedImage(tempImg.getWidth(null),tempImg.getHeight(null), BufferedImage.TYPE_INT_BGR ); 
		image.createGraphics().drawImage(tempImg, 0, 0, null); 
		return image; 
	} 
			
	public String analyseTiltWord(){
		PixelGrabber pg = new PixelGrabber(image.getSource(), 0, 0, iw, ih, pixels, 0, iw); 
		try { 
			pg.grabPixels(); 
		} catch (InterruptedException e) { e.printStackTrace(); }
		
		ColorModel cm = ColorModel.getRGBdefault(); 
		int red, green, blue;
		int[] sumByWidth = new int[iw];
		int[] sumByHigh = new int[ih];
		boolean wordBegin = false, wordEnd = false;
		int startWidth = 0, endWidth = 0;
		int startHigh = 0, endHigh = 0;
		int wordSec = 0;
		String codeValue = "";
		
		// start to scan the word 
		// width
		for (int i = 1; i < iw -1 ; i++) { 
			// high
			for (int j = 1; j < ih ; j++) { 
				red = cm.getRed(pixels[j*iw+i]); 
				green = cm.getGreen(pixels[j*iw+i]);
				blue = cm.getBlue(pixels[j*iw+i]);
				if (red + green + blue == 0){
					// sum the number of black points by column
					sumByWidth[i] += 1;
				}
			}
			
			// get the start point
			if (sumByWidth[i] != 0 && startWidth == 0) {
				wordBegin = true;
				startWidth = i;
			}
			
			// get the end point
			if (sumByWidth[i] == 0 && wordBegin == true) {
				if (wordSec == 1 && (i - startWidth ) < 15) 
					// to avoid the case: the second word JIA is partly scanned 
					;
				else {
					wordEnd = true;
					endWidth = i;
				}
			}
			
			// get the whole word now
			if (wordBegin == true && wordEnd == true) {
				int[] tmpPixels=new int[iw*ih];
				int[] analyzePixels = new int[iw*ih];
				// initial
				for (int kk = 0; kk < iw ; kk++) { 
					// high
					for (int jj = 0; jj < ih ; jj++) { 
						tmpPixels[jj*iw+kk] = -1;
						analyzePixels[jj*iw+kk] = -1;
					}
				}
				
				// word
				wordSec += 1;

				int[] wordHighUpPosition = new int[iw]; 
				int[] wordHighDownPosition = new int[iw];
				
				// high
				for (int m = 1; m < ih ; m++) { 
					// width					
					for (int n = startWidth; n < endWidth ; n++) { 
						red = cm.getRed(pixels[m*iw+n]); 
						green = cm.getGreen(pixels[m*iw+n]);
						blue = cm.getBlue(pixels[m*iw+n]);
						if (red + green + blue == 0){
							sumByHigh[m] += 1;
							tmpPixels[m*iw+n] = 0;
							if (startHigh == 0){
								startHigh = m;
							}
							if (wordHighUpPosition[n] == 0 ) 
								wordHighUpPosition[n] = m;
							else
								wordHighDownPosition[n] = m;
						}
						if((n == (endWidth -1)) && startHigh>1 && sumByHigh[m] == 0 &&  (( endHigh-startHigh) < 4 ) ){
							endHigh = m-1;
							// to avoid the noise black point and other useless scan
							if ((endHigh-startHigh) > 4 ) break;
						}
					}
				}

				// analyze word 
				// calculate points' D-value times
				int[] compareValue = new int[endWidth - startWidth];
				int[] countLeftDTimes = new int[7]; // left direction: count the 0 times for D-Value 0
				int[] countRightDTimes = new int[7]; // right direction: count the 0 times for D-Value 0
				boolean isLeft = true;
				int zeroCount = 0;
				for (int k = 0; k < compareValue.length; k++) { 
					if (wordHighUpPosition[startWidth+k] == 1) continue; // ignore such case
					compareValue[k] = wordHighUpPosition[startWidth+k+1] - wordHighUpPosition[startWidth+k];
					if (compareValue[k] == 0) {
						zeroCount ++;
					}
					else if (compareValue[k] >= 1) {
						if (zeroCount >= 7)
							countLeftDTimes[6] ++;
						else if (zeroCount != 0)
							countLeftDTimes[zeroCount] ++;
						zeroCount = 0;
						isLeft = true;
					}
					else if (compareValue[k] <= -1) {
						if (zeroCount >= 7)
							countRightDTimes[6] ++;
						else if (zeroCount != 0)
							countRightDTimes[zeroCount] ++;
						zeroCount = 0;
						isLeft = false;
					}
				}
				// to cover the last time
				if (zeroCount >= 7) {
					if (isLeft) 
						countLeftDTimes[6] ++;
					else
						countRightDTimes[6] ++;
				}
				else{
					if (isLeft) 
						countLeftDTimes[zeroCount] ++;
					else
						countRightDTimes[zeroCount] ++;
				}
				
				// calculate rotate angle
				int wordMaxHighUpPosition = 80, wordMaxHighDownPosition = 0;
				for (int k = startWidth; k <= endWidth ; k++) { 
					if ( wordMaxHighUpPosition > wordHighUpPosition[k] ) wordMaxHighUpPosition = wordHighUpPosition[k];
					if ( wordMaxHighDownPosition < wordHighDownPosition[k] ) wordMaxHighDownPosition = wordHighDownPosition[k];
				}

				int gapValue = wordHighUpPosition[startWidth] - wordHighUpPosition[endWidth];
				boolean isRotate = true;
				// count the D-value
				int rotateDegree = 0 , rotateLeftDegree = 0, countLeftMax = 0, maxLeftPos = 0, rotateRightDegree = 0, countRightMax = 0, maxRightPos = 0;
				for (int k = 0; k < 7; k++) {
					if (countLeftDTimes[k] > countLeftMax ) {
						countLeftMax = countLeftDTimes[k];
						maxLeftPos = k;
					}
					if (countRightDTimes[k] > countRightMax ) {
						countRightMax = countRightDTimes[k];
						maxRightPos = k;
					}
				}
				// for left
				if (countLeftDTimes[6] > 0 )
					rotateLeftDegree = 0;
				else if (countLeftDTimes[6] > 0 )
					rotateLeftDegree = 4;
				else if (countLeftDTimes[4] > 0)
					rotateLeftDegree = 8;
				else {
					if (maxLeftPos == 3)
						rotateLeftDegree = 12;
					else if (maxLeftPos == 2)
						rotateLeftDegree = 16;
					else if (maxLeftPos == 1)
						rotateLeftDegree = 20;
					else if (maxLeftPos == 0)
						rotateLeftDegree = 24;
				}
				// for right
				if (countRightDTimes[6] > 0 )
					rotateRightDegree = 0;
				else if (countRightDTimes[6] > 0 )
					rotateRightDegree = -4;
				else if (countRightDTimes[4] > 0)
					rotateRightDegree = -8;
				else {
					if (maxRightPos == 3)
						rotateRightDegree = -12;
					else if (maxRightPos == 2)
						rotateRightDegree = -16;
					else if (maxRightPos == 1)
						rotateRightDegree = -20;
					else if (maxRightPos == 0)
						rotateRightDegree = -24;
				}
				
				if ( gapValue > 2 ) {
					// left rotate
					if ( wordHighUpPosition[startWidth] < wordHighUpPosition[endWidth] +3 )
						rotateDegree = 0-rotateLeftDegree;     // some word special, both points in up part
					else
						rotateDegree = rotateLeftDegree;
				}
				else if ( gapValue < -2 ){
					// right rotate
					if ( wordHighUpPosition[startWidth] < wordHighUpPosition[endWidth] + 3 )
						rotateDegree = 0-rotateRightDegree;     // some word special, both points in up part
					else
						rotateDegree = rotateRightDegree;
				}
				else {
					isRotate = false;
				}
				if (rotateDegree == 0) isRotate = false;

				int[] result = new int[2];
				// rotate the word points
				for (int k = startWidth; k <= endWidth ; k++) { 
					// high
					for (int j = 0; j < ih ; j++) { 
						if (tmpPixels[j*iw+k] == 0 ){
							if ( isRotate ) {
								result = rotatePoint(rotateDegree, startWidth, 0, k, j);
								try {
									analyzePixels[result[1]*iw+result[0]] = 0;
								} catch (Exception e) {
									
								}
							}
							else 
								analyzePixels[j*iw+k] = 0;
						}
					}
				}


				Image tempImg=Toolkit.getDefaultToolkit().createImage(new MemoryImageSource(iw,ih, analyzePixels, 0, iw));
				image = new BufferedImage(tempImg.getWidth(null),tempImg.getHeight(null), BufferedImage.TYPE_INT_RGB ); 
				image.createGraphics().drawImage(tempImg, 0, 0, null); 
				
				codeValue = codeValue + getCode("chi_sim");
				
				// reset
				// print the value
				for (int m = 1; m < ih ; m++) { 
					sumByHigh[m] = 0;				
				}
				for (int n = startWidth; n < endWidth ; n++) { 
					sumByWidth[n] = 0;
				}

				wordBegin = false;
				wordEnd = false;
				startWidth = 0;
				endWidth = 0;
				startHigh = 0;
				endHigh = 0;
			}
		}

		return codeValue;
	}
	
	public String analyseTiltNumber(){
		PixelGrabber pg = new PixelGrabber(image.getSource(), 0, 0, iw, ih, pixels, 0, iw); 
		try { 
			pg.grabPixels(); 
		} catch (InterruptedException e) { e.printStackTrace(); }
		
		ColorModel cm = ColorModel.getRGBdefault(); 
		int red, green, blue;
		int[] sumByWidth = new int[iw];
		int[] sumByHigh = new int[ih];
		boolean wordBegin = false, wordEnd = false;
		int startWidth = 0, endWidth = 0;
		int startHigh = 0, endHigh = 0;
		int wordSec = 0;
		String codeValue = "";
		
		// start to scan the word 
		// width
		for (int i = 1; i < iw -1 ; i++) { 
			// high
			for (int j = 1; j < ih ; j++) { 
				red = cm.getRed(pixels[j*iw+i]); 
				green = cm.getGreen(pixels[j*iw+i]);
				blue = cm.getBlue(pixels[j*iw+i]);
				if (red + green + blue == 0){
					// sum the number of black points by column
					sumByWidth[i] += 1;
				}
			}
			
			// get the start point
			if (sumByWidth[i] != 0 && startWidth == 0) {
				wordBegin = true;
				startWidth = i;
			}
			
			// get the end point
			if (sumByWidth[i] == 0 && wordBegin == true) {
				if (wordSec == 1 && (i - startWidth ) < 15) 
					// to avoid the case: the second word JIA is partly scanned 
					;
				else {
					wordEnd = true;
					endWidth = i;
				}
			}
			
			// get the whole word now
			if (wordBegin == true && wordEnd == true) {
				int[] tmpPixels=new int[iw*ih];
				int[] analyzePixels = new int[iw*ih];
				// initial
				for (int kk = 0; kk < iw ; kk++) { 
					// high
					for (int jj = 0; jj < ih ; jj++) { 
						tmpPixels[jj*iw+kk] = -1;
						analyzePixels[jj*iw+kk] = -1;
					}
				}
				
				// word
				wordSec += 1;

				int[] wordHighUpPosition = new int[iw]; 
				int[] wordHighDownPosition = new int[iw];
				
				// high
				for (int m = 1; m < ih ; m++) { 
					// width					
					for (int n = startWidth; n < endWidth ; n++) { 
						red = cm.getRed(pixels[m*iw+n]); 
						green = cm.getGreen(pixels[m*iw+n]);
						blue = cm.getBlue(pixels[m*iw+n]);
						if (red + green + blue == 0){
							sumByHigh[m] += 1;
							tmpPixels[m*iw+n] = 0;
							if (startHigh == 0){
								startHigh = m;
							}
							if (wordHighUpPosition[n] == 0 ) 
								wordHighUpPosition[n] = m;
							else
								wordHighDownPosition[n] = m;
						}
						if((n == (endWidth -1)) && startHigh>1 && sumByHigh[m] == 0 &&  (( endHigh-startHigh) < 4 ) ){
							endHigh = m-1;
							// to avoid the noise black point and other useless scan
							if ((endHigh-startHigh) > 4 ) break;
						}
					}
				}

				// analyze word 
				// calculate points' D-value times
				int[] compareValue = new int[endWidth - startWidth];
				int[] countLeftDTimes = new int[7]; // left direction: count the 0 times for D-Value 0
				int[] countRightDTimes = new int[7]; // right direction: count the 0 times for D-Value 0
				boolean isLeft = true;
				int zeroCount = 0;
				for (int k = 0; k < compareValue.length; k++) { 
					if (wordHighUpPosition[startWidth+k] == 1) continue; // ignore such case
					compareValue[k] = wordHighUpPosition[startWidth+k+1] - wordHighUpPosition[startWidth+k];
					if (compareValue[k] == 0) {
						zeroCount ++;
					}
					else if (compareValue[k] >= 1) {
						if (zeroCount >= 7)
							countLeftDTimes[6] ++;
						else if (zeroCount != 0)
							countLeftDTimes[zeroCount] ++;
						zeroCount = 0;
						isLeft = true;
					}
					else if (compareValue[k] <= -1) {
						if (zeroCount >= 7)
							countRightDTimes[6] ++;
						else if (zeroCount != 0)
							countRightDTimes[zeroCount] ++;
						zeroCount = 0;
						isLeft = false;
					}
				}
				// to cover the last time
				if (zeroCount >= 7) {
					if (isLeft) 
						countLeftDTimes[6] ++;
					else
						countRightDTimes[6] ++;
				}
				else{
					if (isLeft) 
						countLeftDTimes[zeroCount] ++;
					else
						countRightDTimes[zeroCount] ++;
				}
				
				// calculate rotate angle
				int wordMaxHighUpPosition = 80, wordMaxHighDownPosition = 0;
				for (int k = startWidth; k <= endWidth ; k++) { 
					if ( wordMaxHighUpPosition > wordHighUpPosition[k] ) wordMaxHighUpPosition = wordHighUpPosition[k];
					if ( wordMaxHighDownPosition < wordHighDownPosition[k] ) wordMaxHighDownPosition = wordHighDownPosition[k];
				}

				int gapValue = wordHighUpPosition[startWidth] - wordHighUpPosition[endWidth];
				boolean isRotate = true;
				// count the D-value
				int rotateDegree = 0 , rotateLeftDegree = 0, countLeftMax = 0, maxLeftPos = 0, rotateRightDegree = 0, countRightMax = 0, maxRightPos = 0;
				for (int k = 0; k < 7; k++) {
					if (countLeftDTimes[k] > countLeftMax ) {
						countLeftMax = countLeftDTimes[k];
						maxLeftPos = k;
					}
					if (countRightDTimes[k] > countRightMax ) {
						countRightMax = countRightDTimes[k];
						maxRightPos = k;
					}
				}
				// for left
				if (countLeftDTimes[6] > 0 )
					rotateLeftDegree = 0;
				else if (countLeftDTimes[6] > 0 )
					rotateLeftDegree = 4;
				else if (countLeftDTimes[4] > 0)
					rotateLeftDegree = 8;
				else {
					if (maxLeftPos == 3)
						rotateLeftDegree = 12;
					else if (maxLeftPos == 2)
						rotateLeftDegree = 16;
					else if (maxLeftPos == 1)
						rotateLeftDegree = 20;
					else if (maxLeftPos == 0)
						rotateLeftDegree = 24;
				}
				// for right
				if (countRightDTimes[6] > 0 )
					rotateRightDegree = 0;
				else if (countRightDTimes[6] > 0 )
					rotateRightDegree = -4;
				else if (countRightDTimes[4] > 0)
					rotateRightDegree = -8;
				else {
					if (maxRightPos == 3)
						rotateRightDegree = -12;
					else if (maxRightPos == 2)
						rotateRightDegree = -16;
					else if (maxRightPos == 1)
						rotateRightDegree = -20;
					else if (maxRightPos == 0)
						rotateRightDegree = -24;
				}
				
				if ( gapValue > 2 ) {
					// left rotate
					if ( wordHighUpPosition[startWidth] < wordHighUpPosition[endWidth] +3 )
						rotateDegree = 0-rotateLeftDegree;     // some word special, both points in up part
					else
						rotateDegree = rotateLeftDegree;
				}
				else if ( gapValue < -2 ){
					// right rotate
					if ( wordHighUpPosition[startWidth] < wordHighUpPosition[endWidth] + 3 )
						rotateDegree = 0-rotateRightDegree;     // some word special, both points in up part
					else
						rotateDegree = rotateRightDegree;
				}
				else {
					isRotate = false;
				}
				if (rotateDegree == 0) isRotate = false;

				int[] result = new int[2];
				// rotate the word points
				for (int k = startWidth; k <= endWidth ; k++) { 
					// high
					for (int j = 0; j < ih ; j++) { 
						if (tmpPixels[j*iw+k] == 0 ){
							if ( isRotate ) {
								result = rotatePoint(rotateDegree, startWidth, 0, k, j);
								try {
									analyzePixels[result[1]*iw+result[0]] = 0;
								} catch (Exception e) {
									
								}
							}
							else 
								analyzePixels[j*iw+k] = 0;
						}
					}
				}


				Image tempImg=Toolkit.getDefaultToolkit().createImage(new MemoryImageSource(iw,ih, analyzePixels, 0, iw));
				image = new BufferedImage(tempImg.getWidth(null),tempImg.getHeight(null), BufferedImage.TYPE_INT_RGB ); 
				image.createGraphics().drawImage(tempImg, 0, 0, null); 
				
				codeValue = codeValue + getCode("chi_sim");
				
				// reset
				// print the value
				for (int m = 1; m < ih ; m++) { 
					sumByHigh[m] = 0;				
				}
				for (int n = startWidth; n < endWidth ; n++) { 
					sumByWidth[n] = 0;
				}

				wordBegin = false;
				wordEnd = false;
				startWidth = 0;
				endWidth = 0;
				startHigh = 0;
				endHigh = 0;
			}
		}

		return codeValue;
	}
	
	public String filterByColorCount(){
		PixelGrabber pg = new PixelGrabber(image.getSource(), 0, 0, iw, ih, pixels, 0, iw); 
		try { 
			pg.grabPixels(); 
		} catch (InterruptedException e) { e.printStackTrace(); }
			
		//统计出有几种颜色的字符，分类抽取
		String codeValue = "";
		int num = 1000;
		int[] color=new int[num];
		int[] colorcount=new int[num];
		boolean isCount = false;
		ColorModel cm = ColorModel.getRGBdefault(); 
		
		for(int i=0; i<pixels.length; i++)
		{
			isCount = false;
			int red = cm.getRed(pixels[i]);
			int green  = cm.getGreen(pixels[i]);
			int blue = cm.getBlue(pixels[i]);
			if ( ( red + green + blue ) > 300 ) continue;
					
			for (int j=0; j<num; j++){
				if (( pixels[i] == color[j])  ) {
					colorcount[j] ++;
					isCount = true;
					break;
				}
			}
			if (! isCount) {
				for (int j=0; j<num; j++){
					if (0 == color[j]) {
						color[j] = pixels[i];
						colorcount[j] ++;
						break;
					}
				}
			}
		}
		
		int wordNumber = 0;
		// order
		for(int i=0; i<colorcount.length; i++)
		{
			for(int j=i; j<colorcount.length; j++)
			{
				if (colorcount[i] < colorcount[j]){
					int tmp = colorcount[i];
					colorcount[i] = colorcount[j];
					colorcount[j] = tmp;
					
					int tmpcolor = color[i];
					color[i] = color[j];
					color[j] = tmpcolor;
					
				}
			}
			// set the word number, discard other points count less than 30
			if (colorcount[i] < 50){
				wordNumber = i;
				break;
			}
		}

		// set the new picture value
		int[][] sumByWidth = new int[wordNumber][iw];
		int[][] wordHighUpPosition = new int[wordNumber][iw]; 
		int[][] wordHighDownPosition = new int[wordNumber][iw];
		int[] outPixels=new int[iw*ih];
		// width
		for (int i = 1; i < iw -1 ; i++) { 
			// high
			for (int j = 0; j < ih ; j++) { 
				outPixels[j*iw+i] = -1;
				// count the word one bye one
				for (int w = 0; w < wordNumber; w++) {
					if (pixels[j*iw+i] == color[w])
					{
						outPixels[j*iw+i] = pixels[j*iw+i];
						sumByWidth[w][i] += 1;
						if (wordHighUpPosition[w][i] == 0 ) 
							wordHighUpPosition[w][i] = j;
						else
							wordHighDownPosition[w][i] = j;
						break;
					}
				}
			}
		}
		
		// find the word position
		int[] wordCenterWidth = new int[wordNumber], wordCenterValue = new int[wordNumber];
		int[] wordLeftWidth = new int[wordNumber], wordRightWidth = new int[wordNumber];
		for(int w = 0; w < wordNumber ; w++){
			for (int i = 0; i < iw ; i++) {
				if ( wordCenterValue[w] < sumByWidth[w][i] ) {
					wordCenterValue[w] = sumByWidth[w][i];
					wordCenterWidth[w] = i;
				}
			}
			// word left
			for (int i = wordCenterWidth[w]; i >= 0 ; i--) {
				if ( sumByWidth[w][i] == 0 ) {
					wordLeftWidth[w] = i+1;
					break;
				}
			}
			// word right
			for (int i = wordCenterWidth[w]; i <= iw; i++) {
				if ( sumByWidth[w][i] == 0 ) {
					wordRightWidth[w] = i-1;
					break;
				}
			}
		}
		
		// word order
		for(int w = 0; w < wordNumber ; w++){
			for (int i = w; i < wordNumber ; i++) {
				if ( wordCenterWidth[w] > wordCenterWidth[i] ) {
					int tmp = wordCenterWidth[w];
					wordCenterWidth[w] = wordCenterWidth[i];
					wordCenterWidth[i] = tmp;
					tmp = wordLeftWidth[w];
					wordLeftWidth[w] = wordLeftWidth[i];
					wordLeftWidth[i] = tmp;
					tmp = wordRightWidth[w];
					wordRightWidth[w] = wordRightWidth[i];
					wordRightWidth[i] = tmp;
					tmp = color[w];
					color[w] = color[i];
					color[i] = tmp;
					
					int[] arraytmp = wordHighUpPosition[w];
					wordHighUpPosition[w] = wordHighUpPosition[i];
					wordHighUpPosition[i] = arraytmp;
					arraytmp = wordHighDownPosition[w];
					wordHighDownPosition[w] = wordHighDownPosition[i];
					wordHighDownPosition[i] = arraytmp;
				}
			}
		}
		
		// analyze word one bye one
		int[] tmpPixels=new int[iw*ih];
		int[] analyzePixels = new int[iw*ih];
		for (int w = 0; w < wordNumber; w++) {
			// width
			for (int i = 0; i < iw ; i++) { 
				// high
				for (int j = 0; j < ih ; j++) { 
					if (i < wordLeftWidth[w] || i > wordRightWidth[w])
						tmpPixels[j*iw+i] = -1;
					else{
						if (outPixels[j*iw+i] == color[w])
							tmpPixels[j*iw+i] = color[w];
						else
							tmpPixels[j*iw+i] = -1;
					}
				}
			}
			
			
			// calculate points' D-value times
			int[] compareValue = new int[wordRightWidth[w] - wordLeftWidth[w]];
			int[] countLeftDTimes = new int[7]; // left direction: count the 0 times for D-Value 0
			int[] countRightDTimes = new int[7]; // right direction: count the 0 times for D-Value 0
			boolean isLeft = true;
			int zeroCount = 0;
			for (int i = 0; i < wordRightWidth[w] - wordLeftWidth[w]; i++) { 
				if (wordHighUpPosition[w][wordLeftWidth[w]+i] == 1) continue; // ignore such case
				compareValue[i] = wordHighUpPosition[w][wordLeftWidth[w]+i+1] - wordHighUpPosition[w][wordLeftWidth[w]+i];
				if (compareValue[i] == 0) {
					zeroCount ++;
				}
				else if (compareValue[i] >= 1) {
					if (zeroCount >= 7)
						countLeftDTimes[6] ++;
					else if (zeroCount != 0)
						countLeftDTimes[zeroCount] ++;
					zeroCount = 0;
					isLeft = true;
				}
				else if (compareValue[i] <= -1) {
					if (zeroCount >= 7)
						countRightDTimes[6] ++;
					else if (zeroCount != 0)
						countRightDTimes[zeroCount] ++;
					zeroCount = 0;
					isLeft = false;
				}
			}
			// to cover the last time
			if (zeroCount >= 7) {
				if (isLeft) 
					countLeftDTimes[6] ++;
				else
					countRightDTimes[6] ++;
			}
			else{
				if (isLeft) 
					countLeftDTimes[zeroCount] ++;
				else
					countRightDTimes[zeroCount] ++;
			}
			
			// calculate rotate angle
			int wordMaxHighUpPosition = 80, wordMaxHighDownPosition = 0;
			for (int i = wordLeftWidth[w]; i <= wordRightWidth[w] ; i++) { 
				if ( wordMaxHighUpPosition > wordHighUpPosition[w][i] ) wordMaxHighUpPosition = wordHighUpPosition[w][i];
				if ( wordMaxHighDownPosition < wordHighDownPosition[w][i] ) wordMaxHighDownPosition = wordHighDownPosition[w][i];
			}

			int gapValue = wordHighUpPosition[w][wordLeftWidth[w]] - wordHighUpPosition[w][wordRightWidth[w]];
			boolean isRotate = true;
			// count the D-value
			int rotateDegree = 0 , rotateLeftDegree = 0, countLeftMax = 0, maxLeftPos = 0, rotateRightDegree = 0, countRightMax = 0, maxRightPos = 0;
			for (int i = 0; i < 7; i++) {
				if (countLeftDTimes[i] > countLeftMax ) {
					countLeftMax = countLeftDTimes[i];
					maxLeftPos = i;
				}
				if (countRightDTimes[i] > countRightMax ) {
					countRightMax = countRightDTimes[i];
					maxRightPos = i;
				}
			}
			// for left
			if (countLeftDTimes[6] > 0 )
				rotateLeftDegree = 0;
			else if (countLeftDTimes[6] > 0 )
				rotateLeftDegree = 4;
			else if (countLeftDTimes[4] > 0)
				rotateLeftDegree = 8;
			else {
				if (maxLeftPos == 3)
					rotateLeftDegree = 12;
				else if (maxLeftPos == 2)
					rotateLeftDegree = 16;
				else if (maxLeftPos == 1)
					rotateLeftDegree = 20;
				else if (maxLeftPos == 0)
					rotateLeftDegree = 24;
			}
			// for right
			if (countRightDTimes[6] > 0 )
				rotateRightDegree = 0;
			else if (countRightDTimes[6] > 0 )
				rotateRightDegree = -4;
			else if (countRightDTimes[4] > 0)
				rotateRightDegree = -8;
			else {
				if (maxRightPos == 3)
					rotateRightDegree = -12;
				else if (maxRightPos == 2)
					rotateRightDegree = -16;
				else if (maxRightPos == 1)
					rotateRightDegree = -20;
				else if (maxRightPos == 0)
					rotateRightDegree = -24;
			}
			
			if ( gapValue > 2 ) {
				// left rotate
				if ( wordHighUpPosition[w][wordLeftWidth[w]] < wordHighUpPosition[w][wordRightWidth[w]] +3 )
					rotateDegree = 0-rotateLeftDegree;     // some word special, both points in up part
				else
					rotateDegree = rotateLeftDegree;
			}
			else if ( gapValue < -2 ){
				// right rotate
				if ( wordHighUpPosition[w][wordRightWidth[w]] < wordHighUpPosition[w][wordLeftWidth[w]] + 3 )
					rotateDegree = 0-rotateRightDegree;     // some word special, both points in up part
				else
					rotateDegree = rotateRightDegree;
			}
			else {
				isRotate = false;
			}
			if (rotateDegree == 0) isRotate = false;
//			if (countLeftMax - countRightMax <= 2 &&  countLeftMax - countRightMax >= -2 ) isRotate = false;
			
			// some special case which not be covered by above
//			int wordHighDownLeftPosition = 0, wordHighDownRightPosition = 0;
//			for (int i = wordLeftWidth[w]; i < wordLeftWidth[w]+3; i++) {
//				if (wordHighDownLeftPosition < wordHighDownPosition[w][i]) wordHighDownLeftPosition = wordHighDownPosition[w][i];
//			}
//			for (int i = wordRightWidth[w]; i > wordRightWidth[w]-3; i--) {
//				if (wordHighDownRightPosition < wordHighDownPosition[w][i]) wordHighDownRightPosition = wordHighDownPosition[w][i];
//			}
//			if (wordRightWidth[w] - wordLeftWidth[w] >= 25) {
//				isRotate = true;
//			}
//			if (wordHighDownLeftPosition >= wordMaxHighDownPosition - 4 && rotateDegree < 0 ) isRotate = false;
//			else if (wordHighDownRightPosition >= wordMaxHighDownPosition - 4 && rotateDegree > 0 ) isRotate = false;


			int[] result = new int[2];
			// rotate the word points
			for (int i = wordLeftWidth[w]; i <= wordRightWidth[w] ; i++) { 
				// high
				for (int j = 0; j < ih ; j++) { 
					if (tmpPixels[j*iw+i] == color[w] ){
						if ( isRotate ) {
							result = rotatePoint(rotateDegree, wordLeftWidth[w], 0, i, j);
							try {
								analyzePixels[result[1]*iw+result[0]] = color[w];
							} catch (Exception e) {
								
							}
						}
						else 
							analyzePixels[j*iw+i] = color[w];
					}
				}
			}
			
			// update other leave points
			for (int i = 0; i < iw ; i++) { 
				// high
				for (int j = 0; j < ih ; j++) { 
					if (analyzePixels[j*iw+i] != color[w]){
						analyzePixels[j*iw+i] = -1;
					}
				}
			}
			
			Image tempImg=Toolkit.getDefaultToolkit().createImage(new MemoryImageSource(iw,ih, analyzePixels, 0, iw));
			image = new BufferedImage(tempImg.getWidth(null),tempImg.getHeight(null), BufferedImage.TYPE_INT_RGB ); 
			image.createGraphics().drawImage(tempImg, 0, 0, null); 
			
			codeValue = codeValue + getCode("chi_sim");
		}

		return codeValue;
	}
	
	public int[] rotatePoint(float degree, int x0, int y0, int x, int y){
		int[] result = new int[2];
		degree = new Float(Math.toRadians(degree));
		result[0] = (int)Math.round( (x-x0)*Math.cos(degree) +(y-y0)*Math.sin(degree)+x0 );
		result[1] = (int)Math.round(-(x-x0)*Math.sin(degree) + (y-y0)*Math.cos(degree)+y0);
		return result;
	}
	public void rotateImage(int x, int degree) {
        int type = image.getColorModel().getTransparency();
        BufferedImage img;
        Graphics2D graphics2d;
        (graphics2d = (img = new BufferedImage(iw, ih, type))
                .createGraphics()).setRenderingHint(
                RenderingHints.KEY_INTERPOLATION,
                RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        graphics2d.rotate(Math.toRadians(24), x, 0);
        graphics2d.drawImage(image, 0, 0, null);
        graphics2d.rotate(Math.toRadians(12), x, 0);
        graphics2d.dispose();
        image = img;
	}
	
	/**
	 *  distinguish by the H, ignore the light > 200 (this bring problem sometimes), 
	 *              and such case need to be resolved: 
	 *              1, same H, but different L
	 *              2, when strong color disturb, near points color need to be corrected
	 * @return
	 */
	public void filterByHSBColor(int iLight){
		PixelGrabber pg = new PixelGrabber(image.getSource(), 0, 0, iw, ih, pixels, 0, iw); 
		try { 
			pg.grabPixels(); 
		} catch (InterruptedException e) { e.printStackTrace(); }
			
		//统计出有几种颜色的字符，分类抽取
		String codeValue = "";
		int num = 360/5; // to separate the Hue by 10
		float[] color=new float[num];
		for (int i = 0; i < num; i++){
			color[i] = i*5;
		}
		int[] colorCount = new int[num];
		float[] colorAvarageL = new float[num];
		float[][] hsbPixels = new float[pixels.length][3];
		
		ColorModel cm = ColorModel.getRGBdefault(); 
		
		int[] analyzePixels = new int[iw*ih];
		for (int i = 0; i < analyzePixels.length ; i++) { 
			analyzePixels[i] = -1;
		}
		
		boolean isWordStart = false;
		int gapValueH = 25, gapValueL = 40, wordColorHighCount = 0, wordStartWidth = 0, wordCoreWidth = 0;
		float wordColorCoreValue = 0, wordColorCoreL = 0;
		
		// scan the high 
		for (int i = 0; i < iw  ; i++) { 
			// high
			for (int j = 0; j < ih ; j++) { 
				int red = cm.getRed(pixels[iw*j+i]);
				int green  = cm.getGreen(pixels[iw*j+i]);
				int blue = cm.getBlue(pixels[iw*j+i]);
				
				if (red + green + blue > 700) continue;
				
				float[] hsbValue = rgb2hls(red, green, blue );
				// ignore the light > iLight
				if (hsbValue[1] > iLight ) continue;
				
				hsbPixels[iw*j+i] = hsbValue;
						
				for (int k=1; k<num; k++){
					if ( ( color[k] >= hsbValue[0])  ) {
						colorAvarageL[k-1] = (colorAvarageL[k-1]*colorCount[k-1] + hsbValue[1]) / (colorCount[k-1] + 1);
						colorCount[k-1] ++;
						break;
					}
				}
			}
			
			// check whether there is a word color is started
			for( int m = 1; m < num-1 ; m++) {
				if (colorCount[m]+colorCount[m+1] >= 30){
					isWordStart = true;
					wordColorCoreValue = color[m];
					wordColorCoreL = colorAvarageL[m];
					wordCoreWidth = i;
					System.out.println("wordColorCoreValue: " + wordColorCoreValue +"  wordCoreWidth: "+ wordCoreWidth +"  wordColorCoreL: "+ wordColorCoreL);
					break;
				}
			}
			
			if (isWordStart){
				for (int m = wordStartWidth; m < iw  ; m++) { 
					// high
					for (int n = 0; n < ih ; n++) { 
						if (m < i) {
							// use above value
							if ( ( hsbPixels[iw*n+m][0] + 240 <= wordColorCoreValue + gapValueH ||
								   (hsbPixels[iw*n+m][0] >= wordColorCoreValue - gapValueH && hsbPixels[iw*n+m][0] <= wordColorCoreValue + gapValueH)  ) &&
							   (hsbPixels[iw*n+m][1] > wordColorCoreL - gapValueL && hsbPixels[iw*n+m][1] < wordColorCoreL + gapValueH) ) {
									analyzePixels[iw*n+m] = pixels[iw*n+m];
//									analyzePixels[iw*n+m] = 0;
									wordColorHighCount ++;
							}
						}
						else {
							int red = cm.getRed(pixels[iw*n+m]);
							int green  = cm.getGreen(pixels[iw*n+m]);
							int blue = cm.getBlue(pixels[iw*n+m]);
							
							if (red + green + blue > 700) continue;
							
							float[] hsbValue = rgb2hls(red, green, blue );
							// ignore the light > 200
							if (hsbValue[1] > 220 ) continue;
							
							hsbPixels[iw*n+m] = hsbValue;
							if ( ( hsbPixels[iw*n+m][0] + 240 <= wordColorCoreValue + gapValueH ||
									   (hsbPixels[iw*n+m][0] >= wordColorCoreValue - gapValueH && hsbPixels[iw*n+m][0] <= wordColorCoreValue + gapValueH)  ) &&
								   (hsbPixels[iw*n+m][1] > wordColorCoreL - gapValueL && hsbPixels[iw*n+m][1] < wordColorCoreL + gapValueH) ) {
								analyzePixels[iw*n+m] = pixels[iw*n+m];
//								analyzePixels[iw*n+m] = 0;
								wordColorHighCount ++;
							}
						}
					}
					
					if (wordColorHighCount == 0 && m > wordCoreWidth) {
						// set the i value
						i = m;
						wordStartWidth = m;
						System.out.println("word end: " + m);
						break;
					}
					else
						wordColorHighCount = 0 ;
				}

				// reset for the next word
				colorCount=new int[num];
				colorAvarageL = new float[num];
				isWordStart = false;
				wordColorCoreValue = 0;
			}
		}
		
		Image tempImg=Toolkit.getDefaultToolkit().createImage(new MemoryImageSource(iw,ih, analyzePixels, 0, iw));
		image = new BufferedImage(tempImg.getWidth(null),tempImg.getHeight(null), BufferedImage.TYPE_INT_RGB ); 
		image.createGraphics().drawImage(tempImg, 0, 0, null); 
	}
	
	public void filterByHSBLight(int iLight, int ignoreHigh){
		PixelGrabber pg = new PixelGrabber(image.getSource(), 0, 0, iw, ih, pixels, 0, iw); 
		try { 
			pg.grabPixels(); 
		} catch (InterruptedException e) { e.printStackTrace(); }
			
		//颜色gap
		float gapValue = 30.0f;
		
		ColorModel cm = ColorModel.getRGBdefault(); 
		
		// 50 width is used to seperated the words by 10 width
		int[] analyzePixels = new int[iw*ih];
		for (int i = 0; i < analyzePixels.length ; i++) { 
			analyzePixels[i] = -1;
		}
		int[] resultPixels = new int[(iw+50)*ih];
		for (int i = 0; i < resultPixels.length ; i++) { 
			resultPixels[i] = -1;
		}

		float[] avarageHsbH = new float[iw];
		float[][] hsbH = new float[iw][ih];
		// scan the high 
		for (int i = 0; i < iw  ; i++) { 
			// high, ignore the ignoreHigh
			for (int j = ignoreHigh; j < ih-ignoreHigh ; j++) { 
				int red = cm.getRed(pixels[iw*j+i]);
				int green  = cm.getGreen(pixels[iw*j+i]);
				int blue = cm.getBlue(pixels[iw*j+i]);
				
				if (red + green + blue > 700) continue;
				
				float[] hsbValue = rgb2hls(red, green, blue );
				// ignore the light > iLight
				if (hsbValue[1] > iLight ) continue;
				
				analyzePixels[iw*j+i] = pixels[iw*j+i];
				hsbH[i][j] = hsbValue[0];
				if ( avarageHsbH[i] == 0 ) {
					avarageHsbH[i] = hsbValue[0];
				}
				else {
					avarageHsbH[i] = (avarageHsbH[i] + hsbValue[0]) / 2;
				}
			} 
		}
		
		// Separate the word
		int startWidth = 0, endWidth = 0, wordWidth = 20, wordGapWidth = 0, step = 0;
		float wordHsbH = 0;
		int[] wordHsbHCount = new int[wordWidth];
		float[] wordAvarageHsbH = new float[wordWidth];
//
//		for (int i = 0; i < iw-2  ; i++) {
//			if (avarageHsbH[i] != 0) {
//				for (int j = 1; j < wordWidth; j++){
//					if ( wordAvarageHsbH[j] == 0 ) 
//						wordAvarageHsbH[j] = avarageHsbH[i];
//					else if ( Math.abs( avarageHsbH[i] - wordAvarageHsbH[j]) < gapValue ) {
//						wordHsbHCount[j] ++;
//					}
//				}
//				step ++;
//				if (startWidth == 0) startWidth = i;
//			}
//
//			// word found
//			if (step == wordWidth) {
//				int index = 0, countValue = 0;
//				for (int m = 0; m < wordWidth; m++){
//					if (countValue < wordHsbHCount[m] ) {
//						countValue = wordHsbHCount[m];
//						index = m;
//					}
//				}
//
//				wordHsbH = wordAvarageHsbH[index];
//				System.out.println("wordHsbH -----: " + wordHsbH);
//
//				// one word found
//				int pointCount = 0;
//				for (int m = startWidth; m < iw  ; m++) { 
//					for (int n = ignoreHigh; n < ih-ignoreHigh ; n++) { 
//						if (hsbH[m][n] == 0) continue;  // ignore white point
//						if ( Math.abs( wordHsbH - hsbH[m][n]) < gapValue ){
//							resultPixels[(iw+50)*n+m+wordGapWidth] = analyzePixels[iw*n+m];
//							pointCount ++ ;
//						}
//					}
//					if (pointCount == 0) {
//						i = m;
//						System.out.println("iiiiiiiiiiiiiiiiii: " + i);
//						break;
//					}
//					else pointCount = 0;
//				}
//
//				// reset the value
//				startWidth = 0;
//				endWidth = 0;
//				step = 0;
//				wordGapWidth = wordGapWidth + 10;
//				for (int j = 1; j < wordWidth; j++){
//					wordAvarageHsbH[j] = 0;
//					wordHsbHCount[j] = 0;
//				}
//			}
//
//		}
		
		// 20, 36, 54
		for (int m = 1; m < iw  ; m++) { 
			for (int n = ignoreHigh; n < ih-ignoreHigh ; n++) { 
				if (hsbH[m][n] == 0) continue;  // ignore white point
				resultPixels[(iw+50)*n+m+wordGapWidth] = analyzePixels[iw*n+m];
			}
			if (m == 20 || m == 36 || m == 54) {
				wordGapWidth = wordGapWidth + 10;
			}
		}
		Image tempImg=Toolkit.getDefaultToolkit().createImage(new MemoryImageSource(iw+50,ih, resultPixels, 0, iw+50));
		image = new BufferedImage(tempImg.getWidth(null),tempImg.getHeight(null), BufferedImage.TYPE_INT_RGB ); 
		image.createGraphics().drawImage(tempImg, 0, 0, null); 
	}
	
	public void filterTheHSB(int iHsbH, int iLight, float gapValue){
		PixelGrabber pg = new PixelGrabber(image.getSource(), 0, 0, iw, ih, pixels, 0, iw); 
		try { 
			pg.grabPixels(); 
		} catch (InterruptedException e) { e.printStackTrace(); }
		
		ColorModel cm = ColorModel.getRGBdefault(); 
		
		// 
		int[] analyzePixels = new int[iw*ih];
		for (int i = 0; i < analyzePixels.length ; i++) { 
			analyzePixels[i] = -1;
		}
		
		// scan the high 
		for (int i = 3; i < iw-3 ; i++) { 
			// high, ignore the ignoreHigh
			for (int j = 3; j < ih-3 ; j++) { 
				int red = cm.getRed(pixels[iw*j+i]);
				int green  = cm.getGreen(pixels[iw*j+i]);
				int blue = cm.getBlue(pixels[iw*j+i]);
				
				if (red + green + blue > 700) continue;
				
				float[] hsbValue = rgb2hls(red, green, blue );
				// ignore such H && light
				if (hsbValue[0] > iHsbH - gapValue && hsbValue[0] < iHsbH + gapValue && 
					hsbValue[1] > iLight - gapValue && hsbValue[1] < iLight + gapValue	)
					continue;
				
				analyzePixels[iw*j+i] = pixels[iw*j+i];
			} 
		}
		
		Image tempImg=Toolkit.getDefaultToolkit().createImage(new MemoryImageSource(iw,ih, analyzePixels, 0, iw));
		image = new BufferedImage(tempImg.getWidth(null),tempImg.getHeight(null), BufferedImage.TYPE_INT_RGB ); 
		image.createGraphics().drawImage(tempImg, 0, 0, null); 
	}
	
	public void greyJSPix(String input, String output) throws IOException {
		FileInputStream fin=new FileInputStream("D:\\001296\\tmp\\shandong\\"+input+".jpg"); 
		image = ImageIO.read(fin);

		changeGrey(140);
		getGrey(); 
		getBrighten();
		BufferedImage bi=getProcessedImg(); 

		String pname= "D:\\001296\\tmp\\shandong\\tmp\\"+output+"";
		File file = new File(pname+".jpg");
		ImageIO.write(bi, "jpg", file); 
	}
	
	public void greyJXPix(String input, String output) throws IOException {
		FileInputStream fin=new FileInputStream("D:\\001296\\tmp\\jiangxi\\"+input+".jpg"); 
		image = ImageIO.read(fin);

		removeLowBase(300);
		changeGrey(210);
		getGrey(); 
		getBrighten();
		BufferedImage bi=getProcessedImg(); 

		String pname= "D:\\001296\\tmp\\jiangxi\\tmp\\"+output+"";
		File file = new File(pname+".jpg");
		ImageIO.write(bi, "jpg", file); 
	}
	
	public BufferedImage getGrey() { 
		ColorConvertOp ccp=new ColorConvertOp(ColorSpace.getInstance(ColorSpace.CS_GRAY), null); 
		return image=ccp.filter(image,null); 
	} 
	
	//Brighten using a linear formula that increases all color values 
	public BufferedImage getBrighten() { 
		RescaleOp rop=new RescaleOp(1.25f, 0, null);
		return image=rop.filter(image,null);
	} 
	
	//Blur by "convolving" the image with a matrix 
	public BufferedImage getBlur() { 
		float[] data = { .1111f, .1111f, .1111f, .1111f, .1111f, .1111f, .1111f, .1111f, .1111f, }; 
		ConvolveOp cop = new ConvolveOp(new Kernel(3, 3, data));
		return image=cop.filter(image,null); 
	} 
	
	// Sharpen by using a different matrix 
	public BufferedImage getSharpen() { 
		float[] data = { 0.0f, -0.75f, 0.0f, -0.75f, 4.0f, -0.75f, 0.0f, -0.75f, 0.0f}; 
		ConvolveOp cop = new ConvolveOp(new Kernel(3, 3, data));
		return image=cop.filter(image,null); 
	} 
	
	// 11) Rotate the image 180 degrees about its center point
	public BufferedImage getRotate() { 
		AffineTransformOp atop=new AffineTransformOp(AffineTransform.getRotateInstance(Math.PI,image.getWidth()/2,image.getHeight()/2), AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
		return image=atop.filter(image,null); 
	} 
	
	public BufferedImage getProcessedImg() { 
		return image; 
	} 
	
	public String getCode(String lang) {		
		BufferedImage bo = null;
		bo=getProcessedImg(); 
		File file = new File("C:\\tmp.jpg");
		try {
			ImageIO.write(bo, "jpg", file);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
	    TessBaseAPI api = new TessBaseAPI();
	    
	    Loader lod = new Loader();
//	    System.out.println( System.getProperty("user.dir"));
	    log.info("========log====="+System.getProperty("user.dir"));
//	   // File file1 = new File("/lhlabc");//取得根目录
//	    try {
//			file1.createNewFile();
//		} catch (IOException e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		}
	    // Initialize tesseract-ocr with English, without specifying tessdata path
	    if (api.Init("/", lang) != 0) {
	        System.err.println("Could not initialize tesseract.");
//	        try {
//				Thread.sleep(100000);
//			} catch (InterruptedException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
	        System.exit(1);
	    }

	    // Open input image with leptonica library
	    // pixReadMem
	    PIX image = pixRead("C:\\tmp.jpg");
	    api.SetImage(image);
	    // Get OCR result
	    BytePointer outText = api.GetUTF8Text();
	    String codeResult = outText.getString();
	    // remove \n\n
	    if ( codeResult.length()>2 ){
	    	codeResult = codeResult.substring(0, codeResult.length()-2);
	    }
	    
	    codeResult = codeResult.replaceAll(" ", "");
	    codeResult = codeResult.replaceAll("'", "");
	    
	    return codeResult;
	}
	
	public static float[] rgb2hls(int rgbR, int rgbG, int rgbB) {  
	    assert 0 <= rgbR && rgbR <= 255;  
	    assert 0 <= rgbG && rgbG <= 255;  
	    assert 0 <= rgbB && rgbB <= 255;  
	    int[] rgb = new int[] { rgbR, rgbG, rgbB };  
	    Arrays.sort(rgb);  
	    int max = rgb[2];  
	    int min = rgb[0];
	    int diff = max - min;
	    int sum = max + min;
	  
	    float hlsL = sum / 2f / 255f * 240 ;  
	    
	    float hlsS = 0.0f;
	    if ( max == min )
	    	hlsS = 0.0f;
	    else {
	    	if ((max + min )/510f <= 0.5f) 
	    		hlsS = diff * 240f / sum ;
	    	else 
	    		hlsS = diff * 240f / (510 - sum ) ;
	    }
	  
	    float hlsH = 0;  

	    if (max == rgbR && rgbG >= rgbB) {  
	        hlsH = (rgbG - rgbB) * 60f / diff + 0;  
	    } else if (max == rgbR && rgbG < rgbB) {  
	        hlsH = (rgbG - rgbB) * 60f / diff + 360;  
	    } else if (max == rgbG) {  
	        hlsH = (rgbB - rgbR) * 60f / diff + 120;  
	    } else if (max == rgbB) {  
	        hlsH = (rgbR - rgbG) * 60f / diff + 240;  
	    }  
	    // special resolve, does not know the reason now
	    hlsH = hlsH * 2f / 3f;
	  
	    return new float[] { hlsH,  hlsL, hlsS};  
	}  
	 
	/**
	 * this function has not be verified, it may have problem
	 * @param h
	 * @param s
	 * @param v
	 * @return
	 */
	public static int[] hsb2rgb(float h, float s, float v) {  
	    assert Float.compare(h, 0.0f) >= 0 && Float.compare(h, 360.0f) <= 0;  
	    assert Float.compare(s, 0.0f) >= 0 && Float.compare(s, 1.0f) <= 0;  
	    assert Float.compare(v, 0.0f) >= 0 && Float.compare(v, 1.0f) <= 0;  
	  
	    float r = 0, g = 0, b = 0;  
	    int i = (int) ((h / 60) % 6);  
	    float f = (h / 60) - i;  
	    float p = v * (1 - s);  
	    float q = v * (1 - f * s);  
	    float t = v * (1 - (1 - f) * s);  
	    switch (i) {  
	    case 0:  
	        r = v;  
	        g = t;  
	        b = p;  
	        break;  
	    case 1:  
	        r = q;  
	        g = v;  
	        b = p;  
	        break;  
	    case 2:  
	        r = p;  
	        g = v;  
	        b = t;  
	        break;  
	    case 3:  
	        r = p;  
	        g = q;  
	        b = v;  
	        break;  
	    case 4:  
	        r = t;  
	        g = p;  
	        b = v;  
	        break;  
	    case 5:  
	        r = v;  
	        g = p;  
	        b = q;  
	        break;  
	    default:  
	        break;  
	    }  
	    return new int[] { (int) (r * 255.0), (int) (g * 255.0),  
	            (int) (b * 255.0) };  
	}

	    
	private boolean isMatches(String codeValue, String regex){
//	      *各种字符的unicode编码的范围：
//	      * 汉字：[0x4e00,0x9fa5]（或十进制[19968,40869]）
//	      * 数字：[0x30,0x39]（或十进制[48, 57]）
//	      *小写字母：[0x61,0x7a]（或十进制[97, 122]）
//	      * 大写字母：[0x41,0x5a]（或十进制[65, 90]）
//		String regex = "^[a-z0-9A-Z\u4e00-\u9fa5]+$";   ^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{8,16}$

		//remove the /0
		if (codeValue.length() < 1 ) return false;
		
		if (codeValue.matches(regex)){
			return true;
		}
		else{
			return false;
		}
	}
	
	public String getSDCode() {	
//		//山东
		changeGrey(140);
		getGrey(); 
		getBrighten();

	    return getCode("shandong");
	}
	
	public String getJSCode() {
		//江苏
		String codeValue = "";
		BufferedImage originalImage = image; 
		boolean isMathed =false;
		String[] codeValues = new String[20];
		
		for(int greyValue=190, i=0; greyValue>=160; greyValue = greyValue -2, i++){
			jsEcrateCircle(550);  // 江苏图片需要预处理：去掉圆圈
			changeGrey(greyValue);
			getGrey(); 
			addBlack();  // 江苏灰度后的图片需要补充黑点
			getBrighten();
			
			codeValue = getCode("ENG");
			codeValues[i] = codeValue.substring( 0, codeValue.length() );

			if (isMatches(codeValue.substring(0, codeValue.length() ), "^[0-9A-Za-z]{6}$") ){
				isMathed = true;
				break;
			}
			else
			{
				image = originalImage;
				System.out.println("wrong code value: " + codeValue + "  ---try again by greyValue " + greyValue);
			}
		}
		
		// try to guess the codeValue by analyst above codeValues
		if (! isMathed){
			System.out.println("code value to be decided by above values");
			byte[] codeTmp = new byte[6];
			byte[][] charValue = new byte[6][codeValues.length];
			int[][] charCount = new int[6][codeValues.length];
			byte[] byteTmp = null;
			boolean isFound = false;
			for (int i=0; i< codeValues.length; i++){
				if (codeValues[i]==null || codeValues[i].length() != 6) {
					continue;
				}
				// char count
				byteTmp = codeValues[i].getBytes();
				for (int j = 0; j<6; j++){
					byte[] charTmp = {byteTmp[j]};
					if ( isMatches(new String(charTmp), "^[0-9A-Za-z]{1}$") ) {
						isFound = false;
						for (int k=0; k < codeValues.length; k++){ 
							if (byteTmp[j] == charValue[j][k] ) {
								charValue[j][k] = byteTmp[j]; 
								charCount[j][k]++;
								isFound = true;
							}
						}
						if (! isFound){
							charValue[j][i] = byteTmp[j]; 
							charCount[j][i] = 1;
						}
					}
				}
			}
			// set codeValue
			int maxPos = 0;
			for (int i=0; i< 6; i++){
				int maxCount = 0;
				for (int j=0; j< codeValues.length; j++){
					if ( charCount[i][j] > maxCount ) {
						maxCount = charCount[i][j];
						maxPos = j;
					}
				}
				if (maxCount == 0){
					// if no reasonable char has been found, replaced by A,B,C, etc ...
					codeTmp[i] = (byte)(100+i*2);
				}
				else{
					codeTmp[i] = charValue[i][maxPos];
					byte[] charTmp = {codeTmp[i]};
					if ( isMatches(new String(charTmp), "^[0-9A-Za-z]{1}$") ){
						
					}
				}
			}
			codeValue = new String(codeTmp);
		}
		
		return codeValue;
	}
	
	public String getAHCode() {
		//安徽：three type of picture
		String codeValue = "";
		BufferedImage originalImage = image; 
		
		// type 1, blue base, white word: four Chinese words
		changeGrey(210);
		getGrey(); 
		getBrighten();
		codeValue =  getCode("chi_sim");
		if (codeValue.length() < 2) return "";
		codeValue = codeValue.replaceAll("\n", "");
		codeValue = codeValue.replaceAll("、", "");
		codeValue = codeValue.replaceAll("。", "");
		codeValue = codeValue.replaceAll("夭", "天");
		codeValue = codeValue.replaceAll("逃之天天", "逃之夭夭");

		return codeValue;
		
		// type 2, blue base, white word: for pinying
//		image = originalImage;
//		changeGrey(210);
		
		// // type 3, grey base,
//		image = originalImage;
//		ahRemoveBase();
//		changeGrey(210);
//		getGrey(); 
//		removeSingleBlack();
//		getBrighten();
//		codeValue =  getCode("chi_sim");

//	    return getCode("chi_sim");
	}
	
//	public String getSHCode() {
//		// 上海
//		String codeValue = "";
//		BufferedImage originalImage = image; 
//		
//		// find the line, and update it
//		
//		// remove single point and the points, which are isolated and less than seven
//		
//		// 
//
////		changeGrey(210);
////		getGrey(); 
////		getBrighten();
////		
//		HoughLineFilter hf = new HoughLineFilter();
//		hf.filter(image, image);
//		
//		File file = new File("D:\\tmp.jpg");
//		try {
//			ImageIO.write(image, "jpg", file);
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} 
		

//		codeValue =  getCode("chi_sim");
		
	    //return codeValue;
	//}

	public String getJXTestCode() {
		// 江西
		String codeValue = "", code = "";
		BufferedImage originalImage = image; 

		// remove background
		removeLowBase(300);
		changeGrey(210);
		getGrey(); 
		getBrighten();
		removeEdegeBlack(6);
		removeSingleBlack(300);

		
		
//		codeValue = ct.technologySdCode(code);
        
//		codeValue =  getCode("chi_sim");
		codeValue = analyseTiltWord() ;
		System.out.println("code "+code+"    code value: " + codeValue );
		
	    return codeValue;
	}
	
	public String getFJCode() {
		// 福建
		// use the default random value to cheat the code system
		String[] codeValue = {"文过饰非","谈笑风生","好好先生","勇往直前","金玉满堂","人生如梦","肆无忌惮","声色犬马","铁石心汤","病人膏肓",
				              "0","1","2","3","4","5","6","7","8","9",
				              "星火燎原","草场莺飞","为虎作伥","壮志凌云","悬梁刺股","叹为观止","万家灯火","行尸走肉","精忠报国","细水长流",
				              "一路顺风","国色天香","尾大不掉","舌战群儒","缘木求鱼","万里长城","穿针引线","东张西望","瓜田李下","龙虎风云",
				              "-1","-2","-3","-4","-5","-6","时来运转","-7","暗度陈仓","随波逐流",
				              "时不我待","欢聚一堂","神机妙算","画龙点睛","韬光养晦","淋漓尽致","相濡以沫","锦上添花","知法犯法","和光同尘"};

		// remove background
		Random random = new Random();
		int value = random.nextInt(60);
		if (value == 60) value = 0;
		
	    return codeValue[value];
	}
	
	
	public String getHBCode() {
		// 湖北
		String codeValue = "";
		BufferedImage originalImage = image; 
		
		removeGreyPoint();
		removeLowBase(600);
		removeSingleBlack(600);
		changeGrey(250);
		removeSingleBlack(600);
//		getGrey(); 
//		getBrighten();
		
		codeValue =  getCode("chi_sim");
		if (codeValue.length() < 2) return "";
		codeValue = codeValue.replaceAll("\n", "");
		codeValue = codeValue.replaceAll("、", "");
		codeValue = codeValue.replaceAll("。", "");
		codeValue = codeValue.replaceAll("夭", "天");
		codeValue = codeValue.replaceAll("逃之天天", "逃之夭夭");

		return codeValue;
	}
	

	
	
	  public String getSpideCode(){
	    String codeValue = ""; 
	    String code = "";
	    BufferedImage originalImage = this.image;

	    filterSpideHSB(110);
	   
	    codeValue = getCode("chi_sim");

	    return codeValue;
	  }
	  
	  public void filterSpideHSB(int iLight){
       
	    PixelGrabber pg = new PixelGrabber(this.image.getSource(), 0, 0, this.iw, this.ih, this.pixels, 0, this.iw);
	    try {
	    	pg.grabPixels(); 
	      } catch (InterruptedException e) {
	    	  e.printStackTrace();
	    }
	    ColorModel cm = ColorModel.getRGBdefault();

	    int[] analyzePixels = new int[this.iw * this.ih];
	    for (int i = 0; i < analyzePixels.length; ++i) {
	      analyzePixels[i] = -1;
	    }

	    for (int i = 3; i < this.iw - 3; ++i)
	    {
	      for (int j = 3; j < this.ih - 3; ++j) {
	        int red = cm.getRed(this.pixels[(this.iw * j + i)]);
	        int green = cm.getGreen(this.pixels[(this.iw * j + i)]);
	        int blue = cm.getBlue(this.pixels[(this.iw * j + i)]);

	        if (red + green + blue > 700) continue;

	        float[] hsbValue = rgb2hls(red, green, blue);

	        if (hsbValue[1] > iLight)
	          continue;

	        analyzePixels[(this.iw * j + i)] = this.pixels[(this.iw * j + i)];
	      }
	    }
		Image tempImg=Toolkit.getDefaultToolkit().createImage(new MemoryImageSource(iw,ih, analyzePixels, 0, iw));
		image = new BufferedImage(tempImg.getWidth(null),tempImg.getHeight(null), BufferedImage.TYPE_INT_RGB ); 
		image.createGraphics().drawImage(tempImg, 0, 0, null); 
	  }
	
//	public static void main(String[] args){
//		FileInputStream fin;
//		BufferedImage bi = null;
//		
//		System.out.println( System.getProperty("java.library.path"));
//		// convert the original picture file to picGrey.jpg, which to be worked
//		try {
////			fin = new FileInputStream("D:\\001296\\桌面\\tmp\\pic\\1.jpg");
//			fin = new FileInputStream("D:\\test.jpg");
////			fin = new FileInputStream("D:\\11.jpg");
//			try {
//				bi=ImageIO.read(fin);
//				PicGrey pic = new PicGrey(null,bi);
//				String code=pic.getSpideCode();
//				System.out.println("========="+code);	
//			} catch (IOException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			} 
//		} catch (FileNotFoundException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} 
//		

//	}
}