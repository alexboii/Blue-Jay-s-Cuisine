package ca.mcgill.ecse321.foodtruck.view;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.sql.Date;
import java.sql.Time;
import java.text.NumberFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JPanel;

import ca.mcgill.ecse321.foodtruck.controllers.InvalidInputException;
import ca.mcgill.ecse321.foodtruck.controllers.MenuController;
import ca.mcgill.ecse321.foodtruck.controllers.TransactionController;
import ca.mcgill.ecse321.foodtruck.model.MenuItem;
import ca.mcgill.ecse321.foodtruck.model.MenuManager;
import ca.mcgill.ecse321.foodtruck.model.Transaction;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.BoxLayout;
import java.awt.BorderLayout;

public class OrderManagerMenu extends JPanel implements ActionListener {

	List<MenuItemDisplay> tempItems = new LinkedList<MenuItemDisplay>();
	JLabel totalL;
		
	JPanel itemContainer = new JPanel();
	public OrderManagerMenu()
	{
		setLayout(new BorderLayout(0, 0));
		
		JPanel buttonsPanel = new JPanel();
		add(buttonsPanel, BorderLayout.SOUTH);
		
		JButton btnNewButton = new JButton("Add Menu Item");
		buttonsPanel.add(btnNewButton);
		btnNewButton.addActionListener(this);
		
		JButton btnWeeklyFavorites = new JButton("Weekly Favorites");
		buttonsPanel.add(btnWeeklyFavorites);
		btnWeeklyFavorites.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				PopularityReportMenu review =new PopularityReportMenu();
				review.setVisible(true);
				
			}
			
		});
		
		JButton btnRegisterOrder = new JButton("Register Order");
		buttonsPanel.add(btnRegisterOrder);
		btnRegisterOrder.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				AddOrder();
			}
			
		});
		
		JPanel totalP = new JPanel();
		add(totalP, BorderLayout.NORTH);
		
		JLabel totalTextL = new JLabel("Total");
		totalP.add(totalTextL);
		
		totalL = new JLabel("");
		totalP.add(totalL);
		
		JScrollPane scrollPane = new JScrollPane(itemContainer);
		itemContainer.setLayout(new BoxLayout(itemContainer,BoxLayout.Y_AXIS));
		add(scrollPane, BorderLayout.CENTER);
		
	}
	
	private void AddOrder()
	{
		List<MenuItem> menuItems = new LinkedList<MenuItem>();
		List<Integer> quantities = new LinkedList<Integer>();
		
		Iterator<MenuItemDisplay> iterator = tempItems.iterator();
		
		double total =0;
		while(iterator.hasNext())
		{
			MenuItemDisplay miDisplay = iterator.next();
			menuItems.add(miDisplay.getSelectedItem());
			quantities.add(miDisplay.getQuantity());
			total+= miDisplay.getSelectedItem().getPrice() * miDisplay.getQuantity();
		}
		try {
			TransactionController.AddTransaction(new Transaction(new Date(new Time(Calendar.getInstance().getTime().getTime()).getTime()),new Time(Calendar.getInstance().getTime().getTime()),total),menuItems,quantities);
			
			clearTable();
		} catch (InvalidInputException e) {
			JOptionPane.showMessageDialog(null,e.getMessage());
			// TODO Error
		}
	}
	

	private void refreshTable()
	{
		itemContainer.removeAll();
		
		for(MenuItemDisplay mN:tempItems)
		{
			itemContainer.add(mN);
		}
		
		itemContainer.repaint();
		itemContainer.revalidate();
	}
	
	private void clearTable()
	{
		tempItems.clear();
		refreshTable();
	}
	
	@Override
	public void actionPerformed(ActionEvent e){
		if(MenuManager.getInstance().getMenu_items().size()!=0){
			tempItems.add(new MenuItemDisplay(this));
			refreshTable();
		}
		else
		{
			JOptionPane.showMessageDialog(null, "No Menu Items Are Available...");
		}
	}
	
	private void updatePrice(){
		double totalPrice =0;
		for(MenuItemDisplay mN:tempItems)
		{
			MenuItem m = mN.getSelectedItem();
			double price = m.getPrice();
			Integer quantity = mN.getQuantity();
			
			totalPrice += quantity*price;
		}
		
		this.totalL.setText(totalPrice+"$");
	}
	
	private class MenuItemDisplay extends JPanel implements ActionListener, PropertyChangeListener{
		JComboBox itemList;
		JButton btnRemove;
		
		
		private int selectedItem = 0;
		private HashMap<Integer, MenuItem> selectableItems;
		private JFormattedTextField quantityF;
		
		OrderManagerMenu parent;
		
		public MenuItemDisplay(OrderManagerMenu menu) {
			super();
			
			parent =menu;
			itemList = new JComboBox();
			
			btnRemove = new JButton("Remove");
			btnRemove.addActionListener(this);
			quantityF = new JFormattedTextField(NumberFormat.getNumberInstance());
			quantityF.setColumns(10);
			
			setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
			add(itemList);
			add(quantityF);
			add(btnRemove);
			
			itemList.addPropertyChangeListener(this);
			quantityF.addPropertyChangeListener(this);
			btnRemove.addActionListener(this);
			
			selectableItems = new HashMap<Integer,MenuItem>();
			refreshList();

			this.setVisible(true);
		}
		
		
		private void refreshList()
		{
			selectableItems.clear();
			itemList.removeAllItems();
			
			Iterator<MenuItem> itInventory = MenuManager.getInstance().getMenu_items().iterator();
			int index = 0;
			while(itInventory.hasNext())
			{
				MenuItem next = itInventory.next();
				
				if(MenuController.MenuIsPurchasable(next)){
					selectableItems.put(index++, next);
					itemList.addItem(next.getName() + " ("+next.getPrice()+"$)");
				}
				
			}
			itemList.setSelectedIndex(selectedItem);
			quantityF.setValue(0);

		}
		
		public MenuItem getSelectedItem()
		{
			return selectableItems.get(itemList.getSelectedIndex());
		}
		
		public int getQuantity()
		{
			if(quantityF.getText().trim().length()==0)
				return 0;
			
			return (int)Float.parseFloat(quantityF.getText());
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			parent.tempItems.remove(this);
			propertyChange(null);
			parent.refreshTable();
			
		}

		@Override
		public void propertyChange(PropertyChangeEvent evt) {
			parent.updatePrice();
			
		}
		
	}
}
