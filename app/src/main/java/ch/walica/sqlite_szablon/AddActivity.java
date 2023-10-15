package ch.walica.sqlite_szablon;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import ch.walica.sqlite_szablon.model.Car;
import ch.walica.sqlite_szablon.sqlite.DatabaseHelper;

public class AddActivity extends AppCompatActivity {

    private EditText etTitle, etBody;
    private Button btnAdd;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        if(android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            getWindow().setNavigationBarColor(getResources().getColor(android.R.color.black));
        }

        etTitle = findViewById(R.id.etTitle);
        etBody = findViewById(R.id.etBody);
        btnAdd = findViewById(R.id.btnAdd);

        databaseHelper = new DatabaseHelper(AddActivity.this);


        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String title = etTitle.getText().toString().trim();
                String body = etBody.getText().toString().trim();

                if(!title.isEmpty() && !body.isEmpty()) {
                    Car car = new Car(title, body);
                    databaseHelper.addCar(car);
                    Intent intent = new Intent(AddActivity.this, MainActivity.class);
                    startActivity(intent);
                } else {

                    Toast.makeText(AddActivity.this, R.string.emptyToast, Toast.LENGTH_SHORT).show();
                }

            }
        });


    }
}