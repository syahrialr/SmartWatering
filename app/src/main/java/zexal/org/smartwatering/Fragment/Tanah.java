package zexal.org.smartwatering.Fragment;


import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.lzyzsd.circleprogress.ArcProgress;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import zexal.org.smartwatering.Data;
import zexal.org.smartwatering.R;
import zexal.org.smartwatering.RequestInterface;
import zexal.org.smartwatering.Adapter.SoilAdapter;


/**
 * A simple {@link Fragment} subclass.
 */
public class Tanah extends Fragment {

    private RecyclerView recyclerView;
    private ArrayList<Data> data;
    private SoilAdapter adapter;
    String url = "http://krstudio.web.id";
    private Timer timer;


    @BindView(R.id.ltanah) LinearLayout linear;
    @BindView(R.id.arc_progress)
    ArcProgress progress;
    @BindView(R.id.ktanah) TextView kt;
    @BindView(R.id.arc_progress2)
    ArcProgress progress2;
    @BindView(R.id.ktanah2) TextView kt2;


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

    private void updateTextView(String tanah,String kondisii) {
        int temp;
        float hasil;
        float patokan=700;
        temp= (int) Float.parseFloat(tanah);
        hasil=temp/patokan*100;
        int hasil2= (int) hasil;
        if(Integer.parseInt(tanah)>=700)
        {
            progress.setProgress(100);
            kt.setText(kondisii);
            linear.setBackgroundResource(R.color.colorPrimaryDark);
        }else if(Integer.parseInt(tanah)>=300 && Integer.parseInt(tanah)<=700)
        {
            progress.setProgress(hasil2);
            kt.setText(kondisii);
            linear.setBackgroundResource(R.color.hijau);
        }else if(Integer.parseInt(tanah)<=300)
        {
            progress.setProgress(hasil2);
            kt.setText(kondisii);
            linear.setBackgroundResource(R.color.merah);
        }



    }
    private void updateTextView2(String tanah,String kondisii)
    {
        int temp;
        float hasil;
        float patokan=700;
        temp= (int) Float.parseFloat(tanah);
        hasil=temp/patokan*100;
        int hasil2= (int) hasil;
        if(Integer.parseInt(tanah)>=700)
        {
            progress2.setProgress(100);
            kt2.setText(kondisii);
        }else if(Integer.parseInt(tanah)>=300 && Integer.parseInt(tanah)<=700)
        {
            progress2.setProgress(hasil2);
            kt2.setText(kondisii);
        }else if(Integer.parseInt(tanah)<=300)
        {
            progress2.setProgress(hasil2);
            kt2.setText(kondisii);
        }

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
                updateTextView(response.body().get(0).getSensorsoil(),response.body().get(0).getKondisisoil());
                updateTextView2(response.body().get(0).getSensorsoil2(),response.body().get(0).getKondisisoil2());

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
