package com.Cubaj.CubajLemnRotund;

import java.util.Comparator;

public class StringComparator  implements Comparator<String>
{


    @Override
    public int compare(String s1, String s2)
{
    // all comparison
    int compareString = s1.compareToIgnoreCase(s2);

    return compareString;

}
}

