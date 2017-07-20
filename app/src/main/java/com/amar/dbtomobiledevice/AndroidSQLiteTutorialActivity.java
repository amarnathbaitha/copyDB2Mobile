package com.amar.dbtomobiledevice;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;


public class AndroidSQLiteTutorialActivity extends Activity {
    /** Called when the activity is first created. */

    DatabaseHandler db;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
         db = new DatabaseHandler(this);
        
        /**
         * CRUD Operations
         * */
        // Inserting Contacts
        Log.d("Insert: ", "Inserting ..");
        db.addContact(new Contact("Ravi", "9100000000"));
        db.addContact(new Contact("Srinivas", "9199999999"));
        db.addContact(new Contact("Tommy", "9522222222"));
        db.addContact(new Contact("Karthik", "9533333333"));
 
        // Reading all contacts
        Log.d("Reading: ", "Reading all contacts..");
        List<Contact> contacts = db.getAllContacts();       
 
        for (Contact cn : contacts) {
            String log = "Id: "+cn.getID()+" ,Name: " + cn.getName() + " ,Phone: " + cn.getPhoneNumber();
                // Writing Contacts to log
        Log.d("Name: ", log);
        
        }
        backupDatabase();
    }


    public File getBackupDatabaseFile() {
        File dir = new File(getExternalFilesDir("amar") + "/backup");
        if (!dir.exists()) {
            dir.mkdirs();
        }
        Log.d("dddd", "getBackupDatabaseFile dir::" + dir);
        return new File(dir, db.DATABASE_NAME);
    }

    public final boolean backupDatabase() {
        File from = getDatabasePath(db.DATABASE_NAME);
        File to = getBackupDatabaseFile();
        Log.d("dddd", "backupDatabase from::" + from);
        Log.d("dddd", "backupDatabase to::" + to);
        try {
            copyFile(from, to);
            return true;
        } catch (IOException e) {
            // TODO Auto-generated catch block
            Log.d("dddd", "Error backuping up database: " + e.getMessage(), e);
        }
        return false;
    }

    public void copyFile(File src, File dst) throws IOException {
        FileInputStream in = new FileInputStream(src);
        FileOutputStream out = new FileOutputStream(dst);
        Log.d("dddd", " FileInputStream in ::" + in);
        Log.d("dddd", "FileOutputStream out::" + out);
        FileChannel fromChannel = null, toChannel = null;
        try {
            fromChannel = in.getChannel();
            toChannel = out.getChannel();
            fromChannel.transferTo(0, fromChannel.size(), toChannel);
        } finally {
            if (fromChannel != null)
                fromChannel.close();
            if (toChannel != null)
                toChannel.close();
        }

        Log.d("dddd", "copyFile::");
    }

}
