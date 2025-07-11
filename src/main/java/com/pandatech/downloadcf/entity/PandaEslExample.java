package com.pandatech.downloadcf.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PandaEslExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public PandaEslExample() {
        oredCriteria = new ArrayList<>();
    }

    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    public String getOrderByClause() {
        return orderByClause;
    }

    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    public boolean isDistinct() {
        return distinct;
    }

    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    protected abstract static class GeneratedCriteria {
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            return criteria;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
        }

        public Criteria andIdIsNull() {
            addCriterion("ID is null");
            return (Criteria) this;
        }

        public Criteria andIdIsNotNull() {
            addCriterion("ID is not null");
            return (Criteria) this;
        }

        public Criteria andIdEqualTo(String value) {
            addCriterion("ID =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(String value) {
            addCriterion("ID <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(String value) {
            addCriterion("ID >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(String value) {
            addCriterion("ID >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(String value) {
            addCriterion("ID <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(String value) {
            addCriterion("ID <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLike(String value) {
            addCriterion("ID like", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotLike(String value) {
            addCriterion("ID not like", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<String> values) {
            addCriterion("ID in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<String> values) {
            addCriterion("ID not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(String value1, String value2) {
            addCriterion("ID between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(String value1, String value2) {
            addCriterion("ID not between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andTenantIdIsNull() {
            addCriterion("TENANT_ID is null");
            return (Criteria) this;
        }

        public Criteria andTenantIdIsNotNull() {
            addCriterion("TENANT_ID is not null");
            return (Criteria) this;
        }

        public Criteria andTenantIdEqualTo(String value) {
            addCriterion("TENANT_ID =", value, "tenantId");
            return (Criteria) this;
        }

        public Criteria andTenantIdNotEqualTo(String value) {
            addCriterion("TENANT_ID <>", value, "tenantId");
            return (Criteria) this;
        }

        public Criteria andTenantIdGreaterThan(String value) {
            addCriterion("TENANT_ID >", value, "tenantId");
            return (Criteria) this;
        }

        public Criteria andTenantIdGreaterThanOrEqualTo(String value) {
            addCriterion("TENANT_ID >=", value, "tenantId");
            return (Criteria) this;
        }

        public Criteria andTenantIdLessThan(String value) {
            addCriterion("TENANT_ID <", value, "tenantId");
            return (Criteria) this;
        }

        public Criteria andTenantIdLessThanOrEqualTo(String value) {
            addCriterion("TENANT_ID <=", value, "tenantId");
            return (Criteria) this;
        }

        public Criteria andTenantIdLike(String value) {
            addCriterion("TENANT_ID like", value, "tenantId");
            return (Criteria) this;
        }

        public Criteria andTenantIdNotLike(String value) {
            addCriterion("TENANT_ID not like", value, "tenantId");
            return (Criteria) this;
        }

        public Criteria andTenantIdIn(List<String> values) {
            addCriterion("TENANT_ID in", values, "tenantId");
            return (Criteria) this;
        }

        public Criteria andTenantIdNotIn(List<String> values) {
            addCriterion("TENANT_ID not in", values, "tenantId");
            return (Criteria) this;
        }

        public Criteria andTenantIdBetween(String value1, String value2) {
            addCriterion("TENANT_ID between", value1, value2, "tenantId");
            return (Criteria) this;
        }

        public Criteria andTenantIdNotBetween(String value1, String value2) {
            addCriterion("TENANT_ID not between", value1, value2, "tenantId");
            return (Criteria) this;
        }

        public Criteria andSortCodeIsNull() {
            addCriterion("SORT_CODE is null");
            return (Criteria) this;
        }

        public Criteria andSortCodeIsNotNull() {
            addCriterion("SORT_CODE is not null");
            return (Criteria) this;
        }

        public Criteria andSortCodeEqualTo(Integer value) {
            addCriterion("SORT_CODE =", value, "sortCode");
            return (Criteria) this;
        }

        public Criteria andSortCodeNotEqualTo(Integer value) {
            addCriterion("SORT_CODE <>", value, "sortCode");
            return (Criteria) this;
        }

        public Criteria andSortCodeGreaterThan(Integer value) {
            addCriterion("SORT_CODE >", value, "sortCode");
            return (Criteria) this;
        }

        public Criteria andSortCodeGreaterThanOrEqualTo(Integer value) {
            addCriterion("SORT_CODE >=", value, "sortCode");
            return (Criteria) this;
        }

        public Criteria andSortCodeLessThan(Integer value) {
            addCriterion("SORT_CODE <", value, "sortCode");
            return (Criteria) this;
        }

        public Criteria andSortCodeLessThanOrEqualTo(Integer value) {
            addCriterion("SORT_CODE <=", value, "sortCode");
            return (Criteria) this;
        }

        public Criteria andSortCodeIn(List<Integer> values) {
            addCriterion("SORT_CODE in", values, "sortCode");
            return (Criteria) this;
        }

        public Criteria andSortCodeNotIn(List<Integer> values) {
            addCriterion("SORT_CODE not in", values, "sortCode");
            return (Criteria) this;
        }

        public Criteria andSortCodeBetween(Integer value1, Integer value2) {
            addCriterion("SORT_CODE between", value1, value2, "sortCode");
            return (Criteria) this;
        }

        public Criteria andSortCodeNotBetween(Integer value1, Integer value2) {
            addCriterion("SORT_CODE not between", value1, value2, "sortCode");
            return (Criteria) this;
        }

        public Criteria andDeleteFlagIsNull() {
            addCriterion("DELETE_FLAG is null");
            return (Criteria) this;
        }

        public Criteria andDeleteFlagIsNotNull() {
            addCriterion("DELETE_FLAG is not null");
            return (Criteria) this;
        }

        public Criteria andDeleteFlagEqualTo(String value) {
            addCriterion("DELETE_FLAG =", value, "deleteFlag");
            return (Criteria) this;
        }

        public Criteria andDeleteFlagNotEqualTo(String value) {
            addCriterion("DELETE_FLAG <>", value, "deleteFlag");
            return (Criteria) this;
        }

        public Criteria andDeleteFlagGreaterThan(String value) {
            addCriterion("DELETE_FLAG >", value, "deleteFlag");
            return (Criteria) this;
        }

        public Criteria andDeleteFlagGreaterThanOrEqualTo(String value) {
            addCriterion("DELETE_FLAG >=", value, "deleteFlag");
            return (Criteria) this;
        }

        public Criteria andDeleteFlagLessThan(String value) {
            addCriterion("DELETE_FLAG <", value, "deleteFlag");
            return (Criteria) this;
        }

        public Criteria andDeleteFlagLessThanOrEqualTo(String value) {
            addCriterion("DELETE_FLAG <=", value, "deleteFlag");
            return (Criteria) this;
        }

        public Criteria andDeleteFlagLike(String value) {
            addCriterion("DELETE_FLAG like", value, "deleteFlag");
            return (Criteria) this;
        }

        public Criteria andDeleteFlagNotLike(String value) {
            addCriterion("DELETE_FLAG not like", value, "deleteFlag");
            return (Criteria) this;
        }

        public Criteria andDeleteFlagIn(List<String> values) {
            addCriterion("DELETE_FLAG in", values, "deleteFlag");
            return (Criteria) this;
        }

        public Criteria andDeleteFlagNotIn(List<String> values) {
            addCriterion("DELETE_FLAG not in", values, "deleteFlag");
            return (Criteria) this;
        }

        public Criteria andDeleteFlagBetween(String value1, String value2) {
            addCriterion("DELETE_FLAG between", value1, value2, "deleteFlag");
            return (Criteria) this;
        }

        public Criteria andDeleteFlagNotBetween(String value1, String value2) {
            addCriterion("DELETE_FLAG not between", value1, value2, "deleteFlag");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIsNull() {
            addCriterion("CREATE_TIME is null");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIsNotNull() {
            addCriterion("CREATE_TIME is not null");
            return (Criteria) this;
        }

        public Criteria andCreateTimeEqualTo(Date value) {
            addCriterion("CREATE_TIME =", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotEqualTo(Date value) {
            addCriterion("CREATE_TIME <>", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeGreaterThan(Date value) {
            addCriterion("CREATE_TIME >", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("CREATE_TIME >=", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeLessThan(Date value) {
            addCriterion("CREATE_TIME <", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeLessThanOrEqualTo(Date value) {
            addCriterion("CREATE_TIME <=", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIn(List<Date> values) {
            addCriterion("CREATE_TIME in", values, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotIn(List<Date> values) {
            addCriterion("CREATE_TIME not in", values, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeBetween(Date value1, Date value2) {
            addCriterion("CREATE_TIME between", value1, value2, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotBetween(Date value1, Date value2) {
            addCriterion("CREATE_TIME not between", value1, value2, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateUserIsNull() {
            addCriterion("CREATE_USER is null");
            return (Criteria) this;
        }

        public Criteria andCreateUserIsNotNull() {
            addCriterion("CREATE_USER is not null");
            return (Criteria) this;
        }

        public Criteria andCreateUserEqualTo(String value) {
            addCriterion("CREATE_USER =", value, "createUser");
            return (Criteria) this;
        }

        public Criteria andCreateUserNotEqualTo(String value) {
            addCriterion("CREATE_USER <>", value, "createUser");
            return (Criteria) this;
        }

        public Criteria andCreateUserGreaterThan(String value) {
            addCriterion("CREATE_USER >", value, "createUser");
            return (Criteria) this;
        }

        public Criteria andCreateUserGreaterThanOrEqualTo(String value) {
            addCriterion("CREATE_USER >=", value, "createUser");
            return (Criteria) this;
        }

        public Criteria andCreateUserLessThan(String value) {
            addCriterion("CREATE_USER <", value, "createUser");
            return (Criteria) this;
        }

        public Criteria andCreateUserLessThanOrEqualTo(String value) {
            addCriterion("CREATE_USER <=", value, "createUser");
            return (Criteria) this;
        }

        public Criteria andCreateUserLike(String value) {
            addCriterion("CREATE_USER like", value, "createUser");
            return (Criteria) this;
        }

        public Criteria andCreateUserNotLike(String value) {
            addCriterion("CREATE_USER not like", value, "createUser");
            return (Criteria) this;
        }

        public Criteria andCreateUserIn(List<String> values) {
            addCriterion("CREATE_USER in", values, "createUser");
            return (Criteria) this;
        }

        public Criteria andCreateUserNotIn(List<String> values) {
            addCriterion("CREATE_USER not in", values, "createUser");
            return (Criteria) this;
        }

        public Criteria andCreateUserBetween(String value1, String value2) {
            addCriterion("CREATE_USER between", value1, value2, "createUser");
            return (Criteria) this;
        }

        public Criteria andCreateUserNotBetween(String value1, String value2) {
            addCriterion("CREATE_USER not between", value1, value2, "createUser");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeIsNull() {
            addCriterion("UPDATE_TIME is null");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeIsNotNull() {
            addCriterion("UPDATE_TIME is not null");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeEqualTo(Date value) {
            addCriterion("UPDATE_TIME =", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeNotEqualTo(Date value) {
            addCriterion("UPDATE_TIME <>", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeGreaterThan(Date value) {
            addCriterion("UPDATE_TIME >", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("UPDATE_TIME >=", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeLessThan(Date value) {
            addCriterion("UPDATE_TIME <", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeLessThanOrEqualTo(Date value) {
            addCriterion("UPDATE_TIME <=", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeIn(List<Date> values) {
            addCriterion("UPDATE_TIME in", values, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeNotIn(List<Date> values) {
            addCriterion("UPDATE_TIME not in", values, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeBetween(Date value1, Date value2) {
            addCriterion("UPDATE_TIME between", value1, value2, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeNotBetween(Date value1, Date value2) {
            addCriterion("UPDATE_TIME not between", value1, value2, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateUserIsNull() {
            addCriterion("UPDATE_USER is null");
            return (Criteria) this;
        }

        public Criteria andUpdateUserIsNotNull() {
            addCriterion("UPDATE_USER is not null");
            return (Criteria) this;
        }

        public Criteria andUpdateUserEqualTo(String value) {
            addCriterion("UPDATE_USER =", value, "updateUser");
            return (Criteria) this;
        }

        public Criteria andUpdateUserNotEqualTo(String value) {
            addCriterion("UPDATE_USER <>", value, "updateUser");
            return (Criteria) this;
        }

        public Criteria andUpdateUserGreaterThan(String value) {
            addCriterion("UPDATE_USER >", value, "updateUser");
            return (Criteria) this;
        }

        public Criteria andUpdateUserGreaterThanOrEqualTo(String value) {
            addCriterion("UPDATE_USER >=", value, "updateUser");
            return (Criteria) this;
        }

        public Criteria andUpdateUserLessThan(String value) {
            addCriterion("UPDATE_USER <", value, "updateUser");
            return (Criteria) this;
        }

        public Criteria andUpdateUserLessThanOrEqualTo(String value) {
            addCriterion("UPDATE_USER <=", value, "updateUser");
            return (Criteria) this;
        }

        public Criteria andUpdateUserLike(String value) {
            addCriterion("UPDATE_USER like", value, "updateUser");
            return (Criteria) this;
        }

        public Criteria andUpdateUserNotLike(String value) {
            addCriterion("UPDATE_USER not like", value, "updateUser");
            return (Criteria) this;
        }

        public Criteria andUpdateUserIn(List<String> values) {
            addCriterion("UPDATE_USER in", values, "updateUser");
            return (Criteria) this;
        }

        public Criteria andUpdateUserNotIn(List<String> values) {
            addCriterion("UPDATE_USER not in", values, "updateUser");
            return (Criteria) this;
        }

        public Criteria andUpdateUserBetween(String value1, String value2) {
            addCriterion("UPDATE_USER between", value1, value2, "updateUser");
            return (Criteria) this;
        }

        public Criteria andUpdateUserNotBetween(String value1, String value2) {
            addCriterion("UPDATE_USER not between", value1, value2, "updateUser");
            return (Criteria) this;
        }

        public Criteria andEslIdIsNull() {
            addCriterion("ESL_ID is null");
            return (Criteria) this;
        }

        public Criteria andEslIdIsNotNull() {
            addCriterion("ESL_ID is not null");
            return (Criteria) this;
        }

        public Criteria andEslIdEqualTo(String value) {
            addCriterion("ESL_ID =", value, "eslId");
            return (Criteria) this;
        }

        public Criteria andEslIdNotEqualTo(String value) {
            addCriterion("ESL_ID <>", value, "eslId");
            return (Criteria) this;
        }

        public Criteria andEslIdGreaterThan(String value) {
            addCriterion("ESL_ID >", value, "eslId");
            return (Criteria) this;
        }

        public Criteria andEslIdGreaterThanOrEqualTo(String value) {
            addCriterion("ESL_ID >=", value, "eslId");
            return (Criteria) this;
        }

        public Criteria andEslIdLessThan(String value) {
            addCriterion("ESL_ID <", value, "eslId");
            return (Criteria) this;
        }

        public Criteria andEslIdLessThanOrEqualTo(String value) {
            addCriterion("ESL_ID <=", value, "eslId");
            return (Criteria) this;
        }

        public Criteria andEslIdLike(String value) {
            addCriterion("ESL_ID like", value, "eslId");
            return (Criteria) this;
        }

        public Criteria andEslIdNotLike(String value) {
            addCriterion("ESL_ID not like", value, "eslId");
            return (Criteria) this;
        }

        public Criteria andEslIdIn(List<String> values) {
            addCriterion("ESL_ID in", values, "eslId");
            return (Criteria) this;
        }

        public Criteria andEslIdNotIn(List<String> values) {
            addCriterion("ESL_ID not in", values, "eslId");
            return (Criteria) this;
        }

        public Criteria andEslIdBetween(String value1, String value2) {
            addCriterion("ESL_ID between", value1, value2, "eslId");
            return (Criteria) this;
        }

        public Criteria andEslIdNotBetween(String value1, String value2) {
            addCriterion("ESL_ID not between", value1, value2, "eslId");
            return (Criteria) this;
        }

        public Criteria andStoreCodeIsNull() {
            addCriterion("STORE_CODE is null");
            return (Criteria) this;
        }

        public Criteria andStoreCodeIsNotNull() {
            addCriterion("STORE_CODE is not null");
            return (Criteria) this;
        }

        public Criteria andStoreCodeEqualTo(String value) {
            addCriterion("STORE_CODE =", value, "storeCode");
            return (Criteria) this;
        }

        public Criteria andStoreCodeNotEqualTo(String value) {
            addCriterion("STORE_CODE <>", value, "storeCode");
            return (Criteria) this;
        }

        public Criteria andStoreCodeGreaterThan(String value) {
            addCriterion("STORE_CODE >", value, "storeCode");
            return (Criteria) this;
        }

        public Criteria andStoreCodeGreaterThanOrEqualTo(String value) {
            addCriterion("STORE_CODE >=", value, "storeCode");
            return (Criteria) this;
        }

        public Criteria andStoreCodeLessThan(String value) {
            addCriterion("STORE_CODE <", value, "storeCode");
            return (Criteria) this;
        }

        public Criteria andStoreCodeLessThanOrEqualTo(String value) {
            addCriterion("STORE_CODE <=", value, "storeCode");
            return (Criteria) this;
        }

        public Criteria andStoreCodeLike(String value) {
            addCriterion("STORE_CODE like", value, "storeCode");
            return (Criteria) this;
        }

        public Criteria andStoreCodeNotLike(String value) {
            addCriterion("STORE_CODE not like", value, "storeCode");
            return (Criteria) this;
        }

        public Criteria andStoreCodeIn(List<String> values) {
            addCriterion("STORE_CODE in", values, "storeCode");
            return (Criteria) this;
        }

        public Criteria andStoreCodeNotIn(List<String> values) {
            addCriterion("STORE_CODE not in", values, "storeCode");
            return (Criteria) this;
        }

        public Criteria andStoreCodeBetween(String value1, String value2) {
            addCriterion("STORE_CODE between", value1, value2, "storeCode");
            return (Criteria) this;
        }

        public Criteria andStoreCodeNotBetween(String value1, String value2) {
            addCriterion("STORE_CODE not between", value1, value2, "storeCode");
            return (Criteria) this;
        }

        public Criteria andEslModelIsNull() {
            addCriterion("ESL_MODEL is null");
            return (Criteria) this;
        }

        public Criteria andEslModelIsNotNull() {
            addCriterion("ESL_MODEL is not null");
            return (Criteria) this;
        }

        public Criteria andEslModelEqualTo(String value) {
            addCriterion("ESL_MODEL =", value, "eslModel");
            return (Criteria) this;
        }

        public Criteria andEslModelNotEqualTo(String value) {
            addCriterion("ESL_MODEL <>", value, "eslModel");
            return (Criteria) this;
        }

        public Criteria andEslModelGreaterThan(String value) {
            addCriterion("ESL_MODEL >", value, "eslModel");
            return (Criteria) this;
        }

        public Criteria andEslModelGreaterThanOrEqualTo(String value) {
            addCriterion("ESL_MODEL >=", value, "eslModel");
            return (Criteria) this;
        }

        public Criteria andEslModelLessThan(String value) {
            addCriterion("ESL_MODEL <", value, "eslModel");
            return (Criteria) this;
        }

        public Criteria andEslModelLessThanOrEqualTo(String value) {
            addCriterion("ESL_MODEL <=", value, "eslModel");
            return (Criteria) this;
        }

        public Criteria andEslModelLike(String value) {
            addCriterion("ESL_MODEL like", value, "eslModel");
            return (Criteria) this;
        }

        public Criteria andEslModelNotLike(String value) {
            addCriterion("ESL_MODEL not like", value, "eslModel");
            return (Criteria) this;
        }

        public Criteria andEslModelIn(List<String> values) {
            addCriterion("ESL_MODEL in", values, "eslModel");
            return (Criteria) this;
        }

        public Criteria andEslModelNotIn(List<String> values) {
            addCriterion("ESL_MODEL not in", values, "eslModel");
            return (Criteria) this;
        }

        public Criteria andEslModelBetween(String value1, String value2) {
            addCriterion("ESL_MODEL between", value1, value2, "eslModel");
            return (Criteria) this;
        }

        public Criteria andEslModelNotBetween(String value1, String value2) {
            addCriterion("ESL_MODEL not between", value1, value2, "eslModel");
            return (Criteria) this;
        }

        public Criteria andBoundProductIsNull() {
            addCriterion("BOUND_PRODUCT is null");
            return (Criteria) this;
        }

        public Criteria andBoundProductIsNotNull() {
            addCriterion("BOUND_PRODUCT is not null");
            return (Criteria) this;
        }

        public Criteria andBoundProductEqualTo(String value) {
            addCriterion("BOUND_PRODUCT =", value, "boundProduct");
            return (Criteria) this;
        }

        public Criteria andBoundProductNotEqualTo(String value) {
            addCriterion("BOUND_PRODUCT <>", value, "boundProduct");
            return (Criteria) this;
        }

        public Criteria andBoundProductGreaterThan(String value) {
            addCriterion("BOUND_PRODUCT >", value, "boundProduct");
            return (Criteria) this;
        }

        public Criteria andBoundProductGreaterThanOrEqualTo(String value) {
            addCriterion("BOUND_PRODUCT >=", value, "boundProduct");
            return (Criteria) this;
        }

        public Criteria andBoundProductLessThan(String value) {
            addCriterion("BOUND_PRODUCT <", value, "boundProduct");
            return (Criteria) this;
        }

        public Criteria andBoundProductLessThanOrEqualTo(String value) {
            addCriterion("BOUND_PRODUCT <=", value, "boundProduct");
            return (Criteria) this;
        }

        public Criteria andBoundProductLike(String value) {
            addCriterion("BOUND_PRODUCT like", value, "boundProduct");
            return (Criteria) this;
        }

        public Criteria andBoundProductNotLike(String value) {
            addCriterion("BOUND_PRODUCT not like", value, "boundProduct");
            return (Criteria) this;
        }

        public Criteria andBoundProductIn(List<String> values) {
            addCriterion("BOUND_PRODUCT in", values, "boundProduct");
            return (Criteria) this;
        }

        public Criteria andBoundProductNotIn(List<String> values) {
            addCriterion("BOUND_PRODUCT not in", values, "boundProduct");
            return (Criteria) this;
        }

        public Criteria andBoundProductBetween(String value1, String value2) {
            addCriterion("BOUND_PRODUCT between", value1, value2, "boundProduct");
            return (Criteria) this;
        }

        public Criteria andBoundProductNotBetween(String value1, String value2) {
            addCriterion("BOUND_PRODUCT not between", value1, value2, "boundProduct");
            return (Criteria) this;
        }

        public Criteria andBatteryLevelIsNull() {
            addCriterion("BATTERY_LEVEL is null");
            return (Criteria) this;
        }

        public Criteria andBatteryLevelIsNotNull() {
            addCriterion("BATTERY_LEVEL is not null");
            return (Criteria) this;
        }

        public Criteria andBatteryLevelEqualTo(Float value) {
            addCriterion("BATTERY_LEVEL =", value, "batteryLevel");
            return (Criteria) this;
        }

        public Criteria andBatteryLevelNotEqualTo(Float value) {
            addCriterion("BATTERY_LEVEL <>", value, "batteryLevel");
            return (Criteria) this;
        }

        public Criteria andBatteryLevelGreaterThan(Float value) {
            addCriterion("BATTERY_LEVEL >", value, "batteryLevel");
            return (Criteria) this;
        }

        public Criteria andBatteryLevelGreaterThanOrEqualTo(Float value) {
            addCriterion("BATTERY_LEVEL >=", value, "batteryLevel");
            return (Criteria) this;
        }

        public Criteria andBatteryLevelLessThan(Float value) {
            addCriterion("BATTERY_LEVEL <", value, "batteryLevel");
            return (Criteria) this;
        }

        public Criteria andBatteryLevelLessThanOrEqualTo(Float value) {
            addCriterion("BATTERY_LEVEL <=", value, "batteryLevel");
            return (Criteria) this;
        }

        public Criteria andBatteryLevelIn(List<Float> values) {
            addCriterion("BATTERY_LEVEL in", values, "batteryLevel");
            return (Criteria) this;
        }

        public Criteria andBatteryLevelNotIn(List<Float> values) {
            addCriterion("BATTERY_LEVEL not in", values, "batteryLevel");
            return (Criteria) this;
        }

        public Criteria andBatteryLevelBetween(Float value1, Float value2) {
            addCriterion("BATTERY_LEVEL between", value1, value2, "batteryLevel");
            return (Criteria) this;
        }

        public Criteria andBatteryLevelNotBetween(Float value1, Float value2) {
            addCriterion("BATTERY_LEVEL not between", value1, value2, "batteryLevel");
            return (Criteria) this;
        }

        public Criteria andTemperatureIsNull() {
            addCriterion("TEMPERATURE is null");
            return (Criteria) this;
        }

        public Criteria andTemperatureIsNotNull() {
            addCriterion("TEMPERATURE is not null");
            return (Criteria) this;
        }

        public Criteria andTemperatureEqualTo(Float value) {
            addCriterion("TEMPERATURE =", value, "temperature");
            return (Criteria) this;
        }

        public Criteria andTemperatureNotEqualTo(Float value) {
            addCriterion("TEMPERATURE <>", value, "temperature");
            return (Criteria) this;
        }

        public Criteria andTemperatureGreaterThan(Float value) {
            addCriterion("TEMPERATURE >", value, "temperature");
            return (Criteria) this;
        }

        public Criteria andTemperatureGreaterThanOrEqualTo(Float value) {
            addCriterion("TEMPERATURE >=", value, "temperature");
            return (Criteria) this;
        }

        public Criteria andTemperatureLessThan(Float value) {
            addCriterion("TEMPERATURE <", value, "temperature");
            return (Criteria) this;
        }

        public Criteria andTemperatureLessThanOrEqualTo(Float value) {
            addCriterion("TEMPERATURE <=", value, "temperature");
            return (Criteria) this;
        }

        public Criteria andTemperatureIn(List<Float> values) {
            addCriterion("TEMPERATURE in", values, "temperature");
            return (Criteria) this;
        }

        public Criteria andTemperatureNotIn(List<Float> values) {
            addCriterion("TEMPERATURE not in", values, "temperature");
            return (Criteria) this;
        }

        public Criteria andTemperatureBetween(Float value1, Float value2) {
            addCriterion("TEMPERATURE between", value1, value2, "temperature");
            return (Criteria) this;
        }

        public Criteria andTemperatureNotBetween(Float value1, Float value2) {
            addCriterion("TEMPERATURE not between", value1, value2, "temperature");
            return (Criteria) this;
        }

        public Criteria andSignalStrengthIsNull() {
            addCriterion("SIGNAL_STRENGTH is null");
            return (Criteria) this;
        }

        public Criteria andSignalStrengthIsNotNull() {
            addCriterion("SIGNAL_STRENGTH is not null");
            return (Criteria) this;
        }

        public Criteria andSignalStrengthEqualTo(Integer value) {
            addCriterion("SIGNAL_STRENGTH =", value, "signalStrength");
            return (Criteria) this;
        }

        public Criteria andSignalStrengthNotEqualTo(Integer value) {
            addCriterion("SIGNAL_STRENGTH <>", value, "signalStrength");
            return (Criteria) this;
        }

        public Criteria andSignalStrengthGreaterThan(Integer value) {
            addCriterion("SIGNAL_STRENGTH >", value, "signalStrength");
            return (Criteria) this;
        }

        public Criteria andSignalStrengthGreaterThanOrEqualTo(Integer value) {
            addCriterion("SIGNAL_STRENGTH >=", value, "signalStrength");
            return (Criteria) this;
        }

        public Criteria andSignalStrengthLessThan(Integer value) {
            addCriterion("SIGNAL_STRENGTH <", value, "signalStrength");
            return (Criteria) this;
        }

        public Criteria andSignalStrengthLessThanOrEqualTo(Integer value) {
            addCriterion("SIGNAL_STRENGTH <=", value, "signalStrength");
            return (Criteria) this;
        }

        public Criteria andSignalStrengthIn(List<Integer> values) {
            addCriterion("SIGNAL_STRENGTH in", values, "signalStrength");
            return (Criteria) this;
        }

        public Criteria andSignalStrengthNotIn(List<Integer> values) {
            addCriterion("SIGNAL_STRENGTH not in", values, "signalStrength");
            return (Criteria) this;
        }

        public Criteria andSignalStrengthBetween(Integer value1, Integer value2) {
            addCriterion("SIGNAL_STRENGTH between", value1, value2, "signalStrength");
            return (Criteria) this;
        }

        public Criteria andSignalStrengthNotBetween(Integer value1, Integer value2) {
            addCriterion("SIGNAL_STRENGTH not between", value1, value2, "signalStrength");
            return (Criteria) this;
        }

        public Criteria andCommunicationCountIsNull() {
            addCriterion("COMMUNICATION_COUNT is null");
            return (Criteria) this;
        }

        public Criteria andCommunicationCountIsNotNull() {
            addCriterion("COMMUNICATION_COUNT is not null");
            return (Criteria) this;
        }

        public Criteria andCommunicationCountEqualTo(Integer value) {
            addCriterion("COMMUNICATION_COUNT =", value, "communicationCount");
            return (Criteria) this;
        }

        public Criteria andCommunicationCountNotEqualTo(Integer value) {
            addCriterion("COMMUNICATION_COUNT <>", value, "communicationCount");
            return (Criteria) this;
        }

        public Criteria andCommunicationCountGreaterThan(Integer value) {
            addCriterion("COMMUNICATION_COUNT >", value, "communicationCount");
            return (Criteria) this;
        }

        public Criteria andCommunicationCountGreaterThanOrEqualTo(Integer value) {
            addCriterion("COMMUNICATION_COUNT >=", value, "communicationCount");
            return (Criteria) this;
        }

        public Criteria andCommunicationCountLessThan(Integer value) {
            addCriterion("COMMUNICATION_COUNT <", value, "communicationCount");
            return (Criteria) this;
        }

        public Criteria andCommunicationCountLessThanOrEqualTo(Integer value) {
            addCriterion("COMMUNICATION_COUNT <=", value, "communicationCount");
            return (Criteria) this;
        }

        public Criteria andCommunicationCountIn(List<Integer> values) {
            addCriterion("COMMUNICATION_COUNT in", values, "communicationCount");
            return (Criteria) this;
        }

        public Criteria andCommunicationCountNotIn(List<Integer> values) {
            addCriterion("COMMUNICATION_COUNT not in", values, "communicationCount");
            return (Criteria) this;
        }

        public Criteria andCommunicationCountBetween(Integer value1, Integer value2) {
            addCriterion("COMMUNICATION_COUNT between", value1, value2, "communicationCount");
            return (Criteria) this;
        }

        public Criteria andCommunicationCountNotBetween(Integer value1, Integer value2) {
            addCriterion("COMMUNICATION_COUNT not between", value1, value2, "communicationCount");
            return (Criteria) this;
        }

        public Criteria andFailureCountIsNull() {
            addCriterion("FAILURE_COUNT is null");
            return (Criteria) this;
        }

        public Criteria andFailureCountIsNotNull() {
            addCriterion("FAILURE_COUNT is not null");
            return (Criteria) this;
        }

        public Criteria andFailureCountEqualTo(Integer value) {
            addCriterion("FAILURE_COUNT =", value, "failureCount");
            return (Criteria) this;
        }

        public Criteria andFailureCountNotEqualTo(Integer value) {
            addCriterion("FAILURE_COUNT <>", value, "failureCount");
            return (Criteria) this;
        }

        public Criteria andFailureCountGreaterThan(Integer value) {
            addCriterion("FAILURE_COUNT >", value, "failureCount");
            return (Criteria) this;
        }

        public Criteria andFailureCountGreaterThanOrEqualTo(Integer value) {
            addCriterion("FAILURE_COUNT >=", value, "failureCount");
            return (Criteria) this;
        }

        public Criteria andFailureCountLessThan(Integer value) {
            addCriterion("FAILURE_COUNT <", value, "failureCount");
            return (Criteria) this;
        }

        public Criteria andFailureCountLessThanOrEqualTo(Integer value) {
            addCriterion("FAILURE_COUNT <=", value, "failureCount");
            return (Criteria) this;
        }

        public Criteria andFailureCountIn(List<Integer> values) {
            addCriterion("FAILURE_COUNT in", values, "failureCount");
            return (Criteria) this;
        }

        public Criteria andFailureCountNotIn(List<Integer> values) {
            addCriterion("FAILURE_COUNT not in", values, "failureCount");
            return (Criteria) this;
        }

        public Criteria andFailureCountBetween(Integer value1, Integer value2) {
            addCriterion("FAILURE_COUNT between", value1, value2, "failureCount");
            return (Criteria) this;
        }

        public Criteria andFailureCountNotBetween(Integer value1, Integer value2) {
            addCriterion("FAILURE_COUNT not between", value1, value2, "failureCount");
            return (Criteria) this;
        }

        public Criteria andEslCategoryIsNull() {
            addCriterion("ESL_CATEGORY is null");
            return (Criteria) this;
        }

        public Criteria andEslCategoryIsNotNull() {
            addCriterion("ESL_CATEGORY is not null");
            return (Criteria) this;
        }

        public Criteria andEslCategoryEqualTo(String value) {
            addCriterion("ESL_CATEGORY =", value, "eslCategory");
            return (Criteria) this;
        }

        public Criteria andEslCategoryNotEqualTo(String value) {
            addCriterion("ESL_CATEGORY <>", value, "eslCategory");
            return (Criteria) this;
        }

        public Criteria andEslCategoryGreaterThan(String value) {
            addCriterion("ESL_CATEGORY >", value, "eslCategory");
            return (Criteria) this;
        }

        public Criteria andEslCategoryGreaterThanOrEqualTo(String value) {
            addCriterion("ESL_CATEGORY >=", value, "eslCategory");
            return (Criteria) this;
        }

        public Criteria andEslCategoryLessThan(String value) {
            addCriterion("ESL_CATEGORY <", value, "eslCategory");
            return (Criteria) this;
        }

        public Criteria andEslCategoryLessThanOrEqualTo(String value) {
            addCriterion("ESL_CATEGORY <=", value, "eslCategory");
            return (Criteria) this;
        }

        public Criteria andEslCategoryLike(String value) {
            addCriterion("ESL_CATEGORY like", value, "eslCategory");
            return (Criteria) this;
        }

        public Criteria andEslCategoryNotLike(String value) {
            addCriterion("ESL_CATEGORY not like", value, "eslCategory");
            return (Criteria) this;
        }

        public Criteria andEslCategoryIn(List<String> values) {
            addCriterion("ESL_CATEGORY in", values, "eslCategory");
            return (Criteria) this;
        }

        public Criteria andEslCategoryNotIn(List<String> values) {
            addCriterion("ESL_CATEGORY not in", values, "eslCategory");
            return (Criteria) this;
        }

        public Criteria andEslCategoryBetween(String value1, String value2) {
            addCriterion("ESL_CATEGORY between", value1, value2, "eslCategory");
            return (Criteria) this;
        }

        public Criteria andEslCategoryNotBetween(String value1, String value2) {
            addCriterion("ESL_CATEGORY not between", value1, value2, "eslCategory");
            return (Criteria) this;
        }

        public Criteria andEslStatusIsNull() {
            addCriterion("ESL_STATUS is null");
            return (Criteria) this;
        }

        public Criteria andEslStatusIsNotNull() {
            addCriterion("ESL_STATUS is not null");
            return (Criteria) this;
        }

        public Criteria andEslStatusEqualTo(String value) {
            addCriterion("ESL_STATUS =", value, "eslStatus");
            return (Criteria) this;
        }

        public Criteria andEslStatusNotEqualTo(String value) {
            addCriterion("ESL_STATUS <>", value, "eslStatus");
            return (Criteria) this;
        }

        public Criteria andEslStatusGreaterThan(String value) {
            addCriterion("ESL_STATUS >", value, "eslStatus");
            return (Criteria) this;
        }

        public Criteria andEslStatusGreaterThanOrEqualTo(String value) {
            addCriterion("ESL_STATUS >=", value, "eslStatus");
            return (Criteria) this;
        }

        public Criteria andEslStatusLessThan(String value) {
            addCriterion("ESL_STATUS <", value, "eslStatus");
            return (Criteria) this;
        }

        public Criteria andEslStatusLessThanOrEqualTo(String value) {
            addCriterion("ESL_STATUS <=", value, "eslStatus");
            return (Criteria) this;
        }

        public Criteria andEslStatusLike(String value) {
            addCriterion("ESL_STATUS like", value, "eslStatus");
            return (Criteria) this;
        }

        public Criteria andEslStatusNotLike(String value) {
            addCriterion("ESL_STATUS not like", value, "eslStatus");
            return (Criteria) this;
        }

        public Criteria andEslStatusIn(List<String> values) {
            addCriterion("ESL_STATUS in", values, "eslStatus");
            return (Criteria) this;
        }

        public Criteria andEslStatusNotIn(List<String> values) {
            addCriterion("ESL_STATUS not in", values, "eslStatus");
            return (Criteria) this;
        }

        public Criteria andEslStatusBetween(String value1, String value2) {
            addCriterion("ESL_STATUS between", value1, value2, "eslStatus");
            return (Criteria) this;
        }

        public Criteria andEslStatusNotBetween(String value1, String value2) {
            addCriterion("ESL_STATUS not between", value1, value2, "eslStatus");
            return (Criteria) this;
        }

        public Criteria andScreenColorIsNull() {
            addCriterion("SCREEN_COLOR is null");
            return (Criteria) this;
        }

        public Criteria andScreenColorIsNotNull() {
            addCriterion("SCREEN_COLOR is not null");
            return (Criteria) this;
        }

        public Criteria andScreenColorEqualTo(String value) {
            addCriterion("SCREEN_COLOR =", value, "screenColor");
            return (Criteria) this;
        }

        public Criteria andScreenColorNotEqualTo(String value) {
            addCriterion("SCREEN_COLOR <>", value, "screenColor");
            return (Criteria) this;
        }

        public Criteria andScreenColorGreaterThan(String value) {
            addCriterion("SCREEN_COLOR >", value, "screenColor");
            return (Criteria) this;
        }

        public Criteria andScreenColorGreaterThanOrEqualTo(String value) {
            addCriterion("SCREEN_COLOR >=", value, "screenColor");
            return (Criteria) this;
        }

        public Criteria andScreenColorLessThan(String value) {
            addCriterion("SCREEN_COLOR <", value, "screenColor");
            return (Criteria) this;
        }

        public Criteria andScreenColorLessThanOrEqualTo(String value) {
            addCriterion("SCREEN_COLOR <=", value, "screenColor");
            return (Criteria) this;
        }

        public Criteria andScreenColorLike(String value) {
            addCriterion("SCREEN_COLOR like", value, "screenColor");
            return (Criteria) this;
        }

        public Criteria andScreenColorNotLike(String value) {
            addCriterion("SCREEN_COLOR not like", value, "screenColor");
            return (Criteria) this;
        }

        public Criteria andScreenColorIn(List<String> values) {
            addCriterion("SCREEN_COLOR in", values, "screenColor");
            return (Criteria) this;
        }

        public Criteria andScreenColorNotIn(List<String> values) {
            addCriterion("SCREEN_COLOR not in", values, "screenColor");
            return (Criteria) this;
        }

        public Criteria andScreenColorBetween(String value1, String value2) {
            addCriterion("SCREEN_COLOR between", value1, value2, "screenColor");
            return (Criteria) this;
        }

        public Criteria andScreenColorNotBetween(String value1, String value2) {
            addCriterion("SCREEN_COLOR not between", value1, value2, "screenColor");
            return (Criteria) this;
        }

        public Criteria andCommunicationMethodIsNull() {
            addCriterion("COMMUNICATION_METHOD is null");
            return (Criteria) this;
        }

        public Criteria andCommunicationMethodIsNotNull() {
            addCriterion("COMMUNICATION_METHOD is not null");
            return (Criteria) this;
        }

        public Criteria andCommunicationMethodEqualTo(String value) {
            addCriterion("COMMUNICATION_METHOD =", value, "communicationMethod");
            return (Criteria) this;
        }

        public Criteria andCommunicationMethodNotEqualTo(String value) {
            addCriterion("COMMUNICATION_METHOD <>", value, "communicationMethod");
            return (Criteria) this;
        }

        public Criteria andCommunicationMethodGreaterThan(String value) {
            addCriterion("COMMUNICATION_METHOD >", value, "communicationMethod");
            return (Criteria) this;
        }

        public Criteria andCommunicationMethodGreaterThanOrEqualTo(String value) {
            addCriterion("COMMUNICATION_METHOD >=", value, "communicationMethod");
            return (Criteria) this;
        }

        public Criteria andCommunicationMethodLessThan(String value) {
            addCriterion("COMMUNICATION_METHOD <", value, "communicationMethod");
            return (Criteria) this;
        }

        public Criteria andCommunicationMethodLessThanOrEqualTo(String value) {
            addCriterion("COMMUNICATION_METHOD <=", value, "communicationMethod");
            return (Criteria) this;
        }

        public Criteria andCommunicationMethodLike(String value) {
            addCriterion("COMMUNICATION_METHOD like", value, "communicationMethod");
            return (Criteria) this;
        }

        public Criteria andCommunicationMethodNotLike(String value) {
            addCriterion("COMMUNICATION_METHOD not like", value, "communicationMethod");
            return (Criteria) this;
        }

        public Criteria andCommunicationMethodIn(List<String> values) {
            addCriterion("COMMUNICATION_METHOD in", values, "communicationMethod");
            return (Criteria) this;
        }

        public Criteria andCommunicationMethodNotIn(List<String> values) {
            addCriterion("COMMUNICATION_METHOD not in", values, "communicationMethod");
            return (Criteria) this;
        }

        public Criteria andCommunicationMethodBetween(String value1, String value2) {
            addCriterion("COMMUNICATION_METHOD between", value1, value2, "communicationMethod");
            return (Criteria) this;
        }

        public Criteria andCommunicationMethodNotBetween(String value1, String value2) {
            addCriterion("COMMUNICATION_METHOD not between", value1, value2, "communicationMethod");
            return (Criteria) this;
        }

        public Criteria andOperatingTemperatureRangeIsNull() {
            addCriterion("OPERATING_TEMPERATURE_RANGE is null");
            return (Criteria) this;
        }

        public Criteria andOperatingTemperatureRangeIsNotNull() {
            addCriterion("OPERATING_TEMPERATURE_RANGE is not null");
            return (Criteria) this;
        }

        public Criteria andOperatingTemperatureRangeEqualTo(String value) {
            addCriterion("OPERATING_TEMPERATURE_RANGE =", value, "operatingTemperatureRange");
            return (Criteria) this;
        }

        public Criteria andOperatingTemperatureRangeNotEqualTo(String value) {
            addCriterion("OPERATING_TEMPERATURE_RANGE <>", value, "operatingTemperatureRange");
            return (Criteria) this;
        }

        public Criteria andOperatingTemperatureRangeGreaterThan(String value) {
            addCriterion("OPERATING_TEMPERATURE_RANGE >", value, "operatingTemperatureRange");
            return (Criteria) this;
        }

        public Criteria andOperatingTemperatureRangeGreaterThanOrEqualTo(String value) {
            addCriterion("OPERATING_TEMPERATURE_RANGE >=", value, "operatingTemperatureRange");
            return (Criteria) this;
        }

        public Criteria andOperatingTemperatureRangeLessThan(String value) {
            addCriterion("OPERATING_TEMPERATURE_RANGE <", value, "operatingTemperatureRange");
            return (Criteria) this;
        }

        public Criteria andOperatingTemperatureRangeLessThanOrEqualTo(String value) {
            addCriterion("OPERATING_TEMPERATURE_RANGE <=", value, "operatingTemperatureRange");
            return (Criteria) this;
        }

        public Criteria andOperatingTemperatureRangeLike(String value) {
            addCriterion("OPERATING_TEMPERATURE_RANGE like", value, "operatingTemperatureRange");
            return (Criteria) this;
        }

        public Criteria andOperatingTemperatureRangeNotLike(String value) {
            addCriterion("OPERATING_TEMPERATURE_RANGE not like", value, "operatingTemperatureRange");
            return (Criteria) this;
        }

        public Criteria andOperatingTemperatureRangeIn(List<String> values) {
            addCriterion("OPERATING_TEMPERATURE_RANGE in", values, "operatingTemperatureRange");
            return (Criteria) this;
        }

        public Criteria andOperatingTemperatureRangeNotIn(List<String> values) {
            addCriterion("OPERATING_TEMPERATURE_RANGE not in", values, "operatingTemperatureRange");
            return (Criteria) this;
        }

        public Criteria andOperatingTemperatureRangeBetween(String value1, String value2) {
            addCriterion("OPERATING_TEMPERATURE_RANGE between", value1, value2, "operatingTemperatureRange");
            return (Criteria) this;
        }

        public Criteria andOperatingTemperatureRangeNotBetween(String value1, String value2) {
            addCriterion("OPERATING_TEMPERATURE_RANGE not between", value1, value2, "operatingTemperatureRange");
            return (Criteria) this;
        }

        public Criteria andIpRatingIsNull() {
            addCriterion("IP_RATING is null");
            return (Criteria) this;
        }

        public Criteria andIpRatingIsNotNull() {
            addCriterion("IP_RATING is not null");
            return (Criteria) this;
        }

        public Criteria andIpRatingEqualTo(String value) {
            addCriterion("IP_RATING =", value, "ipRating");
            return (Criteria) this;
        }

        public Criteria andIpRatingNotEqualTo(String value) {
            addCriterion("IP_RATING <>", value, "ipRating");
            return (Criteria) this;
        }

        public Criteria andIpRatingGreaterThan(String value) {
            addCriterion("IP_RATING >", value, "ipRating");
            return (Criteria) this;
        }

        public Criteria andIpRatingGreaterThanOrEqualTo(String value) {
            addCriterion("IP_RATING >=", value, "ipRating");
            return (Criteria) this;
        }

        public Criteria andIpRatingLessThan(String value) {
            addCriterion("IP_RATING <", value, "ipRating");
            return (Criteria) this;
        }

        public Criteria andIpRatingLessThanOrEqualTo(String value) {
            addCriterion("IP_RATING <=", value, "ipRating");
            return (Criteria) this;
        }

        public Criteria andIpRatingLike(String value) {
            addCriterion("IP_RATING like", value, "ipRating");
            return (Criteria) this;
        }

        public Criteria andIpRatingNotLike(String value) {
            addCriterion("IP_RATING not like", value, "ipRating");
            return (Criteria) this;
        }

        public Criteria andIpRatingIn(List<String> values) {
            addCriterion("IP_RATING in", values, "ipRating");
            return (Criteria) this;
        }

        public Criteria andIpRatingNotIn(List<String> values) {
            addCriterion("IP_RATING not in", values, "ipRating");
            return (Criteria) this;
        }

        public Criteria andIpRatingBetween(String value1, String value2) {
            addCriterion("IP_RATING between", value1, value2, "ipRating");
            return (Criteria) this;
        }

        public Criteria andIpRatingNotBetween(String value1, String value2) {
            addCriterion("IP_RATING not between", value1, value2, "ipRating");
            return (Criteria) this;
        }

        public Criteria andVersionIsNull() {
            addCriterion("VERSION is null");
            return (Criteria) this;
        }

        public Criteria andVersionIsNotNull() {
            addCriterion("VERSION is not null");
            return (Criteria) this;
        }

        public Criteria andVersionEqualTo(String value) {
            addCriterion("VERSION =", value, "version");
            return (Criteria) this;
        }

        public Criteria andVersionNotEqualTo(String value) {
            addCriterion("VERSION <>", value, "version");
            return (Criteria) this;
        }

        public Criteria andVersionGreaterThan(String value) {
            addCriterion("VERSION >", value, "version");
            return (Criteria) this;
        }

        public Criteria andVersionGreaterThanOrEqualTo(String value) {
            addCriterion("VERSION >=", value, "version");
            return (Criteria) this;
        }

        public Criteria andVersionLessThan(String value) {
            addCriterion("VERSION <", value, "version");
            return (Criteria) this;
        }

        public Criteria andVersionLessThanOrEqualTo(String value) {
            addCriterion("VERSION <=", value, "version");
            return (Criteria) this;
        }

        public Criteria andVersionLike(String value) {
            addCriterion("VERSION like", value, "version");
            return (Criteria) this;
        }

        public Criteria andVersionNotLike(String value) {
            addCriterion("VERSION not like", value, "version");
            return (Criteria) this;
        }

        public Criteria andVersionIn(List<String> values) {
            addCriterion("VERSION in", values, "version");
            return (Criteria) this;
        }

        public Criteria andVersionNotIn(List<String> values) {
            addCriterion("VERSION not in", values, "version");
            return (Criteria) this;
        }

        public Criteria andVersionBetween(String value1, String value2) {
            addCriterion("VERSION between", value1, value2, "version");
            return (Criteria) this;
        }

        public Criteria andVersionNotBetween(String value1, String value2) {
            addCriterion("VERSION not between", value1, value2, "version");
            return (Criteria) this;
        }

        public Criteria andHardwareIsNull() {
            addCriterion("HARDWARE is null");
            return (Criteria) this;
        }

        public Criteria andHardwareIsNotNull() {
            addCriterion("HARDWARE is not null");
            return (Criteria) this;
        }

        public Criteria andHardwareEqualTo(String value) {
            addCriterion("HARDWARE =", value, "hardware");
            return (Criteria) this;
        }

        public Criteria andHardwareNotEqualTo(String value) {
            addCriterion("HARDWARE <>", value, "hardware");
            return (Criteria) this;
        }

        public Criteria andHardwareGreaterThan(String value) {
            addCriterion("HARDWARE >", value, "hardware");
            return (Criteria) this;
        }

        public Criteria andHardwareGreaterThanOrEqualTo(String value) {
            addCriterion("HARDWARE >=", value, "hardware");
            return (Criteria) this;
        }

        public Criteria andHardwareLessThan(String value) {
            addCriterion("HARDWARE <", value, "hardware");
            return (Criteria) this;
        }

        public Criteria andHardwareLessThanOrEqualTo(String value) {
            addCriterion("HARDWARE <=", value, "hardware");
            return (Criteria) this;
        }

        public Criteria andHardwareLike(String value) {
            addCriterion("HARDWARE like", value, "hardware");
            return (Criteria) this;
        }

        public Criteria andHardwareNotLike(String value) {
            addCriterion("HARDWARE not like", value, "hardware");
            return (Criteria) this;
        }

        public Criteria andHardwareIn(List<String> values) {
            addCriterion("HARDWARE in", values, "hardware");
            return (Criteria) this;
        }

        public Criteria andHardwareNotIn(List<String> values) {
            addCriterion("HARDWARE not in", values, "hardware");
            return (Criteria) this;
        }

        public Criteria andHardwareBetween(String value1, String value2) {
            addCriterion("HARDWARE between", value1, value2, "hardware");
            return (Criteria) this;
        }

        public Criteria andHardwareNotBetween(String value1, String value2) {
            addCriterion("HARDWARE not between", value1, value2, "hardware");
            return (Criteria) this;
        }

        public Criteria andStoreCodeIdIsNull() {
            addCriterion("STORE_CODE_ID is null");
            return (Criteria) this;
        }

        public Criteria andStoreCodeIdIsNotNull() {
            addCriterion("STORE_CODE_ID is not null");
            return (Criteria) this;
        }

        public Criteria andStoreCodeIdEqualTo(String value) {
            addCriterion("STORE_CODE_ID =", value, "storeCodeId");
            return (Criteria) this;
        }

        public Criteria andStoreCodeIdNotEqualTo(String value) {
            addCriterion("STORE_CODE_ID <>", value, "storeCodeId");
            return (Criteria) this;
        }

        public Criteria andStoreCodeIdGreaterThan(String value) {
            addCriterion("STORE_CODE_ID >", value, "storeCodeId");
            return (Criteria) this;
        }

        public Criteria andStoreCodeIdGreaterThanOrEqualTo(String value) {
            addCriterion("STORE_CODE_ID >=", value, "storeCodeId");
            return (Criteria) this;
        }

        public Criteria andStoreCodeIdLessThan(String value) {
            addCriterion("STORE_CODE_ID <", value, "storeCodeId");
            return (Criteria) this;
        }

        public Criteria andStoreCodeIdLessThanOrEqualTo(String value) {
            addCriterion("STORE_CODE_ID <=", value, "storeCodeId");
            return (Criteria) this;
        }

        public Criteria andStoreCodeIdLike(String value) {
            addCriterion("STORE_CODE_ID like", value, "storeCodeId");
            return (Criteria) this;
        }

        public Criteria andStoreCodeIdNotLike(String value) {
            addCriterion("STORE_CODE_ID not like", value, "storeCodeId");
            return (Criteria) this;
        }

        public Criteria andStoreCodeIdIn(List<String> values) {
            addCriterion("STORE_CODE_ID in", values, "storeCodeId");
            return (Criteria) this;
        }

        public Criteria andStoreCodeIdNotIn(List<String> values) {
            addCriterion("STORE_CODE_ID not in", values, "storeCodeId");
            return (Criteria) this;
        }

        public Criteria andStoreCodeIdBetween(String value1, String value2) {
            addCriterion("STORE_CODE_ID between", value1, value2, "storeCodeId");
            return (Criteria) this;
        }

        public Criteria andStoreCodeIdNotBetween(String value1, String value2) {
            addCriterion("STORE_CODE_ID not between", value1, value2, "storeCodeId");
            return (Criteria) this;
        }

        public Criteria andBoundProductIdIsNull() {
            addCriterion("BOUND_PRODUCT_ID is null");
            return (Criteria) this;
        }

        public Criteria andBoundProductIdIsNotNull() {
            addCriterion("BOUND_PRODUCT_ID is not null");
            return (Criteria) this;
        }

        public Criteria andBoundProductIdEqualTo(String value) {
            addCriterion("BOUND_PRODUCT_ID =", value, "boundProductId");
            return (Criteria) this;
        }

        public Criteria andBoundProductIdNotEqualTo(String value) {
            addCriterion("BOUND_PRODUCT_ID <>", value, "boundProductId");
            return (Criteria) this;
        }

        public Criteria andBoundProductIdGreaterThan(String value) {
            addCriterion("BOUND_PRODUCT_ID >", value, "boundProductId");
            return (Criteria) this;
        }

        public Criteria andBoundProductIdGreaterThanOrEqualTo(String value) {
            addCriterion("BOUND_PRODUCT_ID >=", value, "boundProductId");
            return (Criteria) this;
        }

        public Criteria andBoundProductIdLessThan(String value) {
            addCriterion("BOUND_PRODUCT_ID <", value, "boundProductId");
            return (Criteria) this;
        }

        public Criteria andBoundProductIdLessThanOrEqualTo(String value) {
            addCriterion("BOUND_PRODUCT_ID <=", value, "boundProductId");
            return (Criteria) this;
        }

        public Criteria andBoundProductIdLike(String value) {
            addCriterion("BOUND_PRODUCT_ID like", value, "boundProductId");
            return (Criteria) this;
        }

        public Criteria andBoundProductIdNotLike(String value) {
            addCriterion("BOUND_PRODUCT_ID not like", value, "boundProductId");
            return (Criteria) this;
        }

        public Criteria andBoundProductIdIn(List<String> values) {
            addCriterion("BOUND_PRODUCT_ID in", values, "boundProductId");
            return (Criteria) this;
        }

        public Criteria andBoundProductIdNotIn(List<String> values) {
            addCriterion("BOUND_PRODUCT_ID not in", values, "boundProductId");
            return (Criteria) this;
        }

        public Criteria andBoundProductIdBetween(String value1, String value2) {
            addCriterion("BOUND_PRODUCT_ID between", value1, value2, "boundProductId");
            return (Criteria) this;
        }

        public Criteria andBoundProductIdNotBetween(String value1, String value2) {
            addCriterion("BOUND_PRODUCT_ID not between", value1, value2, "boundProductId");
            return (Criteria) this;
        }

        public Criteria andEslIdXIsNull() {
            addCriterion("ESL_ID_X is null");
            return (Criteria) this;
        }

        public Criteria andEslIdXIsNotNull() {
            addCriterion("ESL_ID_X is not null");
            return (Criteria) this;
        }

        public Criteria andEslIdXEqualTo(String value) {
            addCriterion("ESL_ID_X =", value, "eslIdX");
            return (Criteria) this;
        }

        public Criteria andEslIdXNotEqualTo(String value) {
            addCriterion("ESL_ID_X <>", value, "eslIdX");
            return (Criteria) this;
        }

        public Criteria andEslIdXGreaterThan(String value) {
            addCriterion("ESL_ID_X >", value, "eslIdX");
            return (Criteria) this;
        }

        public Criteria andEslIdXGreaterThanOrEqualTo(String value) {
            addCriterion("ESL_ID_X >=", value, "eslIdX");
            return (Criteria) this;
        }

        public Criteria andEslIdXLessThan(String value) {
            addCriterion("ESL_ID_X <", value, "eslIdX");
            return (Criteria) this;
        }

        public Criteria andEslIdXLessThanOrEqualTo(String value) {
            addCriterion("ESL_ID_X <=", value, "eslIdX");
            return (Criteria) this;
        }

        public Criteria andEslIdXLike(String value) {
            addCriterion("ESL_ID_X like", value, "eslIdX");
            return (Criteria) this;
        }

        public Criteria andEslIdXNotLike(String value) {
            addCriterion("ESL_ID_X not like", value, "eslIdX");
            return (Criteria) this;
        }

        public Criteria andEslIdXIn(List<String> values) {
            addCriterion("ESL_ID_X in", values, "eslIdX");
            return (Criteria) this;
        }

        public Criteria andEslIdXNotIn(List<String> values) {
            addCriterion("ESL_ID_X not in", values, "eslIdX");
            return (Criteria) this;
        }

        public Criteria andEslIdXBetween(String value1, String value2) {
            addCriterion("ESL_ID_X between", value1, value2, "eslIdX");
            return (Criteria) this;
        }

        public Criteria andEslIdXNotBetween(String value1, String value2) {
            addCriterion("ESL_ID_X not between", value1, value2, "eslIdX");
            return (Criteria) this;
        }

        public Criteria andApSnIsNull() {
            addCriterion("AP_SN is null");
            return (Criteria) this;
        }

        public Criteria andApSnIsNotNull() {
            addCriterion("AP_SN is not null");
            return (Criteria) this;
        }

        public Criteria andApSnEqualTo(String value) {
            addCriterion("AP_SN =", value, "apSn");
            return (Criteria) this;
        }

        public Criteria andApSnNotEqualTo(String value) {
            addCriterion("AP_SN <>", value, "apSn");
            return (Criteria) this;
        }

        public Criteria andApSnGreaterThan(String value) {
            addCriterion("AP_SN >", value, "apSn");
            return (Criteria) this;
        }

        public Criteria andApSnGreaterThanOrEqualTo(String value) {
            addCriterion("AP_SN >=", value, "apSn");
            return (Criteria) this;
        }

        public Criteria andApSnLessThan(String value) {
            addCriterion("AP_SN <", value, "apSn");
            return (Criteria) this;
        }

        public Criteria andApSnLessThanOrEqualTo(String value) {
            addCriterion("AP_SN <=", value, "apSn");
            return (Criteria) this;
        }

        public Criteria andApSnLike(String value) {
            addCriterion("AP_SN like", value, "apSn");
            return (Criteria) this;
        }

        public Criteria andApSnNotLike(String value) {
            addCriterion("AP_SN not like", value, "apSn");
            return (Criteria) this;
        }

        public Criteria andApSnIn(List<String> values) {
            addCriterion("AP_SN in", values, "apSn");
            return (Criteria) this;
        }

        public Criteria andApSnNotIn(List<String> values) {
            addCriterion("AP_SN not in", values, "apSn");
            return (Criteria) this;
        }

        public Criteria andApSnBetween(String value1, String value2) {
            addCriterion("AP_SN between", value1, value2, "apSn");
            return (Criteria) this;
        }

        public Criteria andApSnNotBetween(String value1, String value2) {
            addCriterion("AP_SN not between", value1, value2, "apSn");
            return (Criteria) this;
        }

        public Criteria andApSnIdIsNull() {
            addCriterion("AP_SN_ID is null");
            return (Criteria) this;
        }

        public Criteria andApSnIdIsNotNull() {
            addCriterion("AP_SN_ID is not null");
            return (Criteria) this;
        }

        public Criteria andApSnIdEqualTo(String value) {
            addCriterion("AP_SN_ID =", value, "apSnId");
            return (Criteria) this;
        }

        public Criteria andApSnIdNotEqualTo(String value) {
            addCriterion("AP_SN_ID <>", value, "apSnId");
            return (Criteria) this;
        }

        public Criteria andApSnIdGreaterThan(String value) {
            addCriterion("AP_SN_ID >", value, "apSnId");
            return (Criteria) this;
        }

        public Criteria andApSnIdGreaterThanOrEqualTo(String value) {
            addCriterion("AP_SN_ID >=", value, "apSnId");
            return (Criteria) this;
        }

        public Criteria andApSnIdLessThan(String value) {
            addCriterion("AP_SN_ID <", value, "apSnId");
            return (Criteria) this;
        }

        public Criteria andApSnIdLessThanOrEqualTo(String value) {
            addCriterion("AP_SN_ID <=", value, "apSnId");
            return (Criteria) this;
        }

        public Criteria andApSnIdLike(String value) {
            addCriterion("AP_SN_ID like", value, "apSnId");
            return (Criteria) this;
        }

        public Criteria andApSnIdNotLike(String value) {
            addCriterion("AP_SN_ID not like", value, "apSnId");
            return (Criteria) this;
        }

        public Criteria andApSnIdIn(List<String> values) {
            addCriterion("AP_SN_ID in", values, "apSnId");
            return (Criteria) this;
        }

        public Criteria andApSnIdNotIn(List<String> values) {
            addCriterion("AP_SN_ID not in", values, "apSnId");
            return (Criteria) this;
        }

        public Criteria andApSnIdBetween(String value1, String value2) {
            addCriterion("AP_SN_ID between", value1, value2, "apSnId");
            return (Criteria) this;
        }

        public Criteria andApSnIdNotBetween(String value1, String value2) {
            addCriterion("AP_SN_ID not between", value1, value2, "apSnId");
            return (Criteria) this;
        }

        public Criteria andUpc1IsNull() {
            addCriterion("UPC1 is null");
            return (Criteria) this;
        }

        public Criteria andUpc1IsNotNull() {
            addCriterion("UPC1 is not null");
            return (Criteria) this;
        }

        public Criteria andUpc1EqualTo(String value) {
            addCriterion("UPC1 =", value, "upc1");
            return (Criteria) this;
        }

        public Criteria andUpc1NotEqualTo(String value) {
            addCriterion("UPC1 <>", value, "upc1");
            return (Criteria) this;
        }

        public Criteria andUpc1GreaterThan(String value) {
            addCriterion("UPC1 >", value, "upc1");
            return (Criteria) this;
        }

        public Criteria andUpc1GreaterThanOrEqualTo(String value) {
            addCriterion("UPC1 >=", value, "upc1");
            return (Criteria) this;
        }

        public Criteria andUpc1LessThan(String value) {
            addCriterion("UPC1 <", value, "upc1");
            return (Criteria) this;
        }

        public Criteria andUpc1LessThanOrEqualTo(String value) {
            addCriterion("UPC1 <=", value, "upc1");
            return (Criteria) this;
        }

        public Criteria andUpc1Like(String value) {
            addCriterion("UPC1 like", value, "upc1");
            return (Criteria) this;
        }

        public Criteria andUpc1NotLike(String value) {
            addCriterion("UPC1 not like", value, "upc1");
            return (Criteria) this;
        }

        public Criteria andUpc1In(List<String> values) {
            addCriterion("UPC1 in", values, "upc1");
            return (Criteria) this;
        }

        public Criteria andUpc1NotIn(List<String> values) {
            addCriterion("UPC1 not in", values, "upc1");
            return (Criteria) this;
        }

        public Criteria andUpc1Between(String value1, String value2) {
            addCriterion("UPC1 between", value1, value2, "upc1");
            return (Criteria) this;
        }

        public Criteria andUpc1NotBetween(String value1, String value2) {
            addCriterion("UPC1 not between", value1, value2, "upc1");
            return (Criteria) this;
        }

        public Criteria andUpc2IsNull() {
            addCriterion("UPC2 is null");
            return (Criteria) this;
        }

        public Criteria andUpc2IsNotNull() {
            addCriterion("UPC2 is not null");
            return (Criteria) this;
        }

        public Criteria andUpc2EqualTo(String value) {
            addCriterion("UPC2 =", value, "upc2");
            return (Criteria) this;
        }

        public Criteria andUpc2NotEqualTo(String value) {
            addCriterion("UPC2 <>", value, "upc2");
            return (Criteria) this;
        }

        public Criteria andUpc2GreaterThan(String value) {
            addCriterion("UPC2 >", value, "upc2");
            return (Criteria) this;
        }

        public Criteria andUpc2GreaterThanOrEqualTo(String value) {
            addCriterion("UPC2 >=", value, "upc2");
            return (Criteria) this;
        }

        public Criteria andUpc2LessThan(String value) {
            addCriterion("UPC2 <", value, "upc2");
            return (Criteria) this;
        }

        public Criteria andUpc2LessThanOrEqualTo(String value) {
            addCriterion("UPC2 <=", value, "upc2");
            return (Criteria) this;
        }

        public Criteria andUpc2Like(String value) {
            addCriterion("UPC2 like", value, "upc2");
            return (Criteria) this;
        }

        public Criteria andUpc2NotLike(String value) {
            addCriterion("UPC2 not like", value, "upc2");
            return (Criteria) this;
        }

        public Criteria andUpc2In(List<String> values) {
            addCriterion("UPC2 in", values, "upc2");
            return (Criteria) this;
        }

        public Criteria andUpc2NotIn(List<String> values) {
            addCriterion("UPC2 not in", values, "upc2");
            return (Criteria) this;
        }

        public Criteria andUpc2Between(String value1, String value2) {
            addCriterion("UPC2 between", value1, value2, "upc2");
            return (Criteria) this;
        }

        public Criteria andUpc2NotBetween(String value1, String value2) {
            addCriterion("UPC2 not between", value1, value2, "upc2");
            return (Criteria) this;
        }

        public Criteria andUpc3IsNull() {
            addCriterion("UPC3 is null");
            return (Criteria) this;
        }

        public Criteria andUpc3IsNotNull() {
            addCriterion("UPC3 is not null");
            return (Criteria) this;
        }

        public Criteria andUpc3EqualTo(String value) {
            addCriterion("UPC3 =", value, "upc3");
            return (Criteria) this;
        }

        public Criteria andUpc3NotEqualTo(String value) {
            addCriterion("UPC3 <>", value, "upc3");
            return (Criteria) this;
        }

        public Criteria andUpc3GreaterThan(String value) {
            addCriterion("UPC3 >", value, "upc3");
            return (Criteria) this;
        }

        public Criteria andUpc3GreaterThanOrEqualTo(String value) {
            addCriterion("UPC3 >=", value, "upc3");
            return (Criteria) this;
        }

        public Criteria andUpc3LessThan(String value) {
            addCriterion("UPC3 <", value, "upc3");
            return (Criteria) this;
        }

        public Criteria andUpc3LessThanOrEqualTo(String value) {
            addCriterion("UPC3 <=", value, "upc3");
            return (Criteria) this;
        }

        public Criteria andUpc3Like(String value) {
            addCriterion("UPC3 like", value, "upc3");
            return (Criteria) this;
        }

        public Criteria andUpc3NotLike(String value) {
            addCriterion("UPC3 not like", value, "upc3");
            return (Criteria) this;
        }

        public Criteria andUpc3In(List<String> values) {
            addCriterion("UPC3 in", values, "upc3");
            return (Criteria) this;
        }

        public Criteria andUpc3NotIn(List<String> values) {
            addCriterion("UPC3 not in", values, "upc3");
            return (Criteria) this;
        }

        public Criteria andUpc3Between(String value1, String value2) {
            addCriterion("UPC3 between", value1, value2, "upc3");
            return (Criteria) this;
        }

        public Criteria andUpc3NotBetween(String value1, String value2) {
            addCriterion("UPC3 not between", value1, value2, "upc3");
            return (Criteria) this;
        }

        public Criteria andUpc4IsNull() {
            addCriterion("UPC4 is null");
            return (Criteria) this;
        }

        public Criteria andUpc4IsNotNull() {
            addCriterion("UPC4 is not null");
            return (Criteria) this;
        }

        public Criteria andUpc4EqualTo(String value) {
            addCriterion("UPC4 =", value, "upc4");
            return (Criteria) this;
        }

        public Criteria andUpc4NotEqualTo(String value) {
            addCriterion("UPC4 <>", value, "upc4");
            return (Criteria) this;
        }

        public Criteria andUpc4GreaterThan(String value) {
            addCriterion("UPC4 >", value, "upc4");
            return (Criteria) this;
        }

        public Criteria andUpc4GreaterThanOrEqualTo(String value) {
            addCriterion("UPC4 >=", value, "upc4");
            return (Criteria) this;
        }

        public Criteria andUpc4LessThan(String value) {
            addCriterion("UPC4 <", value, "upc4");
            return (Criteria) this;
        }

        public Criteria andUpc4LessThanOrEqualTo(String value) {
            addCriterion("UPC4 <=", value, "upc4");
            return (Criteria) this;
        }

        public Criteria andUpc4Like(String value) {
            addCriterion("UPC4 like", value, "upc4");
            return (Criteria) this;
        }

        public Criteria andUpc4NotLike(String value) {
            addCriterion("UPC4 not like", value, "upc4");
            return (Criteria) this;
        }

        public Criteria andUpc4In(List<String> values) {
            addCriterion("UPC4 in", values, "upc4");
            return (Criteria) this;
        }

        public Criteria andUpc4NotIn(List<String> values) {
            addCriterion("UPC4 not in", values, "upc4");
            return (Criteria) this;
        }

        public Criteria andUpc4Between(String value1, String value2) {
            addCriterion("UPC4 between", value1, value2, "upc4");
            return (Criteria) this;
        }

        public Criteria andUpc4NotBetween(String value1, String value2) {
            addCriterion("UPC4 not between", value1, value2, "upc4");
            return (Criteria) this;
        }

        public Criteria andNameIsNull() {
            addCriterion("NAME is null");
            return (Criteria) this;
        }

        public Criteria andNameIsNotNull() {
            addCriterion("NAME is not null");
            return (Criteria) this;
        }

        public Criteria andNameEqualTo(String value) {
            addCriterion("NAME =", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotEqualTo(String value) {
            addCriterion("NAME <>", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameGreaterThan(String value) {
            addCriterion("NAME >", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameGreaterThanOrEqualTo(String value) {
            addCriterion("NAME >=", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameLessThan(String value) {
            addCriterion("NAME <", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameLessThanOrEqualTo(String value) {
            addCriterion("NAME <=", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameLike(String value) {
            addCriterion("NAME like", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotLike(String value) {
            addCriterion("NAME not like", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameIn(List<String> values) {
            addCriterion("NAME in", values, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotIn(List<String> values) {
            addCriterion("NAME not in", values, "name");
            return (Criteria) this;
        }

        public Criteria andNameBetween(String value1, String value2) {
            addCriterion("NAME between", value1, value2, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotBetween(String value1, String value2) {
            addCriterion("NAME not between", value1, value2, "name");
            return (Criteria) this;
        }

        public Criteria andCodeIsNull() {
            addCriterion("CODE is null");
            return (Criteria) this;
        }

        public Criteria andCodeIsNotNull() {
            addCriterion("CODE is not null");
            return (Criteria) this;
        }

        public Criteria andCodeEqualTo(String value) {
            addCriterion("CODE =", value, "code");
            return (Criteria) this;
        }

        public Criteria andCodeNotEqualTo(String value) {
            addCriterion("CODE <>", value, "code");
            return (Criteria) this;
        }

        public Criteria andCodeGreaterThan(String value) {
            addCriterion("CODE >", value, "code");
            return (Criteria) this;
        }

        public Criteria andCodeGreaterThanOrEqualTo(String value) {
            addCriterion("CODE >=", value, "code");
            return (Criteria) this;
        }

        public Criteria andCodeLessThan(String value) {
            addCriterion("CODE <", value, "code");
            return (Criteria) this;
        }

        public Criteria andCodeLessThanOrEqualTo(String value) {
            addCriterion("CODE <=", value, "code");
            return (Criteria) this;
        }

        public Criteria andCodeLike(String value) {
            addCriterion("CODE like", value, "code");
            return (Criteria) this;
        }

        public Criteria andCodeNotLike(String value) {
            addCriterion("CODE not like", value, "code");
            return (Criteria) this;
        }

        public Criteria andCodeIn(List<String> values) {
            addCriterion("CODE in", values, "code");
            return (Criteria) this;
        }

        public Criteria andCodeNotIn(List<String> values) {
            addCriterion("CODE not in", values, "code");
            return (Criteria) this;
        }

        public Criteria andCodeBetween(String value1, String value2) {
            addCriterion("CODE between", value1, value2, "code");
            return (Criteria) this;
        }

        public Criteria andCodeNotBetween(String value1, String value2) {
            addCriterion("CODE not between", value1, value2, "code");
            return (Criteria) this;
        }

        public Criteria andF01IsNull() {
            addCriterion("F_01 is null");
            return (Criteria) this;
        }

        public Criteria andF01IsNotNull() {
            addCriterion("F_01 is not null");
            return (Criteria) this;
        }

        public Criteria andF01EqualTo(String value) {
            addCriterion("F_01 =", value, "f01");
            return (Criteria) this;
        }

        public Criteria andF01NotEqualTo(String value) {
            addCriterion("F_01 <>", value, "f01");
            return (Criteria) this;
        }

        public Criteria andF01GreaterThan(String value) {
            addCriterion("F_01 >", value, "f01");
            return (Criteria) this;
        }

        public Criteria andF01GreaterThanOrEqualTo(String value) {
            addCriterion("F_01 >=", value, "f01");
            return (Criteria) this;
        }

        public Criteria andF01LessThan(String value) {
            addCriterion("F_01 <", value, "f01");
            return (Criteria) this;
        }

        public Criteria andF01LessThanOrEqualTo(String value) {
            addCriterion("F_01 <=", value, "f01");
            return (Criteria) this;
        }

        public Criteria andF01Like(String value) {
            addCriterion("F_01 like", value, "f01");
            return (Criteria) this;
        }

        public Criteria andF01NotLike(String value) {
            addCriterion("F_01 not like", value, "f01");
            return (Criteria) this;
        }

        public Criteria andF01In(List<String> values) {
            addCriterion("F_01 in", values, "f01");
            return (Criteria) this;
        }

        public Criteria andF01NotIn(List<String> values) {
            addCriterion("F_01 not in", values, "f01");
            return (Criteria) this;
        }

        public Criteria andF01Between(String value1, String value2) {
            addCriterion("F_01 between", value1, value2, "f01");
            return (Criteria) this;
        }

        public Criteria andF01NotBetween(String value1, String value2) {
            addCriterion("F_01 not between", value1, value2, "f01");
            return (Criteria) this;
        }

        public Criteria andF02IsNull() {
            addCriterion("F_02 is null");
            return (Criteria) this;
        }

        public Criteria andF02IsNotNull() {
            addCriterion("F_02 is not null");
            return (Criteria) this;
        }

        public Criteria andF02EqualTo(String value) {
            addCriterion("F_02 =", value, "f02");
            return (Criteria) this;
        }

        public Criteria andF02NotEqualTo(String value) {
            addCriterion("F_02 <>", value, "f02");
            return (Criteria) this;
        }

        public Criteria andF02GreaterThan(String value) {
            addCriterion("F_02 >", value, "f02");
            return (Criteria) this;
        }

        public Criteria andF02GreaterThanOrEqualTo(String value) {
            addCriterion("F_02 >=", value, "f02");
            return (Criteria) this;
        }

        public Criteria andF02LessThan(String value) {
            addCriterion("F_02 <", value, "f02");
            return (Criteria) this;
        }

        public Criteria andF02LessThanOrEqualTo(String value) {
            addCriterion("F_02 <=", value, "f02");
            return (Criteria) this;
        }

        public Criteria andF02Like(String value) {
            addCriterion("F_02 like", value, "f02");
            return (Criteria) this;
        }

        public Criteria andF02NotLike(String value) {
            addCriterion("F_02 not like", value, "f02");
            return (Criteria) this;
        }

        public Criteria andF02In(List<String> values) {
            addCriterion("F_02 in", values, "f02");
            return (Criteria) this;
        }

        public Criteria andF02NotIn(List<String> values) {
            addCriterion("F_02 not in", values, "f02");
            return (Criteria) this;
        }

        public Criteria andF02Between(String value1, String value2) {
            addCriterion("F_02 between", value1, value2, "f02");
            return (Criteria) this;
        }

        public Criteria andF02NotBetween(String value1, String value2) {
            addCriterion("F_02 not between", value1, value2, "f02");
            return (Criteria) this;
        }

        public Criteria andF03IsNull() {
            addCriterion("F_03 is null");
            return (Criteria) this;
        }

        public Criteria andF03IsNotNull() {
            addCriterion("F_03 is not null");
            return (Criteria) this;
        }

        public Criteria andF03EqualTo(String value) {
            addCriterion("F_03 =", value, "f03");
            return (Criteria) this;
        }

        public Criteria andF03NotEqualTo(String value) {
            addCriterion("F_03 <>", value, "f03");
            return (Criteria) this;
        }

        public Criteria andF03GreaterThan(String value) {
            addCriterion("F_03 >", value, "f03");
            return (Criteria) this;
        }

        public Criteria andF03GreaterThanOrEqualTo(String value) {
            addCriterion("F_03 >=", value, "f03");
            return (Criteria) this;
        }

        public Criteria andF03LessThan(String value) {
            addCriterion("F_03 <", value, "f03");
            return (Criteria) this;
        }

        public Criteria andF03LessThanOrEqualTo(String value) {
            addCriterion("F_03 <=", value, "f03");
            return (Criteria) this;
        }

        public Criteria andF03Like(String value) {
            addCriterion("F_03 like", value, "f03");
            return (Criteria) this;
        }

        public Criteria andF03NotLike(String value) {
            addCriterion("F_03 not like", value, "f03");
            return (Criteria) this;
        }

        public Criteria andF03In(List<String> values) {
            addCriterion("F_03 in", values, "f03");
            return (Criteria) this;
        }

        public Criteria andF03NotIn(List<String> values) {
            addCriterion("F_03 not in", values, "f03");
            return (Criteria) this;
        }

        public Criteria andF03Between(String value1, String value2) {
            addCriterion("F_03 between", value1, value2, "f03");
            return (Criteria) this;
        }

        public Criteria andF03NotBetween(String value1, String value2) {
            addCriterion("F_03 not between", value1, value2, "f03");
            return (Criteria) this;
        }

        public Criteria andF04IsNull() {
            addCriterion("F_04 is null");
            return (Criteria) this;
        }

        public Criteria andF04IsNotNull() {
            addCriterion("F_04 is not null");
            return (Criteria) this;
        }

        public Criteria andF04EqualTo(String value) {
            addCriterion("F_04 =", value, "f04");
            return (Criteria) this;
        }

        public Criteria andF04NotEqualTo(String value) {
            addCriterion("F_04 <>", value, "f04");
            return (Criteria) this;
        }

        public Criteria andF04GreaterThan(String value) {
            addCriterion("F_04 >", value, "f04");
            return (Criteria) this;
        }

        public Criteria andF04GreaterThanOrEqualTo(String value) {
            addCriterion("F_04 >=", value, "f04");
            return (Criteria) this;
        }

        public Criteria andF04LessThan(String value) {
            addCriterion("F_04 <", value, "f04");
            return (Criteria) this;
        }

        public Criteria andF04LessThanOrEqualTo(String value) {
            addCriterion("F_04 <=", value, "f04");
            return (Criteria) this;
        }

        public Criteria andF04Like(String value) {
            addCriterion("F_04 like", value, "f04");
            return (Criteria) this;
        }

        public Criteria andF04NotLike(String value) {
            addCriterion("F_04 not like", value, "f04");
            return (Criteria) this;
        }

        public Criteria andF04In(List<String> values) {
            addCriterion("F_04 in", values, "f04");
            return (Criteria) this;
        }

        public Criteria andF04NotIn(List<String> values) {
            addCriterion("F_04 not in", values, "f04");
            return (Criteria) this;
        }

        public Criteria andF04Between(String value1, String value2) {
            addCriterion("F_04 between", value1, value2, "f04");
            return (Criteria) this;
        }

        public Criteria andF04NotBetween(String value1, String value2) {
            addCriterion("F_04 not between", value1, value2, "f04");
            return (Criteria) this;
        }

        public Criteria andF05IsNull() {
            addCriterion("F_05 is null");
            return (Criteria) this;
        }

        public Criteria andF05IsNotNull() {
            addCriterion("F_05 is not null");
            return (Criteria) this;
        }

        public Criteria andF05EqualTo(String value) {
            addCriterion("F_05 =", value, "f05");
            return (Criteria) this;
        }

        public Criteria andF05NotEqualTo(String value) {
            addCriterion("F_05 <>", value, "f05");
            return (Criteria) this;
        }

        public Criteria andF05GreaterThan(String value) {
            addCriterion("F_05 >", value, "f05");
            return (Criteria) this;
        }

        public Criteria andF05GreaterThanOrEqualTo(String value) {
            addCriterion("F_05 >=", value, "f05");
            return (Criteria) this;
        }

        public Criteria andF05LessThan(String value) {
            addCriterion("F_05 <", value, "f05");
            return (Criteria) this;
        }

        public Criteria andF05LessThanOrEqualTo(String value) {
            addCriterion("F_05 <=", value, "f05");
            return (Criteria) this;
        }

        public Criteria andF05Like(String value) {
            addCriterion("F_05 like", value, "f05");
            return (Criteria) this;
        }

        public Criteria andF05NotLike(String value) {
            addCriterion("F_05 not like", value, "f05");
            return (Criteria) this;
        }

        public Criteria andF05In(List<String> values) {
            addCriterion("F_05 in", values, "f05");
            return (Criteria) this;
        }

        public Criteria andF05NotIn(List<String> values) {
            addCriterion("F_05 not in", values, "f05");
            return (Criteria) this;
        }

        public Criteria andF05Between(String value1, String value2) {
            addCriterion("F_05 between", value1, value2, "f05");
            return (Criteria) this;
        }

        public Criteria andF05NotBetween(String value1, String value2) {
            addCriterion("F_05 not between", value1, value2, "f05");
            return (Criteria) this;
        }

        public Criteria andF06IsNull() {
            addCriterion("F_06 is null");
            return (Criteria) this;
        }

        public Criteria andF06IsNotNull() {
            addCriterion("F_06 is not null");
            return (Criteria) this;
        }

        public Criteria andF06EqualTo(String value) {
            addCriterion("F_06 =", value, "f06");
            return (Criteria) this;
        }

        public Criteria andF06NotEqualTo(String value) {
            addCriterion("F_06 <>", value, "f06");
            return (Criteria) this;
        }

        public Criteria andF06GreaterThan(String value) {
            addCriterion("F_06 >", value, "f06");
            return (Criteria) this;
        }

        public Criteria andF06GreaterThanOrEqualTo(String value) {
            addCriterion("F_06 >=", value, "f06");
            return (Criteria) this;
        }

        public Criteria andF06LessThan(String value) {
            addCriterion("F_06 <", value, "f06");
            return (Criteria) this;
        }

        public Criteria andF06LessThanOrEqualTo(String value) {
            addCriterion("F_06 <=", value, "f06");
            return (Criteria) this;
        }

        public Criteria andF06Like(String value) {
            addCriterion("F_06 like", value, "f06");
            return (Criteria) this;
        }

        public Criteria andF06NotLike(String value) {
            addCriterion("F_06 not like", value, "f06");
            return (Criteria) this;
        }

        public Criteria andF06In(List<String> values) {
            addCriterion("F_06 in", values, "f06");
            return (Criteria) this;
        }

        public Criteria andF06NotIn(List<String> values) {
            addCriterion("F_06 not in", values, "f06");
            return (Criteria) this;
        }

        public Criteria andF06Between(String value1, String value2) {
            addCriterion("F_06 between", value1, value2, "f06");
            return (Criteria) this;
        }

        public Criteria andF06NotBetween(String value1, String value2) {
            addCriterion("F_06 not between", value1, value2, "f06");
            return (Criteria) this;
        }

        public Criteria andF07IsNull() {
            addCriterion("F_07 is null");
            return (Criteria) this;
        }

        public Criteria andF07IsNotNull() {
            addCriterion("F_07 is not null");
            return (Criteria) this;
        }

        public Criteria andF07EqualTo(String value) {
            addCriterion("F_07 =", value, "f07");
            return (Criteria) this;
        }

        public Criteria andF07NotEqualTo(String value) {
            addCriterion("F_07 <>", value, "f07");
            return (Criteria) this;
        }

        public Criteria andF07GreaterThan(String value) {
            addCriterion("F_07 >", value, "f07");
            return (Criteria) this;
        }

        public Criteria andF07GreaterThanOrEqualTo(String value) {
            addCriterion("F_07 >=", value, "f07");
            return (Criteria) this;
        }

        public Criteria andF07LessThan(String value) {
            addCriterion("F_07 <", value, "f07");
            return (Criteria) this;
        }

        public Criteria andF07LessThanOrEqualTo(String value) {
            addCriterion("F_07 <=", value, "f07");
            return (Criteria) this;
        }

        public Criteria andF07Like(String value) {
            addCriterion("F_07 like", value, "f07");
            return (Criteria) this;
        }

        public Criteria andF07NotLike(String value) {
            addCriterion("F_07 not like", value, "f07");
            return (Criteria) this;
        }

        public Criteria andF07In(List<String> values) {
            addCriterion("F_07 in", values, "f07");
            return (Criteria) this;
        }

        public Criteria andF07NotIn(List<String> values) {
            addCriterion("F_07 not in", values, "f07");
            return (Criteria) this;
        }

        public Criteria andF07Between(String value1, String value2) {
            addCriterion("F_07 between", value1, value2, "f07");
            return (Criteria) this;
        }

        public Criteria andF07NotBetween(String value1, String value2) {
            addCriterion("F_07 not between", value1, value2, "f07");
            return (Criteria) this;
        }

        public Criteria andF08IsNull() {
            addCriterion("F_08 is null");
            return (Criteria) this;
        }

        public Criteria andF08IsNotNull() {
            addCriterion("F_08 is not null");
            return (Criteria) this;
        }

        public Criteria andF08EqualTo(String value) {
            addCriterion("F_08 =", value, "f08");
            return (Criteria) this;
        }

        public Criteria andF08NotEqualTo(String value) {
            addCriterion("F_08 <>", value, "f08");
            return (Criteria) this;
        }

        public Criteria andF08GreaterThan(String value) {
            addCriterion("F_08 >", value, "f08");
            return (Criteria) this;
        }

        public Criteria andF08GreaterThanOrEqualTo(String value) {
            addCriterion("F_08 >=", value, "f08");
            return (Criteria) this;
        }

        public Criteria andF08LessThan(String value) {
            addCriterion("F_08 <", value, "f08");
            return (Criteria) this;
        }

        public Criteria andF08LessThanOrEqualTo(String value) {
            addCriterion("F_08 <=", value, "f08");
            return (Criteria) this;
        }

        public Criteria andF08Like(String value) {
            addCriterion("F_08 like", value, "f08");
            return (Criteria) this;
        }

        public Criteria andF08NotLike(String value) {
            addCriterion("F_08 not like", value, "f08");
            return (Criteria) this;
        }

        public Criteria andF08In(List<String> values) {
            addCriterion("F_08 in", values, "f08");
            return (Criteria) this;
        }

        public Criteria andF08NotIn(List<String> values) {
            addCriterion("F_08 not in", values, "f08");
            return (Criteria) this;
        }

        public Criteria andF08Between(String value1, String value2) {
            addCriterion("F_08 between", value1, value2, "f08");
            return (Criteria) this;
        }

        public Criteria andF08NotBetween(String value1, String value2) {
            addCriterion("F_08 not between", value1, value2, "f08");
            return (Criteria) this;
        }

        public Criteria andF09IsNull() {
            addCriterion("F_09 is null");
            return (Criteria) this;
        }

        public Criteria andF09IsNotNull() {
            addCriterion("F_09 is not null");
            return (Criteria) this;
        }

        public Criteria andF09EqualTo(String value) {
            addCriterion("F_09 =", value, "f09");
            return (Criteria) this;
        }

        public Criteria andF09NotEqualTo(String value) {
            addCriterion("F_09 <>", value, "f09");
            return (Criteria) this;
        }

        public Criteria andF09GreaterThan(String value) {
            addCriterion("F_09 >", value, "f09");
            return (Criteria) this;
        }

        public Criteria andF09GreaterThanOrEqualTo(String value) {
            addCriterion("F_09 >=", value, "f09");
            return (Criteria) this;
        }

        public Criteria andF09LessThan(String value) {
            addCriterion("F_09 <", value, "f09");
            return (Criteria) this;
        }

        public Criteria andF09LessThanOrEqualTo(String value) {
            addCriterion("F_09 <=", value, "f09");
            return (Criteria) this;
        }

        public Criteria andF09Like(String value) {
            addCriterion("F_09 like", value, "f09");
            return (Criteria) this;
        }

        public Criteria andF09NotLike(String value) {
            addCriterion("F_09 not like", value, "f09");
            return (Criteria) this;
        }

        public Criteria andF09In(List<String> values) {
            addCriterion("F_09 in", values, "f09");
            return (Criteria) this;
        }

        public Criteria andF09NotIn(List<String> values) {
            addCriterion("F_09 not in", values, "f09");
            return (Criteria) this;
        }

        public Criteria andF09Between(String value1, String value2) {
            addCriterion("F_09 between", value1, value2, "f09");
            return (Criteria) this;
        }

        public Criteria andF09NotBetween(String value1, String value2) {
            addCriterion("F_09 not between", value1, value2, "f09");
            return (Criteria) this;
        }

        public Criteria andF10IsNull() {
            addCriterion("F_10 is null");
            return (Criteria) this;
        }

        public Criteria andF10IsNotNull() {
            addCriterion("F_10 is not null");
            return (Criteria) this;
        }

        public Criteria andF10EqualTo(String value) {
            addCriterion("F_10 =", value, "f10");
            return (Criteria) this;
        }

        public Criteria andF10NotEqualTo(String value) {
            addCriterion("F_10 <>", value, "f10");
            return (Criteria) this;
        }

        public Criteria andF10GreaterThan(String value) {
            addCriterion("F_10 >", value, "f10");
            return (Criteria) this;
        }

        public Criteria andF10GreaterThanOrEqualTo(String value) {
            addCriterion("F_10 >=", value, "f10");
            return (Criteria) this;
        }

        public Criteria andF10LessThan(String value) {
            addCriterion("F_10 <", value, "f10");
            return (Criteria) this;
        }

        public Criteria andF10LessThanOrEqualTo(String value) {
            addCriterion("F_10 <=", value, "f10");
            return (Criteria) this;
        }

        public Criteria andF10Like(String value) {
            addCriterion("F_10 like", value, "f10");
            return (Criteria) this;
        }

        public Criteria andF10NotLike(String value) {
            addCriterion("F_10 not like", value, "f10");
            return (Criteria) this;
        }

        public Criteria andF10In(List<String> values) {
            addCriterion("F_10 in", values, "f10");
            return (Criteria) this;
        }

        public Criteria andF10NotIn(List<String> values) {
            addCriterion("F_10 not in", values, "f10");
            return (Criteria) this;
        }

        public Criteria andF10Between(String value1, String value2) {
            addCriterion("F_10 between", value1, value2, "f10");
            return (Criteria) this;
        }

        public Criteria andF10NotBetween(String value1, String value2) {
            addCriterion("F_10 not between", value1, value2, "f10");
            return (Criteria) this;
        }

        public Criteria andF11IsNull() {
            addCriterion("F_11 is null");
            return (Criteria) this;
        }

        public Criteria andF11IsNotNull() {
            addCriterion("F_11 is not null");
            return (Criteria) this;
        }

        public Criteria andF11EqualTo(String value) {
            addCriterion("F_11 =", value, "f11");
            return (Criteria) this;
        }

        public Criteria andF11NotEqualTo(String value) {
            addCriterion("F_11 <>", value, "f11");
            return (Criteria) this;
        }

        public Criteria andF11GreaterThan(String value) {
            addCriterion("F_11 >", value, "f11");
            return (Criteria) this;
        }

        public Criteria andF11GreaterThanOrEqualTo(String value) {
            addCriterion("F_11 >=", value, "f11");
            return (Criteria) this;
        }

        public Criteria andF11LessThan(String value) {
            addCriterion("F_11 <", value, "f11");
            return (Criteria) this;
        }

        public Criteria andF11LessThanOrEqualTo(String value) {
            addCriterion("F_11 <=", value, "f11");
            return (Criteria) this;
        }

        public Criteria andF11Like(String value) {
            addCriterion("F_11 like", value, "f11");
            return (Criteria) this;
        }

        public Criteria andF11NotLike(String value) {
            addCriterion("F_11 not like", value, "f11");
            return (Criteria) this;
        }

        public Criteria andF11In(List<String> values) {
            addCriterion("F_11 in", values, "f11");
            return (Criteria) this;
        }

        public Criteria andF11NotIn(List<String> values) {
            addCriterion("F_11 not in", values, "f11");
            return (Criteria) this;
        }

        public Criteria andF11Between(String value1, String value2) {
            addCriterion("F_11 between", value1, value2, "f11");
            return (Criteria) this;
        }

        public Criteria andF11NotBetween(String value1, String value2) {
            addCriterion("F_11 not between", value1, value2, "f11");
            return (Criteria) this;
        }

        public Criteria andF20IsNull() {
            addCriterion("F_20 is null");
            return (Criteria) this;
        }

        public Criteria andF20IsNotNull() {
            addCriterion("F_20 is not null");
            return (Criteria) this;
        }

        public Criteria andF20EqualTo(String value) {
            addCriterion("F_20 =", value, "f20");
            return (Criteria) this;
        }

        public Criteria andF20NotEqualTo(String value) {
            addCriterion("F_20 <>", value, "f20");
            return (Criteria) this;
        }

        public Criteria andF20GreaterThan(String value) {
            addCriterion("F_20 >", value, "f20");
            return (Criteria) this;
        }

        public Criteria andF20GreaterThanOrEqualTo(String value) {
            addCriterion("F_20 >=", value, "f20");
            return (Criteria) this;
        }

        public Criteria andF20LessThan(String value) {
            addCriterion("F_20 <", value, "f20");
            return (Criteria) this;
        }

        public Criteria andF20LessThanOrEqualTo(String value) {
            addCriterion("F_20 <=", value, "f20");
            return (Criteria) this;
        }

        public Criteria andF20Like(String value) {
            addCriterion("F_20 like", value, "f20");
            return (Criteria) this;
        }

        public Criteria andF20NotLike(String value) {
            addCriterion("F_20 not like", value, "f20");
            return (Criteria) this;
        }

        public Criteria andF20In(List<String> values) {
            addCriterion("F_20 in", values, "f20");
            return (Criteria) this;
        }

        public Criteria andF20NotIn(List<String> values) {
            addCriterion("F_20 not in", values, "f20");
            return (Criteria) this;
        }

        public Criteria andF20Between(String value1, String value2) {
            addCriterion("F_20 between", value1, value2, "f20");
            return (Criteria) this;
        }

        public Criteria andF20NotBetween(String value1, String value2) {
            addCriterion("F_20 not between", value1, value2, "f20");
            return (Criteria) this;
        }

        public Criteria andF12IsNull() {
            addCriterion("F_12 is null");
            return (Criteria) this;
        }

        public Criteria andF12IsNotNull() {
            addCriterion("F_12 is not null");
            return (Criteria) this;
        }

        public Criteria andF12EqualTo(String value) {
            addCriterion("F_12 =", value, "f12");
            return (Criteria) this;
        }

        public Criteria andF12NotEqualTo(String value) {
            addCriterion("F_12 <>", value, "f12");
            return (Criteria) this;
        }

        public Criteria andF12GreaterThan(String value) {
            addCriterion("F_12 >", value, "f12");
            return (Criteria) this;
        }

        public Criteria andF12GreaterThanOrEqualTo(String value) {
            addCriterion("F_12 >=", value, "f12");
            return (Criteria) this;
        }

        public Criteria andF12LessThan(String value) {
            addCriterion("F_12 <", value, "f12");
            return (Criteria) this;
        }

        public Criteria andF12LessThanOrEqualTo(String value) {
            addCriterion("F_12 <=", value, "f12");
            return (Criteria) this;
        }

        public Criteria andF12Like(String value) {
            addCriterion("F_12 like", value, "f12");
            return (Criteria) this;
        }

        public Criteria andF12NotLike(String value) {
            addCriterion("F_12 not like", value, "f12");
            return (Criteria) this;
        }

        public Criteria andF12In(List<String> values) {
            addCriterion("F_12 in", values, "f12");
            return (Criteria) this;
        }

        public Criteria andF12NotIn(List<String> values) {
            addCriterion("F_12 not in", values, "f12");
            return (Criteria) this;
        }

        public Criteria andF12Between(String value1, String value2) {
            addCriterion("F_12 between", value1, value2, "f12");
            return (Criteria) this;
        }

        public Criteria andF12NotBetween(String value1, String value2) {
            addCriterion("F_12 not between", value1, value2, "f12");
            return (Criteria) this;
        }

        public Criteria andF13IsNull() {
            addCriterion("F_13 is null");
            return (Criteria) this;
        }

        public Criteria andF13IsNotNull() {
            addCriterion("F_13 is not null");
            return (Criteria) this;
        }

        public Criteria andF13EqualTo(String value) {
            addCriterion("F_13 =", value, "f13");
            return (Criteria) this;
        }

        public Criteria andF13NotEqualTo(String value) {
            addCriterion("F_13 <>", value, "f13");
            return (Criteria) this;
        }

        public Criteria andF13GreaterThan(String value) {
            addCriterion("F_13 >", value, "f13");
            return (Criteria) this;
        }

        public Criteria andF13GreaterThanOrEqualTo(String value) {
            addCriterion("F_13 >=", value, "f13");
            return (Criteria) this;
        }

        public Criteria andF13LessThan(String value) {
            addCriterion("F_13 <", value, "f13");
            return (Criteria) this;
        }

        public Criteria andF13LessThanOrEqualTo(String value) {
            addCriterion("F_13 <=", value, "f13");
            return (Criteria) this;
        }

        public Criteria andF13Like(String value) {
            addCriterion("F_13 like", value, "f13");
            return (Criteria) this;
        }

        public Criteria andF13NotLike(String value) {
            addCriterion("F_13 not like", value, "f13");
            return (Criteria) this;
        }

        public Criteria andF13In(List<String> values) {
            addCriterion("F_13 in", values, "f13");
            return (Criteria) this;
        }

        public Criteria andF13NotIn(List<String> values) {
            addCriterion("F_13 not in", values, "f13");
            return (Criteria) this;
        }

        public Criteria andF13Between(String value1, String value2) {
            addCriterion("F_13 between", value1, value2, "f13");
            return (Criteria) this;
        }

        public Criteria andF13NotBetween(String value1, String value2) {
            addCriterion("F_13 not between", value1, value2, "f13");
            return (Criteria) this;
        }

        public Criteria andF14IsNull() {
            addCriterion("F_14 is null");
            return (Criteria) this;
        }

        public Criteria andF14IsNotNull() {
            addCriterion("F_14 is not null");
            return (Criteria) this;
        }

        public Criteria andF14EqualTo(String value) {
            addCriterion("F_14 =", value, "f14");
            return (Criteria) this;
        }

        public Criteria andF14NotEqualTo(String value) {
            addCriterion("F_14 <>", value, "f14");
            return (Criteria) this;
        }

        public Criteria andF14GreaterThan(String value) {
            addCriterion("F_14 >", value, "f14");
            return (Criteria) this;
        }

        public Criteria andF14GreaterThanOrEqualTo(String value) {
            addCriterion("F_14 >=", value, "f14");
            return (Criteria) this;
        }

        public Criteria andF14LessThan(String value) {
            addCriterion("F_14 <", value, "f14");
            return (Criteria) this;
        }

        public Criteria andF14LessThanOrEqualTo(String value) {
            addCriterion("F_14 <=", value, "f14");
            return (Criteria) this;
        }

        public Criteria andF14Like(String value) {
            addCriterion("F_14 like", value, "f14");
            return (Criteria) this;
        }

        public Criteria andF14NotLike(String value) {
            addCriterion("F_14 not like", value, "f14");
            return (Criteria) this;
        }

        public Criteria andF14In(List<String> values) {
            addCriterion("F_14 in", values, "f14");
            return (Criteria) this;
        }

        public Criteria andF14NotIn(List<String> values) {
            addCriterion("F_14 not in", values, "f14");
            return (Criteria) this;
        }

        public Criteria andF14Between(String value1, String value2) {
            addCriterion("F_14 between", value1, value2, "f14");
            return (Criteria) this;
        }

        public Criteria andF14NotBetween(String value1, String value2) {
            addCriterion("F_14 not between", value1, value2, "f14");
            return (Criteria) this;
        }

        public Criteria andF32IsNull() {
            addCriterion("F_32 is null");
            return (Criteria) this;
        }

        public Criteria andF32IsNotNull() {
            addCriterion("F_32 is not null");
            return (Criteria) this;
        }

        public Criteria andF32EqualTo(String value) {
            addCriterion("F_32 =", value, "f32");
            return (Criteria) this;
        }

        public Criteria andF32NotEqualTo(String value) {
            addCriterion("F_32 <>", value, "f32");
            return (Criteria) this;
        }

        public Criteria andF32GreaterThan(String value) {
            addCriterion("F_32 >", value, "f32");
            return (Criteria) this;
        }

        public Criteria andF32GreaterThanOrEqualTo(String value) {
            addCriterion("F_32 >=", value, "f32");
            return (Criteria) this;
        }

        public Criteria andF32LessThan(String value) {
            addCriterion("F_32 <", value, "f32");
            return (Criteria) this;
        }

        public Criteria andF32LessThanOrEqualTo(String value) {
            addCriterion("F_32 <=", value, "f32");
            return (Criteria) this;
        }

        public Criteria andF32Like(String value) {
            addCriterion("F_32 like", value, "f32");
            return (Criteria) this;
        }

        public Criteria andF32NotLike(String value) {
            addCriterion("F_32 not like", value, "f32");
            return (Criteria) this;
        }

        public Criteria andF32In(List<String> values) {
            addCriterion("F_32 in", values, "f32");
            return (Criteria) this;
        }

        public Criteria andF32NotIn(List<String> values) {
            addCriterion("F_32 not in", values, "f32");
            return (Criteria) this;
        }

        public Criteria andF32Between(String value1, String value2) {
            addCriterion("F_32 between", value1, value2, "f32");
            return (Criteria) this;
        }

        public Criteria andF32NotBetween(String value1, String value2) {
            addCriterion("F_32 not between", value1, value2, "f32");
            return (Criteria) this;
        }

        public Criteria andF15IsNull() {
            addCriterion("F_15 is null");
            return (Criteria) this;
        }

        public Criteria andF15IsNotNull() {
            addCriterion("F_15 is not null");
            return (Criteria) this;
        }

        public Criteria andF15EqualTo(String value) {
            addCriterion("F_15 =", value, "f15");
            return (Criteria) this;
        }

        public Criteria andF15NotEqualTo(String value) {
            addCriterion("F_15 <>", value, "f15");
            return (Criteria) this;
        }

        public Criteria andF15GreaterThan(String value) {
            addCriterion("F_15 >", value, "f15");
            return (Criteria) this;
        }

        public Criteria andF15GreaterThanOrEqualTo(String value) {
            addCriterion("F_15 >=", value, "f15");
            return (Criteria) this;
        }

        public Criteria andF15LessThan(String value) {
            addCriterion("F_15 <", value, "f15");
            return (Criteria) this;
        }

        public Criteria andF15LessThanOrEqualTo(String value) {
            addCriterion("F_15 <=", value, "f15");
            return (Criteria) this;
        }

        public Criteria andF15Like(String value) {
            addCriterion("F_15 like", value, "f15");
            return (Criteria) this;
        }

        public Criteria andF15NotLike(String value) {
            addCriterion("F_15 not like", value, "f15");
            return (Criteria) this;
        }

        public Criteria andF15In(List<String> values) {
            addCriterion("F_15 in", values, "f15");
            return (Criteria) this;
        }

        public Criteria andF15NotIn(List<String> values) {
            addCriterion("F_15 not in", values, "f15");
            return (Criteria) this;
        }

        public Criteria andF15Between(String value1, String value2) {
            addCriterion("F_15 between", value1, value2, "f15");
            return (Criteria) this;
        }

        public Criteria andF15NotBetween(String value1, String value2) {
            addCriterion("F_15 not between", value1, value2, "f15");
            return (Criteria) this;
        }
    }

    public static class Criteria extends GeneratedCriteria {
        protected Criteria() {
            super();
        }
    }

    public static class Criterion {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private String typeHandler;

        public String getCondition() {
            return condition;
        }

        public Object getValue() {
            return value;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        public String getTypeHandler() {
            return typeHandler;
        }

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }
}