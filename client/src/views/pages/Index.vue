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
        <el-card shadow="hover" class="system-card">
          <div slot="header" class="card-header">
            <i class="el-icon-s-data"></i>
            <span>系统信息</span>
          </div>
          <div class="system-info">
            <el-row :gutter="20">
              <el-col :span="12">
                <div class="info-card">
                  <div class="info-icon" style="background-color: #67C23A;">
                    <i class="el-icon-s-platform"></i>
                  </div>
                  <div class="info-content">
                    <div class="info-title">系统版本</div>
                    <div class="info-value">v2.5.0</div>
                  </div>
                </div>
              </el-col>
              <el-col :span="12">
                <div class="info-card">
                  <div class="info-icon" style="background-color: #409EFF;">
                    <i class="el-icon-s-order"></i>
                  </div>
                  <div class="info-content">
                    <div class="info-title">社团总数</div>
                    <div class="info-value">{{ stats.clubCount }}</div>
                  </div>
                </div>
              </el-col>
            </el-row>
            <el-row :gutter="20" class="mt-20">
              <el-col :span="12">
                <div class="info-card">
                  <div class="info-icon" style="background-color: #E6A23C;">
                    <i class="el-icon-s-custom"></i>
                  </div>
                  <div class="info-content">
                    <div class="info-title">在线用户</div>
                    <div class="info-value">{{ stats.onlineCount }}</div>
                  </div>
                </div>
              </el-col>
              <el-col :span="12">
                <div class="info-card">
                  <div class="info-icon" style="background-color: #F56C6C;">
                    <i class="el-icon-s-opportunity"></i>
                  </div>
                  <div class="info-content">
                    <div class="info-title">活动总数</div>
                    <div class="info-value">{{ stats.activityCount }}</div>
                  </div>
                </div>
              </el-col>
            </el-row>

            <div class="system-chart mt-20">
              <div class="chart-title">系统资源使用情况</div>
              <div class="chart-container">
                <div class="chart-item">
                  <el-progress
                      type="dashboard"
                      :percentage="75"
                      :color="colors[0]"
                      :width="120">
                    <template #default="{ percentage }">
                      <span class="percentage-value">{{ percentage }}%</span>
                      <span class="percentage-label">CPU</span>
                    </template>
                  </el-progress>
                </div>
                <div class="chart-item">
                  <el-progress
                      type="dashboard"
                      :percentage="58"
                      :color="colors[1]"
                      :width="120">
                    <template #default="{ percentage }">
                      <span class="percentage-value">{{ percentage }}%</span>
                      <span class="percentage-label">内存</span>
                    </template>
                  </el-progress>
                </div>
                <div class="chart-item">
                  <el-progress
                      type="dashboard"
                      :percentage="32"
                      :color="colors[2]"
                      :width="120">
                    <template #default="{ percentage }">
                      <span class="percentage-value">{{ percentage }}%</span>
                      <span class="percentage-label">存储</span>
                    </template>
                  </el-progress>
                </div>
              </div>
            </div>
          </div>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<style scoped>
.fater-body-show {
  padding: 15px;
  background-color: #f5f7fa;
}

.mb-15 {
  margin-bottom: 15px;
}

.mt-20 {
  margin-top: 20px;
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

/* 系统信息卡片样式 */
.system-card {
  border: none;
}

.system-info {
  padding: 10px;
}

.info-card {
  display: flex;
  align-items: center;
  padding: 15px;
  background-color: #fff;
  border-radius: 8px;
  box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.05);
  transition: all 0.3s ease;
}

.info-card:hover {
  transform: translateY(-3px);
  box-shadow: 0 4px 16px 0 rgba(0, 0, 0, 0.1);
}

.info-icon {
  width: 50px;
  height: 50px;
  border-radius: 50%;
  display: flex;
  align-items: center;
  justify-content: center;
  margin-right: 15px;
  color: white;
  font-size: 22px;
}

.info-content {
  flex: 1;
}

.info-title {
  font-size: 14px;
  color: #909399;
  margin-bottom: 5px;
}

.info-value {
  font-size: 22px;
  font-weight: bold;
  color: #303133;
}

/* 图表区域样式 */
.system-chart {
  background-color: #fff;
  padding: 20px;
  border-radius: 8px;
  box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.05);
}

.chart-title {
  font-size: 16px;
  font-weight: bold;
  color: #303133;
  margin-bottom: 20px;
  padding-bottom: 10px;
  border-bottom: 1px solid #ebeef5;
}

.chart-container {
  display: flex;
  justify-content: space-around;
  flex-wrap: wrap;
}

.chart-item {
  margin: 10px;
  text-align: center;
}

.percentage-value {
  display: block;
  font-size: 24px;
  font-weight: bold;
  color: #303133;
}

.percentage-label {
  display: block;
  font-size: 14px;
  color: #909399;
  margin-top: 5px;
}
</style>

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
      colors: [
        { color: '#67C23A', percentage: 20 },
        { color: '#E6A23C', percentage: 40 },
        { color: '#F56C6C', percentage: 80 }
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
    }
  }
}
</script>