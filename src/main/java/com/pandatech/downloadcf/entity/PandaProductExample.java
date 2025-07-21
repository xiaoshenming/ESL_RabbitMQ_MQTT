package com.pandatech.downloadcf.entity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PandaProductExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public PandaProductExample() {
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

        public Criteria andProductIdIsNull() {
            addCriterion("PRODUCT_ID is null");
            return (Criteria) this;
        }

        public Criteria andProductIdIsNotNull() {
            addCriterion("PRODUCT_ID is not null");
            return (Criteria) this;
        }

        public Criteria andProductIdEqualTo(String value) {
            addCriterion("PRODUCT_ID =", value, "productId");
            return (Criteria) this;
        }

        public Criteria andProductIdNotEqualTo(String value) {
            addCriterion("PRODUCT_ID <>", value, "productId");
            return (Criteria) this;
        }

        public Criteria andProductIdGreaterThan(String value) {
            addCriterion("PRODUCT_ID >", value, "productId");
            return (Criteria) this;
        }

        public Criteria andProductIdGreaterThanOrEqualTo(String value) {
            addCriterion("PRODUCT_ID >=", value, "productId");
            return (Criteria) this;
        }

        public Criteria andProductIdLessThan(String value) {
            addCriterion("PRODUCT_ID <", value, "productId");
            return (Criteria) this;
        }

        public Criteria andProductIdLessThanOrEqualTo(String value) {
            addCriterion("PRODUCT_ID <=", value, "productId");
            return (Criteria) this;
        }

        public Criteria andProductIdLike(String value) {
            addCriterion("PRODUCT_ID like", value, "productId");
            return (Criteria) this;
        }

        public Criteria andProductIdNotLike(String value) {
            addCriterion("PRODUCT_ID not like", value, "productId");
            return (Criteria) this;
        }

        public Criteria andProductIdIn(List<String> values) {
            addCriterion("PRODUCT_ID in", values, "productId");
            return (Criteria) this;
        }

        public Criteria andProductIdNotIn(List<String> values) {
            addCriterion("PRODUCT_ID not in", values, "productId");
            return (Criteria) this;
        }

        public Criteria andProductIdBetween(String value1, String value2) {
            addCriterion("PRODUCT_ID between", value1, value2, "productId");
            return (Criteria) this;
        }

        public Criteria andProductIdNotBetween(String value1, String value2) {
            addCriterion("PRODUCT_ID not between", value1, value2, "productId");
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

        public Criteria andProductNameIsNull() {
            addCriterion("PRODUCT_NAME is null");
            return (Criteria) this;
        }

        public Criteria andProductNameIsNotNull() {
            addCriterion("PRODUCT_NAME is not null");
            return (Criteria) this;
        }

        public Criteria andProductNameEqualTo(String value) {
            addCriterion("PRODUCT_NAME =", value, "productName");
            return (Criteria) this;
        }

        public Criteria andProductNameNotEqualTo(String value) {
            addCriterion("PRODUCT_NAME <>", value, "productName");
            return (Criteria) this;
        }

        public Criteria andProductNameGreaterThan(String value) {
            addCriterion("PRODUCT_NAME >", value, "productName");
            return (Criteria) this;
        }

        public Criteria andProductNameGreaterThanOrEqualTo(String value) {
            addCriterion("PRODUCT_NAME >=", value, "productName");
            return (Criteria) this;
        }

        public Criteria andProductNameLessThan(String value) {
            addCriterion("PRODUCT_NAME <", value, "productName");
            return (Criteria) this;
        }

        public Criteria andProductNameLessThanOrEqualTo(String value) {
            addCriterion("PRODUCT_NAME <=", value, "productName");
            return (Criteria) this;
        }

        public Criteria andProductNameLike(String value) {
            addCriterion("PRODUCT_NAME like", value, "productName");
            return (Criteria) this;
        }

        public Criteria andProductNameNotLike(String value) {
            addCriterion("PRODUCT_NAME not like", value, "productName");
            return (Criteria) this;
        }

        public Criteria andProductNameIn(List<String> values) {
            addCriterion("PRODUCT_NAME in", values, "productName");
            return (Criteria) this;
        }

        public Criteria andProductNameNotIn(List<String> values) {
            addCriterion("PRODUCT_NAME not in", values, "productName");
            return (Criteria) this;
        }

        public Criteria andProductNameBetween(String value1, String value2) {
            addCriterion("PRODUCT_NAME between", value1, value2, "productName");
            return (Criteria) this;
        }

        public Criteria andProductNameNotBetween(String value1, String value2) {
            addCriterion("PRODUCT_NAME not between", value1, value2, "productName");
            return (Criteria) this;
        }

        public Criteria andProductCategoryIsNull() {
            addCriterion("PRODUCT_CATEGORY is null");
            return (Criteria) this;
        }

        public Criteria andProductCategoryIsNotNull() {
            addCriterion("PRODUCT_CATEGORY is not null");
            return (Criteria) this;
        }

        public Criteria andProductCategoryEqualTo(String value) {
            addCriterion("PRODUCT_CATEGORY =", value, "productCategory");
            return (Criteria) this;
        }

        public Criteria andProductCategoryNotEqualTo(String value) {
            addCriterion("PRODUCT_CATEGORY <>", value, "productCategory");
            return (Criteria) this;
        }

        public Criteria andProductCategoryGreaterThan(String value) {
            addCriterion("PRODUCT_CATEGORY >", value, "productCategory");
            return (Criteria) this;
        }

        public Criteria andProductCategoryGreaterThanOrEqualTo(String value) {
            addCriterion("PRODUCT_CATEGORY >=", value, "productCategory");
            return (Criteria) this;
        }

        public Criteria andProductCategoryLessThan(String value) {
            addCriterion("PRODUCT_CATEGORY <", value, "productCategory");
            return (Criteria) this;
        }

        public Criteria andProductCategoryLessThanOrEqualTo(String value) {
            addCriterion("PRODUCT_CATEGORY <=", value, "productCategory");
            return (Criteria) this;
        }

        public Criteria andProductCategoryLike(String value) {
            addCriterion("PRODUCT_CATEGORY like", value, "productCategory");
            return (Criteria) this;
        }

        public Criteria andProductCategoryNotLike(String value) {
            addCriterion("PRODUCT_CATEGORY not like", value, "productCategory");
            return (Criteria) this;
        }

        public Criteria andProductCategoryIn(List<String> values) {
            addCriterion("PRODUCT_CATEGORY in", values, "productCategory");
            return (Criteria) this;
        }

        public Criteria andProductCategoryNotIn(List<String> values) {
            addCriterion("PRODUCT_CATEGORY not in", values, "productCategory");
            return (Criteria) this;
        }

        public Criteria andProductCategoryBetween(String value1, String value2) {
            addCriterion("PRODUCT_CATEGORY between", value1, value2, "productCategory");
            return (Criteria) this;
        }

        public Criteria andProductCategoryNotBetween(String value1, String value2) {
            addCriterion("PRODUCT_CATEGORY not between", value1, value2, "productCategory");
            return (Criteria) this;
        }

        public Criteria andProductSpecificationIsNull() {
            addCriterion("PRODUCT_SPECIFICATION is null");
            return (Criteria) this;
        }

        public Criteria andProductSpecificationIsNotNull() {
            addCriterion("PRODUCT_SPECIFICATION is not null");
            return (Criteria) this;
        }

        public Criteria andProductSpecificationEqualTo(String value) {
            addCriterion("PRODUCT_SPECIFICATION =", value, "productSpecification");
            return (Criteria) this;
        }

        public Criteria andProductSpecificationNotEqualTo(String value) {
            addCriterion("PRODUCT_SPECIFICATION <>", value, "productSpecification");
            return (Criteria) this;
        }

        public Criteria andProductSpecificationGreaterThan(String value) {
            addCriterion("PRODUCT_SPECIFICATION >", value, "productSpecification");
            return (Criteria) this;
        }

        public Criteria andProductSpecificationGreaterThanOrEqualTo(String value) {
            addCriterion("PRODUCT_SPECIFICATION >=", value, "productSpecification");
            return (Criteria) this;
        }

        public Criteria andProductSpecificationLessThan(String value) {
            addCriterion("PRODUCT_SPECIFICATION <", value, "productSpecification");
            return (Criteria) this;
        }

        public Criteria andProductSpecificationLessThanOrEqualTo(String value) {
            addCriterion("PRODUCT_SPECIFICATION <=", value, "productSpecification");
            return (Criteria) this;
        }

        public Criteria andProductSpecificationLike(String value) {
            addCriterion("PRODUCT_SPECIFICATION like", value, "productSpecification");
            return (Criteria) this;
        }

        public Criteria andProductSpecificationNotLike(String value) {
            addCriterion("PRODUCT_SPECIFICATION not like", value, "productSpecification");
            return (Criteria) this;
        }

        public Criteria andProductSpecificationIn(List<String> values) {
            addCriterion("PRODUCT_SPECIFICATION in", values, "productSpecification");
            return (Criteria) this;
        }

        public Criteria andProductSpecificationNotIn(List<String> values) {
            addCriterion("PRODUCT_SPECIFICATION not in", values, "productSpecification");
            return (Criteria) this;
        }

        public Criteria andProductSpecificationBetween(String value1, String value2) {
            addCriterion("PRODUCT_SPECIFICATION between", value1, value2, "productSpecification");
            return (Criteria) this;
        }

        public Criteria andProductSpecificationNotBetween(String value1, String value2) {
            addCriterion("PRODUCT_SPECIFICATION not between", value1, value2, "productSpecification");
            return (Criteria) this;
        }

        public Criteria andProductBrandIsNull() {
            addCriterion("PRODUCT_BRAND is null");
            return (Criteria) this;
        }

        public Criteria andProductBrandIsNotNull() {
            addCriterion("PRODUCT_BRAND is not null");
            return (Criteria) this;
        }

        public Criteria andProductBrandEqualTo(String value) {
            addCriterion("PRODUCT_BRAND =", value, "productBrand");
            return (Criteria) this;
        }

        public Criteria andProductBrandNotEqualTo(String value) {
            addCriterion("PRODUCT_BRAND <>", value, "productBrand");
            return (Criteria) this;
        }

        public Criteria andProductBrandGreaterThan(String value) {
            addCriterion("PRODUCT_BRAND >", value, "productBrand");
            return (Criteria) this;
        }

        public Criteria andProductBrandGreaterThanOrEqualTo(String value) {
            addCriterion("PRODUCT_BRAND >=", value, "productBrand");
            return (Criteria) this;
        }

        public Criteria andProductBrandLessThan(String value) {
            addCriterion("PRODUCT_BRAND <", value, "productBrand");
            return (Criteria) this;
        }

        public Criteria andProductBrandLessThanOrEqualTo(String value) {
            addCriterion("PRODUCT_BRAND <=", value, "productBrand");
            return (Criteria) this;
        }

        public Criteria andProductBrandLike(String value) {
            addCriterion("PRODUCT_BRAND like", value, "productBrand");
            return (Criteria) this;
        }

        public Criteria andProductBrandNotLike(String value) {
            addCriterion("PRODUCT_BRAND not like", value, "productBrand");
            return (Criteria) this;
        }

        public Criteria andProductBrandIn(List<String> values) {
            addCriterion("PRODUCT_BRAND in", values, "productBrand");
            return (Criteria) this;
        }

        public Criteria andProductBrandNotIn(List<String> values) {
            addCriterion("PRODUCT_BRAND not in", values, "productBrand");
            return (Criteria) this;
        }

        public Criteria andProductBrandBetween(String value1, String value2) {
            addCriterion("PRODUCT_BRAND between", value1, value2, "productBrand");
            return (Criteria) this;
        }

        public Criteria andProductBrandNotBetween(String value1, String value2) {
            addCriterion("PRODUCT_BRAND not between", value1, value2, "productBrand");
            return (Criteria) this;
        }

        public Criteria andProductCostPriceIsNull() {
            addCriterion("PRODUCT_COST_PRICE is null");
            return (Criteria) this;
        }

        public Criteria andProductCostPriceIsNotNull() {
            addCriterion("PRODUCT_COST_PRICE is not null");
            return (Criteria) this;
        }

        public Criteria andProductCostPriceEqualTo(BigDecimal value) {
            addCriterion("PRODUCT_COST_PRICE =", value, "productCostPrice");
            return (Criteria) this;
        }

        public Criteria andProductCostPriceNotEqualTo(BigDecimal value) {
            addCriterion("PRODUCT_COST_PRICE <>", value, "productCostPrice");
            return (Criteria) this;
        }

        public Criteria andProductCostPriceGreaterThan(BigDecimal value) {
            addCriterion("PRODUCT_COST_PRICE >", value, "productCostPrice");
            return (Criteria) this;
        }

        public Criteria andProductCostPriceGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("PRODUCT_COST_PRICE >=", value, "productCostPrice");
            return (Criteria) this;
        }

        public Criteria andProductCostPriceLessThan(BigDecimal value) {
            addCriterion("PRODUCT_COST_PRICE <", value, "productCostPrice");
            return (Criteria) this;
        }

        public Criteria andProductCostPriceLessThanOrEqualTo(BigDecimal value) {
            addCriterion("PRODUCT_COST_PRICE <=", value, "productCostPrice");
            return (Criteria) this;
        }

        public Criteria andProductCostPriceIn(List<BigDecimal> values) {
            addCriterion("PRODUCT_COST_PRICE in", values, "productCostPrice");
            return (Criteria) this;
        }

        public Criteria andProductCostPriceNotIn(List<BigDecimal> values) {
            addCriterion("PRODUCT_COST_PRICE not in", values, "productCostPrice");
            return (Criteria) this;
        }

        public Criteria andProductCostPriceBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("PRODUCT_COST_PRICE between", value1, value2, "productCostPrice");
            return (Criteria) this;
        }

        public Criteria andProductCostPriceNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("PRODUCT_COST_PRICE not between", value1, value2, "productCostPrice");
            return (Criteria) this;
        }

        public Criteria andProductRetailPriceIsNull() {
            addCriterion("PRODUCT_RETAIL_PRICE is null");
            return (Criteria) this;
        }

        public Criteria andProductRetailPriceIsNotNull() {
            addCriterion("PRODUCT_RETAIL_PRICE is not null");
            return (Criteria) this;
        }

        public Criteria andProductRetailPriceEqualTo(BigDecimal value) {
            addCriterion("PRODUCT_RETAIL_PRICE =", value, "productRetailPrice");
            return (Criteria) this;
        }

        public Criteria andProductRetailPriceNotEqualTo(BigDecimal value) {
            addCriterion("PRODUCT_RETAIL_PRICE <>", value, "productRetailPrice");
            return (Criteria) this;
        }

        public Criteria andProductRetailPriceGreaterThan(BigDecimal value) {
            addCriterion("PRODUCT_RETAIL_PRICE >", value, "productRetailPrice");
            return (Criteria) this;
        }

        public Criteria andProductRetailPriceGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("PRODUCT_RETAIL_PRICE >=", value, "productRetailPrice");
            return (Criteria) this;
        }

        public Criteria andProductRetailPriceLessThan(BigDecimal value) {
            addCriterion("PRODUCT_RETAIL_PRICE <", value, "productRetailPrice");
            return (Criteria) this;
        }

        public Criteria andProductRetailPriceLessThanOrEqualTo(BigDecimal value) {
            addCriterion("PRODUCT_RETAIL_PRICE <=", value, "productRetailPrice");
            return (Criteria) this;
        }

        public Criteria andProductRetailPriceIn(List<BigDecimal> values) {
            addCriterion("PRODUCT_RETAIL_PRICE in", values, "productRetailPrice");
            return (Criteria) this;
        }

        public Criteria andProductRetailPriceNotIn(List<BigDecimal> values) {
            addCriterion("PRODUCT_RETAIL_PRICE not in", values, "productRetailPrice");
            return (Criteria) this;
        }

        public Criteria andProductRetailPriceBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("PRODUCT_RETAIL_PRICE between", value1, value2, "productRetailPrice");
            return (Criteria) this;
        }

        public Criteria andProductRetailPriceNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("PRODUCT_RETAIL_PRICE not between", value1, value2, "productRetailPrice");
            return (Criteria) this;
        }

        public Criteria andProductMembershipPriceIsNull() {
            addCriterion("PRODUCT_MEMBERSHIP_PRICE is null");
            return (Criteria) this;
        }

        public Criteria andProductMembershipPriceIsNotNull() {
            addCriterion("PRODUCT_MEMBERSHIP_PRICE is not null");
            return (Criteria) this;
        }

        public Criteria andProductMembershipPriceEqualTo(BigDecimal value) {
            addCriterion("PRODUCT_MEMBERSHIP_PRICE =", value, "productMembershipPrice");
            return (Criteria) this;
        }

        public Criteria andProductMembershipPriceNotEqualTo(BigDecimal value) {
            addCriterion("PRODUCT_MEMBERSHIP_PRICE <>", value, "productMembershipPrice");
            return (Criteria) this;
        }

        public Criteria andProductMembershipPriceGreaterThan(BigDecimal value) {
            addCriterion("PRODUCT_MEMBERSHIP_PRICE >", value, "productMembershipPrice");
            return (Criteria) this;
        }

        public Criteria andProductMembershipPriceGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("PRODUCT_MEMBERSHIP_PRICE >=", value, "productMembershipPrice");
            return (Criteria) this;
        }

        public Criteria andProductMembershipPriceLessThan(BigDecimal value) {
            addCriterion("PRODUCT_MEMBERSHIP_PRICE <", value, "productMembershipPrice");
            return (Criteria) this;
        }

        public Criteria andProductMembershipPriceLessThanOrEqualTo(BigDecimal value) {
            addCriterion("PRODUCT_MEMBERSHIP_PRICE <=", value, "productMembershipPrice");
            return (Criteria) this;
        }

        public Criteria andProductMembershipPriceIn(List<BigDecimal> values) {
            addCriterion("PRODUCT_MEMBERSHIP_PRICE in", values, "productMembershipPrice");
            return (Criteria) this;
        }

        public Criteria andProductMembershipPriceNotIn(List<BigDecimal> values) {
            addCriterion("PRODUCT_MEMBERSHIP_PRICE not in", values, "productMembershipPrice");
            return (Criteria) this;
        }

        public Criteria andProductMembershipPriceBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("PRODUCT_MEMBERSHIP_PRICE between", value1, value2, "productMembershipPrice");
            return (Criteria) this;
        }

        public Criteria andProductMembershipPriceNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("PRODUCT_MEMBERSHIP_PRICE not between", value1, value2, "productMembershipPrice");
            return (Criteria) this;
        }

        public Criteria andProductDiscountPriceIsNull() {
            addCriterion("PRODUCT_DISCOUNT_PRICE is null");
            return (Criteria) this;
        }

        public Criteria andProductDiscountPriceIsNotNull() {
            addCriterion("PRODUCT_DISCOUNT_PRICE is not null");
            return (Criteria) this;
        }

        public Criteria andProductDiscountPriceEqualTo(BigDecimal value) {
            addCriterion("PRODUCT_DISCOUNT_PRICE =", value, "productDiscountPrice");
            return (Criteria) this;
        }

        public Criteria andProductDiscountPriceNotEqualTo(BigDecimal value) {
            addCriterion("PRODUCT_DISCOUNT_PRICE <>", value, "productDiscountPrice");
            return (Criteria) this;
        }

        public Criteria andProductDiscountPriceGreaterThan(BigDecimal value) {
            addCriterion("PRODUCT_DISCOUNT_PRICE >", value, "productDiscountPrice");
            return (Criteria) this;
        }

        public Criteria andProductDiscountPriceGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("PRODUCT_DISCOUNT_PRICE >=", value, "productDiscountPrice");
            return (Criteria) this;
        }

        public Criteria andProductDiscountPriceLessThan(BigDecimal value) {
            addCriterion("PRODUCT_DISCOUNT_PRICE <", value, "productDiscountPrice");
            return (Criteria) this;
        }

        public Criteria andProductDiscountPriceLessThanOrEqualTo(BigDecimal value) {
            addCriterion("PRODUCT_DISCOUNT_PRICE <=", value, "productDiscountPrice");
            return (Criteria) this;
        }

        public Criteria andProductDiscountPriceIn(List<BigDecimal> values) {
            addCriterion("PRODUCT_DISCOUNT_PRICE in", values, "productDiscountPrice");
            return (Criteria) this;
        }

        public Criteria andProductDiscountPriceNotIn(List<BigDecimal> values) {
            addCriterion("PRODUCT_DISCOUNT_PRICE not in", values, "productDiscountPrice");
            return (Criteria) this;
        }

        public Criteria andProductDiscountPriceBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("PRODUCT_DISCOUNT_PRICE between", value1, value2, "productDiscountPrice");
            return (Criteria) this;
        }

        public Criteria andProductDiscountPriceNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("PRODUCT_DISCOUNT_PRICE not between", value1, value2, "productDiscountPrice");
            return (Criteria) this;
        }

        public Criteria andProductDiscountIsNull() {
            addCriterion("PRODUCT_DISCOUNT is null");
            return (Criteria) this;
        }

        public Criteria andProductDiscountIsNotNull() {
            addCriterion("PRODUCT_DISCOUNT is not null");
            return (Criteria) this;
        }

        public Criteria andProductDiscountEqualTo(BigDecimal value) {
            addCriterion("PRODUCT_DISCOUNT =", value, "productDiscount");
            return (Criteria) this;
        }

        public Criteria andProductDiscountNotEqualTo(BigDecimal value) {
            addCriterion("PRODUCT_DISCOUNT <>", value, "productDiscount");
            return (Criteria) this;
        }

        public Criteria andProductDiscountGreaterThan(BigDecimal value) {
            addCriterion("PRODUCT_DISCOUNT >", value, "productDiscount");
            return (Criteria) this;
        }

        public Criteria andProductDiscountGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("PRODUCT_DISCOUNT >=", value, "productDiscount");
            return (Criteria) this;
        }

        public Criteria andProductDiscountLessThan(BigDecimal value) {
            addCriterion("PRODUCT_DISCOUNT <", value, "productDiscount");
            return (Criteria) this;
        }

        public Criteria andProductDiscountLessThanOrEqualTo(BigDecimal value) {
            addCriterion("PRODUCT_DISCOUNT <=", value, "productDiscount");
            return (Criteria) this;
        }

        public Criteria andProductDiscountIn(List<BigDecimal> values) {
            addCriterion("PRODUCT_DISCOUNT in", values, "productDiscount");
            return (Criteria) this;
        }

        public Criteria andProductDiscountNotIn(List<BigDecimal> values) {
            addCriterion("PRODUCT_DISCOUNT not in", values, "productDiscount");
            return (Criteria) this;
        }

        public Criteria andProductDiscountBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("PRODUCT_DISCOUNT between", value1, value2, "productDiscount");
            return (Criteria) this;
        }

        public Criteria andProductDiscountNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("PRODUCT_DISCOUNT not between", value1, value2, "productDiscount");
            return (Criteria) this;
        }

        public Criteria andProductWholesalePriceIsNull() {
            addCriterion("PRODUCT_WHOLESALE_PRICE is null");
            return (Criteria) this;
        }

        public Criteria andProductWholesalePriceIsNotNull() {
            addCriterion("PRODUCT_WHOLESALE_PRICE is not null");
            return (Criteria) this;
        }

        public Criteria andProductWholesalePriceEqualTo(BigDecimal value) {
            addCriterion("PRODUCT_WHOLESALE_PRICE =", value, "productWholesalePrice");
            return (Criteria) this;
        }

        public Criteria andProductWholesalePriceNotEqualTo(BigDecimal value) {
            addCriterion("PRODUCT_WHOLESALE_PRICE <>", value, "productWholesalePrice");
            return (Criteria) this;
        }

        public Criteria andProductWholesalePriceGreaterThan(BigDecimal value) {
            addCriterion("PRODUCT_WHOLESALE_PRICE >", value, "productWholesalePrice");
            return (Criteria) this;
        }

        public Criteria andProductWholesalePriceGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("PRODUCT_WHOLESALE_PRICE >=", value, "productWholesalePrice");
            return (Criteria) this;
        }

        public Criteria andProductWholesalePriceLessThan(BigDecimal value) {
            addCriterion("PRODUCT_WHOLESALE_PRICE <", value, "productWholesalePrice");
            return (Criteria) this;
        }

        public Criteria andProductWholesalePriceLessThanOrEqualTo(BigDecimal value) {
            addCriterion("PRODUCT_WHOLESALE_PRICE <=", value, "productWholesalePrice");
            return (Criteria) this;
        }

        public Criteria andProductWholesalePriceIn(List<BigDecimal> values) {
            addCriterion("PRODUCT_WHOLESALE_PRICE in", values, "productWholesalePrice");
            return (Criteria) this;
        }

        public Criteria andProductWholesalePriceNotIn(List<BigDecimal> values) {
            addCriterion("PRODUCT_WHOLESALE_PRICE not in", values, "productWholesalePrice");
            return (Criteria) this;
        }

        public Criteria andProductWholesalePriceBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("PRODUCT_WHOLESALE_PRICE between", value1, value2, "productWholesalePrice");
            return (Criteria) this;
        }

        public Criteria andProductWholesalePriceNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("PRODUCT_WHOLESALE_PRICE not between", value1, value2, "productWholesalePrice");
            return (Criteria) this;
        }

        public Criteria andProductMaterialIsNull() {
            addCriterion("PRODUCT_MATERIAL is null");
            return (Criteria) this;
        }

        public Criteria andProductMaterialIsNotNull() {
            addCriterion("PRODUCT_MATERIAL is not null");
            return (Criteria) this;
        }

        public Criteria andProductMaterialEqualTo(String value) {
            addCriterion("PRODUCT_MATERIAL =", value, "productMaterial");
            return (Criteria) this;
        }

        public Criteria andProductMaterialNotEqualTo(String value) {
            addCriterion("PRODUCT_MATERIAL <>", value, "productMaterial");
            return (Criteria) this;
        }

        public Criteria andProductMaterialGreaterThan(String value) {
            addCriterion("PRODUCT_MATERIAL >", value, "productMaterial");
            return (Criteria) this;
        }

        public Criteria andProductMaterialGreaterThanOrEqualTo(String value) {
            addCriterion("PRODUCT_MATERIAL >=", value, "productMaterial");
            return (Criteria) this;
        }

        public Criteria andProductMaterialLessThan(String value) {
            addCriterion("PRODUCT_MATERIAL <", value, "productMaterial");
            return (Criteria) this;
        }

        public Criteria andProductMaterialLessThanOrEqualTo(String value) {
            addCriterion("PRODUCT_MATERIAL <=", value, "productMaterial");
            return (Criteria) this;
        }

        public Criteria andProductMaterialLike(String value) {
            addCriterion("PRODUCT_MATERIAL like", value, "productMaterial");
            return (Criteria) this;
        }

        public Criteria andProductMaterialNotLike(String value) {
            addCriterion("PRODUCT_MATERIAL not like", value, "productMaterial");
            return (Criteria) this;
        }

        public Criteria andProductMaterialIn(List<String> values) {
            addCriterion("PRODUCT_MATERIAL in", values, "productMaterial");
            return (Criteria) this;
        }

        public Criteria andProductMaterialNotIn(List<String> values) {
            addCriterion("PRODUCT_MATERIAL not in", values, "productMaterial");
            return (Criteria) this;
        }

        public Criteria andProductMaterialBetween(String value1, String value2) {
            addCriterion("PRODUCT_MATERIAL between", value1, value2, "productMaterial");
            return (Criteria) this;
        }

        public Criteria andProductMaterialNotBetween(String value1, String value2) {
            addCriterion("PRODUCT_MATERIAL not between", value1, value2, "productMaterial");
            return (Criteria) this;
        }

        public Criteria andProductImageIsNull() {
            addCriterion("PRODUCT_IMAGE is null");
            return (Criteria) this;
        }

        public Criteria andProductImageIsNotNull() {
            addCriterion("PRODUCT_IMAGE is not null");
            return (Criteria) this;
        }

        public Criteria andProductImageEqualTo(String value) {
            addCriterion("PRODUCT_IMAGE =", value, "productImage");
            return (Criteria) this;
        }

        public Criteria andProductImageNotEqualTo(String value) {
            addCriterion("PRODUCT_IMAGE <>", value, "productImage");
            return (Criteria) this;
        }

        public Criteria andProductImageGreaterThan(String value) {
            addCriterion("PRODUCT_IMAGE >", value, "productImage");
            return (Criteria) this;
        }

        public Criteria andProductImageGreaterThanOrEqualTo(String value) {
            addCriterion("PRODUCT_IMAGE >=", value, "productImage");
            return (Criteria) this;
        }

        public Criteria andProductImageLessThan(String value) {
            addCriterion("PRODUCT_IMAGE <", value, "productImage");
            return (Criteria) this;
        }

        public Criteria andProductImageLessThanOrEqualTo(String value) {
            addCriterion("PRODUCT_IMAGE <=", value, "productImage");
            return (Criteria) this;
        }

        public Criteria andProductImageLike(String value) {
            addCriterion("PRODUCT_IMAGE like", value, "productImage");
            return (Criteria) this;
        }

        public Criteria andProductImageNotLike(String value) {
            addCriterion("PRODUCT_IMAGE not like", value, "productImage");
            return (Criteria) this;
        }

        public Criteria andProductImageIn(List<String> values) {
            addCriterion("PRODUCT_IMAGE in", values, "productImage");
            return (Criteria) this;
        }

        public Criteria andProductImageNotIn(List<String> values) {
            addCriterion("PRODUCT_IMAGE not in", values, "productImage");
            return (Criteria) this;
        }

        public Criteria andProductImageBetween(String value1, String value2) {
            addCriterion("PRODUCT_IMAGE between", value1, value2, "productImage");
            return (Criteria) this;
        }

        public Criteria andProductImageNotBetween(String value1, String value2) {
            addCriterion("PRODUCT_IMAGE not between", value1, value2, "productImage");
            return (Criteria) this;
        }

        public Criteria andProductOriginIsNull() {
            addCriterion("PRODUCT_ORIGIN is null");
            return (Criteria) this;
        }

        public Criteria andProductOriginIsNotNull() {
            addCriterion("PRODUCT_ORIGIN is not null");
            return (Criteria) this;
        }

        public Criteria andProductOriginEqualTo(String value) {
            addCriterion("PRODUCT_ORIGIN =", value, "productOrigin");
            return (Criteria) this;
        }

        public Criteria andProductOriginNotEqualTo(String value) {
            addCriterion("PRODUCT_ORIGIN <>", value, "productOrigin");
            return (Criteria) this;
        }

        public Criteria andProductOriginGreaterThan(String value) {
            addCriterion("PRODUCT_ORIGIN >", value, "productOrigin");
            return (Criteria) this;
        }

        public Criteria andProductOriginGreaterThanOrEqualTo(String value) {
            addCriterion("PRODUCT_ORIGIN >=", value, "productOrigin");
            return (Criteria) this;
        }

        public Criteria andProductOriginLessThan(String value) {
            addCriterion("PRODUCT_ORIGIN <", value, "productOrigin");
            return (Criteria) this;
        }

        public Criteria andProductOriginLessThanOrEqualTo(String value) {
            addCriterion("PRODUCT_ORIGIN <=", value, "productOrigin");
            return (Criteria) this;
        }

        public Criteria andProductOriginLike(String value) {
            addCriterion("PRODUCT_ORIGIN like", value, "productOrigin");
            return (Criteria) this;
        }

        public Criteria andProductOriginNotLike(String value) {
            addCriterion("PRODUCT_ORIGIN not like", value, "productOrigin");
            return (Criteria) this;
        }

        public Criteria andProductOriginIn(List<String> values) {
            addCriterion("PRODUCT_ORIGIN in", values, "productOrigin");
            return (Criteria) this;
        }

        public Criteria andProductOriginNotIn(List<String> values) {
            addCriterion("PRODUCT_ORIGIN not in", values, "productOrigin");
            return (Criteria) this;
        }

        public Criteria andProductOriginBetween(String value1, String value2) {
            addCriterion("PRODUCT_ORIGIN between", value1, value2, "productOrigin");
            return (Criteria) this;
        }

        public Criteria andProductOriginNotBetween(String value1, String value2) {
            addCriterion("PRODUCT_ORIGIN not between", value1, value2, "productOrigin");
            return (Criteria) this;
        }

        public Criteria andProductQrcodeIsNull() {
            addCriterion("PRODUCT_QRCODE is null");
            return (Criteria) this;
        }

        public Criteria andProductQrcodeIsNotNull() {
            addCriterion("PRODUCT_QRCODE is not null");
            return (Criteria) this;
        }

        public Criteria andProductQrcodeEqualTo(String value) {
            addCriterion("PRODUCT_QRCODE =", value, "productQrcode");
            return (Criteria) this;
        }

        public Criteria andProductQrcodeNotEqualTo(String value) {
            addCriterion("PRODUCT_QRCODE <>", value, "productQrcode");
            return (Criteria) this;
        }

        public Criteria andProductQrcodeGreaterThan(String value) {
            addCriterion("PRODUCT_QRCODE >", value, "productQrcode");
            return (Criteria) this;
        }

        public Criteria andProductQrcodeGreaterThanOrEqualTo(String value) {
            addCriterion("PRODUCT_QRCODE >=", value, "productQrcode");
            return (Criteria) this;
        }

        public Criteria andProductQrcodeLessThan(String value) {
            addCriterion("PRODUCT_QRCODE <", value, "productQrcode");
            return (Criteria) this;
        }

        public Criteria andProductQrcodeLessThanOrEqualTo(String value) {
            addCriterion("PRODUCT_QRCODE <=", value, "productQrcode");
            return (Criteria) this;
        }

        public Criteria andProductQrcodeLike(String value) {
            addCriterion("PRODUCT_QRCODE like", value, "productQrcode");
            return (Criteria) this;
        }

        public Criteria andProductQrcodeNotLike(String value) {
            addCriterion("PRODUCT_QRCODE not like", value, "productQrcode");
            return (Criteria) this;
        }

        public Criteria andProductQrcodeIn(List<String> values) {
            addCriterion("PRODUCT_QRCODE in", values, "productQrcode");
            return (Criteria) this;
        }

        public Criteria andProductQrcodeNotIn(List<String> values) {
            addCriterion("PRODUCT_QRCODE not in", values, "productQrcode");
            return (Criteria) this;
        }

        public Criteria andProductQrcodeBetween(String value1, String value2) {
            addCriterion("PRODUCT_QRCODE between", value1, value2, "productQrcode");
            return (Criteria) this;
        }

        public Criteria andProductQrcodeNotBetween(String value1, String value2) {
            addCriterion("PRODUCT_QRCODE not between", value1, value2, "productQrcode");
            return (Criteria) this;
        }

        public Criteria andProductBarcodeIsNull() {
            addCriterion("PRODUCT_BARCODE is null");
            return (Criteria) this;
        }

        public Criteria andProductBarcodeIsNotNull() {
            addCriterion("PRODUCT_BARCODE is not null");
            return (Criteria) this;
        }

        public Criteria andProductBarcodeEqualTo(String value) {
            addCriterion("PRODUCT_BARCODE =", value, "productBarcode");
            return (Criteria) this;
        }

        public Criteria andProductBarcodeNotEqualTo(String value) {
            addCriterion("PRODUCT_BARCODE <>", value, "productBarcode");
            return (Criteria) this;
        }

        public Criteria andProductBarcodeGreaterThan(String value) {
            addCriterion("PRODUCT_BARCODE >", value, "productBarcode");
            return (Criteria) this;
        }

        public Criteria andProductBarcodeGreaterThanOrEqualTo(String value) {
            addCriterion("PRODUCT_BARCODE >=", value, "productBarcode");
            return (Criteria) this;
        }

        public Criteria andProductBarcodeLessThan(String value) {
            addCriterion("PRODUCT_BARCODE <", value, "productBarcode");
            return (Criteria) this;
        }

        public Criteria andProductBarcodeLessThanOrEqualTo(String value) {
            addCriterion("PRODUCT_BARCODE <=", value, "productBarcode");
            return (Criteria) this;
        }

        public Criteria andProductBarcodeLike(String value) {
            addCriterion("PRODUCT_BARCODE like", value, "productBarcode");
            return (Criteria) this;
        }

        public Criteria andProductBarcodeNotLike(String value) {
            addCriterion("PRODUCT_BARCODE not like", value, "productBarcode");
            return (Criteria) this;
        }

        public Criteria andProductBarcodeIn(List<String> values) {
            addCriterion("PRODUCT_BARCODE in", values, "productBarcode");
            return (Criteria) this;
        }

        public Criteria andProductBarcodeNotIn(List<String> values) {
            addCriterion("PRODUCT_BARCODE not in", values, "productBarcode");
            return (Criteria) this;
        }

        public Criteria andProductBarcodeBetween(String value1, String value2) {
            addCriterion("PRODUCT_BARCODE between", value1, value2, "productBarcode");
            return (Criteria) this;
        }

        public Criteria andProductBarcodeNotBetween(String value1, String value2) {
            addCriterion("PRODUCT_BARCODE not between", value1, value2, "productBarcode");
            return (Criteria) this;
        }

        public Criteria andProductUnitIsNull() {
            addCriterion("PRODUCT_UNIT is null");
            return (Criteria) this;
        }

        public Criteria andProductUnitIsNotNull() {
            addCriterion("PRODUCT_UNIT is not null");
            return (Criteria) this;
        }

        public Criteria andProductUnitEqualTo(String value) {
            addCriterion("PRODUCT_UNIT =", value, "productUnit");
            return (Criteria) this;
        }

        public Criteria andProductUnitNotEqualTo(String value) {
            addCriterion("PRODUCT_UNIT <>", value, "productUnit");
            return (Criteria) this;
        }

        public Criteria andProductUnitGreaterThan(String value) {
            addCriterion("PRODUCT_UNIT >", value, "productUnit");
            return (Criteria) this;
        }

        public Criteria andProductUnitGreaterThanOrEqualTo(String value) {
            addCriterion("PRODUCT_UNIT >=", value, "productUnit");
            return (Criteria) this;
        }

        public Criteria andProductUnitLessThan(String value) {
            addCriterion("PRODUCT_UNIT <", value, "productUnit");
            return (Criteria) this;
        }

        public Criteria andProductUnitLessThanOrEqualTo(String value) {
            addCriterion("PRODUCT_UNIT <=", value, "productUnit");
            return (Criteria) this;
        }

        public Criteria andProductUnitLike(String value) {
            addCriterion("PRODUCT_UNIT like", value, "productUnit");
            return (Criteria) this;
        }

        public Criteria andProductUnitNotLike(String value) {
            addCriterion("PRODUCT_UNIT not like", value, "productUnit");
            return (Criteria) this;
        }

        public Criteria andProductUnitIn(List<String> values) {
            addCriterion("PRODUCT_UNIT in", values, "productUnit");
            return (Criteria) this;
        }

        public Criteria andProductUnitNotIn(List<String> values) {
            addCriterion("PRODUCT_UNIT not in", values, "productUnit");
            return (Criteria) this;
        }

        public Criteria andProductUnitBetween(String value1, String value2) {
            addCriterion("PRODUCT_UNIT between", value1, value2, "productUnit");
            return (Criteria) this;
        }

        public Criteria andProductUnitNotBetween(String value1, String value2) {
            addCriterion("PRODUCT_UNIT not between", value1, value2, "productUnit");
            return (Criteria) this;
        }

        public Criteria andProductWeightIsNull() {
            addCriterion("PRODUCT_WEIGHT is null");
            return (Criteria) this;
        }

        public Criteria andProductWeightIsNotNull() {
            addCriterion("PRODUCT_WEIGHT is not null");
            return (Criteria) this;
        }

        public Criteria andProductWeightEqualTo(BigDecimal value) {
            addCriterion("PRODUCT_WEIGHT =", value, "productWeight");
            return (Criteria) this;
        }

        public Criteria andProductWeightNotEqualTo(BigDecimal value) {
            addCriterion("PRODUCT_WEIGHT <>", value, "productWeight");
            return (Criteria) this;
        }

        public Criteria andProductWeightGreaterThan(BigDecimal value) {
            addCriterion("PRODUCT_WEIGHT >", value, "productWeight");
            return (Criteria) this;
        }

        public Criteria andProductWeightGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("PRODUCT_WEIGHT >=", value, "productWeight");
            return (Criteria) this;
        }

        public Criteria andProductWeightLessThan(BigDecimal value) {
            addCriterion("PRODUCT_WEIGHT <", value, "productWeight");
            return (Criteria) this;
        }

        public Criteria andProductWeightLessThanOrEqualTo(BigDecimal value) {
            addCriterion("PRODUCT_WEIGHT <=", value, "productWeight");
            return (Criteria) this;
        }

        public Criteria andProductWeightIn(List<BigDecimal> values) {
            addCriterion("PRODUCT_WEIGHT in", values, "productWeight");
            return (Criteria) this;
        }

        public Criteria andProductWeightNotIn(List<BigDecimal> values) {
            addCriterion("PRODUCT_WEIGHT not in", values, "productWeight");
            return (Criteria) this;
        }

        public Criteria andProductWeightBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("PRODUCT_WEIGHT between", value1, value2, "productWeight");
            return (Criteria) this;
        }

        public Criteria andProductWeightNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("PRODUCT_WEIGHT not between", value1, value2, "productWeight");
            return (Criteria) this;
        }

        public Criteria andProductStatusIsNull() {
            addCriterion("PRODUCT_STATUS is null");
            return (Criteria) this;
        }

        public Criteria andProductStatusIsNotNull() {
            addCriterion("PRODUCT_STATUS is not null");
            return (Criteria) this;
        }

        public Criteria andProductStatusEqualTo(String value) {
            addCriterion("PRODUCT_STATUS =", value, "productStatus");
            return (Criteria) this;
        }

        public Criteria andProductStatusNotEqualTo(String value) {
            addCriterion("PRODUCT_STATUS <>", value, "productStatus");
            return (Criteria) this;
        }

        public Criteria andProductStatusGreaterThan(String value) {
            addCriterion("PRODUCT_STATUS >", value, "productStatus");
            return (Criteria) this;
        }

        public Criteria andProductStatusGreaterThanOrEqualTo(String value) {
            addCriterion("PRODUCT_STATUS >=", value, "productStatus");
            return (Criteria) this;
        }

        public Criteria andProductStatusLessThan(String value) {
            addCriterion("PRODUCT_STATUS <", value, "productStatus");
            return (Criteria) this;
        }

        public Criteria andProductStatusLessThanOrEqualTo(String value) {
            addCriterion("PRODUCT_STATUS <=", value, "productStatus");
            return (Criteria) this;
        }

        public Criteria andProductStatusLike(String value) {
            addCriterion("PRODUCT_STATUS like", value, "productStatus");
            return (Criteria) this;
        }

        public Criteria andProductStatusNotLike(String value) {
            addCriterion("PRODUCT_STATUS not like", value, "productStatus");
            return (Criteria) this;
        }

        public Criteria andProductStatusIn(List<String> values) {
            addCriterion("PRODUCT_STATUS in", values, "productStatus");
            return (Criteria) this;
        }

        public Criteria andProductStatusNotIn(List<String> values) {
            addCriterion("PRODUCT_STATUS not in", values, "productStatus");
            return (Criteria) this;
        }

        public Criteria andProductStatusBetween(String value1, String value2) {
            addCriterion("PRODUCT_STATUS between", value1, value2, "productStatus");
            return (Criteria) this;
        }

        public Criteria andProductStatusNotBetween(String value1, String value2) {
            addCriterion("PRODUCT_STATUS not between", value1, value2, "productStatus");
            return (Criteria) this;
        }

        public Criteria andProductStockIsNull() {
            addCriterion("PRODUCT_STOCK is null");
            return (Criteria) this;
        }

        public Criteria andProductStockIsNotNull() {
            addCriterion("PRODUCT_STOCK is not null");
            return (Criteria) this;
        }

        public Criteria andProductStockEqualTo(Integer value) {
            addCriterion("PRODUCT_STOCK =", value, "productStock");
            return (Criteria) this;
        }

        public Criteria andProductStockNotEqualTo(Integer value) {
            addCriterion("PRODUCT_STOCK <>", value, "productStock");
            return (Criteria) this;
        }

        public Criteria andProductStockGreaterThan(Integer value) {
            addCriterion("PRODUCT_STOCK >", value, "productStock");
            return (Criteria) this;
        }

        public Criteria andProductStockGreaterThanOrEqualTo(Integer value) {
            addCriterion("PRODUCT_STOCK >=", value, "productStock");
            return (Criteria) this;
        }

        public Criteria andProductStockLessThan(Integer value) {
            addCriterion("PRODUCT_STOCK <", value, "productStock");
            return (Criteria) this;
        }

        public Criteria andProductStockLessThanOrEqualTo(Integer value) {
            addCriterion("PRODUCT_STOCK <=", value, "productStock");
            return (Criteria) this;
        }

        public Criteria andProductStockIn(List<Integer> values) {
            addCriterion("PRODUCT_STOCK in", values, "productStock");
            return (Criteria) this;
        }

        public Criteria andProductStockNotIn(List<Integer> values) {
            addCriterion("PRODUCT_STOCK not in", values, "productStock");
            return (Criteria) this;
        }

        public Criteria andProductStockBetween(Integer value1, Integer value2) {
            addCriterion("PRODUCT_STOCK between", value1, value2, "productStock");
            return (Criteria) this;
        }

        public Criteria andProductStockNotBetween(Integer value1, Integer value2) {
            addCriterion("PRODUCT_STOCK not between", value1, value2, "productStock");
            return (Criteria) this;
        }

        public Criteria andEslTemplateCodeIsNull() {
            addCriterion("ESL_TEMPLATE_CODE is null");
            return (Criteria) this;
        }

        public Criteria andEslTemplateCodeIsNotNull() {
            addCriterion("ESL_TEMPLATE_CODE is not null");
            return (Criteria) this;
        }

        public Criteria andEslTemplateCodeEqualTo(String value) {
            addCriterion("ESL_TEMPLATE_CODE =", value, "eslTemplateCode");
            return (Criteria) this;
        }

        public Criteria andEslTemplateCodeNotEqualTo(String value) {
            addCriterion("ESL_TEMPLATE_CODE <>", value, "eslTemplateCode");
            return (Criteria) this;
        }

        public Criteria andEslTemplateCodeGreaterThan(String value) {
            addCriterion("ESL_TEMPLATE_CODE >", value, "eslTemplateCode");
            return (Criteria) this;
        }

        public Criteria andEslTemplateCodeGreaterThanOrEqualTo(String value) {
            addCriterion("ESL_TEMPLATE_CODE >=", value, "eslTemplateCode");
            return (Criteria) this;
        }

        public Criteria andEslTemplateCodeLessThan(String value) {
            addCriterion("ESL_TEMPLATE_CODE <", value, "eslTemplateCode");
            return (Criteria) this;
        }

        public Criteria andEslTemplateCodeLessThanOrEqualTo(String value) {
            addCriterion("ESL_TEMPLATE_CODE <=", value, "eslTemplateCode");
            return (Criteria) this;
        }

        public Criteria andEslTemplateCodeLike(String value) {
            addCriterion("ESL_TEMPLATE_CODE like", value, "eslTemplateCode");
            return (Criteria) this;
        }

        public Criteria andEslTemplateCodeNotLike(String value) {
            addCriterion("ESL_TEMPLATE_CODE not like", value, "eslTemplateCode");
            return (Criteria) this;
        }

        public Criteria andEslTemplateCodeIn(List<String> values) {
            addCriterion("ESL_TEMPLATE_CODE in", values, "eslTemplateCode");
            return (Criteria) this;
        }

        public Criteria andEslTemplateCodeNotIn(List<String> values) {
            addCriterion("ESL_TEMPLATE_CODE not in", values, "eslTemplateCode");
            return (Criteria) this;
        }

        public Criteria andEslTemplateCodeBetween(String value1, String value2) {
            addCriterion("ESL_TEMPLATE_CODE between", value1, value2, "eslTemplateCode");
            return (Criteria) this;
        }

        public Criteria andEslTemplateCodeNotBetween(String value1, String value2) {
            addCriterion("ESL_TEMPLATE_CODE not between", value1, value2, "eslTemplateCode");
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