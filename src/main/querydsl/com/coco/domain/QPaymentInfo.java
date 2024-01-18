package com.coco.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QPaymentInfo is a Querydsl query type for PaymentInfo
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QPaymentInfo extends EntityPathBase<PaymentInfo> {

    private static final long serialVersionUID = 875455475L;

    public static final QPaymentInfo paymentInfo = new QPaymentInfo("paymentInfo");

    public final NumberPath<Long> amount = createNumber("amount", Long.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath orderId = createString("orderId");

    public final BooleanPath paymentConfirmed = createBoolean("paymentConfirmed");

    public final StringPath paymentKey = createString("paymentKey");

    public QPaymentInfo(String variable) {
        super(PaymentInfo.class, forVariable(variable));
    }

    public QPaymentInfo(Path<? extends PaymentInfo> path) {
        super(path.getType(), path.getMetadata());
    }

    public QPaymentInfo(PathMetadata metadata) {
        super(PaymentInfo.class, metadata);
    }

}

