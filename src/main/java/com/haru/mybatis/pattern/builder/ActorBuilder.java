package com.haru.mybatis.pattern.builder;

/**
 * @author HARU
 * @since 2018/10/5
 */
public abstract class ActorBuilder {
    Actor actor;

    public ActorBuilder() {
        this.actor = new Actor();
    }

    public abstract void buildRole();
    public abstract void buildSex();
    public abstract void buildFigure();

    public Actor getActor() {
        return actor;
    }
}
