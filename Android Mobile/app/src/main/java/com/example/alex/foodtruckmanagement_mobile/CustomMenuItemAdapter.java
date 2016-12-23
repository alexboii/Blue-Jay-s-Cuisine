package com.example.alex.foodtruckmanagement_mobile;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import ca.mcgill.ecse321.foodtruck.model.MenuItem;

/**
 * Created by Bogdan on 2016-12-06.
 */

public class CustomMenuItemAdapter extends ArrayAdapter<MenuItem>{
    public CustomMenuItemAdapter (Context context, ArrayList<MenuItem> menuItems) {
        super(context, 0, menuItems);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        MenuItem menuItem = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.custom_menu_item_list, parent, false);
        }
        // Lookup view for data population
        TextView tvIngredient = (TextView) convertView.findViewById(R.id.menuItemTV);
        TextView tvPrice = (TextView) convertView.findViewById(R.id.menuItemPriceTV);
        // Populate the data into the template view using the data object
        tvIngredient.setText(menuItem.getName());
        tvPrice.setText(String.format("%.2f", menuItem.getPrice()));

        // Return the completed view to render on screen
        return convertView;
    }
}
