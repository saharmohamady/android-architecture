package com.example.android.architecture.blueprints.todoapp.faq.datasourse;

import android.support.annotation.NonNull;

import com.example.android.architecture.blueprints.todoapp.faq.datasourse.fake.FAQFakeRepository;
import com.example.android.architecture.blueprints.todoapp.faq.datasourse.remote.FAQRemoteRepository;
import com.example.android.architecture.blueprints.todoapp.faq.model.FAQModel;

import java.util.List;

import rx.Observable;

/**
 * Created by Sahar Almohamady on 6/14/2017.
 */

public class FAQRepository implements FAQDataSource {
    private static FAQRepository INSTANCE = null;
    private FAQFakeRepository faqFakeRepository;
    private FAQRemoteRepository faqRemoteRepository;

    public FAQRepository(@NonNull FAQRemoteRepository faqRemoteRepository, @NonNull FAQFakeRepository faqFakeRepository) {
        this.faqRemoteRepository = faqRemoteRepository;
        this.faqFakeRepository = faqFakeRepository;
    }

    public static FAQRepository getInstance(FAQRemoteRepository faqRemoteRepository, FAQFakeRepository faqFakeRepository) {
        if (INSTANCE == null) {
            INSTANCE = new FAQRepository(faqRemoteRepository, faqFakeRepository);
        }
        return INSTANCE;
    }

    public Observable<List<FAQModel>> getListOfFAQs() {
        return faqRemoteRepository.getListOfFAQs();
    }

    @Override
    public void destroy() {
        INSTANCE = null;
        faqFakeRepository.destroy();
        faqRemoteRepository.destroy();
        faqFakeRepository = null;
        faqRemoteRepository = null;
    }
}
