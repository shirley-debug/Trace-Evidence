/**
 * voteDisplay.java
 * Created by Jane Sun
 * Edited by Shirley Lin
 * Created on June 4, 2019
 * This program is called by the main game board, providing a panel for the user to make the final decision on who the murderers are, also displays the analysis after user answers
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import javax.imageio.*; 

public class voteDisplay extends JFrame implements ActionListener{//added actionlistener for user to click the right button(choice)
  JButton clayton = new JButton("Clayton");//create buttons for all the choices for both questions(characters' names)
  JButton clayton2 = new JButton("Clayton");
  JButton giovanni = new JButton("Giovanni");
  JButton giovanni2 = new JButton("Giovanni");
  JButton gabriel = new JButton("Gabriel");
  JButton gabriel2 = new JButton("Suicide");
  JButton luna = new JButton("Luna");
  JButton luna2 = new JButton("Luna");
  JButton anastasia= new JButton("Anastasia");
  JButton anastasia2= new JButton("Anastasia");
  JButton maverick = new JButton("Suicide");
  
  voteBackground vote1 = new voteBackground();//add backgrounds for both questions panels
  voteBackground vote2 =new voteBackground();
  JLabel question1 = new JLabel("Who killed Marverick?");//write out the questions
  JLabel question2 = new JLabel("Who killed Gabriel?");
  
  
  public voteDisplay(){
    setLayout(new FlowLayout());//set it to flow layout
    setTitle("VOTE");
    setSize(1000,700);
    setResizable(false);
    setVisible(true);
    
    // call the Function TextFromFile
    setSize(1000,700);
    setVisible(true);
    
    
    vote1.setPreferredSize(new Dimension(1000,700));
    vote1.setLayout(null);
    question1.setBounds(350,50,300,40);//set location of the question display
    question1.setFont(new Font("SansSerif Bold Italic", Font.PLAIN, 30));//set font
    question1.setForeground(Color.WHITE);//color
    vote1.add(question1);//add it to panel
    clayton.setBounds(450,120,100,40);
    vote1.add(clayton);//add buttons for the first questions
    clayton.addActionListener(this);//add actionlistener for all buttons
    giovanni.setBounds(450,190,100,40);
    vote1.add(giovanni);
    giovanni.addActionListener(this);
    luna.setBounds(450,260,100,40);
    vote1.add(luna);
    luna.addActionListener(this);
    anastasia.setBounds(450,330,100,40);
    vote1.add(anastasia);
    anastasia.addActionListener(this);
    maverick.setBounds(450,400,100,40);
    vote1.add(maverick);//this button only appears in the first questions, as the person already died in the murder 10 yrs ago
    maverick.addActionListener(this);
    gabriel.setBounds(450,470,100,40);
    gabriel.addActionListener(this);
    vote1.add(gabriel);//only first, already died in 2009
    vote1.setVisible(true);
    add(vote1);
    //same thing with buttons options in the second question
    vote2.setPreferredSize(new Dimension(1000,700));
    vote2.setLayout(null);
    question2.setFont(new Font("SansSerif Bold Italic", Font.PLAIN, 30));
    question2.setForeground(Color.WHITE);
    question2.setBounds(350,50,300,40);
    vote2.add(question2);//add buttons and set ground for the second questions
    clayton2.setBounds(450,120,100,40);
    vote2.add(clayton2);
    clayton2.addActionListener(this);
    giovanni2.setBounds(450,190,100,40);
    vote2.add(giovanni2);
    giovanni2.addActionListener(this);
    luna2.setBounds(450,260,100,40);
    vote2.add(luna2);
    luna2.addActionListener(this);
    anastasia2.setBounds(450,330,100,40);
    vote2.add(anastasia2);
    anastasia2.addActionListener(this);
    gabriel2.setBounds(450,400,100,40);
    gabriel2.addActionListener(this);
    vote2.add(gabriel2);
    vote2.setVisible(false);
    add(vote2);
    
    
  }
  class voteBackground extends JPanel{//set a background
    Image imgVote;//initialize image before using
    public voteBackground(){//different class for background
      try{
        imgVote = ImageIO.read(new File("vote.jpg"));
      }catch(Exception e) {  };//catch exception
    }
    public void paintComponent(Graphics g) 
    {        
      g.drawImage(imgVote,0,0,1000,700,null);
    }
  }
  
  public void actionPerformed(ActionEvent event) {//for telling user if their selections are correct and displaying the final analysis
    if(event.getSource()==maverick){//if they got first question right
      JOptionPane.showMessageDialog(vote1,"Congratulations! You are right");
      vote1.setVisible(false);
      vote2.setVisible(true);
    } else if(event.getSource()==luna||event.getSource()==anastasia||event.getSource()==giovanni||event.getSource()==clayton||event.getSource()==gabriel){//otherwise
      JOptionPane.showMessageDialog(vote1, "Ops! Killer escapes.");
      vote1.setVisible(false);
      vote2.setVisible(true);
    }
    if(event.getSource()==giovanni2){//if they got second write
      JOptionPane.showMessageDialog(vote2, "Congratulations! You are right");
      setVisible(false);
      new truthPan();//call out text pane for final analysis
    }else if (event.getSource()==luna2||event.getSource()==anastasia2 || event.getSource()==clayton2||event.getSource()==gabriel2){//otherwise
      JOptionPane.showMessageDialog(vote2, "Ops! Killer escapes.");
      setVisible(false);
      new truthPan();
    }
  }
}

class truthPan extends JFrame{
  
  public static int[] playerClues=new int[42];
  public truthPan(){
    
    setTitle("ANALYSIS");
    
    JTextPane truth = new JTextPane();
    
    // call the Function TextFromFile
    TextFromFile(truth);
    
    Font font = new Font("Georgia", Font.BOLD, 20);
    truth.setFont(font);
    truth.setForeground(Color.WHITE);
    truth.setBackground(Color.BLACK);
    JScrollPane jp = new JScrollPane(truth);
    
    setLocationRelativeTo(null);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setSize(1000,700);
    setVisible(true);
    add(jp);
    
  }
  
  /*
   create a function to get the text from a text file 
   and set it into a JTextPane
   */ 
  public static void TextFromFile(JTextPane tp)
  {
    try{//try this first
      File file = new File("Truth.txt");//declare where to put the texts
      FileReader fr = new FileReader(file);
      while(fr.read() != -1){//when there is no lines to read, stop
        tp.read(fr,null);
      }
      fr.close();
    } catch(Exception ex){//catch exception 
      ex.printStackTrace();
    }
  }
}