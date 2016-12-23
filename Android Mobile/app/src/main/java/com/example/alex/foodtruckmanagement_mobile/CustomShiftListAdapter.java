package com.example.alex.foodtruckmanagement_mobile;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import ca.mcgill.ecse321.foodtruck.model.Shift;

/**
 * Created by Alex on 2016-12-03.
 */

public class CustomShiftListAdapter extends ArrayAdapter<Shift> implements Filterable {

    List<Shift> shifts;
    private ArrayList<Shift> mOriginalValues;

    public CustomShiftListAdapter(Context context, List<Shift> shifts) {
        super(context, 0, shifts);
        this.shifts = shifts;
        mOriginalValues = new ArrayList<Shift>(shifts);
    }


    public int getCount() {
        return shifts.size();
    }

    public Shift getItem(int position) {
        return shifts.get(position);
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        Shift shift = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.custom_shift_list, parent, false);
        }
        // Lookup view for data population
        TextView tvName = (TextView) convertView.findViewById(R.id.employeeListNameView);
        TextView tvStartTime = (TextView) convertView.findViewById(R.id.startTimeListView);
        TextView tvEndTime = (TextView) convertView.findViewById(R.id.endTimeListView);

        tvName.setText(shift.getEmployee().getFirst_name() + " " + shift.getEmployee().getLast_name());
        tvStartTime.setText(String.valueOf(shift.getStartTime()));
        tvEndTime.setText(String.valueOf(shift.getEndTime()));

        // Return the completed view to render on screen
        return convertView;
    }

    Filter filter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults filterResults = new FilterResults();
            ArrayList<Shift> FilteredArrayDates = new ArrayList<Shift>();
            //constraint is the result from text you want to filter against.
            //objects is your data set you will filter from
            if (constraint != null && mOriginalValues != null) {
                for (int i = 0; i < mOriginalValues.size(); i++) {
                    SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
                    java.util.Date parsed = null;
                    try {
                        parsed = format.parse(constraint.toString());
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    java.sql.Date sqlDate = new java.sql.Date(parsed.getTime());

                    if (mOriginalValues.get(i).getShiftDate().toString().equals(sqlDate.toString())) {
                        FilteredArrayDates.add(mOriginalValues.get(i));
                    }
                }

                filterResults.values = FilteredArrayDates;
                filterResults.count = FilteredArrayDates.size();

            } else {
                synchronized (shifts) {
                    filterResults.values = shifts;
                    filterResults.count = shifts.size();
                }
            }


            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            shifts = (ArrayList<Shift>) results.values;
            notifyDataSetChanged();
        }

    };

    @Override
    public Filter getFilter() {
        return filter;
    }
}