package application;

import javafx.scene.shape.Line;

public class Link extends Line 
{
	public Link(LinkNode src, LinkNode dest)
	{
		this.getStyleClass().add("link");
		this.setStartX(src.x);
		this.setStartY(src.y);
		this.setEndX(dest.x);
		this.setEndY(dest.y);
	}
}
