package com.example.aysenur.hwk2.Fragment;

import android.support.v4.app.Fragment;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.aysenur.hwk2.R;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

public class NewFragment extends Fragment {

    public ListView newsList;
    public ArrayList<String> news;
    public ArrayList<String> links;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view =  inflater.inflate(R.layout.news, container, false);
        newsList =(ListView) view.findViewById(R.id.newsList);
        new FetchNews().execute();
        return view;
    }


    private class FetchNews extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... params) {
            try {

                links = new ArrayList<String>();
                news = new ArrayList<String>();

                Document doc = Jsoup.connect("https://aybu.edu.tr/muhendislik/bilgisayar/").get();
                Element newsElement = doc.select("div.cnContent").first();
                Iterator<Element> iterator = newsElement.select("div.cncItem").iterator();

                while(iterator.hasNext()){
                    Element div =iterator.next();
                    news.add(div.text());
                    links.add(div.select("a").attr("href"));
                }


            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {

            ArrayAdapter adptr =new ArrayAdapter<String>(getContext(),android.R.layout.simple_list_item_1,news);
            newsList.setAdapter(adptr);
            newsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position,
                                        long id) {

                    String[] news = new String[10000];
                    for(int i=0;i<links.size();i++){
                        news[i]="http://www.ybu.edu.tr/muhendislik/bilgisayar/"+links.get(i).toString();
                    }

                    Uri uri = Uri.parse(news[position]);
                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                    startActivity(intent);

                }
            });

        }
    }
}
