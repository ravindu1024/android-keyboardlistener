package com.rw.keyboardlistener;

import android.app.Activity;
import android.graphics.Rect;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.content.Context;
import android.view.inputmethod.InputMethodManager;

import java.util.HashMap;

/**
 * Based on the following Stackoverflow answer:
 * http://stackoverflow.com/questions/2150078/how-to-check-visibility-of-software-keyboard-in-android
 */
@SuppressWarnings("WeakerAccess")
public class KeyboardUtils implements ViewTreeObserver.OnGlobalLayoutListener
{
    private final static int MAGIC_NUMBER = 200;

    private SoftKeyboardToggleListener mCallback;
    private View mRootView;
    public static Boolean sPrevValue = null;
    private float mScreenDensity = 1;
    private static HashMap<SoftKeyboardToggleListener, KeyboardUtils> sListenerMap = new HashMap<>();

    public interface SoftKeyboardToggleListener
    {
        void onToggleSoftKeyboard(boolean isVisible);
    }


    @Override
    public void onGlobalLayout()
    {
        Rect r = new Rect();
        mRootView.getWindowVisibleDisplayFrame(r);

        int heightDiff = mRootView.getRootView().getHeight() - (r.bottom - r.top);
        float dp = heightDiff/ mScreenDensity;
        boolean isVisible = dp > MAGIC_NUMBER;

        if (mCallback != null && (sPrevValue == null || isVisible != sPrevValue)) {
            sPrevValue = isVisible;
            mCallback.onToggleSoftKeyboard(isVisible);
        }
    }

    /**
     * Keyboard visibility getter
     * @return whether the keyboard is shown or not.
     */
    public static boolean isKeyboardShown() {
        return sPrevValue;
    }

    /**
     * Add a new keyboard listener
     * @param act calling activity
     * @param listener callback
     */
    public static void addKeyboardToggleListener(Activity act, SoftKeyboardToggleListener listener)
    {
        removeKeyboardToggleListener(listener);

        sListenerMap.put(listener, new KeyboardUtils(act, listener));
    }

    /**
     * Remove a registered listener
     * @param listener {@link SoftKeyboardToggleListener}
     */
    public static void removeKeyboardToggleListener(SoftKeyboardToggleListener listener)
    {
        if(sListenerMap.containsKey(listener))
        {
            KeyboardUtils k = sListenerMap.get(listener);
            k.removeListener();

            sListenerMap.remove(listener);
        }
    }

    /**
     * Remove all registered keyboard listeners
     */
    public static void removeAllKeyboardToggleListeners()
    {
        for(SoftKeyboardToggleListener l : sListenerMap.keySet())
            sListenerMap.get(l).removeListener();

        sListenerMap.clear();
    }

    /**
     * Manually toggle soft keyboard visibility
     * @param context calling context
     */
    public static void toggleKeyboardVisibility(Context context)
    {
        InputMethodManager inputMethodManager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
    }

    /**
     * Force closes the soft keyboard
     * @param activeView the view with the keyboard focus
     */
    public static void forceCloseKeyboard(View activeView)
    {
        InputMethodManager inputMethodManager = (InputMethodManager) activeView.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(activeView.getWindowToken(), 0);
    }

    /**
     * Closes soft keyboard
     * @param context is the context where the keyboard was open from
     */
    public static void closeSoftKeyboard(Context context) {
        InputMethodManager imm = (InputMethodManager)context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(0, InputMethodManager.HIDE_IMPLICIT_ONLY);
    }

    /**
     * Opens soft keyboard
     * @param context is the context where the keyboard was open from
     */
    public static void openSoftKeyboard(Context context) {
        InputMethodManager imm = (InputMethodManager)context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(0, InputMethodManager.SHOW_IMPLICIT);
    }

    /**
     * Force close keyboard which got open unnaturally using Activity
     * @param activity is the activity where the keyboard was open from
     */
    public static void forceCloseSoftKeyboardByActivity(Activity activity) {
        InputMethodManager inputManager = (InputMethodManager)
                activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        inputManager.hideSoftInputFromWindow((null == activity.getCurrentFocus()) ?
                        null : activity.getCurrentFocus().getWindowToken(),
                InputMethodManager.HIDE_NOT_ALWAYS);
    }

    private void removeListener()
    {
        mCallback = null;

        mRootView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
    }

    private KeyboardUtils(Activity act, SoftKeyboardToggleListener listener)
    {
        mCallback = listener;

        mRootView = ((ViewGroup) act.findViewById(android.R.id.content)).getChildAt(0);
        mRootView.getViewTreeObserver().addOnGlobalLayoutListener(this);

        mScreenDensity = act.getResources().getDisplayMetrics().density;
    }

}
