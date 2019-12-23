package com.aszqsc.dontforgeteverything.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class MyDatabaseHelper extends SQLiteOpenHelper {
    private static final String TAG = "SQLite";
    private static final int DATABASE_VERSION = 1;
    // Tên cơ sở dữ liệu.
    private static final String DATABASE_NAME = "Note_Manager";
    // Tên bảng: Note.
    private static final String TABLE_NOTE = "Note";
    private static final String COLUMN_NOTE_ID = "Note_Id";
    private static final String COLUMN_NOTECATE_ID = "NoteCate_Id";
    private static final String COLUMN_NOTE_COLOR = "Note_color";
    private static final String COLUMN_NOTE_TITLE = "Note_Title";
    private static final String COLUMN_NOTE_CONTENT = "Note_Content";
    private static final String COLUMN_NOTE_IS_NOTI = "Is_notify";
    private static final String COLUMN_NOTE_YEAR = "Note_year";
    private static final String COLUMN_NOTE_MONTH = "Note_month";
    private static final String COLUMN_NOTE_Day = "Note_day";
    private static final String COLUMN_NOTE_Hour = "Note_hour";
    private static final String COLUMN_NOTE_Minute = "Note_minute";
    private static final String COLUMN_NOTE_PASSWORD = "Note_password";

    // Tên bảng: Category.
    private static final String TABLE_CATEGORY = "Category";
    private static final String COLUMN_CATE_ID = "Cate_Id";
    private static final String COLUMN_CATE_NAME = "Cate_Name";

    public MyDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        Log.i(TAG, "MyDatabaseHelper.onCreate ... ");
        // Script tạo bảng.
        String script = "CREATE TABLE " + TABLE_NOTE + "("
                + COLUMN_NOTE_ID + " INTEGER PRIMARY KEY,"
                + COLUMN_NOTECATE_ID + " INTEGER,"
                + COLUMN_NOTE_COLOR + " TEXT,"
                + COLUMN_NOTE_TITLE + " TEXT,"
                + COLUMN_NOTE_CONTENT + " TEXT,"
                + COLUMN_NOTE_IS_NOTI + " TEXT,"
                + COLUMN_NOTE_YEAR + " TEXT,"
                + COLUMN_NOTE_MONTH + " TEXT,"
                + COLUMN_NOTE_Day + " TEXT,"
                + COLUMN_NOTE_Hour + " TEXT,"
                + COLUMN_NOTE_Minute + " TEXT,"
                + COLUMN_NOTE_PASSWORD + " TEXT"
                + ")";
        // Chạy lệnh tạo bảng.
        sqLiteDatabase.execSQL(script);
        script = "CREATE TABLE " + TABLE_CATEGORY + "("
                + COLUMN_CATE_ID + " INTEGER PRIMARY KEY,"
                + COLUMN_CATE_NAME + " TEXT"
                + ")";
        sqLiteDatabase.execSQL(script);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        Log.i(TAG, "MyDatabaseHelper.onUpgrade ... ");
        // Hủy (drop) bảng cũ nếu nó đã tồn tại.
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NOTE);
        // Và tạo lại.
        onCreate(sqLiteDatabase);
    }

    public void addNote(Note note) {
        Log.i(TAG, "MyDatabaseHelper.addNote ... " + note.getTitle());

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_NOTECATE_ID, note.getCateID());
        values.put(COLUMN_NOTE_COLOR, note.getColorTheme() + "");
        values.put(COLUMN_NOTE_TITLE, note.getTitle());
        values.put(COLUMN_NOTE_CONTENT, note.getContent());
        values.put(COLUMN_NOTE_IS_NOTI, note.isNoti() + "");
        values.put(COLUMN_NOTE_YEAR, note.getYear() + "");
        values.put(COLUMN_NOTE_MONTH, note.getMonth() + "");
        values.put(COLUMN_NOTE_Day, note.getDay() + "");
        values.put(COLUMN_NOTE_Hour, note.getHour() + "");
        values.put(COLUMN_NOTE_Minute, note.getMinute() + "");
        values.put(COLUMN_NOTE_PASSWORD, note.getPassword() + "");
        // Trèn một dòng dữ liệu vào bảng.
        db.insert(TABLE_NOTE, null, values);

        // Đóng kết nối database.
        db.close();
    }


    public Note getNote(int id) {
        Log.i(TAG, "MyDatabaseHelper.getNote ... " + id);

        SQLiteDatabase db = this.getReadableDatabase();


        Cursor cursor = db.query(TABLE_NOTE, new String[]
                        {COLUMN_NOTE_ID, COLUMN_NOTECATE_ID, COLUMN_NOTE_COLOR, COLUMN_NOTE_TITLE, COLUMN_NOTE_CONTENT, COLUMN_NOTE_IS_NOTI, COLUMN_NOTE_YEAR, COLUMN_NOTE_MONTH,
                                COLUMN_NOTE_Day, COLUMN_NOTE_Hour, COLUMN_NOTE_Minute, COLUMN_NOTE_PASSWORD
                        }

                , COLUMN_NOTE_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);

        if (cursor != null)
            cursor.moveToFirst();

        Note note = new Note(Integer.parseInt(cursor.getString(0)), Integer.parseInt(cursor.getString(1))
                , Integer.parseInt(cursor.getString(2)), cursor.getString(3), cursor.getString(4), Boolean.parseBoolean(cursor.getString(5))
                , Integer.parseInt(cursor.getString(6)), Integer.parseInt(cursor.getString(7)), Integer.parseInt(cursor.getString(8))
                , Integer.parseInt(cursor.getString(9)), Integer.parseInt(cursor.getString(10)), cursor.getString(11));


        // return note
        return note;
    }


    public List<Note> getAllNotes() {
        Log.i(TAG, "MyDatabaseHelper.getAllNotes ... ");

        List<Note> noteList = new ArrayList<Note>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_NOTE;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        // Duyệt trên con trỏ, và thêm vào danh sách.
        if (cursor.moveToFirst()) {
            do {
                Note note = new Note(Integer.parseInt(cursor.getString(0)), Integer.parseInt(cursor.getString(1))
                        , Integer.parseInt(cursor.getString(2)), cursor.getString(3), cursor.getString(4), Boolean.parseBoolean(cursor.getString(5))
                        , Integer.parseInt(cursor.getString(6)), Integer.parseInt(cursor.getString(7)), Integer.parseInt(cursor.getString(8))
                        , Integer.parseInt(cursor.getString(9)), Integer.parseInt(cursor.getString(10)), cursor.getString(11));

                // Thêm vào danh sách.
                noteList.add(note);
            } while (cursor.moveToNext());
        }

        // return note list
        return noteList;
    }

    public List<Note> getAllNotesByCate(int Cateid) {
        Log.i(TAG, "MyDatabaseHelper.getAllNotes ... ");

        List<Note> noteList = new ArrayList<Note>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_NOTE + " WHERE " + COLUMN_NOTECATE_ID + "=" + Cateid;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        // Duyệt trên con trỏ, và thêm vào danh sách.
        if (cursor.moveToFirst()) {
            do {
                Note note = new Note(Integer.parseInt(cursor.getString(0)), Integer.parseInt(cursor.getString(1))
                        , Integer.parseInt(cursor.getString(2)), cursor.getString(3), cursor.getString(4), Boolean.parseBoolean(cursor.getString(5))
                        , Integer.parseInt(cursor.getString(6)), Integer.parseInt(cursor.getString(7)), Integer.parseInt(cursor.getString(8))
                        , Integer.parseInt(cursor.getString(9)), Integer.parseInt(cursor.getString(10)), cursor.getString(11));

                // Thêm vào danh sách.
                noteList.add(note);
            } while (cursor.moveToNext());
        }

        // return note list
        return noteList;
    }

    public int getNotesCount() {
        Log.i(TAG, "MyDatabaseHelper.getNotesCount ... ");

        String countQuery = "SELECT  * FROM " + TABLE_NOTE;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);

        int count = cursor.getCount();

        cursor.close();

        // return count
        return count;
    }


    public int updateNote(Note note) {
        Log.i(TAG, "MyDatabaseHelper.updateNote ... " + note.getTitle());

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_NOTECATE_ID, note.getCateID());
        values.put(COLUMN_NOTE_COLOR, note.getColorTheme() + "");
        values.put(COLUMN_NOTE_TITLE, note.getTitle());
        values.put(COLUMN_NOTE_CONTENT, note.getContent());
        values.put(COLUMN_NOTE_IS_NOTI, note.isNoti() + "");
        values.put(COLUMN_NOTE_YEAR, note.getYear() + "");
        values.put(COLUMN_NOTE_MONTH, note.getMonth() + "");
        values.put(COLUMN_NOTE_Day, note.getDay() + "");
        values.put(COLUMN_NOTE_Hour, note.getHour() + "");
        values.put(COLUMN_NOTE_Minute, note.getMinute() + "");
        values.put(COLUMN_NOTE_PASSWORD, note.getPassword() + "");


        // updating row
        return db.update(TABLE_NOTE, values, COLUMN_NOTE_ID + " = ?",
                new String[]{String.valueOf(note.getId())});
    }

    public void deleteNote(Note note) {
        Log.i(TAG, "MyDatabaseHelper.updateNote ... " + note.getTitle());

        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NOTE, COLUMN_NOTE_ID + " = ?",
                new String[]{String.valueOf(note.getId())});
        db.close();
    }

    //category
    public void addCategory(Category category) {
        Log.i(TAG, "MyDatabaseHelper.addNote ... " + category.getName());

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_CATE_NAME, category.getName());

        // Trèn một dòng dữ liệu vào bảng.
        db.insert(TABLE_CATEGORY, null, values);

        // Đóng kết nối database.
        db.close();
    }


    public Category getCate(int id) {
        Log.i(TAG, "MyDatabaseHelper.getNote ... " + id);

        SQLiteDatabase db = this.getReadableDatabase();


        Cursor cursor = db.query(TABLE_NOTE, new String[]
                        {COLUMN_CATE_ID, COLUMN_CATE_NAME
                        }

                , COLUMN_NOTE_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);

        if (cursor != null)
            cursor.moveToFirst();

        Category c = new Category(Integer.parseInt(cursor.getString(0)), cursor.getString(1));

        // return note
        return c;
    }


    public List<Category> getAllCate() {
        Log.i(TAG, "MyDatabaseHelper.getAllNotes ... ");

        List<Category> noteList = new ArrayList<Category>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_CATEGORY;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        // Duyệt trên con trỏ, và thêm vào danh sách.
        if (cursor.moveToFirst()) {
            do {
                Category c = new Category(Integer.parseInt(cursor.getString(0)), cursor.getString(1));

                // Thêm vào danh sách.
                noteList.add(c);
            } while (cursor.moveToNext());
        }

        // return note list
        return noteList;
    }

    public void deleteCate(Category note) {
        Log.i(TAG, "MyDatabaseHelper.updateNote ... " + note.getName());

        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_CATEGORY, COLUMN_CATE_ID + " = ?",
                new String[]{String.valueOf(note.getId())});
        db.close();
    }
}
