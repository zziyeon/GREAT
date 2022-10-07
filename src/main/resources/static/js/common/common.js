function newCkeditor(target, isReadOnly) {
 return ClassicEditor.create( target, {
         plugin:['ListStyle','Markdown','MediaEmbed','MediaEmbedToolbar'],
            toolbar: {
                items: [
                    'heading',
                    '|',
                    'underline',
                    'bold',
                    'italic',
                    'link',
                    'bulletedList',
                    'numberedList',
                    'todoList',
                    '|',
                    'outdent',
                    'indent',
                    '|',
                    'imageInsert',
                    'imageUpload',
                    'blockQuote',
                    'insertTable',
                    'mediaEmbed',
                    'markdown',
                    'undo',
                    'redo',
                    '|',
                    'highlight',
                    'fontFamily',
                    'fontColor',
                    'fontBackgroundColor',
                    'fontSize',
                    '|',
                    'htmlEmbed',
                    'specialCharacters'
                ]
            },
            language: 'en',
            image: {
                toolbar: [
                    'imageTextAlternative',
                    'imageStyle:full',
                    'imageStyle:side'
                ]
            },
            table: {
                contentToolbar: [
                    'tableColumn',
                    'tableRow',
                    'mergeTableCells',
                    'tableCellProperties',
                    'tableProperties'
                ]
            },
        })
        .then( editor => {
            window.editor = editor;
            console.log(editor);
            console.log("typeof(isReadOnly) -> " + typeof(isReadOnly));
            console.log("typeof(editor.isReadOnly) -> " + typeof(editor.isReadOnly));
            console.log("isReadOnly -> " + isReadOnly);
            console.log("editor.isReadOnly -> " + editor.isReadOnly);
            editor.isReadOnly = isReadOnly;  //읽기모드적용
            console.log("editor.style.visibility -> " + editor.style.visibility);
            editor.style.visibility = 'block';
        } )
        .catch( error => {
            console.error( error );
        } );
}