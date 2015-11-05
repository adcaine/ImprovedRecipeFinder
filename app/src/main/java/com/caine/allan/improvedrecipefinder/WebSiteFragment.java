package com.caine.allan.improvedrecipefinder;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by allancaine on 2015-08-09.
 */
public class WebSiteFragment extends Fragment  {

    private static final String ARGS_URL = "args_url";

    private String mUrl;

    @Bind(R.id.webview)
    WebView mWebView;

    @Bind(R.id.webviewProgressBar)
    ProgressBar mProgressBar;

    @Bind(R.id.titleTextView)
    TextView mTitleTextView;

    public static Fragment newInstance(String url){
        Bundle args = new Bundle();
        args.putString(ARGS_URL, url);
        Fragment fragment = new WebSiteFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        setHasOptionsMenu(true);

        Bundle args = getArguments();

        mUrl = args.getString(ARGS_URL);

    }

    @Nullable
    @Override
    @SuppressLint("SetJavaScriptEnabled")
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_website, container, false);
        ButterKnife.bind(this, view);

        mProgressBar.setMax(100);

        mWebView.getSettings().setJavaScriptEnabled(true);

        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return false;
            }
        });

        mWebView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                if (newProgress == 100) {
                    mProgressBar.setVisibility(View.INVISIBLE);
                } else {
                    mProgressBar.setVisibility(View.VISIBLE);
                    mProgressBar.setProgress(newProgress);
                }
            }

            @Override
            public void onReceivedTitle(WebView view, String title) {
                mTitleTextView.setText(title);
            }
        });

        mWebView.loadUrl(mUrl);

        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.recipe_finder_detail, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_item_share:
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.share_subject));
                intent.putExtra(Intent.EXTRA_TEXT, mUrl);
                startActivity(Intent.createChooser(intent, getString(R.string.chooser)));
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
