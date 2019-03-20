package com.wxx.androiddemo;

import com.wxx.sqllite.BaseDao;
import com.wxx.sqllite.entity.Person;

import org.junit.Test;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        BaseDao<Person> baseDao = new BaseDao<>(Person.class);
        String s = baseDao.autoCreateTable();

        System.out.println("sql=" + s);

    }
}