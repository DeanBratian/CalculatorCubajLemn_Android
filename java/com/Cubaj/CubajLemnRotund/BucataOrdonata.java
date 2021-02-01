package com.Cubaj.CubajLemnRotund;

import android.os.Parcel;
import android.os.Parcelable;

public class BucataOrdonata implements Parcelable
{
    String NumarBucati;
    String Lungime;
    String Diametru;
    String Specie;
    String Container;
    String VolumBucata;
    String VolumTotal;


    public BucataOrdonata(String numbarBucati, String lungime, String diametru, String specie ,String container, String volumb, String volumt ) {
        NumarBucati = numbarBucati;
        Lungime = lungime;
        Diametru = diametru;
        Specie = specie;
        Container = container;
        VolumBucata = volumb;
        VolumTotal = volumt;
    }



    protected BucataOrdonata(Parcel in)
    {
        NumarBucati = in.readString();
        Lungime = in.readString();
        Diametru = in.readString();
        Specie = in.readString();
        Container = in.readString();
        VolumBucata = in.readString();
        VolumTotal = in.readString();
    }

    public static final Creator<BucataOrdonata> CREATOR = new Creator<BucataOrdonata>() {
        @Override
        public BucataOrdonata createFromParcel(Parcel in) {
            return new BucataOrdonata(in);
        }

        @Override
        public BucataOrdonata[] newArray(int size) {
            return new BucataOrdonata[size];
        }
    };

    public String getNumarBucati() {
        return NumarBucati;
    }

    public String getLungime() {
        return Lungime;
    }

    public String getDiametru() {
        return Diametru;
    }

    public String getVolumBucata() {
        return VolumBucata;
    }

    public String getVolumTotal() {
        return VolumTotal;
    }

    public String getSpecie() {
        return Specie;
    }

    public void setNumarBucati(String numarBucati) {
        NumarBucati = numarBucati;
    }

    public void setLungime(String lungime) {
        Lungime = lungime;
    }

    public void setDiametru(String diametru) {
        Diametru = diametru;
    }

    public void setVolumTotal(String volumt) {
        VolumTotal = volumt;
    }

    public void setVolumBucata(String volumb) {
        VolumBucata = volumb;
    }

    public void setSpecie(String specie) {
        Specie = specie;
    }

    public  String getContainer() {
        return Container;
    }

    public void setContainer(String container) {
        Container = container;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(NumarBucati);
        dest.writeString(Lungime);
        dest.writeString(Diametru);
        dest.writeString(Specie);
        dest.writeString(Container);
        dest.writeString(VolumBucata);
        dest.writeString(VolumTotal);
    }


    @Override
    public boolean equals(Object o)
    {
        BucataOrdonata Bo;
        if(!(o instanceof BucataOrdonata))
        {
            return false;
        }

        else
        {
            Bo=(BucataOrdonata)o;
            if(this.Lungime.equals(Bo.getLungime()) && this.Diametru.equals(Bo.getDiametru()) && this.Specie.equals(Bo.getSpecie()) && this.Container.equals(Bo.getContainer()) && this.VolumBucata.equals(Bo.getVolumBucata()) )
            {
                return true;
            }
        }
        return false;
    }
}
