package com.example.december.sunbeamnote;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.view.LayoutInflaterCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.Filter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.example.beans.Cuns;
import com.example.向阳.MyAdapter;
import com.example.向阳.MyDataBase;
import com.example.向阳.MyOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    Button bt;
    ListView lv;
    LayoutInflater inflater;
    ArrayList<Cuns> array;
    MyDataBase mdb;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lv = (ListView) findViewById(R.id.listView1);
        bt = (Button) findViewById(R.id.button1);
        inflater = getLayoutInflater();

        mdb = new MyDataBase(this);
        array = mdb.getArray();
        MyAdapter adapter = new MyAdapter(inflater, array);
        lv.setAdapter(adapter);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplication(), SecondActivity.class);
                intent.putExtra("ids", array.get(position).getIds());
                startActivity(intent);
                MainActivity.this.finish();
            }
        });

        lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view,final int position, long id) {
                new AlertDialog.Builder(MainActivity.this)
                        .setTitle("删除")
                        .setMessage("是否删除笔记")
                        .setNegativeButton("关闭", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        })
                        .setPositiveButton("删除", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                mdb.toDelete(array.get(position).getIds());
                                array = mdb.getArray();
                                MyAdapter adapter = new MyAdapter(inflater, array);
                                lv.setAdapter(adapter);
                            }
                        })
                        .create().show();
                return true;
            }
        });

        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                //Intent intent = new Intent(MainActivity.this,SecondActivity.class);
                intent.setClass(getApplicationContext(), SecondActivity.class);
                startActivity(intent);
                MainActivity.this.finish();
            }
        });
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item1:
                Intent intent = new Intent(getApplicationContext(), SecondActivity.class);
                startActivity(intent);
                this.finish();
                break;
            case R.id.item2:
                this.finish();
                break;
            default:
                break;
        }
        return true;
    }

}