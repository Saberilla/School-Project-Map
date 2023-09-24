

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.GridPane;

public class WindowAlert extends Alert{
	
	private TextField nameField = new TextField();
	private TextField timeField = new TextField();
	private GridPane grid = new GridPane();
	
	public WindowAlert() {
		super(AlertType.CONFIRMATION);
		setView();
		setNewPlace();
		getDialogPane().setContent(grid);
	}
	
	public WindowAlert(City city1, City city2) {
		super(AlertType.CONFIRMATION);
		setView();
		setNewConnection(city1, city2);
		getDialogPane().setContent(grid);
	}
	
	public void setNewPlace() {
		grid.addRow(0, new Label("Name of Place: "), nameField);
		setHeaderText(null);
	}
	
	public void setNewConnection(City a, City b) {
		grid.addRow(0, new Label("Name:"), nameField);
		grid.addRow(1, new Label("Time:"), timeField);
		setHeaderText("Connection from " + a.getName() +" to " + b.getName());
	}
	
	public void setView() {
		grid.setPadding(new Insets(10));
		grid.setAlignment(Pos.CENTER);
		grid.setHgap(5);
		grid.setVgap(5);
	}
	
	public String getName() {
		return nameField.getText();
	}
	
	public int getTime() {
		return Integer.parseInt(timeField.getText());
	}

}
