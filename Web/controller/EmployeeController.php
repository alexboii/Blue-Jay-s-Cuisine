<?php
require_once __DIR__ . '\..\controller\InputValidator.php';
require_once __DIR__ . '\..\persistence\PersistenceEmployeeRegistration.php';
require_once __DIR__ . '\..\model\EmployeeManager.php';
require_once __DIR__ . '\..\model\Employee.php';
class EmployeeController {
	public function __construct() {
	}
	public function createEmployee($employee) {
		$error = "";
		
		$fName = InputValidator::validate_input ( $employee->getFirst_name () );
		if (strlen ( $fName ) == 0) {
			$error .= ("@1Employee first name cannot be empty \n");
		}
		
		$lName = InputValidator::validate_input ( $employee->getLast_name () );
		if (strlen ( $lName ) == 0) {
			$error .= ("@2Employee last name cannot be empty \n");
		}
		
		$salary = InputValidator::validate_input ( $employee->getHourly_salary () );
		if ($salary <= 0 || ! is_numeric ( $salary ))
			$error .= ("@3Employee salary must be a nonzero positive number. \n");
		
		$hours = InputValidator::validate_input ( $employee->getWeekly_hours () );
		if ($hours < 0 || ! is_numeric ( $hours ))
			$error .= ("@4Employee hours must be a positive number. \n");
		if (strlen ( $error ) > 0) {
			throw new Exception ( $error );
		} else {
			$e = new Employee ( $fName, $lName, $employee->getCurrently_employed (), $hours, $salary );
			
			if ($this->employeeExists ( $e )) {
				throw new Exception ( "@5Employee already exists" );
			} else {
				$pm = new PersistenceEmployeeRegistration ();
				$eM = $pm->loadDataFromStore ();
				
				$eM->addEmployee ( $e );
				
				$pm->writeDataToStore ( $eM );
			}
		}
	}
	public static function employeeExists($employee) {
		$pm = new PersistenceEmployeeRegistration ();
		$eM = $pm->loadDataFromStore ();
		
		$flag = false;
		
		foreach ( $eM->getEmployees () as $employee ) {
			$flag = true;
		}
		
		return $flag;
	}
	public function editEmployee($employee, $newFName, $newLName, $newEmployed, $newWeeklyHours, $newHourlyRate) {
		$pm = new PersistenceEmployeeRegistration ();
		$eM = $pm->loadDataFromStore ();
		
		if (empty($eM->getEmployees()) || ($index = array_search ( $employee, $eM->getEmployees () )) == false) {
			throw new Exception ( "Employee not found \n" );
		}
		else {
			$employees = $eM->getEmployees();
		}
		
		$fName = InputValidator::validate_input ( $newFName );
		if (strlen ( $fName ) == 0) {
			throw new Exception ( "Employee first name cannot be empty \n" );
		}
		
		$lName = InputValidator::validate_input ( $newLName );
		if (strlen ( $lName ) == 0) {
			throw new Exception ( "Employee last name cannot be empty \n" );
		}
		
		$salary = InputValidator::validate_input ( $newHourlyRate );
		if ($salary <= 0 || ! is_numeric ( $salary )) {
			throw new Exception ( "Employee salary must be a nonzero positive number. \n" );
		}
		$hours = InputValidator::validate_input ( $newWeeklyHours );
		if ($hours < 0 || ! is_numeric ( $hours )) {
			throw new Exception ( "Employee hours must be a positive number. \n" );
		} else {
			
			$eM->getEmployee_index ( array_search ( $employee, $employees ))->setFirst_name ( $newFName ) ;
			$eM->getEmployee_index ( array_search ( $employee, $employees ))->setLast_name ( $newLName ) ;
			$eM->getEmployee_index ( array_search ( $employee, $employees ))->setCurrently_employed ( $newEmployed ) ;
			$eM->getEmployee_index ( array_search ( $employee, $employees ))->setWeekly_hours ( $newWeeklyHours ) ;
			$eM->getEmployee_index ( array_search ( $employee, $employees ))->setHourly_salary ( $newHourlyRate ) ;
			
			$pm->writeDataToStore ( $eM );
		}
	}
	public function removeEmployee($employee) {
		$pm = new PersistenceEmployeeRegistration ();
		$eM = $pm->loadDataFromStore ();
		
		if (!empty($eM->getEmployees()) || ($index = array_search ( $employee, $eM->getEmployees () )) !== false) {
			$eM->removeEmployee ( $employee );
			$pm->writeDataToStore ( $eM );
		} else {
			throw new Exception ( "Could not find employee" );
		}
	}
}