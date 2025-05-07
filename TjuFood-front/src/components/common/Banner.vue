<template>
  <div class="banner-container">
    <swiper
        :slides-per-view="1"
        :loop="true"
        :pagination="{ clickable: true }"
        :autoplay="{ delay: 5000 }"
        :modules="modules"
        class="banner-swiper"
    >
      <swiper-slide v-for="banner in banners" :key="banner.id">
        <router-link :to="banner.link" class="banner-link">
          <div class="banner-overlay"></div>
          <img :src="banner.image" :alt="banner.title" class="banner-image" />
          <div class="banner-content">
            <h2 class="banner-title">{{ banner.title }}</h2>
          </div>
        </router-link>
      </swiper-slide>
    </swiper>
  </div>
</template>

<script setup>
import { Swiper, SwiperSlide } from 'swiper/vue'
import { Pagination, Autoplay } from 'swiper/modules'
import 'swiper/css'
import 'swiper/css/pagination'
import { useDataStore } from '../../stores/data'

const dataStore = useDataStore()
const banners = dataStore.getBanners()

const modules = [Pagination, Autoplay]
</script>

<style scoped>
.banner-container {
  width: 100%;
  height: 200px;
  border-radius: var(--radius-lg);
  overflow: hidden;
  margin-bottom: var(--space-6);
}

.banner-swiper {
  width: 100%;
  height: 100%;
}

.banner-link {
  position: relative;
  display: block;
  width: 100%;
  height: 100%;
}

.banner-image {
  width: 100%;
  height: 100%;
  object-fit: cover;
}

.banner-overlay {
  position: absolute;
  bottom: 0;
  left: 0;
  right: 0;
  height: 60%;
  background: linear-gradient(to top, rgba(0, 0, 0, 0.7), transparent);
  z-index: 1;
}

.banner-content {
  position: absolute;
  bottom: 0;
  left: 0;
  padding: var(--space-4);
  color: white;
  z-index: 2;
}

.banner-title {
  font-size: 1.5rem;
  font-weight: 700;
  margin-bottom: var(--space-2);
}

@media (min-width: 768px) {
  .banner-container {
    height: 300px;
  }

  .banner-title {
    font-size: 2rem;
  }
}
</style>