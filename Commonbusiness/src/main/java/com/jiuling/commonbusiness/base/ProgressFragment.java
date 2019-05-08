package com.jiuling.commonbusiness.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.TextView;


import com.jiuling.commonbusiness.R;
import com.jiuling.commonbusiness.contract.IBasicContract;
import com.jiuling.commonbusiness.di.component.ApplicationComponent;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.Unbinder;


public abstract class ProgressFragment<T extends BasePresenter> extends Fragment implements IBasicContract.IViewImpl{



    private FrameLayout mRootView;

    private View mViewProgress;
    private FrameLayout mViewContent;
    private View mViewEmpty;
//    private Unbinder mUnbinder;

    private TextView mTextError;

    private BaseApplication mApplication;

    protected View realContentView;
    Unbinder unbinder;


    @Inject
    public T mPresenter;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

//        mPresenter = createPresenter();
//        if (mPresenter != null) {
//            mPresenter.attachView(this);
//        }
//        if (mPresenter==null){
//            Log.i("presenter","presenter");
//        }


        mRootView = (FrameLayout) inflater.inflate(R.layout.fragment_progress2,container,false);
        mViewProgress = mRootView.findViewById(R.id.view_progress);
        mViewContent = (FrameLayout) mRootView.findViewById(R.id.view_content);
        mViewEmpty = mRootView.findViewById(R.id.view_empty);

        mTextError = (TextView) mRootView.findViewById(R.id.text_tip);

        mViewEmpty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onEmptyViewClick();
            }
        });



        return mRootView;

    }

//    protected abstract T createPresenter();

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        this.mApplication = (BaseApplication) getActivity().getApplication();
        setupAcitivtyComponent(mApplication.getApplicationComponent());
        setRealContentView();

        init();

    }

    public void onEmptyViewClick(){

    }

    private void setRealContentView() {

        realContentView=  LayoutInflater.from(getActivity()).inflate(setLayout(),mViewContent,true);
//        mUnbinder=  ButterKnife.bind(this, realContentView);
        unbinder = ButterKnife.bind(this, mRootView);

    }



    public void  showProgressView(){
        showView(R.id.view_progress);

    }

    public void showContentView(){

        showView(R.id.view_content);
    }

    public void showEmptyView(){


        showView(R.id.view_empty);
    }



    public void showEmptyView(String msg){


        showEmptyView();
        mTextError.setText(msg);
    }



    public void showView(int viewId){

        for(int i=0;i<mRootView.getChildCount();i++){

            if( mRootView.getChildAt(i).getId() == viewId){

                mRootView.getChildAt(i).setVisibility(View.VISIBLE);
            }
            else {
                mRootView.getChildAt(i).setVisibility(View.GONE);
            }

        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
//        if(mUnbinder != Unbinder.EMPTY){
//            mUnbinder.unbind();
//        }
    }

    @Override
    public void showLoading() {
        showProgressView();
    }

    @Override
    public void showErrorMsg(String errorMsg) {
        showEmptyView(errorMsg);
    }

    @Override
    public void dismissLoading() {
        showContentView();
    }

    public abstract void  init();

    public abstract int setLayout();

    public abstract  void setupAcitivtyComponent(ApplicationComponent appComponent);

    public void darkenBackground(Float bgcolor) {
        Window window = getActivity().getWindow();
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.alpha = bgcolor;
        window.addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        window.setAttributes(lp);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if(unbinder!=null&&unbinder!=Unbinder.EMPTY){
            unbinder.unbind();
        }
    }





}
