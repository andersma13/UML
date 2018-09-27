package application;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.UnaryOperator;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.TextFormatter.Change;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

/*
 * TODO:
 * - Bound class blocks in central pane (atm they can be dragged entirely out of the window.
 * - Select specific elements (perhaps this.getIndex() on click?)
 * - Factories
 * --- Dialog box factory
 * --- Move the generateClass and generateLink to a dedicated factory
 * - Refactor (does anyone know how to move classes into subfolders on a java project?) 
 * 
 * PARTIAL:
 * - Draw connections
 * - Save and load Model data
 * 
 * DONE:
 * - Drag and drop elements
 * - Make ClassBlock size dynamic (ie. removing unused cells) 
 */

public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
        final int DEFAULT_HEIGHT = 600;
        final int DEFAULT_WIDTH = 660;
		
		try {
			// Set up the stage
			primaryStage.setMinHeight(DEFAULT_HEIGHT);
			primaryStage.setMinWidth(DEFAULT_WIDTH);
			BorderPane root = new BorderPane();
			root.getStyleClass().add("root");
			GridPane tools = new GridPane();
			ScrollPane sp = new ScrollPane();
			Pane center = new Pane();
			center.getStyleClass().add("center");
			Model data = new Model();

			// Define menu bar
			MenuBar menu = new MenuBar();
			Menu file = new Menu("File");
			Menu edit = new Menu("Edit");
			MenuItem save = new MenuItem("Save");
			MenuItem load = new MenuItem("Load...");
			MenuItem clear = new MenuItem("Clear elements");

			List<ClassBlock> classes = new ArrayList<ClassBlock>();
			List<Link> links = new ArrayList<Link>();

			// Add menu items to menus
			file.getItems().add(save);
			file.getItems().add(load);
			edit.getItems().add(clear);
			menu.getMenus().add(file);
			menu.getMenus().add(edit);

			// Define tool panel
			Button newClass = new Button("New class...");
			Button removeClass = new Button("Delete...");
			Button newLink = new Button("New link...");
			newClass.getStyleClass().add("toolbarButtons");
			removeClass.getStyleClass().add("toolbarButtons");
			newLink.getStyleClass().add("toolbarButtons");
			tools.add(newClass, 0, 0);
			tools.add(removeClass, 0, 1);
			tools.add(newLink, 0, 2);

			// Define save behavior
			EventHandler<ActionEvent> saveEvent = new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent e) {
					try {
						data.save("save.uml");
					} catch (IOException e1) {
						System.err.println("IO Failure.");
					}
					e.consume();
				}
			};

			// Define load behavior
			EventHandler<ActionEvent> loadEvent = new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent e) {
					center.getChildren().clear();
					data.clear();
					try {
						data.load("save.uml");
					} catch (IOException e1) {
						System.err.println("IO Failure.");
					}
					refresh(data, center, classes, links, primaryStage);
				}
			};

			// Define clear behavior
			EventHandler<ActionEvent> clearEvent = new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent e) {
					center.getChildren().clear();
					data.clear();
					classes.clear();
					links.clear();
				}
			};

			// Define New Class behavior
			EventHandler<ActionEvent> newClassEvent = new EventHandler<ActionEvent>() {

				@Override
				public void handle(ActionEvent e) {

					Stage newClassDialog = classDialog(data, center, classes, links, -1, primaryStage);
					newClassDialog.initModality(Modality.APPLICATION_MODAL);
					newClassDialog.initOwner(primaryStage);
					newClassDialog.show();
					e.consume();

				}
			};

			// Define New Link behavior
			EventHandler<ActionEvent> newLinkEvent = new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent e) {

					// Define dialog stage
					final Stage newLinkDialog = new Stage();
					newLinkDialog.initModality(Modality.APPLICATION_MODAL);
					newLinkDialog.initOwner(primaryStage);

					// Define interface
					GridPane newLinkInterface = new GridPane();
					Text newLinkTitle = new Text("New link...");
					TextField newLinkLabel = new TextField();
					TextField newLinkSrc = new TextField();
					TextField newLinkDest = new TextField();
					Button newLinkSubmit = new Button("Submit");

					// Declare a TextFormatter filter to ensure integer values
					UnaryOperator<Change> integers = change -> {

						String text = change.getText();
						return (text.matches("[0-9]*") ? change : null);

					};

					TextFormatter<String> forceIntSrc = new TextFormatter<>(integers);
					TextFormatter<String> forceIntDest = new TextFormatter<>(integers);
					newLinkSrc.setTextFormatter(forceIntSrc);
					newLinkDest.setTextFormatter(forceIntDest);

					// Define behavior on Submit
					EventHandler<ActionEvent> newLinkSubmitEvent = new EventHandler<ActionEvent>() {

						@Override
						public void handle(ActionEvent e) {

							try {

								int srcIn = Integer.parseInt(newLinkSrc.getText());
								int destIn = Integer.parseInt(newLinkDest.getText());

								// Create a new ClassModel object out of the given values
								data.addLink(new int[] { data.getLinkTail(), 0, srcIn, destIn, 0, 1, 0, 1 },
										newLinkTitle.getText());

								refresh(data, center, classes, links, primaryStage);

								newLinkDialog.close();
								e.consume();

							}

							catch (NumberFormatException ex) {
							}

						}
					};

					newLinkSubmit.setOnAction(newLinkSubmitEvent);

					// Place elements on Dialog
					newLinkLabel.setPromptText("Link label...");
					newLinkSrc.setPromptText("Link Source");
					newLinkDest.setPromptText("Link Dest");

					newLinkInterface.add(newLinkTitle, 0, 0, 2, 1);
					newLinkInterface.add(newLinkLabel, 0, 1, 2, 1);
					newLinkInterface.add(newLinkSrc, 0, 5);
					newLinkInterface.add(newLinkDest, 1, 5);
					newLinkInterface.add(newLinkSubmit, 1, 6);

					// Show Dialog
					Scene dialogScene = new Scene(newLinkInterface, 300, 230);
					newLinkDialog.setScene(dialogScene);
					newLinkDialog.show();
					e.consume();

				}
			};

			// Define Remove Class behavior
			EventHandler<ActionEvent> removeClassEvent = new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent e) {

				}
			};

			// Attach handlers to buttons
			save.setOnAction(saveEvent);
			load.setOnAction(loadEvent);
			clear.setOnAction(clearEvent);
			newClass.setOnAction(newClassEvent);
			removeClass.setOnAction(removeClassEvent);
			newLink.setOnAction(newLinkEvent);

			// Place elements on stage
			root.setTop(menu);
			root.setLeft(tools);
			sp.setContent(center);
			sp.setPannable(true);
			root.setCenter(sp);
			Scene scene = new Scene(root, DEFAULT_WIDTH, DEFAULT_HEIGHT);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();

		} catch (Exception e) {

			e.printStackTrace();

		}
	}

	/**
	 * Constructs and returns a new Class dialog screen
	 * 
	 * @param data
	 * @param center
	 * @param classes
	 * @param links
	 * @param edit
	 * @param primaryStage
	 * @return
	 */
	private Stage classDialog(Model data, Pane center, List<ClassBlock> classes, List<Link> links, int edit,
			Stage primaryStage) {

		// Define dialog stage
		final Stage newClassDialog = new Stage();

		// Define interface
		GridPane newClassInterface = new GridPane();
		Text newClassTitle = new Text(edit == -1 ? "New class..." : "Edit Class...");
		TextField newClassName = new TextField();
		TextArea newClassAttr = new TextArea();
		TextArea newClassOper = new TextArea();
		TextArea newClassDesc = new TextArea();
		TextField newClassX = new TextField();
		TextField newClassY = new TextField();
		Button newClassSubmit = new Button("Submit");

		if (edit != -1) {
			Button deleteClass = new Button("Delete");
			newClassInterface.add(deleteClass, 0, 6);

			EventHandler<ActionEvent> deleteClassEvent = new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent e) {
					data.removeBlock(edit);
					refresh(data, center, classes, links, primaryStage);
					newClassDialog.close();
					e.consume();
				}
			};

			deleteClass.setOnAction(deleteClassEvent);
		}

		// Declare a TextFormatter filter to ensure integer values
		UnaryOperator<Change> integers = change -> {

			String text = change.getText();
			return (text.matches("[0-9]*") ? change : null);

		};

		TextFormatter<String> forceIntX = new TextFormatter<>(integers);
		TextFormatter<String> forceIntY = new TextFormatter<>(integers);
		newClassX.setTextFormatter(forceIntX);
		newClassY.setTextFormatter(forceIntY);

		// Define behavior on Submit
		EventHandler<ActionEvent> newClassEvent = new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent e) {

				if (edit == -1) {
					// Create a new ClassModel object out of the given values
					data.addBlock(
							new int[] { data.getClassTail(), Integer.parseInt(newClassX.getText()),
									Integer.parseInt(newClassY.getText()), 100, 100 },
							new String[] { newClassName.getText(), newClassAttr.getText(), newClassOper.getText(),
									newClassDesc.getText() });
				} else {
					data.getClass(edit).setName(newClassName.getText());
					data.getClass(edit).setAttr(newClassAttr.getText());
					data.getClass(edit).setOper(newClassOper.getText());
					data.getClass(edit).setDesc(newClassDesc.getText());
				}

				refresh(data, center, classes, links, primaryStage);

				newClassDialog.close();
				e.consume();

			}
		};

		newClassSubmit.setOnAction(newClassEvent);

		// Place elements on Dialog
		if (edit == -1) {
			newClassName.setPromptText("Class name...");
			newClassAttr.setPromptText("Class Attributes...");
			newClassOper.setPromptText("Class Operations...");
			newClassDesc.setPromptText("Class Description...");
			newClassX.setPromptText("Class X");
			newClassX.setText("0");
			newClassY.setPromptText("Class Y");
			newClassY.setText("0");
		} else {
			newClassName.setText(data.getClass(edit).getName());
			newClassAttr.setText(data.getClass(edit).getAttr());
			newClassOper.setText(data.getClass(edit).getOper());
			newClassDesc.setText(data.getClass(edit).getDesc());
			newClassX.setText(String.valueOf(data.getClass(edit).getXPos()));
			newClassY.setText(String.valueOf(data.getClass(edit).getYPos()));
		}

		newClassInterface.add(newClassTitle, 0, 0, 2, 1);
		newClassInterface.add(newClassName, 0, 1, 2, 1);
		newClassInterface.add(newClassAttr, 0, 2, 2, 1);
		newClassInterface.add(newClassOper, 0, 3, 2, 1);
		newClassInterface.add(newClassDesc, 0, 4, 2, 1);
		newClassInterface.add(newClassX, 0, 5);
		newClassInterface.add(newClassY, 1, 5);
		newClassInterface.add(newClassSubmit, 1, 6);

		// Show Dialog
		Scene dialogScene = new Scene(newClassInterface, 300, 230);
		newClassDialog.setScene(dialogScene);
		return newClassDialog;

	}

	/**
	 * Removes all nodes from main panel and redraws them from the Model
	 * 
	 * @param data
	 * @param center
	 * @param classes
	 * @param links
	 */
	private void refresh(Model data, Pane center, List<ClassBlock> classes, List<Link> links, Stage primaryStage) {
		center.getChildren().clear();
		classes.clear();
		links.clear();

		for (int i = 0; i != data.getClassTail(); ++i) {

			// Generate ClassBlock object
			ClassBlock newClass = data.generateClassBlock(i);
			makeDraggable(newClass, data, i, classes, links, center, primaryStage);
			newClass.getStyleClass().add("classBlock");
			classes.add(newClass);

			// Place in the main window and close dialog
			newClass.setLayoutX((double) data.getClass(i).getXPos());
			newClass.setLayoutY((double) data.getClass(i).getYPos());
			center.getChildren().add(newClass);

		}

		for (int i = 0; i != data.getLinkTail(); ++i) {
			// Generate Link object
			int srcIn = data.getLink(i).getSource();
			int destIn = data.getLink(i).getDest();
			Link newLink = new Link(classes.get(srcIn).getNode(), classes.get(destIn).getNode());
			links.add(newLink);
			classes.get(srcIn).getNode().getXProperty().addListener(new ChangeListener<Number>() {
				@Override
				public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
					newLink.setStartX(gridify((int) newValue));
				}
			});

			classes.get(srcIn).getNode().getYProperty().addListener(new ChangeListener<Number>() {
				@Override
				public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
					newLink.setStartY(gridify((int) newValue));
				}
			});

			classes.get(destIn).getNode().getXProperty().addListener(new ChangeListener<Number>() {
				@Override
				public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
					newLink.setEndX(gridify((int) newValue));
				}
			});

			classes.get(destIn).getNode().getYProperty().addListener(new ChangeListener<Number>() {
				@Override
				public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
					newLink.setEndY(gridify((int) newValue));
				}
			});

			// Place in the main window and close dialog
			center.getChildren().add(newLink);
			newLink.toBack();
		}
	}

	/**
	 * Wrap nodes in this method to enable drag and drop
	 * 
	 * @param node
	 * @param data
	 * @param i
	 * @param links
	 */
	private void makeDraggable(ClassBlock node, Model data, int i, List<ClassBlock> classes, List<Link> links,
			Pane center, Stage primaryStage) {
		final Delta delta = new Delta();

		// Turns the cursor into a hand over draggable elements
		node.setOnMouseEntered(me -> {
			if (!me.isPrimaryButtonDown()) {
				node.getScene().setCursor(Cursor.HAND);
			}
		});

		// Turns the cursor normal after leaving a draggable element
		node.setOnMouseExited(me -> {
			if (!me.isPrimaryButtonDown()) {
				node.getScene().setCursor(Cursor.DEFAULT);
			}
		});

		// Turn the cursor normal during a drag
		node.setOnMousePressed(me -> {
			if (me.isPrimaryButtonDown()) {
				node.getScene().setCursor(Cursor.DEFAULT);
			}
			delta.x = me.getX();
			delta.y = me.getY();
			node.getScene().setCursor(Cursor.MOVE);
		});

		// Turn cursor normal upon release of draggable item
		node.setOnMouseReleased(me -> {
			if (!me.isPrimaryButtonDown()) {
				node.getScene().setCursor(Cursor.DEFAULT);
			}
		});

		// Update node position based on dragged position
		node.setOnMouseDragged(me -> {

			// set Model position
			data.getClass(i).setXPos((int) (node.getLayoutX() + me.getX() - delta.x));
			data.getClass(i).setYPos((int) (node.getLayoutY() + me.getY() - delta.y));
			// Set link node position
			node.getNode().setX((int) (data.getClass(i).getXPos() + (node.getWidth() / 2) + me.getX() - delta.x));
			node.getNode().setY((int) (data.getClass(i).getYPos() + (node.getHeight() / 2) + me.getY() - delta.y));
			node.setLayoutX(data.getClass(i).getXPos());
			node.setLayoutY(data.getClass(i).getYPos());

		});

		// Double click to edit
		node.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent mouseEvent) {
				if (mouseEvent.getButton().equals(MouseButton.PRIMARY)) {
					if (mouseEvent.getClickCount() == 2) {
						Stage newClassDialog = classDialog(data, center, classes, links, i, primaryStage);
						newClassDialog.initModality(Modality.APPLICATION_MODAL);
						newClassDialog.initOwner(primaryStage);
						newClassDialog.show();
						mouseEvent.consume();
					}
				}
			}
		});
	}

	/**
	 * Used with the makeDraggable method Stores the x and y values on mouse down so
	 * they can be removed from the final position
	 */
	private class Delta {

		public double x;
		public double y;

	}

	/**
	 * Snaps the given values to a grid
	 * 
	 * @param i
	 *            the value to be rounded
	 * @return the given value rounded to the nearest 10
	 */
	private int gridify(int i) {
		return (i >= 10 ? (i % 10 < 5 ? i - (i % 10) : i + (10 - (i % 10))) : 10);
	}

	// Launch the program
	public static void main(String[] args) {

		launch(args);

	}
}
