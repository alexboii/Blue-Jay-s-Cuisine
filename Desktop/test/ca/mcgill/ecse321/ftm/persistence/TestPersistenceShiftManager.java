package ca.mcgill.ecse321.ftm.persistence;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.Date;
import java.sql.Time;
import java.util.Calendar;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import ca.mcgill.ecse321.foodtruck.controllers.EmployeeController;
import ca.mcgill.ecse321.foodtruck.controllers.InvalidInputException;
import ca.mcgill.ecse321.foodtruck.controllers.ShiftController;
import ca.mcgill.ecse321.foodtruck.model.Employee;
import ca.mcgill.ecse321.foodtruck.model.EmployeeManager;
import ca.mcgill.ecse321.foodtruck.model.InventoryManager;
import ca.mcgill.ecse321.foodtruck.model.Shift;
import ca.mcgill.ecse321.foodtruck.model.ShiftManager;
import ca.mcgill.ecse321.foodtruck.persistence.PersistenceEmployeeRegistration;
import ca.mcgill.ecse321.foodtruck.persistence.PersistenceShiftRegistration;
import ca.mcgill.ecse321.foodtruck.persistence.PersistenceXStream;

public class TestPersistenceShiftManager {

	Shift firstShift, secondShift;
	
	
	@Before
	public void setUp()  {
		
		EmployeeManager em = EmployeeManager.getInstance();
		ShiftManager sm = ShiftManager.getInstance();
		Employee bogdan = new Employee("Bogdan", "Dumitru", true, 15,10);

		Employee alex = new Employee("Alex","Bratyshkin",true, 15, 13);
		
		
		em.addEmployee(alex);
		em.addEmployee(bogdan);
		
		Date aShiftDate  = new Date(System.currentTimeMillis()) ;
		Time aStartTime   = new Time(System.currentTimeMillis() + 1000 * 60 * 60 ) ;
		Time aEndTime     = new Time(System.currentTimeMillis() + 1000 * 60 * 60 * 3 ) ;
		
		Date aShiftDate2  = new Date(System.currentTimeMillis()  + 1000 * 60 * 60 ) ;
		Time aStartTime2   = new Time(System.currentTimeMillis() + 1000 * 60 * 60 * 2) ;
		Time aEndTime2     = new Time(System.currentTimeMillis() + 1000 * 60 * 60 * 4 ) ;
		
		
		firstShift= new Shift(aShiftDate, aStartTime, aEndTime, bogdan);
		secondShift= new Shift(aShiftDate2, aStartTime2, aEndTime2, alex);

		//sm.addShift(firstShift);
	//	sm.addShift(secondShift);
		
		try {
			ShiftController.AddShift(firstShift);
		} catch (InvalidInputException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			ShiftController.AddShift(secondShift);
		} catch (InvalidInputException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@After
	public void tearDown() throws Exception {

		EmployeeManager em = EmployeeManager.getInstance();
		em.delete();
		
		ShiftManager sm = ShiftManager.getInstance();
		sm.delete();
	}

	@Test
	public void test() {
		EmployeeManager em = EmployeeManager.getInstance();

		em.delete();
		assertEquals(0, em.getEmployees().size());
		
		ShiftManager sM =ShiftManager.getInstance();
		sM.delete();
		
		assertEquals(0,sM.getShifts().size());

		PersistenceEmployeeRegistration.loadEmployeeManagementModel();

		em = EmployeeManager.getInstance();
		if (em == null) {
			fail("Could not load file");
		}
		
		PersistenceShiftRegistration.loadShiftManagementModel();

		sM = ShiftManager.getInstance();
		if(sM==null)
		{
			fail("Could not load shift file");
		}
		
		assertEquals(true, ShiftController.shiftExists(firstShift));
		



	}
}


