package android.example.furniture_magik_demo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.example.furniture_magik_demo.Adapters.ProductAdapter;
import android.example.furniture_magik_demo.Login.LoginActivity;
import android.example.furniture_magik_demo.Model.Product;
import android.example.furniture_magik_demo.utils.ProductHelper;
import android.example.furniture_magik_demo.utils.SharedPref_Util;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {


    FirebaseAuth mAuth;
    Toolbar toolbar;
    FloatingActionButton actionButton;
    RecyclerView recyclerView;
    ProductHelper dbHelper;
    ProductAdapter adapter;
    private RecyclerView.LayoutManager mLayoutManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance();
        toolbar = findViewById(R.id.main_activity_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Welcome ");
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);

        //populate recyclerview
        populaterecyclerView();
        actionButton = findViewById(R.id.floatingActionButton);
        actionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,AddItem.class);
                startActivity(intent);
            }
        });

    }

    private void populaterecyclerView() {
        dbHelper = new ProductHelper(this);
        adapter = new ProductAdapter(dbHelper.productList(), this, recyclerView);
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(mAuth.getCurrentUser()==null)
        {
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }
//        if(!SharedPref_Util.getBoolean(this, "USER_PRESENT"))
//        {
//            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
//            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//            startActivity(intent);
//        }
    }
}
