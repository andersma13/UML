package application;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class LinkNode
{
	
	private IntegerProperty xPos = new SimpleIntegerProperty();
	private IntegerProperty yPos = new SimpleIntegerProperty();
	
	/**
	 * @constructor
	 */
	public LinkNode()
	{
		
	}
	
	/**
	 * @constructor
	 * @param x
	 * @param y
	 */
	public LinkNode(int x, int y)
	{
		xPos.set(x);
		yPos.set(y);
	}
	
	/**
	 * 
	 * @param x
	 */
	public void setX(int x)
	{
		xPos.set(x);
	}

	/**
	 * 
	 * @param y
	 */
	public void setY(int y)
	{
		yPos.set(y);
	}
	
	/**
	 * 
	 * @return
	 */
	public int getX()
	{
		return xPos.get();
	}
	
	/**
	 * 
	 * @return
	 */
	public int getY()
	{
		return yPos.get();
	}
	
	/**
	 * 
	 * @return
	 */
	public IntegerProperty getXProperty()
	{
		return xPos;
	}
	
	/**
	 * 
	 * @return
	 */
	public IntegerProperty getYProperty()
	{
		return yPos;
	}
	
}