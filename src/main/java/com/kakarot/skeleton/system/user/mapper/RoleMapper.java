package com.kakarot.skeleton.system.user.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.Set;

@Mapper
public interface RoleMapper {

    @Select("""
            select r.role_code
            from sys_role r 
            join  sys_user_role ur on ur.role_id = r.id
            where ur.user_id = #{userId}
            and r.status = 1
            """)
    Set<String> findRoleCodesByUserId(Long userId);




}
