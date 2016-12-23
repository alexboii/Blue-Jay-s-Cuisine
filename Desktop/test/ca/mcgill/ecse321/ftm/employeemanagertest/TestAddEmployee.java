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

public class TestAddEmployee {

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
	public void testAddNoFirstName(){
		
		try{
			EmployeeController.createEmployee(" ", "Bratyshkin", 15, 13,true);
		}
		catch(InvalidInputException e){
			assertEquals(e.getMessage(),"Employee first name cannot be empty.\n");
		}
		
		try{
			EmployeeController.createEmployee(null, "Bratyshkin", 15, 13, true);
		}
		catch(InvalidInputException e){
			assertEquals(e.getMessage(),"Employee first name cannot be empty.\n");
		}
	}
	
	@Test
	public void testAddNoLastName()
	{
		//Try last name checking
		try{
			EmployeeController.createEmployee("Alexander", null, 15, 13, true);
		}
		catch(InvalidInputException e){
			assertEquals(e.getMessage(),"Employee last name cannot be empty.\n");
		}
		
		try{
			EmployeeController.createEmployee("Alexander", " ", 15, 13, true);
		}
		catch(InvalidInputException e){
			assertEquals(e.getMessage(),"Employee last name cannot be empty.\n");
		}
	}
	
	@Test 
	public void testAddInvalidSalary()
	{
		//Weekly hours check
		try{
			EmployeeController.createEmployee("Alexander", "Bratyshkin", 15, -13, true);
		}
		catch(InvalidInputException e){
			assertEquals(e.getMessage(),"Employee salary must be a positive, non-zero number.\n");
		}
	}
	
	@Test 
	public void testAddInvalidHours()
	{
		//Salary check
		try{
			EmployeeController.createEmployee("Alexander", "Bratyshkin", -15, 13, true);
		}
		catch(InvalidInputException e){
			assertEquals(e.getMessage(),"Employee hours must be a positive, non-zero number.\n");
		}

	}
	
	@Test
	public void testAddDuplicateEmployees()
	{
		try{
			EmployeeController.createEmployee("Alexander", "Bratyshkin", 15, 13, true);
		}
		catch(InvalidInputException e){
			assertEquals(e.getMessage(),"Cannot add duplicate employee.\n");
		}
		
		try{
			EmployeeController.createEmployee("Alexander", "Bratyshkin", 15, 13, true);
		}
		catch(InvalidInputException e){
			assertEquals(e.getMessage(),"Cannot add duplicate employee.\n");
		}
	}
	
	

}
