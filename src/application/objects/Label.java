package application.objects;

import javafx.scene.text.Text;

public class Label extends Text {

	private String labelText;
	private double currentX;
	private double currentY;
	
	public Label(String text) {
		this.getStyleClass().add("label");

		labelText = text;
		this.setText(labelText);
	}
	
	/**
	 * relocates the Text to a new location.
	 * 
	 * @param x
	 *            The x coordinate where the text should go
	 * @param y
	 *            The y coordinate where the text should go
	 */
	public void updateLocation(int x, int y) {
		currentX = (double) x;
		currentY = (double) y;
		relocate(currentX, currentY);

	}	
	
	/**
	 * sets the text to blank ( not a great solution )
	 * 
	 */
	public void eraseText() {
		setText("");
	}

}
