package ch.walica.sqlite_szablon;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import ch.walica.sqlite_szablon.model.Car;
import ch.walica.sqlite_szablon.sqlite.DatabaseHelper;

public class UpdateActivity extends AppCompatActivity {

    private EditText etUptitle, etUpbody;
    private Button btnUpdate;
    private DatabaseHelper databaseHelper;
    private int id;
    private String title, body;
    private long createDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        if(android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            getWindow().setNavigationBarColor(getResources().getColor(android.R.color.black));
        }

        etUptitle = findViewById(R.id.etUpTitle);
        etUpbody = findViewById(R.id.etUpBody);
        btnUpdate = findViewById(R.id.btnUpdate);

        databaseHelper = new DatabaseHelper(UpdateActivity.this);

        if (getIntent().hasExtra(MainActivity.EXTRA_NOTE)) {
            Car car = getIntent().getParcelableExtra(MainActivity.EXTRA_NOTE);
            id = car.getId();
            title = car.getTitle();
            body = car.getBody();
            createDate = car.getCreatedDate();
            etUptitle.setText(title);
            etUpbody.setText(body);
        }

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                title = etUptitle.getText().toString().trim();
                body = etUpbody.getText().toString().trim();
                databaseHelper.updateCar(new Car(id, title, body, createDate));
                setResult(RESULT_OK);
                finish();
            }
        });
    }
}