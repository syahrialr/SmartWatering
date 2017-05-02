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
LinearLayout li1;

    private RecyclerView recyclerView;
    private ArrayList<Data> data;
    private DataAdapter adapter;
    String url = "http://krstudio.web.id";


    @BindView(R.id.beranda_vtanah)
    TextView soilReal;

    @BindView(R.id.beranda_vsuhu)
    TextView suhuReal;

    @BindView(R.id.beranda_vudara)
    TextView humiReal;

    public Beranda() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v =inflater.inflate(R.layout.fragment_beranda, container, false);
        li1=(LinearLayout) v.findViewById(R.id.l1);
        li1.setBackgroundResource(R.color.hijau);
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
        suhuReal.setText(suhu+" \u2103");
    }
    private void updateTextHumi(String humi){ humiReal.setText(humi+" \u0025");}
    private void updateTextSoil(String soil){ soilReal.setText(soil+" \u0025");}

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
                updateTextSoil(response.body().get(0).getSensorsoil());
            }

            @Override
            public void onFailure(Call<List<Data>> call, Throwable t) {

            }
        });

    }

}
