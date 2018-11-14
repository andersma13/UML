package application.objects;

import javafx.scene.shape.Line;

public class Link extends Line {

	private LinkNode source;
	private LinkNode destination;
	private Arrow arrow;
	final int srcOffsetMul;
	final int destOffsetMul;

	// which direction is arrow pointing?
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
	 *
	 * tell each connected LinkNode that this Link will soon be deleted.
	 *
	 */
	public void warnLinkNodes() {
		source.removeMe(this);
		destination.removeMe(this);

	}

	/**
	 *
	 * update the source and destinations of the Link (Line) to appropriate sides
	 * and offsets
	 *
	 */
	public void updateLine() {
		final int srcOffset = 0;
		final int destOffset = 14;

		// to make it look like the lines are always connecting to a box or an arrowhead
		final int arrowheadOffset = -10;
		final int correctnessOffset = 5;

		int deltaX = source.getX() - destination.getX();
		int deltaY = source.getY() - destination.getY();

		// source is right of destination
		if (deltaX > 0) {
			// source is below destination
			if (deltaY > 0) {
				// draw on top of source, on bottom of destination
				if (deltaY > deltaX) {
					this.setStartX(source.getX() + (srcOffsetMul * srcOffset));
					this.setStartY(source.getU() + correctnessOffset);
					this.setEndX(destination.getX() + (destOffsetMul * destOffset));
					this.setEndY(destination.getD() - arrowheadOffset);
					arrow.updateLocation(destination.getX(), destination.getD(), arrowFacing.UP, arrowheadOffset,
							destOffsetMul * destOffset);
				}
				// draw on left of source, on right of destination
				else {
					this.setStartX(source.getL() + correctnessOffset);
					this.setStartY(source.getY() + (srcOffsetMul * srcOffset));
					this.setEndX(destination.getR() - arrowheadOffset);
					this.setEndY(destination.getY() + (destOffsetMul * destOffset));
					arrow.updateLocation(destination.getR(), destination.getY(), arrowFacing.LEFT, arrowheadOffset,
							destOffsetMul * destOffset);
				}
			}
			// source is above destination
			else {
				// draw on bottom of source, on top of destination
				if (-deltaY > deltaX) {
					this.setStartX(source.getX() + (srcOffsetMul * srcOffset));
					this.setStartY(source.getD() - correctnessOffset);
					this.setEndX(destination.getX() + (destOffsetMul * destOffset));
					this.setEndY(destination.getU() + arrowheadOffset);
					arrow.updateLocation(destination.getX(), destination.getU(), arrowFacing.DOWN, arrowheadOffset,
							destOffsetMul * destOffset);
				}
				// draw on left of source, on right of destination
				else {
					this.setStartX(source.getL() + correctnessOffset);
					this.setStartY(source.getY() + (srcOffsetMul * srcOffset));
					this.setEndX(destination.getR() - arrowheadOffset);
					this.setEndY(destination.getY() + (destOffsetMul * destOffset));
					arrow.updateLocation(destination.getR(), destination.getY(), arrowFacing.LEFT, arrowheadOffset,
							destOffsetMul * destOffset);
				}

			}

		}
		// source is left of destination
		else {
			// source is below destination
			if (deltaY > 0) {
				// draw on top of source, on bottom of destination
				if (deltaY > -deltaX) {
					this.setStartX(source.getX() + (srcOffsetMul * srcOffset));
					this.setStartY(source.getU() + correctnessOffset);
					this.setEndX(destination.getX() + (destOffsetMul * destOffset));
					this.setEndY(destination.getD() - arrowheadOffset);
					arrow.updateLocation(destination.getX(), destination.getD(), arrowFacing.UP, arrowheadOffset,
							destOffsetMul * destOffset);
				}
				// draw on right of source, on left of destination
				else {
					this.setStartX(source.getR() - correctnessOffset);
					this.setStartY(source.getY() + (srcOffsetMul * srcOffset));
					this.setEndX(destination.getL() + arrowheadOffset);
					this.setEndY(destination.getY() + (destOffsetMul * destOffset));
					arrow.updateLocation(destination.getL(), destination.getY(), arrowFacing.RIGHT, arrowheadOffset,
							destOffsetMul * destOffset);
				}
			}
			// source is above destination
			else {
				// draw on bottom of source, on top of destination
				if (-deltaY > -deltaX) {
					this.setStartX(source.getX() + (srcOffsetMul * srcOffset));
					this.setStartY(source.getD() - correctnessOffset);
					this.setEndX(destination.getX() + (destOffsetMul * destOffset));
					this.setEndY(destination.getU() + arrowheadOffset);
					arrow.updateLocation(destination.getX(), destination.getU(), arrowFacing.DOWN, arrowheadOffset,
							destOffsetMul * destOffset);
				}
				// draw on right of source, on left of destination
				else {
					this.setStartX(source.getR() - correctnessOffset);
					this.setStartY(source.getY() + (srcOffsetMul * srcOffset));
					this.setEndX(destination.getL() + arrowheadOffset);
					this.setEndY(destination.getY() + (destOffsetMul * destOffset));
					arrow.updateLocation(destination.getL(), destination.getY(), arrowFacing.RIGHT, arrowheadOffset,
							destOffsetMul * destOffset);
				}

			}

		}
	}

	public void setArrowType(int type) {
		arrow = new Arrow(type);

	}

	public Arrow getArrow() {
		return arrow;
	}
}
