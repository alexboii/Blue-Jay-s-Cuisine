<?php
require_once __DIR__ . '\..\controller\InputValidator.php';
require_once __DIR__ . '\..\persistence\PersistenceShiftRegistration.php';
require_once __DIR__ . '\..\model\ShiftManager.php';
require_once __DIR__ . '\..\model\Shift.php';

class ShiftController {
	public function __construct() {
	}
	public function createShift($shift) {
		$error = "";
		if ($shift->getEmployee () == null) {
			$error .= ("@1Shift employee cannot be empty \n");
		}
		
		if ($shift->getShiftDate () == null || strlen ( trim ( $shift->getShiftDate () ) ) == 0 || ! InputValidator::validate_date ( $shift->getShiftDate () )) {
			$error .= ("@2Shift date must be specified correctly (YYYY-MM-DD)! ");
		}
		if ($shift->getStartTime () == null || strlen ( trim ( $shift->getStartTime () ) ) == 0 || ! strtotime ( $shift->getStartTime () )) {
			$error .= ("@3Shift start time must be specified correctly (HH:MM)! ");
		}
		if ($shift->getEndTime () == null || strlen ( trim ( $shift->getEndTime () ) ) == 0 || ! strtotime ( $shift->getEndTime () )) {
			$error .= ("@4Shift end time must be specified correctly (HH:MM)!");
		}
		
		if ($shift->getEndTime () != null && $shift->getStartTime () != null && date ( 'H:i', strtotime ( $shift->getEndTime () ) ) < date ( 'H:i', strtotime ( $shift->getStartTime () ) )) {
			$error .= ("@5Shift end time cannot be before shift start time!");
		}
		
		if (strlen ( $error ) > 0) {
			throw new Exception ( $error );
		} else if ($this->shiftExists ( $shift )) {
			throw new Exception ( "@6Shift already exists!" );
		} else {
			$pm = new PersistenceShiftRegistration ();
			$sM = $pm->loadDataFromStore ();
			
			$sM->addShift ( $shift );
			
			$pm->writeDataToStore ( $sM );
		}
	}
	public static function shiftExists($shift) {
		$pm = new PersistenceShiftRegistration ();
		$sM = $pm->loadDataFromStore ();
		
		$flag = false;
		
		if (($index = array_search ( $shift, $sM->getShifts () )) !== false) {
			$flag = true;
		} else {
			$flag = false;
		}
		
		return $flag;
	}
	public function removeShift($shift) {
		$pm = new PersistenceShiftRegistration ();
		$sM = $pm->loadDataFromStore ();
		
		if (($index = array_search ( $shift, $sM->getShifts () )) !== false) {
			$sM->removeShift($shift);
			$pm->writeDataToStore ( $sM );
		} else {
			throw new Exception ( "Could not find shift" );
		}
	}
	
	// public function addShift($aShiftDate, $aStartTime, $aEndTime, $aEmployee) {
	// }
}