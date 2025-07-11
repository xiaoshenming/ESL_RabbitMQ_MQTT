package com.pandatech.downloadcf.mapper;

import com.pandatech.downloadcf.entity.ActExtTemplatePrint;
import com.pandatech.downloadcf.entity.ActExtTemplatePrintExample;
import com.pandatech.downloadcf.entity.ActExtTemplatePrintWithBLOBs;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface ActExtTemplatePrintMapper {
    long countByExample(ActExtTemplatePrintExample example);

    int deleteByExample(ActExtTemplatePrintExample example);

    int deleteByPrimaryKey(String id);

    int insert(ActExtTemplatePrintWithBLOBs row);

    int insertSelective(ActExtTemplatePrintWithBLOBs row);

    List<ActExtTemplatePrintWithBLOBs> selectByExampleWithBLOBsWithRowbounds(ActExtTemplatePrintExample example, RowBounds rowBounds);

    List<ActExtTemplatePrintWithBLOBs> selectByExampleWithBLOBs(ActExtTemplatePrintExample example);

    List<ActExtTemplatePrint> selectByExampleWithRowbounds(ActExtTemplatePrintExample example, RowBounds rowBounds);

    List<ActExtTemplatePrint> selectByExample(ActExtTemplatePrintExample example);

    ActExtTemplatePrintWithBLOBs selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("row") ActExtTemplatePrintWithBLOBs row, @Param("example") ActExtTemplatePrintExample example);

    int updateByExampleWithBLOBs(@Param("row") ActExtTemplatePrintWithBLOBs row, @Param("example") ActExtTemplatePrintExample example);

    int updateByExample(@Param("row") ActExtTemplatePrint row, @Param("example") ActExtTemplatePrintExample example);

    int updateByPrimaryKeySelective(ActExtTemplatePrintWithBLOBs row);

    int updateByPrimaryKeyWithBLOBs(ActExtTemplatePrintWithBLOBs row);

    int updateByPrimaryKey(ActExtTemplatePrint row);
}