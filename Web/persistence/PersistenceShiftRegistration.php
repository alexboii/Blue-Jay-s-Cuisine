<?php
require_once __DIR__ . '\..\model\ShiftManager.php';

class PersistenceShiftRegistration {
	private $filename;
	function __construct($filename = 'shift_data.txt') {
		$this->filename = $filename;
	}
	function loadDataFromStore() {
		if (file_exists ( $this->filename )) {
			$str = file_get_contents ( $this->filename );
			$sM = unserialize ( $str );
		} else {
			$sM = ShiftManager::getInstance ();
		}
		return $sM;
	}
	function writeDataToStore($eM) {
		$str = serialize ( $eM );
		file_put_contents ( $this->filename, $str );
	}
}
?>