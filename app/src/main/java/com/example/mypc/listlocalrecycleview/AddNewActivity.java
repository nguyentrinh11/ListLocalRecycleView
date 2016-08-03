package com.example.mypc.listlocalrecycleview;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

public class AddNewActivity extends AppCompatActivity implements View.OnClickListener {
    public ImageView imgBack;
    public EditText edtTitle, edtDescription;
    public Button btnCancel, btnOk;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new);
        imgBack = (ImageView) findViewById(R.id.imgBack_add);
        btnCancel = (Button) findViewById(R.id.btnCancel);
        btnOk = (Button) findViewById(R.id.btnOK);
        btnOk.setOnClickListener(this);
        btnCancel.setOnClickListener(this);
        edtTitle = (EditText) findViewById(R.id.edtTitle);
        edtDescription = (EditText) findViewById(R.id.edtDescription);
        imgBack.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imgBack_add:
                this.finish();
                break;
            case R.id.btnOK:
                String title = String.valueOf(edtTitle.getText());
                String description = String.valueOf(edtDescription.getText());
                Log.d("nguyentrinh1: ",title+"");
                Log.d("nguyentrinh2: ",description+"");
                Intent i = getIntent();
                i.putExtra("title_add", title);
                i.putExtra("description_add", description);
                setResult(MainActivity.RESULT_OK, i);
                finish();
                break;
            default:
        }
    }
}
