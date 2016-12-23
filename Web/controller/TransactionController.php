<?php
require_once __DIR__ . '\..\controller\InputValidator.php';
require_once __DIR__ . '\..\persistence\PersistenceTransactionRegistration.php';
require_once __DIR__ . '\..\model\TransactionManager.php';
require_once __DIR__ . '\..\model\Transaction.php';
require_once __DIR__ . '\..\model\MenuItem.php';
class EmployeeController {
	public function __construct() {
	}
	public function createTransaction($transaction) {
		$error = "";
		if ($transaction->getMenu_items () == null) {
			$error .= ("@1Transaction's menu items cannot be empty \n");
		}
		
		if ($transaction->getDate () == null || strlen ( trim ( $transaction->getDate () ) ) == 0 || ! InputValidator::validate_date ( $transaction->getDate () )) {
			$error .= ("@2Transaction date must be specified correctly (YYYY-MM-DD)! ");
		}
		if ($transaction->getTime () == null || strlen ( trim ( $transaction->getTime () ) ) == 0 || ! strtotime ( $transaction->getTime () )) {
			$error .= ("@3Transaction time must be specified correctly (HH:MM)! ");
		}
		
		$total = InputValidator::validate_input ( $transaction->getTotal () );
		if ($total <= 0 || ! is_numeric ( $total )) {
			$error .= ("@4Transaction total must be a positive, non-zero number");
		}
		
		foreach ( $transaction->getMenu_items as $value ) {
			if ($value->getAvailable () == false) {
				$error .= ("@5Cannot create transaction with unavailable menu items");
			}
		}
		
		if (strlen ( $error ) > 0) {
			throw new Exception ( $error );
		} else {
			$pm = new PersistenceTransactionRegistration ();
			$tM = $pm->loadDataFromStore ();
			
			$tM->addTransaction ( $transaction );
			
			$pm->writeDataToStore ( $tM );
		}
	}
}