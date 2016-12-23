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

public class TestEditEmployee {
	
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
	public void testDefault() {
		

		assertEquals(EmployeeManager.getInstance().getEmployees().contains(alex),true);
		
		Employee seb = new Employee("Sebastian","Andrade", false, 20,25);
		try{
			EmployeeController.EditEmployee(alex, "Sebastian", "Andrade", 20, 25, false);
		}
		catch(InvalidInputException e){
			
		}

		assertEquals(EmployeeController.employeeRegistered("Sebastian", "Andrade"),true);

	}
	
	@Test
	public void testEmptyFirstName()
	{
		try{
			EmployeeController.EditEmployee(alex, "", "Andrade", 20, 25, false);
		}
		catch(InvalidInputException e){
			assertEquals(e.getMessage(),"Employee first name cannot be empty \n");
		}
	}
	
	@Test
	public void testNullFirstName()
	{
		try{
			EmployeeController.EditEmployee(alex, null, "Andrade", 20, 25, false);
		}
		catch(InvalidInputException e){
			assertEquals(e.getMessage(),"Employee first name cannot be empty \n");
		}
	}
	
	@Test
	public void testEmptyLastName()
	{
		try{
			EmployeeController.EditEmployee(alex, "Sebastian", "", 20, 25, false);
		}
		catch(InvalidInputException e){
			assertEquals(e.getMessage(),"Employee last name cannot be empty \n");
		}
	}
	
	@Test
	public void testNullLastName()
	{
		try{
			EmployeeController.EditEmployee(alex, "Sebastian", null, 20, 25, false);
		}
		catch(InvalidInputException e){
			assertEquals(e.getMessage(),"Employee last name cannot be empty \n");
		}
	}
	
	@Test
	public void testNegativeHours()
	{
		try{
			EmployeeController.EditEmployee(alex, "Sebastian", "Andrade", -20, 25, false);
		}
		catch(InvalidInputException e){
			assertEquals(e.getMessage(),"Employee hours must be a positive number. \n");
		}
	}
	
	@Test
	public void testZeroHours()
	{
		try{
			EmployeeController.EditEmployee(alex, "Sebastian", "Andrade", 0, 25, false);
		}
		catch(InvalidInputException e){
		}
		
		assertEquals(EmployeeController.employeeRegistered(new Employee("Sebastian","Andrade",false,0,25)),true);
	}
	
	@Test
	public void testMaxIntHours()
	{
		try{
			EmployeeController.EditEmployee(alex, "Sebastian", "Andrade", Integer.MAX_VALUE, 25, false);
		}
		catch(InvalidInputException e){
		}
		
		assertEquals(EmployeeController.employeeRegistered(new Employee("Sebastian","Andrade",false,Integer.MAX_VALUE,25)),true);
	}
	
	@Test
	public void testNegativeRate()
	{
		try{
			EmployeeController.EditEmployee(alex, "Sebastian", "Andrade", 20, -25, false);
		}
		catch(InvalidInputException e){
			assertEquals(e.getMessage(),"Employee rate must be a positive number.  \n");
		}
	}
	
	@Test
	public void testZeroRate()
	{
		try{
			EmployeeController.EditEmployee(alex, "Sebastian", "Andrade", 20, 0, false);
		}
		catch(InvalidInputException e){
		}
		assertEquals(EmployeeController.employeeRegistered(new Employee("Sebastian","Andrade",false,20,0)),true);
	}
	
	@Test
	public void testMaxIntRate()
	{
		try{
			EmployeeController.EditEmployee(alex, "Sebastian", "Andrade", 20, 0, false);
		}
		catch(InvalidInputException e){
		}
		assertEquals(EmployeeController.employeeRegistered(new Employee("Sebastian","Andrade",false,20,Integer.MAX_VALUE)),true);
	}
	
	
}
