package com.koekoetech.sayarma.helper;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Window;
import android.widget.ImageView;

import com.koekoetech.sayarma.R;
import com.koekoetech.sayarma.custom_control.MyanButton;
import com.koekoetech.sayarma.custom_control.MyanTextView;

public class DialogHelperWithOkCancelButton {
    private Dialog myDialog;
    private ImageView img_icon;
    private MyanButton btn_ok;
    private MyanButton btn_cancel;
    private MyanTextView tv_message;

    public DialogHelperWithOkCancelButton(Context context) {
        myDialog = new Dialog(context);
        myDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        myDialog.setContentView(R.layout.custom_dialog_with_ok_cancel_button);
        myDialog.getWindow().
                setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        myDialog.setCancelable(false);

        img_icon = myDialog.findViewById(R.id.img_icon);
        tv_message = myDialog.findViewById(R.id.tv_message);
        btn_ok = myDialog.findViewById(R.id.btn_ok);
        btn_cancel=myDialog.findViewById(R.id.btn_cancel);


    }

    public void showDialog(int icon, String message, String buttonOkText, String buttonCancelText) {
        btn_ok.setMyanmarText(buttonOkText);
        btn_cancel.setMyanmarText(buttonCancelText);
        img_icon.setImageResource(icon);
        tv_message.setMyanmarText(message);
        myDialog.show();
    }

    public void hideDialog() {
        myDialog.dismiss();
    }

    public MyanButton getBtnOk() {
        return btn_ok;
    }

    public MyanButton getBtnCancel() {
        return btn_cancel;
    }


}