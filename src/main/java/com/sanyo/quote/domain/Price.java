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

	@Temporal(TemporalType.DATE)
	@Column(name = "ISSUED_DATE", nullable = false)
    private Date issuedDate; //Ngay bat dau co hieu luc

	@Temporal(TemporalType.DATE)
	@Column(name = "EXPIRED_DATE", nullable = true)
    private Date expiredDate; //Ngay het hieu luc

    @Column(name ="IN_PRICE")
    private long inPrice = 0;

    @Column(name ="OUT_WHLSE_PRICE")
    private long outWholeSalePrice = 0;

    @Column(name ="OUT_SALE_PRICE")
    private float outSalePrice = 0;

    @Column(name ="PRICE_TYPE")
    private PriceType priceType;
    
    private Float max_w_o_tax_usd;
    private Float max_w_o_tax_vnd;
    private Float labour;

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

	public Float getMax_w_o_tax_usd() {
		return max_w_o_tax_usd;
	}

	public void setMax_w_o_tax_usd(Float max_w_o_tax_usd) {
		this.max_w_o_tax_usd = max_w_o_tax_usd;
	}

	public Float getMax_w_o_tax_vnd() {
		return max_w_o_tax_vnd;
	}

	public void setMax_w_o_tax_vnd(Float max_w_o_tax_vnd) {
		this.max_w_o_tax_vnd = max_w_o_tax_vnd;
	}

	public Float getLabour() {
		return labour;
	}

	public void setLabour(Float labour) {
		this.labour = labour;
	}
    
}
