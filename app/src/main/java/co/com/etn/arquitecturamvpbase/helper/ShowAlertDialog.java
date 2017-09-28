package co.com.etn.arquitecturamvpbase.helper;

import android.app.Activity;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;

/**
 * Created by Erika on 26/09/2017.
 */

public class ShowAlertDialog {

    private Activity activity;

    public ShowAlertDialog(Activity activity) {
        this.activity = activity;
    }


    public void showAlertDialog(int title, int message, boolean cancelable, int textPositiveButton, DialogInterface.OnClickListener onClickListenerPositiveButton) {
        showAlertDialog(activity.getResources().getString(title), activity.getResources().getString(message), cancelable, textPositiveButton, onClickListenerPositiveButton, 0, null);
    }

    public void showAlertDialog(int title, String message, boolean cancelable, int textPositiveButton, DialogInterface.OnClickListener onClickListenerPositiveButton) {
        showAlertDialog(activity.getResources().getString(title), message, cancelable, textPositiveButton, onClickListenerPositiveButton, 0, null);
    }

    public void showAlertDialog(String title, String message, boolean cancelable, int textPositiveButton, DialogInterface.OnClickListener onClickListenerPositiveButton) {
        showAlertDialog(title, message, cancelable, textPositiveButton, onClickListenerPositiveButton, 0, null);
    }

    public void showAlertDialog(String title, int message, boolean cancelable, int textPositiveButton, DialogInterface.OnClickListener onClickListenerPositiveButton) {
        showAlertDialog(title, activity.getResources().getString(message), cancelable, textPositiveButton, onClickListenerPositiveButton, 0, null);
    }

    public void showAlertDialog(int title, int message, boolean cancelable, int textPositiveButton, DialogInterface.OnClickListener onClickListenerPositiveButton, int textNegativeButton, DialogInterface.OnClickListener onClickListenerNegativeButton) {
        showAlertDialog(activity.getResources().getString(title), activity.getResources().getString(message), cancelable, textPositiveButton, onClickListenerPositiveButton, textNegativeButton, onClickListenerNegativeButton);
    }

    public void showAlertDialog(int title, String message, boolean cancelable, int textPositiveButton, DialogInterface.OnClickListener onClickListenerPositiveButton, int textNegativeButton, DialogInterface.OnClickListener onClickListenerNegativeButton) {
        showAlertDialog(activity.getResources().getString(title), message, cancelable, textPositiveButton, onClickListenerPositiveButton, textNegativeButton, onClickListenerNegativeButton);
    }

    public void showAlertDialog(String title, int message, boolean cancelable, int textPositiveButton, DialogInterface.OnClickListener onClickListenerPositiveButton, int textNegativeButton, DialogInterface.OnClickListener onClickListenerNegativeButton) {
        showAlertDialog(title, activity.getResources().getString(message), cancelable, textPositiveButton, onClickListenerPositiveButton, textNegativeButton, onClickListenerNegativeButton);
    }

    public void showAlertDialog(String title, String message, boolean cancelable, int textPositiveButton, DialogInterface.OnClickListener onClickListenerPositiveButton, int textNegativeButton, DialogInterface.OnClickListener onClickListenerNegativeButton) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setCancelable(cancelable);

        if (onClickListenerPositiveButton != null) {
            builder.setPositiveButton(textPositiveButton, onClickListenerPositiveButton);
        }
        if (onClickListenerNegativeButton != null) {
            builder.setNegativeButton(textNegativeButton, onClickListenerNegativeButton);
        }
        builder.show();
    }

}
