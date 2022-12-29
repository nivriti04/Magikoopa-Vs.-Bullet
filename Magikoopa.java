package application;

import javafx.geometry.Bounds;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Rectangle;

public class Magikoopa 
{
	//creating private and public variables
	private int xPos, yPos, direction, width, height;
	private Image imgEast, imgWest, imgDeadEast, imgDeadWest;
	private ImageView iviewMK;
	private boolean dead;
	public final static int EAST=0, WEST=1;
	
	//constructor
	public Magikoopa()
	{
		//initializing the variables
		xPos=0;
		yPos=0;
		dead=false;
		direction=EAST;
		
		imgEast= new Image ("file:koopaEast.png");
		imgWest= new Image ("file:koopaWest.png");
		imgDeadWest= new Image ("file:deadWest.png");
		imgDeadEast= new Image ("file:deadEast.png");
		
		iviewMK= new ImageView(imgEast);
		iviewMK.setX(0);
		iviewMK.setY(0);
		
		//setting width and height to that of the image stored in iviewMK
		width=(int) iviewMK.getImage().getWidth();
		height=(int) iviewMK.getImage().getHeight();
		
	}
	
	//method to call when moving right
	public void moveRight(int pixels)
	{
		//increase the xPos by the number passed in 
		xPos+=pixels;
		
		//change direction to EAST
		direction=EAST;
		
		//update the x position
		iviewMK.setX(xPos);
	}
	
	//method to call when moving left
	public void moveLeft(int pixels)
	{
		//decrease the xPos by the number passed in
		xPos-=pixels;
		
		//change direction to WEST
		direction=WEST;
		
		//update the x position
		iviewMK.setX(xPos);
	}
	
	//method creating a rectangle around the magikoopa to help in more precise collision
	public Bounds getObjectBounds() 
	{
		Rectangle rect = new Rectangle(xPos + 20, yPos, width - 30,	height);
		
		return rect.getBoundsInParent();
	}
	
	//method to call when moving up
	public void moveUp(int pixels)
	{
		//decrease the yPos by the number passed in
		yPos-=pixels;
		
		//update the y position
		iviewMK.setY(yPos);
	}
	
	//method to call when moving down
	public void moveDown(int pixels)
	{
		//increase the yPos by the number passed in
		yPos+=pixels;
		
		//update the y position
		iviewMK.setY(yPos);
	}
	
	//method changing the boolean variable dead to true indicating that the magikoopa is dead
	public void kill()
	{
		dead=true;
	}
	
	//method to call upon the appropriate image
	public ImageView getImage()
	{
		//if the magikoopa is alive
		if (dead==false)
		{
			//set the east image if direction is EAST
			if (direction==EAST)
			{
				iviewMK.setImage(imgEast);	
			}
			//set the west image if direction is WEST
			else if (direction==WEST)
			{
				iviewMK.setImage(imgWest);
			}
		}
		
		//else if the magikoopa is dead
		else
		{
			//set the dead east image if direction is EAST
			if (direction==EAST)
			{
				iviewMK.setImage(imgDeadEast);	
			}
			//set the dead west image if direction is WEST
			else if (direction==WEST)
			{
				iviewMK.setImage(imgDeadWest);
			}
		}
			return iviewMK;
		
	}
	
	//method returning direction
	public int direction() 
	{
		return direction;
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
	
	//return the x position of the magikoopa
	public int getX()
	{
		return xPos; 
	}
	
	//return the y position of the magikoopa
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
