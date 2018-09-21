package application;

import javafx.scene.shape.Line;

public class Link extends Line 
{
	public Link(LinkNode src, LinkNode dest)
	{
		this.getStyleClass().add("link");
		this.setStartX(src.getX());
		this.setStartY(src.getY());
		this.setEndX(dest.getX());
		this.setEndY(dest.getY());
	}
}
