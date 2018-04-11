package com.spicy.food.record;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import com.spicy.food.data.Diary;
import com.spicy.food.data.DiaryLog;

public class WriteActivity extends AppCompatActivity {
    private EditText mTitle;
    private EditText mContent;
    public static final String ID = "title";
    private long id = -1;
    private Diary diary;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.write_main);
        mTitle = (EditText) findViewById(R.id.edit_title);
        mContent = (EditText) findViewById(R.id.edit_content);
        Intent intent = getIntent();
        if (intent != null) {
            id = intent.getLongExtra(ID, -1);
            diary = DiaryLog.getInstance(WriteActivity.this).getDiaryById(id);
            if (diary != null) {
                if (diary.getTitle() != null) {
                    mTitle.setText(diary.getTitle());
                }
                if (diary.getContent() != null) {
                    mContent.setText(diary.getContent());
                }
            }

        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            DiaryLog diaryLog = DiaryLog.getInstance(WriteActivity.this);
            String title = mTitle.getEditableText().toString();
            String content = mContent.getEditableText().toString();
            if (this.id == -1) {
                diaryLog.addUserClick(title, content);
            } else {
                if (!TextUtils.isEmpty(title)) {
                    diary.setTitle(title);
                }
                if (!TextUtils.isEmpty(content)) {
                    diary.setContent(content);
                }
                diaryLog.updateDiary(diary);
            }
            onBackPressed();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
