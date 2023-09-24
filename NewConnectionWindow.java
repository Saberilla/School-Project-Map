// PROG2 VT2023, Inlamningsuppgift, del 2
// Grupp 077
// Sara Berg sabe4314
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.GridPane;

public class NewConnectionWindow extends Alert {
	
	private TextField nameField = new TextField();
	private TextField timeField = new TextField();
	
	public NewConnectionWindow(City a, City b) { 
		super(AlertType.CONFIRMATION);
		GridPane grid = new GridPane();
		grid.setPadding(new Insets(10));
		grid.setAlignment(Pos.CENTER);
		grid.setHgap(5);
		grid.setVgap(5);
		grid.addRow(0, new Label("Name:"), nameField);
		grid.addRow(1, new Label("Time:"), timeField);
		setHeaderText("Connection from " + a.getName() +" to " + b.getName());
		//setHeaderText("Connection from ");
		getDialogPane().setContent(grid);
		
			
	}
	
	public String getName() {
		return nameField.getText();
	}
	
	public int getTime() {
		return Integer.parseInt(timeField.getText());
	}
	
	

}
