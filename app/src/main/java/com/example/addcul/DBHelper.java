package com.example.addcul;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {
    public static final int VERSION=2;
    public DBHelper(Context context){
        super(context, "addculDB", null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String memoSQL="create table food(" +
                "_id integer primary key autoincrement," +
                "name text," +
                "tag text," +
                "photo text);";
        db.execSQL(memoSQL);

        db.execSQL("insert into food(name, tag, photo)values ('삼겹살','#존맛#소주#비오는날', 'img_food_pork')");
        db.execSQL("insert into food(name, tag, photo)values ('떡볶이','#매운맛#중독#빨간맛', 'img_food_tteokbokki')");
        db.execSQL("insert into food(name, tag, photo)values ('양념치킨','#매콤달콤#치킨#맥주', 'img_food_chicken')");


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if(newVersion==VERSION){
            db.execSQL("drop table food");
            onCreate(db);
        }
    }
}
