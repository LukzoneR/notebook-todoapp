package ch.walica.sqlite_szablon.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import androidx.activity.result.contract.ActivityResultContract;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import ch.walica.sqlite_szablon.MainActivity;
import ch.walica.sqlite_szablon.UpdateActivity;
import ch.walica.sqlite_szablon.model.Car;

public class UpdateActivityResult extends ActivityResultContract<Car, Boolean> {
    @NonNull
    @Override
    public Intent createIntent(@NonNull Context context, Car car) {
        return new Intent(context, UpdateActivity.class).putExtra(MainActivity.EXTRA_NOTE, car);
    }

    @Override
    public Boolean parseResult(int resultCode, @Nullable Intent intent) {
        if(resultCode == Activity.RESULT_OK) {
            return true;
        }
        return false;
    }
}
