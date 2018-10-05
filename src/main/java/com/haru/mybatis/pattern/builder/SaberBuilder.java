package com.haru.mybatis.pattern.builder;

/**
 * @author HARU
 * @since 2018/10/5
 */
public class SaberBuilder extends ActorBuilder {
    @Override
    public void buildRole() {
        actor.setRole("剑士");
    }

    @Override
    public void buildSex() {
        actor.setSex("妹子");
    }

    @Override
    public void buildFigure() {
        actor.setFigure("苗条");
    }
}
