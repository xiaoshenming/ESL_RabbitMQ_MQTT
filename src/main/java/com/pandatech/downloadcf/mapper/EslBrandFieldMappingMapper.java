package com.pandatech.downloadcf.mapper;

import com.pandatech.downloadcf.entity.EslBrandFieldMapping;
import com.pandatech.downloadcf.entity.EslBrandFieldMappingExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface EslBrandFieldMappingMapper {
    long countByExample(EslBrandFieldMappingExample example);

    int deleteByExample(EslBrandFieldMappingExample example);

    int deleteByPrimaryKey(String id);

    int insert(EslBrandFieldMapping row);

    int insertSelective(EslBrandFieldMapping row);

    List<EslBrandFieldMapping> selectByExampleWithBLOBsWithRowbounds(EslBrandFieldMappingExample example, RowBounds rowBounds);

    List<EslBrandFieldMapping> selectByExampleWithBLOBs(EslBrandFieldMappingExample example);

    List<EslBrandFieldMapping> selectByExampleWithRowbounds(EslBrandFieldMappingExample example, RowBounds rowBounds);

    List<EslBrandFieldMapping> selectByExample(EslBrandFieldMappingExample example);

    EslBrandFieldMapping selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("row") EslBrandFieldMapping row, @Param("example") EslBrandFieldMappingExample example);

    int updateByExampleWithBLOBs(@Param("row") EslBrandFieldMapping row, @Param("example") EslBrandFieldMappingExample example);

    int updateByExample(@Param("row") EslBrandFieldMapping row, @Param("example") EslBrandFieldMappingExample example);

    int updateByPrimaryKeySelective(EslBrandFieldMapping row);

    int updateByPrimaryKeyWithBLOBs(EslBrandFieldMapping row);

    int updateByPrimaryKey(EslBrandFieldMapping row);
}