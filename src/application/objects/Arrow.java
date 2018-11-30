package application.objects;

import application.objects.Link;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
// import javafx.scene.shape.Polygon;

public class Arrow extends Path {

	private int x;
	private int y;
	private Link.arrowFacing orientation;
	private int arrowheadShaft;
	private Boolean redrawing = false;

	private static final double ARROWHEAD_SIZE = 10.0;
	private int linkType;

	/**
	 * Constructor for the Arrow
	 * 
	 * @param type
	 *            Link types by int
	 * 
	 *            0 = "Dependency", 1 = "Assocation", 2 = "Generalization", 3 =
	 *            "Aggregate", 4 = "Composition"
	 * 
	 */
	public Arrow(int type) {

		this.getStyleClass().add("link");

		linkType = type;

		if (linkType == 4) {
			strokeProperty().bind(fillProperty());
			this.getStyleClass().add("arrowFilled");
		}
		else
		{
			strokeProperty().unbind();
			this.getStyleClass().add("arrowBlank");
		}
	}

	public void setType(int type) {
		linkType = type;

		if (linkType == 4) {
			strokeProperty().bind(fillProperty());
			this.getStyleClass().remove("arrowBlank");
			this.getStyleClass().add("arrowFilled");
		} else {
			strokeProperty().unbind();
			this.getStyleClass().remove("arrowFilled");
			this.getStyleClass().add("arrowBlank");
		}

		redrawing = true;
		redraw();
		redrawing = false;
	}

	/**
	 * Draws the appropriate arrowhead with the appropriate orientation at the
	 * appropriate location
	 * 
	 * @param x
	 *            Tip of arrow in X axis
	 * @param y
	 *            Tip of arrow in Y axis
	 * @param orientation
	 *            Direction arrow is facing
	 * @param arrowheadShaft
	 *            Length of the arrow
	 */
	public void updateLocation(int xIn, int yIn, Link.arrowFacing orientationIn, int arrowheadShaftIn) {

		x = xIn;
		y = yIn;
		orientation = orientationIn;
		arrowheadShaft = arrowheadShaftIn;

		redraw();
	}

	public void redraw() {
		getElements().clear();

		int XArrowheadShaft = 0;
		int YArrowheadShaft = 0;
		double XArrowheadAngleMul = 0;
		double YArrowheadAngleMul = 0;
		double angle = 0;

		switch (orientation) {
		case LEFT: {
			if (!redrawing)
				arrowheadShaft = -arrowheadShaft;
		}
		case RIGHT: {
			XArrowheadShaft = arrowheadShaft;
			if (linkType == 3 || linkType == 4)
				XArrowheadAngleMul = 1.35 * arrowheadShaft;
			else
				XArrowheadAngleMul = 1.5 * arrowheadShaft;

			angle = Math.atan2(0, arrowheadShaft) - Math.PI / 2.0;

			break;
		}
		case UP: {
			if (!redrawing)
				arrowheadShaft = -arrowheadShaft;
		}
		case DOWN: {
			YArrowheadShaft = arrowheadShaft;
			if (linkType == 3 || linkType == 4)
				YArrowheadAngleMul = 1.35 * arrowheadShaft;
			else
				YArrowheadAngleMul = 1.5 * arrowheadShaft;

			angle = Math.atan2(arrowheadShaft, 0) - Math.PI / 2.0;

			break;
		}
		}

		double sin = Math.sin(angle);
		double cos = Math.cos(angle);

		double x1 = (-1.0 / 2.0 * cos + Math.sqrt(3) / 2 * sin) * ARROWHEAD_SIZE + x + XArrowheadAngleMul;
		double y1 = (-1.0 / 2.0 * sin - Math.sqrt(3) / 2 * cos) * ARROWHEAD_SIZE + y + YArrowheadAngleMul;

		double x2 = (1.0 / 2.0 * cos + Math.sqrt(3) / 2 * sin) * ARROWHEAD_SIZE + x + XArrowheadAngleMul;
		double y2 = (1.0 / 2.0 * sin - Math.sqrt(3) / 2 * cos) * ARROWHEAD_SIZE + y + YArrowheadAngleMul;

		getElements().add(new MoveTo(x, y));

		// note, -1 is the result of the user NOT selecting an arrow type
		if (linkType == 0 || linkType == 1 || linkType == -1) {
			getElements().add(new LineTo(x + XArrowheadShaft, y + YArrowheadShaft));
			getElements().add(new MoveTo(x, y));

			getElements().add(new LineTo(x1, y1));
			getElements().add(new MoveTo(x2, y2));
			getElements().add(new LineTo(x, y));

		} else if (linkType == 2) {
			getElements().add(new LineTo(x1, y1));
			getElements().add(new LineTo(x2, y2));
			getElements().add(new LineTo(x, y));
			getElements().add(new MoveTo(x2 - (x2 - x1) / 2, y2 - (y2 - y1) / 2));
			getElements().add(new LineTo(x + XArrowheadShaft, y + YArrowheadShaft));
		} else if (linkType == 3 || linkType == 4) {
			getElements().add(new LineTo(x1, y1));
			getElements().add(new LineTo(x + XArrowheadShaft, y + YArrowheadShaft));
			getElements().add(new LineTo(x2, y2));
			getElements().add(new LineTo(x, y));
		}
	}

	/**
	 * erase the arrowhead (likely associated Link is going to be removed)
	 * 
	 */
	public void eraseArrowhead() {
		getElements().clear();
	}
}