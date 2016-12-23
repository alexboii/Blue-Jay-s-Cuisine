<?php
require_once __DIR__ . '\..\controller\InputValidator.php';
require_once __DIR__ . '\..\persistence\PersistenceMenuRegistration.php';
require_once __DIR__ . '\..\model\MenuManager.php';
require_once __DIR__ . '\..\model\MenuItem.php';
require_once __DIR__ . '\..\model\Item.php';
class EmployeeController {
	public function __construct() {
	}
	public function createMenuItem($menu_item) {
		$error = "";
		
		$name = InputValidator::validate_input ( $menu_item->getName () );
		if (strlen ( $name ) == 0) {
			$error .= ("@1Menu item name cannot be empty \n");
		}
		
		$description = InputValidator::validate_input ( $menu_item->getDescription () );
		if (strlen ( $description ) == 0) {
			$error .= ("@2Menu item description cannot be empty \n");
		}
		
		$price = InputValidator::validate_input ( $menu_item->getPrice () );
		if ($price <= 0 || ! is_numeric ( $price )) {
			$error .= ("@3Price must be a nonzero positive number. \n");
		}
		
		if ($menu_item->hasItems () == false) {
			$error .= ("@4Menu item must have associated inventory items. \n");
		}
		
		if (count ( array_unique ( $menu_item->getMenu_Items() () ) ) < count ( $menu_item->getMenu_Items () )) {
			$error .= ("@5Menu item cannot be composed of duplicate items. \n");
		}
		if (strlen ( $error ) > 0) {
			throw new Exception ( $error );
		} else {
			$m = new MenuItem ( $name, $description, $price, $menu_item->getAvailable() );
			$m->addItem ( $menu_item->getItems () ); // THIS MIGHT BE AN ERROR, NOT SURE
			
			if ($this->menuItemExists ( $m )) {
				throw new Exception ( "@6Menu item already exists" );
			} else {
				$pm = new PersistenceMenuRegistration ();
				$mM = $pm->loadDataFromStore ();
				
				$mM->addMenu_item ( $m );
				
				$pm->writeDataToStore ( $mM );
			}
		}
	}
	public static function menuItemExists($menu_item) {
		$pm = new PersistenceMenuRegistration ();
		$mM = $pm->loadDataFromStore ();
		
		$flag = false;
		
		foreach ( $mM->getMenu_items() () as $menu_item ) {
			$flag = true;
		}
		
		return $flag;
	}
	public function editMenuItem($menu_item, $newName, $newDescription, $newPrice, $newItems, $newAvailable) {
		$pm = new PersistenceMenuRegistration ();
		$mM = $pm->loadDataFromStore ();
		
		$menu_items = $mM->getMenu_items ();
		
		$found = isset ( $menu_item, $menu_items );
		if (! found) {
			throw new Exception ( "Menu item not found \n" );
		}
		
		$name = InputValidator::validate_input ( $newName );
		if (strlen ( $name ) == 0) {
			throw new Exception ( "Menu item name cannot be empty \n" );
		}
		
		$description = InputValidator::validate_input ( $newDescription );
		if (strlen ( $description ) == 0) {
			throw new Exception ( "Menu item description cannot be empty \n" );
		}
		
		$price = InputValidator::validate_input ( $newPrice );
		if ($price <= 0 || ! is_numeric ( $price )) {
			throw new Exception ( "Menu item price must be a nonzero positive number. \n" );
		}
		if (count ( array_unique ( $items ) ) < count ( $items )) {
			throw new Exception ( "Employee hours must be a positive number. \n" );
		} else {
			
			$m = new MenuItem ( $name, $description, $price, $newAvailable );
			$m->addItem ( $items ); // THIS MIGHT BE AN ERROR, NOT SURE
			
			if (menuItemExists ( $m )) {
				throw new Exception ( "@6Menu item already exists" );
			}
			
			$mM->getMenu_item_index ( array_search ( $menu_item, $menu_items )->setName ( $m->getName () ) );
			$mM->getMenu_item_index ( array_search ( $menu_item, $menu_items )->setDescritipion ( $m->getDescription () ) );
			$mM->getMenu_item_index ( array_search ( $menu_item, $menu_items )->setPrice ( $m->getPrice () ) );
			$mM->getMenu_item_index ( array_search ( $menu_item, $menu_items )->setAvailable( $m->getAvailable () ) );
			$mM->getMenu_item_index ( array_search ( $menu_item, $menu_items )->delete () );
			$mM->getMenu_item_index ( array_search ( $menu_item, $menu_items )->addItem ( $m->getItems () ) );
			
			$pm->writeDataToStore ( $mM );
		}
	}
	public function removeMenuItem($menu_item) {
		$pm = new PersistenceMenuRegistration ();
		$mM = $pm->loadDataFromStore ();
		
		if (($index = array_search ( $menu_item, $mM->getMenu_items () )) !== false) {
			$mM->removeMenu_item ( $menu_item );
			$pm->writeDataToStore ( $mM );
		} else {
			throw new Exception ( "Could not find menu item" );
		}
	}
}