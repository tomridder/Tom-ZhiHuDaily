
package com.bignerdranch.myrxmeizi.bean;

import com.google.gson.annotations.SerializedName;



import java.io.Serializable;

/**
 * 界面顶部滚动显示的显示内容
 */
public class TopStories  implements Serializable {

    @SerializedName("ga_prefix")
    private String mGaPrefix;
    @SerializedName("id")
    private Long mId;
    @SerializedName("image")
    private String mImage;
    @SerializedName("title")
    private String mTitle;
    @SerializedName("type")
    private Long mType;

    public String getGaPrefix() {
        return mGaPrefix;
    }

    public void setGaPrefix(String ga_prefix) {
        mGaPrefix = ga_prefix;
    }

    public Long getId() {
        return mId;
    }

    public void setId(Long id) {
        mId = id;
    }

    public String getImage() {
        return mImage;
    }

    public void setImage(String image) {
        mImage = image;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public Long getType() {
        return mType;
    }

    public void setType(Long type) {
        mType = type;
    }

    @Override
    public String toString() {
        return "TopStories{" +
                "mGaPrefix='" + mGaPrefix + '\'' +
                ", mId=" + mId +
                ", mImage='" + mImage + '\'' +
                ", mTitle='" + mTitle + '\'' +
                ", mType=" + mType +
                '}';
    }
}
