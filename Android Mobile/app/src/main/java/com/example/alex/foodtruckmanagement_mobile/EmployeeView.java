package com.example.alex.foodtruckmanagement_mobile;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import java.io.File;
import java.util.HashMap;
import java.util.Iterator;

import ca.mcgill.ecse321.foodtruck.controllers.EmployeeController;
import ca.mcgill.ecse321.foodtruck.controllers.InvalidInputException;
import ca.mcgill.ecse321.foodtruck.model.Employee;
import ca.mcgill.ecse321.foodtruck.model.EmployeeManager;
import ca.mcgill.ecse321.foodtruck.persistence.PersistenceEmployeeRegistration;

public class EmployeeView extends AppCompatActivity {

    private TextView firstNameTV, lastNameTV, hoursTV, wageTV, errorView;
    private TextView firstNameDisplay, lastNameDisplay, hoursDisplay, wageDisplay;
    private Switch statusDisplay;
    private CheckBox employedCB;
    private Spinner spinner;
    private String error = null;
    private FrameLayout info;
    private HashMap<Integer, Employee> employees;
    private Integer selectedEmployee = -1;
    private static boolean checkLaunch = true;
    private static Employee employee;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (checkLaunch) {
            PersistenceEmployeeRegistration.setFileName(getFilesDir().getAbsolutePath() + File.separator + "employeeregistration.xml");
            PersistenceEmployeeRegistration.loadEmployeeManagementModel();
            checkLaunch = false;
        }


        setContentView(R.layout.activity_employee_view);

        info = (FrameLayout) findViewById(R.id.infoLayout);
        spinner = (Spinner) findViewById(R.id.employeeSpinner);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapter, View v, int position, long id) {
                info.setVisibility(View.VISIBLE);
                firstNameDisplay = (TextView) findViewById(R.id.firstNameDisplay);
                lastNameDisplay = (TextView) findViewById(R.id.lastNameDisplay);
                hoursDisplay = (TextView) findViewById(R.id.workHoursDisplay);
                wageDisplay = (TextView) findViewById(R.id.wageDisplay);
                statusDisplay = (Switch) findViewById(R.id.statusDisplay);

                firstNameDisplay.setText(employees.get(position).getFirst_name());
                lastNameDisplay.setText(employees.get(position).getLast_name());
                hoursDisplay.setText(String.valueOf(employees.get(position).getWeekly_hours()));
                wageDisplay.setText(String.valueOf(employees.get(position).getHourly_salary()));

                employee = employees.get(position);
                if (employees.get(position).isCurrently_employed()) {
                    statusDisplay.setChecked(true);
                } else {
                    statusDisplay.setChecked(false);
                }

                //TODO: DISPLAY ID, OUTSIDE OF SCOPE OF USE CASE IMPLEMENTED THOUGH


            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        refreshData();
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    public void employeeAdd(View v) {

        firstNameTV = (TextView) findViewById(R.id.firstNameField);
        lastNameTV = (TextView) findViewById(R.id.lastNameField);
        hoursTV = (TextView) findViewById(R.id.workHoursField);
        wageTV = (TextView) findViewById(R.id.wageField);
        employedCB = (CheckBox) findViewById(R.id.employedCB);
        errorView = (TextView) findViewById(R.id.errorMessage);

        error = null;


        EmployeeController ec = new EmployeeController();

        String first_name, last_name;
        float weekly_hours = 0;
        double wage = 0;
        boolean isEmployed = false;

        first_name = firstNameTV.getText().toString();
        last_name = lastNameTV.getText().toString();

        // THESES ERRORS WERE NOT HANDLED IN THE CONTROLLER, BUT THAT IS BECAUSE
        // THIS PART HAS NOTHING TO DO WITH THE ACTUAL ADDING OF THE EMPLOYEE
        // IT SIMPLY ENSURES THAT THE VALUES TAKEN FROM THE TEXTVIEWS CAN BE PARSED
        // TO FLOATS WITHOUT THE APP CRASHING AND THROWING AN INVALID INPUT EXCEPTION
        try {
            weekly_hours = Float.valueOf(hoursTV.getText().toString());
        } catch (Exception e) {
            error = "Weekly hours has to be a number!\n";
            errorView.setText(error);
        }
        try {
            wage = Double.valueOf(wageTV.getText().toString());
        } catch (Exception e) {
            error += "Wage has to be a number!\n";
            errorView.setText(error);
        }

        isEmployed = employedCB.isChecked();

        if (error == null) {
            error = null;
            errorView.setText(error);
            try {
                ec.createEmployee(first_name, last_name, weekly_hours, wage, isEmployed);

            } catch (InvalidInputException e) {
                error += e.getMessage();
                errorView.setText(error);
            }
        }

        refreshData();

    }

    public void employeeEdit(View v) {
        firstNameTV = (TextView) findViewById(R.id.firstNameDisplay);
        lastNameTV = (TextView) findViewById(R.id.lastNameDisplay);
        hoursTV = (TextView) findViewById(R.id.workHoursDisplay);
        wageTV = (TextView) findViewById(R.id.wageDisplay);
        employedCB = (CheckBox) findViewById(R.id.employedCB);
        errorView = (TextView) findViewById(R.id.errorMessage);

        error = null;


        EmployeeController ec = new EmployeeController();

        String first_name, last_name;
        float weekly_hours = 0;
        double wage = 0;
        boolean isEmployed = false;

        first_name = firstNameTV.getText().toString();
        last_name = lastNameTV.getText().toString();
        // THESES ERRORS WERE NOT HANDLED IN THE CONTROLLER, BUT THAT IS BECAUSE
        // THIS PART HAS NOTHING TO DO WITH THE ACTUAL ADDING OF THE EMPLOYEE
        // IT SIMPLY ENSURES THAT THE VALUES TAKEN FROM THE TEXTVIEWS CAN BE PARSED
        // TO FLOATS WITHOUT THE APP CRASHING AND THROWING AN INVALID INPUT EXCEPTION
        try {
            weekly_hours = Float.valueOf(hoursTV.getText().toString());
        } catch (Exception e) {
            error = "Weekly hours has to be a number!\n";
            errorView.setText(error);
        }
        try {
            wage = Double.valueOf(wageTV.getText().toString());
        } catch (Exception e) {
            error += "Wage has to be a number!\n";
            errorView.setText(error);
        }

        isEmployed = employedCB.isChecked();

        if (error == null) {
            error = null;
            errorView.setText(error);
            try {
                ec.EditEmployee(employee, first_name, last_name, weekly_hours, wage, isEmployed);
            } catch (InvalidInputException e) {
                error += e.getMessage();
                errorView.setText(error);
            }
        }

        refreshData();
    }

    private void refreshData() {

        EmployeeManager em = EmployeeManager.getInstance();

        if (error == null || error.length() == 0) {
            spinner = (Spinner) findViewById(R.id.employeeSpinner);
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
            spinner.setAdapter(employeeAdapter);
        }
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Main Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://com.example.alex.foodtruckmanagement_mobile/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Main Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://com.example.alex.foodtruckmanagement_mobile/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }
}