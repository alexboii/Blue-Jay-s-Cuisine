<?php
require_once __DIR__.'\..\persistence\PersistenceEmployeeRegistration.php';
require_once __DIR__.'\..\model\EmployeeManager.php';
require_once __DIR__.'\..\model\Employee.php';

class PersistenceEmployeeTest extends PHPUnit_Framework_TestCase
{
	protected $pm;
	
	protected function setUp()
	{
		$this->pm = new PersistenceEmployeeRegistration();
	}
	
	protected function tearDown()
	{
		
	}
			
	public function testPersistence()
	{
		$erm = EmployeeManager::getInstance();
		$alex = new Employee("Alexander", "Bratyshkin", true, 1, 3);
		$elie = new Employee("Elie", "Harfouche", true, 10, 15);
		$seb = new Employee("Sebastian", "Andrade", true, 35, 22);
		
		$erm->addEmployee($alex);
		$erm->addEmployee($elie);
		$erm->addEmployee($seb);
		
		$this->pm->writeDataToStore($erm);
		
		$erm->delete();
		
		$this->assertEquals(0, count($erm->getEmployees()));
		
		$erm = $this->pm->loadDataFromStore();
		
		$this->assertEquals(3, count($erm->getEmployees()));
		
		$alexEmployee = $erm->getEmployee_index(0);
		$this->assertEquals("Alexander", $alexEmployee->getFirst_Name());
		$this->assertEquals("Bratyshkin", $alexEmployee->getLast_Name());
		$this->assertEquals(true, $alexEmployee->getCurrently_Employed());
		$this->assertTrue($alexEmployee->getWeekly_Hours() == 1);
		$this->assertTrue($alexEmployee->getHourly_Salary() == 3);
		
		$elieEmployee = $erm->getEmployee_index(1);
		$this->assertEquals("Elie", $elieEmployee->getFirst_Name());
		$this->assertEquals("Harfouche", $elieEmployee->getLast_Name());
		$this->assertEquals(true, $elieEmployee->getCurrently_Employed());
		$this->assertTrue($elieEmployee->getWeekly_Hours() == 10);
		$this->assertTrue($elieEmployee->getHourly_Salary() == 15);
		
		$sebEmployee = $erm->getEmployee_index(2);
		$this->assertEquals("Sebastian", $sebEmployee->getFirst_Name());
		$this->assertEquals("Andrade", $sebEmployee->getLast_Name());
		$this->assertEquals(true, $sebEmployee->getCurrently_Employed());
		$this->assertTrue($sebEmployee->getWeekly_Hours() == 35);
		$this->assertTrue($sebEmployee->getHourly_Salary() == 22);
		
	}
}