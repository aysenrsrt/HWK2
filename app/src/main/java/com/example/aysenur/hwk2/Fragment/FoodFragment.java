package com.example.aysenur.hwk2.Fragment;

//import android.app.Fragment;
import android.support.v4.app.Fragment;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.aysenur.hwk2.R;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;

public class FoodFragment extends Fragment {

    public TextView food;
    String link = "https://aybu.edu.tr/sks/";
    ArrayList<String> foodList;
    public ProgressDialog PrgDlg;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.food, container, false);
        food=(TextView) view.findViewById(R.id.foodList);
        new FetchFoodList().execute();
        return view;
    }

    private class FetchFoodList extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            foodList = new ArrayList<String>();
            try {
                URL link = new URL("https://aybu.edu.tr/sks/");
                Document doc = Jsoup.parse(link,3000);
                Element table = doc.select("table").first();

                Iterator<Element> iterator = table.select("td").iterator();

                iterator.next();
                while(iterator.hasNext()){
                    foodList.add(iterator.next().text());
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            String a="";
            for(int i=0;i<foodList.size();i++){
                a=a+"\n"+foodList.get(i).toString();
            }
            food.setText(a);
        }
    }
}
