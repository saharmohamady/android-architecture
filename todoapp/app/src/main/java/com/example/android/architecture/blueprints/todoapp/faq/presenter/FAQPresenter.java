package com.example.android.architecture.blueprints.todoapp.faq.presenter;

import com.example.android.architecture.blueprints.todoapp.BasePresenter;
import com.example.android.architecture.blueprints.todoapp.faq.domain.usecase.DeleteFaqsUseCase;
import com.example.android.architecture.blueprints.todoapp.faq.domain.usecase.FaqUseCase;
import com.example.android.architecture.blueprints.todoapp.faq.model.FAQModel;
import com.example.android.architecture.blueprints.todoapp.faq.view.FAQViewContract;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import rx.Observable;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Sahar Almohamady on 6/14/2017.
 */

public class FAQPresenter implements BasePresenter {
    private final FAQViewContract view;
    private final FaqUseCase faqUseCase;
    private final DeleteFaqsUseCase deleteUseCase;
    private Subscription clearSubscribtion;
    private Subscription loadSubscribtion;
    private Subscription deleteSubscribtion;

    public FAQPresenter(FAQViewContract view, FaqUseCase faqUseCase, DeleteFaqsUseCase deleteUseCase) {
        this.view = view;
        this.faqUseCase = faqUseCase;
        this.deleteUseCase = deleteUseCase;
    }

    @Override
    public void start() {
        loadFAQ();
    }

    private void loadFAQ() {
        view.setProgressIndicator(true);

        Observable<List<FAQModel>> faqObservable = faqUseCase.executeUseCase();
        loadSubscribtion = faqObservable.subscribeOn(Schedulers.io())
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
                    public void onNext(@NotNull List<FAQModel> faqModels) {
                        if (faqModels.isEmpty()) {
                            view.showNoFAQ(faqModels);
                        } else
                            view.showFAQ(faqModels);
                    }
                });
    }

    public void clearFAQs() {
        Observable<Object> clearObservable = deleteUseCase.clearData();
        clearSubscribtion = clearObservable.observeOn(AndroidSchedulers.mainThread()).
                subscribeOn(Schedulers.io()).subscribe(new Observer<Object>() {
            @Override
            public void onCompleted() {
                view.clearAllDataDone();
            }

            @Override
            public void onError(Throwable e) {
                view.showClearError();
            }

            @Override
            public void onNext(Object o) {

            }
        });
    }

    public void deleteSelected(int selectedItemIndex) {

        Observable<Integer> deleteObservable = deleteUseCase.deleteSelectedItem(selectedItemIndex);
        deleteSubscribtion = deleteObservable.observeOn(AndroidSchedulers.mainThread()).
                subscribeOn(Schedulers.io()).subscribe(new Observer<Integer>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                view.showClearError();
            }

            @Override
            public void onNext(Integer itemIndex) {
                view.deleteItemDone(itemIndex);
            }
        });
    }
}
