package application;

import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class ClassBlock extends VBox {

	/*
	 * Private variables stored in the ClassBlock object
	 */
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
	 * Constructs an instance of ClassBlock
	 * 
	 * @constructor
	 * @param intData
	 *            data stored in the ClassModel object
	 * @param stringData
	 *            data stored in the ClassModel object
	 */
	public ClassBlock(int[] intData, String[] stringData) {

		// Set initial variables
		main.getStyleClass().add("classBlock");
		this.getStyleClass().add("classBlockBack");
		this.setHeight(20);
		this.setWidth(100);

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
		 * If you guys can come up with a more elegant way to take care of this part
		 * please do, this feels clunky as hell and I hate it.
		 */
		boolean first = (stringData[1] != null && !stringData[1].isEmpty());
		boolean second = (stringData[2] != null && !stringData[2].isEmpty());
		boolean third = (stringData[3] != null && !stringData[3].isEmpty());

		if (first || second || third) {
			if (second || third) {
				if (third) {

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

		//node.setX(intData[1] + (int) (this.getWidth() / 2));
		//node.setY(intData[2] + (int) (this.getHeight() / 2));
		node.setX(intData[1]);
		node.setY(intData[2]);
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
	 * Updates the ClassBlock's LinkNode with correct height & width, now that it has been displayed
	 * 
	 */
	public void initWidthHeight() {
		node.setX(node.getX() + (int) (this.getWidth() / 2));
		node.setY(node.getY() + (int) (this.getHeight() / 2));
	}
}
