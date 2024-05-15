package uk.ac.le.co2103.part2.Models;

import static androidx.room.ForeignKey.CASCADE;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(tableName = "items",
        foreignKeys = @ForeignKey(entity = Shopping_list.class,
                parentColumns = "listId",
                childColumns = "listId",
                onUpdate = CASCADE,
                onDelete = CASCADE))
public class Items {
    @PrimaryKey(autoGenerate = true)
    private int itemId;

    private int listId;

    private String name;
    private Integer quantity;
    private String unit;

    public Items(String name, int listId, int quantity, String unit) {
        this.name = name;
        this.listId = listId;
        this.quantity = quantity;
        this.unit = unit;
    }

    public Items getItems() {
        return new Items(name, listId,quantity,unit);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public int getListId() {
        return listId;
    }

    public void setListId(int listId) {
        this.listId = listId;
    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }
}
