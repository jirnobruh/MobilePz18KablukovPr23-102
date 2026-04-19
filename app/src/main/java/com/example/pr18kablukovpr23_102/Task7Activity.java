package com.example.pr18kablukovpr23_102;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.ExpandableListView;
import android.widget.SimpleCursorTreeAdapter;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Task7Activity extends AppCompatActivity {

    ExpandableListView elvMain;
    DB db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task7);

        db = new DB(this);
        db.open();

        // получаем курсор для групп
        Cursor cursor = db.getCompanyData();
        startManagingCursor(cursor);

        // сопоставление данных и View для групп
        String[] groupFrom = { DB.COMPANY_NAME };
        int[] groupTo = { android.R.id.text1 };

        // сопоставление данных и View для элементов
        String[] childFrom = { DB.PHONE_NAME };
        int[] childTo = { android.R.id.text1 };

        // создаем адаптер и настраиваем список
        SimpleCursorTreeAdapter sctAdapter = new MyAdapter(this, cursor,
                android.R.layout.simple_expandable_list_item_1, groupFrom,
                groupTo, android.R.layout.simple_list_item_1, childFrom,
                childTo);

        elvMain = findViewById(R.id.elvMain);
        elvMain.setAdapter(sctAdapter);

        findViewById(R.id.btnBack).setOnClickListener(v -> finish());
        findViewById(R.id.btnFinish).setOnClickListener(v -> 
            Toast.makeText(this, "Поздравляем! Вы прошли все 7 заданий.", Toast.LENGTH_LONG).show()
        );
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        db.close();
    }

    class MyAdapter extends SimpleCursorTreeAdapter {

        public MyAdapter(Context context, Cursor cursor, int groupLayout,
                         String[] groupFrom, int[] groupTo, int childLayout,
                         String[] childFrom, int[] childTo) {
            super(context, cursor, groupLayout, groupFrom, groupTo,
                    childLayout, childFrom, childTo);
        }

        @Override
        protected Cursor getChildrenCursor(Cursor groupCursor) {
            // получаем id группы
            int idColumnIndex = groupCursor.getColumnIndex(DB.COMPANY_ID);
            return db.getPhoneData(groupCursor.getLong(idColumnIndex));
        }
    }
}