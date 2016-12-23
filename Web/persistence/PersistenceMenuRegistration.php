<?php
require_once __DIR__ . '\..\model\MenuManager.php';

class PersistenceMenuRegistration {
	private $filename;
	function __construct($filename = 'menu_data.txt') {
		$this->filename = $filename;
	}
	function loadDataFromStore() {
		if (file_exists ( $this->filename )) {
			$str = file_get_contents ( $this->filename );
			$mM = unserialize ( $str );
		} else {
			$mM = MenuManager::getInstance ();
		}
		return $mM;
	}
	function writeDataToStore($mM) {
		$str = serialize ( $mM );
		file_put_contents ( $this->filename, $str );
	}
}
?>