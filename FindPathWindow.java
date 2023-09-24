// PROG2 VT2023, Inlamningsuppgift, del 2
// Grupp 077
// Sara Berg sabe4314

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.*;
import javafx.scene.control.TextArea;

public class FindPathWindow extends Alert{
	
	private TextArea textArea;
	
	public FindPathWindow(City a, City b, String str) { 
		super(AlertType.INFORMATION);
		GridPane grid = new GridPane();

		textArea = new TextArea(str);
		textArea.setEditable(false);
		grid.addRow(0, textArea);

		setHeaderText("Connection from " + a.getName() +" to " + b.getName());
		getDialogPane().setContent(grid);
			
	}

}
