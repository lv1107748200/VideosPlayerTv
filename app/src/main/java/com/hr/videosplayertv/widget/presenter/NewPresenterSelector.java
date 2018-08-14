package com.hr.videosplayertv.widget.presenter;


import android.support.v17.leanback.widget.ListRow;
import android.support.v17.leanback.widget.ListRowPresenter;
import android.support.v17.leanback.widget.Presenter;
import android.support.v17.leanback.widget.PresenterSelector;

import static android.support.v17.leanback.widget.FocusHighlight.ZOOM_FACTOR_NONE;
import static android.support.v17.leanback.widget.FocusHighlight.ZOOM_FACTOR_SMALL;

/**
 * 多个选择器.
 */
public class NewPresenterSelector extends PresenterSelector {
    private ListRowPresenter mListRowPresenter = new ListRowPresenter();
    private NewListRowPresenter mShadowDisabledRowPresenter = new NewListRowPresenter(ZOOM_FACTOR_NONE);
    private InvisibleRowPresenter mTestRowPresenter = new InvisibleRowPresenter();

    public NewPresenterSelector() {
        mListRowPresenter.setNumRows(1);
        mListRowPresenter.setHeaderPresenter(new HeaderPresenter());
        mShadowDisabledRowPresenter.setNumRows(2);
        mShadowDisabledRowPresenter.setHeaderPresenter(new HeaderPresenter());
        mTestRowPresenter.setHeaderPresenter(new HeaderPresenter()); // 可以换不同的头样式，自己写.
    }

    @Override
    public Presenter getPresenter(Object item) {
        if ((item instanceof HeadListRow))
            return mTestRowPresenter;

        return mShadowDisabledRowPresenter;
    }

    @Override
    public Presenter[] getPresenters() {
        return new Presenter[]{
                mShadowDisabledRowPresenter,
                mListRowPresenter,
                mTestRowPresenter
        };
    }
}
