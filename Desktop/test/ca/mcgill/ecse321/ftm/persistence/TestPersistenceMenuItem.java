package ca.mcgill.ecse321.ftm.persistence;

import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import ca.mcgill.ecse321.foodtruck.controllers.InvalidInputException;
import ca.mcgill.ecse321.foodtruck.controllers.MenuController;
import ca.mcgill.ecse321.foodtruck.model.InventoryManager;
import ca.mcgill.ecse321.foodtruck.model.Item;
import ca.mcgill.ecse321.foodtruck.model.MenuItem;
import ca.mcgill.ecse321.foodtruck.model.MenuManager;

import ca.mcgill.ecse321.foodtruck.persistence.PersistenceMenuRegistration;


public class TestPersistenceMenuItem {
	
	
	MenuItem burger, salad;
	
	
	@Before
	public void setUp()  {
		
		MenuManager mm = MenuManager.getInstance();
		
		InventoryManager im = InventoryManager.getInstance();
		
		Item tomato = new Item("Tomato", "Ingredient", 15);
		Item patty = new Item("Veggie Patty", "Ingredient", 10);
		Item bread = new Item("Bread", "Ingredient", 15);
		Item lettuce = new Item("Lettuce", "Ingredient", 15);
		
		
		im.addItem(tomato);
		im.addItem(lettuce);
		im.addItem(patty);
		im.addItem(bread);

		
		
		burger = new MenuItem("Burger", "Amazing vegetarian burger", 7, true);
		salad = new MenuItem("Salad", "Great salad for you and your family", 5, true);

		
		
		
		
		burger.addItem(tomato);
		burger.addItem(patty);
		burger.addItem(bread);
		
		salad.addItem(tomato);
		salad.addItem(lettuce);
		
		
		
//		try {
			//	MenuController.createMenuItem(burger,  burger.getItems(), burger.getItemQuantity());
		//	} catch (InvalidInputException e) {
				// TODO Auto-generated catch block
		//		e.printStackTrace();
		//	}
//			try {
//				MenuController.createMenuItem(salad,  salad.getItems(), salad.getItemQuantity());
//			} catch (InvalidInputException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}

	}
		
	
	@After
	public void tearDown() throws Exception {

		MenuManager mm = MenuManager.getInstance();
		mm.delete();
	}
	
	
	@Test
	public void test() {
		MenuManager mm = MenuManager.getInstance();

		mm.delete();
		assertEquals(0, mm.getMenu_items().size());
	
		

		PersistenceMenuRegistration.loadMenuManagementModel();

		mm = MenuManager.getInstance();
		if (mm == null) {
			fail("Could not load file");
		}
		
		
		
	//	assertEquals(true, mm.hasMenu_items());
		
	}
}
