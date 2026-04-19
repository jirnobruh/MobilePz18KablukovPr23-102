package com.example.pr18kablukovpr23_102;

import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Task5Activity extends AppCompatActivity {

    private static final int CM_DELETE_ID = 1;

    final String ATTR_NAME_TEXT = "text";
    final String ATTR_NAME_IMAGE = "image";

    ListView lvSimple;
    SimpleAdapter sAdapter;
    ArrayList<Map<String, Object>> data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task5);

        // Начальные данные
        data = new ArrayList<>();
        for (int i = 1; i <= 3; i++) {
            Map<String, Object> m = new HashMap<>();
            m.put(ATTR_NAME_TEXT, "Запись " + i);
            m.put(ATTR_NAME_IMAGE, android.R.drawable.ic_menu_agenda);
            data.add(m);
        }

        String[] from = {ATTR_NAME_TEXT, ATTR_NAME_IMAGE};
        int[] to = {R.id.tvText, R.id.ivImg};

        sAdapter = new SimpleAdapter(this, data, R.layout.item, from, to);
        lvSimple = findViewById(R.id.lvSimple);
        lvSimple.setAdapter(sAdapter);

        registerForContextMenu(lvSimple);

        findViewById(R.id.btnAdd).setOnClickListener(v -> {
            Map<String, Object> m = new HashMap<>();
            m.put(ATTR_NAME_TEXT, "Новая запись " + (data.size() + 1));
            m.put(ATTR_NAME_IMAGE, android.R.drawable.ic_input_add);
            data.add(m);
            sAdapter.notifyDataSetChanged();
            Toast.makeText(this, "Добавлено", Toast.LENGTH_SHORT).show();
        });

        findViewById(R.id.btnBack).setOnClickListener(v -> finish());
        findViewById(R.id.btnNext).setOnClickListener(v -> {
            Intent intent = new Intent(this, Task6Activity.class);
            startActivity(intent);
        });
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.add(0, CM_DELETE_ID, 0, "Удалить запись");
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == CM_DELETE_ID) {
            AdapterView.AdapterContextMenuInfo acmi = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
            data.remove(acmi.position);
            sAdapter.notifyDataSetChanged();
            Toast.makeText(this, "Удалено", Toast.LENGTH_SHORT).show();
            return true;
        }
        return super.onContextItemSelected(item);
    }
}