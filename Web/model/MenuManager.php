<?php
/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.22.0.5146 modeling language!*/

class MenuManager
{

  //------------------------
  // STATIC VARIABLES
  //------------------------

  private static $theInstance = null;

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //MenuManager Associations
  private $menu_items;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  private function __construct()
  {
    $this->menu_items = array();
  }

  public static function getInstance()
  {
    if(self::$theInstance == null)
    {
      self::$theInstance = new MenuManager();
    }
    return self::$theInstance;
  }

  //------------------------
  // INTERFACE
  //------------------------

  public function getMenu_item_index($index)
  {
    $aMenu_item = $this->menu_items[$index];
    return $aMenu_item;
  }

  public function getMenu_items()
  {
    $newMenu_items = $this->menu_items;
    return $newMenu_items;
  }

  public function numberOfMenu_items()
  {
    $number = count($this->menu_items);
    return $number;
  }

  public function hasMenu_items()
  {
    $has = $this->numberOfMenu_items() > 0;
    return $has;
  }

  public function indexOfMenu_item($aMenu_item)
  {
    $wasFound = false;
    $index = 0;
    foreach($this->menu_items as $menu_item)
    {
      if ($menu_item->equals($aMenu_item))
      {
        $wasFound = true;
        break;
      }
      $index += 1;
    }
    $index = $wasFound ? $index : -1;
    return $index;
  }

  public static function minimumNumberOfMenu_items()
  {
    return 0;
  }

  public function addMenu_item($aMenu_item)
  {
    $wasAdded = false;
    if ($this->indexOfMenu_item($aMenu_item) !== -1) { return false; }
    $this->menu_items[] = $aMenu_item;
    $wasAdded = true;
    return $wasAdded;
  }

  public function removeMenu_item($aMenu_item)
  {
    $wasRemoved = false;
    if ($this->indexOfMenu_item($aMenu_item) != -1)
    {
      unset($this->menu_items[$this->indexOfMenu_item($aMenu_item)]);
      $this->menu_items = array_values($this->menu_items);
      $wasRemoved = true;
    }
    return $wasRemoved;
  }

  public function addMenu_itemAt($aMenu_item, $index)
  {  
    $wasAdded = false;
    if($this->addMenu_item($aMenu_item))
    {
      if($index < 0 ) { $index = 0; }
      if($index > $this->numberOfMenu_items()) { $index = $this->numberOfMenu_items() - 1; }
      array_splice($this->menu_items, $this->indexOfMenu_item($aMenu_item), 1);
      array_splice($this->menu_items, $index, 0, array($aMenu_item));
      $wasAdded = true;
    }
    return $wasAdded;
  }

  public function addOrMoveMenu_itemAt($aMenu_item, $index)
  {
    $wasAdded = false;
    if($this->indexOfMenu_item($aMenu_item) !== -1)
    {
      if($index < 0 ) { $index = 0; }
      if($index > $this->numberOfMenu_items()) { $index = $this->numberOfMenu_items() - 1; }
      array_splice($this->menu_items, $this->indexOfMenu_item($aMenu_item), 1);
      array_splice($this->menu_items, $index, 0, array($aMenu_item));
      $wasAdded = true;
    } 
    else 
    {
      $wasAdded = $this->addMenu_itemAt($aMenu_item, $index);
    }
    return $wasAdded;
  }

  public function equals($compareTo)
  {
    return $this == $compareTo;
  }

  public function delete()
  {
    $this->menu_items = array();
  }

}
?>