package application;

import java.util.Random;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class BulletBill 
{
	//creating private and public variables
	private Image imgBullet;
	private ImageView iviewBullet;
	private int xPos, yPos, width, height;
	Random rand;
	
	//constructor
	public BulletBill()
	{
		//initializing the variables
		rand= new Random();
		imgBullet= new Image ("file:bulletWest.png");
		iviewBullet= new ImageView(imgBullet);
		xPos=0;
		yPos=0;
		iviewBullet.setX(xPos);
		iviewBullet.setY(yPos);
		
		//setting width and height to that of the image stored in iviewBullet
		width= (int)iviewBullet.getImage().getWidth();
		height= (int) iviewBullet.getImage().getHeight();
	}
	
	//move method to move the bullet
	public void move(int pixels)
	{
		//moving the bullet left according to the value passed in and updating the x position
		xPos-=pixels;
		iviewBullet.setX(xPos);
	}
	
	//set location method
	public void setLocation(int frameWidth, int frameHeight)
	{
		//set the x position outside the frame 
		xPos=(int) (frameWidth+iviewBullet.getImage().getWidth());
		//randomize the y position starting from the top to the bottom of the screen
		yPos=rand.nextInt(frameHeight- (int) iviewBullet.getImage().getHeight());
		
		//update the x and y position of the bullet based on the value stored in xPos and yPos
		iviewBullet.setX(xPos);
		iviewBullet.setY(yPos);
	}
	
	//getImage method
	public ImageView getImage()
	{
		//set the iviewBullet to the image stored in imgBullet and return it
		iviewBullet.setImage(imgBullet);
		return iviewBullet;
		
	}
	
	//set the x position to the value passed in
	public void setX(int x)
	{
		xPos=x;
	}
	
	//set the y position to the value passed in
	public void setY(int y)
	{
		yPos = y; 
	}
	
	//return the x position of the bullet
	public int getX()
	{
		return xPos; 
	}
	
	//return the y position of the bullet
	public int getY()
	{
		return yPos; 
	}
	
	//return the width 
	public int getWidth()
	{
		return width; 
	}
	
	//return the height
	public int getHeight()
	{
		return height; 
	}
}
