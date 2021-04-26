package in.amruthashala.momapp.requestmodel;

import java.io.Serializable;

public class GetProductModel implements Serializable {

    String productid,productname,productfranchise,productstock,productprice,
            productstatus;
    public GetProductModel(String productid,String productname, String productfranchise, String productstock, String productprice, String productstatus){
        this.productid=productid;
        this.productname=productname;
        this.productfranchise=productfranchise;
        this.productstock=productstock;
        this.productprice=productprice;
        this.productstatus=productstatus;
    }

    public String getProductid() {
        return productid;
    }

    public void setProductid(String productid) {
        this.productid = productid;
    }

    public String getProductname() {
        return productname;
    }

    public void setProductname(String productname) {
        this.productname = productname;
    }

    public String getProductfranchise() {
        return productfranchise;
    }

    public void setProductfranchise(String productfranchise) {
        this.productfranchise = productfranchise;
    }

    public String getProductstock() {
        return productstock;
    }

    public void setProductstock(String productstock) {
        this.productstock = productstock;
    }

    public String getProductprice() {
        return productprice;
    }

    public void setProductprice(String productprice) {
        this.productprice = productprice;
    }

    public String getProductstatus() {
        return productstatus;
    }

    public void setProductstatus(String productstatus) {
        this.productstatus = productstatus;
    }
}
