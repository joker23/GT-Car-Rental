package Screens.EmployeeScreens;

import javax.swing.*;

import Data.*;

import java.awt.event.*;
import java.util.*;
public class MaintenanceRequestScreen extends JPanel{
	private String[] loclist,carlist;
	private JTextArea problemDesc; 
	private JScrollPane scrollbar; 
	private JComboBox<String> loc,car;
	private JLabel date,locl,carl,probl;
	private JButton submit,back;
	private JPanel panel;
	private String usn;
	public MaintenanceRequestScreen(JFrame frame,JPanel panel,String usn) throws Exception{
		this.panel = panel;
		this.usn = usn;
		//instantiates layout
		GroupLayout layout = new GroupLayout(this);
	    setLayout(layout);
	    layout.setAutoCreateGaps(true);
	    layout.setAutoCreateContainerGaps(true);
	    
	    QueryAdaptor.connect();
	    loclist = QueryAdaptor.getLocationName();
	    carlist = QueryAdaptor.getCarList();
	    QueryAdaptor.close();
	    /**************************button*****************************/
	    submit = new JButton("Submit Request");
	    submit.addActionListener(new ButtonListener());
	    back = new JButton("Back");
	    back.addActionListener(new BackListener(frame));
	    /***************************textArea and scroll***************/
	    problemDesc = new JTextArea("", 5, 50);
	    problemDesc.setLineWrap(true);
	    scrollbar = new JScrollPane(problemDesc);
	    scrollbar.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
	
	    /******************************comboboxes**********************/
	    loc = new JComboBox<String>(loclist);
	    loc.addActionListener(new LocationListener());
	    car = new JComboBox<String>(carlist);
	    
	    /****************************JLabel****************************/
	    Date d = new Date();
	    date = new JLabel(d.toString());
	    locl = new JLabel("Choose Locaton: ");
	    carl = new JLabel("Choose Car: ");
	    probl = new JLabel("Brief Description of Problem: ");
	    
	    /****************************layout***************************/
	    GroupLayout.SequentialGroup leftToRight = layout.createSequentialGroup();
	    GroupLayout.ParallelGroup col1 = layout.createParallelGroup();
	    col1.addComponent(locl);
	    col1.addComponent(carl);
	    col1.addComponent(probl);
	    col1.addComponent(back);
	    GroupLayout.ParallelGroup col2 = layout.createParallelGroup();
	    col2.addComponent(loc);
	    col2.addComponent(car);
	    col2.addComponent(scrollbar);
	    GroupLayout.ParallelGroup col3 = layout.createParallelGroup();
	    col3.addComponent(date);
	    col3.addComponent(submit);
	    leftToRight.addGroup(col1);
	    leftToRight.addGroup(col2);
	    leftToRight.addGroup(col3);
	    
	    GroupLayout.SequentialGroup topToBottom = layout.createSequentialGroup();
	    topToBottom.addComponent(date);
	    GroupLayout.ParallelGroup row1 = layout.createParallelGroup();
	    row1.addComponent(locl);
	    row1.addComponent(loc);
	    topToBottom.addGroup(row1);
	    GroupLayout.ParallelGroup row2 = layout.createParallelGroup();
	    row2.addComponent(carl);
	    row2.addComponent(car);
	    topToBottom.addGroup(row2);
	    GroupLayout.ParallelGroup row3 = layout.createParallelGroup();
	    row3.addComponent(probl);
	    row3.addComponent(scrollbar);
	    topToBottom.addGroup(row3);
	    topToBottom.addComponent(submit);
	    topToBottom.addComponent(back);
	    
	    layout.setHorizontalGroup(leftToRight);
	    layout.setVerticalGroup(topToBottom);
	}
	
	private class LocationListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e){
			JComboBox cb = (JComboBox)e.getSource();
	        String selected = (String)cb.getSelectedItem();
	        QueryAdaptor.connect();
	        try {
				carlist = QueryAdaptor.getCarList(selected);
			} catch (Exception e1) {
				e1.printStackTrace();
			} QueryAdaptor.close();
			car.removeAllItems();
			for(int i=0;i<carlist.length;i++){
				car.addItem(carlist[i]);
			}
		}	
	}
	
	private class BackListener implements ActionListener{
		JFrame frame;
		public BackListener(JFrame frame){
			this.frame = frame;
		}
		@Override
		public void actionPerformed(ActionEvent e){
			changeScreen(panel,frame);
		}
	}
	
	private class ButtonListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e){
			QueryAdaptor.connect();
			UpdateAdaptor.connect();
			try {
				String currvsn = QueryAdaptor.findVsn((String)loc.getSelectedItem(), (String)car.getSelectedItem());
				String dateTime = Format.parseDateTime(new Date());
				UpdateAdaptor.addMaintenanceRequest(currvsn,dateTime,usn);
				UpdateAdaptor.makeProblem(currvsn, dateTime, problemDesc.getText());
				UpdateAdaptor.setMaintenanceFlag(currvsn);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			QueryAdaptor.close();
			UpdateAdaptor.close();
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