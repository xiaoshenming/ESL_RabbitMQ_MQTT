package com.pandatech.downloadcf.mapper;

import com.pandatech.downloadcf.entity.PrintTemplateDesign;
import com.pandatech.downloadcf.entity.PrintTemplateDesignExample;
import com.pandatech.downloadcf.entity.PrintTemplateDesignWithBLOBs;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface PrintTemplateDesignMapper {
    long countByExample(PrintTemplateDesignExample example);

    int deleteByExample(PrintTemplateDesignExample example);

    int deleteByPrimaryKey(String id);

    int insert(PrintTemplateDesignWithBLOBs row);

    int insertSelective(PrintTemplateDesignWithBLOBs row);

    List<PrintTemplateDesignWithBLOBs> selectByExampleWithBLOBsWithRowbounds(PrintTemplateDesignExample example, RowBounds rowBounds);

    List<PrintTemplateDesignWithBLOBs> selectByExampleWithBLOBs(PrintTemplateDesignExample example);

    List<PrintTemplateDesign> selectByExampleWithRowbounds(PrintTemplateDesignExample example, RowBounds rowBounds);

    List<PrintTemplateDesign> selectByExample(PrintTemplateDesignExample example);

    PrintTemplateDesignWithBLOBs selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("row") PrintTemplateDesignWithBLOBs row, @Param("example") PrintTemplateDesignExample example);

    int updateByExampleWithBLOBs(@Param("row") PrintTemplateDesignWithBLOBs row, @Param("example") PrintTemplateDesignExample example);

    int updateByExample(@Param("row") PrintTemplateDesign row, @Param("example") PrintTemplateDesignExample example);

    int updateByPrimaryKeySelective(PrintTemplateDesignWithBLOBs row);

    int updateByPrimaryKeyWithBLOBs(PrintTemplateDesignWithBLOBs row);

    int updateByPrimaryKey(PrintTemplateDesign row);
    
    // 自定义查询方法
    PrintTemplateDesignWithBLOBs findByName(@Param("name") String name);
    
    PrintTemplateDesignWithBLOBs findByNameLike(@Param("name") String name);
}