package com.example.alex.foodtruckmanagement_mobile;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class LoginView extends AppCompatActivity {

    private static final int PERMISSION_MANAGER = 3;
    private static final int PERMISSION_COOK = 2;
    private static final int PERMISSION_CASHIER = 1;

    Intent intent;
    Button managerBtn, cookBtn, cashierBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        managerBtn = (Button) findViewById(R.id.btnManager);
        cookBtn = (Button) findViewById(R.id.btnCook);
        cashierBtn = (Button) findViewById(R.id.btnCashier);
        intent = new Intent(this, SelectView.class);

    }

    public void goToSelectViewManager(View v) {
        intent.putExtra("Permission", PERMISSION_MANAGER);
        startActivity(intent);
    }

    public void goToSelectViewCook(View v) {
        intent.putExtra("Permission", PERMISSION_COOK);
        startActivity(intent);
    }

    public void goToSelectViewCashier(View v) {
        intent.putExtra("Permission", PERMISSION_CASHIER);
        startActivity(intent);
    }
}