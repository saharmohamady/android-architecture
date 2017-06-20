package com.example.android.architecture.blueprints.todoapp.network;

import com.example.android.architecture.blueprints.todoapp.BuildConfig;

/**
 * Created by Sahar Almohamady on 6/18/2017.
 */

public class ApisUtils {
    public static GetFaqsApi getFaqsAPIInstance() {
        return RetrofitNetworkManager.getClient(BuildConfig.BASE_URL).create(GetFaqsApi.class);
    }
}
