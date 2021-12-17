package com.example.lintangapp.github;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.lintangapp.R;
import com.example.lintangapp.adapter.NumbersView;

public class GithubViewAdapter extends ArrayAdapter<GithubView> {
    public GithubViewAdapter(@NonNull Context context){
        super(context, 0);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View currentItemView = convertView;

        if (currentItemView == null) {
            currentItemView = LayoutInflater.from(getContext()).inflate(R.layout.user_item, parent, false);
        }

        return currentItemView;

    }
}
