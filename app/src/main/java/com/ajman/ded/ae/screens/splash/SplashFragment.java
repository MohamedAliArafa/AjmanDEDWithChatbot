package com.ajman.ded.ae.screens.splash;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.ajman.ded.ae.R;
import com.ajman.ded.ae.screens.main.MainActivity;
import com.ajman.ded.ae.utility.AppPreferenceManager;
import com.ajman.ded.ae.utility.SharedTool.UserData;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.resource.gif.GifDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import static com.ajman.ded.ae.screens.main.MainActivity.SplashAnimation;

public class SplashFragment extends Fragment implements SplashContract.ModelView {

    SplashPresenter splashPresenter;
    Runnable mRunnable;
    Handler mHandler;
    ImageView imageView;

    public SplashFragment() {
        // Required empty public constructor
    }

    public static SplashFragment newInstance() {
        return new SplashFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_splash, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        imageView = view.findViewById(R.id.imageView);
        splashPresenter = new SplashPresenter(this, getContext(), getLifecycle());

        onGifFinished();
    }

    public boolean isFirstLaunch() {
        return AppPreferenceManager.getBool(getContext(), AppPreferenceManager.KEY_IS_FIRST_TIME, true);
    }

    @Override
    public void startTimer() {
//        if (SplashAnimation) {
//            Glide.with(this).asGif().load(R.drawable.ic_splash)
//                    .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.NONE)).listener(new RequestListener<GifDrawable>() {
//                @Override
//                public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<GifDrawable> target, boolean isFirstResource) {
//                    onGifFinished();
//                    return false;
//                }
//
//                @Override
//                public boolean onResourceReady(final GifDrawable resource, Object model, Target<GifDrawable> target, DataSource dataSource, boolean isFirstResource) {
//                    resource.setLoopCount(1);
//                    new Thread(new Runnable() {
//                        @Override
//                        public void run() {
//                            try {
//                                Thread.sleep(200);
//                            } catch (InterruptedException e) {
//                                e.printStackTrace();
//                            }
//                            while (true) {
//                                if (!resource.isRunning()) {
//                                    onGifFinished();
//                                    break;
//                                }
//                            }
//                        }
//                    }).start();
//                    return false;
//                }
//            }).into(imageView);
//        } else {
//            onGifFinished();
//        }
    }

    private void onGifFinished() {
        if (isFirstLaunch()) {
            ((MainActivity) getContext()).launchLanguage();
        } else if (UserData.getUserObject(getContext()) == null || UserData.getUserObject(getContext()).getUserId() == null) {
            ((MainActivity) getContext()).launchLoginMenu();
        } else {
            ((MainActivity) getContext()).launchLanding();
        }
    }

    @Override
    public void killTimer() {
        if (mRunnable != null) {
            mHandler.removeCallbacks(mRunnable);
        }
    }
}
