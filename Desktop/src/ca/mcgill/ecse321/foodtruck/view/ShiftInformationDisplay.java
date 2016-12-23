package ca.mcgill.ecse321.foodtruck.view;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Time;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerDateModel;

import ca.mcgill.ecse321.foodtruck.application.FoodTruckManager.Permissions;
import ca.mcgill.ecse321.foodtruck.controllers.InvalidInputException;
import ca.mcgill.ecse321.foodtruck.controllers.ShiftController;
import ca.mcgill.ecse321.foodtruck.model.Employee;
import ca.mcgill.ecse321.foodtruck.model.EmployeeManager;
import ca.mcgill.ecse321.foodtruck.model.Shift;


public class ShiftInformationDisplay extends JPanel {

	Shift associatedShift;
	ShiftManagerMenu shiftManager;

	JLabel employeeNameL;
	
	private HashMap<Integer, Employee> employees;
	int selectedEmployee =0;
	JComboBox employeeList;
	
	JLabel timeL;
	JTextField timeF;
	
	JSpinner endTimeSpinner;
	JSpinner startTimeSpinner;
	
	JButton editBTN;
	JButton deleteBTN;
		
	private boolean editing = false;
	private Shift shiftHolder;
	
	private static long defautlShiftDuration =  2*60*60*1000;
	
	private String editErrorMessage="";
	private JLabel errorLabel = new JLabel();

	private Permissions permissionLevel;
	/**
	 * @wbp.parser.constructor
	 */
	ShiftInformationDisplay(ShiftManagerMenu s, Permissions perm)
	{		
		this(s,new Shift(new java.sql.Date(s.getCurrentDisplayedDate().getTime()),new Time(Calendar.getInstance().getTime().getTime()),new Time(Calendar.getInstance().getTime().getTime()+defautlShiftDuration),EmployeeManager.getInstance().getEmployee(0)),perm);
	}
	
	ShiftInformationDisplay(ShiftManagerMenu s, Shift shift, Permissions perm) {
		
		
		permissionLevel =perm;
		
		associatedShift = shift;
		shiftManager = s;
		
		if(!ShiftController.shiftExists(associatedShift)){
			try {
				ShiftController.AddShift(associatedShift);
			} catch (InvalidInputException e1) {
				JOptionPane.showMessageDialog(this,
						   e1.getMessage(),
						    "Add Error",
						    JOptionPane.ERROR_MESSAGE);
			}
			editing=true;
		}



		this.setLayout(new FlowLayout());
		employeeNameL = new JLabel();
		timeL = new JLabel();

		// Add commands
		editBTN = new JButton("Edit");
		
		editBTN.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e) {
				refreshView();
			}
		});
		
		deleteBTN = new JButton("Delete");
		deleteBTN.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e) {
				
				try {
					ShiftController.removeShift(associatedShift);
					shiftManager.refreshView();
				} catch (InvalidInputException e1) {
					
					System.out.println("can't delete");
					//can't delete
					//TODO
				}
				
			}
		});
		
		employees = new HashMap<Integer,Employee>();
		employeeList = new JComboBox();
		
		endTimeSpinner = new JSpinner (new SpinnerDateModel());
		JSpinner.DateEditor endTimeEditor = new JSpinner.DateEditor(endTimeSpinner, "HH:mm");
		endTimeSpinner.setEditor(endTimeEditor); //Will only show the current time
		endTimeSpinner.setValue(associatedShift.getEndTime());
		
		startTimeSpinner = new JSpinner (new SpinnerDateModel());
		JSpinner.DateEditor startTimeEditor = new JSpinner.DateEditor(startTimeSpinner, "HH:mm");
		startTimeSpinner.setEditor(startTimeEditor); //Will only show the current time
		startTimeSpinner.setValue(associatedShift.getStartTime());
		
		
		Iterator<Employee> employeeIterator = EmployeeManager.getInstance().getEmployees().iterator();

		Integer index =0;
		selectedEmployee =0;
		while(employeeIterator.hasNext())
		{
			Employee emp = employeeIterator.next();
			employees.put(index, emp);
			employeeList.addItem(emp.getLast_name() +" , "+emp.getFirst_name());

			if(emp.equals(associatedShift.getEmployee()))
			{
				selectedEmployee = index;
			}
			index++;
		}
		employeeList.setSelectedIndex(selectedEmployee);
		
		this.add(timeL);
		this.add(startTimeSpinner);
		this.add(endTimeSpinner);
		endTimeSpinner.setVisible(false);
		startTimeSpinner.setVisible(false);
		this.add(employeeNameL);
		this.add(employeeList);
		employeeList.setVisible(false);

		this.add(editBTN);
		this.add(deleteBTN);
		
		this.add(errorLabel);
		
		this.setSize(366, 98);
		this.setVisible(true);

//		refreshView();
		setVisibility();
	}

	private void setVisibility()
	{
		boolean visibility = true;
		switch(permissionLevel)
		{
		case Manager:
			break;
		case Cook:
		case Cashier:
			visibility = false;
			break;
		}
		
		this.editBTN.setVisible(visibility);
		this.editing = false;
		refreshView();
		this.deleteBTN.setVisible(visibility);

	}
	
	private void refreshView()
	{
		if(editing)
		{
			employeeList.removeAllItems();
						
			Iterator<Employee> employeeIterator = EmployeeManager.getInstance().getEmployees().iterator();

			Integer index =0;
			selectedEmployee =0;
			while(employeeIterator.hasNext())
			{
				Employee emp = employeeIterator.next();
				employees.put(index, emp);
				employeeList.addItem(emp.getLast_name() +" , "+emp.getFirst_name());
				
				if(emp.equals(associatedShift.getEmployee()))
				{
					selectedEmployee = index;
				}
				index++;
			}
			employeeList.setSelectedIndex(selectedEmployee);
			
			
			employeeNameL.setVisible(false);
			employeeList.setVisible(true);
			editBTN.setText("Done");
			deleteBTN.setVisible(true);
			
			timeL.setVisible(false);
			startTimeSpinner.setVisible(true);
			endTimeSpinner.setVisible(true);
		}
		else
		{
			Calendar calendar = Calendar.getInstance();

			//Set start time
			calendar.setTime((Date) startTimeSpinner.getValue());
			Time startTime = new Time(calendar.getTime().getTime());
			
			//Set end time
			calendar.setTime((Date) endTimeSpinner.getValue());
			Time endTime = new Time(calendar.getTime().getTime());
			
			Shift tempShift = associatedShift;
			
			try {
				ShiftController.editShift(associatedShift, new java.sql.Date(shiftManager.getCurrentDisplayedDate().getTime()), startTime, endTime, employees.get(employeeList.getSelectedIndex()));
				associatedShift.setStartTime(startTime);
				associatedShift.setEndTime(endTime);
				associatedShift.setEmployee(employees.get(employeeList.getSelectedIndex()));	
			} catch (InvalidInputException e2) {
			
				JOptionPane.showMessageDialog(this.shiftManager,
					    e2.getMessage(),
					    "Edit Error",
					    JOptionPane.ERROR_MESSAGE);
				return;
				
			}

			this.setEditError("");

			editBTN.setText("Edit");
			deleteBTN.setVisible(false);
			
			employeeNameL.setVisible(true);
			employeeList.setVisible(false);
			
			timeL.setVisible(true);
			startTimeSpinner.setVisible(false);
			endTimeSpinner.setVisible(false);
		}
		
		employeeNameL.setText(associatedShift.getEmployee().getLast_name() + "," + associatedShift.getEmployee().getFirst_name());
		timeL.setText(associatedShift.getStartTime().toString() + " - " + associatedShift.getEndTime().toString());
		
		editBTN.revalidate();
		deleteBTN.revalidate();
		employeeNameL.revalidate();
		timeL.revalidate();
		
		editing = !editing;
	}
	
	public void setEditError(String s)
	{
		editErrorMessage = s;
		errorLabel.setText(editErrorMessage);
		errorLabel.revalidate();
		errorLabel.repaint();	
	}



}
