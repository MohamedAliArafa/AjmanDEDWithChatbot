package com.ajman.ded.ae.data;

import android.app.Notification;

import com.ajman.ded.ae.data.model.request.ApplicationListByEmail.RequestEnvelope_ApplicationListByEmail;
import com.ajman.ded.ae.data.model.request.ConfirmCode.RequestEnvelope_ConfirmCode;
import com.ajman.ded.ae.data.model.request.GetAccount.RequestEnvelope_GetAccount;
import com.ajman.ded.ae.data.model.request.GetRequestStatus.RequestEnvelope_GetRequestStatus;
import com.ajman.ded.ae.data.model.request.InsertNewHappy.RequestEnvelope_InsertNewHappy;
import com.ajman.ded.ae.data.model.request.InsertNewOnlineUser.RequestEnvelope_InsertNewOnlineUser;
import com.ajman.ded.ae.data.model.request.OnlineUserAllLicense_CountMob.RequestEnvelope_OnlineUserAllLicense_CountMob;
import com.ajman.ded.ae.data.model.request.OnlineUserAllLicense_Mob.RequestEnvelope_OnlineUserAllLicense_Mob;
import com.ajman.ded.ae.data.model.request.OnlineUserAllPermits.RequestEnvelope_OnlineUserAllPermits;
import com.ajman.ded.ae.data.model.request.SendSMS.RequestEnvelope_SendSMS;
import com.ajman.ded.ae.data.model.response.ApplicationListByEmail.ResponseEnvelope_ApplicationListByEmail;
import com.ajman.ded.ae.data.model.response.ConfirmCode.ResponseEnvelope_ConfirmCode;
import com.ajman.ded.ae.data.model.response.GetAccount.ResponseEnvelope_GetAccount;
import com.ajman.ded.ae.data.model.response.GetRequestStatus.ResponseEnvelope_GetRequestStatus;
import com.ajman.ded.ae.data.model.response.InsertNewHappy.ResponseEnvelope_InsertNewHappy;
import com.ajman.ded.ae.data.model.response.InsertNewOnlineUser.ResponseEnvelope_InsertNewOnlineUser;
import com.ajman.ded.ae.data.model.response.OnlineUserAllLicense_CountMob.ResponseEnvelope_OnlineUserAllLicense_CountMob;
import com.ajman.ded.ae.data.model.response.OnlineUserAllLicense_Mob.ResponseEnvelope_OnlineUserAllLicense_Mob;
import com.ajman.ded.ae.data.model.response.OnlineUserAllPermits.ResponseEnvelope_OnlineUserAllPermits;
import com.ajman.ded.ae.data.model.response.SendSMS.ResponseEnvelope_SendSMS;
import com.ajman.ded.ae.models.InsertNotificationResponse;
import com.ajman.ded.ae.models.notification.NotificationStatusResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface Api {
    String BASE = "/WS_MobileApp/WebService_MobileApp.asmx";

    String NewsLink = "/_MobFiles/AjmanDED_MobService.asmx/News_Get?NumberOfItems=10";

    String INSERT_NOTIFICATION = "extensions/dedeye/Notification_Insert";

    String STATUS_NOTIFICATION = "extensions/dedeye/Notifications_GetByUserId";


    @Headers({"Content-Type: application/soap+xml; charset=utf-8"})
    @POST(BASE)
    Call<ResponseEnvelope_ApplicationListByEmail> requestListByEmailCall(@Query("op") String param, @Body RequestEnvelope_ApplicationListByEmail body);

    @Headers({"Content-Type: application/soap+xml; charset=utf-8"})
    @POST(BASE)
    Call<ResponseEnvelope_OnlineUserAllLicense_CountMob> requestCountMobCall(@Query("op") String param, @Body RequestEnvelope_OnlineUserAllLicense_CountMob body);

    @Headers({"Content-Type: application/soap+xml; charset=utf-8"})
    @POST(BASE)
    Call<ResponseEnvelope_OnlineUserAllLicense_Mob> requestLicenseMobCall(@Query("op") String param, @Body RequestEnvelope_OnlineUserAllLicense_Mob body);

    @Headers({"Content-Type: application/soap+xml; charset=utf-8"})
    @POST(BASE)
    Call<ResponseEnvelope_OnlineUserAllPermits> requestAllPermitsCall(@Query("op") String param, @Body RequestEnvelope_OnlineUserAllPermits body);

    @Headers({"Content-Type: application/soap+xml; charset=utf-8"})
    @POST(BASE)
    Call<ResponseEnvelope_GetAccount> doGetAccount(@Query("op") String param, @Body RequestEnvelope_GetAccount body);

    @Headers({"Content-Type: application/soap+xml; charset=utf-8"})
    @POST(BASE)
    Call<ResponseEnvelope_InsertNewOnlineUser> requestNewOnlineUserCall(@Query("op") String param, @Body RequestEnvelope_InsertNewOnlineUser body);

    @Headers({"Content-Type: application/soap+xml; charset=utf-8"})
    @POST(BASE)
    Call<ResponseEnvelope_SendSMS> requestSmsCall(@Query("op") String param, @Body RequestEnvelope_SendSMS body);

    @Headers({"Content-Type: application/soap+xml; charset=utf-8"})
    @POST(BASE)
    Call<ResponseEnvelope_InsertNewHappy> requestNewHappyCall(@Query("op") String param, @Body RequestEnvelope_InsertNewHappy body);

    @Headers({"Content-Type: application/soap+xml; charset=utf-8"})
    @POST(BASE)
    Call<ResponseEnvelope_ConfirmCode> requestConfirmCodeCall(@Query("op") String param, @Body RequestEnvelope_ConfirmCode body);

    @Headers({"Content-Type: application/soap+xml; charset=utf-8"})
    @POST(BASE)
    Call<ResponseEnvelope_GetRequestStatus> requestStatusCall(@Query("op") String param, @Body RequestEnvelope_GetRequestStatus body);

    @GET(NewsLink)
    Call<String> news_caller();

    @GET(INSERT_NOTIFICATION)
    Call<InsertNotificationResponse> insert_notification(@Query("UserId") String userId, @Query("NotificationDate") String notificationDate, @Query("EstablishmentName") String establishmentName, @Query("LicenseNumber") String licenceNo, @Query("NotificationTypeId") String notificationTypeId, @Query("NotificationDetails") String notificatoinDetails, @Query("ll") String ll, @Query("lg") String lg);

    @GET(STATUS_NOTIFICATION)
    Call<NotificationStatusResponse> status_notification(@Query("UserId") String userId, @Query("Status") String status);

}
