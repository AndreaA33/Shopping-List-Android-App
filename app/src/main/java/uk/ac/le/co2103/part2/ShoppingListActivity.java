package uk.ac.le.co2103.part2;

import static uk.ac.le.co2103.part2.DB.ShoppingListDB.databaseWriteExecutor;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import uk.ac.le.co2103.part2.Interface.ItemsRVInterface;
import uk.ac.le.co2103.part2.Models.Items;
import uk.ac.le.co2103.part2.ViewModel.ItemsViewModel;

public class ShoppingListActivity extends AppCompatActivity implements ItemsRVInterface {

    private ItemsViewModel itemsViewModel;
    private ItemsAdapter adapter;
    private List<Items> items;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_shopping_list);
        int listId = getIntent().getIntExtra("listId", -1);
        String name = getIntent().getStringExtra("name");
        TextView LSTname = findViewById(R.id.SLname);
        LSTname.setText(name);
        Button homebutton = findViewById(R.id.homeButton);

        FloatingActionButton fabButton = findViewById(R.id.fabAddProduct);
        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        View emptyplaceholder = findViewById(R.id.placeholder_layout);

        fabButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ShoppingListActivity.this, AddProductActivity.class);
                intent.putExtra("listId",listId);
                intent.putExtra("name",name);
                startActivity(intent);
            }
        });

        homebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ShoppingListActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        adapter = new ItemsAdapter(new ItemsAdapter.ItemDiff(),this,items);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        itemsViewModel = new ViewModelProvider(this).get(ItemsViewModel.class);
        itemsViewModel.getItemsForList(listId).observe(this, new Observer<List<Items>>() {
            @Override
            public void onChanged(List<Items> itemsList) {
                adapter.submitList(itemsList);
                items = itemsList;
                if (!itemsList.isEmpty()) {
                    recyclerView.setVisibility(View.VISIBLE);
                    emptyplaceholder.setVisibility(View.GONE);
                } else {
                    recyclerView.setVisibility(View.GONE);
                    emptyplaceholder.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        int listId = getIntent().getIntExtra("listId", -1);
        String name = getIntent().getStringExtra("name");
        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        View emptyplaceholder = findViewById(R.id.placeholder_layout);
        if (requestCode == 1001 && resultCode == RESULT_OK) {
            if (data != null && data.getBooleanExtra("itemAdded", false)) {
                TextView SLname = findViewById(R.id.SLname);
                SLname.setText(name);
                adapter = new ItemsAdapter(new ItemsAdapter.ItemDiff(),this,items);
                itemsViewModel = new ViewModelProvider(this).get(ItemsViewModel.class);
                itemsViewModel.getItemsForList(listId).observe(this, new Observer<List<Items>>() {
                    @Override
                    public void onChanged(List<Items> itemsList) {
                        adapter.submitList(itemsList);
                        if (!itemsList.isEmpty()) {
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
    public void OnClickdeleteRV(int position) {
        databaseWriteExecutor.execute(() -> {
            if (position >= 0 && position < items.size()) {
                int itemId = items.get(position).getItemId();
                items.remove(position);
                itemsViewModel.deletebyID(itemId);
            }
        });
        adapter.notifyItemRemoved(position);
    }

    @Override
    public void OnClickeditRV(int position) {
        Intent intent = new Intent(ShoppingListActivity.this, UpdateProductActivity.class);
        int listId = getIntent().getIntExtra("listId", -1);
        String name = items.get(position).getName();
        int quantity = items.get(position).getQuantity();
        String units = items.get(position).getUnit();
        int itemId = items.get(position).getItemId();
        String LSTname = getIntent().getStringExtra("name");

        intent.putExtra("itemId", itemId);
        intent.putExtra("name", name);
        intent.putExtra("quantity", quantity);
        intent.putExtra("units", units);
        intent.putExtra("listId",listId);
        intent.putExtra("LSTname",LSTname);

        startActivity(intent);
    }
}