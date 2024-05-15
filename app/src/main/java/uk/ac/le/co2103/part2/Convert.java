package uk.ac.le.co2103.part2;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import androidx.room.TypeConverter;

import java.io.ByteArrayOutputStream;

public class Convert {
    @TypeConverter
    public static byte[] ToByteArray(Bitmap bitmap){
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 50, output);
        byte[] byteArray = output.toByteArray();
        return byteArray;
    }

    @TypeConverter
    public static Bitmap ToBitmap(byte[] byteArray){
        return BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
    }
}
