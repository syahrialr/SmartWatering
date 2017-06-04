package zexal.org.smartwatering.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.github.lzyzsd.circleprogress.ArcProgress;


import org.eazegraph.lib.charts.ValueLineChart;
import org.eazegraph.lib.models.ValueLinePoint;
import org.eazegraph.lib.models.ValueLineSeries;

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
import zexal.org.smartwatering.Data;
import zexal.org.smartwatering.Adapter.HumiAdapter;
import zexal.org.smartwatering.DataGraph;
import zexal.org.smartwatering.R;
import zexal.org.smartwatering.RequestInterface;


/**
 * A simple {@link Fragment} subclass.
 */
public class Udara extends Fragment implements View.OnClickListener {

    ArrayList<DataGraph> datagraph = new ArrayList<>();
    String url = "http://krstudio.web.id";

    @BindView(R.id.utamaudara)
    LinearLayout utamanya;
    @BindView(R.id.errorlayout)
    LinearLayout error;
    @BindView(R.id.ludara)
    LinearLayout linear;
    @BindView(R.id.arc_progress2)
    ArcProgress progress;
    @BindView(R.id.cubiclinechart)
    ValueLineChart mCubicValueLineChart;
    @BindView(R.id.kudara)
    TextView ku;
    @BindView(R.id.kudara2)
    TextView ku2;
    @BindView(R.id.pb)
    ProgressBar progressBar;



    public Udara() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_udara, container, false);


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

    private void updateTextHumi(String udara) {
        int x = (int) Float.parseFloat(udara);
        if(x>=50) {
            progress.setProgress(x);
            linear.setBackgroundResource(R.color.colorPrimaryDark);
            ku.setText("Ruangan Terasa Kering");
            ku2.setVisibility(View.VISIBLE);
        }
            else {
                ku.setText("Ruangan Lembab");
                ku2.setVisibility(View.GONE);
                progress.setProgress(x);
                linear.setBackgroundResource(R.color.merah);
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
                updateTextHumi(response.body().get(0).getHumi());
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
                error.setVisibility(View.GONE);
                utamanya.setVisibility(View.VISIBLE);
                for (int i = 0; i < response.body().size(); i++) {
                    String[] split = response.body().get(i).getTime().split(" ");
                    datagraph.add(new DataGraph(split[1],Float.parseFloat(response.body().get(i).getHumi())));
                    Log.d("cek", "loadDataGraph: "+datagraph.get(i).getLabel());
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
                error.setVisibility(View.VISIBLE);
                utamanya.setVisibility(View.GONE);
            }
        });

    }

    @OnClick(R.id.refresher)
    public void refresh(){
        loadJSON();
        progressBar.setVisibility(View.VISIBLE);
    }


    @Override
    public void onClick(View view) {

    }
}