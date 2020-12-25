package com.example.kuou;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

public class MainActivity_note extends AppCompatActivity implements View.OnClickListener {
   private Button bt_add;
   private ListView lv;
   private Intent i;
    private NotesDB notesDB;
    private SQLiteDatabase dbReader;
    private Cursor cursor;
    private MyAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_note);
        initView();
    }
     public void initView(){
        lv = findViewById(R.id.list);
        bt_add = findViewById(R.id.bt_add);
        bt_add.setOnClickListener(this);
        notesDB = new NotesDB(this);
        dbReader = notesDB.getReadableDatabase();
         lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

             @Override
             public void onItemClick(AdapterView<?> parent, View view,
                                     int position, long id) {
                 cursor.moveToPosition(position);
                 Intent i = new Intent(MainActivity_note.this, SelectActivity.class);
                 i.putExtra(NotesDB.ID,
                         cursor.getInt(cursor.getColumnIndex(NotesDB.ID)));
                 i.putExtra(NotesDB.CONTENT, cursor.getString(cursor
                         .getColumnIndex(NotesDB.CONTENT)));
                 i.putExtra(NotesDB.TIME,
                         cursor.getString(cursor.getColumnIndex(NotesDB.TIME)));
                 i.putExtra(NotesDB.PATH,
                         cursor.getString(cursor.getColumnIndex(NotesDB.PATH)));
                 i.putExtra(NotesDB.VIDEO,
                         cursor.getString(cursor.getColumnIndex(NotesDB.VIDEO)));
                 startActivity(i);
             }
         });
     }
    @Override
    public void onClick(View v) {
        i = new Intent(this,AddActivity.class);
        switch (v.getId()){
            case R.id.bt_add:
                i.putExtra("flag",1);
                startActivity(i);
                break;
        }
    }
    public void selectDB() {
        cursor = dbReader.query(NotesDB.TABLE_NAME, null, null, null, null,
                null, null);
        adapter = new MyAdapter(this, cursor);
        lv.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        selectDB();
    }


}