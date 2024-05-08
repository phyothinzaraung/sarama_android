package com.koekoetech.sayarma.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.NonNull;
import com.koekoetech.sayarma.R;
import com.koekoetech.sayarma.custom_control.MyanTextView;
import com.koekoetech.sayarma.helper.ServiceHelper;
import com.koekoetech.sayarma.helper.SharedPreferenceHelper;
import com.koekoetech.sayarma.model.LoginViewModel;
import com.koekoetech.sayarma.model.MemberModel;
import com.koekoetech.sayarma.model.ProgressModel;
import com.koekoetech.sayarma.model.ScoreModel;
import java.util.Objects;
import io.realm.Realm;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends BaseActivity {

    private EditText edt_username;
    private EditText edt_password;
    private MyanTextView tv_version;
    private Button btnLogIn;

    private SharedPreferenceHelper sharedPreferenceHelper;

    private ProgressDialog progressDialog;

    private Realm realm;

    private void bindViews(){
        edt_username = findViewById(R.id.edt_username);
        edt_password = findViewById(R.id.edt_password);
        tv_version = findViewById(R.id.tv_version);
        btnLogIn = findViewById(R.id.btnLogIn);
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_login;
    }

    @Override
    protected void setUpContents(Bundle savedInstanceState) {
        setUpToolbar(false);
        setUpToolbarText("");
        Objects.requireNonNull(getSupportActionBar()).hide();

        bindViews();

        sharedPreferenceHelper = SharedPreferenceHelper.getHelper(this);
        try {
            tv_version.setMyanmarText("Version " + getVersionName());
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        realm = Realm.getDefaultInstance();

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");

        realm.executeTransactionAsync(txRealm ->{
                    txRealm.delete(ScoreModel.class);
                    txRealm.delete(ProgressModel.class);
                });

        btnLogIn.setOnClickListener(v -> {

            progressDialog.show();

            if(validateForm()){
                MemberModel model = getMemberData();

                Call<LoginViewModel> login = ServiceHelper.getClient(LoginActivity.this).login(model);
                login.enqueue(new Callback<LoginViewModel>() {
                    @Override
                    public void onResponse(@NonNull Call<LoginViewModel> call, @NonNull Response<LoginViewModel> response) {
                        if(response.isSuccessful()){

                            if(progressDialog.isShowing()){
                                progressDialog.dismiss();
                            }
                            sharedPreferenceHelper.setLogIn(true);
                            assert response.body() != null;
                            MemberModel memberModel = response.body().getMemberModel();
                            sharedPreferenceHelper.setUserName(memberModel.getUsername());
                            sharedPreferenceHelper.setUserId(memberModel.getUserID());

                            //Add LoginViewModel to Realm
                            realm.executeTransactionAsync(realm -> {

                                realm.insertOrUpdate(response.body().getProgressModel());
                                realm.insertOrUpdate(response.body().getScoreModel());
                                realm.insertOrUpdate(response.body().getMemberModel());
                            });

                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                        }else {
                            if(progressDialog.isShowing()){
                                progressDialog.dismiss();
                            }
                            Toast.makeText(getApplicationContext(), "Username and Password do not match.", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<LoginViewModel> call, @NonNull Throwable t) {
                        if(progressDialog.isShowing()){
                            progressDialog.dismiss();
                        }
                        Toast.makeText(getApplicationContext(), t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }

        });

    }

    private boolean validateForm() {

        if(!edt_username.getText().toString().isEmpty() || !edt_password.getText().toString().isEmpty()){
            return true;
        }else {
            Toast.makeText(getApplicationContext(), "Please enter required fileds.", Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    private MemberModel getMemberData(){
        MemberModel model = new MemberModel();
        model.setUsername(edt_username.getText().toString());
        model.setPassword(edt_password.getText().toString());
        return  model;
    }

    private String getVersionName() throws PackageManager.NameNotFoundException {
        PackageManager manager = getPackageManager();
        PackageInfo info = manager.getPackageInfo(getPackageName(), 0);
        return info.versionName;
    }
}
