package com.example.inclasswhowroteit;

import android.net.Uri;
import android.renderscript.ScriptGroup;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class NetworkUtils {
    private static final String TAG = "nETWORKuTILS";
    private static final String BOOK_BASE_URL = "https://www.googleapis.com/books/v1/volumes?";
    private static final String QUERY_PARAM = "q";

    private static final String MAX_RESULTs = "max_result";
    private static final String MAX_RESULTS_VALUE_AS_STRING ="10";

    private static final String PRINT_TYPE = "printType";
    private static final String PRINT_TYPE_VALUE = "books";

    protected static String getBookInfo(String query){
        String bookJSONString = null;
        HttpURLConnection urlConnection = null;
        BufferedReader bufferedReader = null;

        try{
            Uri builtURL = Uri.parse(BOOK_BASE_URL).buildUpon()
                    .appendQueryParameter(QUERY_PARAM,query)
                    .appendQueryParameter(MAX_RESULTs,MAX_RESULTS_VALUE_AS_STRING)
                    .appendQueryParameter(PRINT_TYPE,PRINT_TYPE_VALUE)
                    .build();
            URL requestURL = new URL(builtURL.toString());

            urlConnection = (HttpURLConnection) requestURL.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            InputStream inputStream = urlConnection.getInputStream();
            bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

            StringBuilder builder = new StringBuilder();
            String line = null;
            while ( (line=bufferedReader.readLine()) !=null){
                builder.append(line+"\n");
            }
            if (builder.length()==0){
                bookJSONString = null;
            }else{
                bookJSONString = builder.toString();
            }

        }catch (IOException e){
            e.printStackTrace();
        }finally {
            if(urlConnection!=null){
                urlConnection.disconnect();
            }
            if(bufferedReader!=null){
                try{
                    bufferedReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return bookJSONString;
    }
}
