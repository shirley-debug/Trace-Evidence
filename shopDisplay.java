/**
 * shopDisplay.java
 * Designed by: Shirley Lin
 * Edited by: Jane
 * June 1st 2019
 * This program proforms shop display which is associated with the SHOP button from the MainGameBoard
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import javax.imageio.*; 
import java.util.Scanner;

public class shopDisplay extends JFrame implements ActionListener{
  ShopBackground shopPan = new ShopBackground(); //set the frame background
  JButton buyButton = new JButton("BUY A CLUE FOR 5 COINS"); //global variables for buttons
  JButton checkButton=new JButton("CHECK MY COINS");
  JButton exitShopButton=new JButton("EXIT SHOP");
  public static int coins = CrossyGameBoard.getCoins(); //call the CrossyGameBoard class to get coins
  public static int cost; //create the varible called cost to save how much the player spent on buying evidence
  public static int[] playerClues=MainGameBoard.getPlayerClues(); //call the MainGameBoard to get player clues
  public static String []allClues=new String[43]; //String Array for display the string
  
  //constructor 
  public shopDisplay(){
    setLayout(new FlowLayout());
    setSize(1000,700);
    setTitle("SHOP");
    shopPan.setPreferredSize(new Dimension(1000,700));//set the size for the panel
    shopPan.setLayout(null);
    buyButton.setBounds(200,60,250,40);//set the size and position for buttons
    checkButton.setBounds(500,60,250,40);
    exitShopButton.setBounds(20,10,100,40);
    shopPan.add(buyButton);//add them to the panel
    shopPan.add(checkButton);
    shopPan.add(exitShopButton);
    buyButton.addActionListener(this);//add action listener so that it can capture the mouse click
    checkButton.addActionListener(this);
    exitShopButton.addActionListener(this);
    add(shopPan);//add the panel to the frame
    setVisible(true);//set frame visible
  }//end constructor 
  
  //******************************Class ShopBackground***********************
  //To set up background as a panel
  class ShopBackground extends JPanel{
    Image img2;
    //constructor
    public ShopBackground(){
      try{
        img2 = ImageIO.read(new File("shop.jpg"));
      }catch(Exception e) {  };//end catch
    }//end constructor
    public void paintComponent(Graphics g) {        
      g.drawImage(img2,0,0,1000,700,null);
    }//end paintComponent
  }//end ShopBackground
  
  /** This method will help to generate a random index as well as associating with other method to check if the clue is already taken
    * @param []playerClues the array which contains 0 and 1 (0 represents not taken and 1 represents taken)
    * return the index that is drawn in the type integer
    */ 
  public static int drawAClue(int[]playerClues){
    int drawIndex = (int)(Math.random()*43); //random generate index
    boolean ifDrawn = check(drawIndex, playerClues);//all the check method to check if it is already drawn
    while(ifDrawn == true){//if is drawn, ganerate another index
      drawIndex = (int)(Math.random()*43);
      ifDrawn = check(drawIndex, playerClues);
    }//end while
    return drawIndex;
  }//end drawAClue
  
  /** This method will help to check if the clue is already taken
    * @param drawIndex the index that is gotten from the drawAClue mthod
    * @param []playerClues the array which contains 0 and 1 to show if the player owns the clue or not
    * return the boolean if the clue is taken
    */ 
  public static Boolean check(int drawIndex, int[] playerClues){
    boolean ifDrawn = false; //default as false
    if(playerClues[drawIndex]!=0){ //if the value in that position isn't 0 then the clue is drawn
      ifDrawn = true; 
    }//end if
    return ifDrawn; 
  }//end check
  
  /** This method set up the string for the allClue arrray by reading from a text file and can be called in other classes 
    * return String array of with evidence in it
    */
  public static String [] getAllClues()throws IOException{
    File clueFile=new File ("Evidence.txt");//define the file
    Scanner readFile=new Scanner (clueFile);//read the file
    for (int index=0; index<allClues.length; index++){//get the string from each line
      allClues[index]=readFile.nextLine();
    }//end for
    readFile.close(); //close the file after finish reading
    return allClues;
  }//end getAllClues
  
  /** This method check if the player alreay has all clues
    * @param []playerClues the array which contains 0 and 1 
    * return if the array is full
    */ 
  public static Boolean ifFull (int[] playerClues){
    boolean ifFull=true;//default as true
    for (int index=0; index<playerClues.length; index++){ 
      if (playerClues[index]==0){ //if the value in that position is 0, then the array is not full
        ifFull=false;
      }//end if
    }//end for
    return ifFull;
  }//end ifFull
  
  public void actionPerformed(ActionEvent event) {
    coins=getCoins();//call the getCoins method to get coins from CrossyGameBoard
    if(event.getSource()==buyButton){
      //compare the coins to cost to calculate if the player has enough coins to but evidence
      if (coins-cost<5){ //if the player doesn't have enough monet
        JOptionPane.showMessageDialog(null,"Ops! You don't have enough coins.");
      } else if (ifFull(getPlayerClues())==true){ //if player has all evidence already
        JOptionPane.showMessageDialog(null,"Wow! You've gotten all evidence");
      } else { //draw a clue
        int drawClueIndex = drawAClue(playerClues); //call the draeAClue to draw a clue
        playerClues[drawClueIndex]=1; //set the value at position to 1 to show that the clue is drawn
        String displayClues = allClues[drawClueIndex];//display the clues
        cost+=5;//record the cost
        JOptionPane.showMessageDialog(shopPan,displayClues);
      }
    }//end if buyButton is pressed
    if (event.getSource()==checkButton){ //display the current coins
      JOptionPane.showMessageDialog(null,"You currently have "+(coins-cost)+" coins");
    }//end checkButton is pressed
    if (event.getSource()==exitShopButton){ //display the current coins
      setVisible(false);
    }//end exitShopButton is pressed
  }//end actionPerformed
  
  /** This method returns the coins which is gotten from the CrossyGameBoard 
    * return value of coins
    */
  public static int getCoins(){
    coins = CrossyGameBoard.getCoins();
    return coins;
  }//end getCoins
  
  /** This method returns int array playerClues so that it can be called within the class and in other classes
    * return playerClues array
    */
  public static int [] getPlayerClues(){
    return playerClues;
  }//end getPlayerClues
  
}//end shopDisplay


