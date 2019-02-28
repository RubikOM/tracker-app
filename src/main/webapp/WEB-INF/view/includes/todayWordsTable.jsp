<p>Get your today added words by click <a href="${root}/dictionary/getTxtFile">here </a> ||
    Get your ALL TIME added words by click <a href="${root}/dictionary/getAllTimeTxtFile">here </a></p>
<div class="table-wrapper">
    <table id="wordsTable" class="table table-condensed table-striped table-hover">
        <caption class="larger-font">Your today created words are here</caption>
        <tr style="height: 1em">
            <td style="width: 8%">Word</td>
            <td style="width: 8%">Transcription</td>
            <td style="width: 20%">Translation</td>
            <td style="width: 20%">Example</td>
            <td style="width: 25%">Example translation</td>
            <td style="width: 6%">Created</td>
            <td style="width: 8%">Actions</td>
        </tr>
        <c:forEach items="${todaysAddedElements}" var="dictionaryElement">
            <tr>
                <td> ${dictionaryElement.word}</td>
                <td> ${dictionaryElement.transcription}</td>
                <td> ${dictionaryElement.translation}</td>
                <td> ${dictionaryElement.example}</td>
                <td> ${dictionaryElement.examplesTranslation}</td>
                <td> ${dictionaryElement.creationDate}</td>
                <td><input type="button" class="btn btn-danger delete-button"
                           delete-dictionaryElement=${dictionaryElement.word} value="Delete">
                        <%--<input type="button" class="btn btn-secondary edit-button"
                               edit-dictionaryElement=${dictionaryElement.word} value="Edit">--%>
                    <a href="dictionary/edit/${dictionaryElement.word}">Edit </a>
                </td>
            </tr>
        </c:forEach>
    </table>
</div>