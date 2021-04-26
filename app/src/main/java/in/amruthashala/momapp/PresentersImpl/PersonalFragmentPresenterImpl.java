package in.amruthashala.momapp.PresentersImpl;

import android.content.Context;

import in.amruthashala.momapp.Presenter.PersonalFragmentPresenter;
import in.amruthashala.momapp.ViewListners.PersonalFragmentViewListner;

public class PersonalFragmentPresenterImpl implements PersonalFragmentPresenter {

    PersonalFragmentViewListner personalFragmentViewListner;
    Context context;

    public PersonalFragmentPresenterImpl(PersonalFragmentViewListner personalFragmentViewListner, Context context) {
        this.personalFragmentViewListner = personalFragmentViewListner;
        this.context = context;
    }


    @Override
    public void getImageFromCamera() {

    }

}
