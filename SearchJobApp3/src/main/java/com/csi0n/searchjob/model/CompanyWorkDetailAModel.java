package com.csi0n.searchjob.model;

import com.csi0n.searchjob.lib.utils.bean.BaseBean;

import java.util.List;

/**
 * Created by chqss on 2016/3/3 0003.
 */
public class CompanyWorkDetailAModel extends BaseBean {

    /**
     * company_id : 3
     * company_name : 明芳汽配
     * job_id : 1
     * job_type : 普工
     * use_type : 劳务派遣/劳务外包
     * gongzi : 2000~3000
     * work_time : 无休
     * shebao : 1
     * work_location : 无锡市滨湖区11号
     * fuli : 1
     * age : 男:18~36
     * degree_wanted : 职中及以上
     * work_life : 不限
     * more_infor : 1.更多介绍
     * work_infor : 1.工作详情
     * day : 2016年03月03日
     * today_money_back : [{"id":"1","uid":"15823766","company_id":"3","money_back_famale":"400","money_back_male":"300","add_time":"1456973510","jingliren":{"uid":"15823766","real_name":"胡兵","phone":"180124886671","username":"test1"}}]
     */

    private String company_id;
    private String company_name;
    private String job_id;
    private String job_type;
    private String use_type;
    private String gongzi;
    private String gongzi_detail;
    private String work_time;
    private String shebao;
    private String work_location;
    private String fuli;
    private String age;
    private String degree_wanted;
    private String work_life;
    private String more_infor;
    private String work_infor;
    private String day;
    /**
     * id : 1
     * uid : 15823766
     * company_id : 3
     * money_back_famale : 400
     * money_back_male : 300
     * add_time : 1456973510
     * jingliren : {"uid":"15823766","real_name":"胡兵","phone":"180124886671","username":"test1"}
     */

    private List<TodayMoneyBackEntity> today_money_back;

    public void setCompany_id(String company_id) {
        this.company_id = company_id;
    }

    public void setCompany_name(String company_name) {
        this.company_name = company_name;
    }

    public void setJob_id(String job_id) {
        this.job_id = job_id;
    }

    public void setJob_type(String job_type) {
        this.job_type = job_type;
    }

    public void setUse_type(String use_type) {
        this.use_type = use_type;
    }

    public void setGongzi(String gongzi) {
        this.gongzi = gongzi;
    }

    public void setWork_time(String work_time) {
        this.work_time = work_time;
    }

    public void setShebao(String shebao) {
        this.shebao = shebao;
    }

    public void setWork_location(String work_location) {
        this.work_location = work_location;
    }

    public void setFuli(String fuli) {
        this.fuli = fuli;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public void setDegree_wanted(String degree_wanted) {
        this.degree_wanted = degree_wanted;
    }

    public void setWork_life(String work_life) {
        this.work_life = work_life;
    }

    public void setMore_infor(String more_infor) {
        this.more_infor = more_infor;
    }

    public void setWork_infor(String work_infor) {
        this.work_infor = work_infor;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public void setToday_money_back(List<TodayMoneyBackEntity> today_money_back) {
        this.today_money_back = today_money_back;
    }

    public String getCompany_id() {
        return company_id;
    }

    public String getCompany_name() {
        return company_name;
    }

    public String getJob_id() {
        return job_id;
    }

    public String getJob_type() {
        return job_type;
    }

    public String getUse_type() {
        return use_type;
    }

    public String getGongzi() {
        return gongzi;
    }

    public String getGongzi_detail() {
        return gongzi_detail;
    }

    public void setGongzi_detail(String gongzi_detail) {
        this.gongzi_detail = gongzi_detail;
    }

    public String getWork_time() {
        return work_time;
    }

    public String getShebao() {
        return shebao;
    }

    public String getWork_location() {
        return work_location;
    }

    public String getFuli() {
        return fuli;
    }

    public String getAge() {
        return age;
    }

    public String getDegree_wanted() {
        return degree_wanted;
    }

    public String getWork_life() {
        return work_life;
    }

    public String getMore_infor() {
        return more_infor;
    }

    public String getWork_infor() {
        return work_infor;
    }

    public String getDay() {
        return day;
    }

    public List<TodayMoneyBackEntity> getToday_money_back() {
        return today_money_back;
    }

    public static class TodayMoneyBackEntity extends BaseBean{
        private String id;
        private String uid;
        private String company_id;
        private String money_back_famale;
        private String money_back_male;
        private String add_time;
        /**
         * uid : 15823766
         * real_name : 胡兵
         * phone : 180124886671
         * username : test1
         */

        private JinglirenEntity jingliren;

        public void setId(String id) {
            this.id = id;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }

        public void setCompany_id(String company_id) {
            this.company_id = company_id;
        }

        public void setMoney_back_famale(String money_back_famale) {
            this.money_back_famale = money_back_famale;
        }

        public void setMoney_back_male(String money_back_male) {
            this.money_back_male = money_back_male;
        }

        public void setAdd_time(String add_time) {
            this.add_time = add_time;
        }

        public void setJingliren(JinglirenEntity jingliren) {
            this.jingliren = jingliren;
        }

        public String getId() {
            return id;
        }

        public String getUid() {
            return uid;
        }

        public String getCompany_id() {
            return company_id;
        }

        public String getMoney_back_famale() {
            return money_back_famale;
        }

        public String getMoney_back_male() {
            return money_back_male;
        }

        public String getAdd_time() {
            return add_time;
        }

        public JinglirenEntity getJingliren() {
            return jingliren;
        }

        public static class JinglirenEntity extends BaseBean{
            private String uid;
            private String real_name;
            private String phone;
            private String username;

            private  int is_follow=0;

            public int getIs_follow() {
                return is_follow;
            }

            public void setIs_follow(int is_follow) {
                this.is_follow = is_follow;
            }

            public void setUid(String uid) {
                this.uid = uid;
            }

            public void setReal_name(String real_name) {
                this.real_name = real_name;
            }

            public void setPhone(String phone) {
                this.phone = phone;
            }

            public void setUsername(String username) {
                this.username = username;
            }

            public String getUid() {
                return uid;
            }

            public String getReal_name() {
                return real_name;
            }

            public String getPhone() {
                return phone;
            }

            public String getUsername() {
                return username;
            }
        }
    }
}
