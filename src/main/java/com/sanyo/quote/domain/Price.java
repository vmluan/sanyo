package com.sanyo.quote.domain;

import javax.persistence.*;

import org.codehaus.jackson.annotate.JsonIgnore;

import java.util.Date;

/**
 * Created by User on 10/9/2015.
 */
//@Entity
//@Table(name = "price", catalog = "sanyo")
@MappedSuperclass
public class Price {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PRICE_ID", nullable = false)
    private int priceId;

	@JsonIgnore
	@ManyToOne(cascade = CascadeType.MERGE)
	@JoinColumn(name = "PRODUCT_ID", nullable = false)
    private Product product;

    @Column(name = "ISSUED_DATE", nullable = false)
    private Date issuedDate; //Ngay bat dau co hieu luc

    @Column(name = "EXPIRED_DATE", nullable = true)
    private Date expiredDate; //Ngay het hieu luc

    @Column(name ="IN_PRICE")
    private long inPrice;

    @Column(name ="OUT_WHLSE_PRICE")
    private long outWholeSalePrice;

    @Column(name ="OUT_SALE_PRICE")
    private float outSalePrice;

    @Column(name ="PRICE_TYPE")
    private PriceType priceType;

    public int getPriceId() {
        return priceId;
    }

    public void setPriceId(int priceId) {
        this.priceId = priceId;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Date getIssuedDate() {
        return issuedDate;
    }

    public void setIssuedDate(Date issuedDate) {
        this.issuedDate = issuedDate;
    }

    public Date getExpiredDate() {
        return expiredDate;
    }

    public void setExpiredDate(Date expiredDate) {
        this.expiredDate = expiredDate;
    }

    public long getInPrice() {
        return inPrice;
    }

    public void setInPrice(long inPrice) {
        this.inPrice = inPrice;
    }

    public long getOutWholeSalePrice() {
        return outWholeSalePrice;
    }

    public void setOutWholeSalePrice(long outWholeSalePrice) {
        this.outWholeSalePrice = outWholeSalePrice;
    }

    public float getOutSalePrice() {
        return outSalePrice;
    }

    public void setOutSalePrice(float outSalePrice) {
        this.outSalePrice = outSalePrice;
    }

	public PriceType getPriceType() {
		return priceType;
	}

	public void setPriceType(PriceType priceType) {
		this.priceType = priceType;
	}
    
}
