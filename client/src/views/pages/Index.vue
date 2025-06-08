<template>
  <component :is="homeComponent" />
</template>

<script>
import AdminHome from '@/components/AdminHome.vue'
import StudentHome from '@/components/StudentHome.vue'
import {getLoginUser} from "@/api";

export default {
  name: 'HomeIndex',
  components: {
    AdminHome,
    StudentHome
  },
  data(){
    return{
      LoginUser:{}
    }

  },
  mounted() {
    getLoginUser(this.$store.state.token).then(resp => {
      this.LoginUser = resp.data;
    });
  },


  computed: {
    homeComponent() {
      const role = this.LoginUser.type;
      return role === 0 || role === 1 ? 'AdminHome' : 'StudentHome'
    }
  }
}
</script>