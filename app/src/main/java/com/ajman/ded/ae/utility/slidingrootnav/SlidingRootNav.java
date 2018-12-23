package com.ajman.ded.ae.utility.slidingrootnav;

/**
 * on 25.03.2017.
 */

public interface SlidingRootNav {

    boolean isMenuClosed();

    boolean isMenuOpened();

    boolean isMenuLocked();

    void setMenuLocked(boolean locked);

    void closeMenu();

    void closeMenu(boolean animated);

    void openMenu();

    void openMenu(boolean animated);

    SlidingRootNavLayout getLayout();

}
