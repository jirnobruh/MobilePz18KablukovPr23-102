package com.example.pr18kablukovpr23_102;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Task3Activity extends AppCompatActivity {

    final String ATTR_NAME_TEXT = "text";
    final String ATTR_NAME_VALUE = "value";
    final String ATTR_NAME_IMAGE = "image";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task3);

        // Данные: название, "цена" (value) и картинка
        String[] names = {"Яблоки", "Апельсины", "Бананы", "Груши", "Лимоны"};
        int[] values = {100, -20, 50, -5, 0};
        int[] images = {
                android.R.drawable.ic_menu_edit,
                android.R.drawable.ic_menu_delete,
                android.R.drawable.ic_menu_add,
                android.R.drawable.ic_menu_close_clear_cancel,
                android.R.drawable.ic_menu_info_details
        };

        ArrayList<Map<String, Object>> data = new ArrayList<>(names.length);
        for (int i = 0; i < names.length; i++) {
            Map<String, Object> map = new HashMap<>();
            map.put(ATTR_NAME_TEXT, names[i]);
            map.put(ATTR_NAME_VALUE, values[i]);
            map.put(ATTR_NAME_IMAGE, images[i]);
            data.add(map);
        }

        // Мы связываем данные с View. 
        // Обрати внимание: мы свяжем и текст, и значение с одним и тем же TextView (R.id.tvText),
        // но в переопределенном методе setViewText решим, как это отобразить.
        String[] from = {ATTR_NAME_TEXT, ATTR_NAME_VALUE, ATTR_NAME_IMAGE};
        int[] to = {R.id.tvText, R.id.tvText, R.id.ivImg};

        MySimpleAdapter sAdapter = new MySimpleAdapter(this, data, R.layout.item, from, to);
        ListView lvSimple = findViewById(R.id.lvSimple);
        lvSimple.setAdapter(sAdapter);

        findViewById(R.id.btnBack).setOnClickListener(v -> finish());
        findViewById(R.id.btnNext).setOnClickListener(v -> 
                Toast.makeText(this, "Вы выполнили все задания!", Toast.LENGTH_SHORT).show()
        );
    }

    class MySimpleAdapter extends SimpleAdapter {
        public MySimpleAdapter(Context context, List<? extends Map<String, ?>> data, int resource, String[] from, int[] to) {
            super(context, data, resource, from, to);
        }

        @Override
        public void setViewText(TextView v, String text) {
            super.setViewText(v, text);
            
            // Если это TextView для основного текста
            if (v.getId() == R.id.tvText) {
                try {
                    // Пытаемся понять, число это или текст
                    int val = Integer.parseInt(text);
                    if (val < 0) {
                        v.setTextColor(Color.RED); // Отрицательные значения красным
                    } else if (val > 0) {
                        v.setTextColor(Color.GREEN); // Положительные зеленым
                    } else {
                        v.setTextColor(Color.GRAY); // Ноль серым
                    }
                    // Добавим префикс для наглядности
                    v.setText("Цена: " + text);
                } catch (NumberFormatException e) {
                    // Если это не число (т.е. название фрукта), сбрасываем цвет в стандартный
                    v.setTextColor(Color.BLACK);
                }
            }
        }

        @Override
        public void setViewImage(ImageView v, String value) {
            super.setViewImage(v, value);
            // Логика из урока: если иконка "delete", меняем её прозрачность
            if (value.equals(String.valueOf(android.R.drawable.ic_menu_delete))) {
                v.setAlpha(0.5f);
            } else {
                v.setAlpha(1.0f);
            }
        }
    }
}