package com.cstlabs.rmd.StorageHandlers;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


/*
 * This class handles all access operations of internal database storage  
 * 
 * @author Akila Darshana Panditha
 * @version 1.0
 */
public class SQLLiteHandler extends SQLiteOpenHelper {

	/*
	 * Creates new handler for database storage
	 * @param refefing context 
	 */
    public SQLLiteHandler(Context context) {
        super(context, "RecordMyDay", null, 8);//TODO AKILA change the SQL lite version number !!!
       
    }

    // called when referred data base is accessed in first instance 
    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL("CREATE TABLE `Note` (`id` INTEGER  NOT NULL PRIMARY KEY AUTOINCREMENT,  `date` DATE NOT NULL,  `title` VARCHAR(45) NOT NULL,  `location` VARCHAR(45) NOT NULL,  `noteContent` VARCHAR(500) NOT NULL)");
        database.execSQL("CREATE TABLE `photos` (`id` INTEGER  NOT NULL PRIMARY KEY AUTOINCREMENT,  `date` DATE NOT NULL,  `title` VARCHAR(45) NOT NULL,  `location` VARCHAR(45) NOT NULL,  `noteDescription` VARCHAR(500) NOT NULL, `photo` BLOB NOT NULL )");
        database.execSQL("CREATE TABLE `SystemData` ( `password` VARCHAR(45) NOT NULL, `Question` VARCHAR(200) NOT NULL,  `answer` VARCHAR(200) NOT NULL,  `key` VARCHAR(100) NOT NULL)");
    }

 // called when database version is updated 
    @Override
    public void onUpgrade(SQLiteDatabase db, int arg1, int arg2) {
        Log.i("upgrade", "SQLLite upgraded");
        db.execSQL("DROP TABLE IF EXISTS Note");
        db.execSQL("DROP TABLE IF EXISTS Photos");
        db.execSQL("DROP TABLE IF EXISTS SystemData");
        onCreate(db);
    }
}
