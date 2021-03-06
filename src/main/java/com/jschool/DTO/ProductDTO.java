package com.jschool.DTO;

public class ProductDTO {
    private Long id;
    private String title;
    private Float price;
    private String category;
    private String brand;
    private String color;
    private Integer quantity;
    private String imgName;

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getImgName() {
        return imgName;
    }

    public void setImgName(String imgName) {
        this.imgName = imgName;
    }

    public boolean equals(Object object){
        if (this==object)
            return true;
        if(getClass()!=object.getClass())
            return false;
        if(object == null)
            return false;
        ProductDTO productDTO = (ProductDTO) object;
        if (((ProductDTO) object).getId() == null)
            if (this.id!=null)
                return false;
        if(!this.id.equals(productDTO.getId()))
            return false;
        return true;
    }
}
