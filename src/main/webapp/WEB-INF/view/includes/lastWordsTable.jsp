<p>Your last added dictionaryElements down here
    <a href="${root}/getTxtFile">Get .txt file with all time created words </a>
</p>
<table id="wordsTable" class="table table-condensed table-striped">
    <tr>
        <td>Word</td>
        <td>Transcription</td>
        <td>Translation</td>
        <td>Example</td>
        <td>Example translation</td>
        <td>Created</td>
    </tr>
    <c:forEach items="${lastAddedElements}" var="dictionaryElement">
        <tr class="table">
            <td> ${dictionaryElement.word}</td>
            <td> ${dictionaryElement.transcription}</td>
            <td> ${dictionaryElement.translation}</td>
            <td> ${dictionaryElement.example}</td>
            <td> ${dictionaryElement.examplesTranslation}</td>
            <td> ${dictionaryElement.creationDate}</td>
        </tr>
    </c:forEach>
</table>