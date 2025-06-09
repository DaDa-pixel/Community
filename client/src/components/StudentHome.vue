<template>
  <div class="fater-body-show">
    <el-row :gutter="15" class="mb-15">
      <el-col :span="24">
        <el-card shadow="hover" class="welcome-card">
          <div class="welcome-content">
            <h2>欢迎回来，{{ loginUser.name }}同学！</h2>
            <p class="welcome-text">今天是{{ currentDate }}，祝您学习生活愉快！</p>
          </div>
        </el-card>
      </el-col>
    </el-row>
    <el-row :gutter="15">
      <el-col :span="8">
        <!-- 个人资料卡片 -->
        <el-card shadow="hover" class="profile-card">
          <div slot="header" class="card-header">
            <i class="el-icon-user-solid"></i>
            <span>个人资料</span>
          </div>
          <div>
            <el-descriptions :column="1" size="small" class="profile-descriptions">
              <el-descriptions-item label="学号">
                <el-tag size="small">{{ loginUser.id }}</el-tag>
              </el-descriptions-item>
              <el-descriptions-item label="姓名">
                {{ loginUser.name }}
              </el-descriptions-item>
              <el-descriptions-item label="性别">
                <el-tag :type="loginUser.gender === '男' ? 'primary' : 'danger'" size="small">
                  {{ loginUser.gender }}
                </el-tag>
              </el-descriptions-item>
              <el-descriptions-item label="联系电话">
                <el-link :href="'tel:' + loginUser.phone" type="primary" v-if="loginUser.phone">
                  {{ loginUser.phone }}
                </el-link>
                <span v-else>未填写</span>
              </el-descriptions-item>
              <el-descriptions-item label="所属社团">
                <el-tag
                    v-for="(club, index) in clubs"
                    :key="index"
                    size="small"
                    type="success"
                    class="club-tag"
                >
                  {{ club }}
                </el-tag>
              </el-descriptions-item>
            </el-descriptions>
          </div>
        </el-card>

        <!-- 系统通知卡片 -->
        <el-card shadow="hover" class="mt-15 notice-card">
          <div slot="header" class="card-header">
            <i class="el-icon-bell"></i>
            <span>系统通知</span>
          </div>
          <div>
            <el-timeline>
              <el-timeline-item
                  v-for="(item, index) in sysNotices"
                  :key="index"
                  :timestamp="item.createTime"
                  placement="top"
                  :color="index === 0 ? '#409EFF' : '#E6A23C'"
                  :size="index === 0 ? 'large' : 'normal'">
                <el-card shadow="hover" class="notice-item">
                  <h4 class="notice-title">{{ item.title }}</h4>
                  <p class="notice-content">{{ item.detail }}</p>
                  <div class="notice-time">
                    <i class="el-icon-time"></i>
                    {{ formatTime(item.createTime) }}
                  </div>
                </el-card>
              </el-timeline-item>
            </el-timeline>
          </div>
        </el-card>
      </el-col>
      <el-col :span="16">
        <!-- 社团风采轮播图 -->
        <el-card shadow="hover">
          <div slot="header" class="card-header">
            <i class="el-icon-picture-outline"></i>
            <span>社团风采</span>
          </div>
          <div class="club-gallery">
            <el-carousel :interval="4000" type="card" height="300px">
              <el-carousel-item v-for="(item, index) in clubGallery" :key="index">
                <img :src="item.image" class="carousel-image" :alt="'社团风采图' + (index + 1)">
                <div class="carousel-caption">
                  <h3>{{ item.title }}</h3>
                  <p>{{ item.description }}</p>
                </div>
              </el-carousel-item>
            </el-carousel>
          </div>
        </el-card>

        <!-- 近期活动日历 -->
        <el-card shadow="hover" class="mt-15">
          <div slot="header" class="card-header">
            <i class="el-icon-date"></i>
            <span>近期活动</span>
          </div>
          <div class="recent-activities-content">
            <el-calendar>
              <template #dateCell="{data}">
                <div class="calendar-day">
                  <div class="day-number">{{ data.day.split('-')[2] }}</div>
                  <div v-if="hasEvent(data.day)" class="event-marker"></div>
                </div>
              </template>
            </el-calendar>
            <div class="upcoming-events">
              <h4>即将开始的活动</h4>
              <el-timeline>
                <el-timeline-item
                    v-for="(event, index) in upcomingEvents"
                    :key="index"
                    :timestamp="event.time"
                    placement="top">
                  <div class="event-item">
                    <span class="event-title">{{ event.title }}</span>
                    <el-tag size="mini" type="info" class="event-location">{{ event.location }}</el-tag>
                  </div>
                </el-timeline-item>
              </el-timeline>
            </div>
          </div>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<script>
import {getLoginUser, getSysNoticeList,  getUserClubs} from "@/api";
import club1 from '@/assets/images/img.png';
import club2 from '@/assets/images/img_1.png';
import club3 from '@/assets/images/img_2.png';
import club4 from '@/assets/images/img_3.png';
import club5 from '@/assets/images/img_4.png';

export default {
  name: 'StudentHome',
  data() {
    return {
      loginUser: {},
      sysNotices: [],
      clubs:[],
      currentDate: this.formatDate(new Date()),
      clubGallery: [
        {
          image: club1,
          title: '社团招新活动',
          description: '新学期社团招新火热进行中'
        },
        {
          image: club2,
          title: '迎新晚会',
          description: '年度迎新晚会精彩瞬间'
        },
        {
          image:club3,
          title: '社团比赛',
          description: '校内社团比赛获奖合影'
        },
        {
          image: club4,
          title: '户外拓展',
          description: '社团成员户外拓展活动'
        },
        {
          image: club5,
          title: '公益活动',
          description: '社团参与社区志愿服务'
        }
      ],
      upcomingEvents: [
        { title: '社团招新', time: '2025-09-15 14:00', location: '学生活动中心' },
        { title: '迎新晚会', time: '2025-09-20 19:00', location: '大礼堂' }
      ]
    }
  },
  mounted() {
    this.fetchData();
  },
  methods: {
    fetchData() {
      console.log("当前Token:", this.$store.state.token);
      getLoginUser(this.$store.state.token).then(resp => {
        this.loginUser = resp.data;
        console.log("实际查询的用户ID:", this.loginUser.id);
      });
      getUserClubs(1749004411057).then(resp=>{
        this.clubs=resp.data;
        console.log("得到社团名称",this.clubs)
      })

      getSysNoticeList(this.$store.state.token).then(resp => {
        this.sysNotices = resp.data;
      });
    },
    formatDate(date) {
      const year = date.getFullYear();
      const month = date.getMonth() + 1;
      const day = date.getDate();
      const week = ['日', '一', '二', '三', '四', '五', '六'][date.getDay()];
      return `${year}年${month}月${day}日 星期${week}`;
    },
    formatTime(timeStr) {
      if (!timeStr) return '';
      return timeStr.split(' ')[0];
    },
    hasEvent(date) {
      return this.upcomingEvents.some(event => event.time.includes(date));
    }
  }
}
</script>

<style scoped>
.fater-body-show {
  padding: 15px;
  background-color: #f5f7fa;
}

.mb-15 {
  margin-bottom: 15px;
}

.mt-15 {
  margin-top: 15px;
}

.ml-10 {
  margin-left: 10px;
}

/* 欢迎卡片样式 */
.welcome-card {
  height: 100%;
  background: linear-gradient(135deg, #f6f8fa, #e9f0f5);
  border: none;
}

.welcome-content {
  padding: 15px;
}

.welcome-content h2 {
  color: #303133;
  margin-bottom: 10px;
  font-size: 22px;
}

.welcome-text {
  color: #606266;
  margin-bottom: 20px;
  font-size: 14px;
}

/* 个人资料卡片样式 */
.profile-card {
  height: 100%;
  border: none;
}

.card-header {
  font-size: 16px;
  font-weight: bold;
  color: #303133;
}

.card-header i {
  margin-right: 8px;
  color: #409EFF;
}

.profile-descriptions {
  padding: 10px;
}

/* 系统通知卡片样式 */
.notice-card {
  border: none;
}

.notice-item {
  margin-bottom: 10px;
  border: none;
  background-color: #f9f9f9;
}

.notice-title {
  font-size: 16px;
  line-height: 28px;
  margin-bottom: 10px;
  color: #303133;
}

.notice-content {
  font-size: 14px;
  line-height: 24px;
  color: #606266;
  margin-bottom: 10px;
}

.notice-time {
  font-size: 12px;
  color: #909399;
  display: flex;
  align-items: center;
}

.notice-time i {
  margin-right: 5px;
}

/* 社团风采轮播图样式 */
.club-gallery {
  padding: 10px;
}

.carousel-image {
  width: 100%;
  height: 100%;
  object-fit: cover;
  border-radius: 4px;
}

.carousel-caption {
  position: absolute;
  bottom: 0;
  left: 0;
  right: 0;
  background: rgba(0, 0, 0, 0.5);
  color: white;
  padding: 15px;
  border-bottom-left-radius: 4px;
  border-bottom-right-radius: 4px;
}

.carousel-caption h3 {
  margin: 0 0 5px 0;
  font-size: 18px;
}

.carousel-caption p {
  margin: 0;
  font-size: 14px;
}

/* 近期活动日历样式 */
.recent-activities-content {
  padding: 10px;
}

.calendar-day {
  height: 100%;
  display: flex;
  flex-direction: column;
  align-items: center;
}

.day-number {
  font-size: 14px;
}

.event-marker {
  width: 6px;
  height: 6px;
  background-color: #F56C6C;
  border-radius: 50%;
  margin-top: 2px;
}

.upcoming-events {
  margin-top: 20px;
}

.upcoming-events h4 {
  font-size: 15px;
  color: #606266;
  margin-bottom: 15px;
  padding-left: 10px;
}

.event-item {
  display: flex;
  align-items: center;
}

.event-title {
  margin-right: 10px;
}

.event-location {
  margin-left: auto;
}
</style>