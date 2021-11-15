package Adapters;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.aplication.dilevery_app.Fragments.Activity_phone_number;
import com.aplication.dilevery_app.Fragments.sign_up_fragment;
import com.aplication.dilevery_app.Fragments.sing_in_fragment;

public class LoginAdapter extends FragmentPagerAdapter {

    private Context mContext;
   private  int mTotaleTabs;
    public LoginAdapter(@NonNull FragmentManager fm , Context context , int tabs)  {
        super(fm);
        this.mTotaleTabs = tabs;
        this.mContext = context;
    }


    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case  0 :
                return "Login";

            case 1 :
                return "SignUp";

        }
        return  null;
    }


    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case  0 :
                return new sing_in_fragment();

            case 1 :
 return new Activity_phone_number();

        }
        return null;
    }

    @Override
    public int getCount() {
        return mTotaleTabs;
    }
}
