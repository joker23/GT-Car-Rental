package Screens.EmployeeScreens;
import javax.swing.*;

import Screens.LoginScreen;


import java.awt.event.*;

public class EmployeeHomePage extends JPanel{
	private static final String[] reports = {"Location Preference Report","Frequent User Report","Maintenance History Report"};
	private JRadioButton manageCar,maintReq,rentalCh,viewReport;
	private JComboBox loc;
	private JButton next,logout;
	private int choiceID;
	private String usn;
	private ButtonGroup choices;
	public EmployeeHomePage(JFrame frame,String usn){
		this.usn = usn;
		//instantiates layout
		GroupLayout layout = new GroupLayout(this);
	    setLayout(layout);
	    layout.setAutoCreateGaps(true);
	    layout.setAutoCreateContainerGaps(true);
	    
	    /****************************Radio Buttons**********************/
	    QuoteListener choice = new QuoteListener();
	    manageCar = new JRadioButton("Manage Cars");
	    manageCar.addActionListener(choice);
	    maintReq = new JRadioButton("Maintenance Requests");
	    maintReq.addActionListener(choice);
	    rentalCh = new JRadioButton("Rental Change Request");
	    rentalCh.addActionListener(choice);
	    viewReport = new JRadioButton("View Reports");
	    viewReport.addActionListener(choice);
	    
	    choices = new ButtonGroup();
	    choices.add(manageCar);
	    choices.add(maintReq);
	    choices.add(rentalCh);
	    choices.add(viewReport);
	    
	    /**************************combobox****************************/
	    loc = new JComboBox(reports);
	    
	    /**************************button*****************************/
	    next = new JButton("Next");
	    next.addActionListener(new ButtonListener(frame,this));
	    
	    logout = new JButton("Logout");
	    logout.addActionListener(new LogoutListener(frame));
	    
	    /********************layout declaration***********************/
		GroupLayout.SequentialGroup leftToRight = layout.createSequentialGroup();
	    GroupLayout.ParallelGroup col1= layout.createParallelGroup();
	    col1.addComponent(manageCar);
	    col1.addComponent(maintReq);
	    col1.addComponent(rentalCh);
	    col1.addComponent(viewReport);
	    col1.addComponent(logout);
	    GroupLayout.ParallelGroup col2= layout.createParallelGroup();
	    col2.addComponent(loc);
	    col2.addComponent(next);
	    leftToRight.addGroup(col1);
	    leftToRight.addGroup(col2);
	
	    GroupLayout.SequentialGroup topToBottom = layout.createSequentialGroup();
	    topToBottom.addComponent(manageCar);
	    topToBottom.addComponent(maintReq);
	    topToBottom.addComponent(rentalCh);
	    GroupLayout.ParallelGroup row1= layout.createParallelGroup();
	    row1.addComponent(viewReport);
	    row1.addComponent(loc);
	    GroupLayout.ParallelGroup row2= layout.createParallelGroup();
	    row2.addComponent(logout);
	    row2.addComponent(next);
	    topToBottom.addGroup(row1);
	    topToBottom.addGroup(row2);
	   
	    
	
	    layout.setHorizontalGroup(leftToRight);
	    layout.setVerticalGroup(topToBottom);
	}
	/*
	 * quote listener for the radio buttons
	 * choiceID:
	 * 	1) manage car
	 *  2) Maintenance Request
	 *  3) rental change
	 *  4) view report
	 */
	private class QuoteListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			Object source = e.getSource();
			if (source == manageCar) choiceID = 1;
			else if (source == maintReq) choiceID = 2;
			else if (source == rentalCh) choiceID =3;
			else choiceID = 4;
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
	
	/*
	 * listener for the next button
	 */
	private class ButtonListener implements ActionListener{
		JFrame frame;
		JPanel panel;
		public ButtonListener(JFrame frame,JPanel panel){
			this.frame = frame;
			this.panel = panel;
		}
		@Override
		public void actionPerformed(ActionEvent e){
			switch (choiceID){
			case 1: 
				try {
					changeScreen(new ManageCarScreen(frame,panel,usn),frame);
				} catch (Exception e2) {
					e2.printStackTrace();
				}
				break;
			case 2:
				try {
					changeScreen(new MaintenanceRequestScreen(frame,panel,usn),frame);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				break;
			case 3:
				changeScreen(new RentalChangeScreen(frame,panel),frame);
				break;
			default: 
				int selected = loc.getSelectedIndex();
				if(selected == 0){
					changeScreen(new LocationPreferenceReportPanel(frame,panel),frame);
				} else if(selected == 1){
					changeScreen(new FrequentUsersReportPanel(frame,panel),frame);
				} else{
					changeScreen(new MaintenanceHistoryReportPanel(frame,panel),frame);
				}
			}
		}
		
		public String getUSN(){
			return usn;
		}
	}
	
	private static void changeScreen(JPanel newScreen,JFrame frame){
		frame.getContentPane().removeAll();
		frame.getContentPane().add(newScreen);
		frame.pack();
		frame.setVisible(true);
	}
}