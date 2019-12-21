package com.xiaoliu.pagingdemo;

import androidx.paging.DataSource;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

/**
 *
 */
@Dao
public interface StudentDao {
    @Insert
    void insertStudent(Student ...students);

    @Query("DELETE FROM student_table")
    void deleteAllStudent();

    @Query("SELECT * FROM student_table")
    DataSource.Factory<Integer,Student> getAllStudets();
}
