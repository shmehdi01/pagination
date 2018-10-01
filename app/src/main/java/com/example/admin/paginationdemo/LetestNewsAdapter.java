package com.example.admin.paginationdemo;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;


import com.example.admin.paginationdemo.Pagination.LatestNewsResponse;
import com.squareup.picasso.Picasso;

import java.util.List;


import static android.support.v7.widget.RecyclerView.*;

public class LetestNewsAdapter extends RecyclerView.Adapter<LetestNewsAdapter.MyViewHolder> {
    Context context;
    List<LatestNewsResponse> latestNewsRespons;
    private static final int ITEM = 0;
    private static final int LOADING = 1;


//variable for pagination
    private boolean isLoadingAdded = false;
    private String heading;

    public LetestNewsAdapter(Context context, List<LatestNewsResponse> latestNewsRespons) {
        this.context = context;
        this.latestNewsRespons = latestNewsRespons;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(context).inflate(R.layout.list_item,parent,false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {

        String headings=latestNewsRespons.get(position).getHeading();
        String head = Html.fromHtml(headings).toString();
        String logo=latestNewsRespons.get(position).getLogo();
        String imageUri="https://www.examsplanner.in/media/"+latestNewsRespons.get(position).getLogo()+"";
        Picasso.get().load(imageUri).into(holder.imageView);




        String keywords = Html.fromHtml(latestNewsRespons.get(position).getKeywords()).toString();
            holder.keyword.setText(keywords+"\n");
            holder.auther.setText("Published Date :-"+latestNewsRespons.get(position).getDate());
           holder.heading.setText(head+"\n");

           holder.itemView.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
//                   Intent intent=new Intent(context, WebViews.class);
//                 heading= String.valueOf(intent.putExtra("heading",latestNewsRespons.get(position).getHeading()));
//                   intent.putExtra("story",latestNewsRespons.get(position).getStory());
//                   intent.putExtra("auther",latestNewsRespons.get(position).getAuthor());
//                   intent.putExtra("url",latestNewsRespons.get(position).getUrl());
//
//                   context.startActivity(intent);
               }
           });
    }


    public void add(LatestNewsResponse mc) {
        latestNewsRespons.add(mc);
        notifyItemInserted(latestNewsRespons.size() - 1);
    }

    public void addAll(List<LatestNewsResponse> mcList) {
        for (LatestNewsResponse mc1 : mcList) {
            latestNewsRespons.add(mc1);
        }
        notifyDataSetChanged();
    }



    public void remove(LatestNewsResponse city) {
        int position = latestNewsRespons.indexOf(city);
        if (position > -1) {
            latestNewsRespons.remove(position);
            notifyItemRemoved(position);
        }
    }


    public boolean isEmpty() {
        return getItemCount() == 0;
    }
//
//
//
//
//    public void removeLoadingFooter() {
//        isLoadingAdded = false;
//
//        int position = latestNewsRespons.size() - 1;
//        LatestNewsResponse item = getItem(position);
//
//        if (item != null) {
//            latestNewsRespons.remove(position);
//            notifyItemRemoved(position);
//        }
//    }

    public LatestNewsResponse getItem(int position) {
        return latestNewsRespons.get(position);
    }

    @Override
    public int getItemCount() {
        return  latestNewsRespons == null ? 0 : latestNewsRespons.size();



    }



    public class MyViewHolder extends ViewHolder {
        TextView heading,keyword,auther;
        ImageView imageView;
        ProgressBar progressBar;
        public MyViewHolder(View itemView) {

            super(itemView);
            heading=itemView.findViewById(R.id.heading);
            imageView=itemView.findViewById( R.id.logo);
            keyword=itemView.findViewById(R.id.keywords);
            auther=itemView.findViewById(R.id.auther);
          //  progressBar=itemView.findViewById(R.id.progressbar);

        }
    }


}


//$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$

//@@@---https://blog.iamsuleiman.com/android-pagination-tutorial-recyclerview-multiple-view-types/