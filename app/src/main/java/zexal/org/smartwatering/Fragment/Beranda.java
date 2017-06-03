package zexal.org.smartwatering.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.github.aakira.expandablelayout.ExpandableRelativeLayout;
import com.github.lzyzsd.circleprogress.ArcProgress;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
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
    @BindView(R.id.brd_progress_dev1) ArcProgress progress1;
    @BindView(R.id.brd_progress_dev2) ArcProgress progress2;
    @BindView(R.id.brd_progress_udara) ArcProgress progress3;
    @BindView(R.id.brd_progress_suhu) ArcProgress progress4;
    @BindView(R.id.brdktanah) TextView ktanah1;
    @BindView(R.id.brdktanah2) TextView ktanah2;
    @BindView(R.id.utamalayout) LinearLayout utamanya;
    @BindView(R.id.errorlayout) LinearLayout error;
    @BindView(R.id.brd_ksuhu) TextView ksuhu;
    @BindView(R.id.brd_kudara) TextView kudara;


    private SoilAdapter adapter;
    private HumiAdapter adapter2;
    private DataAdapter adapter3;
    String url = "http://krstudio.web.id";

    @BindView(R.id.lb1) LinearLayout l1;
    @BindView(R.id.lb2) LinearLayout l2;
    @BindView(R.id.lb3) LinearLayout l3;
    private ExpandableRelativeLayout mExpandLayout,  mExpandLayout2,  mExpandLayout3;

    boolean datanull = false;



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

                        if(getActivity() == null || datanull)
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

        if(!datanull)
            t.start();

        loadJSON();

        return v;
    }

    private void updateTextSuhu(String suhu) {
        int x =(int) Float.parseFloat(suhu);
        if(x>=31) {
            progress4.setProgress(x);
            l2.setBackgroundResource(R.color.merah);
            ksuhu.setText("Ruangan Terlalu Panas");
        }else
        {
            progress4.setProgress(x);
            l2.setBackgroundResource(R.color.colorPrimaryDark);
            ksuhu.setText("Ruangan Sejuk");

        }
    }
    private void updateTextHumi(String humi){
        int x = (int) Float.parseFloat(humi);
        if(x>=50) {
            progress3.setProgress(x);
            l3.setBackgroundResource(R.color.colorPrimaryDark);
            kudara.setText("Ruangan Terasa Kering");

        }
        else {
            progress3.setProgress(x);
            l3.setBackgroundResource(R.color.merah);
            kudara.setText("Ruangan Lembab");
        }
    }
    private void updateTextTanah(String tanah,String tanah2){
        int temp;
        float hasil;
        float patokan=700;
        int hasil2;
        float hasil3;
        int hasil4;
        int temp2;
        temp= (int) Float.parseFloat(tanah);
        temp2=(int) Float.parseFloat(tanah2);
        hasil=temp/patokan*100;
        hasil2= (int) hasil;
        hasil3=temp2/patokan*100;
        hasil4= (int) hasil3;

        if(Integer.parseInt(tanah)>=700&&Integer.parseInt(tanah2)>=700)
        {
            progress1.setProgress(100);
            ktanah1.setText("Air Lebih dari Cukup");
            progress2.setProgress(100);
            ktanah2.setText("Air Lebih dari Cukup");

            l1.setBackgroundResource(R.color.colorPrimaryDark);
        }else if(Integer.parseInt(tanah)>=300 && Integer.parseInt(tanah)<=700&&Integer.parseInt(tanah2)>=300 && Integer.parseInt(tanah2)<=700)
        {
            progress1.setProgress(hasil2);
            ktanah1.setText("Tanah Lembab");
            progress2.setProgress(hasil4);
            ktanah2.setText("Tanah Lembab");

            l1.setBackgroundResource(R.color.hijau);
        }else if(Integer.parseInt(tanah)<=300&&Integer.parseInt(tanah)<=300)
        {
            progress1.setProgress(hasil2);
            ktanah1.setText("Tanah Kering");
            progress2.setProgress(hasil4);
            ktanah2.setText("Tanah Kering");

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
                if (response.body().size() == 0) {
                    Toast.makeText(getContext(),"Data Kosong",Toast.LENGTH_SHORT).show();
                    datanull=true;
                } else {
                    datanull = false;
                    updateTextSuhu(response.body().get(0).getTemp());
                    updateTextHumi(response.body().get(0).getHumi());
                    updateTextTanah(response.body().get(0).getSensorsoil(), response.body().get(0).getSensorsoil2());
                    // updateTextTanah2();
                }
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
                utamanya.setVisibility(View.VISIBLE);
                error.setVisibility(View.GONE);
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
                error.setVisibility(View.VISIBLE);
                utamanya.setVisibility(View.GONE);

            }
        });

    }
    @OnClick(R.id.refresher)
    public void refresh()
    {
        loadJSON();
    }

    @Override
    public void onClick(final View v) {
        switch (v.getId()) {
            case R.id.lb1:
                mExpandLayout.toggle();
                break;
            case R.id.lb2:
                mExpandLayout2.toggle();
                break;
            case R.id.lb3:
                mExpandLayout3.toggle();
                break;
        }
    }
}
