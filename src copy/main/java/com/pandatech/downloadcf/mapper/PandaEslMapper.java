package com.pandatech.downloadcf.mapper;

import com.pandatech.downloadcf.entity.PandaEsl;
import com.pandatech.downloadcf.entity.PandaEslExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface PandaEslMapper {
    long countByExample(PandaEslExample example);

    int deleteByExample(PandaEslExample example);

    int deleteByPrimaryKey(String id);

    int insert(PandaEsl row);

    int insertSelective(PandaEsl row);

    List<PandaEsl> selectByExampleWithBLOBsWithRowbounds(PandaEslExample example, RowBounds rowBounds);

    List<PandaEsl> selectByExampleWithBLOBs(PandaEslExample example);

    List<PandaEsl> selectByExampleWithRowbounds(PandaEslExample example, RowBounds rowBounds);

    List<PandaEsl> selectByExample(PandaEslExample example);

    PandaEsl selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("row") PandaEsl row, @Param("example") PandaEslExample example);

    int updateByExampleWithBLOBs(@Param("row") PandaEsl row, @Param("example") PandaEslExample example);

    int updateByExample(@Param("row") PandaEsl row, @Param("example") PandaEslExample example);

    int updateByPrimaryKeySelective(PandaEsl row);

    int updateByPrimaryKeyWithBLOBs(PandaEsl row);

    int updateByPrimaryKey(PandaEsl row);
}