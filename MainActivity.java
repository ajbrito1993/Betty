package com.example.alexbrito.betty;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.lang.ref.WeakReference;

public class MainActivity extends AppCompatActivity {

    private ImageView bunkerHillImageView;
    static private TextView bettyTitle;
    private ImageView bettyImageView;
    private ImageView woflramLogo;
    private TextView poweredByTitle;
    private EditText queryEditText;
    private Button submitButton;
    private Button clearButton;
    private Button fullInfoButton;
    private EditText answerEditText;
    private CheckBox speakCheckBox;


    private static final String KEY = "YWXHV2-ELJRH47VTP";
    private static final String TAG = "MainActivity";
    private static final String QUERY = "userRequest";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bunkerHillImageView = (ImageView) findViewById(R.id.bunkerImageView);
        bettyTitle = (TextView) findViewById(R.id.bettyTitle);
        bettyImageView = (ImageView) findViewById(R.id.bettyImageView);
        woflramLogo = (ImageView) findViewById(R.id.woflramLogo);
        poweredByTitle = (TextView) findViewById(R.id.poweredByTitle);
        queryEditText = (EditText) findViewById(R.id.queryEditText);
        submitButton = (Button) findViewById(R.id.submitButton);
        clearButton = (Button) findViewById(R.id.clearButton);
        fullInfoButton = (Button) findViewById(R.id.fullResults);
        answerEditText = (EditText) findViewById(R.id.answerEditText);
        speakCheckBox = (CheckBox) findViewById(R.id.speakCheckBox);


        submitButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Log.d("MAIN", "btn clicked");
                new FetchItemsTask(getContext()).execute();
            }
        });


        fullInfoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFullResults();
            }
        });

        clearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                queryEditText.setText("");
                answerEditText.setText("");
            }
        });

    }

    public void openFullResults(){
        Intent intent = new Intent(this, FullResults.class);
        String userQuery = queryEditText.getText().toString();
        intent.putExtra(QUERY,userQuery);
        startActivity(intent);
    }

    MainActivity getContext() {
        return this;
    }

    private static class FetchItemsTask extends AsyncTask<Void, Void, String> {
        private WeakReference<MainActivity> mainActivityWeakReference;

        FetchItemsTask(MainActivity context) {
            mainActivityWeakReference = new WeakReference<>(context);
        }


        @Override
        protected String doInBackground(Void... voids) {

            MainActivity context = mainActivityWeakReference.get();
            Log.d(TAG, "Print query request " + context.queryEditText.getText().toString());

            String result = new WolframAlphaFetch().fetchItems(context.queryEditText.getText().toString());
            Log.i(TAG, "Fetched contents of URL: " + result);

            return result ;
        }


        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            MainActivity context = mainActivityWeakReference.get();
            context.answerEditText.setText(s);

        }
    }
}
