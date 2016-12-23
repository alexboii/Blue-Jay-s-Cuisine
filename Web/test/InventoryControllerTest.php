<?php
require_once __DIR__.'\..\controller\InventoryController.php';
require_once __DIR__.'\..\persistence\PersistenceInventoryRegistration.php';
require_once __DIR__.'\..\model\InventoryManager.php';
require_once __DIR__.'\..\model\Item.php';

class InventoryControllerTest extends PHPUnit_Framework_TestCase
{
	protected $ic;
	protected $pm;
	protected $iM;


	protected function setUp()
	{
		$this->ic = new InventoryController();
		$this->pm = new PersistenceInventoryRegistration();
		$this->iM = $this->pm->loadDataFromStore();
		$this->iM->delete();
		$this->pm->writeDataToStore($this->iM);

	}

	protected function tearDown()
	{
	}
	
	public function testCreateItem()
	{
		$this->assertEquals(0, count($this->iM->getItems()));
		
		$item = new Item("Potato", "Vegetable", 2);
		
		try {
			$this->ic->createItem($item);
		} catch (Exception $e) {
			// check that no error occurred
			$this->fail();
		}
		
		$this->iM = $this->pm->loadDataFromStore();
		$this->assertEquals(1, count($this->iM->getItems()));
		$this->assertEquals($item->getName(), $this->iM->getItem_index(0)->getName());
		$this->assertEquals($item->getQuantity(), $this->iM->getItem_index(0)->getQuantity());
		$this->assertEquals($item->getType(), $this->iM->getItem_index(0)->getType());
	}
	
	public function testEditItemQuantity()
	{
		$this->assertEquals(0, count($this->iM->getItems()));
		
		$item = new Item("Potato", "Vegetable", 2);
		
		$this->ic->createItem($item);
		
		$this->ic->editItemQuantity($item, 3);
		
		$this->iM = $this->pm->loadDataFromStore();
		$this->assertEquals(1, count($this->iM->getItems()));
		$this->assertEquals(3, $this->iM->getItem_index(0)->getQuantity());
	}
}