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

public class TestEditInventory {

	static InventoryManager previous;
	static Item potato = new Item("Potato","Ingredient",10);

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
		}
		catch(InvalidInputException e){
		}
	}

	@After
	public void tearDown() throws Exception {
		InventoryManager.getInstance().delete();
	}
	
	@Test
	public void testEdit() {
		
		Item pickle = new Item("Pickles","Ingredient",10);
		try{
			InventoryController.createItem(pickle);
		}
		catch(InvalidInputException e){
		}
		assertEquals(InventoryController.itemExists(pickle),true);
	}

	@Test
	public void testNegativeQuantity() {
		
		try{
			InventoryController.editItemQuantity(potato, -1);
		}
		catch(InvalidInputException e){
			assertEquals(e.getMessage(),"Quantity must be a positive number. \n");
		}
	}
	
	@Test
	public void testMaxQuantity()
	{
		try{
			InventoryController.editItemQuantity(potato, Integer.MAX_VALUE);
		}
		catch(InvalidInputException e){
		}
		
		assertEquals(InventoryController.itemExists(new Item(potato.getName(),potato.getType(),Integer.MAX_VALUE)),true);
		
		try{
			InventoryController.editItemQuantity(new Item(potato.getName(),potato.getType(),Integer.MAX_VALUE), potato.getQuantity());
		}
		catch(InvalidInputException e){
		}
		
		assertEquals(InventoryController.itemExists(potato),true);

	}
	
}
