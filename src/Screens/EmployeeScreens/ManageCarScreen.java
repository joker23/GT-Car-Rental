package Screens.EmployeeScreens;

import java.awt.*;
import javax.swing.*;

import Screens.MemberScreens.HomePageScreen;
import java.awt.event.*;
public class ManageCarScreen extends JPanel{
	JPanel panel;
	JButton back;
	String usn;
	public ManageCarScreen(JFrame frame,JPanel panel,String usn) throws Exception{
		this.panel = panel;
		setLayout(new BorderLayout());
		JTabbedPane tabbedPane = new JTabbedPane();
		tabbedPane.addTab("Add Car",new AddCarPanel(frame));
		tabbedPane.addTab("Change Car Location",new ChangeCarLocationPanel(frame));
		
		back = new JButton("Back");
		back.addActionListener(new BackListener(frame));
		
		add(tabbedPane,BorderLayout.CENTER);
		add(back,BorderLayout.SOUTH);
	}
	
	private class ButtonListener implements ActionListener{
		JFrame frame;
		JPanel panel;
		public ButtonListener(JFrame frame, JPanel panel){
			this.frame = frame;
			this.panel = panel;
		}
		@Override
		public void actionPerformed(ActionEvent e){
			changeScreen(panel,frame);
		}
	}
	private static void changeScreen(JPanel newScreen,JFrame frame){
		frame.getContentPane().removeAll();
		frame.getContentPane().add(newScreen);
		frame.pack();
		frame.setVisible(true);
	}
	
	private class BackListener implements ActionListener{
		JFrame frame;
		public BackListener(JFrame frame){
			this.frame = frame;
		}
		@Override
		public void actionPerformed(ActionEvent e){
			changeScreen(new EmployeeHomePage(frame,usn),frame);
		}
	}
}