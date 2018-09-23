package application;

import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class ClassBlock extends VBox
{
	
	private GridPane main = new GridPane();
	private Text name = new Text();
	private HBox nameWrap = new HBox();
	private GridPane attr = new GridPane();
	private HBox attrWrap = new HBox();
	private GridPane oper = new GridPane();
	private HBox operWrap = new HBox();
	private GridPane desc = new GridPane();
	private HBox descWrap = new HBox();
	private LinkNode node = new LinkNode();
	
	/**
	 * 
	 * @constructor 
	 * @param intData
	 * @param stringData
	 */
	public ClassBlock(int[] intData, String[] stringData)
	{
		
		// Set initial variables
		main.getStyleClass().add("classBlock");
		this.getStyleClass().add("classBlockBack");
		this.setWidth((double)intData[3]);
		this.setHeight((double)intData[4]);
		
		// Generate and add title block
		name.setText(stringData[0]);
		name.getStyleClass().add("classBlockTitle");
		nameWrap.getStyleClass().add("pad");
		nameWrap.getStyleClass().add("classBlockTitle");
		nameWrap.getChildren().add(name);	
		main.add(nameWrap, 0, 0);
		
		/*
		 * Dynamically display blocks, ignoring trailing empty elements.
		 * 
		 * If you guys can come up with a more elegant way to take care of
		 * this part please do, this feels clunky as hell and I hate it.
		 */
		boolean first = (stringData[1] != null && !stringData[1].isEmpty());
		boolean second = (stringData[2] != null && !stringData[2].isEmpty());
		boolean third = (stringData[3] != null && !stringData[3].isEmpty());
		
		if(first || second || third)
		{
			if(second || third)
			{
				if(third)
				{
					
					desc.addColumn(0, new Text(stringData[3]));
					descWrap.getStyleClass().add("pad");
					descWrap.getChildren().add(desc);
					main.add(descWrap, 0, 3);
					
				}
				
				oper.addColumn(0, new Text(stringData[2]));
				operWrap.getStyleClass().add("pad");
				operWrap.getChildren().add(oper);
				main.add(operWrap, 0, 2);
				
			}
			
			attr.addColumn(0, new Text(stringData[1]));
			attrWrap.getStyleClass().add("pad");
			attrWrap.getChildren().add(attr);
			main.add(attrWrap, 0, 1);
			
		}
		this.getChildren().add(main);
		
		node.setX(intData[1] + (int)(this.getWidth() / 2));
		node.setY(intData[2] + (int)(this.getHeight() / 2));
		
	}
	
	/**
	 * 
	 * @return
	 */
	public LinkNode getNode()
	{
		return node;
	}
	
	/**
	 * 
	 * @param attrIn
	 */
	public void addAttr(String attrIn) 
	{
		
		attr.getStyleClass().add("pad");
		attr.addColumn(0, new Text(attrIn));
		
	}
	
	/**
	 * 
	 * @param operIn
	 */
	public void addOper(String operIn)
	{
		
		oper.getStyleClass().add("pad");
		oper.addColumn(0, new Text(operIn));
		
	}
	
	/**
	 * 
	 * @param descIn
	 */
	public void addDesc(String descIn)
	{
		
		desc.getStyleClass().add("pad");
		desc.addColumn(0,  new Text(descIn));
		
	}
}
