package animandroid.com;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.BounceInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.TextView;


public class InterpolatorFragment extends Fragment {
    Button mButton;
    TextView mText;

    public InterpolatorFragment() {
        // Required empty public constructor
    }


    public static InterpolatorFragment newInstance() {
        InterpolatorFragment fragment = new InterpolatorFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View mainView = inflater.inflate(R.layout.fragment_interpolator, container, false);
        mButton = (Button) mainView.findViewById(R.id.the_button);
        mText = (TextView) mainView.findViewById(R.id.the_text);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                animate();
            }
        });

        return mainView;
    }

    private void animate() {
        mText.clearAnimation();
        TranslateAnimation translation;
        translation = new TranslateAnimation(0f, 0F, 0f, getDisplayHeight());
        translation.setStartOffset(500);
        translation.setDuration(2000);
        translation.setFillAfter(true);
        translation.setInterpolator(new BounceInterpolator());
        mText.startAnimation(translation);
    }

    private int getDisplayHeight() {
        DisplayMetrics metrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(metrics);
        return metrics.widthPixels;
    }


}
