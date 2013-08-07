package Screens.MemberScreens;
import java.awt.event.*;
import java.awt.*;
import java.util.List;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import Data.QueryAdaptor;
import Data.UpdateAdaptor;
import Data.Util;



public class RentalInformationScreen extends JPanel{

	//GUI components
	private realModel currModel = new realModel();
	private realModel pastModel = new realModel();
	private JLabel currResLabel, prevResLabel, rentalInfoLabel,choose;
	private JButton back, update;
	private JTable currTable, pastTable;
	private JComboBox returnDate, returnTime;

	//array of arrays needed to fill table in this format
	private String usn;
	private String[] returnDateEntries;
	private String[] returnTimeEntries;
	private String[][] currResEntries;
	private String[][] pastResEntries;
	private String[][] noneAvailable = {{"No","current","reservations","at","the","moment"}};

	public class realModel extends DefaultTableModel{
		@Override
		public boolean isCellEditable(int row, int col){
			return false;
		}
	}

	public RentalInformationScreen(JFrame frame, String usn){
		this.usn = usn;

		/**********************labels**************************/
		rentalInfoLabel = new JLabel("Rental Information");
		rentalInfoLabel.setFont(new Font("Serif", Font.BOLD, 24));
		choose = new JLabel("Choose Return Time:");
		currResLabel = new JLabel("Current Reservations");
		prevResLabel = new JLabel("Previous Reservations");

		/**********************comboboxes**************************/
		List<String> futureDatesList = Util.generateFutureDate(Util.getCurrentDate());
		this.returnDateEntries = futureDatesList.toArray(new String[futureDatesList.size()]);
		this.returnTimeEntries = Util.generateTime();

		returnDate = new JComboBox(returnDateEntries);
		returnTime = new JComboBox(returnTimeEntries);
		/**********************buttons**************************/
		back = new JButton("Back");
		back.addActionListener(new ButtonListener(frame));

		update = new JButton("Update");
		update.addActionListener(new UpdateListener(frame));

		/**********************tables**************************/
		//adding the column with their column names
		currModel.addColumn("Date");
		currModel.addColumn("Reservation Time");
		currModel.addColumn("Car");
		currModel.addColumn("Location");
		currModel.addColumn("Amount");
		currModel.addColumn("Reservation ID");

		pastModel.addColumn("Date");
		pastModel.addColumn("Reservation Time");
		pastModel.addColumn("Car");
		pastModel.addColumn("Location");
		pastModel.addColumn("Amount");
		pastModel.addColumn("Reservation ID");

		//Create JTable and add to JScrollPane
		currTable = new JTable(currModel);
		currTable.setPreferredScrollableViewportSize(new Dimension(700, 80));
		currTable.setFillsViewportHeight(true);

		pastTable = new JTable(currModel);
		pastTable.setPreferredScrollableViewportSize(new Dimension(700, 80));
		pastTable.setFillsViewportHeight(true);

		//query for table information
		QueryAdaptor.connect();
		try {
			currResEntries = QueryAdaptor.getCurrReservationInfo(usn);
			pastResEntries = QueryAdaptor.getPastReservationInfo(usn);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} QueryAdaptor.close();
		if(currResEntries==null){
			currResEntries = noneAvailable;
		}
		if(pastResEntries==null){
			pastResEntries = noneAvailable;
		}

		addRowsToTable(currModel,currResEntries);
		addRowsToTable(pastModel,pastResEntries);

		//deal with row selection 
		currTable.setRowSelectionAllowed(true);
		ListSelectionModel selectionModel = currTable.getSelectionModel();
		selectionModel.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		//selectionModel.addListSelectionListener(new RowListener(frame,table,this));

		//Add table to scrollPane
		JScrollPane currSPTable = new JScrollPane(currTable);
		currSPTable.setName("Current Reservations");
		JScrollPane pastSPTable = new JScrollPane(pastTable);
		pastSPTable.setName("Previous Reservations");
		//this.add(spTable,BorderLayout.CENTER);

		/**********************layout declarations**************************/

		GroupLayout layout = new GroupLayout(this);
		this.setLayout(layout);
		layout.setAutoCreateGaps(true);
		layout.setAutoCreateContainerGaps(true);

		GroupLayout.SequentialGroup leftToRight = layout.createSequentialGroup();
		GroupLayout.ParallelGroup col1 = layout.createParallelGroup();
		col1.addComponent(rentalInfoLabel);
		col1.addComponent(currResLabel);
		col1.addComponent(currSPTable);
		GroupLayout.SequentialGroup mid = layout.createSequentialGroup();
		mid.addComponent(choose);
		mid.addComponent(returnDate);
		mid.addComponent(returnTime);
		mid.addComponent(update);
		col1.addGroup(mid);
		col1.addComponent(prevResLabel);
		col1.addComponent(pastSPTable);
		col1.addComponent(back);
		leftToRight.addGroup(col1);

		GroupLayout.SequentialGroup topToBottom = layout.createSequentialGroup();
		GroupLayout.ParallelGroup row1 = layout.createParallelGroup();
		row1.addComponent(rentalInfoLabel);
		GroupLayout.ParallelGroup row2 = layout.createParallelGroup();
		row2.addComponent(currResLabel);
		GroupLayout.ParallelGroup row3 = layout.createParallelGroup();
		row3.addComponent(currSPTable);
		GroupLayout.ParallelGroup row4 = layout.createParallelGroup();
		row4.addComponent(choose);
		row4.addComponent(returnDate);
		row4.addComponent(returnTime);
		row4.addComponent(update);
		GroupLayout.ParallelGroup row5 = layout.createParallelGroup();
		row5.addComponent(prevResLabel);
		GroupLayout.ParallelGroup row6 = layout.createParallelGroup();
		row6.addComponent(pastSPTable);
		GroupLayout.ParallelGroup row7 = layout.createParallelGroup();
		row7.addComponent(back);
		topToBottom.addGroup(row1);
		topToBottom.addGroup(row2);
		topToBottom.addGroup(row3);
		topToBottom.addGroup(row4);
		topToBottom.addGroup(row5);
		topToBottom.addGroup(row6);
		topToBottom.addGroup(row7);

		layout.setHorizontalGroup(leftToRight);
		layout.setVerticalGroup(topToBottom);


	}

	/**********************listeners**************************/
	private class UpdateListener implements ActionListener{
		JFrame frame;
		public UpdateListener(JFrame frame){
			this.frame = frame;
		}
		@Override
		public void actionPerformed(ActionEvent e){
			int selectedRowIndex = currTable.getSelectedRow();
			if (selectedRowIndex==-1){
				JOptionPane.showMessageDialog(frame,"Please select a reservation to update by clicking on its row.");
			}
			else{
				String[] selectedReservationInfo = currResEntries[selectedRowIndex];
				String resID = selectedReservationInfo[selectedReservationInfo.length-1];
				String pastReturnDateTime = selectedReservationInfo[0];
				String newReturnDate = (String) returnDate.getSelectedItem();
				String newReturnTime = (String) returnTime.getSelectedItem();
				String newReturnDateTime = newReturnDate + " " + newReturnTime;

				//Update reservation return time in database and create entry into reservation_extended_time
				UpdateAdaptor.connect();
				try {
					UpdateAdaptor.updateReservationReturnTime(resID, pastReturnDateTime, newReturnDateTime);
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				UpdateAdaptor.close();
				JOptionPane.showMessageDialog(frame,"Your reservation has been successfully extended.");
			}
		}
	}

	/*private class RowListener implements ListSelectionListeneExtendr{
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
			JPanel panel = new HomePageScreen(frame, usn);
			changeScreen(panel,frame);
		}
	}

	/*********************helper methods********************/
	private static void changeScreen(JPanel newScreen,JFrame frame){
		frame.getContentPane().removeAll();
		frame.getContentPane().add(newScreen);
		frame.pack();
		frame.setVisible(true);
	}


	private void addRowsToTable(realModel model, String[][] entriesList){
		for(int i = 0; i<entriesList.length; i++){
			//adding each subarray of strings to the table
			model.addRow(entriesList[i]);
		}
	}

}