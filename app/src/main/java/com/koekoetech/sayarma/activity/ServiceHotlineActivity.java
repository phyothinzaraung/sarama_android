package com.koekoetech.sayarma.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import androidx.cardview.widget.CardView;
import android.widget.TextView;
import android.widget.Toast;
import com.koekoetech.sayarma.R;

public class ServiceHotlineActivity extends BaseActivity {

    private CardView cardViewUNDPPhone;
    private CardView cardViewAkhayaPhone1;
    private TextView txtAkhayaPhone1;
    private CardView cardViewAkhayaPhone2;
    private TextView txtAkhayaPhone2;
    private CardView cardViewLegalAidPhone;
    private TextView txtLegalAidPhone;
    private CardView cardViewDswHelpLine1;
    private TextView txtDswHelpLine1;
    private CardView cardViewDswHelpLine2;
    private TextView txtDswHelpLine2;

    private void bindViews(){
        cardViewUNDPPhone = findViewById(R.id.cardViewUNDPPhone);
        cardViewAkhayaPhone1 = findViewById(R.id.cardViewAkhayaPhone1);
        txtAkhayaPhone1 = findViewById(R.id.txtAkhayaPhone1);
        cardViewAkhayaPhone2 = findViewById(R.id.cardViewAkhayaPhone2);
        txtAkhayaPhone2 = findViewById(R.id.txtAkhayaPhone2);
        cardViewLegalAidPhone = findViewById(R.id.cardViewLegalAidPhone);
        txtLegalAidPhone = findViewById(R.id.txtLegalAidPhone);
        cardViewDswHelpLine1 = findViewById(R.id.cardViewDswHelpLine1);
        txtDswHelpLine1 = findViewById(R.id.txtDswHelpLine1);
        cardViewDswHelpLine2 = findViewById(R.id.cardViewDswHelpLine2);
        txtDswHelpLine2 = findViewById(R.id.txtDswHelpLine2);
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_service_hotline;
    }

    @Override
    protected void setUpContents(Bundle savedInstanceState) {
        setUpToolbar(true);
        setUpToolbarText("Service Hotline");

        bindViews();

        //Call UNDP Hotline
        cardViewUNDPPhone.setOnClickListener(v -> Toast.makeText(getApplicationContext(), "Phone Number is not available yet.", Toast.LENGTH_SHORT).show());

        //Call Akhaya Hotline 1
        cardViewAkhayaPhone1.setOnClickListener(v -> {
            String phone = txtAkhayaPhone1.getText().toString();
            Intent intent = new Intent(Intent.ACTION_DIAL);
            intent.setData(Uri.parse("tel:" + phone));
            startActivity(intent);
        });

        //Call Akhaya Hoteline 2
        cardViewAkhayaPhone2.setOnClickListener(v -> {
            String phone = txtAkhayaPhone2.getText().toString();
            Intent intent = new Intent(Intent.ACTION_DIAL);
            intent.setData(Uri.parse("tel:" + phone));
            startActivity(intent);
        });

        //Call Legal Aid
        cardViewLegalAidPhone.setOnClickListener(v -> {
            String phone = txtLegalAidPhone.getText().toString();
            Intent intent = new Intent(Intent.ACTION_DIAL);
            intent.setData(Uri.parse("tel:" + phone));
            startActivity(intent);
        });

        //Call Dsw Help Line
        cardViewDswHelpLine1.setOnClickListener(v -> {
            String phone = txtDswHelpLine1.getText().toString();
            Intent intent = new Intent(Intent.ACTION_DIAL);
            intent.setData(Uri.parse("tel:" + phone));
            startActivity(intent);
        });

        cardViewDswHelpLine2.setOnClickListener(v -> {
            String phone = txtDswHelpLine2.getText().toString();
            Intent intent = new Intent(Intent.ACTION_DIAL);
            intent.setData(Uri.parse("tel:" + phone));
            startActivity(intent);
        });
    }
}
