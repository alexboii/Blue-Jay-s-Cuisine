package generalTest;
import java.util.ArrayList;
import java.util.List;

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

import ca.mcgill.ecse321.ftm.employeemanagertest.TestAddEmployee;
import ca.mcgill.ecse321.ftm.employeemanagertest.TestEditEmployee;
import ca.mcgill.ecse321.ftm.employeemanagertest.TestNumberOfEmployees;
import ca.mcgill.ecse321.ftm.employeemanagertest.TestRemoveEmployee;
import ca.mcgill.ecse321.ftm.inventorymanagertest.TestAddInventory;
import ca.mcgill.ecse321.ftm.inventorymanagertest.TestEditInventory;
import ca.mcgill.ecse321.ftm.inventorymanagertest.TestNumberOfItems;
import ca.mcgill.ecse321.ftm.inventorymanagertest.TestRemoveItemInventory;
import ca.mcgill.ecse321.ftm.menumanagertest.TestAddMenuItem;
import ca.mcgill.ecse321.ftm.persistence.TestPersistenceEmployeeManager;
import ca.mcgill.ecse321.ftm.persistence.TestPersistenceInventoryManager;
import ca.mcgill.ecse321.ftm.persistence.TestPersistenceMenuItem;
import ca.mcgill.ecse321.ftm.persistence.TestPersistenceShiftManager;
import ca.mcgill.ecse321.ftm.persistence.TestPersistenceTransaction;
import ca.mcgill.ecse321.ftm.shiftmanagertest.TestAddShift;
import ca.mcgill.ecse321.ftm.shiftmanagertest.TestEditShift;



@SuppressWarnings("rawtypes")
public class TestAll
{
	public static void main(String[] args)
	{
		ArrayList<Class> testCases = new ArrayList<Class>();

		//Add test cases
		testCases.add(TestAddEmployee.class);
		testCases.add(TestEditEmployee.class);
		testCases.add(TestNumberOfEmployees.class);
		testCases.add(TestRemoveEmployee.class);
		
		
		testCases.add(TestAddInventory.class);
		testCases.add(TestEditInventory.class);
		testCases.add(TestNumberOfItems.class);
		testCases.add(TestRemoveItemInventory.class);
		
		testCases.add(TestAddMenuItem.class);
		
		testCases.add(TestPersistenceEmployeeManager.class);
		testCases.add(TestPersistenceTransaction.class);
		testCases.add(TestPersistenceShiftManager.class);
		testCases.add(TestPersistenceMenuItem.class);
		testCases.add(TestPersistenceInventoryManager.class);
		

		testCases.add(TestAddShift.class);
		testCases.add(TestEditShift.class);


		for (Class testCase : testCases)
               {
                    runTestCase(testCase);
               }
	}

    private static void runTestCase(Class testCase)
    {
        Result result = JUnitCore.runClasses(testCase);
        for (Failure failure : result.getFailures())
        {
            System.out.println(failure.toString());
        }
    }
}
	
	
	
	

