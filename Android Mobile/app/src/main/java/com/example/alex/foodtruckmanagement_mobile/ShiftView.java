package com.example.alex.foodtruckmanagement_mobile;

import android.content.Intent;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.Layout;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import java.io.File;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Date;
import java.util.Iterator;


import ca.mcgill.ecse321.foodtruck.controllers.InvalidInputException;
import ca.mcgill.ecse321.foodtruck.controllers.ShiftController;
import ca.mcgill.ecse321.foodtruck.model.Employee;
import ca.mcgill.ecse321.foodtruck.model.EmployeeManager;
import ca.mcgill.ecse321.foodtruck.model.Shift;
import ca.mcgill.ecse321.foodtruck.model.ShiftManager;
import ca.mcgill.ecse321.foodtruck.persistence.PersistenceEmployeeRegistration;
import ca.mcgill.ecse321.foodtruck.persistence.PersistenceShiftRegistration;

public class ShiftView extends AppCompatActivity {

    Context context;
    private Button addShiftBtn;
    private Integer selectedEmployee = -1;
    private HashMap<Integer, Employee> employees;
    private Spinner employeesSpinner;
    public static boolean checkLaunch = true;
    private String error;
    private ListView shiftList;
    private LinearLayout managerAddLayout;
    private CustomShiftListAdapter adapter;
    private int permission;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shift);
        if (checkLaunch) {
            PersistenceEmployeeRegistration.setFileName(getFilesDir().getAbsolutePath() + File.separator + "employeeregistration.xml");
            PersistenceEmployeeRegistration.loadEmployeeManagementModel();
            PersistenceShiftRegistration.setFileName(getFilesDir().getAbsolutePath() + File.separator + "shiftregistration.xml");
            PersistenceShiftRegistration.loadShiftManagementModel();
            checkLaunch = false;
        }

        Intent mIntent = getIntent();
        permission = mIntent.getIntExtra("Permission", 0);

        if (permission == 1 || permission == 2) {
            managerAddLayout = (LinearLayout) findViewById(R.id.managerShiftLayout);
            managerAddLayout.setVisibility(View.INVISIBLE);
        }

        employeesSpinner = (Spinner) findViewById(R.id.employeeSpinnerShiftMain);

        employeesSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapter, View v, int position, long id) {
                // On selecting a spinner item
                selectedEmployee = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });


        TextView dateView = (TextView) findViewById(R.id.shiftDate);

        dateView.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {
                // When user changed the Text

            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {

            }

            @Override
            public void afterTextChanged(Editable arg0) {
//                adapter.getFilter().filter(arg0);
                refreshData();
            }
        });

        // ALEX: WAS PLANNING ON IMPLEMENTING A DIALOG BOX, DID NOT HAVE TIME TO DO THAT UNFORTUNATELY

        refreshData();

    }

    public void createShift(View view) {

        error = null;
        TextView startView = (TextView) findViewById(R.id.shiftStartTimeMain);
        TextView endView = (TextView) findViewById(R.id.shiftEndTimeMain);
        TextView dateView = (TextView) findViewById(R.id.shiftDate);
        TextView errorMessage = (TextView) findViewById(R.id.errorShift);

        ShiftController sc = new ShiftController();

        SimpleDateFormat fmt = new SimpleDateFormat("hh:mm");

        Calendar calendar = Calendar.getInstance();
        try {
            calendar.setTime(fmt.parse(startView.getText().toString()));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        calendar.set(2000, 1, 1);
        Time startTime = new Time(calendar.getTime().getTime());

        try {
            calendar.setTime(fmt.parse(endView.getText().toString()));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        calendar.set(2000, 1, 1);
        Time endTime = new Time(calendar.getTime().getTime());

        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
        Date parsed = null;
        try {
            parsed = format.parse(dateView.getText().toString());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        java.sql.Date sqlDate = new java.sql.Date(parsed.getTime());

        try {
            errorMessage.setText("");
            if(selectedEmployee != -1) {
                sc.AddShift(new Shift(sqlDate, startTime, endTime, employees.get(selectedEmployee)));
                refreshData();
            }

        } catch (InvalidInputException e) {
            error = e.getMessage();
            errorMessage.setText(error);
        }
    }

    public void showDatePickerDialog(View v) {
        TextView tf = (TextView) v;
        Bundle args = getDateFromLabel(tf.getText());
        args.putInt("id", v.getId());
        DatePickerFragment newFragment = new DatePickerFragment();
        newFragment.setArguments(args);
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }

    public void showTimePickerDialog(View v) {
        TextView tf = (TextView) v;
        Bundle args = getTimeFromLabel(tf.getText());
        args.putInt("id", v.getId());
        TimePickerFragment newFragment = new TimePickerFragment();
        newFragment.setArguments(args);
        newFragment.show(getSupportFragmentManager(), "timePicker");
    }

    private Bundle getTimeFromLabel(CharSequence text) {
        Bundle rtn = new Bundle();
        String comps[] = text.toString().split(":");
        int hour = 12;
        int minute = 0;
        if (comps.length == 2) {
            hour = Integer.parseInt(comps[0]);
            minute = Integer.parseInt(comps[1]);
        }
        rtn.putInt("hour", hour);
        rtn.putInt("minute", minute);
        return rtn;
    }

    private Bundle getDateFromLabel(CharSequence text) {
        Bundle rtn = new Bundle();
        String comps[] = text.toString().split("-");
        int day = 1;
        int month = 1;
        int year = 1;
        if (comps.length == 3) {
            day = Integer.parseInt(comps[0]);
            month = Integer.parseInt(comps[1]);
            year = Integer.parseInt(comps[2]);
        }
        rtn.putInt("day", day);
        rtn.putInt("month", month - 1);
        rtn.putInt("year", year);
        return rtn;
    }

    public void setTime(int id, int h, int m) {
        TextView tv = (TextView) findViewById(id);
        tv.setText(String.format("%02d:%02d", h, m));
    }

    public void setDate(int id, int d, int m, int y) {
        TextView tv = (TextView) findViewById(id);
        tv.setText(String.format("%02d-%02d-%04d", d, m + 1, y));
    }

    public void refreshData() {
        EmployeeManager em = EmployeeManager.getInstance();


        // PUT ADPATER IN REFRESH DATA
        if (error == null || error.length() == 0) {
            employeesSpinner = (Spinner) findViewById(R.id.employeeSpinnerShiftMain);
            ArrayAdapter<CharSequence> employeeAdapter = new
                    ArrayAdapter<CharSequence>(this, R.layout.spinner_layout);
            employeeAdapter.setDropDownViewResource(
                    android.R.layout.simple_spinner_dropdown_item);
            this.employees = new HashMap<Integer, Employee>();
            int i = 0;
            for (Iterator<Employee> employees = em.getEmployees().iterator();
                 employees.hasNext(); i++) {
                Employee e = employees.next();
                employeeAdapter.add(e.getLast_name() + ", " + e.getFirst_name());
                this.employees.put(i, e);
            }
            selectedEmployee = -1;
            employeesSpinner.setAdapter(employeeAdapter);
        }


        context = this;

        TextView dateView = (TextView) findViewById(R.id.shiftDate);


        ShiftManager sm = ShiftManager.getInstance();

        shiftList = (ListView) findViewById(R.id.shiftList);
        shiftList.setTextFilterEnabled(true);

        ArrayList<Shift> FilteredArrayDates = new ArrayList<Shift>();

        if (sm.hasShifts() == true) {
            for (int i = 0; i < sm.getShifts().size(); i++) {
                SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
                java.util.Date parsed = null;
                try {
                    parsed = format.parse(dateView.getText().toString());
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                java.sql.Date sqlDate = new java.sql.Date(parsed.getTime());

                if (sm.getShifts().get(i).getShiftDate().toString().equals(sqlDate.toString())) {
                    FilteredArrayDates.add(sm.getShifts().get(i));
                }
            }

            adapter = new CustomShiftListAdapter(this, FilteredArrayDates);
            shiftList.setAdapter(adapter);

            shiftList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view,
                                        int position, long id) {
                    ShiftController sc = new ShiftController();
                    try {
                        sc.removeShift(adapter.getItem(position));
                    } catch (InvalidInputException e) {
                        e.printStackTrace();
                    }
                    refreshData();
                }
            });

        }


    }
}
