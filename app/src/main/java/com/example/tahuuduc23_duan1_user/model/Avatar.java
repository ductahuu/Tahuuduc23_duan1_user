package com.example.tahuuduc23_duan1_user.model;

public class Avatar {
    private String title,image;

    public Avatar() {
    }

    public Avatar(String title, String image) {
        this.title = title;
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return "Avatar{" +
                "title='" + title + '\'' +
                ", image='" + image + '\'' +
                '}';
    }
}
