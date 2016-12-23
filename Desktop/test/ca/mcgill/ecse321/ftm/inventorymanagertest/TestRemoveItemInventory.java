package ca.mcgill.ecse321.ftm.inventorymanagertest;



import static org.junit.Assert.assertEquals;

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


public class TestRemoveItemInventory {

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
	public void testRemoveItem() {
		
		try {
			InventoryController.removeItem(potato);
		} catch (InvalidInputException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assertEquals(InventoryController.itemExists(potato),false);
	}


}
