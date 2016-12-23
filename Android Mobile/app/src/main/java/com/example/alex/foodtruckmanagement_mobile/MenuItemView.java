package com.example.alex.foodtruckmanagement_mobile;

import android.app.ActionBar;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;

import ca.mcgill.ecse321.foodtruck.controllers.InvalidInputException;
import ca.mcgill.ecse321.foodtruck.controllers.MenuController;
import ca.mcgill.ecse321.foodtruck.controllers.ShiftController;
import ca.mcgill.ecse321.foodtruck.model.InventoryManager;
import ca.mcgill.ecse321.foodtruck.model.Item;
import ca.mcgill.ecse321.foodtruck.model.MenuItem;
import ca.mcgill.ecse321.foodtruck.model.MenuManager;
import ca.mcgill.ecse321.foodtruck.persistence.PersistenceInventoryRegistration;
import ca.mcgill.ecse321.foodtruck.persistence.PersistenceMenuRegistration;

public class MenuItemView extends AppCompatActivity {

    private static boolean checkLaunch = true;
    private TextView errorView;
    private Spinner removeMenuItemsSpinner;
    private NDSpinner ingredientSpinner;
    private TextView menuItemName, menuItemPrice, menuItemDescription;
    private EditText ingredientQuantity;
    private Button ingredientRemoveButton;
    private LinearLayout ingredientLayout;
    private HashMap<Integer, MenuItem> menuItems;
    private HashMap<Integer, Item> inventoryItems;
    private String error = null;
    private ListView ingredientListView;
    private ArrayAdapter<Item> itemsAdapter;
    private int selectionCounter = 0;

    private ArrayList<Item> ingredients;
    private ArrayList<Integer> quantity;

    private int selectedMenuItem = -1, selectedItem = -1;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (checkLaunch) {
            PersistenceMenuRegistration.setFileName(getFilesDir().getAbsolutePath() + File.separator + "menuregistration.xml");
            PersistenceMenuRegistration.loadMenuManagementModel();
            PersistenceInventoryRegistration.setFileName(getFilesDir().getAbsolutePath() + File.separator + "inventoryregistration.xml");
            PersistenceInventoryRegistration.loadInventoryManagementModel();
            checkLaunch = false;
        }
        setContentView(R.layout.activity_menu_item_view);

        removeMenuItemsSpinner = (Spinner) findViewById(R.id.removeMenuItemSpinner);
        errorView = (TextView) findViewById(R.id.menuItemErrorMessage);
        ingredientSpinner = (NDSpinner) findViewById(R.id.ingredientSpinner);
        ingredientListView = (ListView) findViewById(R.id.ingredientListView);


        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();

        ingredients = new ArrayList<>();
        quantity = new ArrayList<>();


        itemsAdapter =
                new CustomIngredientsAdapter(this, ingredients);
        ingredientListView.setAdapter(itemsAdapter);

        ingredientListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                ingredients.remove(position);
                quantity.remove(position);
                itemsAdapter.notifyDataSetChanged();
            }
        });

        ingredientSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapter, View v, int position, long id) {
                if (selectionCounter > 0) {
                    InventoryManager im = InventoryManager.getInstance();
                    ingredients.add(im.getItem(im.indexOfItem(inventoryItems.get(position))));
                    quantity.add(1);
                }
                selectionCounter++;
                itemsAdapter.notifyDataSetChanged();

            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        removeMenuItemsSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapter, View v, int position, long id) {
                selectedMenuItem = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });


        refreshData();
        refreshIngedientData();
    }

    public void addMenuItem(View v) {
        menuItemName = (TextView) findViewById(R.id.addMenuItemNameField);
        menuItemPrice = (TextView) findViewById(R.id.addMenuItemPriceField);
        menuItemDescription = (TextView) findViewById(R.id.addMenuItemDescriptionField);

        error = null;

        MenuController mc = new MenuController();

        String name, description;
        double price = 0;

        name = menuItemName.getText().toString();
        description = menuItemDescription.getText().toString();

        try {
            price = Double.valueOf(menuItemPrice.getText().toString());
        } catch (Exception e) {
            error = "Price has to be a number!\n";
            errorView.setText(error);
        }

        if (error == null) {
            error = null;
            errorView.setText(error);
            try {
                mc.createMenuItem(new MenuItem(name, description, price, true), ingredients, quantity);
                refreshData();
            } catch (InvalidInputException e) {
                error += e.getMessage();
                errorView.setText(error);
            }
        }
    }


    public void removeMenuItem(View v) {
        MenuManager mm = MenuManager.getInstance();
        MenuController mc = new MenuController();

        try {
            mc.removeMenuItem(mm.getMenu_item(selectedMenuItem));
            refreshData();
        } catch (InvalidInputException e) {
            error += e.getMessage();
            errorView.setText(error);
        }
    }

    private void refreshData() {

        MenuManager mm = MenuManager.getInstance();

        if (error == null || error.length() == 0) {
            ArrayAdapter<CharSequence> menuItemAdapter = new
                    ArrayAdapter<CharSequence>(this, R.layout.spinner_layout);
            menuItemAdapter.setDropDownViewResource(
                    android.R.layout.simple_spinner_dropdown_item);
            this.menuItems = new HashMap<Integer, MenuItem>();
            int i = 0;
            for (Iterator<MenuItem> menuItems = mm.getMenu_items().iterator();
                 menuItems.hasNext(); i++) {
                MenuItem mi = menuItems.next();
                menuItemAdapter.add(mi.getName() + "  " + mi.getPrice() + "$");
                this.menuItems.put(i, mi);
            }
            selectedMenuItem = -1;
            removeMenuItemsSpinner.setAdapter(menuItemAdapter);
        }

    }

    private void refreshIngedientData() {
        InventoryManager im = InventoryManager.getInstance();

        ArrayAdapter<CharSequence> inventoryItemAdapter = new
                ArrayAdapter<CharSequence>(this, R.layout.spinner_layout);
        inventoryItemAdapter.setDropDownViewResource(
                android.R.layout.simple_spinner_dropdown_item);
        this.inventoryItems = new HashMap<Integer, Item>();
        int i = 0;
        for (Iterator<Item> Items = im.getItems().iterator();
             Items.hasNext(); i++) {
            Item it = Items.next();
            if (!it.getType().equals("Equipment")) {
                inventoryItemAdapter.add(it.getName());
                this.inventoryItems.put(i, it);
            }
        }
        selectedItem = -1;
        ingredientSpinner.setAdapter(inventoryItemAdapter);
    }

    private void createIngredientSpinner(Spinner spinner) {
        InventoryManager im = InventoryManager.getInstance();

        if (error == null || error.length() == 0) {
            ArrayAdapter<CharSequence> itemAdapter = new
                    ArrayAdapter<CharSequence>(this, R.layout.spinner_layout);
            itemAdapter.setDropDownViewResource(
                    android.R.layout.simple_spinner_dropdown_item);
            this.inventoryItems = new HashMap<Integer, Item>();
            int i = 0;
            for (Iterator<Item> inventoryItems = im.getItems().iterator();
                 inventoryItems.hasNext(); i++) {
                Item it = inventoryItems.next();
                if (it.getType().equals("Ingredient")) {
                    itemAdapter.add(it.getName() + " x" + it.getQuantity());
                    this.inventoryItems.put(i, it);
                }
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
                .setName("MenuItemView Page") // TODO: Define a title for the content shown.
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
