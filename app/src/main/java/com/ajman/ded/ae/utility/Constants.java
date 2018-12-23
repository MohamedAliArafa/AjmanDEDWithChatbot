package com.ajman.ded.ae.utility;


/**
 * Created by root on 10/2/17.
 */

public interface Constants {
    String LOGIN_FRAGMENT_KEY = "login_fragment_key";
    String REGISTER_FRAGMENT_KEY = "register_fragment_key";

    String URL_INTENT_KEY = "url_intent_key";

    interface links {
        String DOMAIN_EN = "Https://eservices.ajmanded.ae/en/";
        String DOMAIN_AR = "Https://eservices.ajmanded.ae/ar/";

        //Domain Services
        String DOMAIN_APP_EN = "https://www.ajmanded.ae/MobileApp/en/";
        String DOMAIN_APP_AR = "https://www.ajmanded.ae/MobileApp/ar/";

        //services
        String suggestions = "Suggestion.aspx";
        String complaints = "complaints.aspx";
        String applayForJob = "hrform_mobile.aspx";
        String investInAjman = "invest_mobile.html";
        String FAQ = "faq_mobile.html";
        String businessControl = "businessguide_mobile.html";
        String Customers_happiness_Pledge = "chart_mobile.html";

        //Inquiries
        String TRADE_NAME_RESERVATION = "entities/createbyform?defid=e65a3e12-691f-448e-9d98-cacf4ba6778b&formName=OnlineForm_Create";
        String CHECK_APPLICATION_STATUS = "extensions/NewOnlineInquiries/getRequestStatus";
        String CHECK_INSPECTION_STATUS = "entities/createbyanon?defid=6fff2b47-e0e7-40bb-92b2-7a9c3039fb72&formName=InspectionInfo_Online";
        String ESTIMATE_NEW_LICENSE_FEE = "entities/createbyanon?defid=935bae4a-3842-432e-9e0a-12a9c2a177e4&formName=OnlineForm_Create";
        String ESTIMATE_RENEW_LICENSE_FEE = "entities/createbyform?defid=fdbba621-b87e-40a1-a646-b7210baee798&formName=OnlineForm_Create";
        String ESTIMATE_NEW_PERMIT_FEES = "entities/createbyanon?defid=daaa4e0e-4958-4a5e-90fa-5f56e257a5a2&formName=OnlineForm";
        String MATCHED_ACTIVITIES = "entities/createbyanon?defid=7a295674-9213-4395-b522-4256cabf72e7&formName=OnlineForm";
        String BUSINESS_ACTIVITY_EXTERNAL_APPROVAL = "entities/createbyanon?defid=6b5dbf2f-282e-4ca2-953b-c4e22d073d7b&formName=OnlineForm";
        String PERMIT_TERMS_AND_CONDITIONS = "entities/createbyanon?defid=daaa4e0e-4958-4a5e-90fa-5f56e257a5a2&formName=PemitConditions";
        String TRADE_LICENSE_INQUIRY = "OnlineInquiries/ViewLicenseData";

        //services
        String INITIAL_APPROVAL = "entities/createbyform?defid=e65a3e12-691f-448e-9d98-cacf4ba6778b&formName=OnlineForm_Create";
        String ISSUE_TRADE_LICENSE = "entities/createbyform?defid=8f538ec6-e623-4675-ab57-534865ec2b73&formName=OnlineForm_Create";
        String RENEW_TRADE_LICENSE = "entities/createbyform?defid=1043782d-9d2b-403c-bfe0-9fbae0aa20e8&formName=OnlineForm_Step1";
        String LINK_EXTERNAL_LICENSE = "entities/createbyform?defid=7b75f0a2-6c88-48c2-b3dc-14bc48fc48c7&formName=OnlineForm_Step1";
        String ISSUE_NEW_PERMIT = "entities/createbyform?defid=3d79ac09-9cf1-49cc-9fb0-f17f6ca3029e&formName=OnlineForm_Step1";
        String CERTIFICATE_REQUEST = "entities/createbyform?defid=3b0848eb-40b0-4b5f-93de-5836323d88bf&formName=OnlineForm_Create";


        String INCOMPLETE_APPLICATIONS = "home/mytransactions";
        String APP_APPLICATIONS = "Transactions/index";
        String UNDER_REVIEW_APPLICATION = "Transactions/InProgress";
        String REJECTED_APPLICATIONS = "Transactions/Rejected";
        String COMPLETE_APPLICATIONS = "Transactions/Completed";
        String PAYMENT_HESTORY = "extensions/OnlineServices/PaymentHistory";
        String LICENSE_REPORT = "OnlineInquiries/PersonLicenseList";
        String VAILED_PERMISSIONS_REPORT = "OnlineInquiries/ValidPermitsReport";
        String EXPIRED_PERMISSIONS_REPORT = "OnlineInquiries/ExpiredPermitsReport";

        //Authenticated
        String PROACTIVE_INSPECTION_REQUEST = "entities/createbyform?defid=a46176d1-bbd4-4244-b357-b6da428df6cf&formName=Create_Online";
        String STAKEHOLDER_FINE_INQUIRY = "entities/createbyform?defid=ed3c89a6-a37b-4083-a0fd-81bd81935ac8&formName=InfractionsFormPersonInquiry_Online";
        String STAKEHOLDER_FINE_PAYMENT = "entities/createbyform?defid=ed3c89a6-a37b-4083-a0fd-81bd81935ac8&formName=InfractionsFormPerson_Online";
        String PRICE_LIST_APPROVAL = "entities/createbyform?defid=3317fd29-df28-408e-b2dd-3a3978b5f307&formName=OnlineForm_Create";
        String FRAUD_COMPLAINT_REQUEST = "entities/createbyform?defid=6870342b-4e0f-4342-a542-86e3e18de026&formName=CreateForm_Online";
        String CANSEL_LICENSE = "entities/createbyform?defid=e42eb1eb-c5a5-4f53-8ea8-4875ec16dddb&formName=OnlineForm_CancelLicense";
        String ACTIVITES_ADJUSTMENT_CONFIRMATION = "entities/createbyform?defid=228e6b6e-f49a-47bf-a0e4-5510cc71dc1f&formName=Online_Create";
    }

}
