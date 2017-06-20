package com.example.android.architecture.blueprints.todoapp.faq.presenter;

import com.example.android.architecture.blueprints.todoapp.BasePresenter;
import com.example.android.architecture.blueprints.todoapp.faq.domain.usecase.FaqUseCase;
import com.example.android.architecture.blueprints.todoapp.faq.model.FAQModel;
import com.example.android.architecture.blueprints.todoapp.faq.view.FAQViewContract;

import java.util.List;

import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Sahar Almohamady on 6/14/2017.
 */

public class FAQPresenter implements BasePresenter {
    private final FAQViewContract view;
    private final FaqUseCase faqUseCase;

    public FAQPresenter(FAQViewContract view, FaqUseCase faqUseCase) {
        this.view = view;
        this.faqUseCase = faqUseCase;
    }

    @Override
    public void start() {
        loadFAQ();
    }

    private void loadFAQ() {
        view.setProgressIndicator(true);

        Observable<List<FAQModel>> faqObservable = faqUseCase.executeUseCase();
        faqObservable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<FAQModel>>() {
                    @Override
                    public void onCompleted() {
                        view.setProgressIndicator(false);
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        view.showLoadingFAQError();
                    }

                    @Override
                    public void onNext(List<FAQModel> faqModels) {
                        view.showFAQ(faqModels);
                    }
                });
    }
}
