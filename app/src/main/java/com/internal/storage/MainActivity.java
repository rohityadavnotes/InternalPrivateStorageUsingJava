package com.internal.storage;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.internal.storage.utilities.InternalPrivateFileUtils;
import java.io.File;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = MainActivity.class.getSimpleName();

    private EditText editText;
    private TextView textView, filePathTextView;
    private Button writeButton, readButton;

    private String fileName                 = "register";
    private String fileExtension            = "txt";

    private File createdFile                = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initializeView();
        initializeEvent();
    }

    private void initializeView() {
        editText            = findViewById(R.id.editText);
        textView            = findViewById(R.id.textView);
        writeButton         = findViewById(R.id.writeButton);
        readButton          = findViewById(R.id.readButton);
        filePathTextView    = findViewById(R.id.filePathTextView);
    }

    private void initializeEvent() {
        writeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /* String stringData = editText.getText().toString(); */
                String stringData = "Rohit Yadav,rohit@gamil.com,rohit_123";
                writeFile(stringData);
            }
        });

        readButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                readFile();
            }
        });
    }

    /* Best Option */
    private void writeFile(String stringData){
        try {
            createdFile = InternalPrivateFileUtils.createAndWriteFile(getApplicationContext(), fileName, fileExtension, stringData);
            filePathTextView.setText(createdFile.getAbsolutePath());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /* Best Option */
    private void readFile(){
        String string = InternalPrivateFileUtils.readFile(getApplicationContext(), fileName, fileExtension);

        if (string != null)
        {
            String[] items  = string.split(",");

            if (items.length > 0)
            {
                String name     = items[0];
                String email    = items[1];
                String password = items[2];

                Log.e(TAG, name);
                Log.e(TAG, email);
                Log.e(TAG, password);

                textView.setText("Name : "+name+"\n"+"Email : "+email+"\n Password : "+password);
            }
        }
    }

    private void writeFileOne(String stringData){
        InternalPrivateFileUtils.createAndWriteFileUsingOpenFileOutput(getApplicationContext(), fileName, fileExtension, stringData);
    }

    private void readFileOne(){
        String string   = InternalPrivateFileUtils.readFileUsingOpenFileInputOne(getApplicationContext(), fileName, fileExtension);

        if (string != null)
        {
            String[] items  = string.split(",");

            if (items.length > 0)
            {
                String name     = items[0];
                String email    = items[1];
                String password = items[2];

                Log.e(TAG, name);
                Log.e(TAG, email);
                Log.e(TAG, password);

                textView.setText("Name : "+name+"\n"+"Email : "+email+"\n Password : "+password);
            }
        }
    }

    private void readFileTwo(){
        String string   = InternalPrivateFileUtils.readFileUsingOpenFileInputTwo(getApplicationContext(), fileName, fileExtension);

        if (string != null)
        {
            String[] items  = string.split(",");

            if (items.length > 0)
            {
                String name     = items[0];
                String email    = items[1];
                String password = items[2];

                Log.e(TAG, name);
                Log.e(TAG, email);
                Log.e(TAG, password);

                textView.setText("Name : "+name+"\n"+"Email : "+email+"\n Password : "+password);
            }
        }
    }
}