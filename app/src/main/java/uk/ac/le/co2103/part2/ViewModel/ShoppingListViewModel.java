package uk.ac.le.co2103.part2.ViewModel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import uk.ac.le.co2103.part2.Models.Shopping_list;
import uk.ac.le.co2103.part2.Repo.ShoppingListRepo;

public class ShoppingListViewModel extends AndroidViewModel {
    private ShoppingListRepo repo;
    private final LiveData<List<Shopping_list>> allshoppinglists;

    public ShoppingListViewModel(Application application) {
        super(application);
        repo = new ShoppingListRepo(application);
        allshoppinglists = repo.getAllShoppinglists();
    }

    public LiveData<List<Shopping_list>> getAllShoppinglists() {
        return allshoppinglists;
    }

    public void insert(Shopping_list shopping_list) {
        repo.insert(shopping_list);
    }

    public void deletebyID(int listId) {
        repo.deletebyID(listId);
    }

    public void getShoppingListbyname(String name){repo.getShoppingListbyname(name);}
}
