package com.city.coding.restaurant3.Adapters;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

public class viewFragmentPagerAdapter extends FragmentPagerAdapter {

    //Fragment page number
    private int count;
    //ArrayList of Fragment
    private ArrayList<Fragment> listOfFragment = new ArrayList<>();
    //ArrayList of fragment title to show on tabLayout
    private ArrayList<String> listOfFragmentTitle = new ArrayList<>();


    //constructor
    public viewFragmentPagerAdapter(FragmentManager fm){
        super(fm);
    }
    //end constructor


    @Override
    public Fragment getItem(int position) {
        //return fragment according ot it's position in listOfFragment ArrayList
        return listOfFragment.get(position);
    }
    //end

    @Override
    public int getCount() {
        return listOfFragment.size();
    }
    //end


    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        if(listOfFragmentTitle.size() == 0)
            return "";
        //return fragment title from listOfFragment ArrayList<String>
        return listOfFragmentTitle.get(position);
    }
    //end



    // add fragment object and fragment title to arrayList
    public void addFragment(Fragment fragment){
        listOfFragment.add(fragment);

    }

    //Note : addFragment is seperated from addFragmentTitle
    //to be able to add or remove ttile from tabLayout with commenting single line

    /*add fragment title*/
     public void addFragmentTitle(String title){
        listOfFragmentTitle.add(title);
    }
}
