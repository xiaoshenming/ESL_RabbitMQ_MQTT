package com.pandatech.downloadcf.mapper;

import com.pandatech.downloadcf.entity.EslBrand;
import com.pandatech.downloadcf.entity.EslBrandExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface EslBrandMapper {
    long countByExample(EslBrandExample example);

    int deleteByExample(EslBrandExample example);

    int deleteByPrimaryKey(String id);

    int insert(EslBrand row);

    int insertSelective(EslBrand row);

    List<EslBrand> selectByExampleWithBLOBsWithRowbounds(EslBrandExample example, RowBounds rowBounds);

    List<EslBrand> selectByExampleWithBLOBs(EslBrandExample example);

    List<EslBrand> selectByExampleWithRowbounds(EslBrandExample example, RowBounds rowBounds);

    List<EslBrand> selectByExample(EslBrandExample example);

    EslBrand selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("row") EslBrand row, @Param("example") EslBrandExample example);

    int updateByExampleWithBLOBs(@Param("row") EslBrand row, @Param("example") EslBrandExample example);

    int updateByExample(@Param("row") EslBrand row, @Param("example") EslBrandExample example);

    int updateByPrimaryKeySelective(EslBrand row);

    int updateByPrimaryKeyWithBLOBs(EslBrand row);

    int updateByPrimaryKey(EslBrand row);
}