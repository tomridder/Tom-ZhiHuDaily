
package com.bignerdranch.myrxmeizi.bean;

import com.google.gson.annotations.SerializedName;


import org.litepal.crud.LitePalSupport;

import java.io.Serializable;
import java.util.List;

/**
 * 知乎当日新闻
 */
public class Stories extends LitePalSupport implements Serializable {
    //供 Google Analytics 使用
    @SerializedName("ga_prefix")
    private String mGaPrefix;
    //url 与 share_url 中最后的数字（应为内容的 id）
    @SerializedName("id")
    private Long mId;
    //图像地址（官方 API 使用数组形式。目前暂未有使用多张图片的情形出现，曾见无 images 属性的情况，请在使用中注意 ）
    @SerializedName("images")
    private List<String> mImages;
    //消息是否包含多张图片（仅出现在包含多图的新闻中）
    @SerializedName("multipic")
    private Boolean mMultipic;
    //新闻标题
    @SerializedName("title")
    private String mTitle;
    //作用未知
    @SerializedName("type")
    private Long mType;

    public Stories()
    {
        this.mGaPrefix = null;
        this.mId = 0L;
        this.mImages = null;
        this.mMultipic = false;
        this.mTitle = null;
        this.mType = 0L;
    }

    public Stories(String mGaPrefix, Long mId, List<String> mImages, Boolean mMultipic, String mTitle, Long mType) {
        this.mGaPrefix = mGaPrefix;
        this.mId = mId;
        this.mImages = mImages;
        this.mMultipic = mMultipic;
        this.mTitle = mTitle;
        this.mType = mType;
    }

    public void setGaPrefix(String mGaPrefix) {
        this.mGaPrefix = mGaPrefix;
    }

    public void setId(Long mId) {
        this.mId = mId;
    }

    public void setImages(List<String> mImages) {
        this.mImages = mImages;
    }

    public void setMultipic(Boolean mMultipic) {
        this.mMultipic = mMultipic;
    }

    public void setTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    public void setType(Long mType) {
        this.mType = mType;
    }

    public String getGaPrefix() {
        return mGaPrefix;
    }

    public Long getId() {
        return mId;
    }

    public List<String> getImages() {
        return mImages;
    }

    public Boolean getMultipic() {
        return mMultipic;
    }

    public String getTitle() {
        return mTitle;
    }

    public Long getType() {
        return mType;
    }

    @Override
    public String toString() {
        return "Stories{" +
                "mGaPrefix='" + mGaPrefix + '\'' +
                ", mId=" + mId +
                ", mImages=" + mImages +
                ", mMultipic=" + mMultipic +
                ", mTitle='" + mTitle + '\'' +
                ", mType=" + mType +
                '}';
    }
}
