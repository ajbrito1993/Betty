
package com.example.alexbrito.betty;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

public class FullResults extends AppCompatActivity {


    /**
     * Member Variables
     * **/
    private static final String TAG = "FullResults";
    private TextView queryTV;
    private TextView result;
    private TextView infoTV;
    private EditText basicInfo, sequence;
    private static  String userQuery;
    static ArrayList<FullResult> json ;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.full_results);

        userQuery = getIntent().getStringExtra("userRequest");

        queryTV = (TextView) findViewById(R.id.queryTV);
        queryTV.setText("Query: " + userQuery);
        infoTV = (TextView) findViewById(R.id.textView);
        basicInfo = (EditText) findViewById(R.id.basicInfo);
        sequence = (EditText) findViewById(R.id.sequenceEditText);
        result = (TextView) findViewById(R.id.result);

        //Execute the Thread
        new FetchItemsTask(getContext()).execute();
    }

    FullResults getContext() {
        return this;
    }

    private static class FetchItemsTask extends AsyncTask<Void, Void, String> {

        JSONObject jsonObject;
        private WeakReference<FullResults> fullResultsWeakReference;
        Bitmap wolfaBit = null;
        FetchItemsTask(FullResults context) {
            fullResultsWeakReference = new WeakReference<>(context);
        }

        

        @Override
        protected String doInBackground(Void... voids) {
         json =  new ArrayList<>();
            String results = null;
            FullResults context = fullResultsWeakReference.get();

            try {
                jsonObject = new WolframAlphaFetch().fetchJSONItems(userQuery);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            try {
                 json = new WolframAlphaFetch().parseJSON(jsonObject);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }


        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            FullResults context = fullResultsWeakReference.get();

                for (FullResult o : json){
                    System.out.println(o.getTitle());

                }

                /**
                 * Each attribute from Wolfram (Basic Info, Result, and Sequence
                 * Although, I couldnt figure out a way to escape a new line with the JSON
                 * The information is correct.
                 * **/

                context.result.setText(json.get(1).getValue());
                context.basicInfo.setText(json.get(2).getValue());
                context.basicInfo.setLines(4);
                context.sequence.setText(json.get(4).getValue());



        }
    }


}

