<?php
require_once __DIR__ . '\..\model\EmployeeManager.php';

class PersistenceEmployeeRegistration {
	private $filename;
	function __construct($filename = 'employee_data.txt') {
		$this->filename = $filename;
	}
	function loadDataFromStore() {
		if (file_exists ( $this->filename )) {
			$str = file_get_contents ( $this->filename );
			$eM = unserialize ( $str );
		} else {
			$eM = EmployeeManager::getInstance ();
		}
		return $eM;
	}
	function writeDataToStore($eM) {
		$str = serialize ( $eM );
		file_put_contents ( $this->filename, $str );
	}
}
?>