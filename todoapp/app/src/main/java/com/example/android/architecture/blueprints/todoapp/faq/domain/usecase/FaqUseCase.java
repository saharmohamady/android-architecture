package com.example.android.architecture.blueprints.todoapp.faq.domain.usecase;

import android.support.annotation.NonNull;

import com.example.android.architecture.blueprints.todoapp.faq.datasourse.FAQRepository;
import com.example.android.architecture.blueprints.todoapp.faq.model.FAQModel;

import java.util.List;

import rx.Observable;


/**
 * Created by Sahar Almohamady on 6/14/2017.
 */

public class FaqUseCase implements UseCaseContract{
    private final FAQRepository faqRepository;

    public FaqUseCase(@NonNull FAQRepository faqRepository) {
        this.faqRepository = faqRepository;
    }

    public Observable<List<FAQModel>> executeUseCase() {
        Observable<List<FAQModel>> models = faqRepository.getListOfFAQs();
        return models;
    }

    @Override
    public void destroy() {
        faqRepository.destroy();
    }
}
