package Screens.EmployeeScreens;
import java.awt.event.*;
import java.awt.*;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

import Data.QueryAdaptor;


public class LocationPreferenceReportPanel extends JPanel{
	//GUI components
	private realModel model = new realModel();
	private JLabel reportAgeLabel;
	private JButton back;
	private JPanel panel;

	//array of arrays needed to fill table in this format: [[Month, Location, No of Reservations, Total no of hours]]
	private String[][] locationPreferenceInfo;

	public class realModel extends DefaultTableModel{
		@Override
		public boolean isCellEditable(int row, int col){
			return false;
		}
	}

	public LocationPreferenceReportPanel(JFrame frame, JPanel panel){
		QueryAdaptor.connect();
		try {
			this.locationPreferenceInfo = QueryAdaptor.getLocationPreferenceReport();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		QueryAdaptor.close();
		this.setLayout(new BorderLayout());
		this.panel = panel;

		//set the enwrapping Admin Home Page border
		this.setBorder (BorderFactory.createTitledBorder (BorderFactory.createEtchedBorder (), "Location Preference Report", TitledBorder.CENTER, TitledBorder.TOP));



		//add label for report age
		reportAgeLabel = new JLabel("Report for last 3 months");
		reportAgeLabel.setFont(new Font("Serif", Font.BOLD, 12));
		this.add(reportAgeLabel, BorderLayout.NORTH);

		//adding the column with their column names
		model.addColumn("Month");
		model.addColumn("Location");
		model.addColumn("No of Reservations");
		model.addColumn("Total No of Hours");

		//add rows to table using helper method
		addRowsToTable();

		//Create JTable and add to JScrollPane
		JTable table = new JTable(model);
		table.setPreferredScrollableViewportSize(new Dimension(700, 80));
		table.setFillsViewportHeight(true);

		//Add JScrollPane to this panel
		JScrollPane spTable = new JScrollPane(table);
		spTable.setName("Location Preference Report");
		this.add(spTable,BorderLayout.CENTER);

		//back button
		back = new JButton("back");
		back.addActionListener(new ButtonListener(frame));
		this.add(back, BorderLayout.SOUTH);
	}


	private void addRowsToTable(){
		for(int i = 0; i<locationPreferenceInfo.length; i++){
			//adding each subarray of strings to the table
			model.addRow(locationPreferenceInfo[i]);
		}
	}

	private class ButtonListener implements ActionListener{
		JFrame frame;
		public ButtonListener(JFrame frame){
			this.frame = frame;
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


}

