package com.ajman.ded.ae.audio_recorder;

import android.animation.LayoutTransition;
import android.animation.ValueAnimator;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Handler;
import android.os.SystemClock;
import android.text.format.DateFormat;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.Chronometer;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.ajman.ded.ae.R;
import com.ajman.ded.ae.libs.LocaleManager;

import java.util.Locale;
import java.util.Objects;
import java.util.UUID;

import androidx.core.content.ContextCompat;

import static com.ajman.ded.ae.libs.LocaleManager.LANGUAGE_ENGLISH;

public class AudioRecordButton extends RelativeLayout implements View.OnTouchListener {

    private final int DEFAULT_ICON_SIZE = Math.round(getResources().getDimension(R.dimen.default_icon_size));
    private final int DEFAULT_REMOVE_ICON_SIZE = Math.round(getResources().getDimension(R.dimen.default_icon_remove_size));

    private Context mContext;
    private RelativeLayout mLayoutTimer;
    private RelativeLayout mLayoutVoice;

    private Chronometer mChronometer;
    private ImageView mImageView;
    private ImageButton mImageButton;
    private AudioListener mAudioListener;
    private AudioRecording mAudioRecording;

    private float initialX = 0;
    private float initialXImageButton;
    private float initialTouchX;

    private int recorderImageWidth = 0;
    private int recorderImageHeight = 0;
    private int removeImageWidth = 0;
    private int removeImageHeight = 0;
    private Drawable drawableMicVoice;
    private Drawable drawableRemoveButton;
    private boolean isPlaying = false;
    private boolean isPausing = false;


    private WindowManager.LayoutParams params;

    public AudioRecordButton(Context context) {
        super(context);
        setupLayout(context, null, -1, -1);
    }

    public AudioRecordButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        setupLayout(context, attrs, -1, -1);
    }

    public AudioRecordButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setupLayout(context, attrs, defStyleAttr, -1);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public AudioRecordButton(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);

        setupLayout(context, attrs, defStyleAttr, defStyleRes);
    }

    WindowManager.LayoutParams getViewParams() {
        return params;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return true;
    }

    private void moveImageToBack() {
        mImageButton.setAlpha(0.5f);
        final ValueAnimator positionAnimator =
                ValueAnimator.ofFloat(mImageView.getX(), initialX);

        positionAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
        positionAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float x = (Float) animation.getAnimatedValue();
                mImageView.setX(x);
                if (mImageView.getX() > DEFAULT_REMOVE_ICON_SIZE) {
                    unRevealSizeToRemove();
                }
            }
        });

        positionAnimator.setDuration(200);
        positionAnimator.start();
    }

    private void startRecord() {
        if (mAudioListener != null) {
            AudioListener audioListener = new AudioListener() {
                @Override
                public boolean isPermissionGranted() {
                    return mAudioListener.isPermissionGranted();
                }

                @Override
                public void onStop(RecordingItem recordingItem) {
                    mAudioListener.onStop(recordingItem);
                }

                @Override
                public void onCancel() {
                    mAudioListener.onCancel();
                }

                @Override
                public void onError(Exception e) {
                    mAudioListener.onError(e);
                }
            };

            mAudioRecording =
                    new AudioRecording(mContext)
                            .setNameFile("/" + UUID.randomUUID() + "-audio.m4a")
                            .start(audioListener);
        }
    }

    private void stopRecord(final Boolean cancel) {
        if (mAudioListener != null) {
            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    mAudioRecording.stop(cancel);
                    unRevealImageView();
                    isPlaying = false;
                    isPausing = false;
                }
            }, 300);
        }
    }

    public void setOnAudioListener(AudioListener audioListener) {
        mAudioListener = audioListener;
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public void setupLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        mContext = context;

        /**
         *  Component Attributes
         */
        if (attrs != null && defStyleAttr == -1 && defStyleRes == -1) {
            TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.AudioButton,
                    defStyleAttr, defStyleRes);

            recorderImageWidth = (int) typedArray.getDimension(R.styleable.AudioButton_recorder_image_size,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
            recorderImageHeight = (int) typedArray.getDimension(R.styleable.AudioButton_recorder_image_size,
                    ViewGroup.LayoutParams.WRAP_CONTENT);

            removeImageWidth = (int) typedArray.getDimension(R.styleable.AudioButton_remove_image_size,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
            removeImageHeight = (int) typedArray.getDimension(R.styleable.AudioButton_remove_image_size,
                    ViewGroup.LayoutParams.WRAP_CONTENT);

            drawableMicVoice = typedArray.getDrawable(R.styleable.AudioButton_recorder_image);
            drawableRemoveButton = typedArray.getDrawable(R.styleable.AudioButton_remove_image);
        }

        /**
         * layout to chronometer
         */
        mLayoutTimer = new RelativeLayout(context);
        mLayoutTimer.setId(9 + 1);
        mLayoutTimer.setVisibility(VISIBLE);

        LayoutParams layoutParams = new LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);

        layoutParams.addRule(RelativeLayout.ALIGN_PARENT_TOP, RelativeLayout.TRUE);
        layoutParams.addRule(RelativeLayout.CENTER_HORIZONTAL, RelativeLayout.TRUE);
        Integer margin = Math.round(getResources().getDimension(R.dimen.chronometer_margin));
        layoutParams.setMargins(margin, margin, margin, margin);
        addView(mLayoutTimer, layoutParams);

        /**
         * chronometer
         */
        mChronometer = new Chronometer(context);
        mChronometer.setTextLocale(Locale.US);
        mChronometer.setTextColor(getResources().getColor(R.color.colorPrimary));
        mChronometer.setTextSize(18);

        LayoutParams layoutParamsChronometer = new LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);

        layoutParamsChronometer.addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE);
        mLayoutTimer.addView(mChronometer, layoutParamsChronometer);

        /**
         * Layout to voice and cancel audio
         */
        mLayoutVoice = new RelativeLayout(context);
        LayoutParams layoutVoiceParams = new LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        );
        layoutVoiceParams.addRule(RelativeLayout.BELOW, 9 + 1);
        addView(mLayoutVoice, layoutVoiceParams);

        /**
         * Image voice
         */
        mImageView = new ImageView(context);
        mImageView.setBackground(drawableMicVoice != null ? drawableMicVoice : ContextCompat.getDrawable(context, R.drawable.mic_shape));
        LayoutParams layoutParamImage = new LayoutParams(
                recorderImageWidth > 0 ? recorderImageWidth : DEFAULT_ICON_SIZE,
                recorderImageHeight > 0 ? recorderImageHeight : DEFAULT_ICON_SIZE);
        layoutParamImage.addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE);
        mLayoutVoice.addView(mImageView, layoutParamImage);
        mImageView.setOnTouchListener(this);

        /**
         * Image Button
         */
        mImageButton = new ImageButton(context);
        mImageButton.setVisibility(INVISIBLE);
        mImageButton.setAlpha(0.5f);
        mImageButton.setImageDrawable(drawableRemoveButton != null ? drawableRemoveButton : ContextCompat.getDrawable(context, R.drawable.ic_close));
        mImageButton.setBackground(ContextCompat.getDrawable(context, R.drawable.shape_circle));
        mImageButton.setColorFilter(Color.WHITE);

        LayoutParams layoutParamImageButton = new LayoutParams(
                ((removeImageWidth > 0) && (removeImageWidth < DEFAULT_REMOVE_ICON_SIZE)) ? removeImageWidth : DEFAULT_REMOVE_ICON_SIZE,
                ((removeImageHeight > 0) && (removeImageHeight < DEFAULT_REMOVE_ICON_SIZE)) ? removeImageHeight : DEFAULT_REMOVE_ICON_SIZE
        );
        layoutParamImageButton.setMargins(20, 0, 20, 0);
        layoutParamImageButton.addRule(RelativeLayout.CENTER_VERTICAL, RelativeLayout.TRUE);

        mLayoutVoice.addView(mImageButton, layoutParamImageButton);

        initialXImageButton = mImageButton.getX();

    }

    public void changeImageView() {
        LayoutTransition transition = new LayoutTransition();
        transition.setDuration(600);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            transition.enableTransitionType(LayoutTransition.CHANGING);
        }
        mLayoutTimer.setLayoutTransition(transition);
        setLayoutTransition(transition);

        mChronometer.setTextLocale(Locale.US);
        mChronometer.setBase(SystemClock.elapsedRealtime());
        mChronometer.setOnChronometerTickListener(cArg -> {
            long t = SystemClock.elapsedRealtime() - cArg.getBase();
            cArg.setText(String.format("%s", DateFormat.format("mm:ss", t), Locale.US));
        });
        mChronometer.start();

        mImageView.setScaleX(0.8f);
        mImageView.setScaleY(0.8f);
        requestLayout();
    }

    public void changeSizeToRemove() {
        if (mImageButton.getLayoutParams().width != mImageView.getWidth()) {
            mImageButton.getLayoutParams().width = mImageView.getWidth();
            mImageButton.getLayoutParams().height = mImageView.getHeight();
            mImageButton.requestLayout();
            mImageButton.setX(0);
        }
    }

    public void unRevealSizeToRemove() {
        mImageButton.getLayoutParams().width = ((removeImageWidth > 0) && (removeImageWidth < DEFAULT_REMOVE_ICON_SIZE)) ? removeImageWidth : DEFAULT_REMOVE_ICON_SIZE;
        mImageButton.getLayoutParams().height = ((removeImageHeight > 0) && (removeImageHeight < DEFAULT_REMOVE_ICON_SIZE)) ? removeImageHeight : DEFAULT_REMOVE_ICON_SIZE;
        mImageButton.requestLayout();
    }

    public void unRevealImageView() {
        mChronometer.stop();
        mChronometer.setBase(SystemClock.elapsedRealtime());

        mImageView.setScaleX(1f);
        mImageView.setScaleY(1f);
        requestLayout();
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (mAudioListener.isPermissionGranted()) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    if (!isPlaying && !isPausing) {
                        requestFocus();
                        setFocusableInTouchMode(true);
                        getParent().requestDisallowInterceptTouchEvent(true);
                        isPlaying = true;
                        initialTouchX = event.getRawX();
                        changeImageView();

                        if (initialX == 0) {
                            initialX = mImageView.getX();
                        }

                        mLayoutTimer.setVisibility(VISIBLE);
                        mImageButton.setVisibility(VISIBLE);
                        startRecord();
                        return true;
                    }
                    break;
                case MotionEvent.ACTION_MOVE:

                    if (isPlaying && !isPausing) {
                        mImageView.setX(event.getRawX() - mImageView.getWidth() / 2);

                        if (mImageView.getX() < DEFAULT_REMOVE_ICON_SIZE - 20) {
                            mImageView.setX(20);
                            changeSizeToRemove();
                        } else if (mImageView.getX() > DEFAULT_REMOVE_ICON_SIZE + DEFAULT_REMOVE_ICON_SIZE / 2) {
                            unRevealSizeToRemove();
                        }

                        if (Objects.equals(LocaleManager.getLanguage(mContext), LANGUAGE_ENGLISH)) {
                            if (mImageView.getX() <= 0) {
                                mImageButton.setX(20);
                            }
                        } else {
                            if (mImageView.getX() >= 0) {
                                mImageButton.setX(20);
                            }
                        }

                        if (mImageView.getX() > initialX) {
                            mImageView.setX(initialX);
                        }
                    }

                    break;
                case MotionEvent.ACTION_UP:
                    if (isPlaying && !isPausing) {
                        getParent().requestDisallowInterceptTouchEvent(false);
                        isPausing = true;
                        moveImageToBack();

                        mLayoutTimer.setVisibility(VISIBLE);
                        mImageButton.setVisibility(INVISIBLE);

                        if (mImageView.getX() < DEFAULT_REMOVE_ICON_SIZE - 10) {
                            stopRecord(true);
                        } else {
                            stopRecord(false);
                        }
                    }
                    break;
                default:
                    return false;
            }
        }
        return true;
    }
}