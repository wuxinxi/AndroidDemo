package com.wxx.sqllite;

import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;

import com.wxx.sqllite.annotation.Id;
import com.wxx.sqllite.annotation.TEntity;
import com.wxx.sqllite.annotation.TField;
import com.wxx.sqllite.interfaces.IBaseDao;

import java.io.File;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

/**
 * 作者: TangRen on 2019/3/20
 * 包名：com.wxx.sqllite
 * 邮箱：996489865@qq.com
 * TODO:一句话描述
 */
public class BaseDao<T> implements IBaseDao<T> {

    private SQLiteDatabase database;
    private Class<T> entityClass;
    private String tableName;


    @Override
    public Long insert(T entity) {
        return null;
    }

    public BaseDao(Class<T> entityClass) {
        this.entityClass = entityClass;
        tableName = entityClass.getAnnotation(TEntity.class).value();
    }

    public synchronized void init() {
        String dbPath = Environment.getExternalStorageDirectory() + File.separator + "db";
        File file = new File(dbPath);
        if (!file.exists()) {
            boolean mkdirs = file.mkdirs();
        }
        String path = dbPath + "/sql.db";
        database = SQLiteDatabase.openOrCreateDatabase(path, null);

    }


    public String autoCreateTable() {
        StringBuilder sql = new StringBuilder();
        sql.append("CREATE TABLE IF NOT EXISTS ");
        sql.append(tableName).append(" ( ");
        int index = sql.length();
        Field[] fields = entityClass.getDeclaredFields();
        for (Field field : fields) {
            Class type = field.getType();
            if (type == String.class) {
                sql.append(field.getAnnotation(TField.class).value()).append(" TEXT,");
            } else if (type == Integer.class) {
                sql.append(field.getAnnotation(TField.class).value()).append(" INTEGER,");
            } else if (type == Double.class) {
                sql.append(field.getAnnotation(TField.class).value()).append(" DOUBLE,");
            } else if (type == Long.class) {
                Annotation[] annotations = field.getAnnotations();
                for (Annotation annotation : annotations) {
                    Class<? extends Annotation> aClass = annotation.annotationType();
                    if (aClass == TField.class) {
                        sql.append(field.getAnnotation(TField.class).value()).append(" BIGINT,");
                    } else if (aClass == Id.class) {
                        //只有当主键类型为Long类型并且AUTOINCREMENT=true
                        boolean autoincrement = field.getAnnotation(Id.class).autoincrement();
                        if (autoincrement) {
                            String s = field.getAnnotation(Id.class).value() + " BIGINT PRIMARY KEY AUTOINCREMENT ,";
                            sql.insert(index, s);
                        } else {
                            sql.append("Id").append(" BIGINT,");
                        }
                    }
                }
            } else if (type == byte[].class) {
                sql.append(field.getAnnotation(TField.class).value()).append(" BLOB,");
            }
        }

        if (sql.charAt(sql.length() - 1) == ',') {
            sql.deleteCharAt(sql.length() - 1);
        }

        sql.append(" )");
        return sql.toString();
    }


}
