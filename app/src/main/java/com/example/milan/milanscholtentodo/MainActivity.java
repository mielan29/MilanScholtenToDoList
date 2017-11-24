package com.example.milan.milanscholtentodo;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    public Button add;
    public EditText input;
    private TodoDatabase db;
    private TodoAdapter adapter;
    public ListView Items;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        db = TodoDatabase.getInstance(getApplicationContext());


        Context context = getApplicationContext();
        Cursor cursor = db.selectAll();

        //find ListView
        Items = findViewById(R.id.TodoList);

        //
        adapter = new TodoAdapter(context, cursor);

        //
        Items.setAdapter(adapter);

        Items.setOnItemClickListener(Listener);
        Items.setOnItemLongClickListener(LongListener);

        input = findViewById(R.id.TodoItem);

        add = findViewById(R.id.add);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newItem = input.getText().toString();
                if(input.length() != 0) {
                    db.insert(newItem);
                    input.setText("");
                    updateData();
                }
                else {
                    Log.d("geen input", "GEEF ME INPUT");
                }
            }
        });
    }

    private void updateData(){
        Cursor cursor = db.selectAll();
        adapter.swapCursor(cursor);
        ListView Items = findViewById(R.id.TodoList);
        Items.setAdapter(adapter);
        input = findViewById(R.id.TodoItem);
    }

    private final AdapterView.OnItemClickListener Listener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
            Cursor selectedItem = (Cursor)adapterView.getItemAtPosition(position);
            int id = selectedItem.getInt(0);
            int oldValue = selectedItem.getInt(2);
            String oldString = selectedItem.getString(1);
            if (oldValue == 0) {
                oldValue = 1;
                db.update(id, oldValue);

                updateData();
            }
            else {
                oldValue = 0;
                db.update(id, oldValue);
                updateData();
            }
            Log.d("Click", oldString);
            Log.d("Click", Integer.toString(oldValue));
        }
    };

    private final AdapterView.OnItemLongClickListener LongListener  = new AdapterView.OnItemLongClickListener(){
        @Override
        public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long l) {
            Cursor LongItemClick = (Cursor) adapterView.getItemAtPosition(position);
            int id = LongItemClick.getInt(0);

            db.delete(id);
            updateData();
            return true;
        }
    };
}
