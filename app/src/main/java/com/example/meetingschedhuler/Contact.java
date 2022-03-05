package com.example.meetingschedhuler;

public class Contact {

    private  int _id , _is_favourite, _is_important, _is_main_user;

    private String _firstName, _lastName, _cellPhone, _email, _facebook, _linkedIn, _instagram,
                    _website, _streetAddress, _suburb, _city, _postalCOde, _country;

    public  Contact(
            Integer Id,
            String FirstName,
            String LastName,

            String CellPhone,
            String Email,
            String Facebook,
            String LinkedIn,
            String Instagram,
            String Website,

            Integer IsFavourite,
            Integer IsImportant,
            Integer IsMainUser,

            String StreetAddress,
            String Suburb,
            String City,
            String PostalCode,
            String Country
            ){

        this._id = Id;

        this._firstName = FirstName;
        this._lastName = LastName;

        this._cellPhone = CellPhone;
        this. _email = Email;
        this._facebook = Facebook;
        this._linkedIn = LinkedIn;
        this._instagram = Instagram;
        this._website = Website;

        this._is_favourite = IsFavourite;
        this._is_important = IsImportant;
        this._is_main_user = IsMainUser;

        this._streetAddress = StreetAddress;
        this._suburb = Suburb;
        this._city = City;
        this._postalCOde = PostalCode;
        this._country = Country;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public int get_is_main_user() {
        return _is_main_user;
    }

    public void set_is_main_user(int _is_main_user) {
        this._is_main_user = _is_main_user;
    }

    public int get_is_important() {
        return _is_important;
    }

    public void set_is_important(int _is_important) {
        this._is_important = _is_important;
    }

    public int get_is_favourite() {
        return _is_favourite;
    }

    public String get_postalCOde() {
        return _postalCOde;
    }

    public void set_postalCOde(String _postalCOde) {
        this._postalCOde = _postalCOde;
    }

    public String get_city() {
        return _city;
    }

    public void set_city(String _city) {
        this._city = _city;
    }

    public String get_suburb() {
        return _suburb;
    }

    public void set_suburb(String _suburb) {
        this._suburb = _suburb;
    }

    public String get_streetAddress() {
        return _streetAddress;
    }

    public void set_streetAddress(String _streetAddress) {
        this._streetAddress = _streetAddress;
    }

    public String get_website() {
        return _website;
    }

    public void set_website(String _website) {
        this._website = _website;
    }

    public String get_instagram() {
        return _instagram;
    }

    public void set_instagram(String _instagram) {
        this._instagram = _instagram;
    }

    public String get_linkedIn() {
        return _linkedIn;
    }

    public void set_linkedIn(String _linkedIn) {
        this._linkedIn = _linkedIn;
    }

    public String get_facebook() {
        return _facebook;
    }

    public void set_facebook(String _facebook) {
        this._facebook = _facebook;
    }

    public String get_email() {
        return _email;
    }

    public void set_email(String _email) {
        this._email = _email;
    }

    public String get_cellPhone() {
        return _cellPhone;
    }

    public void set_cellPhone(String _cellPhone) {
        this._cellPhone = _cellPhone;
    }

    public String get_lastName() {
        return _lastName;
    }

    public void set_lastName(String _lastName) {
        this._lastName = _lastName;
    }

    public String get_country() {
        return _country;
    }

    public void set_country(String _country) {
        this._country = _country;
    }

    public String get_firstName() {
        return _firstName;
    }

    public void set_firstName(String _firstName) {
        this._firstName = _firstName;
    }

    public void set_is_favourite(int _is_favourite) {
        this._is_favourite = _is_favourite;
    }
}
