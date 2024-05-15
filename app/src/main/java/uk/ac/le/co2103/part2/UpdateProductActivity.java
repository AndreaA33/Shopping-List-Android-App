package uk.ac.le.co2103.part2;

import static uk.ac.le.co2103.part2.DB.ShoppingListDB.databaseWriteExecutor;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import uk.ac.le.co2103.part2.DB.ShoppingListDB;
import uk.ac.le.co2103.part2.Models.Items;

public class UpdateProductActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_product);
        EditText name = findViewById(R.id.editTextName);
        TextView quantity = findViewById(R.id.editTextQuantity);
        Spinner spinner = findViewById(R.id.spinner);
        Button save = findViewById(R.id.savebutton);
        Button backbutton = findViewById(R.id.backButton);
        Button plusimage = findViewById(R.id.plusbutton);
        Button minusimage = findViewById(R.id.minusbutton);

        int listId = getIntent().getIntExtra("listId", -1);
        String LSTname = getIntent().getStringExtra("LSTname");
        int itemId = getIntent().getIntExtra("itemId",-1);
        String setname = getIntent().getStringExtra("name");
        int setquantity = getIntent().getIntExtra("quantity",-1);
        String setunits = getIntent().getStringExtra("units");
        name.setText(setname);
        quantity.setText(Integer.toString(setquantity));

        String[] unitsArray = getResources().getStringArray(R.array.labels_unit);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, unitsArray);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        int unitIndex = adapter.getPosition(setunits);
        spinner.setSelection(unitIndex);

        backbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        plusimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int q = Integer.parseInt(quantity.getText().toString()) + 1;
                quantity.setText(String.valueOf(q));
            }
        });

        minusimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int q = Integer.parseInt(quantity.getText().toString()) - 1;
                quantity.setText(String.valueOf(q));
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UpdateProductActivity.this,ShoppingListActivity.class);
                String unitsTextView =  spinner.getSelectedItem().toString();
                databaseWriteExecutor.execute(() -> {
                    ShoppingListDB db = Room.databaseBuilder(getApplicationContext(),
                                    ShoppingListDB.class, "shoppinglist_db").allowMainThreadQueries()
                            .build();
                    db.itemsDao().deletebyID(itemId);
                    int newQuantity = Integer.parseInt(quantity.getText().toString());
                    Items items = new Items(name.getText().toString(),listId,newQuantity,unitsTextView);
                    db.itemsDao().insert(items);
                });
                intent.putExtra("itemAdded", true);
                setResult(ShoppingListActivity.RESULT_OK, intent);
                intent.putExtra("listId",listId);
                intent.putExtra("name",LSTname);
                startActivity(intent);
            }
        });

    }

}