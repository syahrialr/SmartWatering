package zexal.org.smartwatering;

import android.support.v7.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by syahr on 14/04/2017.
 */

public class SoilAdapter extends RecyclerView.Adapter<SoilAdapter.ViewHolder> {
    private List<Data> datas;

    public SoilAdapter(List<Data> datas) {
        this.datas = datas;
    }


    @Override
    public SoilAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_row, viewGroup, false);
        return new SoilAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(SoilAdapter.ViewHolder viewHolder, int i) {
        viewHolder.tv_name.setText(datas.get(i).getSensorsoil());
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
