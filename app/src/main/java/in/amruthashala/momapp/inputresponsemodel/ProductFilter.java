package in.amruthashala.momapp.inputresponsemodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ProductFilter {
    @SerializedName("manufactured_by")
    @Expose
    public String manufactured_by;
    @SerializedName("made_in_country")
    @Expose
    public String made_in_country;
    @SerializedName("product_rating")
    @Expose
    public String product_rating;
    @SerializedName("regional_speciality")
    @Expose
    public String regional_speciality;
    @SerializedName("max_self_life")
    @Expose
    public String max_self_life;
    @SerializedName("organic")
    @Expose
    public String organic;
    @SerializedName("food_preference")
    @Expose
    public String food_preference;
    @SerializedName("product_type")
    @Expose
    public String product_type;
    @SerializedName("product_taste")
    @Expose
    public String product_taste;
    public ProductFilter(String manufactured_by, String made_in_country, String product_rating, String regional_speciality, String max_self_life, String organic, String food_preference, String product_type, String product_taste) {
        this.manufactured_by = manufactured_by;
        this.made_in_country = made_in_country;
        this.product_rating = product_rating;
        this.regional_speciality = regional_speciality;
        this.max_self_life = max_self_life;
        this.organic = organic;
        this.food_preference = food_preference;
        this.product_type = product_type;
        this.product_taste = product_taste;
    }

    public String getManufactured_by() {
        return manufactured_by;
    }

    public void setManufactured_by(String manufactured_by) {
        this.manufactured_by = manufactured_by;
    }

    public String getMade_in_country() {
        return made_in_country;
    }

    public void setMade_in_country(String made_in_country) {
        this.made_in_country = made_in_country;
    }

    public String getProduct_rating() {
        return product_rating;
    }

    public void setProduct_rating(String product_rating) {
        this.product_rating = product_rating;
    }

    public String getRegional_speciality() {
        return regional_speciality;
    }

    public void setRegional_speciality(String regional_speciality) {
        this.regional_speciality = regional_speciality;
    }

    public String getMax_self_life() {
        return max_self_life;
    }

    public void setMax_self_life(String max_self_life) {
        this.max_self_life = max_self_life;
    }

    public String getOrganic() {
        return organic;
    }

    public void setOrganic(String organic) {
        this.organic = organic;
    }

    public String getFood_preference() {
        return food_preference;
    }

    public void setFood_preference(String food_preference) {
        this.food_preference = food_preference;
    }

    public String getProduct_type() {
        return product_type;
    }

    public void setProduct_type(String product_type) {
        this.product_type = product_type;
    }

    public String getProduct_taste() {
        return product_taste;
    }

    public void setProduct_taste(String product_taste) {
        this.product_taste = product_taste;
    }

    public String getProduct_franchise() {
        return product_franchise;
    }

    public void setProduct_franchise(String product_franchise) {
        this.product_franchise = product_franchise;
    }

    public String product_franchise;
}
