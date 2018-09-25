package dev.milos.databaseexample;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class UpdateActivity extends AppCompatActivity {

    DatabaseHelper db;
    TextView tvIdStudent;
    EditText etIndeks, etImeStudenta,etPrezimeStudenta, etBrojBodova;
    Button btAzuriraj;




    public String passedVar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        db = new DatabaseHelper(this);
        tvIdStudent = (TextView) findViewById(R.id.tvIdStudent);
        etIndeks = (EditText) findViewById(R.id.etIndeks);
        etImeStudenta = (EditText) findViewById(R.id.etImeStudenta);
        etPrezimeStudenta = (EditText) findViewById(R.id.etPrezimeStudenta);
        etBrojBodova = (EditText) findViewById(R.id.etBrojBodova);
        btAzuriraj = (Button) findViewById(R.id.btAzuriraj);


        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        final String id = extras.getString("_id");
        String indeks = extras.getString("EXTRA_INDEKS");
        String ime = extras.getString("EXTRA_IME");
        String prezime = extras.getString("EXTRA_PREZIME");
        String bodovi = extras.getString("EXTRA_BROJBODOVA");


        tvIdStudent.setText("ID STUDENTA: " + id);
        etIndeks.setText(indeks);
        etImeStudenta.setText(ime);
        etPrezimeStudenta.setText(prezime);
        etBrojBodova.setText(bodovi);

        //passedVar = getIntent().getStringExtra("_id");
        //tvIdStudent.setText("ID STUDENTA: " + passedVar);

        btAzuriraj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                boolean isUpdated = db.updateStudent(id, etIndeks.getText().toString(),
                        etImeStudenta.getText().toString(), etPrezimeStudenta.getText().toString(),
                        etBrojBodova.getText().toString());

                if(isUpdated){
                    Toast.makeText(UpdateActivity.this, "Student je uspesno azuriran!", Toast.LENGTH_SHORT).show();

                }else{
                    Toast.makeText(UpdateActivity.this, "Student nije azuriran!", Toast.LENGTH_SHORT).show();
                }

                Intent intent = new Intent(UpdateActivity.this, ListActivity.class);
                startActivity(intent);
                finish();
            }
        });


    }

    @Override
    public void onResume() {

        super.onResume();

    }
}
