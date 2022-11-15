import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import javax.swing.*;

public class ImageProcessingApplication
{
	public static void main(String[] args)
	{
		String str = "AST10106 (Semester A, 2014/15) - Introduction to Programming: Image Processing";
		ImageFrame iFrameOriginal = new ImageFrame(str);		
		iFrameOriginal.showWin();
		iFrameOriginal.setExtendedState(Frame.MAXIMIZED_BOTH);
	}
}