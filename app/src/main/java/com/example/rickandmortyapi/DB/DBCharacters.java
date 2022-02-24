package com.example.rickandmortyapi.DB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

public class DBCharacters extends SQLiteOpenHelper {
    private static final String DB_NAME = "BD_CHARACTERS";
    private static final String DB_TABLE_NAME = "characters";
    private static final int DB_VERSION = 3;
    private static final String ID_COLUM = "id";
    private static final String NAME_COLUMN = "name";
    private static final String STATUS_COLUMN = "status";
    private static final String SPECIES_COLUMN = "species";
    private static final String GENDER_COLUMN = "gender";
    private static final String LOCATION_COLUMN = "location";
    private static final String IMAGE_COLUMN = "image";
    private static final String FAVORITE_COLUMN = "favorite";
    private Context mContext;
    SQLiteDatabase sqlDB;

    public DBCharacters(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqlDB = sqLiteDatabase;

        String CREATE_USER_TABLE = "CREATE TABLE "
                + DB_TABLE_NAME + "("
                    +ID_COLUM + " NUMBER, "
                    +NAME_COLUMN + " TEXT,"
                    +STATUS_COLUMN + " TEXT,"
                    +SPECIES_COLUMN + " TEXT,"
                    +GENDER_COLUMN + " TEXT,"
                    +LOCATION_COLUMN + " TEXT,"
                    +IMAGE_COLUMN + " TEXT,"
                    +FAVORITE_COLUMN + " BOOLEAN"
                +")"
        ;
        sqLiteDatabase.execSQL(CREATE_USER_TABLE);

        Log("Tablas creadas");
    }

    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
    public void getVersionDB(){
        Log(Integer.toString(this.getReadableDatabase().getVersion()));
    }
    public void deleteAll(){
        Log("Delete all realisao");
        SQLiteDatabase db = this.getWritableDatabase();

        String delete = "DELETE FROM "+ DB_TABLE_NAME+" WHERE id > 0 ";
        db.execSQL(delete);
        db.close();

    }
    public void delete(int id){
        Log("Delete realisao a id="+id);
        SQLiteDatabase db = this.getWritableDatabase();

        String delete = "DELETE FROM "+ DB_TABLE_NAME+" WHERE id = "+id;
        db.execSQL(delete);
        db.close();
    }

    public void insert(int id, String name,String status, String species, String gender, String location, String image, Boolean favorite){
        Log("Insert realisao");
        SQLiteDatabase db = this.getWritableDatabase();
        long result = -1;

        ContentValues values = new ContentValues();
        values.put(ID_COLUM, id);
        values.put(NAME_COLUMN, name);
        values.put(STATUS_COLUMN, status);
        values.put(SPECIES_COLUMN, species);
        values.put(GENDER_COLUMN, gender);
        values.put(LOCATION_COLUMN, location);
        values.put(IMAGE_COLUMN, image);
        values.put(FAVORITE_COLUMN, favorite);

        db.insert(DB_TABLE_NAME,null,values);

        db.close();
    }

    public ArrayList<Integer> getAllid(){
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<Integer> ides = new ArrayList<>();

        String[] cols = new String[]{ ID_COLUM };

        Cursor c = db.query(DB_TABLE_NAME,cols,null,null,null,null,null);

        if(c.moveToFirst()){
            do{
                int id = c.getInt(0);

                ides.add(id);
            }while(c.moveToNext());
        }
        return ides;
    }

    public ArrayList<String> getAllNames(){
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<String> users = new ArrayList<>();

        String[] cols = new String[]{ NAME_COLUMN };

        Cursor c = db.query(DB_TABLE_NAME,cols,null,null,null,null,null);

        if(c.moveToFirst()){
            do{
                String user = c.getString(0);

                users.add(user);
            }while(c.moveToNext());
        }
        return users;
    }
    public ArrayList<String> getAllStatus(){
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<String> users = new ArrayList<>();

        String[] cols = new String[]{ STATUS_COLUMN };

        Cursor c = db.query(DB_TABLE_NAME,cols,null,null,null,null,null);

        if(c.moveToFirst()){
            do{
                String user = c.getString(0);

                users.add(user);
            }while(c.moveToNext());
        }
        return users;
    }

    public ArrayList<String> getAllSpecies(){
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<String> users = new ArrayList<>();

        String[] cols = new String[]{ SPECIES_COLUMN };

        Cursor c = db.query(DB_TABLE_NAME,cols,null,null,null,null,null);

        if(c.moveToFirst()){
            do{
                String user = c.getString(0);

                users.add(user);
            }while(c.moveToNext());
        }
        return users;
    }

    public ArrayList<String> getAllGender(){
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<String> users = new ArrayList<>();

        String[] cols = new String[]{ GENDER_COLUMN };

        Cursor c = db.query(DB_TABLE_NAME,cols,null,null,null,null,null);

        if(c.moveToFirst()){
            do{
                String user = c.getString(0);
                users.add(user);
            }while(c.moveToNext());
        }
        return users;
    }
    public ArrayList<String> getAllLocation(){
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<String> users = new ArrayList<>();

        String[] cols = new String[]{ LOCATION_COLUMN };

        Cursor c = db.query(DB_TABLE_NAME,cols,null,null,null,null,null);

        if(c.moveToFirst()){
            do{
                String user = c.getString(0);

                users.add(user);
            }while(c.moveToNext());
        }
        return users;
    }

    public ArrayList<String> getAllImage(){
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<String> users = new ArrayList<>();

        String[] cols = new String[]{ IMAGE_COLUMN };

        Cursor c = db.query(DB_TABLE_NAME,cols,null,null,null,null,null);

        if(c.moveToFirst()){
            do{
                String user = c.getString(0);

                users.add(user);
            }while(c.moveToNext());
        }
        return users;
    }

    public ArrayList<Boolean> getAllFavorite(){
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<Boolean> users = new ArrayList<>();

        String[] cols = new String[]{ FAVORITE_COLUMN };

        Cursor c = db.query(DB_TABLE_NAME,cols,null,null,null,null,null);
        boolean aux;
        if(c.moveToFirst()){
            do{
                String user = c.getString(0);

                if(user.equals("1")){
                    aux=true;
                }else{
                    aux=false;
                }
                Log("boolean:"+user);


                users.add(aux);
            }while(c.moveToNext());
        }
        return users;
    }

    public void Log(String msg){
        Log.d("DBaqui", msg);
    }

}
