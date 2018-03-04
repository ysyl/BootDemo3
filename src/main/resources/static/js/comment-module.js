/**
 * 文章页的评论区组件
 */

Vue.component('comment-module',{
	props:["comments", "comment"],
	template: `	
	<div>
		<div v-for = "comment in comments">
				<div>
					<span> {{ comment.content }} <a :href="'/users/profile/user_id-' + comment.user.id" >{{ comment.user.name }}</a></span>
				</div>
		</div>
		<form @submit.prevent="post">
			<textarea v-model="comment.content"></textarea>
			<input type = "submit" value = "提交评论"  />
		</form>			
	</div>
	`,
	data: function() {
		return {};
	},
	methods: {
		post: function() {
			this.$emit("post");
		}
	}
});

var comm = new Vue({
				el:"#test1",
				data:{
					comments: [],
					comment:{
						content:"",
					},
					userId: -1,
				},
				methods: {					
					postComments: function(){
						var data = JSON.stringify(this.comment);
						var instance = axios.create({
							headers: {
								'Content-Type': 'application/json;charset=UTF-8'
							}
						})
						instance.post("/comments/article_id-" + articleId, data)
						.then( result => { 
							this.comments = result.data.comments;
							this.userId = result.data["user-id"];
							})
						.catch( err => { console.log(err) })
					}
				},
				mounted(){
					axios.get("/comments/article_id-" + articleId)
					.then( result => { 
						this.comments = result.data.comments;
						this.userId = result.data["user-id"];
						} )
					.catch( err => { console.log(err); });
				}
			});

Vue.component("like",{
	props:["isLike", "likedCount"],
	template: `
		<div>
			<div>{{ likedCount }}</div>
			<a href="#" @click.prevent="like">like</a>
			<p v-if="isLike">liked</p>
		</div>
	`,
	methods: {
		like: function() {
			this.$emit("like");
		}
	}
})

var likeBtn = new Vue({
	el:"#like",
	data: {
		hrefPrefix: "/notice/post-remind",
		isLike:isLiked,
		likedCount: likedCount
	},
	computed: {
		href: function() {
			return `${this.hrefPrefix}?action=like&article_id=${articleId}`
		}
	},
	methods: {
		likeAction: function() {
			console.log("e");
			axios.get(this.href, {
				action: "like",
				"article-id": articleId,
			})
			.then( result => { this.isLike = result.data })
			.catch( err => { console.log(err)})
		},
		getLikeStats: function() {
			
		}
	}
})
