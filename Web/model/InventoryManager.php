<?php
/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.22.0.5146 modeling language!*/

class InventoryManager
{

  //------------------------
  // STATIC VARIABLES
  //------------------------

  private static $theInstance = null;

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //InventoryManager Associations
  private $items;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  private function __construct()
  {
    $this->items = array();
  }

  public static function getInstance()
  {
    if(self::$theInstance == null)
    {
      self::$theInstance = new InventoryManager();
    }
    return self::$theInstance;
  }

  //------------------------
  // INTERFACE
  //------------------------

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