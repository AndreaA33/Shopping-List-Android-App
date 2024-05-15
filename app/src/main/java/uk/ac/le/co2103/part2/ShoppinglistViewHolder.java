package uk.ac.le.co2103.part2;

import android.graphics.Bitmap;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import uk.ac.le.co2103.part2.Interface.ShoppingListRVInterface;
import uk.ac.le.co2103.part2.Models.Shopping_list;

public class ShoppinglistViewHolder extends RecyclerView.ViewHolder{

    private ImageView imageView;
    private TextView textView;

    public ShoppinglistViewHolder(View shoppingListView, ShoppingListRVInterface shoppingListRVInterface) {
        super(shoppingListView);
        imageView = shoppingListView.findViewById(R.id.cartimage);
        textView = shoppingListView.findViewById(R.id.cartname);


        shoppingListView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (shoppingListRVInterface != null) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        shoppingListRVInterface.OnClickRV(position);
                    }
                }
            }
        });

        shoppingListView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                View popupView = LayoutInflater.from(shoppingListView.getContext()).inflate(R.layout.delete_popup, null);

                PopupWindow popupWindow = new PopupWindow(
                        popupView,
                        ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT
                );
                Button deleteButton = popupView.findViewById(R.id.btn_delete);
                deleteButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (shoppingListRVInterface != null) {
                            int position = getAdapterPosition();
                            RecyclerView recyclerView = (RecyclerView) shoppingListView.getParent();
                            ShoppinglistAdapter adapter = (ShoppinglistAdapter) recyclerView.getAdapter() ;
                            popupWindow.dismiss();
                            if (position != RecyclerView.NO_POSITION) {
                                shoppingListRVInterface.OnLongClickRV(position);
                            }
                        }
                    }});
                popupWindow.setOutsideTouchable(true);

                int[] location = new int[2];
                shoppingListView.getLocationInWindow(location);
                int x = location[0] + shoppingListView.getWidth() / 2 + 150;
                int y = location[1] - popupView.getHeight();
                popupWindow.showAtLocation(shoppingListView, Gravity.NO_GRAVITY, x, y);
                return true;
            }
        });

    }

    public void bind(Shopping_list item) {
        Bitmap conv = Convert.ToBitmap(item.getImage());
        imageView.setImageBitmap(conv);
        textView.setText(item.getName());
    }

    static ShoppinglistViewHolder create(ViewGroup parent, ShoppingListRVInterface shoppingListRVInterface) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recyclerview_item, parent, false);
        return new ShoppinglistViewHolder(view,shoppingListRVInterface);
    }
}