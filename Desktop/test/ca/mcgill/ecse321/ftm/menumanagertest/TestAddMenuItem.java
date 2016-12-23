package ca.mcgill.ecse321.ftm.menumanagertest;


import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import ca.mcgill.ecse321.foodtruck.controllers.InvalidInputException;
import ca.mcgill.ecse321.foodtruck.controllers.InventoryController;
import ca.mcgill.ecse321.foodtruck.controllers.MenuController;
import ca.mcgill.ecse321.foodtruck.model.InventoryManager;
import ca.mcgill.ecse321.foodtruck.model.Item;
import ca.mcgill.ecse321.foodtruck.model.MenuItem;
import ca.mcgill.ecse321.foodtruck.model.MenuManager;
import ca.mcgill.ecse321.foodtruck.persistence.PersistenceInventoryRegistration;
import ca.mcgill.ecse321.foodtruck.persistence.PersistenceMenuRegistration;




public class TestAddMenuItem {
	
	MenuItem burger, salad;
	
	
	@Before
	public void setUp()  {
		
		MenuManager mm = MenuManager.getInstance();
		
		Item tomato = new Item("Tomato", "Ingredient", 15);
		Item patty = new Item("Veggie Patty", "Ingredient", 10);
		Item bread = new Item("Bread", "Ingredient", 15);
		Item lettuce = new Item("Lettuce", "Ingredient", 15);
		
		burger = new MenuItem("Burger", "Amazing vegetarian burger", 7, true);
		salad = new MenuItem("Salad", "Great salad for you and your family", 5, true);

		burger.addItem(tomato);
		burger.addItem(patty);
		burger.addItem(bread);
		
		salad.addItem(tomato);
		salad.addItem(lettuce);
		

	}
		
	
	@After
	public void tearDown() throws Exception {

		MenuManager mm = MenuManager.getInstance();
		mm.delete();
	}
	
	
	
	@Test
	public void testAdd() {
		
		MenuManager mm = MenuManager.getInstance();
		
		mm.delete();
		
		try{
			MenuController.createMenuItem(burger, burger.getItems(), burger.getItemQuantity());
		}
		catch(InvalidInputException e){
		}
		assertEquals(mm.hasMenu_items(),true);
	}

	
	@Test
	public void testAddNullMenuItem() {

		try {
			MenuController.createMenuItem(null,  burger.getItems(), burger.getItemQuantity());
		} catch (InvalidInputException e) {
		assertEquals(e.getMessage(), "Menu item's name cannot be empty\n");
		}
		
	}
	
	
	@Test
	public void testAddNullItems() {

		try {
			MenuController.createMenuItem(burger,  null, burger.getItemQuantity());
		} catch (InvalidInputException e) {
		assertEquals(e.getMessage(), "Non Matching number of quantities and inventory items");
		}
		
	}
	
	
	@Test
	public void testAddNullQuantities() {

		try {
			MenuController.createMenuItem(burger,  burger.getItems(), null);
		} catch (InvalidInputException e) {
		assertEquals(e.getMessage(), "Non Matching number of quantities and inventory items");
		}
		
	}
	
	
	
	
	
	
	
	
	
	
	
	

}
