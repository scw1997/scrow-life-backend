package com.scw.scrowlifebackend.mapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.scw.scrowlifebackend.mojo.User;
import org.apache.ibatis.annotations.Mapper;
@Mapper
public interface UserMapper extends BaseMapper<User> {
}
