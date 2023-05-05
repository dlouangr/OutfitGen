/**
 * OutfitGen - Created by Dominique Louangrath for ITCS 3112
 * Working prototype for the program I've been designing over the course of the semester.
 * 
 * UI provided by FlatLaf: https://github.com/JFormDesigner/FlatLaf
 */

 import javax.swing.UIManager;
 import com.formdev.flatlaf.intellijthemes.FlatDarkPurpleIJTheme;
 
 public class App {
     public static void main(String[] args) {
         try {
             UIManager.setLookAndFeel(new FlatDarkPurpleIJTheme());
         } catch( Exception ex ) {
             System.err.println( "Failed to initialize LaF" );
         }
         new Main();
     }
 }
 