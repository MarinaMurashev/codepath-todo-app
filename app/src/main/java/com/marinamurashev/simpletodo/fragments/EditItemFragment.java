package com.marinamurashev.simpletodo.fragments;


import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.marinamurashev.simpletodo.R;

public class EditItemFragment extends DialogFragment {

    public EditItemFragment(){

    }

    public static EditItemFragment newInstance(){
        EditItemFragment fragment = new EditItemFragment();
        Bundle args = new Bundle();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_edit_item, container);
        return view;
    }
}
