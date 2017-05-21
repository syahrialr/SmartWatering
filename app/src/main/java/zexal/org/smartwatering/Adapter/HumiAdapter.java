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
        viewHolder.tv_name.setText(x+"%");
        viewHolder.tv_version.setText(datas.get(i).getTime());
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView tv_name,tv_version;
        public ViewHolder(View view) {
            super(view);

            tv_name = (TextView)view.findViewById(R.id.tv_name);
            tv_version = (TextView)view.findViewById(R.id.tv_version);

        }
    }
}
