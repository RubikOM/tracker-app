<div class="form-row">
    <div class="col-md-4 mb-3">
        <form:label class="larger-font" path="word">Input word in English *</form:label>
        <div class="input-group">
            <div class="input-group-prepend">
                        <span class="input-group-text" id="validationTooltipWord">
                            <i class="fas fa-edit"></i></span>
            </div>
            <form:input class="form-control" path="word" placeholder="Word in English"/>
        </div>
        <form:errors path="word" class="error"/>
    </div>

    <div class="col-md-2 mb-3">
        <form:label class="larger-font" path="transcription">Transcription </form:label>
        <div class="input-group">
            <div class="input-group-prepend">
                        <span class="input-group-text" id="validationTooltipTranscription">
                            <i class="fab fa-readme"></i></span>
            </div>
            <form:input class="form-control" path="transcription" placeholder="[Transcription]"/>
        </div>
    </div>

    <div class="col-md-6 mb-3">
        <form:label class="larger-font" path="translation">Input Russian translation * </form:label>
        <div class="input-group">
            <div class="input-group-prepend">
                        <span class="input-group-text" id="validationTooltipTranslation">
                            <i class="fas fa-edit"></i></span>
            </div>
            <form:input class="form-control" path="translation" placeholder="Russian translation"/>
        </div>
        <form:errors path="translation" class="error"/>
    </div>
</div>

<div class="padding-top-required">
    <form:label class="larger-font" path="example">Input Example on English </form:label>
    <div class="input-group">
        <div class="input-group-prepend">
                        <span class="input-group-text" id="validationTooltipExample">
                            <i class="fas fa-book-open"></i></span>
        </div>
        <form:input type="text" class="form-control" path="example" placeholder="Example in English"/>
    </div>
</div>

<div class="padding-top-required">
    <form:label class="larger-font" path="examplesTranslation">Input Example's translation in Russian </form:label>
    <div class="input-group ">
        <div class="input-group-prepend ">
                        <span class="input-group-text" id="validationTooltipExampleTranslation">
                            <i class="fas fa-book-open"></i></span>
        </div>
        <form:input type="text" class="form-control" path="examplesTranslation"
                    placeholder="Example's translation"/>
    </div>
</div>