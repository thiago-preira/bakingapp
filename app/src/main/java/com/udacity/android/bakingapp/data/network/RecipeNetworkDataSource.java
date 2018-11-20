package com.udacity.android.bakingapp.data.network;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.util.Log;

import com.udacity.android.bakingapp.model.Recipe;
import com.udacity.android.bakingapp.utils.AppExecutors;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RecipeNetworkDataSource {

    private static final String TAG = RecipeNetworkDataSource.class.getSimpleName();

    private static final Object LOCK = new Object();
    private static RecipeNetworkDataSource mInstance;
    private final AppExecutors mExecutors;
    private final MutableLiveData<List<Recipe>> mRecipesFromNetwork;

    private RecipeNetworkDataSource(AppExecutors executors) {
        this.mExecutors = executors;
        this.mRecipesFromNetwork = new MutableLiveData<>();
        fetchRecipes();
    }

    public static RecipeNetworkDataSource getInstance(AppExecutors executors) {
        Log.d(TAG, "Getting the network data source");
        if (mInstance == null) {
            synchronized (LOCK) {
                mInstance = new RecipeNetworkDataSource(executors);
                Log.d(TAG, "Made new network data source");
            }
        }
        return mInstance;
    }

    public LiveData<List<Recipe>> getRecipes() {
        return mRecipesFromNetwork;
    }

    private void fetchRecipes() {
        final BakingAPI client = BakingAPIClient.getInstance();
        mExecutors.networkIO().execute(() -> {
            client.getRecipes().enqueue(new Callback<List<Recipe>>() {
                @Override
                public void onResponse(Call<List<Recipe>> call, Response<List<Recipe>> response) {
                    if (!response.isSuccessful()) {
                        Log.w(TAG, "onResponse: Error requesting data from network. error code: "
                                + response.code());
                        return;
                    }
                    Log.d(TAG, "onResponse: Data requested from network!");
                    mRecipesFromNetwork.postValue(response.body());
                }

                @Override
                public void onFailure(Call<List<Recipe>> call, Throwable t) {
                    Log.e(TAG, "onFailure:Request to network failed: " + t.getMessage());
                }
            });
        });
    }

}
