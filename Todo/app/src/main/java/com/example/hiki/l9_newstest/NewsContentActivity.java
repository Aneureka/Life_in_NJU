package com.example.hiki.l9_newstest;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Selection;
import android.text.Spannable;
import android.view.Window;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by Hiki on 2016/9/6.
 */

public class NewsContentActivity extends Activity {

    public static void actionStart(Context context, String newsTitle) {
//        Intent intent = new Intent(context, NewsContentActivity.class);
//        intent.putExtra("news_title", newsTitle);
//        intent.putExtra("news_content", newsContent);
//        context.startActivity(intent);

        Intent intent = new Intent(context, NewsContentActivity.class);
        intent.putExtra("news_title", newsTitle);
//        intent.putExtra("news_content", newsContent);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.news_content);
        String newsTitle = getIntent().getStringExtra("news_title");
        String newsContent = getIntent().getStringExtra("news_content");

        NewsContentFragment newsContentFragment = (NewsContentFragment) getFragmentManager().findFragmentById(R.id.news_content_fragment);
        newsContentFragment.refresh(newsTitle, newsContent);

        SharedPreferences settings = this.getSharedPreferences("PREFS_CONF",
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();

        TextView textView = (TextView) findViewById(R.id.news_title);
        EditText editText = (EditText) findViewById(R.id.news_content);

        String title = settings.getString("title", "");
        String content = settings.getString(textView.getText().toString() + "content", "");


        editText.setText(content);

        CharSequence text = editText.getText();
        if (text instanceof Spannable) {
            Spannable spanText = (Spannable) text;
            Selection.setSelection(spanText, text.length());
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences mySharedPreferences = getSharedPreferences("test", Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = mySharedPreferences.edit();


    }

    @Override
    protected void onStop() {
        super.onStop();
        EditText editText = (EditText) findViewById(R.id.news_content);
        TextView textView = (TextView) findViewById(R.id.news_title);
        SharedPreferences settings = this.getSharedPreferences("PREFS_CONF",
                Context.MODE_PRIVATE);
        SharedPreferences.Editor edtor = settings.edit();
        if (!editText.getText().toString().equals("")) {
            edtor.putString(textView.getText().toString() + "content", editText.getText().toString());
            edtor.commit();
        }

    }
}

