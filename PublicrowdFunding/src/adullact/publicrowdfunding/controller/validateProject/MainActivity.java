package adullact.publicrowdfunding.controller.validateProject;

import adullact.publicrowdfunding.R;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class MainActivity extends Fragment {

    public MainActivity() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_register, container, false);

        
        getActivity().setTitle("");
        return rootView;
    }
}
