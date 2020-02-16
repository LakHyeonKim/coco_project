import axios from "axios";

export default axios.create({
	// baseURL: "http://192.168.100.95:8888", // 진호
	// baseURL: "http://192.168.100.57:8888", // 락현
	baseURL: "http://119.202.135.104:8888", // 락현 local
	headers: {
		"Content-type": "application/json"
	}
});