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

public class TestAddInventory {

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
	public void testAdd() {
		
		Item pickle = new Item("Pickles","Ingredient",10);
		try{
			InventoryController.createItem(pickle);
		}
		catch(InvalidInputException e){
		}
		assertEquals(InventoryController.itemExists(pickle),true);
	}

	@Test
	public void testEmptyName() {
		
		Item pickle = new Item("","Ingredient",10);
		try{
			InventoryController.createItem(pickle);
		}
		catch(InvalidInputException e){
			assertEquals(e.getMessage(),"Item name cannot be empty\n");
		}
	}
	

	@Test
	public void testNullName() {
		
		Item pickle = new Item(null,"Ingredient",10);
		try{
			InventoryController.createItem(pickle);
		}
		catch(InvalidInputException e){
			assertEquals(e.getMessage(),"Item name cannot be empty\n");
		}
	}
	
	@Test
	public void testEmptyType() {
		
		Item pickle = new Item("Pickles","",10);
		try{
			InventoryController.createItem(pickle);
		}
		catch(InvalidInputException e){
			assertEquals(e.getMessage(),"Item type cannot be empty\n");
		}
	}
	
	@Test
	public void testNullType() {
		
		Item pickle = new Item("Pickles",null,10);
		try{
			InventoryController.createItem(pickle);
		}
		catch(InvalidInputException e){
			assertEquals(e.getMessage(),"Item type cannot be empty\n");
		}
	}
	
	@Test
	public void testNegativeQuantity() {
		
		Item pickle = new Item("Pickles","Ingredient",-10);
		try{
			InventoryController.createItem(pickle);
		}
		catch(InvalidInputException e){
			assertEquals(e.getMessage(),"Item quantity cannot be negative\n");
		}
	}
	
	@Test
	public void testZeroQuantity() {
		
		Item pickle = new Item("Pickles","Ingredient",0);
		try{
			InventoryController.createItem(pickle);
		}
		catch(InvalidInputException e){
		}
		
		assertEquals(InventoryController.itemExists(pickle),true);
		
		try {
			InventoryController.removeItem(pickle);
		} catch (InvalidInputException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assertEquals(InventoryController.itemExists(pickle),false);

	}
	
	@Test
	public void testMaxQuantity() {
		
		Item pickle = new Item("Pickles","Ingredient",Integer.MAX_VALUE);
		try{
			InventoryController.createItem(pickle);
		}
		catch(InvalidInputException e){
		}
		
		assertEquals(InventoryController.itemExists(pickle),true);
		
		try {
			InventoryController.removeItem(pickle);
		} catch (InvalidInputException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assertEquals(InventoryController.itemExists(pickle),false);
	}
	
	@Test
	public void testDuplicate() {
		
		Item pickle = new Item("Pickles","Ingredient",0);
		try{
			InventoryController.createItem(pickle);
		}
		catch(InvalidInputException e){
		}
		
		assertEquals(InventoryController.itemExists(pickle),true);
		
		try{
			InventoryController.createItem(pickle);
		}
		catch(InvalidInputException e){
			assertEquals(e.getMessage(),"Cannot have duplicate items\n");
		}
		
		assertEquals(InventoryController.itemExists(pickle),true);

	}
}
