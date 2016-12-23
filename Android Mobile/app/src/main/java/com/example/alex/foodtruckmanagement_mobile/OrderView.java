package com.example.alex.foodtruckmanagement_mobile;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;

import java.io.File;
import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;

import ca.mcgill.ecse321.foodtruck.controllers.InvalidInputException;
import ca.mcgill.ecse321.foodtruck.controllers.TransactionController;
import ca.mcgill.ecse321.foodtruck.model.MenuItem;
import ca.mcgill.ecse321.foodtruck.model.MenuManager;
import ca.mcgill.ecse321.foodtruck.model.Transaction;
import ca.mcgill.ecse321.foodtruck.persistence.PersistenceMenuRegistration;
import ca.mcgill.ecse321.foodtruck.persistence.PersistenceTransactionRegistration;

public class OrderView extends AppCompatActivity {

    private LinearLayout addMenuItemLayout;
    private boolean checkLaunch = true;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;
    private NDSpinner menuItemSpinner;
    private ArrayList<MenuItem> menuItems;
    private ArrayList<Double> price;
    private CustomMenuItemAdapter menuItemsAdapter;
    private ListView menuItemsListView;
    private int selectionCounter = 0;
    private String error = null;
    private HashMap<Integer, MenuItem> menuItemsSpinner;
    private int selectedMenuItem = -1;
    private TextView errorView;
    private ArrayList<Integer> quantities;
    private TextView orderTotal;
    private double total = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (checkLaunch) {
            PersistenceMenuRegistration.setFileName(getFilesDir().getAbsolutePath() + File.separator + "menuregistration.xml");
            PersistenceMenuRegistration.loadMenuManagementModel();
            PersistenceTransactionRegistration.setFileName(getFilesDir().getAbsolutePath() + File.separator + "itransactionregistration.xml");
            PersistenceTransactionRegistration.loadTransactionManagementModel();
            checkLaunch = false;
        }

        setContentView(R.layout.activity_order_view);
        errorView = (TextView) findViewById(R.id.orderErrorMessage);
        menuItemSpinner = (NDSpinner) findViewById(R.id.menuItemSpinner);
        menuItemsListView = (ListView) findViewById(R.id.menuItemListView);

        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();

        menuItems = new ArrayList<>();
        price = new ArrayList<>();
        quantities = new ArrayList<>();
        orderTotal = (TextView) findViewById(R.id.orderTotalPrice);


        menuItemsAdapter =
                new CustomMenuItemAdapter(this, menuItems);
        menuItemsListView.setAdapter(menuItemsAdapter);
        menuItemsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                menuItems.remove(position);
                total -= price.get(position);
                price.remove(position);
                quantities.remove(position);
                String totalString = String.format("%.2f", total);
                orderTotal.setText(totalString);
                menuItemsAdapter.notifyDataSetChanged();
            }
        });
        menuItemSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapter, View v, int position, long id) {
                if (selectionCounter > 0) {
                    MenuManager mm = MenuManager.getInstance();
                    menuItems.add(mm.getMenu_item(mm.indexOfMenu_item(menuItemsSpinner.get(position))));
                    price.add(mm.getMenu_item(mm.indexOfMenu_item(menuItemsSpinner.get(position))).getPrice());
                    total += mm.getMenu_item(mm.indexOfMenu_item(menuItemsSpinner.get(position))).getPrice();
                    quantities.add(1);
                    String totalString = String.format("%.2f", total);
                    orderTotal.setText(totalString);
                }
                selectionCounter++;
                menuItemsAdapter.notifyDataSetChanged();

            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });
        refreshMenuItemData();
    }

    public void registerOrder(View v) {
        TransactionController tc = new TransactionController();

        Calendar currentTime = Calendar.getInstance();
        Date date = new Date((currentTime.getTime().getTime()));
        Time time = new Time((currentTime.getTime()).getTime());
        double total = 0;
        for (int i = 0; i < price.size(); i++) {
            total += price.get(i);
        }
        if (error == null) {
            error = null;
            errorView.setText(error);
            try {
                tc.AddTransaction(new Transaction(date, time, total), menuItems, quantities);
                refreshData(total);
                clearOrder();
            } catch (InvalidInputException e) {
                error += e.getMessage();
                errorView.setText(error);
            }
        }
    }

    private void refreshMenuItemData() {

        MenuManager mm = MenuManager.getInstance();

        if (error == null || error.length() == 0) {
            ArrayAdapter<CharSequence> menuItemAdapter = new
                    ArrayAdapter<CharSequence>(this, R.layout.spinner_layout);
            menuItemAdapter.setDropDownViewResource(
                    android.R.layout.simple_spinner_dropdown_item);
            this.menuItemsSpinner = new HashMap<Integer, MenuItem>();
            int i = 0;
            for (Iterator<MenuItem> menuItems = mm.getMenu_items().iterator();
                 menuItems.hasNext(); i++) {
                MenuItem mi = menuItems.next();
                menuItemAdapter.add(mi.getName() + "  " + mi.getPrice() + "$");
                this.menuItemsSpinner.put(i, mi);
            }
            selectedMenuItem = -1;
            menuItemSpinner.setAdapter(menuItemAdapter);
        }

    }

    private void refreshData(double total) {
        orderTotal.setText(Double.toString(total));
    }

    private void clearOrder() {
        menuItems.clear();
        price.clear();
        quantities.clear();
        menuItemsAdapter.notifyDataSetChanged();
        total = 0;
        orderTotal.setText("0.00");
    }

    public void goToWeeklyFavorites(View v) {
        Intent intent = new Intent(v.getContext(), WeeklyFavoriteView.class);
        startActivity(intent);
    }

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("OrderView Page") // TODO: Define a title for the content shown.
                // TODO: Make sure this auto-generated URL is correct.
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
