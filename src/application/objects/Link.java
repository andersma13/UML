package application.objects;

import javafx.scene.shape.Line;

public class Link extends Line {

	private LinkNode source;
	private LinkNode destination;

	/**
	 * Constructs an instance of Link
	 * 
	 * @constructor
	 * @param src
	 *            the LinkNode that connects to the source of the Link
	 * @param dest
	 *            the LinkNode that connects to the destination of the Link
	 */
	public Link(LinkNode src, LinkNode dest) {
		this.getStyleClass().add("link");
		
		source = src;
		destination = dest;
		this.updateLine();
		
		src.giveParent(this);
		dest.giveParent(this);
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
		final int destOffset = 7;
		
		// placeholders to be used for offsetting line placement in future
		final int srcOffsetMul = 1;
		final int destOffsetMul = 1;

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
				}
				// draw on left of source, on right of destination
				else {
					this.setStartX(source.getL() + correctnessOffset);
					this.setStartY(source.getY() + (srcOffsetMul * srcOffset));
					this.setEndX(destination.getR() - arrowheadOffset);
					this.setEndY(destination.getY() + (destOffsetMul * destOffset));
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
				}
				// draw on left of source, on right of destination
				else {
					this.setStartX(source.getL() + correctnessOffset);
					this.setStartY(source.getY() + (srcOffsetMul * srcOffset));
					this.setEndX(destination.getR() - arrowheadOffset);
					this.setEndY(destination.getY() + (destOffsetMul * destOffset));
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
				}
				// draw on right of source, on left of destination
				else {
					this.setStartX(source.getR() - correctnessOffset);
					this.setStartY(source.getY() + (srcOffsetMul * srcOffset));
					this.setEndX(destination.getL() + arrowheadOffset);
					this.setEndY(destination.getY() + (destOffsetMul * destOffset));
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
				}
				// draw on right of source, on left of destination
				else {
					this.setStartX(source.getR() - correctnessOffset);
					this.setStartY(source.getY() + (srcOffsetMul * srcOffset));
					this.setEndX(destination.getL() + arrowheadOffset);
					this.setEndY(destination.getY() + (destOffsetMul * destOffset));
				}

			}

		}

	}
}
