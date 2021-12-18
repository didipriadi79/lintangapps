package com.example.lintangapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.AsyncTaskLoader;
import androidx.loader.content.Loader;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.lintangapp.github.GithubView;
import com.example.lintangapp.github.GithubViewAdapter;
import com.example.lintangapp.github.NetworkUtils;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.IOException;
import java.util.List;

public abstract class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<GithubView>> {

    ConstraintLayout layoutEmpty;
    GithubViewAdapter adapter;

    EditText searchUser;
    ListView listUser;
    FloatingActionButton add;

    private static final int GITHUB_SEARCH_LOADER = 1;
    private static final String GITHUB_QUERY_TAG = "query";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        layoutEmpty = findViewById(R.id.layout_empty);
        searchUser = findViewById(R.id.searchUser);
        listUser = findViewById(R.id.listUser);
        add = findViewById(R.id.fab);

        layoutEmpty.setVisibility(View.VISIBLE);

        add.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), MyUserActivity.class);
            startActivity(intent);
        });

        searchUser.setOnEditorActionListener((v, actionId, event) -> {
            String strUsername =searchUser.getText().toString();
            if (strUsername.isEmpty()){
                Toast.makeText(MainActivity.this, "search tidak boleh kosong",Toast.LENGTH_SHORT).show();
            } else {
                if (actionId == EditorInfo.IME_ACTION_SEARCH){
                    layoutEmpty.setVisibility(View.GONE);
                    return true;
                }
            }
            return false;
        });

        if (savedInstanceState != null){
            String queryUrl = savedInstanceState.getString(GITHUB_QUERY_TAG);
            searchUser.setText(queryUrl);
        }

        getSupportLoaderManager().initLoader(GITHUB_SEARCH_LOADER, null, this);

    }
    @Override
    public void onSaveInstanceState(@NonNull Bundle outState, @NonNull PersistableBundle outPersistanceState){
        super.onSaveInstanceState(outState, outPersistanceState);
        outState.putString(GITHUB_QUERY_TAG, searchUser.getText().toString().trim());
    }

    public Loader<List<GithubView>> onCreateLoader(int d, final Bundle args){
        return new AsyncTaskLoader<List<GithubView>>(this) {
            List<GithubView> mRepositoryList;

            @Override
            protected void onStartLoading(){

                if(args == null){
                    return;
                }

                if (mRepositoryList == null){
                    deliverResult(mRepositoryList);
                } else {
                    forceLoad();
                }
                //super.onStartLoading();;
            }

            @Nullable
            @Override
            public List<GithubView> loadInBackground() {

                String searchQueryUrlString = args.getString(GITHUB_QUERY_TAG);

                try {
                    List<GithubView> githubSearchResult = NetworkUtils.getDataFromApi(searchQueryUrlString);
                    return githubSearchResult;
                } catch (IOException e){
                    e.printStackTrace();
                    return null;
                }

            }
            @Override
            public void deliverResult(@Nullable List<GithubView> githubjson){
                mRepositoryList = githubjson;
                super.deliverResult(githubjson);
            }
        };
    }

    public void onLoadFinished(Loader<List<GithubView>> Loader, List<GithubView> data) {
        if (data == null){
            adapter.clear();
            adapter.addAll(data);
            showJsonDataView();
        }
    }


    private void showJsonDataView(){

    }

    public void searchRepo(View view){
        makeGithubSearchQuery();
    }

    private void makeGithubSearchQuery() {
        String githubQuery = searchUser.getText().toString();

        Bundle queryBundle = new Bundle();
        queryBundle.putString(GITHUB_QUERY_TAG, githubQuery);

        LoaderManager loaderManager = getSupportLoaderManager();
        Loader<String> githubSearchLoader = loaderManager.getLoader(GITHUB_SEARCH_LOADER);
        if (githubSearchLoader == null){
            loaderManager.initLoader(GITHUB_SEARCH_LOADER, queryBundle, this);
        } else {
            loaderManager.restartLoader(GITHUB_SEARCH_LOADER, queryBundle, this);
        }

    }
}