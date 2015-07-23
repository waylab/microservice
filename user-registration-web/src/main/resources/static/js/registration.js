$(function() {

	$("#registration-form").validate({

		rules: {
			email: {
				required: true,
				email: true
			},
			password: {
				required: true,
				minlength: 8
			}
		},

		messages: {
			password: {
				required: "Please provide a password",
				minlength: "Your password must be at least 8 characters long"
			},
			email: "Please enter a valid email address"
		},

		submitHandler: function(form) {
			form.submit();
		}
	});

});