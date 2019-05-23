package com.example.luuanhkiet_1706020042;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.luuanhkiet_1706020042.models.Product;
import com.example.luuanhkiet_1706020042.models.Subject;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Random;

public class AddActivity extends AppCompatActivity {
    TextView txt_name;
    TextView txt_price_credits;
    TextView txt_code_producer;

    EditText edit_name;
    EditText edit_price_credits;
    EditText edit_code_producer;
    EditText edit_des;

    Button btn_ok;
    Button btn_cancel;

    String type = "";
    DatabaseReference child;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        init();
        //phần lấy loại dữ liệu
        DatabaseReference typeRef = FirebaseDatabase.getInstance().getReference("user");
        DatabaseReference childTypeRef = typeRef.child(LoginActivity.IDgg);
        childTypeRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                type = (String) dataSnapshot.getValue();
                DatabaseReference parenRef = FirebaseDatabase.getInstance().getReference(LoginActivity.IDgg).child("AdvancedAndroidFinalTest");
                child = parenRef.push();
//        intent.getStringExtra("type")
                switch (type) {
                    case "subject":
                        txt_code_producer.setText("subject code:");
                        txt_name.setText("subject name:");
                        txt_price_credits.setText("credits:");
                        final Subject item = new Subject();

                        btn_ok.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (Valuable()) {
                                    item.setCredits(Integer.parseInt(edit_price_credits.getText().toString()));
                                    item.setDescription(edit_des.getText().toString());
                                    item.setSubject_code(edit_code_producer.getText().toString());
                                    item.setSubject_name(edit_name.getText().toString());
                                    while (true) {
                                        Random random = new Random();
                                        int number = random.nextInt();
                                        boolean Exit = false;
                                        for (Subject item : MainActivity.subjectList) {
                                            if (item.getId() != number) {
                                                Exit = true;
                                                break;
                                            }
                                        }
                                        if (MainActivity.subjectList.size() <1){
                                            Exit = true;
                                        }
                                        if (Exit) {
                                            item.setId(number);
                                            break;
                                        }
                                    }
                                    child.setValue(item);
                                    finish();
                                }
                            }
                        });
                        break;
                    case "product":
                        txt_code_producer.setText("Product:");
                        txt_name.setText("product name:");
                        txt_price_credits.setText("price:");

                        final Product product = new Product();
                        btn_ok.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (Valuable()) {
                                    product.setDescription(edit_des.getText().toString());
                                    product.setPrice(Integer.parseInt(edit_price_credits.getText().toString()));
                                    product.setProducer(edit_code_producer.getText().toString());
                                    product.setProduct_name(edit_name.getText().toString());
                                    while (true) {
                                        Random random = new Random();
                                        int number = random.nextInt();
                                        boolean Exit = false;
                                        for (Product itemProduct : MainActivity.productList) {
                                            if (itemProduct.getId() != number) {
                                                Exit = true;
                                                break;
                                            }
                                        }

                                        if (MainActivity.subjectList.size() <1){
                                            Exit = true;
                                        }
                                        if (Exit) {
                                            product.setId(number);
                                            break;
                                        }
                                    }
                                    child.setValue(product);
                                    finish();
                                }
                            }
                        });
                        break;
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        //end
        //===============
        Intent intent = getIntent();

        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    void init() {
        txt_name = findViewById(R.id.add_name);
        txt_code_producer = findViewById(R.id.add_producer_code);
        txt_price_credits = findViewById(R.id.add_price_credits);

        edit_name = findViewById(R.id.add_edit_name);
        edit_code_producer = findViewById(R.id.add_edit_producer_code);
        edit_price_credits = findViewById(R.id.add_edit_price_credits);
        edit_des = findViewById(R.id.add_edit_des);

        btn_ok = findViewById(R.id.add_btn_ok);
        btn_cancel = findViewById(R.id.add_btn_cancel);
    }

    boolean Valuable() {
        if (edit_name.getText().toString().trim().equals("")) {
            edit_name.setError("Null");
            return false;
        } else if (edit_price_credits.getText().toString().trim().equals("")) {
            edit_price_credits.setError("Null");
            return false;
        } else if (txt_code_producer.getText().toString().trim().equals("")) {
            edit_code_producer.setError("Null");
            return false;
        } else if (edit_des.getText().toString().trim().equals("")) {
            edit_des.setError("Null");
            return false;
        }
        return true;
    }
}
