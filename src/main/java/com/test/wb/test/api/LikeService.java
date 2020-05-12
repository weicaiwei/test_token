package com.test.wb.test.api;

/**
 * TODO
 *
 * @auther caiwei
 * @date 2020-04-24
 */
public interface LikeService {

    void like(Integer uid, Integer statusId);

    boolean isLiked(Integer uid, Integer statusId);
}
