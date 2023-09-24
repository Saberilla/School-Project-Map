// PROG2 VT2023, Inlamningsuppgift, del 2
// Grupp 077
// Sara Berg sabe4314

import javafx.application.Application;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.scene.layout.*;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import java.util.Optional;

import javax.imageio.ImageIO;

import java.awt.image.BufferedImage;
import java.io.*;
import java.util.*;
import javafx.scene.shape.*;


public class PathFinder extends Application{
	
	private ListGraph<City> graph = new ListGraph<>();
	private Pane bottom;
	private BottomClickHandler bottomClickHandler = new BottomClickHandler();
	private CitySelectedHandler citySelected = new CitySelectedHandler();
	private Button btnNewPlace;
	private Button btnNewConnection;
	private Button btnShowConnection;
	private Button btnChangeConnection;
	private Button btnFindPath;
	private City city1 = null;
	private City city2 = null;
	private Set<City> cities = new HashSet<>();
	private boolean saved = true;

	
	@Override
	public void start(Stage primaryStage) {
		
		//buttons
		btnFindPath = new Button("Find Path");
		btnFindPath.setId("btnFindPath");
		btnFindPath.setOnAction(new FindPathHandler());
		
		btnShowConnection = new Button("Show Connection");
		btnShowConnection.setId("btnShowConnection");
		btnShowConnection.setOnAction(new ShowConnectionHandler());

		btnNewPlace = new Button("New Place");
		btnNewPlace.setId("btnNewPlace");
		btnNewPlace.setOnAction(new NewPlaceHandler());
		
		btnNewConnection = new Button("New Connection");
		btnNewConnection.setId("btnNewConnection");
		btnNewConnection.setOnAction(new NewConnectionHandler());
		
		btnChangeConnection = new Button("Change Connection");
		btnChangeConnection.setId("btnChangeConnection");
		btnChangeConnection.setOnAction(new ChangeConnectionHandler());
		
		
		btnFindPath.setDisable(true);
		btnShowConnection.setDisable(true);
		btnNewPlace.setDisable(true);
		btnNewConnection.setDisable(true);
		btnChangeConnection.setDisable(true);
		
		//main root
		BorderPane root = new BorderPane();
		
		//main outputarea
		bottom = new Pane();
		bottom.setId("outputArea");
		root.setBottom(bottom);


		//menu
		VBox vbox = new VBox();
		MenuBar menuBar = new MenuBar();
		menuBar.setId("menu");
		vbox.getChildren().add(menuBar);
		Menu archiveMenu = new Menu("File");
		archiveMenu.setId("menuFile");
		menuBar.getMenus().add(archiveMenu);
		
		//new map
		MenuItem newItem = new MenuItem("New Map");
		newItem.setId("menuNewMap");
		archiveMenu.getItems().add(newItem);
		newItem.setOnAction((ActionEvent e) ->{
			
			if(!saved) {
				Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "There are unsaved changes. Continue anyway?");
				alert.setHeaderText(null);
				Optional<ButtonType> answer = alert.showAndWait();
				if(answer.isPresent() && answer.get() != ButtonType.OK) {
					return;
				}
			}
			
			graph = new ListGraph<>(); //new empty graph SOM I VPL
			//clearBottom(); //rensa outputArea
			bottom.getChildren().clear();
			cities.clear(); //clean out previous notes
			
			city1 = null; //rensa val
			city2 = null; //rensa val

			Image image = new Image("file:europa.gif");
			ImageView imageView = new ImageView(image);
			imageView.setImage(image);
			bottom.getChildren().add(imageView);
			
			//currentImageView = imageView; //NY


			
			primaryStage.setWidth(image.getWidth());
			primaryStage.setHeight(image.getHeight());
			btnFindPath.setDisable(false);
			btnShowConnection.setDisable(false);
			btnNewPlace.setDisable(false);
			btnNewConnection.setDisable(false);
			btnChangeConnection.setDisable(false);
			saved = false; //unsaved change
			
		});
		
		//open
		MenuItem openItem = new MenuItem("Open");
		openItem.setId("menuOpenFile");
		archiveMenu.getItems().add(openItem);
		openItem.setOnAction((ActionEvent event) ->{
			if(!saved) {
				Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "There are unsaved changes. Continue anyway?");
				alert.setHeaderText(null);
				Optional<ButtonType> answer = alert.showAndWait();
				if(answer.isPresent() && answer.get() != ButtonType.OK) {
					return;
				}
			}
			
			//clearBottom(); //rensa outputArea
			bottom.getChildren().clear();
			cities.clear(); //clean out previous notes
			
			graph = new ListGraph<>(); //new empty graph SOM I VPL
			city1 = null; //rensa va
			city2 = null; //rensa val
			
			try {
				FileReader file = new FileReader("europa.graph");
				BufferedReader in = new BufferedReader(file);
				String line = in.readLine();


				Image image = new Image(line);
				ImageView imageView = new ImageView(image);
				imageView.setImage(image);
				bottom.getChildren().add(imageView);
				
				//currentImageView = imageView; //NY
				
				primaryStage.setWidth(image.getWidth());
				primaryStage.setHeight(image.getHeight());
				btnFindPath.setDisable(false); 
				btnShowConnection.setDisable(false);
				btnNewPlace.setDisable(false);
				btnNewConnection.setDisable(false);
				btnChangeConnection.setDisable(false);
				
				String line2 = in.readLine();
				String[] nodes = line2.split(";");
				int index = 0;
				while(index < nodes.length) {
					String name = nodes[index];
					double x = Double.parseDouble(nodes[index+1]);
					double y = Double.parseDouble(nodes[index+2]);
					
					City city = new City(x,y,name);
					city.setId(name);
					graph.add(city);
					cities.add(city);
					bottom.getChildren().add(city);
					index = index+3;
				}

				while((line = in.readLine()) != null) {
					String[] str = line.split(";");
					String from = str[0];
					String to = str[1];
					String name = str[2];
					int time = Integer.parseInt(str[3]);

					for (City city : cities) {
						if(city.getName().equals(from)) {
							city1 = city;
						}
						if (city.getName().equals(to)) {
							city2 = city;
						}
					}
					if (!graph.validateIfEdgeExists(city1, city2)) {
						
						graph.connect(city1, city2, name, time);
						
						Line conLine = new Line(city1.getX(), city1.getY(), city2.getX(), city2.getY());
						conLine.setStrokeWidth(5);
						
						bottom.getChildren().add(conLine);
						conLine.setDisable(true);
					}
				}
				
				city1 = null; //resets chosen cities
				city2 = null;
				
				for(City c : cities) {
					c.setOnMouseClicked(citySelected);
				}
				
				in.close();
				file.close();
				
				
			} catch(FileNotFoundException e) {
				Alert alert = new Alert(Alert.AlertType.ERROR, "File not found!");
				alert.setHeaderText(null);
				alert.showAndWait();
			} catch(IOException e) {
				Alert alert = new Alert(Alert.AlertType.ERROR, "IOException!"); 
				alert.setHeaderText(null);
				alert.showAndWait();
			}
			saved = false; //unsaved change
		});
		
		//save
		MenuItem saveItem = new MenuItem("Save");
		saveItem.setId("menuSaveFile");
		archiveMenu.getItems().add(saveItem);
		saveItem.setOnAction((ActionEvent event) ->{ 
			
			try {
				FileWriter fil = new FileWriter("europa.graph");
				PrintWriter out = new PrintWriter(fil);
				out.println("file:europa.gif"); 
				
				StringBuilder str = new StringBuilder();
				for (City city : cities) {
					str.append(city + ";" + city.getX() + ";" + city.getY() + ";");
				}

				String nodes = str.toString();
				out.println(nodes);
				for (City city : cities) {
					for (Edge<City> edge : graph.getEdgesFrom(city)) {
						out.println(city + ";" + edge.getDestination() + ";" + edge.getName() + ";" + edge.getWeight());
					}
				}
				
				out.close();
				fil.close();
				saved = true; //saved 
				
			} catch (FileNotFoundException e1) {
				Alert alert = new Alert(Alert.AlertType.ERROR, "File not found!");
				alert.setHeaderText(null);
				alert.showAndWait();
			} catch( IOException e) {
				Alert alert = new Alert(Alert.AlertType.ERROR, "IOException!"); 
				alert.setHeaderText(null);
				alert.showAndWait();
			}
			
			
		});
		
		//save image
		MenuItem saveIvItem = new MenuItem("Save Image");
		saveIvItem.setId("menuSaveImage");
		archiveMenu.getItems().add(saveIvItem);
		saveIvItem.setOnAction((ActionEvent event) ->{ 
			try {
				WritableImage image = root.snapshot(null, null);
				BufferedImage bimage = SwingFXUtils.fromFXImage(image, null);
				ImageIO.write(bimage, "png", new File("capture.png"));
				
			} catch(IOException e) {
				Alert alert = new Alert(Alert.AlertType.ERROR, "Something went wrong");
				alert.setHeaderText(null);
				alert.showAndWait();
				
			}
		});

		//exit
		MenuItem exitItem = new MenuItem("Exit");
		exitItem.setId("menuExit");
		archiveMenu.getItems().add(exitItem);
		exitItem.setOnAction((ActionEvent event) ->{ 
			if(!saved) {
				primaryStage.fireEvent(new WindowEvent(primaryStage, WindowEvent.WINDOW_CLOSE_REQUEST));
			}
			
		});

		//for closing window
		primaryStage.setOnCloseRequest((WindowEvent event)-> {
			if(!saved) {
				
				Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Unsaved changes. Continue anyway?");
				alert.setHeaderText(null);
				Optional<ButtonType> answer = alert.showAndWait();
				if(answer.isPresent() && answer.get() != ButtonType.OK) {
					event.consume();
				}
			}
		});
		
		HBox hbox = new HBox(10);
		hbox.setPadding(new Insets(10,10,10,10));
		hbox.getChildren().add(btnFindPath);
		hbox.getChildren().add(btnShowConnection);
		hbox.getChildren().add(btnNewPlace);
		hbox.getChildren().add(btnNewConnection);
		hbox.getChildren().add(btnChangeConnection);
		hbox.setAlignment(Pos.CENTER); 

		root.setTop(vbox);
		root.setCenter(hbox);

		Scene scene = new Scene(root);
		primaryStage.setTitle("PathFinder");
		primaryStage.setScene(scene);
		primaryStage.setResizable(false);
		primaryStage.show();
	}
	

	
	class FindPathHandler implements EventHandler <ActionEvent>{
		@Override
		public void handle(ActionEvent event) {
			if (city1 == null) {
				Alert alert = new Alert(Alert.AlertType.ERROR, "Du måste välja två städer");
				alert.setHeaderText(null);
				alert.showAndWait();
			} else if (city2 == null) {
				city1.turnBlue();
				city1 = null;
				Alert alert = new Alert(Alert.AlertType.ERROR, "Du måste välja två städer");
				alert.setHeaderText(null);
				alert.showAndWait();

			} else if (!graph.pathExists(city1, city2)){
				Alert alert = new Alert(Alert.AlertType.ERROR, "Path doesn't exist");
				alert.setHeaderText(null);
				alert.showAndWait();
			} else {
				StringBuilder str = new StringBuilder();
				List<Edge<City>> edges = new ArrayList<Edge<City>>();
				edges = graph.getPath(city1, city2);
				int total = 0;
				for (Edge<City> edge : edges) {
					str.append(edge.toString());
					str.append("\n");
					int temp = edge.getWeight();
					total+=temp;
				}
				str.append("Total " + total);
				

				String text = str.toString();
				FindPathWindow window = new FindPathWindow(city1, city2, text);
				Optional<ButtonType> answer = window.showAndWait();
				if(answer.isPresent()) {
					
					return;
				}
			}
		}
	}
	
	class ChangeConnectionHandler implements EventHandler <ActionEvent>{
		@Override
		public void handle(ActionEvent event) {
			if (city1 == null) {
				Alert alert = new Alert(Alert.AlertType.ERROR, "Du måste välja två städer");
				alert.setHeaderText(null);
				alert.showAndWait();
			} else if (city2 == null) {
				city1.turnBlue();
				city1 = null;
				Alert alert = new Alert(Alert.AlertType.ERROR, "Du måste välja två städer");
				alert.setHeaderText(null);
				alert.showAndWait();

			} else if (!graph.validateIfEdgeExists(city1, city2)){
				//reset();
				Alert alert = new Alert(Alert.AlertType.ERROR, "Connection doesn't exist");
				alert.setHeaderText(null);
				alert.showAndWait();
			} else {

				Edge<City> edge1 = graph.getEdgeBetween(city1, city2);
				Edge<City> edge2 = graph.getEdgeBetween(city2, city1);
				
				ChangeConnectionWindow window = new ChangeConnectionWindow(city1, city2, edge1);
				Optional<ButtonType> answer = window.showAndWait();
				if(answer.isPresent() && answer.get() != ButtonType.OK) {
					//reset();
					return;
				}
				
				int time = window.getTime();
				edge1.setWeight(time);
				edge2.setWeight(time);
				//reset();
				saved = false; //unsaved change
			}
			
		}
	}
	
	class ShowConnectionHandler implements EventHandler <ActionEvent>{
		@Override
		public void handle(ActionEvent event) {
			if (city1 == null) {
				Alert alert = new Alert(Alert.AlertType.ERROR, "Du måste välja två städer");
				alert.setHeaderText(null);
				alert.showAndWait();
			} else if (city2 == null) {
				city1.turnBlue();
				city1 = null;
				Alert alert = new Alert(Alert.AlertType.ERROR, "Du måste välja två städer");
				alert.setHeaderText(null);
				alert.showAndWait();

			} else if (!graph.validateIfEdgeExists(city1, city2)){
				//reset();
				Alert alert = new Alert(Alert.AlertType.ERROR, "Connection doesn't exist");
				alert.setHeaderText(null);
				alert.showAndWait();
			} else {
				
				Edge<City> connection = graph.getEdgeBetween(city1, city2);
				ShowConnectionWindow window = new ShowConnectionWindow(city1, city2, connection);
				Optional<ButtonType> answer = window.showAndWait();
				if(answer.isPresent()) { 
					//reset();
					return;
				}
			}
		}
	}
	
	class NewConnectionHandler implements EventHandler <ActionEvent>{
		@Override
		public void handle(ActionEvent event) {
			try {
				
				if (city1 == null) {
					Alert alert = new Alert(Alert.AlertType.ERROR, "Du måste välja två städer");
					alert.setHeaderText(null);
					alert.showAndWait();
				} else if (city2 == null) {
					//city1.turnBlue();
					//city1 = null;
					Alert alert = new Alert(Alert.AlertType.ERROR, "Du måste välja två städer");
					alert.setHeaderText(null);
					alert.showAndWait();

				} else if (graph.validateIfEdgeExists(city1, city2)){
					//reset();
					Alert alert = new Alert(Alert.AlertType.ERROR, "Connection already exists");
					alert.setHeaderText(null);
					alert.showAndWait();
				} else {
					
					NewConnectionWindow window = new NewConnectionWindow(city1, city2); //GÅ TBX
					//WindowAlert window = new WindowAlert(city1, city2);//NY
					Optional<ButtonType> answer = window.showAndWait();
					if(answer.isPresent() && answer.get() != ButtonType.OK) {
						//reset();
						return;
					}
					
					String name = window.getName();
					int time = window.getTime(); 
					
					if (name.isEmpty()){
						//reset();
						Alert alert = new Alert(Alert.AlertType.ERROR, "Fält ej ifyllda korrekt");
						alert.setHeaderText(null);
						alert.showAndWait();
					} else {

						graph.connect(city1, city2, name, time);
						
						//create connection line
						Line line = new Line(city1.getX(), city1.getY(), city2.getX(), city2.getY());
						line.setStrokeWidth(5);
						bottom.getChildren().add(line);
						line.setDisable(true);
						//reset();
						saved = false; //unsaved change
					}
				}
				
				
			} catch (NumberFormatException e) {
				//reset();
				Alert alert = new Alert(Alert.AlertType.ERROR, "Fält ej ifyllda");
				alert.setHeaderText(null);
				alert.showAndWait();
			} catch(IllegalStateException e) {
				//reset();
				Alert alert = new Alert(Alert.AlertType.ERROR, "Connection finns redan");
				alert.setHeaderText(null);
				alert.showAndWait();
			}
		}
		
	}
	

	class NewPlaceHandler implements EventHandler <ActionEvent>{
		@Override
		public void handle(ActionEvent event) {
			
			bottom.setOnMouseClicked(bottomClickHandler);
			btnNewPlace.setDisable(true);
			bottom.setCursor(Cursor.CROSSHAIR);
		}
	}
	
	class BottomClickHandler implements EventHandler <MouseEvent>{
		@Override
		public void handle(MouseEvent event) {
			double x = event.getX();
			double y = event.getY();
			
			NewPlaceWindow window = new NewPlaceWindow(); //GÅ TBX
			//WindowAlert window = new WindowAlert();//NY
			Optional<ButtonType> answer = window.showAndWait();
			if(answer.isPresent() && answer.get() != ButtonType.OK) {
				return;
			}
			
			String name = window.getName();
			City city = new City(x,y,name);
			city.setId(name);
			graph.add(city);
			cities.add(city);
			bottom.getChildren().add(city);
			saved = false; //unsaved change

			for(City c : cities) {
				c.setOnMouseClicked(citySelected);
			}

			bottom.setOnMouseClicked(null);
			btnNewPlace.setDisable(false);
			bottom.setCursor(Cursor.DEFAULT);
		}
	}
	
	class CitySelectedHandler implements EventHandler <MouseEvent>{
		@Override
		public void handle(MouseEvent event) {
			City city = (City)event.getSource();
			
			if (city1 == null && city2 != null) {
				if (city.equals(city2)) {
					city.turnBlue();
					city2 = null;
				} else {
					city1 = city;
					city1.turnRed();
				}
			} else if (city1 != null && city2 == null) {
				if (city.equals(city1)) {
					city.turnBlue();
					city1 = null;
				} else {
					city2 = city;
					city2.turnRed();
				}
			} else if (city1 != null && city2 != null) {
				if (city.equals(city1)) {
					city.turnBlue();
					city1 = null;
				} else if (city.equals(city2)) {
					city.turnBlue();
					city2 = null;
				}
			} else if (city1 == null) {
				city1 = city;
				city1.turnRed();
			} else if (city2 == null) {
				city2 = city;
				city2.turnRed();
			} 
		}
		
	}
	
	
	public static void main(String[] args) {
		Application.launch(args);
	}
	
}



