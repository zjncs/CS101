<template>
  <div class="admin-food-manage-view">
    <div class="page-title-bar">
      <h2><i class="fas fa-utensils"></i> 食堂、窗口与菜品管理</h2>
      <p>管理平台的基础餐饮数据信息。</p>
    </div>

    <div class="management-tabs">
      <button :class="{ active: currentTab === 'canteens' }" @click="currentTab = 'canteens'">
        <i class="fas fa-school"></i> 食堂管理
      </button>
      <button :class="{ active: currentTab === 'windows' }" @click="currentTab = 'windows'">
        <i class="fas fa-store-alt"></i> 窗口管理
      </button>
      <button :class="{ active: currentTab === 'dishes' }" @click="currentTab = 'dishes'">
        <i class="fas fa-drumstick-bite"></i> 菜品管理
      </button>
    </div>

    <div class="content-card">
      <!-- 食堂管理 -->
      <div v-if="currentTab === 'canteens'" class="tab-content">
        <h3>食堂列表</h3>
        <button @click="openCanteenModal()" class="btn btn-primary btn-add-item">
          <i class="fas fa-plus"></i> 添加新食堂
        </button>
        <!-- 食堂列表表格占位 -->
        <p class="placeholder-text">食堂列表和管理功能区域 (表格、编辑、删除等)...</p>
        <table class="data-table">
          <thead><tr><th>ID</th><th>名称</th><th>校区</th><th>描述</th><th>操作</th></tr></thead>
          <tbody>
          <tr v-for="canteen in canteens" :key="canteen.id">
            <td>{{ canteen.id }}</td>
            <td>{{ canteen.name }}</td>
            <td>{{ canteen.campus === 'weijinlu' ? '卫津路' : '北洋园' }}</td>
            <td>{{ canteen.description }}</td>
            <td><button class="btn-icon btn-edit" @click="openCanteenModal(canteen)"><i class="fas fa-edit"></i></button> <button class="btn-icon btn-delete" @click="deleteItem('canteen', canteen.id)"><i class="fas fa-trash"></i></button></td>
          </tr>
          <tr v-if="!canteens.length"><td colspan="5" class="text-center">暂无食堂数据</td></tr>
          </tbody>
        </table>
      </div>

      <!-- 窗口管理 -->
      <div v-if="currentTab === 'windows'" class="tab-content">
        <h3>窗口列表</h3>
        <button @click="openWindowModal()" class="btn btn-primary btn-add-item">
          <i class="fas fa-plus"></i> 添加新窗口
        </button>
        <p class="placeholder-text">窗口列表和管理功能区域 (按食堂筛选、表格、编辑、删除等)...</p>
        <table class="data-table">
          <thead><tr><th>ID</th><th>名称</th><th>所属食堂</th><th>描述</th><th>操作</th></tr></thead>
          <tbody>
          <tr v-for="windowItem in windows" :key="windowItem.id">
            <td>{{ windowItem.id }}</td>
            <td>{{ windowItem.name }}</td>
            <td>{{ getCanteenNameById(windowItem.canteenId) }}</td>
            <td>{{ windowItem.description }}</td>
            <td><button class="btn-icon btn-edit" @click="openWindowModal(windowItem)"><i class="fas fa-edit"></i></button> <button class="btn-icon btn-delete" @click="deleteItem('window', windowItem.id)"><i class="fas fa-trash"></i></button></td>
          </tr>
          <tr v-if="!windows.length"><td colspan="5" class="text-center">暂无窗口数据</td></tr>
          </tbody>
        </table>
      </div>

      <!-- 菜品管理 -->
      <div v-if="currentTab === 'dishes'" class="tab-content">
        <h3>菜品列表</h3>
        <button @click="openDishModal()" class="btn btn-primary btn-add-item">
          <i class="fas fa-plus"></i> 添加新菜品
        </button>
        <p class="placeholder-text">菜品列表和管理功能区域 (按窗口筛选、表格、编辑、删除等)...</p>
        <table class="data-table">
          <thead><tr><th>ID</th><th>名称</th><th>所属窗口</th><th>价格</th><th>描述</th><th>操作</th></tr></thead>
          <tbody>
          <tr v-for="dish in dishes" :key="dish.id">
            <td>{{ dish.id }}</td>
            <td>{{ dish.name }}</td>
            <td>{{ getWindowNameById(dish.windowId) }}</td>
            <td>￥{{ dish.price }}</td>
            <td>{{ dish.description }}</td>
            <td><button class="btn-icon btn-edit" @click="openDishModal(dish)"><i class="fas fa-edit"></i></button> <button class="btn-icon btn-delete" @click="deleteItem('dish', dish.id)"><i class="fas fa-trash"></i></button></td>
          </tr>
          <tr v-if="!dishes.length"><td colspan="6" class="text-center">暂无菜品数据</td></tr>
          </tbody>
        </table>
      </div>
    </div>

    <!-- 通用模态框 (食堂/窗口/菜品 添加/编辑) -->
    <div v-if="showItemModal" class="modal-overlay" @click.self="closeItemModal">
      <div class="modal-content item-modal-content">
        <h3>{{ modalTitle }}</h3>
        <form @submit.prevent="saveItem">
          <!-- 根据 modalType 动态生成表单字段 -->
          <div class="form-group" v-if="modalType === 'canteen' || modalType === 'window' || modalType === 'dish'">
            <label for="itemName">名称:</label>
            <input type="text" id="itemName" v-model="currentItem.name" required>
          </div>
          <div class="form-group" v-if="modalType === 'canteen'">
            <label for="itemCampus">校区:</label>
            <select id="itemCampus" v-model="currentItem.campus" required>
              <option value="weijinlu">卫津路校区</option>
              <option value="beiyangyuan">北洋园校区</option>
            </select>
          </div>
          <div class="form-group" v-if="modalType === 'window'">
            <label for="itemCanteenId">所属食堂:</label>
            <select id="itemCanteenId" v-model="currentItem.canteenId" required>
              <option v-for="c in canteens" :key="c.id" :value="c.id">{{ c.name }} ({{c.campus === 'weijinlu' ? '卫津路' : '北洋园'}})</option>
            </select>
          </div>
          <div class="form-group" v-if="modalType === 'dish'">
            <label for="itemWindowId">所属窗口:</label>
            <select id="itemWindowId" v-model="currentItem.windowId" required>
              <option v-for="w in windows" :key="w.id" :value="w.id">{{ w.name }} - {{getCanteenNameById(w.canteenId)}}</option>
            </select>
          </div>
          <div class="form-group" v-if="modalType === 'dish'">
            <label for="itemPrice">价格 (元):</label>
            <input type="number" id="itemPrice" v-model.number="currentItem.price" required min="0" step="0.01">
          </div>
          <div class="form-group">
            <label for="itemDescription">描述:</label>
            <textarea id="itemDescription" v-model="currentItem.description" rows="3"></textarea>
          </div>
          <!-- 更多字段根据需要添加，如图片上传等 -->
          <div class="modal-actions">
            <button type="button" @click="closeItemModal" class="btn btn-secondary">取消</button>
            <button type="submit" class="btn btn-primary">保存</button>
          </div>
        </form>
      </div>
    </div>

  </div>
</template>

<script>
export default {
  name: 'AdminFoodManagementView',
  data() {
    return {
      currentTab: 'canteens', //默认显示食堂管理
      // 模拟数据
      canteens: [
        { id: 1, name: '学一食堂', campus: 'weijinlu', description: '卫津路历史悠久的食堂之一' },
        { id: 4, name: '梅园食堂', campus: 'beiyangyuan', description: '北洋园校区中心食堂' },
      ],
      windows: [
        { id: 101, name: '红烧牛肉面窗口', canteenId: 1, description: '招牌红烧牛肉面' },
        { id: 201, name: '梅园小炒1号', canteenId: 4, description: '经济实惠，出餐快' },
      ],
      dishes: [
        { id: 10101, name: '红烧牛肉面', windowId: 101, price: 15, description: '精选牛腱肉' },
        { id: 20101, name: '番茄炒蛋套餐', windowId: 201, price: 16, description: '国民菜' },
      ],
      showItemModal: false,
      modalType: '', // 'canteen', 'window', 'dish'
      modalTitle: '',
      currentItem: {}, // 用于添加或编辑的对象
      isEditing: false,
    };
  },
  methods: {
    openCanteenModal(canteen = null) {
      this.isEditing = !!canteen;
      this.modalType = 'canteen';
      this.modalTitle = canteen ? '编辑食堂信息' : '添加新食堂';
      this.currentItem = canteen ? { ...canteen } : { name: '', campus: 'weijinlu', description: '' };
      this.showItemModal = true;
    },
    openWindowModal(windowItem = null) {
      this.isEditing = !!windowItem;
      this.modalType = 'window';
      this.modalTitle = windowItem ? '编辑窗口信息' : '添加新窗口';
      this.currentItem = windowItem ? { ...windowItem } : { name: '', canteenId: this.canteens.length > 0 ? this.canteens[0].id : null, description: '' };
      this.showItemModal = true;
    },
    openDishModal(dish = null) {
      this.isEditing = !!dish;
      this.modalType = 'dish';
      this.modalTitle = dish ? '编辑菜品信息' : '添加新菜品';
      this.currentItem = dish ? { ...dish } : { name: '', windowId: this.windows.length > 0 ? this.windows[0].id : null, price: null, description: '' };
      this.showItemModal = true;
    },
    closeItemModal() {
      this.showItemModal = false;
      this.currentItem = {};
      this.modalType = '';
      this.isEditing = false;
    },
    saveItem() {
      console.log(`保存 ${this.modalType}:`, this.currentItem);
      // 模拟API调用
      if (this.isEditing) { // 编辑逻辑
        if (this.modalType === 'canteen') {
          const index = this.canteens.findIndex(c => c.id === this.currentItem.id);
          if (index !== -1) this.canteens.splice(index, 1, this.currentItem);
        } else if (this.modalType === 'window') {
          const index = this.windows.findIndex(w => w.id === this.currentItem.id);
          if (index !== -1) this.windows.splice(index, 1, this.currentItem);
        } else if (this.modalType === 'dish') {
          const index = this.dishes.findIndex(d => d.id === this.currentItem.id);
          if (index !== -1) this.dishes.splice(index, 1, this.currentItem);
        }
        alert(`${this.modalTitle} 信息已更新！`);
      } else { // 添加逻辑
        const newItem = { ...this.currentItem, id: Date.now() }; // 简单唯一ID
        if (this.modalType === 'canteen') this.canteens.push(newItem);
        else if (this.modalType === 'window') this.windows.push(newItem);
        else if (this.modalType === 'dish') this.dishes.push(newItem);
        alert(`新 ${this.modalTitle.split(' ')[1]} 已添加！`);
      }
      this.closeItemModal();
    },
    deleteItem(type, id) {
      if (confirm(`确定要删除这个 ${type === 'canteen' ? '食堂' : type === 'window' ? '窗口' : '菜品'} 吗？相关联的数据可能也会受到影响。`)) {
        console.log(`删除 ${type} ID: ${id}`);
        // 模拟API调用和本地数据删除
        if (type === 'canteen') this.canteens = this.canteens.filter(c => c.id !== id);
        else if (type === 'window') this.windows = this.windows.filter(w => w.id !== id);
        else if (type === 'dish') this.dishes = this.dishes.filter(d => d.id !== id);
        alert(`${type === 'canteen' ? '食堂' : type === 'window' ? '窗口' : '菜品'} 已删除！`);
      }
    },
    getCanteenNameById(canteenId){
      const canteen = this.canteens.find(c => c.id === canteenId);
      return canteen ? canteen.name : '未知食堂';
    },
    getWindowNameById(windowId){
      const windowItem = this.windows.find(w => w.id === windowId);
      return windowItem ? windowItem.name : '未知窗口';
    }
  }
}
</script>

<style scoped>
.admin-food-manage-view { padding: 20px; }
.page-title-bar { margin-bottom: 25px; padding-bottom: 15px; border-bottom: 1px solid #e0e0e0; }
.page-title-bar h2 { font-size: 1.8em; color: #333; margin-top: 0; margin-bottom: 5px; display:flex; align-items:center; }
.page-title-bar h2 i { margin-right: 12px; color: #fd7e14; } /* 橙色图标 */
.page-title-bar p { font-size: 1em; color: #666; margin-bottom: 0; }

.management-tabs {
  display: flex;
  margin-bottom: 20px;
  border-bottom: 2px solid #dee2e6;
}
.management-tabs button {
  padding: 12px 20px;
  font-size: 1.05em;
  font-weight: 500;
  color: #495057;
  background-color: transparent;
  border: none;
  border-bottom: 3px solid transparent;
  cursor: pointer;
  transition: color 0.2s, border-color 0.2s;
  display: flex; align-items:center;
}
.management-tabs button i { margin-right:8px; }
.management-tabs button:hover {
  color: #007bff;
}
.management-tabs button.active {
  color: #007bff;
  border-bottom-color: #007bff;
}

.content-card { background-color: #fff; padding: 25px; border-radius: 0 0 8px 8px; box-shadow: 0 1px 3px rgba(0,0,0,0.08); }
.tab-content h3 { font-size: 1.5em; color: #333; margin-top:0; margin-bottom:15px; }
.btn-add-item { margin-bottom: 20px; }
.btn { padding: 8px 16px; border: none; border-radius: 5px; font-size: 0.9em; font-weight: 500; cursor: pointer; transition: background-color 0.2s ease; display:inline-flex; align-items:center; }
.btn i { margin-right: 6px; }
.btn-primary { background-color: #007bff; color: white; }
.btn-primary:hover { background-color: #0069d9; }
.btn-secondary { background-color: #6c757d; color:white; }
.btn-secondary:hover { background-color: #5a6268; }


.placeholder-text { color: #888; font-style: italic; margin: 20px 0; text-align: center; }
.data-table { width: 100%; border-collapse: collapse; font-size: 0.9em; margin-top:15px;}
.data-table th, .data-table td { padding: 10px 12px; text-align: left; border: 1px solid #e9ecef; vertical-align: middle; }
.data-table th { background-color: #f8f9fa; font-weight: 600; color: #495057; }
.data-table tbody tr:hover { background-color: #f1f3f5; }
.text-center { text-align: center !important; }
.action-buttons .btn-icon { background: none; border: none; cursor: pointer; padding: 6px; margin: 0 4px; font-size: 1.1em; transition: color 0.2s ease; }
.btn-edit { color: #007bff; }
.btn-delete { color: #dc3545; }
.btn-edit:hover { color: #0056b3; }
.btn-delete:hover { color: #c82333; }

/* Modal Styles */
.modal-overlay { position: fixed; top: 0; left: 0; width: 100%; height: 100%; background-color: rgba(0,0,0,0.5); display: flex; justify-content: center; align-items: center; z-index: 1050;}
.item-modal-content { background-color: white; padding: 25px; border-radius: 8px; box-shadow: 0 5px 15px rgba(0,0,0,0.3); width: 90%; max-width: 600px; }
.item-modal-content h3 { margin-top: 0; margin-bottom: 20px; font-size:1.4em; color:#333; }
.item-modal-content .form-group { margin-bottom: 15px; }
.item-modal-content .form-group label { display: block; font-weight: bold; margin-bottom: 6px; font-size:0.9em; }
.item-modal-content input[type="text"],
.item-modal-content input[type="number"],
.item-modal-content select,
.item-modal-content textarea { width: 100%; padding: 10px; border: 1px solid #ced4da; border-radius: 4px; box-sizing: border-box; font-size:0.95em; }
.item-modal-content textarea { min-height: 80px; }
.modal-actions { margin-top: 25px; display: flex; justify-content: flex-end; gap: 10px; }
</style>
    