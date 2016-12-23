<?php
/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.22.0.5146 modeling language!*/

class ShiftManager
{

  //------------------------
  // STATIC VARIABLES
  //------------------------

  private static $theInstance = null;

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //ShiftManager Associations
  private $shifts;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  private function __construct()
  {
    $this->shifts = array();
  }

  public static function getInstance()
  {
    if(self::$theInstance == null)
    {
      self::$theInstance = new ShiftManager();
    }
    return self::$theInstance;
  }

  //------------------------
  // INTERFACE
  //------------------------

  public function getShift_index($index)
  {
    $aShift = $this->shifts[$index];
    return $aShift;
  }

  public function getShifts()
  {
    $newShifts = $this->shifts;
    return $newShifts;
  }

  public function numberOfShifts()
  {
    $number = count($this->shifts);
    return $number;
  }

  public function hasShifts()
  {
    $has = $this->numberOfShifts() > 0;
    return $has;
  }

  public function indexOfShift($aShift)
  {
    $wasFound = false;
    $index = 0;
    foreach($this->shifts as $shift)
    {
      if ($shift->equals($aShift))
      {
        $wasFound = true;
        break;
      }
      $index += 1;
    }
    $index = $wasFound ? $index : -1;
    return $index;
  }

  public static function minimumNumberOfShifts()
  {
    return 0;
  }

  public function addShift($aShift)
  {
    $wasAdded = false;
    if ($this->indexOfShift($aShift) !== -1) { return false; }
    $this->shifts[] = $aShift;
    $wasAdded = true;
    return $wasAdded;
  }

  public function removeShift($aShift)
  {
    $wasRemoved = false;
    if ($this->indexOfShift($aShift) != -1)
    {
      unset($this->shifts[$this->indexOfShift($aShift)]);
      $this->shifts = array_values($this->shifts);
      $wasRemoved = true;
    }
    return $wasRemoved;
  }

  public function addShiftAt($aShift, $index)
  {  
    $wasAdded = false;
    if($this->addShift($aShift))
    {
      if($index < 0 ) { $index = 0; }
      if($index > $this->numberOfShifts()) { $index = $this->numberOfShifts() - 1; }
      array_splice($this->shifts, $this->indexOfShift($aShift), 1);
      array_splice($this->shifts, $index, 0, array($aShift));
      $wasAdded = true;
    }
    return $wasAdded;
  }

  public function addOrMoveShiftAt($aShift, $index)
  {
    $wasAdded = false;
    if($this->indexOfShift($aShift) !== -1)
    {
      if($index < 0 ) { $index = 0; }
      if($index > $this->numberOfShifts()) { $index = $this->numberOfShifts() - 1; }
      array_splice($this->shifts, $this->indexOfShift($aShift), 1);
      array_splice($this->shifts, $index, 0, array($aShift));
      $wasAdded = true;
    } 
    else 
    {
      $wasAdded = $this->addShiftAt($aShift, $index);
    }
    return $wasAdded;
  }

  public function equals($compareTo)
  {
    return $this == $compareTo;
  }

  public function delete()
  {
    $this->shifts = array();
  }

}
?>