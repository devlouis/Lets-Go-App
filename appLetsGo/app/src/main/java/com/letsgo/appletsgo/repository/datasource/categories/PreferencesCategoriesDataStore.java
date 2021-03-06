package com.letsgo.appletsgo.repository.datasource.categories;

import android.content.Context;

import com.letsgo.appletsgo.data.entity.response.CategoriesResponse;
import com.letsgo.appletsgo.data.rest.ApiClient;
import com.letsgo.appletsgo.data.store.SessionUser;
import com.letsgo.appletsgo.domain.model.entity.Categories;
import com.letsgo.appletsgo.domain.model.entity.CategoriesToPreferences;
import com.letsgo.appletsgo.repository.Categories.RepositoryCallBackCategories;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by louislopez on 4/03/17.
 */

public class PreferencesCategoriesDataStore implements CategoriesPreferencesDataStore {
    private static String TAG = "PreferencesCategoriesDataStore";
    private SessionUser sessionUser;
    private Context context;

    public PreferencesCategoriesDataStore(Context context, SessionUser sessionUser) {
        this.sessionUser = sessionUser;
        this.context = context;
    }

    @Override
    public void getCategoriesPreferences(final RepositoryCallBackCategories repositoryCallBackCategories) {
        CategoriesToPreferences categoriesToPreferences = this.sessionUser.getCategoriesUser(context);
        repositoryCallBackCategories.onSuccess(categoriesToPreferences);
    }

    @Override
    public void saveCategoriesToPreferences(CategoriesToPreferences categoriesToPreferences, RepositoryCallBackCategories repositoryCallBackCategories) {
        this.sessionUser.saveCategoriesUser(context, categoriesToPreferences);
        repositoryCallBackCategories.onSuccess(true);
    }
}
