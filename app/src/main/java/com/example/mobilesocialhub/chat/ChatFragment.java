package com.example.mobilesocialhub.chat;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mobilesocialhub.R;

import java.util.ArrayList;
import java.util.List;

public class ChatFragment extends Fragment {

    public final static String TAG = "TEST";

    private List<Msg> msgList = new ArrayList<Msg>();

    private EditText inputText;

    private Button send;

    private RecyclerView msgRecyclerView;

    private MsgAdapter adapter;

    public ChatFragment(){
        super();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chat_page, container, false);
        initMsgs(); // 初始化消息数据
        inputText =  view.findViewById(R.id.input_text);
        send =  view.findViewById(R.id.send);
        msgRecyclerView = view.findViewById(R.id.msg_recycler_view);

        Log.d(TAG, "running here");
        Log.d(TAG, String.valueOf(msgRecyclerView == null));
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        msgRecyclerView.setLayoutManager(layoutManager);



        adapter = new MsgAdapter(msgList);
        msgRecyclerView.setAdapter(adapter);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String content = inputText.getText().toString();
                if (!"".equals(content)) {
                    Msg msg = new Msg(content, Msg.TYPE_SENT);
                    msgList.add(msg);
                    adapter.notifyItemInserted(msgList.size() - 1); // 当有新消息时，刷新ListView中的显示
                    msgRecyclerView.scrollToPosition(msgList.size() - 1); // 将ListView定位到最后一行
                    inputText.setText(""); // 清空输入框中的内容
                }
            }
        });

        return view;
    }

    private void initMsgs() {
        Msg msg1 = new Msg("Hello Yangyang.", Msg.TYPE_RECEIVED);
        msgList.add(msg1);
        Msg msg2 = new Msg("Hi Mia", Msg.TYPE_SENT);
        msgList.add(msg2);
        Msg msg3 = new Msg("memeda. ", Msg.TYPE_RECEIVED);
        msgList.add(msg3);
    }

}
