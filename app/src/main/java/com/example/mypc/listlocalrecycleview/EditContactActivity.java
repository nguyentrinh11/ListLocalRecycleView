package com.example.mypc.listlocalrecycleview;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.mypc.listlocalrecycleview.Aapter.DanhSachAdapter;
import com.example.mypc.listlocalrecycleview.Model.Item;
import com.example.mypc.listlocalrecycleview.Model.Snippet;

public class EditContactActivity extends AppCompatActivity implements View.OnClickListener {
    public ImageView imgBack;
    public EditText edtTitle, edtDescription;
    public Button btnCancelEdit, btnOKEdit;
    public DanhSachAdapter adapter;
    public Integer position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_contact);
        imgBack = (ImageView) findViewById(R.id.imgBack);
        edtTitle = (EditText) findViewById(R.id.edtTitle_edit);
        edtDescription = (EditText) findViewById(R.id.edtDescription_edit);
        btnOKEdit = (Button) findViewById(R.id.btnOK_edit);
        btnCancelEdit = (Button) findViewById(R.id.btnCancel_edit);
        btnCancelEdit.setOnClickListener(this);
        btnOKEdit.setOnClickListener(this);
        imgBack.setOnClickListener(this);
        Intent intent = getIntent();
        position = intent.getIntExtra("position", 0);
        Toast.makeText(EditContactActivity.this, Integer.toString(position), Toast.LENGTH_SHORT).show();
        String title = intent.getStringExtra("title");
        String description = intent.getStringExtra("description");
        edtTitle.setText(title);
        edtDescription.setText(description);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imgBack:
                this.finish();
                break;
            case R.id.btnCancel_edit:
                this.finish();
                break;
            case R.id.btnOK_edit:
                String title = String.valueOf(edtTitle.getText());
                String description = String.valueOf(edtDescription.getText());
                Intent i = getIntent();
                i.putExtra("position", position);
                i.putExtra("title1", title);
                i.putExtra("description1", description);
                setResult(MainActivity.RESULT_OK, i);
                finish();
//                Item item = new Item();
//                if(adapter.getItem(position)==null){
//                    Log.d("error","adapter is null");
//                }else {
//                    item = adapter.getItem(position);
//                    Log.d("error","adapter not null");
//                    item.getSnippet().setTitle("2");
//                    item.getSnippet().setDescription("3");
//                    adapter.notifyDataSetChanged();
//                    this.finish();
//                }


                break;
            default:
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}
