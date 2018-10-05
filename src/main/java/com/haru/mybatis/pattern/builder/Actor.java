package com.haru.mybatis.pattern.builder;

/**
 * @author HARU
 * @since 2018/10/5
 */
public class Actor {
    private String role;
    private String sex;
    private String figure;

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getFigure() {
        return figure;
    }

    public void setFigure(String figure) {
        this.figure = figure;
    }

    @Override
    public String toString() {
        return "Actor{" +
                "role='" + role + '\'' +
                ", sex='" + sex + '\'' +
                ", figure='" + figure + '\'' +
                '}';
    }
}
