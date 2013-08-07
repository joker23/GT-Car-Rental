package Screens.MemberScreens;

import java.awt.Font;
import java.awt.event.*;

import javax.swing.*;

import Data.QueryAdaptor;
import Data.UpdateAdaptor;
public class PersonalInfoScreen extends JPanel{
	private JLabel fName,lName,mName,title,email,pNum,addr,membershipTitle,
					choosePlan,paymentInfoTitle,nameOnCard,cardNo,cvv,expireDate,billingAddr;
	private JTextField fNamet,lNamet,mNamet,emailt,pNumt,addrt,nameOnCardt,cardNot,cvvt,expireDatet,billingAddrt;
	private JRadioButton occ,freq,daily;
	private JButton next,viewPlan,back;
	private String usn;
	private boolean first;
	private String planID = "Occasional Driver";
	/**
	 * planID:
	 * 	1) Occasional Driver
	 * 	2) Frequent Driver
	 * 	3) Daily Driver
	 * @param frame
	 * @throws Exception 
	 */

	public PersonalInfoScreen(JFrame frame,String Username,boolean firstTime) throws Exception{
		this.usn = Username;
		this.first = firstTime;
		//TODO learn how to do hyperlinks
		//instantiates layout
		GroupLayout layout = new GroupLayout(this);
		this.setLayout(layout);
		layout.setAutoCreateGaps(true);
		layout.setAutoCreateContainerGaps(true);
		//TODO pull from database to fill in the initial JTextFields
		
		/***********************button******************************/
		next = new JButton("Next");
		next.addActionListener(new ButtonListener(frame));
		viewPlan = new JButton("view plan details");
		viewPlan.addActionListener(new lListener(frame,this));
		back = new JButton("Back");
		back.addActionListener(new BackListener(frame));
		
		/***********************titles*******************************/
		title = new JLabel("General Information");
		title.setFont(new Font("Serif", Font.BOLD, 24));
		membershipTitle = new JLabel("Membership Info");
		membershipTitle.setFont(new Font("Serif", Font.BOLD, 24));
		choosePlan = new JLabel("CHOOSE PLAN");
		paymentInfoTitle = new JLabel("Payment Information");
		paymentInfoTitle.setFont(new Font("Serif", Font.BOLD, 24));
		
		
		/***********************labels*******************************/
		fName = new JLabel("First Name: ");
		lName = new JLabel("Last Name: ");
		mName = new JLabel("Middle Initial: ");
		email = new JLabel("Email Address: ");
		pNum = new JLabel("Phone Number: ");
		addr = new JLabel("Addresss: ");
		nameOnCard = new JLabel("Name on Card: ");
		cardNo = new JLabel("Card Number: ");
		cvv = new JLabel("CVV");
		expireDate = new JLabel("Expiration Date: ");
		billingAddr = new JLabel("Billing Address: ");
		
		/**********************text fields**************************/
		fNamet = new JTextField(30);
		lNamet = new JTextField(30);
		mNamet = new JTextField(30);
		emailt = new JTextField(30);
		pNumt = new JTextField(30);
		addrt = new JTextField(30);
		nameOnCardt = new JTextField(30);
		cardNot = new JTextField(30);
		cvvt = new JTextField(30);
		expireDatet = new JTextField(30);
		billingAddrt = new JTextField(30);
		
		/*********************Radio Buttons ************************/
		QuoteListener choice = new QuoteListener();
		occ = new JRadioButton("Occasional Driver");
		occ.addActionListener(choice);
		freq = new JRadioButton("Frequent Driver");
		freq.addActionListener(choice);
		daily = new JRadioButton("Daily Driver");
		daily.addActionListener(choice);
		
		if(!firstTime) pullInfo();
		/*********************layout declaration*********************/
		GroupLayout.SequentialGroup leftToRight = layout.createSequentialGroup();
		
	    GroupLayout.ParallelGroup columnLeft = layout.createParallelGroup();
	    columnLeft.addComponent(fName);
	    columnLeft.addComponent(mName);
	    columnLeft.addComponent(lName);
	    columnLeft.addComponent(email);
	    columnLeft.addComponent(pNum);
	    columnLeft.addComponent(addr);
	    columnLeft.addComponent(occ);
	    columnLeft.addComponent(nameOnCard);
	    columnLeft.addComponent(cardNo);
	    columnLeft.addComponent(cvv);
	    columnLeft.addComponent(expireDate);
	    columnLeft.addComponent(billingAddr);
	    columnLeft.addComponent(back);
		GroupLayout.ParallelGroup columnMid = layout.createParallelGroup(GroupLayout.Alignment.CENTER);
	    columnMid.addComponent(fNamet);
	    columnMid.addComponent(mNamet);
	    columnMid.addComponent(lNamet);
	    columnMid.addComponent(title);
	    columnMid.addComponent(emailt);
	    columnMid.addComponent(pNumt);
	    columnMid.addComponent(addrt);
	    columnMid.addComponent(membershipTitle);
	    columnMid.addComponent(choosePlan);
	    columnMid.addComponent(freq);
	    columnMid.addComponent(paymentInfoTitle);
	    columnMid.addComponent(nameOnCardt);
	    columnMid.addComponent(cvvt);
	    columnMid.addComponent(cardNot);
	    columnMid.addComponent(expireDatet);
	    columnMid.addComponent(billingAddrt);
	    GroupLayout.ParallelGroup columnRight = layout.createParallelGroup();
	    columnRight.addComponent(daily);
	    columnRight.addComponent(viewPlan);
	    columnRight.addComponent(next);
	    leftToRight.addGroup(columnLeft);
	    leftToRight.addGroup(columnMid);
	    leftToRight.addGroup(columnRight);
	    
	    GroupLayout.SequentialGroup topToBottom = layout.createSequentialGroup();
	    topToBottom.addComponent(title);
	    GroupLayout.ParallelGroup fNameRow = layout.createParallelGroup();
	    fNameRow.addComponent(fName);
	    fNameRow.addComponent(fNamet);
	    GroupLayout.ParallelGroup mNameRow = layout.createParallelGroup();
	    mNameRow.addComponent(mName);
	    mNameRow.addComponent(mNamet);
	    GroupLayout.ParallelGroup lNameRow = layout.createParallelGroup();
	    lNameRow.addComponent(lName);
	    lNameRow.addComponent(lNamet);
	    GroupLayout.ParallelGroup emailRow = layout.createParallelGroup();
	    emailRow.addComponent(email);
	    emailRow.addComponent(emailt);
	    GroupLayout.ParallelGroup pNumRow = layout.createParallelGroup();
	    pNumRow.addComponent(pNum);
	    pNumRow.addComponent(pNumt);
	    GroupLayout.ParallelGroup addrRow = layout.createParallelGroup();
	    addrRow.addComponent(addr);
	    addrRow.addComponent(addrt);
	    GroupLayout.ParallelGroup planRow = layout.createParallelGroup();
	    planRow.addComponent(occ);
	    planRow.addComponent(freq);
	    planRow.addComponent(daily);
	    GroupLayout.ParallelGroup nameOnCardRow = layout.createParallelGroup();
	    nameOnCardRow.addComponent(nameOnCard);
	    nameOnCardRow.addComponent(nameOnCardt);
	    GroupLayout.ParallelGroup cardNoRow = layout.createParallelGroup();
	    cardNoRow.addComponent(cardNo);
	    cardNoRow.addComponent(cardNot);
	    GroupLayout.ParallelGroup cvvRow = layout.createParallelGroup();
	    cvvRow.addComponent(cvv);
	    cvvRow.addComponent(cvvt);
	    GroupLayout.ParallelGroup expireRow = layout.createParallelGroup();
	    expireRow.addComponent(expireDate);
	    expireRow.addComponent(expireDatet);
	    GroupLayout.ParallelGroup billingAddrRow = layout.createParallelGroup();
	    billingAddrRow.addComponent(billingAddr);
	    billingAddrRow.addComponent(billingAddrt);
	    GroupLayout.ParallelGroup planningRow = layout.createParallelGroup();
	    planningRow.addComponent(choosePlan);
	    planningRow.addComponent(viewPlan);
	    topToBottom.addGroup(fNameRow);
	    topToBottom.addGroup(mNameRow);
	    topToBottom.addGroup(lNameRow);
	    topToBottom.addGroup(emailRow);
	    topToBottom.addGroup(pNumRow);
	    topToBottom.addGroup(addrRow);
	    topToBottom.addComponent(membershipTitle);
	    topToBottom.addGroup(planningRow);
	    topToBottom.addGroup(planRow);
	    topToBottom.addComponent(paymentInfoTitle);
	    topToBottom.addGroup(nameOnCardRow);
	    topToBottom.addGroup(cardNoRow);
	    topToBottom.addGroup(cvvRow);
	    topToBottom.addGroup(expireRow);
	    topToBottom.addGroup(billingAddrRow);
	    GroupLayout.ParallelGroup row1 = layout.createParallelGroup();
	    row1.addComponent(next);
	    row1.addComponent(back);
	    topToBottom.addGroup(row1);
	    
	    layout.setHorizontalGroup(leftToRight);
	    layout.setVerticalGroup(topToBottom);
	}
	private void pullInfo() throws Exception{
		QueryAdaptor.connect();
		if(QueryAdaptor.findUser(usn)&&!first){
			String[] ans = QueryAdaptor.getMemberInfo(usn);
			fNamet.setText(ans[1]);
			lNamet.setText(ans[2]);
			mNamet.setText(ans[3]);
			addrt.setText(ans[4]);
			emailt.setText(ans[6]);
			pNumt.setText(ans[5]);
			cardNot.setText(ans[7]);
			switch (ans[8]){
				case "Occasional Driver": 
					occ.doClick();
					break;
				case "Frequent Driver":
					freq.doClick();
					break;
				default:
					daily.doClick();
			} if(QueryAdaptor.hasCard(usn)){
				System.out.println("getting card");
				System.out.println(cardNot.getText());
				String[] cardInfo = QueryAdaptor.pullCardInfo(cardNot.getText());
				nameOnCardt.setText(cardInfo[0]);
				cvvt.setText(cardInfo[1]);
				expireDatet.setText(cardInfo[2]);
				billingAddrt.setText(cardInfo[3]);
			}
		}
		QueryAdaptor.close();
	}
	
	/*
	 * quote listener for the radio buttons
	 */
	private class QuoteListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			Object source = e.getSource();
			if(source == occ) planID = "Occasional Driver";
			else if(source == freq) planID = "Frequent Driver";
			else planID = "Daily Driver";
		}
	}
	private class BackListener implements ActionListener{
		JFrame frame;
		public BackListener(JFrame frame){
			this.frame = frame;
		}
		@Override
		public void actionPerformed(ActionEvent e){
			changeScreen(new HomePageScreen(frame,usn),frame);
		}
	}
	/*
	 * buttonListener for the next button
	 */
	private class ButtonListener implements ActionListener{
		JFrame frame;
		public ButtonListener(JFrame frame){
			this.frame = frame;
		}
		@Override
		public void actionPerformed(ActionEvent e){
			if(fNamet.getText().isEmpty()||lNamet.getText().isEmpty()){
				JOptionPane.showMessageDialog(frame,"Some Fields are Empty");
			}else{
				UpdateAdaptor.connect();
				if(first){
					try {
						UpdateAdaptor.makeMemberInfo(usn,fNamet.getText(),lNamet.getText(),mNamet.getText(),
								addrt.getText(),pNumt.getText(),emailt.getText(),cardNot.getText(),planID);
						UpdateAdaptor.createCardInfo(cardNot.getText(),nameOnCardt.getText(),cvvt.getText(),expireDatet.getText(),billingAddrt.getText());
					} catch (Exception e1) {
						e1.printStackTrace();
					} JOptionPane.showMessageDialog(frame,"Member Information Successfully Created!");
				} else{
					try {
						UpdateAdaptor.updateMemberInfo(usn,fNamet.getText(),lNamet.getText(),mNamet.getText(),
								addrt.getText(),pNumt.getText(),emailt.getText(),cardNot.getText(),planID);
						QueryAdaptor.connect();
						if(QueryAdaptor.hasCard(usn)){
							UpdateAdaptor.updateCardInfo(cardNot.getText(),nameOnCardt.getText(),cvvt.getText(),expireDatet.getText(),billingAddrt.getText());
						}else{
							UpdateAdaptor.createCardInfo(cardNot.getText(),nameOnCardt.getText(),cvvt.getText(),expireDatet.getText(),billingAddrt.getText());
						}QueryAdaptor.close();
					} catch (Exception e1) {
						e1.printStackTrace();
					} JOptionPane.showMessageDialog(frame,"Member Information Successfully Changed!");
				} UpdateAdaptor.close();
			}
		}
	}
	/*
	 * buttonListener for the view plan button
	 */
	private class lListener implements ActionListener{
		JFrame frame;
		JPanel panel;
		public lListener(JFrame frame,JPanel panel){
			this.frame = frame;
			this.panel = panel;
		}
		@Override
		public void actionPerformed(ActionEvent e){
			changeScreen(new DrivingPlansScreen(frame,panel),frame);
		}
	}
	private static void changeScreen(JPanel newScreen,JFrame frame){
		frame.getContentPane().removeAll();
		frame.getContentPane().add(newScreen);
		frame.pack();
		frame.setVisible(true);
	}
}
