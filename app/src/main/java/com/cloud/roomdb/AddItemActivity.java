package com.cloud.roomdb;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.cloud.roomdb.Room.DatabaseClient;
import com.cloud.roomdb.Room.Task;


public class AddItemActivity extends AppCompatActivity {
    EditText ed1,ed2,ed3;
    String name,contact,address;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);
        findViews();
    }
    public void findViews(){
        ed1 = findViewById(R.id.ed1);
        ed2 = findViewById(R.id.ed2);
        ed3 = findViewById(R.id.ed3);
    }

    public void saveItem(View view) {
        name = ed1.getText().toString();
        contact = ed2.getText().toString();
        address = ed3.getText().toString();
        if (name.isEmpty() || contact.isEmpty() || address.isEmpty()) {
           Toast.makeText(this,"Please fill all detail",Toast.LENGTH_SHORT).show();
           return;
        }
        Person person = new Person();
        person.execute();
    }

    class Person extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... strings) {
            Task task = new Task();
            task.setName(name);
            task.setPhoneNo(contact);
            task.setAddress(address);
            //adding to database
            DatabaseClient.getInstance(getApplicationContext()).getAppDatabase()
                    .taskDao()
                    .insert(task);
            return null;
        }
        @Override
        protected void onPostExecute(String s) {
            Toast.makeText(getApplicationContext(),"saved",Toast.LENGTH_SHORT).show();
            finish();
        }
    }
}