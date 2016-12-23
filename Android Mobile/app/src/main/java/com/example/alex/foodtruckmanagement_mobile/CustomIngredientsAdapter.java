package com.example.alex.foodtruckmanagement_mobile;

import android.content.ClipData;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import ca.mcgill.ecse321.foodtruck.model.Item;
import ca.mcgill.ecse321.foodtruck.model.Shift;

/**
 * Created by Alex on 12/5/2016.
 */

public class CustomIngredientsAdapter extends ArrayAdapter<Item> {

        public CustomIngredientsAdapter (Context context, ArrayList<Item> items) {
            super(context, 0, items);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // Get the data item for this position
            Item item = getItem(position);
            // Check if an existing view is being reused, otherwise inflate the view
            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.custom_ingredients_list, parent, false);
            }
            // Lookup view for data population
            TextView tvIngredient = (TextView) convertView.findViewById(R.id.ingredientTV);
            // Populate the data into the template view using the data object
            tvIngredient.setText(item.getName());
            // Return the completed view to render on screen
            return convertView;
        }
}
