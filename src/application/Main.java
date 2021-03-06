/************************************************************************************\
 * This class describes the relationship between all the various elements of				*
 * the model and the view.  Once all the elements are in place, everything else			*
 * should operate in a pretty modular fashion.  By using properties and listeners		*
 * rather than bare data types (like String or int), the relationship between				*
 * a given piece of data can be established and then ignored.  Rather than having		*
 * to continually pass these pieces of data back and forth, a listener allows a 		*
 * change to propogate to the necessary places in other elements.  It's a much more	*
 * complex setup than we had before, but much cleaner and much more scalable.				*
 \***********************************************************************************/

package application;

import application.include.Model;
import application.include.Model.ClassModel;
import application.include.Model.LinkModel;
import application.objects.ClassBlock;
import application.objects.Link;
import application.view.NewClassWindow;
import application.view.NewLinkWindow;
import application.view.ProgramWindow;
import application.view.context.ClassMenu;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ListChangeListener;
import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class Main extends Application {

	int linkSrc = -1;
	Line line;
	
	/**
	 * Model: this class will handle all objects in the program. Data can be pulled
	 * from it at will and pushed to the view.
	 */
	static Model data = new Model();

	/**
	 * View: This class displays the data for user interaction. It holds a reference
	 * to the model to make passing information back into the model cleaner and
	 * easier.
	 */
	static ProgramWindow window = new ProgramWindow(data);

	@Override
	public void start(Stage primaryStage) {

		/**
		 * Listen for changes to the Class model and update the view accordingly.
		 * Whenever the list of Classes is updated, that class block can be redrawn
		 * while leaving the others untouched. New class blocks can be added and removed
		 * at will.
		 */
		data.getClassProperty().addListener(classListener());

		/**
		 * Listen for changes to the Link model and update the view accordingly. At the
		 * moment, only listens for new links. Removing links is done by the
		 * classListener, because currently links cannot be removed on their own. When
		 * we implement a method to remove them on their own, this listener can be
		 * modified to perform that action.
		 */
		data.getLinkProperty().addListener(linkListener());

		try {
			/**
			 * Make the main window visible
			 */
			window.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Generates and returns a ChangeListener to operate on the Class Model
	 * 
	 * @return the ChangeListener to handle each individual Class Model
	 */
	private ListChangeListener<ClassModel> classListener() {
		ListChangeListener<ClassModel> classListener = new ListChangeListener<ClassModel>() {
			@Override
			public void onChanged(Change<? extends ClassModel> c) {
				updateButtons();
				while (c.next()) {

					/*****************************
					 * ELEMENT MODIFIED
					 *****************************/

					if (c.wasUpdated()) {
						for (int i = c.getFrom(); i != c.getTo(); ++i) {
							// For each modified element, refresh the element's view
							data.getClass(i).setName(data.getClassModel(i).getName());
							data.getClass(i).setAttr(data.getClassModel(i).getAttr());
							data.getClass(i).setOper(data.getClassModel(i).getOper());
							data.getClass(i).setDesc(data.getClassModel(i).getDesc());

						}
					} else {
						/*****************************
						 * ELEMENT ADDED
						 *****************************/

						if (c.wasAdded()) {
							for (ClassModel added : c.getAddedSubList()) {
								// Generate new class block
								ClassBlock newClass = new ClassBlock(added);
								newClass.setLayoutX((double) added.getXPos());
								newClass.setLayoutY((double) added.getYPos());
								ClassMenu classContextMenu = new ClassMenu(added.getIndex(), data);
								// data.addMenu(added.getIndex(), classContextMenu);

								// Declare delta to be used with click events
								final Delta delta = new Delta();

								/*****************************
								 * SET UP LISTENERS
								 *****************************/

								// Name listener
								added.getNameProp().addListener(new ChangeListener<String>() {
									@Override
									public void changed(ObservableValue<? extends String> observable, String oldValue,
											String newValue) {
										newClass.setName(newValue);
									}
								});

								// Attribute listener
								added.getAttrProp().addListener(new ChangeListener<String>() {
									@Override
									public void changed(ObservableValue<? extends String> observable, String oldValue,
											String newValue) {
										newClass.setAttr(newValue);
									}
								});

								// Operation property
								added.getOperProp().addListener(new ChangeListener<String>() {
									@Override
									public void changed(ObservableValue<? extends String> observable, String oldValue,
											String newValue) {
										newClass.setOper(newValue);
									}
								});

								// Description property
								added.getDescProp().addListener(new ChangeListener<String>() {
									@Override
									public void changed(ObservableValue<? extends String> observable, String oldValue,
											String newValue) {
										newClass.setDesc(newValue);
									}
								});

								/*****************************
								 * CURSOR MODIFICATIONS
								 *****************************/

								// Turns the cursor into a hand over draggable elements
								newClass.setOnMouseEntered(new EventHandler<MouseEvent>() {
									@Override
									public void handle(MouseEvent e) {
										if (!e.isPrimaryButtonDown()) {
											newClass.getScene().setCursor(Cursor.HAND);
										}
									}
								});

								// Turns the cursor normal after leaving a draggable element
								newClass.setOnMouseExited(new EventHandler<MouseEvent>() {
										@Override
								public void handle(MouseEvent e) {
										newClass.getScene().setCursor(Cursor.DEFAULT);
									}
								});

								// Turn the cursor normal during a drag
								newClass.setOnMousePressed(new EventHandler<MouseEvent>() {
									@Override
									public void handle(MouseEvent e) {
										
										if (data.safeToSave()) {
											data.saveUndoState();
											data.clearRedoState();
										}
										if (e.isPrimaryButtonDown()) {
											newClass.getScene().setCursor(Cursor.DEFAULT);
										}
										delta.x = e.getX();
										delta.y = e.getY();
										newClass.getScene().setCursor(Cursor.MOVE);
										

									}
								});

								// Turn cursor normal upon release of draggable item
								newClass.setOnMouseReleased(new EventHandler<MouseEvent>() {
									@Override
									public void handle(MouseEvent e) {
										if (!e.isPrimaryButtonDown()) {
											newClass.getScene().setCursor(Cursor.DEFAULT);
										}

										if (!data.isUndoEmpty())
											window.undo.setDisable(false);
										else
											window.undo.setDisable(true);
										if (!data.isRedoEmpty())
											window.redo.setDisable(false);
										else
											window.redo.setDisable(true);
										
									}
								});

								/*****************************
								 * MAKE DRAGGABLE/SELECTABLE
								 *****************************/
								
								// Makes the class block draggable
								newClass.setOnMouseDragged(new EventHandler<MouseEvent>() {
									@Override
									public void handle(MouseEvent e) {
										
										if (!data.isLinkable())
										{
										// set stored X and Y positions
										added.setXPos((int) (newClass.getLayoutX() + e.getX() - delta.x));
										added.setYPos((int) (newClass.getLayoutY() + e.getY() - delta.y));

										// Set link node position
										newClass.getNode().setX((int) (added.getXPos() + (newClass.getWidth() / 2)
												+ e.getX() - delta.x));
										newClass.getNode().setY((int) (added.getYPos() + (newClass.getHeight() / 2)
												+ e.getY() - delta.y));
										newClass.setLayoutX(added.getXPos());
										newClass.setLayoutY(added.getYPos());

										// Set the bounds of the ClassBlock within the LinkNode
										newClass.getNode().setBounds((int) (added.getXPos() + e.getX() - delta.x),
												(int) (added.getXPos() + (newClass.getWidth()) + e.getX() - delta.x),
												(int) (added.getYPos() + e.getY() - delta.y),
												(int) (added.getYPos() + (newClass.getHeight()) + e.getY() - delta.y));
										newClass.getNode().updateLink();
										}
										else
										{
											if (window.mainPanel.getChildren().contains(line))
											{
												line.setEndX(added.getXPos() + e.getX());
												line.setEndY(added.getYPos() + e.getY());
											}
										}
									}
								});

								// Makes the class block selectable
								newClass.setOnMouseClicked(new EventHandler<MouseEvent>() {
									@Override
									public void handle(MouseEvent e) {
										// Check for double click coming from primary mouse button
										if (e.getButton().equals(MouseButton.PRIMARY)) {
											if (e.getClickCount() == 2) {
												// Launch class edit window
												NewClassWindow dialog = new NewClassWindow(added.getIndex(), data);
												dialog.initModality(Modality.APPLICATION_MODAL);
												dialog.show();
											}
											classContextMenu.hide();
											e.consume();
										} else if (e.getButton() == MouseButton.SECONDARY) {
											classContextMenu.show(newClass, e.getScreenX(), e.getScreenY());
										}

									}
								});
						
						     
								/****************
								 * DRAG TO LINK *
								 ****************/
								
								//	Sets linkSrc to wherever the drag started, creates line
						        newClass.setOnDragDetected(new EventHandler<MouseEvent>() {
									@Override
									public void handle(MouseEvent e) {
										newClass.startFullDrag();
										
										if (data.isLinkable())
										{
											linkSrc = added.getIndex();
											
											//line = new Line(added.getXPos() + added.getWidth()/2, added.getYPos() + added.getHeight()/2, e.getX() - delta.x, e.getY() - delta.y);
											line = new Line(added.getXPos() + e.getX(), added.getYPos() + e.getY(), added.getXPos() + e.getX(), added.getYPos() + e.getY());
											line.setStroke(Color.GRAY);
											line.getStrokeDashArray().addAll(3.0);
											window.mainPanel.getChildren().add(line);
											line.toBack();
										}
									}
						        });
						        
						        //	Sets destination and brings up new link editor
						        newClass.setOnMouseDragReleased(new EventHandler<MouseEvent>() {
									@Override
									public void handle(MouseEvent e) {
										if (data.isLinkable())
										{
											if (linkSrc != added.getIndex() && linkSrc != -1)
											{	
												// Create link window, with filled in src/dest
												window.mainPanel.getChildren().remove(line);
												NewLinkWindow dialog = new NewLinkWindow(-1, data);
												dialog.setSrc(linkSrc);
												dialog.setDest(added.getIndex());
												dialog.initModality(Modality.APPLICATION_MODAL);
												dialog.show();
											}

											//	Reset src variable
											linkSrc = -1;
											
											updateButtons();
										}										
									}
						        });
						        
						        
						        //	Handles erasing temp line on mouse release
						        window.addEventFilter(MouseEvent.MOUSE_RELEASED, new EventHandler<MouseEvent>(){
									@Override
									public void handle(MouseEvent e) {
				
										if (window.mainPanel.getChildren().contains(line))
										{
											window.mainPanel.getChildren().remove(line);
											window.applyCss();
											
										}
										
										//	Reset src variable
										linkSrc = -1;
									}
						        });

								// Display class
								addClass(newClass);
								newClass.toFront();
								window.applyCss();
								newClass.initWidthHeight();

								// Set the bounds of the ClassBlock within the LinkNode
								newClass.getNode().setBounds((int) (added.getXPos()),
										(int) (added.getXPos() + newClass.getWidth()), (int) (added.getYPos()),
										(int) (added.getYPos() + newClass.getHeight()));
							}
						}

						/*****************************
						 * ELEMENT REMOVED
						 *****************************/

						else if (c.wasRemoved()) {
							if (c.getList().size() != 0) {
								for (ClassModel removed : c.getRemoved()) {
									int pivot = removed.getIndex();
									int bound = data.getLinkTail();

									// Remove links that connected to removed block
									for (int i = 0; i != bound; ++i) {
										if (data.getLinkModel(i).getSource() == pivot
												|| data.getLinkModel(i).getDest() == pivot) {
											data.removeLinkModel(i);
											--bound;
											--i;
										}
									}

									// Shift all index references past the removed down one
									for (int i = 0; i != data.getLinkTail(); ++i) {
										data.getLinkModel(i).setIndex(i);
										if (data.getLinkModel(i).getSource() > pivot) {
											data.getLinkModel(i).setSource(data.getLinkModel(i).getSource() - 1);
										}
										if (data.getLinkModel(i).getDest() > pivot) {
											data.getLinkModel(i).setDest(data.getLinkModel(i).getDest() - 1);
										}
									}

									for (int i = pivot; i != data.getClassTail(); ++i) {
										data.getClassModel(i).setIndex(i);
									}

									// erase classes
									window.removeClass(data.getClass(removed.getIndex()));
									data.removeClass(removed.getIndex());
								}
							} else {

							}
						}
					}
				}
			}
		};
		return classListener;
	}

	/**
	 * Generates and returns a ChangeListener to operate on the Link Model
	 * 
	 * @return the ChangeListener to handle each individual LinkModel
	 */
	private ListChangeListener<LinkModel> linkListener() {
		ListChangeListener<LinkModel> linkListener = new ListChangeListener<LinkModel>() {
			@Override
			public void onChanged(Change<? extends LinkModel> c) {
				while (c.next()) {
					if (c.wasUpdated()) {
						for (int i = c.getFrom(); i != c.getTo(); ++i) {

						}
					} else {
						if (c.wasAdded()) {
							for (LinkModel added : c.getAddedSubList()) {
								int srcIndex = added.getSource();
								int destIndex = added.getDest();

								// if Source or Dest Min are < 0, entire string should be *, otherwise express a
								// range Min to Max (including * for int<0 Max)
								String srcMulti;
								String destMulti;

								srcMulti = stringifyMulti(added.getSourceMin(), added.getSourceMax());
								destMulti = stringifyMulti(added.getDestMin(), added.getDestMax());

								Link newLink = new Link(data.getClass(srcIndex).getNode(),
										data.getClass(destIndex).getNode(), added.getLabel(), added.getType(), srcMulti,
										destMulti);

								/*****************************
								 * SET UP LISTENERS
								 *****************************/

								// Label listener
								added.getLabelProp().addListener(new ChangeListener<String>() {
									@Override
									public void changed(ObservableValue<? extends String> observable, String oldValue,
											String newValue) {
										newLink.setLabel(newValue);
										
										updateButtons();
									}
								});

								// Src Min listener
								added.getSourceMinProp().addListener(new ChangeListener<Number>() {
									@Override
									public void changed(ObservableValue<? extends Number> observable, Number oldValue,
											Number newValue) {
										newLink.setSrcMultiplicity(
												stringifyMulti((int) newValue, added.getSourceMax()));
										
										updateButtons();
									}
								});

								// Src Max listener
								added.getSourceMaxProp().addListener(new ChangeListener<Number>() {
									@Override
									public void changed(ObservableValue<? extends Number> observable, Number oldValue,
											Number newValue) {
										newLink.setSrcMultiplicity(
												stringifyMulti(added.getSourceMin(), (int) newValue));
										
										updateButtons();
									}
								});

								// Dest Min listener
								added.getDestMinProp().addListener(new ChangeListener<Number>() {
									@Override
									public void changed(ObservableValue<? extends Number> observable, Number oldValue,
											Number newValue) {
										newLink.setDestMultiplicity(stringifyMulti((int) newValue, added.getDestMax()));
										
										updateButtons();
									}
								});

								// Dest Max listener
								added.getDestMaxProp().addListener(new ChangeListener<Number>() {
									@Override
									public void changed(ObservableValue<? extends Number> observable, Number oldValue,
											Number newValue) {
										newLink.setDestMultiplicity(stringifyMulti(added.getDestMin(), (int) newValue));
										
										updateButtons();
									}
								});

								// Type listener
								added.getTypeProp().addListener(new ChangeListener<Number>() {
									@Override
									public void changed(ObservableValue<? extends Number> observable, Number oldValue,
											Number newValue) {
										newLink.setType((int) newValue);
										
										updateButtons();
									}
								});

								// Source X position listener
								data.getClass(srcIndex).getNode().getXProperty()
										.addListener(new ChangeListener<Number>() {
											@Override
											public void changed(ObservableValue<? extends Number> observable,
													Number oldValue, Number newValue) {
												newLink.setStartX((int) newValue);
												
												updateButtons();
											}
										});

								// Source Y position listener
								data.getClass(srcIndex).getNode().getYProperty()
										.addListener(new ChangeListener<Number>() {
											@Override
											public void changed(ObservableValue<? extends Number> observable,
													Number oldValue, Number newValue) {
												newLink.setStartY((int) newValue);
												
												updateButtons();
											}
										});

								// Dest X Position listener
								data.getClass(destIndex).getNode().getXProperty()
										.addListener(new ChangeListener<Number>() {
											@Override
											public void changed(ObservableValue<? extends Number> observable,
													Number oldValue, Number newValue) {
												newLink.setEndX((int) newValue);
												
												updateButtons();
											}
										});

								// Dest Y position listener
								data.getClass(destIndex).getNode().getYProperty()
										.addListener(new ChangeListener<Number>() {
											@Override
											public void changed(ObservableValue<? extends Number> observable,
													Number oldValue, Number newValue) {
												newLink.setEndY((int) newValue);
												
												updateButtons();
											}
										});

								/*****************************
								 * CURSOR MODIFICATIONS
								 *****************************/

								// Turns the cursor into a hand over draggable elements
								newLink.setOnMouseEntered(new EventHandler<MouseEvent>() {
									@Override
									public void handle(MouseEvent e) {
										if (!e.isPrimaryButtonDown()) {
											newLink.getScene().setCursor(Cursor.HAND);
										}
									}
								});

								// Turns the cursor normal after leaving a draggable element
								newLink.setOnMouseExited(new EventHandler<MouseEvent>() {
									@Override
									public void handle(MouseEvent e) {
										newLink.getScene().setCursor(Cursor.DEFAULT);
									}
								});

								// Makes the link selectable
								newLink.setOnMouseClicked(new EventHandler<MouseEvent>() {
									@Override
									public void handle(MouseEvent e) {
										// Check for double click coming from primary mouse button
										if (e.getButton().equals(MouseButton.PRIMARY)) {
											if (e.getClickCount() == 2) {
												// Launch link edit window
												NewLinkWindow dialog = new NewLinkWindow(added.getIndex(), data);
												dialog.initModality(Modality.APPLICATION_MODAL);
												dialog.show();
											}
											e.consume();
										}
									}
								});

								// Display Link
								addLink(newLink);
								newLink.toBack();

								newLink.updateLine();
							}
						} else if (c.wasRemoved()) {
							for (LinkModel removed : c.getRemoved()) {
								if (!data.isClearing()) {
									int pivot = removed.getIndex();

									data.getLink(pivot).warnLinkNodes();
									window.remove(data.getLink(pivot));
									data.removeLink(pivot);
									
									updateButtons();
								}
							}
						}

					}
				}
			}


			/**
			 * Take special flag values and mutate the output string based on their values (
			 * -2 means no entry, -1 means asterisk )
			 * 
			 * Ranges should appear in the form "min ... max" except when starting with "*"
			 * or if only one value has been entered
			 * 
			 * @param min
			 *            The user's chosen min value of multiplicity (start of range)
			 * @param max
			 *            The user's chosen max value of multiplicity (end of range)
			 * @return A correct complete string for the range
			 */
			private String stringifyMulti(int min, int max) {
				String multi;

				if (min == -1)
					multi = "*";
				else if (min == -2) {
					if (max == -2)
						multi = "";
					else
						multi = (max == -1 ? "*" : Integer.toString(max));
				} else if (max == -2)
					multi = (min == -1 ? "*" : Integer.toString(min));
				else
					multi = Integer.toString(min) + "..." + (max == -1 ? "*" : Integer.toString(max));

				return multi;
			}
		};

		return linkListener;
	}

	/**
	 * Used with the makeDraggable method Stores the x and y values on mouse down so
	 * they can be removed from the final position
	 */
	private class Delta {

		public double x;
		public double y;

		public Delta() {
		}

	}

	/**
	 * Update the redo and undo buttons on the ProgramWindow (they may have to be dimmed or lit)
	 * 
	 */
	private void updateButtons() {
		if (!data.isUndoEmpty())
			window.undo.setDisable(false);
		else
			window.undo.setDisable(true);
		if (!data.isRedoEmpty())
			window.redo.setDisable(false);
		else
			window.redo.setDisable(true);

	}
	
	/**
	 * Snaps the given values to a grid
	 * 
	 * @param i
	 *            the value to be rounded
	 * @return the given value rounded to the nearest 10
	 *//*
		 * private int gridify(int i) { return (i >= 10 ? (i % 10 < 5 ? i - (i % 10) : i
		 * + (10 - (i % 10))) : 10); }
		 */

	/**
	 * Adds the given Class Block to the visible window and to the Model
	 * 
	 * @param in
	 *            The Class Block to be added
	 */
	private void addClass(ClassBlock in) {
		data.addClass(in);
		window.addClass(in);
	}

	/**
	 * Adds the given Link to the visible window and to the Model.
	 * 
	 * @param in
	 *            The link to be added
	 */
	private void addLink(Link in) {
		data.addLink(in);
		window.addLink(in);
		window.addLabel(in.getLabel());
		window.addArrow(in.getArrow());
		window.addMultiplicity(in.getSrcMultiplicity());
		window.addMultiplicity(in.getDestMultiplicity());
	}

	public static void main(String[] args) {
		launch(args);
	}
}
