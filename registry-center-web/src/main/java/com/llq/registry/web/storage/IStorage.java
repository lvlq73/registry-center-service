package com.llq.registry.web.storage;

import java.util.List;

/**
 * @author lvlianqi
 * @description
 * @date 2021/12/9
 */
public interface IStorage<T> {

    void add(String key, T data);

    void remove(String key, T data);

    List<T> getList(String key);

    void update(String key, T data, long timestamp);
}
