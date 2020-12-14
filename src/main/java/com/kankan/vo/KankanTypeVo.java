package com.kankan.vo;


import java.util.List;

import lombok.Data;

/**
 * @author <qiding@qiding.com>
 * Created on 2020-12-08
 */
@Data
public class KankanTypeVo {
   private Long id;
   private String typeDesc;
   List<KankanUserVo>  userVoList;
}
