package com.example.fitnesscenter.fragments.member;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.fitnesscenter.R;

public class ViewAvailableClassesFragment extends Fragment {

    public static ViewAvailableClassesFragment newInstance() {
        return new ViewAvailableClassesFragment();
    }

    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        return inflater.inflate(R.layout.fragment_view_available_classes, container, false);
    }

    public void onViewCreated(View view, Bundle savedInstanceState){

    }

}
