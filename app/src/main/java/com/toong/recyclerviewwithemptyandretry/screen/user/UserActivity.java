package com.toong.recyclerviewwithemptyandretry.screen.user;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import com.toong.recyclerviewwithemptyandretry.R;
import com.toong.recyclerviewwithemptyandretry.model.UserItem;
import com.toong.recyclerviewwithemptyandretry.screen.user.adapter.UserAdapter;
import com.toong.recyclerviewwithemptyandretry.widget.NetworkStateLayout;
import java.util.ArrayList;
import java.util.List;

public class UserActivity extends AppCompatActivity {
    private UserAdapter adapter;
    private List<UserItem> data = new ArrayList<>();
    private NetworkStateLayout networkStateLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        initViews();
        handleEvents();
    }

    private void initViews() {
        networkStateLayout = findViewById(R.id.layout_network_state);

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        LinearLayoutManager l = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(l);
        adapter = new UserAdapter();
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(
                new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        networkStateLayout.getFailView()
                .findViewById(R.id.button_retry)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        fakeLoadDataSuccess();
                    }
                });
    }

    private void handleEvents() {
        findViewById(R.id.test_load_data_empty).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fakeLoadDataEmpty();
            }
        });

        findViewById(R.id.test_load_data_failed).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fakeLoadDataFailed();
            }
        });

        findViewById(R.id.test_load_data_success).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fakeLoadDataSuccess();
            }
        });
    }

    private void fakeLoadDataEmpty() {
        adapter.clear();
        networkStateLayout.showLoadingView();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                networkStateLayout.showEmptyView();
                adapter.notifyDataSetChanged();
            }
        }, 1000);
    }

    private void fakeLoadDataFailed() {
        adapter.clear();
        networkStateLayout.showLoadingView();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                networkStateLayout.showFailView();
                adapter.notifyDataSetChanged();
            }
        }, 1000);
    }

    private void fakeLoadDataSuccess() {
        data.clear();
        networkStateLayout.showLoadingView();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 10; i++) {
                    data.add(new UserItem("" + i));
                }
                adapter.set(data);
                networkStateLayout.showSuccessView();
                adapter.notifyDataSetChanged();
            }
        }, 1000);
    }
}