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
import ca.mcgill.ecse321.foodtruck.model.InventoryManager;
import ca.mcgill.ecse321.foodtruck.model.Item;
import ca.mcgill.ecse321.foodtruck.model.Shift;
import ca.mcgill.ecse321.foodtruck.persistence.PersistenceInventoryRegistration;
import ca.mcgill.ecse321.foodtruck.persistence.PersistenceShiftRegistration;


public class TestAddShift {
	
	
	static ShiftManager previous;
	static Employee alex;
	private static Date aShiftDate;  
	private static Time aStartTime;
	private static Time aEndTime;     

	
	static Shift aShift;
	
	
	

	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		
		alex = new Employee("Alex","Bratyshkin",true, 15, 13);

		aShiftDate  = new Date(System.currentTimeMillis()) ;
		aStartTime   = new Time(System.currentTimeMillis() + 1000 * 60 * 60 ) ;
		aEndTime     = new Time(System.currentTimeMillis() + 1000 * 60 * 60 * 3 ) ;
		
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
	public void testAddShiftNullShift()
	{

		aShift = null;
		try{
			ShiftController.AddShift(aShift);
		}
		catch(InvalidInputException e){
			assertEquals(e.getMessage(),"Shift cannot be empty\n");
		}
		
	}

	
	@Test
	public void testAddShiftNullDate()
	{
		aShift = new Shift(null, aStartTime, aEndTime, alex);
		try{
			ShiftController.AddShift(aShift);
		}
		catch(InvalidInputException e){
			assertEquals(e.getMessage(),"Shift Date cannot be empty\n");
		}
		
	}	
		
	

	@Test
	public void testAddShiftNullStartTime()
	{
		aShift = new Shift(aShiftDate, null, aEndTime, alex);	
		try{
			ShiftController.AddShift(aShift);
		}
		catch(InvalidInputException e){
			assertEquals(e.getMessage(),"Shift start time cannot be empty\n");
		}
		
	}
	

	@Test
	public void testAddShiftNullEndTime()
	{
		
		aShift = new Shift(aShiftDate, aStartTime, null, alex);
		try{
			ShiftController.AddShift(aShift);
		}
		catch(InvalidInputException e){
			assertEquals(e.getMessage(),"Shift end time cannot be empty\n");
		}
	}	
		
	@Test
	public void testAddShiftNullEmployee()
	{
		aShift = new Shift(aShiftDate, aStartTime, aEndTime, null);
		try{
			ShiftController.AddShift(aShift);
		}
		catch(InvalidInputException e){
			assertEquals(e.getMessage(),"Shift cannot be empty\n");
		}
	}
	
	
	@Test
	public void testAddEndBeforeStart()
	{
		
		aShift = new Shift(aShiftDate, aEndTime,aStartTime , alex);
		
		try{
			ShiftController.AddShift(aShift);
		}
		catch(InvalidInputException e){
			assertEquals(e.getMessage(),"Shift start time cannot be before shift end time\n");
		}
		
	}
	
	@Test
	public void testAddShiftEmployeeNotRegistered()
	{
		
		try {
			EmployeeController.removeEmployee(alex);
		} catch (InvalidInputException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		aShift = new Shift(aShiftDate, aStartTime , aEndTime  , alex);
		
		try{
			ShiftController.AddShift(aShift);
		}
		catch(InvalidInputException e){
			assertEquals(e.getMessage(),"Employee is not registered within the system!\n");
		}
	}
	
	
	@Test
	public void testAddShiftTwice()
	{
		aShift = new Shift(aShiftDate, aStartTime , aEndTime  , alex);
		try{
			ShiftController.AddShift(aShift);
		}
		catch(InvalidInputException e){
		}
		
		try{
			ShiftController.AddShift(aShift);
		}
		catch(InvalidInputException e){
			assertEquals(e.getMessage(),"Cannot have duplicate shifts for the same employee\n");
		}
	}
	
	@Test
	public void testShiftExists()
	{
		aShift = new Shift(aShiftDate, aStartTime , aEndTime  , alex);
		
		
		try{
			ShiftController.AddShift(aShift);
		}
		catch(InvalidInputException e){
		}
		
		assertEquals(previous.hasShifts(),true);
		
	}

}
