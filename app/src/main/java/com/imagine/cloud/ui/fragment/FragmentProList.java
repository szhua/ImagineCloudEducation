package com.imagine.cloud.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.imagine.cloud.R;
import com.imagine.cloud.base.BaseFragment;
/**
 * Created by szhua on 2017/7/11/011.
 * github:https://github.com/szhua
 * ImagineCloudEducation
 * FragmentProList
 */
public class FragmentProList extends BaseFragment{


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_list_layout,container,false);
    }
}
