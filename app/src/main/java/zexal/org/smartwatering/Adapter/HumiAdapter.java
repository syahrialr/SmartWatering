package zexal.org.smartwatering.Adapter;

import android.support.v7.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import zexal.org.smartwatering.Data;
import zexal.org.smartwatering.R;

/**
 * Created by syahr on 14/04/2017.
 */

public class HumiAdapter extends RecyclerView.Adapter<HumiAdapter.ViewHolder> {
    private List<Data> datas;

    public HumiAdapter(List<Data> datas) {
        this.datas = datas;
    }


    @Override
    public HumiAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_row, viewGroup, false);
        return new HumiAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(HumiAdapter.ViewHolder viewHolder, int i) {

        int x = (int) Float.parseFloat(datas.get(i).getHumi());
        viewHolder.text1.setText(x+"\u0025");
        viewHolder.text2.setText(datas.get(i).getTime());
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView text1,text2;
        public ViewHolder(View view) {
            super(view);

            text1 = (TextView)view.findViewById(R.id.txt1);
            text2 = (TextView)view.findViewById(R.id.txt2);

        }
    }
}
