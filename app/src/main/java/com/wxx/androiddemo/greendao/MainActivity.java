package com.wxx.androiddemo.greendao;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.wxx.androiddemo.R;
import com.wxx.androiddemo.base.BaseActivity;
import com.wxx.androiddemo.greendao.dao.GreenDaoEntityDao;
import com.wxx.androiddemo.greendao.entity.GreenDaoEntity;
import com.wxx.androiddemo.greendao.manager.DBCore;

import org.greenrobot.greendao.async.AsyncSession;
import org.greenrobot.greendao.query.DeleteQuery;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者: TangRen on 2019/4/6
 * 包名：com.wxx.androiddemo.greendao
 * 邮箱：996489865@qq.com
 * TODO:一句话描述
 */
public class MainActivity extends BaseActivity implements View.OnClickListener{
    private Button insert,delete,query;
    public static final int CODE=1;
    @Override
    protected int layout() {
        return R.layout.main_sql;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        insert=findViewById(R.id.insert);
        delete=findViewById(R.id.delete);
        query=findViewById(R.id.query);
        insert.setOnClickListener(this);
        delete.setOnClickListener(this);
        query.setOnClickListener(this);
    }

    @Override
    protected void initData() {
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (Build.VERSION.SDK_INT>Build.VERSION_CODES.M){
            int permission = ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
            if (permission!= PackageManager.PERMISSION_GRANTED){
                ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_EXTERNAL_STORAGE},CODE);
            }
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.insert:
                Log.e("MainActivity",
                    "onClick(MainActivity.java:68)开始装插入");
                new Thread(() -> {
            GreenDaoEntityDao insertDao= DBCore.getDaoSession().getGreenDaoEntityDao();
            List<GreenDaoEntity>list=new ArrayList<>();
            for (int i = 0; i < 10000*5; i++) {
                GreenDaoEntity entity=new GreenDaoEntity();
                entity.setContent(System.currentTimeMillis()+ SystemClock.elapsedRealtime()+"");
                list.add(entity);
            }
            AsyncSession aSyncDaoSession = DBCore.getASyncDaoSession();
            aSyncDaoSession.runInTx(() -> {
                Log.e("MainActivity",
                    "onClick(MainActivity.java:80)开始插入");
                insertDao.insertInTx(list);
                Log.e("MainActivity",
                    "onClick(MainActivity.java:83)插入结束");
            });
                }).start();
                break;
            case R.id.delete:
                delete();
                break;
            case R.id.query:
                GreenDaoEntityDao queryDao=DBCore.getDaoSession().getGreenDaoEntityDao();
                long count = queryDao.queryBuilder().count();
                System.err.println("MainActivity.onClick 总数:"+count);
                break;
            default:
                break;
        }
    }

    private void delete() {
        GreenDaoEntityDao dao=DBCore.getDaoSession().getGreenDaoEntityDao();
        long count = dao.queryBuilder().count();
        if (count<10*10000){
            System.err.println("MainActivity.delete 小于1000->return");
            return;
        }
        //最小的记录信息
       GreenDaoEntity minEntity= dao.queryBuilder().orderAsc(GreenDaoEntityDao.Properties.Id).limit(1).unique();
        //最大的记录信息
       GreenDaoEntity maxEntity= dao.queryBuilder().orderDesc(GreenDaoEntityDao.Properties.Id).limit(1).unique();
        if (minEntity==null||maxEntity==null){
            System.err.println("MainActivity.delete 数据为null->return");
            return;
        }
        System.out.println("MainActivity.delete minEntity="+minEntity);
        System.out.println("MainActivity.delete maxEntity="+maxEntity);

        Long min=minEntity.getId();
        Long max=maxEntity.getId();
        //删除的截止ID
        Long deleteEndId=min+3*10000;
        if (deleteEndId>=max){
            System.err.println("MainActivity.delete 删除截止ID大于等于最大ID->return");
            return;
        }
        System.out.println("MainActivity.delete 执行删除 minID="+min+",maxID="+max+",deleteEndId="+deleteEndId);
        DeleteQuery<GreenDaoEntity> greenDaoEntityDeleteQuery = dao
                .queryBuilder()
                .where(GreenDaoEntityDao.Properties.Id.between(min, deleteEndId))
                .buildDelete();
        greenDaoEntityDeleteQuery.executeDeleteWithoutDetachingEntities();
        dao.detachAll();
        System.out.println("MainActivity.delete 剩余："+dao.queryBuilder().count());
    }

}
