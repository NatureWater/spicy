package com.spicy.food.record;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.spicy.food.data.Diary;
import com.spicy.food.data.DiaryLog;
import com.spicy.food.record.widget.CollectionLIstItemTouchHelperCallback;
import com.spicy.food.record.widget.ItemTouchHelperExtension;
import com.spicy.food.record.widget.ListItemAdapter;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private TextView mEmptyTxt;
    private RecyclerView recyclerView;
    private ListItemAdapter itemAdapter;
    public ItemTouchHelperExtension mItemTouchHelper;
    public CollectionLIstItemTouchHelperCallback mCallback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, WriteActivity.class);
                startActivity(intent);
            }
        });
        mEmptyTxt = (TextView) findViewById(R.id.txt_empty);
        recyclerView = (RecyclerView) findViewById(R.id.recycle_view);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(MainActivity.this);
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(mLayoutManager);
        itemAdapter = new ListItemAdapter();

        itemAdapter.setClickItemListener(new ListItemAdapter.ClickItemListener() {
            @Override
            public void onClickItem(Diary item) {

                if (!mItemTouchHelper.closeOpenedPreItem()) {
                    return;
                }
                if (item == null) {
                    return;
                }

                Intent intent = new Intent();
                intent.setClass(MainActivity.this, WriteActivity.class);
                intent.putExtra(WriteActivity.ID, item.getId());
                startActivity(intent);
            }

            @Override
            public void delete(Diary item) {
                if (item == null) {
                    return;
                }
                DiaryLog diaryLog = DiaryLog.getInstance(MainActivity.this);
                diaryLog.deleteById(item.getId());
                List<Diary> diary = diaryLog.getAllUserClick();
                if (diary == null || diary.size() == 0) {
                    mEmptyTxt.setVisibility(View.VISIBLE);
                    recyclerView.setVisibility(View.GONE);
                } else {
                    mEmptyTxt.setVisibility(View.GONE);
                    recyclerView.setVisibility(View.VISIBLE);
                }
            }

        });
        mCallback = new CollectionLIstItemTouchHelperCallback();
        mItemTouchHelper = new ItemTouchHelperExtension(mCallback);
        mItemTouchHelper.setSwipActionInterface(itemAdapter);
        mItemTouchHelper.attachToRecyclerView(recyclerView);


        recyclerView.setAdapter(itemAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        List<Diary> diary = DiaryLog.getInstance(this).getAllUserClick();
        if (diary == null || diary.size() == 0) {
            mEmptyTxt.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
        } else {
            mEmptyTxt.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
        }
        itemAdapter.setData(diary);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }

        return super.onOptionsItemSelected(item);
    }
}
