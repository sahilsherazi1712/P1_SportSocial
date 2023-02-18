package com.sahilssoft.p1_sportsocial.Methods;

import android.content.Context;

import com.android.volley.Request;
import com.sahilssoft.p1_sportsocial.callback.ObjectCallback;
import com.sahilssoft.p1_sportsocial.models.SignUpModel;
import com.sahilssoft.p1_sportsocial.models.SportsModel;
import com.sahilssoft.p1_sportsocial.models.CheckEmailModel;
import com.sahilssoft.p1_sportsocial.models.ContactUsModel;
import com.sahilssoft.p1_sportsocial.models.FavouriteModel;
import com.sahilssoft.p1_sportsocial.models.GetPodcastModel;
import com.sahilssoft.p1_sportsocial.models.LoginModel;
import com.sahilssoft.p1_sportsocial.models.LogoutModel;
import com.sahilssoft.p1_sportsocial.models.PlayModel;
import com.sahilssoft.p1_sportsocial.models.PodcastModel;
import com.sahilssoft.p1_sportsocial.models.ProfileModel;
import com.sahilssoft.p1_sportsocial.models.SearchCatClickModel;
import com.sahilssoft.p1_sportsocial.models.SubCategoryModel;
import com.sahilssoft.p1_sportsocial.models.UpdateAccountModel;

public class APIDao {
//    public static void login(Context context, UserModel userModel, ObjectCallback<UserModel> callback) {
//        APIRequest.getInstance(context).postPutRequest("login", userModel, callback, Request.Method.POST);
//    }
//    public static void getInfo(Context context, ObjectCallback<LoginModel> callback){
//        APIRequest.getInstance(context).getObjectRequest(Constraints.API_URL,UserModel.class,callback);
//    }
    public static void LoginAPI(Context context, LoginModel loginModel, ObjectCallback<LoginModel> callback){
        APIRequest.getInstance(context).postPutRequest("login",loginModel,callback, Request.Method.POST);
    }
    public static void LogoutAPI(Context context, ObjectCallback<LogoutModel> callback){
        APIRequest.getInstance(context).getObjectRequest("logout",LogoutModel.class,callback);
    }
    public static void PodcastAPI(Context context, ObjectCallback<PodcastModel> callback){
        APIRequest.getInstance(context).getObjectRequest("podcastlists",PodcastModel.class,callback);
    }
    public static void GetPodCast(Context context, GetPodcastModel getPodcastModel, ObjectCallback<GetPodcastModel> callback){
        APIRequest.getInstance(context).postPutRequest("getpodcast",getPodcastModel,callback, Request.Method.POST);
    }
    public static void Play(Context context, PlayModel playModel, ObjectCallback<PlayModel> callback){
        APIRequest.getInstance(context).postPutRequest("getepisode",playModel,callback, Request.Method.POST);
    }
    public static void Search(Context context, PodcastModel podcastModel, ObjectCallback<PodcastModel> callback){
        APIRequest.getInstance(context).postPutRequest("search_category",podcastModel,callback, Request.Method.POST);
    }
    public static void SearchCatClick(Context context, SearchCatClickModel searchCatClickModel, ObjectCallback<SearchCatClickModel> callback){
        APIRequest.getInstance(context).postPutRequest("category_search_podcast",searchCatClickModel,callback, Request.Method.POST);
    }
    public static void News(Context context, ObjectCallback<PodcastModel> callback){
        APIRequest.getInstance(context).getObjectRequest("newspodcast",PodcastModel.class,callback);
    }
    public static void Library(Context context, PodcastModel podcastModel, ObjectCallback<PodcastModel> callback){
        APIRequest.getInstance(context).postPutRequest("library",podcastModel,callback, Request.Method.POST);
    }
    public static void Favourite(Context context, FavouriteModel favouriteModel, ObjectCallback<FavouriteModel> callback){
        APIRequest.getInstance(context).postPutRequest("favourite",favouriteModel,callback, Request.Method.POST);
    }
    public static void UnFavourite(Context context, FavouriteModel favouriteModel, ObjectCallback<FavouriteModel> callback){
        APIRequest.getInstance(context).postPutRequest("unfavourite",favouriteModel,callback, Request.Method.POST);
    }
    public static void UpdateAccount(Context context, UpdateAccountModel updateAccountModel, ObjectCallback<UpdateAccountModel> callback){
        APIRequest.getInstance(context).postPutRequest("editaccount",updateAccountModel,callback, Request.Method.POST);
    }
    public static void ResetPassword(Context context, UpdateAccountModel updateAccountModel, ObjectCallback<UpdateAccountModel> callback){
        APIRequest.getInstance(context).postPutRequest("resetpassword",updateAccountModel,callback, Request.Method.POST);
    }
    public static void ContactUs(Context context, ContactUsModel contactUsModel, ObjectCallback<ContactUsModel> callback){
        APIRequest.getInstance(context).postPutRequest("contactus",contactUsModel,callback, Request.Method.POST);
    }
    public static void Profile(Context context, ObjectCallback<ProfileModel> callback){
        APIRequest.getInstance(context).getObjectRequest("getprofile",ProfileModel.class,callback);
    }
    public static void CheckEmail(Context context, CheckEmailModel checkEmailModel, ObjectCallback<CheckEmailModel> callback){
        APIRequest.getInstance(context).postPutRequest("emailcheck",checkEmailModel,callback, Request.Method.POST);
    }
    public static void Category(Context context, ObjectCallback<SportsModel> callback){
        APIRequest.getInstance(context).getObjectRequest("category", SportsModel.class,callback);
    }
    public static void SubCategory(Context context, SubCategoryModel subCategoryModel, ObjectCallback<SubCategoryModel> callback){
        APIRequest.getInstance(context).postPutRequest("sub_category",subCategoryModel,callback, Request.Method.POST);
    }
    public static void SignUp(Context context, SignUpModel signUpModel, ObjectCallback<SignUpModel> callback){
        APIRequest.getInstance(context).postPutRequest("signup",signUpModel,callback, Request.Method.POST);
    }
}
