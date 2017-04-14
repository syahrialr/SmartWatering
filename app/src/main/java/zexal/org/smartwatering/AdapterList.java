package zexal.org.smartwatering;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Ilham on 3/29/2017.
 */

public class AdapterList extends RecyclerView.Adapter<AdapterList.MyViewHolder> {

    private List<History> smartList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView k1, k2,k3 ;

        public MyViewHolder(View view) {
            super(view);
            k1 = (TextView) view.findViewById(R.id.kondisi1);
            k2 = (TextView) view.findViewById(R.id.kondisi2);
            k3 =(TextView) view.findViewById(R.id.kondisi3);
        }
    }


    public AdapterList(List<History> smartList) {
        this.smartList = smartList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.history_list, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        History h = smartList.get(position);
        holder.k1.setText(h.getKondisi1());
        holder.k2.setText(h.getKondisi2());
        holder.k3.setText(h.getKeterangan());

    }

    @Override
    public int getItemCount() {
        return smartList.size();
    }
}