package drawing;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class ControlPanel extends JPanel {
	
	private JRadioButton redButton, yellowButton, blueButton;
	private JButton clearButton, undoButton, redoButton;
	private ButtonListener buttonListener;
	public DrawingPanel drawingPanel;
	private ButtonGroup colorGroup;
	
	public ControlPanel(DrawingPanel drawingPanel) {
		setPreferredSize(new Dimension(530, 50));
		setBackground( Color.gray );
		
		this.drawingPanel = drawingPanel;
		
		buttonListener = new ButtonListener();
		
		redButton = new JRadioButton( "red" , true );
        redButton.addActionListener( buttonListener );
        yellowButton = new JRadioButton( "yellow", true);
        yellowButton.addActionListener( buttonListener );
        blueButton = new JRadioButton("blue", true);
        blueButton.addActionListener( buttonListener );
        
        clearButton = new JButton("clear");
        clearButton.addActionListener( buttonListener );
        
        
        //Extra Credit: Undo operation
        undoButton = new JButton("Undo");
        undoButton.addActionListener( new ActionListener() {
        	public void actionPerformed(ActionEvent e ) {
        		if ( ! ControlPanel.this.drawingPanel.isInDrawingMode()) {
        			ControlPanel.this.drawingPanel.undoIt();
        		}
        	}
        });
        
        //Extra Credit: Redo operation
        redoButton = new JButton("Redo");
        redoButton.addActionListener( new ActionListener() {
        	public void actionPerformed(ActionEvent e ) {
        		if( ! ControlPanel.this.drawingPanel.isInDrawingMode() ) {
        		    ControlPanel.this.drawingPanel.redoIt();
        		}
        		
        	}
        });
        
        colorGroup = new ButtonGroup();
        colorGroup.add( redButton );
        colorGroup.add( yellowButton );
        colorGroup.add( blueButton );
        colorGroup.add( clearButton );
        
        add( redButton );
        add( yellowButton );
        add( blueButton );

        add( undoButton );
        add( redoButton );
        add( clearButton );
	}

	private class ButtonListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			
			if( e.getSource() == redButton ) {
				drawingPanel.setPenColor( Color.red );
			} else if ( e.getSource() == yellowButton ){
                drawingPanel.setPenColor( Color.yellow );
			} else if ( e.getSource() == blueButton ) {
				drawingPanel.setPenColor( Color.blue );
			}
			
			if ( e.getSource() == clearButton ) {
				drawingPanel.clearPoints();
			}
		}
	}
}
