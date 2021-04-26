package in.amruthashala.momapp.inputresponsemodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ProductVariant {

    public String productCertificate;

    public Integer productStock;

    public String productQuantity;

    public Integer productPriceWeight;

    public Integer productPricePiece;

    public Integer discountOffer;

    public String productContainer;

    public String status;

    public String preservationProcess;

    public String manufactureDate;

    public String productHsn;

    public String productTax;

    public String productEanOrUpc;
    public ProductVariant(String productCertificate, Integer productStock, String productQuantity, Integer productPriceWeight, Integer productPricePiece, Integer discountOffer, String productContainer, String status, String preservationProcess, String manufactureDate, String productHsn, String productTax, String productEanOrUpc) {
        this.productCertificate = productCertificate;
        this.productStock = productStock;
        this.productQuantity = productQuantity;
        this.productPriceWeight = productPriceWeight;
        this.productPricePiece = productPricePiece;
        this.discountOffer = discountOffer;
        this.productContainer = productContainer;
        this.status = status;
        this.preservationProcess = preservationProcess;
        this.manufactureDate = manufactureDate;
        this.productHsn = productHsn;
        this.productTax = productTax;
        this.productEanOrUpc = productEanOrUpc;
    }

    public String getProductCertificate() {
        return productCertificate;
    }

    public void setProductCertificate(String productCertificate) {
        this.productCertificate = productCertificate;
    }

    public Integer getProductStock() {
        return productStock;
    }

    public void setProductStock(Integer productStock) {
        this.productStock = productStock;
    }

    public String getProductQuantity() {
        return productQuantity;
    }

    public void setProductQuantity(String productQuantity) {
        this.productQuantity = productQuantity;
    }

    public Integer getProductPriceWeight() {
        return productPriceWeight;
    }

    public void setProductPriceWeight(Integer productPriceWeight) {
        this.productPriceWeight = productPriceWeight;
    }

    public Integer getProductPricePiece() {
        return productPricePiece;
    }

    public void setProductPricePiece(Integer productPricePiece) {
        this.productPricePiece = productPricePiece;
    }

    public Integer getDiscountOffer() {
        return discountOffer;
    }

    public void setDiscountOffer(Integer discountOffer) {
        this.discountOffer = discountOffer;
    }

    public String getProductContainer() {
        return productContainer;
    }

    public void setProductContainer(String productContainer) {
        this.productContainer = productContainer;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPreservationProcess() {
        return preservationProcess;
    }

    public void setPreservationProcess(String preservationProcess) {
        this.preservationProcess = preservationProcess;
    }

    public String getManufactureDate() {
        return manufactureDate;
    }

    public void setManufactureDate(String manufactureDate) {
        this.manufactureDate = manufactureDate;
    }

    public String getProductHsn() {
        return productHsn;
    }

    public void setProductHsn(String productHsn) {
        this.productHsn = productHsn;
    }

    public String getProductTax() {
        return productTax;
    }

    public void setProductTax(String productTax) {
        this.productTax = productTax;
    }

    public String getProductEanOrUpc() {
        return productEanOrUpc;
    }

    public void setProductEanOrUpc(String productEanOrUpc) {
        this.productEanOrUpc = productEanOrUpc;
    }
}
