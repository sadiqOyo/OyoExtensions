package com.oyo.oyoExt.Entities;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.google.gson.JsonObject;
import com.oyo.oyoExt.Request.Products;
import com.oyo.paymentgatewayscommon.utilities.JSONBUserType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.UpdateTimestamp;
import org.json.simple.JSONObject;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@Getter
@Setter
@Entity
@Table(name = "orders")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@org.hibernate.annotations.TypeDef(name = "JSONBUserType", typeClass = JSONBUserType.class)
public class OrderEntity {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String bookingId;

    private String orderId;

    private String userProfileId;

    private Boolean isPaid;

    private Long totalAmount;

    @Column(nullable = true)
    private String products;

    private String Category;

    @Column(nullable = true)
    private Date createdAt;

    @UpdateTimestamp
    @Column(nullable = true)
    private Date updatedAt;




}
