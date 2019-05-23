package com.example.luuanhkiet_1706020042;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.luuanhkiet_1706020042.models.Product;
import com.example.luuanhkiet_1706020042.models.Subject;
import com.google.firebase.database.DatabaseReference;

public class DetailActivity extends AppCompatActivity {
    EditText custom_txt_id;
    EditText txt_des;
    TextView price_credits;
    EditText txt_price_credits;
    TextView name;
    EditText txt_name;
    TextView code_producer;
    EditText txt_code_producer;

    Button btnEdit;
    Button btnSave;
    Subject subject;
    Product product;
    DatabaseReference main;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        init();
        main = MainActivity.myChilRef;
        Intent intent = getIntent();
        switch (intent.getStringExtra("type")) {
            case "subject":
                subject = (Subject) intent.getSerializableExtra("data");
                code_producer.setText("subject code:");
                name.setText("subject name:");
                price_credits.setText("credits:");

                txt_code_producer.setText(subject.getSubject_code());
                txt_price_credits.setText(String.valueOf(subject.getCredits()));
                txt_des.setText(subject.getDescription());
                custom_txt_id.setText(String.valueOf(subject.getId()));
                txt_name.setText(subject.getSubject_name());
                break;
            case "product":
                product = (Product) intent.getSerializableExtra("data");
                code_producer.setText("Product:");
                name.setText("product name:");
                price_credits.setText("price:");

                txt_code_producer.setText(product.getProducer());
                txt_price_credits.setText(String.valueOf(product.getPrice()));
                txt_des.setText(product.getDescription());
                custom_txt_id.setText(String.valueOf(product.getId()));
                txt_name.setText(product.getProduct_name());
                break;
        }

        //sự kiện nút edit
        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onEdit();
            }
        });

        //sự kiên nút save
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSave();
                if (subject != null) {
                    subject.setSubject_name(txt_name.getText().toString());
                    subject.setSubject_code(txt_code_producer.getText().toString());
                    subject.setDescription(txt_des.getText().toString());
                    subject.setCredits(Integer.parseInt(txt_price_credits.getText().toString()));
                    DatabaseReference chil = main.child(subject.getKeyParent());
                    chil.setValue(subject);
                } else {
                    product.setProduct_name(txt_name.getText().toString());
                    product.setProducer(txt_code_producer.getText().toString());
                    product.setDescription(txt_des.getText().toString());
                    product.setPrice(Integer.parseInt(txt_price_credits.getText().toString()));
                    DatabaseReference chil = main.child(product.getKeyParent());
                    chil.setValue(product);
                }
            }
        });
    }

    void init() {
        custom_txt_id = findViewById(R.id.detail_edit_id);
        txt_name = findViewById(R.id.detail_edit_name);
        name = findViewById(R.id.detail_name);
        price_credits = findViewById(R.id.detail_price_credits);
        txt_des = findViewById(R.id.detail_edit_des);
        txt_price_credits = findViewById(R.id.detail_edit_price_credits);
        code_producer = findViewById(R.id.detail_producer_code);
        txt_code_producer = findViewById(R.id.detail_edit_producer_code);

        btnEdit = findViewById(R.id.detail_btn_edit);
        btnSave = findViewById(R.id.detail_btn_save);
    }

    void onEdit() {
        btnSave.setEnabled(true);
        btnEdit.setEnabled(false);

        txt_name.setEnabled(true);
        txt_des.setEnabled(true);
        txt_price_credits.setEnabled(true);
        txt_code_producer.setEnabled(true);
    }

    void onSave() {
        btnEdit.setEnabled(true);
        btnSave.setEnabled(false);

        txt_name.setEnabled(false);
        txt_des.setEnabled(false);
        txt_price_credits.setEnabled(false);
        txt_code_producer.setEnabled(false);
    }
}
