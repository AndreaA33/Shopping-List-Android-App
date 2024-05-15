package uk.ac.le.co2103.part2.DB;


import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import uk.ac.le.co2103.part2.DAO.ItemsDAO;
import uk.ac.le.co2103.part2.DAO.Shopping_listDAO;
import uk.ac.le.co2103.part2.Models.Items;
import uk.ac.le.co2103.part2.Models.Shopping_list;

@Database(entities = {Shopping_list.class, Items.class}, version = 1, exportSchema = false)
public abstract class ShoppingListDB extends RoomDatabase {
    public abstract Shopping_listDAO shopping_listDao();
    public abstract ItemsDAO itemsDao();


    private static volatile ShoppingListDB INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;
    public static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public static ShoppingListDB getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (ShoppingListDB.class) {
                if (INSTANCE == null) {
                    if (context == null) {
                        throw new IllegalArgumentException("Cannot provide null context for the database.");
                    }
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    ShoppingListDB.class, "shoppinglist_db")
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
