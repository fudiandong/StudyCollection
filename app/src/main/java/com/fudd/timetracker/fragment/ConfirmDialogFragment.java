package com.fudd.timetracker.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;

import com.fudd.timetracker.adapter.TimeListAdapter;

/**
 * Created by fudd-office on 2017-3-16 17:09.
 * Email: 5036175@qq.com
 * QQ: 5036175
 * Description:
 */

public class ConfirmDialogFragment extends DialogFragment {
    private TimeListAdapter timeListAdapter;

    public static  ConfirmDialogFragment instance(TimeListAdapter aAdapter){
        ConfirmDialogFragment confirmDialogFragment = new ConfirmDialogFragment();
        confirmDialogFragment.timeListAdapter = aAdapter;
        return  confirmDialogFragment;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return new AlertDialog.Builder(getActivity()).setTitle("警告：")
                .setMessage("是否确定清除列表？")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        timeListAdapter.clear();
                    }
                })
                .setNegativeButton("取消",null)
                .create();
    }
}
