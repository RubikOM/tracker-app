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

$(document).ready(function () {
    $(".edit-button").click(function () {
        var editWord = $(this).attr("edit-dictionaryElement");
        $.ajax({
            type: "PATCH",
            url: "/dictionary/edit/" + editWord,
            success: function () {
                location.reload();
            }
        })
    })
});