package Screens;

import javax.swing.*;

import Data.QueryAdaptor;
import Data.UpdateAdaptor;
import Screens.EmployeeScreens.EmployeeHomePage;
import Screens.MemberScreens.PersonalInfoScreen;

import java.awt.event.*;
public class RegistrationScreen extends JPanel{
	private final String[] fields= {"Georgia Tech student/faculty","GTCR employee"};
	private JLabel usn,pwd,confirmpwd,usertype;
	private JTextField username;
	private JPasswordField password,passwordconf;
	private JComboBox<String> type;
	private JButton cancel, register;
	public RegistrationScreen(JFrame frame){
		//instantiates layout
		GroupLayout layout = new GroupLayout(this);
	    setLayout(layout);
	    layout.setAutoCreateGaps(true);
	    layout.setAutoCreateContainerGaps(true);
	    
	    /***************************labels**************************/
	    usn = new JLabel("Username: ");
	    pwd = new JLabel("Password: ");
	    confirmpwd = new JLabel("Confirm Password: ");
	    usertype = new JLabel("Type of User: ");
	    
	    /**********************Text Fields**************************/
	    username = new JTextField(30);
	    password = new JPasswordField(30);
	    passwordconf = new JPasswordField(30);
	    
	    /**********************Combobox*****************************/
	    type = new JComboBox<String>(fields);
	    
	    /***********************JButton*****************************/
	    register = new JButton("Register");
	    register.addActionListener(new ButtonListener(frame));
	    cancel = new JButton("Cancel");
	    cancel.addActionListener(new Cancel(frame));
	    
	  //layout declaration
		GroupLayout.SequentialGroup leftToRight = layout.createSequentialGroup();
	    GroupLayout.ParallelGroup col1 = layout.createParallelGroup();
	    col1.addComponent(usn);
	    col1.addComponent(pwd);
	    col1.addComponent(confirmpwd);
	    col1.addComponent(usertype);
	    GroupLayout.ParallelGroup col2 = layout.createParallelGroup();
	    col2.addComponent(username);
	    col2.addComponent(password);
	    col2.addComponent(passwordconf);
	    col2.addComponent(type);
	    GroupLayout.SequentialGroup mid = layout.createSequentialGroup();
	    mid.addComponent(register);
	    mid.addComponent(cancel);
	    col2.addGroup(mid);
	    leftToRight.addGroup(col1);
	    leftToRight.addGroup(col2);
	
	    GroupLayout.SequentialGroup topToBottom = layout.createSequentialGroup();
	    GroupLayout.ParallelGroup row1 = layout.createParallelGroup();
	    row1.addComponent(usn);
	    row1.addComponent(username);
	    GroupLayout.ParallelGroup row2 = layout.createParallelGroup();
	    row2.addComponent(pwd);
	    row2.addComponent(password);
	    GroupLayout.ParallelGroup row3 = layout.createParallelGroup();
	    row3.addComponent(confirmpwd);
	    row3.addComponent(passwordconf);
	    GroupLayout.ParallelGroup row4 = layout.createParallelGroup();
	    row4.addComponent(usertype);
	    row4.addComponent(type);
	    GroupLayout.ParallelGroup row5 = layout.createParallelGroup();
	    row5.addComponent(register);
	    row5.addComponent(cancel);
	    topToBottom.addGroup(row1);
	    topToBottom.addGroup(row2);
	    topToBottom.addGroup(row3);
	    topToBottom.addGroup(row4);
	    topToBottom.addGroup(row5);
	    
	    layout.setHorizontalGroup(leftToRight);
	    layout.setVerticalGroup(topToBottom);
	    
	}
	private class Cancel implements ActionListener{
		JFrame frame;
		public Cancel(JFrame frame){
			this.frame = frame;
		}
		@Override
		public void actionPerformed(ActionEvent e){
			changeScreen(new LoginScreen(frame),frame);
		}
	}
	private class ButtonListener implements ActionListener{
		JFrame frame;
		//TODO better validations
		public ButtonListener(JFrame frame){
			this.frame = frame;
		}
		@Override
		public void actionPerformed(ActionEvent e){
			boolean flag;
			if(password.getText().equals(passwordconf.getText())) flag = true;
			else{
				flag = false;
				JOptionPane.showMessageDialog(frame,"Password does not match");
			} 
			if(flag&&(username.getText().length()==0||password.getText().length()==0)){
				JOptionPane.showMessageDialog(frame,"Please do not leave any fields empty");
				flag = false;
			} 
			if(flag) QueryAdaptor.connect();
			try {
				if(flag&&QueryAdaptor.findUser(username.getText())){
					JOptionPane.showMessageDialog(frame,"Username already exist");
					flag = false;
					QueryAdaptor.close();
				}
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			if(flag){
				UpdateAdaptor.connect();
				try {
					if(type.getSelectedIndex()==0){
						UpdateAdaptor.makeUser(username.getText(), password.getText(), 0, 0, 1);
						changeScreen(new PersonalInfoScreen(frame,username.getText(),true),frame);
					}
					else {
						UpdateAdaptor.makeUser(username.getText(), password.getText(), 1, 0, 0);
						changeScreen(new EmployeeHomePage(frame,username.getText()),frame);
					}
				} catch (Exception e1) {
					e1.printStackTrace();
				} finally{
					UpdateAdaptor.close();
				} 
			}
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
