package com.example.admin.paginationdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.admin.paginationdemo.Pagination.LatestNewsResponse;
import com.example.admin.paginationdemo.Pagination.WebRequest;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements WebRequest.Listener {
    RecyclerView rc;
    LinearLayoutManager linearLayoutManager;
    ProgressBar progressBar;
    LetestNewsAdapter letestNewsAdapter;
    int page = 1;

    ArrayList<LatestNewsResponse> newsResponseList = new ArrayList<>();


    private int Pagenumber=1;
//    private int ItemCount=10;

    //varible for pagination
    public boolean isLoading;
    int Pastvisible,visibleitemcout,totalitemcount,privioustotalcount=0;
    private  int viewthreshould;
    private WebRequest webRequest;
    private int totalPage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        progressBar=findViewById(R.id.progress1);
        rc=findViewById(R.id.rcv);

        linearLayoutManager=new LinearLayoutManager(this);
        rc.setLayoutManager(linearLayoutManager);
        rc.setHasFixedSize(true);


        webRequest = new WebRequest(this);
        fetchNews(1,LatestNewsResponse.SELECTED_CATEGROY);
        rc.addOnScrollListener(new Pagination());
    }


    public void performPagination(int page){
        Toast.makeText(this, "End, please fetch new data " + page, Toast.LENGTH_SHORT).show();
        fetchNews(page,LatestNewsResponse.SELECTED_CATEGROY);
    }

    private void fetchNews(int page, String category){
        webRequest.getNewsRequest(page,category,this);
    }

    @Override
    public void onNewsResponse(ArrayList<LatestNewsResponse> newsModelList, int currentPage, int totalPage) {
        Toast.makeText(this, ""+ currentPage, Toast.LENGTH_SHORT).show();
        Toast.makeText(this, ""+ totalPage, Toast.LENGTH_SHORT).show();
        page = currentPage;
        this.totalPage = totalPage;
        isLoading = true;
        newsResponseList.addAll(newsModelList);

        if(letestNewsAdapter == null){
            letestNewsAdapter = new LetestNewsAdapter(this,newsResponseList);
            rc.setAdapter(letestNewsAdapter);
        }else{
            letestNewsAdapter.notifyDataSetChanged();
            //rc.setAdapter(letestNewsAdapter);

        }
    }

    class Pagination extends RecyclerView.OnScrollListener{

        int firstVisibleItem, visibleItemCount, totalItemCount;

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);

            visibleItemCount = rc.getChildCount();
            totalItemCount = linearLayoutManager.getItemCount();
            firstVisibleItem = linearLayoutManager.findFirstVisibleItemPosition();


            if(isLoading) {
                if (firstVisibleItem + visibleItemCount == totalItemCount) {
                    page = page+1;
                    if(page<totalPage) {
                        performPagination(page);
                    }
                    isLoading = false;
                }

            }

        }
    }
}
//https://www.examsplanner.in/epdesk/api/news/?page=1
