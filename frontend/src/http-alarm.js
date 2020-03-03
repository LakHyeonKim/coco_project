import axios from "axios";

export default axios.create({
	//baseURL: "http://192.168.100.57:8081"
	//baseURL: "http://192.168.100.95:8888", // 진호
	//baseURL: "http://192.168.100.57:8081", // 락현
	baseURL: "http://52.79.118.55:8081", // 배포 락현 local
});
