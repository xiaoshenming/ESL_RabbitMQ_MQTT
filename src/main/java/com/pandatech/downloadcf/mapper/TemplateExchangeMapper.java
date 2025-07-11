package com.pandatech.downloadcf.mapper;

import com.pandatech.downloadcf.entity.TemplateExchange;
import com.pandatech.downloadcf.entity.TemplateExchangeExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface TemplateExchangeMapper {
    long countByExample(TemplateExchangeExample example);

    int deleteByExample(TemplateExchangeExample example);

    int deleteByPrimaryKey(Long id);

    int insert(TemplateExchange row);

    int insertSelective(TemplateExchange row);

    List<TemplateExchange> selectByExampleWithBLOBsWithRowbounds(TemplateExchangeExample example, RowBounds rowBounds);

    List<TemplateExchange> selectByExampleWithBLOBs(TemplateExchangeExample example);

    List<TemplateExchange> selectByExampleWithRowbounds(TemplateExchangeExample example, RowBounds rowBounds);

    List<TemplateExchange> selectByExample(TemplateExchangeExample example);

    TemplateExchange selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("row") TemplateExchange row, @Param("example") TemplateExchangeExample example);

    int updateByExampleWithBLOBs(@Param("row") TemplateExchange row, @Param("example") TemplateExchangeExample example);

    int updateByExample(@Param("row") TemplateExchange row, @Param("example") TemplateExchangeExample example);

    int updateByPrimaryKeySelective(TemplateExchange row);

    int updateByPrimaryKeyWithBLOBs(TemplateExchange row);

    int updateByPrimaryKey(TemplateExchange row);
}