package application.view;

import application.include.Model;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class NewClassWindow extends Stage {
	// Dialog box elements
	private GridPane newClassInterface = new GridPane();
	private Text newClassTitle = new Text();
	private TextField newClassName = new TextField();
	private TextArea newClassAttr = new TextArea();
	private TextArea newClassOper = new TextArea();
	private TextArea newClassDesc = new TextArea();
	public Button newClassSubmit = new Button("Submit");
	public Button deleteClass = new Button("Delete");

	// Override tab presses for field traversal
	
	private EventHandler<KeyEvent> tabTraverse = new EventHandler<KeyEvent>() {
		@Override
		public void handle(KeyEvent event) {
		    KeyCode code = event.getCode();

		    if (code == KeyCode.TAB && !event.isShiftDown() && !event.isControlDown()) {
		        event.consume();
		        Node node = (Node) event.getSource();            
		        KeyEvent newEvent 
		          = new KeyEvent(event.getSource(),
		                     event.getTarget(), event.getEventType(),
		                     event.getCharacter(), event.getText(),
		                     event.getCode(), event.isShiftDown(),
		                     true, event.isAltDown(),
		                     event.isMetaDown());

		        node.fireEvent(newEvent);            
		        }
		    }
	};

	
	/**
	 * Constructs a NewClassWindow instance
	 * 
	 * @param editIndex
	 *            The index of the class being edited. If a new class is being
	 *            created, this value is -1.
	 * @param data
	 *            The Model to write data to.
	 */
	public NewClassWindow(int editIndex, Model data) {
		// Set window title
		newClassTitle.setText((editIndex == -1) ? "Create Class Block" : "Edit Class Block");

		// Attach elements to window
		if (editIndex == -1) {
			newClassName.setPromptText("Class name...");
			newClassAttr.setPromptText("Class Attributes...");
			newClassOper.setPromptText("Class Operations...");
			newClassDesc.setPromptText("Class Description...");
		} else {
			newClassName.setText(data.getClassModel(editIndex).getName());
			newClassAttr.setText(data.getClassModel(editIndex).getAttr());
			newClassOper.setText(data.getClassModel(editIndex).getOper());
			newClassDesc.setText(data.getClassModel(editIndex).getDesc());
		}

		// Place elements on stage
		newClassInterface.add(newClassTitle, 0, 0, 2, 1);
		newClassInterface.add(newClassName, 0, 1, 2, 1);
		newClassInterface.add(newClassAttr, 0, 2, 2, 1);
		newClassInterface.add(newClassOper, 0, 3, 2, 1);
		newClassInterface.add(newClassDesc, 0, 4, 2, 1);
		newClassInterface.add(newClassSubmit, 1, 6);

		// Check for new class
		if (editIndex != -1) {
			newClassInterface.add(deleteClass, 0, 6);
		}

		// Allow tab traversal through TextAreas
		newClassAttr.addEventFilter(KeyEvent.KEY_PRESSED, tabTraverse);
		newClassOper.addEventFilter(KeyEvent.KEY_PRESSED, tabTraverse);
		newClassDesc.addEventFilter(KeyEvent.KEY_PRESSED, tabTraverse);

		// Handler to submit the selected class
		EventHandler<ActionEvent> submitClassEvent = new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				if (editIndex == -1) {
					data.saveUndoState();
					data.addClassModel(
							new int[] { data.getClassTail(), 0, 0, 100, 100 },
							new String[] { newClassName.getText(), newClassAttr.getText(), newClassOper.getText(),
									newClassDesc.getText() });
				} else {
					data.saveUndoState();
					data.getClassModel(editIndex).setName(newClassName.getText());
					data.getClassModel(editIndex).setAttr(newClassAttr.getText());
					data.getClassModel(editIndex).setOper(newClassOper.getText());
					data.getClassModel(editIndex).setDesc(newClassDesc.getText());
				}
				closeWindow();
				e.consume();
			}
		};

		// Handler to delete the selected class
		EventHandler<ActionEvent> deleteClassEvent = new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				data.removeClassModel(editIndex);
				closeWindow();
				e.consume();
			}
		};

		// Attach handlers to buttons
		newClassSubmit.setOnAction(submitClassEvent);
		deleteClass.setOnAction(deleteClassEvent);

		// Display scene
		Scene scene = new Scene(newClassInterface, 300, 230);
		this.setScene(scene);

	}

	/**
	 * Closes the dialog box
	 */
	private void closeWindow() {
		this.close();
	}
}
