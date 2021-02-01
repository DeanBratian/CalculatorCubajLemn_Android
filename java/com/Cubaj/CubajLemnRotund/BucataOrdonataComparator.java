package com.Cubaj.CubajLemnRotund;

import java.util.Comparator;

public class BucataOrdonataComparator implements Comparator<BucataOrdonata> {


    @Override
    public int compare(BucataOrdonata o1, BucataOrdonata o2) {

        // all comparison
        int compareLungime = o1.getLungime().compareToIgnoreCase(o2.getLungime());
        int compareDiametre = o1.getDiametru().compareToIgnoreCase(o2.getDiametru());
        int compareSpecie = o1.getSpecie().compareToIgnoreCase(o2.getSpecie());


        if(compareSpecie == 0) {
            return ((compareLungime == 0) ? compareDiametre : compareLungime);
        }
        else {
            return compareSpecie;
        }

    }
}

