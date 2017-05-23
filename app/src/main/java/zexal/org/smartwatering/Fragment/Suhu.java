package zexal.org.smartwatering.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.github.lzyzsd.circleprogress.ArcProgress;

import org.eazegraph.lib.charts.ValueLineChart;
import org.eazegraph.lib.models.ValueLinePoint;
import org.eazegraph.lib.models.ValueLineSeries;

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
import zexal.org.smartwatering.Adapter.DataAdapter;
import zexal.org.smartwatering.DataGrahp;
import zexal.org.smartwatering.R;
import zexal.org.smartwatering.RequestInterface;


/**
 * A simple {@link Fragment} subclass.
 */
public class Suhu extends Fragment {

    private RecyclerView recyclerView;
    private ArrayList<Data> data;
    private DataAdapter adapter;
    String url = "http://krstudio.web.id";


    @BindView(R.id.arc_progress3) ArcProgress progress;
    @BindView(R.id.lsuhu) LinearLayout linear;
    @BindView(R.id.chartsuhu)
    ValueLineChart mCubicValueLineChart;
    ArrayList<DataGrahp> datagraph = new ArrayList<>();


    public Suhu() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_suhu, container, false);
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
        loadgraphJSON();
        return v;
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();

    }

    private void updateTextSuhu(String suhu) {
        int x =(int) Float.parseFloat(suhu);
        if(x>=31) {
           progress.setProgress(x);
            linear.setBackgroundResource(R.color.merah);
        }else
        {
            progress.setProgress(x);
            linear.setBackgroundResource(R.color.colorPrimaryDark);
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
                updateTextSuhu(response.body().get(0).getTemp());
                /*adapter = new DataAdapter(response.body());
                recyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();*/
            }

            @Override
            public void onFailure(Call<List<Data>> call, Throwable t) {

            }
        });

    }
    private void loadgraphJSON() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        RequestInterface request = retrofit.create(RequestInterface.class);
        Call<List<Data>> call = request.getJSON();
        call.enqueue(new Callback<List<Data>>() {
            @Override
            public void onResponse(Call<List<Data>> call, Response<List<Data>> response) {
                for (int i = 0; i < response.body().size(); i++) {
                    String[] split = response.body().get(i).getTime().split(" ");
                    datagraph.add(new DataGrahp(split[1],Float.parseFloat(response.body().get(i).getTemp())));
                }

                ValueLineSeries series = new ValueLineSeries();
                series.setColor(0xFF56B7F1);

                for (int i = 0; i < datagraph.size(); i++) {
                    series.addPoint(new ValueLinePoint(datagraph.get(i).getLabel(), datagraph.get(i).getValue()));
                }

                mCubicValueLineChart.addSeries(series);
                mCubicValueLineChart.startAnimation();
            }

            @Override
            public void onFailure(Call<List<Data>> call, Throwable t) {

            }
        });

    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}
