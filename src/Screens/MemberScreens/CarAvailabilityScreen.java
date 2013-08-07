package Screens.MemberScreens;
import java.awt.event.*;
import java.awt.*;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import Data.UpdateAdaptor;



public class CarAvailabilityScreen extends JPanel{

	//GUI components
	private realModel model = new realModel();
	private JLabel label;
	private JButton reserve;
	private JTable table;
	private JPanel panel;
	private boolean b;

	//array of arrays needed to fill table 
	private String[][] carAvailabilityList;
	
	//array of rental info in this order: locInput,modelInput,typeInput,startDateInput,startTimeInput,returnDateInput,returnTimeInput
	private String[] rentalInfo;
	private String usn;
	
	public class realModel extends DefaultTableModel{
		@Override
		public boolean isCellEditable(int row, int col){
			return false;
		}
	}

	public CarAvailabilityScreen(JFrame frame, String usn, String[][] carAvailabilityList, String[] rentalInfo, JPanel panel, boolean b){
		this.carAvailabilityList = carAvailabilityList;
		this.usn = usn;
		this.rentalInfo = rentalInfo;
		this.setLayout(new BorderLayout());
		this.panel = panel;
		this.b = b;
		
		//set the enwrapping Admin Home Page border
		this.setBorder (BorderFactory.createTitledBorder (BorderFactory.createEtchedBorder (), "Car Availability", TitledBorder.CENTER, TitledBorder.TOP));
		
		//add label for table title
		label = new JLabel("Car Availability");
		label.setFont(new Font("Serif", Font.BOLD, 24));
		this.add(label,BorderLayout.NORTH);

		//adding the column with their column names
		model.addColumn("Vehicle Sno");
		model.addColumn("Car Model");
		model.addColumn("Car Type");
		model.addColumn("Location");
		model.addColumn("Car Color");
		model.addColumn("Hourly Rate (Occasional Driving Plan)");
		model.addColumn("Discounted Rate (Frequent Driving Plan)");
		model.addColumn("Discounted Rate (Daily Driving Plan)");
		model.addColumn("Daily Rate");
		model.addColumn("Seating Capacity");
		model.addColumn("Transmission Type");
		model.addColumn("Bluetooth Connectivity");
		model.addColumn("Auxiliary Cable Present");
		model.addColumn("Estimated Cost");

		//add rows to table using helper method
		addRowsToTable();

		//Create JTable and add to JScrollPane
		table = new JTable(model);
		table.setPreferredScrollableViewportSize(new Dimension(1300, 150));
		table.setFillsViewportHeight(true);
		
		//deal with row selection 
		table.setRowSelectionAllowed(true);
		ListSelectionModel selectionModel = table.getSelectionModel();
		selectionModel.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		//selectionModel.addListSelectionListener(new RowListener(frame,table,this));

		//Add JScrollPane to this panel
		JScrollPane spTable = new JScrollPane(table);
		spTable.setName("Revenue Generated");
		this.add(spTable,BorderLayout.CENTER);
		
		//reserve button
		reserve = new JButton("Reserve");
		reserve.addActionListener(new ButtonListener(frame));
		this.add(reserve, BorderLayout.SOUTH);
	}

	/**********************listeners**************************/
	
	/*private class RowListener implements ListSelectionListener{
		private JFrame frame;
		private JTable table;
		
		public RowListener(JFrame frame, JTable table, JPanel panel){
			this.frame = frame;
			this.table = table;
			this.panel = panel;
		}

		@Override
		public void valueChanged(ListSelectionEvent e) {
			// TODO Auto-generated method stub
			 if(!e.getValueIsAdjusting()){  
		            ListSelectionModel model = table.getSelectionModel();  
		            int lead = model.getLeadSelectionIndex();        
		            setRowValue(lead); 
		            selectedRow = table.getSe
		     }  
		}
		
		private void setRowValue(int rowIndex){  
	       Object o = table.getValueAt(rowIndex, 0);  
	       selectedCar = o.toString();
	       
	    }  
		
	}

	*/
	private class ButtonListener implements ActionListener{
		JFrame frame;
		public ButtonListener(JFrame frame){
			this.frame = frame;
		}
		@Override
		public void actionPerformed(ActionEvent e){
			int selectedRowIndex = table.getSelectedRow();
			if (selectedRowIndex==-1){
				JOptionPane.showMessageDialog(frame,"Please select a car to reserve by clicking on its row.");
			}
			else{
				
				String[] selectedCarInfo = carAvailabilityList[selectedRowIndex];
				
				String pickUpDate = rentalInfo[3];
				String pickUpTime = rentalInfo[4];
				String pickUpDateTime = pickUpDate + " " + pickUpTime;
				String returnDate = rentalInfo[5];
				String returnTime = rentalInfo[6];
				String returnDateTime = returnDate + " " + returnTime;
				String location= rentalInfo[0];
				String vehicleSno= selectedCarInfo[0];
				String estimatedCost= selectedCarInfo[13];
				
				UpdateAdaptor.connect();
				try {
					UpdateAdaptor.createReservation(usn, pickUpDateTime, returnDateTime, location, vehicleSno, estimatedCost);
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				UpdateAdaptor.close();
				JOptionPane.showMessageDialog(frame,"Reservation successful.");
				
				if(!b){
					panel = new HomePageScreen(frame, usn);
					changeScreen(panel,frame);
				}
				else{
					changeScreen(panel,frame);
				}
				
			}
		}
	}
	
	/*********************helper methods********************/
	private static void changeScreen(JPanel newScreen,JFrame frame){
		frame.getContentPane().removeAll();
		frame.getContentPane().add(newScreen);
		frame.pack();
		frame.setVisible(true);
	}

	
	private void addRowsToTable(){
		for(int i = 0; i<carAvailabilityList.length; i++){
			//adding each subarray of strings to the table
			model.addRow(carAvailabilityList[i]);
		}
	}
	
}