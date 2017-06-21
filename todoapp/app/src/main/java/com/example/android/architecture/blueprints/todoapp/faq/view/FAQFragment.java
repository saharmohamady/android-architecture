package com.example.android.architecture.blueprints.todoapp.faq.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.example.android.architecture.blueprints.todoapp.Injection;
import com.example.android.architecture.blueprints.todoapp.R;
import com.example.android.architecture.blueprints.todoapp.faq.model.FAQModel;
import com.example.android.architecture.blueprints.todoapp.faq.presenter.FAQPresenter;

import java.util.ArrayList;
import java.util.List;

public class FAQFragment extends Fragment implements FAQViewContract, FAQListItemListener, SwipeRefreshLayout.OnRefreshListener {

    private FAQPresenter faqPresenter;
    private FAQDataAdapter faqDataAdapter;
    private LinearLayout faqListLayout;
    private View noFaqView;
    private SwipeRefreshLayout swipeRefreshLayout;

    public static FAQFragment newInstance() {
        return new FAQFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        faqDataAdapter = new FAQDataAdapter(new ArrayList<FAQModel>(0), this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_faq, container, false);
        ListView listView = (ListView) view.findViewById(R.id.faq_list);
        listView.setAdapter(faqDataAdapter);
        faqListLayout = (LinearLayout) view.findViewById(R.id.faqLListLayout);
        noFaqView = view.findViewById(R.id.noFaq);

        faqPresenter = new FAQPresenter(this, Injection.provideGetFAQ(getActivity()), Injection.provideDeleteFAQ());

        swipeRefreshLayout =
                (SwipeRefreshLayout) view.findViewById(R.id.refresh_layout);
        swipeRefreshLayout.setOnRefreshListener(this);
        setHasOptionsMenu(true);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        faqPresenter.start();
    }

    @Override
    public void setProgressIndicator(final boolean active) {
        if (getView() == null) {
            return;
        }
        swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(active);
            }
        });
    }

    @Override
    public void showFAQ(List<FAQModel> faqModels) {
        faqDataAdapter.replaceData(faqModels);
        faqListLayout.setVisibility(View.VISIBLE);
        noFaqView.setVisibility(View.GONE);
        setProgressIndicator(false);
    }

    @Override
    public void showLoadingFAQError() {
        Toast.makeText(getActivity(), R.string.error_loading_faq, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void setPresenter(FAQPresenter presenter) {
        faqPresenter = presenter;
    }

    //list item click actions
    @Override
    public void onFAQItemClick(FAQModel clickedItem) {
        Toast.makeText(getActivity(), R.string.faq_item_clicked, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRefresh() {
        faqPresenter.start();
    }

    @Override
    public boolean isActive() {
        return isAdded();
    }

    @Override
    public void showNoFAQ(List<FAQModel> faqModels) {
        faqDataAdapter.replaceData(faqModels);
        faqListLayout.setVisibility(View.GONE);
        noFaqView.setVisibility(View.VISIBLE);
        setProgressIndicator(false);
    }

    @Override
    public void showClearError() {
        Toast.makeText(getActivity(), R.string.errorOccer, Toast.LENGTH_SHORT).show();

    }

    @Override
    public void clearAllDataDone() {
        showNoFAQ(new ArrayList<FAQModel>());
    }

    @Override
    public void deleteItemDone(Integer itemIndex) {
        faqDataAdapter.deleteSelectedItem();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_clear:
                faqPresenter.clearFAQs();
                break;
            case R.id.menu_delete:
                faqPresenter.deleteSelected(faqDataAdapter.getSelectedItemIndex());
                break;
        }
        return true;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.faq_fragment_menu, menu);
    }
}
