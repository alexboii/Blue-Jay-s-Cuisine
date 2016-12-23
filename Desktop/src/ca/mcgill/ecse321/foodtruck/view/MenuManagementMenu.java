package ca.mcgill.ecse321.foodtruck.view;

import javax.swing.JPanel;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.NumberFormat;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;

import ca.mcgill.ecse321.foodtruck.application.FoodTruckManager.Permissions;
import ca.mcgill.ecse321.foodtruck.controllers.InvalidInputException;
import ca.mcgill.ecse321.foodtruck.controllers.MenuController;
import ca.mcgill.ecse321.foodtruck.model.InventoryManager;
import ca.mcgill.ecse321.foodtruck.model.Item;
import ca.mcgill.ecse321.foodtruck.model.MenuItem;
import ca.mcgill.ecse321.foodtruck.model.MenuManager;

import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JScrollPane;
import javax.swing.border.BevelBorder;

public class MenuManagementMenu extends JPanel implements ActionListener{
	
	
	JComboBox removeItemComboB;
	
	private JTextField nameF;
	private JTextField descF;

	JScrollPane ingredientsPane;
	JPanel holderPane = new JPanel();
	
	public List<ItemDisplayPanel> tempIngredients;
	private JFormattedTextField priceF;
	
	HashMap<Integer,MenuItem> displayedItems = new HashMap<Integer, MenuItem>();

	Permissions permissionLevel;
	
	
	JPanel addPanel;
	JLabel lblAddNewMenu;
	JLabel lblRemoveMenuItem;
	JButton btnRemoveMenu;
	
	public MenuManagementMenu(Permissions perm) {
		
		permissionLevel = perm;
				
		tempIngredients = new LinkedList<ItemDisplayPanel>();
		
		addPanel = new JPanel();
		addPanel.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		
		JPanel removePanel = new JPanel();
		removePanel.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		
		lblAddNewMenu = new JLabel("Add New Menu Item");
		
		lblRemoveMenuItem = new JLabel("Remove Menu Item");
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING, false)
							.addComponent(addPanel, Alignment.LEADING, 0, 0, Short.MAX_VALUE)
							.addComponent(removePanel, Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 427, Short.MAX_VALUE))
						.addComponent(lblAddNewMenu)
						.addComponent(lblRemoveMenuItem, GroupLayout.PREFERRED_SIZE, 126, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(20, Short.MAX_VALUE))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(18)
					.addComponent(lblAddNewMenu)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(addPanel, GroupLayout.PREFERRED_SIZE, 280, GroupLayout.PREFERRED_SIZE)
					.addGap(28)
					.addComponent(lblRemoveMenuItem)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(removePanel, GroupLayout.PREFERRED_SIZE, 50, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(26, Short.MAX_VALUE))
		);
		
		JLabel menuName = new JLabel("Name");
		
		JLabel lblDescription = new JLabel("Description");
		
		nameF = new JTextField();
		nameF.setColumns(10);
		
		descF = new JTextField();
		descF.setColumns(10);
		
		JButton btnAdd = new JButton("Add");
		btnAdd.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				List<Item> ingredientsToAdd = new LinkedList<Item>();
				List<Integer> quantitiesToAdd = new LinkedList<Integer>();
				

					for(ItemDisplayPanel p:tempIngredients)
					{
						ingredientsToAdd.add(p.getSelectedItem());
						quantitiesToAdd.add((int)p.getQuantity());			
					}
					
					try {
						MenuController.createMenuItem(new MenuItem(nameF.getText(),descF.getText(),Double.parseDouble(priceF.getText()),true),ingredientsToAdd,quantitiesToAdd);
						clearAdd();
					} catch (InvalidInputException e1) {
						JOptionPane.showMessageDialog(null,e1.getMessage());
					}
			}
			
		});
		
		JLabel lblComponents = new JLabel("Ingredients");
		
		JButton newIngredientBtn = new JButton("New Ingredient");
		newIngredientBtn.addActionListener(this);
		ingredientsPane = new JScrollPane(holderPane);

		holderPane.setAutoscrolls(true);
		holderPane.setLayout(new BoxLayout(holderPane,BoxLayout.Y_AXIS));
		ingredientsPane.setPreferredSize(new Dimension( 650,400));
		
		JLabel priceL = new JLabel("Price");
		
		priceF = new JFormattedTextField(NumberFormat.getNumberInstance());
		priceF.setColumns(10);
		priceF.setValue(0);

		
		GroupLayout gl_addPanel = new GroupLayout(addPanel);
		gl_addPanel.setHorizontalGroup(
			gl_addPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_addPanel.createSequentialGroup()
					.addGap(20)
					.addGroup(gl_addPanel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_addPanel.createSequentialGroup()
							.addComponent(newIngredientBtn)
							.addPreferredGap(ComponentPlacement.RELATED, 174, Short.MAX_VALUE)
							.addComponent(btnAdd))
						.addComponent(lblComponents, GroupLayout.PREFERRED_SIZE, 103, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblDescription)
						.addComponent(descF, 388, 388, 388)
						.addGroup(gl_addPanel.createSequentialGroup()
							.addGroup(gl_addPanel.createParallelGroup(Alignment.LEADING)
								.addComponent(menuName)
								.addComponent(nameF, GroupLayout.PREFERRED_SIZE, 149, GroupLayout.PREFERRED_SIZE))
							.addGap(43)
							.addGroup(gl_addPanel.createParallelGroup(Alignment.LEADING)
								.addComponent(priceF, GroupLayout.PREFERRED_SIZE, 123, GroupLayout.PREFERRED_SIZE)
								.addComponent(priceL)))
						.addGroup(gl_addPanel.createSequentialGroup()
							.addGap(6)
							.addComponent(ingredientsPane, GroupLayout.PREFERRED_SIZE, 371, GroupLayout.PREFERRED_SIZE)))
					.addGap(47))
		);
		gl_addPanel.setVerticalGroup(
			gl_addPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_addPanel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_addPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(menuName)
						.addComponent(priceL))
					.addGap(8)
					.addGroup(gl_addPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(nameF, GroupLayout.PREFERRED_SIZE, 19, GroupLayout.PREFERRED_SIZE)
						.addComponent(priceF, GroupLayout.PREFERRED_SIZE, 19, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(lblDescription)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(descF, GroupLayout.PREFERRED_SIZE, 51, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(lblComponents)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(ingredientsPane, GroupLayout.PREFERRED_SIZE, 84, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_addPanel.createParallelGroup(Alignment.LEADING)
						.addComponent(newIngredientBtn)
						.addComponent(btnAdd))
					.addGap(31))
		);
		addPanel.setLayout(gl_addPanel);
		
		removeItemComboB = new JComboBox();
		
		btnRemoveMenu = new JButton("Remove");
		btnRemoveMenu.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				removeMenuItem();
				
			}
			
		});
		GroupLayout gl_removePanel = new GroupLayout(removePanel);
		gl_removePanel.setHorizontalGroup(
			gl_removePanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_removePanel.createSequentialGroup()
					.addGap(16)
					.addComponent(removeItemComboB, GroupLayout.PREFERRED_SIZE, 293, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(btnRemoveMenu, GroupLayout.PREFERRED_SIZE, 91, GroupLayout.PREFERRED_SIZE)
					.addGap(15))
		);
		gl_removePanel.setVerticalGroup(
			gl_removePanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_removePanel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_removePanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(removeItemComboB, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnRemoveMenu))
					.addContainerGap(16, Short.MAX_VALUE))
		);
		removePanel.setLayout(gl_removePanel);
		setLayout(groupLayout);
		
		refreshTable();
		refreshRemoveList();
		setPermissionVisibility();
	}
	
	private void setPermissionVisibility(){
		
		boolean visibility = true;
		switch(permissionLevel)
		{
			case Cashier:
				visibility = false;
				break;
			case Cook:
			case Manager:
				break;
		}
		
		btnRemoveMenu.setVisible(visibility);
		lblAddNewMenu.setVisible(visibility);
		addPanel.setVisible(visibility);
		
	}
	
	private void refreshTable()
	{
		holderPane.removeAll();
		
		for(ItemDisplayPanel pane:tempIngredients)
		{
			holderPane.add(pane);
		}

		holderPane.repaint();
		holderPane.revalidate();
		ingredientsPane.repaint();
		ingredientsPane.revalidate();
		
	}
	
	
	private void refreshRemoveList()
	{
		displayedItems.clear();
		removeItemComboB.removeAllItems();
		int index =0;
		List<MenuItem> menuItems = MenuManager.getInstance().getMenu_items();
		for(MenuItem m:menuItems)
		{
			displayedItems.put(index++, m);
			this.removeItemComboB.addItem(m.getName());
		}
		removeItemComboB.setSelectedIndex(-1);
		
	}
	
	private void removeMenuItem()
	{
		if(displayedItems.size()>0)
		{
			if(removeItemComboB.getSelectedIndex()>=0)
			{
				try {
					System.out.println(displayedItems.get(removeItemComboB.getSelectedIndex()));
					MenuController.removeMenuItem(displayedItems.get(removeItemComboB.getSelectedIndex()));
					refreshRemoveList();
				} catch (InvalidInputException e) {
					JOptionPane.showMessageDialog(null,e.getMessage());
				}
			}
			else
			{
				JOptionPane.showMessageDialog(null,"No Menu Items Registered.");	
			}
		}
	}
	
	private void clearAdd()
	{
		this.nameF.setText("");
		this.priceF.setValue(0);
		this.descF.setText("");
		this.tempIngredients.clear();
		this.refreshTable();
		refreshRemoveList();
		
	}
		
	@Override
	public void actionPerformed(ActionEvent e) {
		
		if(InventoryManager.getInstance().getItems().size()>0)
		{
			tempIngredients.add(new ItemDisplayPanel(this));
			refreshTable();
		}
		else
		{
			JOptionPane.showMessageDialog(null,"No items in your inventory....");
		}
		
	}
	
	private class ItemDisplayPanel extends JPanel implements ActionListener{
		JComboBox itemList;
		JButton btnRemove;
		
		
		private int selectedItem = 0;
		private HashMap<Integer, Item> selectableItems;
		private JFormattedTextField quantityF;
		
		MenuManagementMenu parent;
		
		public ItemDisplayPanel(MenuManagementMenu actionListener) {
			super();
			
			parent =actionListener;
			itemList = new JComboBox();
			
			btnRemove = new JButton("Remove");
			btnRemove.addActionListener(this);
			quantityF = new JFormattedTextField(NumberFormat.getNumberInstance());
			quantityF.setColumns(10);
			
			setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
			add(itemList);
			add(quantityF);
			add(btnRemove);
			
			
			selectableItems = new HashMap<Integer,Item>();
			refreshList();

			this.setVisible(true);
		}
		
		
		private void refreshList()
		{
			selectableItems.clear();
			itemList.removeAllItems();
			
			Iterator<Item> itInventory = InventoryManager.getInstance().getItems().iterator();
			int index= 0;
			while(itInventory.hasNext())
			{
				Item next = itInventory.next();
				if(next.getType().equalsIgnoreCase("Ingredient"))
				{
					selectableItems.put(index++, next);
					itemList.addItem(next.getName() +" ("+next.getQuantity()+")");
				}
			}
			itemList.setSelectedIndex(selectedItem);
			quantityF.setValue(0);

		}
		
		public Item getSelectedItem()
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
			parent.tempIngredients.remove(this);
			parent.refreshTable();
			
		}
		
	}

}
