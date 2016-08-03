package com.example.mypc.listlocalrecycleview;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.mypc.listlocalrecycleview.APIInterface.APIInterface;
import com.example.mypc.listlocalrecycleview.Aapter.DanhSachAdapter;
import com.example.mypc.listlocalrecycleview.Config.UtilsRest;
import com.example.mypc.listlocalrecycleview.Model.Contact;
import com.example.mypc.listlocalrecycleview.Model.Default;
import com.example.mypc.listlocalrecycleview.Model.Item;
import com.example.mypc.listlocalrecycleview.Model.Snippet;
import com.example.mypc.listlocalrecycleview.Model.Thumbnails;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements DanhSachAdapter.ICallAdaterToAtv {
    public RecyclerView recyclerView;
    public List<Item> mList, mListLocal;
    public DanhSachAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mList = new ArrayList<>();
        mListLocal = new ArrayList<>();
        recyclerView = (RecyclerView) findViewById(R.id.rcList);
        adapter = new DanhSachAdapter(MainActivity.this, mList, this);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecration(this, LinearLayoutManager.VERTICAL));
        recyclerView.setAdapter(adapter);
        setData();
        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(this, recyclerView, new ClickListener() {
            @Override
            public void onClick(View view, int position) {

            }

            @Override
            public void onLongClick(View view, int position) {

            }

        }));

    }

    private void setData() {
        APIInterface apiInterface = UtilsRest.getInterfaceService();
        Call<Contact> call = apiInterface.getList();
        call.enqueue(new Callback<Contact>() {
            @Override
            public void onResponse(Call<Contact> call, Response<Contact> response) {
                Log.d("Code", response.code() + " ");
                if (response.isSuccessful()) {
                    int size = response.body().getItems().size();
                    for (int i = 0; i < size; i++) {
                        mList.add(response.body().getItems().get(i));
                        mListLocal.add(response.body().getItems().get(i));

                    }
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<Contact> call, Throwable t) {

            }
        });
    }

    @Override
    public void callAdapterToAtv(int pos, String name, String des) {
        Log.d("TAG:", "check pos " + pos);
        Log.d("TAG:", "check title " + name);
        Log.d("TAG:", "check des " + des);
        Intent intent = new Intent(this, EditContactActivity.class);
        intent.putExtra("position", pos);
        intent.putExtra("title", name);
        intent.putExtra("description", des);
        startActivityForResult(intent, 1);

    }


    @Override
    public void selectItem(View view, int pos) {
        Log.e("nskjbgjksbg", "" + pos);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Integer position = null;
        String title = null, description = null, title_add = null, description_add = null;
        if (resultCode == RESULT_OK) {
            if (requestCode == 1) {
                if (data.getExtras().containsKey("position")) {
                    position = data.getIntExtra("position", 0);
                    Log.d("pos1", position + "");
                }
                if (data.getExtras().containsKey("title1")) {
                    title = data.getStringExtra("title1");
                    Log.d("tile1", title);
                }
                if (data.getExtras().containsKey("description1")) {
                    description = data.getStringExtra("description1");
                    Log.d("des1", description);
                }
                Item item = adapter.getItem(position);
                item.getSnippet().setTitle(title);
                item.getSnippet().setDescription(description);
                adapter.notifyDataSetChanged();
            }
            if (requestCode == 2) {
                if (data.getExtras().containsKey("title_add")) {
                    title_add = data.getStringExtra("title_add");
                }
                if (data.getExtras().containsKey("description_add")) {
                    description_add = data.getStringExtra("description_add");
                }
                Log.d("nguyentrinh: ", title_add + "");
                Log.d("emnho: ", description_add + "");
                Item item = new Item();
                Snippet snippet = new Snippet();
                snippet.setTitle(title_add);
                snippet.setDescription(description_add);
                Thumbnails thumbnails = new Thumbnails();
                Default de = new Default();
                de.setUrl("https://i.ytimg.com/vi/VOUp3ZZ9t3A/default.jpg");
                thumbnails.setDefault(de);
                snippet.setThumbnails(thumbnails);
                item.setSnippet(snippet);
                mList.add(item);
                adapter.notifyDataSetChanged();
            }
        } else {

        }

    }

    public interface ClickListener {
        void onClick(View view, int position);

        void onLongClick(View view, int position);
    }

    public static class RecyclerTouchListener implements RecyclerView.OnItemTouchListener {

        private GestureDetector gestureDetector;
        private MainActivity.ClickListener clickListener;

        public RecyclerTouchListener(Context context, final RecyclerView recyclerView, final MainActivity.ClickListener clickListener) {
            this.clickListener = clickListener;
            gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
                @Override
                public boolean onSingleTapUp(MotionEvent e) {
                    return true;
                }

                @Override
                public void onLongPress(MotionEvent e) {
                    View child = recyclerView.findChildViewUnder(e.getX(), e.getY());
                    if (child != null && clickListener != null) {
                        clickListener.onLongClick(child, recyclerView.getChildPosition(child));
                    }
                }
            });
        }

        @Override
        public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {

            View child = rv.findChildViewUnder(e.getX(), e.getY());
            if (child != null && clickListener != null && gestureDetector.onTouchEvent(e)) {
                clickListener.onClick(child, rv.getChildPosition(child));
            }
            return false;
        }

        @Override
        public void onTouchEvent(RecyclerView rv, MotionEvent e) {
        }

        @Override
        public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_add:
//                startActivity(new Intent(MainActivity.this, AddNewActivity.class));
                Intent intent = new Intent(MainActivity.this, AddNewActivity.class);
                startActivityForResult(intent, 2);
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();

    }
}
