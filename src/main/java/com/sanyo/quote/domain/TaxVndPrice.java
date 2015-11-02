package com.sanyo.quote.domain;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "tax_vnd_price", catalog = "sanyo")
public class TaxVndPrice extends Price{

}
