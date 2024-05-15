package uk.ac.le.co2103.part2.Models;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "shopping_list")
public class Shopping_list {

    @PrimaryKey(autoGenerate = true)
    Integer listId;

    String name;

    byte[] image;

    public Shopping_list(String name, byte[] image) {
        this.name = name;
        this.image = image;
    }

    public Shopping_list getShoppingList() {
        return new Shopping_list(name, image);
    }

    public Integer getListId() {
        return listId;
    }

    public void setListId(Integer listId) {
        this.listId = listId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }
}
