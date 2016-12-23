<?php
/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.22.0.5146 modeling language!*/

class EmployeeManager
{

  //------------------------
  // STATIC VARIABLES
  //------------------------

  private static $theInstance = null;

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //EmployeeManager Associations
  private $employees;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  private function __construct()
  {
    $this->employees = array();
  }

  public static function getInstance()
  {
    if(self::$theInstance == null)
    {
      self::$theInstance = new EmployeeManager();
    }
    return self::$theInstance;
  }

  //------------------------
  // INTERFACE
  //------------------------

  public function getEmployee_index($index)
  {
    $aEmployee = $this->employees[$index];
    return $aEmployee;
  }

  public function getEmployees()
  {
    $newEmployees = $this->employees;
    return $newEmployees;
  }

  public function numberOfEmployees()
  {
    $number = count($this->employees);
    return $number;
  }

  public function hasEmployees()
  {
    $has = $this->numberOfEmployees() > 0;
    return $has;
  }

  public function indexOfEmployee($aEmployee)
  {
    $wasFound = false;
    $index = 0;
    foreach($this->employees as $employee)
    {
      if ($employee->equals($aEmployee))
      {
        $wasFound = true;
        break;
      }
      $index += 1;
    }
    $index = $wasFound ? $index : -1;
    return $index;
  }

  public static function minimumNumberOfEmployees()
  {
    return 0;
  }

  public function addEmployee($aEmployee)
  {
    $wasAdded = false;
    if ($this->indexOfEmployee($aEmployee) !== -1) { return false; }
    $this->employees[] = $aEmployee;
    $wasAdded = true;
    return $wasAdded;
  }

  public function removeEmployee($aEmployee)
  {
    $wasRemoved = false;
    if ($this->indexOfEmployee($aEmployee) != -1)
    {
      unset($this->employees[$this->indexOfEmployee($aEmployee)]);
      $this->employees = array_values($this->employees);
      $wasRemoved = true;
    }
    return $wasRemoved;
  }

  public function addEmployeeAt($aEmployee, $index)
  {  
    $wasAdded = false;
    if($this->addEmployee($aEmployee))
    {
      if($index < 0 ) { $index = 0; }
      if($index > $this->numberOfEmployees()) { $index = $this->numberOfEmployees() - 1; }
      array_splice($this->employees, $this->indexOfEmployee($aEmployee), 1);
      array_splice($this->employees, $index, 0, array($aEmployee));
      $wasAdded = true;
    }
    return $wasAdded;
  }

  public function addOrMoveEmployeeAt($aEmployee, $index)
  {
    $wasAdded = false;
    if($this->indexOfEmployee($aEmployee) !== -1)
    {
      if($index < 0 ) { $index = 0; }
      if($index > $this->numberOfEmployees()) { $index = $this->numberOfEmployees() - 1; }
      array_splice($this->employees, $this->indexOfEmployee($aEmployee), 1);
      array_splice($this->employees, $index, 0, array($aEmployee));
      $wasAdded = true;
    } 
    else 
    {
      $wasAdded = $this->addEmployeeAt($aEmployee, $index);
    }
    return $wasAdded;
  }

  public function equals($compareTo)
  {
    return $this == $compareTo;
  }

  public function delete()
  {
    $this->employees = array();
  }

}
?>