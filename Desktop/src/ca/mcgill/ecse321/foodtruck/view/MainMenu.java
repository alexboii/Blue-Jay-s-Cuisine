package ca.mcgill.ecse321.foodtruck.view;

import javax.swing.JFrame;

import javax.swing.JTabbedPane;

import ca.mcgill.ecse321.foodtruck.application.FoodTruckManager.Permissions;

import java.awt.Dimension;
import java.awt.BorderLayout;

@SuppressWarnings("serial")
public class MainMenu extends JFrame{
	

	
	public MainMenu(Permissions p){
		
		EmployeeManagerMenu employeeMenu = new EmployeeManagerMenu(p);
		InventoryManagementMenu inventoryMenu = new InventoryManagementMenu(this,p);
		ShiftManagerMenu shiftMenu = new ShiftManagerMenu(p);
		MenuManagementMenu menuItem = new MenuManagementMenu(p);
		OrderManagerMenu orderMenu = new OrderManagerMenu();
		
		getContentPane().setLayout(new BorderLayout(0, 0));
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
				
		tabbedPane.addTab("Employee",null, employeeMenu,
				"Manage your employees"); 
		// Cashier Cook no access
		//Manager has access
		tabbedPane.addTab("Shifts", null, shiftMenu, 
				"Manage your shifts");
		//Manager can add
		//Cook cashier no add or edit button
		tabbedPane.addTab("Inventory",null, inventoryMenu,
                "Manage your inventory"); 
		//Cashier no add menu, disable edit button
		//Manager, Cook can edit
		
		tabbedPane.addTab("Menu", null, menuItem,
				"Manage your menus"); //Manager, Cook
		
		tabbedPane.addTab("Order", null, orderMenu,
				"Order Food"); //Everyone
	   
		getContentPane().add(tabbedPane);
		this.setSize(new Dimension(500,500));
		this.setResizable(false);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		
	}
	
	
	
	
}
