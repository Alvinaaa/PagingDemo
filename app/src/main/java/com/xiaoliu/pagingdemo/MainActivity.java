package com.xiaoliu.pagingdemo;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class MainActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private LiveData<PagedList<Student>> mBuild;
    private StudentDao mStudentDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView = findViewById(R.id.recycler);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));
        final PageAdapter pageAdapter = new PageAdapter();
        mRecyclerView.setAdapter(pageAdapter);
        StudentDatabase studentDatabase = StudentDatabase.getInstance(this);
        mStudentDao = studentDatabase.getStudentDao();
        mBuild = new LivePagedListBuilder<>(mStudentDao.getAllStudets(), 2).build();
        mBuild.observe(this, new Observer<PagedList<Student>>() {
            @Override
            public void onChanged(final PagedList<Student> students) {
                pageAdapter.submitList(students);
                students.addWeakCallback(null, new PagedList.Callback() {
                    private static final String TAG = "addWeakCallback";
                    //当数据不够时调用该方法
                    @Override
                    public void onChanged(int position, int count) {
                        Log.d(TAG, "onChanged-position: "+position);
                        Log.d(TAG, "onChanged-count: "+count);
                    }

                    @Override
                    public void onInserted(int position, int count) {

                    }

                    @Override
                    public void onRemoved(int position, int count) {

                    }
                });
            }
        });
    }

    //生成
    public void buttonPopulate(View view) {
        Student[] students = new Student[1000];
        for(int i =0;i<students.length;i++){
            Student student = new Student();
            student.setStudentNumber(i);
            students[i] = student;
        }
        new InsertAsyncTask(mStudentDao).execute(students);
    }

    //清除
    public void buttonClear(View view) {
       new ClearAsyncTask(mStudentDao).execute();
    }

    static class InsertAsyncTask extends AsyncTask<Student,Void,Void>{
        StudentDao mStudentDao;
        public InsertAsyncTask(StudentDao student) {
            this.mStudentDao = student;
        }

        @Override
        protected Void doInBackground(Student... students) {
            mStudentDao.insertStudent(students);
            return null;
        }
    }

    static class ClearAsyncTask extends AsyncTask<Void,Void,Void>{
        StudentDao mStudentDao;

        public ClearAsyncTask(StudentDao studentDao) {
            mStudentDao = studentDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            mStudentDao.deleteAllStudent();
            return null;
        }
    }
}
