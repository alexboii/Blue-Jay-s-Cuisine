package com.example.alex.foodtruckmanagement_mobile;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class SelectView extends AppCompatActivity {

    Button employeesBtn, shiftsBtn, inventoryBtn, menuBtn, ordersBtn;

    private int permission;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_view);
        Intent mIntent = getIntent();
        permission = mIntent.getIntExtra("Permission", 0);
        employeesBtn = (Button) findViewById(R.id.btnEmployees);
        shiftsBtn = (Button) findViewById(R.id.btnShifts);
        inventoryBtn = (Button) findViewById(R.id.btnInventory);
        menuBtn = (Button) findViewById(R.id.btnMenu);
        ordersBtn = (Button) findViewById(R.id.btnOrders);

        if (permission == 1) {
            employeesBtn.setEnabled(false);
            inventoryBtn.setEnabled(false);
            menuBtn.setEnabled(false);
            employeesBtn.setTextColor(Color.RED);
            inventoryBtn.setTextColor(Color.RED);
            menuBtn.setTextColor(Color.RED);
        }
        if (permission == 2) {
            employeesBtn.setEnabled(false);
            menuBtn.setEnabled(false);
            employeesBtn.setTextColor(Color.RED);
            menuBtn.setTextColor(Color.RED);
        }
    }

    public void goToEmployeeMenu(View v) {
        Intent intent = new Intent(v.getContext(), EmployeeView.class);
        startActivity(intent);
    }

    public void goToShiftMenu(View v) {
        Intent intent = new Intent(v.getContext(), ShiftView.class);
        intent.putExtra("Permission", permission);
        startActivity(intent);
    }

    public void goToInventoryMenu(View v) {
        Intent intent = new Intent(v.getContext(), InventoryView.class);
        startActivity(intent);
    }

    public void goToMenuItemMenu(View v) {
        Intent intent = new Intent(v.getContext(), MenuItemView.class);
        startActivity(intent);
    }

    public void goToOrderMenu(View v) {
        Intent intent = new Intent(v.getContext(), OrderView.class);
        startActivity(intent);
    }

    public int getPermission() {

        return permission;
    }

    public void setPermission(int permission) {

        this.permission = permission;
    }
}
