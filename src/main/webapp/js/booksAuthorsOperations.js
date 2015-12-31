$(document).ready(function()
{
     var idBooksAuthors,
         idAuthor,
         idBook,
         $inputsUpdate = $('#inputs-update').children(),
         $tbody = $('#books-authors-tbody');

     doAjax('../rest/booksAuthors/getAllBooksAuthors', 'GET', 'JSON', '').then(
     function(response)
     {
         makeRowsInTable(response, $tbody);
     });

     doAjax('../rest/author/getAllAuthors', 'GET', 'JSON', '').then(
     function(response)
     {
         createOptionSelect($('#select-author-add'), response);
         createOptionSelect($inputsUpdate.eq(1), response);
     });

     doAjax('../rest/book/getAllBooks',  'GET', 'JSON', '').then(
     function(response)
     {
         createOptionSelect($('#select-book-add'), response);
         createOptionSelect($inputsUpdate.eq(2), response);
     });

     $tbody.on('click', '.remove-row', function()
     {
         idBooksAuthors = $(this).closest('tr').children().eq(0).text();
     });

     $("#delete-btn").on('click', function()
     {
         if(typeof idBooksAuthors != null && typeof idBooksAuthors != 'undefined' )
         {
             doAjax('../rest/booksAuthors/deleteBooksAuthors', 'DELETE', '', {idBooksAuthors: idBooksAuthors})
                .success(function(response){ location.reload(true); });
         }
     });

     $tbody.on('click', '.update-row', function()
     {
        var $this = $(this).closest('tr').children();
        $('#inputs-update select').children().prop('selected', false);

        idBooksAuthors = $this.eq(0).text();
        idAuthor = $this.eq(1).attr('data-value');
        idBook= $this.eq(2).attr('data-value');
        $inputsUpdate.eq(0).val(idBooksAuthors);

        $inputsUpdate.eq(1).find('option[value='+idAuthor+']').prop('selected', true);
        $inputsUpdate.eq(2).find('option[value='+idBook+']').prop('selected', true);
     });

     $('#update-form').submit(function(e)
     {
         var newIdBooksAuthors = $inputsUpdate.eq(0).val(),
             newIdAuthor = $inputsUpdate.eq(1).val(),
             newIdBook = $inputsUpdate.eq(2).val();
         if(idBooksAuthors != '' && typeof idBooksAuthors != 'undefined' && idBooksAuthors == newIdBooksAuthors)
         {
             if(idAuthor != newIdAuthor || newIdBook != idBook )
             {
                 doAjax('../rest/booksAuthors/updateBooksAuthors', 'PUT', '', {idBooksAuthors: newIdBooksAuthors, idAuthor: newIdAuthor, idBook: newIdBook })
                    .success(function(response){ location.reload(true); });
             }
             $('#update-modal').modal('hide');
             e.preventDefault();
         }
     });

     $('#add-btn').click(function()
     {
     	var $addAlert = $('#add-alert');
     	$addAlert.removeClass('in');
     	$addAlert.text('');
     	idAuthor = $('#select-author-add').val();
     	idBook = $('#select-book-add').val();
     	if(idAuthor == '')
     	{
     		$addAlert.text('You have not selected author...');
     		$addAlert.addClass('in');
     	}
     	else if(idBook  == '')
     	{
     		$addAlert.text('You have not selected book...');
     		$addAlert.addClass('in');
     	}
     	else
     	{
     		doAjax('../rest/booksAuthors/addBooksAuthors', 'POST', '', {idAuthor: idAuthor, idBook: idBook})
     		    .success(function(response){ location.reload(true); });
     	}
     });
});