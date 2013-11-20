package drawing;

import java.awt.*;
import java.awt.event.*;
import javax.swing.JPanel;
import java.util.*;

public class DrawingPanel extends JPanel {
	
	private DrawingListener drawingListener;
	private Stack<Point> points;
	private Color penColor;
	private char penShape;
	private String[] penSizes = {"small", "medium", "large"};
	private int penDimension;
	private boolean inDrawingMode;

	//These instance data are for the redo/undo behaviors
	private Stack<Integer> numPointsPerDraw;
	private Stack<Integer> tempNumPointsPerDraw;
	private Stack<Point> tempPoints;
	private int countPointsPerDraw;
	
	public DrawingPanel() {
		
		points = new Stack<Point>();
		numPointsPerDraw = new Stack<Integer>();
		tempNumPointsPerDraw = new Stack<Integer>();
		tempPoints = new Stack<Point>();
		
		setPreferredSize(new Dimension(540, 440));
		setBackground( Color.white );
		
		drawingListener = new DrawingListener();
		addMouseListener( drawingListener );
		addMouseMotionListener( drawingListener );
		
		penColor = DrawingPoint.DEFAULT_COLOR;
		penShape = DrawingPoint.DEFAULT_BRUSH_SHAPE;
		penDimension = DrawingPoint.DEFAULT_PEN_DIMENSION;
		
		inDrawingMode = false;
	}
	
	public void paintComponent( Graphics g ) {
		
		super.paintComponent( g );
		
		int x, y;
		DrawingPoint p;
		for( Point point : points ) {
			p = ( DrawingPoint ) point;
			x = (int) p.getX();
			y = (int) p.getY();
		    g.setColor( ((DrawingPoint) p).getPointColor() );
		    int penDimen = p.getPenDimension();
			switch( ((DrawingPoint) p).getPointShape() ) {
				case 'r': g.fillOval(x, y, penDimen, penDimen ); break;
				case 's': g.fillRect(x, y, penDimen, penDimen ); break;
			}
		}
	}

	public void clearPoints() {
		points.clear();
		
		//clear the lists that keeps track of points and number of points
		//(These are for the undo/redo features)
		numPointsPerDraw.clear();
		tempPoints.clear();
		tempNumPointsPerDraw.clear();
		
		repaint();
	}
	
	public boolean isInDrawingMode() {
		return this.inDrawingMode;
	}
	
	public void undoIt() {
		if( ! numPointsPerDraw.isEmpty() ) {
			tempNumPointsPerDraw.push( numPointsPerDraw.pop() );
		}
		if ( ! points.isEmpty() ) {
			int pointCount = tempNumPointsPerDraw.peek();
			for( int time = 0; time < pointCount; time++ ) {
				tempPoints.push( points.pop() );
			}
			repaint();
		}
	}
	
	public void redoIt() {
		int pointCount = 0;
		if( ! tempNumPointsPerDraw.isEmpty() ) {
			pointCount = tempNumPointsPerDraw.peek();
			numPointsPerDraw.push( tempNumPointsPerDraw.pop() );
		}
		if( ! tempPoints.isEmpty() ) {
			for( int time = 0; time < pointCount; time++ ) {
				points.push( tempPoints.pop() );
			}
			repaint();
		}
	}
	
	public void setPenColor(Color penColor) {
		this.penColor = penColor;
	}
	
	public Color getPenColor() {
		return this.penColor;
	}

	public void setPenShape(char penShape) {
		this.penShape = penShape;
	}


	public String[] getPenSizes() {
		return penSizes;
	}

	public int getPenDimension() {
		return penDimension;
	}

	public void setPenDimension(int penDimension) {
		this.penDimension = penDimension;
	}

	private class DrawingListener implements MouseListener,
	                                         MouseMotionListener {

		@Override
		public void mouseDragged(MouseEvent e) {
		}

		@Override
		public void mouseMoved(MouseEvent e) {
			doPaint( e );
		}
		
		@Override
		public void mouseClicked(MouseEvent e) {
			if( ! inDrawingMode ) {
				inDrawingMode = true;
			}
			else {
				//keep track of the number of points for each draw
				//(for redo/undo feature)
				numPointsPerDraw.add( countPointsPerDraw );
				
				//reset the point counter (for redo/undo feature)
				countPointsPerDraw = 0;
				
				inDrawingMode = false;
			}
		}

		@Override
		public void mousePressed(MouseEvent e) {
		}

		@Override
		public void mouseReleased(MouseEvent e) {
		}

		@Override
		public void mouseEntered(MouseEvent e) {
		}

		@Override
		public void mouseExited(MouseEvent e) {
		}
		
		//a helper method that determines if it should draw
		private void doPaint(MouseEvent e ) {
			if( inDrawingMode ) {
				//get point and store as a customized DrawingPoint
				Point point = e.getPoint();
			    Point drawingPoint = new DrawingPoint( 
							point, penColor, penShape, penDimension );
				points.add( drawingPoint );
				
				//increment the number of points for each draw
				//This will be used for the redo/undo behaviors
				countPointsPerDraw++;
				
				repaint();
			}
		}
	}
}
