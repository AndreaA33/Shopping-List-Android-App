package uk.ac.le.co2103.part2;

import static uk.ac.le.co2103.part2.DB.ShoppingListDB.databaseWriteExecutor;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import uk.ac.le.co2103.part2.Interface.ShoppingListRVInterface;
import uk.ac.le.co2103.part2.Models.Shopping_list;
import uk.ac.le.co2103.part2.ViewModel.ShoppingListViewModel;

public class MainActivity extends AppCompatActivity implements ShoppingListRVInterface {

    private static final String TAG = MainActivity.class.getSimpleName();
    private ShoppingListViewModel shoppingListViewModel;
    private ShoppinglistAdapter adapter;
    private List<Shopping_list> shopping_list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        Log.d(TAG, "onCreate()");
        setContentView(R.layout.activity_main);

        FloatingActionButton fabButton = findViewById(R.id.fab);
        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        View emptyplaceholder = findViewById(R.id.placeholder_layout);

        fabButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CreateListActivity.class);
                startActivity(intent);
            }
        });
        adapter = new ShoppinglistAdapter(new ShoppinglistAdapter.ItemDiff(),this,shopping_list);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        shoppingListViewModel = new ViewModelProvider(this).get(ShoppingListViewModel.class);
        shoppingListViewModel.getAllShoppinglists().observe(this, shoppingLists -> {
            adapter.submitList(shoppingLists);
            if (shoppingListViewModel.getAllShoppinglists().getValue().isEmpty()) {
                recyclerView.setVisibility(View.GONE);
                emptyplaceholder.setVisibility(View.VISIBLE);
            } else {
                recyclerView.setVisibility(View.VISIBLE);
                emptyplaceholder.setVisibility(View.GONE);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        View emptyplaceholder = findViewById(R.id.placeholder_layout);
        if (requestCode == 1002 && resultCode == RESULT_OK) {
            if (data != null && data.getBooleanExtra("shoppingListAdded", false)) {
                adapter = new ShoppinglistAdapter(new ShoppinglistAdapter.ItemDiff(),this,shopping_list);
                shoppingListViewModel = new ViewModelProvider(this).get(ShoppingListViewModel.class);
                shoppingListViewModel.getAllShoppinglists().observe(this, new Observer<List<Shopping_list>>() {
                    @Override
                    public void onChanged(List<Shopping_list> shopping_l) {
                        System.out.println(shopping_l);
                        if (!shopping_l.isEmpty()) {
                            adapter.notifyDataSetChanged();
                            recyclerView.setVisibility(View.VISIBLE);
                            emptyplaceholder.setVisibility(View.GONE);
                        }
                    }
                });
            }
        }
    }

    @Override
    public void OnClickRV(int position) {
        Intent intent = new Intent(this, ShoppingListActivity.class);
        startActivity(intent);
    }

    @Override
    public void OnLongClickRV(int position) {
        databaseWriteExecutor.execute(() -> {
            shopping_list = shoppingListViewModel.getAllShoppinglists().getValue();
            int listId = shopping_list.get(position).getListId();
            if (position >= 0 && position < shopping_list.size()) {
                shopping_list.remove(position);
                shoppingListViewModel.deletebyID(listId);
            }
        });
        adapter.notifyItemRemoved(position);
    }
}