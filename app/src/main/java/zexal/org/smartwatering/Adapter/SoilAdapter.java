package zexal.org.smartwatering.Adapter;

import android.support.v7.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.github.aakira.expandablelayout.ExpandableLinearLayout;

import java.util.List;

import zexal.org.smartwatering.Data;
import zexal.org.smartwatering.R;

/**
 * Created by syahr on 14/04/2017.
 */

public class SoilAdapter extends RecyclerView.Adapter<SoilAdapter.ViewHolder> {
    private List<Data> datas,datas2;

    public SoilAdapter(List<Data> datas) {
        this.datas = datas;
    }


    @Override
    public SoilAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_row2, viewGroup, false);
        return new SoilAdapter.ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(SoilAdapter.ViewHolder viewHolder, int i) {

        int x = Integer.parseInt(datas.get(i).getSensorsoil());
        int y = Integer.parseInt(datas.get(i).getSensorsoil2());
        int temp;
        float hasil;
        float patokan=700;
        int hasil2;
        float hasil3;
        int hasil4;
        int temp2;

        temp= x;
        temp2=y;
        hasil=temp/patokan*100;
        hasil2= (int) hasil;
        hasil3=temp2/patokan*100;
        hasil4= (int) hasil3;

        if(x>=700)
        {
            viewHolder.textsoil1.setText(String.valueOf(100)+"\u0025"+" Sensor Soil 1");
            viewHolder.textsoil2.setText(datas.get(i).getTime());
            viewHolder.textsoil3.setText(datas.get(i).getKondisisoil());
            viewHolder.textsoil4.setText(String.valueOf(100)+"\u0025"+" Sensor Soil 2");
            viewHolder.textsoil5.setText(datas.get(i).getTime());
            viewHolder.textsoil6.setText(datas.get(i).getKondisisoil2());
        }else if(x<=700)
        {
            viewHolder.textsoil1.setText(String.valueOf(hasil2)+"\u0025"+" Sensor Soil 1");
            viewHolder.textsoil2.setText(datas.get(i).getTime());
            viewHolder.textsoil3.setText(datas.get(i).getKondisisoil());
            viewHolder.textsoil4.setText(String.valueOf(hasil4)+"\u0025"+" Sensor Soil 2");
            viewHolder.textsoil5.setText(datas.get(i).getTime());
            viewHolder.textsoil6.setText(datas.get(i).getKondisisoil2());


        }else if(x<=300)
        {
            viewHolder.textsoil1.setText(String.valueOf(hasil2)+"\u0025"+" Sensor Soil 1");
            viewHolder.textsoil2.setText(datas.get(i).getTime());
            viewHolder.textsoil3.setText(datas.get(i).getKondisisoil());
            viewHolder.textsoil4.setText(String.valueOf(hasil4)+"\u0025"+" Sensor Soil 2");
            viewHolder.textsoil5.setText(datas.get(i).getTime());
            viewHolder.textsoil6.setText(datas.get(i).getKondisisoil2());

        }


    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView textsoil1,textsoil2,textsoil3,textsoil4,textsoil5,textsoil6;
        public ViewHolder(View view) {
            super(view);

            textsoil1 = (TextView)view.findViewById(R.id.cv1);
            textsoil2 = (TextView)view.findViewById(R.id.cv2);
            textsoil3=(TextView) view.findViewById(R.id.cv3);
            textsoil4 = (TextView)view.findViewById(R.id.cv4);
            textsoil5 = (TextView)view.findViewById(R.id.cv5);
            textsoil6=(TextView) view.findViewById(R.id.cv6);

        }
    }
}
