package com.pandatech.downloadcf.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class EslModelExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public EslModelExample() {
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

        public Criteria andModelCodeIsNull() {
            addCriterion("MODEL_CODE is null");
            return (Criteria) this;
        }

        public Criteria andModelCodeIsNotNull() {
            addCriterion("MODEL_CODE is not null");
            return (Criteria) this;
        }

        public Criteria andModelCodeEqualTo(String value) {
            addCriterion("MODEL_CODE =", value, "modelCode");
            return (Criteria) this;
        }

        public Criteria andModelCodeNotEqualTo(String value) {
            addCriterion("MODEL_CODE <>", value, "modelCode");
            return (Criteria) this;
        }

        public Criteria andModelCodeGreaterThan(String value) {
            addCriterion("MODEL_CODE >", value, "modelCode");
            return (Criteria) this;
        }

        public Criteria andModelCodeGreaterThanOrEqualTo(String value) {
            addCriterion("MODEL_CODE >=", value, "modelCode");
            return (Criteria) this;
        }

        public Criteria andModelCodeLessThan(String value) {
            addCriterion("MODEL_CODE <", value, "modelCode");
            return (Criteria) this;
        }

        public Criteria andModelCodeLessThanOrEqualTo(String value) {
            addCriterion("MODEL_CODE <=", value, "modelCode");
            return (Criteria) this;
        }

        public Criteria andModelCodeLike(String value) {
            addCriterion("MODEL_CODE like", value, "modelCode");
            return (Criteria) this;
        }

        public Criteria andModelCodeNotLike(String value) {
            addCriterion("MODEL_CODE not like", value, "modelCode");
            return (Criteria) this;
        }

        public Criteria andModelCodeIn(List<String> values) {
            addCriterion("MODEL_CODE in", values, "modelCode");
            return (Criteria) this;
        }

        public Criteria andModelCodeNotIn(List<String> values) {
            addCriterion("MODEL_CODE not in", values, "modelCode");
            return (Criteria) this;
        }

        public Criteria andModelCodeBetween(String value1, String value2) {
            addCriterion("MODEL_CODE between", value1, value2, "modelCode");
            return (Criteria) this;
        }

        public Criteria andModelCodeNotBetween(String value1, String value2) {
            addCriterion("MODEL_CODE not between", value1, value2, "modelCode");
            return (Criteria) this;
        }

        public Criteria andModelNameIsNull() {
            addCriterion("MODEL_NAME is null");
            return (Criteria) this;
        }

        public Criteria andModelNameIsNotNull() {
            addCriterion("MODEL_NAME is not null");
            return (Criteria) this;
        }

        public Criteria andModelNameEqualTo(String value) {
            addCriterion("MODEL_NAME =", value, "modelName");
            return (Criteria) this;
        }

        public Criteria andModelNameNotEqualTo(String value) {
            addCriterion("MODEL_NAME <>", value, "modelName");
            return (Criteria) this;
        }

        public Criteria andModelNameGreaterThan(String value) {
            addCriterion("MODEL_NAME >", value, "modelName");
            return (Criteria) this;
        }

        public Criteria andModelNameGreaterThanOrEqualTo(String value) {
            addCriterion("MODEL_NAME >=", value, "modelName");
            return (Criteria) this;
        }

        public Criteria andModelNameLessThan(String value) {
            addCriterion("MODEL_NAME <", value, "modelName");
            return (Criteria) this;
        }

        public Criteria andModelNameLessThanOrEqualTo(String value) {
            addCriterion("MODEL_NAME <=", value, "modelName");
            return (Criteria) this;
        }

        public Criteria andModelNameLike(String value) {
            addCriterion("MODEL_NAME like", value, "modelName");
            return (Criteria) this;
        }

        public Criteria andModelNameNotLike(String value) {
            addCriterion("MODEL_NAME not like", value, "modelName");
            return (Criteria) this;
        }

        public Criteria andModelNameIn(List<String> values) {
            addCriterion("MODEL_NAME in", values, "modelName");
            return (Criteria) this;
        }

        public Criteria andModelNameNotIn(List<String> values) {
            addCriterion("MODEL_NAME not in", values, "modelName");
            return (Criteria) this;
        }

        public Criteria andModelNameBetween(String value1, String value2) {
            addCriterion("MODEL_NAME between", value1, value2, "modelName");
            return (Criteria) this;
        }

        public Criteria andModelNameNotBetween(String value1, String value2) {
            addCriterion("MODEL_NAME not between", value1, value2, "modelName");
            return (Criteria) this;
        }

        public Criteria andBrandCodeIsNull() {
            addCriterion("BRAND_CODE is null");
            return (Criteria) this;
        }

        public Criteria andBrandCodeIsNotNull() {
            addCriterion("BRAND_CODE is not null");
            return (Criteria) this;
        }

        public Criteria andBrandCodeEqualTo(String value) {
            addCriterion("BRAND_CODE =", value, "brandCode");
            return (Criteria) this;
        }

        public Criteria andBrandCodeNotEqualTo(String value) {
            addCriterion("BRAND_CODE <>", value, "brandCode");
            return (Criteria) this;
        }

        public Criteria andBrandCodeGreaterThan(String value) {
            addCriterion("BRAND_CODE >", value, "brandCode");
            return (Criteria) this;
        }

        public Criteria andBrandCodeGreaterThanOrEqualTo(String value) {
            addCriterion("BRAND_CODE >=", value, "brandCode");
            return (Criteria) this;
        }

        public Criteria andBrandCodeLessThan(String value) {
            addCriterion("BRAND_CODE <", value, "brandCode");
            return (Criteria) this;
        }

        public Criteria andBrandCodeLessThanOrEqualTo(String value) {
            addCriterion("BRAND_CODE <=", value, "brandCode");
            return (Criteria) this;
        }

        public Criteria andBrandCodeLike(String value) {
            addCriterion("BRAND_CODE like", value, "brandCode");
            return (Criteria) this;
        }

        public Criteria andBrandCodeNotLike(String value) {
            addCriterion("BRAND_CODE not like", value, "brandCode");
            return (Criteria) this;
        }

        public Criteria andBrandCodeIn(List<String> values) {
            addCriterion("BRAND_CODE in", values, "brandCode");
            return (Criteria) this;
        }

        public Criteria andBrandCodeNotIn(List<String> values) {
            addCriterion("BRAND_CODE not in", values, "brandCode");
            return (Criteria) this;
        }

        public Criteria andBrandCodeBetween(String value1, String value2) {
            addCriterion("BRAND_CODE between", value1, value2, "brandCode");
            return (Criteria) this;
        }

        public Criteria andBrandCodeNotBetween(String value1, String value2) {
            addCriterion("BRAND_CODE not between", value1, value2, "brandCode");
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

        public Criteria andOperatingTempRangeIsNull() {
            addCriterion("OPERATING_TEMP_RANGE is null");
            return (Criteria) this;
        }

        public Criteria andOperatingTempRangeIsNotNull() {
            addCriterion("OPERATING_TEMP_RANGE is not null");
            return (Criteria) this;
        }

        public Criteria andOperatingTempRangeEqualTo(String value) {
            addCriterion("OPERATING_TEMP_RANGE =", value, "operatingTempRange");
            return (Criteria) this;
        }

        public Criteria andOperatingTempRangeNotEqualTo(String value) {
            addCriterion("OPERATING_TEMP_RANGE <>", value, "operatingTempRange");
            return (Criteria) this;
        }

        public Criteria andOperatingTempRangeGreaterThan(String value) {
            addCriterion("OPERATING_TEMP_RANGE >", value, "operatingTempRange");
            return (Criteria) this;
        }

        public Criteria andOperatingTempRangeGreaterThanOrEqualTo(String value) {
            addCriterion("OPERATING_TEMP_RANGE >=", value, "operatingTempRange");
            return (Criteria) this;
        }

        public Criteria andOperatingTempRangeLessThan(String value) {
            addCriterion("OPERATING_TEMP_RANGE <", value, "operatingTempRange");
            return (Criteria) this;
        }

        public Criteria andOperatingTempRangeLessThanOrEqualTo(String value) {
            addCriterion("OPERATING_TEMP_RANGE <=", value, "operatingTempRange");
            return (Criteria) this;
        }

        public Criteria andOperatingTempRangeLike(String value) {
            addCriterion("OPERATING_TEMP_RANGE like", value, "operatingTempRange");
            return (Criteria) this;
        }

        public Criteria andOperatingTempRangeNotLike(String value) {
            addCriterion("OPERATING_TEMP_RANGE not like", value, "operatingTempRange");
            return (Criteria) this;
        }

        public Criteria andOperatingTempRangeIn(List<String> values) {
            addCriterion("OPERATING_TEMP_RANGE in", values, "operatingTempRange");
            return (Criteria) this;
        }

        public Criteria andOperatingTempRangeNotIn(List<String> values) {
            addCriterion("OPERATING_TEMP_RANGE not in", values, "operatingTempRange");
            return (Criteria) this;
        }

        public Criteria andOperatingTempRangeBetween(String value1, String value2) {
            addCriterion("OPERATING_TEMP_RANGE between", value1, value2, "operatingTempRange");
            return (Criteria) this;
        }

        public Criteria andOperatingTempRangeNotBetween(String value1, String value2) {
            addCriterion("OPERATING_TEMP_RANGE not between", value1, value2, "operatingTempRange");
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

        public Criteria andScreenSizeWidthIsNull() {
            addCriterion("SCREEN_SIZE_WIDTH is null");
            return (Criteria) this;
        }

        public Criteria andScreenSizeWidthIsNotNull() {
            addCriterion("SCREEN_SIZE_WIDTH is not null");
            return (Criteria) this;
        }

        public Criteria andScreenSizeWidthEqualTo(Float value) {
            addCriterion("SCREEN_SIZE_WIDTH =", value, "screenSizeWidth");
            return (Criteria) this;
        }

        public Criteria andScreenSizeWidthNotEqualTo(Float value) {
            addCriterion("SCREEN_SIZE_WIDTH <>", value, "screenSizeWidth");
            return (Criteria) this;
        }

        public Criteria andScreenSizeWidthGreaterThan(Float value) {
            addCriterion("SCREEN_SIZE_WIDTH >", value, "screenSizeWidth");
            return (Criteria) this;
        }

        public Criteria andScreenSizeWidthGreaterThanOrEqualTo(Float value) {
            addCriterion("SCREEN_SIZE_WIDTH >=", value, "screenSizeWidth");
            return (Criteria) this;
        }

        public Criteria andScreenSizeWidthLessThan(Float value) {
            addCriterion("SCREEN_SIZE_WIDTH <", value, "screenSizeWidth");
            return (Criteria) this;
        }

        public Criteria andScreenSizeWidthLessThanOrEqualTo(Float value) {
            addCriterion("SCREEN_SIZE_WIDTH <=", value, "screenSizeWidth");
            return (Criteria) this;
        }

        public Criteria andScreenSizeWidthIn(List<Float> values) {
            addCriterion("SCREEN_SIZE_WIDTH in", values, "screenSizeWidth");
            return (Criteria) this;
        }

        public Criteria andScreenSizeWidthNotIn(List<Float> values) {
            addCriterion("SCREEN_SIZE_WIDTH not in", values, "screenSizeWidth");
            return (Criteria) this;
        }

        public Criteria andScreenSizeWidthBetween(Float value1, Float value2) {
            addCriterion("SCREEN_SIZE_WIDTH between", value1, value2, "screenSizeWidth");
            return (Criteria) this;
        }

        public Criteria andScreenSizeWidthNotBetween(Float value1, Float value2) {
            addCriterion("SCREEN_SIZE_WIDTH not between", value1, value2, "screenSizeWidth");
            return (Criteria) this;
        }

        public Criteria andScreenSizeHighIsNull() {
            addCriterion("SCREEN_SIZE_HIGH is null");
            return (Criteria) this;
        }

        public Criteria andScreenSizeHighIsNotNull() {
            addCriterion("SCREEN_SIZE_HIGH is not null");
            return (Criteria) this;
        }

        public Criteria andScreenSizeHighEqualTo(Float value) {
            addCriterion("SCREEN_SIZE_HIGH =", value, "screenSizeHigh");
            return (Criteria) this;
        }

        public Criteria andScreenSizeHighNotEqualTo(Float value) {
            addCriterion("SCREEN_SIZE_HIGH <>", value, "screenSizeHigh");
            return (Criteria) this;
        }

        public Criteria andScreenSizeHighGreaterThan(Float value) {
            addCriterion("SCREEN_SIZE_HIGH >", value, "screenSizeHigh");
            return (Criteria) this;
        }

        public Criteria andScreenSizeHighGreaterThanOrEqualTo(Float value) {
            addCriterion("SCREEN_SIZE_HIGH >=", value, "screenSizeHigh");
            return (Criteria) this;
        }

        public Criteria andScreenSizeHighLessThan(Float value) {
            addCriterion("SCREEN_SIZE_HIGH <", value, "screenSizeHigh");
            return (Criteria) this;
        }

        public Criteria andScreenSizeHighLessThanOrEqualTo(Float value) {
            addCriterion("SCREEN_SIZE_HIGH <=", value, "screenSizeHigh");
            return (Criteria) this;
        }

        public Criteria andScreenSizeHighIn(List<Float> values) {
            addCriterion("SCREEN_SIZE_HIGH in", values, "screenSizeHigh");
            return (Criteria) this;
        }

        public Criteria andScreenSizeHighNotIn(List<Float> values) {
            addCriterion("SCREEN_SIZE_HIGH not in", values, "screenSizeHigh");
            return (Criteria) this;
        }

        public Criteria andScreenSizeHighBetween(Float value1, Float value2) {
            addCriterion("SCREEN_SIZE_HIGH between", value1, value2, "screenSizeHigh");
            return (Criteria) this;
        }

        public Criteria andScreenSizeHighNotBetween(Float value1, Float value2) {
            addCriterion("SCREEN_SIZE_HIGH not between", value1, value2, "screenSizeHigh");
            return (Criteria) this;
        }

        public Criteria andResolutionIsNull() {
            addCriterion("RESOLUTION is null");
            return (Criteria) this;
        }

        public Criteria andResolutionIsNotNull() {
            addCriterion("RESOLUTION is not null");
            return (Criteria) this;
        }

        public Criteria andResolutionEqualTo(Float value) {
            addCriterion("RESOLUTION =", value, "resolution");
            return (Criteria) this;
        }

        public Criteria andResolutionNotEqualTo(Float value) {
            addCriterion("RESOLUTION <>", value, "resolution");
            return (Criteria) this;
        }

        public Criteria andResolutionGreaterThan(Float value) {
            addCriterion("RESOLUTION >", value, "resolution");
            return (Criteria) this;
        }

        public Criteria andResolutionGreaterThanOrEqualTo(Float value) {
            addCriterion("RESOLUTION >=", value, "resolution");
            return (Criteria) this;
        }

        public Criteria andResolutionLessThan(Float value) {
            addCriterion("RESOLUTION <", value, "resolution");
            return (Criteria) this;
        }

        public Criteria andResolutionLessThanOrEqualTo(Float value) {
            addCriterion("RESOLUTION <=", value, "resolution");
            return (Criteria) this;
        }

        public Criteria andResolutionIn(List<Float> values) {
            addCriterion("RESOLUTION in", values, "resolution");
            return (Criteria) this;
        }

        public Criteria andResolutionNotIn(List<Float> values) {
            addCriterion("RESOLUTION not in", values, "resolution");
            return (Criteria) this;
        }

        public Criteria andResolutionBetween(Float value1, Float value2) {
            addCriterion("RESOLUTION between", value1, value2, "resolution");
            return (Criteria) this;
        }

        public Criteria andResolutionNotBetween(Float value1, Float value2) {
            addCriterion("RESOLUTION not between", value1, value2, "resolution");
            return (Criteria) this;
        }

        public Criteria andBatteryLifeIsNull() {
            addCriterion("BATTERY_LIFE is null");
            return (Criteria) this;
        }

        public Criteria andBatteryLifeIsNotNull() {
            addCriterion("BATTERY_LIFE is not null");
            return (Criteria) this;
        }

        public Criteria andBatteryLifeEqualTo(Integer value) {
            addCriterion("BATTERY_LIFE =", value, "batteryLife");
            return (Criteria) this;
        }

        public Criteria andBatteryLifeNotEqualTo(Integer value) {
            addCriterion("BATTERY_LIFE <>", value, "batteryLife");
            return (Criteria) this;
        }

        public Criteria andBatteryLifeGreaterThan(Integer value) {
            addCriterion("BATTERY_LIFE >", value, "batteryLife");
            return (Criteria) this;
        }

        public Criteria andBatteryLifeGreaterThanOrEqualTo(Integer value) {
            addCriterion("BATTERY_LIFE >=", value, "batteryLife");
            return (Criteria) this;
        }

        public Criteria andBatteryLifeLessThan(Integer value) {
            addCriterion("BATTERY_LIFE <", value, "batteryLife");
            return (Criteria) this;
        }

        public Criteria andBatteryLifeLessThanOrEqualTo(Integer value) {
            addCriterion("BATTERY_LIFE <=", value, "batteryLife");
            return (Criteria) this;
        }

        public Criteria andBatteryLifeIn(List<Integer> values) {
            addCriterion("BATTERY_LIFE in", values, "batteryLife");
            return (Criteria) this;
        }

        public Criteria andBatteryLifeNotIn(List<Integer> values) {
            addCriterion("BATTERY_LIFE not in", values, "batteryLife");
            return (Criteria) this;
        }

        public Criteria andBatteryLifeBetween(Integer value1, Integer value2) {
            addCriterion("BATTERY_LIFE between", value1, value2, "batteryLife");
            return (Criteria) this;
        }

        public Criteria andBatteryLifeNotBetween(Integer value1, Integer value2) {
            addCriterion("BATTERY_LIFE not between", value1, value2, "batteryLife");
            return (Criteria) this;
        }

        public Criteria andHardwareVersionIsNull() {
            addCriterion("HARDWARE_VERSION is null");
            return (Criteria) this;
        }

        public Criteria andHardwareVersionIsNotNull() {
            addCriterion("HARDWARE_VERSION is not null");
            return (Criteria) this;
        }

        public Criteria andHardwareVersionEqualTo(String value) {
            addCriterion("HARDWARE_VERSION =", value, "hardwareVersion");
            return (Criteria) this;
        }

        public Criteria andHardwareVersionNotEqualTo(String value) {
            addCriterion("HARDWARE_VERSION <>", value, "hardwareVersion");
            return (Criteria) this;
        }

        public Criteria andHardwareVersionGreaterThan(String value) {
            addCriterion("HARDWARE_VERSION >", value, "hardwareVersion");
            return (Criteria) this;
        }

        public Criteria andHardwareVersionGreaterThanOrEqualTo(String value) {
            addCriterion("HARDWARE_VERSION >=", value, "hardwareVersion");
            return (Criteria) this;
        }

        public Criteria andHardwareVersionLessThan(String value) {
            addCriterion("HARDWARE_VERSION <", value, "hardwareVersion");
            return (Criteria) this;
        }

        public Criteria andHardwareVersionLessThanOrEqualTo(String value) {
            addCriterion("HARDWARE_VERSION <=", value, "hardwareVersion");
            return (Criteria) this;
        }

        public Criteria andHardwareVersionLike(String value) {
            addCriterion("HARDWARE_VERSION like", value, "hardwareVersion");
            return (Criteria) this;
        }

        public Criteria andHardwareVersionNotLike(String value) {
            addCriterion("HARDWARE_VERSION not like", value, "hardwareVersion");
            return (Criteria) this;
        }

        public Criteria andHardwareVersionIn(List<String> values) {
            addCriterion("HARDWARE_VERSION in", values, "hardwareVersion");
            return (Criteria) this;
        }

        public Criteria andHardwareVersionNotIn(List<String> values) {
            addCriterion("HARDWARE_VERSION not in", values, "hardwareVersion");
            return (Criteria) this;
        }

        public Criteria andHardwareVersionBetween(String value1, String value2) {
            addCriterion("HARDWARE_VERSION between", value1, value2, "hardwareVersion");
            return (Criteria) this;
        }

        public Criteria andHardwareVersionNotBetween(String value1, String value2) {
            addCriterion("HARDWARE_VERSION not between", value1, value2, "hardwareVersion");
            return (Criteria) this;
        }

        public Criteria andSoftwareVersionIsNull() {
            addCriterion("SOFTWARE_VERSION is null");
            return (Criteria) this;
        }

        public Criteria andSoftwareVersionIsNotNull() {
            addCriterion("SOFTWARE_VERSION is not null");
            return (Criteria) this;
        }

        public Criteria andSoftwareVersionEqualTo(String value) {
            addCriterion("SOFTWARE_VERSION =", value, "softwareVersion");
            return (Criteria) this;
        }

        public Criteria andSoftwareVersionNotEqualTo(String value) {
            addCriterion("SOFTWARE_VERSION <>", value, "softwareVersion");
            return (Criteria) this;
        }

        public Criteria andSoftwareVersionGreaterThan(String value) {
            addCriterion("SOFTWARE_VERSION >", value, "softwareVersion");
            return (Criteria) this;
        }

        public Criteria andSoftwareVersionGreaterThanOrEqualTo(String value) {
            addCriterion("SOFTWARE_VERSION >=", value, "softwareVersion");
            return (Criteria) this;
        }

        public Criteria andSoftwareVersionLessThan(String value) {
            addCriterion("SOFTWARE_VERSION <", value, "softwareVersion");
            return (Criteria) this;
        }

        public Criteria andSoftwareVersionLessThanOrEqualTo(String value) {
            addCriterion("SOFTWARE_VERSION <=", value, "softwareVersion");
            return (Criteria) this;
        }

        public Criteria andSoftwareVersionLike(String value) {
            addCriterion("SOFTWARE_VERSION like", value, "softwareVersion");
            return (Criteria) this;
        }

        public Criteria andSoftwareVersionNotLike(String value) {
            addCriterion("SOFTWARE_VERSION not like", value, "softwareVersion");
            return (Criteria) this;
        }

        public Criteria andSoftwareVersionIn(List<String> values) {
            addCriterion("SOFTWARE_VERSION in", values, "softwareVersion");
            return (Criteria) this;
        }

        public Criteria andSoftwareVersionNotIn(List<String> values) {
            addCriterion("SOFTWARE_VERSION not in", values, "softwareVersion");
            return (Criteria) this;
        }

        public Criteria andSoftwareVersionBetween(String value1, String value2) {
            addCriterion("SOFTWARE_VERSION between", value1, value2, "softwareVersion");
            return (Criteria) this;
        }

        public Criteria andSoftwareVersionNotBetween(String value1, String value2) {
            addCriterion("SOFTWARE_VERSION not between", value1, value2, "softwareVersion");
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