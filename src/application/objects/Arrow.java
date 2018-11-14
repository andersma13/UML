package application.objects;

import javafx.scene.paint.Color;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;

public class Arrow extends Path {

	private static final double arrowHeadSize = 12.0;
	
	public Arrow(int type) {
		super();
		
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
	 * @param arrowheadLength
	 * @param spacingOffset
	 */
	public void updateLocation(int x, int y, int orientation, int arrowheadLength, int spacingOffset) {
        //spacingOffset is can be x or y depending on orientation
		
		getElements().clear();
        getElements().add(new MoveTo(x, y + spacingOffset));
        getElements().add(new LineTo(x + arrowheadLength, y + spacingOffset));
        
        //ArrowHead
        double angle = Math.atan2((y - y), (x+arrowheadLength - x)) - Math.PI / 2.0;
        double sin = Math.sin(angle);
        double cos = Math.cos(angle);
        //point1
        double x1 = (- 1.0 / 2.0 * cos + Math.sqrt(3) / 2 * sin) * arrowHeadSize + x+arrowheadLength;
        double y1 = (- 1.0 / 2.0 * sin - Math.sqrt(3) / 2 * cos) * arrowHeadSize + y+ spacingOffset;
        //point2
        double x2 = (1.0 / 2.0 * cos + Math.sqrt(3) / 2 * sin) * arrowHeadSize + x+arrowheadLength;
        double y2 = (1.0 / 2.0 * sin - Math.sqrt(3) / 2 * cos) * arrowHeadSize + y+spacingOffset;
        
        getElements().add(new LineTo(x1, y1));
        getElements().add(new LineTo(x2, y2));
        getElements().add(new LineTo(x+arrowheadLength, y + spacingOffset));
	}
	

	/**
	 *
	 * @author kn
	 */
	    


}
