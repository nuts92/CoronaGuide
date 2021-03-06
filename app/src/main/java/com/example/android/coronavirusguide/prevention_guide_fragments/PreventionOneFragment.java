package com.example.android.coronavirusguide.prevention_guide_fragments;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.example.android.coronavirusguide.R;

/**
 * PreventionOneFragment subclass represents the first step in the Prevention Guide.
 */
public class PreventionOneFragment extends Fragment {

    public PreventionOneFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_prevention_guide, container, false);

        //Declaring and initializing the preventionImage object variable
        ImageView preventionImage = rootView.findViewById(R.id.prevention_guide_image);

        //Setting the right image for the preventionImage object variable in Prevention Guide Step One
        preventionImage.setImageResource(R.drawable.prevention_guide_slide_one);

        return rootView;
    }
}
