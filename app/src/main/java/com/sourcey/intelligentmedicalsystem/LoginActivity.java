package com.sourcey.intelligentmedicalsystem;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.sourcey.intelligentmedicalsystem.bean.User;
import com.sourcey.intelligentmedicalsystem.db.UserDBDao;
import com.sourcey.intelligentmedicalsystem.httpUtils.GetThread;
import com.sourcey.intelligentmedicalsystem.httpUtils.HttpCallbackListener;
import com.sourcey.intelligentmedicalsystem.utils.MyApplication;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.BindView;

import static com.sourcey.intelligentmedicalsystem.R.drawable.user;

/**
 * 登陆实体类
 */

public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "LoginActivity";
    private static final int REQUEST_SIGNUP = 0;
    private UserDBDao userDBDao;

    @BindView(R.id.input_username) EditText _usernameText;
    @BindView(R.id.input_password) EditText _passwordText;
    @BindView(R.id.btn_login) Button _loginButton;
    @BindView(R.id.link_signup) TextView _signupLink;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        SharedPreferences sp = getSharedPreferences("loginToken", 0);
        String token = sp.getString("token", null);
        String username = sp.getString("username", null);
        Log.e("sp",username+"2333"+token);
        if(token!=null&&username!=null) {
           onLoginSuccess();
        }
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        
        _loginButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                login();
            }
        });

        _signupLink.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // Start the Signup activity
                Intent intent = new Intent(getApplicationContext(), SignupActivity.class);
                startActivityForResult(intent, REQUEST_SIGNUP);
                finish();
                overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
            }
        });
    }

    public void login() {
        Log.d(TAG, "Login");

        if (!validate()) {
            onLoginFailed();
            return;
        }

        final String username = _usernameText.getText().toString();
        String password = _passwordText.getText().toString();

        Log.e("up",username+password);
        GetThread getThread=new GetThread(username, password, new HttpCallbackListener() {
            @Override
            public void onFinish(String response) throws JSONException {
                Looper.prepare();
                Log.e("finish","11");
                JSONObject json=new JSONObject(response);
                if(json.getString("status").equals("success")){
                    SharedPreferences sp = getSharedPreferences("loginToken", 0);
                    SharedPreferences.Editor editor = sp.edit();
                    editor.putString("token",json.getString("token"));
                    Log.e("token",json.getString("token"));
                    editor.putString("username",username);
                    editor.commit();
                    onLoginSuccess();
                }else if(json.getString("status").equals("failed")){
                    onLoginFailed();
                }
                Looper.loop();
            }

            @Override
            public void onError(Exception e) {
//                Looper.prepare();
                Log.e("erro","22");
                onLoginFailed();
//                Looper.loop();
            }
        });
        getThread.start();
        /*_loginButton.setEnabled(false);

        final ProgressDialog progressDialog = new ProgressDialog(LoginActivity.this,
                R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Authenticating...");
        progressDialog.show();



        // TODO: Implement your own authentication logic here.


       new android.os.Handler().postDelayed(
               new Runnable() {
                   public void run() {
                       // On complete call either onLoginSuccess or onLoginFailed
                       String username = _usernameText.getText().toString();
                       String password = _passwordText.getText().toString();
                       userDBDao=new UserDBDao(getApplicationContext());
                       Boolean flag=userDBDao.login(username,password);
                       if(flag){
                           Log.d(TAG,"登陆成功");
                           List<User> users=userDBDao.findByitem(username);
                           int id;
                           if(users!=null){
                                id=users.get(0).getId();
                               onLoginSuccess(id);
                           }

                       }else{
                           Log.d(TAG,"登陆失败");
                           onLoginFailed();
                       }

                       // onLoginFailed();
                       progressDialog.dismiss();
                   }
               }, 3000);*/
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_SIGNUP) {
            if (resultCode == RESULT_OK) {

                // TODO: Implement successful signup logic here
                // By default we just finish the Activity and log them in automatically
                this.finish();
            }
        }
    }

    @Override
    public void onBackPressed() {
        // Disable going back to the MainActivity
//        moveTaskToBack(true);
        super.onBackPressed();
    }

    public void onLoginSuccess() {
        MyApplication.setLoginFlag(true);
      //  MyApplication.setUserId(id);
       // _loginButton.setEnabled(true);
        Toast.makeText(getBaseContext(), "登录成功", Toast.LENGTH_SHORT).show();
        Intent intent=new Intent(LoginActivity.this,RecordActivity.class);
        startActivity(intent);

        finish();
    }

    public void onLoginFailed() {
        Toast.makeText(this, "登录失败", Toast.LENGTH_SHORT).show();

       // _loginButton.setEnabled(true);
    }

    public boolean validate() {
        boolean valid = true;

        String username = _usernameText.getText().toString();
        String password = _passwordText.getText().toString();

        if (username.isEmpty() ) {//|| !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
            _usernameText.setError("enter a valid email address");
            valid = false;
        } else {
            _usernameText.setError(null);
        }

        if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
            _passwordText.setError("between 4 and 10 alphanumeric characters");
            valid = false;
        } else {
            _passwordText.setError(null);
        }


        return valid;
    }
}
