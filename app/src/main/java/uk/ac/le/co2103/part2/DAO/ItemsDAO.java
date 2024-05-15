package uk.ac.le.co2103.part2.DAO;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import uk.ac.le.co2103.part2.Models.Items;

@Dao
public interface ItemsDAO {
    @Insert
    void insert(Items items);

    @Query("SELECT * FROM items WHERE listId = :listId ORDER BY name ASC")
    LiveData<List<Items>> getItemsForList(int listId);

    @Query("DELETE FROM items")
    void deleteAll();

    @Query("DELETE FROM items WHERE itemId= :itemId")
    void deletebyID(int itemId);

    @Query("SELECT * FROM items ORDER BY name ASC")
    LiveData<List<Items>> getItems();

    @Query("SELECT * FROM items ORDER BY itemId= :itemId")
    Items getItembyId(int itemId);

    @Update
    void update(Items item);

    @Query("SELECT * FROM items WHERE name = :name AND listId = :listId")
    Items getItemByNameAndListId(String name,int listId);
}
