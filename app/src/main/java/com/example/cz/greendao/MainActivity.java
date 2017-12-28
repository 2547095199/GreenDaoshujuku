package com.example.cz.greendao;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.cz.greendao.dao.DaoMaster;
import com.example.cz.greendao.dao.DaoSession;
import com.example.cz.greendao.dao.UserDao;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    /**
     * 添加
     */
    private Button mInsert;
    /**
     * 删除
     */
    private Button mDelete;
    /**
     * 修改
     */
    private Button mUpdate;
    /**
     * 查询
     */
    private Button mSelect;
    /**
     * 请输入用户名
     */
    private EditText mName;
    /**
     * 请输入年龄
     */
    private EditText mAge;
    private User user;
    UserDao userDao;
    /**
     * 这是修改的id
     */
    private EditText mId;
    private ListView mListview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        DaoMaster.DevOpenHelper devOpenHelper = new DaoMaster.DevOpenHelper(getApplicationContext(), "lenve.db", null);
        DaoMaster daoMaster = new DaoMaster(devOpenHelper.getWritableDb());
        DaoSession daoSession = daoMaster.newSession();
        //UserDao接受
        userDao = daoSession.getUserDao();

        //添加
        mInsert.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(mName.getText().toString()) || TextUtils.isEmpty(mAge.getText().toString())) {
                    Toast.makeText(MainActivity.this, "请输入信息", Toast.LENGTH_SHORT).show();
                } else {
                    User user = new User();
                    user.setAge(Integer.parseInt(mAge.getText().toString()));
                    user.setName("" + mName.getText().toString());
                    userDao.insert(user);

                }

            }
        });
        //查询
        mSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                List<User> list = userDao.queryBuilder()
                        .build().list();
                MyAdapter adapter = new MyAdapter(MainActivity.this, list);
                mListview.setAdapter(adapter);
                //之后就是遍历查询出来的集合
//                for (int i = 0; i < list.size(); i++) {
//                    mTextview.setText("id=========" + list.get(i).getId() + "name===== " + list.get(i).getName() + "age====" + list.get(i).getAge());
//                }

            }
        });
        //删除
        mDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                List<User> userList = (List<User>) userDao.queryBuilder().where(UserDao.Properties.Id.eq(mId.getText().toString())).build().list();
                for (User user : userList) {
                    userDao.delete(user);
                }

            }
        });
        //修改
        mUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                List<User> user2 = (List<User>) userDao.queryBuilder()
                        .where(UserDao.Properties.Id.eq(mId.getText().toString())).build().list();
                //查询出后如果集合user2是空，证明你要修改的那个不存在，
                if (user2 == null) {
                    Toast.makeText(MainActivity.this, "用户不存在!", Toast.LENGTH_SHORT).show();
                } else {
//                    user2.setUsername("儿子");
//                    userDao.update(user2);
                    //查询出后遍历集合，一条一条的修改。
                    for (int i = 0; i < user2.size(); i++) {
                        //Log.d("google_lenve","id========="+list.get(i).getId()+ "name===== " + list.get(i).getUsername()+"age===="+list.get(i).getNickname());
                        user2.get(i).setName("将定燃");
                        userDao.update(user2.get(i));
                    }

                }
            }
        });
    }

    private void initView() {
        mInsert = (Button) findViewById(R.id.insert);
        mDelete = (Button) findViewById(R.id.delete);
        mUpdate = (Button) findViewById(R.id.update);
        mSelect = (Button) findViewById(R.id.select);
        mName = (EditText) findViewById(R.id.name);
        mAge = (EditText) findViewById(R.id.age);
        mId = (EditText) findViewById(R.id.id);
        mListview = (ListView) findViewById(R.id.listview);
    }
}
