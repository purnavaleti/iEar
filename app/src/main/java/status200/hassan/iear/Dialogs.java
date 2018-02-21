package status200.hassan.iear;

import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import status200.hassan.iear.DatabaseSql;
public class Dialogs extends AppCompatActivity {

    public final static String KEY_EXTRA_CONTACT_ID = "KEY_EXTRA_CONTACT_ID";
    SQLiteDatabase sql;
    private ListView listView;
    DatabaseSql dbHelper;
    Intent intent;
    String check,name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialogs);

        intent = getIntent();

        dbHelper = new DatabaseSql(this);

        check = intent.getStringExtra("edittext");
        name = intent.getStringExtra("nameedit");
        if(check != null && name != null) {
            dbHelper.insertWord(name, check);
        }
        final Cursor cursor = dbHelper.getAllPersons();

        String [] columns = new String[] {
                DatabaseSql.PERSON_COLUMN_ID,
                DatabaseSql.PERSON_COLUMN_NAME,
                DatabaseSql.PERSON_COLUMN_DIALOG
        };
        int [] widgets = new int[] {
                R.id.id,
                R.id.name,
                R.id.dialog
        };

        SimpleCursorAdapter cursorAdapter = new SimpleCursorAdapter(this, R.layout.list,
                cursor, columns, widgets,2);
        listView = (ListView)findViewById(R.id.listView1);
        listView.setAdapter(cursorAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // action with ID action_refresh was selected
            case R.id.clear:
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
// Add the buttons
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User clicked OK button
                        dbHelper.deleteAll();
                        onBackPressed();
                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User cancelled the dialog

                    }
                });
                builder.setMessage("Your dialogs will be flushed")
                        .setTitle("Are you sure?");
                AlertDialog dialog = builder.create();
                dialog.show();
                break;


            default:
                break;
        }

        return true;
    }
}

class DatabaseSql extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "SQLiteExample.db";
    private static final int DATABASE_VERSION = 1;
    public static final String PERSON_TABLE_NAME = "sentences";
    public static final String PERSON_COLUMN_ID = "_id";
    public static final String PERSON_COLUMN_NAME = "word";
    public static final String PERSON_COLUMN_DIALOG = "dialog";
    public DatabaseSql(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + PERSON_TABLE_NAME + "( " +
                PERSON_COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                PERSON_COLUMN_NAME + " TEXT, " + PERSON_COLUMN_DIALOG + " TEXT )"
        );
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + PERSON_TABLE_NAME);
        onCreate(db);
    }
    public boolean insertWord(String word, String dialog) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(PERSON_COLUMN_NAME, word);
        contentValues.put(PERSON_COLUMN_DIALOG,dialog);
        db.insert(PERSON_TABLE_NAME, null, contentValues);

        return true;
    }
    public Cursor getPerson(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery( "SELECT * FROM " + PERSON_TABLE_NAME + " WHERE " +
                PERSON_COLUMN_ID + "=?", new String[] { Integer.toString(id) } );
        return res;
    }
    public Cursor getAllPersons() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery( "SELECT * FROM " + PERSON_TABLE_NAME, null );
        return res;
    }
    public Integer deletePerson(Integer id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(PERSON_TABLE_NAME,
                PERSON_COLUMN_ID + " = ? ",
                new String[] { Integer.toString(id) });
    }

    public void deleteAll(){
        SQLiteDatabase db = this.getReadableDatabase();
        db.delete(PERSON_TABLE_NAME,null,null);
    }
}