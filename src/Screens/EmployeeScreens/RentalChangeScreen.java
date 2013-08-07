package Screens.EmployeeScreens;

import javax.swing.*;

import Data.QueryAdaptor;
import Data.UpdateAdaptor;
import Data.Util;
import Screens.MemberScreens.CarAvailabilityScreen;

import java.awt.event.*;
import java.awt.*;
public class RentalChangeScreen extends JPanel{
	private JLabel modell,locl,origretl1,origretl2,newl,origpickl,currusnl,
		affectedusnl,affectedemaill,affectedphonel,leftTitle,rightTitle;
	private JButton backButton,updateButton,cancelButton, showButton,searchButton;
	private JTextField modelt,loct,currusnt,
		affectedusnt,affectedemailt,affectedphonet;
	private String model, loc,origret1,origret2, newret, origpick,currusn,affectedusn,
		affectedemail, affectedphone;
	private JComboBox<String> retDate1,retTime1,newDate,newTime,retDate2,retTime2,pickDate,pickTime;
	private JFrame frame;
	private JPanel panel;
	public RentalChangeScreen(JFrame frame, JPanel panel){
		this.frame = frame;
		this.panel = panel;
		
		this.setLayout(new BorderLayout());
		
		JPanel rentalInfo = new JPanel();
		GroupLayout leftLayout = new GroupLayout(rentalInfo);
		rentalInfo.setLayout(leftLayout);
		leftLayout.setAutoCreateGaps(true);
	    leftLayout.setAutoCreateContainerGaps(true);
	    
		JPanel userAffected = new JPanel();
		GroupLayout rightLayout = new GroupLayout(userAffected);
		userAffected.setLayout(rightLayout);
		rightLayout.setAutoCreateGaps(true);
	    rightLayout.setAutoCreateContainerGaps(true);
		
		/*****************************Labels***************************/
		modell = new JLabel("Car Model: ");
		locl = new JLabel("Location: ");
		origretl1 = new JLabel("Original Return Time: ");
		newl = new JLabel("New Arrival Time: ");
		origretl2 = new JLabel("Original Return Time: ");
		origpickl = new JLabel("Original Pick Up Time: ");
		currusnl = new JLabel("Enter Username: ");
		affectedusnl = new JLabel("Username: ");
		affectedemaill = new JLabel("Email Address: ");
		affectedphonel = new JLabel("Phone Number: ");
		
		//titles
		leftTitle = new JLabel("Rental Information");
		leftTitle.setFont(new Font("Serif", Font.BOLD, 24));
		rightTitle = new JLabel("User Affected");
		rightTitle.setFont(new Font("Serif", Font.BOLD, 24));
		
		
		/************************text fields****************************/
		modelt = new JTextField(15);
		loct = new JTextField(15);
		currusnt = new JTextField(15);
		affectedusnt = new JTextField(15);
		affectedemailt = new JTextField(15);
		affectedphonet = new JTextField(15);
		
		/*****************boxes****************************************/
		retDate1 = new JComboBox<String>();
		retTime1 = new JComboBox<String>();
		ActionListener dt = new DateTimeListener();
		newDate = new JComboBox<String>();
		newDate.addActionListener(dt);
		newTime = new JComboBox<String>();
		newTime.addActionListener(dt);
		retDate2 = new JComboBox<String>();
		retTime2 = new JComboBox<String>();
		pickDate = new JComboBox<String>();
		pickTime = new JComboBox<String>();
		
		/*******************************buttons*************************/
		backButton = new JButton("Back");
		backButton.addActionListener(new BackListener(frame,panel));
		updateButton = new JButton("Update");
		updateButton.addActionListener(new UpdateListener());
		cancelButton = new JButton("Cancel Reservation"); 
		cancelButton.addActionListener(new CancelListener());
		showButton = new JButton("Show Car Availability");
		showButton.addActionListener(new ShowListener(frame, this));
		searchButton  = new JButton("Search");
		searchButton.addActionListener(new SearchListener());
	
		//leftScreen 
		GroupLayout.SequentialGroup lr1 = leftLayout.createSequentialGroup();
	    GroupLayout.ParallelGroup col1= leftLayout.createParallelGroup();
	    col1.addComponent(currusnl);
	    col1.addComponent(modell);
	    col1.addComponent(locl);
	    col1.addComponent(origretl1);
	    col1.addComponent(newl);
	    GroupLayout.ParallelGroup col2= leftLayout.createParallelGroup();
	    col2.addComponent(currusnt);
	    col2.addComponent(leftTitle);
	    col2.addComponent(modelt);
	    col2.addComponent(loct);
	    GroupLayout.SequentialGroup time1 = leftLayout.createSequentialGroup();
	    time1.addComponent(retDate1);
	    time1.addComponent(retTime1);
	    col2.addGroup(time1);
	    GroupLayout.SequentialGroup time2 = leftLayout.createSequentialGroup();
	    time2.addComponent(newDate);
	    time2.addComponent(newTime);
	    col2.addGroup(time2);
	    lr1.addGroup(col1);
	    lr1.addGroup(col2);
	    GroupLayout.ParallelGroup col3= leftLayout.createParallelGroup();
	    col3.addComponent(updateButton);
	    col3.addComponent(searchButton);
	    lr1.addGroup(col3);
	    
	    GroupLayout.SequentialGroup tp1 = leftLayout.createSequentialGroup();
	    GroupLayout.ParallelGroup row1= leftLayout.createParallelGroup();
	    row1.addComponent(currusnl);
	    row1.addComponent(currusnt);
	    row1.addComponent(searchButton);
	    tp1.addGroup(row1);
	    tp1.addComponent(leftTitle);
	    GroupLayout.ParallelGroup row2= leftLayout.createParallelGroup();
	    row2.addComponent(modell);
	    row2.addComponent(modelt);
	    tp1.addGroup(row2);
	    GroupLayout.ParallelGroup row3= leftLayout.createParallelGroup();
	    row3.addComponent(locl);
	    row3.addComponent(loct);
	    tp1.addGroup(row3);
	    GroupLayout.ParallelGroup row4= leftLayout.createParallelGroup();
	    row4.addComponent(origretl1);
	    row4.addComponent(retTime1);
	    row4.addComponent(retDate1);
	    tp1.addGroup(row4);
	    GroupLayout.ParallelGroup row5= leftLayout.createParallelGroup();
	    row5.addComponent(newl);
	    row5.addComponent(newTime);
	    row5.addComponent(newDate);
	    tp1.addGroup(row5);
	    tp1.addComponent(updateButton);
	
	    leftLayout.setHorizontalGroup(lr1);
	    leftLayout.setVerticalGroup(tp1);
	    
	    //rigthScreen
	    GroupLayout.SequentialGroup lr2 = rightLayout.createSequentialGroup();
	    GroupLayout.ParallelGroup ccol1= rightLayout.createParallelGroup();
	    ccol1.addComponent(affectedusnl);
	    ccol1.addComponent(origpickl);
	    ccol1.addComponent(origretl2);
	    ccol1.addComponent(affectedemaill);
	    ccol1.addComponent(affectedphonel);
	    ccol1.addComponent(cancelButton);
	    GroupLayout.ParallelGroup ccol2= rightLayout.createParallelGroup();
	    ccol2.addComponent(rightTitle);
	    ccol2.addComponent(affectedusnt);
	    GroupLayout.SequentialGroup date1 = rightLayout.createSequentialGroup();
	    date1.addComponent(pickDate);
	    date1.addComponent(pickTime);
	    ccol2.addGroup(date1);
	    GroupLayout.SequentialGroup date2 = rightLayout.createSequentialGroup();
	    date2.addComponent(retDate2);
	    date2.addComponent(retTime2);
	    ccol2.addGroup(date2);
	    ccol2.addComponent(affectedemailt);
	    ccol2.addComponent(affectedphonet);
	    ccol2.addComponent(showButton);
	    lr2.addGroup(ccol1);
	    lr2.addGroup(ccol2);
	    
	    GroupLayout.SequentialGroup tp2 = rightLayout.createSequentialGroup();
	    tp2.addComponent(rightTitle);
	    GroupLayout.ParallelGroup rrow1= rightLayout.createParallelGroup();
	    rrow1.addComponent(affectedusnl);
	    rrow1.addComponent(affectedusnt);
	    tp2.addGroup(rrow1);
	    GroupLayout.ParallelGroup rrow2= rightLayout.createParallelGroup();
	    rrow2.addComponent(origpickl);
	    rrow2.addComponent(pickDate);
	    rrow2.addComponent(pickTime);
	    tp2.addGroup(rrow2);
	    GroupLayout.ParallelGroup rrow3= rightLayout.createParallelGroup();
	    rrow3.addComponent(origretl2);
	    rrow3.addComponent(retDate2);
	    rrow3.addComponent(retTime2);
	    tp2.addGroup(rrow3);
	    GroupLayout.ParallelGroup rrow4= rightLayout.createParallelGroup();
	    rrow4.addComponent(affectedemaill);
	    rrow4.addComponent(affectedemailt);
	    tp2.addGroup(rrow4);
	    GroupLayout.ParallelGroup rrow5= rightLayout.createParallelGroup();
	    rrow5.addComponent(affectedphonel);
	    rrow5.addComponent(affectedphonet);
	    tp2.addGroup(rrow5);
	    GroupLayout.ParallelGroup rrow6= rightLayout.createParallelGroup();
	    rrow6.addComponent(cancelButton);
	    rrow6.addComponent(showButton);
	    tp2.addGroup(rrow6);
	    
	    rightLayout.setHorizontalGroup(lr2);
	    rightLayout.setVerticalGroup(tp2);
		
		add(rentalInfo,BorderLayout.WEST);
		add(userAffected, BorderLayout.EAST);
		add(backButton,BorderLayout.SOUTH);
	}
	
	private void getValues(){
		model = modelt.getText();
		loc = loct.getText();
		origret1 = (String)retDate1.getSelectedItem() + retTime1.getSelectedItem();
		origret2 = (String)retDate2.getSelectedItem() + retTime2.getSelectedItem();
		newret = (String) newDate.getSelectedItem() + newTime.getSelectedItem();
		origpick = (String) pickDate.getSelectedItem() + pickDate.getSelectedItem();
		currusn = currusnt.getText();
		affectedusn = affectedusnt.getText();
		affectedemail = affectedemailt.getText();
		affectedphone = affectedphonet.getText();
	}
	private class SearchListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e){
			String usn = currusn = currusnt.getText();
			QueryAdaptor.connect();
			try {
				if(QueryAdaptor.hasReservation(currusn)){
					String[] ans = QueryAdaptor.getReservationInfo(usn);
					loct.setText(ans[1]);
					modelt.setText(ans[0]);
					retDate1.addItem(ans[2].substring(0,10));
					retTime1.addItem(ans[2].substring(11,ans[2].length()));
					for(String s:Util.generateTime()) newTime.addItem(s);
					for(String s: Util.generateFutureDate(Util.getDate(ans[2].substring(0,10)))) newDate.addItem(s);
				}else{
					JOptionPane.showMessageDialog(frame,"User does not have a reservation");
				}
			} catch (Exception e1) {
				e1.printStackTrace();
				QueryAdaptor.close();
			}QueryAdaptor.close();
		}
	}
	
	private class DateTimeListener implements ActionListener{
		public void actionPerformed(ActionEvent e){
			String d = (String)newDate.getSelectedItem() +" "+ newTime.getSelectedIndex();
			QueryAdaptor.connect();
			String[] store;
			boolean found;
			String vsn;
			try {
				vsn = QueryAdaptor.findVsn(loct.getText(), modelt.getText());
				if(QueryAdaptor.getUserAffected(vsn,d,currusnt.getText())!=null){
					found = true;
					store = QueryAdaptor.getUserAffected(vsn,d,currusnt.getText());
				} else{
					found = false;
					store = new String[10];
				}
			} catch (Exception e1) {
				found = false;
				store = new String[10];
				e1.printStackTrace();
			}QueryAdaptor.close();
			if(found){
				affectedusnt.setText(store[0]);
				pickDate.addItem(store[1].substring(0,10));
				pickTime.addItem(store[1].substring(11,store[1].length()));
				retDate2.addItem(store[2].substring(0,10));
				retTime2.addItem(store[2].substring(11,store[2].length()));
				affectedemailt.setText(store[3]);
				affectedphonet.setText(store[4]);
			} if(!found){
				affectedusnt.setText("");
				pickDate.removeAll();
				pickTime.removeAll();
				retDate2.removeAll();
				retTime2.removeAll();
				affectedemailt.setText("");
				affectedphonet.setText("");
			}
		}
	}
	
	private class UpdateListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e){
			int hours = - Util.getDifference((String) retDate1.getSelectedItem(), 
					(String) newDate.getSelectedItem(), (String) retTime1.getSelectedItem(),
					(String) newTime.getSelectedItem());
			UpdateAdaptor.connect();
			QueryAdaptor.connect();
			try {
				String rid = QueryAdaptor.getResID(currusnt.getText(), QueryAdaptor.findVsn(loct.getText(), modelt.getText()));
				System.out.println(rid);
				UpdateAdaptor.updateLateTime(hours,rid);
				JOptionPane.showMessageDialog(frame,"Successfully updated reservation!");
			} catch (Exception e1) {
				e1.printStackTrace();
			} UpdateAdaptor.close();
			QueryAdaptor.close();
		}
	}
	private class CancelListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e){
			UpdateAdaptor.connect();
			QueryAdaptor.connect();
			try{
				String rid = QueryAdaptor.getResID(affectedusnt.getText(), QueryAdaptor.findVsn(loct.getText(), modelt.getText()));
				UpdateAdaptor.removeReservation(rid);
				JOptionPane.showMessageDialog(frame,"Reservation has been Removed!");
			}catch(Exception e1){
				e1.printStackTrace();
			}UpdateAdaptor.close();
			QueryAdaptor.close();
		}
	}
	private class ShowListener implements ActionListener{
		JFrame frame;
		JPanel panel;
		public ShowListener(JFrame frame,JPanel panel){
			this.frame = frame;
			this.panel = panel;
		}
		@Override
		public void actionPerformed(ActionEvent e){
			QueryAdaptor.connect();
			try{
				String[][] ans = QueryAdaptor.getOtherCarAvailabilityList(affectedusnt.getText(), loct.getText(), modelt.getText(),
						QueryAdaptor.getCarTypeList(loct.getText(),modelt.getText())[0], (String)pickDate.getSelectedItem(), (String)pickTime.getSelectedItem(), 
						(String) retDate2.getSelectedItem(), (String) retTime2.getSelectedItem());
				String[] w = {loct.getText(),modelt.getText(),QueryAdaptor.getCarTypeList(loct.getText(),modelt.getText())[0],
						(String)pickDate.getSelectedItem(), (String)pickTime.getSelectedItem(), (String) retDate2.getSelectedItem(), (String) retTime2.getSelectedItem()};
				changeScreen(new CarAvailabilityScreen(frame,affectedusnt.getText(),ans,w,panel,true),frame);
			} catch(Exception e1){
				e1.printStackTrace();
			}QueryAdaptor.close();
		}
	}
	private class BackListener implements ActionListener{
		JFrame frame;
		JPanel panel;
		public BackListener(JFrame frame, JPanel panel){
			this.frame = frame;
			this.panel = panel;
		} @Override
		public void actionPerformed(ActionEvent e){
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
