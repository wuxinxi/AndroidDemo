package com.wxx.androiddemo.databinding;

import android.annotation.SuppressLint;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.wxx.androiddemo.BR;
import com.wxx.androiddemo.R;
import com.wxx.androiddemo.bean.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * 作者：Tangren on 2019-02-27
 * 包名：com.wxx.androiddemo.databinding
 * 邮箱：996489865@qq.com
 * TODO:一句话描述
 */
public class MyAdapter extends RecyclerView.Adapter<BindingViewHolder> {
    public static final int ITEM_VIEW_RIGHT = 1;
    public static final int ITEM_VIEW_LEFT = 2;

    private List<User> mUsers;
    private LayoutInflater mInflater;
    private Context mContext;
    private OnItemClickListener mListener;

    public interface OnItemClickListener {
        void onUserClick(int position, User user);
    }

    public MyAdapter(Context mContext) {
        this.mContext = mContext;
        mUsers = new ArrayList<>();
        mInflater = LayoutInflater.from(mContext);
    }

    @NonNull
    @Override
    public BindingViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int itemType) {
        ViewDataBinding binding;
        if (itemType == ITEM_VIEW_LEFT) {
            binding = DataBindingUtil.inflate(mInflater, R.layout.item_binding_left, viewGroup, false);
        } else {
            binding = DataBindingUtil.inflate(mInflater, R.layout.item_binding_right, viewGroup, false);
        }
        return new BindingViewHolder(binding);
    }

    @Override
    public int getItemViewType(int position) {
        User user = mUsers.get(position);
        if (user.isVisable().get()) {
            return ITEM_VIEW_LEFT;
        } else {
            return ITEM_VIEW_RIGHT;
        }
    }

    static final int[] colors = new int[]{R.color.colorBlack, android.R.color.holo_orange_light, R.color.colorGray,
            R.color.colorPrimary, R.color.design_default_color_primary};

    @SuppressLint("ResourceType")
    @Override
    public void onBindViewHolder(@NonNull final BindingViewHolder bindingViewHolder, final int position) {
        final User user = mUsers.get(position);
        bindingViewHolder.getmBinding().setVariable(BR.user, user);
        bindingViewHolder.getmBinding().executePendingBindings();
        ImageView img = bindingViewHolder.itemView.findViewById(R.id.bgColor);
        img.setBackgroundColor(mContext.getResources().getColor(colors[new Random().nextInt(5)]));
        img.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Toast.makeText(mContext, "长按事件" + bindingViewHolder.getAdapterPosition(), Toast.LENGTH_SHORT).show();
                return false;
            }
        });
        bindingViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null) {
                    mListener.onUserClick(bindingViewHolder.getAdapterPosition(), user);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mUsers.size();
    }

    private Random random = new Random(System.currentTimeMillis());

    public void addAll(List<User> userList) {
        mUsers.addAll(userList);
    }

    public void add(User user) {
        int position = random.nextInt(mUsers.size() + 1);
        mUsers.add(position, user);
        notifyItemInserted(position);
    }

    public void remove() {
        if (mUsers.size() == 0) {
            return;
        }
        int position = random.nextInt(mUsers.size());
        mUsers.remove(position);
        notifyItemRemoved(position);
    }

    public void setmListener(OnItemClickListener mListener) {
        this.mListener = mListener;
    }

    private String randomColor() {
        String R, G, B;
        Random random = new Random();
        R = Integer.toHexString(random.nextInt(256)).toUpperCase();
        G = Integer.toHexString(random.nextInt(256)).toUpperCase();
        B = Integer.toHexString(random.nextInt(256)).toUpperCase();

        R = R.length() == 1 ? "0" + R : R;
        G = R.length() == 1 ? "0" + G : G;
        B = R.length() == 1 ? "0" + B : B;
        return R + G + B;
    }
}
