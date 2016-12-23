package ca.mcgill.ecse321.ftm.shiftmanagertest;



import static org.junit.Assert.*;

import java.sql.Date;
import java.sql.Time;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import ca.mcgill.ecse321.foodtruck.controllers.EmployeeController;
import ca.mcgill.ecse321.foodtruck.controllers.InvalidInputException;
import ca.mcgill.ecse321.foodtruck.controllers.InventoryController;
import ca.mcgill.ecse321.foodtruck.controllers.ShiftController;
import ca.mcgill.ecse321.foodtruck.model.ShiftManager;
import ca.mcgill.ecse321.foodtruck.model.Employee;
import ca.mcgill.ecse321.foodtruck.model.EmployeeManager;
import ca.mcgill.ecse321.foodtruck.model.InventoryManager;
import ca.mcgill.ecse321.foodtruck.model.Item;
import ca.mcgill.ecse321.foodtruck.model.Shift;
import ca.mcgill.ecse321.foodtruck.persistence.PersistenceInventoryRegistration;
import ca.mcgill.ecse321.foodtruck.persistence.PersistenceShiftRegistration;



public class TestEditShift {
	
	static ShiftManager previous;
	static Employee alex;
	private static Date aShiftDate;  
	private static Time aStartTime;
	private static Time aEndTime;     

	
	static Shift aShift;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		
		alex = new Employee("Alex","Bratyshkin",true, 15, 13);
		
		
		EmployeeManager m = EmployeeManager.getInstance();
		m.delete();
		
		try{
			EmployeeController.createEmployee(alex);
		}
		catch(InvalidInputException e){
		}
		
		

		aShiftDate  = new Date(System.currentTimeMillis()) ;
		aStartTime   = new Time(System.currentTimeMillis() + 1000 * 60 * 60 ) ;
		aEndTime     = new Time(System.currentTimeMillis() + 1000 * 60 * 60 * 3 ) ;
		
		
		aShift = new Shift(aShiftDate, aStartTime, aEndTime, alex);

		try{
			ShiftController.AddShift(aShift);
		}
		catch(InvalidInputException e){
		}
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		PersistenceShiftRegistration.saveShiftManagementModel(previous);
	}

	@Before
	public void setUp() throws Exception {
		PersistenceInventoryRegistration.loadInventoryManagementModel();
		previous = ShiftManager.getInstance();
		InventoryManager m = InventoryManager.getInstance();
		m.delete();
	}

	@After
	public void tearDown() throws Exception {
		InventoryManager.getInstance().delete();
	}

	
	@Test
	public void testEditShiftStartAfterEnd()
	{
		try{
			ShiftController.editShift(aShift, null ,  aShift.getEndTime(), aShift.getStartTime() , aShift.getEmployee());
		}
		catch(InvalidInputException e){
			assertEquals(e.getMessage(),"Shift start time cannot be before shift end time\n");
		}
		
	}	
	
	
	@Test
	public void testEditShiftEmployeeNotRegistered()
	{
		

		Employee seb = new Employee("Seb","Astian",true, 15, 13);

		
		try{
			ShiftController.editShift(aShift, aShift.getShiftDate() , aShift.getStartTime(), aShift.getEndTime(), seb);
		}
		catch(InvalidInputException e){
			assertEquals(e.getMessage(),"Employee is not registered within the system!\n");
		}
		
	}	
	
	
	@Test
	public void testEditShiftNullDate()
	{
		try{
			ShiftController.editShift(aShift, null , aShift.getStartTime(), aShift.getEndTime(), aShift.getEmployee());
		}
		catch(InvalidInputException e){
			assertEquals(e.getMessage(),"Shift Date cannot be empty\n");
		}
		
	}	
		
	

	@Test
	public void testEditShiftNullStartTime()
	{
		try{
			ShiftController.editShift(aShift, aShift.getShiftDate() , null , aShift.getEndTime(), aShift.getEmployee());
		}
		catch(InvalidInputException e){
			assertEquals(e.getMessage(),"Shift start time cannot be empty\n");
		}
		
	}
	

	@Test
	public void testEditShiftNullEndTime()
	{
		
		try{
			ShiftController.editShift(aShift, aShift.getShiftDate() , aShift.getStartTime(), null, aShift.getEmployee());
		}
		catch(InvalidInputException e){
			assertEquals(e.getMessage(),"Shift end time cannot be empty\n");
		}
		
	}	
		
	
	@Test
	public void testEditShiftNullEmployee()
	{
		
		try{
			ShiftController.editShift(aShift, aShift.getShiftDate(), aShift.getStartTime(), aShift.getEndTime(), null);
		}
		catch(InvalidInputException e){
			assertEquals(e.getMessage(),"Shift cannot be empty\n");
		}
	
	}
	
}
