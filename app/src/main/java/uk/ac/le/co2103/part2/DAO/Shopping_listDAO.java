package uk.ac.le.co2103.part2.DAO;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import uk.ac.le.co2103.part2.Models.Shopping_list;

@Dao
public interface Shopping_listDAO {
    @Insert
    void insert(Shopping_list shopping_list);

    @Query("SELECT * FROM shopping_list ORDER BY name ASC")
    LiveData<List<Shopping_list>> getShoppingList();

    @Query("DELETE FROM shopping_list")
    void deleteAll();

    @Query("DELETE FROM shopping_list WHERE ListId= :listId")
    void deletebyID(int listId);

    @Query("SELECT * FROM shopping_list WHERE name= :name")
    Shopping_list getShoppingListbyname(String name);
}
