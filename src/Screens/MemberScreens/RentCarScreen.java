package Screens.MemberScreens;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.util.List;

import javax.swing.*;

import Data.QueryAdaptor;
import Data.UpdateAdaptor;
import Data.Util;
import Screens.EmployeeScreens.EmployeeHomePage;

@SuppressWarnings("serial")
public class RentCarScreen extends JPanel{
	private String[] startDateEntries ;
	private String[] startTimeEntries;
	private String[] returnDateEntries;
	private String[] returnTimeEntries;
	private String[] locationsEntries = {"Klaus","Institute of Paper Science","CULC","Student Center","Tech Square","COC","Peters Parking Deck"};
	private String[] carModelEntries, carTypeEntries;
	private String[] noneAvailable = {"None Available"};

	private String usn;
	JComboBox startDate,startTime,returnDate,returnTime,locations,carModel,carType;
	JLabel pickup,returnl,loc,car;
	JButton search,back;

	public RentCarScreen(JFrame frame, String usn){
		//TODO query stuff
		//instantiates layout
		GroupLayout layout = new GroupLayout(this);
		this.setLayout(layout);
		layout.setAutoCreateGaps(true);
		layout.setAutoCreateContainerGaps(true);
		
		this.usn = usn;


		/**********************comboboxes*****************/
		//TODO 
		QueryAdaptor.connect();
		try {
			carModelEntries = QueryAdaptor.getCarList();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			carTypeEntries = QueryAdaptor.getCarTypeList();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		QueryAdaptor.close();
		
		//generating time and date arrays
		List<String> futureDatesList = Util.generateFutureDate(Util.getCurrentDate());
		this.startDateEntries = futureDatesList.toArray(new String[futureDatesList.size()]);
		this.startTimeEntries = Util.generateTime();
		this.returnDateEntries = futureDatesList.toArray(new String[futureDatesList.size()]);
		this.returnTimeEntries = Util.generateTime();
		
		
		
		startDate = new JComboBox(startDateEntries);
		startTime = new JComboBox(startTimeEntries);
		returnDate = new JComboBox(returnDateEntries);
		returnTime = new JComboBox(returnTimeEntries);
		locations = new JComboBox(locationsEntries);
		locations.addActionListener(new LocationsListener());
		carModel = new JComboBox(carModelEntries);
		carModel.addActionListener(new CarModelListener());
		carType = new JComboBox(carTypeEntries);
		
		/****************labels*********************/
		pickup = new JLabel("Pick Up Time: ");
		returnl = new JLabel("Return Time: ");
		loc = new JLabel("Location: ");
		car = new JLabel("Cars: ");

		/******************buttons*****************/
		search = new JButton("Search");
		search.addActionListener(new SearchListener(frame));
		
		back = new JButton("Back");
		back.addActionListener(new BackListener(frame));

		//layout declaration
		GroupLayout.SequentialGroup leftToRight = layout.createSequentialGroup();
		GroupLayout.ParallelGroup columnleft = layout.createParallelGroup();
		columnleft.addComponent(pickup);
		columnleft.addComponent(returnl);
		columnleft.addComponent(loc);
		columnleft.addComponent(car);
		columnleft.addComponent(back);
		GroupLayout.SequentialGroup columnMidLeft = layout.createSequentialGroup();
		GroupLayout.ParallelGroup r = layout.createParallelGroup();
		r.addComponent(startDate);
		r.addComponent(returnDate);
		r.addComponent(carModel);
		columnMidLeft.addGroup(r);
		GroupLayout.ParallelGroup l = layout.createParallelGroup();
		l.addComponent(startTime);
		l.addComponent(returnTime);
		l.addComponent(carType);
		columnMidLeft.addGroup(l);
		GroupLayout.ParallelGroup columnMid = layout.createParallelGroup();
		columnMid.addGroup(columnMidLeft);
		columnMid.addComponent(locations);
		columnMid.addComponent(search);
		leftToRight.addGroup(columnleft);
		leftToRight.addGroup(columnMid);

		GroupLayout.SequentialGroup topToBottom = layout.createSequentialGroup();
		GroupLayout.ParallelGroup row1 = layout.createParallelGroup();
		row1.addComponent(startDate);
		row1.addComponent(startTime);
		row1.addComponent(pickup);
		GroupLayout.ParallelGroup row2 = layout.createParallelGroup();
		row2.addComponent(returnDate);
		row2.addComponent(returnl);
		row2.addComponent(returnTime);
		GroupLayout.ParallelGroup row3 = layout.createParallelGroup();
		row3.addComponent(locations);
		row3.addComponent(loc);
		GroupLayout.ParallelGroup row4 = layout.createParallelGroup();
		row4.addComponent(car);
		row4.addComponent(carType);
		row4.addComponent(carModel);
		GroupLayout.ParallelGroup row5 = layout.createParallelGroup();
		row5.addComponent(back);
		row5.addComponent(search);
		topToBottom.addGroup(row1);
		topToBottom.addGroup(row2);
		topToBottom.addGroup(row3);
		topToBottom.addGroup(row4);
		topToBottom.addGroup(row5);

		layout.setHorizontalGroup(leftToRight);
		layout.setVerticalGroup(topToBottom);

	}


	/******************button listeners*****************/
	private class SearchListener implements ActionListener{
		JFrame frame;
		String[][] availableCarsList;
		
		public SearchListener(JFrame frame){
			this.frame = frame;
		}
		@Override
		public void actionPerformed(ActionEvent e){
			//TODO
			String  locInput = (String) locations.getSelectedItem(),
					modelInput = (String) carModel.getSelectedItem(),
					typeInput = (String) carType.getSelectedItem(),
					startDateInput = (String) startDate.getSelectedItem(),
					startTimeInput = (String) startTime.getSelectedItem(),
					returnDateInput = (String) returnDate.getSelectedItem(),
					returnTimeInput = (String) returnTime.getSelectedItem();
			String[] rentalInfo = {locInput,modelInput,typeInput,startDateInput,startTimeInput,returnDateInput,returnTimeInput};
			QueryAdaptor.connect();
			try {
				availableCarsList = QueryAdaptor.getCarAvailabilityList(usn,locInput, modelInput, typeInput, startDateInput, startTimeInput, returnDateInput, returnTimeInput);
			} catch (Exception e1) {
				e1.printStackTrace();
			} QueryAdaptor.close();
			
			//transferring availableCarsList to the next screen
			if (availableCarsList==null){
				JOptionPane.showMessageDialog(frame,"No cars available according to your search at this time.");
			}
			else{
			changeScreen(new CarAvailabilityScreen(frame,usn,availableCarsList,rentalInfo,null,false),frame);
			}
		}
	}


	private class LocationsListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e){
			JComboBox cb = (JComboBox)e.getSource();
			String selected = (String)cb.getSelectedItem();
			QueryAdaptor.connect();
			try {
				carModelEntries = QueryAdaptor.getCarList(selected);
			} catch (Exception e1) {
				e1.printStackTrace();
			} QueryAdaptor.close();
			carModel.removeAllItems();
			for(int i=0;i<carModelEntries.length;i++){
				carModel.addItem(carModelEntries[i]);
			}
		}	
	}

	private class CarModelListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e){
			JComboBox cb = (JComboBox)e.getSource();
			String carModelSelected = (String)cb.getSelectedItem();
			String locationSelected = (String) locations.getSelectedItem();
			QueryAdaptor.connect();
			try {
				carTypeEntries = QueryAdaptor.getCarTypeList(locationSelected ,carModelSelected);
			} catch (Exception e1) {
				e1.printStackTrace();
			} QueryAdaptor.close();
			carType.removeAllItems();
			for(int i=0;i<carTypeEntries.length;i++){
				carType.addItem(carTypeEntries[i]);
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
			JPanel panel = new HomePageScreen(frame,usn);
			changeScreen(panel,frame);
		}
	}


	private static void changeScreen(JPanel newScreen,JFrame frame){
		frame.getContentPane().removeAll();
		frame.getContentPane().add(newScreen);
		frame.pack();
		frame.setVisible(true);
	}
}