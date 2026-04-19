package com.example.pr18kablukovpr23_102;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class Task8Activity extends AppCompatActivity {

    ArrayList<Product> products = new ArrayList<Product>();
    BoxAdapter boxAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task8);

        // создаем данные
        fillData();

        // создаем адаптер
        boxAdapter = new BoxAdapter(this, products);

        // настраиваем список
        ListView lvMain = findViewById(R.id.lvMain);
        lvMain.setAdapter(boxAdapter);

        // кнопка Назад
        findViewById(R.id.btnBack).setOnClickListener(v -> finish());
        
        // кнопка Завершить
        findViewById(R.id.btnFinish).setOnClickListener(v -> 
            Toast.makeText(this, "Ну допустим гав.", Toast.LENGTH_LONG).show()
        );

        // кнопка Показать выбранное
        findViewById(R.id.btnShowResult).setOnClickListener(v -> {
            String result = "Товары в корзине:";
            for (Product p : boxAdapter.getBox()) {
                if (p.box)
                    result += "\n" + p.name;
            }
            Toast.makeText(this, result, Toast.LENGTH_LONG).show();
        });
    }

    // генерируем данные для списка
    void fillData() {
        for (int i = 1; i <= 20; i++) {
            products.add(new Product("Product " + i, i * 100,
                    android.R.drawable.ic_menu_report_image, false));
        }
    }

    class BoxAdapter extends BaseAdapter {
        Context ctx;
        LayoutInflater lInflater;
        ArrayList<Product> objects;

        BoxAdapter(Context context, ArrayList<Product> products) {
            ctx = context;
            objects = products;
            lInflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        // кол-во элементов
        @Override
        public int getCount() {
            return objects.size();
        }

        // элемент по позиции
        @Override
        public Object getItem(int position) {
            return objects.get(position);
        }

        // id по позиции
        @Override
        public long getItemId(int position) {
            return position;
        }

        // пункт списка
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // используем созданные, но не используемые view
            View view = convertView;
            if (view == null) {
                view = lInflater.inflate(R.layout.item_task8, parent, false);
            }

            Product p = getProduct(position);

            // заполняем View из объекта
            ((TextView) view.findViewById(R.id.tvName)).setText(p.name);
            ((TextView) view.findViewById(R.id.tvPrice)).setText(p.price + "");
            ((ImageView) view.findViewById(R.id.ivImage)).setImageResource(p.image);

            CheckBox cbBuy = view.findViewById(R.id.cbBox);
            // присваиваем чекбоксу обработчик
            cbBuy.setOnCheckedChangeListener(myCheckChangeList);
            // пишем позицию
            cbBuy.setTag(position);
            // заполняем данными из объекта: в корзине или нет
            cbBuy.setChecked(p.box);

            return view;
        }

        // товар по позиции
        Product getProduct(int position) {
            return ((Product) getItem(position));
        }

        // содержимое корзины
        ArrayList<Product> getBox() {
            ArrayList<Product> box = new ArrayList<Product>();
            for (Product p : objects) {
                if (p.box)
                    box.add(p);
            }
            return box;
        }

        // обработчик для чекбоксов
        CompoundButton.OnCheckedChangeListener myCheckChangeList = new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // меняем данные товара (в корзине или нет)
                getProduct((Integer) buttonView.getTag()).box = isChecked;
            }
        };
    }
}