package android.example.furniture_magik_demo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

public class AddItem extends AppCompatActivity {

    Toolbar toolbar;
    EditText name,price,discountprice;
    Spinner type;
    Button back,submit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);
        toolbar = findViewById(R.id.add_item_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Add Items ");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setViews();
        setUpSPinner();

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    void setViews(){
        name = findViewById(R.id.add_item_name);
        price = findViewById(R.id.add_item_price);
        discountprice = findViewById(R.id.add_item_discount);
        type = findViewById(R.id.add_item_spinner3);
        back = findViewById(R.id.add_item_cancel);
        submit = findViewById(R.id.add_item_button);
    }

    void setUpSPinner()
    {
        List<String> categories = new ArrayList<String>();
        categories.add("Chairs");
        categories.add("Desks");
        categories.add("Almirah");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        type.setAdapter(dataAdapter);
        type.setSelection(0);
    }

    void saveProduct()
    {

    }
}
