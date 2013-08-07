package Debug;

import javax.swing.JFrame;
import javax.swing.JPanel;

import Screens.EmployeeScreens.EmployeeHomePage;
import Screens.EmployeeScreens.RentalChangeScreen;
import Screens.MemberScreens.RentCarScreen;

/**
 * This will let us test specific panels 
 * USE: 
 * 	there is an empty JPanel class...just copy and paste the code on 
 * 	it and import necessary components then run this code
 * @author steven
 *
 */

public class GuiDebug extends JFrame {
	public static void main(String[] args) throws Exception{
		 JFrame frame = new JFrame("TEST");
		 frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		 frame.setResizable(false);
		 
		 JPanel panel1 = new EmployeeHomePage(frame,"employ1");
		 JPanel panel = new RentalChangeScreen(frame,panel1);
		 
		 frame.getContentPane().add(panel);
		 frame.pack();
		 frame.setVisible(true);
	}	
}
