package co.com.etn.arquitecturamvpbase.views.activities;

import android.content.res.ColorStateList;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;

import co.com.etn.arquitecturamvpbase.R;
import co.com.etn.arquitecturamvpbase.presenters.customers.CreateCustomerPresenter;
import co.com.etn.arquitecturamvpbase.repositories.customers.CustomerRepository;
import co.com.etn.arquitecturamvpbase.views.BaseActivity;
import co.com.etn.arquitecturamvpbase.views.adapters.DashBoardTabsAdapter;

/**
 * Created by Erika on 14/10/2017.
 */

public class DashBoardActivity extends BaseActivity {

    private TabLayout dashboard_tablayout;
    private ViewPager dashboard_viewpager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dashboard_activity);
        init();
        loadTabsAdapter();
    }

    private void loadTabsAdapter() {
        DashBoardTabsAdapter dashBoardTabsAdapter = new DashBoardTabsAdapter(getSupportFragmentManager());
        dashboard_viewpager.setAdapter(dashBoardTabsAdapter);
        dashboard_tablayout.setupWithViewPager(dashboard_viewpager);
        dashboard_tablayout.setSelectedTabIndicatorColor(ContextCompat.getColor(this,R.color.colorWhite));
        dashboard_tablayout.setTabTextColors(ColorStateList.valueOf(ContextCompat.getColor(this,R.color.colorPrimaryDark)));
    }

    private void init() {
        dashboard_tablayout = (TabLayout) findViewById(R.id.dashboard_tablayout);
        dashboard_viewpager = (ViewPager) findViewById(R.id.dashboard_viewpager);
    }
}
