package in.amruthashala.momapp.Adapaters;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import in.amruthashala.momapp.Fragments.AddressFragment;
import in.amruthashala.momapp.Fragments.DocumentFragment;
import in.amruthashala.momapp.Fragments.FrimFragment;
import in.amruthashala.momapp.Fragments.PaymentFragment;
import in.amruthashala.momapp.Fragments.PersonalFragment;

public class SignUpPagerAdapater extends FragmentStatePagerAdapter {
    public SignUpPagerAdapater(@NonNull FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        if (position == 0) {
            fragment = new PersonalFragment();
        } else if (position == 1) {
            fragment = new AddressFragment();
        } else if (position == 2) {
            fragment = new PaymentFragment();
        } else if (position == 3) {
            fragment = new FrimFragment();
        } else {
            fragment = new DocumentFragment();
        }
        return fragment;
    }

    @Override
    public int getCount() {
        return 5;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        String title = null;
        if (position == 0) {
            title = "Personal";
        } else if (position == 1) {
            title = "Address";
        } else if (position == 2) {
            title = "Payment";
        } else if (position == 3) {
            title = "Firm";
        } else {
            title = "Document";
        }
        return title;
    }
}
