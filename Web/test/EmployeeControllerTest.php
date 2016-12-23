<?php
require_once __DIR__.'\..\controller\EmployeeController.php';
require_once __DIR__.'\..\persistence\PersistenceEmployeeRegistration.php';
require_once __DIR__.'\..\model\EmployeeManager.php';
require_once __DIR__.'\..\model\Employee.php';

class EmployeeControllerTest extends PHPUnit_Framework_TestCase
{
    protected $ec;
    protected $pm;
    protected $eM;
    

    protected function setUp()
    {
        $this->ec = new EmployeeController();
        $this->pm = new PersistenceEmployeeRegistration();
        $this->eM = $this->pm->loadDataFromStore();
        $this->eM->delete();
        $this->pm->writeDataToStore($this->eM);
        
    }

    protected function tearDown()
    {
    }

    public function testCreateEmployee() {
        $this->assertEquals(0, count($this->eM->getEmployees()));
    
        $employee = new Employee('Alexander', 'Bratyshkin', true, 5.0, 11.0);
        $employee2 = new Employee('Alexanderrrrrrrrrrrrrrrrrrrrrr', 'Bratyshkin', true, 5.0, 11.0);
        $employee3 = new Employee('Alexander', 'Bratyshkinnmnnnnnnnnnnnnnnnnnnnn', true, 5.0, 11.0);
        $employee4 = new Employee('Alexander', 'Bratyshkin', false, 5.0, 11.0);
        $employee5 = new Employee('Alexander', 'BRATYSHKIN', true, 5.0, 11.0);
        $employee6 = new Employee('ALEXANDER', 'Bratyshkin', true, 5.0, 11.0);
        $employee7 = new Employee('Alexander', 'Bratyshkin', true, 0, 11.0);
        $employee8 = new Employee('Alexander', 'Bratyshkin', true, 5.0, 0);
        $employee = new Employee('Alexander', 'Bratyshkin', true, 5.0, 11.0);
    
    	try {
    		$this->ec->createEmployee($employee);
    	} catch (Exception $e) {
    		// check that no error occurred
    		$this->fail();
    	}
    
    	// check file contents
    	$this->eM = $this->pm->loadDataFromStore();
    	$this->assertEquals(1, count($this->eM->getEmployees()));
    	$this->assertEquals($employee->getFirst_name(), $this->eM->getEmployee_index(0)->getFirst_name());
    	$this->assertEquals($employee->getLast_name(), $this->eM->getEmployee_index(0)->getLast_name());
    	$this->assertEquals($employee->getCurrently_employed(), $this->eM->getEmployee_index(0)->getCurrently_employed());
    	$this->assertEquals($employee->getWeekly_hours(), $this->eM->getEmployee_index(0)->getWeekly_hours());
    	$this->assertEquals($employee->getHourly_salary(), $this->eM->getEmployee_index(0)->getHourly_salary());
    	 
    }
    
    public function testCreateEmployeeFirstNameEmpty()
    {
    	$this->assertEquals(0, count($this->eM->getEmployees()));
    	 
    	$employee = new Employee("", "Bratyshkin", true, 5.0, 11.0);
    	 
    	try {
    		$this->ec->createEmployee($employee);
    	} catch (Exception $e) {
    		// check that no error occurred
    		$error = $e->getMessage();
    	}
    	 
    	// check error
    	$this->assertEquals("@1Employee first name cannot be empty \n", $error);
    	 
    	//check file contents
    	$this->eM = $this->pm->loadDataFromStore();
    	$this->assertEquals(0,count($this->eM->getEmployees()));
    }
    
    public function testCreateEmployeeFirstNameSpaces()
    {
    	$this->assertEquals(0, count($this->eM->getEmployees()));
    
    	$employee = new Employee(" ", "Bratyshkin", true, 5.0, 11.0);;
    
    	try {
    		$this->ec->createEmployee($employee);
    	} catch (Exception $e) {
    		// check that no error occurred
    		$error = $e->getMessage();
    	}
    
    	// check error
    	$this->assertEquals("@1Employee first name cannot be empty \n", $error);
    
    	//check file contents
    	$this->eM = $this->pm->loadDataFromStore();
    	$this->assertEquals(0,count($this->eM->getEmployees()));
    }
    
public function testCreateEmployeeLastNameEmpty()
    {
    	$this->assertEquals(0, count($this->eM->getEmployees()));
    	 
    	$employee = new Employee("Elie", "", true, 5.0, 11.0);;
    	 
    	try {
    		$this->ec->createEmployee($employee);
    	} catch (Exception $e) {
    		// check that no error occurred
    		$error = $e->getMessage();
    	}
    	 
    	// check error
    	$this->assertEquals("@2Employee last name cannot be empty \n", $error);
    	 
    	//check file contents
    	$this->eM = $this->pm->loadDataFromStore();
    	$this->assertEquals(0,count($this->eM->getEmployees()));
    }
    
    public function testCreateEmployeeLastNameSpaces()
    {
    	$this->assertEquals(0, count($this->eM->getEmployees()));
    
    	$employee = new Employee("Elie", " ", true, 5.0, 11.0);;
    
    	try {
    		$this->ec->createEmployee($employee);
    	} catch (Exception $e) {
    		// check that no error occurred
    		$error = $e->getMessage();
    	}
    
    	// check error
    	$this->assertEquals("@2Employee last name cannot be empty \n", $error);
    
    	//check file contents
    	$this->eM = $this->pm->loadDataFromStore();
    	$this->assertEquals(0,count($this->eM->getEmployees()));
    }
    
    public function testCreateEmployeeSalaryNegative()
    {
    	$this->assertEquals(0, count($this->eM->getEmployees()));
    	
    	$employee = new Employee("Sebastian", "Andrade", true, 5.0, -5);
    	
    	try {
    		$this->ec->createEmployee($employee);
    	} catch (Exception $e) {
    		// check that no error occurred
    		$error = $e->getMessage();
    	}
    	
    	// check error
    	$this->assertEquals("@3Employee salary must be a nonzero positive number. \n", $error);
    	
    	//check file contents
    	$this->eM = $this->pm->loadDataFromStore();
    	$this->assertEquals(0,count($this->eM->getEmployees()));
    }
    
    public function testCreateEmployeeSalaryZero()
    {
    	$this->assertEquals(0, count($this->eM->getEmployees()));
    	 
    	$employee = new Employee("Sebastian", "Andrade", true, 5.0, 0);;
    	 
    	try {
    		$this->ec->createEmployee($employee);
    	} catch (Exception $e) {
    		// check that no error occurred
    		$error = $e->getMessage();
    	}
    	 
    	// check error
    	$this->assertEquals("@3Employee salary must be a nonzero positive number. \n", $error);
    	 
    	//check file contents
    	$this->eM = $this->pm->loadDataFromStore();
    	$this->assertEquals(0,count($this->eM->getEmployees()));
    }
    
    public function testCreateEmployeeSalaryEmpty()
    {
    	$this->assertEquals(0, count($this->eM->getEmployees()));
    
    	$employee = new Employee("Sebastian", "Andrade", true, 5.0, "");;
    
    	try {
    		$this->ec->createEmployee($employee);
    	} catch (Exception $e) {
    		// check that no error occurred
    		$error = $e->getMessage();
    	}
    
    	// check error
    	$this->assertEquals("@3Employee salary must be a nonzero positive number. \n", $error);
    
    	//check file contents
    	$this->eM = $this->pm->loadDataFromStore();
    	$this->assertEquals(0,count($this->eM->getEmployees()));
    }
    
    public function testCreateEmployeeSalaryNaN()
    {
    	$this->assertEquals(0, count($this->eM->getEmployees()));
    
    	$employee = new Employee("Sebastian", "Andrade", true, 5.0, "test");;
    
    	try {
    		$this->ec->createEmployee($employee);
    	} catch (Exception $e) {
    		// check that no error occurred
    		$error = $e->getMessage();
    	}
    
    	// check error
    	$this->assertEquals("@3Employee salary must be a nonzero positive number. \n", $error);
    
    	//check file contents
    	$this->eM = $this->pm->loadDataFromStore();
    	$this->assertEquals(0,count($this->eM->getEmployees()));
    }
    
    public function testCreateEmployeeHoursNegative()
    {
    	$this->assertEquals(0, count($this->eM->getEmployees()));
    	 
    	$employee = new Employee("Bogdan", "Dumitru", true, -5, 22);
    	 
    	try {
    		$this->ec->createEmployee($employee);
    	} catch (Exception $e) {
    		// check that no error occurred
    		$error = $e->getMessage();
    	}
    	 
    	// check error
    	$this->assertEquals("@4Employee hours must be a positive number. \n", $error);
    	 
    	//check file contents
    	$this->eM = $this->pm->loadDataFromStore();
    	$this->assertEquals(0,count($this->eM->getEmployees()));
    }
    
    public function testCreateEmployeeHoursEmpty()
    {
    	$this->assertEquals(0, count($this->eM->getEmployees()));
    
    	$employee = new Employee("Bogdan", "Dumitru", true, "", 22);;
    
    	try {
    		$this->ec->createEmployee($employee);
    	} catch (Exception $e) {
    		// check that no error occurred
    		$error = $e->getMessage();
    	}
    
    	// check error
    	$this->assertEquals("@4Employee hours must be a positive number. \n", $error);
    
    	//check file contents
    	$this->eM = $this->pm->loadDataFromStore();
    	$this->assertEquals(0,count($this->eM->getEmployees()));
    }
    
    public function testCreateEmployeeHoursNaN()
    {
    	$this->assertEquals(0, count($this->eM->getEmployees()));
    
    	$employee = new Employee("Bogdan", "Dumitru", true, "test", 22);;
    
    	try {
    		$this->ec->createEmployee($employee);
    	} catch (Exception $e) {
    		// check that no error occurred
    		$error = $e->getMessage();
    	}
    
    	// check error
    	$this->assertEquals("@4Employee hours must be a positive number. \n", $error);
    
    	//check file contents
    	$this->eM = $this->pm->loadDataFromStore();
    	$this->assertEquals(0,count($this->eM->getEmployees()));
    }
    
    public function testCreateEmployeeExist()
    {
    	$this->assertEquals(0, count($this->eM->getEmployees()));
    	
    	$employee1 = new Employee("Alexander", "Bratyshkin", true, 5.0, 11.0);
    	$employee2 = new Employee("Alexander", "Bratyshkin", true, 5.0, 11.0);
    	
    	$this->ec->createEmployee($employee1);
    	
    	try {
    		$this->ec->createEmployee($employee2);
    	}catch (Exception $e){
    		$error = $e->getMessage();
    	}
    	
    	$this->assertEquals("@5Employee already exists", $error);
    	
    	$this->eM = $this->pm->loadDataFromStore();
    	$this->assertEquals(1,count($this->eM->getEmployees()));
    }
    
    public function testEditEmployee()
    {
    	$this->assertEquals(0, count($this->eM->getEmployees()));
    	
    	$employee = new Employee("Alexander", "Bratyshkin", true, 5.0, 11.0);
    	
    	try {
    		$this->ec->createEmployee($employee);
    		$this->ec->editEmployee($employee, "Sebastian", "Andrade", false, 6, 12);
    	} catch (Exception $e) {
    		// check that no error occurred
    		$error = $e->getMessage();
    	}
    	
    	$this->assertEquals("Employee not found \n", $error);
    	
    	// check file contents
    	$this->eM = $this->pm->loadDataFromStore();
    	
    	$this->assertEquals(1, count($this->eM->getEmployees()));
    	$this->assertEquals("Sebastian", $this->eM->getEmployee_index(0)->getFirst_name());
    	$this->assertEquals("Andrade", $this->eM->getEmployee_index(0)->getLast_name());
    	$this->assertEquals(false, $this->eM->getEmployee_index(0)->getCurrently_employed());
//     	$this->assertEquals(6, $this->eM->getEmployee_index(0)->getWeekly_hours());
//     	$this->assertEquals(12, $this->eM->getEmployee_index(0)->getHourly_salary());
    }
    
    public function testEditEmployeeDNE()
    {
    	$this->assertEquals(0, count($this->eM->getEmployees()));
    	 
    	$employee = new Employee("Alexander", "Bratyshkin", true, 5.0, 11.0);
    	
    	try {
    		$this->ec->editEmployee($employee, "Sebastian", "Andrade", false, 6, 12);
    	} catch (Exception $e) {
    		// check that no error occurred
    		$error = $e->getMessage();
    	}
    	
    	$this->assertEquals("Employee not found \n", $error);
    	
    	$this->eM = $this->pm->loadDataFromStore();
    	$this->assertEquals(0,count($this->eM->getEmployees()));
    }
    
    public function testRemoveEmployee()
    {
    	$this->assertEquals(0, count($this->eM->getEmployees()));
    	
    	$employee = new Employee('Alex', 'Bratyshkin', true, 6.0, 11.0);
    	 
    	$this->ec->createEmployee($employee);
//     	$this->ec->removeEmployee($employee);
    	 
    	$this->eM = $this->pm->loadDataFromStore();
    	$this->assertEquals(0, count($this->eM->getEmployees()));
    }
    
    public function testRemoveEmployeeDNE()
    {
    	$this->assertEquals(0, count($this->eM->getEmployees()));
    	
    	$employee = new Employee("Alexander", "Bratyshkin", true, 5.0, 11.0);
    	 
    	try {
    		$this->ec->removeEmployee($employee);
    	} catch (Exception $e) {
    		// check that no error occurred
    		$error = $e->getMessage();
    	}
    	 
    	$this->assertEquals("Could not find employee", $error);
    	 
    	$this->eM = $this->pm->loadDataFromStore();
    	$this->assertEquals(0,count($this->eM->getEmployees()));
    }
}
?>
