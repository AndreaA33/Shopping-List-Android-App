package uk.ac.le.co2103.part2;

import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import uk.ac.le.co2103.part2.Interface.ItemsRVInterface;
import uk.ac.le.co2103.part2.Models.Items;


public class ItemsViewHolder extends RecyclerView.ViewHolder {

    private TextView nameTextView;
    private TextView quantityTextView;
    private TextView unitsTextView;

    public ItemsViewHolder(View itemView, ItemsRVInterface itemsRVInterface) {
        super(itemView);
        nameTextView = itemView.findViewById(R.id.itemname);
        quantityTextView = itemView.findViewById(R.id.itemquantity);
        unitsTextView = itemView.findViewById(R.id.itemunits);

       itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String description = "Name: " + nameTextView.getText().toString() +
                        ", Quantity: " + quantityTextView.getText().toString() +
                        ", Unit: " + unitsTextView.getText().toString();
                Toast.makeText(itemView.getContext(), description, Toast.LENGTH_LONG).show();
                AlertDialog.Builder builder = new AlertDialog.Builder(itemView.getContext());
                int position = getAdapterPosition();
                builder.setTitle("Options")
                        .setItems(new CharSequence[]{"Edit", "Delete"}, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                switch (which) {
                                    case 0: // Edit
                                        itemsRVInterface.OnClickeditRV(position);
                                        break;
                                    case 1: // Delete
                                        itemsRVInterface.OnClickdeleteRV(position);
                                        break;
                                }
                            }
                        })
                        .show();
            }
    });
    }

    public void bind(Items item) {
        nameTextView.setText(item.getName());
        quantityTextView.setText(String.valueOf(item.getQuantity()));
        unitsTextView.setText(item.getUnit());
    }

    static ItemsViewHolder create(ViewGroup parent,ItemsRVInterface itemsRVInterface) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recyclerview_shoppinglist, parent, false);
        return new ItemsViewHolder(view,itemsRVInterface);
    }
}
