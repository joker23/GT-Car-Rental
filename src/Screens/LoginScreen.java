package Screens;

import java.awt.Dimension;
import java.awt.event.*;

import javax.swing.*;

import Data.QueryAdaptor;
import Screens.EmployeeScreens.EmployeeHomePage;
import Screens.MemberScreens.HomePageScreen;
/**
 * displays login screen
 * this is where username and passwords are authenticated
 * 
 * @author steven
 *
 */

public class LoginScreen extends JPanel{
	private JTextField inputUN;
	private JPasswordField inputPW;
	private JLabel un,pw;
	private JButton loginButton,registration;
	private final int HEIGHT = 250,WIDTH = 500;
	
	/**
	 * 
	 * @param frame : let us access the frame so that we can 
	 * 				  switch panels
	 * 
	 * TODO make things look better!!
	 */
	public LoginScreen(JFrame frame){
		
		//setPreferredSize(new Dimension(WIDTH,HEIGHT));
		
		//instantiates layout
		GroupLayout layout = new GroupLayout(this);
	    setLayout(layout);
	    layout.setAutoCreateGaps(true);
	    layout.setAutoCreateContainerGaps(true);
	   
	    
		un = new JLabel("Username :");
		pw = new JLabel("Password: ");
		
		loginButton = new JButton("Login");
		loginButton.addActionListener(new ButtonListener(frame));
		registration = new JButton("Register");
		registration.addActionListener(new RegisterUser(frame));
		
		inputUN = new JTextField(100);
		inputUN.setMaximumSize(new Dimension(150,20));
		inputPW = new JPasswordField(100);
		inputPW.setMaximumSize(new Dimension(150,20));
		
		//layout declaration
		GroupLayout.SequentialGroup leftToRight = layout.createSequentialGroup();

	    GroupLayout.ParallelGroup columnOne = layout.createParallelGroup();
	    columnOne.addComponent(un);
	    columnOne.addComponent(pw);
	    columnOne.addComponent(registration);
	    GroupLayout.ParallelGroup columnTwo = layout.createParallelGroup();
	    columnTwo.addComponent(inputUN);
	    columnTwo.addComponent(inputPW);
	    columnTwo.addComponent(loginButton);
	    leftToRight.addGroup(columnOne);
	    leftToRight.addGroup(columnTwo);

	    GroupLayout.SequentialGroup topToBottom = layout.createSequentialGroup();
	    GroupLayout.ParallelGroup rowTop = layout.createParallelGroup(GroupLayout.Alignment.CENTER);
	    rowTop.addComponent(un);
	    rowTop.addComponent(inputUN);
	    GroupLayout.ParallelGroup rowMid = layout.createParallelGroup(GroupLayout.Alignment.CENTER);
	    rowMid.addComponent(pw);
	    rowMid.addComponent(inputPW);
	    topToBottom.addGroup(rowTop);
	    topToBottom.addGroup(rowMid);
	    GroupLayout.ParallelGroup rowLast = layout.createParallelGroup(GroupLayout.Alignment.CENTER);
	    rowLast.addComponent(loginButton);
	    rowLast.addComponent(registration);
	    topToBottom.addGroup(rowLast);

	    layout.setHorizontalGroup(leftToRight);
	    layout.setVerticalGroup(topToBottom);
	}
	
	
	private class ButtonListener implements ActionListener{
		JFrame frame;
		public ButtonListener(JFrame frame){
			this.frame = frame;
		}
		@SuppressWarnings("deprecation")
		@Override
		public void actionPerformed(ActionEvent e) {
			try {
				QueryAdaptor.connect();
				if(QueryAdaptor.findUser(inputUN.getText())){
					if(QueryAdaptor.matchPassword(inputUN.getText(), inputPW.getText())){
						switch (QueryAdaptor.getMemberType(inputUN.getText())){
							case '1': changeScreen(new EmployeeHomePage(frame,inputUN.getText()),frame);	//Employee
									break;
							case '2': changeScreen(new AdminHomePage(frame),frame);		//Admin
									break;
							default: changeScreen(new HomePageScreen(frame,inputUN.getText()),frame); 	//member
						}
					}else
						JOptionPane.showMessageDialog(frame,"Username/Password mismatch");
				} else
					JOptionPane.showMessageDialog(frame,"User Not Found!");
			} catch (Exception e1) {
				e1.printStackTrace();
			} QueryAdaptor.close();
		}
	}
	private class RegisterUser implements ActionListener{
		private JFrame frame;
		public RegisterUser(JFrame frame){
			this.frame = frame;
		}
		@Override
		public void actionPerformed(ActionEvent e){
			changeScreen(new RegistrationScreen(frame),frame);
		}
		
	}
	/**
	 * change screen method
	 * @param newScreen
	 * @param frame
	 */
	private static void changeScreen(JPanel newScreen,JFrame frame){
		frame.getContentPane().removeAll();
		frame.getContentPane().add(newScreen);
		frame.pack();
		frame.setVisible(true);
	}
}
