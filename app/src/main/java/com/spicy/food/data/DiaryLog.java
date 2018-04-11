package com.spicy.food.data;

import android.content.Context;
import android.text.TextUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import de.greenrobot.dao.query.QueryBuilder;


/**
 * Created by liucong on 17/4/8.
 */

public class DiaryLog {
    private static DiaryLog instance = null;
    private static DiaryDao userClickDao = null;
    private static DaoSession daoSession = null;

    public static DiaryLog getInstance(Context context) {
        if (instance == null) {
            instance = new DiaryLog();

            if (userClickDao == null) {
                userClickDao = DBHelper.getInstance(context).getDaoSession().getDiaryDao();
                daoSession = DBHelper.getInstance(context).getDaoSession();
            }
        }
        return instance;
    }

    public void addUserClick(String title, String content) {
        if (userClickDao == null) {
            return;
        }
        QueryBuilder<Diary> crashQb = userClickDao.queryBuilder();
        if (crashQb == null) {
            return;
        }
        //多余1000条，不保存
        if (crashQb.count() <= 1000) {
            try {
                String time = new SimpleDateFormat(
                        "yyyy-MM-dd HH:mm:ss").format(new Date());
                Diary userClick = new Diary();
                userClick.setTitle(TextUtils.isEmpty(title) ? title : title);
                userClick.setContent(content);
                userClick.setTime(time);
                userClickDao.insert(userClick);
            } catch (Exception e) {
                // 快速点击会crash 时间为主键 造成异常
            }
        }

    }

    public void updateDiary(Diary diary) {
        if (diary != null && daoSession != null) {
            daoSession.update(diary);
        }
    }

    public List<Diary> getAllUserClick() {
        if (userClickDao == null) {
            return null;
        }
        QueryBuilder<Diary> crashQb = userClickDao.queryBuilder();
        List<Diary> userClicks = new ArrayList<>();
        if (crashQb != null) {
            userClicks = crashQb.list();
        }
        return userClicks;
    }
    public void deleteById(long id){
        userClickDao.deleteByKey(id);
    }
    public Diary getDiaryById(long id) {
        if (userClickDao == null) {
            return null;
        }
        Diary diary = userClickDao.queryBuilder()
                .where(DiaryDao.Properties.Id.eq(id)).unique();
        return diary;
    }

    public void deleteAll() {
        if (userClickDao == null) {
            return;
        }
        userClickDao.deleteAll();
    }

    public void close(Context context) {
        if (userClickDao == null) {
            return;
        }
        DBHelper.getInstance(context).closeDataBase();
        instance = null;
        userClickDao = null;
    }
}
