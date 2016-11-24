package animandroid.com;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.CheckBox;

public class MainActivity extends AppCompatActivity {
    CheckBox mCheckBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mCheckBox = (CheckBox) findViewById(R.id.checkbox);
        final Button alphaButton = (Button) findViewById(R.id.alphaButton);
        final Button translateButton = (Button) findViewById(R.id.translateButton);
        final Button rotateButton = (Button) findViewById(R.id.rotateButton);
        final Button scaleButton = (Button) findViewById(R.id.scaleButton);
        final Button setButton = (Button) findViewById(R.id.setButton);

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
    }

    private void setupAnimation(View view, final Animation animation,
                                final int animationID) {
        view.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // If the button is checked, load the animation from the given resource
                // id instead of using the passed-in animation paramter. See the xml files
                // for the details on those animations.
                v.startAnimation(mCheckBox.isChecked() ?
                        AnimationUtils.loadAnimation(MainActivity.this, animationID) :
                        animation);
            }
        });
    }
}
