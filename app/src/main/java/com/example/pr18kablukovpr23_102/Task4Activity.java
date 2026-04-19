package com.example.pr18kablukovpr23_102;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Task4Activity extends AppCompatActivity {

    final String ATTR_NAME_TEXT = "text";
    final String ATTR_NAME_PROGRESS = "progress";
    final String ATTR_NAME_IMAGE = "image";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task4);

        String[] names = {"Загрузка системы", "Обновление ПО", "Синхронизация", "Очистка кэша"};
        int[] progress = {75, 40, 90, 20};
        int[] images = {
                android.R.drawable.ic_menu_rotate,
                android.R.drawable.ic_menu_save,
                android.R.drawable.ic_menu_share,
                android.R.drawable.ic_menu_delete
        };

        ArrayList<Map<String, Object>> data = new ArrayList<>(names.length);
        for (int i = 0; i < names.length; i++) {
            Map<String, Object> map = new HashMap<>();
            map.put(ATTR_NAME_TEXT, names[i]);
            map.put(ATTR_NAME_PROGRESS, progress[i]);
            map.put(ATTR_NAME_IMAGE, images[i]);
            data.add(map);
        }

        String[] from = {ATTR_NAME_TEXT, ATTR_NAME_PROGRESS, ATTR_NAME_IMAGE};
        int[] to = {R.id.tvText, R.id.pbValue, R.id.ivImg};

        SimpleAdapter sAdapter = new SimpleAdapter(this, data, R.layout.item_task4, from, to);
        sAdapter.setViewBinder(new MyViewBinder());

        ListView lvSimple = findViewById(R.id.lvSimple);
        lvSimple.setAdapter(sAdapter);

        findViewById(R.id.btnBack).setOnClickListener(v -> finish());
        findViewById(R.id.btnNext).setOnClickListener(v -> {
            Intent intent = new Intent(this, Task5Activity.class);
            startActivity(intent);
        });
    }

    class MyViewBinder implements SimpleAdapter.ViewBinder {
        @Override
        public boolean setViewValue(View view, Object data, String textRepresentation) {
            int i = view.getId();
            if (i == R.id.pbValue) {
                int progress = (int) data;
                ((ProgressBar) view).setProgress(progress);
                return true;
            }
            if (i == R.id.ivImg) {
                ((ImageView) view).setImageResource((int) data);
                if ((int)data == android.R.drawable.ic_menu_delete) {
                    ((ImageView) view).setColorFilter(Color.RED);
                } else {
                    ((ImageView) view).clearColorFilter();
                }
                return true;
            }
            return false;
        }
    }
}