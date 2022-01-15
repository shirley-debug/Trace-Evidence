/**
 * rulePan.java
 * Created by Jane Sun
 * Created on June 2, 2019
 * This program is to display the background info for the game "Trace Evidence" using a textpane
 */

import java.awt.Color;
import java.awt.Font;
import java.io.*;
import javax.swing.*;

public class backgroundInfoPan extends JFrame{
  
  public backgroundInfoPan(){
    
    super("BACKGROUND INFO");
    
    JTextPane tp = new JTextPane();//creating text panel
    
    // call the Function TextFromFile
    TextFromFile(tp);
    
    Font font = new Font("Georgia", Font.BOLD, 20);//set a font
    tp.setFont(font);
    tp.setForeground(Color.WHITE);
    tp.setBackground(Color.BLACK);
    JScrollPane jp = new JScrollPane(tp);//create a scroll bar
    setLocationRelativeTo(null);//set pop up location side from the panel, so the user knows that's seperate frames
    setSize(1000,700);
    setVisible(true);
    add(jp);//add scrollpane
  }
  
  /*
   create a function to get the text from a text file 
   and set it into a JTextPane
   */ 
  public static void TextFromFile(JTextPane tp)
  {
    //@param JTextPane tp used to display contents in the text file to a GUI panel
    try{
      File file = new File("murderbackground.txt");//tell reader the file name to look for
      FileReader fr = new FileReader(file);//create a reader
      while(fr.read() != -1){//while there is still texts next
        tp.read(fr,null);//continue reading
      }//end while
      fr.close();
    } catch(Exception ex){
      ex.printStackTrace();//for diagnosing exceptions
    }//end catch
  }//end TextFromFile
  
}//end class 
