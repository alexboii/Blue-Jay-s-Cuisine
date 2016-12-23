package com.example.alex.foodtruckmanagement_mobile;

import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;

import java.io.File;
import java.util.HashMap;
import java.util.Iterator;

import ca.mcgill.ecse321.foodtruck.controllers.InvalidInputException;
import ca.mcgill.ecse321.foodtruck.controllers.InventoryController;
import ca.mcgill.ecse321.foodtruck.model.InventoryManager;
import ca.mcgill.ecse321.foodtruck.model.Item;
import ca.mcgill.ecse321.foodtruck.persistence.PersistenceInventoryRegistration;

public class InventoryView extends AppCompatActivity {

    private static boolean checkLaunch = true;
    private TextView inventoryName, inventoryQuantity;
    private RadioGroup inventoryType;
    private TextView spinnerName, spinnerQuantity;
    private RadioGroup spinnerType;
    private FrameLayout info;
    private Spinner spinner;
    private HashMap<Integer, Item> inventoryItems;
    private Item inventoryItem;
    private String error = null;
    private Integer selectedItem= -1;
    private Button editButton;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;
    private TextView errorView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        if (checkLaunch) {
            PersistenceInventoryRegistration.setFileName(getFilesDir().getAbsolutePath() + File.separator + "inventoryregistration.xml");
            PersistenceInventoryRegistration.loadInventoryManagementModel();
            checkLaunch = false;
        }
        setContentView(R.layout.activity_inventory_view);

        info = (FrameLayout) findViewById(R.id.editInfoLayout);
        spinner = (Spinner) findViewById(R.id.inventorySpinner);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {


            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                info.setVisibility(View.VISIBLE);
                spinnerName = (TextView) findViewById(R.id.editInventoryNameField);
                spinnerQuantity = (TextView) findViewById(R.id.editInventoryQuantityField);
                spinnerType = (RadioGroup) findViewById(R.id.editRadioType);

                inventoryItem = inventoryItems.get(position);
                spinnerName.setText(inventoryItems.get(position).getName());
                spinnerQuantity.setText(Integer.toString(inventoryItems.get(position).getQuantity()));

                if (inventoryItems.get(position).getType().equals("Equipment")) {
                    spinnerType.check(spinnerType.getChildAt(0).getId());
                    spinnerType.setClickable(false);
                } else spinnerType.check(spinnerType.getChildAt(1).getId());

                editButton = (Button) findViewById(R.id.editButton);
                editButton.setText("Done");
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        refreshData();
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    public void inventoryAdd(View v) {

        inventoryName = (TextView) findViewById(R.id.addInventoryNameField);
        inventoryQuantity = (TextView) findViewById(R.id.addInventoryQuantityField);
        inventoryType = (RadioGroup) findViewById(R.id.addRadioType);
        errorView = (TextView) findViewById(R.id.errorMessage);

        error = null;

        InventoryController ic = new InventoryController();

        String name;
        int quantity = 0;
        String isEquipment;


        name = inventoryName.getText().toString();

        // THESES ERRORS WERE NOT HANDLED IN THE CONTROLLER, BUT THAT IS BECAUSE
        // THIS PART HAS NOTHING TO DO WITH THE ACTUAL ADDING OF THE EMPLOYEE
        // IT SIMPLY ENSURES THAT THE VALUES TAKEN FROM THE TEXTVIEWS CAN BE PARSED
        // TO FLOATS WITHOUT THE APP CRASHING AND THROWING AN INVALID INPUT EXCEPTION
        try {
            quantity = Integer.valueOf(inventoryQuantity.getText().toString());
        } catch (Exception e) {
            error = "Quantity has to be a number!\n";
            errorView.setText(error);
        }
        isEquipment = ((RadioButton) findViewById(inventoryType.getCheckedRadioButtonId())).getText().toString();

        if (error == null) {
            error = null;
            errorView.setText(error);
            try {
                ic.createItem(new Item(name, isEquipment, quantity));
            } catch (InvalidInputException e) {
                error += e.getMessage();
                errorView.setText(error);
            }
        }

        refreshData();

    }
    public void inventoryEdit(View v)
    {
        spinner.setSelection(0);

        inventoryQuantity = (TextView) findViewById(R.id.editInventoryQuantityField);
        errorView = (TextView) findViewById(R.id.errorMessage);

        error = null;

        InventoryController ic = new InventoryController();

        int quantity = 0;

        // THESES ERRORS WERE NOT HANDLED IN THE CONTROLLER, BUT THAT IS BECAUSE
        // THIS PART HAS NOTHING TO DO WITH THE ACTUAL ADDING OF THE EMPLOYEE
        // IT SIMPLY ENSURES THAT THE VALUES TAKEN FROM THE TEXTVIEWS CAN BE PARSED
        // TO FLOATS WITHOUT THE APP CRASHING AND THROWING AN INVALID INPUT EXCEPTION
        try {
            quantity = Integer.valueOf(inventoryQuantity.getText().toString());
        } catch (Exception e) {
            error = "Quantity has to be a number!\n";
            errorView.setText(error);
        }

        if (error == null) {
            error = null;
            errorView.setText(error);
            try {
                ic.editItemQuantity(inventoryItem, quantity);

            } catch (InvalidInputException e) {
                error += e.getMessage();
                errorView.setText(error);
            }
        }

        refreshData();
        editButton.setText("Edit");

    }
    private void refreshData() {

        InventoryManager im = InventoryManager.getInstance();

        if (error == null || error.length() == 0) {
            spinner = (Spinner) findViewById(R.id.inventorySpinner);
            ArrayAdapter<CharSequence> itemAdapter = new
                    ArrayAdapter<CharSequence>(this, R.layout.spinner_layout);
            itemAdapter.setDropDownViewResource(
                    android.R.layout.simple_spinner_dropdown_item);
            this.inventoryItems = new HashMap<Integer, Item>();
            int i = 0;
            for (Iterator<Item> inventoryItems = im.getItems().iterator();
                 inventoryItems.hasNext(); i++) {
                Item it = inventoryItems.next();
                itemAdapter.add(it.getName() + " x" + it.getQuantity());
                this.inventoryItems.put(i, it);
            }
            selectedItem = -1;
            spinner.setAdapter(itemAdapter);
        }
    }

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("InventoryView Page") // TODO: Define a title for the content shown.
                // TODO: Makesure this auto-generated URL is correct.
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        AppIndex.AppIndexApi.start(client, getIndexApiAction());
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        AppIndex.AppIndexApi.end(client, getIndexApiAction());
        client.disconnect();
    }
}
