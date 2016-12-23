<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Food Truck Management System</title>
<style>
.error {
	color: #FF0000;
}
</style>
</head>

<body>
		<?php
		require_once 'model/Employee.php';
		require_once 'persistence/PersistenceEmployeeRegistration.php';
		
		session_start ();
		
		?>
		
		<form action="createemployee.php" method="post">
		<h3>
		Add new employee
		</h3>
		<p>
			First Name <input type="text" name="firstName" /> <span class="error">
				<?php
				if (isset ( $_SESSION ['errorEmployeeFirstName'] ) && ! empty ( $_SESSION ['errorEmployeeFirstName'] )) {
					echo " * " . $_SESSION ['errorEmployeeFirstName'];
				}
				?>
			</span> Last Name <input type="text" name="lastName" /> <span
				class="error">
				<?php
				if (isset ( $_SESSION ['errorEmployeeLastName'] ) && ! empty ( $_SESSION ['errorEmployeeLastName'] )) {
					echo " * " . $_SESSION ['errorEmployeeLastName'];
				}
				?>
			</span>
		</p>

		<p>
			Weekly Hours <input type="text" name="weeklyHours" /> <span
				class="error">
				<?php
				if (isset ( $_SESSION ['errorEmployeeWeeklyHours'] ) && ! empty ( $_SESSION ['errorEmployeeWeeklyHours'] )) {
					echo " * " . $_SESSION ['errorEmployeeWeeklyHours'];
				}
				?>
			</span> Hourly Rate <input type="text" name="hourlyRate" /> <span
				class="error">
				<?php
				if (isset ( $_SESSION ['errorEmployeeHourlyRate'] ) && ! empty ( $_SESSION ['errorEmployeeHourlyRate'] )) {
					echo " * " . $_SESSION ['errorEmployeeHourlyRate'];
				}
				?>
			</span> Employed <input type='hidden' value='false'
				name='currentlyEmployed'> <input type='checkbox' value='true'
				name='currentlyEmployed'> <span class="error">
			</span>
		</p>

		<p>
			<input type="submit" value="Register" />
		</p>
		</form>
		
		<form action="editemployee.php" method="post">
		<h3>Edit existing employee</h3>
		<?php
		$pm = new PersistenceEmployeeRegistration ();
		$eM = $pm->loadDataFromStore ();
		
		echo "<p><select name='employeespinner'>";
		foreach ( $eM->getEmployees () as $employee ) {
			echo "<option>" . $employee->getFirst_name () . " " . $employee->getLast_name () . "</option>";
		}
		
		echo "</select></p>";
		$selectedEmployee = $_POST['employeespinner'];
		echo "<p>First Name <input type='text' name='firstNameEdit' placeholder=" . " " . $selectedEmployee->getFirst_Name() . "/></p>"
		?>
		
		
		</form>
		
</body>
</html>