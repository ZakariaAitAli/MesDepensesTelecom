package com.gi3.mesdepensestelecom.ui.login;

import android.content.SharedPreferences;
import android.util.Patterns;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.gi3.mesdepensestelecom.R;
import com.gi3.mesdepensestelecom.data.LoginRepository;
import com.gi3.mesdepensestelecom.data.Result;
import com.gi3.mesdepensestelecom.data.model.LoggedInUser;

public class LoginViewModel extends ViewModel {

    private final MutableLiveData<LoginFormState> loginFormState = new MutableLiveData<>();
    private final MutableLiveData<LoginResult> loginResult = new MutableLiveData<>();
    private final LoginRepository loginRepository;
    private SharedPreferences sharedPreferences;

    LoginViewModel(LoginRepository loginRepository, SharedPreferences sharedPreferences) {
        this.loginRepository = loginRepository;
        this.sharedPreferences = sharedPreferences;
    }


    LiveData<LoginFormState> getLoginFormState() {
        return loginFormState;
    }

    LiveData<LoginResult> getLoginResult() {
        return loginResult;
    }

    public void login(String username, String password, SharedPreferences sharedPreferences) {
        // can be launched in a separate asynchronous job
        Result<LoggedInUser> result = loginRepository.login(username, password);

            if (result instanceof Result.Success) {
            LoggedInUser data = ((Result.Success<LoggedInUser>) result).getData();
            loginResult.setValue(new LoginResult(new LoggedInUserView(data.getDisplayName())));
            saveUserSession(data.getUserId(), data.getDisplayName());
        } else {
            loginResult.setValue(new LoginResult(R.string.login_failed));
        }
    }
    private void saveUserSession(String userId, String displayName) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("user_id", userId);
        editor.putString("display_name", displayName);
        editor.apply();
    }

    public void loginDataChanged(String username, String password) {
        if (!isUserNameValid(username)) {
            loginFormState.setValue(new LoginFormState(R.string.invalid_username, null));
        } else if (!isPasswordValid(password)) {
            loginFormState.setValue(new LoginFormState(null, R.string.invalid_password));
        } else {
            loginFormState.setValue(new LoginFormState(true));
        }
    }

    // A placeholder username validation check
    private boolean isUserNameValid(String username) {
        if (username == null) {
            return false;
        }
        if (username.contains("@")) {
            return Patterns.EMAIL_ADDRESS.matcher(username).matches();
        } else {
            return !username.trim().isEmpty();
        }
    }

    // A placeholder password validation check
    private boolean isPasswordValid(String password) {
        return password != null && password.trim().length() > 5;
    }

    public void logout() {
        loginRepository.logout();
        clearUserSession();
    }

    private void clearUserSession() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove("user_id");
        editor.remove("display_name");
        editor.apply();
    }
}