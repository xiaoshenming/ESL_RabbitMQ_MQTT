package com.pandatech.downloadcf.mapper;

import com.pandatech.downloadcf.entity.EslModel;
import com.pandatech.downloadcf.entity.EslModelExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface EslModelMapper {
    long countByExample(EslModelExample example);

    int deleteByExample(EslModelExample example);

    int deleteByPrimaryKey(String id);

    int insert(EslModel row);

    int insertSelective(EslModel row);

    List<EslModel> selectByExampleWithBLOBsWithRowbounds(EslModelExample example, RowBounds rowBounds);

    List<EslModel> selectByExampleWithBLOBs(EslModelExample example);

    List<EslModel> selectByExampleWithRowbounds(EslModelExample example, RowBounds rowBounds);

    List<EslModel> selectByExample(EslModelExample example);

    EslModel selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("row") EslModel row, @Param("example") EslModelExample example);

    int updateByExampleWithBLOBs(@Param("row") EslModel row, @Param("example") EslModelExample example);

    int updateByExample(@Param("row") EslModel row, @Param("example") EslModelExample example);

    int updateByPrimaryKeySelective(EslModel row);

    int updateByPrimaryKeyWithBLOBs(EslModel row);

    int updateByPrimaryKey(EslModel row);
}