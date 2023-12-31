package com.coco.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QTrainReservation is a Querydsl query type for TrainReservation
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QTrainReservation extends EntityPathBase<TrainReservation> {

    private static final long serialVersionUID = 1116394469L;

    public static final QTrainReservation trainReservation = new QTrainReservation("trainReservation");

    public final NumberPath<Long> adultcharge = createNumber("adultcharge", Long.class);

    public final StringPath arrplacename = createString("arrplacename");

    public final DateTimePath<java.time.LocalDateTime> arrplandtime = createDateTime("arrplandtime", java.time.LocalDateTime.class);

    public final StringPath depplacename = createString("depplacename");

    public final DateTimePath<java.time.LocalDateTime> depplandtime = createDateTime("depplandtime", java.time.LocalDateTime.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath orderId = createString("orderId");

    public final StringPath paymentKey = createString("paymentKey");

    public final StringPath traingradename = createString("traingradename");

    public final NumberPath<Long> trainNo = createNumber("trainNo", Long.class);

    public QTrainReservation(String variable) {
        super(TrainReservation.class, forVariable(variable));
    }

    public QTrainReservation(Path<? extends TrainReservation> path) {
        super(path.getType(), path.getMetadata());
    }

    public QTrainReservation(PathMetadata metadata) {
        super(TrainReservation.class, metadata);
    }

}

