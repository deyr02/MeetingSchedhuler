package com.example.meetingschedhuler;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.io.ByteArrayOutputStream;
import java.io.Console;
import java.security.PublicKey;
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



    //////////////////////////////////////////////////////////////////
    //Image
    //////////////////////////////////////////////////////////////////
    private  static  final String TABLE_IMAGE = "Image";
    private  static final String IMAGE_ID = "_id";
    private  static final String IMAGE_CONTACT_ID = "_contactId";
    private  static final String IMAGE_IS_PROFILE_PICTURE = "IsProfilePicture";
    private  static final String IMAGE = "Image";

    //////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////

    private ByteArrayOutputStream byteArrayOutputStream;
    private byte[] imageInBytes;


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

        //Creating Contact Table
        sqLiteDatabase.execSQL(create_contact_table);

        String create_image_table = "CREATE TABLE " + TABLE_IMAGE + " (" +
                 IMAGE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                IMAGE_CONTACT_ID + " INTEGER NOT NULL,  " +
                IMAGE_IS_PROFILE_PICTURE + " INTEGER, "  +
                IMAGE + " BLOB NOT NULL," +
                "FOREIGN KEY (" +IMAGE_CONTACT_ID + ") REFERENCES " + TABLE_CONTACT + "("  +CONTACT_ID+"));";
        sqLiteDatabase.execSQL(create_image_table);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_CONTACT);
        onCreate(sqLiteDatabase);

    }


    public  long addContact(Contact contact){
        SQLiteDatabase db = this.getReadableDatabase();

        try{
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
        finally {
            db.close();
        }
    }
/*
    public ArrayList<Contact> getAllContacts(){
        ArrayList<Contact>output = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        try{
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

                    Bitmap profileImage = this.getProfileImage( Integer.parseInt( cursor.getString(0)));
                    contact.set_profileImage(profileImage);
                    output.add(contact);

                }while (cursor.moveToNext());
            }

            return  output;
        }
        catch (Exception ex){
            ex.printStackTrace();
            return  null;
        }
        finally {
            db.close();
        }


    }
    */

    public ArrayList<Contact> getAllContacts(ContactType contactType){
        ArrayList<Contact>output = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "";
        switch (contactType){
            case IMPORTANT:
                query = "SELECT * FROM " +TABLE_CONTACT+ " WHERE  IsMainUser = 0"
                        + " AND " + CONTACT_IS_IMPORTANT +" = 1"
                        + " ORDER BY "+ CONTACT_FIRSTNAME +" ASC";
                break;
            case FAVOURITE:
                query = "SELECT * FROM " +TABLE_CONTACT+ " WHERE  IsMainUser = 0"
                        + " AND " + CONTACT_IS_FAVOURITE +" = 1"
                        + " ORDER BY "+ CONTACT_FIRSTNAME +" ASC";
                break;
            case NORMAL:
                query = "SELECT * FROM " +TABLE_CONTACT+ " WHERE  IsMainUser = 0" + " ORDER BY "+ CONTACT_FIRSTNAME +" ASC";
                break;
        }

        try{
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

                    Bitmap profileImage = this.getProfileImage( Integer.parseInt( cursor.getString(0)));
                    contact.set_profileImage(profileImage);
                    output.add(contact);

                }while (cursor.moveToNext());
            }

            return  output;
        }
        catch (Exception ex){
            ex.printStackTrace();
            return  null;
        }
        finally {
            db.close();
        }


    }



    public  long toggleContactType(Integer id, ContactType contactType){
        SQLiteDatabase db = this.getReadableDatabase();

        try{
            String queryType = "";
            switch (contactType){
                case IMPORTANT:
                    queryType = CONTACT_IS_IMPORTANT;
                    break;
                case FAVOURITE:
                    queryType= CONTACT_IS_FAVOURITE;
                    break;
                default:
                    queryType = "";
                    break;
            }
            if (queryType != ""){
                String  checkingQuery = "SELECT " + queryType + " FROM " +TABLE_CONTACT + " WHERE " + CONTACT_ID + " = " + id +";";
                Cursor cursor = db.rawQuery(checkingQuery, null);
                if(cursor.getCount() == 0){
                    return  -1;
                }
                cursor.moveToFirst();
                Integer result = Integer.parseInt( cursor.getString(0));

                SQLiteDatabase db_w= this.getWritableDatabase();
                ContentValues cv = new ContentValues();

                if(result ==0){
                    cv.put(queryType, 1);
                }
                else {
                    cv.put(queryType, 0);
                }
                return  db_w.update(TABLE_CONTACT, cv, "_id=?", new String[]{id.toString()});
            }

            return  1;
        }
        catch (Exception ex){
            ex.printStackTrace();
            return -1;
        }
        finally {
            db.close();
        }

    }


    public Contact getContactDetail(Integer id){
        Contact output = new Contact();
        SQLiteDatabase db = this.getReadableDatabase();
        try{
            String query = "SELECT * FROM " +TABLE_CONTACT+ " WHERE " +CONTACT_ID +" = " + id + " ;";
            if(db != null) {
               Cursor cursor = db.rawQuery(query, null);
                if (cursor.getCount() == 0) {
                    return output;
                }
                cursor.moveToFirst();
                output = new Contact(
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
                Bitmap profileImage = this.getProfileImage( Integer.parseInt( cursor.getString(0)));
                output.set_profileImage(profileImage);

            }
            return output;
        }
        catch (Exception ex){
            ex.printStackTrace();
            return null;
        }
        finally {
            db.close();
        }

    }

    public  long updateContactDetails(Contact contact){
        SQLiteDatabase db = this.getWritableDatabase();
        try{
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

            return db.update(TABLE_CONTACT, cv, "_id=?", new String[]{""+contact.get_id()});
        }
        catch (Exception ex){
            ex.printStackTrace();
            return -1;
        }
        finally {
            db.close();
        }
    }

    public long deleteContactDetail(Integer id){
        SQLiteDatabase db = this.getWritableDatabase();
        try{
            return db.delete(TABLE_CONTACT, "_id=?", new String[]{""+id});
        }
        catch (Exception ex){
            ex.printStackTrace();
            return -1;
        }
        finally {
            db.close();
        }

    }


    public long addImage(Image image){
        SQLiteDatabase db = this.getWritableDatabase();
        try{

            Bitmap imageToStore = image.getImage();
            byteArrayOutputStream = new ByteArrayOutputStream();
            imageToStore.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
            imageInBytes = byteArrayOutputStream.toByteArray();

            ContentValues cv = new ContentValues();
            cv.put(IMAGE_CONTACT_ID, image.get_contactId());
            cv.put(IMAGE_IS_PROFILE_PICTURE, image.getIsProfilePicture());
            cv.put(IMAGE, imageInBytes);

            return db.insert(TABLE_IMAGE, null ,cv);
        }
        catch (Exception ex){
            ex.printStackTrace();
            return  -1;

        }
        finally {
            db.close();
        }
    }

    public  ArrayList<Image> getAllImages(Integer contactID){
        SQLiteDatabase db = this.getWritableDatabase();
        ArrayList<Image> output = new ArrayList<Image>();
        try{
            String query = "SELECT * FROM " +TABLE_IMAGE+ " WHERE " + IMAGE_CONTACT_ID + " = "+ contactID+
                    " ORDER BY "+ IMAGE_ID +" DESC";
            Cursor cursor = null;
            if(db != null){
                cursor = db.rawQuery(query, null);
                if (cursor.getCount() == 0){
                    return output;
                }
                cursor.moveToFirst();
                do{
                    byte[] re_image_bytes = cursor.getBlob(3);
                    Bitmap imageBitmap = BitmapFactory.decodeByteArray(re_image_bytes, 0, re_image_bytes.length);

                    Image image = new Image(
                            Integer.parseInt( cursor.getString(0)),
                            Integer.parseInt( cursor.getString(1)),
                            Integer.parseInt(cursor.getString(2)),
                            imageBitmap);
                    output.add(image);
                }while (cursor.moveToNext());
            }
            return  output;
        }
        catch (Exception ex){
            ex.printStackTrace();
            return null;
        }
        finally {
            db.close();
        }
    }
    private Bitmap getProfileImage(Integer ContactID){
        SQLiteDatabase db = this.getReadableDatabase();
        try{
            String query = "SELECT IMAGE FROM " + TABLE_IMAGE + " WHERE " +IMAGE_CONTACT_ID + " = " + ContactID + " AND " + IMAGE_IS_PROFILE_PICTURE + " =  1";
            Cursor cursor = null;
            Bitmap imageBitmap = null;
            if(db != null){
                cursor = db.rawQuery(query, null);
                if (cursor.getCount() == 0){
                    return null;
                }
                cursor.moveToFirst();
                    byte[] re_image_bytes = cursor.getBlob(0);
                    imageBitmap = BitmapFactory.decodeByteArray(re_image_bytes, 0, re_image_bytes.length);
            }
            return  imageBitmap;
        }
        catch (Exception ex){
            ex.printStackTrace();
            Toast.makeText(context, ex.getMessage(), Toast.LENGTH_SHORT).show();
            return null;
        }
        finally {
            db.close();
        }
    }

    public  long deleteImage(Integer id){
        SQLiteDatabase db = this.getWritableDatabase();
        try{
            return db.delete(TABLE_IMAGE, "_id=?", new String[]{""+id});
        }
        catch (Exception ex){
            ex.printStackTrace();
            return  -1;
        }
        finally {
            db.close();
        }

    }

    public  long  setProfileImage (Integer _imageID, Integer _imageContactID){
        SQLiteDatabase db = this.getWritableDatabase();
        try{
            ContentValues cv = new ContentValues();
            cv.put(IMAGE_IS_PROFILE_PICTURE, 0);
            long output =0;
           long result =  db.update(TABLE_IMAGE, cv,"_contactId=?", new String[]{_imageContactID.toString()});
            if (result != -1){
               ContentValues cv1 = new ContentValues();
               cv1.put(IMAGE_IS_PROFILE_PICTURE, 1);
               output = db.update(TABLE_IMAGE, cv1,"_id=?", new String[]{_imageID.toString()});
           }
           return  output;
        }
        catch (Exception ex){
            ex.printStackTrace();
            return  -1;
        }
        finally {
            db.close();
        }
    }

}

