package com.kankan.api;

import static com.kankan.constant.ErrorCode.PARAM_CHECK_ERROR;

import com.kankan.constant.CommonResponse;
import com.kankan.constant.ErrorCode;

import com.kankan.dao.entity.KankanApply;
import com.kankan.module.privilege.UserPrivilege;
import com.kankan.param.KankanCompanyApply;
import lombok.extern.log4j.Log4j2;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@Log4j2
@RestControllerAdvice
public class BaseController {

  /**
   * 未知异常
   */
  @ExceptionHandler(Throwable.class)
  @ResponseBody
  public CommonResponse exception(Throwable throwable) {
    log.error("服务器未知异常", throwable);
    return CommonResponse.error(ErrorCode.UN_KNOW_ERROR, throwable.getMessage());
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public CommonResponse handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
    FieldError fieldError = e.getBindingResult().getFieldError();
    String message = "参数校验错误";
    if (fieldError != null) {
      message = fieldError.getDefaultMessage();
    }
    return CommonResponse.error(PARAM_CHECK_ERROR, message);
  }

  /**
   * 成功
   */
  public <T> CommonResponse success(T result) {
    return CommonResponse.success(result);
  }


  /**
   * 成功
   */
  public <T> CommonResponse success() {
    return CommonResponse.success();
  }

  public Object getApplyInfo(MongoTemplate mongoTemplate, Long userId) {
    Query query = Query.query(Criteria.where("_id").is(userId));
    Object applyInfo = mongoTemplate.findOne(query, Object.class, "kankan_apply");
    return applyInfo;
  }

  public String getUsername( Object applyInfo){
    if (applyInfo instanceof KankanApply) {
      return ((KankanApply) applyInfo).getUsername();
    } else {
      return ((KankanCompanyApply) applyInfo).getAdminName();
    }
  }


  public String getRemark( Object applyInfo){
    if (applyInfo instanceof KankanApply) {
      return ((KankanApply) applyInfo).getRemark();
    } else {
      return ((KankanCompanyApply) applyInfo).getCity();
    }
  }




  public UserPrivilege getUserPrivilege(MongoTemplate mongoTemplate, Long userId) {
    Query query = Query.query(Criteria.where("_id").is(userId));
    UserPrivilege userPrivilege = mongoTemplate.findOne(query, UserPrivilege.class);
    return userPrivilege;
  }

  public List<String> getPrivilege(Object applyInfo) {
    if (applyInfo instanceof KankanApply) {
      return ((KankanApply) applyInfo).getPrivilege();
    } else {
      return ((KankanCompanyApply) applyInfo).getPrivilege();
    }
  }

}
