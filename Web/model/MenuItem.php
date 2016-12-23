<?php
/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.24.0-c37463a modeling language!*/

class MenuItem
{

  //------------------------
  // STATIC VARIABLES
  //------------------------

  private static $nextId = 1;

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //MenuItem Attributes
  private $name;
  private $description;
  private $price;
  private $available;

  //Autounique Attributes
  private $id;

  //MenuItem Associations
  private $items;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public function __construct($aName, $aDescription, $aPrice, $aAvailable)
  {
    $this->name = $aName;
    $this->description = $aDescription;
    $this->price = $aPrice;
    $this->available = $aAvailable;
    $this->id = self::$nextId++;
    $this->items = array();
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

  public function setDescription($aDescription)
  {
    $wasSet = false;
    $this->description = $aDescription;
    $wasSet = true;
    return $wasSet;
  }

  public function setPrice($aPrice)
  {
    $wasSet = false;
    $this->price = $aPrice;
    $wasSet = true;
    return $wasSet;
  }

  public function setAvailable($aAvailable)
  {
    $wasSet = false;
    $this->available = $aAvailable;
    $wasSet = true;
    return $wasSet;
  }

  public function getName()
  {
    return $this->name;
  }

  public function getDescription()
  {
    return $this->description;
  }

  public function getPrice()
  {
    return $this->price;
  }

  public function getAvailable()
  {
    return $this->available;
  }

  public function getId()
  {
    return $this->id;
  }

  public function isAvailable()
  {
    return $this->available;
  }

  public function getItem_index($index)
  {
    $aItem = $this->items[$index];
    return $aItem;
  }

  public function getItems()
  {
    $newItems = $this->items;
    return $newItems;
  }

  public function numberOfItems()
  {
    $number = count($this->items);
    return $number;
  }

  public function hasItems()
  {
    $has = $this->numberOfItems() > 0;
    return $has;
  }

  public function indexOfItem($aItem)
  {
    $wasFound = false;
    $index = 0;
    foreach($this->items as $item)
    {
      if ($item->equals($aItem))
      {
        $wasFound = true;
        break;
      }
      $index += 1;
    }
    $index = $wasFound ? $index : -1;
    return $index;
  }

  public static function minimumNumberOfItems()
  {
    return 0;
  }

  public function addItem($aItem)
  {
    $wasAdded = false;
    if ($this->indexOfItem($aItem) !== -1) { return false; }
    $this->items[] = $aItem;
    $wasAdded = true;
    return $wasAdded;
  }

  public function removeItem($aItem)
  {
    $wasRemoved = false;
    if ($this->indexOfItem($aItem) != -1)
    {
      unset($this->items[$this->indexOfItem($aItem)]);
      $this->items = array_values($this->items);
      $wasRemoved = true;
    }
    return $wasRemoved;
  }

  public function addItemAt($aItem, $index)
  {  
    $wasAdded = false;
    if($this->addItem($aItem))
    {
      if($index < 0 ) { $index = 0; }
      if($index > $this->numberOfItems()) { $index = $this->numberOfItems() - 1; }
      array_splice($this->items, $this->indexOfItem($aItem), 1);
      array_splice($this->items, $index, 0, array($aItem));
      $wasAdded = true;
    }
    return $wasAdded;
  }

  public function addOrMoveItemAt($aItem, $index)
  {
    $wasAdded = false;
    if($this->indexOfItem($aItem) !== -1)
    {
      if($index < 0 ) { $index = 0; }
      if($index > $this->numberOfItems()) { $index = $this->numberOfItems() - 1; }
      array_splice($this->items, $this->indexOfItem($aItem), 1);
      array_splice($this->items, $index, 0, array($aItem));
      $wasAdded = true;
    } 
    else 
    {
      $wasAdded = $this->addItemAt($aItem, $index);
    }
    return $wasAdded;
  }

  public function equals($compareTo)
  {
    return $this == $compareTo;
  }

  public function delete()
  {
    $this->items = array();
  }

}
?>