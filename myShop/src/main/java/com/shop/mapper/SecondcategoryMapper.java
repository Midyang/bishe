package com.shop.mapper;

import com.shop.pojo.Secondcategory;
import com.shop.pojo.SecondcategoryExample;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

public interface SecondcategoryMapper {
    int countByExample(SecondcategoryExample example);

    int deleteByExample(SecondcategoryExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Secondcategory record);

    int insertSelective(Secondcategory record);

    List<Secondcategory> selectByExample(SecondcategoryExample example);

    Secondcategory selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Secondcategory record, @Param("example") SecondcategoryExample example);

    int updateByExample(@Param("record") Secondcategory record, @Param("example") SecondcategoryExample example);

    int updateByPrimaryKeySelective(Secondcategory record);

    int updateByPrimaryKey(Secondcategory record);

    List<Secondcategory> myGetBynameList(Map<String, Object> map);
}