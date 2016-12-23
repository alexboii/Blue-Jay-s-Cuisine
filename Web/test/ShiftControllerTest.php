<?php
require_once __DIR__.'\..\controller\ShiftController.php';
require_once __DIR__.'\..\persistence\PersistenceShiftRegistration.php';
require_once __DIR__.'\..\model\ShiftManager.php';
require_once __DIR__.'\..\model\Shift.php';
require_once __DIR__.'\..\model\Employee.php';

class ShiftControllerTest extends PHPUnit_Framework_TestCase
{
    protected $sc;
    protected $pm;
    protected $sM;
    

    protected function setUp()
    {
        $this->sc = new ShiftController();
        $this->pm = new PersistenceShiftRegistration();
        $this->sM = $this->pm->loadDataFromStore();
        $this->sM->delete();
        $this->pm->writeDataToStore($this->sM);
        
        
    }

    protected function tearDown()
    {
    }
    
    public function testCreateShift()
    {
    	$this->assertEquals(0, count($this->sM->getShifts()));
    	
    	$employee = new Employee('Alex', 'Bratyshkin', true, 6.0, 11.0);
    	
    	$shift = new Shift("2016-12-01", "12:00", "17:00", $employee);
    	
    	//Check if shift was created successfully
    	try {
    		$this->sc->createShift($shift);
    	}catch (Exception $e){
    		$error = $e->getMessage();
    		$this->fail();
    	}
    	
    	$this->sM = $this->pm->loadDataFromStore();
    	$this->assertEquals(1, count($this->sM->getShifts()));
    	$this->assertEquals($shift->getShiftDate(), $this->sM->getShift_index(0)->getShiftDate());
    	$this->assertEquals($shift->getStartTime(), $this->sM->getShift_index(0)->getStartTime());
    	$this->assertEquals($shift->getEndTime(), $this->sM->getShift_index(0)->getEndTime());
    	$this->assertEquals($shift->getEmployee(), $this->sM->getShift_index(0)->getEmployee());
    }
    
    public function testCreateShiftInvalidDate()
    {
    	 
    	$employee = new Employee('Alex', 'Bratyshkin', true, 6.0, 11.0);
    	 
    	$shift = new Shift("2016-12-32", "12:00", "17:00", $employee);
    	
    	//Check if shift was created successfully
    	try {
    		$this->sc->createShift($shift);
    	}catch (Exception $e){
    		$error = $e->getMessage();
    	}
    	
    	$this->assertEquals("@2Shift date must be specified correctly (YYYY-MM-DD)! ", $error);
    	
    	$this->sM = $this->pm->loadDataFromStore();
    	$this->assertEquals(0, count($this->sM->getShifts()));
    }
    
    public function testCreateShiftInvalidStartTime()
    {
    	$employee = new Employee('Alex', 'Bratyshkin', true, 6.0, 11.0);
    	
    	$shift = new Shift("2016-12-01", "25:00", "17:00", $employee);
    	 
    	//Check if shift was created successfully
    	try {
    		$this->sc->createShift($shift);
    	}catch (Exception $e){
    		$error = $e->getMessage();
    	}
    	 
    	$this->assertEquals("@3Shift start time must be specified correctly (HH:MM)! ", $error);
    	 
    	$this->sM = $this->pm->loadDataFromStore();
    	$this->assertEquals(0, count($this->sM->getShifts()));
    }
    
    public function testRemoveShift()
    {
    	$this->assertEquals(0, count($this->sM->getShifts()));
    	 
    	$employee = new Employee('Alex', 'Bratyshkin', true, 6.0, 11.0);
    	 
    	$shift = new Shift("2016-12-01", "12:00", "17:00", $employee);
    	
    	$this->sc->createShift($shift);
    	$this->sc->removeShift($shift);
    	
    	$this->sM = $this->pm->loadDataFromStore();
    	$this->assertEquals(0, count($this->sM->getShifts()));
    }
    
    
}