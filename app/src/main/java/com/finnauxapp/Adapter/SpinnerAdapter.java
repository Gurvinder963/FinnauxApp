package com.finnauxapp.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.finnauxapp.R;
import com.finnauxapp.Util.SpinnerModel;

import java.util.ArrayList;
import java.util.List;



/**
 * Created by intel on 10/22/2015.
 */
public class SpinnerAdapter extends ArrayAdapter<SpinnerModel>
{
    List<SpinnerModel> profiles;
    public SpinnerAdapter(Context context,List<SpinnerModel> profiles)
    {
        super(context, R.layout.spinner_item, profiles);
        this.profiles = profiles;
    }

    public void setProfiles(ArrayList<SpinnerModel> profiles)
    {
        this.profiles = profiles;
        clear();
        addAll(profiles);
    }

    public int getSelectedId(String id)
    {
        int position = 0;
        if(profiles == null || id == null)
            return position;
        for (SpinnerModel profile: profiles)
        {
                if(profile.getId().equals(id))
                    position = profiles.indexOf(profile);
        }
        return position;
    }

    public int getSelectedName(String name)
    {
        int position = 0;
        if(profiles == null || name == null)
            return position;
        for (SpinnerModel profile: profiles)
        {
            if(profile.getName().equals(name))
                position = profiles.indexOf(profile);
        }
        return position;
    }

    class ViewHolder
    {
        TextView tvSpinnerItem;
        public ViewHolder(View view)
        {
            tvSpinnerItem = (TextView) view.findViewById(R.id.textViewSpinnerItem);
        }

        public void bindSpinner(SpinnerModel profile)
        {
            tvSpinnerItem.setText(profile.getName());
        }
    }

    class DropDownHolder
    {
        TextView tvSpinnerItem;
        public DropDownHolder(View view)
        {
            tvSpinnerItem = (TextView) view.findViewById(R.id.textViewSpinnerItem);
        }

        public void bindSpinner(SpinnerModel profile)
        {
            tvSpinnerItem.setText(profile.getName());
        }
    }

    public String getID(int position)
    {
        return getItem(position).getId();
    }

    public String getName(int position)
    {
        return getItem(position).getName();
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent)
    {
        DropDownHolder holder;
        if(convertView == null)
        {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.spinner_item,parent,false);
            holder = new DropDownHolder(convertView);
            convertView.setTag(holder);
        }
        else
        {
            holder = (DropDownHolder) convertView.getTag();
        }
        holder.bindSpinner(getItem(position));
        return convertView;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        ViewHolder holder;
        if(convertView == null)
        {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.spinner_item,parent,false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }
        else
        {
            holder = (ViewHolder) convertView.getTag();
        }
            holder.bindSpinner(getItem(position));
        return convertView;
    }
}
