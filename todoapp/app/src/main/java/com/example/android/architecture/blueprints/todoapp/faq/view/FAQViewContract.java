package com.example.android.architecture.blueprints.todoapp.faq.view;

import com.example.android.architecture.blueprints.todoapp.BaseView;
import com.example.android.architecture.blueprints.todoapp.faq.model.FAQModel;
import com.example.android.architecture.blueprints.todoapp.faq.presenter.FAQPresenter;

import java.util.List;

/**
 * Created by Sahar Almohamady on 6/14/2017.
 */

public interface FAQViewContract extends BaseView<FAQPresenter> {
    void setProgressIndicator(boolean active);

    void showFAQ(List<FAQModel> faqModels);

    void showLoadingFAQError();

    boolean isActive();

    void showNoFAQ(List<FAQModel> faqModels);
}
