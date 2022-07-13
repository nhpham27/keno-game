import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;

import com.sun.scenario.effect.impl.prism.PrImage;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

public class JavaFXTemplate extends Application {
	private int width = 700, height = 650;
	
	private HashMap<String, Scene> sceneMap;
	// Main scene
	private Button startButton;
	Text rules, odds;
	
	// Play Scene
	Text drawText, spotText, gridText, drawnText, resultText;
	private ArrayList<Button> drawButtons, spotButtons, gridButtons;
	HBox draws, spots;
	VBox resultBox, mainVbox, playVbox, ruleVbox, oddVbox;
	private int maxDraws = 4, maxSpots = 4;
	private int spotIsChosen = 0, drawSoFar = 0;
	private int spotsList[] = {1, 4, 8, 10};
	private GridPane grid;
	private Button quickPickButton;
	private Button startDraw, newGameButton;
	private TextArea drawnNumbers, result;
	private String temp = "", temp2 = "";
	private int counter;
	String buttonColor = "";
	
	// Keno game object
	private KenoGame game;
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		launch(args);
	}

	//feel free to remove the starter code from this method
	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		primaryStage.setTitle("Welcome to Keno game");
		
		game = new KenoGame();
		
		// Add the scenes to the hashmap
		sceneMap = new HashMap<String, Scene>();
		sceneMap.put("main", createMainScene(primaryStage));
		sceneMap.put("rules", createRuleScene(primaryStage));
		sceneMap.put("odds", createOddScene(primaryStage));
		sceneMap.put("play", createPlayScene(primaryStage));
		
		// display main scene/welcome page
		primaryStage.setScene(sceneMap.get("main"));
		primaryStage.show();
		
	}
	
	// Main scene/ Welcome page
	Scene createMainScene(Stage primaryStage) {
		// start game button
		startButton = new Button("PLAY GAME");
		startButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				// TODO Auto-generated method stub
				primaryStage.setScene(sceneMap.get("play"));
			}
		});
		
		VBox vbx = new VBox(20, startButton);
		vbx.setAlignment(Pos.CENTER);
		
		// menu bar
		// -> display rules
		// -> display odds of winning
		// -> Exit
		MenuBar mb = createMenuBar(primaryStage, 0);
		mainVbox = new VBox(20, mb, vbx);
		return new Scene(mainVbox, width, height);
	}
	
	// The scene displays the rule of the game
	Scene createRuleScene(Stage primaryStage) {
		rules = new Text("\t\t\t\t\tRULES OF THE GAME\n"
				+ "\n1. Select how many consecutive draws to play. Pick up to 20."
				+ "\n2. Select how many numbers to match from 1 to 10. In Keno, these are called Spots. "
				+ "\n\t The number of Spots you choose and the amount you play "
				+ "\n\t per draw will determine the amount you could win. "
				+ "\n\t See the prize chart to determine the amount you could win with a $1 play."
				+ "\n3. Pick as many numbers as you did Spots. "
				+ "\n\t You can select numbers from 1 to 80 "
				+ "\n\t or choose Quick Pick and let the computer "
				+ "\n\t terminal randomly pick some or all of these "
				+ "\n\t numbers for you.");
		rules.setStyle("-fx-font: 18 arial;");
		// menu back to main page
		Button b = new Button("Back to main");
		b.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				// TODO Auto-generated method stub
				primaryStage.setScene(sceneMap.get("main"));
				primaryStage.show();
			}
		});
		
		ruleVbox = new VBox(20, b, rules);
		return new Scene(ruleVbox, width, height);
	}
	
	// create the scene to show the odds of winning in the game
	Scene createOddScene(Stage primaryStage) {
		odds = new Text("ODDS OF WINNING\n"
						+ "\nSpot 1:"
						+ "\nMatch		Prize"
						+ "\n 1			 $2"
						+ "\n\nSpot 4:"
						+ "\nMatch		Prize"
						+ "\n 4			$75"
						+ "\n 3			$5"
						+ "\n 2			$1"
						+ "\n\nSpot 8:"
						+ "\nMatch		Prize"
						+ "\n 8			$10,000"
						+ "\n 7			$750"
						+ "\n 6			$50"
						+ "\n 5			$12"
						+ "\n 4			$2"
						+ "\n\nSpot 10:"
						+ "\nMatch		Prize"
						+ "\n10			$100,000"
						+ "\n 9			$4250"
						+ "\n 8			$450"
						+ "\n 7			$40"
						+ "\n 6			$15"
						+ "\n 5			$2"
						+ "\n 0			$5");
		//change font size to 18 arial
		odds.setStyle("-fx-font: 18 arial;");
		
		// button to go back to mani scene
		Button b = new Button("Back to main");
		b.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				// TODO Auto-generated method stub
				primaryStage.setScene(sceneMap.get("main"));
				primaryStage.show();
			}
		});
		
		// VBox contains all components
		oddVbox = new VBox(20, b, odds);
		oddVbox.setAlignment(Pos.CENTER);
		
		return new Scene(oddVbox, width, height);
	}
	
	// create the play scene that allows user to play the game
	Scene createPlayScene(Stage primaryStage) {
		// Playing components
		// buttons to choose # of draws
		createDrawButtons();
		
		//buttons to choose spots
		createSpotButtons();
		
		// gridpane contains 80 numbers
		createBetCard();
		
		// quick pick button
		createQuickPick();
		
		// start drawing button
		createStartDraw();
		
		// result display textfield
		createResultText();
		
		// new game button
		createNewGameButton();
		
		// menu bar
		// -> display rules
		// -> display odds of winning
		// -> Exit
		MenuBar mb = createMenuBar(primaryStage, 1);
		
		// HBox contains quickpick, start drawing and new game buttons
		HBox hb = new HBox(quickPickButton, startDraw, newGameButton);
		hb.setAlignment(Pos.CENTER);
		
		// vbox contains all components
		VBox vb = new VBox(20, drawText, draws, spotText, spots, gridText, 
							grid, hb, resultBox);
		vb.setAlignment(Pos.CENTER);
		vb.setSpacing(10);
		playVbox = new VBox(mb, vb);
		return new Scene(playVbox, width, height);
	}
	
	// create new game button
	private void createNewGameButton() {
		newGameButton = new Button("New game");
		newGameButton.setDisable(true);
		newGameButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				// reset the game
				game.reset();
				
				// enable all the selection fields
				draws.setDisable(false);
				spots.setDisable(false);
				quickPickButton.setDisable(false);
				startDraw.setDisable(false);
				// reset all the buttons
				resetButtonsStyle(drawButtons);
				resetButtonsStyle(spotButtons);
				resetButtonsStyle(gridButtons);
				
				// clear result text field
				drawnNumbers.clear();
				result.clear();
				
				// disable new game button
				newGameButton.setDisable(true);
			}
		});
	}

	// create result text field
	private void createResultText() {
		// drawn number textfield
		drawnText = new Text("Drawn numbers");
		drawnNumbers = new TextArea();
		drawnNumbers.setPrefSize(500, 10);
		drawnNumbers.setEditable(false);
		drawnNumbers.setMouseTransparent(true);
		drawnNumbers.setFocusTraversable(false);
		
		// Result textArea
		result = new TextArea();
		result.setPrefSize(500, 100);
		// Read-only text field
		result.setEditable(false);
		result.setMouseTransparent(true);
		result.setFocusTraversable(false);
		
		HBox hb = new HBox(drawnNumbers);
		hb.setAlignment(Pos.CENTER);
		HBox hb2 = new HBox(result);
		hb2.setAlignment(Pos.CENTER);
		resultText = new Text("Result");
		
		resultBox = new VBox(drawnText, hb, resultText, hb2);
		resultBox.setAlignment(Pos.CENTER);
		
	}
	
	// create start drawing button
	private void createStartDraw() {
		// Draw Button
		startDraw = new Button("DRAW");
		startDraw.setAlignment(Pos.CENTER);
		startDraw.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				// check if all the fields are chosen
				if(game.getNumDraws() != 0 && game.getNumSpots() != 0
						&& game.getUserNumbers().size() == game.getNumSpots()) {
					// Change label to continue
					startDraw.setText("CONTINUE");
					
					//clear drawn numbers and result text area
					drawnNumbers.clear();
					result.clear();
					
					// start drawing random numbers
					game.draw();
					
					// disable all the selection fields
					draws.setDisable(true);
					spots.setDisable(true);
					//grid.setDisable(true);
					grid.setMouseTransparent(true);
					
					spotIsChosen = 0;
					quickPickButton.setDisable(true);
					
					// increment number of draw so far
					drawSoFar++;
					
					// disable draw button when the result is printing
					startDraw.setDisable(true);
					// clear the result text box
					counter = 0;
					EventHandler<ActionEvent> eventHandler = (e->{
						if(counter == 0) {
							// reset the string
							temp = "";
						}
						
						// Print drawn numbers
						ArrayList<Integer> list = game.getDrawNumbers();
						temp = temp + Integer.toString(list.get(counter++)) + "  ";
						drawnNumbers.setText(temp);
						
						if(counter >= 20 ) {
							// check if the total draws reach the chosen limit
							if(drawSoFar >= game.getNumDraws()) {
								// disable draw button
								startDraw.setDisable(true);
								
								// reset the grid
								grid.setDisable(true);
								grid.setMouseTransparent(false);
								
								// enable new game button
								newGameButton.setDisable(false);
								
								// reset counter
								drawSoFar = 0;
								
								// change label
								startDraw.setText("DRAW");
							}
							else
								startDraw.setDisable(false);
							
							// print the result
							temp2 = "";
							
							// Print number of matched numbers
							temp2 = temp2 + "Number of matched numbers: " 
									+ game.getMatchedNumbers().size();
							
							// Print matched numbers
							temp2 = temp2 + "\n" + "Matched Numbers: ";
							for(Integer x : game.getMatchedNumbers()) {
								temp2 = temp2 + Integer.toString(x) + "  ";
							}
							
							// print the amount won this game and total
							temp2 = temp2 + "\n" + "Prize: " + game.getPrize();
							temp2 = temp2 + "\n" + "Total Prize: " + game.getTotalPrize();
							result.setText(temp2);
						}
					});
					
					// set timeline to delay the drawn numbers printing
					Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1), eventHandler),
													new KeyFrame(Duration.millis(1000)));
					// set loops for 20 numbers
					timeline.setCycleCount(20);
					// start looping
					timeline.play();	
				}	
			}
		});
		
	}

		// create quick pick button
	private void createQuickPick() {
		// setup quick pick button
		quickPickButton = new Button("Quick Pick");
		quickPickButton.setAlignment(Pos.CENTER);
		quickPickButton.setOnAction(new EventHandler<ActionEvent>(){
			@Override
			public void handle(ActionEvent event) {
				// clear the grid if the number of spots is changed
				game.clearUserNumbers();
				for(Button x : gridButtons) {
					x.setStyle(null);
				}
				
				// generate random numbers
				game.quickPick();
				ArrayList<Integer> list = game.getUserNumbers();
				
				// put the random numbers on the grid
				for(Integer x : list) {
					Button b = gridButtons.get(x-1);
					b.setStyle("-fx-background-color: #14E1C4;");
				}
			}
		});
	}

	// create gridpane/bet card
	private void createBetCard() {
		// Setup bet card
		gridText = new Text("Pick your own numbers( reclick to remove the chosen number) "
				+ "or use Quick Pick to choose numbers randomly");
		grid = new GridPane();
		grid.setFocusTraversable(true);
		// disable gridpane until the number of spot is chosen
		grid.setDisable(true);
		grid.setAlignment(Pos.CENTER);
		gridButtons = new ArrayList<>();
		for(int x = 0; x<8; x++) {
			for(int i = 0; i<10; i++) {
				Button b = new Button(Integer.toString(x*10 + i + 1));
				b.setMinSize(30, 30);
				b.setOnAction(new EventHandler<ActionEvent>() {
					@Override
					public void handle(ActionEvent event) {
						// get the label of the button
						int num = Integer.valueOf(b.getText());
						
						// if the number is added to the list properly
						//, change button to green
						if(game.addUserNumber(num) == true) {
							b.setStyle("-fx-background-color: #14E1C4;");
						}
						else {
							// remove the color if the number is not added
							b.setStyle(null);
							// remove number from the list if the button is
							// clicked the second time
							game.removeUserNumber(num);
						}
					}
				});
				
				// add button to the list
				gridButtons.add(b);
				
				// add button to gridpane
				grid.add(b, i, x);	 
			}
		}
		
	}

	// create buttons for choosing # of spots
	private void createSpotButtons() {
		// Number of spots
		spots = new HBox();
		spots.setFocusTraversable(true);
		spots.setAlignment(Pos.CENTER);;
		// prompt
		spotText = new Text("How many numbers(spots) "
										+ "do you want to play?");

		// add buttons to HBox
		spotButtons = new ArrayList<>();
		for(int i = 0; i < maxSpots; i++){
			Button b = new Button(Integer.toString(spotsList[i]));
			b.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					// TODO Auto-generated method stub
					game.setNumSpots(Integer.valueOf(b.getText()));
					// set the chosen button to given color
					b.setStyle("-fx-background-color: #14E1C4;");
					for(Button x : spotButtons) {
						if(x != b) {
							x.setStyle(null);
						}
					}
					
					// Activate Bet card when one of the spots is chosen
					if(spotIsChosen == 0) {
						grid.setDisable(false);
						spotIsChosen = 1;
					}
					
					// clear the grid if the number of spots is changed
					game.clearUserNumbers();
					resetButtonsStyle(gridButtons);
				}
			});
			spotButtons.add(b);
			spots.getChildren().add(b);
		}
		
	}

	// create buttons for choosing # of draws
	private void createDrawButtons() {
		// Number of draws
		draws = new HBox();
		draws.setFocusTraversable(true);
		draws.setAlignment(Pos.CENTER);
		drawText = new Text("How many consecutive draws "
									+ "do you want to play?");
		drawButtons = new ArrayList<>();
		for(int i = 0; i < maxDraws; i++){
			Button b = new Button(Integer.toString(i+1));
			b.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					// TODO Auto-generated method stub
					game.setNumDraws(Integer.valueOf(b.getText()));
					// set the chosen button to given color
					b.setStyle("-fx-background-color: #14E1C4;");
					for(Button x : drawButtons) {
						if(x != b) {
							x.setStyle(null);
						}
					}
				}
			});
			drawButtons.add(b);
			draws.getChildren().add(b);
		}
	}
	
	// menu bar
	// -> display rules
	// -> display odds of winning
	// -> Exit
	MenuBar createMenuBar(Stage primaryStage, int choice) {
		// Menu bar
		MenuBar menuBar = new MenuBar();
		
		// Menu
		Menu menu = new Menu("Menu");
		
		// Menu items
		MenuItem mRule = new MenuItem("Rules of the game");
		MenuItem mOdds = new MenuItem("Odds of winning");
		MenuItem mExit = new MenuItem("Exit");
		MenuItem mNewLook = new MenuItem("New Look");
		
		// add menu items to menu
		if(choice == 0)
			menu.getItems().addAll(mRule, mOdds, mExit);
		else
			menu.getItems().addAll(mRule, mOdds, mNewLook, mExit);
		// add menu to menu bar
		menuBar.getMenus().add(menu);
		
		// set actions for menu items
		mRule.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				// TODO Auto-generated method stub
				primaryStage.setScene(sceneMap.get("rules"));
				primaryStage.show();
			}
		});
		
		mOdds.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				// TODO Auto-generated method stub
				primaryStage.setScene(sceneMap.get("odds"));
				primaryStage.show();
			}
		});
		
		mNewLook.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				String str = "-fx-background-color: #373331;";
				// set all the scene background color to #373331
				mainVbox.setStyle(str);
				playVbox.setStyle(str);
				ruleVbox.setStyle(str);
				oddVbox.setStyle(str);
				str = "-fx-fill: #14E1C4;";
				// set all the text color to #14E1C4
				drawText.setStyle(str); 
				spotText.setStyle(str); 
				gridText.setStyle(str); 
				drawnText.setStyle(str); 
				resultText.setStyle(str);
				str = str + "-fx-font: 18 arial;";
				rules.setStyle(str);
				odds.setStyle(str);
				
				// set all the text color to #14E1C4, background color to #373331
				drawnNumbers.setStyle("-fx-text-inner-color: #14E1C4;" + "-fx-control-inner-background: #373331;");
				result.setStyle("-fx-text-inner-color: #14E1C4;" + "-fx-control-inner-background: #373331;");
			}
		});
		
		mExit.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				// TODO Auto-generated method stub
				System.exit(0);
			}
		});
		
		return menuBar;
	}
	
	// set the style of all the buttons in the list to null
	private void resetButtonsStyle(ArrayList<Button> buttons) {
		for(Button x : buttons) {
			x.setStyle(null);
		}
	}
}
