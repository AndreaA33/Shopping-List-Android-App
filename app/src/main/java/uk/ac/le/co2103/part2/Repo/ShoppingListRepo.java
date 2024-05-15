package uk.ac.le.co2103.part2.Repo;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

import uk.ac.le.co2103.part2.DAO.Shopping_listDAO;
import uk.ac.le.co2103.part2.DB.ShoppingListDB;
import uk.ac.le.co2103.part2.Models.Shopping_list;

public class ShoppingListRepo {
    private Shopping_listDAO shopping_listDao;

    public ShoppingListRepo(Application application) {
        ShoppingListDB db = ShoppingListDB.getDatabase(application);
        shopping_listDao = db.shopping_listDao();
    }

    public LiveData<List<Shopping_list>> getAllShoppinglists() {
        return shopping_listDao.getShoppingList();
    }

    public void insert(Shopping_list item) {
        ShoppingListDB.databaseWriteExecutor.execute(() -> {
            shopping_listDao.insert(item);
        });
    }
    public void deletebyID(int listId){
        shopping_listDao.deletebyID(listId);
    }

    public void getShoppingListbyname(String name) {shopping_listDao.getShoppingListbyname(name);}
}
