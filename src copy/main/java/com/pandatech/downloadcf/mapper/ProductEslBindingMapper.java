package com.pandatech.downloadcf.mapper;

import com.pandatech.downloadcf.entity.ProductEslBinding;
import com.pandatech.downloadcf.entity.ProductEslBindingExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface ProductEslBindingMapper {
    long countByExample(ProductEslBindingExample example);

    int deleteByExample(ProductEslBindingExample example);

    int deleteByPrimaryKey(String id);

    int insert(ProductEslBinding row);

    int insertSelective(ProductEslBinding row);

    List<ProductEslBinding> selectByExampleWithBLOBsWithRowbounds(ProductEslBindingExample example, RowBounds rowBounds);

    List<ProductEslBinding> selectByExampleWithBLOBs(ProductEslBindingExample example);

    List<ProductEslBinding> selectByExampleWithRowbounds(ProductEslBindingExample example, RowBounds rowBounds);

    List<ProductEslBinding> selectByExample(ProductEslBindingExample example);

    ProductEslBinding selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("row") ProductEslBinding row, @Param("example") ProductEslBindingExample example);

    int updateByExampleWithBLOBs(@Param("row") ProductEslBinding row, @Param("example") ProductEslBindingExample example);

    int updateByExample(@Param("row") ProductEslBinding row, @Param("example") ProductEslBindingExample example);

    int updateByPrimaryKeySelective(ProductEslBinding row);

    int updateByPrimaryKeyWithBLOBs(ProductEslBinding row);

    int updateByPrimaryKey(ProductEslBinding row);
}