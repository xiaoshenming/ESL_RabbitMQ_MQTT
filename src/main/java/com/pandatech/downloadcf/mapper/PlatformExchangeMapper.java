package com.pandatech.downloadcf.mapper;

import com.pandatech.downloadcf.entity.PlatformExchange;
import com.pandatech.downloadcf.entity.PlatformExchangeExample;
import com.pandatech.downloadcf.entity.PlatformExchangeWithBLOBs;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface PlatformExchangeMapper {
    long countByExample(PlatformExchangeExample example);

    int deleteByExample(PlatformExchangeExample example);

    int deleteByPrimaryKey(Long id);

    int insert(PlatformExchangeWithBLOBs row);

    int insertSelective(PlatformExchangeWithBLOBs row);

    List<PlatformExchangeWithBLOBs> selectByExampleWithBLOBsWithRowbounds(PlatformExchangeExample example, RowBounds rowBounds);

    List<PlatformExchangeWithBLOBs> selectByExampleWithBLOBs(PlatformExchangeExample example);

    List<PlatformExchange> selectByExampleWithRowbounds(PlatformExchangeExample example, RowBounds rowBounds);

    List<PlatformExchange> selectByExample(PlatformExchangeExample example);

    PlatformExchangeWithBLOBs selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("row") PlatformExchangeWithBLOBs row, @Param("example") PlatformExchangeExample example);

    int updateByExampleWithBLOBs(@Param("row") PlatformExchangeWithBLOBs row, @Param("example") PlatformExchangeExample example);

    int updateByExample(@Param("row") PlatformExchange row, @Param("example") PlatformExchangeExample example);

    int updateByPrimaryKeySelective(PlatformExchangeWithBLOBs row);

    int updateByPrimaryKeyWithBLOBs(PlatformExchangeWithBLOBs row);

    int updateByPrimaryKey(PlatformExchange row);
}