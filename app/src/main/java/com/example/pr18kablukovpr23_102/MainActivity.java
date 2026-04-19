package com.example.pr18kablukovpr23_102;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    // Имена ключей для Map (как в уроке 48)
    final String ATTRIBUTE_NAME_TEXT = "text";
    final String ATTRIBUTE_NAME_CHECKED = "checked";
    final String ATTRIBUTE_NAME_IMAGE = "image";

    ListView lvSimple;
    Button btnNext;
    Button btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Инициализация компонентов UI
        lvSimple = findViewById(R.id.lvSimple);
        btnNext = findViewById(R.id.btnNext);
        btnBack = findViewById(R.id.btnBack);

        // Продублируем отключение кнопки "Назад" в коде для надежности,
        // хотя ты уже отлично сделал это через XML (android:enabled="false")
        btnBack.setEnabled(false);

        // Подготавливаем массивы данных
        String[] texts = {"Первый пункт", "Второй пункт", "Третий пункт", "Четвертый пункт", "Пятый пункт"};
        boolean[] checked = {true, false, false, true, false};
        int img = R.mipmap.ic_launcher; // Стандартная иконка Android Studio

        // Упаковываем данные в структуру, понятную для SimpleAdapter
        ArrayList<Map<String, Object>> data = new ArrayList<>(texts.length);
        Map<String, Object> m;
        for (int i = 0; i < texts.length; i++) {
            m = new HashMap<>();
            m.put(ATTRIBUTE_NAME_TEXT, texts[i]);
            m.put(ATTRIBUTE_NAME_CHECKED, checked[i]);
            m.put(ATTRIBUTE_NAME_IMAGE, img);
            data.add(m);
        }

        // Массив from содержит имена Map-ключей
        String[] from = {ATTRIBUTE_NAME_TEXT, ATTRIBUTE_NAME_CHECKED, ATTRIBUTE_NAME_IMAGE};
        // Массив to – ID View-компонентов из файла item.xml
        int[] to = {R.id.tvText, R.id.cbChecked, R.id.ivImg};

        // Создаем адаптер.
        // ВАЖНО: Здесь используется R.layout.item, который мы размечали ранее!
        SimpleAdapter sAdapter = new SimpleAdapter(this, data, R.layout.item, from, to);

        // Привязываем адаптер к ListView
        lvSimple.setAdapter(sAdapter);

        // Обработчик нажатия на кнопку "Далее"
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Переход к Заданию 3
                // Убедись, что ты создал Task3Activity через File -> New -> Activity -> Empty Views Activity
                Intent intent = new Intent(MainActivity.this, Task3Activity.class);
                startActivity(intent);
            }
        });
    }
}