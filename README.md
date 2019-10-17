## 授权系统

### 客户端
* org.scy.priv.PrivilegeClient
* org.scy.priv.PrivilegeClientAdapter

### API
* getAccountInfo(int accountId)
* findAccount(AccountForm account)
* addAccount(Account account)
* setAccount(Account account)
* deleteAccount(int accountId)
* makeAccountSecret(int accountId)
* setAccountState(int accountId, short state)
* findGroup(GroupForm group)
* getUserGroups(int userId)
* addGroup(Group group)
* setGroup(Group group)
* deleteGroup(int groupId)
* addGroupUser(int groupId, int userId)
* addGroupUser(int groupId, String userIds)
* addGroupUser(int groupId, int[] userIds)
* deleteGroupUser(int groupId, int userId)
* deleteGroupUser(int groupId, String userIds)
* deleteGroupUser(int groupId, int[] userIds)
* deleteAllGroupUsers(int groupId)
* findRole(RoleForm role)
* getUserRoles(int userId)
* addRole(Role role)
* setRole(Role role)
* deleteRole(int roleId)
* addRoleUser(int roleId, int userId)
* addRoleUser(int roleId, String userIds)
* addRoleUser(int roleId, int[] userIds)
* deleteRoleUser(int roleId, int userId)
* deleteRoleUser(int roleId, String userIds)
* deleteRoleUser(int roleId, int[] userIds)
* deleteAllRoleUsers(int roleId)
* findUser(UserForm user)
* addUser(User user, int groupId, int roleId)
* addUser(User user, String groupIds, String roleIds)
* addUser(User user, int[] groupIds, int[] roleIds)
* setUser(User user)
* deleteUser(int userId)
* setUserPassword(int userId, String password, String oldPassword)
* setUserState(int userId, short state)
* setUserGroups(int userId, int[] groupIds)
* setUserGroups(int userId, String groupIds)
* setUserRoles(int userId, int[] roleIds)
* setUserRoles(int userId, String roleIds)
* findModule(ModuleForm module)
* getUserModules(int userId)
* addModule(Module module)
* setModule(Module module)
* deleteModule(int moduleId, boolean force)
* getUserPrivileges(int userId)
* getUserPrivilegesAll(int userId)
* getGroupPrivileges(int groupId)
* getRolePrivileges(int roleId)
* addPrivilege(Privilege privilege)
* addPrivileges(Privilege[] privileges)
* deletePrivileges(Privilege privilege)
* deletePrivileges(Privilege[] privileges)
* setUserPrivilege(int userId, int moduleId, int grantType)
* setUserPrivilege(int userId, Privilege privilege)
* setUserPrivileges(int userId, Privilege[] privileges)
* setGroupPrivilege(int groupId, int moduleId, int grantType)
* setGroupPrivilege(int groupId, Privilege privilege)
* setGroupPrivileges(int groupId, Privilege[] privileges)
* setRolePrivilege(int roleId, int moduleId, int grantType)
* setRolePrivilege(int roleId, Privilege privilege)
* setRolePrivileges(int roleId, Privilege[] privileges)
* checkUserModules(int userId, String moduleIds, String moduleCodes, String moduleNames)
* checkUserModules(int userId, String[] moduleIds, String[] moduleCodes, String[] moduleNames)
* getUserGrantType(int userId, String moduleCode)

### 配置项
* 登录地址：app.loginUrl (默认：`/login`)
* Session服务器：app.session-service.url
* 缓存服务器：app.cache-service.url

### 启动服务
运行 org.scy.priv.App

需要引用`jcoms`、`cache_api`

### 打包发布
mvn clean package -f pom.xml -P prod   
IDEL 配置选项 Profiles 为 prod

### 部署
cd ../privilege/priv_service/target   
scp privilege.war root@47.111.123.77:/mnt/service/privilege.war
