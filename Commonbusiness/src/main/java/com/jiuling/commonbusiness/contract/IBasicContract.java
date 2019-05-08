package com.jiuling.commonbusiness.contract;



import com.jiuling.commonbusiness.presenter.IBasicPresenter;
import com.jiuling.commonbusiness.view.IBasicView;

import java.lang.ref.WeakReference;


/**
 * 用于管理View层接口和Model层接口
 * Created by Rayman on 2017/1/10.
 */
public interface IBasicContract {

    /**
     * View层的具体实现，可自行添加方法
     */
    interface IViewImpl extends IBasicView {

    }

    /**
     *  防止因为异步回调，Model层在回调完成的时候，View层已经被销毁的情况下
     *  引起的内存泄露
     *  @param <T>
     */
    abstract class IPresenterImpl<T> implements IBasicPresenter {

        /**
         * 内存不足的时候回收
         */
        protected WeakReference<T> mViewRef;

        /**
         * 添加View层的引用
         *
         * @param view
         */
        public void attachView(IViewImpl view) {
            mViewRef = new WeakReference<T>((T) view);
        }

        /**
         * 删除对View层的引用，免得引起内存泄露
         */
        public void detachView() {
            if (mViewRef != null) {
                mViewRef.clear();
                mViewRef = null;
            }
        }
    }
}
