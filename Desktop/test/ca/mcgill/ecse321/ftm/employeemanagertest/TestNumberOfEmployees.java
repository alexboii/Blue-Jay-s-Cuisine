package ca.mcgill.ecse321.ftm.employeemanagertest;



import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import ca.mcgill.ecse321.foodtruck.model.Employee;
import ca.mcgill.ecse321.foodtruck.model.EmployeeManager;
import ca.mcgill.ecse321.foodtruck.model.InventoryManager;
import ca.mcgill.ecse321.foodtruck.persistence.PersistenceEmployeeRegistration;
import ca.mcgill.ecse321.foodtruck.persistence.PersistenceInventoryRegistration;
import ca.mcgill.ecse321.foodtruck.controllers.EmployeeController;
import ca.mcgill.ecse321.foodtruck.controllers.InvalidInputException;


public class TestNumberOfEmployees {
	
	
	static EmployeeManager previous;
	static Employee alex = new Employee("Alex","Bratyshkin",true, 15, 13);
	static Employee pelie = new Employee("Pelie","McMuter",true, 15, 12);
	static Employee lowther = new Employee("Allister","Lowther",true, 15, 13);
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		PersistenceEmployeeRegistration.saveEmployeeManagementModel(previous);
	}

	
	@Before
	public void setUp() throws Exception {
		PersistenceEmployeeRegistration.loadEmployeeManagementModel();
		previous = EmployeeManager.getInstance();
		EmployeeManager m = EmployeeManager.getInstance();
		m.delete();
		
		try{
			EmployeeController.createEmployee(alex);
			EmployeeController.createEmployee(pelie);
			EmployeeController.createEmployee(lowther);
		}
		catch(InvalidInputException e){
		}
	}

	@After
	public void tearDown() throws Exception {
		EmployeeManager.getInstance().delete();
	}


	@Test
	public void testNumberOfEmployees(){
			assertEquals(previous.numberOfEmployees(),0 );
	}
	
	@Test
	public void testHasEmployees(){	
			assertEquals(previous.hasEmployees(), true);

	}
	
	
}
