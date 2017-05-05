package zexal.org.smartwatering.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
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


/**
 * A simple {@link Fragment} subclass.
 */
public class Beranda extends Fragment {

    private RecyclerView recyclerView;
    private ArrayList<Data> data;
    private DataAdapter adapter;
    String url = "http://krstudio.web.id";


    @BindView(R.id.beranda_vtanah)TextView soilReal;
    @BindView(R.id.beranda_ktanah) TextView kondisi;
    @BindView(R.id.beranda_vsuhu) TextView suhuReal;
    @BindView(R.id.beranda_vudara) TextView humiReal;
    @BindView(R.id.lb1) LinearLayout l1;
    @BindView(R.id.lb2) LinearLayout l2;
    @BindView(R.id.lb3) LinearLayout l3;


    public Beranda() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v =inflater.inflate(R.layout.fragment_beranda, container, false);
        // Inflate the layout for this fragment

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

        ButterKnife.bind(this,v);
        return v;
    }

    private void updateTextView(String suhu) {
        if(Float.parseFloat(suhu)>=31.00) {
            suhuReal.setText(suhu + "°");
            l2.setBackgroundResource(R.color.merah);
        }else
        {
            suhuReal.setText(suhu + "°");
            l2.setBackgroundResource(R.color.colorPrimaryDark);
        }
    }
    private void updateTextHumi(String humi){
        if(Float.parseFloat(humi)>=50.00) {
            humiReal.setText(humi + "°");
            l3.setBackgroundResource(R.color.colorPrimaryDark);
        }
        else {
            humiReal.setText(humi + " \u0025");
            l3.setBackgroundResource(R.color.merah);
        }
    }
    private void updateTextSoil(String tanah,String kondisii){
        int temp;
        float hasil;
        float patokan=700;
        temp=Integer.parseInt(tanah);
        hasil=temp/patokan*100;
        int hasil2= (int) hasil;
        if(Integer.parseInt(tanah)>=700)
        {
            soilReal.setText(hasil2+" \u0025");
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
                updateTextView(response.body().get(0).getTemp());
                updateTextHumi(response.body().get(0).getHumi());
                updateTextSoil(response.body().get(0).getSensorsoil(),response.body().get(0).getKondisisoil());
            }

            @Override
            public void onFailure(Call<List<Data>> call, Throwable t) {

            }
        });

    }

}
