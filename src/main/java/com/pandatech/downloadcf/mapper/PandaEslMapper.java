package com.pandatech.downloadcf.mapper;

import com.pandatech.downloadcf.entity.PandaEsl;
import com.pandatech.downloadcf.entity.PandaEslExample;
import com.pandatech.downloadcf.entity.PandaEslWithBLOBs;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface PandaEslMapper {
    long countByExample(PandaEslExample example);

    int deleteByExample(PandaEslExample example);

    int deleteByPrimaryKey(String id);

    int insert(PandaEslWithBLOBs row);

    int insertSelective(PandaEslWithBLOBs row);

    List<PandaEslWithBLOBs> selectByExampleWithBLOBsWithRowbounds(PandaEslExample example, RowBounds rowBounds);

    List<PandaEslWithBLOBs> selectByExampleWithBLOBs(PandaEslExample example);

    List<PandaEsl> selectByExampleWithRowbounds(PandaEslExample example, RowBounds rowBounds);

    List<PandaEsl> selectByExample(PandaEslExample example);

    PandaEslWithBLOBs selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("row") PandaEslWithBLOBs row, @Param("example") PandaEslExample example);

    int updateByExampleWithBLOBs(@Param("row") PandaEslWithBLOBs row, @Param("example") PandaEslExample example);

    int updateByExample(@Param("row") PandaEsl row, @Param("example") PandaEslExample example);

    int updateByPrimaryKeySelective(PandaEslWithBLOBs row);

    int updateByPrimaryKeyWithBLOBs(PandaEslWithBLOBs row);

    int updateByPrimaryKey(PandaEsl row);
}