<?php
/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.24.0-c37463a modeling language!*/

class Transaction
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Transaction Attributes
  private $transaction_date;
  private $transaction_time;
  private $total;

  //Transaction Associations
  private $menu_items;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public function __construct($aTransaction_date, $aTransaction_time, $aTotal)
  {
    $this->transaction_date = $aTransaction_date;
    $this->transaction_time = $aTransaction_time;
    $this->total = $aTotal;
    $this->menu_items = array();
  }

  //------------------------
  // INTERFACE
  //------------------------

  public function setTransaction_date($aTransaction_date)
  {
    $wasSet = false;
    $this->transaction_date = $aTransaction_date;
    $wasSet = true;
    return $wasSet;
  }

  public function setTransaction_time($aTransaction_time)
  {
    $wasSet = false;
    $this->transaction_time = $aTransaction_time;
    $wasSet = true;
    return $wasSet;
  }

  public function setTotal($aTotal)
  {
    $wasSet = false;
    $this->total = $aTotal;
    $wasSet = true;
    return $wasSet;
  }

  public function getTransaction_date()
  {
    return $this->transaction_date;
  }

  public function getTransaction_time()
  {
    return $this->transaction_time;
  }

  public function getTotal()
  {
    return $this->total;
  }

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