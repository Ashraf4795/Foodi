package com.city.coding.restaurant3.Model;

public class voucherData {

    private String title, qty ,value ,voucherId;
    private String voucherImage;

    public voucherData(String title, String qty, String value, String voucherImage ,String voucherId) {
        this.title = title;
        this.qty = qty;
        this.value = value;
        this.voucherImage = voucherImage;
        this.voucherId = voucherId;
    }

    public void setVoucherId(String voucherId) {
        this.voucherId = voucherId;
    }

    public String getVoucherId() {
        return voucherId;
    }

    public String getTitle() {
        return title;
    }

    public String getQty() {
        return qty;
    }

    public String getValue() {
        return value;
    }

    public String getVoucherImage() {
        return voucherImage;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public void setVoucherImage(String voucherImage) {
        this.voucherImage = voucherImage;
    }
}
