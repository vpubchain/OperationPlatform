package com.jiuling.operate.adapter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.jiuling.operate.entity.FragmentInfo;
import com.jiuling.operate.ui.fragment.NodeFragment;

import org.w3c.dom.Node;

import java.util.ArrayList;
import java.util.List;



public class ViewPagerAdapter extends FragmentStatePagerAdapter {




    public static List<FragmentInfo> mFragments = new ArrayList<>(4);

    public static List<Fragment> fragmentList = new ArrayList<>(4);


    public ViewPagerAdapter(FragmentManager fm,int fragmentType) {
        super(fm);

        initFragments(fragmentType);
    }




    private void initFragments(int fragmentType){

        fragmentList.clear();
        if (fragmentType == 1){
            NodeFragment nodeFragment1 = new NodeFragment();
            Bundle bundle1 = new Bundle();
            bundle1.putString("type","null");
            bundle1.putInt("fragmentType",fragmentType);
            nodeFragment1.setArguments(bundle1);

            fragmentList.add(nodeFragment1);

            NodeFragment nodeFragment2 = new NodeFragment();
            Bundle bundle2 = new Bundle();
            bundle2.putString("type","-1");
            bundle2.putInt("fragmentType",fragmentType);
            nodeFragment2.setArguments(bundle2);
            fragmentList.add(nodeFragment2);

            NodeFragment nodeFragment3 = new NodeFragment();
            Bundle bundle3 = new Bundle();
            bundle3.putString("type","ENABLED");
            bundle3.putInt("fragmentType",fragmentType);
            nodeFragment3.setArguments(bundle3);
            fragmentList.add(nodeFragment3);

            NodeFragment nodeFragment4 = new NodeFragment();
            Bundle bundle4 = new Bundle();
            bundle4.putString("type","NOTENABLED");
            bundle4.putInt("fragmentType",fragmentType);
            nodeFragment4.setArguments(bundle4);
            fragmentList.add(nodeFragment4);
        } else if (fragmentType == 2){
            NodeFragment nodeFragment1 = new NodeFragment();
            Bundle bundle1 = new Bundle();
            bundle1.putString("type","null");
            bundle1.putInt("fragmentType",fragmentType);
            nodeFragment1.setArguments(bundle1);

            fragmentList.add(nodeFragment1);

            NodeFragment nodeFragment2 = new NodeFragment();
            Bundle bundle2 = new Bundle();
            bundle2.putString("type","0");
            bundle2.putInt("fragmentType",fragmentType);
            nodeFragment2.setArguments(bundle2);
            fragmentList.add(nodeFragment2);

            NodeFragment nodeFragment3 = new NodeFragment();
            Bundle bundle3 = new Bundle();
            bundle3.putString("type","1");
            bundle3.putInt("fragmentType",fragmentType);
            nodeFragment3.setArguments(bundle3);
            fragmentList.add(nodeFragment3);


        } else if (fragmentType == 3){


            NodeFragment nodeFragment1 = new NodeFragment();
            Bundle bundle1 = new Bundle();
            bundle1.putString("type","0");
            bundle1.putInt("fragmentType",fragmentType);
            nodeFragment1.setArguments(bundle1);
            fragmentList.add(nodeFragment1);

            NodeFragment nodeFragment2 = new NodeFragment();
            Bundle bundle2 = new Bundle();
            bundle2.putString("type","1");
            bundle2.putInt("fragmentType",fragmentType);
            nodeFragment2.setArguments(bundle2);
            fragmentList.add(nodeFragment2);

            NodeFragment nodeFragment3 = new NodeFragment();
            Bundle bundle3 = new Bundle();
            bundle3.putString("type","2");
            bundle3.putInt("fragmentType",fragmentType);
            nodeFragment3.setArguments(bundle3);
            fragmentList.add(nodeFragment3);

        }

    }


    @Override
    public Fragment getItem(int position) {


            return (Fragment) fragmentList.get(position);

    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }


}
