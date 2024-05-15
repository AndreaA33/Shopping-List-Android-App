package uk.ac.le.co2103.part2;

import static uk.ac.le.co2103.part2.DB.ShoppingListDB.databaseWriteExecutor;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import uk.ac.le.co2103.part2.DB.ShoppingListDB;
import uk.ac.le.co2103.part2.Models.Items;


public class AddProductActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        int listId = getIntent().getIntExtra("listId", -1);
        String LSTname = getIntent().getStringExtra("name");
        setContentView(R.layout.activity_add_product);
        TextView name = findViewById(R.id.editTextName);
        TextView quantity = findViewById(R.id.editTextQuantity);
        Button add = findViewById(R.id.addbutton);
        Button backbutton = findViewById(R.id.backButton);
        Spinner spinner = findViewById(R.id.spinner);

        String[] unitsArray = getResources().getStringArray(R.array.labels_unit);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                unitsArray
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);


        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nameTextView = name.getText().toString();
                String quantityTextView = quantity.getText().toString();
                String unitsTextView =  spinner.getSelectedItem().toString();
                if (nameTextView.isEmpty() || quantityTextView.isEmpty()) {
                    if (nameTextView.isEmpty()) {
                        name.setError("Name is required");
                        name.requestFocus();
                    }
                    if (quantityTextView.isEmpty()) {
                        quantity.setError("Quantity is required");
                        quantity.requestFocus();
                    }
                } else{
                    Intent intent = new Intent(AddProductActivity.this,ShoppingListActivity.class);
                    databaseWriteExecutor.execute(() -> {
                        ShoppingListDB db = Room.databaseBuilder(getApplicationContext(),
                                        ShoppingListDB.class, "shoppinglist_db").allowMainThreadQueries()
                                .build();
                        if (IfProductExist(db, nameTextView,listId)){
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(AddProductActivity.this, "Product already exists", Toast.LENGTH_SHORT).show();
                                }
                            });
                        } else {
                            Items items = new Items(nameTextView, listId, Integer.parseInt(quantityTextView), unitsTextView);
                            db.itemsDao().insert(items);
                            intent.putExtra("itemAdded", true);
                            setResult(ShoppingListActivity.RESULT_OK, intent);
                            intent.putExtra("listId",listId);
                            intent.putExtra("name",LSTname);
                            startActivity(intent);
                        }
                    });
                }
            }
        });

        backbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
    private boolean IfProductExist(ShoppingListDB db, String productName,int listId) {
        return db.itemsDao().getItemByNameAndListId(productName,listId) != null;
    }
}