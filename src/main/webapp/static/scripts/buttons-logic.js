function deleteDictionaryElement () {
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
};

$(document).ready(function () {
    $(".edit-button").click(function () {
        var editWord = $(this).attr("edit-dictionaryElement");
        $.ajax({
            type: "GET",
            url: "/dictionary/edit/" + editWord
        })
    })
});