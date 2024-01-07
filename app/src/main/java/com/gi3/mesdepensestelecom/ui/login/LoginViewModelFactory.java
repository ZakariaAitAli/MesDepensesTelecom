package com.gi3.mesdepensestelecom.ui.login;

import android.content.SharedPreferences;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.gi3.mesdepensestelecom.data.LoginRepository;

/**
 * ViewModel provider factory to instantiate LoginViewModel.
 * Required given LoginViewModel has a non-empty constructor
 */
public class LoginViewModelFactory implements ViewModelProvider.Factory {

    private final LoginRepository loginRepository;
    private SharedPreferences sharedpreferences;

    public LoginViewModelFactory(LoginRepository loginRepository, SharedPreferences sharedPreferences) {
        this.loginRepository = loginRepository;
        this.sharedpreferences = sharedPreferences;
    }


    @NonNull
    @Override
    @SuppressWarnings("unchecked")
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(LoginViewModel.class)) {
            return (T) new LoginViewModel(loginRepository, sharedpreferences);
        } else {
            throw new IllegalArgumentException("Unknown ViewModel class");
        }
    }
}
