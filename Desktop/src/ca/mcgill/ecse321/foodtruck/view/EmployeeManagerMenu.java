package ca.mcgill.ecse321.foodtruck.view;

import ca.mcgill.ecse321.foodtruck.application.FoodTruckManager.Permissions;
import ca.mcgill.ecse321.foodtruck.controllers.EmployeeController;
import ca.mcgill.ecse321.foodtruck.controllers.InvalidInputException;
import ca.mcgill.ecse321.foodtruck.model.Employee;
import ca.mcgill.ecse321.foodtruck.model.EmployeeManager;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.NumberFormat;
import java.util.HashMap;
import java.util.Iterator;

import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Dimension;
import java.text.Format;
import javax.swing.border.EtchedBorder;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import java.awt.FlowLayout;

public class EmployeeManagerMenu extends JPanel {

	private JTextField firstNameField;
	private JTextField lastNameField;
	private JFormattedTextField hourlyField;
	private JFormattedTextField weeklyHoursField;
	private JLabel hourlyRateLabel;
	JCheckBox employedChkbox;

	JComboBox employeeList;
	// DATA ELEMENTS
	private String error = null;
	private HashMap<Integer, Employee> employees;
	private Integer selectedEmployee = -1;

	private JTextField fNameEditField;
	private JTextField lNameEditField;

	JCheckBox employedEditChkbox;
	JFormattedTextField weeklyHoursEditField;
	JFormattedTextField hourlyEditField;
	JButton btnEdit;

	private boolean editing;
	private JLabel lblAddNewEmployee;
	private JLabel lblEditExistingEmployee;
	
	private Permissions permissionLevel;
	
	JPanel AddContainer;
	/**
	 * Create the application.
	 */
	public EmployeeManagerMenu(Permissions p) {
		
		permissionLevel =p;
		
		this.setMinimumSize(new Dimension(420, 315));
		this.setMaximumSize(new Dimension(420, 315));

		initialize();
		refreshAllData();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {

		this.setBounds(100, 100, 440, 329);
		setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		lblAddNewEmployee = new JLabel("Add new employee");
		add(lblAddNewEmployee);
		
				AddContainer = new JPanel();
				AddContainer.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
				
						JLabel fNameLabel = new JLabel("First Name");
						fNameLabel.setFont(new Font("Dialog", Font.PLAIN, 12));
						
								firstNameField = new JTextField();
								firstNameField.setColumns(8);
								
										JLabel weeklyHoursLabel = new JLabel("Weekly hours");
										weeklyHoursLabel.setFont(new Font("Dialog", Font.PLAIN, 12));
										
												weeklyHoursField = new JFormattedTextField(NumberFormat.getNumberInstance());
												weeklyHoursField.setValue(0);
												weeklyHoursField.setColumns(3);
												
														hourlyRateLabel = new JLabel("Hourly Rate");
														hourlyRateLabel.setFont(new Font("Dialog", Font.PLAIN, 12));
														
																JLabel lNameLabel = new JLabel("Last Name");
																lNameLabel.setFont(new Font("Dialog", Font.PLAIN, 12));
																
																		hourlyField = new JFormattedTextField(NumberFormat.getNumberInstance());
																		hourlyField.setValue(0);
																		hourlyField.setColumns(3);
																		
																				employedChkbox = new JCheckBox("Employed");
																				employedChkbox.setSelected(true);
																				employedChkbox.setFont(new Font("Dialog", Font.BOLD, 10));
																				
																						lastNameField = new JTextField();
																						lastNameField.setColumns(8);
																						
																								JButton registerEmployeeBtn = new JButton("Register");
																								registerEmployeeBtn.addActionListener(new ActionListener() {
																									public void actionPerformed(ActionEvent e) {
																										addEmployee();
																									}
																								});
																								
																										registerEmployeeBtn.setFont(new Font("Tahoma", Font.BOLD, 11));
																										GroupLayout gl_AddContainer = new GroupLayout(AddContainer);
																										gl_AddContainer.setHorizontalGroup(
																											gl_AddContainer.createParallelGroup(Alignment.LEADING)
																												.addGroup(gl_AddContainer.createSequentialGroup()
																													.addGap(14)
																													.addGroup(gl_AddContainer.createParallelGroup(Alignment.LEADING)
																														.addGroup(gl_AddContainer.createSequentialGroup()
																															.addComponent(fNameLabel)
																															.addGap(32)
																															.addComponent(firstNameField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
																															.addGap(6)
																															.addComponent(lNameLabel, GroupLayout.PREFERRED_SIZE, 74, GroupLayout.PREFERRED_SIZE)
																															.addGap(4)
																															.addComponent(lastNameField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
																														.addGroup(gl_AddContainer.createSequentialGroup()
																															.addComponent(weeklyHoursLabel)
																															.addGap(18)
																															.addComponent(weeklyHoursField, GroupLayout.PREFERRED_SIZE, 106, GroupLayout.PREFERRED_SIZE)
																															.addGap(4)
																															.addComponent(hourlyRateLabel)
																															.addGap(13)
																															.addComponent(hourlyField, GroupLayout.PREFERRED_SIZE, 102, GroupLayout.PREFERRED_SIZE))
																														.addGroup(gl_AddContainer.createSequentialGroup()
																															.addComponent(employedChkbox)
																															.addGap(211)
																															.addComponent(registerEmployeeBtn))))
																										);
																										gl_AddContainer.setVerticalGroup(
																											gl_AddContainer.createParallelGroup(Alignment.LEADING)
																												.addGroup(gl_AddContainer.createSequentialGroup()
																													.addGap(11)
																													.addGroup(gl_AddContainer.createParallelGroup(Alignment.LEADING)
																														.addGroup(gl_AddContainer.createSequentialGroup()
																															.addGap(6)
																															.addComponent(fNameLabel))
																														.addComponent(firstNameField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
																														.addGroup(gl_AddContainer.createSequentialGroup()
																															.addGap(6)
																															.addComponent(lNameLabel))
																														.addComponent(lastNameField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
																													.addGap(6)
																													.addGroup(gl_AddContainer.createParallelGroup(Alignment.LEADING)
																														.addGroup(gl_AddContainer.createSequentialGroup()
																															.addGap(6)
																															.addComponent(weeklyHoursLabel))
																														.addComponent(weeklyHoursField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
																														.addGroup(gl_AddContainer.createSequentialGroup()
																															.addGap(6)
																															.addComponent(hourlyRateLabel))
																														.addComponent(hourlyField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
																													.addGap(6)
																													.addGroup(gl_AddContainer.createParallelGroup(Alignment.LEADING)
																														.addComponent(employedChkbox)
																														.addComponent(registerEmployeeBtn)))
																										);
																										AddContainer.setLayout(gl_AddContainer);
																										add(AddContainer);
		
		lblEditExistingEmployee = new JLabel("Edit existing employee");
		add(lblEditExistingEmployee);
		
				JPanel panel = new JPanel();
				panel.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
				
						employeeList = new JComboBox();
						employeeList.setToolTipText("list of employees");
						employeeList.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent e) {
								selectedEmployee = employeeList.getSelectedIndex();

								DisableEdit();
								setFieldsEdit();
							}
						});
						
								fNameEditField = new JTextField();
								fNameEditField.setColumns(8);
								
										lNameEditField = new JTextField();
										lNameEditField.setColumns(8);
										
												weeklyHoursEditField = new JFormattedTextField((Format) null);
												weeklyHoursEditField.setColumns(3);
												weeklyHoursEditField.setValue(0);
												
														hourlyEditField = new JFormattedTextField((Format) null);
														hourlyEditField.setColumns(3);
														hourlyEditField.setValue(0);
														
																btnEdit = new JButton("Edit");
																btnEdit.addActionListener(new ActionListener() {
																	public void actionPerformed(ActionEvent e) {
																		Edit();
																	}
																});
																
																		btnEdit.setActionCommand("");
																		btnEdit.setFont(new Font("Tahoma", Font.BOLD, 11));
																		
																				JLabel firstNameEditLabel = new JLabel("First Name");
																				firstNameEditLabel.setFont(new Font("Dialog", Font.PLAIN, 12));
																				
																						JLabel lastNameEditLabel = new JLabel("Last Name");
																						lastNameEditLabel.setFont(new Font("Dialog", Font.PLAIN, 12));
																						
																								JLabel weeklyHoursEditLabel = new JLabel("Weekly hours");
																								weeklyHoursEditLabel.setFont(new Font("Dialog", Font.PLAIN, 12));
																								
																										JLabel hourlyEditLabel = new JLabel("Hourly Rate");
																										hourlyEditLabel.setFont(new Font("Dialog", Font.PLAIN, 12));
																										
																												employedEditChkbox = new JCheckBox("Employed");
																												employedEditChkbox.setSelected(true);
																												employedEditChkbox.setFont(new Font("Dialog", Font.BOLD, 10));
																												GroupLayout gl_panel = new GroupLayout(panel);
																												gl_panel.setHorizontalGroup(
																													gl_panel.createParallelGroup(Alignment.LEADING)
																														.addGroup(gl_panel.createSequentialGroup()
																															.addGap(6)
																															.addComponent(employeeList, GroupLayout.PREFERRED_SIZE, 403, GroupLayout.PREFERRED_SIZE))
																														.addGroup(gl_panel.createSequentialGroup()
																															.addGap(6)
																															.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
																																.addComponent(firstNameEditLabel, GroupLayout.PREFERRED_SIZE, 90, GroupLayout.PREFERRED_SIZE)
																																.addGroup(gl_panel.createSequentialGroup()
																																	.addGap(86)
																																	.addComponent(fNameEditField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
																															.addGap(6)
																															.addComponent(lastNameEditLabel, GroupLayout.PREFERRED_SIZE, 74, GroupLayout.PREFERRED_SIZE)
																															.addGap(28)
																															.addComponent(lNameEditField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
																														.addGroup(gl_panel.createSequentialGroup()
																															.addGap(6)
																															.addComponent(weeklyHoursEditLabel, GroupLayout.PREFERRED_SIZE, 81, GroupLayout.PREFERRED_SIZE)
																															.addGap(5)
																															.addComponent(weeklyHoursEditField, GroupLayout.PREFERRED_SIZE, 90, GroupLayout.PREFERRED_SIZE)
																															.addGap(22)
																															.addComponent(hourlyEditLabel, GroupLayout.PREFERRED_SIZE, 90, GroupLayout.PREFERRED_SIZE)
																															.addGap(12)
																															.addComponent(hourlyEditField, GroupLayout.PREFERRED_SIZE, 97, GroupLayout.PREFERRED_SIZE))
																														.addGroup(gl_panel.createSequentialGroup()
																															.addGap(7)
																															.addComponent(employedEditChkbox, GroupLayout.PREFERRED_SIZE, 130, GroupLayout.PREFERRED_SIZE)
																															.addGap(169)
																															.addComponent(btnEdit, GroupLayout.PREFERRED_SIZE, 81, GroupLayout.PREFERRED_SIZE))
																												);
																												gl_panel.setVerticalGroup(
																													gl_panel.createParallelGroup(Alignment.LEADING)
																														.addGroup(gl_panel.createSequentialGroup()
																															.addGap(6)
																															.addComponent(employeeList, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
																															.addGap(18)
																															.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
																																.addGroup(gl_panel.createSequentialGroup()
																																	.addGap(5)
																																	.addComponent(firstNameEditLabel, GroupLayout.PREFERRED_SIZE, 16, GroupLayout.PREFERRED_SIZE))
																																.addComponent(fNameEditField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
																																.addGroup(gl_panel.createSequentialGroup()
																																	.addGap(6)
																																	.addComponent(lastNameEditLabel))
																																.addComponent(lNameEditField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
																															.addGap(6)
																															.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
																																.addGroup(gl_panel.createSequentialGroup()
																																	.addGap(5)
																																	.addComponent(weeklyHoursEditLabel, GroupLayout.PREFERRED_SIZE, 16, GroupLayout.PREFERRED_SIZE))
																																.addComponent(weeklyHoursEditField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
																																.addGroup(gl_panel.createSequentialGroup()
																																	.addGap(5)
																																	.addComponent(hourlyEditLabel, GroupLayout.PREFERRED_SIZE, 16, GroupLayout.PREFERRED_SIZE))
																																.addComponent(hourlyEditField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
																															.addGap(6)
																															.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
																																.addGroup(gl_panel.createSequentialGroup()
																																	.addGap(1)
																																	.addComponent(employedEditChkbox))
																																.addComponent(btnEdit)))
																												);
																												panel.setLayout(gl_panel);
																												add(panel);
	setPermission();
	}
	
	private void setPermission()
	{
		boolean visibility = true;
		switch(permissionLevel)
		{
		case Cashier:
		case Cook:
			visibility=false;
			break;
		case Manager:
			break;
		}
		
		lblAddNewEmployee.setVisible(visibility);
		this.AddContainer.setVisible(visibility);
		btnEdit.setVisible(visibility);
	}

	private void refreshAllData() {

		if (error == null || error.length() == 0) {
			if (employees == null) {
				employees = new HashMap<Integer, Employee>();
			}
			refreshEditData();

			firstNameField.setText("");
			lastNameField.setText("");
			hourlyField.setValue(0);
			weeklyHoursField.setValue(0);
			employedChkbox.setSelected(false);

		}

		this.repaint();
	}

	@SuppressWarnings("unchecked")
	private void refreshEditData() {
		EmployeeManager eM = EmployeeManager.getInstance();

		employees.clear();
		employeeList.removeAllItems();

		Iterator<Employee> employeeIterator = eM.getEmployees().iterator();

		Integer index = 0;
		while (employeeIterator.hasNext()) {
			Employee emp = employeeIterator.next();
			employees.put(index++, emp);
			employeeList.addItem(emp.getLast_name() + " , " + emp.getFirst_name());
		}
		selectedEmployee = -1;
		employeeList.setSelectedIndex(selectedEmployee);

		fNameEditField.setText("");
		lNameEditField.setText("");
		weeklyHoursEditField.setValue(0);
		hourlyEditField.setValue(0);
		employedEditChkbox.setSelected(false);

		this.repaint();
	}

	private void setFieldsEdit() {
		if (selectedEmployee >= 0) {
			EmployeeManager eM = EmployeeManager.getInstance();
			Employee selected = eM.getEmployees().get(this.selectedEmployee);

			fNameEditField.setText(selected.getFirst_name());
			lNameEditField.setText(selected.getLast_name());
			weeklyHoursEditField.setValue(selected.getWeekly_hours());
			hourlyEditField.setValue(selected.getHourly_salary());
			employedEditChkbox.setSelected(selected.getCurrently_employed());
		}
	}

	private void UpdateEmployee() {
		error = "";
		String first_name, last_name;
		int weekly_hours = 0;
		double wage = 0;
		boolean isEmployed = false;

		first_name = fNameEditField.getText().toString();
		last_name = lNameEditField.getText().toString();
		try {
			weekly_hours = ((Number) (weeklyHoursEditField.getValue())).intValue();
		} catch (Exception e) {
			error += "Weekly hours has to be an integer!\n";
		}
		try {
			wage = ((Number) (hourlyEditField.getValue())).doubleValue();
		} catch (Exception e) {
			error += "Wage has to be a number!\n";
		}
		isEmployed = employedEditChkbox.isSelected();

		if (error.trim().length() == 0) {
			try {
				EmployeeController.EditEmployee(EmployeeManager.getInstance().getEmployees().get(selectedEmployee),
						first_name, last_name, (float) weekly_hours, wage, isEmployed);

			} catch (InvalidInputException e) {
				error += e.getMessage();
			}
		}
		if (error.trim().length() != 0) {
			JOptionPane.showMessageDialog(null, error);
		}
		refreshEditData();
	}

	private void Edit() {
		if(selectedEmployee>=0)
			{
			editing = !editing;
	
			fNameEditField.setEnabled(editing);
			lNameEditField.setEnabled(editing);
			weeklyHoursEditField.setEnabled(editing);
			hourlyEditField.setEnabled(editing);
			if (editing) {
				btnEdit.setText("Confirm");
	
			} else {
				btnEdit.setText("Edit");
				UpdateEmployee();
			}
		}
		else
		{
			this.error ="No employees selected";
		}
	}

	private void DisableEdit() {
		editing = false;
		fNameEditField.setEnabled(editing);
		lNameEditField.setEnabled(editing);
		weeklyHoursEditField.setEnabled(editing);
		hourlyEditField.setEnabled(editing);
		btnEdit.setText("Edit");
	}

	private void addEmployee() {
		error = "";

		String first_name, last_name;
		int weekly_hours = 0;
		double wage = 0;
		boolean isEmployed = false;

		first_name = firstNameField.getText().toString();
		last_name = lastNameField.getText().toString();
		try {
			weekly_hours = ((Number) (weeklyHoursField.getValue())).intValue();
		} catch (Exception e) {
			error += "Weekly hours has to be an integer!\n";
		}
		try {
			wage = ((Number) (hourlyField.getValue())).doubleValue();
		} catch (Exception e) {
			error += "Wage has to be a number!\n";
		}
		isEmployed = employedChkbox.isSelected();

		if (error.trim().length() == 0) {
			try {
				EmployeeController.createEmployee(first_name, last_name, weekly_hours, wage, isEmployed);

			} catch (InvalidInputException e) {
				error += e.getMessage();
			}
		}
		if (error.trim().length() == 0) {
			refreshAllData();
		} else {
			JOptionPane.showMessageDialog(null, error);
		}
	}
}
