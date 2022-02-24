package com.example.rickandmortyapi.DB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


public class DBUsers extends SQLiteOpenHelper {

    private static final String DB_NAME = "bd_usuarios";
    private static final String DB_TABLE_NAME = "usuarios";
    private static final int DB_VERSION = 3;
    private static final String USER_COLUMN = "user";
    private static final String PASSWORD_COLUMN = "password";
    private Context mContext;

    public DBUsers(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        String CREATE_USER_TABLE = "CREATE TABLE " + DB_TABLE_NAME + "("+ USER_COLUMN + " TEXT, "+PASSWORD_COLUMN + " TEXT)";
        sqLiteDatabase.execSQL(CREATE_USER_TABLE);

        Log("Tablas creadas");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public void getVersionDB(){
        Log(Integer.toString(this.getReadableDatabase().getVersion()));
    }

    public long insert(String user, String password){

        SQLiteDatabase db = this.getWritableDatabase();
        long result = -1;

        ContentValues values = new ContentValues();
        values.put(USER_COLUMN, user);
        values.put(PASSWORD_COLUMN, password);

        result = db.insert(DB_TABLE_NAME,null,values);

        db.close();

        return result;
    }

    public ArrayList<String> getAllUsers(){
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<String> users = new ArrayList<>();

        String[] cols = new String[]{ USER_COLUMN };

        Cursor c = db.query(DB_TABLE_NAME,cols,null,null,null,null,null);

        if(c.moveToFirst()){
            do{
                String user = c.getString(0);

                users.add(user);
            }while(c.moveToNext());
        }
        return users;
    }
    public ArrayList<String> getAllPasswords(){
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<String> passwords = new ArrayList<>();

        String[] cols = new String[]{ PASSWORD_COLUMN };

        Cursor c = db.query(DB_TABLE_NAME,cols,null,null,null,null,null);

        if(c.moveToFirst()){
            do{
                String pass = c.getString(0);

                passwords.add(pass);
            }while(c.moveToNext());
        }
        return passwords;
    }


    public void Log(String msg){
        Log.d("DBaqui", msg);
    }
}
