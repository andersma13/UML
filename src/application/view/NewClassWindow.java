package application.view;

import java.awt.AWTException;
import java.awt.Robot;
import java.util.function.UnaryOperator;

import application.include.Model;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.TextFormatter.Change;
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
	private TextField newClassX = new TextField("0");
	private TextField newClassY = new TextField("0");
	public Button newClassSubmit = new Button("Submit");
	public Button deleteClass = new Button("Delete");

	// Filter input for integral values
	private UnaryOperator<Change> integers = change -> {
		String text = change.getText();
		return (text.matches("[0-9]*") ? change : null);
	};

	private TextFormatter<String> forceIntX = new TextFormatter<>(integers);
	private TextFormatter<String> forceIntY = new TextFormatter<>(integers);

	// Override tab presses for field traversal
	private EventHandler<KeyEvent> tabTraverse = new EventHandler<KeyEvent>() {
		@Override
		public void handle(KeyEvent e) {
			KeyCode code = e.getCode();

			if (code == KeyCode.TAB && !e.isShiftDown() && !e.isControlDown()) {
				e.consume();
				e.getSource();
				try {
					Robot robot = new Robot();
					robot.keyPress(KeyCode.CONTROL.getCode());
					robot.keyPress(KeyCode.TAB.getCode());
					robot.delay(10);
					robot.keyRelease(KeyCode.TAB.getCode());
					robot.keyRelease(KeyCode.CONTROL.getCode());
				} catch (AWTException ex) {
				}
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
			newClassX.setPromptText("Class X");
			newClassY.setPromptText("Class Y");
		} else {
			newClassName.setText(data.getClassModel(editIndex).getName());
			newClassAttr.setText(data.getClassModel(editIndex).getAttr());
			newClassOper.setText(data.getClassModel(editIndex).getOper());
			newClassDesc.setText(data.getClassModel(editIndex).getDesc());
			newClassX.setText(String.valueOf(data.getClassModel(editIndex).getXPos()));
			newClassY.setText(String.valueOf(data.getClassModel(editIndex).getYPos()));
		}

		// Place elements on stage
		newClassInterface.add(newClassTitle, 0, 0, 2, 1);
		newClassInterface.add(newClassName, 0, 1, 2, 1);
		newClassInterface.add(newClassAttr, 0, 2, 2, 1);
		newClassInterface.add(newClassOper, 0, 3, 2, 1);
		newClassInterface.add(newClassDesc, 0, 4, 2, 1);
		newClassInterface.add(newClassX, 0, 5);
		newClassInterface.add(newClassY, 1, 5);
		newClassInterface.add(newClassSubmit, 1, 6);

		// Check for new class
		if (editIndex != -1) {
			newClassInterface.add(deleteClass, 0, 6);
		}

		// Allow tab traversal through TextAreas
		newClassAttr.addEventFilter(KeyEvent.KEY_PRESSED, tabTraverse);
		newClassOper.addEventFilter(KeyEvent.KEY_PRESSED, tabTraverse);
		newClassDesc.addEventFilter(KeyEvent.KEY_PRESSED, tabTraverse);

		// Limit X and Y to ints
		newClassX.setTextFormatter(forceIntX);
		newClassY.setTextFormatter(forceIntY);

		// Handler to submit the selected class
		EventHandler<ActionEvent> submitClassEvent = new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				if (editIndex == -1) {
					data.addClassModel(
							new int[] { data.getClassTail(), Integer.parseInt(newClassX.getText()),
									Integer.parseInt(newClassY.getText()), 100, 100 },
							new String[] { newClassName.getText(), newClassAttr.getText(), newClassOper.getText(),
									newClassDesc.getText() });
				} else {
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
