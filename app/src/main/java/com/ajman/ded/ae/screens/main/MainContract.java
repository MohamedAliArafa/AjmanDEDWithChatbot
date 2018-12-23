package com.ajman.ded.ae.screens.main;

/**
 * Created by root on 10/2/17.
 */

public interface MainContract {
    interface ModelView {
        void launchLanding();

        void launchLogin();

        void launchLoginMenu();

        void launchLanguage();

        void requestRestartActivity();

        void launchIntro();
    }

    interface UserActions {

    }
}
