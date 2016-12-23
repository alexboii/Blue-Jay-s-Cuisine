<?php
require_once __DIR__ . '\..\persistence\PersistenceMenuRegistration.php';
require_once __DIR__ . '\..\model\MenuManager.php';
require_once __DIR__ . '\..\model\MenuItem.php';
require_once __DIR__ . '\..\model\Item.php';
require_once __DIR__ . '\..\controller\MenuController.php';

class MenuControllerTest extends PHPUnit_Framework_TestCase
{
	protected $ic;
	protected $pm;
	protected $iM;


	protected function setUp()
	{
		$this->mc = new MenuControllerTest();
		$this->pm = new PersistenceMenuRegistration();
		$this->mM = $this->pm->loadDataFromStore();
		$this->mM->delete();
		$this->pm->writeDataToStore($this->mM);

	}

	protected function tearDown()
	{
	}
	
	public function testCreateMenuItem()
	{
		$this->assertEquals(0, count($this->mM->getMenu_items()));
		
		$menuItem = new MenuItem("Hamburger", "Burger patty in a bun", 5, true);
		
	}
}