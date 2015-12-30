checkUserIsLogin('../login').then(function(response)
{
    if(response == false)
        document.location.href='../index.html';

});
$(document).ready(function()
{
     var idBooksAuthors,
         idAuthor,
         idBook,
         $inputsUpdate = $('#inputs-update').children(),
         $tbody = $('#books-authors-tbody');

     doAjaxGet('getAllBooksAuthors', 'json').then(
     function(response)
     {
         makeRowsInTable(response, $tbody);
     });

     doAjaxGet('getAllAuthors', 'json').then(
     function(response)
     {
         createOptionSelect($('#select-author-add'), response);
         createOptionSelect($inputsUpdate.eq(1), response);
     });

     doAjaxGet('getAllBooks', 'json').then(
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
             doAjaxPost('deleteBooksAuthors', 'json', {idBooksAuthors: idBooksAuthors});
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
                 doAjaxPost('updateBooksAuthors', 'json', {idBooksAuthors: newIdBooksAuthors, idAuthor: newIdAuthor, idBook: newIdBook });
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
     		doAjaxPost('addBooksAuthors', 'json', {idAuthor: idAuthor, idBook: idBook});
     	}
     });
});