<?php
require_once 'controller\EmployeeController.php';
require_once 'model\Employee.php';



session_start ();

$_SESSION ["errorEmployeeEmpty"] = "";
$_SESSION ["errorEmployeeFirstName"] = "";
$_SESSION ["errorEmployeeLastName"] = "";
$_SESSION ["errorEmployeeHourlyRate"] = "";
$_SESSION ["errorEmployeeWeeklyHours"] = "";

$c = new EmployeeController ();
try {
	$c->createEmployee (new Employee($_POST ['firstName'], $_POST['lastName'], $_POST['currentlyEmployed'], $_POST['weeklyHours'], $_POST['hourlyRate'])  );

} catch ( Exception $e ) {
	
	$errors = explode("@", $e->getMessage());
	foreach($errors as $error)
	{
		if(substr($error, 0 ,1) == "1")
		{
			$_SESSION ["errorEmployeeFirstName"] = substr($error, 1);
		}
		if(substr($error, 0 ,1) == "2")
		{
			$_SESSION ["errorEmployeeLastName"] = substr($error, 1);
		}
		
		if(substr($error, 0 , 1) == "3")
		{
			$_SESSION ["errorEmployeeHourlyRate"] = substr($error, 1);
		}
		
		if(substr($error, 0 , 1) == "4")
		{
			$_SESSION ["errorEmployeeWeeklyHours"] = substr($error, 1);
		}
	}
}
?>
<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="refresh" content="0; url=/FoodTruckManagement_Web/" />
	</head>
</html>