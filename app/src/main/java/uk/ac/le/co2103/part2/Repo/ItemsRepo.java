package uk.ac.le.co2103.part2.Repo;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

import uk.ac.le.co2103.part2.DAO.ItemsDAO;
import uk.ac.le.co2103.part2.DB.ShoppingListDB;
import uk.ac.le.co2103.part2.Models.Items;

public class ItemsRepo {
    private ItemsDAO itemDao;

    public ItemsRepo(Application application) {
        ShoppingListDB db = ShoppingListDB.getDatabase(application);
        this.itemDao = db.itemsDao();
    }

    public LiveData<List<Items>> getItemsForList(int listId) {
        return itemDao.getItemsForList(listId);
    }

    public void insert(Items item) {
        ShoppingListDB.databaseWriteExecutor.execute(() -> {
            itemDao.insert(item);
        });
    }

    public void deletebyID(int itemId) {
        itemDao.deletebyID(itemId);
    }

    public LiveData<List<Items>> getAllItems(){
            return itemDao.getItems();
    }

    public Items getItembyId(int itemId){return itemDao.getItembyId(itemId);}

    public void updateitem(Items item){itemDao.update(item);}

    public Items getItemByNameAndListId(String name,int listId){return itemDao.getItemByNameAndListId(name,listId);}
}