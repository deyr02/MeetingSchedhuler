package com.example.meetingschedhuler;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;

import javax.xml.transform.Result;

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
                CONTACT_FIRSTNAME + " TEXT NOT NULL,  "+
                CONTACT_LASTNAME + " TEXT NOT NULL,  "+

                CONTACT_CELLPHONE + " TEXT NOT NULL,  "+
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


    public  long addContact(Contact contact){
        try{
            SQLiteDatabase db = this.getReadableDatabase();
            ContentValues cv = new ContentValues();
            cv.put(CONTACT_FIRSTNAME, contact.get_firstName());
            cv.put(CONTACT_LASTNAME, contact.get_lastName());
            cv.put(CONTACT_CELLPHONE, contact.get_cellPhone());
            cv.put(CONTACT_EMAIL, contact.get_email());
            cv.put(CONTACT_FACEBOOK, contact.get_facebook());
            cv.put(CONTACT_LINKEDIN, contact.get_linkedIn());
            cv.put(CONTACT_INSTAGRAM, contact.get_instagram());
            cv.put(CONTACT_WEBSITE, contact.get_website());
            cv.put(CONTACT_IS_FAVOURITE, contact.get_is_favourite());
            cv.put(CONTACT_IS_IMPORTANT, contact.get_is_important());
            cv.put(CONTACT_IS_MAIN_USER, contact.get_is_main_user());
            cv.put(CONTACT_STREET_ADDRESS, contact.get_streetAddress());
            cv.put(CONTACT_SUBURB, contact.get_suburb());
            cv.put(CONTACT_CITY, contact.get_city());
            cv.put(CONTACT_POSTAL_CODE, contact.get_postalCOde());
            cv.put(CONTACT_COUNTRY, contact.get_country());

            long result = db.insert(TABLE_CONTACT, null, cv);
            return result;
        }
        catch (Exception ex){
            return -1;
        }
    }

    public ArrayList<Contact> getAllContacts(){
     ArrayList<Contact>output = new ArrayList<>();
     SQLiteDatabase db = this.getReadableDatabase();
     String query = "SELECT * FROM " +TABLE_CONTACT+ " WHERE  IsMainUser = 0" + " ORDER BY "+ CONTACT_FIRSTNAME +" ASC";

        Cursor cursor = null;
        if(db != null){
            cursor = db.rawQuery(query, null);
            if (cursor.getCount() == 0){
                return output;
            }

            cursor.moveToFirst();

            do{
                Contact contact = new Contact(
                       Integer.parseInt( cursor.getString(0)),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getString(4),
                        cursor.getString(5),
                        cursor.getString(6),
                        cursor.getString(7),
                        cursor.getString(8),

                       Integer.parseInt( cursor.getString(9)),
                       Integer.parseInt( cursor.getString(10)),
                       Integer.parseInt( cursor.getString(11)),

                        cursor.getString(12),
                        cursor.getString(13),
                        cursor.getString(14),
                        cursor.getString(15),
                        cursor.getString(16)
                );

                output.add(contact);

            }while (cursor.moveToNext());
        }
     return  output;
    }


}
