var id = 0;

$(document).ready(function () {
    //random quote of today
    getRandomQuote();

    //get new random quote btn
    $("#getRandomQuoteBtn").click(function () {
        $("#quoteOfToday").text(getRandomQuote());
    });

    //listen for changes in quote input field 
    $("#findQuote").keyup(function () {
        var input = $("#findQuote").val();
        console.log("input is: " + input);
        if (input !== "" && $.isNumeric(input)) {
            //is number, enable button
            $('#findQuoteBtn').prop('disabled', false);
            //get quote by id
            $("#findQuoteBtn").click(function () {
                getQuotebyId();
            });
        } else {
            //is text, disable button
            $('#findQuoteBtn').prop('disabled', true);
        }
    });

    //save changes to quote
    $("#putQuoteBtn").click(function () {
        if (id !== 0) {
            console.log("id is not 0!, but: " + id);
            updateQuote();
        } else {
            console.log("id is 0!: " + id);
            //do nothing
        }
    });

    //create quote
    $("#createQuote").submit(function (event) {
        event.preventDefault();
        createQuote();

    });

    //delete quote
    $("#deleteQuoteBtn").click(function () {
        deleteQuote();
    });
});

function createQuote() {
    var quote = new Object();
    quote.quote = $("#createQuote").find('input[name="createQuoteText"]').val();
    url = "http://localhost:8080/RESTwithJAX_RS_Quote/api/quote";
    $.ajax({
        url: url,
        type: "POST",
        dataType: "json",
        data: JSON.stringify(quote),
        contentType: 'application/json',
        mimeType: 'application/json',
        success: function (data) {
            quote.quote = $("#createQuote").find('input[name="createQuoteText"]').val("");
            $("#error").attr("class", "alert alert-success");
            $("#error").show().html("Succesfully created!");
        },
        fail: function (data) {
            var json = data.responseJSON;
            $("#error").attr("class", "alert alert-danger");
            $("#error").show().html(json.message);
        }
    });
}

function updateQuote() {
    var quote = new Object();
    quote.quote = $("#editQuote").find('input[name="updateQuoteText"]').val();
    url = "http://localhost:8080/RESTwithJAX_RS_Quote/api/quote/" + id;
    $.ajax({
        url: url,
        type: "PUT",
        dataType: "json",
        data: JSON.stringify(quote),
        contentType: 'application/json',
        mimeType: 'application/json',
        success: function () {
            $("#error").attr("class", "alert alert-success");
            $("#error").show().html("Succesfully updated!");
        },
        error: function (data) {
            var json = data.responseJSON;
            $("#error").attr("class", "alert alert-danger");
            $("#error").show().html(json.message);
        }
    });
}

function deleteQuote() {
    console.log("id is: " + id);
    var url = "http://localhost:8080/RESTwithJAX_RS_Quote/api/quote/" + id;
    $("#findQuote").text("");
    $.ajax({
        url: url,
        method: "DELETE",
        success: function (data) {
            $("#error").attr("class", "alert alert-success");
            $("#error").show().html("Succesfully deleted!");
            $("#findQuote").val(data.quote);
            $('#findQuoteBtn').prop('disabled', true);
        },
        error: function (data) {
            var json = data.responseJSON;
            $("#error").attr("class", "alert alert-danger");
            $("#error").show().html(json.message);
        }
    });
}

function getRandomQuote() {
    var url = "http://localhost:8080/RESTwithJAX_RS_Quote/api/quote/random";
    $("#findQuote").text("");
    $.ajax({
        url: url,
        method: "GET",
        success: function (data) {
            $("#quoteOfToday").text(data.quote);
        },
        error: function (data) {
            var json = data.responseJSON;
            $("#error").attr("class", "alert alert-danger");
            $("#error").show().html(data.message);
        }
    });
}

function getQuotebyId() {
    id = $("#findQuote").val();
    console.log("id is: " + id);
    var url = "http://localhost:8080/RESTwithJAX_RS_Quote/api/quote/" + id;
    $("#findQuote").text("");
    $.ajax({
        url: url,
        method: "GET",
        success: function (data) {
            $("#error").attr("class", "alert alert-success");
            $("#error").show().html("Success!");
            $("#findQuote").val(data.quote);
            $('#findQuoteBtn').prop('disabled', true);
        },
        error: function (data) {
            var json = data.responseJSON;
            $("#error").attr("class", "alert alert-danger");
            $("#error").show().html(json.message);
        }
    });
}




