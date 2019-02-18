package org.scy.priv.form;

/**
 * 用户组
 * Created by shicy on 2019/2/18
 */
public class GroupForm {

    // 名称
    private String name;

    // 名称模糊
    private String nameLike;

    // 所属用户编号
    private int userId;

    private int page;

    private int limit;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNameLike() {
        return nameLike;
    }

    public void setNameLike(String nameLike) {
        this.nameLike = nameLike;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }
}
