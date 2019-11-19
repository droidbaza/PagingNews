package com.droidbaza.pagingnews.mvp.view;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import com.droidbaza.pagingnews.R;
import com.droidbaza.pagingnews.mvp.MainContract;
import com.droidbaza.pagingnews.mvp.presenter.MainPresenter;
import com.droidbaza.pagingnews.paging.NewsAdapter;
import com.github.pwittchen.reactivenetwork.library.rx2.ReactiveNetwork;
import io.reactivex.Completable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class MainFragment extends Fragment implements MainContract.MainView {

    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;

    @Override
        public View onCreateView (@NonNull LayoutInflater inflater,
                                  ViewGroup container, Bundle savedInstanceState){

        View v = inflater.inflate(R.layout.fragment_main, container, false);
        recyclerView = v.findViewById(R.id.recyclerview);
        swipeRefreshLayout = v.findViewById(R.id.swipe);
        FrameLayout card = v.findViewById(R.id.card);
        Button retryBut = v.findViewById(R.id.retry);

        ReactiveNetwork
                .observeInternetConnectivity()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(isConnectedToInternet -> {
                    if(isConnectedToInternet){
                       card.setVisibility(View.GONE);
                    }else {
                        card.setVisibility(View.VISIBLE);
                        Toast.makeText(getContext(),getString(R.string.error_download),Toast.LENGTH_LONG).show();

                    }
                    retryBut.setOnClickListener(v1 -> Toast.makeText(getContext(),getString(R.string.error_download),Toast.LENGTH_LONG).show());
                }).isDisposed();

            NewsAdapter adapter = new NewsAdapter(getContext());
            LinearLayoutManager lm = new LinearLayoutManager(getContext());
            recyclerView.setLayoutManager(lm);
            recyclerView.setAdapter(adapter);

            createPresenter();
            swipeRefreshLayout.setOnRefreshListener(() -> {
               createPresenter();
                final Handler handler = new Handler();
                handler.postDelayed(() -> {
                    if(swipeRefreshLayout.isRefreshing()) {
                        swipeRefreshLayout.setRefreshing(false);
                    }
                }, 1000);

            });

            return v;
        }

    private void createPresenter() {
        MainContract.Presenter presenter = new MainPresenter(MainFragment.this,
                getContext(), getActivity());
        presenter.getAdapter();
    }


    @Override
        public void setAdapter (NewsAdapter adapter){
            Completable.fromAction(() -> recyclerView.setAdapter(adapter))
                    .observeOn(Schedulers.computation())
                    .subscribe();
        }

}