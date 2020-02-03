import axios from "axios";

export default axios.create({
	baseURL: "http://52.79.118.55:8888/api",
	headers: {
		"Content-type": "application/json"
	}
});
