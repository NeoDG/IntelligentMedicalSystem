package com.sourcey.intelligentmedicalsystem;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.sourcey.intelligentmedicalsystem.bean.User;
import com.sourcey.intelligentmedicalsystem.db.UserDBDao;
import com.sourcey.intelligentmedicalsystem.httpUtils.HttpCallbackListener;
import com.sourcey.intelligentmedicalsystem.httpUtils.PostThread;

import org.json.JSONException;

import butterknife.ButterKnife;
import butterknife.BindView;

/**
 * 注册activity
 */

public class SignupActivity extends AppCompatActivity {
    private static final String TAG = "SignupActivity";
    private UserDBDao userDBDao;

    //@Bind(R.id.input_username) EditText _usernameText;
   // @Bind(R.id.input_email) EditText _emailText;
    @BindView(R.id.input_phone) EditText _phoneText;
    @BindView(R.id.input_password) EditText _passwordText;
    @BindView(R.id.input_reEnterPassword) EditText _reEnterPasswordText;
    @BindView(R.id.btn_signup) Button _signupButton;
    @BindView(R.id.link_login) TextView _loginLink;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        ButterKnife.bind(this);

        _signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signup();
            }
        });

        _loginLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Finish the registration screen and return to the Login activity
                Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
                startActivity(intent);
                finish();
                overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
            }
        });
    }

    public void signup() {
        Log.d(TAG, "Signup");

        if (!validate()) {
            onSignupFailed("信息错误！");
            return;
        }

        //_signupButton.setEnabled(false);

        //String username = _usernameText.getText().toString();
        //String email = _emailText.getText().toString();
        String username = _phoneText.getText().toString();
        String password = _passwordText.getText().toString();
        String reEnterPassword = _reEnterPasswordText.getText().toString();

        Log.e("a","a");
        PostThread postThread=new PostThread(username, password, new HttpCallbackListener() {

            @Override
            public void onFinish(String response) throws JSONException{
                Looper.prepare();
               if(response.equals("success")){
                    Log.e("result","success");
                  // Toast.makeText(getBaseContext(), "注册成功！", Toast.LENGTH_LONG).show();

                  // Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
                  // startActivity(intent);
                  // finish();
                   //overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
                   onSignupSuccess();
                }else if(response.equals("401")){
                    Log.e("result","failed");
                  // Toast.makeText(getBaseContext(), "用户已存在！", Toast.LENGTH_LONG).show();
                   onSignupFailed("用户已存在！");
                   //onSignupFailed("用户已存在");
                }
                Looper.loop();
            }

            @Override
            public void onError(Exception e) {
                e.printStackTrace();
                Log.e("!!!!!","!!!!!");
                onSignupFailed("注册失败！");

            }
        },2);
        postThread.start();

       /* final ProgressDialog progressDialog = new ProgressDialog(SignupActivity.this,
                R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Creating Account...");
        progressDialog.show();



        // TODO: Implement your own signup logic here.



        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        // On complete call either onSignupSuccess or onSignupFailed
                        // depending on success

                        String username = _usernameText.getText().toString();
                        String email = _emailText.getText().toString();
                        String phone = _phoneText.getText().toString();
                        String password = _passwordText.getText().toString();
                        String reEnterPassword = _reEnterPasswordText.getText().toString();
                        userDBDao=new UserDBDao(getApplicationContext());
                        User user=new User(username,password,email,phone);
                        if(userDBDao.findByitem(username)==null){
                            if(userDBDao.add(user)){
                                Log.d(TAG,"注册成功");
                                onSignupSuccess();
                            }else{
                                onSignupFailed("数据库连接失败！");
                            }
                        }else{
                            onSignupFailed("该用户已存在！");
                        }
                        progressDialog.dismiss();
                    }
                }, 3000);*/



    }


    public void onSignupSuccess() {
        Toast.makeText(getBaseContext(), "注册成功", Toast.LENGTH_LONG).show();
        //_signupButton.setEnabled(true);
        setResult(RESULT_OK, null);
        Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
        startActivity(intent);
        finish();
        overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
    }

    public void onSignupFailed(String message) {
        Toast.makeText(getBaseContext(), message, Toast.LENGTH_LONG).show();

        //_signupButton.setEnabled(true);
    }


    public boolean validate() {
        boolean valid = true;

       // String username = _usernameText.getText().toString();
       // String email = _emailText.getText().toString();
        String mobile = _phoneText.getText().toString();
        String password = _passwordText.getText().toString();
        String reEnterPassword = _reEnterPasswordText.getText().toString();
//
        //if (username.isEmpty() || username.length() < 3) {
          //  _usernameText.setError("at least 3 characters");
            //valid = false;
       // } else if(userDBDao.findByitem(username)!=null){
         //   _usernameText.setError("该用户名已存在");
        //}else{
          //  _usernameText.setError(null);
        //}




       // if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
         //   _emailText.setError("enter a valid email address");
           // valid = false;
        //} else {
          //  _emailText.setError(null);
       // }

        if (mobile.isEmpty() || mobile.length()!=11) {
            _phoneText.setError("Enter Valid Mobile Number");
            valid = false;
        } else {
            _phoneText.setError(null);
        }

        if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
            _passwordText.setError("between 4 and 10 alphanumeric characters");
            valid = false;
        } else {
            _passwordText.setError(null);
        }

        if (reEnterPassword.isEmpty() || reEnterPassword.length() < 4 || reEnterPassword.length() > 10 || !(reEnterPassword.equals(password))) {
            _reEnterPasswordText.setError("Password Do not match");
            valid = false;
        } else {
            _reEnterPasswordText.setError(null);
        }

        return valid;
    }
}