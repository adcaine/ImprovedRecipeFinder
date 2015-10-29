package com.caine.allan.improvedrecipefinder;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.Fragment;

import com.caine.allan.onefragmentactivity.SingleFragmentActivity;

/**
 * Created by allancaine on 2015-08-09.
 */
public class WebSiteActivity extends SingleFragmentActivity {


    public static Intent newIntent(Context context, String url){
        Intent intent = new Intent(context, WebSiteActivity.class);
        intent.setData(Uri.parse(url));
        return intent;
    }

    @Override
    protected Fragment createFragment() {
        return new WebSiteFragment();
    }


}
