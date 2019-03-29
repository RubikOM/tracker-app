<p>Get your today added words by click <a href="${root}/getTxtFile">here </a> ||
    Get your ALL TIME added words by click <a href="${root}/getAllTimeTxtFile">here </a></p>
<div class="table-wrapper">
    <table id="wordsTable" class="table table-condensed table-striped table-hover">
        <caption class="larger-font">Your today created words are here</caption>
        <tr>
            <td style="width: 10%">Word</td>
            <td style="width: 8%">Transcription</td>
            <td style="width: 18%">Translation</td>
            <td style="width: 20%">Example</td>
            <td style="width: 20%">Example translation</td>
            <td style="width: 8%">Created</td>
            <td style="width: 12%">Actions</td>
        </tr>
        <c:forEach items="${todaysAddedElements}" var="dictionaryElement">
            <tr>
                <td> ${dictionaryElement.word}</td>
                <td> ${dictionaryElement.transcription}</td>
                <td> ${dictionaryElement.translation}</td>
                <td> ${dictionaryElement.example}</td>
                <td> ${dictionaryElement.exampleTranslation}</td>
                <td> ${dictionaryElement.creationDate}</td>
                <td><input type="button" class="btn btn-danger" id="delete-button"
                           delete-dictionaryElement='${dictionaryElement.word}' value="Delete">
                    <input type="button" class="btn btn-primary" id="edit-button"
                           edit-dictionaryElement='${dictionaryElement.word}' value=" Edit ">
                </td>
            </tr>
        </c:forEach>
    </table>
</div>