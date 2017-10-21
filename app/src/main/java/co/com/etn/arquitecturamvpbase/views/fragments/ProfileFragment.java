package co.com.etn.arquitecturamvpbase.views.fragments;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.net.URI;

import co.com.etn.arquitecturamvpbase.R;
import co.com.etn.arquitecturamvpbase.helper.Constants;
import co.com.etn.arquitecturamvpbase.helper.CustomSharedPreferences;
import co.com.etn.arquitecturamvpbase.models.Login;
import co.com.etn.arquitecturamvpbase.presenters.ProfilePresenter;
import co.com.etn.arquitecturamvpbase.presenters.products.ProductPresenter;
import co.com.etn.arquitecturamvpbase.repositories.products.ProductRepository;
import co.com.etn.arquitecturamvpbase.views.BaseFragments;
import co.com.etn.arquitecturamvpbase.views.profile.IProfileView;

/**
 * Created by Erika on 17/10/2017.
 */

public class ProfileFragment extends BaseFragments<ProfilePresenter> implements IProfileView {

    private TextView tv_wins;
    private TextView tv_laws;
    private TextView tv_losts;
    private TextView tv_username;
    private ImageView logo;
    private CustomSharedPreferences customSharedPreferences;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_profile,container,false);
        getBaseActivity().createProgressDialog();
        setPresenter(new ProfilePresenter());
        getPresenter().inject(this,getValidateInternet());
        customSharedPreferences = new CustomSharedPreferences(getActivity());
        init(view);
        loadInfoProfile();
        return view;
    }

    private void loadInfoProfile() {
        Login login = customSharedPreferences.getObjectUser(Constants.SP_LOGIN);
        if(login != null && !"".equals(login.getToken())){
            tv_laws.setText(String.valueOf(login.getLikes()));
            tv_wins.setText(String.valueOf(login.getFollowers()));
            tv_losts.setText(String.valueOf(login.getFollowings()));
            tv_username.setText("@"+String.valueOf(login.getUsername()));
            Picasso.with(getActivity())
                    .load(login.getPhoto())
                    .into(logo);
        }
    }

    private void init(View view) {
        tv_laws = (TextView) view.findViewById(R.id.profile_activity_laws);
        tv_wins = (TextView) view.findViewById(R.id.profile_activity_wins);
        tv_losts = (TextView) view.findViewById(R.id.profile_activity_losts);
        tv_username = (TextView) view.findViewById(R.id.profile_textvie_username);
        logo = (ImageView) view.findViewById(R.id.profile_imageview_logo);
    }
}
