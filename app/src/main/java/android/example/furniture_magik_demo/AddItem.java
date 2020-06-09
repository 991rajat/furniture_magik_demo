package android.example.furniture_magik_demo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.example.furniture_magik_demo.Model.Product;
import android.example.furniture_magik_demo.utils.ProductHelper;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.theartofdev.edmodo.cropper.CropImage;

import java.util.ArrayList;
import java.util.List;

public class AddItem extends AppCompatActivity {

    private static final String TAG = ">>>>>>>>>>";
    Toolbar toolbar;
    EditText name,price,discountprice;
    Spinner type;
    Button back,submit;
    ImageView imageView;
    boolean image_selected = false;
    private static final int PERMISSION_CODE = 101;
    private String image_path;
    ProductHelper dbHelper;

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

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
                        //permission not granted, requesting permission
                        String[] permissions = {Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};
                        //show popup for runtime permission
                        requestPermissions(permissions, PERMISSION_CODE);
                    }else {
                        startCropImageActivity();
                    }
                } else {
                    startCropImageActivity();
                }

            }
        });
    }

    void setViews(){
        name = findViewById(R.id.add_item_name);
        price = findViewById(R.id.add_item_price);
        discountprice = findViewById(R.id.add_item_discount);
        type = findViewById(R.id.add_item_spinner3);
        back = findViewById(R.id.add_item_cancel);
        imageView = findViewById(R.id.add_item_image);
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
        String p_name = name.getText().toString().trim();
        String p_type = type.getSelectedItem().toString();
        Float p_price = Float.parseFloat(price.getText().toString().trim());
        Float p_discount = Float.parseFloat(discountprice.getText().toString().trim());
        dbHelper = new ProductHelper(this);

        if(p_name.isEmpty()){
            //error name is empty
            Toast.makeText(this, "Enter product name", Toast.LENGTH_SHORT).show();
        }

        if(p_type.isEmpty()){
            //error name is empty
            Toast.makeText(this, "Select type", Toast.LENGTH_SHORT).show();
        }

        if(price.getText().toString().trim().isEmpty()){
            //error name is empty
            Toast.makeText(this, "Please product price", Toast.LENGTH_SHORT).show();
        }

        if(discountprice.getText().toString().trim().isEmpty()){
            //error name is empty
            Toast.makeText(this, "Enter product discount price", Toast.LENGTH_SHORT).show();
        }

        if(!image_selected)
            image_path="";
        //create new person
        Product product = new Product(p_name, p_type, p_price, p_discount,image_path);
        boolean success = dbHelper.saveNewProduct(product,AddItem.this);
        if(success) {
            image_selected = false;
            name.setText("");
            price.setText("");
            discountprice.setText("");
        }
    }

    private void startCropImageActivity() {
        CropImage.activity().start(this);
    }



    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                Uri resultUri = result.getUri();
                Log.d(TAG, "onActivityResult: "+resultUri.getPath());
                image_path = resultUri.getPath();
                image_selected = true;
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
                Toast.makeText(this, "Error Try Again!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == PERMISSION_CODE)  {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Permission GRANTED", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Permission DENIED", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
