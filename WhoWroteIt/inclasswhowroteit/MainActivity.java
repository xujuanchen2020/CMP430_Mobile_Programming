package com.example.inclasswhowroteit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethod;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private EditText bookInput;
    private TextView titleText, authorText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bookInput = findViewById(R.id.editBookInput);
        titleText = findViewById(R.id.tvTitle);
        authorText = findViewById(R.id.tvAuthor);
    }

    public void searchBook(View view) {

        String query = bookInput.getText().toString();

        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        if (inputMethodManager!=null){
            inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(),InputMethodManager.HIDE_NOT_ALWAYS);

        }

        ConnectivityManager connectivityManager =(ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = null;
        if(connectivityManager != null){
            networkInfo = connectivityManager.getActiveNetworkInfo();

        }
        if(networkInfo!=null && networkInfo.isConnected() && query.length()!=0){
            new FetchBook(titleText,authorText).execute(query);
            authorText.setText("");
            titleText.setText("fetching result");
        }else{
            if(query.length()==0){
                authorText.setText("no result");
                titleText.setText("no result");
            }else{
                authorText.setText("no networking");
                titleText.setText("no networking");
            }
        }

    }
}
