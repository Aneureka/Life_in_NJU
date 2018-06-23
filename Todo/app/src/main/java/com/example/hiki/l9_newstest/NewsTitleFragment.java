package com.example.hiki.l9_newstest;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Hiki on 2016/9/6.
 */

public class NewsTitleFragment extends Fragment implements AdapterView.OnItemClickListener{

    private ListView newsTitleListView;
    private List<News> newsList;
    private NewsAdapter adapter;
    private boolean isTwoPane;

    @Override
    public void onAttach(Activity activity){
        super.onAttach(activity);
        newsList = getNews();
        adapter = new NewsAdapter(activity, R.layout.news_item, newsList);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.news_title_frag, container, false);
        newsTitleListView = (ListView) view.findViewById(R.id.news_title_list_view);
        newsTitleListView.setAdapter(adapter);
        newsTitleListView.setOnItemClickListener(this);

//        int height = newsTitleListView.getHeight();




        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);
        if(getActivity().findViewById(R.id.news_content_layout) != null){
            isTwoPane = true;
        }
        else{
            isTwoPane = false;
        }

    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
       News news = newsList.get(position);
        if(isTwoPane){
            NewsContentFragment newsContentFragment = (NewsContentFragment) getFragmentManager().findFragmentById(R.id.news_content_fragment);
            newsContentFragment.refresh(news.getTitle(), news.getContent());
        }
        else{
//            NewsContentActivity.actionStart(getActivity(), news.getTitle(), news.getContent());
            NewsContentActivity.actionStart(getActivity(), news.getTitle());
        }

    }

    public List<News> getNews(){
        List<News> newsList = new ArrayList<News>();

//        News newsa = new News();
//        newsa.setTitle("Succeed in College as a Learning Disabled Student");
//        newsa.setContent("College freshmen will soon learn to live with a roommate, adjust to a new social scene and survive less-than-stellar dining hall food. Students with learning disabilities will face these transitions while also grappling with a few more hurdles.");
//        newsList.add(newsa);
//
//        News newsb = new News();
//        newsb.setTitle("Google Android exec poached by China's Xiaomi");
//        newsb.setContent("China's Xiaomi has poached a key Google executive involved in the tech giant's Android phones, in a move seen as a coup for the rapidly growing Chinese smartphone maker.");
//        newsList.add(newsb);

        News news1 = new News();
        news1.setTitle("Monday");
        news1.setContent("");
        newsList.add(news1);

        News news2 = new News();
        news2.setTitle("Tuesday");
        news2.setContent("");
        newsList.add(news2);

        News news3 = new News();
        news3.setTitle("Wednesday");
        news3.setContent("");
        newsList.add(news3);

        News news4 = new News();
        news4.setTitle("Fourthday");
        news4.setContent("");
        newsList.add(news4);

        News news5 = new News();
        news5.setTitle("Friday");
        news5.setContent("");
        newsList.add(news5);

        News news6 = new News();
        news6.setTitle("Weekend");
        news6.setContent("");
        newsList.add(news6);

        return newsList;
    }

}
