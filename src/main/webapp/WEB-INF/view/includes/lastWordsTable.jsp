<p>Get your ALL TIME added words by click <a href="${root}/getAllTimeTxtFile">here </a>
</p>
<div class="table-wrapper">
    <table id="wordsTable" class="table table-condensed table-striped table-hover">
        <span>Your ${lastAddedElements.size()} last created words displayed here.</span>
        <tr>
            <td style="width: 10%">Word</td>
            <td style="width: 8%">Transcription</td>
            <td style="width: 16%">Translation</td>
            <td style="width: 24%">Example</td>
            <td style="width: 24%">Example translation</td>
            <td style="width: 6%">Created</td>
        </tr>
        <c:forEach items="${lastAddedElements}" var="dictionaryElement">
            <tr class="table">
                <td> ${dictionaryElement.word}</td>
                <td> ${dictionaryElement.transcription}</td>
                <td> ${dictionaryElement.translation}</td>
                <td> ${dictionaryElement.example}</td>
                <td> ${dictionaryElement.exampleTranslation}</td>
                <td> ${dictionaryElement.creationDate}</td>
            </tr>
        </c:forEach>
    </table>
</div>