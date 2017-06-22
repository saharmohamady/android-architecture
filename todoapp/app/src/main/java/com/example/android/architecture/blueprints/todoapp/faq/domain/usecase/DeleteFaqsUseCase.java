package com.example.android.architecture.blueprints.todoapp.faq.domain.usecase;


import rx.Observable;

/**
 * Created by Sahar Almohamady on 6/21/2017.
 */

public class DeleteFaqsUseCase implements UseCaseContract {
    public Observable<Object> clearData() {
        //delete items from repo if cached
        return Observable.empty();
    }

    public Observable<Integer> deleteSelectedItem(int selectedItemIndex) {
        return Observable.just(selectedItemIndex);
    }

    @Override
    public void destroy() {

    }
}
