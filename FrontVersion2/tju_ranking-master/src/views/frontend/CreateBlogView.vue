<template>
  <div class="create-blog-view container">
    <div class="page-header">
      <h1><i class="fas fa-edit"></i> 发表新博客</h1>
      <p>分享你的美食见闻与体验</p>
    </div>

    <form @submit.prevent="submitBlog" class="blog-form card">
      <div class="form-group">
        <label for="blogTitle">博客标题:</label>
        <input type="text" id="blogTitle" v-model="blog.title" required placeholder="给你的博客起个吸引人的标题吧">
      </div>

      <div class="form-group">
        <label for="blogCampus">关联校区 (可选):</label>
        <select id="blogCampus" v-model="blog.campus">
          <option value="">不限校区</option>
          <option value="weijinlu">卫津路校区</option>
          <option value="beiyangyuan">北洋园校区</option>
        </select>
      </div>

      <div class="form-group">
        <label for="blogCanteen">关联食堂/窗口 (可选):</label>
        <!-- 实际项目中这里可能是个搜索选择组件 -->
        <input type="text" id="blogCanteen" v-model="blog.relatedCanteen" placeholder="例如：学一食堂，梅园小炒">
      </div>

      <div class="form-group">
        <label for="blogContent">博客内容:</label>
        <!-- 简单文本域，实际项目中可替换为富文本编辑器 -->
        <textarea id="blogContent" v-model="blog.content" rows="10" required placeholder="尽情发挥你的文采..."></textarea>
      </div>

      <div class="form-group">
        <label for="blogImages">上传图片 (可选):</label>
        <!-- 简易图片上传占位 -->
        <input type="file" id="blogImages" @change="handleImageUpload" multiple accept="image/*">
        <div class="image-preview-container" v-if="imagePreviews.length > 0">
          <div v-for="(preview, index) in imagePreviews" :key="index" class="image-preview">
            <img :src="preview" alt="图片预览">
            <button type="button" @click="removeImage(index)" class="btn-remove-image">&times;</button>
          </div>
        </div>
        <small>最多可上传5张图片，每张不超过2MB。</small>
      </div>

      <div v-if="errorMessage" class="error-message">
        {{ errorMessage }}
      </div>

      <div class="form-actions">
        <button type="button" @click="cancelPost" class="btn btn-secondary">取消</button>
        <button type="submit" class="btn btn-primary" :disabled="submitting">
          <i class="fas fa-spinner fa-spin" v-if="submitting"></i>
          <span v-else><i class="fas fa-paper-plane"></i> 立即发表</span>
        </button>
      </div>
    </form>
  </div>
</template>

<script>
export default {
  name: 'CreateBlogView',
  data() {
    return {
      blog: {
        title: '',
        campus: '', // 'weijinlu', 'beiyangyuan'
        relatedCanteen: '', // 关联食堂或窗口的ID或名称
        content: '',
        images: [], // 存储 File 对象
      },
      imagePreviews: [], // 存储图片预览的 Data URL
      errorMessage: '',
      submitting: false,
    };
  },
  methods: {
    handleImageUpload(event) {
      const files = Array.from(event.target.files);
      if (this.blog.images.length + files.length > 5) {
        this.errorMessage = '最多只能上传5张图片。';
        return;
      }
      this.errorMessage = '';

      files.forEach(file => {
        if (file.size > 2 * 1024 * 1024) { // 2MB
          this.errorMessage += `${file.name} 文件过大，请选择小于2MB的图片。\n`;
          return;
        }
        if (!file.type.startsWith('image/')) {
          this.errorMessage += `${file.name} 不是有效的图片文件。\n`;
          return;
        }

        this.blog.images.push(file);
        const reader = new FileReader();
        reader.onload = (e) => {
          this.imagePreviews.push(e.target.result);
        };
        reader.readAsDataURL(file);
      });
    },
    removeImage(index) {
      this.blog.images.splice(index, 1);
      this.imagePreviews.splice(index, 1);
    },
    submitBlog() {
      this.errorMessage = '';
      if (!this.blog.title.trim() || !this.blog.content.trim()) {
        this.errorMessage = '博客标题和内容不能为空。';
        return;
      }

      this.submitting = true;
      console.log('准备提交博客:', this.blog);

      // 实际应用中，这里会使用 FormData 来上传文件和表单数据
      // const formData = new FormData();
      // formData.append('title', this.blog.title);
      // formData.append('content', this.blog.content);
      // if (this.blog.campus) formData.append('campus', this.blog.campus);
      // if (this.blog.relatedCanteen) formData.append('related_canteen', this.blog.relatedCanteen);
      // this.blog.images.forEach(imageFile => {
      //   formData.append('images[]', imageFile);
      // });
      // axios.post('/api/blogs', formData, { headers: { 'Content-Type': 'multipart/form-data' } }) ...

      // 模拟API提交
      setTimeout(() => {
        alert('博客发表成功！'); // 简单提示
        this.$router.push({ name: 'Home' }); // 发表成功后返回首页
        this.submitting = false;
      }, 2000);
    },
    cancelPost() {
      if (confirm('确定要取消发表吗？未保存的内容将会丢失。')) {
        this.$router.go(-1); // 返回上一页
      }
    }
  },
  beforeRouteLeave(to, from, next) {
    if (this.blog.title || this.blog.content || this.blog.images.length > 0) {
      if (!confirm('您有未保存的内容，确定要离开吗？')) {
        next(false); // 取消导航
        return;
      }
    }
    next(); // 继续导航
  }
}
</script>

<style scoped>
.create-blog-view {
  padding-top: 20px;
  padding-bottom: 40px;
}

.page-header {
  text-align: center;
  margin-bottom: 30px;
}
.page-header h1 {
  font-size: 2.5em;
  color: #333;
  margin-bottom: 10px;
}
.page-header h1 i {
  margin-right: 10px;
  color: #007bff;
}
.page-header p {
  font-size: 1.1em;
  color: #666;
}

.blog-form.card {
  background-color: #fff;
  padding: 30px;
  border-radius: 8px;
  box-shadow: 0 3px 15px rgba(0,0,0,0.08);
  max-width: 800px; /* 限制表单最大宽度 */
  margin: 0 auto;
}

.form-group {
  margin-bottom: 25px;
}

.form-group label {
  display: block;
  font-weight: bold;
  margin-bottom: 8px;
  color: #495057;
}

input[type="text"],
select,
textarea {
  width: 100%;
  padding: 12px 15px;
  border: 1px solid #ced4da;
  border-radius: 5px;
  box-sizing: border-box;
  font-size: 1em;
  transition: border-color 0.2s, box-shadow 0.2s;
}
input[type="text"]:focus,
select:focus,
textarea:focus {
  border-color: #007bff;
  outline: none;
  box-shadow: 0 0 0 3px rgba(0, 123, 255, 0.15);
}
textarea {
  min-height: 150px;
  resize: vertical;
}

input[type="file"] {
  padding: 8px;
  background-color: #f8f9fa;
}
.image-preview-container {
  margin-top: 15px;
  display: flex;
  flex-wrap: wrap;
  gap: 10px;
}
.image-preview {
  position: relative;
  width: 100px;
  height: 100px;
  border: 1px solid #ddd;
  border-radius: 4px;
  overflow: hidden;
}
.image-preview img {
  width: 100%;
  height: 100%;
  object-fit: cover;
}
.btn-remove-image {
  position: absolute;
  top: 3px;
  right: 3px;
  background-color: rgba(0,0,0,0.6);
  color: white;
  border: none;
  border-radius: 50%;
  width: 20px;
  height: 20px;
  font-size: 12px;
  line-height: 18px;
  text-align: center;
  cursor: pointer;
  opacity: 0.8;
  transition: opacity 0.2s;
}
.btn-remove-image:hover {
  opacity: 1;
}
.form-group small {
  display: block;
  margin-top: 8px;
  color: #6c757d;
  font-size: 0.85em;
}


.error-message {
  background-color: #f8d7da;
  color: #721c24;
  border: 1px solid #f5c6cb;
  padding: 10px 15px;
  border-radius: 5px;
  margin-bottom: 20px;
  font-size: 0.95em;
}

.form-actions {
  display: flex;
  justify-content: flex-end; /* 按钮靠右 */
  gap: 15px;
  margin-top: 30px;
  padding-top: 20px;
  border-top: 1px solid #e9ecef;
}

.btn {
  padding: 10px 25px;
  border-radius: 5px;
  font-size: 1em;
  font-weight: 500;
  cursor: pointer;
  transition: background-color 0.2s, opacity 0.2s;
  display: inline-flex;
  align-items: center;
  justify-content: center;
}
.btn i {
  margin-right: 8px;
}

.btn-primary {
  background-color: #007bff;
  color: white;
  border: 1px solid #007bff;
}
.btn-primary:hover:not(:disabled) {
  background-color: #0056b3;
  border-color: #0056b3;
}
.btn-primary:disabled {
  opacity: 0.7;
  cursor: not-allowed;
}

.btn-secondary {
  background-color: #6c757d;
  color: white;
  border: 1px solid #6c757d;
}
.btn-secondary:hover {
  background-color: #5a6268;
  border-color: #545b62;
}
</style>
    