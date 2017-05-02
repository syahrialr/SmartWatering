package zexal.org.smartwatering.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import zexal.org.smartwatering.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class Beranda extends Fragment {
LinearLayout li1;

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
        return v;
    }

}
