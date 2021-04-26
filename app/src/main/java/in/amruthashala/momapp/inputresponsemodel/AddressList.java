package in.amruthashala.momapp.inputresponsemodel;

public class AddressList {
    public String name;
    public String address;
    public String landmark;
    public String state;
    public String city;
    public String latitude;
    public String longitude;
    public String pincode;
    public String alternate_mobile_number;


    public BankAccountDetails bank_account_details;
    public OtherAccount other_account;

    public AddressList(String name, String address, String landmark, String state, String city, String latitude, String longitude, String pincode, String alternate_mobile_number) {
        this.name = name;
        this.address = address;
        this.landmark = landmark;
        this.state = state;
        this.city = city;
        this.latitude = latitude;
        this.longitude = longitude;
        this.pincode = pincode;
        this.alternate_mobile_number = alternate_mobile_number;
    }

    public AddressList(BankAccountDetails bank_account_details, OtherAccount other_account) {
        this.bank_account_details = bank_account_details;
        this.other_account = other_account;
    }

    public BankAccountDetails getBank_account_details() {
        return bank_account_details;
    }

    public void setBank_account_details(BankAccountDetails bank_account_details) {
        this.bank_account_details = bank_account_details;
    }

    public OtherAccount getOther_account() {
        return other_account;
    }

    public void setOther_account(OtherAccount other_account) {
        this.other_account = other_account;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getLandmark() {
        return landmark;
    }

    public void setLandmark(String landmark) {
        this.landmark = landmark;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getPincode() {
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }

    public String getAlternate_mobile_number() {
        return alternate_mobile_number;
    }

    public void setAlternate_mobile_number(String alternate_mobile_number) {
        this.alternate_mobile_number = alternate_mobile_number;
    }
}
