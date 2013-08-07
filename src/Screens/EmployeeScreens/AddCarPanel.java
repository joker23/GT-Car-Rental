package Screens.EmployeeScreens;

import javax.swing.*;

import Data.QueryAdaptor;
import Data.UpdateAdaptor;

import java.awt.event.*;

public class AddCarPanel extends JPanel{
	//TODO have a database of cartypes
	private String bool[] = {"No","Yes"},carType[] = {"Hybrid","Sedan","Minivan","Convertable","Hatchback"},
			location[]={"Klaus","Institute of Paper Science","CULC","Student Center","Tech Square","COC","Peters Parking Deck"},tt[] = {"Manual","Automatic"};
	private JLabel vsno,model,ctype,loc,color,hr,dr,sc,transmission,bluetooth,Aux;
	private JComboBox<String> type,locbox,transmissionbox,bluetoothbox,auxbox;
	private JTextField vsnotext,modeltext,colortext,hrtext,drtext,sctext;
	private JButton add;
	public AddCarPanel(JFrame frame){
		//instantiates layout
		GroupLayout layout = new GroupLayout(this);
	    setLayout(layout);
	    layout.setAutoCreateGaps(true);
	    layout.setAutoCreateContainerGaps(true);
	    
	    /*****************************labels**********************************/
	    vsno = new JLabel("Vehicle Serial Number: ");
	    model = new JLabel("Car Model: ");
	    ctype = new JLabel("Car Type: ");
	    loc = new JLabel("Location: ");
	    color = new JLabel("Color: ");
	    hr = new JLabel("Hourly Rates: ");
	    dr = new JLabel("Daily Rates: ");
	    sc = new JLabel("Seating Capacity: ");
	    transmission = new JLabel("Transmission Type: ");
	    bluetooth = new JLabel("Bluetooth Connectivity: ");
	    Aux = new JLabel("Auxiliary Cable: ");
	    
	    /*****************************boxes***********************************/
	    type = new JComboBox<String>(carType);
	    locbox = new JComboBox<String>(location);
	    transmissionbox = new JComboBox<String>(tt);
	    bluetoothbox = new JComboBox<String>(bool);
	    auxbox = new JComboBox<String>(bool);
	    /****************************textfields*******************************/
	    vsnotext=  new JTextField(30);
	    modeltext = new JTextField(30);
	    colortext = new JTextField(30);
	    hrtext = new JTextField(30);
	    drtext = new JTextField(30);
	    sctext = new JTextField(30);
	    /*****************************button*********************************/
	    add = new JButton("Add");
	    add.addActionListener(new ButtonListener(frame));
	    
	    GroupLayout.SequentialGroup leftToRight = layout.createSequentialGroup();
	    GroupLayout.ParallelGroup col1 = layout.createParallelGroup();
	    col1.addComponent(vsno);
	    col1.addComponent(model);
	    col1.addComponent(ctype);
	    col1.addComponent(loc);
	    col1.addComponent(color);
	    col1.addComponent(hr);
	    col1.addComponent(dr);
	    col1.addComponent(sc);
	    col1.addComponent(transmission);
	    col1.addComponent(bluetooth);
	    col1.addComponent(Aux);
	    GroupLayout.ParallelGroup col2 = layout.createParallelGroup();
	    col2.addComponent(type);
	    col2.addComponent(locbox);
	    col2.addComponent(transmissionbox);
	    col2.addComponent(bluetoothbox);
	    col2.addComponent(auxbox);
	    col2.addComponent(vsnotext);
	    col2.addComponent(modeltext);
	    col2.addComponent(colortext);
	    col2.addComponent(hrtext);
	    col2.addComponent(drtext);
	    col2.addComponent(sctext);
	    leftToRight.addGroup(col1);
	    leftToRight.addGroup(col2);
	    leftToRight.addComponent(add);
	    
	    
	    GroupLayout.SequentialGroup topToBottom = layout.createSequentialGroup();
	    GroupLayout.ParallelGroup row1 = layout.createParallelGroup();
	    row1.addComponent(vsno);
	    row1.addComponent(vsnotext);
	    topToBottom.addGroup(row1);
	    GroupLayout.ParallelGroup row2 = layout.createParallelGroup();
	    row2.addComponent(model);
	    row2.addComponent(modeltext);
	    topToBottom.addGroup(row2);
	    GroupLayout.ParallelGroup row3 = layout.createParallelGroup();
	    row3.addComponent(ctype);
	    row3.addComponent(type);
	    topToBottom.addGroup(row3);
	    GroupLayout.ParallelGroup row4 = layout.createParallelGroup();
	    row4.addComponent(loc);
	    row4.addComponent(locbox);
	    topToBottom.addGroup(row4);
	    GroupLayout.ParallelGroup row5 = layout.createParallelGroup();
	    row5.addComponent(color);
	    row5.addComponent(colortext);
	    topToBottom.addGroup(row5);
	    GroupLayout.ParallelGroup row6 = layout.createParallelGroup();
	    row6.addComponent(hr);
	    row6.addComponent(hrtext);
	    topToBottom.addGroup(row6);
	    GroupLayout.ParallelGroup row7 = layout.createParallelGroup();
	    row7.addComponent(dr);
	    row7.addComponent(drtext);
	    topToBottom.addGroup(row7);
	    GroupLayout.ParallelGroup row8 = layout.createParallelGroup();
	    row8.addComponent(sc);
	    row8.addComponent(sctext);
	    topToBottom.addGroup(row8);
	    GroupLayout.ParallelGroup row9 = layout.createParallelGroup();
	    row9.addComponent(transmission);
	    row9.addComponent(transmissionbox);
	    topToBottom.addGroup(row9);
	    GroupLayout.ParallelGroup row10 = layout.createParallelGroup();
	    row10.addComponent(bluetooth);
	    row10.addComponent(bluetoothbox);
	    topToBottom.addGroup(row10);
	    GroupLayout.ParallelGroup row11 = layout.createParallelGroup();
	    row11.addComponent(Aux);
	    row11.addComponent(auxbox);
	    topToBottom.addGroup(row11);
	    topToBottom.addComponent(add);
	    
	    layout.setHorizontalGroup(leftToRight);
	    layout.setVerticalGroup(topToBottom);
	}
	private class ButtonListener implements ActionListener{
		JFrame frame;
		public ButtonListener(JFrame frame){
			this.frame = frame;
		}
		@Override
		public void actionPerformed(ActionEvent e){
			QueryAdaptor.connect();
			try {
				if(QueryAdaptor.carExists((String) locbox.getSelectedItem(),modeltext.getText())){
					JOptionPane.showMessageDialog(frame,"Car model exists in current location");
				}else{
					UpdateAdaptor.connect();
					UpdateAdaptor.addCar(vsnotext.getText(), ""+auxbox.getSelectedIndex(), 
							""+transmissionbox.getSelectedIndex(), sctext.getText(), 
							""+bluetoothbox.getSelectedIndex(), drtext.getText(), 
							hrtext.getText(), colortext.getText(), (String) type.getSelectedItem(), 
							modeltext.getText(), (String) locbox.getSelectedItem());
					UpdateAdaptor.close();
					JOptionPane.showMessageDialog(frame,"Car Successfully Added!");
				}
			} catch (Exception e1) {
				JOptionPane.showMessageDialog(frame,"Vehicle Serial Number already exists");
				UpdateAdaptor.close();
			} 
			QueryAdaptor.close();
		}
	}
}
