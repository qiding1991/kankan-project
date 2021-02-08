package com.kankan.api.user;
import com.kankan.api.BaseController;
import com.kankan.constant.CommonResponse;
import com.kankan.module.UserMessage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Validated
@Api(tags = "站内信")
@RestController
@RequestMapping("message")
public class UserMessageController extends BaseController {

  @Autowired
  private MongoTemplate mongoTemplate;

  @ApiOperation(value = "获取站内信")
  @GetMapping("list")
  public CommonResponse list(@RequestParam(value = "userId") Long userId) {
    Query query = Query.query(Criteria.where("userId").is(userId));
    List<UserMessage> infoList = mongoTemplate.find(query, UserMessage.class);
    return success(infoList);
  }

  @ApiOperation(value = "读取站内信")
  @PostMapping("read/{id}")
  public CommonResponse read(@PathVariable(value = "id") String id) {
    Query query = Query.query(Criteria.where("id").is(id));
    Update update = Update.update("isRead", true);
    mongoTemplate.updateMulti(query, update, UserMessage.class);
    return success();
  }

  @ApiOperation(value = "全部读取")
  @PostMapping("readAll")
  public CommonResponse readAll(@RequestParam(value = "userId") Long userId) {
    Query query = Query.query(Criteria.where("userId").is(userId));
    Update update = Update.update("isRead", true);
    mongoTemplate.updateMulti(query, update, UserMessage.class);
    return success();
  }
}
