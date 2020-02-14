import axios from "axios";

export default axios.create({
	baseURL: "http://192.168.100.95:8888", // 진호
	// baseURL: "http://192.168.100.57:8888", // 락현
	headers: {
		"Content-type": "application/json"
	}
});
