$(document).ready(function () {
    $(".delete-word-button").click(function () {
        var deleteWord = $(this).attr("delete-dictionaryElement");
        $.ajax({
            type: "DELETE",
            url: "/dictionary/delete/" + deleteWord,
            success: function () {
                location.reload();
            }
        })
    })
});

$(document).ready(function () {
    $("#go-to-api-button").click(function () {
        $.ajax({
            type: "GET",
            url: "/fillPage/" + $('#word-input').val(),
            success: function (data) {
                document.getElementById("word-input").value = data.word;
                document.getElementById("translation-input").value = data.translation;
                document.getElementById("transcription-input").value = data.transcription;
                document.getElementById("example-input").value = data.example;
                document.getElementById("exampleTranslation-input").value = data.exampleTranslation;
            }
        })
    })
});

$(document).ready(function () {
    $(".edit-word-button").click(function () {
        var editWord = $(this).attr("edit-dictionaryElement");
        $.ajax({
            type: "GET",
            url: "/dictionary/edit/" + editWord
        })
    })
});