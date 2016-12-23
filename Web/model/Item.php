<?php
/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.22.0.5146 modeling language!*/

class Item
{

  //------------------------
  // STATIC VARIABLES
  //------------------------

  private static $nextId = 1;

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Item Attributes
  private $name;
  private $type;
  private $quantity;

  //Autounique Attributes
  private $id;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public function __construct($aName, $aType, $aQuantity)
  {
    $this->name = $aName;
    $this->type = $aType;
    $this->quantity = $aQuantity;
    $this->id = self::$nextId++;
  }

  //------------------------
  // INTERFACE
  //------------------------

  public function setName($aName)
  {
    $wasSet = false;
    $this->name = $aName;
    $wasSet = true;
    return $wasSet;
  }

  public function setType($aType)
  {
    $wasSet = false;
    $this->type = $aType;
    $wasSet = true;
    return $wasSet;
  }

  public function setQuantity($aQuantity)
  {
    $wasSet = false;
    $this->quantity = $aQuantity;
    $wasSet = true;
    return $wasSet;
  }

  public function getName()
  {
    return $this->name;
  }

  public function getType()
  {
    return $this->type;
  }

  public function getQuantity()
  {
    return $this->quantity;
  }

  public function getId()
  {
    return $this->id;
  }

  public function equals($compareTo)
  {
    return $this == $compareTo;
  }

  public function delete()
  {}

}
?>