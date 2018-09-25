package application;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class LinkNode
{
	
	private IntegerProperty xPos = new SimpleIntegerProperty();
	private IntegerProperty yPos = new SimpleIntegerProperty();
	
	/**
	 * Constructs an instance of LinkNode
	 * @constructor
	 */
	public LinkNode()
	{
		
	}
	
	/**
	 * Constructs an instance of LinkNode
	 * @constructor
	 * @param x	
	 * 	the x position of the LinkNode
	 * @param y
	 * 	the y position of the LinkNode
	 */
	public LinkNode(int x, int y)
	{
		xPos.set(x);
		yPos.set(y);
	}
	
	/**
	 * Sets the x position of the LinkNode
	 * @param x
	 * 	the x position to be set
	 */
	public void setX(int x)
	{
		xPos.set(x);
	}

	/**
	 * Sets the y position of the LinkNode
	 * @param y
	 * 	the y position to be set
	 */
	public void setY(int y)
	{
		yPos.set(y);
	}
	
	/**
	 * Returns the x position of the LinkNode
	 * @return
	 * 	the x position of the LinkNode
	 */
	public int getX()
	{
		return xPos.get();
	}
	
	/**
	 * Returns the y position of the LinkNode
	 * @return
	 * 	the y position of the LinkNode
	 */
	public int getY()
	{
		return yPos.get();
	}
	
	/**
	 * Returns the xPos property of the LinkNode
	 * @return
	 * 	the LinkNode's xPos property
	 */
	public IntegerProperty getXProperty()
	{
		return xPos;
	}
	
	/**
	 * Returns the yPos property of the LinkNode
	 * @return
	 * 	the LinkNode's yPos property
	 */
	public IntegerProperty getYProperty()
	{
		return yPos;
	}
}