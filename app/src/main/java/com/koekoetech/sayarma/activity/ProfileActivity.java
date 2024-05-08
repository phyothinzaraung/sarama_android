package com.koekoetech.sayarma.activity;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;
import com.koekoetech.sayarma.R;
import com.koekoetech.sayarma.helper.DateTimeHelper;
import com.koekoetech.sayarma.helper.ServiceHelper;
import com.koekoetech.sayarma.helper.SharedPreferenceHelper;
import com.koekoetech.sayarma.model.MemberModel;
import java.util.Calendar;
import java.util.Date;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileActivity extends BaseActivity {

    private AppCompatEditText txtMemberName;
    private AppCompatEditText txtName;
    private AppCompatEditText txtDOB;
    private AppCompatEditText txtPhone;
    private AppCompatButton btnSave;

    private MemberModel model;
    private ProgressDialog progressDialog;

    private void bindViews(){
        txtMemberName = findViewById(R.id.txtMemberName);
        txtName = findViewById(R.id.txtName);
        txtDOB = findViewById(R.id.txtDOB);
        txtPhone = findViewById(R.id.txtPhone);
        btnSave = findViewById(R.id.btnSave);
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_profile;
    }

    @Override
    protected void setUpContents(Bundle savedInstanceState) {
        setUpToolbar(true);
        setUpToolbarText("Profile");

        bindViews();

        SharedPreferenceHelper sharedPreferenceHelper = SharedPreferenceHelper.getHelper(this);
        String username = sharedPreferenceHelper.getUserName();

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");

        model = getUserDataByUserName(username);

        txtDOB.setInputType(InputType.TYPE_NULL);
        txtDOB.setFocusableInTouchMode(false);
        txtDOB.setOnClickListener(v -> {
            Date initDate = new Date();
            String enteredDob = txtDOB.getText().toString().trim();
            if (!TextUtils.isEmpty(enteredDob) && DateTimeHelper.validateDateFormat(enteredDob, DateTimeHelper.LOCAL_DATE_FORMAT)) {
                Date parsedDate = DateTimeHelper.getDateFromString(enteredDob, DateTimeHelper.LOCAL_DATE_FORMAT);
                if (parsedDate != null) {
                    initDate = parsedDate;
                }
            }
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(initDate);
            DatePickerDialog datePickerDialog = new DatePickerDialog(ProfileActivity.this, DatePickerDialog.THEME_HOLO_LIGHT,
                    (view, year, monthOfYear, dayOfMonth) -> {
                        String strDate = dayOfMonth + "/" + (monthOfYear + 1) + "/" + year;
                        txtDOB.setText(strDate);
                    }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
            datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
            datePickerDialog.show();
        });

        btnSave.setOnClickListener(v -> {
            progressDialog.show();
            MemberModel memberModel = getMemberData();
            saveMember(memberModel);
        });

    }

    private MemberModel getUserDataByUserName(String username){
        progressDialog.show();
        Call<MemberModel> getUserData = ServiceHelper.getClient(this).getUserDataByUsername(username);
        getUserData.enqueue(new Callback<MemberModel>() {
            @Override
            public void onResponse(Call<MemberModel> call, Response<MemberModel> response) {
                if(progressDialog.isShowing()){
                    progressDialog.dismiss();
                }
                if(response.isSuccessful()){
                    model = response.body();
                    if(model != null){
                        txtName.setText(model.getNameinEnglish());
                        txtMemberName.setText(model.getUsername());
                        if(!TextUtils.isEmpty(model.getDOB())){
                            txtDOB.setText(model.getDOB());
                        }
                        if(model.getPhoneNumber() != null){
                            txtPhone.setText(model.getPhoneNumber());
                        }
//                        if(model.getPhoneNumber() != null || !model.getPhoneNumber().isEmpty()){
//                            txtPhone.setText(model.getPhoneNumber());
//                        }else {
//                            txtPhone.setText("");
//                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<MemberModel> call, Throwable t) {
                if(progressDialog.isShowing()){
                    progressDialog.dismiss();
                }
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        return model;
    }

    private MemberModel getMemberData (){
        MemberModel memberModel = new MemberModel();
        Log.v("UserID", model.getUserID());
        memberModel.setUserID(model.getUserID());
        memberModel.setUsername(txtMemberName.getText().toString());
        if(!TextUtils.isEmpty(txtName.getText().toString())) {
            memberModel.setNameinEnglish(txtName.getText().toString());
        }
        memberModel.setOrganizationalID(model.getOrganizationalID());
        memberModel.setOrganizationDescription(model.getOrganizationDescription());
        if(!TextUtils.isEmpty(txtDOB.getText().toString())){
            memberModel.setDOB(txtDOB.getText().toString());
        }
        if(!TextUtils.isEmpty(txtPhone.getText().toString())){
            memberModel.setPhoneNumber(txtPhone.getText().toString());
        }

        return memberModel;
    }

    private void saveMember(MemberModel memberModel){
        Call<MemberModel> saveMember = ServiceHelper.getClient(ProfileActivity.this).updateUserData(memberModel);
        saveMember.enqueue(new Callback<MemberModel>() {
            @Override
            public void onResponse(Call<MemberModel> call, Response<MemberModel> response) {
                if(response.isSuccessful()){
                    if(progressDialog.isShowing()){
                        progressDialog.dismiss();
                    }
                    Toast.makeText(getApplicationContext(), "User profile has successfully updated.", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(ProfileActivity.this, MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                }else {
                    if(progressDialog.isShowing()){
                        progressDialog.dismiss();
                    }
                    Toast.makeText(getApplicationContext(), "failed to update user profile.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<MemberModel> call, Throwable t) {
                if(progressDialog.isShowing()){
                    progressDialog.dismiss();
                }
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
