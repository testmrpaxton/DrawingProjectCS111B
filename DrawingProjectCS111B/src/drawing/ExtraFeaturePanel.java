package drawing;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class ExtraFeaturePanel extends JPanel {
	
	public static final int SLIDER_MIN = 5;
	public static final int SLIDER_MAX = 20;
	public static final int SLIDER_INIT = 12;
	
	private JComboBox<String> penSizeComboBox;
	private ButtonGroup penShapeGroup;
	private JRadioButton roundButton, squareButton, eraseButton;
	private JButton chooseColorButton;
	private ButtonListener buttonListener;
	private DrawingPanel drawingPanel;
	private JButton exportImageButton;
	
	private JSlider penSizeSlider;
	
	public ExtraFeaturePanel(DrawingPanel drawingPanel) {
		setPreferredSize( new Dimension(550, 57) );
		setBackground( Color.darkGray );
		this.drawingPanel = drawingPanel;
		buttonListener = new ButtonListener();
		
		//Extra Credit: the Save Image button allows the user to 
		//              save his/her drawing into an image file
		//Related Class File: ImageExporter.java
		exportImageButton = new JButton("Export");
	    exportImageButton.addActionListener( new ActionListener()
		{
			public void actionPerformed(ActionEvent ae) {
				//Use the customized ImageExporter class to perform the export
				ImageExporter.exportImage( ExtraFeaturePanel.this );
			}
		});
	    add( exportImageButton );
	    
	    //Extra Credit: The More Color button has more choices of colors
	    //              for the user to use. The program uses JColorChooser. 
		chooseColorButton = createChooseColorButton();
		add( chooseColorButton );
		
		roundButton = new JRadioButton( "round", true );
        roundButton.addActionListener( buttonListener );
        squareButton = new JRadioButton("square", true );
        squareButton.addActionListener( buttonListener );
        eraseButton = new JRadioButton("Erase", true);
        eraseButton.addActionListener( buttonListener );
        
        penShapeGroup = new ButtonGroup();
        penShapeGroup.add( roundButton );
        penShapeGroup.add( squareButton );
        penShapeGroup.add( eraseButton );
        
        //Extra Credit: use a slide bar to set the size of the pen
  		penSizeSlider = addPenSizeSlider();
        
  		//Extra Credit: an alternative to select the size of the pen 
  		//using a combo box
        penSizeComboBox = createPenSizeComboBox();
        		
        add( roundButton );
        add( squareButton );
        add( eraseButton );
        add( penSizeSlider );
        add( penSizeComboBox );
       
	}
	
	private JComboBox<String> createPenSizeComboBox() {
		JComboBox<String> penSizeCombo = new JComboBox<String>();
        String[] penSizes = drawingPanel.getPenSizes();
        for( String penSize : penSizes ) {
        	penSizeCombo.addItem( penSize );
        }
        penSizeCombo.addActionListener( buttonListener );
        return penSizeCombo;
	}
	
	private JButton createChooseColorButton() {
		JButton chooseColorButton = new JButton("More Color");
		chooseColorButton.addActionListener(new ActionListener() 
		{
		    public void actionPerformed(ActionEvent ae) 
		    { 
		    	Color chosenColor = JColorChooser.showDialog(
			    		 ExtraFeaturePanel.this, "Choose a color", Color.red);
			    //set default color if no color is returned
			    if(chosenColor == null) chosenColor = Color.green;
			  	ExtraFeaturePanel.this.drawingPanel.setPenColor( chosenColor );
		    }
 		});
		return chooseColorButton;
	}
	
	private JSlider addPenSizeSlider() {
		JSlider penSizeSlider =  new JSlider( JSlider.HORIZONTAL, 
                  SLIDER_MIN, SLIDER_MAX, SLIDER_INIT);
		
		penSizeSlider.addChangeListener( new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				JSlider source = (JSlider) e.getSource();
				int inputPenSize = (int) source.getValue();
				ExtraFeaturePanel.this.drawingPanel.
				              setPenDimension( inputPenSize );
			}
		});
		
		penSizeSlider.setForeground(Color.orange);
		penSizeSlider.setMajorTickSpacing(5);
		penSizeSlider.setMinorTickSpacing(1);
		penSizeSlider.setPaintTicks( true );
		penSizeSlider.setPaintLabels( true );
		return penSizeSlider;
	}
	
	public JPanel getDrawingPanel() {
		return this.drawingPanel;
	}
	
	private class ButtonListener implements ActionListener{
		Color prevColorBeforeErased;

		@Override
		public void actionPerformed(ActionEvent e) {
			//Extra Credit: Erase button
			if ( e.getSource() == eraseButton ) {
				//save color before erasing it
				prevColorBeforeErased = drawingPanel.getPenColor();
				//setting the color of the pen to be the same as background
				drawingPanel.setPenColor( drawingPanel.getBackground());
			} else if( e.getSource() == roundButton ) {
				if( prevColorBeforeErased != null ) {
					drawingPanel.setPenColor( prevColorBeforeErased );
				}
				//set the point shape to be round
				drawingPanel.setPenShape( DrawingPoint.ROUND );
			} else if ( e.getSource() == squareButton ) {
				if( prevColorBeforeErased != null ) {
					drawingPanel.setPenColor( prevColorBeforeErased );
				}
				//set the point shape to be square
				drawingPanel.setPenShape( DrawingPoint.SQUARE );
			}
			setBrushDimension( e );
		}
		
		private void setBrushDimension( ActionEvent e ) {
			if( e.getSource() == penSizeComboBox ) {
				if ( penSizeComboBox.getSelectedItem() == "small" ) {
					drawingPanel.setPenDimension( DrawingPoint.SMALL );
				} else if ( penSizeComboBox.getSelectedItem() == "medium" ) {
					drawingPanel.setPenDimension( DrawingPoint.MEDIUM );
				} else if ( penSizeComboBox.getSelectedItem() == "large" ) {
					drawingPanel.setPenDimension( DrawingPoint.LARGE );
				}
			}
		}
	}
}
