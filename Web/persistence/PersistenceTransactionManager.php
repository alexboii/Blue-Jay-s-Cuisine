<?php
require_once __DIR__ . '\..\model\TransactionManager.php';

class PersistenceTransactionRegistration {
	private $filename;
	function __construct($filename = 'transaction_data.txt') {
		$this->filename = $filename;
	}
	function loadDataFromStore() {
		if (file_exists ( $this->filename )) {
			$str = file_get_contents ( $this->filename );
			$tM = unserialize ( $str );
		} else {
			$tM = TransactionManager::getInstance ();
		}
		return $tM;
	}
	function writeDataToStore($tM) {
		$str = serialize ( $tM );
		file_put_contents ( $this->filename, $str );
	}
}
?>