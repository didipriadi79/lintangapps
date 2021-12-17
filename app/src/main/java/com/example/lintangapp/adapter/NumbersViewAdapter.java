package com.example.lintangapp.adapter;

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

import java.util.ArrayList;

public class NumbersViewAdapter extends ArrayAdapter<NumbersView> {

    public NumbersViewAdapter(@NonNull Context context, ArrayList<NumbersView> arrayList) {
        super(context, 0, arrayList);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View currentItemView = convertView;

        if (currentItemView == null) {
            currentItemView = LayoutInflater.from(getContext()).inflate(R.layout.user_item, parent, false);
        }

        NumbersView currentNumberPosition = getItem(position);

        ImageView numbersImage = currentItemView.findViewById(R.id.avatar);
        assert currentNumberPosition != null;
        numbersImage.setImageResource(currentNumberPosition.getNumbersImageId());

        TextView textView1 = currentItemView.findViewById(R.id.name);
        textView1.setText(currentNumberPosition.getNumberInDigit());

        TextView textView2 = currentItemView.findViewById(R.id.role);
        textView2.setText(currentNumberPosition.getNumbersInText());

        return currentItemView;
    }
}