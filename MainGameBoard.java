/**
 * Main Game Board.java
 * Created by Shirley Lin, Jane Sun
 * Comments added by Jane
 * Created on May 26, 2019
 * Purpose: this is the two main menus for our game, used for display and calling out other files(button selections)
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


public class MainGameBoard extends JFrame implements ActionListener{ //this is for displaying the menu
  MainBackground mainPan = new MainBackground();//for setting a background
  
  JLabel titleDisplay = new JLabel("TRACE EVIDENCE");//name of the game
  public static String[]allClues;
  public static String[]allClues()throws IOException{//pass this variable from the shopDisplay java file, make this a method so can be used multiple times
    allClues=shopDisplay.getAllClues();
    return allClues;
  }//end allClues
  public static int coins;
  public static int cost;
  public static int[] playerClues=new int[43];//positions for player's clues, used clues as 1, not used ones as 0
  
  
  MainBackground subMainPan = new MainBackground();//insert a background image
  JButton rule = new JButton("RULE");//these two buttons are the main menu
  JButton start = new JButton("START");
  JButton backButton=new JButton("BACK");//the subpanels'buttons, appear when RULE button is clicked
  JButton gameButton = new JButton("CROSSY");
  JButton bagButton = new JButton("BAG");
  JButton backgroundInfo = new JButton("BACKGROUND INFO");
  JButton shopButton = new JButton("SHOP");
  JButton voteButton = new JButton("VOTE");
  JButton saveButton = new JButton("SAVE");
  JButton resetButton = new JButton("RESET");
  JButton exitButton = new JButton("EXIT");
  
  public MainGameBoard(){//for the GUI display part
    setLayout(new FlowLayout());
    setTitle("Trace Evidence");
    setSize(1000,700);
    setVisible(true);
    
    //set up the mainpan
    int buttonWidth=150;
    int buttonHeight=40; 
    mainPan.setLayout(null);
    titleDisplay.setFont(new Font("Comic Sans MS", Font.PLAIN, 30));//set font and size for the title on main menu
    titleDisplay.setForeground(Color.WHITE);
    titleDisplay.setBounds(400,100,300,buttonHeight);
    mainPan.add(titleDisplay);
    mainPan.setPreferredSize(new Dimension(1000,700));
    rule.setBounds(50,300,buttonWidth,buttonHeight);
    mainPan.add(rule);
    start.setBounds(50,400,buttonWidth,buttonHeight);
    mainPan.add(start);//add the two buttons in the main menu
    rule.addActionListener(this);//add actionlistener for sensitive mouse click to get to the next step
    start.addActionListener(this);
    mainPan.setVisible(true);
    add(mainPan);//add main menu panel to frame
    
    //set up the subMainPan
    subMainPan.setLayout(null);
    subMainPan.setPreferredSize(new Dimension(1000,700));
    backButton.setBounds(50,30,buttonWidth,buttonHeight);
    subMainPan.add(backButton);
    backButton.addActionListener(this);
    gameButton.setBounds(50,90,buttonWidth,buttonHeight);
    subMainPan.add(gameButton);
    gameButton.addActionListener(this);
    backgroundInfo.setBounds(50,150,buttonWidth,buttonHeight);
    subMainPan.add(backgroundInfo);
    backgroundInfo.addActionListener(this);
    bagButton.setBounds(50,210,buttonWidth,buttonHeight);
    subMainPan.add(bagButton);
    bagButton.addActionListener(this);
    shopButton.setBounds(50,270,buttonWidth,buttonHeight);
    subMainPan.add(shopButton);
    shopButton.addActionListener(this);
    voteButton.setBounds(50,330,buttonWidth,buttonHeight);
    subMainPan.add(voteButton);
    voteButton.addActionListener(this);
    saveButton.setBounds(50,390,buttonWidth,buttonHeight);
    subMainPan.add(saveButton);
    saveButton.addActionListener(this);
    resetButton.setBounds(50,450,buttonWidth,buttonHeight);
    subMainPan.add(resetButton);
    resetButton.addActionListener(this);
    exitButton.setBounds(50,510,buttonWidth,buttonHeight);
    subMainPan.add(exitButton);
    exitButton.addActionListener(this);
    subMainPan.setVisible(false);
    add(subMainPan);
  }//end MainGameBoard    
  
  class MainBackground extends JPanel{ //for adding a background, in its class
    Image img;
    public MainBackground(){
      try{
        img = ImageIO.read(new File("Castle.jpg"));
      }catch(Exception e) {  };
    }//end constructor
    public void paintComponent(Graphics g) {        
      g.drawImage(img,0,0,1000,700,null);
    }//end paintComponent
  }//end MainBackground Class
  
  public static int []getPlayerClues(){
    return playerClues;
  }//end getPlayerClues
  
  public void actionPerformed(ActionEvent event) {//for actions after clicking the buttons above
    if(event.getSource()==rule){
      new rulePan();
    }//end rule
    
    if(event.getSource()==start){ 
      mainPan.setVisible(false);
      subMainPan.setVisible(true);
      try{//at the same time, read this txt file in case the user is resuming the game
        File dataFile=new File ("PlayerClues.txt");//define the file
        Scanner readFile=new Scanner (dataFile);//read the file
        for (int index=0; index<playerClues.length; index++){
          playerClues[index]=readFile.nextInt();//print all clues' index, 0 or 1, 0 is not assigned, 1 is already assigned in txt
        }//end for
        readFile.close();//close the file immeadiately after reading
      }//end try
      catch (IOException e) {//catch IOException
        e.printStackTrace();
      }//end catch
    }//end start
    
    if(event.getSource()==backButton){     
      mainPan.setVisible(true);
      subMainPan.setVisible(false);
    }//end backButton
    
    if(event.getSource()==gameButton){
      new CrossyGameBoard();
    }//end gameButton
    
    if(event.getSource()==backgroundInfo){
      new backgroundInfoPan();
    }//end backgroundInfo
    
    if(event.getSource()==bagButton){
      playerClues= shopDisplay.getPlayerClues();//get the playerClues' array from shopDisplay file(the ones users already bought)
      System.out.println("Evidence:");
      for(int i = 0;i<playerClues.length;i++){//
        if(playerClues[i]==1){//if the position is 1, which means the actual string for this pos is used
          System.out.println(allClues[i]);//print it in console
        }//end if
      }//end for
      System.out.println();      
    }//end bagButton
    
    if(event.getSource()==shopButton){//call shopdisplay frame if the user clicked that option
      new shopDisplay();
    }//end shop
    
    if(event.getSource()==voteButton){ //call voteDisplay class
      new voteDisplay();
    }//end voteButton
    
    if(event.getSource()==saveButton){
      JOptionPane.showMessageDialog(subMainPan,"Your progress has been saved");//display the saved successfully message
      try {
        playerClues = shopDisplay.getPlayerClues();
        PrintWriter printClues = new PrintWriter("PlayerClues.txt");    
        for (int i=0; i<playerClues.length; i++) {//print all elements from this array, differentiate between 0 and 1
          printClues.println(playerClues[i]);
        }//end for
        printClues.close();
      } catch (IOException e){
        e.printStackTrace();
      }//end catch
    }//end resetButton
    
    if (event.getSource()==resetButton){
      try{
        PrintWriter printClues = new PrintWriter("PlayerClues.txt");
        for(int j = 0;  j<playerClues.length;j++){
          printClues.println(0);
        }//end for
        printClues.close();
      }catch (IOException e){
        e.printStackTrace();
      }//end catch
      mainPan.setVisible(true);
      subMainPan.setVisible(false);        
    }//end resetButton
    
    if(event.getSource()==exitButton){
      System.exit(0);
    }//end exitButton
  }//end actionPerformed
  
  public static void main(String[]args) throws IOException{
    playerClues = shopDisplay.getPlayerClues();//declare playerClues(used constantly in this file)
    allClues=allClues();
    new MainGameBoard();
  }//end main
}//end MainGameBoard

