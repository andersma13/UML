package application.objects;

import javafx.scene.shape.Line;

public class Link extends Line {

	// how many pixels a line connecting to source or destination should be offset
	// from their neighbors
	private static final int SOURCE_OFFSET = 14;
	private static final int DESTINATION_OFFSET = 14;

	// to make it look like the lines are connected to a box or an arrowhead
	private static final int ARROWHEAD_OFFSET = -10;
	private static final int CORRECTNESS_OFFSET = 1;

	// how much to offset the multiplicity text by
	private static final int BOTTOM_XOFFSET = 26;
	private static final int BOTTOM_YOFFSET = 0;
	private static final int TOP_XOFFSET = 26;
	private static final int TOP_YOFFSET = 15;
	private static final int RIGHT_XOFFSET = 3;
	private static final int RIGHT_YOFFSET = 22;
	private static final int LEFT_XOFFSET = 23;
	private static final int LEFT_YOFFSET = 20;

	private LinkNode source;
	private LinkNode destination;
	private Label label;
	private Arrow arrow;
	private Multiplicity srcMultiplicity;
	private Multiplicity destMultiplicity;
	private int srcOffsetMul;
	private int destOffsetMul;
	private int deltaX;
	private int deltaY;

	enum arrowFacing {
		UP, RIGHT, DOWN, LEFT
	};

	/**
	 * Constructs an instance of Link
	 * 
	 * @constructor
	 * @param src
	 *            the LinkNode that connects to the source of the Link
	 * @param dest
	 *            the LinkNode that connects to the destination of the Link
	 * @param labelText
	 * 			  the string of the link as assigned by the user
	 * @param arrowType
	 *            the type of link the user chose (changes type of arrow displayed)
	 * @param sourceMultiplicity
	 *            the string to be displayed as a representation of multiplicity for
	 *            the source end of the Link
	 * @param destinationMultiplicity
	 *            the string to be displayed as a representation of multiplicity for
	 *            the destination end of the Link
	 */
	public Link(LinkNode src, LinkNode dest, String labelText, int arrowType, String sourceMultiplicity, String destinationMultiplicity) {
		this.getStyleClass().add("link");

		if (arrowType == 0)
			this.getStrokeDashArray().addAll(10d);

		source = src;
		destination = dest;
		label = new Label(labelText);
		label.getStyleClass().add("textColor");
		//HERE
		arrow = new Arrow(arrowType);
		srcMultiplicity = new Multiplicity(sourceMultiplicity);
		destMultiplicity = new Multiplicity(destinationMultiplicity);
		updateLine();

		src.giveParent(this);
		dest.giveParent(this);

		destOffsetMul = dest.askNum(this);
		srcOffsetMul = src.askNum(this);
	}
	
	/**
	 * Creates an label object, assigning its appropriate text.
	 * 
	 * @param text
	 *            The text of the label
	 */
	public void setLabel(String text) {
		label.setText(text);	
	}

	/**
	 * Sets the Link's source node and updates the start positions accordingly.
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
	 * Sets the Link's dest node and updates the end positions accordingly.
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
	 * Also tell the arrowheads to clear themselves while you're at it.
	 *
	 */
	public void warnLinkNodes() {
		source.removeMe(this);
		destination.removeMe(this);

		label.eraseText();
		arrow.eraseArrowhead();
		srcMultiplicity.eraseText();
		destMultiplicity.eraseText();
	}

	/**
	 * Update the source and destinations of the Link (Line) to appropriate sides
	 * and offsets.
	 * 
	 * Also calls the Arrow redraw method.
	 *
	 */
	public void updateLine() {
		// needs to be updated in case links have been removed
		destOffsetMul = destination.askNum(this);
		srcOffsetMul = source.askNum(this);

		deltaX = source.getX() - destination.getX();
		deltaY = source.getY() - destination.getY();

		label.updateLocation(source.getX() - deltaX / 2, source.getY() - deltaY / 2);
		
		// source is right of destination
		if (deltaX > 0) {
			// source is below destination
			if (deltaY > 0) {
				// draw on top of source, on bottom of destination
				if (deltaY > deltaX)
					topToBottom();
				// draw on left of source, on right of destination
				else
					leftToRight();
			}
			// source is above destination
			else {
				// draw on bottom of source, on top of destination
				if (-deltaY > deltaX)
					bottomToTop();
				// draw on left of source, on right of destination
				else
					leftToRight();
			}

		}
		// source is left of destination
		else {
			// source is below destination
			if (deltaY > 0) {
				// draw on top of source, on bottom of destination
				if (deltaY > -deltaX)
					topToBottom();
				// draw on right of source, on left of destination
				else {
					rightToLeft();
				}
			}
			// source is above destination
			else {
				// draw on bottom of source, on top of destination
				if (-deltaY > -deltaX)
					bottomToTop();
				// draw on right of source, on left of destination
				else
					rightToLeft();

			}

		}
	}

	/**
	 * Draw line from top of source to bottom of destination
	 * 
	 * Also update the location of the arrow and the multiplicities
	 * 
	 */
	private void topToBottom() {
		this.setStartX(source.getX() + (srcOffsetMul * SOURCE_OFFSET));
		this.setStartY(source.getU() + CORRECTNESS_OFFSET);
		this.setEndX(destination.getX() + (destOffsetMul * DESTINATION_OFFSET));
		this.setEndY(destination.getD() - ARROWHEAD_OFFSET);
		arrow.updateLocation(destination.getX() + (destOffsetMul * DESTINATION_OFFSET), destination.getD(),
				arrowFacing.UP, ARROWHEAD_OFFSET);
		srcMultiplicity.updateLocation(
				source.getX() + (srcOffsetMul * SOURCE_OFFSET) - TOP_XOFFSET,
				source.getU() - TOP_YOFFSET);
		destMultiplicity.updateLocation(
				destination.getX() + (destOffsetMul * DESTINATION_OFFSET) - BOTTOM_XOFFSET,
				destination.getD() + BOTTOM_YOFFSET);
	}

	/**
	 * Draw line from bottom of source to top of destination
	 * 
	 * Also update the location of the arrow and the multiplicities
	 * 
	 */
	private void bottomToTop() {
		this.setStartX(source.getX() + (srcOffsetMul * SOURCE_OFFSET));
		this.setStartY(source.getD() - CORRECTNESS_OFFSET);
		this.setEndX(destination.getX() + (destOffsetMul * DESTINATION_OFFSET));
		this.setEndY(destination.getU() + ARROWHEAD_OFFSET);
		arrow.updateLocation(destination.getX() + (destOffsetMul * DESTINATION_OFFSET), destination.getU(),
				arrowFacing.DOWN, ARROWHEAD_OFFSET);
		srcMultiplicity.updateLocation(source.getX() + (srcOffsetMul * SOURCE_OFFSET) - BOTTOM_XOFFSET,
				source.getD() + BOTTOM_YOFFSET);
		destMultiplicity.updateLocation(destination.getX() + (destOffsetMul * DESTINATION_OFFSET) - TOP_XOFFSET,
				destination.getU() - TOP_YOFFSET);
	}

	/**
	 * Draw line from left of source to right of destination
	 * 
	 * Also update the location of the arrow and the multiplicities
	 * 
	 */
	private void leftToRight() {
		this.setStartX(source.getL() + CORRECTNESS_OFFSET);
		this.setStartY(source.getY() + (srcOffsetMul * SOURCE_OFFSET));
		this.setEndX(destination.getR() - ARROWHEAD_OFFSET);
		this.setEndY(destination.getY() + (destOffsetMul * DESTINATION_OFFSET));
		arrow.updateLocation(destination.getR(), destination.getY() + (destOffsetMul * DESTINATION_OFFSET),
				arrowFacing.LEFT, ARROWHEAD_OFFSET);
		srcMultiplicity.updateLocation(source.getL() - LEFT_XOFFSET,
				source.getY() + (srcOffsetMul * SOURCE_OFFSET) - LEFT_YOFFSET);
		destMultiplicity.updateLocation(destination.getR() + RIGHT_XOFFSET,
				destination.getY() + (destOffsetMul * DESTINATION_OFFSET) - RIGHT_YOFFSET);

	}

	/**
	 * Draw line from right of source to left of destination
	 * 
	 * Also update the location of the arrow and the multiplicities
	 * 
	 */
	private void rightToLeft() {
		this.setStartX(source.getR() - CORRECTNESS_OFFSET);
		this.setStartY(source.getY() + (srcOffsetMul * SOURCE_OFFSET));
		this.setEndX(destination.getL() + ARROWHEAD_OFFSET);
		this.setEndY(destination.getY() + (destOffsetMul * DESTINATION_OFFSET));
		arrow.updateLocation(destination.getL(), destination.getY() + (destOffsetMul * DESTINATION_OFFSET),
				arrowFacing.RIGHT, ARROWHEAD_OFFSET);
		srcMultiplicity.updateLocation(source.getR() + RIGHT_XOFFSET,
				source.getY() + (srcOffsetMul * SOURCE_OFFSET) - RIGHT_YOFFSET);
		destMultiplicity.updateLocation(destination.getL() - LEFT_XOFFSET,
				destination.getY() + (destOffsetMul * DESTINATION_OFFSET) - LEFT_YOFFSET);
	}

	/**
	 * Creates an arrow object, assigning its appropriate type.
	 * 
	 * @param type
	 *            The type of the arrow to draw
	 */
	public void setType(int type) {
		if (type == 0) {
			this.getStrokeDashArray().addAll(10d);
		} else {
			this.getStrokeDashArray().clear();
		}
		// arrow.eraseArrowhead();
		arrow.setType(type);
	}

	/**
	 * Creates source Multiplicity object, assigning its appropriate type.
	 * 
	 * @param type
	 *            The type of multiplicity to display
	 */
	public void setSrcMultiplicity(String type) {
		srcMultiplicity.updateMultiplicity(type);
	}

	/**
	 * Creates destination Multiplicity object, assigning its appropriate type.
	 * 
	 * @param type
	 *            The type of multiplicity to display
	 */
	public void setDestMultiplicity(String type) {
		destMultiplicity.updateMultiplicity(type);
	}

	/**
	 * returns the label object.
	 * 
	 * @return
	 * 			The Link's label object
	 */
	public Label getLabel() {
		return label;
	}
	
	/**
	 * returns the arrow object.
	 * 
	 * @return The Link's arrow object
	 */
	public Arrow getArrow() {
		return arrow;
	}

	/**
	 * returns the source multiplicity object.
	 * 
	 * @return
	 * 			The Link's source multiplicity object
	 */
	public Multiplicity getSrcMultiplicity() {
		return srcMultiplicity;
	}

	/**
	 * returns the destination multiplicity object.
	 * 
	 * @return
	 * 			The Link's destination multiplicity object
	 */
	public Multiplicity getDestMultiplicity() {
		return destMultiplicity;
	}
}