package com.wangruns.lightmusicplayer;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Administrator on 2017/3/21.
 */

public class ListFragment extends Fragment{
    //create a adapter
    ArrayAdapter<String> musicAdapter;
    private TextView tv_more_songs;
    private static boolean isMoreSongs=false;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //Create a root view for the list_fragment_layout
        final View list_fragment_layout = inflater.inflate(R.layout.list_fragment_layout, container, false);

        /**
         * if the tv_more_songs is clicked,more music will be loaded from other servers
         */
        tv_more_songs=(TextView)list_fragment_layout.findViewById(R.id.tv_more_songs);
        tv_more_songs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isMoreSongs=true;

                //intent to play the selected music
                Intent intent=new Intent(getActivity(),ListActivityMore.class);
                startActivity(intent);
                getActivity().finish();

            }
        });



        //judge the status of the internet
        Context context=getActivity();
        ConnectivityManager cm =(ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();
        if(!isConnected){
            //the internet does not work
            Toast.makeText(getActivity(),"Please check your internet",Toast.LENGTH_SHORT).show();
            return list_fragment_layout;
        }

        //get all the music names from the special catalog on the server
        final String[] musicNameArray = {
                "http://mpianatra.com/Courses/files/Eminem - Not Afraid_audio",
                "http://mpianatra.com/Courses/files/The Internet Millionaires' Club",
                "http://mpianatra.com/Courses/files/You're Never Over",
                "http://mpianatra.com/Courses/files/不要认为自己没有用",
                "http://mpianatra.com/Courses/files/对面的女孩看过来",};
        //get rid of the "http..."
        int musicLenght=musicNameArray.length;
        final String musicName[]=new String[musicLenght];
        for(int i=0;i<musicLenght;i++){
            String tempHttp=musicNameArray[i];
            musicName[i]=tempHttp.substring(tempHttp.lastIndexOf("/")+1,tempHttp.length());
        }


        //change musicArray to List to fix musicAdapter
        List<String> musicNameList=new ArrayList<String>(Arrays.asList(musicName));

        //initialize musicAdapter
        musicAdapter=new ArrayAdapter<String>(getActivity(),R.layout.layout_each_item,R.id.tv_each_element,musicNameList);

        //get the listView from the fragment of list_fragment_layout
        final ListView listView=(ListView)list_fragment_layout.findViewById(R.id.listView_music_name);
        //fill the listView with musicAdapter
        listView.setAdapter(musicAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //get the selected item name
                String selectedMusicName=null;
                switch (position){
                    case 0:selectedMusicName=musicNameArray[position];
                        break;
                    case 1:selectedMusicName=musicNameArray[position];
                        break;
                    case 2:selectedMusicName=musicNameArray[position];
                        break;
                    case 3:selectedMusicName=musicNameArray[position];
                        break;
                    case 4:selectedMusicName=musicNameArray[position];
                        break;

                    default:selectedMusicName=musicNameArray[0];
                        break;
                }
                //intent to play the selected music
                Intent intent=new Intent(getActivity(),MainActivity.class);
                intent.putExtra("musicName",selectedMusicName);
                startActivity(intent);

            }
        });

        return list_fragment_layout;

    }

}
