package com.kankan.dao.mapper;

import com.kankan.dao.entity.UserEntity;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

@Service
public class UserMapperImpl implements UserMapper {

  @Autowired
  private MongoTemplate mongoTemplate;
  private Class<UserEntity> myClass = UserEntity.class;

  @Override
  public UserEntity findUserByEmail(String userEmail) {
//   <select id="findUserByEmail" resultType="UserEntity">
//        select * from user_info where user_email=#{userEmail}
//    </select>

    Query query = Query.query(Criteria.where("userEmail").is(userEmail));
    return mongoTemplate.findOne(query, myClass);
  }

  @Override
  public void createUser(UserEntity entity) {
    mongoTemplate.insert(entity);
  }

  @Override
  public void registerUser(UserEntity entity) {
//    update user_info set username=#{username}, password=#{password},  update_time=#{updateTime} where id=#{id}
    Query query = Query.query(Criteria.where("id").is(entity.getId()));
    Update update = Update.update("username", entity.getUsername()).set("password", entity.getPassword());
    mongoTemplate.updateFirst(query, update, myClass);
  }


  @Override
  public void updateUser(UserEntity entity) {

//
//    update user_info set update_time=#{updateTime}
//        <if test="username!=null">
//        , username=#{username}
//        </if>
//
//        <if test="password!=null">
//        ,password=#{password}
//        </if>
//        <if test="userEmail!=null">
//        ,user_email=#{userEmail}
//        </if>
//        <if test="userPhoto!=null">
//        , user_photo=#{userPhoto}
//        </if>
//    where id=#{id}
//

    Update update = new Update();

    String username = entity.getUsername();
    String password = entity.getPassword();
    String userEmail = entity.getUserEmail();
    String userPhoto = entity.getUserPhoto();

    if (StringUtils.isNotEmpty(username)) {
      update.set("username", username);
    }
    if (StringUtils.isNotEmpty(password)) {
      update.set("password", password);
    }
    if (StringUtils.isNotEmpty(userEmail)) {
      update.set("userEmail", userEmail);
    }
    if (StringUtils.isNotEmpty(userPhoto)) {
      update.set("userPhoto", userPhoto);
    }
    Query query = Query.query(Criteria.where("id").is(entity.getId()));
    mongoTemplate.updateFirst(query, update, myClass);
  }

  @Override
  public List<UserEntity> findUser(UserEntity userEntity) {

//    select * from user_info
//        <where>
//        status=1
//        <if test="username!=null">
//        or username like concat('%',username,'%')
//        </if>
//       <if test="userEmail!=null">
//        or user_email like concat('%',userEmail,'%')
//        </if>
//     </where>

    String username = userEntity.getUsername();
    String userEmail = userEntity.getUserEmail();
    List<Criteria> criteriaList = new ArrayList<>(2);

    if (StringUtils.isNotEmpty(userEmail)) {
      criteriaList.add(Criteria.where("userEmail").is("/"+userEmail+"/"));
    }
    if (StringUtils.isNotEmpty(username)) {
      criteriaList.add(Criteria.where("username").is("/"+username+"/"));
    }

    Query query = Query.query(Criteria.where("status").is(1)
        .orOperator(
            criteriaList.toArray(new Criteria[]{})
        ));
    return mongoTemplate.find(query, myClass);
  }

  @Override
  public UserEntity findUserById(String userId) {
    Query query = Query.query(Criteria.where("id").is(userId));
    return mongoTemplate.findOne(query, myClass);
  }
}
