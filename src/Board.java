import javax.swing.*;
import BreezySwing.*;
import java.awt.Color;
import java.util.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 * This program uses BreezySwing to create a grid of panes. In the grid the user controls a snake who's goal is to collect as many dots as it can
 * while avoiding running into walls of their growing body.
 * 
 */
public class Board extends GBFrame{
	
	//trueDirection is the actual direction of the snake while the other Direction variables are to keep track of where the user wants to go.
	static boolean leftDirection, upDirection, downDirection, rightDirection = true;
	static int trueDirection = 1;//0 is up. 1 is right. 2 is down. 3 is left
	
	/*
	 * speed controls how quickly the snake moves. 
	 * Alive will become false if the user hits a wall or themselves.
	 */
	static boolean alive = true;
	static int speed = 5;//100 is one second
	//Colors used in the game
	static Color defaultColor = Color.black;
	static Color snakeColor = Color.green;
	static Color dotColor = Color.red;
	//Used to maintain the specified speed
	static long prevTime;
	static long now;
	//Score is at the top of the window and is updated when the user collects a apple or dies.
	static JLabel score;
	static Integer numScore = 0;
	
	//gamePanels is a 2d list of GBPanels. This contains the colored panels used to visualize the game.
	static ArrayList<ArrayList<GBPanel>> gamePanels;
	
	/*
	 * This constructor takes the number of rows, columns and a color as parameters.
	 * It uses them to make the score label and call the makePanels method to add the grid to the window.
	 * addKeyListener listens to keyboard input
	 */
	public Board(int numrows, int numcols, Color hue) {
		score = addLabel("Score: " + numScore,	 1,2,numrows,1);
		
		gamePanels = new ArrayList<>();
		
		makePanels(numrows, numcols, gamePanels, 1, 1, Color.black);
		addKeyListener(new TAdapter());
		
	}
	
	public static void main(String[] args) {
		Random rand = new Random();
		alive = true;
		int rows = 15;
		int cols = 15;
		
		//A position is made to store the location of the apple.
		Position dot;
		//A list of positions is used to represent the location of the snake's body
		LinkedList<Position> Snake = new LinkedList<>();	
		
		//The board is created and added into the frame
		JFrame frm = new Board(rows, cols, defaultColor);
		frm.setSize (600, 610);
		frm.setTitle("Snake");
		frm.setVisible (true);
		
		//Loops infinitely to startup the game immediately after death
			while(true) {
				
				int randRow = rand.nextInt(rows);
				int randCol = rand.nextInt(cols);
				//Putting the snake's head into the grid and into the snake's Position list
				getGamePanels().get(randRow).get(randCol).setBackground(snakeColor);
			    setSnake(randRow, randCol, Snake);
			    
			    //Places an apple in an empty spot in the grid
			    spawnApple();
			    
			    
				prevTime = System.currentTimeMillis()/10;
				now = System.currentTimeMillis()/10;
				
				//Loops forever until the snake runs into a wall or itself
				while(alive) {
					if(now - prevTime > speed) {//This determines how quickly the snake moves through the grid
						
						prevTime = System.currentTimeMillis()/10;
						now = System.currentTimeMillis()/10;
						
						//Moving the snake through the grid
						move(Snake);		
						
					}else {
						now = System.currentTimeMillis()/10;
						
					}
			    	  
			    	
				}
				//Reseting the board for a new game
				resetBoard(defaultColor, Snake);
			
		}
		
	}
	/**
	 * 
	 * @param defaultColor is the color used for empty grid blocks
	 * @param Snake is the list of Positions that the snake exists in
	 */
	public static void resetBoard(Color defaultColor, LinkedList<Position>Snake) {
		//Changing the background of all grid panels to defaultColor
		for(int i = 0; i < gamePanels.size(); i++) {// Rows
		 			 
			for(int j = 0; j < gamePanels.get(0).size(); j++) {//columns
			gamePanels.get(i).get(j).setBackground(defaultColor);
			
			}
		}
		//Resetting variables
		numScore = 0;
		score.setText("Score: " + numScore);
		trueDirection = 1;
		alive = true;
		prevTime = 0;
		now = 0;
		leftDirection = false;
		upDirection = false;
		downDirection = false;
		rightDirection = true;
		int size = Snake.size();
		//Popping every item out of snake
		while(size > 0) {
			Snake.poll();
			size--;
		}
	}
	/**
	 * 
	 * @param randRow Takes a random row in main
	 * @param randCol Takes a random column in main
	 *  Together the variables make up the position of the Snake's head
	 * @param Snake The list of Snake Positions
	 * setSnake adds the first locations of the snake's body.
	 * When assigning the location of the tail it checks that where there is empty space adjacent to the head.
	 */ 
	public static void setSnake(int randRow, int randCol, LinkedList<Position> Snake) {
		Position snakeStart = new Position(randRow, randCol);
	    Position snakeTail;
	    //if the head isn't at the first column place the tail to the left
	    if(randCol != 0) {
	    	getGamePanels().get(randRow).get(randCol - 1).setBackground(snakeColor);
	    	snakeTail = new Position(randRow, randCol - 1);
	    }else if(randRow != 0) {//otherwise if the head isn't at the first row place the tail above
	    	getGamePanels().get(randRow - 1).get(randCol).setBackground(snakeColor);
	    	snakeTail = new Position(randRow - 1, randCol);
	    }else {//otherwise place the tail below
	    	getGamePanels().get(randRow).get(randCol + 1).setBackground(snakeColor);
	    	snakeTail = new Position(randRow, randCol + 1);
	    }
	    
	    Snake.add(snakeTail);
	    Snake.add(snakeStart);
	}
	/**
	 * 
	 * @param snake The list of Snake Positions
	 * Moves the snake throughout the grid according to the user's directions. It also updates the score and changes the alive variable
	 */
	public static void move(LinkedList<Position> snake) {
		//Temp Position
		Position holder;
		//A placeholder for getGamePanels()
		ArrayList<ArrayList<GBPanel>> pointer = getGamePanels();
		
		//Going UP
		if(upDirection) {
			if(snake.peekLast().getRow() == 0) {//if the last Item in snake(the head) is against a wall then set alive to false and return
				alive = false;
				return;
			//If the snake is hitting itself then return
			}else if(pointer.get(snake.peekLast().getRow() - 1).get(snake.peekLast().getCol()).getBackground().equals(snakeColor)) {
				alive = false;
				return;
			//If the snake is going to an empty space then add to the head and remove the tail
			}else if(pointer.get(snake.peekLast().getRow() - 1).get(snake.peekLast().getCol()).getBackground().equals(defaultColor)) {
				holder = snake.poll();
				pointer.get(holder.getRow()).get(holder.getCol()).setBackground(defaultColor);
			
			//if the snake hits an apple add to score and spawn an apple
			}else if(pointer.get(snake.peekLast().getRow() - 1).get(snake.peekLast().getCol()).getBackground().equals(dotColor)){
				numScore++;
				score.setText("Score: " + numScore.toString());
				spawnApple();
			}
			//Move the snake in the grid and update the position values in the snake list. Also update trueDirection
			pointer.get(snake.peekLast().getRow() - 1).get(snake.peekLast().getCol()).setBackground(snakeColor);
			snake.add(new Position((snake.peekLast().getRow() - 1), snake.peekLast().getCol()));
			trueDirection = 0;//0 is up
			return;
		//Every other direction has the same structure as up does
		//Going Right
		}else if(rightDirection) {
			if(snake.peekLast().getCol() == pointer.size() - 1) {//if the 
				alive = false;
				return;
			
			}else if(pointer.get(snake.peekLast().getRow()).get(snake.peekLast().getCol() + 1).getBackground().equals(snakeColor)) {
				alive = false;
				return;
			}
			else if(pointer.get(snake.peekLast().getRow()).get(snake.peekLast().getCol() + 1).getBackground().equals(defaultColor)) {
				holder = snake.poll();
				pointer.get(holder.getRow()).get(holder.getCol()).setBackground(defaultColor);
			}else if(pointer.get(snake.peekLast().getRow()).get(snake.peekLast().getCol() + 1).getBackground().equals(dotColor)){
				numScore++;
				score.setText("Score: " + numScore.toString());
				spawnApple();
			}
			pointer.get(snake.peekLast().getRow()).get(snake.peekLast().getCol() + 1).setBackground(snakeColor);
			snake.add(new Position((snake.peekLast().getRow()), snake.peekLast().getCol() + 1));
			trueDirection = 1;//1 is right
			return;
			//Going Down
		}else if(downDirection) {
			if(snake.peekLast().getRow() == pointer.get(0).size() - 1) {
				alive = false;
				return;
			
			}else if(pointer.get(snake.peekLast().getRow() + 1).get(snake.peekLast().getCol()).getBackground().equals(snakeColor)) {
				alive = false;
				return;
			}
			else if(pointer.get(snake.peekLast().getRow() + 1).get(snake.peekLast().getCol()).getBackground().equals(defaultColor)) {
				holder = snake.poll();
				pointer.get(holder.getRow()).get(holder.getCol()).setBackground(defaultColor);
			}else if(pointer.get(snake.peekLast().getRow() + 1).get(snake.peekLast().getCol()).getBackground().equals(dotColor)){
				numScore++;
				score.setText("Score: " + numScore.toString());
				spawnApple();
			}
			pointer.get(snake.peekLast().getRow() + 1).get(snake.peekLast().getCol()).setBackground(snakeColor);
			snake.add(new Position((snake.peekLast().getRow() + 1), snake.peekLast().getCol()));
			trueDirection = 2;//2 is right
			return;
		//Going Left
		}else{
			if(snake.peekLast().getCol() == 0) {
				alive = false;
				return;
			
			}else if(pointer.get(snake.peekLast().getRow()).get(snake.peekLast().getCol() - 1).getBackground().equals(snakeColor)) {
				alive = false;
				return;
			}
			else if(pointer.get(snake.peekLast().getRow()).get(snake.peekLast().getCol() - 1).getBackground().equals(defaultColor)) {
				holder = snake.poll();
				pointer.get(holder.getRow()).get(holder.getCol()).setBackground(defaultColor);
			}else if(pointer.get(snake.peekLast().getRow()).get(snake.peekLast().getCol() - 1).getBackground().equals(dotColor)){
				numScore++;
				score.setText("Score: " + numScore.toString());
				spawnApple();
			}
			pointer.get(snake.peekLast().getRow()).get(snake.peekLast().getCol() - 1).setBackground(snakeColor);
			snake.add(new Position((snake.peekLast().getRow()), snake.peekLast().getCol() - 1));
			trueDirection = 3;//3 is down
			return;
		}
	}
	/**
	 * Spawns and apple in an empty space in the grid. If it couldn't randomly find an empty space
	 * after looking 500 times it starts to move through the grid one spot after the other to find a free space.
	 */
	public static void spawnApple() {
		Random rand = new Random();
		ArrayList<ArrayList<GBPanel>> pointer = getGamePanels();
		int row = rand.nextInt(pointer.size());
		int col = rand.nextInt(pointer.get(0).size());
		for(int i = 0; i < 500; i++) {
			if(pointer.get(row).get(col).getBackground().equals(defaultColor)) {
				pointer.get(row).get(col).setBackground(dotColor);
				return;
			}
		}
		
		for(int i = 0; i < pointer.size(); i++) {
			for(int j = 0; j < pointer.get(0).size(); j++) {
				if(pointer.get(i).get(j).getBackground().equals(defaultColor)) {
					pointer.get(i).get(j).setBackground(dotColor);
					return;
				}
			}
		}
	}
	
	/**
	 * 
	 * @param rows number of rows in the list of panels
	 * @param cols number of columns in the list of panels
	 * @param gamePanels list of lists of panels
	 * @param width width of each panel
	 * @param height height of each panel
	 * @param hue color of each panel
	 * 
	 * Used in the Board constructor to make the grid of black ColorPanels
	 */
	public void makePanels(int rows, int cols, ArrayList<ArrayList<GBPanel>> gamePanels, int width, int height, Color hue){
		
		//Adding a new Array of GBPanels to the gamePanels 
		for(int i = 0; i < rows; i++) {// Rows
				gamePanels.add(new ArrayList<GBPanel>());
			 // Getting the item at the ith index of gamePanels and adding a ColorPanels to it.			 
			for(int j = 0; j < cols; j++) {//columns
				gamePanels.get(i).add(addPanel(new ColorPanel(hue), i + 2, j + 1, width, height));
			}
		}
		
	}
	/**
	 * 
	 * Used to make a keyListener. It listens to input to move the snake around. Specifically it listens
	 * for the arrow keys and WASD.
	 * trueDirection is used to prevent the directions from being changed quickly before the graphic update. It prevents
	 * the snake from going to the position its head was just at.
	 *
	 */
	private class TAdapter extends KeyAdapter {

	    @Override
	    public void keyPressed(KeyEvent e) {

	        int key = e.getKeyCode();
	        //Left
	        if (((key == KeyEvent.VK_LEFT)|| (key == KeyEvent.VK_A)) && trueDirection != 1) {
	            leftDirection = true;
	            upDirection = false;
	            downDirection = false;
	            
	        }
	        //Right
	        if (((key == KeyEvent.VK_RIGHT) || (key == KeyEvent.VK_D)) && trueDirection != 3) {
	            rightDirection = true;
	            upDirection = false;
	            downDirection = false;
	        }
	        //Up
	        if (((key == KeyEvent.VK_UP) || (key == KeyEvent.VK_W)) && trueDirection != 2) {
	            upDirection = true;
	            rightDirection = false;
	            leftDirection = false;
	        }
	        //Down
	        if (((key == KeyEvent.VK_DOWN) || (key == KeyEvent.VK_S)) && trueDirection != 0) {
	            downDirection = true;
	            rightDirection = false;
	            leftDirection = false;
	        }
	        
	        
	    }
	}

	public static ArrayList<ArrayList<GBPanel>> getGamePanels() {
		return gamePanels;
	}
	
}
//Used to make colored panels for the grid
class ColorPanel extends GBPanel{
	public ColorPanel(Color color){
		setBackground(color);
	}
	 
	
}
/**
 * 
 * Object that stores two integers, row and col. It is used to store the current location
 * of game items within the grid. 
 *
 */
class Position{
	int row;
	int col;
	public Position(int row, int col){
		this.row = row;
		this.col = col;
	}
	public int getRow() {
		return row;
	}
	public void setRow(int row) {
		this.row = row;
	}
	public int getCol() {
		return col;
	}
	public void setCol(int col) {
		this.col = col;
	}
}


