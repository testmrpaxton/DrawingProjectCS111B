package drawing;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.JPanel;

public class ImageExporter {
	
	//This exportImage() method takes the graphics from the panel and 
	//saves it to a file using the JFileChooser class
	public static void exportImage(JPanel panel ) {
		BufferedImage bi = getBufferedImage( panel );
		JFileChooser fileChooser = new JFileChooser();
		if ( fileChooser.showSaveDialog( panel ) == 
			             JFileChooser.APPROVE_OPTION ) {
			saveFile( bi, fileChooser );
		}
	}
	
	public static BufferedImage getBufferedImage (JPanel panel) {
		//create a buffered image to be saved 
		BufferedImage bi =  new BufferedImage( 
		        ((ExtraFeaturePanel) panel).getDrawingPanel().getWidth(),
		        ((ExtraFeaturePanel) panel).getDrawingPanel().getHeight(), 
		        BufferedImage.TYPE_INT_RGB);
		Graphics2D page = bi.createGraphics();
		((ExtraFeaturePanel) panel ).getDrawingPanel().paintAll( page );
		return bi;
	}
	
	public static void saveFile( BufferedImage bi, JFileChooser fileChooser ) {
		File file = fileChooser.getSelectedFile();
		String parentPath = file.getParent();
		String fileName = getFileName( file );
		String absPath = parentPath + "/" + fileName;
		String extension = getExtension( fileName );
		//Write the image to file
		try{
			ImageIO.write(bi, extension , new File( absPath )); 
		} catch (IOException e ) {
			e.printStackTrace();
		}
	}
	
	public static String getFileName( File file ) {
		//when the file name does not have the jpg, gif, bmp, or png ending,
		//the jpg is used as a file extension by default
		Pattern p = Pattern.compile( "^\\w.*.(jpg|gif|bmp|png)$");
		Matcher m = p.matcher( file.getName() );
		String fileName = file.getName();
		if( ! m.matches() ) fileName += ".jpg";
		return fileName;
	}
	
	public static String getExtension(String fileName ) {
		int fileNameLength = fileName.length();
		return fileName.substring(fileNameLength - 3, fileNameLength );
	}
}
