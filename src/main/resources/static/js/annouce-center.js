/**
 * 
 */

Vue.component("announce-post",{
	props:["announces","announce"],
	template: `
		<div>
			<div v-for="announce in announces">
				<p>{{ announce.content }}</p>
			</div>
			<form @submit.prevent="post">
				<textarea v-model="announce.content" placeholder="输入公告内容"></textarea><br/>
				<input type="submit" value="发布公告" />
			</form>
		</div>
	`,
	data: () => {
		return {};
	},
	methods: {
		post: function(){
			this.$emit("post");
		}
	}
});

var announcePostForm = new Vue({
	el:"#announce-post",
	data: {
		announces:[{
			content:"等待抓取公告"
			}],
		announce:{
			content:"",
		}
	},
	methods:{
		postAnnounce: function() {
			var announce = this.announce;
			axios.post("/announces/post_announce",announce)
			.then( result => {this.announces = result.data})
			.catch( err => console.log(err));
		}
	},
	mounted: function(){
		axios.get("/announces/all")
		.then( result => {this.announces = result.data})
		.catch( err => {console.log(err)} )
	}
})