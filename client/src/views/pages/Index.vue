<template>
  <div class="fater-body-show">
    <el-row :gutter="15" class="mb-15">
      <el-col :span="16">
        <el-card shadow="hover" class="welcome-card">
          <div class="welcome-content">
            <h2>欢迎回来，{{ loginUser.name }}！</h2>
            <p class="welcome-text">今天是{{ currentDate }}，祝您工作愉快！</p>
            <div class="welcome-stats">
              <div class="stat-item">
                <div class="stat-value">{{ stats.noticeCount }}</div>
                <div class="stat-label">系统通知</div>
              </div>
              <div class="stat-item">
                <div class="stat-value">{{ stats.userCount }}</div>
                <div class="stat-label">注册用户</div>
              </div>
              <div class="stat-item">
                <div class="stat-value">{{ stats.activeCount }}</div>
                <div class="stat-label">活跃社团</div>
              </div>
            </div>
          </div>
        </el-card>
      </el-col>
      <el-col :span="8">
        <el-card shadow="hover" class="profile-card">
          <div slot="header" class="card-header">
            <i class="el-icon-user-solid"></i>
            <span>个人资料</span>
          </div>
          <div>
            <el-descriptions :column="1" size="small" class="profile-descriptions">
              <el-descriptions-item label="用户ID">
                <el-tag size="small">{{ loginUser.id }}</el-tag>
              </el-descriptions-item>
              <el-descriptions-item label="用户姓名">
                {{ loginUser.name }}
              </el-descriptions-item>
              <el-descriptions-item label="用户性别">
                <el-tag :type="loginUser.gender === '男' ? 'primary' : 'danger'" size="small">
                  {{ loginUser.gender }}
                </el-tag>
              </el-descriptions-item>
              <el-descriptions-item label="用户年龄">
                {{ loginUser.age || '未填写' }}
              </el-descriptions-item>
              <el-descriptions-item label="联系电话">
                <el-link :href="'tel:' + loginUser.phone" type="primary" v-if="loginUser.phone">
                  {{ loginUser.phone }}
                </el-link>
                <span v-else>未填写</span>
              </el-descriptions-item>
              <el-descriptions-item label="联系地址">
                {{ loginUser.address || '未填写' }}
              </el-descriptions-item>
            </el-descriptions>
          </div>
        </el-card>
      </el-col>
    </el-row>
    <el-row :gutter="15">
      <el-col :span="8">
        <el-card shadow="hover" class="notice-card">
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
        <!-- 快捷操作面板 -->
        <el-card shadow="hover" class="quick-actions-card">
          <div slot="header" class="card-header">
            <i class="el-icon-thumb"></i>
            <span>快捷操作</span>
          </div>
          <div class="quick-actions-content">
            <el-row :gutter="20">
              <el-col :span="6" v-for="(action, index) in quickActions" :key="index">
                <el-card shadow="hover" class="action-card" @click.native="handleAction(action)">
                  <div class="action-icon">
                    <i :class="action.icon"></i>
                  </div>
                  <div class="action-title">{{ action.title }}</div>
                </el-card>
              </el-col>
            </el-row>
          </div>
        </el-card>

        <!-- 近期活动日历 -->
        <el-card shadow="hover" class="mt-15">
          <div slot="header" class="card-header">
            <i class="el-icon-date"></i>
            <span>近期重要活动</span>
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
                  {{ event.title }} - {{ event.location }}
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
import { getLoginUser, getSysNoticeList } from "../../api";

export default {
  data() {
    return {
      loginUser: {},
      sysNotices: [],
      currentDate: this.formatDate(new Date()),
      stats: {
        noticeCount: 5,
        userCount: 128,
        activeCount: 24,
        clubCount: 36,
        onlineCount: 42,
        activityCount: 87
      },
      quickActions: [
        { title: '发布通知', icon: 'el-icon-bell', path: '/notices' },
        { title: '入团申请', icon: 'el-icon-tickets', path: '/applyLogs' },
        { title: '成员管理', icon: 'el-icon-user', path: '/members' },
        { title: '活动管理', icon: 'el-icon-date', path: '/activities' }
      ],
      upcomingEvents: [
        { title: '春季招新', time: '2023-03-15 14:00', location: '学生活动中心' },
        { title: '社团负责人会议', time: '2023-03-20 10:00', location: '会议室A' },
        { title: '文艺汇演', time: '2023-04-01 19:00', location: '大礼堂' }
      ]
    }
  },
  mounted() {
    getLoginUser(this.$store.state.token).then(resp => {
      this.loginUser = resp.data;
    });

    getSysNoticeList(this.$store.state.token).then(resp => {
      this.sysNotices = resp.data;
    });

    // 模拟获取统计数据
    this.getSystemStats();
  },
  methods: {
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
    getSystemStats() {
      // 这里应该是从API获取系统统计数据
      // 模拟数据
      setTimeout(() => {
        this.stats = {
          noticeCount: this.sysNotices.length,
          userCount: Math.floor(Math.random() * 100) + 100,
          activeCount: Math.floor(Math.random() * 20) + 10,
          clubCount: Math.floor(Math.random() * 30) + 20,
          onlineCount: Math.floor(Math.random() * 50) + 20,
          activityCount: Math.floor(Math.random() * 100) + 50
        };
      }, 500);
    },
    hasEvent(date) {
      return this.upcomingEvents.some(event => event.time.includes(date));
    },
    handleAction(action) {
      this.$router.push(action.path);
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

.welcome-stats {
  display: flex;
  justify-content: space-between;
  margin-top: 30px;
}

.stat-item {
  text-align: center;
  flex: 1;
}

.stat-value {
  font-size: 28px;
  font-weight: bold;
  color: #409EFF;
  margin-bottom: 5px;
}

.stat-label {
  font-size: 14px;
  color: #909399;
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
  height: 100%;
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

/* 快捷操作面板样式 */
.quick-actions-card {
  border: none;
}

.quick-actions-content {
  padding: 10px;
}

.action-card {
  cursor: pointer;
  text-align: center;
  transition: all 0.3s;
  border: none;
  background-color: #f9f9f9;
}

.action-card:hover {
  transform: translateY(-3px);
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
}

.action-icon {
  font-size: 24px;
  color: #409EFF;
  margin-bottom: 10px;
}

.action-title {
  font-size: 14px;
  color: #606266;
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
</style>