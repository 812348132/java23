package com.kaishengit.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.CacheNamespace;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Options.FlushCachePolicy;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.kaishengit.entity.User;


@CacheNamespace()
public interface UserMapper {

	@Select("select * from t_user where id = #{id}")
	@Options(useCache=false)
	User findById(Integer id);
	
	@Select("select * from t_user")
	List<User> findAll();
	
	@Insert("insert into t_user (user_name,password,address) values(#{userName},#{password},#{address})")
    @Options(useGeneratedKeys=true,keyProperty="id",flushCache=FlushCachePolicy.FALSE)
	void save(User user);
	
	User findByIdAndPassword(@Param("userName")String name,@Param("address")String address);
    User findByMapParam(Map<String,Object> params);
	
	List<User> findAllLoadDept();

    List<User> searchByNameAndAddress(Map<String,Object> params);

    void batchSave(List<User> userList);
    void delByIds(@Param("list") List<Integer> idList);

}
