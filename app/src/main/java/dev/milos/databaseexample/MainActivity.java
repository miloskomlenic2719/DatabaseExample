package dev.milos.databaseexample;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    DatabaseHelper db;
    EditText etIndeks, etImeStudenta, etPrezimeStudenta, etBrojBodova;
    Button btDodajStudenta, btListaStudenata;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = new DatabaseHelper(this);
        etIndeks = (EditText) findViewById(R.id.etIndeks);
        etImeStudenta = (EditText) findViewById(R.id.etImeStudenta);
        etPrezimeStudenta = (EditText) findViewById(R.id.etPrezimeStudenta);
        etBrojBodova = (EditText) findViewById(R.id.etBrojBodova);
        btDodajStudenta = (Button) findViewById(R.id.btDodajStudenta);
        btListaStudenata = (Button) findViewById(R.id.btListaStudenata);

        btListaStudenata.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), ListActivity.class);
                startActivity(intent);
            }
        });

        btDodajStudenta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                boolean isIndeksEmpty = etIndeks.getText().toString().isEmpty();
                boolean isImeEmpty = etImeStudenta.getText().toString().isEmpty();
                boolean isPrezimeEmpty = etPrezimeStudenta.getText().toString().isEmpty();
                boolean isBrojBodovaEmpty = etBrojBodova.getText().toString().isEmpty();

                if(isIndeksEmpty == true || isImeEmpty == true || isPrezimeEmpty == true || isBrojBodovaEmpty == true){
                    Toast.makeText(MainActivity.this, "Niste uneli sva polja!", Toast.LENGTH_SHORT).show();

                }else{
                    boolean isInserted = db.insertStudent(etIndeks.getText().toString(),
                            etImeStudenta.getText().toString(),etPrezimeStudenta.getText().toString(),
                            etBrojBodova.getText().toString());

                    if(isInserted){
                        Toast.makeText(MainActivity.this, "Student je uspesno dodat!", Toast.LENGTH_SHORT).show();
                        clearInputs();

                    }else{
                        Toast.makeText(MainActivity.this, "Student nije dodat!", Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });


    }

    public void clearInputs(){
        etIndeks.getText().clear();
        etImeStudenta.getText().clear();
        etPrezimeStudenta.getText().clear();
        etBrojBodova.getText().clear();
    }
}
