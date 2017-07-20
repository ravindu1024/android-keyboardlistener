package com.rw.keyboardutils;

import android.databinding.DataBindingUtil;
import android.inputmethodservice.Keyboard;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.rw.keyboardlistener.KeyboardUtils;
import com.rw.keyboardutils.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity
{
    private ActivityMainBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        mBinding.setActivity(this);

        mBinding.status.setText("unregistered");

        setTitle(getString(R.string.app_name));
    }

    public void onRegisterClicked()
    {
        onUnregisterClicked();

        KeyboardUtils.addKeyboardToggleListener(this, new KeyboardUtils.SoftKeyboardToggleListener()
        {
            @Override
            public void onToggleSoftKeyboard(boolean isVisible)
            {
                mBinding.visibilityText.setText("Visibility: " + isVisible);
            }
        });

        mBinding.status.setText("registered");
    }

    public void onUnregisterClicked()
    {
        KeyboardUtils.removeAllKeyboardToggleListeners();
        mBinding.status.setText("unregistered");
    }

    public void onToggleClicked()
    {
        KeyboardUtils.toggleKeyboardVisibility(this);
    }

    public void onForceCloseClicked()
    {
        KeyboardUtils.forceCloseKeyboard(mBinding.editTest);
    }
}
