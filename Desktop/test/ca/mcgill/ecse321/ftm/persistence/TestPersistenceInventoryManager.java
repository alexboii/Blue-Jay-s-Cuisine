package ca.mcgill.ecse321.ftm.persistence;

import static org.junit.Assert.*;

import java.io.File;


import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ca.mcgill.ecse321.foodtruck.model.Item;

import ca.mcgill.ecse321.foodtruck.model.InventoryManager;

import ca.mcgill.ecse321.foodtruck.controllers.InventoryController;

import ca.mcgill.ecse321.foodtruck.controllers.InvalidInputException;
import ca.mcgill.ecse321.foodtruck.persistence.PersistenceEmployeeRegistration;
import ca.mcgill.ecse321.foodtruck.persistence.PersistenceInventoryRegistration;
import ca.mcgill.ecse321.foodtruck.persistence.PersistenceXStream;


public class TestPersistenceInventoryManager {
	
	@Before
	public void setUp()  {

		Item potato = new Item("Potato","Ingredient",10);
		Item stove = new Item("Stove","Equipment",10);
		Item kale = new Item("Kale","Ingredient",4);

		
		try {
			InventoryController.createItem(potato);
		} catch (InvalidInputException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

		
		try {
			InventoryController.createItem(stove);
		} catch (InvalidInputException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		

		try {
			InventoryController.createItem(kale);
		} catch (InvalidInputException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	@After
	public void tearDown() throws Exception {

		InventoryManager im = InventoryManager.getInstance();
		im.delete();
	}

	@Test
	public void test() {
		InventoryManager im = InventoryManager.getInstance();
		PersistenceXStream.setFilename(
				"test" + File.separator + "ca" + File.separator + "mcgill" + File.separator + "ecse321" + File.separator
						+ "ftm" + File.separator + "persistence" + File.separator + "data.xml");
		PersistenceXStream.setAlias("item", Item.class);
		PersistenceXStream.setAlias("manager", InventoryManager.class);
		
		if (!PersistenceInventoryRegistration.saveInventoryManagementModel(im)) {
			fail("Could not save file");
		}

		im.delete();
		assertEquals(0, im.getItems().size());

		
		PersistenceEmployeeRegistration.loadEmployeeManagementModel();

		im = InventoryManager.getInstance();
		//em = (EmployeeManager) PersistenceXStream.loadFromXMLwithXStream();
		if (im == null) {
			fail("Could not load file");
		}

		assertEquals(3, im.getItems().size());
		

		assertEquals(im.getItem(0).getName(),"Potato");
		assertEquals(im.getItem(0).getType(),"Ingredient");
		assertTrue(im.getItem(0).getQuantity() == 10);

		assertEquals(im.getItem(1).getName(),"Stove");
		assertEquals(im.getItem(1).getType(),"Equipment");	
		assertTrue(im.getItem(1).getQuantity() ==10);

		assertEquals(im.getItem(2).getName(),"Kale");
		assertEquals(im.getItem(2).getType(),"Ingredient");
		assertTrue(im.getItem(2).getQuantity()==4);


	}
}
