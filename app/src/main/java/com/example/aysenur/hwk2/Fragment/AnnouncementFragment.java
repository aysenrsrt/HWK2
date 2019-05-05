package com.example.aysenur.hwk2.Fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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

public class AnnouncementFragment extends Fragment{

    public ListView announcements;
    public ArrayList<String> announcementList;
    public ArrayList<String> links;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.announcements, container, false);
        announcements =(ListView) view.findViewById(R.id.announcementList);

        new FetchAnnouncement().execute();

        return view;
    }


    private class FetchAnnouncement extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected Void doInBackground(Void... params) {

            try {
                announcementList= new ArrayList<String>();
                links= new ArrayList<String>();
                Document doc = Jsoup.connect("https://aybu.edu.tr/muhendislik/bilgisayar/").get();
                Element announcement = doc.select("div.caContent").first();
                Iterator<Element> iterator = announcement.select("div.cncItem").iterator();


                while(iterator.hasNext()){
                    Element div = iterator.next();
                    announcementList.add(div.text());
                    links.add(div.select("a").attr("href"));
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {

            ArrayAdapter adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, announcementList);
            announcements.setAdapter(adapter);
            announcements.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position,
                                        long id) {


                    String[] link = new String[10000];
                    for(int i=0;i<links.size();i++){
                        link[i]="http://www.ybu.edu.tr/muhendislik/bilgisayar/"+links.get(i).toString();
                    }

                    Uri uri = Uri.parse(link[position]);
                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                    startActivity(intent);

                }
            });
        }
    }
}
