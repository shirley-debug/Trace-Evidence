/**
 * rulePan.java
 * Created by Jane Sun
 * Created on June 2, 2019
 * This program is to display the rules for the game "Trace Evidence" using a textpane
 */

import java.awt.Color;
import java.awt.Font;
import java.io.*;
import javax.swing.*;

public class rulePan extends JFrame{
  
  public rulePan(){//start GUI display
    
    setTitle("RULES");
    
    JTextPane tp = new JTextPane();//create a text panel
    
    TextFromFile(tp);// call the Function TextFromFile
    
    Font font = new Font("Georgia", Font.BOLD, 20);//set font and size of the texts
    tp.setFont(font);
    tp.setForeground(Color.WHITE);
    tp.setBackground(Color.BLACK);
    JScrollPane jp = new JScrollPane(tp);//create a scroll bar
    setLocationRelativeTo(null);
    setSize(1000,700);
    setVisible(true);
    add(jp);//add the scroll pane
    
  }
  
  /*
   create a function to get the text from a text file 
   and set it into a JTextPane
   */ 
  public static void TextFromFile(JTextPane tp)
  {
    try{
      File file = new File("rules.txt");//indicate where to find the data
      FileReader fr = new FileReader(file);//create a reader
      while(fr.read() != -1){//while it still has texts next
        tp.read(fr,null);//textpane gets it
      }
      fr.close();
    } catch(Exception ex){
      ex.printStackTrace();//for diagnosing exceptions
    }
  }
  
}

