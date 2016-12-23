package ca.mcgill.ecse321.foodtruck.view;

import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import org.jdatepicker.impl.DateComponentFormatter;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.SqlDateModel;

import ca.mcgill.ecse321.foodtruck.application.FoodTruckManager.Permissions;
import ca.mcgill.ecse321.foodtruck.controllers.ShiftController;
import ca.mcgill.ecse321.foodtruck.model.Employee;
import ca.mcgill.ecse321.foodtruck.model.EmployeeManager;
import ca.mcgill.ecse321.foodtruck.model.Shift;
import ca.mcgill.ecse321.foodtruck.model.ShiftManager;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.sql.Time;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class ShiftManagerMenu extends JPanel implements ActionListener {

	JDatePickerImpl shiftDate;
	JButton addShiftBTN;
	JPanel shiftAddingPanel;
	private boolean adding = false;
	
	private JPanel shiftsHolder;
	private LinkedList<Shift> displayedShifts;
	
	private LinkedList<ShiftInformationDisplay> displayedShiftPanels;
	
	JPanel calendarHolder;
	JScrollPane scrollFrame;
	
	Permissions permissionLevel;
	/**
	 * Initialize the contents of the frame.
	 */
	public ShiftManagerMenu(Permissions perm) {
		super();
		
		permissionLevel = perm;
	    calendarHolder = new JPanel();

		this.setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
		Date eventDate = new Date(Calendar.getInstance().getTime().getTime());
		this.setBounds(100, 100, 450, 300);
		this.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		JPanel panel = new JPanel();
		FlowLayout flowLayout = (FlowLayout) panel.getLayout();
		this.add(panel);
		
		SqlDateModel model = new SqlDateModel();
		Properties p = new Properties();
		p.put("text.today", "Today");
		p.put("text.month", "Month");
		p.put("text.year", "Year");
		JDatePanelImpl datePanel = new JDatePanelImpl(model, p);
		shiftDate = new JDatePickerImpl(datePanel, new DateComponentFormatter());
		calendarHolder.add(shiftDate);
		
		
		
		addShiftBTN = new JButton("+");
		calendarHolder.add(addShiftBTN);
		
		this.add(calendarHolder);
		
		addShiftBTN.addActionListener(this);
		
		shiftsHolder = new JPanel();
		scrollFrame = new JScrollPane(shiftsHolder);
		shiftsHolder.setAutoscrolls(true);
		shiftsHolder.setLayout(new BoxLayout(shiftsHolder,BoxLayout.Y_AXIS));

		scrollFrame.setPreferredSize(new Dimension(450, 300));
		
		//this.getContentPane().add(shiftsHolder);
		this.add(scrollFrame);
		
		displayedShifts = new LinkedList<Shift>();
		displayedShiftPanels = new LinkedList<ShiftInformationDisplay>();
		
		Calendar cal = Calendar.getInstance();
		cal.roll(Calendar.DAY_OF_YEAR, -1);
		shiftDate.getModel().setDay(cal.get(Calendar.DAY_OF_YEAR));
		shiftDate.getModel().setMonth(cal.get(Calendar.MONTH));
		shiftDate.getModel().setYear(cal.get(Calendar.YEAR));
		shiftDate.getModel().setSelected(true);
		shiftDate.getModel().addChangeListener(new ChangeListener(){

			@Override
			public void stateChanged(ChangeEvent e) {
				
				refreshView();
			}
		});

		this.setSize(503,268);
		shiftsHolder.setVisible(true);	
		
		refreshView();
	}
	
	public void refreshView()
	{
		displayedShifts.clear();

		for(ShiftInformationDisplay p: displayedShiftPanels)
		{
			shiftsHolder.remove(p);
		}
		displayedShiftPanels.clear();
		
		Date d = (Date) shiftDate.getModel().getValue();
		
		
		List<Shift> shifts =ShiftManager.getInstance().getShifts();

		for(Shift s:shifts)
		{

			if(s.getShiftDate().toString().equals(d.toString()))
			{
				displayedShifts.add(s);
				ShiftInformationDisplay sIP = new ShiftInformationDisplay(this,s,permissionLevel);
				shiftsHolder.add(sIP);
				displayedShiftPanels.add(sIP);
			}
		}
		
		setPermission();
		
		scrollFrame.revalidate();
		shiftsHolder.revalidate();
		calendarHolder.revalidate();
		this.revalidate();
		
		scrollFrame.repaint();
		shiftsHolder.repaint();
		calendarHolder.repaint();
		this.repaint();
	}
	
	private void setPermission()
	{
		boolean visibility = false;
		switch(permissionLevel)
		{
			case Cook:
				break;
			case Cashier:
				break;
			case Manager:
				visibility = true;
				break;
		}
		this.addShiftBTN.setVisible(visibility);
	}
	
	
	public Date getCurrentDisplayedDate()
	{
		return (Date) shiftDate.getModel().getValue();

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(EmployeeManager.getInstance().hasEmployees()){
			ShiftInformationDisplay sIP = new ShiftInformationDisplay(this,permissionLevel);
			shiftsHolder.add(sIP);
			displayedShiftPanels.add(sIP);
			
			scrollFrame.revalidate();
			shiftsHolder.revalidate();
			this.revalidate();
		}
		else
		{
			JOptionPane.showMessageDialog(this,
				   "No Employees Registered",
				    "Add Error",
				    JOptionPane.ERROR_MESSAGE);
		}
		  
	}

}
