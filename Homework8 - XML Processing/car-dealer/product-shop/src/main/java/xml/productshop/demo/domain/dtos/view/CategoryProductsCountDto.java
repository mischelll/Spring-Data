package xml.productshop.demo.domain.dtos.view;

import javax.xml.bind.annotation.*;
import java.math.BigDecimal;

@XmlRootElement(name = "categories")
@XmlAccessorType(XmlAccessType.FIELD)
public class CategoryProductsCountDto {

    @XmlAttribute(name = "name")
    private String categoryName;
    @XmlElement(name = "products-count")
    private int productsCount;
    @XmlElement(name = "average-price")
    private Double averagePRice;
    @XmlElement(name = "total-revenue")
    private BigDecimal totalRevenue;

    public CategoryProductsCountDto() {
    }

    public CategoryProductsCountDto(String categoryName, int productsCount, Double averagePRice, BigDecimal totalRevenue) {
        this.categoryName = categoryName;
        this.productsCount = productsCount;
        this.averagePRice = averagePRice;
        this.totalRevenue = totalRevenue;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public Integer getProductsCount() {
        return productsCount;
    }

    public void setProductsCount(Integer productsCount) {
        this.productsCount = productsCount;
    }

    public void setProductsCount(int productsCount) {
        this.productsCount = productsCount;
    }

    public Double getAveragePRice() {
        return averagePRice;
    }

    public void setAveragePRice(Double averagePRice) {
        this.averagePRice = averagePRice;
    }

    public BigDecimal getTotalRevenue() {
        return totalRevenue;
    }

    public void setTotalRevenue(BigDecimal totalRevenue) {
        this.totalRevenue = totalRevenue;
    }
}
