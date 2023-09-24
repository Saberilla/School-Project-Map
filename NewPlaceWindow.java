// PROG2 VT2023, Inlamningsuppgift, del 2
// Grupp 077
// Sara Berg sabe4314
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;

public class NewPlaceWindow extends Alert {
	
	private TextField nameField = new TextField();
	
	public NewPlaceWindow() {
		super(AlertType.CONFIRMATION);
		GridPane grid = new GridPane();
		grid.setPadding(new Insets(10));
		grid.setAlignment(Pos.CENTER);
		grid.setHgap(5);
		grid.setVgap(5);
		grid.addRow(0, new Label("Name of Place: "), nameField);
		setHeaderText(null);
		getDialogPane().setContent(grid);
			
	}
	
	public String getName() {
		return nameField.getText();
	}
}


