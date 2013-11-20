package drawing;

import java.awt.*;

public class DrawingPoint extends Point {
	
	public static final char ROUND = 'r';
	public static final char SQUARE = 's';
	public static final int SMALL = 7;
	public static final int MEDIUM = 12;
	public static final int LARGE = 17;
	public static final int DEFAULT_PEN_DIMENSION = MEDIUM;
	public static final char DEFAULT_BRUSH_SHAPE = ROUND;
	public static final Color DEFAULT_COLOR = Color.red;
	
	private Color pointColor;
	private char pointShape; 
	private int penDimension;
	
	public DrawingPoint( Point point, Color pointColor, 
			             char pointShape, int penDimension) {
		x = (int) point.getX();
		y = (int) point.getY();
		this.pointColor = pointColor;
		this.setPointShape(pointShape);
		this.setPenDimension(penDimension);
	}
	
	public DrawingPoint( Point point ) {
		this( point, DEFAULT_COLOR, DEFAULT_BRUSH_SHAPE, MEDIUM);
	}
	
	public Color getPointColor() {
		return pointColor;
	}
	
	public void setPointColor(Color pointColor) {
		this.pointColor = pointColor;
	}

	public char getPointShape() {
		return pointShape;
	}

	public void setPointShape(char pointShape) {
		this.pointShape = pointShape;
	}

	public int getPenDimension() {
		return penDimension;
	}

	public void setPenDimension(int penDimension) {
		this.penDimension = penDimension;
	}
}
