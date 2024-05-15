package uk.ac.le.co2103.part2;

import static uk.ac.le.co2103.part2.DB.ShoppingListDB.databaseWriteExecutor;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import java.util.List;

import uk.ac.le.co2103.part2.DB.ShoppingListDB;
import uk.ac.le.co2103.part2.Models.Shopping_list;
import uk.ac.le.co2103.part2.ViewModel.ShoppingListViewModel;


public class CreateListActivity extends AppCompatActivity {

    private List<Shopping_list> shopping_list;
    private ShoppingListViewModel shoppingListViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_create_list);
        TextView name = findViewById(R.id.name);
        Button upload = findViewById(R.id.uploadbutton);
        ImageView imageview = findViewById(R.id.imagedisplay);
        Button create = findViewById(R.id.createbutton);
        Button backbutton = findViewById(R.id.backButton);

        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nameTextView = name.getText().toString();
                if (nameTextView.isEmpty()) {
                    name.setError("Name is required");
                    name.requestFocus();
                }else{
                    Intent intent = new Intent(CreateListActivity.this, MainActivity.class);
                    databaseWriteExecutor.execute(() -> {
                        ShoppingListDB db = Room.databaseBuilder(getApplicationContext(),
                                        ShoppingListDB.class, "shoppinglist_db").allowMainThreadQueries()
                                .build();
                        if (IfShoppingListExist(db, nameTextView)){
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(CreateListActivity.this, "Shopping List already exists", Toast.LENGTH_SHORT).show();
                                }
                            });
                        } else {
                            BitmapDrawable bitmapDrawable = (BitmapDrawable) imageview.getDrawable();
                            Bitmap imgbitmap = bitmapDrawable.getBitmap();
                            byte[] conv = Convert.ToByteArray(imgbitmap);
                            Shopping_list shoppingList = new Shopping_list(name.getText().toString(), conv);
                            db.shopping_listDao().insert(shoppingList);
                            intent.putExtra("shoppingListAdded", true);
                            setResult(MainActivity.RESULT_OK, intent);
                            startActivity(intent);
                        }
                    });
                }
            }
        });

        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK,MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent,3);
            }
        });

        backbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode,Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && data != null){
            Uri Image = data.getData();
            ImageView imageview = findViewById(R.id.imagedisplay);
            imageview.setImageURI(Image);
        }
    }

    private boolean IfShoppingListExist(ShoppingListDB db, String productName) {
        return db.shopping_listDao().getShoppingListbyname(productName) != null;
    }
}