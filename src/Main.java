import javax.swing.*;

import Screens.*;


/**
 * main driver class 
 * instantiates the gui
 * makes all of the the login screen
 * 
 * TODO we need to make this thing look nice...
 * @author steven
 *
 */
public class Main extends JFrame{
	public static void main(String[] args){
		 JFrame frame = new JFrame("GT Car Rental");
		 frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		 frame.setResizable(false);
		 
		 LoginScreen panel = new LoginScreen(frame); //passing in frame so we can switch panels
		    
		 frame.getContentPane().add(panel);
		 frame.pack();
		 frame.setVisible(true);
	}
}
