<?php
/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.22.0.5146 modeling language!*/

class Shift
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Shift Attributes
  private $shiftDate;
  private $startTime;
  private $endTime;

  //Shift Associations
  private $employee;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public function __construct($aShiftDate, $aStartTime, $aEndTime, $aEmployee)
  {
    $this->shiftDate = $aShiftDate;
    $this->startTime = $aStartTime;
    $this->endTime = $aEndTime;
    if (!$this->setEmployee($aEmployee))
    {
      throw new Exception("Unable to create Shift due to aEmployee");
    }
  }

  //------------------------
  // INTERFACE
  //------------------------

  public function setShiftDate($aShiftDate)
  {
    $wasSet = false;
    $this->shiftDate = $aShiftDate;
    $wasSet = true;
    return $wasSet;
  }

  public function setStartTime($aStartTime)
  {
    $wasSet = false;
    $this->startTime = $aStartTime;
    $wasSet = true;
    return $wasSet;
  }

  public function setEndTime($aEndTime)
  {
    $wasSet = false;
    $this->endTime = $aEndTime;
    $wasSet = true;
    return $wasSet;
  }

  public function getShiftDate()
  {
    return $this->shiftDate;
  }

  public function getStartTime()
  {
    return $this->startTime;
  }

  public function getEndTime()
  {
    return $this->endTime;
  }

  public function getEmployee()
  {
    return $this->employee;
  }

  public function setEmployee($aNewEmployee)
  {
    $wasSet = false;
    if ($aNewEmployee != null)
    {
      $this->employee = $aNewEmployee;
      $wasSet = true;
    }
    return $wasSet;
  }

  public function equals($compareTo)
  {
    return $this == $compareTo;
  }

  public function delete()
  {
    $this->employee = null;
  }

}
?>