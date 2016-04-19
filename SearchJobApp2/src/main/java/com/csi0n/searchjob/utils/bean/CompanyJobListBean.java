package com.csi0n.searchjob.utils.bean;

import com.csi0n.searchjob.lib.utils.bean.BaseBean;

import java.util.List;

/**
 * Created by chqss on 2016/3/2 0002.
 */
public class CompanyJobListBean extends BaseBean {
    private List<CompanyJobBean> data;

    public List<CompanyJobBean> getData() {
        return data;
    }

    public void setData(List<CompanyJobBean> data) {
        this.data = data;
    }

    public static class CompanyJobBean extends BaseBean{  private int id;
        private String company_name;
        private String job_id;
        private int money_back_famale;
        private int money_back_male;
        private String age;
        private int sex;
        private String fuli;
        private String area;
        private String city;

        public int getSex() {
            return sex;
        }

        public void setSex(int sex) {
            this.sex = sex;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getCompany_name() {
            return company_name;
        }

        public void setCompany_name(String company_name) {
            this.company_name = company_name;
        }

        public String getJob_id() {
            return job_id;
        }

        public void setJob_id(String job_id) {
            this.job_id = job_id;
        }

        public int getMoney_back_famale() {
            return money_back_famale;
        }

        public void setMoney_back_famale(int money_back_famale) {
            this.money_back_famale = money_back_famale;
        }

        public int getMoney_back_male() {
            return money_back_male;
        }

        public void setMoney_back_male(int money_back_male) {
            this.money_back_male = money_back_male;
        }

        public String getAge() {
            return age;
        }

        public void setAge(String age) {
            this.age = age;
        }

        public String getFuli() {
            return fuli;
        }

        public void setFuli(String fuli) {
            this.fuli = fuli;
        }

        public String getArea() {
            return area;
        }

        public void setArea(String area) {
            this.area = area;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }}

}
