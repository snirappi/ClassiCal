var loading = false;

function scoreUp(inputID) {
	//TODO
}

function newMsg(text) {
	//TODO
}

function newPost(title, desc){
	//TODO
}

function newReply(id, text){
	//TODO
}

function report(id, reason){
	//TODO
}

function login(user, pass){
	//TODO
}

function refreshClasses(){
	//TODO
}

function loadPost(id){
	//TODO
}

function loadChat(){
	//TODO
}

function loadCalendar(){
	//TODO
}

function loadForum(){
	//TODO
}

function logOut(){
	//TODO
}

$('#profileButton').click(function() {
	$('#darkBox').fadeIn();
	$('#userBox').fadeIn();
});

$('#settingsButton').click(function() {
	$('#darkBox').fadeIn();
	$('#settingsBox').fadeIn();
});

$('#forBut').click(function() {
	if(!$('#calendar').hasClass("hidden")){
		$('#calendar').fadeOut(function(){
			$('#forum').fadeIn();
			$('#forum').removeClass("hidden");
		});
		$('#calendar').addClass("hidden");
	} else if(!$('#chatroom').hasClass("hidden")){
		$('#chatroom').fadeOut(function(){
			$('#forum').fadeIn();
			$('#forum').removeClass("hidden");
		});
		$('#chatroom').addClass("hidden");
	} else if(!$('#forumPostView').hasClass("hidden")){
		$('#forumPostView').fadeOut(function(){
			$('#forum').fadeIn();
			$('#forum').removeClass("hidden");
		});
		$('#forumPostView').addClass("hidden");
	}
});

$('#chaBut').click(function() {
	if(!$('#calendar').hasClass("hidden")){
		$('#calendar').fadeOut(function(){
			$('#chatroom').fadeIn();
			$('#chatroom').removeClass("hidden");

		});
		$('#calendar').addClass("hidden");

	} else if(!$('#forum').hasClass("hidden")){
		$('#forum').fadeOut(function(){
			$('#chatroom').fadeIn();
			$('#chatroom').removeClass("hidden");

		});
		$('#forum').addClass("hidden");


	} else if(!$('#forumPostView').hasClass("hidden")){
		$('#forumPostView').fadeOut(function(){
			$('#chatroom').fadeIn();
			$('#chatroom').removeClass("hidden");

		});
		$('#forumPostView').addClass("hidden");
	}

});

$('#calBut').click(function() {
	if(!$('#forum').hasClass("hidden")){
		$('#forum').fadeOut(function(){
			$('#calendar').fadeIn();
			$('#calendar').removeClass("hidden");

		});
		$('#forum').addClass("hidden");

	} else if(!$('#chatroom').hasClass("hidden")){
		$('#chatroom').fadeOut(function(){
			$('#calendar').fadeIn();
			$('#chatroom').removeClass("hidden");


		});
		$('#chatroom').addClass("hidden");

	} else if(!$('#forumPostView').hasClass("hidden")){
		$('#forumPostView').fadeOut(function(){
			$('#calendar').fadeIn();
			$('#calendar').removeClass("hidden");

		});
		$('#forumPostView').addClass("hidden");
	}

});

$('.forumPost').click(function() {
	loadPost( $(this).attr("id") );
	$('#forum').fadeOut(function(){
		$('#forumPostView').fadeIn();
		$('#forumPostView').removeClass("hidden");
	});
	$('#forum').addClass("hidden");
});

$('#backButton').click(function(){
	$('#forumPostView').fadeOut(function(){
		$('#forum').fadeIn();
		$('#forumPostView').removeClass("hidden");
	});
	$('#forumPostView').addClass("hidden");
})

$('#loginButton').click(function() {
	$('#darkBox').fadeIn();
	$('#loginBox').fadeIn();
});


$('#newPostButton').click(function() {
	$('#darkBox').fadeIn();
	$('#newPostBox').fadeIn();
});



$('#darkBox').click(function(e) {
	if(loading)
		return;
	$('#loginBox').fadeOut();
	$('#userBox').fadeOut();
	$('#darkBox').fadeOut();
	$('#settingsBox').fadeOut();
	$('#reportBox').fadeOut();
	$('#newPostBox').fadeOut();
	$('#newReplyBox').fadeOut();
});


$('#chatInput').keypress(function(e) {
	if( e.which == 13) {
		sendInput($('#chatInput').val());
		$('#chatInput').val('');
	}
});

$('.reportButton').click(function(){
	$('#darkBox').fadeIn();
	$('#reportBox').fadeIn();
	//console.log($(this).parent().children('.sender').html());
	$('#reportedSender').text($(this).parent().children('.sender').text());
	$('#reportedMessage').text($(this).parent().children('.fromMessage').text());

})

$('#passwordBox').keypress(function(e) {
	if( e.which == 13) {
		alert('hi ' + $('#usernameBox').val());
		$('#loginBox').fadeOut();
		//$('#darkBox').fadeOut();
		$('#loadingBox').fadeIn();
		loading = true;
		login($('#usernameBox').val(),$('#passwordBox').val());
	}
})


$('#darkBox *').click(function(e) {
	e.stopPropagation();
});

//Forum View Code

$('.scoreUp').click(function(e) {
	$(this).text('_');
	scoreUp($(this).parent().attr("id"));
})

$('#newReplyButton').click(function() {
	$('#darkBox').fadeIn();
	$('#newReplyBox').fadeIn();
})