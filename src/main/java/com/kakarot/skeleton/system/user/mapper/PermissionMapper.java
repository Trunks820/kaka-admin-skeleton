package com.kakarot.skeleton.system.user.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.Set;

@Mapper
public interface PermissionMapper {


    @Select("""
            select DISTINCT  p.perm_code
            from sys_permission p 
            join sys_role_permission rp on rp.permission_id = p.id
            join sys_user_role ur on  ur.role_id = rp.role_id
            join sys_role r on r.id = ur.role_id
            where ur.user_id = #{userId}
            and p.status = 1
            and r.status = 1
            """)
    Set<String> findPermCodesByUserId(@Param("userId")Long userId);


}
