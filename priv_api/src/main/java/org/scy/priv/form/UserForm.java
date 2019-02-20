package org.scy.priv.form;

/**
 * 用户
 * Created by shicy 2019-02-19
 */
public class UserForm {

    // 编码
    private String code;

    // 编码模糊
    private String codeLike;

    // 名称
    private String name;

    // 名称模糊
    private String nameLike;

    // 手机号码
    private String mobile;

    // 邮箱
    private String email;

    // 类型，多个类型逗号分隔
    private String type;

    // 所属分组，多个分组逗号分隔
    private String groupId;

    // 所属角色，多个角色逗号分隔
    private String roleId;

    private int page;

    private int limit;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCodeLike() {
        return codeLike;
    }

    public void setCodeLike(String codeLike) {
        this.codeLike = codeLike;
    }

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

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
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
