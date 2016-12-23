<?php
/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.22.0.5146 modeling language!*/

class Employee
{

  //------------------------
  // STATIC VARIABLES
  //------------------------

  private static $nextId = 1;

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Employee Attributes
  private $first_name;
  private $last_name;
  private $currently_employed;
  private $weekly_hours;
  private $hourly_salary;

  //Autounique Attributes
  private $id;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public function __construct($aFirst_name, $aLast_name, $aCurrently_employed, $aWeekly_hours, $aHourly_salary)
  {
    $this->first_name = $aFirst_name;
    $this->last_name = $aLast_name;
    $this->currently_employed = $aCurrently_employed;
    $this->weekly_hours = $aWeekly_hours;
    $this->hourly_salary = $aHourly_salary;
    $this->id = self::$nextId++;
  }

  //------------------------
  // INTERFACE
  //------------------------

  public function setFirst_name($aFirst_name)
  {
    $wasSet = false;
    $this->first_name = $aFirst_name;
    $wasSet = true;
    return $wasSet;
  }

  public function setLast_name($aLast_name)
  {
    $wasSet = false;
    $this->last_name = $aLast_name;
    $wasSet = true;
    return $wasSet;
  }

  public function setCurrently_employed($aCurrently_employed)
  {
    $wasSet = false;
    $this->currently_employed = $aCurrently_employed;
    $wasSet = true;
    return $wasSet;
  }

  public function setWeekly_hours($aWeekly_hours)
  {
    $wasSet = false;
    $this->weekly_hours = $aWeekly_hours;
    $wasSet = true;
    return $wasSet;
  }

  public function setHourly_salary($aHourly_salary)
  {
    $wasSet = false;
    $this->hourly_salary = $aHourly_salary;
    $wasSet = true;
    return $wasSet;
  }

  public function getFirst_name()
  {
    return $this->first_name;
  }

  public function getLast_name()
  {
    return $this->last_name;
  }

  public function getCurrently_employed()
  {
    return $this->currently_employed;
  }

  public function getWeekly_hours()
  {
    return $this->weekly_hours;
  }

  public function getHourly_salary()
  {
    return $this->hourly_salary;
  }

  public function getId()
  {
    return $this->id;
  }

  public function isCurrently_employed()
  {
    return $this->currently_employed;
  }

  public function equals($compareTo)
  {
    return $this == $compareTo;
  }

  public function delete()
  {}

}
?>