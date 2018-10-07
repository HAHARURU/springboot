package com.haru.mybatis.pattern.prototype;

/**
 * @author HARU
 * @since 2018/10/6
 */
public class Order implements Cloneable {
    private String no;
    private Attachment attachment;

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }

    public Attachment getAttachment() {
        return attachment;
    }

    public void setAttachment(Attachment attachment) {
        this.attachment = attachment;
    }

    public Order clone() {
        Object object = null;
        try {
            object = super.clone();
            return (Order) object;
        } catch (CloneNotSupportedException e) {
            System.out.println("clone error");
            return null;
        }
    }
}
