package com.example.inclasswhowroteit;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.ref.WeakReference;

public class FetchBook extends AsyncTask<String, Void, String> {
    private static final String TAG = "FetchBook";
    private WeakReference<TextView> titleText;
    private WeakReference<TextView> authorText;

    FetchBook(TextView titleText, TextView authorText){
        this.titleText = new WeakReference<>(titleText);
        this.authorText = new WeakReference<>(authorText);
    }

    @Override
    protected String doInBackground(String... strings) {
        String s = NetworkUtils.getBookInfo(strings[0]);
        Log.d(TAG,"INSIDE FetchBook");
        return s;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        try {
            JSONObject jsonObject = new JSONObject(s);
            JSONArray jsonArray = jsonObject.getJSONArray("items");
            String title = null;
            String authors = null;

            for (int i=0;(i<jsonArray.length() && title==null && authors==null);i++){
                JSONObject book = jsonArray.getJSONObject(i);
                JSONObject bookInfo = book.getJSONObject("volumeInfo");
                title = bookInfo.getString("title");
                authors = bookInfo.getString("authors");

            }

            if(title!=null && authors != null){
                titleText.get().setText(title);
                authorText.get().setText(authors);
            }else{
                titleText.get().setText("no title");
                authorText.get().setText("no authors");
            }

        } catch (JSONException e) {
            titleText.get().setText("title exception");
            authorText.get().setText("author exception");
            e.printStackTrace();
        }
    }

}
