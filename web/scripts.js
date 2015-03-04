var loading = false;
var className = "CS 30700";
var username = "mholm";





function truncate(text, limit){
	if(text.length > limit)
		return text.substring(0, limit) + "...";
	else
		return text;
}

function scoreUp(inputID) {
	$.post(
		"scoreUp", {
			command: "scoreUp",
			user: username
			//any other data can go here
		},
		function(data) {
			console.log("UpNoted!");
		});
}

function scoreDown(inputID) {
	$.post(
		"scoreDown", {
			command: "scoreDown",
			user: username
		},
		function(data) {
			console.log("UnNoted!");
		});
}

function newMsg(text) {
	$.post(
		"chatmessage", {
			command: "chatMessage",
			user: username,
			content: text
		},
		function(data) {
			console.log(data);
			//
		});
}

function newPost(ttitle, desc){
	$.post(
		"newPost", {
			command: "newPost",
			user: username,
			title: ttitle,
			descrip: desc
		},
		function(data) {
			console.log(data);

		});
}

function newReply(id, text){
	$.post(
		"newReply", {
			command: "newPost",
			user: username,
			postId: id,
			replyContent: text
		},
		function(data) {
			console.log(data);
			//
		});
}

function report(id, rreason){
	var chat = false;;
	if($('#chatroom').hasClass('hidden')){
		chat = true;
	}
	$.post(
		"reportPost", {
			command: "newPost",
			user: username,
			postId: id,
			reason: rreason,
			type: chat
		},
		function(data) {
			console.log(data);
			//
		});
}

function login(user, pass){
	$.post(
		"login", {
			command: "login",
			user: username,
			password: pass
		},
		function(data) {
			console.log(data);
			//
		});
}

function refreshClasses(){
	$.post(
		"refreshClasses", {
			command: "refreshClasses",
			user: username
		},
		function(data) {
			console.log(data);

		});
}

function loadPost(id){
	$.post(
		"loadPost", {
			command: "loadPost",
			user: username,
			postId: id
		},
		function(data) {
			console.log(data);

		});
}

function loadChat(){
	$.post(
		"loadChat", {
			command: "loadChat",
			user: username
		},
		function(data) {
			console.log(data);
		});
	//TODO
}

function loadCalendarView(){
	$.post(
		"loadCal", {
			command: "loadCal",
			user: username
		},
		function(data) {
			console.log(data);
			//
		});
}

function loadForum(){
	$.post(
		"loadForum", {
			command: "login",
			user: username,
			currClass: className
		},
		function(data) {
			console.log(data);
			//
		});
}

function newEvent(name, start, end, recurring, descrip){
	$.post(
		"newEvent", {
			command: "newEvent",
			user: username,
			title: name,
			startTime: start,
			endTime: end,
			recur: recurring,
			desc: descrip
		},
		function(data) {
			console.log(data);
			//
		});
}
function removeEvent(){

}

function editEvent(eId, name, start, end, recurring, descrip){
 $.post(
		"editEvent", {
			command: "editEvent",
			user: username,
			id: eId,
			title: name,
			startTime: start,
			endTime: end,
			recur: recurring,
			desc: descrip
		},
		function(data) {
			console.log(data);
			//
		});
}

function joinEvent(eId){
	$.post(
		"joinEvent", {
			command: "joinEvent",
			user: username,
			id: eId
		},
		function(data) {
			console.log(data);
			//
		});
}

function logOut(){
	alert("not implemented yet");
}

function addPost(title, description, date, replies, score, user, eid){
	var newPost = $("<div>", {id: eid, class: "forumPost"});
	newPost.append($("<span>", {text: user, class: "author"}));
	newPost.append($("<span>", {text: score, class: "score"}));
	newPost.append($("<span>", {text: replies, class: "replies"}));
	newPost.append($("<span>", {text: date, class: "date"}));
	newPost.append($("<span>", {text: title, class: "title"}));
	newPost.append($("<span>", {text: truncate(description, 100), class: "preview"}));
	$("#forumPosts").prepend(newPost);
}

function addReply(description, date, score, user, eid){
	var newReply = $("<div>", {id: eid, class: "forumReply"});
	newReply.append($("<span>", {text: user, class: "author"}))
	newReply.append($("<span>", {text: score, class: "score"}))
	newReply.append($("<span>", {text: date, class: "date"}))
	newReply.append($("<span>", {text: description, class: "preview"}))
	//add an upvote button +handler
	$("#forumReplies").append(newReply);
}

function addMessage(message, user, eid){
	var newMsg = $('<div>', {id: eid, class: "chatMessageBox"});
	var container = $("<div>", {class: "messageContainer"})
	if(username == user){
		newMsg.append($("<span>", {text: "You", class: "sender you"}));
		container.append($("<span>", {text: message, class: "toMessage"}))

	} else {
		newMsg.append($("<span>", {text: username, class: "sender"}));
		container.append($("<span>", {text: message, class: "fromMessage"}));
		newMsg.append($("<span>", {text: "Report", class: "reportButton button"}))
		//TODO: ADD HANDLER
	}
	newMsg.append(container);
	$("#chatBox").append(newMsg);
	$("#chatBox").animate({ scrollTop: $("#chatBox").prop("scrollHeight") }, 100);
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
	if(!$('#calendarView').hasClass("hidden")){
		$('#calendarView').fadeOut(function(){
			$('#forum').fadeIn();
			$('#forum').removeClass("hidden");
		});
		$('#calendarView').addClass("hidden");

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
	if(!$('#calendarView').hasClass("hidden")){
		$('#calendarView').fadeOut(function(){
			$('#chatroom').fadeIn();
			$('#chatroom').removeClass("hidden");

		});
		$('#calendarView').addClass("hidden");

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
			$('#calendarView').fadeIn();
			$('#calendarView').removeClass("hidden");

		});
		$('#forum').addClass("hidden");

	} else if(!$('#chatroom').hasClass("hidden")){
		$('#chatroom').fadeOut(function(){
			$('#calendarView').fadeIn();
			$('#calendarView').removeClass("hidden");


		});
		$('#chatroom').addClass("hidden");

	} else if(!$('#forumPostView').hasClass("hidden")){
		$('#forumPostView').fadeOut(function(){
			$('#calendarView').fadeIn();
			$('#calendarView').removeClass("hidden");

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
		$('#forum').removeClass("hidden");
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

$('#submitPost').click(function() {
	$('#darkBox').fadeOut();
	$('#newPostBox').fadeOut();
	//title, description, date, replies, score, user, id

	//TEMPORARY
	addPost($('#newPostTitle').val(),
			$('#newPostContent').val(),
			new Date,
			0,
			0,
			username,
			Math.floor(Math.random()*1000000));
});

$('#submitReply').click(function(){
	$('#darkBox').fadeOut();
	$('#newReplyBox').fadeOut();
	//desc, date, score, user, id
	addReply(
			$('#newReplyContent').val(),
			new Date,
			0,
			username,
			Math.floor(Math.random()*1000000));
});

$('#classButton #classMenu li').click(function() {
	className = $(this).text();
	alert(className);
})

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
	$('#newEventBox').fadeOut();
});


$('#chatInput').keypress(function(e) {
	if( e.which == 13) {
		newMsg($('#chatInput').val());
		//TEMPORARY
		if($("#fakeUser").is(':checked'))
			addMessage($('#chatInput').val(), "Jerg", Math.floor(Math.random()*1000000))
		else
			addMessage($('#chatInput').val(), username, Math.floor(Math.random()*1000000))

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


//function newEvent(name, start, end, recurring, descrip){

$('#submitEvent').click(function(){
	newEvent($('#newEventTitle').val(),
			 $('#newEventTimeScale').text(),
			 $('#newEventTimeScale').text() + $('#newEventDuration') ,//FIX THIS LATER
			 $('#newEventRecurring').is(":checked"),
			 $('#newEventContent').val()
			)
	$('#darkBox').fadeOut();
	$('#newEventBox').fadeOut();

})


$('#darkBox *').click(function(e) {
	e.stopPropagation();
});

//Forum View Code

$('.scoreUp').click(function(e) {
	if($(this).text() == '.'){
		$(this).text('_');
		scoreUp($(this).parent().attr("id"));
	} else {
		$(this).text('.');
		scoreDown($(this).parent().attr("id"));
	}
})

$('#newReplyButton').click(function() {
	$('#darkBox').fadeIn();
	$('#newReplyBox').fadeIn();
})


//Calendar code

$(document).ready(function() {

	var date = new Date();
	var d = date.getDate();
	var m = date.getMonth();
	var y = date.getFullYear();

    // page is now ready, initialize the calendarView...
    var calendar = $('#calendar').fullCalendar({
    	header:
				{
					left: 'prev,next today',
					center: 'title',
					right: 'month,agendaWeek,agendaDay'
				},
		selectable: true,
		selectHelper: true,
		defaultView: 'agendaWeek',

		dayClick: function(date, jsEvent, view) {
			$('#darkBox').fadeIn();
	        $('#newEventBox').fadeIn();
	        if(date.minutes() == 0)
	        	$('#newEventTimeScale').text(date.hours() + ":" + date.minutes() + "0");
	    	else
	        	$('#newEventTimeScale').text(date.hours() + ":" + date.minutes());
    	},
    	eventClick: function(calEvent, jsEvent, view) {

	        alert('Event: ' + calEvent.title);

	        $('#darkBox').fadeIn();
	        $('#eventOpsBox').fadeIn();
	        $('#eventOpTitle').text(calEvent.title)
	        $('#eventOpTimeScale').text("TimeScale not yet implemented");
	        $('#eventOpDesc').text("Desc not yet implemented");

    	},

		// select: function(start, end, allDay)
		// {
		// 	var title = prompt('Event Title:');
		// 	if (title){
		// 		calendar.fullCalendar('renderEvent',
		// 			{
		// 				title: title,							start: start,
		// 				end: end,
		// 				allDay: allDay
		// 			},
		// 			true // make the event "stick"
		// 		);
		// 	}
		// 	calendar.fullCalendar('unselect');
		// },
		editable: true,
		events: [
			{
				title: 'All Day Event',
				start: new Date(y, m, 1)
			},
			{
				title: 'Long Event',
				start: new Date(y, m, d-5),
				end: new Date(y, m, d-2)
			},
			{
				id: 999,
				title: 'Repeating Event',
				start: new Date(y, m, d-3, 16, 0),
				allDay: false
			},
			{
				id: 999,
				title: 'Repeating Event',
				start: new Date(y, m, d+4, 16, 0),
				allDay: false
			},
			{
				title: 'Meeting',
				start: new Date(y, m, d, 10, 30),
				allDay: false
			},
			{
				title: 'Lunch',
				start: new Date(y, m, d, 12, 0),
				end: new Date(y, m, d, 14, 0),
				allDay: false
			},
			{
				title: 'Birthday Party',
				start: new Date(y, m, d+1, 19, 0),
				end: new Date(y, m, d+1, 22, 30),
				allDay: false
			},
			{
				title: 'Click for Google',
				start: new Date(y, m, 28),
				end: new Date(y, m, 29),
				url: 'http://google.com/'
			}
		]
        // put your options and callbacks here
    })

});