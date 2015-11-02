package com.sanyo.quote.domain;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "tax_usd_price", catalog = "sanyo")
public class TaxUsdPrice extends Price{

}
