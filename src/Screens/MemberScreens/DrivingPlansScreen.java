package Screens.MemberScreens;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/*
 * This class creates the Driving Plans screen that displays a graph of the rates and costs of
 * the 3 different driving plans.
 * @author Tri-An Le
 * @version 1.0
 * 
 */

public class DrivingPlansScreen extends JPanel{
	
	//GUI components
	private realModel model = new realModel();
	private JPanel panel;
	
	//Strings needed to fill graph
	private String occasionalDriving_monthlyPayment, occasionalDriving_discount, occasionalDriving_annualFees;
	private String frequentDriving_monthlyPayment, frequentDriving_discount, frequentDriving_annualFees;
	private String dailyDriving_monthlyPayment, dailyDriving_discount, dailyDriving_annualFees;
	private JButton back;
	
	public class realModel extends DefaultTableModel{
		@Override
		public boolean isCellEditable(int row, int col){
			return false;
		}
	}
	
	public DrivingPlansScreen(JFrame frame,JPanel panel){
		this.panel = panel;
		this.setLayout(new BorderLayout());
		
		//setting text for each graph cell
		this.occasionalDriving_monthlyPayment= "-NA-";
		this.occasionalDriving_discount= "-NA-";
		this.occasionalDriving_annualFees= "$50";
		
		this.frequentDriving_monthlyPayment= "$60";
		this.frequentDriving_discount= "10%";
		this.frequentDriving_annualFees= "-NA-";
		
		this.dailyDriving_monthlyPayment= "$100";
		this.dailyDriving_discount= "15%";
		this.dailyDriving_annualFees= "-NA-";
		
		//adding the column with their column names
		model.addColumn("Driving Plan");
		model.addColumn("Monthly Payment");
		model.addColumn("Discount");
		model.addColumn("Annual Fees");
		
		//adding row names and strings to each row array
		String[] occasionalDriving = {"Occasional Driving", occasionalDriving_monthlyPayment, occasionalDriving_discount, occasionalDriving_annualFees};
		String[] frequentDriving = {"Frequent Driving", frequentDriving_monthlyPayment, frequentDriving_discount, frequentDriving_annualFees};
		String[] dailyDriving = {"Daily Driving", dailyDriving_monthlyPayment, dailyDriving_discount, dailyDriving_annualFees};
		
		
		//adding the rows with their row names
		model.addRow(occasionalDriving);
		model.addRow(frequentDriving);
		model.addRow(dailyDriving);
		
		//back button
		back = new JButton("Back");
		back.addActionListener(new ButtonListener(frame));
		this.add(back, BorderLayout.SOUTH);

		//Create JTable and add to JScrollPane
		JTable table = new JTable(model);
		table.setPreferredScrollableViewportSize(new Dimension(500, 70));
        table.setFillsViewportHeight(true);
        
       
        //Add JScrollPane to this panel
        JScrollPane spTable = new JScrollPane(table);
        this.add(spTable, BorderLayout.CENTER);
        
        
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