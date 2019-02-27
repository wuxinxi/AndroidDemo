package com.wxx.androiddemo.bean;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableArrayMap;
import android.databinding.ObservableBoolean;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import com.wxx.androiddemo.BR;

/**
 * 作者：Tangren on 2019-02-22
 * 包名：com.wxx.androiddemo.bean
 * 邮箱：996489865@qq.com
 * TODO:一句话描述
 */
public class User extends BaseObservable implements Parcelable {
    private int id;
    private int age;
    private String name = "default";
    private String content = "xxx";
    private String headImgUrl;
    private ObservableBoolean isVisable;
    public ObservableArrayMap<String, String> map = new ObservableArrayMap<>();
    public ObservableArrayList<String> list = new ObservableArrayList<>();

    public ObservableBoolean isVisable() {
        return isVisable;
    }

    public void setIsVisable(boolean isVisable) {
        this.isVisable.set(isVisable);
    }

    public User( boolean isVisable, String name, String content) {
        this.name = name;
        this.content = content;
        this.isVisable = new ObservableBoolean(isVisable);
    }

    public User(String name, String content) {
        this.name = name;
        this.content = content;
        isVisable = new ObservableBoolean(false);
        map.put("ite_1", "Click_1 参数");
        map.put("ite_2", "Click_2 参数");
        map.put("ite_3", "Click_3 参数");

        list.add("Click_1");
        list.add("Click_1");
        list.add("Click_1");
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Bindable
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        notifyPropertyChanged(BR.name);
    }

    @Bindable
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
        notifyPropertyChanged(BR.content);
    }

    public String getHeadImgUrl() {
        return headImgUrl;
    }

    public void setHeadImgUrl(String headImgUrl) {
        this.headImgUrl = headImgUrl;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeInt(this.age);
        dest.writeString(this.name);
        dest.writeString(this.content);
        dest.writeString(this.headImgUrl);
    }

    public User() {
    }

    protected User(Parcel in) {
        this.id = in.readInt();
        this.age = in.readInt();
        this.name = in.readString();
        this.content = in.readString();
        this.headImgUrl = in.readString();
    }

    public static final Parcelable.Creator<User> CREATOR = new Parcelable.Creator<User>() {
        @Override
        public User createFromParcel(Parcel source) {
            return new User(source);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    @NonNull
    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", age=" + age +
                ", name='" + name + '\'' +
                ", content='" + content + '\'' +
                ", headImgUrl='" + headImgUrl + '\'' +
                '}';
    }
}
