package com.example.luuanhkiet_1706020042;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import com.example.luuanhkiet_1706020042.models.Product;
import com.example.luuanhkiet_1706020042.models.Subject;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    RycAdapter adapter;
    static List<Subject> subjectList = new ArrayList<>();
    static List<Product> productList = new ArrayList<>();
    static DatabaseReference myChilRef;

    Button btn_add;
    boolean isProduct = true;

    ProgressBar loadingData;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        startService(new Intent(getBaseContext(), CleanOnkillApp.class));

        init();
        //phần lưu thong tin tai khoản
        DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("user");
        final DatabaseReference childUserRef = userRef.child(LoginActivity.IDgg);
        childUserRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Object data = dataSnapshot.getValue();
                if (data ==null){
                    if (isProduct){
                        childUserRef.setValue("product");
                    }else{
                        childUserRef.setValue("subject");
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        //end
        //==========================
        FirebaseDatabase myFire = FirebaseDatabase.getInstance();
        DatabaseReference myRef = myFire.getReference().child(LoginActivity.IDgg);
        myChilRef = myRef.child("AdvancedAndroidFinalTest");
        myChilRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                subjectList.clear();
                productList.clear();
                for (DataSnapshot item : dataSnapshot.getChildren()) {
                    loadingData.setVisibility(View.VISIBLE);
                    try
                    {
                        Subject subject = item.getValue(Subject.class);
                        Product product = item.getValue(Product.class);
                        String key = item.getKey();
                        assert subject != null;
                        if (subject.getSubject_code() != null) {
                            boolean dont = true;
                            isProduct = false;
                            for (Subject chil : subjectList) {
                                if (chil.getId() == subject.getId()) {
                                    dont = false;
                                    break;
                                }
                            }
                            if (dont) {
                                subject.setKeyParent(key);
                                subjectList.add(subject);
                            }
                        }
                        assert product != null;
                        if (product.getProduct_name() != null) {
                            boolean dont = true;
                            for (Product chil : productList) {
                                if (chil.getId() == product.getId()) {
                                    dont = false;
                                    break;
                                }
                            }
                            if (dont) {
                                product.setKeyParent(key);
                                productList.add(product);
                            }
                        }
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
                adapter.notifyDataSetChanged();
                loadingData.setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddActivity.class);
                if (isProduct){
                    intent.putExtra("type","product");
                }else
                {
                    intent.putExtra("type","subject");
                }
                startActivity(intent);
            }
        });
        adapter = new RycAdapter(subjectList, productList, MainActivity.this, R.layout.ryc_customlayout);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(adapter);
    }

    void init() {
        recyclerView = findViewById(R.id.list);
        btn_add = findViewById(R.id.main_btnAdd);
        loadingData = findViewById(R.id.indeterminateBar);
    }
}
