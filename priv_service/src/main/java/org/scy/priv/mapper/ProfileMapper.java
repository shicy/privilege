package org.scy.priv.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.scy.priv.model.UserProfileModel;

import java.util.List;

/**
 * 用户属性
 * Created by shicy on 2020/01/22
 */
@Mapper
@SuppressWarnings("unused")
public interface ProfileMapper {

    UserProfileModel getById(@Param("id") int id);
    UserProfileModel getProfile(@Param("userId") int userId, @Param("name") String name);

    List<UserProfileModel> getProfilesAll(@Param("userId") int userId, @Param("paasId") int paasId);
    List<UserProfileModel> getProfilesLike(@Param("userId") int userId, @Param("nameLike") String nameLike,
        @Param("paasId") int paasId);
    List<UserProfileModel> getProfilesIn(@Param("userId") int userId, @Param("names") String names,
        @Param("paasId") int paasId);

    void add(UserProfileModel profile);
    void update(UserProfileModel profile);

    void deleteByUserId(@Param("userId") int userId, @Param("paasId") int paasId);
    void deleteByName(@Param("userId") int userId, @Param("name") String name, @Param("paasId") int paasId);
    void deleteByNames(@Param("userId") int userId, @Param("names") String name, @Param("paasId") int paasId);
    void deleteLikeName(@Param("userId") int userId, @Param("nameLike") String nameLike,
        @Param("paasId") int paasId);

}
