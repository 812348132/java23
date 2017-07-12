package com.kaishengit.dao;

import com.kaishengit.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by zjs on 2017/7/7.
 */

@Service
public class UserDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void save(User user){
        String sql = "insert into t_user (user_name,address,password,dept_id) values (?,?,?,?)";
        jdbcTemplate.update(sql,user.getUser_name(),user.getAddress(),user.getPassword(),user.getDept_id());
    }

    public User findById(Integer id){
        String sql = "select * from t_user where id = ?";
       return jdbcTemplate.queryForObject(sql,new UserMapper(),id);
    }

    public List<User> findAll(){
        String sql = "select * from t_user";
        return jdbcTemplate.query(sql,new BeanPropertyRowMapper<>(User.class));
    }

    public Long count(){
        String sql = "select count(*) from t_user";
        return jdbcTemplate.queryForObject(sql,Long.class);
    }

    private class UserMapper implements RowMapper<User>{

        @Override
        public User mapRow(ResultSet resultSet, int i) throws SQLException {
            User user = new User();
            user.setId(resultSet.getInt("id"));
            user.setUser_name(resultSet.getString("user_name"));
            user.setAddress(resultSet.getString("address"));
            user.setPassword(resultSet.getString("password"));
            user.setDept_id(resultSet.getInt("dept_id"));
            return user;
        }
    }



   /* public void init(){
        System.out.println("init .....");
    }

    public UserDao(){
        System.out.println("create user...");
    }*/

  /*  public void save() {
        System.out.println("save .....");
    }*/

   /* public void destory(){
        System.out.println("destory...");
    }*/
}
