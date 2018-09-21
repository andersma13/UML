package application;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class LinkNode
{
	
	private IntegerProperty xPos = new SimpleIntegerProperty();
	private IntegerProperty yPos = new SimpleIntegerProperty();
	
	public LinkNode()
	{
		
	}
	
	public LinkNode(int x, int y)
	{
		xPos.set(x);
		yPos.set(y);
	}
	
	public void setX(int x)
	{
		xPos.set(x);
	}

	public void setY(int y)
	{
		yPos.set(y);
	}
	
	public int getX()
	{
		return xPos.get();
	}
	
	public int getY()
	{
		return yPos.get();
	}
	
	public IntegerProperty getXProperty()
	{
		return xPos;
	}
	
	public IntegerProperty getYProperty()
	{
		return yPos;
	}
	
}