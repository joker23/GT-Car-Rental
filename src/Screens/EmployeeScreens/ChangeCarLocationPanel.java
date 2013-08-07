package Screens.EmployeeScreens;

import javax.swing.*;

import Data.QueryAdaptor;
import Data.UpdateAdaptor;

import java.awt.*;
import java.awt.event.*;

public class ChangeCarLocationPanel extends JPanel{
	private String[] locationlist = {"Klaus","Institute of Paper Science","CULC","Student Center","Tech Square","COC","Peters Parking Deck"},
			carList;
	private JLabel currLocL,carL,title, typeL,colorL,scL,ttL,chooseLocL;
	private JComboBox currLocBox,carBox,chooseLocBox;
	private JTextField typeT, colorT,scT,ttT;
	private JButton submit;
	
	public ChangeCarLocationPanel(JFrame frame) throws Exception{
		//instantiates layout
		GroupLayout layout = new GroupLayout(this);
	    setLayout(layout);
	    layout.setAutoCreateGaps(true);
	    layout.setAutoCreateContainerGaps(true);
	    /*********************************labels****************************/
	    currLocL = new JLabel("Choose Current Location: ");
	    carL = new JLabel("Choose Car: ");
	    title = new JLabel("Brief Description");
	    title.setFont(new Font("Serif", Font.BOLD, 24));
	    typeL = new JLabel("Car Type: ");
	    colorL = new JLabel("Color: ");
	    scL = new JLabel("Seating Capacity: ");
	    ttL = new JLabel("Transmission Type: ");
	    chooseLocL = new JLabel("Choose New Location: ");
	    /********************************ComboBox***************************/
	    QueryAdaptor.connect();
	    carList = QueryAdaptor.getCarList();
	    QueryAdaptor.close();
	    currLocBox = new JComboBox(locationlist);
	    currLocBox.addActionListener(new LocationListener());
	    carBox = new JComboBox(carList);
	    chooseLocBox = new JComboBox(locationlist);
	    /*******************************textText****************************/
	    typeT = new JTextField(30);
	    colorT = new JTextField(30);
	    scT = new JTextField(30);
	    ttT = new JTextField(30);
	    /*******************************button******************************/
	    submit = new JButton("Submit Changes");
	    submit.addActionListener(new ButtonListener(frame));
	    
	    /********************************layout*****************************/
	    GroupLayout.SequentialGroup leftToRight = layout.createSequentialGroup();
	    GroupLayout.ParallelGroup col1 = layout.createParallelGroup();
	    col1.addComponent(currLocL);
	    col1.addComponent(carL);
	    col1.addComponent(typeL);
	    col1.addComponent(colorL);
	    col1.addComponent(scL);
	    col1.addComponent(ttL);
	    col1.addComponent(chooseLocL);
	    leftToRight.addGroup(col1);	    
	    GroupLayout.ParallelGroup col2 = layout.createParallelGroup();
	    col2.addComponent(title);
	    col2.addComponent(currLocBox);
	    col2.addComponent(carBox);
	    col2.addComponent(chooseLocBox);
	    col2.addComponent(typeT);
	    col2.addComponent(colorT);
	    col2.addComponent(scT);
	    col2.addComponent(ttT);
	    leftToRight.addGroup(col2);
	    leftToRight.addComponent(submit);
	    
	    GroupLayout.SequentialGroup topToBottom = layout.createSequentialGroup();
	    GroupLayout.ParallelGroup row1 = layout.createParallelGroup();
	    row1.addComponent(currLocL);
	    row1.addComponent(currLocBox);
	    topToBottom.addGroup(row1);
	    GroupLayout.ParallelGroup row2 = layout.createParallelGroup();
	    row2.addComponent(carL);
	    row2.addComponent(carBox);
	    topToBottom.addGroup(row2);
	    topToBottom.addComponent(title);
	    GroupLayout.ParallelGroup row3 = layout.createParallelGroup();
	    row3.addComponent(typeL);
	    row3.addComponent(typeT);
	    topToBottom.addGroup(row3);
	    GroupLayout.ParallelGroup row4 = layout.createParallelGroup();
	    row4.addComponent(colorL);
	    row4.addComponent(colorT);
	    topToBottom.addGroup(row4);
	    GroupLayout.ParallelGroup row5 = layout.createParallelGroup();
	    row5.addComponent(scL);
	    row5.addComponent(scT);
	    topToBottom.addGroup(row5);
	    GroupLayout.ParallelGroup row6 = layout.createParallelGroup();
	    row6.addComponent(ttL);
	    row6.addComponent(ttT);
	    topToBottom.addGroup(row6);
	    GroupLayout.ParallelGroup row7 = layout.createParallelGroup();
	    row7.addComponent(chooseLocL);
	    row7.addComponent(chooseLocBox);
	    topToBottom.addGroup(row7);
	    topToBottom.addComponent(submit);
	     
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
				carList = QueryAdaptor.getCarList(selected);
			} catch (Exception e1) {
				e1.printStackTrace();
			} QueryAdaptor.close();
			carBox.removeAllItems();
			for(int i=0;i<carList.length;i++){
				carBox.addItem(carList[i]);
			}
		}	
	}
	private class ButtonListener implements ActionListener{
		Frame frame;
		public ButtonListener(Frame frame){
			this.frame = frame;
		}
		@Override
		public void actionPerformed(ActionEvent e){
			String currloc = (String) currLocBox.getSelectedItem(),
					model = (String) carBox.getSelectedItem(),
					newloc = (String) chooseLocBox.getSelectedItem(),
					type = typeT.getText(),
					color = colorT.getText(),
					seatcap = scT.getText(),
					transtype = ttT.getText();
			QueryAdaptor.connect();
			try {
				if(QueryAdaptor.carExists(newloc,model)){
					JOptionPane.showMessageDialog(frame,"Car model exists in selected location");
				} else{
					UpdateAdaptor.connect();
					UpdateAdaptor.changeCarLocation(currloc, newloc, model, type, color, seatcap, transtype);
					UpdateAdaptor.close();
					if(QueryAdaptor.carExists(newloc,model))
						JOptionPane.showMessageDialog(frame,"Location Successfully Changed!");
					else{
						JOptionPane.showMessageDialog(frame,"Failed to Change Car Location!");
					}
				}
			} catch (Exception e1) {
				e1.printStackTrace();
			} QueryAdaptor.close();
		}
	}
}
