import Vue from "vue";
import VueRouter from "vue-router";
import Room from "./views/Room.vue";
import RoomDetail from "./views/RoomDetail";

Vue.use(VueRouter);



const routes = [
	{
		path: "/",
		name: "Room",
		component: Room
    },
    {
		path: "/roomdetail",
		name: "RoomDetail",
		component: RoomDetail
	}
];

const router = new VueRouter({
	mode: "history",
	base: process.env.BASE_URL,
	routes
});


export default router;