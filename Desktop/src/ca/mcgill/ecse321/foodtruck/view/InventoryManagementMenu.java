package ca.mcgill.ecse321.foodtruck.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.NumberFormat;
import java.util.HashMap;
import java.util.Iterator;

import javax.swing.JFrame;
import javax.swing.ButtonGroup;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JPanel;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JButton;
import javax.swing.border.BevelBorder;

import ca.mcgill.ecse321.foodtruck.application.FoodTruckManager.Permissions;
import ca.mcgill.ecse321.foodtruck.controllers.InvalidInputException;
import ca.mcgill.ecse321.foodtruck.controllers.InventoryController;
import ca.mcgill.ecse321.foodtruck.model.InventoryManager;
import ca.mcgill.ecse321.foodtruck.model.Item;

import java.awt.Color;
import javax.swing.JRadioButton;
import java.awt.FlowLayout;

public class InventoryManagementMenu extends JPanel{

	private JTextField nameEditF;
	private JFormattedTextField quantityEditF;
	private JTextField nameAddF;
	private JFormattedTextField quantityAddF;
	
	private JComboBox<String> itemsComboBox;

	JLabel addErrorL;
	JLabel editErrorL;
	
	JRadioButton equipmentEditRdio;
	JRadioButton ingredientEditRdio;
	
	JRadioButton equipmentAddRdio;
	JRadioButton ingredientAddRdio;
	
	JButton editBtn;
	
	private boolean editing = false;
	private HashMap<Integer, Item> items;
	
	int selectedItem =-1;
	
	JFrame parentF;
	private JLabel addItemDescL;
	
	Permissions permissionLevel;
	
	JPanel addItemPanel;
	/**
	 * Create the application.
	 */
	public InventoryManagementMenu(JFrame parentFrame,Permissions perm) {
		super();
		permissionLevel=perm;
		parentF = parentFrame;
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		
		
		ButtonGroup addRdioGroup = new ButtonGroup();
		ButtonGroup editRdioGroup = new ButtonGroup();
	
		
		this.setBounds(100, 100, 451, 288);
		
		addItemPanel = new JPanel();
		addItemPanel.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		
		JPanel editPanel = new JPanel();
		editPanel.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		
		itemsComboBox = new JComboBox();
		itemsComboBox.setToolTipText("list of items");
		itemsComboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				selectedItem = itemsComboBox.getSelectedIndex();
				setFieldsEdit();
				if(editing)
				{
					toggleEdit();
				}
			}
		});
		
		JLabel nameEditL = new JLabel("Name");
		
		JLabel quantityEditL = new JLabel("Quantity");
		
		
		
		nameEditF = new JTextField();
		nameEditF.setColumns(10);
		
		quantityEditF = new JFormattedTextField(NumberFormat.getNumberInstance());
		quantityEditF.setValue(0);		
		quantityEditF.setColumns(10);
		
		editBtn = new JButton("Edit Item");
		editBtn.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				toggleEdit();
				
			}
			
		});
		
		editErrorL = new JLabel("");
		editErrorL.setForeground(Color.RED);
		
		equipmentEditRdio = new JRadioButton("Equipment");
		
		ingredientEditRdio = new JRadioButton("Ingredient");
		GroupLayout gl_editPanel = new GroupLayout(editPanel);
		gl_editPanel.setHorizontalGroup(
			gl_editPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_editPanel.createSequentialGroup()
					.addGap(21)
					.addGroup(gl_editPanel.createParallelGroup(Alignment.LEADING)
						.addComponent(itemsComboBox, GroupLayout.PREFERRED_SIZE, 384, GroupLayout.PREFERRED_SIZE)
						.addGroup(gl_editPanel.createSequentialGroup()
							.addGap(6)
							.addGroup(gl_editPanel.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_editPanel.createSequentialGroup()
									.addComponent(nameEditL, GroupLayout.PREFERRED_SIZE, 36, GroupLayout.PREFERRED_SIZE)
									.addGap(18)
									.addComponent(nameEditF, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(equipmentEditRdio, GroupLayout.PREFERRED_SIZE, 99, GroupLayout.PREFERRED_SIZE)
									.addGap(6)
									.addComponent(ingredientEditRdio, GroupLayout.PREFERRED_SIZE, 96, GroupLayout.PREFERRED_SIZE))
								.addGroup(gl_editPanel.createSequentialGroup()
									.addPreferredGap(ComponentPlacement.RELATED)
									.addGroup(gl_editPanel.createParallelGroup(Alignment.LEADING)
										.addComponent(editErrorL, GroupLayout.PREFERRED_SIZE, 378, GroupLayout.PREFERRED_SIZE)
										.addGroup(gl_editPanel.createSequentialGroup()
											.addComponent(quantityEditL, GroupLayout.PREFERRED_SIZE, 54, GroupLayout.PREFERRED_SIZE)
											.addPreferredGap(ComponentPlacement.UNRELATED)
											.addComponent(quantityEditF, GroupLayout.PREFERRED_SIZE, 118, GroupLayout.PREFERRED_SIZE)
											.addGap(18)
											.addComponent(editBtn)))))))
					.addContainerGap(17, Short.MAX_VALUE))
		);
		gl_editPanel.setVerticalGroup(
			gl_editPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_editPanel.createSequentialGroup()
					.addGap(5)
					.addComponent(itemsComboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_editPanel.createParallelGroup(Alignment.TRAILING)
						.addGroup(gl_editPanel.createParallelGroup(Alignment.BASELINE)
							.addComponent(nameEditL)
							.addComponent(nameEditF, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_editPanel.createParallelGroup(Alignment.LEADING)
							.addComponent(equipmentEditRdio)
							.addComponent(ingredientEditRdio)))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_editPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(quantityEditL)
						.addComponent(quantityEditF, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(editBtn))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(editErrorL)
					.addGap(22))
		);
		editPanel.setLayout(gl_editPanel);
		
		addErrorL = new JLabel("");
		addErrorL.setForeground(Color.RED);
		
		nameAddF = new JTextField();
		nameAddF.setColumns(10);
		
		JLabel nameAddL = new JLabel("Name");
		
		JLabel quantityAddL = new JLabel("Quantity");
		
		quantityAddF = new JFormattedTextField(NumberFormat.getNumberInstance());
		quantityAddF.setValue(0);		
		quantityAddF.setColumns(10);
		
		JButton addBtn = new JButton("Add Item");
		addBtn.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				addInventoryItem();
				
			}
			
		});
			
		
		
		equipmentAddRdio = new JRadioButton("Equipment");
		
		ingredientAddRdio = new JRadioButton("Ingredient");
		GroupLayout gl_addItemPanel = new GroupLayout(addItemPanel);
		gl_addItemPanel.setHorizontalGroup(
			gl_addItemPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_addItemPanel.createSequentialGroup()
					.addGap(18)
					.addGroup(gl_addItemPanel.createParallelGroup(Alignment.LEADING, false)
						.addGroup(gl_addItemPanel.createSequentialGroup()
							.addComponent(nameAddL, GroupLayout.PREFERRED_SIZE, 36, GroupLayout.PREFERRED_SIZE)
							.addGap(18)
							.addComponent(nameAddF, GroupLayout.PREFERRED_SIZE, 130, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(equipmentAddRdio)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(ingredientAddRdio))
						.addGroup(gl_addItemPanel.createSequentialGroup()
							.addComponent(quantityAddL, GroupLayout.PREFERRED_SIZE, 54, GroupLayout.PREFERRED_SIZE)
							.addGap(12)
							.addComponent(quantityAddF, GroupLayout.PREFERRED_SIZE, 118, GroupLayout.PREFERRED_SIZE)
							.addGap(18)
							.addComponent(addBtn, GroupLayout.PREFERRED_SIZE, 100, GroupLayout.PREFERRED_SIZE))
						.addComponent(addErrorL, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
					.addContainerGap(10, Short.MAX_VALUE))
		);
		gl_addItemPanel.setVerticalGroup(
			gl_addItemPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_addItemPanel.createSequentialGroup()
					.addGap(6)
					.addGroup(gl_addItemPanel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_addItemPanel.createSequentialGroup()
							.addGap(5)
							.addComponent(nameAddL))
						.addGroup(gl_addItemPanel.createParallelGroup(Alignment.BASELINE)
							.addComponent(nameAddF, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addComponent(equipmentAddRdio)
							.addComponent(ingredientAddRdio)))
					.addGap(6)
					.addGroup(gl_addItemPanel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_addItemPanel.createSequentialGroup()
							.addGap(5)
							.addComponent(quantityAddL))
						.addComponent(quantityAddF, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(addBtn))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(addErrorL)
					.addContainerGap(15, Short.MAX_VALUE))
		);
		addItemPanel.setLayout(gl_addItemPanel);
	
		addRdioGroup.add(equipmentAddRdio);
		addRdioGroup.add(ingredientAddRdio);
		
		editRdioGroup.add(equipmentEditRdio);
		editRdioGroup.add(ingredientEditRdio);
		
		addItemDescL = new JLabel("Add new item");
		
		JLabel editItemsDescL = new JLabel("Modify existing items");
		setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		add(addItemDescL);
		add(addItemPanel);
		add(editItemsDescL);
		add(editPanel);
		
		
		items = new HashMap<Integer,Item>();
		
		refreshDisplays();
		

	}
	
	private void refreshEditData()
	{
		nameEditF.setText("");
		quantityEditF.setValue(0);
		ingredientEditRdio.setSelected(true);
		ingredientEditRdio.setEnabled(false);
		equipmentEditRdio.setEnabled(false);
		
		
		this.nameEditF.setEnabled(false);
		this.quantityEditF.setEnabled(false);
		
		items.clear();
		this.itemsComboBox.removeAllItems();
		this.editErrorL.setText("");
		
		editBtn.setText("Edit");
		
		Iterator<Item> itemIterator = InventoryManager.getInstance().getItems().iterator();

		Integer index = 0;
		while (itemIterator.hasNext()) {
			Item i = itemIterator.next();
			items.put(index++, i);
			itemsComboBox.addItem(i.getName());
		}
		selectedItem = -1;
		itemsComboBox.setSelectedIndex(selectedItem);
	}
	
	private void refreshAddData()
	{
		ingredientAddRdio.setSelected(true);
		this.addErrorL.setText("");
		this.nameAddF.setText("");
		this.quantityAddF.setValue(0);

	}
	
	private void refreshDisplays()
	{
		refreshEditData();
		refreshAddData();
		
		boolean visibility =true;
		switch(permissionLevel)
		{
			case Manager:
			case Cook:
				break;
			case Cashier:
				visibility=false;
				break;
		}
		
		editBtn.setVisible(visibility);
		this.addErrorL.setVisible(visibility);
		this.addItemPanel.setVisible(visibility);
		addItemDescL.setVisible(visibility);
		
		
	}
	
	private boolean addInventoryItem()
	{
		String error = "";
		try {
			String type;
			if(this.equipmentAddRdio.isSelected())
			{
				type = "Equipment";
			}
			else
			{
				type = "Ingredient";
			}
			InventoryController.createItem(new Item(this.nameAddF.getText(),type,Integer.parseInt(this.quantityAddF.getText())));
			this.refreshDisplays();
		} catch (InvalidInputException e) {
			this.addErrorL.setText(e.getMessage());
		}
		
		
		//Set error label
		return error.isEmpty();
	}
	
	private void setFieldsEdit()
	{
		if(selectedItem>=0){
			Item i = items.get(selectedItem);		
			this.nameEditF.setText(i.getName());
			this.quantityEditF.setValue(i.getQuantity());
			this.ingredientEditRdio.setSelected(i.getType().equalsIgnoreCase("Ingredient"));
			this.equipmentEditRdio.setSelected(!this.ingredientEditRdio.isSelected());
		}
		else
		{
			this.refreshEditData();
		}
	}
	
	private void toggleEdit()
	{
		if(editing)
		{
			try {
				String type ="";
				if(this.equipmentEditRdio.isSelected())
				{
					type ="Equipment";
				}
				else
				{
					type ="Ingredient";
				}
				
				InventoryController.editItemQuantity(items.get(selectedItem), Integer.parseInt(this.quantityEditF.getText()));
				
				this.editErrorL.setText("");
				this.quantityEditF.setEnabled(false);
				editing = false;
				editBtn.setText("Edit");
			} catch (NumberFormatException e) {
				this.editErrorL.setText("Invalid Quantity Set.");
			} catch (InvalidInputException e) {
				this.editErrorL.setText(e.getMessage());
			}
			
			
		}
		else
		{
			if(InventoryManager.getInstance().numberOfItems()>0 && selectedItem >=0)
			{
				this.editErrorL.setText("");
				this.quantityEditF.setEnabled(true);
				editBtn.setText("Done");
				editing = true;
			}
			else
			{
				this.editErrorL.setText("No item selected to edit");
			}
		}
		
		
	}
}
