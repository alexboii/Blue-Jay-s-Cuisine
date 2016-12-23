package ca.mcgill.ecse321.ftm.persistence;



import static org.junit.Assert.*;

import java.sql.Date;
import java.sql.Time;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import ca.mcgill.ecse321.foodtruck.controllers.InvalidInputException;
import ca.mcgill.ecse321.foodtruck.controllers.MenuController;
import ca.mcgill.ecse321.foodtruck.controllers.TransactionController;
import ca.mcgill.ecse321.foodtruck.model.Item;
import ca.mcgill.ecse321.foodtruck.model.MenuItem;
import ca.mcgill.ecse321.foodtruck.model.MenuManager;
import ca.mcgill.ecse321.foodtruck.model.Transaction;
import ca.mcgill.ecse321.foodtruck.model.TransactionManager;
import ca.mcgill.ecse321.foodtruck.persistence.PersistenceMenuRegistration;
import ca.mcgill.ecse321.foodtruck.persistence.PersistenceTransactionRegistration;

public class TestPersistenceTransaction {

	Transaction tr1, tr2;
	
	@Before
	public void setUp()  {
		TransactionManager tm = TransactionManager.getInstance();
		Date TransactionDate1  = new Date(System.currentTimeMillis()) ;
		Time TransactionTime1   = new Time(System.currentTimeMillis() + 1000 * 60 * 60 ) ;
		Double price1 = 20.1;
		
		Date TransactionDate2 = new Date(System.currentTimeMillis()) ;
		Time TransactionTime2   = new Time(System.currentTimeMillis() + 1000 * 60 * 60 ) ;
		Double price2 = 20.1;
		

		MenuManager mm = MenuManager.getInstance();
		
		Item tomato = new Item("Tomato", "Ingredient", 15);
		Item patty = new Item("Veggie Patty", "Ingredient", 10);
		Item bread = new Item("Bread", "Ingredient", 15);
		Item lettuce = new Item("Lettuce", "Ingredient", 15);
		
		MenuItem burger = new MenuItem("Burger", "Amazing vegetarian burger", 7, true);
		MenuItem salad = new MenuItem("Salad", "Great salad for you and your family", 5, true);

		burger.addItem(tomato);
		burger.addItem(patty);
		burger.addItem(bread);
		
		salad.addItem(tomato);
		salad.addItem(lettuce);
		
		try {
			MenuController.createMenuItem(burger,  burger.getItems(), burger.getItemQuantity());
		} catch (InvalidInputException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			MenuController.createMenuItem(salad,  salad.getItems(), salad.getItemQuantity());
		} catch (InvalidInputException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

		tr1 = new Transaction(TransactionDate1, TransactionTime1, price1);
		tr2 = new Transaction(TransactionDate2, TransactionTime2, price2);

		
		try {
			TransactionController.AddTransaction(tr1, mm.getMenu_items(), burger.getItemQuantity());
		} catch (InvalidInputException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			TransactionController.AddTransaction(tr2,  mm.getMenu_items(), salad.getItemQuantity());
		} catch (InvalidInputException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
		
	@After
	public void tearDown() throws Exception {

		MenuManager mm = MenuManager.getInstance();
		mm.delete();
		
		TransactionManager tm = TransactionManager.getInstance();
		tm.delete();
	}
	
	@Test
	public void test() {
		TransactionManager tm = TransactionManager.getInstance();

		tm.delete();
		assertEquals(false, tm.hasTransactions());


		PersistenceTransactionRegistration.loadTransactionManagementModel();

		tm = TransactionManager.getInstance();
		if (tm == null) {
			fail("Could not load file");
		}
		
		assertEquals(true, tm.hasTransactions());
		
	}
		
}
