package application.objects;

import javafx.scene.shape.Line;

public class Link extends Line {

	// how many pixels a line connecting to source or destination should be offset from their neighbors
	private static final int SOURCE_OFFSET = 0;
	private static final int DESTINATION_OFFSET = 14;

	// to make it look like the lines are connected to a box or an arrowhead
	private static final int ARROWHEAD_OFFSET = -10;
	private static final int CORRECTNESS_OFFSET = 5;
	
	private LinkNode source;
	private LinkNode destination;
	private Arrow arrow;
	private int srcOffsetMul;
	private int destOffsetMul;

	enum arrowFacing { UP, RIGHT, DOWN, LEFT }; 

	/**
	 * Constructs an instance of Link
	 * 
	 * @constructor
	 * @param src
	 *            the LinkNode that connects to the source of the Link
	 * @param dest
	 *            the LinkNode that connects to the destination of the Link
	 * @param arrowType
	 *            the type of link the user chose (changes type of arrow displayed)
	 */
	public Link(LinkNode src, LinkNode dest, int arrowType) {
		this.getStyleClass().add("link");

		if (arrowType == 0)
			this.getStrokeDashArray().addAll(10d);

		source = src;
		destination = dest;
		setArrowType(arrowType);
		this.updateLine();

		src.giveParent(this);
		dest.giveParent(this);
		
		destOffsetMul=dest.askNum(this);
		srcOffsetMul=src.askNum(this);
	}

	/**
	 * Sets the Link's source node and updates the start positions accordingly
	 * 
	 * @param src
	 *            The LinkNode to attach the start of the link to
	 * 
	 */
	public void setSource(LinkNode src) {
		this.setStartX(src.getX());
		this.setStartY(src.getY());
	}

	/**
	 * Sets the Link's dest node and updates the end positions accordingly
	 * 
	 * @param dest
	 *            The LinkNode to attach the end of the link to
	 * 
	 */
	public void setDest(LinkNode dest) {
		this.setEndX(dest.getX());
		this.setEndY(dest.getY());
	}

	/**
	 * Tell each connected LinkNode that this Link will soon be deleted.
	 *   Also tell the arrowheads to clear themselves while you're at it.
	 *
	 */
	public void warnLinkNodes() {
		source.removeMe(this);
		destination.removeMe(this);

		arrow.eraseArrowheads();
	}

	/**
	 * Update the source and destinations of the Link (Line) to appropriate sides
	 * and offsets.
	 * 
	 * Also calls the Arrow redraw method
	 *
	 */
	public void updateLine() {		
		// needs to be updated in case links have been removed
		destOffsetMul=destination.askNum(this);
		srcOffsetMul=source.askNum(this);
		
		int deltaX = source.getX() - destination.getX();
		int deltaY = source.getY() - destination.getY();

		// source is right of destination
		if (deltaX > 0) {
			// source is below destination
			if (deltaY > 0) {
				// draw on top of source, on bottom of destination
				if (deltaY > deltaX) {
					this.setStartX(source.getX() + (srcOffsetMul * SOURCE_OFFSET));
					this.setStartY(source.getU() + CORRECTNESS_OFFSET);
					this.setEndX(destination.getX() + (destOffsetMul * DESTINATION_OFFSET));
					this.setEndY(destination.getD() - ARROWHEAD_OFFSET);
					arrow.updateLocation(destination.getX() + (destOffsetMul * DESTINATION_OFFSET), destination.getD(), arrowFacing.UP, ARROWHEAD_OFFSET);
				}
				// draw on left of source, on right of destination
				else {
					this.setStartX(source.getL() + CORRECTNESS_OFFSET);
					this.setStartY(source.getY() + (srcOffsetMul * SOURCE_OFFSET));
					this.setEndX(destination.getR() - ARROWHEAD_OFFSET);
					this.setEndY(destination.getY() + (destOffsetMul * DESTINATION_OFFSET));
					arrow.updateLocation(destination.getR(), destination.getY() + (destOffsetMul * DESTINATION_OFFSET), arrowFacing.LEFT, ARROWHEAD_OFFSET);
				}
			}
			// source is above destination
			else {
				// draw on bottom of source, on top of destination
				if (-deltaY > deltaX) {
					this.setStartX(source.getX() + (srcOffsetMul * SOURCE_OFFSET));
					this.setStartY(source.getD() - CORRECTNESS_OFFSET);
					this.setEndX(destination.getX() + (destOffsetMul * DESTINATION_OFFSET));
					this.setEndY(destination.getU() + ARROWHEAD_OFFSET);
					arrow.updateLocation(destination.getX() + (destOffsetMul * DESTINATION_OFFSET), destination.getU(), arrowFacing.DOWN, ARROWHEAD_OFFSET);
				}
				// draw on left of source, on right of destination
				else {
					this.setStartX(source.getL() + CORRECTNESS_OFFSET);
					this.setStartY(source.getY() + (srcOffsetMul * SOURCE_OFFSET));
					this.setEndX(destination.getR() - ARROWHEAD_OFFSET);
					this.setEndY(destination.getY() + (destOffsetMul * DESTINATION_OFFSET));
					arrow.updateLocation(destination.getR(), destination.getY() + (destOffsetMul * DESTINATION_OFFSET), arrowFacing.LEFT, ARROWHEAD_OFFSET);
				}

			}

		}
		// source is left of destination
		else {
			// source is below destination
			if (deltaY > 0) {
				// draw on top of source, on bottom of destination
				if (deltaY > -deltaX) {
					this.setStartX(source.getX() + (srcOffsetMul * SOURCE_OFFSET));
					this.setStartY(source.getU() + CORRECTNESS_OFFSET);
					this.setEndX(destination.getX() + (destOffsetMul * DESTINATION_OFFSET));
					this.setEndY(destination.getD() - ARROWHEAD_OFFSET);
					arrow.updateLocation(destination.getX() + (destOffsetMul * DESTINATION_OFFSET), destination.getD(), arrowFacing.UP, ARROWHEAD_OFFSET);
				}
				// draw on right of source, on left of destination
				else {
					this.setStartX(source.getR() - CORRECTNESS_OFFSET);
					this.setStartY(source.getY() + (srcOffsetMul * SOURCE_OFFSET));
					this.setEndX(destination.getL() + ARROWHEAD_OFFSET);
					this.setEndY(destination.getY() + (destOffsetMul * DESTINATION_OFFSET));
					arrow.updateLocation(destination.getL(), destination.getY() + (destOffsetMul * DESTINATION_OFFSET), arrowFacing.RIGHT, ARROWHEAD_OFFSET);
				}
			}
			// source is above destination
			else {
				// draw on bottom of source, on top of destination
				if (-deltaY > -deltaX) {
					this.setStartX(source.getX() + (srcOffsetMul * SOURCE_OFFSET));
					this.setStartY(source.getD() - CORRECTNESS_OFFSET);
					this.setEndX(destination.getX() + (destOffsetMul * DESTINATION_OFFSET));
					this.setEndY(destination.getU() + ARROWHEAD_OFFSET);
					arrow.updateLocation(destination.getX() + (destOffsetMul * DESTINATION_OFFSET), destination.getU(), arrowFacing.DOWN, ARROWHEAD_OFFSET);
				}
				// draw on right of source, on left of destination
				else {
					this.setStartX(source.getR() - CORRECTNESS_OFFSET);
					this.setStartY(source.getY() + (srcOffsetMul * SOURCE_OFFSET));
					this.setEndX(destination.getL() + ARROWHEAD_OFFSET);
					this.setEndY(destination.getY() + (destOffsetMul * DESTINATION_OFFSET));
					arrow.updateLocation(destination.getL(), destination.getY() + (destOffsetMul * DESTINATION_OFFSET), arrowFacing.RIGHT, ARROWHEAD_OFFSET);
				}

			}

		}
	}

	/**
	 * Creates an arrow object, assigning its appropriate type
	 * 
	 * @param type
	 * 				The type of the arrow to draw
	 */
	public void setArrowType(int type) {
		arrow = new Arrow(type);

	}

	/**
	 * returns the arrow object
	 * 
	 * @return
	 * 			The Link's arrow object
	 */
	public Arrow getArrow() {
		return arrow;
	}
}
