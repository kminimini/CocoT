package com.coco.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QMember is a Querydsl query type for Member
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QMember extends EntityPathBase<Member> {

    private static final long serialVersionUID = -909547749L;

    public static final QMember member = new QMember("member1");

    public final StringPath address = createString("address");

    public final ListPath<Board, QBoard> boardList = this.<Board, QBoard>createList("boardList", Board.class, QBoard.class, PathInits.DIRECT2);

    public final StringPath detailAddress = createString("detailAddress");

    public final StringPath email = createString("email");

    public final StringPath enabled = createString("enabled");

    public final StringPath ename = createString("ename");

    public final StringPath id = createString("id");

    public final NumberPath<Long> mid = createNumber("mid", Long.class);

    public final StringPath name = createString("name");

    public final StringPath password = createString("password");

    public final StringPath phone = createString("phone");

    public final StringPath provider = createString("provider");

    public final EnumPath<Role> role = createEnum("role", Role.class);

    public final StringPath rrnumber = createString("rrnumber");

    public QMember(String variable) {
        super(Member.class, forVariable(variable));
    }

    public QMember(Path<? extends Member> path) {
        super(path.getType(), path.getMetadata());
    }

    public QMember(PathMetadata metadata) {
        super(Member.class, metadata);
    }

}

