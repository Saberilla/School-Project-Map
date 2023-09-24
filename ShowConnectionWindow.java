// PROG2 VT2023, Inlamningsuppgift, del 2
// Grupp 077
// Sara Berg sabe4314
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.GridPane;

public class ShowConnectionWindow extends Alert {
	
	private TextField nameField;
	private TextField timeField;
	
	public ShowConnectionWindow(City a, City b, Edge<City> edge) { 
		super(AlertType.CONFIRMATION);
		GridPane grid = new GridPane();
		grid.setPadding(new Insets(10));
		grid.setAlignment(Pos.CENTER);
		grid.setHgap(5);
		grid.setVgap(5);
		nameField = new TextField(edge.getName());
		timeField = new TextField(Integer.toString(edge.getWeight()));
		nameField.setEditable(false);
		timeField.setEditable(false);
		grid.addRow(0, new Label("Name: "), nameField);
		grid.addRow(1, new Label("Time: "), timeField);
		setHeaderText("Connection from " + a.getName() +" to " + b.getName());
		getDialogPane().setContent(grid);
			
	}

}
