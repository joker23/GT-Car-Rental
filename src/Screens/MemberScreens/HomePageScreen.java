package Screens.MemberScreens;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import Data.QueryAdaptor;
import Screens.LoginScreen;


/**
 * Member homepage screen
 * we should be able to choose from where we want to go 
 * from here
 * there are three options:
 * 1) rent a car: goes to the rent a car screen
 * 2) personal info: if the user wants to edit or see his/her personal info
 * 3) rentInfo:...yea
 * 
 * @author steven
 *
 */
public class HomePageScreen extends JPanel{
	private JRadioButton rentCar,personalInfo,rentInfo;
	private JButton go,logout;
	private ButtonGroup choices;
	private int chosen;
	
	/**
	 * 
	 * @param frame
	 */
	public HomePageScreen(JFrame frame,String usn){
		//instantiates layout
		GroupLayout layout = new GroupLayout(this);
		this.setLayout(layout);
		layout.setAutoCreateGaps(true);
		layout.setAutoCreateContainerGaps(true);
		
		QuoteListener choice = new QuoteListener();
		rentCar = new JRadioButton("Rent a Car",false);
		rentCar.addActionListener(choice);
		personalInfo = new JRadioButton("Enter/View Personal Information",false);
		personalInfo.addActionListener(choice);
		rentInfo = new JRadioButton("View Rental Information",false);
		rentInfo.addActionListener(choice);
		
		go = new JButton("Next");
		go.addActionListener(new ButtonListener(frame,usn));
		
		logout = new JButton("Logout");
	    logout.addActionListener(new LogoutListener(frame));
		
		choices = new ButtonGroup();
		choices.add(rentCar);
		choices.add(personalInfo);
		choices.add(rentInfo);
		
		//layout declaration
		GroupLayout.SequentialGroup leftToRight = layout.createSequentialGroup();
		
	    GroupLayout.ParallelGroup columnMid = layout.createParallelGroup();
	    columnMid.addComponent(rentCar);
	    columnMid.addComponent(personalInfo);
	    columnMid.addComponent(rentInfo);
	    columnMid.addComponent(logout);
	    
	    GroupLayout.ParallelGroup col2= layout.createParallelGroup();
	    col2.addComponent(go);
	    leftToRight.addGroup(columnMid);
	    leftToRight.addGroup(col2);

	    GroupLayout.SequentialGroup topToBottom = layout.createSequentialGroup();
	    topToBottom.addComponent(rentCar);
	    topToBottom.addComponent(personalInfo);
	    topToBottom.addComponent(rentInfo);
	    GroupLayout.ParallelGroup row2= layout.createParallelGroup();
	    row2.addComponent(go);
	    row2.addComponent(logout);
	   
	    topToBottom.addGroup(row2);

	    layout.setHorizontalGroup(leftToRight);
	    layout.setVerticalGroup(topToBottom);
	}
	private class QuoteListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			Object source = e.getSource();
			if (source == personalInfo) chosen = 1;
			else if (source == rentCar) chosen = 2;
			else chosen = 3;
		}
	}
	
	private class ButtonListener implements ActionListener{
		JFrame frame;
		String usn;
		public ButtonListener(JFrame frame,String usn){
			this.frame = frame;
			this.usn = usn;
		} @Override
		public void actionPerformed(ActionEvent e) {
			//do something
			if(chosen == 1){
				try {
					changeScreen(new PersonalInfoScreen(frame,usn,false),frame);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
			else if(chosen == 2){
				changeScreen(new RentCarScreen(frame,usn),frame);
			}
			else if(chosen==3){ 
				/*String[] test=null;
				QueryAdaptor.connect();
				try {
					test = QueryAdaptor.getReservationInfo(usn);
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} QueryAdaptor.close();
				if (test==null){
					JOptionPane.showMessageDialog(frame,"You have no current or past reservations at this time.");
				}
				else{*/
					changeScreen(new RentalInformationScreen(frame,usn),frame);
			
			}
			else{
				JOptionPane.showMessageDialog(frame,"Please Select an Option");
			}
		}
		
	}
	
	private class LogoutListener implements ActionListener{
		JFrame frame;
		public LogoutListener(JFrame frame){
			this.frame = frame;
		}
		@Override
		public void actionPerformed(ActionEvent e){
			JPanel panel = new LoginScreen(frame);
			changeScreen(panel,frame);
		}
	}
	
	private void changeScreen(JPanel newScreen,JFrame frame){
		frame.getContentPane().removeAll();
		frame.getContentPane().add(newScreen);
		frame.pack();
		frame.setVisible(true);
	}
	
}