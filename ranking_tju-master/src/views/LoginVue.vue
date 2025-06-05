<template>
    <div>
        <img alt="Vue logo" src="../assets/logotju.png" />
        <HelloWorld msg="Welcome to Your Vue.js App" />
        <el-divider></el-divider>
        <transition name="el-fade-in">
            <el-row type="flex" justify="center">
                <el-col :span="12" class="">
                    <el-row type="flex" justify="center">
                        <el-card class="box-card">
                            <div slot="header" class="clearfix">
                                <span>用户登录</span>
                                <router-link to="/" style="float: right; padding: 3px 0">
                                    <el-button type="info" circle>返回</el-button>
                                </router-link>
                            </div>
                            <el-form :model="ruleForm" status-icon :rules="rules" ref="ruleForm" label-width="100px"
                                class="demo-ruleForm">
                                <el-form-item label="名称" prop="username">
                                    <el-input v-model="ruleForm.username"></el-input>
                                </el-form-item>
                                <el-form-item label="密码" prop="pass">
                                    <el-input type="password" v-model="ruleForm.pass" autocomplete="off"></el-input>
                                </el-form-item>
                                <el-form-item>
                                    <el-button type="primary" @click="submitForm('ruleForm')">登录</el-button>
                                    <el-button @click="resetForm('ruleForm')">重置</el-button>
                                </el-form-item>
                            </el-form>
                        </el-card>
                    </el-row>
                </el-col>
            </el-row>
        </transition>
    </div>
</template>

<script>
import HelloWorld from "@/components/HelloWorld.vue";
import services from "@/services/AccountServices.js";

export default {
    name: "LoginView",
    components: {
        HelloWorld,
    },
    data() {
        return {
            /**表单数据 */
            ruleForm: {
                pass: "",
                username: "",
            },
            /**校验规则 */
            rules: {
                pass: [
                    { required: true, message: "不能为空", trigger: "blur" },
                ],
                username: [
                    { required: true, message: "名称不能为空", trigger: "blur" },
                ],
            },
        };
    },
    computed: {
        model() { return { uid: this.ruleForm.username, password: this.ruleForm.pass } }
    },
    mounted(){
        this.ruleForm.username = localStorage.getItem('uid') || '';  
        this.ruleForm.pass = localStorage.getItem('password') || '';  
    },
    methods: {
        /**表单函数 */
        submitForm(formName) {
            this.$refs[formName].validate((valid) => {
                if (valid) {
                    services.Login(this.model).then(response => {
                        // console.log('response', response)
                        // console.log('this:',this)
                        // console.log('response:',response)
                        if (response.isSuccess) {
                            //存储token
                            this.$store.commit('setToken',{token:response.data,username:this.ruleForm.username})
                            // console.log('response', response)
                            // console.log('this:',this)
                            // console.log('response:',response)
                            console.log("登录成功")
                            //存储用户名
                            localStorage.setItem('uid', this.model.uid);
                            localStorage.setItem('password',this.model.password)  

                            this.$notify.info({
                                title: '消息',
                                message: '登录成功'
                            });
                            this.$router.push('/main')
                        } else {
                            console.log(`登录失败{response.message}`)
                        }
                    })
                } else {
                    console.log("error submit!!")
                    return false
                }
            })
        },
        resetForm(formName) {
            this.$refs[formName].resetFields();
        },
    },
}
</script>

<style scoped>
.transition-box {
    border-radius: 4px;
    box-shadow: 0 2px 4px rgba(0, 0, 0, .12), 0 0 6px rgba(0, 0, 0, .04);
    margin: 1em;
    padding: 1em;
}
</style>