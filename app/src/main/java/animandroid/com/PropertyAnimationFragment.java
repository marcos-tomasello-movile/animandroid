package animandroid.com;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.BounceInterpolator;
import android.widget.Button;
import android.widget.CheckBox;


public class PropertyAnimationFragment extends Fragment {
    CheckBox mCheckBox;

    public PropertyAnimationFragment() {
        // Required empty public constructor
    }


    public static PropertyAnimationFragment newInstance() {
        PropertyAnimationFragment fragment = new PropertyAnimationFragment();
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
        View mainView =  inflater.inflate(R.layout.fragment_property_animation, container, false);
        mCheckBox = (CheckBox) mainView.findViewById(R.id.checkbox);
        final Button alphaButton = (Button) mainView.findViewById(R.id.alphaButton);
        final Button translateButton = (Button) mainView.findViewById(R.id.translateButton);
        final Button rotateButton = (Button) mainView.findViewById(R.id.rotateButton);
        final Button scaleButton = (Button) mainView.findViewById(R.id.scaleButton);
        final Button setButton = (Button) mainView.findViewById(R.id.setButton);

        // Fade the button out and back in
        ObjectAnimator alphaAnimation = ObjectAnimator.ofFloat(alphaButton,
                View.ALPHA, 0);
        alphaAnimation.setRepeatCount(1);
        alphaAnimation.setRepeatMode(ValueAnimator.REVERSE);

        // Move the button over to the right and then back
        ObjectAnimator translateAnimation =
                ObjectAnimator.ofFloat(translateButton, View.TRANSLATION_X, 800);
        translateAnimation.setRepeatCount(1);
        translateAnimation.setRepeatMode(ValueAnimator.REVERSE);


        // Spin the button around in a full circle
        ObjectAnimator rotateAnimation =
                ObjectAnimator.ofFloat(rotateButton, View.ROTATION, 360);
        rotateAnimation.setRepeatCount(1);
        rotateAnimation.setRepeatMode(ValueAnimator.REVERSE);

        // Scale the button in X and Y. Note the use of PropertyValuesHolder to animate
        // multiple properties on the same object in parallel.
        PropertyValuesHolder pvhX = PropertyValuesHolder.ofFloat(View.SCALE_X, 2);
        PropertyValuesHolder pvhY = PropertyValuesHolder.ofFloat(View.SCALE_Y, 2);
        ObjectAnimator scaleAnimation =
                ObjectAnimator.ofPropertyValuesHolder(scaleButton, pvhX, pvhY);
        scaleAnimation.setRepeatCount(1);
        scaleAnimation.setRepeatMode(ValueAnimator.REVERSE);

        // Run the animations above in sequence
        AnimatorSet setAnimation = new AnimatorSet();
        setAnimation.play(translateAnimation).after(alphaAnimation).before(rotateAnimation);
        setAnimation.play(rotateAnimation).before(scaleAnimation);

        setupAnimation(alphaButton, alphaAnimation, R.animator.fade);
        setupAnimation(translateButton, translateAnimation, R.animator.move);
        setupAnimation(rotateButton, rotateAnimation, R.animator.spin);
        setupAnimation(scaleButton, scaleAnimation, R.animator.scale);
        setupAnimation(setButton, setAnimation, R.animator.combo);

        return mainView;
    }

    private void setupAnimation(View view, final Animator animation, final int animationID) {
        view.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // If the button is checked, load the animation from the given resource
                // id instead of using the passed-in animation parameter. See the xml files
                // for the details on those animations.
                if (mCheckBox.isChecked()) {
                    Animator anim = AnimatorInflater.loadAnimator(getContext(), animationID);
                    anim.setTarget(v);
                    anim.start();
                    return;
                }
                animation.start();
            }
        });
    }
}
