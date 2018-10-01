package com.example.admin.paginationdemo.Pagination;

import android.content.Context;
import android.support.annotation.NonNull;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class WebRequest {

    private Context context;

    public WebRequest(Context context) {
        this.context = context;
    }

    public static String BASE_URL = "https://www.examsplanner.in/";

    public static String API_URL = "epdesk/api/news/?page=";


    public void getNewsRequest(final int page, final String category, @NonNull final Listener listener) {

        String url = BASE_URL + API_URL + page;

        StringRequest request = new StringRequest(0, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                JSONArray array = null;
                try {
                    array = new JSONArray(response);
                    int page_count = array.getJSONObject(0).getInt("page_count");
                    int c_page = array.getJSONObject(1).getInt("page");
                    listener.onNewsResponse(parseResonse(response, category), c_page, page_count);
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(context, "crash " + e, Toast.LENGTH_SHORT).show();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show();
            }
        });


        RequestQueue queue = Volley.newRequestQueue(context);

        queue.add(request);

    }


    public interface Listener {
        public void onNewsResponse(ArrayList<LatestNewsResponse> newsModelList, int currentPage, int totalPage);
    }


    private ArrayList<LatestNewsResponse> parseResonse(String response, String selectedCategory) {
        ArrayList<LatestNewsResponse> list = new ArrayList<>();
        JSONArray jsonArray = null;
        try {
            jsonArray = new JSONArray(response);
            for (int i = 2; i < jsonArray.length(); i++) {


                JSONObject jsonObject = jsonArray.getJSONObject(i);

                LatestNewsResponse latestNewsResponse = new LatestNewsResponse();

                JSONObject jsonObject1 = jsonObject.getJSONObject("news");

                String category = jsonObject1.getString("category");

                if(selectedCategory.equalsIgnoreCase(LatestNewsResponse.SELECTED_CATEGROY)){
                    category = LatestNewsResponse.SELECTED_CATEGROY;
                }



                if (category.equalsIgnoreCase(selectedCategory)) {

                    latestNewsResponse.setHeading(jsonObject1.getString("heading"));
                    latestNewsResponse.setStory(jsonObject1.getString("story"));
                    latestNewsResponse.setUrl(jsonObject1.getString("url"));
                    latestNewsResponse.setAuthor(jsonObject1.getString("author"));
                    latestNewsResponse.setCategory(jsonObject1.getString("category"));
                    latestNewsResponse.setLogo(jsonObject1.getString("logo"));
                    latestNewsResponse.setDate(jsonObject1.getString("date"));
                    latestNewsResponse.setKeywords(jsonObject1.getString("keywords"));

                    list.add(latestNewsResponse);
                }
            }


            return list;

        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();

            return new ArrayList<>();
        }

    }
}
