<?php
require_once __DIR__ . '\..\controller\InputValidator.php';
require_once __DIR__ . '\..\persistence\PersistenceInventoryRegistration.php';
require_once __DIR__ . '\..\model\InventoryManager.php';
require_once __DIR__ . '\..\model\Item.php';
class InventoryController {
	public function __construct() {
	}
	public function createItem($item) {
		$error = "";
		
		$name = InputValidator::validate_input ( $item->getName() );
		if (strlen ( $name ) == 0) {
			$error .= ("@1Item name cannot be empty \n");
		}
		
		$type = InputValidator::validate_input ( $item->getType () );
		if (strlen ( $type ) == 0) {
			$error .= ("@2Item type cannot be empty \n");
		}
		
		$quantity = InputValidator::validate_input ( $item->getQuantity () );
		if ($quantity < 0 || ! is_numeric ( $quantity )) {
			$error .= ("@3Item quantity has to be a positive number. \n");
		}
		
		if (strlen ( $error ) > 0) {
			throw new Exception ( $error );
		} else {
			$i = new Item ( $name, $type, $quantity );
			
			if ($this->itemExists ( $i )) {
				throw new Exception ( "@5Item already exists" );
			} else {
				$pm = new PersistenceInventoryRegistration ();
				$iM = $pm->loadDataFromStore ();
				
				$iM->addItem ( $i );
				
				$pm->writeDataToStore ( $iM );
			}
		}
	}
	public static function itemExists($item) {
		$pm = new PersistenceInventoryRegistration ();
		$iM = $pm->loadDataFromStore ();
		
		$flag = false;
		
		foreach ( $iM->getItems () as $item ) {
			$flag = true;
		}
		
		return $flag;
	}
	
	public function editItemQuantity($item, $newQuantity){
		$pm = new PersistenceInventoryRegistration ();
		$iM = $pm->loadDataFromStore ();
		
		$error = "";
		
		
		$index = array_search($item, $iM->getItems());

		$quantity = InputValidator::validate_input ( $newQuantity);
		if($quantity < 0){
			$error .= ("@1Item quantity has to be a positive number. \n");
		}
		
		if (strlen ( $error ) > 0) {
			throw new Exception ( $error );
		} else {
			$iM->getItem_index($index)->setQuantity($quantity);
			$pm->writeDataToStore ( $iM );
		}
		
	}
}