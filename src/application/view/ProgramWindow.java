package application.view;

import java.io.File;
import java.io.IOException;

import application.include.Model;
import application.objects.ClassBlock;
import application.objects.Link;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.print.PageOrientation;
import javafx.print.Paper;
import javafx.print.PrintQuality;
import javafx.print.Printer;
import javafx.print.PrinterJob;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ProgramWindow extends Stage {

	final private double DEFAULT_HEIGHT = 720.0;
	final private double DEFAULT_WIDTH = 1280.0;
	private Model data;

	// Main Window elements
	private BorderPane root = new BorderPane();
	private GridPane tools = new GridPane();
	private ScrollPane center = new ScrollPane();
	public Pane mainPanel = new Pane();
	private MenuBar menu = new MenuBar();

	// Define menu elements
	private Menu file = new Menu("File");
	private Menu edit = new Menu("Edit");
	public MenuItem save = new MenuItem("Save...");
	public MenuItem load = new MenuItem("Load...");
	public MenuItem export = new MenuItem("Export...");
	public MenuItem clear = new MenuItem("Clear elements");

	// Define tool panel elements
	public Button newClass = new Button("New class...");
	public Button removeClass = new Button("Delete...");
	public Button newLink = new Button("New link...");

	public ProgramWindow(Model dataIn) {
		Stage ref = this;
		data = dataIn;
		this.setMinHeight(DEFAULT_HEIGHT);
		this.setMinWidth(DEFAULT_WIDTH);
		center.setContent(mainPanel);
		root.getStyleClass().add("root");
		mainPanel.getStyleClass().add("mainPanel");

		// Construct Menu bar
		file.getItems().add(save);
		file.getItems().add(load);
		file.getItems().add(export);
		edit.getItems().add(clear);
		menu.getMenus().add(file);
		menu.getMenus().add(edit);

		// Construct tool panel
		newClass.getStyleClass().add("toolbarButtons");
		removeClass.getStyleClass().add("toolbarButtons");
		newLink.getStyleClass().add("toolbarButtons");
		tools.add(newClass, 0, 0);
		tools.add(removeClass, 0, 1);
		tools.add(newLink, 0, 2);

		// Creates a new class dialog upon click
		EventHandler<ActionEvent> newClassEvent = new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				NewClassWindow dialog = new NewClassWindow(-1, data);
				dialog.initModality(Modality.APPLICATION_MODAL);
				dialog.show();
				e.consume();
			}
		};

		// Creates a new link dialog upon click
		EventHandler<ActionEvent> newLinkEvent = new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				NewLinkWindow dialog = new NewLinkWindow(data);
				dialog.initModality(Modality.APPLICATION_MODAL);
				dialog.show();
				e.consume();
			}
		};

		// Clears the main panel upon click
		EventHandler<ActionEvent> clearEvent = new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				data.clear();
				mainPanel.getChildren().clear();
			}
		};

		// Saves the current state of the Model
		EventHandler<ActionEvent> saveEvent = new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				FileChooser dialog = new FileChooser();
				dialog.setTitle("Save file...");
				File file = dialog.showSaveDialog(ref);
				if (file != null) {
					try {
						data.save(file);
					} catch (IOException ex) {
						System.err.println("IO Failure: " + ex);
					}
				}
				e.consume();
			}
		};

		// Loads the saved Model state
		EventHandler<ActionEvent> loadEvent = new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {

				FileChooser dialog = new FileChooser();
				dialog.setTitle("Open UML file...");
				File file = dialog.showOpenDialog(ref);

				if (file != null) {
					mainPanel.getChildren().clear();
					data.clear();
					try {
						data.load(file);
					} catch (IOException ex) {
						System.err.println("IO Failure: " + ex);
					}
				}
				e.consume();
			}
		};

		// Export the main panel to a print dialog
		EventHandler<ActionEvent> exportEvent = new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				PrinterJob job = PrinterJob.createPrinterJob();
				Printer printer = Printer.getDefaultPrinter();
				if (job != null) {
					job.showPrintDialog(ref);
					job.getJobSettings().setPageLayout(
							printer.createPageLayout(Paper.NA_LETTER, PageOrientation.LANDSCAPE, 0, 0, 0, 0));
					job.getJobSettings().setPrintQuality(PrintQuality.HIGH);
					job.printPage(mainPanel);
					job.endJob();
				}
				e.consume();
			}
		};

		// Apply handlers
		newClass.setOnAction(newClassEvent);
		newLink.setOnAction(newLinkEvent);
		clear.setOnAction(clearEvent);
		save.setOnAction(saveEvent);
		load.setOnAction(loadEvent);
		export.setOnAction(exportEvent);

		// Place items on stage
		root.setTop(menu);
		root.setLeft(tools);
		root.setCenter(center);
		root.getCenter().getStyleClass().add("pad");
		Scene scene = new Scene(root, DEFAULT_WIDTH, DEFAULT_HEIGHT);
		scene.getStylesheets().add(getClass().getResource("../include/application.css").toExternalForm());
		this.setScene(scene);
	}

	/**
	 * Clears the main panel of all visible elements
	 */
	public void clear() {
		mainPanel.getChildren().clear();
	}

	/**
	 * Add a class to the main panel
	 * 
	 * @param in
	 *            The Class Block to be added
	 */
	public void addClass(ClassBlock in) {
		mainPanel.getChildren().add(in);
	}

	/**
	 * Remove the given class from the main panel
	 * 
	 * @param in
	 *            The class block to be removed
	 */
	public void removeClass(ClassBlock in) {
		mainPanel.getChildren().remove(in);
	}

	/**
	 * Add a link to the main panel
	 * 
	 * @param in
	 *            The link to be added
	 */
	public void addLink(Link in) {
		mainPanel.getChildren().add(in);
	}

	/**
	 * Removes the given link from the main panel
	 * 
	 * @param in
	 *            The link to be removed
	 */
	public void removeLink(Link in) {
		mainPanel.getChildren().remove(in);
	}

	/**
	 * Refreshes the main panel's layout information
	 */
	public void applyCss() {
		mainPanel.applyCss();
		mainPanel.layout();
	}
}
