package application.objects;

import java.util.ArrayList;
import java.util.List;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class LinkNode {

	private IntegerProperty xPos = new SimpleIntegerProperty();
	private IntegerProperty yPos = new SimpleIntegerProperty();
	private List<Link> connectedLinks;
	private IntegerProperty xPosL = new SimpleIntegerProperty();
	private IntegerProperty xPosR = new SimpleIntegerProperty();
	private IntegerProperty yPosU = new SimpleIntegerProperty();
	private IntegerProperty yPosD = new SimpleIntegerProperty();

	/**
	 * Constructs an instance of LinkNode
	 * 
	 * @constructor
	 */
	public LinkNode() {
		connectedLinks = new ArrayList<Link>();
	}

	/**
	 * Constructs an instance of LinkNode
	 * 
	 * @constructor
	 * @param x
	 *            the x position of the LinkNode
	 * @param y
	 *            the y position of the LinkNode
	 */
	public LinkNode(int x, int y) {
		xPos.set(x);
		yPos.set(y);

		connectedLinks = new ArrayList<Link>();
	}

	/**
	 * Sets the x position of the LinkNode
	 * 
	 * @param x
	 *            the x position to be set
	 */
	public void setX(int x) {
		xPos.set(x);
	}

	/**
	 * Sets the y position of the LinkNode
	 * 
	 * @param y
	 *            the y position to be set
	 */
	public void setY(int y) {
		yPos.set(y);
	}

	/**
	 * Returns the x position of the LinkNode
	 * 
	 * @return 
	 * 			the x position of the LinkNode
	 */
	public int getX() {
		return xPos.get();
	}

	/**
	 * Returns the y position of the LinkNode
	 * 
	 * @return 
	 * 			the y position of the LinkNode
	 */
	public int getY() {
		return yPos.get();
	}

	/**
	 * Returns the xPos property of the LinkNode
	 * 
	 * @return 
	 * 			the LinkNode's xPos property
	 */
	public IntegerProperty getXProperty() {
		return xPos;
	}

	/**
	 * Returns the yPos property of the LinkNode
	 * 
	 * @return 
	 * 			the LinkNode's yPos property
	 */
	public IntegerProperty getYProperty() {
		return yPos;
	}

	/**
	 * Add the parent Link to the list of Links that originate from this LinkNode
	 * 
	 * @param one
	 *            of the Links that uses this LinkNode
	 */
	public void giveParent(Link dad) {
		if (!connectedLinks.contains(dad))
			connectedLinks.add(dad);
	}

	/**
	 * Sets the 4 bounds of the LinkNode
	 * 
	 * @param xL
	 *            the left bound to be set
	 * @param xR
	 *            the right bound to be set
	 * @param yU
	 *            the top bound to be set
	 * @param yD
	 *            to bottom bound to be set
	 */
	public void setBounds(int xL, int xR, int yU, int yD) {
		xPosL.set(xL);
		xPosR.set(xR);
		yPosU.set(yU);
		yPosD.set(yD);
	}

	/**
	 * Returns the left bound of the LinkNode
	 * 
	 * @return the left bound of the LinkNode
	 */
	public int getL() {
		return xPosL.get();
	}

	/**
	 * Returns the right bound of the LinkNode
	 * 
	 * @return the right bound of the LinkNode
	 */
	public int getR() {
		return xPosR.get();
	}

	/**
	 * Returns the upper bound of the LinkNode
	 * 
	 * @return the upper bound of the LinkNode
	 */
	public int getU() {
		return yPosU.get();
	}

	/**
	 * Returns the lower bound of the LinkNode
	 * 
	 * @return the lower bound of the LinkNode
	 */
	public int getD() {
		return yPosD.get();
	}

	/**
	 * Tell each parent Link to update its line coordinates
	 * 
	 */
	public void updateLink() {
		for (Link link : connectedLinks)
			if (link != null)
				link.updateLine();
	}

	/**
	 * Link will be removed from this LinkNode, remove all instances of link
	 * 
	 * @param link
	 *            to be removed
	 */
	public void removeMe(Link link) {
		while (connectedLinks.contains(link))
			connectedLinks.remove(link);

	}
	
	/**
	 * All links will be removed from this LinkNode
	 * 
	 */
	public void clearLinks() {
		connectedLinks.clear();
		
	}

	/**
	 *  Returns an appropriate offset for the Link based on its index in the list of links connected to this LinkNode
	 *    ( offsets number pattern is such:  0, 1, -1, 2, -2, ... )
	 * 
	 * @param link
	 * 				The link object that wants to know what its index is
	 * 
	 * @return
	 * 			The correct number in the series as derived from the index of the Link
	 */
	public int askNum(Link link) {
		int x = connectedLinks.indexOf(link);
		
		return (int) Math.ceil(((double)x)/((double)x%2==0?-2:2));
	}
}