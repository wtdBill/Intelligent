package com.muxin.asus.arg.login;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.muxin.asus.arg.R;
import com.muxin.asus.arg.base.BaseActivity;
import com.muxin.asus.arg.bean.LoginRequest;
import com.muxin.asus.arg.main.MainActivity;

import butterknife.InjectView;
import butterknife.OnClick;


/**
 * @Author： Lianwei Bu
 * @Project_Name： IntelligentPastoral
 * @Date： 2016年5月25日
 * @Description:
 */
public class LoginActivity extends BaseActivity implements LoginContract.View {
    @InjectView(R.id.login_et_user_name)
    EditText mUserNameET;
    @InjectView(R.id.login_et_password)
    EditText mPasswordET;
    @InjectView(R.id.login_tv_register)
    TextView mRegisterTV;
    @InjectView(R.id.login_tv_forget_password)
    TextView mForgetTV;
    @InjectView(R.id.login_btn_login)
    Button mLoginBtn;

    private LoginContract.Presenter mPresenter;

    @Override
    protected void getBundle(@NonNull Bundle bundle) {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.login_activity;
    }

    @Override
    protected String getTitleBarTitle() {
        return getString(R.string.login);
    }

    @Override
    protected void initView() {
        mPresenter=new LoginPreseter(this);
        mLayout.setVisibility(View.GONE);

        mUserNameET.setText(getPreference("username"));
        mPasswordET.setText(getPreference("password"));
        mUserNameET.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
                mPasswordET.setText("");
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }


    @Override
    public void loginSuccess() {
        toActivityFinish(MainActivity.class);

    }

    @OnClick({R.id.login_btn_login, R.id.layout_right})
    public void onClick(View v) {
        Bundle bundle = new Bundle();
        bundle.putString("username", mUserNameET.getText().toString().trim());
        switch (v.getId()) {
            case R.id.login_btn_login:
                mPresenter.login();
                break;
//            case R.id.login_tv_forget_password:
//                toActivityWithBundle(ForgetActivity.class, bundle);
//                break;
//            case R.id.login_tv_register:
//                toActivityWithBundle(RegisterActivity.class, bundle);
//                break;

        }
    }

    @Override
    public LoginRequest getLoginBean() {
        return new LoginRequest(mUserNameET.getText().toString().trim(), mPasswordET.getText().toString().toString());
    }

    @Override
    protected void onPause() {
        super.onPause();
        mPresenter.unsubscribe();
    }

}
