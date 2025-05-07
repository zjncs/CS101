import { defineStore } from 'pinia'
import { ref } from 'vue'

export const useDataStore = defineStore('data', () => {
    // Mock data for campus, canteens, stalls and dishes

    const campuses = ref([
        {
            id: 1,
            name: '北洋园校区',
            address: '天津市津南区雅观路135号',
            image: 'https://images.pexels.com/photos/207691/pexels-photo-207691.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=2'
        },
        {
            id: 2,
            name: '卫津路校区',
            address: '天津市南开区卫津路92号',
            image: 'https://images.pexels.com/photos/207692/pexels-photo-207692.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=2'
        }
    ])

    const canteens = ref([
        {
            id: 1,
            name: '第一食堂',
            campusId: 1,
            address: '北洋园校区西区',
            openHours: '7:00-22:00',
            avgPrice: 15,
            introduction: '北洋园校区最大的学生食堂，提供多种特色菜品',
            score: 4.5,
            tasteScore: 4.6,
            environmentScore: 4.3,
            serviceScore: 4.5,
            liked: 328,
            comments: 156,
            images: [
                'https://images.pexels.com/photos/1307698/pexels-photo-1307698.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=2',
                'https://images.pexels.com/photos/6267/menu-restaurant-vintage-table.jpg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=2'
            ]
        },
        {
            id: 2,
            name: '第二食堂',
            campusId: 1,
            address: '北洋园校区东区',
            openHours: '7:00-21:00',
            avgPrice: 18,
            introduction: '以川菜为主的食堂，味道正宗，价格实惠',
            score: 4.7,
            tasteScore: 4.8,
            environmentScore: 4.5,
            serviceScore: 4.6,
            liked: 284,
            comments: 134,
            images: [
                'https://images.pexels.com/photos/67468/pexels-photo-67468.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=2',
                'https://images.pexels.com/photos/262978/pexels-photo-262978.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=2'
            ]
        },
        {
            id: 3,
            name: '清真食堂',
            campusId: 2,
            address: '卫津路校区北区',
            openHours: '7:00-21:00',
            avgPrice: 20,
            introduction: '提供清真美食，兰州拉面和手抓饭是招牌',
            score: 4.6,
            tasteScore: 4.7,
            environmentScore: 4.4,
            serviceScore: 4.5,
            liked: 245,
            comments: 116,
            images: [
                'https://images.pexels.com/photos/541216/pexels-photo-541216.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=2',
                'https://images.pexels.com/photos/735869/pexels-photo-735869.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=2'
            ]
        }
    ])

    const stalls = ref([
        {
            id: 1,
            name: '老王炒菜',
            canteenId: 1,
            location: 'A12',
            introduction: '提供各种家常炒菜，以鱼香肉丝最为出名',
            openHours: '10:00-20:00',
            score: 4.8,
            tasteScore: 4.9,
            priceScore: 4.7,
            comments: 89,
            images: [
                'https://images.pexels.com/photos/958545/pexels-photo-958545.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=2',
                'https://images.pexels.com/photos/1640772/pexels-photo-1640772.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=2'
            ]
        },
        {
            id: 2,
            name: '川湘居',
            canteenId: 1,
            location: 'B08',
            introduction: '正宗川菜湘菜，麻辣香锅和水煮鱼深受欢迎',
            openHours: '10:30-20:30',
            score: 4.7,
            tasteScore: 4.8,
            priceScore: 4.5,
            comments: 76,
            images: [
                'https://images.pexels.com/photos/2347311/pexels-photo-2347311.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=2',
                'https://images.pexels.com/photos/5836771/pexels-photo-5836771.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=2'
            ]
        },
        {
            id: 3,
            name: '兰州拉面',
            canteenId: 3,
            location: 'C02',
            introduction: '正宗兰州拉面，现场拉制，配料丰富',
            openHours: '7:00-21:00',
            score: 4.9,
            tasteScore: 4.9,
            priceScore: 4.8,
            comments: 102,
            images: [
                'https://images.pexels.com/photos/2664216/pexels-photo-2664216.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=2',
                'https://images.pexels.com/photos/1437267/pexels-photo-1437267.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=2'
            ]
        }
    ])

    const dishes = ref([
        {
            id: 1,
            name: '鱼香肉丝',
            stallId: 1,
            category: '川菜',
            price: 12.00,
            description: '经典川菜，酸甜可口，搭配米饭绝佳',
            isSpecial: true,
            score: 4.8,
            comments: 56,
            images: [
                'https://images.pexels.com/photos/2347311/pexels-photo-2347311.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=2',
                'https://images.pexels.com/photos/5836771/pexels-photo-5836771.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=2'
            ]
        },
        {
            id: 2,
            name: '麻婆豆腐',
            stallId: 2,
            category: '川菜',
            price: 10.00,
            description: '麻辣鲜香，豆腐嫩滑，肉沫鲜香',
            isSpecial: false,
            score: 4.7,
            comments: 42,
            images: [
                'https://images.pexels.com/photos/2313686/pexels-photo-2313686.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=2',
                'https://images.pexels.com/photos/7353379/pexels-photo-7353379.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=2'
            ]
        },
        {
            id: 3,
            name: '兰州牛肉拉面',
            stallId: 3,
            category: '面食',
            price: 15.00,
            description: '清汤挂面，牛肉鲜嫩，配料丰富',
            isSpecial: true,
            score: 4.9,
            comments: 78,
            images: [
                'https://images.pexels.com/photos/2664216/pexels-photo-2664216.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=2',
                'https://images.pexels.com/photos/1437267/pexels-photo-1437267.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=2'
            ]
        },
        {
            id: 4,
            name: '回锅肉',
            stallId: 2,
            category: '川菜',
            price: 14.00,
            description: '川菜经典，肥而不腻，香气四溢',
            isSpecial: true,
            score: 4.6,
            comments: 35,
            images: [
                'https://images.pexels.com/photos/691114/pexels-photo-691114.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=2',
                'https://images.pexels.com/photos/842571/pexels-photo-842571.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=2'
            ]
        }
    ])

    const blogs = ref([
        {
            id: 1,
            userId: 1,
            title: '北洋园必吃的五大美食',
            content: '作为天大学生，北洋园校区的美食已经成为日常生活中不可缺少的一部分。今天我想和大家分享我心目中的北洋园五大必吃美食...',
            canteenId: 1,
            stallId: 1,
            liked: 156,
            comments: 32,
            createTime: '2023-10-15',
            images: [
                'https://images.pexels.com/photos/958545/pexels-photo-958545.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=2',
                'https://images.pexels.com/photos/1640772/pexels-photo-1640772.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=2'
            ]
        },
        {
            id: 2,
            userId: 1,
            title: '川湘居麻辣香锅的终极吃法',
            content: '作为一个四川人，对于麻辣香锅的要求很高。不过川湘居的麻辣香锅确实让我刮目相看，今天要分享的是我的独家配菜方案和吃法...',
            canteenId: 1,
            stallId: 2,
            liked: 98,
            comments: 24,
            createTime: '2023-11-02',
            images: [
                'https://images.pexels.com/photos/2347311/pexels-photo-2347311.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=2',
                'https://images.pexels.com/photos/5836771/pexels-photo-5836771.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=2'
            ]
        }
    ])

    const reviews = ref([
        {
            id: 1,
            userId: 1,
            userNickname: '美食达人',
            userAvatar: 'https://images.pexels.com/photos/220453/pexels-photo-220453.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=2',
            content: '鱼香肉丝味道正宗，分量也足，性价比很高',
            dishId: 1,
            overallScore: 5,
            tasteScore: 5,
            priceScore: 4,
            liked: 15,
            createTime: '2023-11-10',
            images: [
                'https://images.pexels.com/photos/2347311/pexels-photo-2347311.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=2'
            ]
        },
        {
            id: 2,
            userId: 2,
            userNickname: '天大吃货',
            userAvatar: 'https://images.pexels.com/photos/415829/pexels-photo-415829.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=2',
            content: '麻婆豆腐的麻辣程度刚好，豆腐很嫩，喜欢辣的可以试试',
            dishId: 2,
            overallScore: 4,
            tasteScore: 4,
            priceScore: 5,
            liked: 8,
            createTime: '2023-11-12',
            images: []
        }
    ])

    const comments = ref([
        {
            id: 1,
            userId: 2,
            userNickname: '天大吃货',
            userAvatar: 'https://images.pexels.com/photos/415829/pexels-photo-415829.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=2',
            blogId: 1,
            content: '看起来真不错，我也要去尝尝这个鱼香肉丝',
            liked: 5,
            createTime: '2023-10-16'
        },
        {
            id: 2,
            userId: 3,
            userNickname: '美食猎人',
            userAvatar: 'https://images.pexels.com/photos/1222271/pexels-photo-1222271.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=2',
            blogId: 1,
            content: '北洋园的美食真的很多，但是你居然没提西区的那家烤冷面！',
            liked: 8,
            createTime: '2023-10-16'
        }
    ])

    // Mock data for rankings
    const dishRankings = ref([
        { id: 3, name: '兰州牛肉拉面', score: 4.9, image: 'https://images.pexels.com/photos/2664216/pexels-photo-2664216.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=2' },
        { id: 1, name: '鱼香肉丝', score: 4.8, image: 'https://images.pexels.com/photos/2347311/pexels-photo-2347311.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=2' },
        { id: 2, name: '麻婆豆腐', score: 4.7, image: 'https://images.pexels.com/photos/2313686/pexels-photo-2313686.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=2' }
    ])

    const canteenRankings = ref([
        { id: 1, name: '第一食堂', score: 4.5, image: 'https://images.pexels.com/photos/1307698/pexels-photo-1307698.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=2' },
        { id: 3, name: '清真食堂', score: 4.6, image: 'https://images.pexels.com/photos/541216/pexels-photo-541216.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=2' },
        { id: 2, name: '第二食堂', score: 4.7, image: 'https://images.pexels.com/photos/67468/pexels-photo-67468.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=2' }
    ])

    // Mock data for banners
    const banners = ref([
        {
            id: 1,
            title: '美食节来袭',
            image: 'https://images.pexels.com/photos/1640772/pexels-photo-1640772.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=2',
            link: '/blog/1'
        },
        {
            id: 2,
            title: '打卡校园必吃Top 10',
            image: 'https://images.pexels.com/photos/958545/pexels-photo-958545.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=2',
            link: '/rankings'
        },
        {
            id: 3,
            title: '清真食堂新菜上线',
            image: 'https://images.pexels.com/photos/541216/pexels-photo-541216.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=2',
            link: '/canteen/3'
        }
    ])

    // Methods to get data
    function getCampuses() {
        return campuses.value
    }

    function getCampusById(id) {
        return campuses.value.find(campus => campus.id === Number(id))
    }

    function getCanteensByCampusId(campusId) {
        return canteens.value.filter(canteen => canteen.campusId === Number(campusId))
    }

    function getCanteenById(id) {
        return canteens.value.find(canteen => canteen.id === Number(id))
    }

    function getStallsByCanteenId(canteenId) {
        return stalls.value.filter(stall => stall.canteenId === Number(canteenId))
    }

    function getStallById(id) {
        return stalls.value.find(stall => stall.id === Number(id))
    }

    function getDishesByStallId(stallId) {
        return dishes.value.filter(dish => dish.stallId === Number(stallId))
    }

    function getDishById(id) {
        return dishes.value.find(dish => dish.id === Number(id))
    }

    function getAllBlogs() {
        return blogs.value
    }

    function getBlogById(id) {
        return blogs.value.find(blog => blog.id === Number(id))
    }

    function getReviewsByDishId(dishId) {
        return reviews.value.filter(review => review.dishId === Number(dishId))
    }

    function getCommentsByBlogId(blogId) {
        return comments.value.filter(comment => comment.blogId === Number(blogId))
    }

    function getBanners() {
        return banners.value
    }

    function getDishRankings() {
        return dishRankings.value
    }

    function getCanteenRankings() {
        return canteenRankings.value
    }

    function searchItems(query) {
        if (!query) return { dishes: [], canteens: [], stalls: [], blogs: [] }

        const lowerQuery = query.toLowerCase()

        const filteredDishes = dishes.value.filter(dish =>
            dish.name.toLowerCase().includes(lowerQuery) ||
            dish.description.toLowerCase().includes(lowerQuery)
        )

        const filteredCanteens = canteens.value.filter(canteen =>
            canteen.name.toLowerCase().includes(lowerQuery) ||
            canteen.introduction.toLowerCase().includes(lowerQuery)
        )

        const filteredStalls = stalls.value.filter(stall =>
            stall.name.toLowerCase().includes(lowerQuery) ||
            stall.introduction.toLowerCase().includes(lowerQuery)
        )

        const filteredBlogs = blogs.value.filter(blog =>
            blog.title.toLowerCase().includes(lowerQuery) ||
            blog.content.toLowerCase().includes(lowerQuery)
        )

        return {
            dishes: filteredDishes,
            canteens: filteredCanteens,
            stalls: filteredStalls,
            blogs: filteredBlogs
        }
    }

    return {
        getCampuses,
        getCampusById,
        getCanteensByCampusId,
        getCanteenById,
        getStallsByCanteenId,
        getStallById,
        getDishesByStallId,
        getDishById,
        getAllBlogs,
        getBlogById,
        getReviewsByDishId,
        getCommentsByBlogId,
        getBanners,
        getDishRankings,
        getCanteenRankings,
        searchItems
    }
})