package com.sanyo.quote.domain;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "labour_price", catalog = "sanyo")
public class LabourPrice extends Price{

}
