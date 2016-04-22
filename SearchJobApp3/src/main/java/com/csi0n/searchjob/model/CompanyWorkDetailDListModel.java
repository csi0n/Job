package com.csi0n.searchjob.model;

import com.csi0n.searchjob.lib.utils.bean.BaseBean;

import java.util.List;

/**
 * Created by chqss on 2016/3/7 0007.
 */
public class CompanyWorkDetailDListModel extends BaseBean {
   private List<CompanyWorkDetailDModel> data;

   public List<CompanyWorkDetailDModel> getData() {
      return data;
   }

   public void setData(List<CompanyWorkDetailDModel> data) {
      this.data = data;
   }

   public  static class CompanyWorkDetailDModel extends BaseBean {


      /**
       * username : test2
       * id : 2
       * uid : 15842165
       * company_id : 3
       * reply_uid : null
       * content : 我感觉他家的普工是蛮好的。
       * add_time : 1457577562
       */

      private String username;
      private String id;
      private String uid;
      private String company_id;
      private Object reply_uid;
      private String content;
      private String add_time;

      public void setUsername(String username) {
         this.username = username;
      }

      public void setId(String id) {
         this.id = id;
      }

      public void setUid(String uid) {
         this.uid = uid;
      }

      public void setCompany_id(String company_id) {
         this.company_id = company_id;
      }

      public void setReply_uid(Object reply_uid) {
         this.reply_uid = reply_uid;
      }

      public void setContent(String content) {
         this.content = content;
      }

      public void setAdd_time(String add_time) {
         this.add_time = add_time;
      }

      public String getUsername() {
         return username;
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

      public Object getReply_uid() {
         return reply_uid;
      }

      public String getContent() {
         return content;
      }

      public String getAdd_time() {
         return add_time;
      }
   }
}
