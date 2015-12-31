$(document).ready(function()
{
    var idBook,
        title,
        relaseDate,
        relase,
        $updateAlert,
        $inputsUpdate,
        $updateAlert = $('#update-alert'),
        $inputsUpdate = $('#inputs-update').children(),
        $tbody = $('#book-tbody');

    $( '.form-date' ).datetimepicker( { format: 'YYYY-MM-DD' } );

    doAjax('../rest/book/getAllBooks','GET', 'JSON', '').then(
    function(response)
    {
        makeRowsInTable(response, $tbody);
    });

    $tbody.on('click', '.remove-row', function()
    {
        idBook = $(this).closest('tr').children().eq(0).text();
    });

    $("#deleteRecord").on('click', function()
    {
        if(typeof idBook != null && typeof idBook != 'undefined')
        {
            doAjax('../rest/book/deleteBook', 'DELETE', '', {idBook: idBook} ).success(function(response){ location.reload(true); });
        }
    });

    $tbody.on('click', '.update-row', function()
    {
        var $this = $(this).closest('tr').children();
        $updateAlert.removeClass('in');
        $updateAlert.text('');
        idBook = $this.eq(0).text();
        title = $this.eq(1).text();
        relaseDate = $this.eq(2).text();
        relase = $this.eq(3).text();
        $inputsUpdate.eq(0).val(idBook);
        $inputsUpdate.eq(1).val(title);
        $inputsUpdate.eq(2).val(relaseDate);
        $inputsUpdate.eq(3).val(relase);

    });

    $('#update-form').submit(function(e)
    {
        $updateAlert.removeClass('in');
        $updateAlert.text('');
        var newIdBook = $inputsUpdate.eq(0).val(),
            newTitle = $inputsUpdate.eq(1).val(),
            newRelaseDate = $inputsUpdate.eq(2).val(),
            newRelase = $inputsUpdate.eq(3).val();
        if(idBook != '' && typeof idBook != 'undefined' && idBook == newIdBook)
        {
            if(title == newTitle && relaseDate == newRelaseDate && relase == newRelase)
            {
                $('#update-modal').modal('hide');
            }
            else if(newTitle.length < 2 || newTitle.length > 30)
            {
                $updateAlert.text('Title should have minimum 3 and maximum 30 characters!');
                $updateAlert.addClass('in');
            }
            else if(!isValidDate(newRelaseDate))
            {
                $updateAlert.text('Relase Date is incorrect (correct format is yyyy-mm-dd)');
                $updateAlert.addClass('in');
            }
            else if(isNaN(newRelase) || newRelase == '' || newRelase.length > 10)
            {
                $updateAlert.text('Relase should be a number (max 10 numbers)!');
                $updateAlert.addClass('in');
            }
            else
            {
                doAjax('../rest/book/updateBook', 'PUT', '', {idBook: newIdBook, title: newTitle, relaseDate: newRelaseDate, relase: newRelase})
                    .success(function(response){ location.reload(true); });
                $('#update-modal').modal('hide');
            }
            e.preventDefault();
        }
    });

    $('#add-btn').click(function()
    {
        var $addAlert = $('#add-alert');
        $addAlert.removeClass('in');
        $addAlert.text('');
        title = $('#title').val();
        relaseDate = $('#relase-date').val();
        relase = $('#relase').val();
        if(title.length < 2 || title.length > 30)
        {
        	$addAlert.text('Title should have minimum 3 and maximum 30 characters!');
        	$addAlert.addClass('in');
        }
        else if(!isValidDate(relaseDate))
        {
        	$addAlert.text('Relase Date is incorrect (correct format is yyyy-mm-dd)');
        	$addAlert.addClass('in');
        }
        else if(isNaN(relase) || relase == '' || relase.length > 10)
        {
        	$addAlert.text('Relase should be a number (max 10 numbers)!');
        	$addAlert.addClass('in');
        }
        else
        {
        	doAjax('../rest/book/addBook', 'POST', '', { title: title, relaseDate: relaseDate, relase: relase})
        	    .success(function(response){ location.reload(true); });
        }
    });
});



