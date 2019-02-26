package com.wxx.androiddemo.dialog;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialog;
import android.support.v7.app.AlertDialog;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.wxx.androiddemo.R;
import com.wxx.androiddemo.base.BaseActivity;
import com.wxx.androiddemo.util.AppUtil;

/**
 * 作者：Tangren on 2019-02-26
 * 包名：com.wxx.androiddemo.dialog
 * 邮箱：996489865@qq.com
 * TODO:Dialog
 */
public class MainActivity extends BaseActivity implements View.OnClickListener {

    Button base_dialog, list_dialog, one_check_list_dialog, many_check_list_dialog, little_my_dialog, my_dialog,
            circle_progress_dialog, line_progress_dialog, bottom_sheet_dialog;
    String[] items = new String[]{"Android", "Java", "Python", "C++"};

    @Override
    protected int layout() {
        return R.layout.main_dialog;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        base_dialog = findViewById(R.id.base_dialog);
        list_dialog = findViewById(R.id.list_dialog);
        one_check_list_dialog = findViewById(R.id.one_check_list_dialog);
        many_check_list_dialog = findViewById(R.id.many_check_list_dialog);
        little_my_dialog = findViewById(R.id.little_my_dialog);
        my_dialog = findViewById(R.id.my_dialog);
        circle_progress_dialog = findViewById(R.id.circle_progress_dialog);
        line_progress_dialog = findViewById(R.id.line_progress_dialog);
        bottom_sheet_dialog = findViewById(R.id.bottom_sheet_dialog);

        base_dialog.setOnClickListener(this);
        list_dialog.setOnClickListener(this);
        one_check_list_dialog.setOnClickListener(this);
        many_check_list_dialog.setOnClickListener(this);
        my_dialog.setOnClickListener(this);
        little_my_dialog.setOnClickListener(this);
        circle_progress_dialog.setOnClickListener(this);
        line_progress_dialog.setOnClickListener(this);
        bottom_sheet_dialog.setOnClickListener(this);
    }

    @Override
    protected void initData() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.base_dialog:
                toBaseDialog();
                break;
            case R.id.list_dialog:
                toListDialog();
                break;
            case R.id.one_check_list_dialog:
                toOneCheckListDialog();
                break;
            case R.id.many_check_list_dialog:
                mayCheckListDialog();
                break;
            case R.id.little_my_dialog:
                littleMyDialog();
                break;
            case R.id.my_dialog:
                myDialog();
                break;
            case R.id.circle_progress_dialog:
                circleProgressDialog();
                break;
            case R.id.line_progress_dialog:
                lineProgressDialog();
                break;
            case R.id.bottom_sheet_dialog:
                bottomSheetDialog();
                break;
            default:
                break;
        }
    }

    private void bottomSheetDialog() {
        BottomSheetDialog dialog = new BottomSheetDialog(this);
        View view = getLayoutInflater().inflate(R.layout.main_dialog, null);
        dialog.setContentView(view);
        dialog.show();

        final FrameLayout frameLayout = dialog.findViewById(android.support.design.R.id.design_bottom_sheet);
        assert frameLayout != null;
        frameLayout.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                BottomSheetBehavior bottomSheetBehavior = BottomSheetBehavior.from(frameLayout);
                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
            }
        });


    }

    private void lineProgressDialog() {

    }

    /**
     * 圆形进度条
     */
    private void circleProgressDialog() {
    }

    /**
     * 半自定义
     */
    private void littleMyDialog() {
        View view = LayoutInflater.from(getApplicationContext()).inflate(R.layout.dialog_item_half, null);
        final EditText editText = view.findViewById(R.id.psw);
        AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle("我是半自定义对话框")
                .setIcon(R.mipmap.drawable_idea)
                .setView(view)
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                }).setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(MainActivity.this, editText.getText().toString(), Toast.LENGTH_SHORT).show();
                        dialog.cancel();
                    }
                }).create();
        dialog.show();
    }

    /**
     * 自定义对话框
     * params.width=0.75,params.gravity = Gravity.CENTER 就是居中显示的Dialog
     */
    private void myDialog() {
        DisplayMetrics displayMetrics = AppUtil.getDisplayMetrics(getApplicationContext());
        View view = View.inflate(getApplicationContext(), R.layout.dialog_item_my, null);
        TextView content = view.findViewById(R.id.content);
        TextView cancel = view.findViewById(R.id.cancel);
        TextView confirm = view.findViewById(R.id.confirm);
        final Dialog dialog = new Dialog(this, R.style.MyDialogStyle);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setContentView(view);
        view.setMinimumHeight((int) (0.23f * displayMetrics.heightPixels));
        Window window = dialog.getWindow();
        if (window == null) {
            return;
        }
        WindowManager.LayoutParams params = window.getAttributes();
        params.width = (int) (displayMetrics.widthPixels * 0.95f);
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        params.gravity = Gravity.BOTTOM;
        window.setAttributes(params);
        content.setText("您有新短消息\n请注意查收");
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });

        dialog.show();
    }

    /**
     * 多选列表对话框
     */
    private void mayCheckListDialog() {
        final boolean[] checked = new boolean[items.length];
        for (int i = 0; i < items.length; i++) {
            checked[i] = false;
        }
        AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle("我是多选列表对话框")
                .setIcon(R.mipmap.drawable_idea)
                .setMultiChoiceItems(items, null, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                        checked[which] = isChecked;

                    }
                }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                }).setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        StringBuilder msg = new StringBuilder("选择了:");
                        for (int i = 0; i < checked.length; i++) {
                            if (checked[i]) {
                                msg.append("【").append(items[i]).append("】");
                            }
                        }
                        Toast.makeText(MainActivity.this, msg.toString(), Toast.LENGTH_SHORT).show();
                        dialog.cancel();
                    }
                }).create();
        dialog.show();
    }

    /**
     * 单选列表
     * CharSequence[] items 数据源
     * int checkedItem 默认选择
     * OnClickListener listener 点击事件
     */
    private void toOneCheckListDialog() {
        AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle("我是单选列表对话框")
                .setIcon(R.mipmap.drawable_idea)
                .setSingleChoiceItems(items, -1, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(MainActivity.this, "选择了" + items[which] + "选项", Toast.LENGTH_SHORT).show();
                    }
                }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                }).setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                }).create();
        dialog.show();
    }

    /**
     * 列表对话框
     * setMessage()与setItems冲突
     */
    private void toListDialog() {
        AlertDialog dialog = new AlertDialog.Builder(this)
                .setIcon(R.mipmap.drawable_idea)
                .setTitle("我是列表对话框")
                .setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(MainActivity.this, "选择了" + items[which] + "选项", Toast.LENGTH_SHORT).show();
                        dialog.cancel();
                    }
                }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                }).create();
        dialog.show();
    }

    /**
     * 普通Dialog
     * 上下文一定要传Activity的
     */
    private void toBaseDialog() {
        AlertDialog dialog = new AlertDialog.Builder(this)
                .setIcon(R.mipmap.drawable_idea)
                .setTitle("我是普通对话框")
                .setMessage("我是普通对话框的消息")
                .setCancelable(true)
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(MainActivity.this, "点击了取消按钮", Toast.LENGTH_SHORT).show();
                        dialog.cancel();
                    }
                }).setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(MainActivity.this, "点击了确定按钮", Toast.LENGTH_SHORT).show();
                        dialog.cancel();
                    }
                }).create();
        dialog.show();
    }
}
