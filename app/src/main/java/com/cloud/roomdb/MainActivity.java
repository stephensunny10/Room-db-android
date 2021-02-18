package com.cloud.roomdb;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Database;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.cloud.roomdb.Room.AppDatabase;
import com.cloud.roomdb.Room.DatabaseClient;
import com.cloud.roomdb.Room.Task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    ListView lv;
    ItemAdapter adapter;
    HashMap<String, String>personInfo;
    ArrayList<HashMap>itemList = new ArrayList<>();
    List<Task>taskList;
    Integer p;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lv = findViewById(R.id.lv);
        adapter = new ItemAdapter(this,itemList);
        lv.setAdapter(adapter);
        lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                p = position;
                DeletePerson deletePerson = new DeletePerson();
                deletePerson.execute();
                return false;
            }
        });
    }

    public void addData(View view) {
        startActivity(new Intent(this,AddItemActivity.class));
    }

    class GetPerson extends AsyncTask<String,Void, List<Task>>{

        @Override
        protected List<Task> doInBackground(String... strings) {
            List<Task> taskList = DatabaseClient
                    .getInstance(getApplicationContext())
                    .getAppDatabase()
                    .taskDao()
                    .getAll();
            return taskList;
        }
        @Override
        protected void onPostExecute(List<Task> tasks) {
            taskList = tasks;
            addItem(tasks);
        }
    }
    class DeletePerson extends AsyncTask<Integer,Void,String>{

        @Override
        protected String doInBackground(Integer... integers) {
            Task task = taskList.get(p);
            DatabaseClient.getInstance(getApplicationContext()).getAppDatabase().taskDao().delete(task);
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            Toast.makeText(getApplicationContext(),"Deleted",Toast.LENGTH_SHORT).show();
            readItem();
        }

    }
    public void addItem(List<Task>tasks){
        int l = tasks.size();
        itemList.clear();
        for (int i=0; i<l; i++){
            Task task = tasks.get(i);
            personInfo = new HashMap<>();
            personInfo.put("name",task.getName());
            personInfo.put("contact",task.getPhoneNo());
            personInfo.put("address",task.getAddress());
            itemList.add(personInfo);
            adapter.notifyDataSetChanged();
        }
    }
    public void readItem(){
        GetPerson person = new GetPerson();
        person.execute();
    }
    public void onResume(){
        super.onResume();
        readItem();
    }
}