package com.creatingskies.game.config.common;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.NodeOrientation;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.util.Callback;

import com.creatingskies.game.common.TableRowActivateButton;
import com.creatingskies.game.common.TableRowDeleteButton;
import com.creatingskies.game.common.TableRowEditButton;
import com.creatingskies.game.common.TableRowViewButton;
import com.creatingskies.game.model.IRecord;

public abstract class TableViewController {

	public abstract TableView<? extends IRecord> getTableView();
	
	private final int BUTTON_BAR_MIN_WIDTH_MULTIPLIER = 30;
	private final int BUTTON_MIN_WIDTH = 20;
	
	protected enum Action {
		VIEW, EDIT, DELETE, ACTIVATE;
	}
	
	@SuppressWarnings("rawtypes")
	protected Callback generateCellFactory(Action... actions){
		Callback<TableColumn<? extends IRecord, Object>, TableCell<? extends IRecord, Object>> 
		actionColumnCellFactory = 
            new Callback<TableColumn<? extends IRecord, Object>, TableCell<? extends IRecord, Object>>() {

	        @Override
	        public TableCell<? extends IRecord, Object> call(final TableColumn<? extends IRecord,Object> param) {
				final TableCell<? extends IRecord, Object> cell = new TableCell<IRecord, Object>() {
					@Override
					public void updateItem(Object item, boolean empty) {
						super.updateItem(item, empty);
						if (empty) {
							setText(null);
							setGraphic(null);
						} else {
							setAlignment(Pos.CENTER);
							setGraphic(createButtonBar(param, getIndex(), actions));
							setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
						}
					}
				};
				return cell;
			}
		};
		
		return actionColumnCellFactory;
	}
	
	private ButtonBar createButtonBar(TableColumn<? extends IRecord, Object> param,
			Integer index, Action... actions){
		ButtonBar buttonBar = new ButtonBar();
		buttonBar.setButtonMinWidth(BUTTON_MIN_WIDTH);
		buttonBar.setMinWidth(BUTTON_BAR_MIN_WIDTH_MULTIPLIER * buttonBar.getButtons().size());
		buttonBar.setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);

		for(Action action : actions){
			if(action.equals(Action.VIEW)){
				buttonBar.getButtons().add(createViewButton(param, index));
			} else if(action.equals(Action.EDIT)){
				buttonBar.getButtons().add(createEditButton(param, index));
			} else if(action.equals(Action.DELETE)){
				buttonBar.getButtons().add(createDeleteButton(param, index));
			} else if(action.equals(Action.ACTIVATE)){
				buttonBar.getButtons().add(createActivateButton(param, index));
			}
		}
		return buttonBar;
	}
	
	private Button createViewButton(
			TableColumn<? extends IRecord, Object> param, Integer index){
		Button viewButton = new TableRowViewButton();
		viewButton.setOnAction(createViewEventHandler(param, index));
		return viewButton;
	}
	
	private Button createEditButton(
			TableColumn<? extends IRecord, Object> param, Integer index){
		Button editButton = new TableRowEditButton();
		editButton.setOnAction(createEditEventHandler(param, index));
		return editButton;
	}
	
	private Button createDeleteButton(
			TableColumn<? extends IRecord, Object> param, Integer index){
		Button deleteButton = new TableRowDeleteButton();
		deleteButton.setOnAction(createDeleteEventHandler(param, index));
		return deleteButton;
	}
	
	private Button createActivateButton(
			TableColumn<? extends IRecord, Object> param, Integer index){
		Button activateButton = new TableRowActivateButton();
		activateButton.setOnAction(createActivateEventHandler(param, index));
		return activateButton;
	}
	
	private EventHandler<ActionEvent> createViewEventHandler(
			TableColumn<? extends IRecord, Object> param, Integer index) {
		return new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				param.getTableView().getSelectionModel().select(index);
				IRecord record = getTableView().getSelectionModel().getSelectedItem();
				if (record != null) {
					viewRecord(record);
				}
			}
		};
	}
	
	private EventHandler<ActionEvent> createEditEventHandler(
			TableColumn<? extends IRecord, Object> param, Integer index) {
		return new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				param.getTableView().getSelectionModel().select(index);
				IRecord record = getTableView().getSelectionModel().getSelectedItem();
				if (record != null) {
					editRecord(record);
				}
			}
		};
	}
	
	private EventHandler<ActionEvent> createDeleteEventHandler(
			TableColumn<? extends IRecord, Object> param, Integer index) {
		return new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				param.getTableView().getSelectionModel().select(index);
				IRecord record = getTableView().getSelectionModel().getSelectedItem();
				if (record != null) {
					deleteRecord(record);
				}
			}
		};
	}
	
	private EventHandler<ActionEvent> createActivateEventHandler(
			TableColumn<? extends IRecord, Object> param, Integer index) {
		return new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				param.getTableView().getSelectionModel().select(index);
				IRecord record = getTableView().getSelectionModel().getSelectedItem();
				if (record != null) {
					activateRecord(record);
				}
			}
		};
	}
	
	/*
	 * Override this methods to add functionality to action buttons
	 */
	protected void viewRecord(IRecord record){}
	
	protected void editRecord(IRecord record){}
	
	protected void deleteRecord(IRecord record){}
	
	protected void activateRecord(IRecord record){}
	
}
