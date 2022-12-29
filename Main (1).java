/* PROGRAM DESCRIPTION: The program is designed to mirror a Magikoopa vs. Bullet Bill game where the 
* objective is to avoid the bullets flying across the room. The bullets are stored in an arraylist and 
* generated from the right side of the screen and are randomized on the y direction. The Magikoopa can be
* controlled by using the up, down, left or right arrow keys. There is background music playing. Every 
* bullet that the Magikoopa is able to dodge (passes the room) gives the player 10 points and the score
* starts from 0 when the game loads. Every 100 points, a laughing sound plays. If a bullet hits the Magikoopa
* a gunshot sound is played and the game ends. The Magikoopa's image changes to greyscale and an alert is
* displayed showing the user's score and the message of the game being over.
*/

package application;
	
import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;

import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;


public class Main extends Application {
	
	private Label scorelbl;
	private int score, bulletCount, bspeed, mspeed;
	private boolean up, down, right, left;
	private AnimationTimer animation;
	private Timeline clock;
	private ArrayList<BulletBill> bullet = new ArrayList<BulletBill>();
	private Magikoopa mk;
	private Media background;
	private MediaPlayer backgroundPlayer;
	private ImageView mkAlert;
	public boolean collide=false;
	
	public void start(Stage primaryStage) {
		try {
			
			//creating background music using Media Player
			File backFile = new File ("background.wav");
			background = new Media(backFile.toURI().toString());
			backgroundPlayer = new MediaPlayer(background);
			backgroundPlayer.setOnEndOfMedia(new Runnable() {
			
			public void run() 
			{
				backgroundPlayer.seek(Duration.ZERO);	
			}
			});
			
			backgroundPlayer.play();
			
			Pane root = new Pane();
			
			//creating the background and setting its x and y position to the top left of the room
			Image imgBack= new Image ("file:game-background.png");
			ImageView iviewBack= new ImageView (imgBack);
			
			iviewBack.setX(0);
			iviewBack.setY(0);
			
			//loading the scene and setting its size to the size of the background image
			Scene scene = new Scene(root, imgBack.getWidth(), imgBack.getHeight());

			//loading custom font
			Font font=Font.loadFont(new FileInputStream(new File("Mufferaw Regular.ttf")), 25);
			
			//creating the title label displaying the name of the game, setting its position and font
			Label title= new Label ("Magikoopa vs. Bullet Bill");
			title.setPrefSize(300, 25);
			title.setLayoutX((scene.getWidth()/2) -150);
			title.setLayoutY(30);
			title.setFont(font);
			title.setAlignment(Pos.CENTER);
			title.setTextAlignment(TextAlignment.CENTER);
			
			//setting score to 0 to begin with
			score=0;
			
			//setting bulletCount to -1 (tracks the index of the arrayList)
			bulletCount=-1;
		
			//initializing the speed of the bullet and magikoopa to 10
			bspeed=10;
			mspeed=10;
			
			//creating the score label displaying the score of the player, setting its position and font
			scorelbl=new Label();
			scorelbl.setText("SCORE: " + score);
			scorelbl.setPrefSize(120, 25);
			scorelbl.setLayoutX(scene.getWidth()/2-60);
			scorelbl.setFont(font);
			scorelbl.setLayoutY(70);
			scorelbl.setAlignment(Pos.CENTER);
			scorelbl.setTextAlignment(TextAlignment.CENTER);
			
			//calling the magikoopa class
			mk= new Magikoopa();
			
			//adding the background and title and score labels to the pane
			root.getChildren().addAll(iviewBack, title, scorelbl);
			//adding the magikoopa image to the pane by calling the getImage method
			root.getChildren().add(mk.getImage());
			
			//show the scene
			primaryStage.setTitle("Magikoopa vs. Bullet Bill");
			primaryStage.setScene(scene);
			primaryStage.show();
			
			//creating secondary sound effects using AudioClip and storing them in an array
			AudioClip[] clips = new AudioClip[] {new AudioClip("file:laugh.mp3"),
					new AudioClip("file:bullet.wav")};
			
			
			//when the user presses a key
			scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
				public void handle(KeyEvent e) 
				{
					//right, left, up and down are boolean variables controlling the movement of the magikoopa
					//depending upon whether the keys are pressed or released
					
					
					//if right key is pressed
					if (e.getCode() == KeyCode.RIGHT)
					{
						//set the variable to true
						right=true;
					}
					//if left key is pressed
					else if (e.getCode()==KeyCode.LEFT)
					{
						//set the variable to true
						left=true;
					}
					//if up key is pressed
					else if (e.getCode()==KeyCode.UP)
					{
						//set the variable to true
						up=true;
					}
					//if down key is pressed
					else if (e.getCode()==KeyCode.DOWN)
					{
						//set the variable to true
						down=true;
					}
				}});
			
			//when the user releases a specific key
			scene.setOnKeyReleased(new EventHandler<KeyEvent>() {
				public void handle (KeyEvent e)
				{
					//see line 129
					
					//if right key is released
					if (e.getCode() == KeyCode.RIGHT)
					{
						//set the variable to false
						right = false;
					}
					//if left key is released
					else if (e.getCode() == KeyCode.LEFT)
					{
						//set the variable to false
						left = false;
					}
					//if up key is released
					else if (e.getCode() == KeyCode.UP)
					{
						//set the variable to false
						up = false;
					}
					//if down key is released
					else if (e.getCode() == KeyCode.DOWN)
					{
						//set the variable to false
						down = false;
					}
						
					
				}
			});
			
			//using the animation timer to move the magikoopa and the bullets
			animation= new AnimationTimer() 
			{
				public void handle (long now)
				{
					//if up is true (the key is pressed)
					if (up == true)
					{
						//call the moveUp method from the magikoopa class (passing in the default speed)
						mk.moveUp(mspeed);
						
						//if it touches the top of the screen
						if (mk.getY() -10<0)
						{
							//reset its position so it doesn't cross the boundary of the room
							mk.setY(10);
						}
					}
					
					//if down is true (the key is pressed)
					else if (down == true)
					{
						//call the moveDown method from the magikoopa class (passing in the default speed)
						mk.moveDown(mspeed);
						
						//if it touches the bottom of the room
						if (mk.getY() + mk.getHeight() +10> scene.getHeight())
						{
							//reset its position so it doesn't cross the boundary of the room
							mk.setY((int) (scene.getHeight() - mk.getHeight()-10));
						}
					}
					
					//if left is true (the key is pressed)
					else if (left == true)
					{
						//call the moveLeft method from the magikoopa class (passing in the default speed)
						mk.moveLeft(mspeed);	
						
						//if it touches the left side of the room
						if (mk.getX()-10<0)
						{
							//reset its position so it doesn't cross the boundary of the room
							mk.setX(10);
						}
						
					}
					
					//if right is true (the key is pressed)
					else if (right == true)
					{
						//call the moveRight method from the magikoopa class (passing in the default speed)
						mk.moveRight(mspeed);
						
						//if it touches the right side of the room
						if (mk.getX() + mk.getWidth() +10> scene.getWidth())
						{
							//reset its position so it doesn't cross the boundary of the room
							mk.setX((int) (scene.getWidth() - mk.getWidth()-10));
						}						
					}
					
					//call the getImage method so that the corresponding image can be seen on the screen
					mk.getImage();
					
					//for loop going through the size of the bullet adding bullets to the arraylist
					for (int i=0;i<bullet.size();i++)
					{
						//move the bullet at the index corresponding to i with a default speed of 10 and 
						//call upon the getImage method so that the correct image can be seen on the screen
						bullet.get(i).move(bspeed);
						bullet.get(i).getImage();
						
						//if a bullet at the i index collides with the magikoopa
						if (bullet.get(i).getImage().getBoundsInParent().intersects(mk.getObjectBounds()))
						{
							//play the collision sound
							clips[1].play();
							
							//change collide to true so that the laughing sound and bullet colliding sound don't play together
							//if the player collides when their score is a multiple of 100
							collide=true;
							
							//stop the animation so that the bullets don't keep moving and magikoopa can't move either
							animation.stop();
							
							//call the kill method from the magikoopa (which declares that the magikoopa is indeed dead)
							mk.kill();
							
							//call the getImage method so that the magikoopa's image can be changed to greyscale
							mk.getImage();
							
							//stop the bullets from generating by stopping the timeline
							clock.stop();
							
							//call the endgame method
							endgame();
							
						}
						
						//if it reaches the left side of the room without hitting magikoopa (he was able to escape it)
						if (bullet.get(i).getX()<0)
						{
							//remove that specific bullet from the pane, arrayList and reduce the index value by 1
							root.getChildren().remove(bullet.get(i).getImage());
							bullet.remove(i);
							bulletCount--;
							
							//increase the score by 10 and change the label to display the updated score
							score+=10;
							scorelbl.setText("SCORE: "+ score);
							
							//if the score is a multiple of 100 and collide is false (the magikoopa doesn't collide with a bullet)
							if (score%100==0 && collide==false)
							{
								//play the laughing sound
								clips[0].play();
							}
							
						}
					}
				}};
				
				//start the animation timer
				animation.start();
				
				//creating the keyframe object running every 300 milliseconds to generate bullets
				KeyFrame kfBullet = new KeyFrame(Duration.millis(300), new
							EventHandler<ActionEvent>() 
					{
							public void handle(ActionEvent e) 
							{
								//increase the index value by one 
								bulletCount++;
								
								//add a bullet (based on the properties of the constructor of the BulletBill class
								//at the index
								bullet.add(bulletCount, new BulletBill());
								
								//set its location passing in the width and height to the method and add it to the pane
								bullet.get(bulletCount).setLocation((int) scene.getWidth(), (int) scene.getHeight());
								root.getChildren().add(bullet.get(bulletCount).getImage());
							}
					});
					
							//initializing the timeline object and passing in the keyframe
							clock = new Timeline(kfBullet);
							clock.setCycleCount(Timeline.INDEFINITE);
							
							//starting the timeline object
							clock.play();
		}
		
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
	
	//method to display the game over alert 
	public void endgame()
	{
		//alert to go along with animation timer
		Platform.runLater(new Runnable() 
		{
				
			public void run()
			{		
				//stop the background music
				backgroundPlayer.stop();
				
				//checking the direction so that the relevant image can be drawn to the alert
				if (mk.direction()==mk.EAST)
				{
					mkAlert= new ImageView (new Image ("file:deadEast.png"));
				}
				
				else
				{
					mkAlert= new ImageView (new Image ("file:deadWest.png"));
				}
				
				//alert to display the game over message and score the player achieved
				Alert over= new Alert(AlertType.INFORMATION);
				over.setContentText("GAME OVER!\n Your score was: " + score);
				over.setGraphic(mkAlert);
				over.setHeaderText(null);
				over.getButtonTypes().clear();
				over.getButtonTypes().add(ButtonType.OK);
				over.showAndWait();
				
				//exit the game when the user presses ok
				System.exit(0);
			}
			
		});
	}
}
