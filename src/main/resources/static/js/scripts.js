function checkBillingAddress() {
    if($("#theSameAsShippingAddress").is("checked")) {
        $(".billingAddress").prop("disabled", true);
    } else {
        $(".billingAddress").prop("disabled", false);
    }
}

function checkPasswordMatch() {
    var password = $("#txtNewPassword").val();
    var confirmPassword = $("#txtConfirmPassword").val();

    if(password == "" && confirmPassword == "") {
        $("#checkPasswordMatch").html("");
        $("updateUserInfoButton").prop('disabled', false);
    } else {
        if(password != confirmPassword) {
            $("#checkPasswordMatch").html("Hasła nie są takie same");
            $("updateUserInfoButton").prop('disabled', true);
        } else {
            $("#checkPasswordMatch").html("Hasła są takie same");
            $("updateUserInfoButton").prop('disabled', false);
        }
    }
}

$(document).ready(function (){
   $("#theSameAsShippingAddress").on('click', checkBillingAddress);
   $("#txtConfirmPassword").keyup(checkPasswordMatch);
   $("#txtNewPassword").keyup(checkPasswordMatch);
});