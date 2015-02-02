package cn.bingoogolapple.loonannotation.demo.ui.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import cn.bingoogolapple.loonannotation.demo.App;
import cn.bingoogolapple.loonannotation.library.Loon;


/**
 * Created by bingoogolapple on 14-10-10.
 */
public abstract class BaseFragment extends Fragment implements View.OnClickListener {
    protected App mApp;
    protected LocalBroadcastManager mLocalBroadcastManager;
    protected View mRootView;

    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mApp = App.getInstance();
        mLocalBroadcastManager = LocalBroadcastManager.getInstance(activity);
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (mRootView == null) {
            injectView();
            setListener();
            processLogic(savedInstanceState);
        } else {
            ViewGroup parent = (ViewGroup) mRootView.getParent();
            if (parent != null) {
                parent.removeView(mRootView);
            }
        }
        return mRootView;
    }

    protected void injectView() {
        mRootView = Loon.injectView2ViewHolderOrFragment(this, mApp);
    }

    protected void setListener() {
    }

    protected abstract void processLogic(Bundle savedInstanceState);

    @Override
    public void onClick(View v) {
    };

}