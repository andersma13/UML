package application.view;

import java.util.function.UnaryOperator;

import application.include.Model;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.TextFormatter.Change;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class NewLinkWindow extends Stage {

	// Dialog box elements
	private GridPane newLinkInterface = new GridPane();
	private Text newLinkTitle = new Text("Create Link");
	private TextField newLinkLabel = new TextField();
	private TextField newLinkSrc = new TextField();
	private TextField newLinkDest = new TextField();
	private Button newLinkSubmit = new Button("Submit");

	// Filter input for integral values
	private UnaryOperator<Change> integers = change -> {

		String text = change.getText();
		return (text.matches("[0-9]*") ? change : null);

	};

	private TextFormatter<String> forceIntSrc = new TextFormatter<>(integers);
	private TextFormatter<String> forceIntDest = new TextFormatter<>(integers);

	/**
	 * Constructs a NewLinkWindow instance
	 * 
	 * @param data
	 *            The model to write data to
	 */
	public NewLinkWindow(Model data) {
		// Link formatters
		newLinkSrc.setTextFormatter(forceIntSrc);
		newLinkDest.setTextFormatter(forceIntDest);

		// Place elements on Dialog
		newLinkInterface.add(newLinkTitle, 0, 0, 2, 1);
		newLinkInterface.add(newLinkLabel, 0, 1, 2, 1);
		newLinkInterface.add(newLinkSrc, 0, 5);
		newLinkInterface.add(newLinkDest, 1, 5);
		newLinkInterface.add(newLinkSubmit, 1, 6);
		newLinkLabel.setPromptText("Link label...");
		newLinkSrc.setPromptText("Link Source");
		newLinkDest.setPromptText("Link Dest");

		// Handler to submit a new Link
		EventHandler<ActionEvent> submitLinkEvent = new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				data.saveUndoState();
				data.clearRedoState();
				try {
					int srcIn = Integer.parseInt(newLinkSrc.getText());
					int destIn = Integer.parseInt(newLinkDest.getText());

					if (srcIn <= data.maxLink() && srcIn >= 0 && destIn <= data.maxLink() && destIn >= 0) {
						data.addLinkModel(new int[] { data.getLinkTail(), 0, srcIn, destIn, 0, 1, 0, 1 },
								newLinkTitle.getText());
					}
				} catch (NumberFormatException ex) {
				}
				closeWindow();
				e.consume();
			}
		};
		newLinkSubmit.setOnAction(submitLinkEvent);

		// Display dialog
		Scene scene = new Scene(newLinkInterface);
		this.setScene(scene);
	}

	/**
	 * Closes the dialog box
	 */
	private void closeWindow() {
		this.close();
	}
}
