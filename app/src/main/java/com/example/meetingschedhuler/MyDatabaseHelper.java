package com.example.meetingschedhuler;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class MyDatabaseHelper extends SQLiteOpenHelper {

    private  final  Context context;

    //Database details
    private  static  final  String DATABASE_NAME = "ContactScheduler";
    private  static  final int DATABASE_VERSION = 1;

    /////////////////////////////////////////////////////////////
    //CONTACT TABLE
    /////////////////////////////////////////////////////////////
    private  static  final String TABLE_CONTACT = "Contact";
    private  static final String CONTACT_ID = "_id";
    private  static final String CONTACT_FIRSTNAME = "FirstName";
    private  static final String CONTACT_LASTNAME = "LastName";

    private  static final String CONTACT_CELLPHONE = "CellPhone";
    private  static final String CONTACT_EMAIL  = "Email";
    private  static final String CONTACT_FACEBOOK  = "FACEBOOK";
    private  static final String CONTACT_LINKEDIN  = "LinkedIN";
    private  static final String CONTACT_INSTAGRAM  = "Instagram";
    private  static final String CONTACT_WEBSITE  = "Website";

    private  static final String CONTACT_IS_FAVOURITE  = "IsFavourite";
    private  static final String CONTACT_IS_IMPORTANT  = "IsImportant";
    private  static final String CONTACT_IS_MAIN_USER  = "IsMainUser";

    private static  final String CONTACT_STREET_ADDRESS = "StreetAddress";
    private static  final String CONTACT_SUBURB = "Suburb";
    private static  final String CONTACT_CITY = "City";
    private static  final String CONTACT_POSTAL_CODE = "PostalCODE";
    private static  final String CONTACT_COUNTRY = "Country";

    //////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////


    public MyDatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String create_contact_table = "CREATE TABLE " + TABLE_CONTACT +
                " (" + CONTACT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                CONTACT_FIRSTNAME + " TEXT,  "+
                CONTACT_LASTNAME + " TEXT,  "+

                CONTACT_CELLPHONE + " TEXT,  "+
                CONTACT_EMAIL + " TEXT,  "+
                CONTACT_FACEBOOK + " TEXT,  "+
                CONTACT_LINKEDIN + " TEXT,  "+
                CONTACT_INSTAGRAM + " TEXT,  "+
                CONTACT_WEBSITE+ " TEXT,  "+

                CONTACT_IS_FAVOURITE+ " INTEGER,  "+
                CONTACT_IS_IMPORTANT+ " INTEGER,  "+
                CONTACT_IS_MAIN_USER+ " INTEGER,  "+

                CONTACT_STREET_ADDRESS+ " TEXT,  "+
                CONTACT_SUBURB+ " TEXT,  "+
                CONTACT_CITY+ " TEXT,  "+
                CONTACT_POSTAL_CODE+ " TEXT,  "+
                CONTACT_COUNTRY+ " TEXT);";

        //Creating the table
        sqLiteDatabase.execSQL(create_contact_table);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_CONTACT);
        onCreate(sqLiteDatabase);

    }
}
