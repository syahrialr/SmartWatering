package zexal.org.smartwatering.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import zexal.org.smartwatering.Data;
import zexal.org.smartwatering.DataAdapter;
import zexal.org.smartwatering.R;
import zexal.org.smartwatering.RequestInterface;
import zexal.org.smartwatering.SoilAdapter;


/**
 * A simple {@link Fragment} subclass.
 */
public class Tanah extends Fragment {

    private RecyclerView recyclerView;
    private ArrayList<Data> data;
    private SoilAdapter adapter;
    String url = "http://krstudio.web.id";

    @BindView(R.id.real_condition)
    TextView suhuReal;

    public Tanah() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_tanah, container, false);
        ButterKnife.bind(this,v);

        Thread t = new Thread(){
            @Override
            public void run(){
                while (!isInterrupted()){

                    try {
                        Thread.sleep(1000);

                        if(getActivity() == null)
                            return;

                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                loadJSON();
                            }
                        });

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }
            }
        };

        t.start();

        initViews(v);

        return v;
    }

    private void updateTextView(String tanah) {
        suhuReal.setText(tanah+" \u0025");
    }

    private void initViews(View v) {
        recyclerView = (RecyclerView) v.findViewById(R.id.card_recycler_view);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        loadJSON();
    }

    private void loadJSON() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        RequestInterface request = retrofit.create(RequestInterface.class);
        Call<List<Data>> call = request.getJSON();
        call.enqueue(new Callback<List<Data>>() {
            @Override
            public void onResponse(Call<List<Data>> call, Response<List<Data>> response) {
                updateTextView(response.body().get(0).getSensorsoil());
                adapter = new SoilAdapter(response.body());
                recyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<Data>> call, Throwable t) {

            }
        });

    }

}
