package co.com.etn.arquitecturamvpbase.views.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;

import co.com.etn.arquitecturamvpbase.helper.Constants;
import co.com.etn.arquitecturamvpbase.views.fragments.CustomerFragment;
import co.com.etn.arquitecturamvpbase.views.fragments.ProductFragment;
import co.com.etn.arquitecturamvpbase.views.fragments.ProfileFragment;

/**
 * Created by Erika on 14/10/2017.
 */

public class DashBoardTabsAdapter extends FragmentStatePagerAdapter {



    public DashBoardTabsAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {

        switch (position){
            case 0:
                return new ProductFragment();
            case 1:
                return new CustomerFragment();
            case 2:
                return new ProfileFragment();
        }

        return null;
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position){
            case 0:
                return Constants.TITLE_PRODUCT;
            case 1:
                return Constants.TITLE_CONTACT;
            case 2:
                return Constants.TITLE_PROFILE;
            default:
                return super.getPageTitle(position);
        }
    }
}
