package com.ajman.ded.ae.data;

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
import com.ajman.ded.ae.data.model.request.UserId.RequestEnvelope_UserId;
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
import com.ajman.ded.ae.data.model.response.UserId.ResponseEnvelope_UserId;
import com.ajman.ded.ae.models.NotificationResponse;
import com.ajman.ded.ae.models.notification.NotificationStatusResponse;
import com.ajman.ded.ae.models.notification.details.NotificationDetailsResponse;
import com.ajman.ded.ae.models.notification.files.FilesRsponse;
import com.ajman.ded.ae.models.notification.tybe.NotificationTypeResponse;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface Api {
    String BASE = "/WS_MobileApp/WebService_MobileApp.asmx";

    String NewsLink = "/_MobFiles/AjmanDED_MobService.asmx/News_Get?NumberOfItems=10";

    String INSERT_NOTIFICATION = "extensions/dedeye/Notification_Insert";

    String STATUS_NOTIFICATION = "extensions/dedeye/Notifications_GetByUserId";

    String TYPE_NOTIFICATION = "extensions/dedeye/NotificationTypes_Get";

    String DETAILS_NOTIFICATION = "extensions/dedeye/Notification_GetById";

    String RATE_NOTIFICATION = "extensions/dedeye/NotificationRate_Insert";

    String UPLAOD_NOTIFICATION = "extensions/dedeye/UploadNotificationFiles";

    String FILES_NOTIFICATION = "extensions/dedeye/Notification_GetFiles";

    String FILE_NOTIFICATION = "apis/GetFileById";

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
    Call<ResponseEnvelope_UserId> requestUserIdCall(@Query("op") String param, @Body RequestEnvelope_UserId body);

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

    @POST(INSERT_NOTIFICATION)
    Call<NotificationResponse> insert_notification(@Query("UserId") String userId, @Query("NotificationDate") String notificationDate, @Query("EstablishmentName") String establishmentName, @Query("LicenseNumber") String licenceNo, @Query("NotificationTypeId") String notificationTypeId, @Query("NotificationDetails") String notificatoinDetails, @Query("ll") String ll, @Query("lg") String lg);

    @GET(STATUS_NOTIFICATION)
    Call<NotificationStatusResponse> status_notification(@Query("UserId") String userId, @Query("Status") String status);

    @GET(TYPE_NOTIFICATION)
    Call<NotificationTypeResponse> type_notification();

    @GET(DETAILS_NOTIFICATION)
    Call<NotificationDetailsResponse> details_notification(@Query("notificationId") String notificationId);

    @GET(FILES_NOTIFICATION)
    Call<FilesRsponse> files_notification(@Query("notificationId") String notificationId);

    @GET(FILE_NOTIFICATION)
    Call<ResponseBody> file_notification(@Query("id") String id);

    @GET(RATE_NOTIFICATION)
    Call<ResponseBody> rate_notification(@Query("UserId") String userId, @Query("notificationId") String notificationId, @Query("Satisfied") String satisfied, @Query("Note") String note);

    @Multipart
    @POST(UPLAOD_NOTIFICATION)
    Call<NotificationResponse> uploadMultipleFilesDynamic(@Part("NotificationId") RequestBody description, @Part List<MultipartBody.Part> files);
}
