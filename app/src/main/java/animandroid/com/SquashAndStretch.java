package animandroid.com;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.CheckBox;


public class SquashAndStretch extends Fragment {
    private Button mButton;
    private CheckBox mCheckBox;

    private static final AccelerateInterpolator sAccelerator = new AccelerateInterpolator();
    private static final DecelerateInterpolator sDecelerator = new DecelerateInterpolator();

    ViewGroup mContainer = null;
    private static final long BASE_DURATION = 300;
    private long sAnimatorScale = 1;

    public SquashAndStretch() {
        // Required empty public constructor
    }


    public static SquashAndStretch newInstance() {
        SquashAndStretch fragment = new SquashAndStretch();
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
        final View view = inflater.inflate(R.layout.fragment_squash_and_stretch, container, false);
        mCheckBox = (CheckBox) view.findViewById(R.id.checkbox);
        mContainer = (ViewGroup) view.findViewById(R.id.container);
        view.findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sAnimatorScale = mCheckBox.isChecked() ? 1 : 5;
                long animationDuration = (long) (BASE_DURATION * sAnimatorScale);

                // Scale around bottom/middle to simplify squash against the window bottom
                view.setPivotX(view.getWidth() / 2);
                view.setPivotY(view.getHeight());

                // Animate the button down, accelerating, while also stretching in Y and squashing in X
                PropertyValuesHolder pvhTY = PropertyValuesHolder.ofFloat(View.TRANSLATION_Y,
                        mContainer.getHeight() - view.getHeight());
                PropertyValuesHolder pvhSX = PropertyValuesHolder.ofFloat(View.SCALE_X, .7f);
                PropertyValuesHolder pvhSY = PropertyValuesHolder.ofFloat(View.SCALE_Y, 1.2f);
                ObjectAnimator downAnim = ObjectAnimator.ofPropertyValuesHolder(
                        view, pvhTY, pvhSX, pvhSY);
                downAnim.setInterpolator(sAccelerator);
                downAnim.setDuration((long) (animationDuration * 2));

                // Stretch in X, squash in Y, then reverse
                pvhSX = PropertyValuesHolder.ofFloat(View.SCALE_X, 2);
                pvhSY = PropertyValuesHolder.ofFloat(View.SCALE_Y, .5f);
                ObjectAnimator stretchAnim =
                        ObjectAnimator.ofPropertyValuesHolder(view, pvhSX, pvhSY);
                stretchAnim.setRepeatCount(1);
                stretchAnim.setRepeatMode(ValueAnimator.REVERSE);
                stretchAnim.setInterpolator(sDecelerator);
                stretchAnim.setDuration(animationDuration);

                // Animate back to the start
                pvhTY = PropertyValuesHolder.ofFloat(View.TRANSLATION_Y, 0);
                pvhSX = PropertyValuesHolder.ofFloat(View.SCALE_X, 1);
                pvhSY = PropertyValuesHolder.ofFloat(View.SCALE_Y, 1);
                ObjectAnimator upAnim =
                        ObjectAnimator.ofPropertyValuesHolder(view, pvhTY, pvhSX, pvhSY);
                upAnim.setDuration((long) (animationDuration * 2));
                upAnim.setInterpolator(sDecelerator);

                AnimatorSet set = new AnimatorSet();
                set.playSequentially(downAnim, stretchAnim, upAnim);
                set.start();
            }
        });

        return view;
    }

}
