var loading = false;

$('#profileButton').click(function() {
	$('#darkBox').fadeIn();
	$('#userBox').fadeIn();
});

$('#settingsButton').click(function() {
	$('#darkBox').fadeIn();
	$('#settingsBox').fadeIn();
});

$('#loginButton').click(function() {
	$('#darkBox').fadeIn();
	$('#loginBox').fadeIn();
});

$('#darkBox').click(function(e) {
	if(loading)
		return;
	$('#loginBox').fadeOut();
	$('#userBox').fadeOut();
	$('#darkBox').fadeOut();
	$('#settingsBox').fadeOut();
	$('#reportBox').fadeOut();
});

$('#chatInput').keypress(function(e) {
	if( e.which == 13) {
		//sendInput($('#chatInput').val());
		$('#chatInput').val('');
	}
});

$('.reportButton').click(function(){
	$('#darkBox').fadeIn();
	$('#reportBox').fadeIn();
	$(this).parent().css("background-color", "red");
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