package application.objects;

import javafx.scene.shape.Line;

public class Link extends Line {

	/**
	 * Constructs an instance of Link
	 * 
	 * @constructor
	 * @param src
	 *            the LinkNode that connects to the source of the Link
	 * @param dest
	 *            the LinkNode that connects to the destination of the Link
	 */
	public Link(LinkNode src, LinkNode dest) 
	{
		this.getStyleClass().add("link");
		this.setStartX(src.getX());
		this.setStartY(src.getY());
		this.setEndX(dest.getX());
		this.setEndY(dest.getY());
	}
	
	/**
	 * Sets the Link's source node and updates the start positions
	 * accordingly
	 * 
	 * @param src	The LinkNode to attach the start of the link to
	 * 
	 */
	public void setSource(LinkNode src)
	{
		this.setStartX(src.getX());
		this.setStartY(src.getY());
	}
	
	/**
	 * Sets the Link's dest node and updates the end positions
	 * accordingly
	 * 
	 * @param dest	The LinkNode to attach the end of the link to
	 * 
	 */
	public void setDest(LinkNode dest)
	{
		this.setEndX(dest.getX());
		this.setEndY(dest.getY());
	}
}
