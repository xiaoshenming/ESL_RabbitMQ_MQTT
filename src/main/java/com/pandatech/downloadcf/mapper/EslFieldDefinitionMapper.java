package com.pandatech.downloadcf.mapper;

import com.pandatech.downloadcf.entity.EslFieldDefinition;
import com.pandatech.downloadcf.entity.EslFieldDefinitionExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface EslFieldDefinitionMapper {
    long countByExample(EslFieldDefinitionExample example);

    int deleteByExample(EslFieldDefinitionExample example);

    int deleteByPrimaryKey(String id);

    int insert(EslFieldDefinition row);

    int insertSelective(EslFieldDefinition row);

    List<EslFieldDefinition> selectByExampleWithBLOBsWithRowbounds(EslFieldDefinitionExample example, RowBounds rowBounds);

    List<EslFieldDefinition> selectByExampleWithBLOBs(EslFieldDefinitionExample example);

    List<EslFieldDefinition> selectByExampleWithRowbounds(EslFieldDefinitionExample example, RowBounds rowBounds);

    List<EslFieldDefinition> selectByExample(EslFieldDefinitionExample example);

    EslFieldDefinition selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("row") EslFieldDefinition row, @Param("example") EslFieldDefinitionExample example);

    int updateByExampleWithBLOBs(@Param("row") EslFieldDefinition row, @Param("example") EslFieldDefinitionExample example);

    int updateByExample(@Param("row") EslFieldDefinition row, @Param("example") EslFieldDefinitionExample example);

    int updateByPrimaryKeySelective(EslFieldDefinition row);

    int updateByPrimaryKeyWithBLOBs(EslFieldDefinition row);

    int updateByPrimaryKey(EslFieldDefinition row);
}