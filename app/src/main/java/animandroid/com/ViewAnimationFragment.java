package animandroid.com;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.CheckBox;


public class ViewAnimationFragment extends Fragment {
    private CheckBox mCheckBox;

    public ViewAnimationFragment() {
        // Required empty public constructor
    }

    public static ViewAnimationFragment newInstance() {
        ViewAnimationFragment fragment = new ViewAnimationFragment();
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
        View mainView = inflater.inflate(R.layout.fragment_view_animation, container, false);

        mCheckBox = (CheckBox) mainView.findViewById(R.id.checkbox);
        final Button alphaButton = (Button) mainView.findViewById(R.id.alphaButton);
        final Button translateButton = (Button) mainView.findViewById(R.id.translateButton);
        final Button rotateButton = (Button) mainView.findViewById(R.id.rotateButton);
        final Button scaleButton = (Button) mainView.findViewById(R.id.scaleButton);
        final Button setButton = (Button) mainView.findViewById(R.id.setButton);

        // Fade the button out and back in
        final AlphaAnimation alphaAnimation = new AlphaAnimation(1, 0);
        alphaAnimation.setDuration(1000);

        // Move the button over and then back
        final TranslateAnimation translateAnimation =
                new TranslateAnimation(Animation.ABSOLUTE, 0,
                        Animation.RELATIVE_TO_PARENT, 1,
                        Animation.ABSOLUTE, 0, Animation.ABSOLUTE, 100);
        translateAnimation.setDuration(1000);

        // Spin the button around in a full circle
        final RotateAnimation rotateAnimation = new RotateAnimation(0, 360,
                Animation.RELATIVE_TO_SELF, .5f, Animation.RELATIVE_TO_SELF, .5f);
        rotateAnimation.setDuration(1000);

        // Scale the button in X and Y.
        final ScaleAnimation scaleAnimation = new ScaleAnimation(1, 2, 1, 2);
        scaleAnimation.setDuration(1000);

        // Run the animations above in sequence on the final button. Looks horrible.
        final AnimationSet setAnimation = new AnimationSet(true);
        setAnimation.addAnimation(alphaAnimation);
        setAnimation.addAnimation(translateAnimation);
        setAnimation.addAnimation(rotateAnimation);
        setAnimation.addAnimation(scaleAnimation);

        setupAnimation(alphaButton, alphaAnimation, R.anim.alpha_anim);
        setupAnimation(translateButton, translateAnimation, R.anim.translate_anim);
        setupAnimation(rotateButton, rotateAnimation, R.anim.rotate_anim);
        setupAnimation(scaleButton, scaleAnimation, R.anim.scale_anim);
        setupAnimation(setButton, setAnimation, R.anim.set_anim);

        return mainView;
    }

    private void setupAnimation(View view, final Animation animation,
                                final int animationID) {
        view.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // If the button is checked, load the animation from the given resource
                // id instead of using the passed-in animation paramter. See the xml files
                // for the details on those animations.
                v.startAnimation(mCheckBox.isChecked() ?
                        AnimationUtils.loadAnimation(getContext(), animationID) :
                        animation);
            }
        });
    }
}
