package com.Cubaj.CubajLemnRotund;

import android.content.Context;
import android.graphics.Color;
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

public class BucataOrdonataListAdapter extends ArrayAdapter<BucataOrdonata> {

    private Context mContext;
    private int mResource;
    private int lastPosition = -1;

    private ArrayList<Boolean> mSelectedElements = new ArrayList<>();

    public BucataOrdonataListAdapter(@NonNull Context context, int resource, @NonNull ArrayList<BucataOrdonata> objects, ArrayList<Boolean> SelectedElements) {
        super(context, resource, objects);
        mContext = context;
        mResource = resource;
        mSelectedElements=SelectedElements;
    }


    /**
     * Holds variables in a View
     */
    private static class ViewHolder {
        TextView NrBuc;
        TextView Lungime;
        TextView Diametru;
        TextView Specie;
        TextView Container;
        TextView Volum;
    }



    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        final View localresult_View;

        //ViewHolder object
        ViewHolder localholder_ViewHolder;
        
        String Volumdeafisat;

        String Lungimedeafisat = getItem(position).getLungime();
        String Diametrudeafisat =  getItem(position).getDiametru();
        String Speciedeafisat = getItem(position).getSpecie();
        String Containerdeafisat = getItem(position).getContainer();
        String NrBucdeAfisat = getItem(position).getNumarBucati();

        if(NrBucdeAfisat.equals("1"))
        {
            Volumdeafisat = getItem(position).getVolumBucata();
        }
        else
        {
            Volumdeafisat = getItem(position).getVolumTotal();
        }




        if(convertView == null){

            LayoutInflater inflater = LayoutInflater.from(mContext);
            convertView = inflater.inflate(mResource, parent, false);
            localholder_ViewHolder = new ViewHolder();

            localholder_ViewHolder.Lungime = convertView.findViewById(R.id.textViewLungime);
            localholder_ViewHolder.Diametru = convertView.findViewById(R.id.textViewDiametru);
            localholder_ViewHolder.Specie = convertView.findViewById(R.id.textViewSpecie);
            localholder_ViewHolder.Container = convertView.findViewById(R.id.textViewContainer);
            localholder_ViewHolder.Volum = convertView.findViewById(R.id.textViewVolum);
            localholder_ViewHolder.NrBuc = convertView.findViewById(R.id.textViewNrBuc);

            localresult_View = convertView;

            convertView.setTag(localholder_ViewHolder);
        }
        else
            {
            localholder_ViewHolder = (ViewHolder) convertView.getTag();
            localresult_View = convertView;
        }


        if(mSelectedElements.get(position).equals(false))
        {

            localholder_ViewHolder.Lungime.setBackgroundResource(R.drawable.back_blue);
            localholder_ViewHolder.Diametru.setBackgroundResource(R.drawable.back_blue);
            localholder_ViewHolder.Specie.setBackgroundResource(R.drawable.back_blue);
            localholder_ViewHolder.Container.setBackgroundResource(R.drawable.back_blue);
            localholder_ViewHolder.Volum.setBackgroundResource(R.drawable.back_blue);
            localholder_ViewHolder.NrBuc.setBackgroundResource(R.drawable.back_blue);


        }
        else {
            localholder_ViewHolder.Lungime.setBackgroundColor(Color.parseColor("#D8D2D2"));
            localholder_ViewHolder.Diametru.setBackgroundColor(Color.parseColor("#D8D2D2"));
            localholder_ViewHolder.Specie.setBackgroundColor(Color.parseColor("#D8D2D2"));
            localholder_ViewHolder.Container.setBackgroundColor(Color.parseColor("#D8D2D2"));
            localholder_ViewHolder.Volum.setBackgroundColor(Color.parseColor("#D8D2D2"));
            localholder_ViewHolder.NrBuc.setBackgroundColor(Color.parseColor("#D8D2D2"));

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
        localholder_ViewHolder.NrBuc.setText(NrBucdeAfisat);


        return convertView;

    }

    public void setElementeSelectate(ArrayList<Boolean> booleanArrayList)
    {
        mSelectedElements = booleanArrayList;
    }
    public ArrayList<Boolean> getElementeSelectate()
    {
        return mSelectedElements;
    }




}

