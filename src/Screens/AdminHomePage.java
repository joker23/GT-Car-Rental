package Screens;
import java.awt.event.*;
import java.awt.*;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

import Data.QueryAdaptor;


public class AdminHomePage extends JPanel{

	//GUI components
	private realModel model = new realModel();
	private JLabel label;
	private JButton logout;
	private JPanel panel;

	//array of arrays needed to fill table in this format: [[Vehicle Sno, Type, Car Model, Reservation Revenue, Late Fees Revenue]]
	private String[][] revenueTableInfo;

	public class realModel extends DefaultTableModel{
		public boolean isCellEditable(int row, int col){
			return false;
		}
	}

	public AdminHomePage(JFrame frame){
		//this.revenueTableInfo = getRevenueTableInfo(); or some back-end method like that
		this.setLayout(new BorderLayout());
		this.panel = new LoginScreen(frame);
		
		//set the enwrapping Admin Home Page border
		this.setBorder (BorderFactory.createTitledBorder (BorderFactory.createEtchedBorder (), "Admin Home Page", TitledBorder.CENTER, TitledBorder.TOP));
		
		//add label for table title
		label = new JLabel("Revenue Generated");
		label.setFont(new Font("Serif", Font.BOLD, 24));
		this.add(label,BorderLayout.NORTH);
		
		//PULL EVERYTHING 
		QueryAdaptor.connect();
		try {
			revenueTableInfo = QueryAdaptor.getRevenueGenerated();
		} catch (Exception e) {
			e.printStackTrace();
			revenueTableInfo = new String[1][1];
			QueryAdaptor.close();
		}QueryAdaptor.close();
		
		


		//adding the column with their column names
		model.addColumn("Vehicle Sno");
		model.addColumn("Type");
		model.addColumn("Car Model");
		model.addColumn("Reservation Revenue");
		model.addColumn("Late Fees Revenue");

		//add rows to table using helper method
		addRowsToTable();

		//Create JTable and add to JScrollPane
		JTable table = new JTable(model);
		table.setPreferredScrollableViewportSize(new Dimension(700, 80));
		table.setFillsViewportHeight(true);

		//Add JScrollPane to this panel
		JScrollPane spTable = new JScrollPane(table);
		spTable.setName("Revenue Generated");
		this.add(spTable,BorderLayout.CENTER);
		
		//logout button
		logout = new JButton("Logout");
		logout.addActionListener(new ButtonListener(frame));
		this.add(logout, BorderLayout.SOUTH);
	}


	private void addRowsToTable(){
		for(int i = 0; i<revenueTableInfo.length; i++){
			//adding each subarray of strings to the table
			model.addRow(revenueTableInfo[i]);
		}
	}
	
	private class ButtonListener implements ActionListener{
		JFrame frame;
		public ButtonListener(JFrame frame){
			this.frame = frame;
		}
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