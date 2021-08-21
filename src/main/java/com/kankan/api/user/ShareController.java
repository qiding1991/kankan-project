package com.kankan.api.user;

import com.kankan.api.BaseController;
import com.kankan.constant.CommonResponse;
import com.kankan.constant.EnumItemType;
import com.kankan.module.KankanComment;
import com.kankan.module.KankanUser;
import com.kankan.module.KankanWork;
import com.kankan.module.MediaResource;
import com.kankan.module.News;
import com.kankan.module.ShareDetail;
import com.kankan.module.Tab;
import com.kankan.module.User;
import com.kankan.service.ShareService;
import com.kankan.vo.comment.BaseCommentVo;
import com.kankan.vo.comment.NewsCommentVo;
import com.kankan.vo.comment.WorkCommentVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import java.util.stream.Collectors;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author <qiding@qiding.com> Created on 2020-12-06
 */
@Log4j2
@Validated
@Api(tags = "用户-分享-相关接口")
@RestController
@RequestMapping("share")
public class ShareController extends BaseController {

  @Autowired
  private ShareService shareService;

  @ApiOperation("分享信息")
  @PostMapping("send")
  public CommonResponse sendShare(@RequestBody ShareDetail shareDetail) {
     shareDetail = shareService.addShare(shareDetail);
    //获取基本信息
    return success(shareDetail);
  }

  @ApiOperation("获取分享信息")
  @GetMapping("detail/{id}")
  public CommonResponse detail(@PathVariable(value = "id") String id) {
    ShareDetail shareDetail = shareService.getShare(id);
    return success(shareDetail);
  }
}
