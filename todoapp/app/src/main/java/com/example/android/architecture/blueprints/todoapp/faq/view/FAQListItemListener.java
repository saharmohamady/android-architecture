package com.example.android.architecture.blueprints.todoapp.faq.view;

import com.example.android.architecture.blueprints.todoapp.faq.model.FAQModel;

/**
 * Created by Sahar Almohamady on 6/14/2017.
 */

public interface FAQListItemListener {

    void onFAQItemClick(FAQModel clickedItem);
}
