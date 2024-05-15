package uk.ac.le.co2103.part2;

import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;

import java.util.List;

import uk.ac.le.co2103.part2.Interface.ShoppingListRVInterface;
import uk.ac.le.co2103.part2.Models.Shopping_list;

public class ShoppinglistAdapter extends ListAdapter<Shopping_list, ShoppinglistViewHolder> {
    private final ShoppingListRVInterface shoppingListRVInterface;

    private List<Shopping_list> shopping_list;
    private OnItemLongClickListener onItemLongClickListener;

    public ShoppinglistAdapter(@NonNull DiffUtil.ItemCallback<Shopping_list> diffCallback, ShoppingListRVInterface shoppingListRVInterface, List<Shopping_list> shopping_list ) {
        super(diffCallback);
        this.shoppingListRVInterface = shoppingListRVInterface;
        this.shopping_list = shopping_list;
    }

    @NonNull
    @Override
    public ShoppinglistViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return ShoppinglistViewHolder.create(parent, shoppingListRVInterface);
    }


    @Override
    public void onBindViewHolder(@NonNull ShoppinglistViewHolder holder, int position) {
        Shopping_list current = getItem(position);
        holder.bind(current.getShoppingList());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int listId = current.getListId();
                String name = current.getName();
                Intent intent = new Intent(view.getContext(), ShoppingListActivity.class);
                intent.putExtra("listId", listId);
                intent.putExtra("name", name);
                view.getContext().startActivity(intent);
            }
        });
    }

    public interface OnItemLongClickListener {
        void onItemClick(int position);
    }

    public void setOnItemLongClickListener(OnItemLongClickListener listener) {
        this.onItemLongClickListener = listener;
    }


    static class ItemDiff extends DiffUtil.ItemCallback<Shopping_list> {

        @Override
        public boolean areItemsTheSame(@NonNull Shopping_list oldItem, @NonNull Shopping_list newItem) {
            return oldItem.getListId() == newItem.getListId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull Shopping_list oldItem, @NonNull Shopping_list newItem) {
            return oldItem.getShoppingList().equals(newItem.getShoppingList());
        }
    }
}