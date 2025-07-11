package com.pandatech.downloadcf.mapper;

import com.pandatech.downloadcf.entity.PandaProduct;
import com.pandatech.downloadcf.entity.PandaProductExample;
import com.pandatech.downloadcf.entity.PandaProductWithBLOBs;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface PandaProductMapper {
    long countByExample(PandaProductExample example);

    int deleteByExample(PandaProductExample example);

    int deleteByPrimaryKey(String id);

    int insert(PandaProductWithBLOBs row);

    int insertSelective(PandaProductWithBLOBs row);

    List<PandaProductWithBLOBs> selectByExampleWithBLOBsWithRowbounds(PandaProductExample example, RowBounds rowBounds);

    List<PandaProductWithBLOBs> selectByExampleWithBLOBs(PandaProductExample example);

    List<PandaProduct> selectByExampleWithRowbounds(PandaProductExample example, RowBounds rowBounds);

    List<PandaProduct> selectByExample(PandaProductExample example);

    PandaProductWithBLOBs selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("row") PandaProductWithBLOBs row, @Param("example") PandaProductExample example);

    int updateByExampleWithBLOBs(@Param("row") PandaProductWithBLOBs row, @Param("example") PandaProductExample example);

    int updateByExample(@Param("row") PandaProduct row, @Param("example") PandaProductExample example);

    int updateByPrimaryKeySelective(PandaProductWithBLOBs row);

    int updateByPrimaryKeyWithBLOBs(PandaProductWithBLOBs row);

    int updateByPrimaryKey(PandaProduct row);
}