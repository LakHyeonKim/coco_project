<template>
 
</template>

<script>
import http from "../http-common";
export default {
    name: "EmailMessage",
    data() {
        return {
            key: ""
        }
    },
    mounted() {
        console.log("KEY");
        console.log(this.$route.params.key);

        http.get("/jwt/certificationByEmail/" + this.$route.params.key)
			.then(res => {
                console.log("RESPONSE");
                console.log(res);
                if(res.status == 200) {
                    alert("이메일 인증에 성공했습니다!");
                } else alert("이메일 인증에 실패했습니다..");
                this.$router.push("/");
			})
			.catch(err => {
				console.log("certificationByEmail err ", err);
			});
    }
}
</script>

<style>

</style>