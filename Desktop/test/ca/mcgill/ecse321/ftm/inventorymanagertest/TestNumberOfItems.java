package ca.mcgill.ecse321.ftm.inventorymanagertest;


import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import ca.mcgill.ecse321.foodtruck.controllers.InvalidInputException;
import ca.mcgill.ecse321.foodtruck.controllers.InventoryController;
import ca.mcgill.ecse321.foodtruck.model.InventoryManager;
import ca.mcgill.ecse321.foodtruck.model.Item;
import ca.mcgill.ecse321.foodtruck.persistence.PersistenceInventoryRegistration;

public class TestNumberOfItems {
	
	static InventoryManager previous;
	static Item potato = new Item("Potato","Ingredient",10);
	static Item stove = new Item("Stove","Equipment",10);
	static Item cheese = new Item("Cheese","Ingredient",10);


	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		PersistenceInventoryRegistration.saveInventoryManagementModel(previous);
	}

	@Before
	public void setUp() throws Exception {
		PersistenceInventoryRegistration.loadInventoryManagementModel();
		previous = InventoryManager.getInstance();
		InventoryManager m = InventoryManager.getInstance();
		m.delete();
		try{
			InventoryController.createItem(potato);
			InventoryController.createItem(stove);
			InventoryController.createItem(cheese);	
		}
		catch(InvalidInputException e){
		}
	}

	@After
	public void tearDown() throws Exception {
		InventoryManager.getInstance().delete();
	}
	

	@Test
	public void testNumberOfItems() {
		assertEquals(previous.numberOfItems(),3);
	}

	
	@Test
	public void testHasItems() {
		assertEquals(previous.hasItems(),true);
	}
	
	@Test
	public void testIndexOfItem() {
		assertEquals(previous.indexOfItem(potato), 0);
	}

	@Test
	public void testItemExists() {
		assertEquals(InventoryController.itemExists(potato),true);
	}
	
}
