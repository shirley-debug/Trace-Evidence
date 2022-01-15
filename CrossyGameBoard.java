/**
 * CrossyGameBoard.java
 * Shirley Lin
 * May 26 2019
 * This program proforms the game Crossy
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*; 
import java.util.Scanner;
import java.awt.image.*;
import javax.imageio.*;
import java.awt.Rectangle;
import javax.swing.JOptionPane;

//********************************Crossy Game Board**************************************
public class CrossyGameBoard extends JFrame{ 
  
  //set global variables
  public static int point=0;//point for each round
  public static int subtotalPoint=0;//the points evenytime when the game is played
  public static int coins;//total points, which will be called in main game board
  public static final int ROW_WIDTH=750, ROW_HEIGHT=100;//bounds for bird components
  JFrame crossy=new JFrame();//the frame where the game is displayed
  
  /** This method will create a 9-row game board, which will be triggered at the beginning or when the player successfully complete one page
    * @param game the panel where the components will be added
    */ 
  public void create (JPanel game){
    //ramdom generate components for each row
    for (int numOfRows=1; numOfRows<=8; numOfRows++){
      int randomRow=(int) (Math.random()*6);
      if (randomRow==0){
        GreenBird green=new GreenBird();//call the GreenBird class to add a green bird component
        green.setName("green");//set name so that it can be used when checking intersection in the future
        green.setBounds(0,numOfRows*100,ROW_WIDTH,ROW_HEIGHT);//set position and boundary
        game.add(green);//add the component to the panel
      } else if (randomRow==1){
        RedBird red=new RedBird();
        red.setName("red");
        red.setBounds(0,numOfRows*100,ROW_WIDTH,ROW_HEIGHT);
        game.add(red);
      } else if (randomRow==2){
        BrownBird brown=new BrownBird();
        brown.setName("brown");
        brown.setBounds(0,numOfRows*100,ROW_WIDTH,ROW_HEIGHT);
        game.add(brown);
      } else if (randomRow==3){
        YellowBird yellow=new YellowBird();
        yellow.setName("yellow");
        yellow.setBounds(0,numOfRows*100,ROW_WIDTH,ROW_HEIGHT);
        game.add(yellow);
      } else if (randomRow==4){
        BlueBird blue=new BlueBird();
        blue.setName("blue");
        blue.setBounds(0,numOfRows*100,ROW_WIDTH,ROW_HEIGHT);
        game.add(blue);
      } else {
        Cloud cloud=new Cloud();
        cloud.setBounds(0,numOfRows*100,ROW_WIDTH,ROW_HEIGHT);
        game.add(cloud);
        cloud.setName("cloud");
      }//end else
    }//end for
  }//end create()
  
  //constructor
  public CrossyGameBoard() { 
    setLayout(new FlowLayout());
    crossy.setTitle("Crossy");
    crossy.setSize(new Dimension(ROW_WIDTH,ROW_HEIGHT*9+50));  
    crossy.setResizable(true);
    crossy.setDefaultCloseOperation(crossy.HIDE_ON_CLOSE);//hide this frame when exit window
    //set up the game panel
    CrossyBackground game = new CrossyBackground();//call the CrossyBackground call to add the background panel
    game.setLayout (null); 
    //set up components
    Ball ball=new Ball();//call the ball class to create a new ball
    ball.setName("ball");//set name for checking intersection
    ball.setBounds(0,0,750,880);//set position and boundary
    game.add(ball);//add it to the game board
    create(game);//add components
    crossy.add(game);
    pack(); //makes the frame fit the contents
    ball.setFocusable(true);//so that the ball can perform movements 
    ball.requestFocusInWindow();
    crossy.setVisible(true);
    
    //add window listener so that everytime player exits the window, total coins will be calculated
    crossy.addWindowListener(new java.awt.event.WindowAdapter() {
      @Override
      //invoked when a window is in the process of being closed
        public void windowClosing(java.awt.event.WindowEvent windowEvent) {
        coins+=subtotalPoint;//update coins
      }//end window closing
    }//end window adapter
    );//end window listener
    
    //*******Run the Game*******
    try {
      Thread.sleep(50);//set thread
    } catch (InterruptedException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }//end catch
    
    //Android UIThread, make this program run on the main game board
    (new Thread(new Runnable(){
      @Override
      public void run(){
        subtotalPoint=0;//set the subtotal to 0 everytime re-enter the game
        while (!Thread.interrupted())
          try{
          Thread.sleep(50);
          Component[] components = game.getComponents();//get the components into an array
          Rectangle tempBall = ball.getBall();//get boundary for the ball 
          //************if the player finish one page
          if (tempBall.getY()>=880){
            point+=8;//add points
            //remove all components except the ball and create a new panel
            for (int i = 0; i<components.length; i++) {
              String tempName = components[i].getName();//save the components' name
              if (tempName != "ball") {
                game.remove(components[i]);
              }//end if
            }//end for
            ball.resetBall((int)tempBall.getX());//call resetBall method to reset
            create(game);//call create method to re-create the game
          } else { 
            //**********check intersection
            for (int i = 0; i<components.length; i++) { 
              String tempName = components[i].getName();
              if (tempName != "cloud" && tempName != "ball") {
                //Call the Bird class which inclued getXValue method to get the posion of the bird
                Rectangle tempBird = new Rectangle(((Bird) components[i]).getXValue(), components[i].getY(), 85, 85);
                //**********if intersects
                if (tempBall.intersects(tempBird)) { //if intersects, display message and reset the game
                  ball.resetBall(750/2-80/2);
                  point+=(components[i].getY())/100-1;//calculate points for the last page
                  JOptionPane.showMessageDialog(null,"You have touched the " + tempName + " bird");
                  JOptionPane.showMessageDialog(null,"You've get "+point+ " points");
                  subtotalPoint+=point;//calculate subtotal points (from the last GameOver)
                  point=0;//set the point to zero
                }//end if intersects
              }//end if birds
            }//end for
          }//end else
        } catch (InterruptedException e) {}//end catch
      }//end run()
    }//end override
    )//end new runable
    ).start();//end new thread
  }//end constructor
  
  /** This method gets the value of the coins variable which can be called in another class
    * return the coins value in the type integer
    */ 
  public static int getCoins(){
    return coins;
  }//end getCoins
  
  //***********************************************BALL CLASS************************************
  static class Ball extends JComponent implements KeyListener {
    Graphics ball;
    Player player;
    
    //constructor
    public Ball() { 
      setPreferredSize(new Dimension(80,80));//set size for the ball
      addKeyListener(this);
      player = new Player();//call the player class
      player.x=750/2-90/2;
      player.y=0;
    }//end constructor
    
    //paint the ball component
    public void paintComponent(Graphics ball) { 
      super.paintComponent(ball); //required to ensure the panel so correctly redrawn
      player.move();//call the move method to move the ball from Player class
      player.draw(ball);////update the content. draw the screen
      repaint();//request a repaint
    }//end paintComponent
    
    public void keyTyped(KeyEvent e) {    
    }//end keyTyped
    
    //set speed of the movement
    public void keyPressed(KeyEvent e) {
      int code=e.getKeyCode();
      if(code==KeyEvent.VK_LEFT){   
        player.xdirection=-5;
      } else if(code==KeyEvent.VK_UP){
        player.ydirection=-5;
      } else if(code==KeyEvent.VK_RIGHT){
        player.xdirection=5;
      } else if(code==KeyEvent.VK_DOWN){
        player.ydirection=5;
      }//end else if 
    }//end keyPressed
    
    //once released set the speed to 0
    public void keyReleased(KeyEvent e) {      
      int code=e.getKeyCode();
      if(code==KeyEvent.VK_LEFT){    
        player.xdirection=0;
      } else if(code==KeyEvent.VK_UP){
        player.ydirection=0;
      } else if(code==KeyEvent.VK_RIGHT){
        player.xdirection=0;
      } else if(code==KeyEvent.VK_DOWN){
        player.ydirection=0;
      } //end else if
    }//end keyPressed
    
    /** This method gets Rectangle of the ball, which has a location of the ball in the coordinate space
      * return the coordinate of the ball
      */ 
    public Rectangle getBall() {
      return new Rectangle ( player.x, player.y, 75, 75);
    }//getBall
    
    /** This method reset the ball, by given a new y value and reset the direction
      * @param the xValue so that the ball can be reset without changing the original x value
      */ 
    public void resetBall(int orgX) {
      player.x=orgX;
      player.y=-20;
      player.xdirection = 0;
      player.ydirection = 0;
    }//resetBall
  }//end class Ball
  
}//end class CrossyGameBoard


//***********************************CLASS PLAYER********************************
class Player { 
  //global variables
  int x, y; 
  int xdirection,ydirection;
  BufferedImage ballPic;
  
  //constructor
  public Player() { 
    //load picture
    try {
      ballPic = ImageIO.read(new File("ball.png"));
    } catch(Exception e) { };
  }//end constructor
  
  //draw the picture
  public void draw(Graphics ball) { 
    ball.drawImage(ballPic,x,y,80,80,null);
  }//end draw
  
  //movements
  public void move() { 
    this.x=this.x+this.xdirection;
    this.y=this.y+this.ydirection;
    //make sure that the ball doesn't go out of bounds
    if (x<-20){
      x=-20;
    } if (x>645){
      x=645;
    } if (y<0){
      y=0;
    }// end if 
  }//end move
  
}//end Player class

//*******************************CLASS BIRD*****************************
//The class which contains all birds
class Bird extends JComponent{
  public int x;
  //call every getXValue method from the class that extends Birds
  public int getXValue() {
    return x;
  }//end getXValue
}//end Bird class

//*******************************CLASS GREEN BIRD*****************************
class GreenBird extends Bird implements ActionListener{
  
  public int x = 650;//set start position
  Timer timer=new Timer (0,this);//add a new timer
  int xChange=(int)(Math.random()*5)-6;//randomize speed
  BufferedImage greenPic;
  
  //paint the ball component
  public void paintComponent(Graphics green) { 
    super.paintComponent(green); 
    timer.start();//start the timer
    draw(green);//call the draw method
    repaint();
  }//end paintComponent
  
  //draw the picture
  public void draw(Graphics green) { 
    //load picture
    try {
      greenPic = ImageIO.read(new File("Green Left.png"));
    } catch(Exception e) {  };
    green.drawImage(greenPic,x,0,100,100,null);
  }//end draw
  
  public void actionPerformed (ActionEvent e){
    //make sure the bird doesn't go out of bounds
    if (x<0 || x>650){
      x=650;
    }//end if
    x = x+xChange; //movement
    repaint();
  }//end actionPerformed
  
  //the method to pass the x value for checking intersection
  public int getXValue() {
    return x;
  }//end getXValue
  
}//end GreenBird Class

//*******************************CLASS RED BIRD*****************************
class RedBird extends Bird implements ActionListener{
  
  Timer timer=new Timer (0,this);
  public int x=0;
  int xChange=(int)(Math.random()*5)+8;
  BufferedImage redPic;
  
  public void paintComponent(Graphics red) { 
    super.paintComponent(red); 
    timer.start();
    draw(red);
    repaint();
  }//end paintComponent
  
  public void draw(Graphics red) { 
    try {
      redPic = ImageIO.read(new File("Red Right.png"));
    } catch(Exception e) {  };//end catch
    red.drawImage(redPic,x,0,100,100,null);
  }//end draw
  
  public void actionPerformed (ActionEvent e){
    if (x<0 || x>650){
      x=0;
    }//end if
    x = x+xChange;
    repaint();
  }//end actionPerformed
  
  public int getXValue() {
    return x;
  }//end getXValue
  
}//end RedBird Class

//*******************************CLASS BROWN BIRD*****************************
class BrownBird extends Bird implements ActionListener{
  
  Timer timer=new Timer (0,this);
  public int x=650;
  int xChange=(int)(Math.random()*5)-13;
  BufferedImage brownPic;
  
  public void paintComponent(Graphics brown) { 
    super.paintComponent(brown); 
    timer.start();
    draw(brown);
    repaint();
  }//end paintComponent
  
  public void draw(Graphics brown) { 
    try {
      brownPic = ImageIO.read(new File("Brown Left.png"));
    } catch(Exception e) {  };//end catch
    brown.drawImage(brownPic,x,0,100,100,null);
  }//end draw
  
  public void actionPerformed (ActionEvent e){
    if (x<0 || x>650){
      x=650;
    }//end if 
    x = x+xChange;
    repaint();
  }//end actionPerformed
  
  public int getXValue() {
    return x;
  }//end getXValue
  
}//end BrownBird Class

//*******************************CLASS YELLOW BIRD*****************************
class YellowBird extends Bird implements ActionListener{
  
  Timer timer=new Timer (0,this);
  public int x=0;
  int xChange=(int)(Math.random()*5)+13;
  BufferedImage yellowPic;
  
  public void paintComponent(Graphics yellow) { 
    super.paintComponent(yellow); 
    timer.start();
    draw(yellow);
    repaint();
  }//end paintComponent
  
  public void draw(Graphics yellow) { 
    try {
      yellowPic = ImageIO.read(new File("Yellow-Green Right.png"));
    } catch(Exception e) {  }; //end catch
    yellow.drawImage(yellowPic,x,0,100,100,null);
  }//end draw
  
  public void actionPerformed (ActionEvent e){
    if (x<0 || x>650){
      x=0;
    }//end if
    x = x+xChange;
    repaint();
  }//end actionPerformed
  
  public int getXValue() {
    return x;
  }//end getXValue
}//end YellowBird Class

//*******************************CLASS BLUE BIRD*****************************
class BlueBird extends Bird implements ActionListener{
  
  Timer timer=new Timer (0,this);
  public int x=650;
  int xChange=(int)(Math.random()*5)-20;
  BufferedImage bluePic;
  
  public void paintComponent(Graphics blue) { 
    super.paintComponent(blue); 
    timer.start();
    draw(blue);
    repaint();
  }//end paintComponent
  
  public void draw(Graphics blue) { 
    try {
      bluePic = ImageIO.read(new File("Blue Left.png"));
    } catch(Exception e) {  };
    blue.drawImage(bluePic,x,0,100,100,null);
  }//end draw
  
  public void actionPerformed (ActionEvent e){
    if (x<0 || x>650){
      x=650;
    }//end if
    x = x+xChange;
    repaint();
  }//end actionPerformed
  
  public int getXValue() {
    return x;
  }//end getXValue
  
}//end BlueBird Class

//*******************************CLASS CLOUD BIRD*****************************
class Cloud extends JComponent{//draw cloud component as an empty row with picture
  
  BufferedImage cloudPic;
  
  public void paintComponent(Graphics cloud) { 
    super.paintComponent(cloud); 
    draw(cloud);
    repaint();
  }//end paintComponent
  
  public void draw(Graphics cloud) { 
    //load picture
    try {
      cloudPic = ImageIO.read(new File("Cloud.png"));
    } catch(Exception e) {  }; //end catch
    cloud.drawImage(cloudPic,0,0,750,100,null);//draw the picture
  }//end draw
  
}//and Cloud class

//*******************************CLASS BACKGROUND*****************************
class CrossyBackground extends JPanel{ //set the background image into a panel
  Image bgImg;
  //constructor
  public CrossyBackground(){
    //load picture
    try{
      bgImg = ImageIO.read(new File("Crossy.jpg"));
    }catch(Exception e) {  };
  }//end constructor
  public void paintComponent(Graphics g) {        
    g.drawImage(bgImg,0,0,750,900,null);
  }//end paintComponent
  
}//end CrossyBackground