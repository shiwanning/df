package com.tcg.mis.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MisBankAcctsExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public MisBankAcctsExample() {
        oredCriteria = new ArrayList<Criteria>();
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
            criteria = new ArrayList<Criterion>();
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

        public Criteria andRidIsNull() {
            addCriterion("RID is null");
            return (Criteria) this;
        }

        public Criteria andRidIsNotNull() {
            addCriterion("RID is not null");
            return (Criteria) this;
        }

        public Criteria andRidEqualTo(BigDecimal value) {
            addCriterion("RID =", value, "rid");
            return (Criteria) this;
        }

        public Criteria andRidNotEqualTo(BigDecimal value) {
            addCriterion("RID <>", value, "rid");
            return (Criteria) this;
        }

        public Criteria andRidGreaterThan(BigDecimal value) {
            addCriterion("RID >", value, "rid");
            return (Criteria) this;
        }

        public Criteria andRidGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("RID >=", value, "rid");
            return (Criteria) this;
        }

        public Criteria andRidLessThan(BigDecimal value) {
            addCriterion("RID <", value, "rid");
            return (Criteria) this;
        }

        public Criteria andRidLessThanOrEqualTo(BigDecimal value) {
            addCriterion("RID <=", value, "rid");
            return (Criteria) this;
        }

        public Criteria andRidIn(List<BigDecimal> values) {
            addCriterion("RID in", values, "rid");
            return (Criteria) this;
        }

        public Criteria andRidNotIn(List<BigDecimal> values) {
            addCriterion("RID not in", values, "rid");
            return (Criteria) this;
        }

        public Criteria andRidBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("RID between", value1, value2, "rid");
            return (Criteria) this;
        }

        public Criteria andRidNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("RID not between", value1, value2, "rid");
            return (Criteria) this;
        }

        public Criteria andBankIdIsNull() {
            addCriterion("BANK_ID is null");
            return (Criteria) this;
        }

        public Criteria andBankIdIsNotNull() {
            addCriterion("BANK_ID is not null");
            return (Criteria) this;
        }

        public Criteria andBankIdEqualTo(BigDecimal value) {
            addCriterion("BANK_ID =", value, "bankId");
            return (Criteria) this;
        }

        public Criteria andBankIdNotEqualTo(BigDecimal value) {
            addCriterion("BANK_ID <>", value, "bankId");
            return (Criteria) this;
        }

        public Criteria andBankIdGreaterThan(BigDecimal value) {
            addCriterion("BANK_ID >", value, "bankId");
            return (Criteria) this;
        }

        public Criteria andBankIdGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("BANK_ID >=", value, "bankId");
            return (Criteria) this;
        }

        public Criteria andBankIdLessThan(BigDecimal value) {
            addCriterion("BANK_ID <", value, "bankId");
            return (Criteria) this;
        }

        public Criteria andBankIdLessThanOrEqualTo(BigDecimal value) {
            addCriterion("BANK_ID <=", value, "bankId");
            return (Criteria) this;
        }

        public Criteria andBankIdIn(List<BigDecimal> values) {
            addCriterion("BANK_ID in", values, "bankId");
            return (Criteria) this;
        }

        public Criteria andBankIdNotIn(List<BigDecimal> values) {
            addCriterion("BANK_ID not in", values, "bankId");
            return (Criteria) this;
        }

        public Criteria andBankIdBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("BANK_ID between", value1, value2, "bankId");
            return (Criteria) this;
        }

        public Criteria andBankIdNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("BANK_ID not between", value1, value2, "bankId");
            return (Criteria) this;
        }

        public Criteria andBankNameIsNull() {
            addCriterion("BANK_NAME is null");
            return (Criteria) this;
        }

        public Criteria andBankNameIsNotNull() {
            addCriterion("BANK_NAME is not null");
            return (Criteria) this;
        }

        public Criteria andBankNameEqualTo(String value) {
            addCriterion("BANK_NAME =", value, "bankName");
            return (Criteria) this;
        }

        public Criteria andBankNameNotEqualTo(String value) {
            addCriterion("BANK_NAME <>", value, "bankName");
            return (Criteria) this;
        }

        public Criteria andBankNameGreaterThan(String value) {
            addCriterion("BANK_NAME >", value, "bankName");
            return (Criteria) this;
        }

        public Criteria andBankNameGreaterThanOrEqualTo(String value) {
            addCriterion("BANK_NAME >=", value, "bankName");
            return (Criteria) this;
        }

        public Criteria andBankNameLessThan(String value) {
            addCriterion("BANK_NAME <", value, "bankName");
            return (Criteria) this;
        }

        public Criteria andBankNameLessThanOrEqualTo(String value) {
            addCriterion("BANK_NAME <=", value, "bankName");
            return (Criteria) this;
        }

        public Criteria andBankNameLike(String value) {
            addCriterion("BANK_NAME like", value, "bankName");
            return (Criteria) this;
        }

        public Criteria andBankNameNotLike(String value) {
            addCriterion("BANK_NAME not like", value, "bankName");
            return (Criteria) this;
        }

        public Criteria andBankNameIn(List<String> values) {
            addCriterion("BANK_NAME in", values, "bankName");
            return (Criteria) this;
        }

        public Criteria andBankNameNotIn(List<String> values) {
            addCriterion("BANK_NAME not in", values, "bankName");
            return (Criteria) this;
        }

        public Criteria andBankNameBetween(String value1, String value2) {
            addCriterion("BANK_NAME between", value1, value2, "bankName");
            return (Criteria) this;
        }

        public Criteria andBankNameNotBetween(String value1, String value2) {
            addCriterion("BANK_NAME not between", value1, value2, "bankName");
            return (Criteria) this;
        }

        public Criteria andBankTypeIsNull() {
            addCriterion("BANK_TYPE is null");
            return (Criteria) this;
        }

        public Criteria andBankTypeIsNotNull() {
            addCriterion("BANK_TYPE is not null");
            return (Criteria) this;
        }

        public Criteria andBankTypeEqualTo(String value) {
            addCriterion("BANK_TYPE =", value, "bankType");
            return (Criteria) this;
        }

        public Criteria andBankTypeNotEqualTo(String value) {
            addCriterion("BANK_TYPE <>", value, "bankType");
            return (Criteria) this;
        }

        public Criteria andBankTypeGreaterThan(String value) {
            addCriterion("BANK_TYPE >", value, "bankType");
            return (Criteria) this;
        }

        public Criteria andBankTypeGreaterThanOrEqualTo(String value) {
            addCriterion("BANK_TYPE >=", value, "bankType");
            return (Criteria) this;
        }

        public Criteria andBankTypeLessThan(String value) {
            addCriterion("BANK_TYPE <", value, "bankType");
            return (Criteria) this;
        }

        public Criteria andBankTypeLessThanOrEqualTo(String value) {
            addCriterion("BANK_TYPE <=", value, "bankType");
            return (Criteria) this;
        }

        public Criteria andBankTypeLike(String value) {
            addCriterion("BANK_TYPE like", value, "bankType");
            return (Criteria) this;
        }

        public Criteria andBankTypeNotLike(String value) {
            addCriterion("BANK_TYPE not like", value, "bankType");
            return (Criteria) this;
        }

        public Criteria andBankTypeIn(List<String> values) {
            addCriterion("BANK_TYPE in", values, "bankType");
            return (Criteria) this;
        }

        public Criteria andBankTypeNotIn(List<String> values) {
            addCriterion("BANK_TYPE not in", values, "bankType");
            return (Criteria) this;
        }

        public Criteria andBankTypeBetween(String value1, String value2) {
            addCriterion("BANK_TYPE between", value1, value2, "bankType");
            return (Criteria) this;
        }

        public Criteria andBankTypeNotBetween(String value1, String value2) {
            addCriterion("BANK_TYPE not between", value1, value2, "bankType");
            return (Criteria) this;
        }

        public Criteria andChannelNameIsNull() {
            addCriterion("CHANNEL_NAME is null");
            return (Criteria) this;
        }

        public Criteria andChannelNameIsNotNull() {
            addCriterion("CHANNEL_NAME is not null");
            return (Criteria) this;
        }

        public Criteria andChannelNameEqualTo(String value) {
            addCriterion("CHANNEL_NAME =", value, "channelName");
            return (Criteria) this;
        }

        public Criteria andChannelNameNotEqualTo(String value) {
            addCriterion("CHANNEL_NAME <>", value, "channelName");
            return (Criteria) this;
        }

        public Criteria andChannelNameGreaterThan(String value) {
            addCriterion("CHANNEL_NAME >", value, "channelName");
            return (Criteria) this;
        }

        public Criteria andChannelNameGreaterThanOrEqualTo(String value) {
            addCriterion("CHANNEL_NAME >=", value, "channelName");
            return (Criteria) this;
        }

        public Criteria andChannelNameLessThan(String value) {
            addCriterion("CHANNEL_NAME <", value, "channelName");
            return (Criteria) this;
        }

        public Criteria andChannelNameLessThanOrEqualTo(String value) {
            addCriterion("CHANNEL_NAME <=", value, "channelName");
            return (Criteria) this;
        }

        public Criteria andChannelNameLike(String value) {
            addCriterion("CHANNEL_NAME like", value, "channelName");
            return (Criteria) this;
        }

        public Criteria andChannelNameNotLike(String value) {
            addCriterion("CHANNEL_NAME not like", value, "channelName");
            return (Criteria) this;
        }

        public Criteria andChannelNameIn(List<String> values) {
            addCriterion("CHANNEL_NAME in", values, "channelName");
            return (Criteria) this;
        }

        public Criteria andChannelNameNotIn(List<String> values) {
            addCriterion("CHANNEL_NAME not in", values, "channelName");
            return (Criteria) this;
        }

        public Criteria andChannelNameBetween(String value1, String value2) {
            addCriterion("CHANNEL_NAME between", value1, value2, "channelName");
            return (Criteria) this;
        }

        public Criteria andChannelNameNotBetween(String value1, String value2) {
            addCriterion("CHANNEL_NAME not between", value1, value2, "channelName");
            return (Criteria) this;
        }

        public Criteria andAcctNumberIsNull() {
            addCriterion("ACCT_NUMBER is null");
            return (Criteria) this;
        }

        public Criteria andAcctNumberIsNotNull() {
            addCriterion("ACCT_NUMBER is not null");
            return (Criteria) this;
        }

        public Criteria andAcctNumberEqualTo(String value) {
            addCriterion("ACCT_NUMBER =", value, "acctNumber");
            return (Criteria) this;
        }

        public Criteria andAcctNumberNotEqualTo(String value) {
            addCriterion("ACCT_NUMBER <>", value, "acctNumber");
            return (Criteria) this;
        }

        public Criteria andAcctNumberGreaterThan(String value) {
            addCriterion("ACCT_NUMBER >", value, "acctNumber");
            return (Criteria) this;
        }

        public Criteria andAcctNumberGreaterThanOrEqualTo(String value) {
            addCriterion("ACCT_NUMBER >=", value, "acctNumber");
            return (Criteria) this;
        }

        public Criteria andAcctNumberLessThan(String value) {
            addCriterion("ACCT_NUMBER <", value, "acctNumber");
            return (Criteria) this;
        }

        public Criteria andAcctNumberLessThanOrEqualTo(String value) {
            addCriterion("ACCT_NUMBER <=", value, "acctNumber");
            return (Criteria) this;
        }

        public Criteria andAcctNumberLike(String value) {
            addCriterion("ACCT_NUMBER like", value, "acctNumber");
            return (Criteria) this;
        }

        public Criteria andAcctNumberNotLike(String value) {
            addCriterion("ACCT_NUMBER not like", value, "acctNumber");
            return (Criteria) this;
        }

        public Criteria andAcctNumberIn(List<String> values) {
            addCriterion("ACCT_NUMBER in", values, "acctNumber");
            return (Criteria) this;
        }

        public Criteria andAcctNumberNotIn(List<String> values) {
            addCriterion("ACCT_NUMBER not in", values, "acctNumber");
            return (Criteria) this;
        }

        public Criteria andAcctNumberBetween(String value1, String value2) {
            addCriterion("ACCT_NUMBER between", value1, value2, "acctNumber");
            return (Criteria) this;
        }

        public Criteria andAcctNumberNotBetween(String value1, String value2) {
            addCriterion("ACCT_NUMBER not between", value1, value2, "acctNumber");
            return (Criteria) this;
        }

        public Criteria andAcctNameIsNull() {
            addCriterion("ACCT_NAME is null");
            return (Criteria) this;
        }

        public Criteria andAcctNameIsNotNull() {
            addCriterion("ACCT_NAME is not null");
            return (Criteria) this;
        }

        public Criteria andAcctNameEqualTo(String value) {
            addCriterion("ACCT_NAME =", value, "acctName");
            return (Criteria) this;
        }

        public Criteria andAcctNameNotEqualTo(String value) {
            addCriterion("ACCT_NAME <>", value, "acctName");
            return (Criteria) this;
        }

        public Criteria andAcctNameGreaterThan(String value) {
            addCriterion("ACCT_NAME >", value, "acctName");
            return (Criteria) this;
        }

        public Criteria andAcctNameGreaterThanOrEqualTo(String value) {
            addCriterion("ACCT_NAME >=", value, "acctName");
            return (Criteria) this;
        }

        public Criteria andAcctNameLessThan(String value) {
            addCriterion("ACCT_NAME <", value, "acctName");
            return (Criteria) this;
        }

        public Criteria andAcctNameLessThanOrEqualTo(String value) {
            addCriterion("ACCT_NAME <=", value, "acctName");
            return (Criteria) this;
        }

        public Criteria andAcctNameLike(String value) {
            addCriterion("ACCT_NAME like", value, "acctName");
            return (Criteria) this;
        }

        public Criteria andAcctNameNotLike(String value) {
            addCriterion("ACCT_NAME not like", value, "acctName");
            return (Criteria) this;
        }

        public Criteria andAcctNameIn(List<String> values) {
            addCriterion("ACCT_NAME in", values, "acctName");
            return (Criteria) this;
        }

        public Criteria andAcctNameNotIn(List<String> values) {
            addCriterion("ACCT_NAME not in", values, "acctName");
            return (Criteria) this;
        }

        public Criteria andAcctNameBetween(String value1, String value2) {
            addCriterion("ACCT_NAME between", value1, value2, "acctName");
            return (Criteria) this;
        }

        public Criteria andAcctNameNotBetween(String value1, String value2) {
            addCriterion("ACCT_NAME not between", value1, value2, "acctName");
            return (Criteria) this;
        }

        public Criteria andStatusIsNull() {
            addCriterion("`STATUS` is null");
            return (Criteria) this;
        }

        public Criteria andStatusIsNotNull() {
            addCriterion("`STATUS` is not null");
            return (Criteria) this;
        }

        public Criteria andStatusEqualTo(BigDecimal value) {
            addCriterion("`STATUS` =", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotEqualTo(BigDecimal value) {
            addCriterion("`STATUS` <>", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThan(BigDecimal value) {
            addCriterion("`STATUS` >", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("`STATUS` >=", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLessThan(BigDecimal value) {
            addCriterion("`STATUS` <", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLessThanOrEqualTo(BigDecimal value) {
            addCriterion("`STATUS` <=", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusIn(List<BigDecimal> values) {
            addCriterion("`STATUS` in", values, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotIn(List<BigDecimal> values) {
            addCriterion("`STATUS` not in", values, "status");
            return (Criteria) this;
        }

        public Criteria andStatusBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("`STATUS` between", value1, value2, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("`STATUS` not between", value1, value2, "status");
            return (Criteria) this;
        }

        public Criteria andTpAcctIdIsNull() {
            addCriterion("TP_ACCT_ID is null");
            return (Criteria) this;
        }

        public Criteria andTpAcctIdIsNotNull() {
            addCriterion("TP_ACCT_ID is not null");
            return (Criteria) this;
        }

        public Criteria andTpAcctIdEqualTo(BigDecimal value) {
            addCriterion("TP_ACCT_ID =", value, "tpAcctId");
            return (Criteria) this;
        }

        public Criteria andTpAcctIdNotEqualTo(BigDecimal value) {
            addCriterion("TP_ACCT_ID <>", value, "tpAcctId");
            return (Criteria) this;
        }

        public Criteria andTpAcctIdGreaterThan(BigDecimal value) {
            addCriterion("TP_ACCT_ID >", value, "tpAcctId");
            return (Criteria) this;
        }

        public Criteria andTpAcctIdGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("TP_ACCT_ID >=", value, "tpAcctId");
            return (Criteria) this;
        }

        public Criteria andTpAcctIdLessThan(BigDecimal value) {
            addCriterion("TP_ACCT_ID <", value, "tpAcctId");
            return (Criteria) this;
        }

        public Criteria andTpAcctIdLessThanOrEqualTo(BigDecimal value) {
            addCriterion("TP_ACCT_ID <=", value, "tpAcctId");
            return (Criteria) this;
        }

        public Criteria andTpAcctIdIn(List<BigDecimal> values) {
            addCriterion("TP_ACCT_ID in", values, "tpAcctId");
            return (Criteria) this;
        }

        public Criteria andTpAcctIdNotIn(List<BigDecimal> values) {
            addCriterion("TP_ACCT_ID not in", values, "tpAcctId");
            return (Criteria) this;
        }

        public Criteria andTpAcctIdBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("TP_ACCT_ID between", value1, value2, "tpAcctId");
            return (Criteria) this;
        }

        public Criteria andTpAcctIdNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("TP_ACCT_ID not between", value1, value2, "tpAcctId");
            return (Criteria) this;
        }

        public Criteria andMtDisplayAccountIsNull() {
            addCriterion("MT_DISPLAY_ACCOUNT is null");
            return (Criteria) this;
        }

        public Criteria andMtDisplayAccountIsNotNull() {
            addCriterion("MT_DISPLAY_ACCOUNT is not null");
            return (Criteria) this;
        }

        public Criteria andMtDisplayAccountEqualTo(String value) {
            addCriterion("MT_DISPLAY_ACCOUNT =", value, "mtDisplayAccount");
            return (Criteria) this;
        }

        public Criteria andMtDisplayAccountNotEqualTo(String value) {
            addCriterion("MT_DISPLAY_ACCOUNT <>", value, "mtDisplayAccount");
            return (Criteria) this;
        }

        public Criteria andMtDisplayAccountGreaterThan(String value) {
            addCriterion("MT_DISPLAY_ACCOUNT >", value, "mtDisplayAccount");
            return (Criteria) this;
        }

        public Criteria andMtDisplayAccountGreaterThanOrEqualTo(String value) {
            addCriterion("MT_DISPLAY_ACCOUNT >=", value, "mtDisplayAccount");
            return (Criteria) this;
        }

        public Criteria andMtDisplayAccountLessThan(String value) {
            addCriterion("MT_DISPLAY_ACCOUNT <", value, "mtDisplayAccount");
            return (Criteria) this;
        }

        public Criteria andMtDisplayAccountLessThanOrEqualTo(String value) {
            addCriterion("MT_DISPLAY_ACCOUNT <=", value, "mtDisplayAccount");
            return (Criteria) this;
        }

        public Criteria andMtDisplayAccountLike(String value) {
            addCriterion("MT_DISPLAY_ACCOUNT like", value, "mtDisplayAccount");
            return (Criteria) this;
        }

        public Criteria andMtDisplayAccountNotLike(String value) {
            addCriterion("MT_DISPLAY_ACCOUNT not like", value, "mtDisplayAccount");
            return (Criteria) this;
        }

        public Criteria andMtDisplayAccountIn(List<String> values) {
            addCriterion("MT_DISPLAY_ACCOUNT in", values, "mtDisplayAccount");
            return (Criteria) this;
        }

        public Criteria andMtDisplayAccountNotIn(List<String> values) {
            addCriterion("MT_DISPLAY_ACCOUNT not in", values, "mtDisplayAccount");
            return (Criteria) this;
        }

        public Criteria andMtDisplayAccountBetween(String value1, String value2) {
            addCriterion("MT_DISPLAY_ACCOUNT between", value1, value2, "mtDisplayAccount");
            return (Criteria) this;
        }

        public Criteria andMtDisplayAccountNotBetween(String value1, String value2) {
            addCriterion("MT_DISPLAY_ACCOUNT not between", value1, value2, "mtDisplayAccount");
            return (Criteria) this;
        }

        public Criteria andBranchIsNull() {
            addCriterion("BRANCH is null");
            return (Criteria) this;
        }

        public Criteria andBranchIsNotNull() {
            addCriterion("BRANCH is not null");
            return (Criteria) this;
        }

        public Criteria andBranchEqualTo(String value) {
            addCriterion("BRANCH =", value, "branch");
            return (Criteria) this;
        }

        public Criteria andBranchNotEqualTo(String value) {
            addCriterion("BRANCH <>", value, "branch");
            return (Criteria) this;
        }

        public Criteria andBranchGreaterThan(String value) {
            addCriterion("BRANCH >", value, "branch");
            return (Criteria) this;
        }

        public Criteria andBranchGreaterThanOrEqualTo(String value) {
            addCriterion("BRANCH >=", value, "branch");
            return (Criteria) this;
        }

        public Criteria andBranchLessThan(String value) {
            addCriterion("BRANCH <", value, "branch");
            return (Criteria) this;
        }

        public Criteria andBranchLessThanOrEqualTo(String value) {
            addCriterion("BRANCH <=", value, "branch");
            return (Criteria) this;
        }

        public Criteria andBranchLike(String value) {
            addCriterion("BRANCH like", value, "branch");
            return (Criteria) this;
        }

        public Criteria andBranchNotLike(String value) {
            addCriterion("BRANCH not like", value, "branch");
            return (Criteria) this;
        }

        public Criteria andBranchIn(List<String> values) {
            addCriterion("BRANCH in", values, "branch");
            return (Criteria) this;
        }

        public Criteria andBranchNotIn(List<String> values) {
            addCriterion("BRANCH not in", values, "branch");
            return (Criteria) this;
        }

        public Criteria andBranchBetween(String value1, String value2) {
            addCriterion("BRANCH between", value1, value2, "branch");
            return (Criteria) this;
        }

        public Criteria andBranchNotBetween(String value1, String value2) {
            addCriterion("BRANCH not between", value1, value2, "branch");
            return (Criteria) this;
        }

        public Criteria andCityIsNull() {
            addCriterion("CITY is null");
            return (Criteria) this;
        }

        public Criteria andCityIsNotNull() {
            addCriterion("CITY is not null");
            return (Criteria) this;
        }

        public Criteria andCityEqualTo(String value) {
            addCriterion("CITY =", value, "city");
            return (Criteria) this;
        }

        public Criteria andCityNotEqualTo(String value) {
            addCriterion("CITY <>", value, "city");
            return (Criteria) this;
        }

        public Criteria andCityGreaterThan(String value) {
            addCriterion("CITY >", value, "city");
            return (Criteria) this;
        }

        public Criteria andCityGreaterThanOrEqualTo(String value) {
            addCriterion("CITY >=", value, "city");
            return (Criteria) this;
        }

        public Criteria andCityLessThan(String value) {
            addCriterion("CITY <", value, "city");
            return (Criteria) this;
        }

        public Criteria andCityLessThanOrEqualTo(String value) {
            addCriterion("CITY <=", value, "city");
            return (Criteria) this;
        }

        public Criteria andCityLike(String value) {
            addCriterion("CITY like", value, "city");
            return (Criteria) this;
        }

        public Criteria andCityNotLike(String value) {
            addCriterion("CITY not like", value, "city");
            return (Criteria) this;
        }

        public Criteria andCityIn(List<String> values) {
            addCriterion("CITY in", values, "city");
            return (Criteria) this;
        }

        public Criteria andCityNotIn(List<String> values) {
            addCriterion("CITY not in", values, "city");
            return (Criteria) this;
        }

        public Criteria andCityBetween(String value1, String value2) {
            addCriterion("CITY between", value1, value2, "city");
            return (Criteria) this;
        }

        public Criteria andCityNotBetween(String value1, String value2) {
            addCriterion("CITY not between", value1, value2, "city");
            return (Criteria) this;
        }

        public Criteria andStateIsNull() {
            addCriterion("`STATE` is null");
            return (Criteria) this;
        }

        public Criteria andStateIsNotNull() {
            addCriterion("`STATE` is not null");
            return (Criteria) this;
        }

        public Criteria andStateEqualTo(String value) {
            addCriterion("`STATE` =", value, "state");
            return (Criteria) this;
        }

        public Criteria andStateNotEqualTo(String value) {
            addCriterion("`STATE` <>", value, "state");
            return (Criteria) this;
        }

        public Criteria andStateGreaterThan(String value) {
            addCriterion("`STATE` >", value, "state");
            return (Criteria) this;
        }

        public Criteria andStateGreaterThanOrEqualTo(String value) {
            addCriterion("`STATE` >=", value, "state");
            return (Criteria) this;
        }

        public Criteria andStateLessThan(String value) {
            addCriterion("`STATE` <", value, "state");
            return (Criteria) this;
        }

        public Criteria andStateLessThanOrEqualTo(String value) {
            addCriterion("`STATE` <=", value, "state");
            return (Criteria) this;
        }

        public Criteria andStateLike(String value) {
            addCriterion("`STATE` like", value, "state");
            return (Criteria) this;
        }

        public Criteria andStateNotLike(String value) {
            addCriterion("`STATE` not like", value, "state");
            return (Criteria) this;
        }

        public Criteria andStateIn(List<String> values) {
            addCriterion("`STATE` in", values, "state");
            return (Criteria) this;
        }

        public Criteria andStateNotIn(List<String> values) {
            addCriterion("`STATE` not in", values, "state");
            return (Criteria) this;
        }

        public Criteria andStateBetween(String value1, String value2) {
            addCriterion("`STATE` between", value1, value2, "state");
            return (Criteria) this;
        }

        public Criteria andStateNotBetween(String value1, String value2) {
            addCriterion("`STATE` not between", value1, value2, "state");
            return (Criteria) this;
        }

        public Criteria andAcsCustIdIsNull() {
            addCriterion("ACS_CUST_ID is null");
            return (Criteria) this;
        }

        public Criteria andAcsCustIdIsNotNull() {
            addCriterion("ACS_CUST_ID is not null");
            return (Criteria) this;
        }

        public Criteria andAcsCustIdEqualTo(BigDecimal value) {
            addCriterion("ACS_CUST_ID =", value, "acsCustId");
            return (Criteria) this;
        }

        public Criteria andAcsCustIdNotEqualTo(BigDecimal value) {
            addCriterion("ACS_CUST_ID <>", value, "acsCustId");
            return (Criteria) this;
        }

        public Criteria andAcsCustIdGreaterThan(BigDecimal value) {
            addCriterion("ACS_CUST_ID >", value, "acsCustId");
            return (Criteria) this;
        }

        public Criteria andAcsCustIdGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("ACS_CUST_ID >=", value, "acsCustId");
            return (Criteria) this;
        }

        public Criteria andAcsCustIdLessThan(BigDecimal value) {
            addCriterion("ACS_CUST_ID <", value, "acsCustId");
            return (Criteria) this;
        }

        public Criteria andAcsCustIdLessThanOrEqualTo(BigDecimal value) {
            addCriterion("ACS_CUST_ID <=", value, "acsCustId");
            return (Criteria) this;
        }

        public Criteria andAcsCustIdIn(List<BigDecimal> values) {
            addCriterion("ACS_CUST_ID in", values, "acsCustId");
            return (Criteria) this;
        }

        public Criteria andAcsCustIdNotIn(List<BigDecimal> values) {
            addCriterion("ACS_CUST_ID not in", values, "acsCustId");
            return (Criteria) this;
        }

        public Criteria andAcsCustIdBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("ACS_CUST_ID between", value1, value2, "acsCustId");
            return (Criteria) this;
        }

        public Criteria andAcsCustIdNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("ACS_CUST_ID not between", value1, value2, "acsCustId");
            return (Criteria) this;
        }

        public Criteria andAcsAcctIdIsNull() {
            addCriterion("ACS_ACCT_ID is null");
            return (Criteria) this;
        }

        public Criteria andAcsAcctIdIsNotNull() {
            addCriterion("ACS_ACCT_ID is not null");
            return (Criteria) this;
        }

        public Criteria andAcsAcctIdEqualTo(BigDecimal value) {
            addCriterion("ACS_ACCT_ID =", value, "acsAcctId");
            return (Criteria) this;
        }

        public Criteria andAcsAcctIdNotEqualTo(BigDecimal value) {
            addCriterion("ACS_ACCT_ID <>", value, "acsAcctId");
            return (Criteria) this;
        }

        public Criteria andAcsAcctIdGreaterThan(BigDecimal value) {
            addCriterion("ACS_ACCT_ID >", value, "acsAcctId");
            return (Criteria) this;
        }

        public Criteria andAcsAcctIdGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("ACS_ACCT_ID >=", value, "acsAcctId");
            return (Criteria) this;
        }

        public Criteria andAcsAcctIdLessThan(BigDecimal value) {
            addCriterion("ACS_ACCT_ID <", value, "acsAcctId");
            return (Criteria) this;
        }

        public Criteria andAcsAcctIdLessThanOrEqualTo(BigDecimal value) {
            addCriterion("ACS_ACCT_ID <=", value, "acsAcctId");
            return (Criteria) this;
        }

        public Criteria andAcsAcctIdIn(List<BigDecimal> values) {
            addCriterion("ACS_ACCT_ID in", values, "acsAcctId");
            return (Criteria) this;
        }

        public Criteria andAcsAcctIdNotIn(List<BigDecimal> values) {
            addCriterion("ACS_ACCT_ID not in", values, "acsAcctId");
            return (Criteria) this;
        }

        public Criteria andAcsAcctIdBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("ACS_ACCT_ID between", value1, value2, "acsAcctId");
            return (Criteria) this;
        }

        public Criteria andAcsAcctIdNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("ACS_ACCT_ID not between", value1, value2, "acsAcctId");
            return (Criteria) this;
        }

        public Criteria andCreateOperatorNameIsNull() {
            addCriterion("CREATE_OPERATOR_NAME is null");
            return (Criteria) this;
        }

        public Criteria andCreateOperatorNameIsNotNull() {
            addCriterion("CREATE_OPERATOR_NAME is not null");
            return (Criteria) this;
        }

        public Criteria andCreateOperatorNameEqualTo(String value) {
            addCriterion("CREATE_OPERATOR_NAME =", value, "createOperatorName");
            return (Criteria) this;
        }

        public Criteria andCreateOperatorNameNotEqualTo(String value) {
            addCriterion("CREATE_OPERATOR_NAME <>", value, "createOperatorName");
            return (Criteria) this;
        }

        public Criteria andCreateOperatorNameGreaterThan(String value) {
            addCriterion("CREATE_OPERATOR_NAME >", value, "createOperatorName");
            return (Criteria) this;
        }

        public Criteria andCreateOperatorNameGreaterThanOrEqualTo(String value) {
            addCriterion("CREATE_OPERATOR_NAME >=", value, "createOperatorName");
            return (Criteria) this;
        }

        public Criteria andCreateOperatorNameLessThan(String value) {
            addCriterion("CREATE_OPERATOR_NAME <", value, "createOperatorName");
            return (Criteria) this;
        }

        public Criteria andCreateOperatorNameLessThanOrEqualTo(String value) {
            addCriterion("CREATE_OPERATOR_NAME <=", value, "createOperatorName");
            return (Criteria) this;
        }

        public Criteria andCreateOperatorNameLike(String value) {
            addCriterion("CREATE_OPERATOR_NAME like", value, "createOperatorName");
            return (Criteria) this;
        }

        public Criteria andCreateOperatorNameNotLike(String value) {
            addCriterion("CREATE_OPERATOR_NAME not like", value, "createOperatorName");
            return (Criteria) this;
        }

        public Criteria andCreateOperatorNameIn(List<String> values) {
            addCriterion("CREATE_OPERATOR_NAME in", values, "createOperatorName");
            return (Criteria) this;
        }

        public Criteria andCreateOperatorNameNotIn(List<String> values) {
            addCriterion("CREATE_OPERATOR_NAME not in", values, "createOperatorName");
            return (Criteria) this;
        }

        public Criteria andCreateOperatorNameBetween(String value1, String value2) {
            addCriterion("CREATE_OPERATOR_NAME between", value1, value2, "createOperatorName");
            return (Criteria) this;
        }

        public Criteria andCreateOperatorNameNotBetween(String value1, String value2) {
            addCriterion("CREATE_OPERATOR_NAME not between", value1, value2, "createOperatorName");
            return (Criteria) this;
        }

        public Criteria andUpdateOperatorNameIsNull() {
            addCriterion("UPDATE_OPERATOR_NAME is null");
            return (Criteria) this;
        }

        public Criteria andUpdateOperatorNameIsNotNull() {
            addCriterion("UPDATE_OPERATOR_NAME is not null");
            return (Criteria) this;
        }

        public Criteria andUpdateOperatorNameEqualTo(String value) {
            addCriterion("UPDATE_OPERATOR_NAME =", value, "updateOperatorName");
            return (Criteria) this;
        }

        public Criteria andUpdateOperatorNameNotEqualTo(String value) {
            addCriterion("UPDATE_OPERATOR_NAME <>", value, "updateOperatorName");
            return (Criteria) this;
        }

        public Criteria andUpdateOperatorNameGreaterThan(String value) {
            addCriterion("UPDATE_OPERATOR_NAME >", value, "updateOperatorName");
            return (Criteria) this;
        }

        public Criteria andUpdateOperatorNameGreaterThanOrEqualTo(String value) {
            addCriterion("UPDATE_OPERATOR_NAME >=", value, "updateOperatorName");
            return (Criteria) this;
        }

        public Criteria andUpdateOperatorNameLessThan(String value) {
            addCriterion("UPDATE_OPERATOR_NAME <", value, "updateOperatorName");
            return (Criteria) this;
        }

        public Criteria andUpdateOperatorNameLessThanOrEqualTo(String value) {
            addCriterion("UPDATE_OPERATOR_NAME <=", value, "updateOperatorName");
            return (Criteria) this;
        }

        public Criteria andUpdateOperatorNameLike(String value) {
            addCriterion("UPDATE_OPERATOR_NAME like", value, "updateOperatorName");
            return (Criteria) this;
        }

        public Criteria andUpdateOperatorNameNotLike(String value) {
            addCriterion("UPDATE_OPERATOR_NAME not like", value, "updateOperatorName");
            return (Criteria) this;
        }

        public Criteria andUpdateOperatorNameIn(List<String> values) {
            addCriterion("UPDATE_OPERATOR_NAME in", values, "updateOperatorName");
            return (Criteria) this;
        }

        public Criteria andUpdateOperatorNameNotIn(List<String> values) {
            addCriterion("UPDATE_OPERATOR_NAME not in", values, "updateOperatorName");
            return (Criteria) this;
        }

        public Criteria andUpdateOperatorNameBetween(String value1, String value2) {
            addCriterion("UPDATE_OPERATOR_NAME between", value1, value2, "updateOperatorName");
            return (Criteria) this;
        }

        public Criteria andUpdateOperatorNameNotBetween(String value1, String value2) {
            addCriterion("UPDATE_OPERATOR_NAME not between", value1, value2, "updateOperatorName");
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
    }

    /**
     */
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