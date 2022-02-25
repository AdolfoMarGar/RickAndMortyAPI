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
    //Base de datos para almacenar el color y los usuarios

    private static final String DB_NAME = "usuarios_database";
    private static final String DB_TABLE_NAME = "usuarios";
    private static final int DB_VERSION = 3;
    private static final String USER_COLUMN = "user";
    private static final String PASSWORD_COLUMN = "password";
    private static final String COLOR_COLUMN = "colores";

    private Context mContext;

    //Constructor de la bd
    public DBUsers(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        mContext = context;
    }

    //crea la estructura de la bd
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        String CREATE_USER_TABLE = "CREATE TABLE "
                + DB_TABLE_NAME + "("
                + USER_COLUMN + " TEXT, "
                +PASSWORD_COLUMN + " TEXT, "
                +COLOR_COLUMN+ " NUMBER" +
                ")"
        ;
        Log("Sentencia SQL:"+CREATE_USER_TABLE);


        sqLiteDatabase.execSQL(CREATE_USER_TABLE);

        Log("Tablas creadas "+USER_COLUMN+", "+PASSWORD_COLUMN+", "+COLOR_COLUMN);
        Log(CREATE_USER_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {}
    public void getVersionDB(){
        Log(Integer.toString(this.getReadableDatabase().getVersion()));
    }

    //metodo que realiza el insert en la bd
    public void insert(String user, String password,int color){
        Log("Insert realisao");
        SQLiteDatabase db = this.getWritableDatabase();
        long result = -1;

        ContentValues values = new ContentValues();
        values.put(USER_COLUMN, user);
        values.put(PASSWORD_COLUMN, password);
        values.put(COLOR_COLUMN,color);

        result = db.insert(DB_TABLE_NAME,null,values);

        db.close();

    }

    //metodo que borra todos los datos de la tabla
    public void deleteAll(){
        Log("Delete all realisao");
        SQLiteDatabase db = this.getWritableDatabase();

        String delete = "DELETE FROM "+ DB_TABLE_NAME;
        db.execSQL(delete);
        db.close();

    }

    //metodo select de los usuarios
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

    //metodo para obtener el color del tema
    public int getColor(){
        SQLiteDatabase db = this.getReadableDatabase();
        String[] cols = new String[]{ COLOR_COLUMN };

        Cursor c = db.query(DB_TABLE_NAME,cols,null,null,null,null,null);

        int col =-1;
        if(c.moveToFirst()) {
            col = c.getInt(0);
        }
        if(c!=null){
            c.close();

        }
        db.close();

        return col;
    }

    //metodo para obtener todas las contraseñas
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

    //log para ver los errores y mensajes en el logcat
    public void Log(String msg){
        Log.d("DBLOGIN", msg);
    }
}
