package com.ajman.ded.ae.data.model.request.InsertNewOnlineUser;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Namespace;
import org.simpleframework.xml.Order;
import org.simpleframework.xml.Root;


@Root(name = "InsertNewOnlineUser_Josn", strict = false)
@Namespace(reference = "http://tempuri.org/")
@Order(elements = {"StackholderType", "Email", "MobileNo", "Password", "ConfirmPassword", "Language", "NameEN", "NameAR", "Nationality", "Gender", "IdentityNumber", "IdentityExpireDate", "PassportNo", "Address", "IsCurrentInvestor"})
public class RequestData_InsertNewOnlineUser {


    @Element(name = "StackholderType", required = false)
    private String StackholderType;

    @Element(name = "Email", required = false)
    private String Email;

    @Element(name = "MobileNo", required = false)
    private String MobileNo;

    @Element(name = "Password", required = false)
    private String Password;

    @Element(name = "ConfirmPassword", required = false)
    private String ConfirmPassword;

    @Element(name = "Language", required = false)
    private String Language;

    @Element(name = "NameEN", required = false)
    private String NameEN;

    @Element(name = "NameAR", required = false)
    private String NameAR;

    @Element(name = "Nationality", required = false)
    private String Nationality;

    @Element(name = "Gender", required = false)
    private String Gender;

    @Element(name = "IdentityNumber", required = false)
    private String IdentityNumber;

    @Element(name = "IdentityExpireDate", required = false)
    private String IdentityExpireDate;

    @Element(name = "PassportNo", required = false)
    private String PassportNo;

    @Element(name = "Address", required = false)
    private String Address;

    @Element(name = "IsCurrentInvestor", required = false)
    private boolean IsCurrentInvestor;

    public String getStackholderType() {
        return StackholderType;
    }

    public void setStackholderType(String stackholderType) {
        this.StackholderType = stackholderType;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        this.Email = email;
    }

    public String getMobileNo() {
        return MobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.MobileNo = mobileNo;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        this.Password = password;
    }

    public String getConfirmPassword() {
        return ConfirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.ConfirmPassword = confirmPassword;
    }

    public String getLanguage() {
        return Language;
    }

    public void setLanguage(String language) {
        this.Language = language;
    }

    public String getNameEN() {
        return NameEN;
    }

    public void setNameEN(String nameEN) {
        this.NameEN = nameEN;
    }

    public String getNameAR() {
        return NameAR;
    }

    public void setNameAR(String nameAR) {
        this.NameAR = nameAR;
    }

    public String getNationality() {
        return Nationality;
    }

    public void setNationality(String nationality) {
        this.Nationality = nationality;
    }

    public String getGender() {
        return Gender;
    }

    public void setGender(String gender) {
        this.Gender = gender;
    }

    public String getIdentityNumber() {
        return IdentityNumber;
    }

    public void setIdentityNumber(String identityNumber) {
        this.IdentityNumber = identityNumber;
    }

    public String getIdentityExpireDate() {
        return IdentityExpireDate;
    }

    public void setIdentityExpireDate(String identityExpireDate) {
        this.IdentityExpireDate = identityExpireDate;
    }

    public String getPassportNo() {
        return PassportNo;
    }

    public void setPassportNo(String passportNo) {
        this.PassportNo = passportNo;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        this.Address = address;
    }

    public boolean isCurrentInvestor() {
        return IsCurrentInvestor;
    }

    public void setIsCurrentInvestor(boolean isCurrentInvestor) {
        this.IsCurrentInvestor = isCurrentInvestor;
    }

}


