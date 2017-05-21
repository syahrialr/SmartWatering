package zexal.org.smartwatering.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.aakira.expandablelayout.ExpandableRelativeLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import zexal.org.smartwatering.Adapter.HumiAdapter;
import zexal.org.smartwatering.Adapter.SoilAdapter;
import zexal.org.smartwatering.Data;
import zexal.org.smartwatering.Adapter.DataAdapter;
import zexal.org.smartwatering.R;
import zexal.org.smartwatering.RequestInterface;


/**
 * A simple {@link Fragment} subclass.
 */
public class Beranda extends Fragment implements View.OnClickListener{

    private RecyclerView recyclerView, recyclerView2, recyclerView3;
    private ArrayList<Data> data;
    private SoilAdapter adapter;
    private HumiAdapter adapter2;
    private DataAdapter adapter3;
    String url = "http://krstudio.web.id";


    @BindView(R.id.beranda_vtanah)TextView soilReal;
    @BindView(R.id.beranda_ktanah) TextView kondisi;
    @BindView(R.id.beranda_vsuhu) TextView suhuReal;
    @BindView(R.id.beranda_vudara) TextView humiReal;
    @BindView(R.id.lb1) LinearLayout l1;
    @BindView(R.id.lb2) LinearLayout l2;
    @BindView(R.id.lb3) LinearLayout l3;
    private ExpandableRelativeLayout mExpandLayout,  mExpandLayout2,  mExpandLayout3;





    public Beranda() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v =inflater.inflate(R.layout.fragment_beranda, container, false);
        // Inflate the layout for this fragment
        ButterKnife.bind(this,v);

        mExpandLayout = (ExpandableRelativeLayout) v.findViewById(R.id.expandableLayout);
        mExpandLayout2 = (ExpandableRelativeLayout) v.findViewById(R.id.expandableLayout2);
        mExpandLayout3 = (ExpandableRelativeLayout) v.findViewById(R.id.expandableLayout3);
        l1.setOnClickListener(this);
        l2.setOnClickListener(this);
        l3.setOnClickListener(this);

        recyclerView = (RecyclerView) v.findViewById(R.id.list_tanah);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);

        recyclerView2 = (RecyclerView) v.findViewById(R.id.list_udara);
        recyclerView2.setHasFixedSize(true);
        LinearLayoutManager layoutManager2 = new LinearLayoutManager(getContext());
        layoutManager2.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView2.setLayoutManager(layoutManager2);

        recyclerView3 = (RecyclerView) v.findViewById(R.id.list_suhu);
        recyclerView3.setHasFixedSize(true);
        LinearLayoutManager layoutManager3 = new LinearLayoutManager(getContext());
        layoutManager3.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView3.setLayoutManager(layoutManager3);


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
                                loadJSONRealtime();
                            }
                        });


                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }
            }
        };

        t.start();

        loadJSON();

        return v;
    }

    private void updateTextView(String suhu) {
        int x =(int) Float.parseFloat(suhu);
        if(x>=31) {
            suhuReal.setText(String.valueOf(x) + "°C");
            l2.setBackgroundResource(R.color.merah);
        }else
        {
            suhuReal.setText(String.valueOf(x) + "°C");
            l2.setBackgroundResource(R.color.colorPrimaryDark);
        }
    }
    private void updateTextHumi(String humi){
        int x = (int) Float.parseFloat(humi);
        if(x>=50) {
            humiReal.setText(String.valueOf(x) + "\u0025");
            l3.setBackgroundResource(R.color.colorPrimaryDark);
        }
        else {
            humiReal.setText(String.valueOf(x) + " \u0025");
            l3.setBackgroundResource(R.color.merah);
        }
    }
    private void updateTextSoil(String tanah,String kondisii){
        int temp;
        float hasil;
        float patokan=700;
        temp= (int) Float.parseFloat(tanah);
        hasil=temp/patokan*100;
        int hasil2= (int) hasil;
        if(Integer.parseInt(tanah)>=700)
        {
            soilReal.setText(100+" \u0025");
            kondisi.setText(kondisii);
            l1.setBackgroundResource(R.color.colorPrimaryDark);
        }else if(Integer.parseInt(tanah)>=300 && Integer.parseInt(tanah)<=700)
        {
            soilReal.setText(hasil2+" \u0025");
            kondisi.setText(kondisii);
            l1.setBackgroundResource(R.color.hijau);


        }else if(Integer.parseInt(tanah)<=300)
        {
            soilReal.setText(hasil2+" \u0025");
            kondisi.setText(kondisii);
            l1.setBackgroundResource(R.color.merah);

        }

    }

    private void loadJSONRealtime() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        RequestInterface request = retrofit.create(RequestInterface.class);
        Call<List<Data>> call = request.getJSON();
        call.enqueue(new Callback<List<Data>>() {
            @Override
            public void onResponse(Call<List<Data>> call, Response<List<Data>> response) {
                updateTextView(response.body().get(0).getTemp());
                updateTextHumi(response.body().get(0).getHumi());
                updateTextSoil(response.body().get(0).getSensorsoil(),response.body().get(0).getKondisisoil());

            }

            @Override
            public void onFailure(Call<List<Data>> call, Throwable t) {

            }
        });

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

                adapter = new SoilAdapter(response.body());
                recyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();

                adapter2 = new HumiAdapter(response.body());
                recyclerView2.setAdapter(adapter2);
                adapter2.notifyDataSetChanged();

                adapter3 = new DataAdapter(response.body());
                recyclerView3.setAdapter(adapter3);
                adapter3.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<Data>> call, Throwable t) {

            }
        });

    }


    @Override
    public void onClick(final View v) {
        switch (v.getId()) {
            case R.id.lb1:
                mExpandLayout.toggle();
                break;
            case R.id.lb3:
                mExpandLayout2.toggle();
                break;
            case R.id.lb2:
                mExpandLayout3.toggle();
                break;
        }
    }
}
