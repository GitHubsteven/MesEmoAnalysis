package com.mea.dao.com;

import java.util.List;

/**
 * @version 1.0.0 COPYRIGHT © 2001 - 2018 VOYAGE ONE GROUP INC. ALL RIGHTS RESERVED.
 * @Author rongbin.xie
 * @Description:
 * @Date: Created at 22:47 2018/4/17.
 */
public interface BaseDao<T> {
    int insert(T param);

    int update(T param);

    T selectById(Long id);

    T selectOne(T param);

    List<T> selectList(T param);

    int deleteById(Long id);

    void delete(T param);
}
