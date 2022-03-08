package com.example.meetingschedhuler;

import android.graphics.Bitmap;

public class Image {
    private Integer _id;
    private Integer _contactId;
    private Integer IsProfilePicture; //boolean
    private  Bitmap image;

    public Image(Integer _id, Integer _contactId, Integer isProfilePicture, Bitmap image) {
        this._id = _id;
        this._contactId = _contactId;
        IsProfilePicture = isProfilePicture;
        this.image = image;
    }

    public Integer get_id() {
        return _id;
    }

    public void set_id(Integer _id) {
        this._id = _id;
    }

    public Integer get_contactId() {
        return _contactId;
    }

    public void set_contactId(Integer _contactId) {
        this._contactId = _contactId;
    }

    public Integer getIsProfilePicture() {
        return IsProfilePicture;
    }

    public void setIsProfilePicture(Integer isProfilePicture) {
        IsProfilePicture = isProfilePicture;
    }

    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }
}
