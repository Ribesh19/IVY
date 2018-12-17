package com.example.ribeshmaharjan.ivy;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;

import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

//import org.json.JSONArray;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
//import org.json.JSONObject;

import static com.android.volley.Request.Method.POST;


/**
 * A simple {@link Fragment} subclass.
 */
public class ListingHelpFragment extends Fragment {
    Context mcontext;
    Spinner spinner;
    Button mbtnmapview;
    ImageButton mbtnsignuplogin;

    private static String URL1 = "https://api.ivyschool.in/api/getcity";
    private static String URL2 = "https://api.ivyschool.in/api/schools";
    ArrayList<String> list=new ArrayList<>();

    public ListingHelpFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View rootview= inflater.inflate(R.layout.fragment_listing_help, container, false);
        spinner=rootview.findViewById(R.id.spinner_listhelper);
        mbtnsignuplogin=rootview.findViewById(R.id.signup_login);
        final RecyclerView recyclerView =rootview.findViewById(R.id.recyclerview);
        final RequestQueue queue = Volley.newRequestQueue(Objects.requireNonNull(getActivity()));
        final StringRequest request_citynames = new StringRequest(Request.Method.GET,URL1, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(getContext(), "Response Received", Toast.LENGTH_SHORT).show();
                Log.d("MainActivity", response);
                try {
                    JSONObject jsonObject=new JSONObject(response);
                        JSONArray jsonArray=jsonObject.getJSONArray("results");
                       // Toast.makeText(getActivity(), response, Toast.LENGTH_SHORT).show();
                        for(int i=0;i<jsonArray.length();i++){
                            JSONObject jsonObject1=jsonArray.getJSONObject(i);
                            String city=jsonObject1.getString("city");
                            list.add(city);
                        }
                    //spinner.setAdapter(new ArrayAdapter<>(Objects.requireNonNull(getActivity()),R.layout.spinner_layout,R.id.spinner_txt_item, list));
                    SpinnerAdapter adapter=new SpinnerAdapter(getActivity(),R.layout.spinner_layout,R.id.spinner_txt_item,list);
                    spinner.setAdapter(adapter);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(), "error", Toast.LENGTH_SHORT).show();
            }
        });
        queue.add(request_citynames);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String city = spinner.getItemAtPosition(spinner.getSelectedItemPosition()).toString();
                // Toast.makeText(getContext(),city,Toast.LENGTH_LONG).show();

                JSONObject jsonObject_body = new JSONObject();
                if (city.equalsIgnoreCase("Lalitpur")){
                    try {
                        jsonObject_body.put("city","Lalitpur");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    final String mRequestBody = jsonObject_body.toString();
                    Toast.makeText(getActivity(), mRequestBody, Toast.LENGTH_SHORT).show();
                    JsonObjectRequest jsonObjectRequest_school_list_lalitpur = new JsonObjectRequest(Request.Method.POST, URL2, null, new Response.Listener<JSONObject>(){
                        @Override
                        public void onResponse(JSONObject response) {
                            Toast.makeText(getActivity(), "Response Received_schools", Toast.LENGTH_SHORT).show();
                            Log.d("MainActivity", response.toString());
                            try {
                                JSONArray jsonArray=response.getJSONArray("results");
                                final SchoollistAdapter recyclerview_adapter = new SchoollistAdapter(Objects.requireNonNull(getActivity()).getApplicationContext(),jsonArray);
                                recyclerView.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));
                                recyclerView.setAdapter(recyclerview_adapter);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }

                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            VolleyLog.e("Error: ", error.getMessage());
                            Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    })
                    {
                        @Override    public Map<String, String> getHeaders() throws AuthFailureError {
                            HashMap<String, String> headers = new HashMap<>();
                            headers.put("Content-Type", "application/json");
                            return headers;
                        }


                        @Override    public byte[] getBody() {
                            try {
                                return mRequestBody.getBytes("utf-8");
                            } catch (UnsupportedEncodingException uee) {
                                VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s",
                                        mRequestBody, "utf-8");
                                return null;
                            }
                        }
                    };

                   // RequestQueue queue = Volley.newRequestQueue(Objects.requireNonNull(getActivity()));
                    queue.add(jsonObjectRequest_school_list_lalitpur);
                    }
                    else if (city.equalsIgnoreCase("Kathmandu"))
                    {
                        try {
                            jsonObject_body.put("city","Kathmandu");
                         } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    final String mRequestBody = jsonObject_body.toString();
                    Toast.makeText(getActivity(), mRequestBody, Toast.LENGTH_SHORT).show();
                    JsonObjectRequest jsonObjectRequest_school_list_ktm = new JsonObjectRequest(Request.Method.POST, URL2, null, new Response.Listener<JSONObject>(){
                        @Override
                        public void onResponse(JSONObject response) {
                            Toast.makeText(getActivity(), "Response Received_schools", Toast.LENGTH_SHORT).show();
                            Log.d("MainActivity", response.toString());
                            try {
                                JSONArray jsonArray=response.getJSONArray("results");
                                final SchoollistAdapter recyclerview_adapter = new SchoollistAdapter(Objects.requireNonNull(getActivity()).getApplicationContext(),jsonArray);
                                recyclerView.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));
                                recyclerView.setAdapter(recyclerview_adapter);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }

                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            VolleyLog.e("Error: ", error.getMessage());
                            Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    })
                    {
                        @Override    public Map<String, String> getHeaders() throws AuthFailureError {
                            HashMap<String, String> headers = new HashMap<>();
                            headers.put("Content-Type", "application/json");
                            return headers;
                        }


                        @Override    public byte[] getBody() {
                            try {
                                return mRequestBody.getBytes("utf-8");
                            } catch (UnsupportedEncodingException uee) {
                                VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s",
                                        mRequestBody, "utf-8");
                                return null;
                            }
                        }
                    };

                    // RequestQueue queue = Volley.newRequestQueue(Objects.requireNonNull(getActivity()));
                    queue.add(jsonObjectRequest_school_list_ktm);

                }
                else if (city.equalsIgnoreCase("Kirtipur"))
                {
                    try {
                        jsonObject_body.put("city","Kirtipur");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    final String mRequestBody = jsonObject_body.toString();
                    Toast.makeText(getActivity(), mRequestBody, Toast.LENGTH_SHORT).show();
                    JsonObjectRequest jsonObjectRequest_school_list_ktm = new JsonObjectRequest(Request.Method.POST, URL2, null, new Response.Listener<JSONObject>(){
                        @Override
                        public void onResponse(JSONObject response) {
                            Toast.makeText(getActivity(), "Response Received_schools", Toast.LENGTH_SHORT).show();
                            Log.d("MainActivity", response.toString());
                            try {
                                JSONArray jsonArray=response.getJSONArray("results");
                                final SchoollistAdapter recyclerview_adapter = new SchoollistAdapter(Objects.requireNonNull(getActivity()).getApplicationContext(),jsonArray);
                                recyclerView.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));
                                recyclerView.setAdapter(recyclerview_adapter);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }

                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            VolleyLog.e("Error: ", error.getMessage());
                            Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    })
                    {
                        @Override    public Map<String, String> getHeaders() throws AuthFailureError {
                            HashMap<String, String> headers = new HashMap<>();
                            headers.put("Content-Type", "application/json");
                            return headers;
                        }


                        @Override    public byte[] getBody() {
                            try {
                                return mRequestBody.getBytes("utf-8");
                            } catch (UnsupportedEncodingException uee) {
                                VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s",
                                        mRequestBody, "utf-8");
                                return null;
                            }
                        }
                    };

                    // RequestQueue queue = Volley.newRequestQueue(Objects.requireNonNull(getActivity()));
                    queue.add(jsonObjectRequest_school_list_ktm);

                }





            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                // DO Nothing here
            }
        });

        /*ArrayList<ItemData> list=new ArrayList<>();
        list.add(new ItemData("Delhi"));
        list.add(new ItemData("Hauz Khas"));
        list.add(new ItemData("Lajpat Nagar"));
        list.add(new ItemData("Greater Kailash"));
        list.add(new ItemData("Rangpuri"));
        list.add(new ItemData("Noida"));
        SpinnerAdapter adapter=new SpinnerAdapter(getActivity(),R.layout.spinner_layout,R.id.spinner_txt_item,list);
        spinner.setAdapter(adapter);*/
        mbtnmapview=rootview.findViewById(R.id.btn_mapview);
        mbtnmapview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getContext(),MapActivity.class);
                startActivity(intent);
            }
        });

        /*final SchoollistAdapter recyclerview_adapter = new SchoollistAdapter(Objects.requireNonNull(getActivity()).getApplicationContext(),ImageAssets.getAll());
        recyclerView.setAdapter(recyclerview_adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));*/
        mbtnsignuplogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(),RegisterActivity.class);
                startActivity(intent);
            }
        });

        return rootview;
    }

}
