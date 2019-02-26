<p>Your todays dictionaryElements down here
    <a href="${root}/getTxtFile">Get .txt file with all your today's words </a>
</p>
<p><a href="${root}/getTxtFile">Get .txt file with all time created words </a></p>
<table id="wordsTable" class="table table-condensed table-striped">
    <tr>
        <td>Word</td>
        <td>Transcription</td>
        <td>Translation</td>
        <td>Example</td>
        <td>Example translation</td>
        <td>Created</td>
        <td>Actions</td>
    </tr>
    <c:forEach items="${todaysAddedElements}" var="dictionaryElement">
        <tr class="table">
            <td> ${dictionaryElement.word}</td>
            <td> ${dictionaryElement.transcription}</td>
            <td> ${dictionaryElement.translation}</td>
            <td> ${dictionaryElement.example}</td>
            <td> ${dictionaryElement.examplesTranslation}</td>
            <td> ${dictionaryElement.creationDate}</td>
            <td><input type="button" class="btn-danger delete-button"
                       delete-dictionaryElement=${dictionaryElement.word} value="Delete">
                <input type="button" class="btn btn-secondary edit-button"
                       edit-dictionaryElement=${dictionaryElement.word} value="Edit">
            </td>
        </tr>
    </c:forEach>
</table>
