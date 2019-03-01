<p>Get your ALL TIME added words by click <a href="${root}/dictionary/getAllTimeTxtFile">here </a>
</p>
<div class="table-wrapper">
    <table id="wordsTable" class="table table-condensed table-striped table-hover">
        <caption class="larger-font">Your today created words are here</caption>
        <tr>
            <td style="width: 8%">Word</td>
            <td style="width: 8%">Transcription</td>
            <td style="width: 16%">Translation</td>
            <td style="width: 25%">Example</td>
            <td style="width: 25%">Example translation</td>
            <td style="width: 6%">Created</td>
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
</div>