package com.coco.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QTrain is a Querydsl query type for Train
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QTrain extends EntityPathBase<Train> {

    private static final long serialVersionUID = 670237031L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QTrain train = new QTrain("train");

    public final StringPath arrPlace = createString("arrPlace");

    public final DateTimePath<java.util.Date> arrPlandTime = createDateTime("arrPlandTime", java.util.Date.class);

    public final StringPath depPlaced = createString("depPlaced");

    public final DateTimePath<java.util.Date> depPlandTime = createDateTime("depPlandTime", java.util.Date.class);

    public final DateTimePath<java.util.Date> depSelectTime = createDateTime("depSelectTime", java.util.Date.class);

    public final NumberPath<Integer> economyCharge = createNumber("economyCharge", Integer.class);

    public final StringPath endStationName = createString("endStationName");

    public final QMember member;

    public final NumberPath<Integer> pageNo = createNumber("pageNo", Integer.class);

    public final NumberPath<Integer> prestigeCharge = createNumber("prestigeCharge", Integer.class);

    public final StringPath startStationName = createString("startStationName");

    public final NumberPath<Integer> totalCount = createNumber("totalCount", Integer.class);

    public final StringPath trainName = createString("trainName");

    public final NumberPath<Integer> trainNumber = createNumber("trainNumber", Integer.class);

    public QTrain(String variable) {
        this(Train.class, forVariable(variable), INITS);
    }

    public QTrain(Path<? extends Train> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QTrain(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QTrain(PathMetadata metadata, PathInits inits) {
        this(Train.class, metadata, inits);
    }

    public QTrain(Class<? extends Train> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.member = inits.isInitialized("member") ? new QMember(forProperty("member")) : null;
    }

}

