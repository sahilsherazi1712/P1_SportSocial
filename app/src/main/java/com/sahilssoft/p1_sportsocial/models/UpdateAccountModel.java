package com.sahilssoft.p1_sportsocial.models;

public class UpdateAccountModel {
    public int status;
    public String message;

    //update name, email, gender
    public String first_name, sur_name, email, gender;

    //update password
    public String old_password, new_password, confirm_password;
}
