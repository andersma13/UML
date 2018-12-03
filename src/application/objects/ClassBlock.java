package application.objects;

import application.include.Model.ClassModel;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class ClassBlock extends VBox {

	/*
	 * Private variables stored in the ClassBlock object
	 */
	private Panel name = new Panel();
	private Panel attr = new Panel();
	private Panel oper = new Panel();
	private Panel desc = new Panel();
	private LinkNode node = new LinkNode();

	/**
	 * Constructs a ClassBlock object using the data stored in modelIn
	 * 
	 * @param modelIn
	 *            The ClassModel object storing the data to be used
	 * 
	 */
	public ClassBlock(ClassModel modelIn) {
		// Set initial variables
		this.getStyleClass().add("classBlock");
		this.setHeight(200);
		this.setWidth(100);

		// Generate and add title block
		name.updateText(modelIn.getName());
		attr.updateText(modelIn.getAttr());
		oper.updateText(modelIn.getOper());
		desc.updateText(modelIn.getDesc());

		name.getStyleClass().add("classBlockTitle");
		attr.getStyleClass().add("classBlockPanel");
		oper.getStyleClass().add("classBlockPanel");
		desc.getStyleClass().add("classBlockPanel");

		this.getChildren().add(name);

		setRows();

		node.setX(modelIn.getXPos());
		node.setY(modelIn.getYPos());

	}

	/**
	 * Dynamically allocate rows depending on which ones contain content
	 */
	private void setRows() {

		this.getChildren().clear();
		this.getChildren().add(name);

		boolean row1 = !attr.isEmpty();
		boolean row2 = !oper.isEmpty();
		boolean row3 = !desc.isEmpty();

		if (row1 || row2 || row3) {
			this.getChildren().add(attr);
			if (row2 || row3) {
				this.getChildren().add(oper);
				if (row3) {
					this.getChildren().add(desc);
				}
			}
		}
	}

	/**
	 * Set the Class Block's name field
	 * 
	 * @param nameIn
	 *            The name to be set
	 */
	public void setName(String nameIn) {
		name.updateText(nameIn);
	}

	/**
	 * Set the Class Block's attr field
	 * 
	 * @param attrIn
	 *            The attributes to be set
	 */
	public void setAttr(String in) {
		attr.updateText(in);
		setRows();
	}

	/**
	 * Set the Class Block's oper field
	 * 
	 * @param operIn
	 *            The operations to be set
	 */
	public void setOper(String in) {
		oper.updateText(in);
		setRows();
	}

	/**
	 * Set the Class Block's desc field
	 * 
	 * @param descIn
	 *            The description to be set
	 */
	public void setDesc(String in) {
		desc.updateText(in);
		setRows();
	}

	/**
	 * Returns the LinkNode to attach links to
	 * 
	 * @return the LinkNode stored in the ClassBlock
	 */
	public LinkNode getNode() {
		return node;
	}

	/**
	 * Updates the ClassBlock's LinkNode with correct height & width, now that it
	 * has been displayed.
	 * 
	 */
	public void initWidthHeight() {
		node.setX(node.getX() + (int) (this.getWidth() / 2));
		node.setY(node.getY() + (int) (this.getHeight() / 2));
	}
	
	/**
	 * Remove the width & height from the LinkNode
	 * 
	 * Prerequisite before refreshing the Width & Height. Must be completed before the CSS is altered or the ClassBlock dimensions otherwise change.
	 * 
	 */
	public void prepWidthHeight() {
		node.setX(node.getX() - (int) (this.getWidth() / 2));
		node.setY(node.getY() - (int) (this.getHeight() / 2));
	}
	

	/**
	 * Panels to be used in the Class Block object. Pretty straightforward.
	 */
	private class Panel extends HBox {
		// Stores the panel's text
		private Text text = new Text();

		/**
		 * Construct a Panel object
		 * 
		 */
		public Panel() {
			this.getChildren().add(text);
			text.getStyleClass().add("textColor");
		}

		/**
		 * Updates the text stored by the panel
		 * 
		 * @param in
		 *            Text to be stored
		 */
		public void updateText(String in) {
			text.setText(in);
		}

		/**
		 * Checks whether the panel stores any text or not
		 * 
		 * @return Returns true if the panel stores text, false otherwise.
		 */
		public boolean isEmpty() {
			return (text.getText() == null || text.getText().isEmpty());
		}
	}
}
