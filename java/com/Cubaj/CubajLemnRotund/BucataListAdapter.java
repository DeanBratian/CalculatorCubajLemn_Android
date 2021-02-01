package com.Cubaj.CubajLemnRotund;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class BucataListAdapter extends ArrayAdapter<Bucata> {

    private Context mContext;
    private int mResource;
    private int lastPosition = -1;

    public BucataListAdapter(@NonNull Context context, int resource, @NonNull ArrayList<Bucata> objects) {
        super(context, resource, objects);
        mContext = context;
        mResource = resource;
    }




    /**
     * Holds variables in a View
     */
    private static class ViewHolder {
        TextView Id;
        TextView Lungime;
        TextView Diametru;
        TextView Specie;
        TextView Container;
        TextView Volum;
    }



    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        String Lungimedeafisat = getItem(position).getLungime();
        String Diametrudeafisat =  getItem(position).getDiametru();
        String Speciedeafisat = getItem(position).getSpecie();
        String Containerdeafisat = getItem(position).getContainer();
        String Volumdeafisat = getItem(position).getVolum();
        String IdDeAfisat = getItem(position).getId();

        Bucata Buc = new Bucata(Lungimedeafisat,Diametrudeafisat,Speciedeafisat,Containerdeafisat,Volumdeafisat,IdDeAfisat);

        final View localresult_View;

        //ViewHolder object
        ViewHolder localholder_ViewHolder;

        if(convertView == null){
            LayoutInflater inflater = LayoutInflater.from(mContext);
            convertView = inflater.inflate(mResource, parent, false);
            localholder_ViewHolder = new ViewHolder();

            localholder_ViewHolder.Lungime = (TextView) convertView.findViewById(R.id.textViewLungime);
            localholder_ViewHolder.Diametru = (TextView) convertView.findViewById(R.id.textViewDiametru);
            localholder_ViewHolder.Specie = convertView.findViewById(R.id.textViewSpecie);
            localholder_ViewHolder.Container = (TextView) convertView.findViewById(R.id.textViewContainer);
            localholder_ViewHolder.Volum = (TextView) convertView.findViewById(R.id.textViewVolum);
            localholder_ViewHolder.Id = convertView.findViewById(R.id.textViewNrBuc);

            localresult_View = convertView;

            convertView.setTag(localholder_ViewHolder);
        }
        else{
            localholder_ViewHolder = (ViewHolder) convertView.getTag();
            localresult_View = convertView;
        }


        Animation animation = AnimationUtils.loadAnimation(mContext,
                (position > lastPosition) ? R.anim.load_down_anim : R.anim.load_up_anim);
        localresult_View.startAnimation(animation);
        lastPosition = position;


        localholder_ViewHolder.Lungime.setText(Lungimedeafisat);
        localholder_ViewHolder.Diametru.setText(Diametrudeafisat);
        localholder_ViewHolder.Specie.setText(Speciedeafisat);
        localholder_ViewHolder.Container.setText(Containerdeafisat);
        localholder_ViewHolder.Volum.setText(Volumdeafisat);
        localholder_ViewHolder.Id.setText(IdDeAfisat);

        return convertView;


    }





}
