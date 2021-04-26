package in.amruthashala.momapp.inputresponsemodel;

import java.util.List;

public class AddNewProductResponse {
    public String type;
    public List<String> product_category_id;
    public List<String> product_subcategory_id;
    public String product_name;
    public String product_type;
    public String product_description;
    public String seller_id;
    public List<String> franchise_id;
    public String brand_id;
   /* public List<ProductVariant> product_variants;
    public List<ProductFilter> product_filters;*/
    public DeliveryCharge delivery_charge;
    public NutritionalFacts nutritional_facts;
    public Ingredents ingredents;
    public List<String> product_images;
    public String video_url;
    public String product_status;
    public List<String> search_keyword;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<String> getProduct_category_id() {
        return product_category_id;
    }

    public void setProduct_category_id(List<String> product_category_id) {
        this.product_category_id = product_category_id;
    }

    public List<String> getProduct_subcategory_id() {
        return product_subcategory_id;
    }

    public void setProduct_subcategory_id(List<String> product_subcategory_id) {
        this.product_subcategory_id = product_subcategory_id;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public String getProduct_type() {
        return product_type;
    }

    public void setProduct_type(String product_type) {
        this.product_type = product_type;
    }

    public String getProduct_description() {
        return product_description;
    }

    public void setProduct_description(String product_description) {
        this.product_description = product_description;
    }

    public String getSeller_id() {
        return seller_id;
    }

    public void setSeller_id(String seller_id) {
        this.seller_id = seller_id;
    }

    public List<String> getFranchise_id() {
        return franchise_id;
    }

    public void setFranchise_id(List<String> franchise_id) {
        this.franchise_id = franchise_id;
    }

    public String getBrand_id() {
        return brand_id;
    }

    public void setBrand_id(String brand_id) {
        this.brand_id = brand_id;
    }

 /*   public List<ProductVariant> getProduct_variants() {
        return product_variants;
    }

    public void setProduct_variants(List<ProductVariant> product_variants) {
        this.product_variants = product_variants;
    }

    public List<ProductFilter> getProduct_filters() {
        return product_filters;
    }

    public void setProduct_filters(List<ProductFilter> product_filters) {
        this.product_filters = product_filters;
    }
*/
    public DeliveryCharge getDelivery_charge() {
        return delivery_charge;
    }

    public void setDelivery_charge(DeliveryCharge delivery_charge) {
        this.delivery_charge = delivery_charge;
    }

    public NutritionalFacts getNutritional_facts() {
        return nutritional_facts;
    }

    public void setNutritional_facts(NutritionalFacts nutritional_facts) {
        this.nutritional_facts = nutritional_facts;
    }

    public Ingredents getIngredents() {
        return ingredents;
    }

    public void setIngredents(Ingredents ingredents) {
        this.ingredents = ingredents;
    }

    public List<String> getProduct_images() {
        return product_images;
    }

    public void setProduct_images(List<String> product_images) {
        this.product_images = product_images;
    }

    public String getVideo_url() {
        return video_url;
    }

    public void setVideo_url(String video_url) {
        this.video_url = video_url;
    }

    public String getProduct_status() {
        return product_status;
    }

    public void setProduct_status(String product_status) {
        this.product_status = product_status;
    }

    public List<String> getSearch_keyword() {
        return search_keyword;
    }

    public void setSearch_keyword(List<String> search_keyword) {
        this.search_keyword = search_keyword;
    }
}
