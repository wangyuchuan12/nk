package com.ifrabbit.nk.express.repository.impl;

import org.apache.ibatis.session.SqlSession;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mybatis.repository.support.SqlSessionRepositorySupport;

import java.util.List;
import java.util.Map;

/**
 * Created with IDEA
 * author:SunJiaJian
 * Date:2018/3/16
 * Time:18:43
 */
public class ExpressInfoDetailRepositoryImpl extends SqlSessionRepositorySupport {
    @Override
    protected String getNamespace() {
        return null;
    }

    protected ExpressInfoDetailRepositoryImpl(SqlSessionTemplate sqlSessionTemplate) {
        super(sqlSessionTemplate);
    }

    @Override
    public SqlSession getSqlSession() {
        return super.getSqlSession();
    }

    @Override
    protected String getStatement(String partStatement) {
        return super.getStatement(partStatement);
    }

    @Override
    protected <T> T selectOne(String statement) {
        return super.selectOne(statement);
    }

    @Override
    protected <T> T selectOne(String statement, Object parameter) {
        return super.selectOne(statement, parameter);
    }

    @Override
    protected <T> List<T> selectList(String statement) {
        return super.selectList(statement);
    }

    @Override
    protected <T> List<T> selectList(String statement, Object parameter) {
        return super.selectList(statement, parameter);
    }

    @Override
    protected int insert(String statement) {
        return super.insert(statement);
    }

    @Override
    protected int insert(String statement, Object parameter) {
        return super.insert(statement, parameter);
    }

    @Override
    protected int update(String statement) {
        return super.update(statement);
    }

    @Override
    protected int update(String statement, Object parameter) {
        return super.update(statement, parameter);
    }

    @Override
    protected int delete(String statement) {
        return super.delete(statement);
    }

    @Override
    protected int delete(String statement, Object parameter) {
        return super.delete(statement, parameter);
    }

    @Override
    protected <X> long calculateTotal(Pageable pager, List<X> result) {
        return super.calculateTotal(pager, result);
    }

    @Override
    protected <X, Y, T extends Page<X>> T findByPager(Class<T> resultType, Pageable pager, String selectStatement, String countStatement, Y condition, Map<String, Object> otherParams) {
        return super.findByPager(resultType, pager, selectStatement, countStatement, condition, otherParams);
    }

    @Override
    protected <X, Y> Page<X> findByPager(Pageable pager, String selectStatement, String countStatement, Y condition, Map<String, Object> otherParams) {
        return super.findByPager(pager, selectStatement, countStatement, condition, otherParams);
    }

    @Override
    protected <X, Y> Page<X> findByPager(Pageable pager, String selectStatement, String countStatement, Y condition, Map<String, Object> otherParams, String... columns) {
        return super.findByPager(pager, selectStatement, countStatement, condition, otherParams, columns);
    }

    @Override
    protected <X> Page<X> findByPager(Pageable pager, String selectStatement, String countStatement) {
        return super.findByPager(pager, selectStatement, countStatement);
    }

    @Override
    protected <X, Y> Page<X> findByPager(Pageable pager, String selectStatement, String countStatement, Y condition) {
        return super.findByPager(pager, selectStatement, countStatement, condition);
    }

    @Override
    protected <X, Y> Page<X> findByPager(Pageable pager, String selectStatement, String countStatement, Y condition, String... columns) {
        return super.findByPager(pager, selectStatement, countStatement, condition, columns);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    @Override
    public String toString() {
        return super.toString();
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
    }


}
