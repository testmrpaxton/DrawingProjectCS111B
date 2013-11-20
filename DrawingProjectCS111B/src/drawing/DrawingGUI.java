package drawing;

import java.awt.*;
import javax.swing.*;

public class DrawingGUI extends JFrame {
  
    //declare GUI components
	private DrawingPanel drawingPanel;
    private ControlPanel controlPanel;
    private ExtraFeaturePanel extraFeaturePanel;
    private Container contentPane;
    private JPanel mainControlPanel;

    public DrawingGUI() {
    	
        super("Draw something!");
        setSize(780,550);
        contentPane = getContentPane();
        contentPane.setLayout( new BorderLayout() );
        contentPane.setBackground( Color.white );
        setResizable( true );
        
        drawingPanel = new DrawingPanel();
        controlPanel = new ControlPanel( drawingPanel );
        extraFeaturePanel = new ExtraFeaturePanel( drawingPanel );        
        
        mainControlPanel = new JPanel( new GridLayout( 2, 1) );
        mainControlPanel.add( controlPanel );
        mainControlPanel.add( extraFeaturePanel );
        
        contentPane.add( drawingPanel, BorderLayout.CENTER );
        contentPane.add( mainControlPanel, BorderLayout.SOUTH );
        
        setVisible( true );
    }
    
    public static void main(String args[]) {
		DrawingGUI frame = new DrawingGUI();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}
