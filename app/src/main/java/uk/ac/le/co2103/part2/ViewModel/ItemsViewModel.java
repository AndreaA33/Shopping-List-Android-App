package uk.ac.le.co2103.part2.ViewModel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import uk.ac.le.co2103.part2.Models.Items;
import uk.ac.le.co2103.part2.Repo.ItemsRepo;

public class ItemsViewModel extends AndroidViewModel {
    private ItemsRepo itemsRepo;
    private final LiveData<List<Items>> allItems;

    public ItemsViewModel(Application application) {
        super(application);
        itemsRepo = new ItemsRepo(application);
        allItems = itemsRepo.getAllItems();
    }

    public LiveData<List<Items>> getItemsForList(int listId) {
        return itemsRepo.getItemsForList(listId);
    }

    public void insert(Items items) {
        itemsRepo.insert(items);
    }

    public void deletebyID(int itemId) {
        itemsRepo.deletebyID(itemId);
    }

    public LiveData<List<Items>> getAllItems() {
        return allItems;
    }

    public Items getItembyId(int itemId){return itemsRepo.getItembyId(itemId);}

    public void update(Items item){itemsRepo.updateitem(item);}

    public Items getItemByNameAndListId(String name,int listId){return itemsRepo.getItemByNameAndListId(name,listId);}
}