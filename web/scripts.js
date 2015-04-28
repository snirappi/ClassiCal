var loading = false;
var className = "CS 30700";
var courseNum = 0;
var username = "mholm";
var calendar;
var currentStartTime;

var webSocket;

var courses = ["CS 30700", "MA 26100", "STAT 35000", "CS 25200"];
var crns    = ["43855", "12345", "23456", "34567"];



function truncate(text, limit){
	if(text.length > limit)
		return text.substring(0, limit) + "...";
	else
		return text;
}

function login(user, pass){
	// var superSecureKey = "Jintado" //no peekerino. We'll hopefully fix this in S3

	// var encryptedPass = CryptoJS.AES.encrypt(pass, superSecureKey);
	// var encryptedUser = CryptoJS.AES.encrypt(user, superSecureKey);


	//ENCRYPTION DOES SOMETHING FUNKY TO THE POST REQUEST, FIX WHEN SERVER IS RUNNING
	var encryptedUser = user;
	var encryptedPass = pass;

	$.post(
		"login", {
			command: "login",
			username: encryptedUser,
			password: encryptedPass
		},
		function(data) {
			console.log(data);
			var temp = JSON.parse(data);
			if(temp.valid == 1){
				username = temp.user;
				openSocket();
				return true;
			} else return false;


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


function report(id, rreason){
	var chat = false;;
	if($('#chatroom').hasClass('hidden')){
		chat = true;
	}
	$.post(
		"reportPost", {
			command: "report",
			user: username,
			crn: crns[courseNum],
			postId: id,
			reason: rreason,
			type: chat
		},
		function(data) {
			console.log(data);
			//
		});
}

function openSocket() {
	if(webSocket !== undefined && webSocket.readyState !== WebSocket.CLOSED) {
		console.log("WebSocket is already open.");
		return;
	}
	var connectString = "ws://73.168.58.212:8080/chatserver/" + username + "/" + className;
	webSocket = new WebSocket(connectString);
	webSocket.onopen = function(event) {
		if(event.data === undefined)
			return;
		console.log(event.data);
	};
	webSocket.onmessage = function(event){
		console.log(event.data);
		var temp = JSON.parse(event.data);
		var tcontent = temp.content;
		if(tcontent !== "2g982i29")
			addMessage(temp.content, temp.user, temp.id);
	};
	webSocket.onclose = function(event){
		console.log("Connection Closed (RIP 💔)");
	};
}

function sendMessage(text) {
	var toSend = "{user: \""+username+"\", crn: \""+className+"\", content: \""+text+"\"}";
	console.log(toSend);
	if(webSocket == undefined || webSocket.readyState == WebSocket.CLOSED) { //reopen connection and try again
		console.log("not connected");
		openSocket();
		setTimeout(webSocket.send(toSend), 500);
	} else if(webSocket.readyState == WebSocket.CONNECTING) { //still connecting - wait then try again
		setTimeout(webSocket.send(toSend), 2000);
	} else {
		webSocket.send(toSend);
	}
}

function closeSocket() {
	if(webSocket !== undefined || webSocket.readyState == WebSocket.OPENED)
		webSocket.close();
}


function loadForum(){
	$('#forumPosts').empty();
	$.post(
		"forums", {
			command: "getParents",
			//user: username,
			crn: crns[courseNum]
		},
		function(data) {
			$('#forumPosts').innerHTML = '';
			console.log(data);
			//
			var temp = JSON.parse(data);
			for(var i = 0; i < temp.messages.length; i++) {
				var post = temp.messages[i];
				// addPost(title, description, date, replies, score, user, eid){
				var upnoted = false;

				for(var j = 0; j < temp.upnoters.length; j++){
					var t = t[j];
					if(t == username)
						upnoted = true;
				}
				console.log(post.title + " " + post.content + " " + post.date + " " + post.upnoters.length + " " + post.creator + " " + post.id + " " + upnoted)
				addPost(post.title, post.content, post.date, post.upnoters.length, post.creator, post.id, upnoted);
			}
		});
}

function newPost(ttitle, desc, postref){
	$.post(
		"forums", {
			command: "post",
			user: username,
			crn: crns[courseNum],
			title: ttitle,
			content: desc
		},
		function(data) {
			console.log(data);

		});
}

function loadPost(id){

	$.post(
		"forums", {
			command: "getChildren",
			crn: crns[courseNum],
			user: username,
			postId: id
		},
		function(data) {
			console.log(data);

			var temp = JSON.parse(data);
				//setOP(title, description, date, score, user, eid){
			setOP(temp.title, temp.description, temp.date, temp.score, temp.creator, temp.id);
			for(var i = 0; i < temp.messages.length; i++) {
				var reply = temp.messages[i];
				var upnoted = false;

				for(var j = 0; j < temp.upnoters.length; j++){
					var t = t[j];
					if(t == username)
						upnoted = true;
				}
				//addReply(description, date, score, user, eid){
				addReply(post.desc, post.date, post.score, post.creator, post.id);
			}
		});
}

function scoreUp(inputID) {
	$.post(
		"forums", {
			command: "scoreUp",
			user: username,
			id: parentId,
			crn: courseNum,
			up: true
			//any other data can go here
		},
		function(data) {
			console.log("UpNoted!");
		});
}

function scoreDown(inputID) {
	$.post(
		"forums", {
			command: "scoreDown",
			user: username,
			id: parentId,
			crn: courseNum,
			up: false
		},
		function(data) {
			console.log("UnNoted!");
		});
}



function newReply(id, text){
	$.post(
		"forums", {
			command: "reply",
			user: username,
			postId: id,
			crn: crns[courseNum],
			content: text
		},
		function(data) {
			console.log(data);
			//
		});
}

function loadCalendarView(){
	$.post(
		"calendar", {
			command: "loadCal",
			class: courseNum,
			user: username
		},
		function(data) {
			calendar.fullCalendar('removeEvents');
			console.log(data);
			var temp = JSON.parse(data);
			for(var i = 0; i < temp.events.length; i++) {
				var ev = temp.events[i];
				//function addEvent(evTitle, currentStartTime, endTime, create, desc){
				addEvent(ev.title, ev.start, ev.end, ev.creator, ev.desc);
			}
		});
}



function newEvent(name, start, end, recurring, descrip){
	$.post(
		"calendar", {
			command: "newEvent",
			user: username,
			crn: crns[courseNum],
			title: name,
			startTime: start,
			endTime: end,
			desc: descrip
		},
		function(data) {
			console.log(data);
			//
		});
}
function removeEvent(eid){
	$.post(
		"calendar", {
			command: "removeEvent",
			user: username,
			crn: crns[courseNum],
			id: eid
		})
}

function editEvent(eId, name, start, end, recurring, descrip){
 $.post(
		"calendar", {
			command: "editEvent",
			user: username,
			id: eId,
			title: name,
			crn: crns[courseNum],
			startTime: start.format,
			endTime: end.format,
			desc: descrip
		},
		function(data) {
			console.log(data);
			//
		});
}

function joinEvent(eId){
	$.post(
		"calendar", {
			command: "joinEvent",
			user: username,
			crn: crns[courseNum],
			id: eId
		},
		function(data) {
			console.log(data);
			//
		});
}

function logOut(){
	var c = document.cookie.split("; ");
 	for (i in c)
  		document.cookie =/^[^=]+/.exec(c[i])[0]+"=;expires=Thu, 01 Jan 1970 00:00:00 GMT";
	location.reload();
}


/*------###############----GRAVEYARD---########################################--*/

/*

OLD MESSAGING CODE

function newMsg(text) { fuck this function
	$.post(
		"http://localhost:8080/chatserver", {
			command: "message",
			crn: crns[courseNum],
			user: username,
			content: text
		},
		function(data) {
			console.log("Message from Server!: " + data);
			var temp = JSON.parse(data);
			addMessage(temp.content, temp.user, temp.id);
			//
		});
}







function loadChat(){
	$.post(
		"loadChat", {
			command: "loadChat",
			crn: crns[courseNum],
			user: username
		},
		function(data) {
			console.log(data);
		});
}

*/

//BUILDER FUNCTIONS

function setOP(title, description, date, score, user, eid){
	$('#originalPost').attr('id', eid);
	$('#originalPost .author').text(user);
	$('#originalPost .title').text(title);
	$('#originalPost .preview').text(description);
	$('#originalPost .score').text(score);
	$('#originalPost .date').text(date);
}

// $('.score').click(function(e) {
// 	if($(this).hasClass("voted")){
// 		$(this).text(parseInt($(this).text()) - 1);
// 		$(this).removeClass("voted");
// 		scoreUp($(this).parent().attr("id"));
// 	} else {
// 		$(this).text(parseInt($(this).text()) + 1);
// 		$(this).addClass("voted");
// 		scoreDown($(this).parent().attr("id"));
// 	}
// })

function addPost(title, description, date, replies, score, user, eid, upNoted){
	var newPost = $("<div>", {id: eid, class: "forumPost"});
	var scorebut;
	if(upNoted)
		scorebut = $("<span>", {text: score, class: "score voted"});
	else
		scorebut = $("<span>", {text: score, class: "score"});
	scorebut.click(function(e) {
		if($(this).hasClass("voted")){
			$(this).text(parseInt($(this).text()) - 1);
			$(this).removeClass("voted");
			scoreUp($(this).parent().attr("id"));
		} else {
			$(this).text(parseInt($(this).text()) + 1);
			$(this).addClass("voted");
			scoreDown($(this).parent().attr("id"));
		}
	});
	newPost.append($("<span>", {text: user, class: "author"}));
	newPost.append(scorebut);
	newPost.append($("<span>", {text: replies, class: "replies"}));
	newPost.append($("<span>", {text: date, class: "date"}));
	newPost.append($("<span>", {text: title, class: "title"}));
	newPost.append($("<span>", {text: description, class: "preview"}));
	$("#forumPosts").prepend(newPost);
}

function addReply(description, date, score, user, eid, upNoted){
	var newReply = $("<div>", {id: eid, class: "forumReply"});
	var scorebut;
	if(upNoted)
		scorebut = $("<span>", {text: score, class: "score voted"});
	else
		scorebut = $("<span>", {text: score, class: "score"});
	scorebut.click(function(e) {
		if($(this).hasClass("voted")){
			$(this).text(parseInt($(this).text()) - 1);
			$(this).removeClass("voted");
			scoreUp($(this).parent().attr("id"));
		} else {
			$(this).text(parseInt($(this).text()) + 1);
			$(this).addClass("voted");
			scoreDown($(this).parent().attr("id"));
		}
	});
	newReply.append($("<span>", {text: user, class: "author"}));
	newReply.append($("<span>", {text: score, class: "score"}));
	newReply.append($("<span>", {text: date, class: "date"}));
	newReply.append($("<span>", {text: description, class: "preview"}));
	var reportbut = $("<span>", {text: "_", class: "reportPost"});
	reportbut.click(function() {
		$('#darkBox').fadeIn();
		$('#reportBox').fadeIn();
		$('#reportedSender').text($(this).parent().children('.author').text());
		$('#reportedMessage').text(truncate($(this).parent().children('.preview').text(), 100));
	})
	newReply.append(reportbut)
	//add an upvote, report handler

	$("#forumReplies").append(newReply);
}

function addMessage(message, user, eid){
	var newMsg = $('<div>', {id: eid, class: "chatMessageBox"});
	var container = $("<div>", {class: "messageContainer"})
	if(username == user){
		newMsg.append($("<span>", {text: "You", class: "sender you"}));
		container.append($("<span>", {text: message, class: "toMessage"}))

	} else {
		newMsg.append($("<span>", {text: user, class: "sender"}));
		container.append($("<span>", {text: message, class: "fromMessage"}));
		newMsg.append($("<span>", {text: "Report", class: "reportButton button"}))
		//TODO: ADD HANDLER
	}
	newMsg.append(container);
	$("#chatBox").append(newMsg);
	$("#chatBox").animate({ scrollTop: $("#chatBox").prop("scrollHeight") }, 100);
}

function addEvent(evTitle, currentStartTime, endTime, create, desc){
	calendar.fullCalendar('renderEvent',
		{
			title: evTitle,
			start: currentStartTime,
			end: endTime,
			creator: create,
			description: desc
		},
		true // make the event "stick"
	);
}


//Darkboxes-------------------------------------------------------------------------


$('#darkBox *').click(function(e) {
	e.stopPropagation();
});


$('#loginButton').click(function() {
	$('#darkBox').fadeIn();
	$('#loginBox').fadeIn();
});

$('#newPostButton').click(function() {
	$('#darkBox').fadeIn();
	$('#newPostBox').fadeIn();
});

$('#submitPost').click(function() {
	if($('#newPostTitle').val() != '' && $('#newPostContent').val() != ''){
		$('#darkBox').fadeOut();
		$('#newPostBox').fadeOut();
		//title, description, date, replies, score, user, id
		newPost($('#newPostTitle').val(), $('#newPostContent').val())
		//TEMPORARY
		addPost($('#newPostTitle').val(),
				$('#newPostContent').val(),
				new Date,
				0,
				0,
				username,
				Math.floor(Math.random()*1000000));
	}
});

$('#submitReply').click(function(){
	$('#darkBox').fadeOut();
	$('#newReplyBox').fadeOut();
	//desc, date, score, user, id
	newReply($('#originalPost').attr("id") ,$('#newReplyContent').val())
	addReply(
			$('#newReplyContent').val(),
			new Date,
			0,
			username,
			Math.floor(Math.random()*1000000),
			false);
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
	$('#newEventBox').fadeOut();
	$('#eventOpsBox').fadeOut();
	$('#editEventBox').fadeOut();
});


//HEADER CODE -------------------------------------------------------------------------

$('#profileButton').click(function() {
	$('#darkBox').fadeIn();
	$('#userBox').fadeIn();
	$('#usern').text(username);
});

$('#settingsButton').click(function() {
	$('#darkBox').fadeIn();
	$('#settingsBox').fadeIn();
});

//VIEW CODE ----------------------------------------------------------------------------

$('#forBut').click(function() {
	if(!$('#calendarView').hasClass("hidden")){
		$('#calendarView').fadeOut(function(){
			loadForum();
			$('#forum').fadeIn();
			$('#forum').removeClass("hidden");
			closeSocket();
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
			openSocket();

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
			closeSocket();

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

//LOGIN CODE

$('#passwordBox').keypress(function(e) {
	if( e.which == 13) {
		alert('hi ' + $('#usernameBox').val());
		$('#loginBox').fadeOut();
		$('#loadingBox').fadeIn(300);
		$('#loadingBox').fadeOut(300);
		$('#darkBox').fadeOut();
		loading = true;
		if(login($('#usernameBox').val(),$('#passwordBox').val()) || true) {
			$('#splash').addClass('hidden');
			$('#calendarView').removeClass('hidden');
			$('#calendarView').fadeIn();
			username = $('#usernameBox').val();
		} else {
			$('#loadingBox').fadeOut(300);
			$('#loginBox').fadeIn(300);
		}
		loading = false;

	}
})


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



$('#classButton #classMenu li').click(function() {
	className = $(this).text();
	$('#chatBox').empty();
	closeSocket();
	//handle with messageHandler
	//sendMessage("$" + courseName + "$")
	alert(className);
})


$('#chatInput').keypress(function(e) {
	if( e.which == 13 && $('#chatInput').val() != '') {
		sendMessage($('#chatInput').val());
		$('#chatInput').val('');
	}
});

$('#sendInputButton').click(function(){
	if($('#chatInput').val() != ''){
		sendMessage($('#chatInput').val());
		$('#chatInput').val('');
	}
})


//REPORT CODE ------------------------------------------------------------------

$('.reportButton').click(function(){
	$('#darkBox').fadeIn();
	$('#reportBox').fadeIn();
	$('#reportedSender').text($(this).parent().children('.sender').text());
	$('#reportedMessage').text($(this).parent().children('.fromMessage').text());

})

$('.reportPost').click(function() {
	$('#darkBox').fadeIn();
	$('#reportBox').fadeIn();
	$('#reportedSender').text($(this).parent().children('.author').text());
	$('#reportedMessage').text(truncate($(this).parent().children('.preview').text(), 100));

})





//Forum View Code--------------------------------------------------------------------

$('.score').click(function(e) {
	if($(this).hasClass("voted")){
		$(this).text(parseInt($(this).text()) - 1);
		$(this).removeClass("voted");
		scoreUp($(this).parent().attr("id"));
	} else {
		$(this).text(parseInt($(this).text()) + 1);
		$(this).addClass("voted");
		scoreDown($(this).parent().attr("id"));
	}
})

$('#newReplyButton').click(function() {
	$('#darkBox').fadeIn();
	$('#newReplyBox').fadeIn();
	$('#newReplyButton').attr('backgroundColor', 'red');
})


//Report View ------------------------------------------------------------------
$('#reportCheating').click(function(){
	report($(this).parent().data("item").id, "cheating")
	$('#darkBox').fadeOut();
	$('#reportBox').fadeOut();
})

$('#reportOther').click(function(){
	report($(this).parent().data("item").id, "other");
	$('#darkBox').fadeOut();
	$('#reportBox').fadeOut();
})


//Calendar code ------------------------------------------------------------------------

$('#joinEvent').click(function(){
	//joinEvent($(this));
	$('#darkBox').fadeOut();
	$('#eventOpsBox').fadeOut();
	$(this).parent().data('event').backgroundColor = "#77B7FC";
})

$('#removeEvent').click(function(){
	calendar.fullCalendar('removeEvents',$(this).parent().data("event").id);
	$('#darkBox').fadeOut();
	$('#eventOpsBox').fadeOut();
})

$('#reportEvent').click(function(){
	$('#reportedSender').text($('#eventAuthor').text());
	$('#reportedMessage').text($('#eventOpTitle').text());
	$('#reportBox').data("item", $(this).parent().data("event"))
	$('#eventOpsBox').hide();
	$('#reportBox').fadeIn();
})

$('#hideEvent').click(function(){
	calendar.fullCalendar('removeEvents',$(this).parent().data("event").id);
	//Need a local hiding system
})


$('#submitEvent').click(function(){
	var endTime = moment(currentStartTime);
	endTime.hour(currentStartTime.hour() + parseInt($('#newEventDuration').val()));

	newEvent($('#newEventTitle').val(),
			 currentStartTime.format(),
			 endTime.format(),
			 $('#newEventRecurring').is(":checked"),
			 $('#newEventContent').val()
			)
	$('#darkBox').fadeOut();
	$('#newEventBox').fadeOut();
	calendar.fullCalendar('renderEvent',
		{
			title: $('#newEventTitle').val(),
			start: currentStartTime,
			end: endTime,
			creator: username,
			description: $('#newEventContent').val()
		},
		true // make the event "stick"
	);
})

$('#editEvent').click(function(){
	$('#eventOpsBox').fadeOut();
	$('#editEventBox').fadeIn();
	var ev = $('#eventOpsBox').data('event');
	$('#editEventDuration').val(ev.end.hour() - ev.start.hour());
	$('#editEventTitle').val(ev.title);
	$('#editEventContent').val(ev.description);

})

$('#editEventSubmit').click(function(){
	var ev = $('#eventOpsBox').data('event');
	ev.title = $('#editEventTitle').val();
	ev.content = $('#editEventContent').val();
	ev.end.hour(ev.start.hour() + parseInt($('#editEventDuration').val())); //THIS CODE IS SHAKEY WATCH OUT

	$('#darkBox').fadeOut();
	$('#newEventBox').fadeOut();
	calendar.fullCalendar('renderEvent',
		{
			title: $('#editEventTitle').val(),
			start: ev.start.hours(),
			end: ev.end.hours(),
			creator: username,
			description: $('#editEventContent').val()
		},
		true // make the event "stick"
	);
	newEvent($('#newEventTitle').val(),
			 ev.start.format('X'),
			 ev.end.format('X'),
			 false,
			 $('#editEventContent').val()
			)
	calendar.fullCalendar('removeEvents', ev.id);
})

$(document).ready(function() {
	$('#loadingBox').fadeOut();
	$('#loginBox').fadeOut();
	$('#userBox').fadeOut();
	$('#darkBox').fadeOut();
	$('#settingsBox').fadeOut();
	$('#reportBox').fadeOut();
	$('#newPostBox').fadeOut();
	$('#newReplyBox').fadeOut();
	$('#newEventBox').fadeOut();
	$('#eventOpsBox').fadeOut();
	$('#editEventBox').fadeOut();

	var date = new Date();
	var d = date.getDate();
	var m = date.getMonth();
	var y = date.getFullYear();

    // page is now ready, initialize the calendarView...
    calendar = $('#calendar').fullCalendar({
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
	        	$('#newEventTimeScale').text(date.format("h:mm"));
	    	else
	        	$('#newEventTimeScale').text(date.hours() + ":" + date.minutes());
	        currentStartTime = date;
    	},
    	eventClick: function(calEvent, jsEvent, view) {
	        $('#darkBox').fadeIn();
	        $('#eventOpsBox').fadeIn();
	        $('#eventOpsBox').data("event",calEvent);
	        $('#eventOpTitle').addClass(calEvent.id);
	        $('#eventOpTitle').text(calEvent.title);
	        $('#eventAuthor').text(calEvent.creator);
	        $('#eventOpTimeScale').text(calEvent.start.format("h:mm") + " - " + calEvent.end.format("h:mm"));
	        $('#eventOpDesc').text(calEvent.description);
	        if(calEvent.creator == username){
	        	$('#editEvent').removeClass('hidden')
	        	$('#editEvent').show();
	        	$('#removeEvent').removeClass('hidden')
	        	$('#removeEvent').show();
	        }

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
    })

});