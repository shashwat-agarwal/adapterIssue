package agarwal.shashwat.ecommerce.data;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "product_table")
public class Product {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String productImage;
    private String productName;
    private String productQuantity;
    private long  productPrice;
    private long userPrice;
    private long userQuantity;

    public Product(){
    }

    public Product(String productImage,String productName, String productQuantity, long productPrice,long userPrice,long userQuantity) {
        this.productImage=productImage;
        this.productName = productName;
        this.productQuantity = productQuantity;
        this.productPrice = productPrice;
        this.userPrice=userPrice;
        this.userQuantity=userQuantity;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductQuantity() {
        return productQuantity;
    }

    public void setProductQuantity(String productQuantity) {
        this.productQuantity = productQuantity;
    }

    public long getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(long productPrice) {
        this.productPrice = productPrice;
    }

    public String getProductImage() {
        return productImage;
    }

    public void setProductImage(String productImage) {
        this.productImage = productImage;
    }

    public void setUserPrice(long userPrice) {
        this.userPrice = userPrice;
    }

    public long getUserPrice() {
        return userPrice;
    }

    public long getUserQuantity() {
        return userQuantity;
    }

    public void setUserQuantity(long userQuantity) {
        this.userQuantity = userQuantity;
    }
}
