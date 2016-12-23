<?php
require_once __DIR__ . '\..\model\InventoryManager.php';

class PersistenceInventoryRegistration {
	private $filename;
	function __construct($filename = 'inventory_data.txt') {
		$this->filename = $filename;
	}
	function loadDataFromStore() {
		if (file_exists ( $this->filename )) {
			$str = file_get_contents ( $this->filename );
			$iM = unserialize ( $str );
		} else {
			$iM = InventoryManager::getInstance ();
		}
		return $iM;
	}
	function writeDataToStore($iM) {
		$str = serialize ( $iM );
		file_put_contents ( $this->filename, $str );
	}
}
?>