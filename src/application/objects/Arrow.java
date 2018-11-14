package application.objects;

import javafx.scene.paint.Color;
import application.objects.Link;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.shape.Polygon;

public class Arrow extends Path {

	private static final double arrowHeadSize = 10.0;
	
	public Arrow(int type) {
		
		this.getStyleClass().add("link");
		// can be used for filling!
		//strokeProperty().bind(fillProperty());
        //setFill(Color.BLACK);
		
		//updateLocation(10, 10, 0, -10, 10);
	}
	
	/**
	 * Utilized https://gist.github.com/kn0412/2086581e98a32c8dfa1f69772f14bca4 in process of designing arrowheads
	 * 
	 * 
	 * @param x
	 * @param y
	 * @param orientation
	 * @param arrowheadShaft
	 * @param spacingOffset
	 */
	public void updateLocation(int x, int y, Link.arrowFacing orientation, int arrowheadShaft, int spacingOffset) {
        //spacingOffset is can be x or y depending on orientation
		//arrowheadShaft starts negative
		
		getElements().clear();
		
		switch (orientation) {
		case LEFT:
		{
			arrowheadShaft=-arrowheadShaft;		
			break;
		}
		case RIGHT:
		{
		
			break;
		}
		case UP:
		{
			break;
		}
		case DOWN:
		{
			break;
		}
		}
		
        getElements().add(new MoveTo(x, y + spacingOffset));
        getElements().add(new LineTo(x + arrowheadShaft , y + spacingOffset));
        getElements().add(new MoveTo(x, y + spacingOffset));
        
        //atan (distance y, distance x) - pi/2
        double angle = Math.atan2((y - y), (arrowheadShaft)) - Math.PI / 2.0;
        double sin = Math.sin(angle);
        double cos = Math.cos(angle);
        
        double x1 = (- 1.0 / 2.0 * cos + Math.sqrt(3) / 2 * sin) * arrowHeadSize + x + (1.5*arrowheadShaft);
        double y1 = (- 1.0 / 2.0 * sin - Math.sqrt(3) / 2 * cos) * arrowHeadSize + y+ spacingOffset;
        
        double x2 = (1.0 / 2.0 * cos + Math.sqrt(3) / 2 * sin) * arrowHeadSize + x + (1.5*arrowheadShaft);
        double y2 = (1.0 / 2.0 * sin - Math.sqrt(3) / 2 * cos) * arrowHeadSize + y+spacingOffset;
        
        getElements().add(new LineTo(x1, y1));
        getElements().add(new MoveTo(x2, y2));
        //getElements().add(new LineTo(x+arrowheadShaft, y + spacingOffset));
        getElements().add(new LineTo(x, y + spacingOffset));
	}
	




}
