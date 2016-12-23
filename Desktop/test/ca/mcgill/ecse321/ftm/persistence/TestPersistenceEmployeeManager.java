package ca.mcgill.ecse321.ftm.persistence;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileNotFoundException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import ca.mcgill.ecse321.foodtruck.controllers.EmployeeController;
import ca.mcgill.ecse321.foodtruck.model.Employee;
import ca.mcgill.ecse321.foodtruck.model.EmployeeManager;
import ca.mcgill.ecse321.foodtruck.persistence.PersistenceEmployeeRegistration;
import ca.mcgill.ecse321.foodtruck.persistence.PersistenceXStream;


public class TestPersistenceEmployeeManager {

	@Before
	public void setUp() throws Exception {

		EmployeeController.createEmployee("Alexander","Bratyshkin",15,13,true);
		EmployeeController.createEmployee("Bogdan", "Dumitru", 10, 15,true);
		EmployeeController.createEmployee("Sebastian", "Andrade", 55, 75, true);
	}

	@After
	public void tearDown() throws Exception {

		EmployeeManager em = EmployeeManager.getInstance();
		em.delete();
	}

	@Test
	public void test() {
		EmployeeManager em = EmployeeManager.getInstance();
		PersistenceXStream.setFilename(
				"test" + File.separator + "ca" + File.separator + "mcgill" + File.separator + "ecse321" + File.separator
						+ "ftm" + File.separator + "persistence" + File.separator + "data.xml");
		PersistenceXStream.setAlias("employee", Employee.class);
		PersistenceXStream.setAlias("manager", EmployeeManager.class);
		
		if (!PersistenceEmployeeRegistration.saveEmployeeManagementModel(em)) {
			fail("Could not save file");
		}

		em.delete();
		assertEquals(0, em.getEmployees().size());

		
		PersistenceEmployeeRegistration.loadEmployeeManagementModel();

		em = EmployeeManager.getInstance();
		//em = (EmployeeManager) PersistenceXStream.loadFromXMLwithXStream();
		if (em == null) {
			fail("Could not load file");
		}

		assertEquals(3, em.getEmployees().size());
		
		assertEquals(em.getEmployee(0).getFirst_name(),"Alexander");
		assertEquals(em.getEmployee(0).getLast_name(),"Bratyshkin");
		assertTrue(em.getEmployee(0).getWeekly_hours() == 15f);
		assertTrue(em.getEmployee(0).getHourly_salary() == 13f);

		assertEquals(em.getEmployee(1).getFirst_name(),"Bogdan");
		assertEquals(em.getEmployee(1).getLast_name(),"Dumitru");	
		assertTrue(em.getEmployee(1).getWeekly_hours() ==10f);
		assertTrue(em.getEmployee(1).getHourly_salary()==15f);

		assertEquals(em.getEmployee(2).getFirst_name(),"Sebastian");
		assertEquals(em.getEmployee(2).getLast_name(),"Andrade");
		assertTrue(em.getEmployee(2).getWeekly_hours()==55);
		assertTrue(em.getEmployee(2).getHourly_salary()==75);


	}

}
