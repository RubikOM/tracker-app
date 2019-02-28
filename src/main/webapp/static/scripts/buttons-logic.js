$(document).ready(function () {
    $(".delete-button").click(function () {
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

// TODO if word contains several words - only 1 will be sent (FUUUUUUUUUUUUUUUUUUUUUUUUUUCK)
$(document).ready(function () {
    $(".edit-button").click(function () {
        var editWord = $(this).attr("edit-dictionaryElement");
        $.ajax({
            type: "GET",
            url: "/dictionary/edit/" + editWord
        })
    })
});