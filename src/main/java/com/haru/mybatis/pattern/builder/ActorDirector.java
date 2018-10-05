package com.haru.mybatis.pattern.builder;

/**
 * @author HARU
 * @since 2018/10/5
 */
public class ActorDirector {
    public Actor createActor(ActorBuilder actorBuilder) {
        actorBuilder.buildRole();
        actorBuilder.buildSex();
        actorBuilder.buildFigure();
        return actorBuilder.getActor();
    }
}
