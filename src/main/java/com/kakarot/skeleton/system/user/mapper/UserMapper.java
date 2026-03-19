package com.kakarot.skeleton.system.user.mapper;

import com.kakarot.skeleton.system.user.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper {

    @Select("""
            select id, username, password, nickname, status, del_flag, create_time, create_by, update_time, update_by
            from sys_user
            where username = #{username}
            """)
    User findByUserName(String username);


}
