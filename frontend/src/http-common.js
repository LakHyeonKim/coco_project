import axios from "axios";

export default axios.create({
	baseURL: "http://192.168.100.95:8888",
	headers: {
		"Content-type": "application/json"
	}
});