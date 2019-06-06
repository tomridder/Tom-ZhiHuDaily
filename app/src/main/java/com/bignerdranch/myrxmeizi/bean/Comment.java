package com.bignerdranch.myrxmeizi.bean;

import com.bignerdranch.myrxmeizi.utils.DateUtil;
import com.google.gson.annotations.SerializedName;

public class Comment
{
    private int id;
    private String author;
    private String content;
    private String avatar;
    private long time;
    @SerializedName("reply_to")
    private ReplyTo replyTo;
    private int likes;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public long getTime() {
        return time;
    }

    public String getTimeStr(){
        return DateUtil.format(time);
    }

    public void setTime(long time) {
        this.time = time;
    }

    public ReplyTo getReplyTo() {
        return replyTo;
    }

    public void setReplyTo(ReplyTo replyTo) {
        this.replyTo = replyTo;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "id=" + id +
                ", author='" + author + '\'' +
                ", content='" + content + '\'' +
                ", avatar='" + avatar + '\'' +
                ", time='" + time + '\'' +
                ", replyTo=" + replyTo +
                ", likes=" + likes +
                '}';
    }



    public class ReplyTo
    {
        private int id;
        private String author;
        private String content;
        private int status;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getAuthor() {
            return author;
        }

        public void setAuthor(String author) {
            this.author = author;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public ReplyTo(int id, String author, String content, int status) {
            this.id = id;
            this.author = author;
            this.content = content;
            this.status = status;
        }

        @Override
        public String toString() {
            return "ReplyTo{" +
                    "id=" + id +
                    ", author='" + author + '\'' +
                    ", content='" + content + '\'' +
                    ", status=" + status +
                    '}';
        }

    }
}
