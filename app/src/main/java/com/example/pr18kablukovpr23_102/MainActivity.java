package com.example.pr18kablukovpr23_102;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    // Имена атрибутов для ключей словаря (Map)
    final String ATTRIBUTE_NAME_TEXT = "text";
    final String ATTRIBUTE_NAME_CHECKED = "checked";
    final String ATTRIBUTE_NAME_IMAGE = "image";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 1. Подготавливаем сырые данные (массивы)
        String[] texts = {"Изучить Java", "Настроить Android Studio", "Создать адаптер", "Выпить кофе"};
        boolean[] checked = {true, true, false, false};
        // Используем стандартную системную иконку Android для простоты
        int img = android.R.drawable.ic_dialog_info;

        // 2. Упаковываем данные в структуру ArrayList<Map<String, Object>>, которую понимает SimpleAdapter
        ArrayList<Map<String, Object>> data = new ArrayList<>(texts.length);
        Map<String, Object> map;

        for (int i = 0; i < texts.length; i++) {
            map = new HashMap<>();
            map.put(ATTRIBUTE_NAME_TEXT, texts[i]);
            map.put(ATTRIBUTE_NAME_CHECKED, checked[i]);
            map.put(ATTRIBUTE_NAME_IMAGE, img);
            data.add(map);
        }

        // 3. Создаем массивы для сопоставления данных
        // from - имена ключей из нашего Map
        String[] from = { ATTRIBUTE_NAME_TEXT, ATTRIBUTE_NAME_CHECKED, ATTRIBUTE_NAME_IMAGE };
        // to - ID компонентов в файле item.xml, куда эти данные нужно вставить
        int[] to = { R.id.tvText, R.id.cbChecked, R.id.ivImg };

        // 4. Создаем сам адаптер (передаем контекст, данные, файл разметки элемента и массивы сопоставления)
        SimpleAdapter sAdapter = new SimpleAdapter(this, data, R.layout.item, from, to);

        // 5. Находим наш ListView на главном экране и применяем к нему адаптер
        ListView lvSimple = findViewById(R.id.lvSimple);
        lvSimple.setAdapter(sAdapter);
    }
}