package com.dxzmpk.hongpeng.ui.actdetail;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import com.dxzmpk.hongpeng.databinding.ActDetailActivityBinding;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;


public class ActDetailActivity extends AppCompatActivity{

    private static final String KEY_FEED = "key_feed";


    String actId;

    private ActViewHandler viewHandler = null;

    // 这里是为了方便别的用户使用startActivity进行传参，可以学习这种方式，提高代码的可复用性。
    public static void startFeedDetailActivity(Context context, String actId) {
        Intent intent = new Intent(context, ActDetailActivity.class);
        intent.putExtra(KEY_FEED, actId);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        viewHandler = new ActViewHandler(this);
        actId = (String) getIntent().getSerializableExtra(KEY_FEED);

        viewHandler.bindInitData(actId);

    }

}