package com.ozgursakizli.offlineapp.base;

import android.support.v4.app.Fragment;

/**
 * Created by ozgursakizli on 10.09.2017.
 */

public class BaseFragment extends Fragment {

    public void getStringResource(int resource) {
        ((BaseAppCompatActivity) getActivity()).getStringResource(resource);
    }

    public void showToast(int resource) {
        ((BaseAppCompatActivity) getActivity()).showToast(resource);
    }

}
