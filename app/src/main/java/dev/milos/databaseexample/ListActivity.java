package dev.milos.databaseexample;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class ListActivity extends AppCompatActivity {

    DatabaseHelper db;
    ListView listView;
    TextView tvIdStudent, tvIndeks, tvImeStudenta, tvPrezimeStudenta, tvBrojBodova;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        db = new DatabaseHelper(this);
        listView = (ListView) findViewById(R.id.listView);
        tvIdStudent = (TextView) findViewById(R.id.tvIdStudent);
        tvIndeks = (TextView) findViewById(R.id.tvIndeks);
        tvImeStudenta = (TextView) findViewById(R.id.tvImeStudenta);
        tvPrezimeStudenta = (TextView) findViewById(R.id.tvPrezimeStudenta);
        tvBrojBodova = (TextView) findViewById(R.id.tvBrojBodova);


        listView.setOnCreateContextMenuListener(this);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> myAdapter, View myView, int myItemInt, long mylng) {
                Intent intent = new Intent(ListActivity.this, UpdateActivity.class);

                String id = String.valueOf(mylng);
                String indeks = ((TextView) myView.findViewById(R.id.tvIndeks)).getText().toString();
                String ime = ((TextView) myView.findViewById(R.id.tvImeStudenta)).getText().toString();
                String prezime = ((TextView) myView.findViewById(R.id.tvPrezimeStudenta)).getText().toString();
                String bodovi = ((TextView) myView.findViewById(R.id.tvBrojBodova)).getText().toString();


                Bundle extras = new Bundle();
                extras.putString("_id", id);
                extras.putString("EXTRA_INDEKS", indeks );
                extras.putString("EXTRA_IME", ime);
                extras.putString("EXTRA_PREZIME", prezime);
                extras.putString("EXTRA_BROJBODOVA", bodovi);
                intent.putExtras(extras);
                startActivity(intent);
                finish();

            }
        });

        printStudents();


    }

    public void printStudents(){
        Cursor cursor = db.getAllStudents();
        String[] nizStudenata = new String[] {db.KEY_INDEKS, db.KEY_IMESTUDENTA, db.KEY_PREZIMESTUDENTA, db.KEY_BROJBODOVA};
        int[] toViewID = new int[] {R.id.tvIndeks, R.id.tvImeStudenta, R.id.tvPrezimeStudenta, R.id.tvBrojBodova};
        SimpleCursorAdapter simpleCursorAdapter;
        simpleCursorAdapter = new SimpleCursorAdapter(getApplicationContext(), R.layout.item_layout, cursor, nizStudenata, toViewID,0);

        listView.setAdapter(simpleCursorAdapter);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View view, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, view, menuInfo);
        CreateMenu(menu);
    }
    @Override
    public boolean onContextItemSelected(MenuItem item){

        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();

        switch (item.getItemId()){
            case 0:
                int deletedRows = db.removeStudent(String.valueOf(listView.getItemIdAtPosition(info.position)));

                if (deletedRows > 0){
                    Toast.makeText(ListActivity.this, "Student je uspesno obrisan!", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(ListActivity.this, "Student nije obrisan!", Toast.LENGTH_SHORT).show();
                }

                printStudents();
            default:
                return super.onContextItemSelected(item);
        }

    }

    private void CreateMenu(Menu menu){
        menu.setQwertyMode(true);
        menu.add(0, 0, 0, "Obrisi");

    }

    @Override
    public void onResume() {

        super.onResume();
        printStudents();
    }
}
