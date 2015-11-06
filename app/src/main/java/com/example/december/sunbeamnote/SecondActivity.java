package com.example.december.sunbeamnote;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.beans.Cuns;
import com.example.向阳.MyDataBase;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by december on 15/11/4.
 */
public class SecondActivity extends Activity {
    EditText ed1, ed2;
    Button bt1;
    MyDataBase myDataBase;
    Cuns cun;
    int ids;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        ed1 = (EditText) findViewById(R.id.editText1);
        ed2 = (EditText) findViewById(R.id.editText2);
        bt1 = (Button) findViewById(R.id.button1);
        myDataBase = new MyDataBase(this);
        Intent intent = this.getIntent();
        ids = intent.getIntExtra("ids", 0);
        //默认为0，不为0，数据修改时跳转过来
        if (ids != 0) {
            cun = myDataBase.getTiandCon(ids);
            ed1.setText(cun.getTitle());
            ed2.setText(cun.getContent());

        }
        //保存按钮的点击事件，和返回按钮一样
        bt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isSave();
            }
        });
        /*
        *返回按钮调用的方法
        * @see android.app.Activity#onBackPressed()
         */
    }

    @Override
    public void onBackPressed() {
        // TODO Auto-generated method stub
        //super.onBackPressed();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy.MM.dd  HH:mm:ss");
        Date curDate = new Date(System.currentTimeMillis());//获取当前时间
        String times = formatter.format(curDate);
        String title = ed1.getText().toString();
        String content = ed2.getText().toString();
        //修改数据
        if (ids != 0) {
            cun = new Cuns(title, ids, content, times);
            myDataBase.toUpdate(cun);
            Intent intent = new Intent(SecondActivity.this, MainActivity.class);
            startActivity(intent);
            SecondActivity.this.finish();
        }
        //新建文本
        else {
            cun = new Cuns(title, content, times);
            myDataBase.toInsert(cun);
            Intent intent = new Intent(SecondActivity.this, MainActivity.class);
            startActivity(intent);
            SecondActivity.this.finish();

        }
    }

 private void isSave(){
     SimpleDateFormat   formatter   =   new   SimpleDateFormat   ("yyyy.MM.dd  HH:mm:ss");
     Date   curDate   =   new   Date(System.currentTimeMillis());//ªÒ»°µ±«∞ ±º‰
     String times   =   formatter.format(curDate);
     String title=ed1.getText().toString();
     String content=ed2.getText().toString();
     //修改数据
     if (ids!=0) {
         cun = new Cuns(title, ids, content, times);
         myDataBase.toUpdate(cun);
         Intent intent = new Intent(SecondActivity.this, MainActivity.class);
         startActivity(intent);
         SecondActivity.this.finish();
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
        // TODO Auto-generated method stub
        switch (item.getItemId()) {
            case R.id.action_settings:
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_TEXT, "标题" + ed1.getText().toString() + "内容" + ed2.getText().toString());
                startActivity(intent);
                break;

            default:
                break;
        }
        return false;
    }

 }

