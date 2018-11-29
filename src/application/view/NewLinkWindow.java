package application.view;

import java.util.function.UnaryOperator;

import application.include.Model;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
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
	private TextField newSrcMultiMin = new TextField();
	private TextField newDestMultiMin = new TextField();
	private TextField newSrcMultiMax = new TextField();
	private TextField newDestMultiMax = new TextField();
	private Button newLinkSubmit = new Button("Submit");
	private Button deleteLink = new Button("Delete");

	private int srcIn;
	private int destIn;
	
	ObservableList<String> options = FXCollections.observableArrayList("Dependency", "Assocation", "Generalization",
			"Aggregate", "Composition");
	private ComboBox<String> newLinkArrow = new ComboBox<String>(options);

	// Filter input for integral values
	private UnaryOperator<Change> integers = change -> {

		String text = change.getText();
		return (text.matches("[0-9]*") ? change : null);

	};

	private UnaryOperator<Change> multiplicities = change -> {

		String text = change.getText();
		return (text.matches("\\*|[0-9]*") ? change : null);
	};

	private TextFormatter<String> forceSrcMultiMin = new TextFormatter<>(multiplicities);
	private TextFormatter<String> forceDestMultiMin = new TextFormatter<>(multiplicities);
	private TextFormatter<String> forceSrcMultiMax = new TextFormatter<>(multiplicities);
	private TextFormatter<String> forceDestMultiMax = new TextFormatter<>(multiplicities);

	/**
	 * Constructs a NewLinkWindow instance
	 * 
	 * @param data
	 *            The model to write data to
	 */
	public NewLinkWindow(int editIndex, Model data) {
		// Link formatters
		newSrcMultiMin.setTextFormatter(forceSrcMultiMin);
		newDestMultiMin.setTextFormatter(forceDestMultiMin);
		newSrcMultiMax.setTextFormatter(forceSrcMultiMax);
		newDestMultiMax.setTextFormatter(forceDestMultiMax);

		// Place elements on Dialog
		newLinkInterface.add(newLinkTitle, 0, 0, 2, 1);
		newLinkInterface.add(newLinkArrow, 0, 1, 2, 1);
		newLinkInterface.add(newLinkLabel, 0, 2, 2, 1);
	
		newLinkInterface.add(newSrcMultiMin, 0, 6);
		newLinkInterface.add(newDestMultiMin, 1, 6);
		newLinkInterface.add(newSrcMultiMax, 0, 7);
		newLinkInterface.add(newDestMultiMax, 1, 7);
		if(editIndex != -1) {
			newLinkInterface.add(deleteLink, 0, 8);
		}
		newLinkInterface.add(newLinkSubmit, 1, 8);
		
		if(editIndex == -1) {
			newLinkLabel.setPromptText("Link label...");
			newLinkArrow.setPromptText("Select link type...");
			newSrcMultiMin.setPromptText("Src multiplicity min");
			newDestMultiMin.setPromptText("Dest multiplicity min");
			newSrcMultiMax.setPromptText("Src multiplicity max");
			newDestMultiMax.setPromptText("Dest multiplicity max");
		} else {
			newLinkLabel.setText(data.getLinkModel(editIndex).getLabel());
			newLinkArrow.getSelectionModel().select(data.getLinkModel(editIndex).getType());
			newSrcMultiMin.setText(String.valueOf(data.getLinkModel(editIndex).getSourceMin() == -1 ? "*" : data.getLinkModel(editIndex).getSourceMin()));
			newSrcMultiMax.setText(String.valueOf(data.getLinkModel(editIndex).getSourceMax() == -1 ? "*" : data.getLinkModel(editIndex).getSourceMax()));
			newDestMultiMin.setText(String.valueOf(data.getLinkModel(editIndex).getDestMin() == -1 ? "*" : data.getLinkModel(editIndex).getDestMin()));
			newDestMultiMax.setText(String.valueOf(data.getLinkModel(editIndex).getDestMax() == -1 ? "*" : data.getLinkModel(editIndex).getDestMax()));
		}

		// Handler to submit a new Link
		EventHandler<ActionEvent> submitLinkEvent = new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				data.saveUndoState();
				data.clearRedoState();
				if(editIndex == -1)
				{

					try {

						int srcMulMin = (newSrcMultiMin.getText().length() == 0 ? -2
								: (newSrcMultiMin.getText().matches("(\\*)*") ? -1
										: Integer.parseInt(newSrcMultiMin.getText())));
						int destMulMin = (newDestMultiMin.getText().length() == 0 ? -2
								: (newDestMultiMin.getText().matches("(\\*)*") ? -1
										: Integer.parseInt(newDestMultiMin.getText())));
						int srcMulMax = (newSrcMultiMax.getText().length() == 0 ? -2
								: (newSrcMultiMax.getText().matches("(\\*)*") ? -1
										: Integer.parseInt(newSrcMultiMax.getText())));
						int destMulMax = (newDestMultiMax.getText().length() == 0 ? -2
								: (newDestMultiMax.getText().matches("(\\*)*") ? -1
										: Integer.parseInt(newDestMultiMax.getText())));

						if (srcIn <= data.maxLink() && srcIn >= 0 && destIn <= data.maxLink() && destIn >= 0) {
							data.addLinkModel(
									new int[] { data.getLinkTail(), newLinkArrow.getSelectionModel().getSelectedIndex(),
											srcIn, destIn, srcMulMin, srcMulMax, destMulMin, destMulMax },
									newLinkLabel.getText());
						}
					} catch (NumberFormatException ex) {
					}
				} else {
					data.getLinkModel(editIndex).setLabel(newLinkLabel.getText());
					data.getLinkModel(editIndex).setType(newLinkArrow.getSelectionModel().getSelectedIndex());
					data.getLinkModel(editIndex).setSourceMin(newSrcMultiMin.getText().length() == 0 ? -2
							: (newSrcMultiMin.getText().matches("(\\*)*") ? -1
									: Integer.parseInt(newSrcMultiMin.getText())));
					data.getLinkModel(editIndex).setSourceMax(newSrcMultiMax.getText().length() == 0 ? -2
							: (newSrcMultiMax.getText().matches("(\\*)*") ? -1
									: Integer.parseInt(newSrcMultiMax.getText())));
					data.getLinkModel(editIndex).setDestMin(newDestMultiMin.getText().length() == 0 ? -2
							: (newDestMultiMin.getText().matches("(\\*)*") ? -1
									: Integer.parseInt(newDestMultiMin.getText())));
					data.getLinkModel(editIndex).setDestMax(newDestMultiMax.getText().length() == 0 ? -2
							: (newDestMultiMax.getText().matches("(\\*)*") ? -1
									: Integer.parseInt(newDestMultiMax.getText())));
				}				
				closeWindow();
				e.consume();
			}
		};
		
		EventHandler<ActionEvent> deleteLinkEvent = new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				data.saveUndoState();
				data.clearRedoState();
				data.removeLinkModel(editIndex);
				closeWindow();
				e.consume();
			}
		};
		
		newLinkSubmit.setOnAction(submitLinkEvent);
		deleteLink.setOnAction(deleteLinkEvent);
		
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
	
	public void setSrc(int s) {
		srcIn = s;
	}
	
	public void setDest(int d) {
		destIn = d;
	}
}
