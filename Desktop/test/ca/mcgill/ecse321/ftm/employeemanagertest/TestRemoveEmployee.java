package ca.mcgill.ecse321.ftm.employeemanagertest;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import ca.mcgill.ecse321.foodtruck.controllers.EmployeeController;
import ca.mcgill.ecse321.foodtruck.controllers.InvalidInputException;
import ca.mcgill.ecse321.foodtruck.model.Employee;
import ca.mcgill.ecse321.foodtruck.model.EmployeeManager;
import ca.mcgill.ecse321.foodtruck.model.InventoryManager;
import ca.mcgill.ecse321.foodtruck.persistence.PersistenceEmployeeRegistration;
import ca.mcgill.ecse321.foodtruck.persistence.PersistenceInventoryRegistration;

public class TestRemoveEmployee {

	static EmployeeManager previous;
	static Employee alex = new Employee("Alex","Bratyshkin",true, 15, 13);

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
		}
		catch(InvalidInputException e){
		}
	}

	@After
	public void tearDown() throws Exception {
		EmployeeManager.getInstance().delete();
	}
	
	
	@Test
	public void test() {	
		assertEquals(EmployeeManager.getInstance().getEmployees().contains(alex),true);
		
		try{
			EmployeeController.removeEmployee(alex);
		}
		catch(InvalidInputException e){
			
		}

		assertEquals(EmployeeController.employeeRegistered("Alex", "Bratyhskin"),false);
	}

}
