package com.example.alex.foodtruckmanagement_mobile;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

import ca.mcgill.ecse321.foodtruck.controllers.TransactionController;
import ca.mcgill.ecse321.foodtruck.model.MenuItem;
import ca.mcgill.ecse321.foodtruck.persistence.PersistenceMenuRegistration;
import ca.mcgill.ecse321.foodtruck.persistence.PersistenceTransactionRegistration;

public class WeeklyFavoriteView extends AppCompatActivity {
    private ListView topListView;
    private boolean checkLaunch = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weekly_favorite);

        if (checkLaunch) {
            PersistenceMenuRegistration.setFileName(getFilesDir().getAbsolutePath() + File.separator + "menuregistration.xml");
            PersistenceMenuRegistration.loadMenuManagementModel();
            PersistenceTransactionRegistration.setFileName(getFilesDir().getAbsolutePath() + File.separator + "itransactionregistration.xml");
            PersistenceTransactionRegistration.loadTransactionManagementModel();
            checkLaunch = false;
        }


        Map<MenuItem, Integer> sorted = TransactionController.getTopTenMenuItemsCurrentWeek();
        Iterator<MenuItem> it = sorted.keySet().iterator();
        ArrayList<String> data = new ArrayList<String>();

        int count = 0;
        int position = 0;

        while (count < 10) {
            if (it.hasNext()) {
                MenuItem next = it.next();
                position++;
                data.add(position + ". " + next.getName() + "    $" +  String.format("%.2f", next.getPrice()) + "     " + sorted.get(next) + "      $" +  String.format("%.2f", ((int) sorted.get(next)) * (next.getPrice())));
            }
            count++;
        }

        topListView = (ListView) findViewById(R.id.weeklyFavouriteList);
        ArrayAdapter<String> itemsAdapter =
                new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, data);
        topListView.setAdapter(itemsAdapter);

    }
}
