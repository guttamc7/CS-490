package finalproject.com.getfit.baseworkout;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.support.v4.app.Fragment;
import finalproject.com.getfit.R;
import finalproject.com.getfit.viewpager.RootFragment;

/**
 * Created by Gurumukh on 3/2/15.
 */
public class BaseWorkoutDetailsFragment extends Fragment {
    BaseWorkoutFragment b;
    private WebView webView;
    View v;
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        b = new BaseWorkoutFragment();
        v = inflater.inflate(R.layout.fragment_base_workout_details, container, false);
        webView = (WebView) v.findViewById(R.id.webView);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl(b.getWebLink());
        return v;
    }
}
