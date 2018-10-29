package application.view.context;

import application.include.Model;
import application.view.NewClassWindow;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.stage.Modality;

public class FieldMenu extends ContextMenu
{
	
	Model data;
	
	MenuItem newClass = new MenuItem("New Class Block...");
	MenuItem newLink = new MenuItem("New Link...");
	
	EventHandler<ActionEvent> newClassEvent = new EventHandler<ActionEvent> ()
	{
		@Override
		public void handle(ActionEvent e)
		{
			NewClassWindow dialog = new NewClassWindow(-1, data);
			dialog.initModality(Modality.APPLICATION_MODAL);
			dialog.show();
			e.consume();
		}
	};
	
	public FieldMenu(Model dataIn)
	{
		data = dataIn;
		
		newClass.setOnAction(newClassEvent);
		
		this.getItems().add(newClass);
		this.getItems().add(newLink);
	}
}
