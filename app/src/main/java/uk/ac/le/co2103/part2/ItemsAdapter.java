package uk.ac.le.co2103.part2;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;

import java.util.List;

import uk.ac.le.co2103.part2.Interface.ItemsRVInterface;
import uk.ac.le.co2103.part2.Models.Items;

public class ItemsAdapter extends ListAdapter<Items, ItemsViewHolder> {

    private ItemsRVInterface itemsRVInterface;
    private List<Items> itemsList;
    private OnItemClickListener onItemClickListener;

    public ItemsAdapter(@NonNull DiffUtil.ItemCallback<Items> diffCallback, ItemsRVInterface itemsRVInterface, List<Items> itemsList) {
        super(diffCallback);
        this.itemsRVInterface = itemsRVInterface;
        this.itemsList = itemsList;
    }

    @NonNull
    @Override
    public ItemsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return ItemsViewHolder.create(parent,itemsRVInterface);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemsViewHolder holder, int position) {
        Items current = getItem(position);
        holder.bind(current.getItems());
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }

    static class ItemDiff extends DiffUtil.ItemCallback<Items> {

        @Override
        public boolean areItemsTheSame(@NonNull Items oldItem, @NonNull Items newItem) {
            return oldItem.getListId() == newItem.getListId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull Items oldItem, @NonNull Items newItem) {
            return oldItem.getItems().equals(newItem.getItems());
        }
    }
}
