package ch.walica.sqlite_szablon;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import ch.walica.sqlite_szablon.adapter.CarAdapter;
import ch.walica.sqlite_szablon.adapter.OnCarClickListener;
import ch.walica.sqlite_szablon.model.Car;
import ch.walica.sqlite_szablon.sqlite.DatabaseHelper;
import ch.walica.sqlite_szablon.util.UpdateActivityResult;

public class MainActivity extends AppCompatActivity implements OnCarClickListener {

    public static final String EXTRA_NOTE = "note_obj";
    public static final String EXTRA_KEY = "key";

    public static final String SHARED = "shared";
    public static final String SHARED_KEY = "shared_key";

    private EditText etPass;
    private RecyclerView recyclerView;
    private FloatingActionButton floatingActionButton;
    private TextView tvInfo;
    private List<Car> cars = new ArrayList<>();
    private DatabaseHelper databaseHelper;
    private CarAdapter carAdapter;
    private ActivityResultLauncher<Car> launcher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);
        floatingActionButton = findViewById(R.id.floatingActionButton);
        tvInfo = findViewById(R.id.tvInfo);



        if(android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            getWindow().setNavigationBarColor(getResources().getColor(android.R.color.black));
        }


        launcher = registerForActivityResult(new UpdateActivityResult(), new ActivityResultCallback<Boolean>() {
            @Override
            public void onActivityResult(Boolean result) {
                if(result) {
                    recreate();
                }
            }
        });

        databaseHelper = new DatabaseHelper(MainActivity.this);
        storeNotesInList();

        carAdapter = new CarAdapter(cars, this);
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        recyclerView.setAdapter(carAdapter);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddActivity.class);
                startActivity(intent);
            }
        });
    }

    private void storeNotesInList() {
        Cursor cursor = databaseHelper.getAllCars();
        if(cursor.getCount() == 0) {
            tvInfo.setVisibility(View.VISIBLE);
        } else {
            tvInfo.setVisibility(View.GONE);
            while (cursor.moveToNext()) {
                int id = cursor.getInt(0);
                String title = cursor.getString(1);
                String body = cursor.getString(2);
                Long createDate = cursor.getLong(3);
                Car car = new Car(id, title, body, createDate);
                cars.add(car);
            }
        }
    }

    @Override
    public void onCarClick(int position, View view) {
        switch(view.getId()) {
            case R.id.ivDelete:
                removeCar(position);
                break;
            case R.id.ivEdit:
                editNote(cars.get(position));
                break;
        }
    }

    private void removeCar(int position) {
        Car car = cars.get(position);
        new MaterialAlertDialogBuilder(this)
                .setTitle(R.string.deleteCar)
                .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        databaseHelper.deleteCar(car.getId());
                        cars.remove(car);

                        Cursor cursor = databaseHelper.getAllCars();
                        if(cursor.getCount()==0){
                            tvInfo.setVisibility(View.VISIBLE);
                        }
                        carAdapter.notifyDataSetChanged();
                    }
                })
                .setNegativeButton(R.string.no, null)
                .show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()) {
            case R.id.item1:
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                final View customLayout = getLayoutInflater().inflate(R.layout.password_alert, null);
                builder.setView(customLayout);

                builder.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        EditText etPass = customLayout.findViewById(R.id.etPass);
                        String pass = etPass.getText().toString();
                        if (pass.length() == 3) {

                            SharedPreferences sharedPreferences = getSharedPreferences(SHARED, MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString(SHARED_KEY, pass);
                            editor.apply();
                            Toast.makeText(MainActivity.this, R.string.save, Toast.LENGTH_SHORT).show();

                        } else {
                            Toast.makeText(MainActivity.this, R.string.saveNo, Toast.LENGTH_SHORT).show();
                        }
                    }
                });

                builder.setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Handle the "No" button click.
                    }
                });

                builder.show(); // Display the AlertDialog
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void editNote(Car car) {
        launcher.launch(car);
    }
}