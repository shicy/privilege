package org.scy.priv.form;

import org.scy.common.utils.ArrayUtilsEx;

/**
 * 用户属性查询
 * Create by shicy 2020/01/23
 */
public class ProfileForm {

    private String nameLike;
    private String names;

    public String getNameLike() {
        return nameLike;
    }

    public void setNameLike(String nameLike) {
        this.nameLike = nameLike;
    }

    public String getNames() {
        return names;
    }

    public void setNames(String names) {
        this.names = names;
    }

    public void setNames(String[] names) {
        this.names = ArrayUtilsEx.join(names, ",");
    }

}
