package dev.milos.databaseexample;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper{

    public static final String DATABASE_NAME="dbexample.db";
    public static final String TABLE_NAME="student";
    public static final String KEY_IDSTUDENT="_id";
    public static final String KEY_INDEKS="indeks";
    public static final String KEY_IMESTUDENTA="imeStudenta";
    public static final String KEY_PREZIMESTUDENTA="prezimeStudenta";
    public static final String KEY_BROJBODOVA="brojBodova";
    public static final String DATABASE_CREATE = "CREATE TABLE `student` (\n" +
            "\t`_id`\tINTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,\n" +
            "\t`indeks`\tINTEGER,\n" +
            "\t`imeStudenta`\tTEXT,\n" +
            "\t`prezimeStudenta`\tTEXT,\n" +
            "\t`brojBodova`\tREAL\n" +
            ");";

    SQLiteDatabase db;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DATABASE_CREATE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public boolean insertStudent(String indeks, String imeStudenta, String prezimeStudenta, String brojBodova){

        db = this.getWritableDatabase();
        ContentValues args = new ContentValues();
        args.put(KEY_INDEKS, indeks);
        args.put(KEY_IMESTUDENTA, imeStudenta);
        args.put(KEY_PREZIMESTUDENTA, prezimeStudenta);
        args.put(KEY_BROJBODOVA, brojBodova);



        long result = db.insert(TABLE_NAME, null, args);

        if(result == -1){
            return false;
        }else{
            return true;
        }

    }

    public Cursor getAllStudents(){
        db = this.getReadableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);

        return res;

    }

    public boolean updateStudent(String id, String indeks, String imeStudenta, String prezimeStudenta, String brojBodova){

        db = this.getWritableDatabase();
        ContentValues args = new ContentValues();
        args.put(KEY_IDSTUDENT, id);
        args.put(KEY_INDEKS, indeks);
        args.put(KEY_IMESTUDENTA, imeStudenta);
        args.put(KEY_PREZIMESTUDENTA, prezimeStudenta);
        args.put(KEY_BROJBODOVA, brojBodova);

        long result = db.update(TABLE_NAME, args, "_id = ?", new String[] {id});


        return true;
    }

    public Integer removeStudent(String id){

        db = this.getWritableDatabase();
        return db.delete(TABLE_NAME,"_id = ?", new String[] {id});

    }
}
