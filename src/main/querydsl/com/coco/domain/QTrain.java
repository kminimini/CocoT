package com.coco.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QTrain is a Querydsl query type for Train
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QTrain extends EntityPathBase<Train> {

    private static final long serialVersionUID = 670237031L;

    public static final QTrain train = new QTrain("train");

    public final NumberPath<Long> adultcharge = createNumber("adultcharge", Long.class);

    public final StringPath arrplacename = createString("arrplacename");

    public final DateTimePath<java.time.LocalDateTime> arrplandtime = createDateTime("arrplandtime", java.time.LocalDateTime.class);

    public final StringPath depplacename = createString("depplacename");

    public final DateTimePath<java.time.LocalDateTime> depplandtime = createDateTime("depplandtime", java.time.LocalDateTime.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath orderId = createString("orderId");

    public final StringPath traingradename = createString("traingradename");

    public final NumberPath<Long> trainNo = createNumber("trainNo", Long.class);

    public QTrain(String variable) {
        super(Train.class, forVariable(variable));
    }

    public QTrain(Path<? extends Train> path) {
        super(path.getType(), path.getMetadata());
    }

    public QTrain(PathMetadata metadata) {
        super(Train.class, metadata);
    }

}

