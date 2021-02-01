package com.Cubaj.CubajLemnRotund;

import android.os.Parcel;
import android.os.Parcelable;

public class Bucata implements Parcelable {

    private String Lungime;
    private String Diametru;
    private String Specie;
    private String Container;
    private String Volum;
    private String Id;


    Bucata(String l, String d, String s,String c, String v, String i)
        {
        this.Lungime = l;
        this.Diametru = d;
        this.Specie = s;
        this.Container = c;
        this.Volum = v;
        this.Id = i;
    }

    protected Bucata(Parcel in) {
        Lungime = in.readString();
        Diametru = in.readString();
        Specie = in.readString();
        Container = in.readString();
        Volum = in.readString();
        Id = in.readString();
    }


    //getters
    public String getLungime()
    {
        return this.Lungime;
    }

    public String getDiametru()
    {
        return this.Diametru;
    }

    public String getSpecie()
    {
        return this.Specie;
    }

    public String getContainer()
    {
        return this.Container;
    }

    public String getVolum()
    {
        return this.Volum;
    }

    public String getId()
    {
        return this.Id;
    }


    //setters
    public void setLungime(String l)
    {
        this.Lungime =l;
    }

    public void setDiametru(String d)
    {
        this.Diametru =d;
    }

    public void setSpecie(String s)
    {
        this.Specie =s;
    }

    public void setContainer(String c)
    {
        this.Container =c;
    }

    public void setVolum(String v)
    {
        this.Volum =v;
    }

    public void setId(String i)
    {
        this.Id =i;
    }



    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(Lungime);
        dest.writeString(Diametru);
        dest.writeString(Specie);
        dest.writeString(Container);
        dest.writeString(Volum);
        dest.writeString(Id);
    }

    public static final Creator<Bucata> CREATOR = new Creator<Bucata>() {
        @Override
        public Bucata createFromParcel(Parcel in) {
            return new Bucata(in);
        }

        @Override
        public Bucata[] newArray(int size) {
            return new Bucata[size];
        }
    };




}
