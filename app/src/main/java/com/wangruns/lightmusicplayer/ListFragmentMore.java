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

public class ListFragmentMore extends Fragment{
    //create a adapter
    ArrayAdapter<String> musicAdapterMore;
    private TextView tv_more_songs;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //Create a root view for the list_fragment_layout
        final View list_fragment_layout_more = inflater.inflate(R.layout.list_fragment_layout_more, container, false);

        //judge the status of the internet
        Context context=getActivity();
        ConnectivityManager cm =(ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();
        if(!isConnected){
            //the internet does not work
            Toast.makeText(getActivity(),"Please check your internet",Toast.LENGTH_SHORT).show();
            return list_fragment_layout_more;
        }

        //get all the music names from the special catalog on the server
        final String[] musicNameArrayMore = {
                "http://mpianatra.com/Courses/files/Eminem - Not Afraid_audio",
                "http://mpianatra.com/Courses/files/The Internet Millionaires' Club",
                "http://mpianatra.com/Courses/files/You're Never Over",
                "http://mpianatra.com/Courses/files/不要认为自己没有用",
                "http://mpianatra.com/Courses/files/对面的女孩看过来",
                "http://www.wangruns.top/wp-content/uploads/2017/03/我愿意",
                "http://www.wangruns.top/wp-content/uploads/2017/03/Whistle",
                "http://www.wangruns.top/wp-content/uploads/2017/03/WeDoNotTalkAnyMore",
                "http://www.wangruns.top/wp-content/uploads/2017/03/Kids",
                "http://www.wangruns.top/wp-content/uploads/2017/03/IfILoseMyself",
                "http://www.wangruns.top/wp-content/uploads/2017/03/Apologize",
        };
        //get rid of the "http..."
        int musicLenght=musicNameArrayMore.length;
        final String musicName[]=new String[musicLenght];
        for(int i=0;i<musicLenght;i++){
            String tempHttp=musicNameArrayMore[i];
            musicName[i]=tempHttp.substring(tempHttp.lastIndexOf("/")+1,tempHttp.length());
        }

        //change musicArray to List to fix musicAdapter
        List<String> musicNameList=new ArrayList<String>(Arrays.asList(musicName));

        //initialize musicAdapter
        musicAdapterMore=new ArrayAdapter<String>(getActivity(),R.layout.layout_each_item_more,R.id.tv_each_element_more,musicNameList);

        //get the listView from the fragment of list_fragment_layout
        final ListView listViewMore=(ListView)list_fragment_layout_more.findViewById(R.id.listView_music_name_more);
        //fill the listView with musicAdapter
        listViewMore.setAdapter(musicAdapterMore);

        listViewMore.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //get the selected item name
                String selectedMusicName=null;
                switch (position){
                    case 0:selectedMusicName=musicNameArrayMore[position];
                        break;
                    case 1:selectedMusicName=musicNameArrayMore[position];
                        break;
                    case 2:selectedMusicName=musicNameArrayMore[position];
                        break;
                    case 3:selectedMusicName=musicNameArrayMore[position];
                        break;
                    case 4:selectedMusicName=musicNameArrayMore[position];
                        break;
                    case 5:selectedMusicName=musicNameArrayMore[position];
                        break;
                    case 6:selectedMusicName=musicNameArrayMore[position];
                        break;
                    case 7:selectedMusicName=musicNameArrayMore[position];
                        break;
                    case 8:selectedMusicName=musicNameArrayMore[position];
                        break;
                    case 9:selectedMusicName=musicNameArrayMore[position];
                        break;
                    case 10:selectedMusicName=musicNameArrayMore[position];
                        break;
                    default:selectedMusicName=musicNameArrayMore[0];
                        break;
                }
                //intent to play the selected music
                Intent intent=new Intent(getActivity(),MainActivity.class);
                intent.putExtra("musicName",selectedMusicName);
                startActivity(intent);

            }
        });

        return list_fragment_layout_more;

    }

}
