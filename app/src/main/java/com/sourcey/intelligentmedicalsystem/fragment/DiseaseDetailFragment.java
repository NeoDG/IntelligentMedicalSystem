package com.sourcey.intelligentmedicalsystem.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sourcey.intelligentmedicalsystem.R;

/**
 * 疾病详情分页的fragment
 */

public class DiseaseDetailFragment extends Fragment {
    private String content;

     public static Fragment newInstance(String content){
         DiseaseDetailFragment fragment = new DiseaseDetailFragment();
         fragment.content=content;
         return fragment;
     }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.first_fragment_layout,null);
        TextView textView= (TextView) view.findViewById(R.id.content);
        textView.setText(content);
//        textView.setMovementMethod(ScrollingMovementMethod.getInstance());

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }


}
